package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class DeliverConsignServiceImpl implements DeliverConsignService {

    private static Logger logger = LoggerFactory.getLogger(DeliverDetailServiceImpl.class);

    @Autowired
    private DeliverConsignDao deliverConsignDao;

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private DeliverNoticeDao deliverNoticeDao;


    @Autowired
    private DeliverDetailDao deliverDetailDao;


    @Autowired
    ProjectDao projectDao;

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[MEMBER_LIST]}")
    private String memberList;  //查询人员信息调用接口

    @Value("#{orderProp[CREDIT_EXTENSION]}")
    private String creditExtension;  //授信服务器地址

    @Override
    @Transactional(readOnly = true)
    public DeliverConsign findById(Integer id) throws Exception {
        DeliverConsign deliverConsign = deliverConsignDao.findOne(id);
        if (deliverConsign != null) {
            List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
            if(deliverConsignGoodsSet.size() > 0){
                for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet){
                    deliverConsignGoods.getGoods().setPurchGoods(null);
                }
            }
            deliverConsign.getAttachmentSet().size();
        }
        Order order = deliverConsign.getOrder();
        BigDecimal exchangeRate = order.getExchangeRate() == null ? BigDecimal.valueOf(1) : order.getExchangeRate();
        deliverConsign.setExchangeRate(exchangeRate);   //汇率

        Integer status = deliverConsign.getStatus();    //获取出口发货通知单状态

        //非提交状态
        if(status != 3){

            //获取授信信息
            DeliverConsign deliverConsign1;
            try {
                deliverConsign1 = queryCreditData(order);
            }catch (Exception e){
                throw  new Exception(e.getMessage());
            }

            if(deliverConsign1 != null){
                //如果是保存状态，可用授信额度需要实时更新
                deliverConsign.setCreditAvailable(deliverConsign1.getCreditAvailable()); //可用授信额度

                //获取预收
                BigDecimal currencyBnShipmentsMoney =  order.getShipmentsMoney() == null ? BigDecimal.valueOf(0.00) : order.getShipmentsMoney();  //已发货总金额 （财务管理
                BigDecimal currencyBnAlreadyGatheringMoney = order.getAlreadyGatheringMoney() == null ? BigDecimal.valueOf(0.00) : order.getAlreadyGatheringMoney();//已收款总金额

                //收款总金额  -  发货总金额
                BigDecimal subtract = currencyBnAlreadyGatheringMoney.subtract(currencyBnShipmentsMoney);
                if(subtract.compareTo(BigDecimal.valueOf(0)) != -1 ){    //-1 小于     0 等于      1 大于
                    deliverConsign.setAdvanceMoney(subtract);     //预收金额
                }else {
                    deliverConsign.setAdvanceMoney(BigDecimal.valueOf(0.00));     //预收金额
                }

            }else {
                deliverConsign.setCreditAvailable(BigDecimal.valueOf(0.00));    //可用授信额度
                deliverConsign.setAdvanceMoney(BigDecimal.valueOf(0.00));     //预收金额
            }




        }

        return deliverConsign;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDeliverConsign(DeliverConsign deliverConsign) throws Exception {
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignUpdate = deliverConsignDao.findOne(deliverConsign.getId());
        deliverConsignUpdate.setOrder(order);
        deliverConsignUpdate.setDeptId(order.getExecCoId());
        deliverConsignUpdate.setCreateUserId(order.getAgentId());
        deliverConsignUpdate.setWriteDate(deliverConsign.getWriteDate());
        deliverConsignUpdate.setArrivalDate(deliverConsign.getArrivalDate());
        deliverConsignUpdate.setBookingDate(deliverConsign.getBookingDate());
        deliverConsignUpdate.setCreateUserId(deliverConsign.getCreateUserId());
        deliverConsignUpdate.setRemarks(deliverConsign.getRemarks());
        deliverConsignUpdate.setStatus(deliverConsign.getStatus());
        // 授信信息  and
        if(deliverConsign.getStatus() == 3){    //如果是提交操作保存 可用授信额度
            deliverConsignUpdate.setCreditAvailable(deliverConsign.getCreditAvailable());  //可用授信额度
            deliverConsignUpdate.setAdvanceMoney(deliverConsign.getAdvanceMoney());    //预收金额      /应收账款余额
        }
        deliverConsignUpdate.setLineOfCredit(deliverConsign.getLineOfCredit());     //授信额度
        deliverConsignUpdate.setThisShipmentsMoney(deliverConsign.getThisShipmentsMoney());     //本批次发货金额
        // 授信信息  end

        //付款信息
        deliverConsignUpdate.setDeliverConsignPayments(deliverConsign.getDeliverConsignPayments());
        // 处理附件
        List<Attachment> attachments = attachmentService.handleParamAttachment(deliverConsignUpdate.getAttachmentSet(), deliverConsign.getAttachmentSet(), null, null);
        deliverConsignUpdate.setAttachmentSet(attachments);
        // 商品信息
        Map<Integer, DeliverConsignGoods> oldDcGoodsMap = deliverConsignUpdate.getDeliverConsignGoodsSet().parallelStream().collect(Collectors.toMap(DeliverConsignGoods::getId, vo -> vo));
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        Set<Integer> orderIds = new HashSet<>();
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
                    orderIds.add(goods.getOrder().getId());
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
//        goodsDao.save(goodsList.values());
        DeliverConsign deliverConsign1 = deliverConsignDao.saveAndFlush(deliverConsignUpdate);
        if (deliverConsign.getStatus() == 3) {
            Project project = order.getProject();
            order.setDeliverConsignHas(2);
            project.setDeliverConsignHas(2);
            orderDao.save(order);
            projectDao.save(project);
            orderService.updateOrderDeliverConsignC(orderIds);

            //推送出库信息
            String deliverDetailNo = createDeliverDetailNo();   //产品放行单号
            pushOutbound(deliverConsignUpdate,deliverDetailNo);


            // 出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
            Map<String,Object> map = new HashMap<>();
            map.put("deliverConsignNo",deliverConsignUpdate.getDeliverConsignNo());  //出口通知单号
            map.put("deliverDetailNo",deliverDetailNo);  //产品放行单号
            map.put("contractNoOs",order.getContractNo());     //销售合同号
            try {
                sendSms(map);
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                JSONObject jsonObject = disposeAdvanceMoney(order, deliverConsign1);
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }

        }
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDeliverConsign(DeliverConsign deliverConsign) throws Exception {
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignAdd = new DeliverConsign();
        // 根据数据库中最后的发货通知单单号重新自动生成
        String deliverConsignNo = deliverConsignDao.findLaseDeliverConsignNo();
        deliverConsignAdd.setDeliverConsignNo(StringUtil.genDeliverConsignNo(deliverConsignNo));
        deliverConsignAdd.setOrder(order);
        deliverConsignAdd.setCoId(order.getSigningCo());
        deliverConsignAdd.setDeptId(order.getExecCoId());
        deliverConsignAdd.setExecCoName(order.getExecCoName());
        deliverConsignAdd.setWriteDate(deliverConsign.getWriteDate());
        deliverConsignAdd.setArrivalDate(deliverConsign.getArrivalDate());
        deliverConsignAdd.setBookingDate(deliverConsign.getBookingDate());
        deliverConsignAdd.setCreateUserId(deliverConsign.getCreateUserId());
        deliverConsignAdd.setDeliverYn(deliverConsign.getDeliverYn());
        deliverConsignAdd.setRemarks(deliverConsign.getRemarks());
        deliverConsignAdd.setCountry(order.getCountry());
        deliverConsignAdd.setRegion(order.getRegion());
        deliverConsignAdd.setCreateTime(new Date());
        deliverConsignAdd.setStatus(deliverConsign.getStatus());
        deliverConsignAdd.setDeliverConsignGoodsSet(deliverConsign.getDeliverConsignGoodsSet());
        // 授信信息  and
        if(deliverConsign.getStatus() == 3){    //如果是提交操作保存 可用授信额度
            deliverConsignAdd.setCreditAvailable(deliverConsign.getCreditAvailable());  //可用授信额度
            deliverConsignAdd.setAdvanceMoney(deliverConsign.getAdvanceMoney());    //预收金额      /应收账款余额
        }
        deliverConsignAdd.setLineOfCredit(deliverConsign.getLineOfCredit());     //授信额度
        deliverConsignAdd.setThisShipmentsMoney(deliverConsign.getThisShipmentsMoney());     //本批次发货金额
        // 授信信息  end


        // 处理附件信息
        List<Attachment> attachments = attachmentService.handleParamAttachment(null, deliverConsign.getAttachmentSet(), null, null);
        deliverConsignAdd.setAttachmentSet(attachments);
        //添加收款信息
        deliverConsignAdd.setDeliverConsignPayments(deliverConsign.getDeliverConsignPayments());
        // 处理商品信息
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        Set<Integer> orderIds = new HashSet<>();
        for (DeliverConsignGoods dcGoods : deliverConsign.getDeliverConsignGoodsSet()) {
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            if (goods.getOutstockApplyNum() + dcGoods.getSendNum() <= goods.getContractGoodsNum()) {
                dcGoods.setGoods(goods);
                dcGoods.setCreateTime(new Date());
                if (deliverConsign.getStatus() == 3) {
                    goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
                    orderIds.add(goods.getOrder().getId());
                }
                goods.setOutstockApplyNum(goods.getOutstockApplyNum() + dcGoods.getSendNum());
                goodsDao.save(goods);
            } else {
                throw new Exception(String.format("%s%s%s", "发货总数量超过合同数量", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Total quantity of shipments exceeds the number of contracts"));
            }
        }
        DeliverConsign deliverConsign1 = deliverConsignDao.save(deliverConsignAdd);
        if (deliverConsign.getStatus() == 3) {
            Project project = order.getProject();
            order.setDeliverConsignHas(2);
            project.setDeliverConsignHas(2);
            orderDao.save(order);
            projectDao.save(project);
            orderService.updateOrderDeliverConsignC(orderIds);

            //发送短信  and
            //推送出库信息
            String deliverDetailNo = createDeliverDetailNo();
            pushOutbound(deliverConsign1,deliverDetailNo);


            // 出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
            Map<String,Object> map = new HashMap<>();
            map.put("deliverConsignNo",deliverConsign1.getDeliverConsignNo());  //出口通知单号
            map.put("deliverDetailNo",deliverDetailNo);  //产品放行单号
            map.put("contractNoOs",order.getContractNo());     //销售合同号
            try {
                sendSms(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //发送短信  end

            
            try {
                JSONObject jsonObject = disposeAdvanceMoney(order, deliverConsign1);
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }



        }
        return true;
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
            deliverConsign.setDeliverConsignGoodsSet(null);
            deliverConsign.setAttachmentSet(null);
            List<Goods> goodsList = deliverConsign.getOrder().getGoodsList();
            if(goodsList.size() > 0){
                for (Goods goods : goodsList){
                    goods.setPurchGoods(null);
                }
            }

        }
        return deliverConsignList;
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


    /**
     * 看货通知管理   查询出口发货通知单
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliverConsign> queryExitAdvice(DeliverNotice deliverNotice) {

        PageRequest request = new PageRequest(deliverNotice.getPage() - 1, deliverNotice.getRows(), Sort.Direction.DESC, "createTime");

        Page<DeliverConsign> page = deliverConsignDao.findAll(new Specification<DeliverConsign>() {
            @Override
            public Predicate toPredicate(Root<DeliverConsign> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据国家查询
                String[] country = null;
                if (StringUtils.isNotBlank(deliverNotice.getCountry())) {
                    country = deliverNotice.getCountry().split(",");
                }
                if (country != null) {
                    list.add(root.get("country").in(country));
                }

                //根据下单人（国际物流经办人）      销售合同号    项目号
                if (deliverNotice.getSenderId() != null || StringUtil.isNotBlank(deliverNotice.getContractNo()) || StringUtil.isNotBlank(deliverNotice.getProjectNo())) {
                    Join<DeliverConsign, Order> orderRoot = root.join("order");
                    //根据 销售合同号
                    if (StringUtil.isNotBlank(deliverNotice.getContractNo())) {
                        list.add(cb.like(orderRoot.get("contractNo").as(String.class), "%" + deliverNotice.getContractNo() + "%"));
                    }
                    Join<Order, Project> projectRoot = orderRoot.join("project");
                    //根据下单人（国际物流经办人）
                    if (deliverNotice.getSenderId() != null) {
                        list.add(cb.equal(projectRoot.get("logisticsUid").as(Integer.class), deliverNotice.getSenderId()));
                    }
                    //根据 项目号
                    if (StringUtil.isNotBlank(deliverNotice.getProjectNo())) {
                        list.add(cb.like(projectRoot.get("projectNo").as(String.class), "%" + deliverNotice.getProjectNo() + "%"));
                    }
                }

                // 根据出口通知单号
                if (StringUtil.isNotBlank(deliverNotice.getDeliverConsignNo())) {
                    list.add(cb.like(root.get("deliverConsignNo").as(String.class), "%" + deliverNotice.getDeliverConsignNo() + "%"));
                }
                list.add(cb.equal(root.get("status").as(Integer.class), 3));    //已提交
                list.add(cb.equal(root.get("deliverYn").as(Integer.class), 1)); //未删除


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);

                Predicate result = cb.and(predicates);
                if (deliverNotice.getId() != null) {
                    DeliverNotice one = deliverNoticeDao.findOne(deliverNotice.getId());
                    List<DeliverConsign> deliverConsigns = one.getDeliverConsigns();//查询已选择
                    Integer[] arr = new Integer[deliverConsigns.size()];    //获取id
                    int i = 0;
                    for (DeliverConsign deliverConsign : deliverConsigns) {
                        arr[i] = (deliverConsign.getId());
                        i++;
                    }
                    return cb.or(result, root.get("id").in(arr));
                } else {
                    return result;
                }

            }
        }, request);

       /* if (page != null && page.size() > 1) {
            // 反序排列
            Collections.reverse(page);
        }*/

        return page;

    }


    /**
     * 推送出库信息
     * <p>
     * 订单V2.0
     * <p>
     * 根据出口通知单，推送出库信息
     */

    public void pushOutbound(DeliverConsign deliverConsign1,String deliverDetailNo) throws Exception {

        // 1:未编辑 2：保存/草稿 3:已提交'        当状态为已提交的时候，推送到出库管理
        DeliverDetail deliverDetail = new DeliverDetail();
        deliverDetail.setDeliverConsign(deliverConsign1);    //出库通知单
        deliverDetail.setDeliverDetailNo(deliverDetailNo);   //产品放行单

        //推送仓库经办人   物流经办人
        Order order1 = deliverConsign1.getOrder();
        Project project = order1.getProject();
        /*if (project.getWarehouseUid() != null) {
            deliverDetail.setWareHouseman(project.getWarehouseUid());   //仓库经办人id
        }
        if (StringUtil.isNotBlank(project.getWarehouseName())) {
            deliverDetail.setWareHousemanName(project.getWarehouseName());    //仓库经办人名字
        }
        if (project.getLogisticsUid() != null) {
            deliverDetail.setLogisticsUserId(project.getLogisticsUid());         //物流经办人id
        }
        if (StringUtil.isNotBlank(project.getLogisticsName())) {
            deliverDetail.setLogisticsUserName(project.getLogisticsName());   //物流经办人名字
        }*/
        if (project.getQualityUid() != null) {
            deliverDetail.setCheckerUid(project.getQualityUid());    //  检验工程师(品控经办人) ID
        }
        if (StringUtil.isNotBlank(project.getQualityName())) {
            deliverDetail.setCheckerName(project.getQualityName()); //  检验工程师名称(品控经办人名称)
        }

        deliverDetail.setStatus(DeliverDetail.StatusEnum.SAVED_OUTSTOCK.getStatusCode());
        deliverDetail.setDeliverConsignGoodsList(new ArrayList<>(deliverConsign1.getDeliverConsignGoodsSet()));
        try {
            deliverDetailDao.saveAndFlush(deliverDetail);
        } catch (Exception e) {
            throw new Exception("推送出库信息失败");
        }


        //TODO  项目执行跟踪：推送看货通知时间，订舱人
          /*  for (Goods goods : order1.getGoodsList()) {
                Goods one1 = goodsDao.findOne(goods.getId());
                one1.setSendDate(deliverNotice.getSendDate());//发送看货通知日期
                one1.setSenderId(deliverNotice.getSenderId());//订舱人id
                goodsDao.save(one1);
            }*/

    }


    /**
     * \//生成产品放行单
     *
     * @return
     */

    public String createDeliverDetailNo() {
        SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy");

        //查询最近插入的产品放行单
        String deliverDetailNo = deliverDetailDao.findDeliverDetailNo();
        if (deliverDetailNo == null) {
            String formats = simpleDateFormats.format(new Date());  //当前年份
            return formats + String.format("%04d", 1);     //第一个
        } else {
            String substring = deliverDetailNo.substring(0, 4); //获取到产品放行单的年份
            String formats = simpleDateFormats.format(new Date());  //当前年份
            if (substring.equals(formats)) {   //判断年份
                String substring1 = deliverDetailNo.substring(4);
                return formats + String.format("%04d", (Integer.parseInt(substring1) + 1));//最大的数值上加1
            } else {
                return formats + String.format("%04d", 1);     //第一个
            }
        }

    }



    //  出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
    public void sendSms(Map<String,Object> map1) throws  Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try{
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

                if(code == 1){
                    // 获取人员手机号
                    JSONArray data1 = jsonObject.getJSONArray("data");

                    //去除重复
                    Set<String> listAll = new HashSet<>();
                    for (int i = 0; i < data1.size(); i++){
                        JSONObject ob  = (JSONObject)data1.get(i);
                        String mobile = ob.getString("mobile");
                        if(StringUtils.isNotBlank(mobile)){
                            listAll.add(mobile);    //获取人员手机号
                        }
                    }

                    listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                    JSONArray smsarray = new JSONArray();   //手机号JSON数组
                    for (String me : listAll) {
                        smsarray.add(me);
                    }

                    //发送短信
                    Map<String,String> map= new HashMap();
                    map.put("areaCode","86");
                    map.put("to",smsarray.toString());

                    map.put("content"," 您好，销售合同号："+map1.get("contractNoOs")+"，已生成出口通知单号："+map1.get("deliverConsignNo")+"，产品放行单号："+map1.get("deliverDetailNo")+"，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType","0");
                    map.put("groupSending","0");
                    map.put("useType","订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态"+s1);
                }
            }catch (Exception e){
                throw new Exception(String.format("%s%s%s","发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"Failure to send SMS"));
            }

        }
    }

    /**
     * 根据订单中crm编码，查询授信信息
     * @param order
     * @return
     * @throws Exception
     */
    public DeliverConsign queryCreditData( Order order) throws Exception {
        //拿取局部返回信息
        String returnMassage;
        try {
            //获取当前订单用户crm客户码
            String crmCode = order.getCrmCode();
            //拼接查询授信路径
            String url = creditExtension + "V2/Buyercredit/getCreditInfoByCrmCode";
            //获取token
            String eruiToken = (String) ThreadLocalUtil.getObject();

            // 根据id获取人员信息
            String jsonParam = "{\"crm_code\":\""+crmCode+"\"}";
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            returnMassage = HttpRequest.sendPost(url, jsonParam, header);
            logger.info("人员详情返回信息：" + returnMassage);
        }catch (Exception ex){
            throw new Exception(String.format("获取客户授信信息失败"));
        }

        JSONObject jsonObject = JSONObject.parseObject(returnMassage);
        Integer code = jsonObject.getInteger("code");   //获取查询状态
        if(code != 1  &&  code != -401 ){  //查询数据正确返回 1
            String message = jsonObject.getString("message");
            throw new Exception(message);
        }
        if(code == 1 ){
            JSONObject data = jsonObject.getJSONObject("data");//获取查询数据

            BigDecimal nolcGranted = BigDecimal.valueOf(0);
            BigDecimal lcgranted = BigDecimal.valueOf(0);
            String accountSettle = null;
            BigDecimal creditAvailable = null;
            if (data != null){
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

            if(paymentModeBn != null){
                if(paymentModeBn.equals("1")){ //  1:信用证          //['1' => '信用证','2' => '托收','3'=>"电汇",'4'=>"信汇",'5'=>"票汇"];
                    accountSettles = "L/C";
                }else if(paymentModeBn.equals("2") || paymentModeBn.equals("3") || paymentModeBn.equals("4") || paymentModeBn.equals("5") ){
                    accountSettles = "OA";
                }
            }

            DeliverConsign deliverConsign = new DeliverConsign();

            if(accountSettle != null && accountSettles != null){
                if(accountSettle.equals(accountSettles) && accountSettle.equals("L/C")){    //信用证
                    deliverConsign.setLineOfCredit(lcgranted);   //信用证授信额度
                    deliverConsign.setCreditAvailable(creditAvailable);    // 可用授信额度

                }else if (accountSettle.equals(accountSettles) && accountSettle.equals("OA")){  //非信用证
                    deliverConsign.setLineOfCredit(nolcGranted);   //非信用证授信额度
                    deliverConsign.setCreditAvailable(creditAvailable);    // 可用授信额度
                }else {
                    deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
                    deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度
                }
            }else {
                deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
                deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度
            }

            return deliverConsign;
        }

        return  null;

    }


    /**
     * 处理授信额度
     * @param order 订单信息
     * @param flag  支出还是回款标识   1：支出   2：回款
     * @param orderMoney    支出OR回款金额
     */
    public JSONObject buyerCreditPaymentByOrder(Order order , Integer flag, BigDecimal orderMoney) throws Exception {
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
            String jsonParam = "{\"contract_no\":\""+contractNo+"\",\"order_money\":\""+orderMoney+"\",\"order_type\":\""+flag+"\",\"crm_code\":\""+crmCode+"\"}";
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            returnMassage = HttpRequest.sendPost(url, jsonParam, header);
            logger.info("人员详情返回信息：" + returnMassage);

            JSONObject jsonObject = JSONObject.parseObject(returnMassage);
            Integer code = jsonObject.getInteger("code");   //获取查询状态
            if(code != 1){  //查询数据正确返回 1
                String message = jsonObject.getString("message");
                throw new Exception(message);
            }

            if(code == 1){
                JSONObject data = jsonObject.getJSONObject("data");//获取查询数据
                return data;
            }

        }catch (Exception ex){
            throw new Exception(String.format("查询授信信息失败"));
        }

        return null;

    }


    public JSONObject  disposeAdvanceMoney(Order order , DeliverConsign deliverConsign1) throws Exception {

        //（1）当“本批次发货金额”≤“预收金额”+“可用授信额度/汇率”时，系统判定可以正常发货。
        //（2）当“本批次发货金额”＞“预收金额”+“可用授信额度/汇率”时，系统判定不允许发货
        BigDecimal advanceMoney = order.getAdvanceMoney()== null ? BigDecimal.valueOf(0.00) : order.getAdvanceMoney();//预收金额      /应收账款余额
        BigDecimal thisShipmentsMoney = deliverConsign1.getThisShipmentsMoney()== null ? BigDecimal.valueOf(0.00) : deliverConsign1.getThisShipmentsMoney();//本批次发货金额
        BigDecimal exchangeRate = order.getExchangeRate() == null ? BigDecimal.valueOf(0.00) : order.getExchangeRate();//订单中利率

        //获取授信额度信息
        DeliverConsign deliverConsignByCreditData;
        try {
            deliverConsignByCreditData = queryCreditData(order);

        }catch (Exception e){
            logger.info("查询授信返回信息：" + e);
            throw new Exception(e);
        }

            BigDecimal creditAvailable = deliverConsignByCreditData.getCreditAvailable();//可用授信额度
            BigDecimal divide = creditAvailable.divide(exchangeRate);//可用授信额度/利率
            BigDecimal add = divide.add(advanceMoney);  //预收金额”+“可用授信额度/汇率      可发货额度

            if (add.compareTo(thisShipmentsMoney) == 1 || add.compareTo(thisShipmentsMoney) == 0){  //可用授信额度 大于 使用的授信的额度 或者等于时 ，  可以发货

                BigDecimal subtract = thisShipmentsMoney.subtract(advanceMoney);


                BigDecimal lineOfCredit = deliverConsignByCreditData.getLineOfCredit(); //授信额度
                if(subtract.compareTo(BigDecimal.valueOf(0)) == 1 && lineOfCredit.compareTo(BigDecimal.valueOf(0)) == 1 ){  //本批次发货金额 大于 预收金额时，调用授信接口，修改授信额度
                    try {
                        JSONObject jsonObject = buyerCreditPaymentByOrder(order, 1, subtract);
                        JSONObject data = jsonObject.getJSONObject("data");//获取查询数据
                        if(data == null){  //查询数据正确返回 1
                            throw new Exception("同步授信额度失败");
                        }else {
                            return data;
                        }
                    }catch (Exception e){
                        logger.info("查询授信返回信息：" + e);
                        throw new Exception(e);
                    }
                }

            }else {
                throw new Exception("可用授信额度不足");
            }

        return null;

    }


}
