package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.requestVo.*;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.DeliverDetailService;
import com.erui.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    OrderLogDao orderLogDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private DeliverDetailService deliverDetailService;
    @Autowired
    private ComplexOrderDao complexOrderDao;

    @Override
    @Transactional(readOnly = true)
    public Order findById(Integer id) {
        Order order = orderDao.findOne(id);
        order.getGoodsList().size();
        order.getAttachmentSet().size();
        order.getOrderPayments().size();
        return order;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Order> findByPage(final OrderListCondition condition) {
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), new Sort(Sort.Direction.DESC, "id"));
        Page<Order> pageList = orderDao.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据销售同号模糊查询
                if (StringUtil.isNotBlank(condition.getContractNo())) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                //根据Po号模糊查询
                if (StringUtil.isNotBlank(condition.getPoNo())) {
                    list.add(cb.like(root.get("poNo").as(String.class), "%" + condition.getPoNo() + "%"));
                }
                //根据询单号查询
                if (StringUtil.isNotBlank(condition.getInquiryNo())) {
                    list.add(cb.like(root.get("inquiryNo").as(String.class), "%" + condition.getInquiryNo() + "%"));
                }
                //根据订单签订时间查询
                if (condition.getSigningDate() != null) {
                    list.add(cb.equal(root.get("signingDate").as(Date.class), NewDateUtil.getDate(condition.getSigningDate())));
                }
                //根据合同交货日期查询
                if (condition.getDeliveryDate() != null) {
                    list.add(cb.equal(root.get("deliveryDate").as(Date.class), NewDateUtil.getDate(condition.getDeliveryDate())));
                }
                //根据crm客户代码查询
                if (StringUtil.isNotBlank(condition.getCrmCode())) {
                    list.add(cb.like(root.get("crmCode").as(String.class), "%" + condition.getCrmCode() + "%"));
                }
                //根据框架协议号查询
                if (StringUtil.isNotBlank(condition.getFrameworkNo())) {
                    list.add(cb.like(root.get("frameworkNo").as(String.class), "%" + condition.getFrameworkNo() + "%"));
                }
                //根据订单类型
                if (condition.getOrderType() != null) {
                    list.add(cb.equal(root.get("orderType").as(Integer.class), condition.getOrderType()));
                }
                //根据汇款状态
                if (condition.getPayStatus() != null) {
                    list.add(cb.equal(root.get("payStatus").as(Integer.class), condition.getPayStatus()));
                }
                if (condition.getStatus() != null) {
                    list.add(cb.equal(root.get("status").as(Integer.class), condition.getStatus()));
                }
                //根据订单来源查询
                if (StringUtil.isNotBlank(condition.getOrderSource())) {
                    list.add(cb.like(root.get("orderSource").as(String.class), "%" + condition.getOrderSource() + "%"));
                }
                //根据区域所在国家查询
                String[] country = null;
                if (StringUtils.isNotBlank(condition.getCountry())) {
                    country = condition.getCountry().split(",");
                }
                if (condition.getType() == 1) {
                    if (country != null || condition.getCreateUserId() != null) {
                        list.add(cb.or(root.get("country").in(country), cb.equal(root.get("createUserId").as(Integer.class), condition.getCreateUserId())));
                    }
                    //根据市场经办人查询
                    if (condition.getAgentId() != null) {
                        list.add(cb.equal(root.get("agentId").as(String.class), condition.getAgentId()));
                    }
                } else if (condition.getType() == 2) {
                    //根据市场经办人查询
                    if (condition.getAgentId() != null || condition.getCreateUserId() != null) {
                        list.add(cb.or(cb.equal(root.get("agentId").as(String.class), condition.getAgentId()), cb.equal(root.get("createUserId").as(Integer.class), condition.getCreateUserId())));
                    }
                } else {
                    //根据市场经办人查询
                    if (condition.getAgentId() != null) {
                        list.add(cb.equal(root.get("agentId").as(String.class), condition.getAgentId()));
                    }
                    if (country != null) {
                        list.add(root.get("country").in(country));
                    }
                }
                list.add(cb.equal(root.get("deleteFlag"), false));
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, pageRequest);

        if (pageList.hasContent()) {
            pageList.getContent().forEach(vo -> {
                vo.setAttachmentSet(null);
                vo.setOrderPayments(null);
                if (vo.getDeliverConsignC() && vo.getStatus() == Order.StatusEnum.EXECUTING.getCode()) {
                    boolean flag = vo.getGoodsList().parallelStream().anyMatch(goods -> goods.getOutstockApplyNum() < goods.getContractGoodsNum());
                    vo.setDeliverConsignC(flag);
                } else {
                    vo.setDeliverConsignC(Boolean.FALSE);
                }
                if (deliverDetailService.findStatusAndNumber(vo.getId()) && vo.getDeliverConsignC() == false) {
                    vo.setOrderFinish(Boolean.TRUE);
                }
                vo.setGoodsList(null);
            });
        }
        return pageList;
    }

    @Override
    public Page<ComplexOrder> findByOutList(OutListCondition condition) {
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), new Sort(Sort.Direction.DESC, "id"));
        Page<ComplexOrder> pageList = complexOrderDao.findAll(new Specification<ComplexOrder>() {
            @Override
            public Predicate toPredicate(Root<ComplexOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //根据订单日期查询
                if (condition.getStart_time() != null && condition.getEnd_time() != null) {
                    Date startT = DateUtil.getOperationTime(condition.getStart_time(), 0, 0, 0);
                    Date endT = DateUtil.getOperationTime(condition.getEnd_time(), 23, 59, 59);
                    Predicate startTime = cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), startT);
                    Predicate endTime = cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), endT);
                    list.add(startTime);
                    list.add(endTime);
                }

                /*//根据crm客户代码查询
                if (StringUtil.isNotBlank(condition.getBuyer_no())) {
                    list.add(cb.equal(root.get("buyer_no").as(String.class), condition.getBuyer_no()));
                }*/
                //根据客户ID
                if (condition.getBuyer_id() != null) {
                    list.add(cb.equal(root.get("buyerId").as(Integer.class), condition.getBuyer_id()));
                }
                //根据付款状态
                if (!StringUtils.isEmpty(condition.getPay_status())) {
                    list.add(cb.equal(root.get("payStatus").as(Integer.class), ComplexOrder.fromPayMsg(condition.getPay_status())));
                }
                //根据订单状态
                if (!StringUtils.isEmpty(condition.getStatus())) {
                    if (!condition.getStatus().equals("to_be_confirmed")) {
                        list.add(cb.equal(root.get("status").as(Integer.class), ComplexOrder.fromStatusMsg(condition.getStatus())));
                    } else {
                        Integer[] orderStatus = {1, 2};
                        list.add(root.get("status").in(orderStatus));
                    }

                }
                //  list.add(cb.equal(root.get("deleteFlag"), false));
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, pageRequest);
    /*    if (pageList.hasContent()) {
            pageList.getContent().forEach(vo -> {
                vo.setAttachmentSet(null);
                vo.setOrderPayments(null);
                if (vo.getDeliverConsignC() && vo.getStatus() == Order.StatusEnum.EXECUTING.getCode()) {
                    boolean flag = vo.getGoodsList().parallelStream().anyMatch(goods -> goods.getOutstockApplyNum() < goods.getContractGoodsNum());
                    vo.setDeliverConsignC(flag);
                } else {
                    vo.setDeliverConsignC(Boolean.FALSE);
                }
                if (deliverDetailService.findStatusAndNumber(vo.getId()) && vo.getDeliverConsignC() == false) {
                    vo.setOrderFinish(Boolean.TRUE);
                }
                vo.setGoodsList(null);
            });
        }*/
        return pageList;
    }

    @Override
    @Transactional
    public void deleteOrder(Integer[] ids) {
        List<Order> orderList = orderDao.findByIdIn(ids);
        List<Order> collect = orderList.parallelStream()
                .filter(vo -> vo.getStatus() == 1)
                .map(vo -> {
                    vo.setDeleteFlag(true);
                    vo.setDeleteTime(new Date());
                    return vo;
                }).collect(Collectors.toList());
        orderDao.save(collect);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrder(AddOrderVo addOrderVo) throws Exception {
        Order order = orderDao.findOne(addOrderVo.getId());
        if (order == null) {
            return false;
        }
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            // 检查和贸易术语相关字段的完整性
            checkOrderTradeTermsRelationField(addOrderVo);
        }
        addOrderVo.copyBaseInfoTo(order);
        // 处理附件信息
        //  List<Attachment> attachments = attachmentService.handleParamAttachment(null, addOrderVo.getAttachDesc(), null, null);
        order.setAttachmentSet(addOrderVo.getAttachDesc());
        List<PGoods> pGoodsList = addOrderVo.getGoodDesc();
        Goods goods = null;
        List<Goods> goodsList = new ArrayList<>();
        Map<Integer, Goods> dbGoodsMap = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        for (PGoods pGoods : pGoodsList) {
            if (pGoods.getId() == null) {
                goods = new Goods();
                goods.setOrder(order);
            } else {
                goods = dbGoodsMap.remove(pGoods.getId());
                if (goods == null) {
                    throw new Exception("不存在的商品标识");
                }
            }
            //goods.setSeq(pGoods.getSeq());
            goods.setSku(pGoods.getSku());
            goods.setMeteType(pGoods.getMeteType());
            goods.setNameEn(pGoods.getNameEn());
            goods.setNameZh(pGoods.getNameZh());
            goods.setContractGoodsNum(pGoods.getContractGoodsNum());
            goods.setUnit(pGoods.getUnit());
            goods.setModel(pGoods.getModel());
            goods.setClientDesc(pGoods.getClientDesc());
            goods.setBrand(pGoods.getBrand());
            goods.setContractNo(order.getContractNo());
            goods.setPurchasedNum(0);
            goods.setPrePurchsedNum(0);
            goods.setInspectNum(0);
            goods.setInstockNum(0);
            goods.setOutstockApplyNum(0);
            goods.setExchanged(false);
            goods.setOutstockNum(0);
            goodsList.add(goods);
        }
        order.setGoodsList(goodsList);
        goodsDao.delete(dbGoodsMap.values());
        order.setOrderPayments(addOrderVo.getContractDesc());
        order.setDeleteFlag(false);
        Order orderUpdate = orderDao.saveAndFlush(order);
        Date signingDate = null;
        if (orderUpdate.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            signingDate = orderUpdate.getSigningDate();
        }
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            addLog(OrderLog.LogTypeEnum.CREATEORDER, orderUpdate.getId(), null, null, signingDate);
            Project projectAdd = new Project();
            projectAdd.setOrder(orderUpdate);
            projectAdd.setExecCoName(orderUpdate.getExecCoName());
            projectAdd.setContractNo(orderUpdate.getContractNo());
            projectAdd.setBusinessUid(orderUpdate.getTechnicalId());
            projectAdd.setExecCoName(orderUpdate.getExecCoName());
            projectAdd.setBusinessUnitName(orderUpdate.getBusinessUnitName());
            projectAdd.setRegion(orderUpdate.getRegion());
            projectAdd.setCountry(orderUpdate.getCountry());
            projectAdd.setDistributionDeptName(orderUpdate.getDistributionDeptName());
            projectAdd.setProjectStatus(Project.ProjectStatusEnum.SUBMIT.getCode());
            projectAdd.setPurchReqCreate(Project.PurchReqCreateEnum.NOT_CREATE.getCode());
            projectAdd.setPurchDone(Boolean.FALSE);
            projectAdd.setCreateTime(new Date());
            projectAdd.setUpdateTime(new Date());
            Project project2 = projectDao.save(projectAdd);
            // 设置商品的项目信息
            List<Goods> goodsList1 = orderUpdate.getGoodsList();
            goodsList1.parallelStream().forEach(goods1 -> {
                goods1.setProject(project2);
                goods1.setProjectNo(project2.getProjectNo());
            });
            goodsDao.save(goodsList1);
        }
        return true;
    }

    // 检查和贸易术语相关字段的完整性
    private void checkOrderTradeTermsRelationField(AddOrderVo addOrderVo) throws Exception {
        String tradeTerms = addOrderVo.getTradeTerms(); // 贸易术语
        String toCountry = addOrderVo.getToCountry(); // 目的国
        String transportType = addOrderVo.getTransportType(); // 运输方式
        String toPort = addOrderVo.getToPort(); // 目的港
        String toPlace = addOrderVo.getToPlace(); // 目的地
        if (StringUtils.isBlank(tradeTerms)) {
            throw new Exception("贸易术语不能为空");
        }
        if (StringUtils.isBlank(toCountry)) {
            throw new Exception("目的国不能为空");
        }
        switch (tradeTerms) {
            case "EXW":
            case "FCA":
                if (StringUtils.isBlank(transportType)) {
                    throw new Exception("运输方式不能为空");
                }
                break;
            case "CNF":
            case "CFR":
            case "CIF":
                if (StringUtils.isBlank(toPort)) {
                    throw new Exception("目的港不能为空");
                }
                break;
            case "CPT":
            case "CIP":
                if (StringUtils.isBlank(toPort)) {
                    throw new Exception("目的港不能为空");
                }
                if (StringUtils.isBlank(toPlace)) {
                    throw new Exception("目的地不能为空");
                }
                break;
            case "DAT":
            case "DAP":
            case "DDP":
                if (StringUtils.isBlank(toPlace)) {
                    throw new Exception("目的地不能为空");
                }
                break;
            /*
                case "FOB":
                case "FAS":
                    break;
                default:
                    throw new Exception("不存在的贸易术语");
            */
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addOrder(AddOrderVo addOrderVo) throws Exception {
        if (orderDao.countByContractNo(addOrderVo.getContractNo()) > 0) {
            throw new Exception("销售合同号已存在");
        }
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            // 检查和贸易术语相关字段的完整性
            checkOrderTradeTermsRelationField(addOrderVo);
        }
        Order order = new Order();
        addOrderVo.copyBaseInfoTo(order);
        order.setCreateUserId(addOrderVo.getCreateUserId());
        order.setCreateUserName(addOrderVo.getCreateUserName());
        order.setAttachmentSet(addOrderVo.getAttachDesc());
        List<PGoods> pGoodsList = addOrderVo.getGoodDesc();
        Goods goods = null;
        List<Goods> goodsList = new ArrayList<>();
        for (PGoods pGoods : pGoodsList) {
            goods = new Goods();
            //goods.setSeq(pGoods.getSeq());

            goods.setSku(pGoods.getSku());
            goods.setOutstockNum(0);
            goods.setMeteType(pGoods.getMeteType());
            goods.setNameEn(pGoods.getNameEn());
            goods.setNameZh(pGoods.getNameZh());
            goods.setContractGoodsNum(pGoods.getContractGoodsNum());
            goods.setUnit(pGoods.getUnit());
            goods.setModel(pGoods.getModel());
            goods.setClientDesc(pGoods.getClientDesc());
            goods.setBrand(pGoods.getBrand());
            goods.setContractNo(order.getContractNo());
            goods.setPurchasedNum(0);
            goods.setPrePurchsedNum(0);
            goods.setInstockNum(0);
            goods.setInspectNum(0);
            goods.setOutstockApplyNum(0);
            goods.setOutstockNum(0);
            goods.setExchanged(false);
            goodsList.add(goods);
        }
        order.setGoodsList(goodsList);
        order.setOrderPayments(addOrderVo.getContractDesc());
        order.setCreateTime(new Date());
        order.setDeleteFlag(false);
        Order order1 = orderDao.save(order);
        Date signingDate = null;
        if (order1.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            signingDate = order1.getSigningDate();
        }
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            addLog(OrderLog.LogTypeEnum.CREATEORDER, order1.getId(), null, null, signingDate);
            // 订单提交时推送项目信息
            Project project = new Project();
            //project.setProjectNo(UUID.randomUUID().toString());
            project.setOrder(order1);
            project.setContractNo(order1.getContractNo());
            project.setBusinessUid(order1.getTechnicalId());
            project.setExecCoName(order1.getExecCoName());
            project.setBusinessUnitName(order1.getBusinessUnitName());
            project.setDistributionDeptName(order1.getDistributionDeptName());
            project.setRegion(order1.getRegion());
            project.setCountry(order1.getCountry());
            project.setProjectStatus(Project.ProjectStatusEnum.SUBMIT.getCode());
            project.setPurchReqCreate(Project.PurchReqCreateEnum.NOT_CREATE.getCode());
            project.setPurchDone(Boolean.FALSE);
            project.setCreateTime(new Date());
            project.setUpdateTime(new Date());
            Project project2 = projectDao.save(project);
            // 设置商品的项目信息
            List<Goods> goodsList1 = order1.getGoodsList();
            goodsList1.parallelStream().forEach(goods1 -> {
                goods1.setProject(project2);
                goods1.setProjectNo(project2.getProjectNo());
            });
            goodsDao.save(goodsList1);
        }
        return true;
    }

    /**
     * @param logType 操作类型，见OrderLog.LogTypeEnum
     * @param orderId 订单ID
     * @param operato 可空，操作内容
     * @param goodsId 可空，商品ID
     */
    @Transactional
    public void addLog(OrderLog.LogTypeEnum logType, Integer orderId, String operato, Integer goodsId, Date signingDate) {
        OrderLog orderLog = new OrderLog();
        try {
            orderLog.setOrder(orderDao.findOne(orderId));
            orderLog.setLogType(logType.getCode());
            orderLog.setOperation(StringUtils.defaultIfBlank(operato, logType.getMsg()));
            orderLog.setCreateTime(new Date());
            orderLog.setBusinessDate(signingDate);  //订单签约日期
            orderLog.setOrdersGoodsId(goodsId);
            orderLogDao.save(orderLog);
        } catch (Exception ex) {
            logger.error("日志记录失败 {}", orderLog.toString());
            logger.error("错误", ex);
            ex.printStackTrace();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Order detail(Integer orderId) {
        Order order = orderDao.findOne(orderId);
        if (order != null) {
            order.getGoodsList().size();
        }
        return order;
    }

    /**
     * @Author:SHIGS
     * @Description订单日志
     * @Date:11:30 2018/1/20
     * @modified By
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderLog> orderLog(Integer orderId) {
        List<OrderLog> orderLog = orderLogDao.findByOrderIdOrderByCreateTimeAsc(orderId);
        if (orderLog == null) {
            orderLog = new ArrayList<>();
        }
        /*


        else {
            orderLog = orderLog.stream().filter(log -> {
                if (OrderLog.LogTypeEnum.OTHER.getCode() == log.getLogType()) {
                    return false;
                }
                return true;
            }).collect(Collectors.toList());
        }
        */
        return orderLog;


    }

    /**
     * @Author:SHIGS
     * @Description 订单商品已发货完成后改为不可再次生成出口发货单标识
     * @Date:11:29 2018/2/1
     * @modified By
     */
    @Override
    public void updateOrderDeliverConsignC(Set<Integer> orderId) {
        if (orderId != null && orderId.size() > 0) {
            List<Order> orderList = new ArrayList<>();
            for (Integer id : orderId) {
                Order order = orderDao.findOne(id);
                boolean flag = order.getGoodsList().parallelStream().allMatch(vo -> vo.getContractGoodsNum() == vo.getOutstockNum());
                if (flag) {
                    order.setDeliverConsignC(Boolean.FALSE);
                    orderList.add(order);
                }
            }
            orderDao.save(orderList);
        }
    }

    @Override
    public boolean orderFinish(Order order) {
        Order order1 = orderDao.findOne(order.getId());
        if (order1 != null) {
            order1.setStatus(order.getStatus());
            orderDao.save(order1);
            addLog(OrderLog.LogTypeEnum.DELIVERYDONE, order1.getId(), null, null, new Date());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> findByIdOut(Integer id) {
        Order order = orderDao.findOne(id);
        List<OrderLog> logList = orderLogDao.findByOrderIdOrderByCreateTimeAsc(id);
        ArrayList<Object> logs = new ArrayList<>();
        for (OrderLog orderLog : logList) {
            OrderLog orderLog2 = new OrderLog();
            switch (orderLog.getOperation()) {
                case "创建订单":
                    orderLog2.setOperation("Create Order");
                    orderLog2.setCreateTime(orderLog.getCreateTime());
                    break;
                case "收到预付款":
                    orderLog2.setOperation("Receive advance payment");
                    orderLog2.setCreateTime(orderLog.getCreateTime());
                    break;
                case "商品出库":
                    orderLog2.setOperation("Goods export");
                    orderLog2.setCreateTime(orderLog.getCreateTime());
                    break;
                case "已收货":
                    orderLog2.setOperation("received");
                    orderLog2.setCreateTime(orderLog.getCreateTime());
                    break;
                case "全部交收完成":
                    orderLog2.setOperation("Completed delivery");
                    orderLog2.setCreateTime(orderLog.getCreateTime());
                    break;
                case "订单签约":
                    orderLog2.setOperation("Signing order");
                    orderLog2.setCreateTime(orderLog.getCreateTime());
                    break;
                case "其他":
                    orderLog2.setOperation("Other");
                    orderLog2.setCreateTime(orderLog.getCreateTime());
                    break;

            }
            logs.add(orderLog2);
        }
        OutOrderDetail outOrderDetail = null;
        List<OutGoods> goodList = new ArrayList<>();
        HashMap<String, Object> resultMap = null;
        if (order != null) {
            if (order.getDeliverConsignC() && order.getStatus() == Order.StatusEnum.EXECUTING.getCode()) {
                boolean flag = order.getGoodsList().parallelStream().anyMatch(vo -> vo.getOutstockApplyNum() < vo.getContractGoodsNum());
                order.setDeliverConsignC(flag);
            } else {
                order.setDeliverConsignC(Boolean.FALSE);
            }
           /* order.getGoodsList().size();
            order.getAttachmentSet().size();
            order.getOrderPayments().size();*/
            outOrderDetail = new OutOrderDetail();
            outOrderDetail.copyInfo(order);
            for (Goods goods : order.getGoodsList()) {
                OutGoods outGoods = new OutGoods();
                outGoods.copyInfo(goods);
                outGoods.setBuyer_id(null);
                goodList.add(outGoods);
            }
            resultMap = new HashMap<>();
            resultMap.put("orderinfo", outOrderDetail);
            resultMap.put("ordergoods", goodList);
            resultMap.put("orderlogs", logs);
            resultMap.put("order_no", order.getContractNo());
            resultMap.put("orderaddress", null);
            resultMap.put("orderpayments", null);
            resultMap.put("imgprefix", null);

        }
        return resultMap;
    }
}
