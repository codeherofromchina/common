package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.event.TasksAddEvent;
import com.erui.order.requestVo.DeliverConsignListCondition;
import com.erui.order.service.*;
import com.erui.order.util.BpmUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverConsignV2ServiceImpl implements DeliverConsignV2Service {
    private static Logger LOGGER = LoggerFactory.getLogger(DeliverDetailServiceImpl.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DeliverConsignDao deliverConsignDao;

    @Autowired
    DeliverConsignBookingSpaceDao deliverConsignBookingSpaceDao;

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private DeliverNoticeDao deliverNoticeDao;

    @Autowired
    private DeliverDetailDao deliverDetailDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    private BackLogService backLogService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private CheckLogDao checkLogDao;

    @Autowired
    private CheckLogService checkLogService;

    @Autowired
    private InstockServiceImpl getInstockServiceImpl;

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[MEMBER_LIST]}")
    private String memberList;  //查询人员信息调用接口

    @Value("#{orderProp[CREDIT_EXTENSION]}")
    private String creditExtension;  //授信服务器地址

    @Value("#{orderProp[DING_SEND_SMS]}")
    private String dingSendSms;  //发钉钉通知接口

    @Override
    @Transactional(readOnly = true)
    public DeliverConsign findById(Integer id) throws Exception {
        DeliverConsign deliverConsign = deliverConsignDao.findOne(id);
        if (deliverConsign != null) {
            List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
            if (deliverConsignGoodsSet.size() > 0) {
                for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet) {
                    deliverConsignGoods.getGoods().setPurchGoods(null);
                }
            }
            List<Attachment> attachments = attachmentDao.findByRelObjIdAndCategory(deliverConsign.getId(), Attachment.AttachmentCategory.DELIVERCONSIGN.getCode());
            deliverConsign.setAttachmentSet(attachments);
            deliverConsign.getAttachmentSet().size();
        }
        Order order = deliverConsign.getOrder();
        BigDecimal exchangeRate = order.getExchangeRate() == null ? BigDecimal.valueOf(1) : order.getExchangeRate();
        deliverConsign.setExchangeRate(exchangeRate);   //汇率

        Integer status = deliverConsign.getStatus();    //获取出口发货通知单状态

        //非提交状态
        if (status != 3) {

            //获取授信信息
            DeliverConsign deliverConsign1 = null;
            try {
                if (order.getCrmCode() != null && order.getCrmCode() != "") {
                    deliverConsign1 = queryCreditData(order);
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }

            if (deliverConsign1 != null) {
                //如果是保存状态，可用授信额度需要实时更新
                deliverConsign.setCreditAvailable(deliverConsign1.getCreditAvailable()); //可用授信额度

                //获取预收
                BigDecimal currencyBnShipmentsMoney = order.getShipmentsMoney() == null ? BigDecimal.valueOf(0.00) : order.getShipmentsMoney();  //已发货总金额 （财务管理
                BigDecimal currencyBnAlreadyGatheringMoney = order.getAlreadyGatheringMoney() == null ? BigDecimal.valueOf(0.00) : order.getAlreadyGatheringMoney();//已收款总金额

                //收款总金额  -  发货总金额
                BigDecimal subtract = currencyBnAlreadyGatheringMoney.subtract(currencyBnShipmentsMoney);
                if (subtract.compareTo(BigDecimal.valueOf(0)) != -1) {    //-1 小于     0 等于      1 大于
                    deliverConsign.setAdvanceMoney(subtract);     //预收金额
                } else {
                    deliverConsign.setAdvanceMoney(BigDecimal.valueOf(0.00));     //预收金额
                }
            } else {
                deliverConsign.setCreditAvailable(BigDecimal.valueOf(0.00));    //可用授信额度
                deliverConsign.setAdvanceMoney(BigDecimal.valueOf(0.00));     //预收金额
            }
        }
        deliverConsign.setoId(deliverConsign.getOrder().getId());//order表id
        deliverConsign.setContractNo(deliverConsign.getOrder().getContractNo());//销售合同号
        deliverConsign.setTotalPriceUsd(deliverConsign.getOrder().getTotalPriceUsd());//合同总价
        deliverConsign.setReceivablePriceUsd(deliverConsign.getOrder().getTotalPriceUsd());//应收账款金额
        deliverConsign.setPerLiableRepay(deliverConsign.getOrder().getPerLiableRepay());//回款责任人

        return deliverConsign;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDeliverConsign(DeliverConsign deliverConsign) throws Exception {
        String eruitoken = (String) ThreadLocalUtil.getObject();
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignUpdate = findById(deliverConsign.getId());
        deliverConsignUpdate.setOrder(order);
        deliverConsignUpdate.setCoId(order.getSigningCo());
        deliverConsignUpdate.setDeptId(order.getExecCoId());
        deliverConsignUpdate.setDeptName(order.getExecCoName());
        deliverConsignUpdate.setExecCoName(order.getExecCoName());
        deliverConsignUpdate.setCreateUserId(order.getAgentId());
        deliverConsignUpdate.setContractNo(order.getContractNo());
        deliverConsignUpdate.setWriteDate(deliverConsign.getWriteDate());
        deliverConsignUpdate.setBookingDate(deliverConsign.getBookingDate());
        deliverConsignUpdate.setCreateUserId(deliverConsign.getCreateUserId());
        deliverConsignUpdate.setCreateUserName(deliverConsign.getCreateUserName());
        deliverConsignUpdate.setRemarks(deliverConsign.getRemarks());
        deliverConsignUpdate.setStatus(deliverConsign.getStatus());

        deliverConsignUpdate.setInvoiceRise(deliverConsign.getInvoiceRise());
        deliverConsignUpdate.setBusinessNature(deliverConsign.getBusinessNature());
        deliverConsignUpdate.setBusinessSketch(deliverConsign.getBusinessSketch());
        deliverConsignUpdate.setDeclareCustomsMoney(deliverConsign.getDeclareCustomsMoney());
        deliverConsignUpdate.setTradeMoney(deliverConsign.getTradeMoney());
        deliverConsignUpdate.setDirectTransferMoney(deliverConsign.getDirectTransferMoney());
        deliverConsignUpdate.setIndirectTransferMoney(deliverConsign.getIndirectTransferMoney());
        deliverConsignUpdate.setClearCustomsMoney(deliverConsign.getClearCustomsMoney());
        deliverConsignUpdate.setPayMethod(deliverConsign.getPayMethod());
        deliverConsignUpdate.setShippingBatch(deliverConsign.getShippingBatch());
        deliverConsignUpdate.setMoreBatchExplain(deliverConsign.getMoreBatchExplain());
        deliverConsignUpdate.setIsDangerous(deliverConsign.getIsDangerous());
        deliverConsignUpdate.setGoodsDepositPlace(deliverConsign.getGoodsDepositPlace());
        deliverConsignUpdate.setHasInsurance(deliverConsign.getHasInsurance());

        deliverConsignUpdate.setCountryLeader(deliverConsign.getCountryLeader());
        deliverConsignUpdate.setCountryLeaderId(deliverConsign.getCountryLeaderId());
        deliverConsignUpdate.setSettlementLeader(deliverConsign.getSettlementLeader());
        deliverConsignUpdate.setSettlementLeaderId(deliverConsign.getSettlementLeaderId());
        deliverConsignUpdate.setLogisticsLeader(deliverConsign.getLogisticsLeader());
        deliverConsignUpdate.setLogisticsLeaderId(deliverConsign.getLogisticsLeaderId());
        deliverConsignUpdate.setBusinessLeader(deliverConsign.getBusinessLeader());
        deliverConsignUpdate.setBusinessLeaderId(deliverConsign.getBusinessLeaderId());
        deliverConsignUpdate.setAuditingStatus(deliverConsign.getAuditingStatus());
        deliverConsignUpdate.setAuditingProcess(deliverConsign.getAuditingProcess());
        deliverConsignUpdate.setAuditingUserId(deliverConsign.getAuditingUserId());
        deliverConsignUpdate.setAuditingUser(deliverConsign.getAuditingUser());
        deliverConsignUpdate.setCrmCodeOrName(order.getCrmCode());
        // 授信信息  and
        if (deliverConsign.getStatus() == 3) {    //如果是提交操作保存 可用授信额度
            deliverConsignUpdate.setCreditAvailable(deliverConsign.getCreditAvailable());  //可用授信额度
            deliverConsignUpdate.setAdvanceMoney(deliverConsign.getAdvanceMoney());    //预收金额      /应收账款余额
        }
        deliverConsignUpdate.setLineOfCredit(deliverConsign.getLineOfCredit());     //授信额度
        deliverConsignUpdate.setThisShipmentsMoney(deliverConsign.getThisShipmentsMoney());     //本批次发货金额
        // 授信信息  end

        //付款信息
        deliverConsignUpdate.setDeliverConsignPayments(deliverConsign.getDeliverConsignPayments());
        // 处理附件
        //List<Attachment> attachments = attachmentService.handleParamAttachment(deliverConsignUpdate.getAttachmentSet(), deliverConsign.getAttachmentSet(), null, null);
        //deliverConsignUpdate.setAttachmentSet(attachments);
        // 商品信息
        Map<Integer, DeliverConsignGoods> oldDcGoodsMap = deliverConsignUpdate.getDeliverConsignGoodsSet().parallelStream().collect(Collectors.toMap(DeliverConsignGoods::getId, vo -> vo));
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        //Set<Integer> orderIds = new HashSet<>();
        for (DeliverConsignGoods dcGoods : deliverConsign.getDeliverConsignGoodsSet()) {
            DeliverConsignGoods deliverConsignGoods = oldDcGoodsMap.remove(dcGoods.getId());
            int oldSendNum = 0;
            if (deliverConsignGoods == null) {
                dcGoods.setId(null);
            } else {
                oldSendNum = deliverConsignGoods.getSendNum();
            }
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            //商品需增加发货数量 = 要修改的数量-原发货数量
            //Integer outStockNum = dcGoods.getSendNum() - goods.getOutstockNum();
            if (goods.getOutstockApplyNum() - oldSendNum + dcGoods.getSendNum() <= goods.getContractGoodsNum()) {
                dcGoods.setGoods(goods);
                dcGoods.setCreateTime(new Date());
                if (deliverConsign.getStatus() == 3) {
                    goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
                    //orderIds.add(goods.getOrder().getId());
                }
                goods.setOutstockApplyNum(goods.getOutstockApplyNum() - oldSendNum + dcGoods.getSendNum());
                goodsDao.save(goods);
            } else {
                throw new Exception(String.format("%s%s%s", "发货总数量超过合同数量", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Total quantity of shipments exceeds the number of contracts"));
            }
        }
        deliverConsignUpdate.setDeliverConsignGoodsSet(deliverConsign.getDeliverConsignGoodsSet());
        // 被删除的发货通知单商品
        for (DeliverConsignGoods dcGoods : oldDcGoodsMap.values()) {
            Goods goods = dcGoods.getGoods();
            goods.setOutstockApplyNum(goods.getOutstockApplyNum() - dcGoods.getSendNum());
            goodsDao.save(goods);
        }
        // 出口通知单审批添加部分
        if (deliverConsignUpdate.getStatus() == DeliverConsign.StatusEnum.BEING.getCode()) {
            deliverConsignUpdate.setAuditingStatus(1);
        } else if (deliverConsignUpdate.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode()) {
            deliverConsignUpdate.setAuditingStatus(2);
        }
        DeliverConsign deliverConsign1 = deliverConsignDao.save(deliverConsignUpdate);
        if (deliverConsign1.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode()) {
            // 启动业务流流程实例
            JSONObject processResp = BpmUtils.startProcessInstanceByKey("process_bookingorder", null, eruitoken, "deliver_consign:" + deliverConsign1.getId(), null);
            // 设置订单和业务流标示关联
            deliverConsign1.setProcessId(processResp.getString("instanceId"));
        }
        List<Attachment> attachmentList = deliverConsign.getAttachmentSet();
        Map<Integer, Attachment> dbAttahmentsMap = deliverConsignUpdate.getAttachmentSet().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        if (attachmentList != null && attachmentList.size() > 0) {
            attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, deliverConsign1.getId(), Attachment.AttachmentCategory.DELIVERCONSIGN.getCode());
        }
        //出口发货通知单订舱信息
        if (deliverConsign.getDeliverConsignBookingSpace() != null) {
            DeliverConsignBookingSpace deliverConsignBookingSpace = deliverConsign.getDeliverConsignBookingSpace();
            deliverConsignBookingSpace.setDeliverConsign(deliverConsign1);
            deliverConsignBookingSpace.setId(deliverConsign1.getDeliverConsignBookingSpace().getId());
            deliverConsignBookingSpaceDao.saveAndFlush(deliverConsignBookingSpace);
        }
        if (deliverConsign1.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode()) {
            try {
                disposeAdvanceMoney(order, deliverConsign);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDeliverConsign(DeliverConsign deliverConsign) throws Exception {
        String eruitoken = (String) ThreadLocalUtil.getObject();
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignAdd = new DeliverConsign();
        // 根据数据库中最后的发货通知单单号重新自动生成
        String deliverConsignNo = deliverConsignDao.findLaseDeliverConsignNo();
        deliverConsignAdd.setDeliverConsignNo(StringUtil.genDeliverConsignNo(deliverConsignNo));
        deliverConsignAdd.setOrder(order);
        deliverConsignAdd.setCoId(order.getSigningCo());
        deliverConsignAdd.setDeptId(order.getExecCoId());
        deliverConsignAdd.setDeptName(order.getExecCoName());
        deliverConsignAdd.setExecCoName(order.getExecCoName());
        deliverConsignAdd.setContractNo(order.getContractNo());
        deliverConsignAdd.setWriteDate(deliverConsign.getWriteDate());
        deliverConsignAdd.setBookingDate(deliverConsign.getBookingDate());
        deliverConsignAdd.setCreateUserId(deliverConsign.getCreateUserId());
        deliverConsignAdd.setCreateUserName(deliverConsign.getCreateUserName());
        deliverConsignAdd.setDeliverYn(deliverConsign.getDeliverYn());
        deliverConsignAdd.setRemarks(deliverConsign.getRemarks());
        deliverConsignAdd.setCountry(order.getCountry());
        deliverConsignAdd.setRegion(order.getRegion());
        deliverConsignAdd.setCreateTime(new Date());
        deliverConsignAdd.setStatus(deliverConsign.getStatus());
        deliverConsignAdd.setDeliverConsignGoodsSet(deliverConsign.getDeliverConsignGoodsSet());
        // 授信信息  and
        if (deliverConsignAdd.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode()) {    //如果是提交操作保存 可用授信额度
            deliverConsignAdd.setCreditAvailable(deliverConsign.getCreditAvailable());  //可用授信额度
            deliverConsignAdd.setAdvanceMoney(deliverConsign.getAdvanceMoney());    //预收金额      /应收账款余额
        }
        deliverConsignAdd.setLineOfCredit(deliverConsign.getLineOfCredit());     //授信额度
        deliverConsignAdd.setThisShipmentsMoney(deliverConsign.getThisShipmentsMoney());     //本批次发货金额
        // 授信信息  end


        // 处理附件信息
        //List<Attachment> attachments = attachmentService.handleParamAttachment(null, deliverConsign.getAttachmentSet(), null, null);
        //deliverConsignAdd.setAttachmentSet(attachments);
        //添加收款信息
        deliverConsignAdd.setDeliverConsignPayments(deliverConsign.getDeliverConsignPayments());
        // 处理商品信息
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        for (DeliverConsignGoods dcGoods : deliverConsign.getDeliverConsignGoodsSet()) {
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            if (goods.getOutstockApplyNum() + dcGoods.getSendNum() <= goods.getContractGoodsNum()) {
                dcGoods.setGoods(goods);
                dcGoods.setCreateTime(new Date());
                if (deliverConsign.getStatus() == 3) {
                    goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
                }
                goods.setOutstockApplyNum(goods.getOutstockApplyNum() + dcGoods.getSendNum());
                goodsDao.save(goods);
            } else {
                throw new Exception(String.format("%s%s%s", "发货总数量超过合同数量", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Total quantity of shipments exceeds the number of contracts"));
            }
        }
        deliverConsignAdd.setInvoiceRise(deliverConsign.getInvoiceRise());
        deliverConsignAdd.setBusinessNature(deliverConsign.getBusinessNature());
        deliverConsignAdd.setBusinessSketch(deliverConsign.getBusinessSketch());
        deliverConsignAdd.setDeclareCustomsMoney(deliverConsign.getDeclareCustomsMoney());
        deliverConsignAdd.setTradeMoney(deliverConsign.getTradeMoney());
        deliverConsignAdd.setDirectTransferMoney(deliverConsign.getDirectTransferMoney());
        deliverConsignAdd.setIndirectTransferMoney(deliverConsign.getIndirectTransferMoney());
        deliverConsignAdd.setClearCustomsMoney(deliverConsign.getClearCustomsMoney());
        deliverConsignAdd.setPayMethod(deliverConsign.getPayMethod());
        deliverConsignAdd.setShippingBatch(deliverConsign.getShippingBatch());
        deliverConsignAdd.setMoreBatchExplain(deliverConsign.getMoreBatchExplain());
        deliverConsignAdd.setIsDangerous(deliverConsign.getIsDangerous());
        deliverConsignAdd.setGoodsDepositPlace(deliverConsign.getGoodsDepositPlace());
        deliverConsignAdd.setHasInsurance(deliverConsign.getHasInsurance());

        deliverConsignAdd.setCountryLeader(deliverConsign.getCountryLeader());
        deliverConsignAdd.setCountryLeaderId(deliverConsign.getCountryLeaderId());
        deliverConsignAdd.setSettlementLeader(deliverConsign.getSettlementLeader());
        deliverConsignAdd.setSettlementLeaderId(deliverConsign.getSettlementLeaderId());
        deliverConsignAdd.setLogisticsLeader(deliverConsign.getLogisticsLeader());
        deliverConsignAdd.setLogisticsLeaderId(deliverConsign.getLogisticsLeaderId());
        deliverConsignAdd.setBusinessLeader(deliverConsign.getBusinessLeader());
        deliverConsignAdd.setBusinessLeaderId(deliverConsign.getBusinessLeaderId());
        deliverConsignAdd.setAuditingStatus(deliverConsign.getAuditingStatus());
        deliverConsignAdd.setAuditingProcess(deliverConsign.getAuditingProcess());
        deliverConsignAdd.setAuditingUserId(deliverConsign.getAuditingUserId());
        deliverConsignAdd.setAuditingUser(deliverConsign.getAuditingUser());
        deliverConsignAdd.setCrmCodeOrName(order.getCrmCode());

        // 出口通知单审批添加部分
        if (deliverConsignAdd.getStatus() == DeliverConsign.StatusEnum.BEING.getCode()) {
            deliverConsignAdd.setAuditingStatus(1);
        } else if (deliverConsignAdd.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode()) {
            deliverConsignAdd.setAuditingStatus(2);
        }
        DeliverConsign deliverConsign1 = deliverConsignDao.save(deliverConsignAdd);
        if (deliverConsign1.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode()) {
            // 启动业务流流程实例
            JSONObject processResp = BpmUtils.startProcessInstanceByKey("process_bookingorder", null, eruitoken, "deliver_consign:" + deliverConsign1.getId(), null);
            // 设置订单和业务流标示关联
            deliverConsign1.setProcessId(processResp.getString("instanceId"));
        }
        //出口通知单附件添加
        if (deliverConsign.getAttachmentSet() != null && deliverConsign.getAttachmentSet().size() > 0) {
            attachmentService.addAttachments(deliverConsign.getAttachmentSet(), deliverConsign1.getId(), Attachment.AttachmentCategory.DELIVERCONSIGN.getCode());
        }

        //出口发货通知单订舱信息
        if (deliverConsign.getDeliverConsignBookingSpace() != null) {
            DeliverConsignBookingSpace deliverConsignBookingSpace = deliverConsign.getDeliverConsignBookingSpace();
            deliverConsignBookingSpace.setDeliverConsign(deliverConsign1);
            deliverConsignBookingSpaceDao.saveAndFlush(deliverConsignBookingSpace);
        }
        return true;
    }

    public JSONObject disposeAdvanceMoney(Order order, DeliverConsign deliverConsign1) throws Exception {

        //（1）当“本批次发货金额”≤“预收金额”+“可用授信额度/汇率”时，系统判定可以正常发货。
        //（2）当“本批次发货金额”＞“预收金额”+“可用授信额度/汇率”时，系统判定不允许发货
        BigDecimal advanceMoney = order.getAdvanceMoney() == null ? BigDecimal.valueOf(0) : order.getAdvanceMoney();//预收金额      /应收账款余额
        BigDecimal thisShipmentsMoney = deliverConsign1.getThisShipmentsMoney() == null ? BigDecimal.valueOf(0.00) : deliverConsign1.getThisShipmentsMoney();//本批次发货金额
        BigDecimal exchangeRate = order.getExchangeRate() == null ? BigDecimal.valueOf(1) : order.getExchangeRate();//订单中利率

        //获取授信额度信息
        DeliverConsign deliverConsignByCreditData = null;
        try {
            if (StringUtils.isNotBlank(order.getCrmCode())) {
                deliverConsignByCreditData = queryCreditData(order);
            }

        } catch (Exception e) {
            LOGGER.info("查询授信返回信息：" + e);
            throw new Exception(e);
        }

        if (deliverConsignByCreditData != null) {
            BigDecimal creditAvailable = deliverConsignByCreditData.getCreditAvailable() == null ? BigDecimal.valueOf(0) : deliverConsignByCreditData.getCreditAvailable();//可用授信额度
            BigDecimal divide = creditAvailable.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);//可用授信额度/利率
            BigDecimal add = divide.add(advanceMoney);  //“可用授信额度/汇率 + 预收金额”      可发货额度

            BigDecimal lineOfCredit = deliverConsignByCreditData.getLineOfCredit() == null ? BigDecimal.valueOf(0) : deliverConsignByCreditData.getLineOfCredit(); //授信额度
            if (lineOfCredit.compareTo(BigDecimal.valueOf(0)) == 1) {   // 判断是否有授信额度

                BigDecimal subtract1 = advanceMoney.subtract(thisShipmentsMoney); //预收  减去  本次发货金额

                if (subtract1.compareTo(BigDecimal.valueOf(0)) == -1) {   //先判断是否有预收，预收够不够本次发货的

                    //判断授信额度够不够
                    BigDecimal add1 = divide.add(subtract1);

                    if (add1.compareTo(BigDecimal.valueOf(0)) == 1 || add1.compareTo(BigDecimal.valueOf(0)) == 0) {  //可用授信额度 大于 使用的授信的额度 或者等于时 ，  可以发货

                        BigDecimal subtract = thisShipmentsMoney.subtract(advanceMoney);    // 本次发货金额  -  预收金额  = 需要使用授信的额度

                        BigDecimal multiply = subtract.multiply(exchangeRate);  //需要使用授信的额度 * 汇率

                        if (multiply.compareTo(BigDecimal.valueOf(0)) == 1) {  //本批次发货金额 大于 预收金额时，调用授信接口，修改授信额度
                            try {
                                JSONObject jsonObject = buyerCreditPaymentByOrder(order, 1, multiply);
                                JSONObject data = jsonObject.getJSONObject("data");//获取查询数据
                                if (data == null) {  //查询数据正确返回 1
                                    throw new Exception("同步授信额度失败");
                                } else {
                                    return data;
                                }
                            } catch (Exception e) {
                                LOGGER.info("查询授信返回信息：" + e);
                                throw new Exception(e);
                            }
                        } else {
                            throw new Exception("预收金额和可用授信额度不足");
                        }

                    } else {
                        throw new Exception("预收金额和可用授信额度不足");
                    }
                }
            } else {

                if (advanceMoney.compareTo(BigDecimal.valueOf(0)) == 1) { //小于0  说明收款多    等于0，说明没有

                    BigDecimal subtract = advanceMoney.subtract(thisShipmentsMoney); // 预收金额   -    本批次发货金额

                    if (subtract.compareTo(BigDecimal.valueOf(0)) == -1) {  //小于0的话，说明预收金额不够花钱金额
                        throw new Exception("预收金额和可用授信额度不足");
                    }

                } else {
                    throw new Exception("预收金额和可用授信额度不足");
                }

            }
        } else {
            if (advanceMoney.compareTo(BigDecimal.valueOf(0)) == 1) { //小于0  说明收款多    等于0，说明没有

                BigDecimal subtract = advanceMoney.subtract(thisShipmentsMoney); // 预收金额   -    本批次发货金额

                if (subtract.compareTo(BigDecimal.valueOf(0)) == -1) {  //小于0的话，说明预收金额不够花钱金额
                    throw new Exception("预收金额和可用授信额度不足");
                }

            } else {
                throw new Exception("预收金额和可用授信额度不足");
            }
        }

        return null;

    }


    /**
     * 根据订单中crm编码，查询授信信息
     *
     * @param order
     * @return
     * @throws Exception
     */
    public DeliverConsign queryCreditData(Order order) throws Exception {
        //拿取局部返回信息
        String returnMassage;
        //获取当前订单用户crm客户码
        String crmCode = order.getCrmCode();
        if (crmCode != null && crmCode != "") {
            try {

                //拼接查询授信路径
                String url = creditExtension + "V2/Buyercredit/getCreditInfoByCrmCode";
                //获取token
                String eruiToken = (String) ThreadLocalUtil.getObject();

                // 根据id获取人员信息
                String jsonParam = "{\"crm_code\":\"" + crmCode + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                returnMassage = HttpRequest.sendPost(url, jsonParam, header);
                LOGGER.info("人员详情返回信息：" + returnMassage);
            } catch (Exception ex) {
                throw new Exception(String.format("获取客户授信信息失败"));
            }

            JSONObject jsonObject = JSONObject.parseObject(returnMassage);
            Integer code = jsonObject.getInteger("code");   //获取查询状态
           /* if(code != 1  &&  code != -401 ){  //查询数据正确返回 1
                String message = jsonObject.getString("message");
                throw new Exception(message);
            }*/
            if (code == 1) {
                JSONObject data = jsonObject.getJSONObject("data");//获取查询数据

                BigDecimal nolcGranted = BigDecimal.valueOf(0);
                BigDecimal lcgranted = BigDecimal.valueOf(0);
                String accountSettle = null;
                BigDecimal creditAvailable = null;
                if (data != null) {
                    nolcGranted = data.getBigDecimal("nolc_granted") == null ? BigDecimal.valueOf(0) : data.getBigDecimal("nolc_granted"); //非信用证授信额度
                    lcgranted = data.getBigDecimal("lc_granted") == null ? BigDecimal.valueOf(0) : data.getBigDecimal("lc_granted"); // 信用证授信额度
                    accountSettle = data.getString("account_settle"); // OA",(OA非信用证;L/C信用证)
                    creditAvailable = data.getBigDecimal("credit_available"); // 可用授信额度
                }

                //收款方式：
                //L/C:信用证，授信使用信用证
                //OA:托收，电汇，信汇，票汇，授信使用非信用证
                String paymentModeBn = order.getPaymentModeBn();    //获取订单收款方式
                String accountSettles = null;   //收款方式属于什么授信类型

                if (paymentModeBn != null) {
                    if (paymentModeBn.equals("1")) { //  1:信用证          //['1' => '信用证','2' => '托收','3'=>"电汇",'4'=>"信汇",'5'=>"票汇"];
                        accountSettles = "L/C";
                    } else if (paymentModeBn.equals("2") || paymentModeBn.equals("3") || paymentModeBn.equals("4") || paymentModeBn.equals("5")) {
                        accountSettles = "OA";
                    }
                }

                DeliverConsign deliverConsign = new DeliverConsign();

                if (accountSettle != null && accountSettles != null) {
                    if (accountSettle.equals(accountSettles) && accountSettle.equals("L/C")) {    //信用证
                        deliverConsign.setLineOfCredit(lcgranted);   //信用证授信额度
                        deliverConsign.setCreditAvailable(creditAvailable);    // 可用授信额度

                    } else if (accountSettle.equals(accountSettles) && accountSettle.equals("OA")) {  //非信用证
                        deliverConsign.setLineOfCredit(nolcGranted);   //非信用证授信额度
                        deliverConsign.setCreditAvailable(creditAvailable);    // 可用授信额度
                    } else {
                        deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
                        deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度
                    }
                } else {
                    deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
                    deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度
                }

                return deliverConsign;
            } else {
                DeliverConsign deliverConsign = new DeliverConsign();

                deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
                deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度

                return deliverConsign;
            }
        } else {
            DeliverConsign deliverConsign = new DeliverConsign();

            deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
            deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度

            return deliverConsign;
        }

    }


    //钉钉通知 审批人
    private void sendDingtalk(DeliverConsign deliverConsign, String user, boolean rejectFlag) {
        //获取token
        final String eruiToken = (String) ThreadLocalUtil.getObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("发送短信的用户token:" + eruiToken);
                // 根据id获取商务经办人信息
                String jsonParam = "{\"id\":\"" + user + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String userInfo = HttpRequest.sendPost(memberInformation, jsonParam, header);
                LOGGER.info("人员详情返回信息：" + userInfo);
                //钉钉通知接口头信息
                Map<String, String> header2 = new HashMap<>();
                header2.put("Cookie", eruiToken);
                header2.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                JSONObject jsonObject = JSONObject.parseObject(userInfo);
                Integer code = jsonObject.getInteger("code");
                String userNo = null;
                String userName = null;  //商务经办人手机号
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    //获取通知者姓名员工编号
                    //userName = data.getString("name");
                    userNo = data.getString("user_no");
                    Long startTime = System.currentTimeMillis();
                    Date sendTime = new Date(startTime);
                    String sendTime02 = DateUtil.format(DateUtil.FULL_FORMAT_STR, sendTime);
                    //发送钉钉通知
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("toUser=").append(userNo);
                    if (!rejectFlag) {
                        stringBuffer.append("&message=您好！" + deliverConsign.getOrder().getAgentName() + "的出口通知单已申请审批。出口通知单号：" + deliverConsign.getDeliverConsignNo() + "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！" +
                                "" + sendTime02 + "");
                    } else {
                        stringBuffer.append("&message=您好！" + deliverConsign.getOrder().getAgentName() + "的出口通知单审批未通过。出口通知单号：" + deliverConsign.getDeliverConsignNo() + "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！" +
                                "" + sendTime02 + "");
                    }
                    stringBuffer.append("&type=userNo");
                    String s1 = HttpRequest.sendPost(dingSendSms, stringBuffer.toString(), header2);
                    LOGGER.info("发送钉钉通知返回状态" + s1);
                }
            }
        }).start();
    }


    /**
     * 处理授信额度
     *
     * @param order      订单信息
     * @param flag       支出还是回款标识   1：支出   2：回款
     * @param orderMoney 支出OR回款金额
     */
    public JSONObject buyerCreditPaymentByOrder(Order order, Integer flag, BigDecimal orderMoney) throws Exception {
        String contractNo = order.getContractNo();  //销售合同号
        String crmCode = order.getCrmCode();    //crm编码

        //拿取局部返回信息
        String returnMassage;
        try {
            //拼接查询授信路径
            String url = creditExtension + "V2/Buyercredit/buyerCreditPaymentByOrder";
            //获取token
            String eruiToken = (String) ThreadLocalUtil.getObject();

            // 根据id获取人员信息
            String jsonParam = "{\"contract_no\":\"" + contractNo + "\",\"order_money\":\"" + orderMoney + "\",\"order_type\":\"" + flag + "\",\"crm_code\":\"" + crmCode + "\"}";
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            returnMassage = HttpRequest.sendPost(url, jsonParam, header);
            LOGGER.info("人员详情返回信息：" + returnMassage);

            JSONObject jsonObject = JSONObject.parseObject(returnMassage);
            Integer code = jsonObject.getInteger("code");   //获取查询状态
            if (code != 1) {  //查询数据正确返回 1
                String message = jsonObject.getString("message");
                throw new Exception(message);
            }

            if (code == 1) {
                JSONObject data = jsonObject.getJSONObject("data");//获取查询数据
                return data;
            }

        } catch (Exception ex) {
            throw new Exception(String.format("查询授信信息失败"));
        }

        return null;

    }


    private void auditBackLogHandle(DeliverConsign deliverConsign, boolean rejectFlag, String auditingUserId, String auditorId, boolean isComeMore) {
        try {
            // 删除上一个待办
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.DELIVERCONSIGN_REJECT.getNum());    //功能访问路径标识
            backLog2.setHostId(deliverConsign.getOrder().getId());
            backLog2.setFollowId(deliverConsign.getId());
            backLogService.updateBackLogByDelYn(backLog2);
            if (isComeMore) {
                backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.DELIVERCONSIGN_AUDIT.getNum());    //功能访问路径标识
                backLogService.updateBackLogByDelYnNew(backLog2, auditorId);
            } else {
                backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.DELIVERCONSIGN_AUDIT.getNum());    //功能访问路径标识
                backLogService.updateBackLogByDelYn(backLog2);
            }
            if (auditingUserId != null && !isComeMore) {//并行未走完不用推送待办
                String region = deliverConsign.getOrder().getRegion();   //所属地区
                Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
                String country = deliverConsign.getOrder().getCountry();  //国家
                Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
                String infoContent = String.format("%s | %s", bnMapZhRegion.get(region), bnMapZhCountry.get(country));
                Integer followId = deliverConsign.getId();
                Integer hostId = deliverConsign.getOrder().getId();
                String deliverConsignNo = deliverConsign.getDeliverConsignNo();

                for (String user : auditingUserId.split(",")) {
                    // 推送待办事件
                    applicationContext.publishEvent(new TasksAddEvent(applicationContext, backLogService,
                            rejectFlag ? BackLog.ProjectStatusEnum.DELIVERCONSIGN_REJECT : BackLog.ProjectStatusEnum.DELIVERCONSIGN_AUDIT,
                            deliverConsignNo,
                            infoContent,
                            hostId,
                            followId,
                            "订舱",
                            Integer.parseInt(user)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
