package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.service.BackLogService;
import com.erui.order.service.IogisticsDataService;
import com.erui.order.util.IReceiverDate;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class IogisticsDataServiceImpl implements IogisticsDataService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    OrderLogDao orderLogDao;

    @Autowired
    IogisticsDataDao iogisticsDataDao;

    @Autowired
    private AttachmentServiceImpl attachmentService;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    DeliverConsignDao deliverConsignDao;

    @Autowired
    private DeliverDetailDao deliverDetailDao;

    @Autowired
    private BackLogService backLogService;

    @Value("#{orderProp[EXPORT_EACP]}")
    private String exportEacp; //给提供eacp数据

    /**
     * 物流跟踪管理（V 2.0）   列表页查询
     *
     * @param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IogisticsData> trackingList(IogisticsData iogisticsData) {

        PageRequest request = new PageRequest(iogisticsData.getPage() - 1, iogisticsData.getRows(), Sort.Direction.DESC, "id");

        Page<IogisticsData> page = iogisticsDataDao.findAll(new Specification<IogisticsData>() {
            @Override
            public Predicate toPredicate(Root<IogisticsData> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                //根据物流经办人id
                if (iogisticsData.getLogisticsUserId() != null) {
                    list.add(cb.equal(root.get("logisticsUserId").as(Integer.class), iogisticsData.getLogisticsUserId()));
                }

                //根据运单号
                if (iogisticsData.getTheAwbNo() != null) {
                    list.add(cb.like(root.get("theAwbNo").as(String.class), "%" + iogisticsData.getTheAwbNo() + "%"));
                }

                //根据 物流经办日期
                if (iogisticsData.getLogisticsDate() != null) {
                    list.add(cb.equal(root.get("logisticsDate").as(Date.class), iogisticsData.getLogisticsDate()));
                }

                //根据状态
                if (iogisticsData.getStatus() != null) {
                    list.add(cb.equal(root.get("status").as(Integer.class), iogisticsData.getStatus()));
                }

                //根据 销售合同号    产品放行单号   根据放行日期
                if (StringUtil.isNotBlank(iogisticsData.getContractNo()) || StringUtil.isNotBlank(iogisticsData.getDeliverDetailNo()) || iogisticsData.getReleaseDate() != null) {

                    Set<IogisticsData> IogisticsDataSet = findContractNoAndDeliverDetailNoAndReleaseDate(iogisticsData.getContractNo(), iogisticsData.getDeliverDetailNo(), iogisticsData.getReleaseDate());
                    CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                    if (IogisticsDataSet != null && IogisticsDataSet.size() > 0) {
                        for (IogisticsData p : IogisticsDataSet) {
                            idIn.value(p.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);

            }
        }, request);

        return page;
    }

    /**
     * 物流跟踪管理（V 2.0）  查询物流详情id
     *
     * @param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IogisticsData iogisticsDataById(Integer id) throws Exception {
        IogisticsData logisticsDataById = iogisticsDataDao.findById(id);
        if (logisticsDataById == null) {
            throw new Exception(String.format("%s%s%s", "没有此id信息", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "No ID information"));
        }
        if (logisticsDataById.getId() != null) {
            List<Attachment> attachments = attachmentService.queryAttachs(logisticsDataById.getId(), Attachment.AttachmentCategory.LOGISTICS.getCode());
            if (attachments != null && attachments.size() > 0) {
                logisticsDataById.setAttachmentList(attachments);
            }
        }
        return logisticsDataById;
    }


    /**
     * 物流跟踪管理（V 2.0）物流动态跟踪 ：动态更新-项目完结
     *
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logisticsActionAddOrSave(IogisticsData iogisticsData) throws Exception {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        IogisticsData one = iogisticsDataById(iogisticsData.getId());
        //物流经办人
        if (iogisticsData.getLogisticsUserId() != null) {
            one.setLogisticsUserId(iogisticsData.getLogisticsUserId());
        }
        //物流经办人姓名
        if (StringUtil.isNotBlank(iogisticsData.getLogisticsUserName())) {
            one.setLogisticsUserName(iogisticsData.getLogisticsUserName());
        }
        //经办日期
        if (iogisticsData.getLogisticsDate() != null) {
            one.setLogisticsDate(iogisticsData.getLogisticsDate());

            /**
             *  订单执行跟踪   推送运单号      经办日期
             */
            List<OrderLog> orderLogList = orderLogDao.findByIogisticsDataId(iogisticsData.getId());   //查询是否有记录
            if (orderLogList.size() == 0) {  //如果等于空，新增

                List<Iogistics> iogisticsList = one.getIogistics(); //获取到出库分单

                List<Integer> idList = new ArrayList<>();   //获取到出库id避免重复提交      如果一个物流中，有两个分单信息，避免出现两个物流跟踪 。  如果一个出库有两个分单信息，分别发物流，也不会出现不添加物流信息的情况

                for (Iogistics iogistics : iogisticsList) {

                    DeliverDetail deliverDetail = iogistics.getDeliverDetail(); //获取出库信息

                    List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();
                    for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
                        Order order = deliverConsignGoods.getGoods().getOrder();//获取到订单id
                        if (!idList.contains(order.getId())) {
                            OrderLog orderLog1 = new OrderLog();
                            try {
                                orderLog1.setIogisticsDataId(iogisticsData.getId());
                                orderLog1.setOrder(order);
                                orderLog1.setLogType(OrderLog.LogTypeEnum.OTHER.getCode());
                                orderLog1.setOperation(one.getTheAwbNo());
                                orderLog1.setCreateTime(new Date());
                                orderLog1.setBusinessDate(iogisticsData.getLogisticsDate());
                                orderLogDao.save(orderLog1);
                            } catch (Exception ex) {
                                logger.error("日志记录失败 {}", orderLog1.toString());
                                logger.error("错误", ex);
                                ex.printStackTrace();
                            }

                            idList.add(order.getId());
                        }
                    }
                }
            } else {  //不等于空，更新时间
                for (OrderLog orderLog : orderLogList) {
                    orderLog.setBusinessDate(iogisticsData.getLogisticsDate());
                    orderLogDao.save(orderLog);
                }
            }

        }
        //物流发票号
        if (StringUtil.isNotBlank(iogisticsData.getLogiInvoiceNo())) {
            one.setLogiInvoiceNo(iogisticsData.getLogiInvoiceNo());
        }
        //通知市场箱单时间
        if (iogisticsData.getPackingTime() != null) {
            one.setPackingTime(iogisticsData.getPackingTime());
        }
        //离厂时间          --
        if (iogisticsData.getLeaveFactory() != null) {
            one.setLeaveFactory(iogisticsData.getLeaveFactory());
            //当货物离场时即是出口通知单发货通知EACP回执已发货状态
            if (one.getDeliverDetailNo() != null) {
                DeliverDetail deliverDetail = deliverDetailDao.findByDeliverDetailNo(one.getDeliverDetailNo());
                if (deliverDetail.getDeliverConsign() != null && deliverDetail.getDeliverConsign().getOrder().getOrderSource() == 4) {
                    if (StringUtils.isNotBlank(eruiToken)) {
                        Map<String, Object> jsonMap = new HashMap<>();
                        jsonMap.put("exportId", deliverDetail.getDeliverConsign().getId());
                        jsonMap.put("exportStatus", "SHIPPEND");
                        Map<String, String> header = new HashMap<>();
                        header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                        header.put("Content-Type", "application/json");
                        header.put("accept", "*/*");
                        HttpRequest.sendPost(exportEacp, JSONObject.toJSONString(jsonMap), header);
                    }
                }
            }

        }
        //动态描述
        if (StringUtil.isNotBlank(iogisticsData.getLogs())) {
            one.setLogs(iogisticsData.getLogs());
        }
        //备注
        if (StringUtil.isNotBlank(iogisticsData.getRemarks())) {
            one.setRemarks(iogisticsData.getRemarks());
        }
        //物流状态
        if (iogisticsData.getStatus() != null) {
            one.setStatus(iogisticsData.getStatus());
        }
        //下发订舱时间
        if (iogisticsData.getBookingTime() != null) {
            one.setBookingTime(iogisticsData.getBookingTime());//下发订舱时间
        }
        //船期或航班
        if (iogisticsData.getSailingDate() != null) {
            one.setSailingDate(iogisticsData.getSailingDate());//船期或航班
        }
        //报关放行时间
        if (iogisticsData.getCustomsClearance() != null) {
            one.setCustomsClearance(iogisticsData.getCustomsClearance());//报关放行时间
        }
        //实际离港时间
        if (iogisticsData.getLeavePortTime() != null) {
            one.setLeavePortTime(iogisticsData.getLeavePortTime());//实际离港时间
        }
        //预计抵达时间
        if (iogisticsData.getArrivalPortTime() != null) {
            one.setArrivalPortTime(iogisticsData.getArrivalPortTime());//预计抵达时间
        }

        //物流发运金额(美元)
        if (iogisticsData.getLogisticsPriceUsd() != null) {
            one.setLogisticsPriceUsd(iogisticsData.getLogisticsPriceUsd());//物流发运金额(美元)
        }
        //物流费用
        if (iogisticsData.getLogisticsCost() != null) {
            one.setLogisticsCost(iogisticsData.getLogisticsCost());//物流发运金额(美元)
        }
        //物流费用币种
        if (iogisticsData.getLogisticsCostType() != null) {
            one.setLogisticsCostType(iogisticsData.getLogisticsCostType());//物流发运金额(美元)
        }

        List<Iogistics> iogisticsList = one.getIogistics(); //获取出库分单信息
        Integer projectId = 0;
        for (Iogistics iogistics : iogisticsList) {
            List<DeliverConsignGoods> deliverConsignGoodsList = iogistics.getDeliverDetail().getDeliverConsignGoodsList();  //获取出口发货商品信息
            for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {

                if (deliverConsignGoods.getSendNum() != 0) {  //本批次发货数量为0的商品不推送信息

                    /**
                     * 推送项目跟踪
                     */
                    Goods goods = deliverConsignGoods.getGoods();

                    goods.setSenderId(one.getLogisticsUserId());    //订舱人id

                    //下发订舱时间
                    if (iogisticsData.getBookingTime() != null) {
                        if(goods.getBookingTime() == null) goods.setBookingTime(iogisticsData.getBookingTime());//订舱日期
                    }
                    //船期或航班
                    if (iogisticsData.getSailingDate() != null) {
                        if(goods.getSailingDate() == null) goods.setSailingDate(iogisticsData.getSailingDate());//船期或航班
                    }
                    //报关放行时间
                    if (iogisticsData.getCustomsClearance() != null) {
                        if(goods.getCustomsClearance() == null) goods.setCustomsClearance(iogisticsData.getCustomsClearance());//报关放行时间
                    }
                    //实际离港时间
                    if (iogisticsData.getLeavePortTime() != null) {
                        if(goods.getLeavePortTime() == null) goods.setLeavePortTime(iogisticsData.getLeavePortTime());//实际离港时间
                    }
                    //离厂时间
                    if (iogisticsData.getLeaveFactory() != null) {
                        if(goods.getLeaveFactory() == null) goods.setLeaveFactory(iogisticsData.getLeaveFactory());//离厂时间
                    }
                    //预计抵达时间
                    if (iogisticsData.getArrivalPortTime() != null) {
                        if(goods.getArrivalPortTime() == null) goods.setArrivalPortTime(iogisticsData.getArrivalPortTime());//预计抵达时间
                    }
                    //物流经办人
                    if (iogisticsData.getLogisticsUserName() != null) {
                        if(goods.getLogisticsUserName() == null) goods.setLogisticsUserName(iogisticsData.getLogisticsUserName());//物流经办人
                    }
                    //物流费用
                    if (iogisticsData.getLogisticsCost() != null) {
                        if(goods.getLogisticsCost() == null) goods.setLogisticsCost(iogisticsData.getLogisticsCost());//物流费用
                        if(!projectId.equals(goods.getProject().getId())){
                            if(goods.getProject().getTotalLogisticsCost() == null){
                                goods.getProject().setTotalLogisticsCost(iogisticsData.getLogisticsCost());
                            }else{
                                goods.getProject().setTotalLogisticsCost(goods.getProject().getTotalLogisticsCost().add(iogisticsData.getLogisticsCost()));
                            }
                        }
                        projectId = goods.getProject().getId();
                    }

                    if (iogisticsData.getLeaveFactory() != null) {
                        //已发运
                        applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 9, eruiToken));
                    }

                    if (iogisticsData.getStatus() == 7) {
                        Date date = new Date();
                        one.setAccomplishDate(date);    // 物流动态更新 实际完成时间

                        List<Iogistics> iogistics1 = one.getIogistics();

                        for (Iogistics iogistics2 : iogistics1) {
                            List<DeliverConsignGoods> deliverConsignGoodsList1 = iogistics2.getDeliverDetail().getDeliverConsignGoodsList(); //获取出库信息，获取出口通知单商品
                            for (DeliverConsignGoods deliverConsignGoodss : deliverConsignGoodsList1) {
                                if (deliverConsignGoodss.getSendNum() != 0) {  //本批次发货数量为0的商品不推送信息
                                    Goods one1 = goodsDao.findOne(deliverConsignGoodss.getGoods().getId()); //推送实际完成日期
                                    one1.setAccomplishDate(date);
                                    goodsDao.saveAndFlush(one1);
                                }
                            }
                        }

                        //出库信息管理添加经办人确定以后删除  确认出库  待办信息
                        BackLog backLog2 = new BackLog();
                        backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.LOGISTICSDATA.getNum());    //功能访问路径标识
                        backLog2.setHostId(iogisticsData.getId());
                        backLogService.updateBackLogByDelYn(backLog2);
                    }
                }

            }

        }

        // 只接受国际物流部的附件
      /*  List<Attachment> collect = iogisticsData.getAttachmentList().stream().filter(attachment -> {
            return "国际物流部".equals(attachment.getGroup());
        }).collect(Collectors.toList());

        List<Attachment> attachmentList = new ArrayList(one.getAttachmentList());

        List<Attachment> attachmentList02 = new ArrayList<>();
        Iterator<Attachment> iterator = attachmentList.iterator();
        while (iterator.hasNext()) {
            Attachment next = iterator.next();
            if (!"国际物流部".equals(next.getGroup())) {
                attachmentList02.add(next);
                iterator.remove();
            }
        }

        List<Attachment> attachments = attachmentService.updateAttachments(attachmentList, collect, iogisticsData.getCreateUserId(), iogisticsData.getCreateUserName());
        attachmentList02.addAll(attachments);
        one.setAttachmentList(attachmentList02);*/
        //附件处理
        List<Attachment> attachmentList = iogisticsData.getAttachmentList();
        if (attachmentList != null && attachmentList.size() > 0) {
            Map<Integer, Attachment> dbAttahmentsMap = one.getAttachmentList().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
            attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, one.getId(), Attachment.AttachmentCategory.LOGISTICS.getCode());
        }

        IogisticsData iogisticsData1 = iogisticsDataDao.saveAndFlush(one);

        //订单合同交货日期  日期回填
        if (iogisticsData1.getArrivalPortTime() != null || iogisticsData1.getLeavePortTime() != null || iogisticsData1.getLeaveFactory() != null) {   //预计抵达日期   实际离港日期  离厂日期
            deliveryDate(iogisticsData1);
        }


    }

    /**
     * 订单执行跟踪(V2.0)  根据运单号（物流运单号）查询物流信息
     *
     * @param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IogisticsData queryByTheAwbNo(String theAwbNo) {
        return iogisticsDataDao.findByTheAwbNo(theAwbNo); //根据物流运单号查询
    }


    /**
     * V2.0 订单执行跟踪  根据运单号（物流运单号）查询物流信息   确认收货
     *
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmTheGoodsByTheAwbNo(IogisticsData iogisticsData) throws Exception {
        IogisticsData one = iogisticsDataDao.findByTheAwbNo(iogisticsData.getTheAwbNo());
        if (one == null) {
            throw new Exception(String.format("%s%s%s", "未查到此条运单号信息", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "No information is found on this article"));
        }
        one.setConfirmTheGoods(iogisticsData.getConfirmTheGoods());
        iogisticsDataDao.saveAndFlush(one);
    }


    //根据 销售合同号    产品放行单号   根据放行日期
    public Set<IogisticsData> findContractNoAndDeliverDetailNoAndReleaseDate(String contractNo, String deliverDetailNo, Date releaseDate) {

        Set<IogisticsData> result = null;

        List<IogisticsData> page = iogisticsDataDao.findAll(new Specification<IogisticsData>() {
            @Override
            public Predicate toPredicate(Root<IogisticsData> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                Join<IogisticsData, Iogistics> iogisticsRoot = root.join("iogistics"); //获取合并出库

                //根据 销售合同号
                if (StringUtil.isNotBlank(contractNo)) {
                    list.add(cb.like(iogisticsRoot.get("contractNo").as(String.class), "%" + contractNo + "%"));
                }

                //产品放行单号
                if (StringUtil.isNotBlank(deliverDetailNo)) {
                    list.add(cb.like(iogisticsRoot.get("deliverDetailNo").as(String.class), "%" + deliverDetailNo + "%"));
                }

                Join<Iogistics, DeliverDetail> deliverDetailRoot = iogisticsRoot.join("deliverDetail"); //获取出库

                //根据放行日期
                if (releaseDate != null) {
                    list.add(cb.equal(deliverDetailRoot.get("releaseDate").as(Date.class), releaseDate));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);

            }
        });

        if (page != null && page.size() > 0) {
            result = new HashSet<>(page);
        }

        return result;
    }


    /**
     * V2.0订单列表增加确认收货按钮：
     * 2、所有出口发货通知单中的商品全部出库并在物流跟踪管理中“跟踪状态”为“执行中”。
     *
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean findStatusAndNumber(Integer orderId) {


        //根据订单id查询全部出库信息
        // 出口通知单 查询信息
        List<DeliverDetail> companyList = deliverDetailDao.findAll(new Specification<DeliverDetail>() {
            @Override
            public Predicate toPredicate(Root<DeliverDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {    //根据订单id查询出库信息
                List<Predicate> list = new ArrayList<>();

                //订单id查询
                Join<DeliverDetail, DeliverConsign> deliverConsign = root.join("deliverConsign");   //关联出口发货通知单
                Join<DeliverConsign, Order> order = deliverConsign.join("order");   //关联订单
                list.add(cb.equal(order.get("id").as(Integer.class), orderId)); //订单id

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        });


        //出库
        if (companyList.size() != 0) {    //如果出库信息为0的话，说明没有出口发货通知单，  出口发货通知单新建会推送到出库管理  一对一的关系
            for (DeliverDetail DeliverDetail : companyList) {
                if (DeliverDetail.getStatus() < 5) {     //出库信息必须为 确认出库 因为后面进行了分单
                    return false;
                }

                List<Integer> iogisticsDataIdList = new ArrayList<>();  //分单父级id （物流id）    避免一个出口两次发货

                List<Iogistics> iogisticsList = DeliverDetail.getIogistics();    //根据出库信息  查询有几条分单信息

                if (iogisticsList.size() != 0) {      //如果没有分单数据  说明没有进行分单
                    for (Iogistics iogistics : iogisticsList) {  //计算有几条物流信息（父级信息）
                        IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                        if (iogisticsData != null) {  //如果没有父级信息（物流），说明没有进行合单
                            Integer id1 = iogisticsData.getId();//物流id （分单父级id）
                            if (!iogisticsDataIdList.contains(id1)) { //避免父级信息重复
                                iogisticsDataIdList.add(id1);
                            }
                        } else {
                            return false;
                        }
                    }

                    if (iogisticsDataIdList.size() != 0) {    //如果没有父级id  说明没有出库
                        for (Integer iogisticsDataId : iogisticsDataIdList) {
                            IogisticsData byId = iogisticsDataDao.findOne(iogisticsDataId); //获取父级信息，一个出口，可能又两个父级信息
                            if (byId == null || byId.getStatus() == 5) { //判断物流 跟踪状态”为“执行中（6）”，必须大于5（确认出库）
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }


    /**
     * 订单合同交货日期  日期回填
     */
    public void deliveryDate(IogisticsData iogisticsData) {

        List<Iogistics> iogisticsList = iogisticsData.getIogistics();   //获取出库信息管理

        for (Iogistics iogistics : iogisticsList) {
            DeliverConsign deliverConsign = iogistics.getDeliverDetail().getDeliverConsign();//出口发货通知单
            Order order1 = deliverConsign.getOrder();
            String tradeTerms = order1.getTradeTerms();//贸易术语
            List<OrderPayment> orderPayments = order1.getOrderPayments(); // 订单 根据支付类型处理
            if (orderPayments.size() != 0) {
                orderPaymentType(iogisticsData, tradeTerms, orderPayments);
            }
            List<DeliverConsignPayment> deliverConsignPayments = deliverConsign.getDeliverConsignPayments();    //出口发货通知单支付方式
            if (deliverConsignPayments.size() != 0) {
                deliverConsignType(iogisticsData, tradeTerms, deliverConsignPayments);     // 出口发货通知单   根据支付类型处理
            }
        }
    }


    // 出口发货通知单   根据支付类型处理
    public void deliverConsignType(IogisticsData iogisticsData, String tradeTerms, List<DeliverConsignPayment> deliverConsignPayments) {

        if (StringUtil.isNotBlank(tradeTerms)) {  //贸易术语

            String[] etypeList = {"EXW"};   //E类
            String[] dtypeList = {"DAP", "DAT", "DDP"};   //D类
            String[] elsetypeList = {"CFR", "CIF", "CIP", "CPT", "FCA", "FOB"};   //其他类

            //   Arrays.binarySearch()   大于0  说明有值。如果没有值的话返回的是负数
            if (Arrays.binarySearch(etypeList, tradeTerms) >= 0) {               //E类
                for (DeliverConsignPayment deliverConsignPayment : deliverConsignPayments) {
                    Integer type = deliverConsignPayment.getType();  //收款类型      4:'发货后',5:'货到后',6:'提单日后',7:'交货后',8:'验收后',9:'工厂交货后
                    //   E类收款方式  4:'发货后',   8:'验收后'  9:''工厂交货后'
                    if (type != null) {
                        commonSetReceiptDate(type, deliverConsignPayment, iogisticsData);
                    }
                }

            } else if (Arrays.binarySearch(dtypeList, tradeTerms) >= 0) {          //D类
                for (DeliverConsignPayment deliverConsignPayment : deliverConsignPayments) {
                    Integer type = deliverConsignPayment.getType();  //收款类型      4:'发货后',5:'货到后',6:'提单日后',7:'交货后',8:'验收后',9:'工厂交货后
                    //   D类收款方式    4:'发货后'   7:'交货后',  8:'验收后'
                    if (type != null) {
                        commonSetReceiptDate(type, deliverConsignPayment, iogisticsData);
                    }
                }

            } else if (Arrays.binarySearch(elsetypeList, tradeTerms) >= 0) {       //其他类
                for (DeliverConsignPayment deliverConsignPayment : deliverConsignPayments) {
                    Integer type = deliverConsignPayment.getType();  //收款类型      4:'发货后',5:'货到后',6:'提单日后',7:'交货后',8:'验收后',9:'工厂交货后
                    //   其他类收款方式    4:'发货后'   5:'货到后',  6:'提单日后'    7:'交货后'  ,8:'验收后'
                    if (type != null) {
                        commonSetReceiptDate(type, deliverConsignPayment, iogisticsData);
                    }
                }

            }
        }
    }

    // 订单 根据支付类型处理
    public void orderPaymentType(IogisticsData iogisticsData, String tradeTerms, List<OrderPayment> orderPayments) {

        if (StringUtil.isNotBlank(tradeTerms)) {  //贸易术语

            String[] etypeList = {"EXW"};   //E类
            String[] dtypeList = {"DAP", "DAT", "DDP"};   //D类
            String[] elsetypeList = {"CFR", "CIF", "CIP", "CPT", "FCA", "FOB"};   //其他类

            //   Arrays.binarySearch()   大于0  说明有值。如果没有值的话返回的是负数
            if (Arrays.binarySearch(etypeList, tradeTerms) >= 0) {               //E类
                for (OrderPayment orderPayment : orderPayments) {
                    Integer type = orderPayment.getType();  //收款类型      4:'发货后',5:'货到后',6:'提单日后',7:'交货后',8:'验收后',9:'工厂交货后
                    //   E类收款方式  4:'发货后',   8:'验收后'  9:''工厂交货后'
                    if (type != null) {
                        commonSetReceiptDate(type, orderPayment, iogisticsData);
                    }
                }

            } else if (Arrays.binarySearch(dtypeList, tradeTerms) >= 0) {          //D类
                for (OrderPayment orderPayment : orderPayments) {
                    Integer type = orderPayment.getType();  //收款类型      4:'发货后',5:'货到后',6:'提单日后',7:'交货后',8:'验收后',9:'工厂交货后
                    //   D类收款方式    4:'发货后'   7:'交货后',  8:'验收后'
                    if (type != null) {
                        commonSetReceiptDate(type, orderPayment, iogisticsData);
                    }
                }

            } else if (Arrays.binarySearch(elsetypeList, tradeTerms) >= 0) {       //其他类
                for (OrderPayment orderPayment : orderPayments) {
                    Integer type = orderPayment.getType();  //收款类型      4:'发货后',5:'货到后',6:'提单日后',7:'交货后',8:'验收后',9:'工厂交货后
                    //   其他类收款方式    4:'发货后'   5:'货到后',  6:'提单日后'    7:'交货后'  ,8:'验收后'
                    if (type != null) {
                        commonSetReceiptDate(type, orderPayment, iogisticsData);
                    }
                }
            }
        }
    }

    private void commonSetReceiptDate(Integer type, IReceiverDate receiverDate, IogisticsData iogisticsData) {
        switch (type) {
            case 4: //发货后   取值 离厂日期
                if (iogisticsData.getLeaveFactory() != null) {
                    receiverDate.setReceiptDate(iogisticsData.getLeaveFactory());
                }
                break;
            case 5:  //货到后      取值   预计抵达日期
                if (iogisticsData.getArrivalPortTime() != null) {
                    receiverDate.setReceiptDate(iogisticsData.getArrivalPortTime());
                }
                break;
            case 6: //提单日后   取值  实际离港日期
                if (iogisticsData.getLeavePortTime() != null) {
                    receiverDate.setReceiptDate(iogisticsData.getLeavePortTime());
                }
                break;
            case 7:   //交货后   取值  预计抵达日期
                if (iogisticsData.getArrivalPortTime() != null) {
                    receiverDate.setReceiptDate(iogisticsData.getArrivalPortTime());
                }
                break;
            case 8:     //验收后   取值  预计抵达日期
                if (iogisticsData.getArrivalPortTime() != null) {
                    receiverDate.setReceiptDate(iogisticsData.getArrivalPortTime());
                }
                break;
            case 9:     //工厂交货后   取值  离厂日期
                if (iogisticsData.getLeaveFactory() != null) {
                    receiverDate.setReceiptDate(iogisticsData.getLeaveFactory());
                }
                break;
            case 10://装船后（新增加内容，取物流跟踪“实际离港日期”）
                if (iogisticsData.getLeavePortTime() != null) {
                    receiverDate.setReceiptDate(iogisticsData.getLeavePortTime());
                }
                break;
            case 11://到港后（新增加内容，取物流跟踪“预计抵达日期”）
                if (iogisticsData.getArrivalPortTime() != null) {
                    receiverDate.setReceiptDate(iogisticsData.getArrivalPortTime());
                }
                break;
        }
    }


}
