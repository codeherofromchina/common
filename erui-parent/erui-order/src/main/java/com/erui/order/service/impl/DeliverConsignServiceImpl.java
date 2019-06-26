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
import com.erui.order.v2.service.UserService;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverConsignServiceImpl implements DeliverConsignService {

    private static Logger logger = LoggerFactory.getLogger(DeliverDetailServiceImpl.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DeliverConsignDao deliverConsignDao;

    @Autowired
    DeliverConsignBookingSpaceDao deliverConsignBookingSpaceDao;
    @Autowired
    private UserService userService;
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
    ProjectDao projectDao;

    @Autowired
    private BackLogService backLogService;

    @Autowired
    private StatisticsService statisticsService;

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

            if(deliverConsign.getDeliverNoticeStatus() == DeliverNotice.StatusEnum.DONE.getCode()){
                List<Attachment> attachmentNotices = attachmentDao.findByRelObjIdAndCategory(deliverConsign.getDeliverNotice().getId(), Attachment.AttachmentCategory.DELIVERNOTICE.getCode());
                deliverConsign.setAttachmentNotice(attachmentNotices);
                deliverConsign.getAttachmentNotice().size();
            }
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


    private DeliverConsign findByIdForUpdate(Integer id) throws Exception {
        DeliverConsign deliverConsign = deliverConsignDao.findById(id);
        if (deliverConsign != null) {
            List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
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
    public Integer updateDeliverConsign(DeliverConsign deliverConsign) throws Exception {

        String eruitoken = (String) ThreadLocalUtil.getObject();

        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignUpdate = findByIdForUpdate(deliverConsign.getId());
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
//            if (StringUtils.isNotBlank(deliverConsign1.getAudiRemark()) && StringUtils.isBlank(deliverConsign1.getProcessId())) {
            if (false) {
                // 老审核流程
                deliverConsignUpdate.setAuditingProcess("31");
                deliverConsignUpdate.setAuditingStatus(2);
                if (deliverConsignUpdate.getCountryLeaderId() != null) {
                    deliverConsignUpdate.setAuditingUserId(deliverConsignUpdate.getCountryLeaderId().toString());
                } else {
                    deliverConsignUpdate.setAuditingUserId(null);
                }
                CheckLog checkLog_i = null; //审批流日志
                checkLog_i = orderService.fullCheckLogInfo(null, CheckLog.checkLogCategory.DELIVERCONSIGN.getCode(), deliverConsign1.getId(), 30, order.getAgentId(), order.getAgentName(), deliverConsign1.getAuditingProcess().toString(), deliverConsign1.getCountryLeaderId().toString(), deliverConsign1.getAuditingReason(), "1", 4);
                checkLogService.insert(checkLog_i);
                // 待办
                if (deliverConsign.getCountryLeaderId() != null) {
                    sendDingtalk(deliverConsign1, deliverConsignUpdate.getCountryLeaderId().toString(), false);
                    auditBackLogHandle(deliverConsign1, false, deliverConsign1.getCountryLeaderId().toString(), "", false);
                }
            } else {
                // 新审核流程
                String taskId = deliverConsign.getTaskId();
                if (StringUtils.isBlank(taskId)) {
                    Map<String, Object> initVar = new HashMap<>();
                    initVar.put("param_contract", order.getContractNo());
                    initVar.put("task_cm_country", deliverConsign1.getCountry());
                    initVar.put("task_rm_area", deliverConsign1.getRegion());
                    if (order.getTechnicalId() != null) {
                        String userNo = userService.findUserNoById(order.getTechnicalId().longValue());
                        if (StringUtils.isNotBlank(userNo)) {
                            initVar.put("assignee_pm", userNo);  // 设置项目负责人为项目中的项目负责人
                        } else {
                            throw new Exception("没有项目负责人错误");
                        }
                    } else {
                        throw new Exception("没有项目负责人错误");
                    }

                    // 启动业务流流程实例
                    JSONObject processResp = BpmUtils.startProcessInstanceByKey("booking_order", null, eruitoken, "deliver_consign:" + deliverConsign1.getId(), initVar);
                    // 设置订单和业务流标示关联
                    deliverConsign1.setProcessId(processResp.getString("instanceId"));
                    deliverConsign1.setAuditingUser("");
                    deliverConsign1.setAuditingUserId("");
                    deliverConsign1.setAuditingProcess("task_cm");

                    /// 删除老流程待办 ，驳回到初始节点时候会开启新流程 TODO 如果不存在老审核流程可删除
                    // 删除上一个待办
                    try {
                        BackLog backLog2 = new BackLog();
                        backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.DELIVERCONSIGN_REJECT.getNum());    //功能访问路径标识
                        backLog2.setHostId(deliverConsign.getOrder().getId());
                        backLog2.setFollowId(deliverConsign.getId());
                        backLogService.updateBackLogByDelYn(backLog2);
                        backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.DELIVERCONSIGN_AUDIT.getNum());    //功能访问路径标识
                        backLogService.updateBackLogByDelYn(backLog2);
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // 完善订单任务调用
                    Map<String, Object> localVariables = new HashMap<>();
                    localVariables.put("audit_status", "APPROVED");
                    BpmUtils.completeTask(taskId, eruitoken, null, localVariables, "同意");
                }
                deliverConsign1.setAuditingProcess("task_cm"); // 第一个节点通知失败，写固定的第一个节点
                deliverConsign1.setAuditingUser("");
                deliverConsign1.setAuditingUserId("");
                deliverConsign1.setAuditingStatus(Order.AuditingStatusEnum.PROCESSING.getStatus());
            }
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
            if (deliverConsign1.getDeliverConsignBookingSpace() != null && deliverConsign1.getDeliverConsignBookingSpace().getId() != null) {
                deliverConsignBookingSpace.setId(deliverConsign1.getDeliverConsignBookingSpace().getId());
            }
            deliverConsignBookingSpaceDao.saveAndFlush(deliverConsignBookingSpace);
        }

        if (deliverConsign1.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode()) {
            try {
                disposeAdvanceMoney(order, deliverConsign);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return deliverConsign1.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addDeliverConsign(DeliverConsign deliverConsign) throws Exception {
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
        deliverConsignAdd.setDeliverNoticeStatus(0);
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
            Map<String, Object> initVar = new HashMap<>();
            initVar.put("param_contract", order.getContractNo());
            initVar.put("task_cm_country", deliverConsign1.getCountry());
            initVar.put("task_rm_area", deliverConsign1.getRegion());
            if (order.getTechnicalId() != null) {
                String userNo = userService.findUserNoById(order.getTechnicalId().longValue());
                if (StringUtils.isNotBlank(userNo)) {
                    initVar.put("assignee_pm", userNo);  // 设置项目负责人为项目中的项目负责人
                } else {
                    throw new Exception("没有项目负责人错误");
                }
            } else {
                throw new Exception("没有项目负责人错误");
            }
            // 启动业务流流程实例
            JSONObject processResp = BpmUtils.startProcessInstanceByKey("booking_order", null, eruitoken, "deliver_consign:" + deliverConsign1.getId(), initVar);
            // 设置订单和业务流标示关联
            deliverConsign1.setProcessId(processResp.getString("instanceId"));
            deliverConsign1.setAuditingProcess("task_cm"); // 第一个节点通知失败，写固定的第一个节点
            deliverConsign1.setAuditingUser("");
            deliverConsign1.setAuditingUserId("");
            deliverConsign1.setAuditingStatus(Order.AuditingStatusEnum.PROCESSING.getStatus());
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
        if (deliverConsign1.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode()) {
            try {
                disposeAdvanceMoney(order, deliverConsign1);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return deliverConsign1.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliverConsign> findByOrderId(Integer orderId) {
        List<DeliverConsign> deliverConsignList = deliverConsignDao.findByOrderId(orderId);
        for (DeliverConsign deliverConsign : deliverConsignList) {
            deliverConsign.getId();
            deliverConsign.getCoId();
            deliverConsign.getDeliverConsignNo();
            deliverConsign.getWriteDate();
            deliverConsign.getStatus();
            deliverConsign.getDeptId();
            deliverConsign.getCreateUserId();
            deliverConsign.setoId(orderId);
            List<Goods> goodsList = deliverConsign.getOrder().getGoodsList();
            if (goodsList.size() > 0) {
                for (Goods goods : goodsList) {
                    goods.setPurchGoods(null);
                }
            }

        }
        return deliverConsignList;
    }

    /**
     * 根据条件分页查询订舱列表
     *
     * @param condition
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<DeliverConsign> findByPage(DeliverConsignListCondition condition) {
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), new Sort(Sort.Direction.DESC, "createTime"));
        Page<DeliverConsign> pageList = deliverConsignDao.findAll(new Specification<DeliverConsign>() {
            @Override
            public Predicate toPredicate(Root<DeliverConsign> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> searchList = new ArrayList<>(); // 前端查询条件的AND关系列表
//                // 根据订舱执行状态模糊查询
//                if (condition.getStatus() != null) {
//                    searchList.add(cb.equal(root.get("status").as(Integer.class), condition.getStatus()));
//                }
                // 根据出口通知单号模糊查询
                if (StringUtil.isNotBlank(condition.getDeliverConsignNo())) {
                    searchList.add(cb.like(root.get("deliverConsignNo").as(String.class), "%" + condition.getDeliverConsignNo() + "%"));
                }
                // 根据客户代码或名称模糊查询
                if (StringUtil.isNotBlank(condition.getCrmCodeOrName())) {
                    searchList.add(cb.like(root.get("crmCodeOrName").as(String.class), "%" + condition.getCrmCodeOrName() + "%"));
                }
                // 根据销售同号模糊查询
                if (StringUtil.isNotBlank(condition.getContractNo())) {
                    searchList.add(cb.like(root.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                // 根据发货申请部门查询
                if (StringUtil.isNotBlank(condition.getDeptId())) {
                    searchList.add(cb.equal(root.get("deptName").as(String.class), condition.getDeptId()));
                }
                // 审核状态查询
                if (null != condition.getAuditingStatus() && condition.getAuditingStatus() != 0) {
                    searchList.add(cb.equal(root.get("auditingStatus").as(Integer.class), condition.getAuditingStatus()));
                }
                // 根据审核进度
                if (StringUtils.isNotBlank(condition.getAuditingProcess())) {
                    if ("999".equals(condition.getAuditingProcess())) {
                        // 999 定位审核完成的查询
                        searchList.add(cb.equal(root.get("auditingStatus").as(Integer.class), 4));
                    } else {
                        searchList.add(cb.like(root.get("auditingProcess").as(String.class), "%" + condition.getAuditingProcess() + "%"));
                    }
                }

                // 可以看到列表的人
                String eruiToken = (String) ThreadLocalUtil.getObject();
                if (StringUtils.isNotBlank(eruiToken)) {
                    List<Integer> userList = getUserListByRoleNo(eruiToken, "O42"); // 获取O42订舱负责人
                    Map<String, String> stringStringMap = getInstockServiceImpl.ssoUser(eruiToken);
                    String submenuId = stringStringMap.get("id");

                    // O42订舱负责人角色下面的人可以看到所有列表信息。
                    if (userList != null && userList.contains(Integer.parseInt(submenuId))) {
                        Predicate[] predicates = new Predicate[userList.size() * 2 + 1];
                        int i = 0;
                        for (Integer userId : userList) {
                            predicates[i] = cb.like(root.get("auditingUserId").as(String.class), "%" + userId + "%");
                            i++;
                            predicates[i] = cb.like(root.get("audiRemark").as(String.class), "%" + userId + "%");
                            i++;
                        }
                        predicates[i] = cb.equal(root.get("createUserId").as(Integer.class), Integer.parseInt(submenuId));
                        searchList.add(cb.or(predicates));
                    } else {
                        Predicate auditingUserId01 = cb.like(root.get("auditingUserId").as(String.class), "%" + submenuId + "%");
                        Predicate auditingUserId02 = cb.equal(root.get("createUserId").as(Integer.class), Integer.parseInt(submenuId));
                        Predicate auditingUserId03 = cb.like(root.get("audiRemark").as(String.class), "%" + submenuId + "%");
                        searchList.add(cb.or(auditingUserId01, auditingUserId02, auditingUserId03));
                    }
                }

                Predicate[] predicates = new Predicate[searchList.size()];
                predicates = searchList.toArray(predicates);
                return cb.and(predicates);
            }
        }, pageRequest);

        return pageList;
    }

    /**
     * 根据角色获取人员列表
     *
     * @param eruiToken
     * @param roleNo
     * @return
     */
    private List<Integer> getUserListByRoleNo(String eruiToken, String roleNo) {
        // 获取人员id
        List<Integer> listAll = new ArrayList<>(); //分单员id

        if (StringUtils.isNotBlank(eruiToken)) {
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            try {
                //获取物流分单员
                String jsonParam = "{\"role_no\":\"" + roleNo + "\"}";
                String s2 = HttpRequest.sendPost(memberList, jsonParam, header);
                logger.info("人员详情返回信息：" + s2);

                // 获取人员手机号
                JSONObject jsonObjects = JSONObject.parseObject(s2);
                Integer codes = jsonObjects.getInteger("code");
                if (codes == 1) { //判断请求是否成功
                    // 获取数据信息
                    JSONArray data1 = jsonObjects.getJSONArray("data");
                    for (int i = 0; i < data1.size(); i++) {
                        JSONObject ob = (JSONObject) data1.get(i);
                        listAll.add(ob.getInteger("id")); //获取人员id
                    }
                } else {
                    throw new Exception("人员详情返回信息查询失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return listAll;
    }


    /**
     * 根据出口发货通知单 查询信息
     *
     * @param deliverNoticeNos 看货通知单号  数组
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeliverConsign> querExitInformMessage(Integer[] deliverNoticeNos) throws Exception {
        List<DeliverConsign> page = deliverConsignDao.findByIdInAndStatus(deliverNoticeNos, 3);
        for (DeliverConsign deliverConsign : page) {
            deliverConsign.getOrder().getId();
            deliverConsign.getAttachmentSet().size();
            List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
            if (deliverConsignGoodsSet.size() == 0) {
                throw new Exception("出口通知单号号" + deliverConsign.getDeliverConsignNo() + "无出口发货通知单商品信息");
            }
            for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet) {
                Goods goods = deliverConsignGoods.getGoods();
                if (goods == null) {
                    throw new Exception("出口发货通知单号" + deliverConsign.getDeliverConsignNo() + "无商品信息");
                }
                Project project = goods.getProject();
                if (project == null) {
                    throw new Exception("出口发货通知单号" + deliverConsign.getDeliverConsignNo() + "无项目信息");
                }
                project.getId();
            }
        }
        return page;

    }

    //  出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
    public void sendSms(Map<String, Object> map1) throws Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                // 根据id获取人员信息
                String jsonParam = "{\"role_no\":\"O019\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(memberList, jsonParam, header);
                logger.info("人员详情返回信息：" + s);

                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");

                if (code == 1) {
                    // 获取人员手机号
                    JSONArray data1 = jsonObject.getJSONArray("data");

                    //去除重复
                    Set<String> listAll = new HashSet<>();
                    for (int i = 0; i < data1.size(); i++) {
                        JSONObject ob = (JSONObject) data1.get(i);
                        String mobile = ob.getString("mobile");
                        if (StringUtils.isNotBlank(mobile)) {
                            listAll.add(mobile);    //获取人员手机号
                        }
                    }

                    listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                    JSONArray smsarray = new JSONArray();   //手机号JSON数组
                    for (String me : listAll) {
                        smsarray.add(me);
                    }

                    //发送短信
                    Map<String, String> map = new HashMap();
                    map.put("areaCode", "86");
                    map.put("to", smsarray.toString());

                    map.put("content", " 您好，销售合同号：" + map1.get("contractNoOs") + "，已生成出口通知单号：" + map1.get("deliverConsignNo") + "，产品放行单号：" + map1.get("deliverDetailNo") + "，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType", "0");
                    map.put("groupSending", "0");
                    map.put("useType", "订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态" + s1);
                }
            } catch (Exception e) {
                throw new Exception(String.format("%s%s%s", "发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Failure to send SMS"));
            }

        }
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
                logger.info("人员详情返回信息：" + returnMassage);
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
            logger.info("人员详情返回信息：" + returnMassage);

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

    /**
     * 审核出口发货通知单
     *
     * @param deliverConsign
     * @param auditorId
     * @param auditorName
     * @param rDeliverConsign 请求的参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean audit(DeliverConsign deliverConsign, String auditorId, String auditorName, DeliverConsign rDeliverConsign) throws Exception {
        //@param rejectFlag true:驳回项目   false:审核项目
        StringBuilder auditorIds = null;
        if (deliverConsign.getAudiRemark() != null) {
            auditorIds = new StringBuilder(deliverConsign.getAudiRemark());
        } else {
            auditorIds = new StringBuilder("");
        }
        boolean rejectFlag = "-1".equals(rDeliverConsign.getAuditingType());
        boolean isComeMore = Boolean.FALSE;// 是否来自并行的审批，且并行还没走完。
        String reason = rDeliverConsign.getAuditingReason();
        // 获取当前审核进度
        String auditingProcess = deliverConsign.getAuditingProcess();
        String auditingUserId = deliverConsign.getAuditingUserId();
        Integer curAuditProcess = null;
        if (auditorId.equals(auditingUserId)) {
            curAuditProcess = Integer.parseInt(auditingProcess);
        } else {
            String[] split = auditingUserId.split(",");
            String[] split1 = auditingProcess.split(",");
            for (int n = 0; n < split.length; n++) {
                if (auditorId.equals(split[n])) {
                    curAuditProcess = Integer.parseInt(split1[n]);
                }
            }
        }
        if (curAuditProcess == null) {
            return false;
        }
        auditorIds.append("," + auditorId + ",");
        // 定义最后处理结果变量，最后统一操作
        Integer auditingStatus_i = 2; // 默认状态为审核中
        String auditingProcess_i = null; // 审核当前进度
        String auditingUserId_i = null; // 审核当前人
        CheckLog checkLog_i = null; // 审核日志
        if (rejectFlag) { // 如果是驳回，则直接记录日志，修改审核进度
            auditingStatus_i = 3;
            // 驳回到采购订单办理
            auditingProcess_i = "30"; //驳回到订舱 处理
            auditingUserId_i = deliverConsign.getOrder().getAgentId() != null ? deliverConsign.getOrder().getAgentId().toString() : ""; // 要驳回给谁
            //驳回后状态设置为保存状态
            deliverConsign.setStatus(2);
            // 驳回的日志记录的下一处理流程和节点是当前要处理的节点信息
            checkLog_i = orderService.fullCheckLogInfo(null, CheckLog.checkLogCategory.DELIVERCONSIGN.getCode(), deliverConsign.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, deliverConsign.getAuditingProcess().toString(), deliverConsign.getAuditingUserId().toString(), reason, "-1", 4);
        } else {
            switch (curAuditProcess) {
                case 31:
                    auditingProcess_i = "32,33,34";
                    auditingUserId_i = deliverConsign.getSettlementLeaderId() + "," + deliverConsign.getBusinessLeaderId() + "," + deliverConsign.getLogisticsLeaderId();
                    break;
                case 32: //结算专员审核
                    String replace = StringUtils.strip(auditingUserId.replaceFirst(deliverConsign.getSettlementLeaderId().toString(), ""));
                    if ("".equals(replace)) { // 跟他并行审核的都已经审核完成
                        auditingProcess_i = "35,36";
                        auditingUserId_i = String.format("%d,%d", deliverConsign.getBookingOfficerId(), deliverConsign.getOperationSpecialistId());
                    } else {
                        isComeMore = true;
                        String replaceProcess = auditingProcess.replace("32", "");
                        auditingProcess_i = StringUtils.strip(replaceProcess, ",");
                        auditingUserId_i = StringUtils.strip(replace, ",");
                    }
                    break;
                case 33://事业部项目负责人审核
                    String replace2 = StringUtils.strip(auditingUserId.replaceFirst(deliverConsign.getBusinessLeaderId().toString(), ""));
                    if ("".equals(replace2)) { // 跟他并行审核的都已经审核完成
                        auditingProcess_i = "35,36";
                        auditingUserId_i = String.format("%d,%d", deliverConsign.getBookingOfficerId(), deliverConsign.getOperationSpecialistId());
                    } else {
                        isComeMore = true;
                        String replaceProcess = auditingProcess.replace("33", "");
                        auditingProcess_i = StringUtils.strip(replaceProcess, ",");
                        auditingUserId_i = StringUtils.strip(replace2, ",");
                    }
                    break;
                case 34:// 物流负责人审核
                    // 由物流负责人指派订舱专员和操作专员
                    deliverConsign.setBookingOfficer(rDeliverConsign.getBookingOfficer());
                    deliverConsign.setBookingOfficerId(rDeliverConsign.getBookingOfficerId());
                    deliverConsign.setOperationSpecialist(rDeliverConsign.getOperationSpecialist());
                    deliverConsign.setOperationSpecialistId(rDeliverConsign.getOperationSpecialistId());
                    String replace3 = StringUtils.strip(auditingUserId.replaceFirst(deliverConsign.getLogisticsLeaderId().toString(), ""));
                    if ("".equals(replace3)) { // 跟他并行审核的都已经审核完成
                        auditingProcess_i = "35,36";
                        auditingUserId_i = String.format("%d,%d", deliverConsign.getBookingOfficerId(), deliverConsign.getOperationSpecialistId());
                    } else {
                        isComeMore = true;
                        String replaceProcess = auditingProcess.replace("34", "");
                        auditingProcess_i = StringUtils.strip(replaceProcess, ",");
                        auditingUserId_i = StringUtils.strip(replace3, ",");
                    }
                    break;
                case 35://订舱专员审核
                    if (auditingProcess.indexOf("36") == -1) { // 跟他并行审核的操作专员已经审核完成
                        auditingStatus_i = 4; // 完成
                        auditingProcess_i = "999"; // 无下一审核进度和审核人
                        auditingUserId_i = null;
                    } else {
                        isComeMore = true;
                        auditingProcess_i = "36";
                        auditingUserId_i = deliverConsign.getOperationSpecialistId().toString();
                    }
                    break;
                case 36://操作专员审核
                    if (auditingProcess.indexOf("35") == -1) { // 跟他并行审核的订舱专员已经审核完成
                        auditingStatus_i = 4; // 完成
                        auditingProcess_i = "999"; // 无下一审核进度和审核人
                        auditingUserId_i = null;
                    } else {
                        isComeMore = true;
                        auditingProcess_i = "35";
                        auditingUserId_i = deliverConsign.getBookingOfficerId().toString();
                    }
                    break;
                default:
                    return false;
            }
            checkLog_i = orderService.fullCheckLogInfo(null, CheckLog.checkLogCategory.DELIVERCONSIGN.getCode(), deliverConsign.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, auditingProcess_i, auditingUserId_i, reason, "2", 4);
        }
        checkLogService.insert(checkLog_i);
        deliverConsign.setAuditingStatus(auditingStatus_i);
        deliverConsign.setAuditingProcess(auditingProcess_i);
        deliverConsign.setAuditingUserId(auditingUserId_i);
        deliverConsign.setAudiRemark(auditorIds.toString());
        //当时并行审核时分别推送待办和钉钉
        if (auditingUserId_i != null) {
            if ("32,33,34".equals(auditingProcess_i) || "35,36".equals(auditingProcess_i)) {
                String[] split = auditingUserId_i.split(",");
                for (int n = 0; n < split.length; n++) {
                    sendDingtalk(deliverConsign, split[n], rejectFlag);
                    // auditBackLogHandle(deliverConsign, rejectFlag, Integer.parseInt(split[n]));
                }
            } else if (!isComeMore) {//并行未审核完，不需要重复发送
                sendDingtalk(deliverConsign, auditingUserId_i.toString(), rejectFlag);
            }
        }
        auditBackLogHandle(deliverConsign, rejectFlag, auditingUserId_i, auditorId, isComeMore);
        deliverConsignDao.save(deliverConsign);
//        if (deliverConsign.getAuditingStatus() == 4 && deliverConsign.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode()) {
//            pushOutStock(deliverConsign);
//        }
        if (rejectFlag) { //如果是驳回，需要同步授信额度
            disposeLineOfCredit(deliverConsign.getId());
        }
        return true;
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

//            if (deliverConsign.getAuditingStatus() == 4 && "999".equals(deliverConsign.getAuditingProcess())) {
//                // 推动
//                String returnNo = deliverConsign.getDeliverConsignNo(); // 返回单号
//                String infoContent = deliverConsign.getCreateUserName();  //提示内容
//                Integer followId = deliverConsign.getId();
//                Integer hostId = deliverConsign.getOrder().getId();
//                Integer userId = deliverConsign.getCreateUserId(); //经办人id
//                // 推送增加待办事件，通知采购经办人办理报检单
//                applicationContext.publishEvent(new TasksAddEvent(applicationContext, backLogService,
//                        BackLog.ProjectStatusEnum.INSTOCKSUBMENUDELIVER,
//                        returnNo,
//                        infoContent,
//                        hostId,
//                        followId,
//                        "订舱",
//                        userId));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //钉钉通知 审批人
    private void sendDingtalk(DeliverConsign deliverConsign, String user, boolean rejectFlag) {
        //获取token
        final String eruiToken = (String) ThreadLocalUtil.getObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("发送短信的用户token:" + eruiToken);
                // 根据id获取商务经办人信息
                String jsonParam = "{\"id\":\"" + user + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String userInfo = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + userInfo);
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
                    logger.info("发送钉钉通知返回状态" + s1);
                }
            }
        }).start();
    }

    // 同步授信额度
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
            logger.info("查询授信返回信息：" + e);
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

                    // 判断授信额度够不够
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
                                logger.info("查询授信返回信息：" + e);
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

    // 同步授信额度驳回相当于回款
    public JSONObject disposeLineOfCredit(Integer id) throws Exception {

        DeliverConsign deliverConsign = deliverConsignDao.findOne(id);
        //（1）当“本批次发货金额”≤“预收金额”+“可用授信额度/汇率”时，系统判定可以正常发货。
        //（2）当“本批次发货金额”＞“预收金额”+“可用授信额度/汇率”时，系统判定不允许发货
        BigDecimal advanceMoney = deliverConsign.getOrder().getAdvanceMoney() == null ? BigDecimal.valueOf(0) : deliverConsign.getOrder().getAdvanceMoney();//预收金额      /应收账款余额
        BigDecimal thisShipmentsMoney = deliverConsign.getThisShipmentsMoney() == null ? BigDecimal.valueOf(0.00) : deliverConsign.getThisShipmentsMoney();//本批次发货金额
        BigDecimal exchangeRate = deliverConsign.getOrder().getExchangeRate() == null ? BigDecimal.valueOf(1) : deliverConsign.getOrder().getExchangeRate();//订单中利率


        //获取授信额度信息
        DeliverConsign deliverConsignByCreditData = null;
        try {
            if (StringUtils.isNotBlank(deliverConsign.getOrder().getCrmCode())) {
                deliverConsignByCreditData = queryCreditData(deliverConsign.getOrder());
            }

        } catch (Exception e) {
            throw new Exception(e);
        }

        if (deliverConsignByCreditData != null) {
            BigDecimal lineOfCredit = deliverConsignByCreditData.getLineOfCredit() == null ? BigDecimal.valueOf(0) : deliverConsignByCreditData.getLineOfCredit(); //授信额度
            if (lineOfCredit.compareTo(BigDecimal.valueOf(0)) == 1) {   // 判断是否有授信额度

                BigDecimal subtract1 = advanceMoney.subtract(thisShipmentsMoney); //预收  减去  本次发货金额

                if (subtract1.compareTo(BigDecimal.valueOf(0)) == -1) {   //先判断是否有预收，预收够不够本次发货的
                    //判断授信额度够不够
                    BigDecimal subtract = thisShipmentsMoney.subtract(advanceMoney);    // 本次发货金额  -  预收金额  = 需要使用授信的额度
                    BigDecimal multiply = subtract.multiply(exchangeRate);  //需要使用授信的额度 * 汇率
                    if (multiply.compareTo(BigDecimal.valueOf(0)) == 1) {  //本批次发货金额 大于 预收金额时，调用授信接口，修改授信额度
                        BigDecimal creditAvailable1 = deliverConsignByCreditData.getCreditAvailable().divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);// 可用授信额度
                        BigDecimal lineOfCredit1 = deliverConsignByCreditData.getLineOfCredit().divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);    //授信额度
                        BigDecimal subtract2 = lineOfCredit1.subtract(creditAvailable1);   //所欠授信额度
                        BigDecimal subtract3 = multiply; //可以返还的授信额度
                        if (subtract2.subtract(multiply).compareTo(BigDecimal.valueOf(0)) == -1) { // 所欠授信额度小于可以返还的授信额度
                            subtract3 = subtract2; // 返还所欠授信额度
                        }
                        try {
                            JSONObject jsonObject = buyerCreditPaymentByOrder(deliverConsign.getOrder(), 2, subtract3);
                            JSONObject data = jsonObject.getJSONObject("data");//获取查询数据
                            if (data == null) {  //查询数据正确返回 1
                                throw new Exception("同步授信额度失败");
                            } else {
                                return data;
                            }

                        } catch (Exception e) {
                            logger.info("查询授信返回信息：" + e);
                            throw new Exception(e);
                        }

                    } else {
                        throw new Exception("预收金额和可用授信额度不足");
                    }

                }
            }
        }
        return null;
    }
}
