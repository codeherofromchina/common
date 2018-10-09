package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.ChineseAndEnglish;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.requestVo.*;
import com.erui.order.service.*;
import com.erui.order.util.excel.ExcelUploadTypeEnum;
import com.erui.order.util.excel.ImportDataResponse;
import com.erui.order.util.exception.MyException;
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
public class OrderServiceImpl implements OrderService {
    // 用户升级方法
    static final String CRM_URL_METHOD = "/buyer/autoUpgrade";
    static final BigDecimal STEP_ONE_PRICE = new BigDecimal("100000");
    static final BigDecimal STEP_TWO_PRICE = new BigDecimal("3000000");
    static final BigDecimal STEP_THREE_PRICE = new BigDecimal("10000000");
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    OrderLogDao orderLogDao;
    @Autowired
    private DeptService deptService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectProfitDao projectProfitDao;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private IogisticsDataService iogisticsDataService;

    @Autowired
    private DeliverConsignService deliverConsignService;

    @Autowired
    private BackLogService backLogService;
    @Autowired
    private CheckLogService checkLogService;
    @Autowired
    private CheckLogDao checkLogDao;

    @Value("#{orderProp[CRM_URL]}")
    private String crmUrl;  //CRM接口地址


    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Autowired
    private ComplexOrderDao complexOrderDao;

    @Override
    @Transactional(readOnly = true)
    public Order findById(Integer id) {
        Order order = orderDao.findOne(id);
        if (order != null) {
            order.getGoodsList().size();
            order.getAttachmentSet().size();
            order.getOrderPayments().size();
        }
        return order;
    }


    @Override
    @Transactional(readOnly = true)
    public Order findByIdLang(Integer id, String lang) {
        Order order = orderDao.findOne(id);
        if (order != null) {
            Integer size = order.getGoodsList().size();
            if (size > 0) {
                List<Goods> goodsList = order.getGoodsList();
                for (Goods goods : goodsList) {
                    goods.setPurchGoods(null);
                }
            }
            order.getAttachmentSet().size();
            order.getOrderPayments().size();
            // 获取执行分公司、分销部
            Integer execCoId = order.getExecCoId();
            if (execCoId != null) {
                Company company = companyService.findByIdLazy(execCoId);
                if ("en".equals(lang)) {
                    order.setExecCoName(company.getEnName());
                } else {
                    order.setExecCoName(company.getName());
                }
            }
            String distributionDeptName = getDeptNameByLang(lang, order.getDistributionDeptName());
            order.setDistributionDeptName(distributionDeptName);
        }

        // and  处理授信数据信息
       /* BigDecimal currencyBnShipmentsMoney =  order.getShipmentsMoney() == null ? BigDecimal.valueOf(0) : order.getShipmentsMoney();  //已发货总金额 （财务管理
        BigDecimal currencyBnAlreadyGatheringMoney = order.getAlreadyGatheringMoney() == null ? BigDecimal.valueOf(0) : order.getAlreadyGatheringMoney();//已收款总金额

        //收款总金额  -  发货总金额
        BigDecimal subtract = currencyBnAlreadyGatheringMoney.subtract(currencyBnShipmentsMoney);
        if(subtract.compareTo(BigDecimal.valueOf(0)) != -1 ){    //-1 小于     0 等于      1 大于
            order.setAdvanceMoney(subtract);     //预收金额
        }else {
            order.setAdvanceMoney(BigDecimal.valueOf(0));     //预收金额
        }*/

        try {
            DeliverConsign deliverConsign1 = deliverConsignService.queryCreditData(order);
            if (deliverConsign1 != null) {
                order.setLineOfCredit(deliverConsign1.getLineOfCredit()); //授信额度
                order.setCreditAvailable(deliverConsign1.getCreditAvailable()); //可用授信额度
            } else {
                order.setLineOfCredit(BigDecimal.valueOf(0.00)); //授信额度
                order.setCreditAvailable(BigDecimal.valueOf(0.00)); //可用授信额度
            }
        } catch (Exception e) {
            logger.info("CRM返回信息：" + e);
            order.setLineOfCredit(BigDecimal.valueOf(0.00)); //授信额度
            order.setCreditAvailable(BigDecimal.valueOf(0.00)); //可用授信额度
        }
        //end

        return order;
    }

    @Transactional
    @Override
    public Page<Order> findByPage(OrderListCondition condition) {
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), new Sort(Sort.Direction.DESC, "id"));
        Page<Order> pageList = orderDao.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据销售同号模糊查询
                if (StringUtil.isNotBlank(condition.getContractNo())) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                // 根据订单审核状态
                if (condition.getAuditingProcess() != null) {
                    list.add(cb.equal(root.get("auditingProcess").as(String.class), condition.getAuditingProcess()));
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
                /*if (condition.getSigningDate() != null) {
                    list.add(cb.equal(root.get("signingDate").as(Date.class), NewDateUtil.getDate(condition.getSigningDate())));
                }*/
                //根据订单签订时间段查询 开始
                if (condition.getStartTime() != null) {
                    Date startT = DateUtil.getOperationTime(condition.getStartTime(), 0, 0, 0);
                    Predicate startTime = cb.greaterThanOrEqualTo(root.get("signingDate").as(Date.class), startT);
                    list.add(startTime);
                }
                //根据订单签订时间段查询 结束
                if (condition.getEndTime() != null) {
                    Date endT = DateUtil.getOperationTime(condition.getEndTime(), 23, 59, 59);
                    Predicate endTime = cb.lessThanOrEqualTo(root.get("signingDate").as(Date.class), endT);
                    list.add(endTime);
                }
                //根据合同交货日期查询
                if (StringUtil.isNotBlank(condition.getDeliveryDate())) {
                    list.add(cb.like(root.get("deliveryDate").as(String.class), "%" + condition.getDeliveryDate() + "%"));
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
                //根据项目号
                if (StringUtil.isNotBlank(condition.getProjectNo())) {
                    list.add(cb.like(root.get("projectNo").as(String.class), "%" + condition.getProjectNo() + "%"));
                }
                //根据流程进度
                if (StringUtil.isNotBlank(condition.getProcessProgress())) {
                    if (StringUtils.equals("1", condition.getProcessProgress())) {
                        list.add(cb.equal(root.get("processProgress").as(String.class), condition.getProcessProgress()));
                    } else {
                        list.add(cb.greaterThanOrEqualTo(root.get("processProgress").as(String.class), condition.getProcessProgress()));
                    }
                }
                //根据是否已生成出口通知单
                if (condition.getDeliverConsignHas() != null) {
                    list.add(cb.equal(root.get("deliverConsignHas").as(Integer.class), condition.getDeliverConsignHas()));
                }
                //商务技术经办人
                if (condition.getTechnicalId02() != null) {
                    list.add(cb.equal(root.get("technicalId").as(Integer.class), condition.getTechnicalId02()));
                }
                //事业部
                if (StringUtils.isNotBlank(condition.getBusinessUnitId02())) {
                    list.add(cb.equal(root.get("businessUnitId").as(String.class), condition.getBusinessUnitId02()));
                }
                //根据区域所在国家查询
               /* String[] country = null;
                if (StringUtils.isNotBlank(condition.getCountry())) {
                    country = condition.getCountry().split(",");
                }*/
                //根据事业部
                String[] bid = null;
                if (StringUtils.isNotBlank(condition.getBusinessUnitId())) {
                    bid = condition.getBusinessUnitId().split(",");
                }
                /*if (bid != null) {
                    list.add(root.get("businessUnitId").in(bid));
                }*/
                if (condition.getType() == 1) {
                    Predicate createUserId = null;
                    if (condition.getCreateUserId() != null) {
                        createUserId = cb.equal(root.get("createUserId").as(Integer.class), condition.getCreateUserId());
                    }
                    Predicate businessUnitId = null;
                    if (bid != null) {
                        businessUnitId = root.get("businessUnitId").in(bid);
                    }
                    Predicate technicalId = null;
                    if (condition.getTechnicalId() != null) {
                        technicalId = cb.equal(root.get("technicalId").as(Integer.class), condition.getTechnicalId());
                    }
                    //根据市场经办人查询
                    if (condition.getAgentId() != null) {
                        list.add(cb.equal(root.get("agentId").as(String.class), condition.getAgentId()));
                    }
                    Predicate and = cb.and(businessUnitId, technicalId);
                    if (businessUnitId != null && technicalId != null) {
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            if (audiRemark != null) {
                                list.add(cb.or(and, createUserId, auditingUserId, perLiableRepayId, audiRemark));
                            }
                            list.add(cb.or(and, createUserId, auditingUserId, perLiableRepayId));
                        } else {
                            list.add(cb.or(and, createUserId));
                        }
                    } else if (businessUnitId != null && technicalId == null) {
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            if (audiRemark != null) {
                                list.add(cb.or(businessUnitId, createUserId, auditingUserId, perLiableRepayId, audiRemark));
                            } else {
                                list.add(cb.or(businessUnitId, createUserId, auditingUserId, perLiableRepayId));
                            }
                        } else {
                            list.add(cb.or(businessUnitId, createUserId));
                        }
                    } else if (technicalId != null && businessUnitId == null) {
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            if (audiRemark != null) {
                                list.add(cb.or(technicalId, createUserId, auditingUserId, perLiableRepayId, audiRemark));
                            } else {
                                list.add(cb.or(technicalId, createUserId, auditingUserId, perLiableRepayId));
                            }
                        }
                        list.add(cb.or(technicalId, createUserId));
                    }
                } else {
                    //根据市场经办人查询
                    if (condition.getAgentId() != null && condition.getCreateUserId() != null) {
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            if (audiRemark != null) {
                                list.add(cb.or(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()), cb.equal(root.get("createUserId").as(Integer.class),
                                        condition.getCreateUserId()), auditingUserId, perLiableRepayId, audiRemark));
                            } else {
                                list.add(cb.or(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()), cb.equal(root.get("createUserId").as(Integer.class),
                                        condition.getCreateUserId()), auditingUserId, perLiableRepayId));
                            }
                        } else {
                            list.add(cb.or(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()), cb.equal(root.get("createUserId").as(Integer.class),
                                    condition.getCreateUserId())));
                        }
                    } else if (condition.getAgentId() == null && condition.getCreateUserId() != null) {
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            if (audiRemark != null) {
                                list.add(cb.or(cb.equal(root.get("createUserId").as(Integer.class), condition.getCreateUserId()), auditingUserId, perLiableRepayId, audiRemark));
                            } else {
                                list.add(cb.or(cb.equal(root.get("createUserId").as(Integer.class), condition.getCreateUserId()), auditingUserId, perLiableRepayId));
                            }
                        } else {
                            list.add(cb.or(cb.equal(root.get("createUserId").as(Integer.class),
                                    condition.getCreateUserId())));
                        }
                    }
                } /*else {
                    //根据市场经办人查询
                    if (condition.getAgentId() != null && condition.getCreateUserId() != null) {
                        list.add(cb.or(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()), cb.equal(root.get("createUserId").as(Integer.class),
                                condition.getCreateUserId())));
                    } else if (condition.getAgentId() == null && condition.getCreateUserId() != null) {
                        list.add(cb.equal(root.get("createUserId").as(Integer.class), condition.getCreateUserId()));
                    }
                   *//* if (country != null) {
                        list.add(root.get("country").in(country));
                    }*//*
                }*/
                list.add(cb.equal(root.get("deleteFlag"), false));
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                /*Predicate and = cb.and(predicates);
                // 审核人查询,和其他关系是or，所有写在最后
                if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                    Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                    Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                    Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                    Predicate or = cb.or(auditingUserId, perLiableRepayId, audiRemark);
                    return cb.and(and, or);
                } else {
                    return and;
                }*/
                return cb.and(predicates);
            }
        }, pageRequest);
        if (pageList.hasContent()) {
            pageList.getContent().forEach(vo -> {
                //vo.setAttachmentSet(null);
                if (vo.getDeliverConsignC() && vo.getStatus() == Order.StatusEnum.EXECUTING.getCode()) {
                    boolean flag;
                    if (vo.getGoodsList() != null || vo.getGoodsList().size() > 0) {
                        flag = vo.getGoodsList().parallelStream().anyMatch(goods -> goods.getOutstockApplyNum() < goods.getContractGoodsNum());
                    } else {
                        flag = false;
                    }
                    if (flag) {

                        vo.setDeliverConsignC(flag);
                    } else {
                        vo.setDeliverConsignC(Boolean.FALSE);
                    }
                }
                //vo.setOrderPayments(null);
                if (vo.getDeliverConsignC() == false && iogisticsDataService.findStatusAndNumber(vo.getId())) {
                    vo.setOrderFinish(Boolean.TRUE);
                }
                // vo.setGoodsList(null);
            });
        }
        return pageList;
    }


    private String getDeptNameByLang(String lang, String deptName) {
        Dept dept = null;
        if (StringUtils.isNotBlank(lang)) {
            if ("en".equals(lang) && ChineseAndEnglish.isChinese(deptName)) {
                dept = deptService.findTop1ByName(deptName);
                if (dept != null) {
                    return dept.getEnName();
                }
            } else if ("zh".equals(lang) && !ChineseAndEnglish.isChinese(deptName)) {
                dept = deptService.findTop1ByEnName(deptName);
                if (dept != null) {


                    return dept.getName();
                }
            }
        }
        return deptName;
    }

    @Override
    public Page<ComplexOrder> findByOutList(OutListCondition condition) {
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), new Sort(Sort.Direction.DESC, "id"));
        try {
            Page<ComplexOrder> pageList = complexOrderDao.findAll(new Specification<ComplexOrder>() {
                @Override
                public Predicate toPredicate(Root<ComplexOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();
                    //根据订单日期查询
                    if (condition.getStart_time() != null) {
                        Date startT = DateUtil.getOperationTime(condition.getStart_time(), 0, 0, 0);
                        Predicate startTime = cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), startT);
                        list.add(startTime);
                    }
                    if (condition.getStart_time() != null || condition.getEnd_time() != null) {
                        Date endT = DateUtil.getOperationTime(condition.getEnd_time(), 23, 59, 59);
                        Predicate endTime = cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), endT);
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
            return pageList;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return null;
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

    //确认检测销售合同号
    @Override
    public Integer checkContractNo(String contractNo, Integer id) {
        Order order = null;
        int flag = 1;
        if (id != null && id != 0) {
            order = orderDao.findOne(id);
        }
        if (order != null && contractNo.equals(order.getContractNo())) {
            if (!StringUtils.isBlank(contractNo) && orderDao.countByContractNo(contractNo) <= 1) {
                flag = 0;
            } else {
                flag = 1;
            }
        } else {
            if (!StringUtils.isBlank(contractNo) && orderDao.countByContractNo(contractNo) > 0) {
                flag = 1;
            } else {
                flag = 0;
            }
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean audit(Order order, String auditorId, String auditorName, AddOrderVo addOrderVo) throws Exception {
        // rejectFlag true:驳回项目   false:审核项目
        // reason
        StringBuilder auditorIds = null;
        if (order.getAudiRemark() != null) {
            auditorIds = new StringBuilder(order.getAudiRemark());
        } else {
            auditorIds = new StringBuilder("");
        }
        boolean rejectFlag = "-1".equals(addOrderVo.getAuditingType());
        String reason = addOrderVo.getAuditingReason();
        // 获取当前审核进度
        Integer auditingProcess = order.getAuditingProcess();
        String auditingUserId = order.getAuditingUserId();
        Integer curAuditProcess = null;
        if (auditorId.equals(auditingUserId)) {
            curAuditProcess = auditingProcess;
        }
        // 定义最后处理结果变量，最后统一操作
        Integer auditingStatus_i = 2; // 默认状态为审核中
        String auditingProcess_i = null; // 订单审核当前进度
        String auditingUserId_i = null; // 订单审核当前人
        CheckLog checkLog_i = null; // 审核日志
        if (rejectFlag) { // 如果是驳回，则
            // 直接记录日志，修改审核进度
            CheckLog checkLog = checkLogDao.findOne(addOrderVo.getCheckLogId());
            auditingStatus_i = 3;//驳回状态
            auditingProcess_i = checkLog.getAuditingProcess().toString(); //驳回给哪一步骤
            auditingUserId_i = String.valueOf(checkLog.getAuditingUserId());//要驳回给谁
            auditorIds.append("," + auditingUserId_i + ",");
            // 驳回的日志记录的下一处理流程和节点是当前要处理的节点信息
            checkLog_i = fullCheckLogInfo(order.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, order.getAuditingProcess().toString(), order.getAuditingUserId(), reason, "-1", 1);
            if (auditingProcess_i.equals("0")) {
                order.setStatus(1);
            }
        } else {
            // 判断是驳回处理，还是正常核算，查找最近一条日志，看是否是驳回日志
            CheckLog checkLog = checkLogService.findLastLog(1, order.getId());
            switch (curAuditProcess) {
                case 0:
                    if (checkLog != null && "-1".equals(checkLog.getOperation())) { // 驳回后的处理
                        // 添加从项目驳回过来的信息
                        if (checkLog.getType() == 2) {
                            Project project = order.getProject();
                            project.setAuditingStatus(2); // 设置项目为审核中
                            project.setAuditingUserId(checkLog.getNextAuditingUserId());
                            project.setAuditingProcess(checkLog.getNextAuditingProcess());

                            auditingStatus_i = 4; // 完成
                            auditingProcess_i = null; // 无下一审核进度和审核人
                            auditingUserId_i = null;
                        } else {
                            auditingProcess_i = checkLog.getNextAuditingProcess();
                            auditingUserId_i = checkLog.getNextAuditingUserId();
                            auditorIds.append("," + auditingUserId_i + ",");
                            addOrderVo.copyBaseInfoTo(order);
                        }
                    } else {
                        auditingProcess_i = "1";
                        auditingUserId_i = order.getCountryLeaderId().toString();
                        auditorIds.append("," + auditingUserId_i + ",");
                    }
                    break;
                case 1:
                    if (checkLog != null && "-1".equals(checkLog.getOperation())) { // 驳回后的处理
                        auditingProcess_i = checkLog.getNextAuditingProcess();
                        auditingUserId_i = checkLog.getNextAuditingUserId();
                        auditorIds.append("," + auditingUserId_i + ",");
                        addOrderVo.copyBaseInfoTo(order);
                    } else {
                        auditingProcess_i = "2";
                        auditingUserId_i = order.getCountryLeaderId().toString();
                        auditorIds.append("," + auditingUserId_i + ",");
                    }
                    break;
                //国家负责人审批
                case 2:
                    if (checkLog != null && "-1".equals(checkLog.getOperation())) { // 驳回后的处理
                        auditingProcess_i = checkLog.getNextAuditingProcess();
                        auditingUserId_i = checkLog.getNextAuditingUserId();
                        auditorIds.append("," + auditingUserId_i + ",");
                        addOrderVo.copyBaseInfoTo(order);
                    } else {
                        //根据订单金额判断 填写审批人级别
                        if (order.getTotalPriceUsd().doubleValue() < STEP_ONE_PRICE.doubleValue()) {
                            if (order.getFinancing() == null || order.getFinancing() == 0) {
                                //若不是融资项目 且订单金额小于10万美元 提交至商品添加
                                auditingProcess_i = "6";
                                auditingUserId_i = order.getTechnicalId().toString();//提交到商务技术经办人
                                auditorIds.append("," + auditingUserId_i + ",");
                            } else if (order.getFinancing() == 1) {
                                //若是融资项目 且订单金额小于10万美元 提交由融资专员审核
                                auditingProcess_i = "5"; // 融资审核
                                auditingUserId_i = order.getFinancingCommissionerId().toString();
                                auditorIds.append("," + auditingUserId_i + ",");
                            }
                        } else {
                            //订单金额大于10万小于300万 交给区域负责人审核
                            auditingProcess_i = "3";
                            if (order.getAreaLeaderId() != null)
                                auditingUserId_i = order.getAreaLeaderId().toString();
                            auditorIds.append("," + auditingUserId_i + ",");
                        }
                    }
                    break;

                //区域负责人
                case 3:
                    if (checkLog != null && "-1".equals(checkLog.getOperation())) { // 驳回后的处理
                        auditingProcess_i = checkLog.getNextAuditingProcess();
                        auditingUserId_i = checkLog.getNextAuditingUserId();
                        auditorIds.append("," + auditingUserId_i + ",");
                        addOrderVo.copyBaseInfoTo(order);
                    } else {
                        if (STEP_ONE_PRICE.doubleValue() <= order.getTotalPriceUsd().doubleValue() && order.getTotalPriceUsd().doubleValue() < STEP_TWO_PRICE.doubleValue()) {
                            if (order.getFinancing() == null || order.getFinancing() == 0) {
                                //若不是融资项目 且订单金额小于10万-300万美元 提交至商品添加
                                auditingProcess_i = "6";
                                auditingUserId_i = order.getTechnicalId().toString();//提交到商务技术经办人
                                auditorIds.append("," + auditingUserId_i + ",");
                            } else if (addOrderVo.getFinancing() == 1) {
                                //若是融资项目 且订单金额小于10万美元 提交由融资专员审核
                                auditingProcess_i = "5"; // 融资审核
                                auditingUserId_i = order.getFinancingCommissionerId().toString();
                                auditorIds.append("," + auditingUserId_i + ",");
                            }
                        } else {
                            //订单金额大于300万 交给区域VP审核
                            auditingProcess_i = "4";
                            if (order.getAreaVpId() != null)
                            if (order.getAreaVpId() != null)
                                auditingUserId_i = order.getAreaVpId().toString();
                            auditorIds.append("," + auditingUserId_i + ",");
                        }
                    }
                    break;
                //区域VP
                case 4:
                    if (checkLog != null && "-1".equals(checkLog.getOperation())) { // 驳回后的处理
                        auditingProcess_i = checkLog.getNextAuditingProcess();
                        auditingUserId_i = checkLog.getNextAuditingUserId();
                        auditorIds.append("," + auditingUserId_i + ",");
                        addOrderVo.copyBaseInfoTo(order);
                    } else {
                        if (order.getFinancing() == null || order.getFinancing() == 0) {
                            //若不是融资项目 且订单金额大于1000万美元 提交至商品添加
                            auditingProcess_i = "6";
                            auditingUserId_i = order.getTechnicalId().toString();//提交到商务技术经办人
                            auditorIds.append("," + auditingUserId_i + ",");
                        } else if (order.getFinancing() == 1) {
                            //若是融资项目 且订单金额小于10万美元 提交由融资专员审核
                            auditingProcess_i = "5"; // 融资审核
                            auditingUserId_i = order.getFinancingCommissionerId().toString();//郭永涛
                            auditorIds.append("," + auditingUserId_i + ",");
                        }
                    }
                    break;
                //是否融资项目 是 融资审核
                case 5:
                    if (checkLog != null && "-1".equals(checkLog.getOperation())) { // 驳回后的处理
                        auditingProcess_i = checkLog.getNextAuditingProcess();
                        auditingUserId_i = checkLog.getNextAuditingUserId();
                        auditorIds.append("," + auditingUserId_i + ",");
                        addOrderVo.copyBaseInfoTo(order);
                    } else {
                        //若不是融资项目 且订单金额大于1000万美元 提交至商品添加
                        auditingProcess_i = "6";
                        auditingUserId_i = order.getTechnicalId().toString();//提交到商务技术经办人
                        auditorIds.append("," + auditingUserId_i + ",");
                    }
                    break;
                //提交商品
                case 6:
                    order.setGoodsList(updateOrderGoods(addOrderVo));
                    order.setLogiQuoteNo(addOrderVo.getLogiQuoteNo());
                    auditingStatus_i = 4; // 完成
                    auditingProcess_i = null; // 无下一审核进度和审核人
                    auditingUserId_i = null;
                    break;
                default:
                    return false;
            }
            checkLog_i = fullCheckLogInfo(order.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, auditingProcess_i, auditingUserId_i, reason, "2", 1);
        }
        checkLogService.insert(checkLog_i);
        if (auditingProcess_i != null) {
            order.setAuditingProcess(Integer.parseInt(auditingProcess_i));
        } else {
            order.setAuditingProcess(null);
        }
        order.setAuditingUserId(auditingUserId_i);
        order.setAuditingStatus(auditingStatus_i);
        order.setAudiRemark(auditorIds.toString());
        orderDao.save(order);
        return true;
    }

    // 处理日志
    private CheckLog fullCheckLogInfo(Integer orderId, Integer auditingProcess, Integer auditorId, String auditorName, String nextAuditingProcess, String nextAuditingUserId,
                                      String auditingMsg, String operation, int type) {
        CheckLog checkLog = new CheckLog();
        checkLog.setOrderId(orderId);
        checkLog.setCreateTime(new Date());
        checkLog.setAuditingProcess(auditingProcess);
        checkLog.setAuditingUserId(auditorId);
        checkLog.setAuditingUserName(auditorName);
        checkLog.setNextAuditingProcess(nextAuditingProcess);
        checkLog.setNextAuditingUserId(nextAuditingUserId);
        checkLog.setAuditingMsg(auditingMsg);
        checkLog.setOperation(operation);
        checkLog.setType(type);
        return checkLog;
    }

    /*    @Override
        @Transactional(rollbackFor = Exception.class)
        public Integer updateOrder(AddOrderVo addOrderVo) throws Exception {
            Order order = orderDao.findOne(addOrderVo.getId());
            if (order == null) {
                return null;
            }
            if (!order.getContractNo().equals(addOrderVo.getContractNo())) {
                if (!StringUtils.equals("", addOrderVo.getContractNo()) && orderDao.countByContractNo(addOrderVo.getContractNo()) > 0) {
                    throw new MyException("销售合同号已存在&&The order No. already exists");
                }
            } else {
                if (!StringUtils.equals("", addOrderVo.getContractNo()) && orderDao.countByContractNo(addOrderVo.getContractNo()) > 1) {
                    throw new MyException("销售合同号已存在&&The order No. already exists");
                }
            }
         *//*   if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            // 检查和贸易术语相关字段的完整性
            checkOrderTradeTermsRelationField(addOrderVo);
        }*//*
        addOrderVo.copyBaseInfoTo(order);
        if (new Integer(3).equals(addOrderVo.getOverseasSales())) {
            order.setContractNo(addOrderVo.getContractNoOs());
        } else {
            order.setContractNo(addOrderVo.getContractNo());
        }
        // 处理附件信息
        //  List<Attachment> attachments = attachmentService.handleParamAttachment(null, addOrderVo.getAttachDesc(), null, null);
        order.setAttachmentSet(addOrderVo.getAttachDesc());
        List<PGoods> pGoodsList = addOrderVo.getGoodDesc();
        Goods goods = null;
        List<Goods> goodsList = new ArrayList<>();
        Map<Integer, Goods> dbGoodsMap = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        Set<String> skuRepeatSet = new HashSet<>();

        for (PGoods pGoods : pGoodsList) {
            if (pGoods.getId() == null) {
                goods = new Goods();
                goods.setOrder(order);
            } else {
                goods = dbGoodsMap.remove(pGoods.getId());
                if (goods == null) {
                    throw new MyException("不存在的商品标识&&Non-existent product identifier");
                }
            }
            String sku = pGoods.getSku();
            if (StringUtils.isNotBlank(sku) && !skuRepeatSet.add(sku)) {
                // 已经存在的sku，返回错误
                throw new MyException("同一sku不可以重复添加&&The same sku can not be added repeatedly");
            }
            goods.setSku(sku);
            goods.setMeteType(pGoods.getMeteType());
            goods.setMeteName(pGoods.getMeteName());
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
            goods.setDepartment(pGoods.getDepartment());
            goods.setPrice(pGoods.getPrice());
            goodsList.add(goods);
        }
        order.setGoodsList(goodsList);
        goodsDao.delete(dbGoodsMap.values());
        order.setOrderPayments(addOrderVo.getContractDesc());
        order.setDeleteFlag(false);
        //根据订单金额判断 填写审批人级别
        if (addOrderVo.getTotalPriceUsd().doubleValue() < STEP_ONE_PRICE.doubleValue()) {
            order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
            order.setCountryLeader(addOrderVo.getCountryLeader());
        } else if (STEP_ONE_PRICE.doubleValue() <= addOrderVo.getTotalPriceUsd().doubleValue() && addOrderVo.getTotalPriceUsd().doubleValue() < STEP_TWO_PRICE.doubleValue()) {
            order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
            order.setCountryLeader(addOrderVo.getCountryLeader());
            order.setAreaLeaderId(addOrderVo.getAreaLeaderId());
            order.setAreaLeader(addOrderVo.getAreaLeader());
        } else if (addOrderVo.getTotalPriceUsd().doubleValue() >= STEP_THREE_PRICE.doubleValue()) {
            order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
            order.setCountryLeader(addOrderVo.getCountryLeader());
            order.setAreaLeaderId(addOrderVo.getAreaLeaderId());
            order.setAreaLeader(addOrderVo.getAreaLeader());
            order.setAreaVpId(addOrderVo.getAreaVpId());
            order.setAreaVp(addOrderVo.getAreaVp());
        }
        order.setAuditingProcess(1);
        order.setFinancingCommissionerId(39535);
        if (addOrderVo.getStatus() == Order.StatusEnum.INIT.getCode()) {
            order.setAuditingStatus(1);
        } else if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            order.setAuditingStatus(2);
            order.setAuditingUserId(addOrderVo.getPerLiableRepayId().toString());
        }
        CheckLog checkLog_i = null; // 审核日志
        Order orderUpdate = orderDao.saveAndFlush(order);
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            checkLog_i = fullCheckLogInfo(order.getId(), 0, orderUpdate.getCreateUserId(), orderUpdate.getCreateUserName(), orderUpdate.getAuditingProcess().toString(), orderUpdate.getPerLiableRepayId().toString(), null, "1", 1);
            checkLogService.insert(checkLog_i);
        }
        Date signingDate = null;
        if (orderUpdate.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            signingDate = orderUpdate.getSigningDate();
        }
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            List<OrderLog> orderLog = orderLogDao.findByOrderIdOrderByCreateTimeAsc(orderUpdate.getId());
            if (orderLog.size() > 0) {
                Map<String, OrderLog> collect = orderLog.stream().collect(Collectors.toMap(vo -> vo.getLogType().toString(), vo -> vo));
                if (collect.containsKey("1")) {
                    orderLogDao.delete(collect.get("1").getId());
                }
            }
            addLog(OrderLog.LogTypeEnum.CREATEORDER, orderUpdate.getId(), null, null, signingDate);
            applicationContext.publishEvent(new OrderProgressEvent(orderUpdate, 1));
            Project projectAdd = new Project();
            projectAdd.setOrder(orderUpdate);
            projectAdd.setExecCoName(orderUpdate.getExecCoName());
            projectAdd.setContractNo(orderUpdate.getContractNo());
            projectAdd.setBusinessUid(orderUpdate.getTechnicalId());
            projectAdd.setExecCoName(orderUpdate.getExecCoName());
            projectAdd.setBusinessUnitName(orderUpdate.getBusinessUnitName());
            projectAdd.setSendDeptId(orderUpdate.getBusinessUnitId());
            projectAdd.setRegion(orderUpdate.getRegion());
            projectAdd.setCountry(orderUpdate.getCountry());
            projectAdd.setTotalPriceUsd(orderUpdate.getTotalPriceUsd());
            projectAdd.setDistributionDeptName(orderUpdate.getDistributionDeptName());
            projectAdd.setProjectStatus(Project.ProjectStatusEnum.SUBMIT.getCode());
            projectAdd.setPurchReqCreate(Project.PurchReqCreateEnum.NOT_CREATE.getCode());
            projectAdd.setOrderCategory(orderUpdate.getOrderCategory());
            projectAdd.setOverseasSales(orderUpdate.getOverseasSales());
            projectAdd.setPurchDone(Boolean.FALSE);
            projectAdd.setCreateTime(new Date());
            projectAdd.setUpdateTime(new Date());
            projectAdd.setBusinessName(orderUpdate.getBusinessName());
            projectAdd.setAuditingStatus(1);
            //商务技术经办人名称
            Project project2 = projectDao.save(projectAdd);
            // 设置商品的项目信息
            List<Goods> goodsList1 = orderUpdate.getGoodsList();
            goodsList1.parallelStream().forEach(goods1 -> {
                goods1.setProject(project2);
                goods1.setProjectNo(project2.getProjectNo());
            });
            goodsDao.save(goodsList1);

            // 调用CRM系统，触发CRM用户升级任务
            String eruiToken = (String) ThreadLocalUtil.getObject();
            if (StringUtils.isNotBlank(eruiToken)) {
                String jsonParam = "{\"crm_code\":\"" + order.getCrmCode() + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");*/
    //             header.put("accept", "*/*");
              /*  String s = HttpRequest.sendPost(crmUrl + CRM_URL_METHOD, jsonParam, header);
                logger.info("CRM返回信息：" + s);
            }

            //销售订单通知：销售订单下达后通知商务技术经办人
            sendSms(order);


            //项目提交的时候判断是否有驳回的信息  如果有删除  “驳回订单” 待办提示
            BackLog backLog = new BackLog();
            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.REJECTORDER.getNum());    //功能访问路径标识
            backLog.setHostId(order.getId());
            backLogService.updateBackLogByDelYn(backLog);


            //订单提交 推送“待办”到项目
            BackLog newBackLog = new BackLog();
            newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getMsg());  //功能名称
            newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getNum());    //功能访问路径标识
            String contractNo = orderUpdate.getContractNo();  //销售合同号
            newBackLog.setReturnNo(contractNo);  //返回单号
            String region = orderUpdate.getRegion();//地区
            Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
            String country = orderUpdate.getCountry();//国家
            Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
            newBackLog.setInformTheContent(bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country));  //提示内容
            newBackLog.setHostId(orderUpdate.getId());    //父ID，列表页id    项目id
            Integer technicalId = orderUpdate.getTechnicalId();   //商务技术经办人id
            newBackLog.setUid(technicalId);   ////经办人id
            backLogService.addBackLogByDelYn(newBackLog);

        }
        return order.getId();
    }*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateOrder(AddOrderVo addOrderVo) throws Exception {
        Order order = orderDao.findOne(addOrderVo.getId());
        addOrderVo.copyBaseInfoTo(order);
        // 处理附件信息
        order.setAttachmentSet(addOrderVo.getAttachDesc());
        order.setOrderPayments(addOrderVo.getContractDesc());
        order.setDeleteFlag(false);
        //根据订单金额判断 填写审批人级别
        if (addOrderVo.getTotalPriceUsd() != null) {
            if (addOrderVo.getTotalPriceUsd().doubleValue() < STEP_ONE_PRICE.doubleValue()) {
                order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
                order.setCountryLeader(addOrderVo.getCountryLeader());
            } else if (STEP_ONE_PRICE.doubleValue() <= addOrderVo.getTotalPriceUsd().doubleValue() && addOrderVo.getTotalPriceUsd().doubleValue() < STEP_TWO_PRICE.doubleValue()) {
                order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
                order.setCountryLeader(addOrderVo.getCountryLeader());
                order.setAreaLeaderId(addOrderVo.getAreaLeaderId());
                order.setAreaLeader(addOrderVo.getAreaLeader());
            } else if (addOrderVo.getTotalPriceUsd().doubleValue() >= STEP_THREE_PRICE.doubleValue()) {
                order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
                order.setCountryLeader(addOrderVo.getCountryLeader());
                order.setAreaLeaderId(addOrderVo.getAreaLeaderId());
                order.setAreaLeader(addOrderVo.getAreaLeader());
                order.setAreaVpId(addOrderVo.getAreaVpId());
                order.setAreaVp(addOrderVo.getAreaVp());
            }
        }
        order.setFinancingCommissionerId(39535);
        if (addOrderVo.getStatus() == Order.StatusEnum.INIT.getCode()) {
            order.setAuditingStatus(1);
        } else if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            order.setAuditingStatus(2);
            if (StringUtils.isNotBlank(addOrderVo.getPerLiableRepay())) {
                order.setAuditingProcess(1);
                order.setAuditingUserId(addOrderVo.getPerLiableRepayId().toString());
            } else {
                order.setAuditingUserId(addOrderVo.getCountryLeaderId().toString());
                order.setAuditingProcess(2);
            }
        }
        CheckLog checkLog_i = null; // 审核日志
        Order orderUpdate = orderDao.saveAndFlush(order);
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            if (orderUpdate.getPerLiableRepayId() == null) {
                checkLog_i = fullCheckLogInfo(order.getId(), 0, orderUpdate.getCreateUserId(), orderUpdate.getCreateUserName(), orderUpdate.getAuditingProcess().toString(), orderUpdate.getPerLiableRepayId().toString(), null, "1", 1);
            } else {
                checkLog_i = fullCheckLogInfo(order.getId(), 0, orderUpdate.getCreateUserId(), orderUpdate.getCreateUserName(), orderUpdate.getAuditingProcess().toString(), orderUpdate.getCountryLeaderId().toString(), null, "1", 1);
            }
            checkLogService.insert(checkLog_i);
        }
        Date signingDate = null;
        if (orderUpdate.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            signingDate = orderUpdate.getSigningDate();
        }
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            List<OrderLog> orderLog = orderLogDao.findByOrderIdOrderByCreateTimeAsc(orderUpdate.getId());
            if (orderLog.size() > 0) {
                Map<String, OrderLog> collect = orderLog.stream().collect(Collectors.toMap(vo -> vo.getLogType().toString(), vo -> vo));
                if (collect.containsKey("1")) {
                    orderLogDao.delete(collect.get("1").getId());
                }
            }
            addLog(OrderLog.LogTypeEnum.CREATEORDER, orderUpdate.getId(), null, null, signingDate);
            applicationContext.publishEvent(new OrderProgressEvent(orderUpdate, 1));
            Project projectAdd = new Project();
            projectAdd.setOrder(orderUpdate);
            projectAdd.setExecCoName(orderUpdate.getExecCoName());
            projectAdd.setBusinessUid(orderUpdate.getTechnicalId());
            projectAdd.setExecCoName(orderUpdate.getExecCoName());
            projectAdd.setBusinessUnitName(orderUpdate.getBusinessUnitName());
            projectAdd.setSendDeptId(orderUpdate.getBusinessUnitId());
            projectAdd.setRegion(orderUpdate.getRegion());
            projectAdd.setCountry(orderUpdate.getCountry());
            projectAdd.setTotalPriceUsd(orderUpdate.getTotalPriceUsd());
            projectAdd.setDistributionDeptName(orderUpdate.getDistributionDeptName());
            projectAdd.setProjectStatus(Project.ProjectStatusEnum.SUBMIT.getCode());
            projectAdd.setPurchReqCreate(Project.PurchReqCreateEnum.NOT_CREATE.getCode());
            projectAdd.setOrderCategory(orderUpdate.getOrderCategory());
            projectAdd.setOverseasSales(orderUpdate.getOverseasSales());
            projectAdd.setPurchDone(Boolean.FALSE);
            projectAdd.setCreateTime(new Date());
            projectAdd.setUpdateTime(new Date());
            projectAdd.setBusinessName(orderUpdate.getBusinessName());
            projectAdd.setAuditingStatus(1);
            //商务技术经办人名称
            Project project = projectDao.save(projectAdd);
            //添加项目利润核算单信息
            ProjectProfit projectProfit = new ProjectProfit();
            projectProfit.setProject(project);
            projectProfit.setCountry(orderUpdate.getCountry());
            projectProfit.setTradeTerm(orderUpdate.getTradeTerms());
            projectProfit.setContractAmountUsd(orderUpdate.getTotalPriceUsd());
            projectProfit.setExchangeRate(orderUpdate.getExchangeRate());
            //projectAdd.setProjectProfit(projectProfit);
            projectProfitDao.save(projectProfit);
            // 调用CRM系统，触发CRM用户升级任务
            String eruiToken = (String) ThreadLocalUtil.getObject();
            if (StringUtils.isNotBlank(eruiToken)) {
                String jsonParam = "{\"crm_code\":\"" + order.getCrmCode() + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(crmUrl + CRM_URL_METHOD, jsonParam, header);
                logger.info("CRM返回信息：" + s);
            }
            //销售订单通知：销售订单下达后通知商务技术经办人
            sendSms(order);

            //项目提交的时候判断是否有驳回的信息  如果有删除  “驳回订单” 待办提示
            BackLog backLog = new BackLog();
            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.REJECTORDER.getNum());    //功能访问路径标识
            backLog.setHostId(order.getId());
            backLogService.updateBackLogByDelYn(backLog);

            //订单提交 推送“待办”到项目
            BackLog newBackLog = new BackLog();
            newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getMsg());  //功能名称
            newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getNum());    //功能访问路径标识
            String contractNo = orderUpdate.getContractNo();  //销售合同号
            newBackLog.setReturnNo(contractNo);  //返回单号
            String region = orderUpdate.getRegion();//地区
            Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
            String country = orderUpdate.getCountry();//国家
            Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
            newBackLog.setInformTheContent(bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country));  //提示内容
            newBackLog.setHostId(orderUpdate.getId());    //父ID，列表页id    项目id
            Integer technicalId = orderUpdate.getTechnicalId();   //商务技术经办人id
            newBackLog.setUid(technicalId);   ////经办人id
            backLogService.addBackLogByDelYn(newBackLog);

        }
        return order.getId();
    }

    private List<Goods> updateOrderGoods(AddOrderVo addOrderVo) throws Exception {
        Order order = orderDao.findOne(addOrderVo.getId());
        List<PGoods> pGoodsList = addOrderVo.getGoodDesc();
        Goods goods = null;
        List<Goods> goodsList = new ArrayList<>();
        Map<Integer, Goods> dbGoodsMap = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        Set<String> skuRepeatSet = new HashSet<>();
        for (PGoods pGoods : pGoodsList) {
            if (pGoods.getId() == null) {
                goods = new Goods();
                goods.setOrder(order);
            } else {
                goods = dbGoodsMap.remove(pGoods.getId());
                if (goods == null) {
                    throw new MyException("不存在的商品标识&&Non-existent product identifier");
                }
            }
            String sku = pGoods.getSku();
            if (StringUtils.isNotBlank(sku) && !skuRepeatSet.add(sku)) {
                // 已经存在的sku，返回错误
                throw new MyException("同一sku不可以重复添加&&The same sku can not be added repeatedly");
            }
            goods.setSku(sku);
            goods.setMeteType(pGoods.getMeteType());
            goods.setMeteName(pGoods.getMeteName());
            goods.setNameEn(pGoods.getNameEn());
            goods.setNameZh(pGoods.getNameZh());
            goods.setContractGoodsNum(pGoods.getContractGoodsNum());
            goods.setUnit(pGoods.getUnit());
            goods.setModel(pGoods.getModel());
            goods.setClientDesc(pGoods.getClientDesc());
            goods.setBrand(pGoods.getBrand());
            goods.setPurchasedNum(0);
            goods.setPrePurchsedNum(0);
            goods.setInspectNum(0);
            goods.setInstockNum(0);
            goods.setOutstockApplyNum(0);
            goods.setExchanged(false);
            goods.setOutstockNum(0);
            goods.setDepartment(pGoods.getDepartment());
            goods.setPrice(pGoods.getPrice());
            goodsList.add(goods);
        }
        order.setGoodsList(goodsList);
        goodsDao.delete(dbGoodsMap.values());
        // 设置商品的项目信息
        List<Goods> goodsList1 = order.getGoodsList();
        goodsList1.parallelStream().forEach(goods1 -> {
            goods1.setProject(order.getProject());
        });
        return goodsList1;
    }

    /*  // 检查和贸易术语相关字段的完整性
      private void checkOrderTradeTermsRelationField(AddOrderVo addOrderVo) throws Exception {
          String tradeTerms = addOrderVo.getTradeTerms(); // 贸易术语
          String toCountry = addOrderVo.getToCountry(); // 目的国
          String transportType = addOrderVo.getTransportType(); // 运输方式
          String toPort = addOrderVo.getToPort(); // 目的港
          String toPlace = addOrderVo.getToPlace(); // 目的地
          if (StringUtils.isBlank(tradeTerms)) {
              throw new MyException("贸易术语不能为空");
          }
          if (StringUtils.isBlank(toCountry)) {
              throw new MyException("目的国不能为空");
          }
          switch (tradeTerms) {
              case "EXW":
              case "FCA":
                  if (StringUtils.isBlank(transportType)) {
                      throw new MyException("运输方式不能为空");
                  }
                  break;
              case "CNF":
              case "CFR":
              case "CIF":
                  if (StringUtils.isBlank(toPort)) {
                      throw new MyException("目的港不能为空");
                  }
                  break;
              case "CPT":
              case "CIP":
                  if (StringUtils.isBlank(toPort)) {
                      throw new MyException("目的港不能为空");
                  }
                  if (StringUtils.isBlank(toPlace)) {
                      throw new MyException("目的地不能为空");
                  }
                  break;
              case "DAT":
              case "DAP":
              case "DDP":
                  if (StringUtils.isBlank(toPlace)) {
                      throw new MyException("目的地不能为空");
                  }
                  break;
              *//*
                case "FOB":
                case "FAS":
                    break;
                default:
                    throw new MyException("不存在的贸易术语");
            *//*
        }
    }*/
   /* @Override
    @Transactional(rollbackFor = Exception.class)*/
    /*public Integer addOrder(AddOrderVo addOrderVo) throws Exception {

        if (!StringUtils.equals("", addOrderVo.getContractNo()) && orderDao.countByContractNo(addOrderVo.getContractNo()) > 0) {
            throw new MyException("销售合同号已存在&&The order No. already exists");
        }
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            // 检查和贸易术语相关字段的完整性
            checkOrderTradeTermsRelationField(addOrderVo);
        }
        Order order = new Order();
        addOrderVo.copyBaseInfoTo(order);
        if (new Integer(3).equals(addOrderVo.getOverseasSales())) {
            order.setContractNo(addOrderVo.getContractNoOs());
        } else {
            order.setContractNo(addOrderVo.getContractNo());
        }
        order.setCreateUserId(addOrderVo.getCreateUserId());
        order.setCreateUserName(addOrderVo.getCreateUserName());
        order.setAttachmentSet(addOrderVo.getAttachDesc());
        List<PGoods> pGoodsList = addOrderVo.getGoodDesc();
        Goods goods = null;
        List<Goods> goodsList = new ArrayList<>();
        Set<String> skuRepeatSet = new HashSet<>();
        for (PGoods pGoods : pGoodsList) {
            goods = new Goods();
            //goods.setSeq(pGoods.getSeq());
            String sku = pGoods.getSku();
            if (StringUtils.isNotBlank(sku) && !skuRepeatSet.add(sku)) {
                // 已经存在的sku，返回错误
                throw new MyException("同一sku不可以重复添加&&The same sku can not be added repeatedly");
            }
            goods.setSku(sku);
            goods.setOutstockNum(0);
            goods.setMeteType(pGoods.getMeteType());
            goods.setMeteName(pGoods.getMeteName());
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
            goods.setDepartment(pGoods.getDepartment());
            goods.setPrice(pGoods.getPrice());
            goods.setOrder(order);
            goodsList.add(goods);
            goods.setOrder(order);
        }
        order.setGoodsList(goodsList);
        order.setOrderPayments(addOrderVo.getContractDesc());
        order.setCreateTime(new Date());
        order.setDeleteFlag(false);
        //根据订单金额判断 填写审批人级别
        if (addOrderVo.getTotalPriceUsd() != null) {
            if (addOrderVo.getTotalPriceUsd().doubleValue() < STEP_ONE_PRICE.doubleValue()) {
                order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
                order.setCountryLeader(addOrderVo.getCountryLeader());
            } else if (STEP_ONE_PRICE.doubleValue() <= addOrderVo.getTotalPriceUsd().doubleValue() && addOrderVo.getTotalPriceUsd().doubleValue() < STEP_TWO_PRICE.doubleValue()) {
                order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
                order.setCountryLeader(addOrderVo.getCountryLeader());
                order.setAreaLeaderId(addOrderVo.getAreaLeaderId());
                order.setAreaLeader(addOrderVo.getAreaLeader());
            } else if (addOrderVo.getTotalPriceUsd().doubleValue() >= STEP_THREE_PRICE.doubleValue()) {
                order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
                order.setCountryLeader(addOrderVo.getCountryLeader());
                order.setAreaLeaderId(addOrderVo.getAreaLeaderId());
                order.setAreaLeader(addOrderVo.getAreaLeader());
                order.setAreaVpId(addOrderVo.getAreaVpId());
                order.setAreaVp(addOrderVo.getAreaVp());
            }
        }
        order.setAuditingProcess(1);
        order.setFinancingCommissionerId(39535);
        if (addOrderVo.getStatus() == Order.StatusEnum.INIT.getCode()) {
            order.setAuditingStatus(1);
        } else if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            order.setAuditingStatus(2);
            order.setAuditingUserId(addOrderVo.getPerLiableRepayId().toString());
        }
        CheckLog checkLog_i = null;//审批流日志
        Order order1 = orderDao.save(order);
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            checkLog_i = fullCheckLogInfo(order.getId(), 0, order1.getCreateUserId(), order1.getCreateUserName(), order1.getAuditingProcess().toString(), order1.getPerLiableRepayId().toString(), null, "1", 1);
            checkLogService.insert(checkLog_i);
        }
        Date signingDate = null;
        if (order1.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            signingDate = order1.getSigningDate();
        }
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            //添加订单未执行事件
            applicationContext.publishEvent(new OrderProgressEvent(order1, 1));
            List<OrderLog> orderLog = orderLogDao.findByOrderIdOrderByCreateTimeAsc(order1.getId());
            if (orderLog.size() > 0) {
                Map<String, OrderLog> collect = orderLog.stream().collect(Collectors.toMap(vo -> vo.getLogType().toString(), vo -> vo));
                if (collect.containsKey("1")) {
                    orderLogDao.delete(collect.get("1").getId());
                }
            }
            addLog(OrderLog.LogTypeEnum.CREATEORDER, order1.getId(), null, null, signingDate);
            // 订单提交时推送项目信息
            Project project = new Project();
            //project.setProjectNo(UUID.randomUUID().toString());
            project.setOrder(order1);
            project.setContractNo(order1.getContractNo());
            project.setBusinessUid(order1.getTechnicalId());
            project.setExecCoName(order1.getExecCoName());
            project.setBusinessUnitName(order1.getBusinessUnitName());
            project.setSendDeptId(order1.getBusinessUnitId());
            project.setDistributionDeptName(order1.getDistributionDeptName());
            project.setRegion(order1.getRegion());
            project.setCountry(order1.getCountry());
            project.setProjectStatus(Project.ProjectStatusEnum.SUBMIT.getCode());
            project.setPurchReqCreate(Project.PurchReqCreateEnum.NOT_CREATE.getCode());
            project.setTotalPriceUsd(order1.getTotalPriceUsd());
            project.setOrderCategory(order1.getOrderCategory());
            project.setOverseasSales(order1.getOverseasSales());
            project.setPurchDone(Boolean.FALSE);
            project.setCreateTime(new Date());
            project.setUpdateTime(new Date());
            project.setProcessProgress("1");
            project.setBusinessName(order1.getBusinessName());   //商务技术经办人名称
            //新建项目审批状态为未审核
            project.setAuditingStatus(1);
            Project project2 = projectDao.save(project);
            // 设置商品的项目信息
            List<Goods> goodsList1 = order1.getGoodsList();
            goodsList1.parallelStream().forEach(goods1 -> {
                goods1.setProject(project2);
                goods1.setProjectNo(project2.getProjectNo());
            });
            goodsDao.save(goodsList1);
            // 调用CRM系统，触发CRM用户升级任务
            String eruiToken = (String) ThreadLocalUtil.getObject();
            if (StringUtils.isNotBlank(eruiToken)) {
                String jsonParam = "{\"crm_code\":\"" + order.getCrmCode() + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");*/
    //header.put("accept", "**/*//*");
               /* String s = HttpRequest.sendPost(crmUrl + CRM_URL_METHOD, jsonParam, header);
                logger.info("调用升级CRM用户接口，CRM返回信息：" + s);
            }
            // 销售订单通知：销售订单下达后通知商务技术经办人
            sendSms(order);

            //项目提交的时候判断是否有驳回的信息  如果有删除  项目驳回 待办提示
            BackLog backLog = new BackLog();
            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.REJECTORDER.getNum());    //功能访问路径标识
            backLog.setHostId(order.getId());
            backLogService.updateBackLogByDelYn(backLog);

            //订单提交 推送“待办”到项目
            BackLog newBackLog = new BackLog();
            newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getMsg());  //功能名称
            newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getNum());    //功能访问路径标识
            String contractNo = order1.getContractNo();  //销售合同号
            newBackLog.setReturnNo(contractNo);  //返回单号
            String region = order1.getRegion();//地区
            Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
            String country = order1.getCountry();//国家
            Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
            newBackLog.setInformTheContent(bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country));  //提示内容
            newBackLog.setHostId(order.getId());    //父ID，列表页id    项目id
            Integer technicalId = order1.getTechnicalId();   //商务技术经办人id
            newBackLog.setUid(technicalId);   ////经办人id
            backLogService.addBackLogByDelYn(newBackLog);

        }
        return order1.getId();
    }*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrder(AddOrderVo addOrderVo) throws Exception {
        Order order = new Order();
        addOrderVo.copyBaseInfoTo(order);
        order.setCreateUserId(addOrderVo.getCreateUserId());
        order.setCreateUserName(addOrderVo.getCreateUserName());
        order.setAttachmentSet(addOrderVo.getAttachDesc());
        order.setOrderPayments(addOrderVo.getContractDesc());
        order.setCreateTime(new Date());
        order.setDeleteFlag(false);
        //根据订单金额判断 填写审批人级别
        if (addOrderVo.getTotalPriceUsd() != null) {
            if (addOrderVo.getTotalPriceUsd().doubleValue() < STEP_ONE_PRICE.doubleValue()) {
                order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
                order.setCountryLeader(addOrderVo.getCountryLeader());
            } else if (STEP_ONE_PRICE.doubleValue() <= addOrderVo.getTotalPriceUsd().doubleValue() && addOrderVo.getTotalPriceUsd().doubleValue() < STEP_TWO_PRICE.doubleValue()) {
                order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
                order.setCountryLeader(addOrderVo.getCountryLeader());
                order.setAreaLeaderId(addOrderVo.getAreaLeaderId());
                order.setAreaLeader(addOrderVo.getAreaLeader());
            } else if (addOrderVo.getTotalPriceUsd().doubleValue() >= STEP_THREE_PRICE.doubleValue()) {
                order.setCountryLeaderId(addOrderVo.getCountryLeaderId());
                order.setCountryLeader(addOrderVo.getCountryLeader());
                order.setAreaLeaderId(addOrderVo.getAreaLeaderId());
                order.setAreaLeader(addOrderVo.getAreaLeader());
                order.setAreaVpId(addOrderVo.getAreaVpId());
                order.setAreaVp(addOrderVo.getAreaVp());
            }
        }
        order.setFinancingCommissionerId(39535);
        if (addOrderVo.getStatus() == Order.StatusEnum.INIT.getCode()) {
            order.setAuditingStatus(1);
        } else if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            order.setAuditingStatus(2);
            if (StringUtils.isNotBlank(addOrderVo.getPerLiableRepay())) {
                order.setAuditingProcess(1);
                order.setAuditingUserId(addOrderVo.getPerLiableRepayId().toString());
            } else {
                order.setAuditingProcess(2);
                order.setAuditingUserId(addOrderVo.getCountryLeaderId().toString());
            }
        }
        CheckLog checkLog_i = null;//审批流日志
        Order order1 = orderDao.save(order);
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            if (order1.getPerLiableRepayId() == null) {
                checkLog_i = fullCheckLogInfo(order.getId(), 0, order1.getCreateUserId(), order1.getCreateUserName(), order1.getAuditingProcess().toString(), order1.getPerLiableRepayId().toString(), null, "1", 1);
            } else {
                checkLog_i = fullCheckLogInfo(order.getId(), 0, order1.getCreateUserId(), order1.getCreateUserName(), order1.getAuditingProcess().toString(), order1.getCountryLeaderId().toString(), null, "1", 1);

            }
            checkLogService.insert(checkLog_i);
        }
        Date signingDate = null;
        if (order1.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            signingDate = order1.getSigningDate();
        }
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            //添加订单未执行事件
            applicationContext.publishEvent(new OrderProgressEvent(order1, 1));
            List<OrderLog> orderLog = orderLogDao.findByOrderIdOrderByCreateTimeAsc(order1.getId());
            if (orderLog.size() > 0) {
                Map<String, OrderLog> collect = orderLog.stream().collect(Collectors.toMap(vo -> vo.getLogType().toString(), vo -> vo));
                if (collect.containsKey("1")) {
                    orderLogDao.delete(collect.get("1").getId());
                }
            }
            addLog(OrderLog.LogTypeEnum.CREATEORDER, order1.getId(), null, null, signingDate);
            // 订单提交时推送项目信息
            Project project = new Project();
            //project.setProjectNo(UUID.randomUUID().toString());
            project.setOrder(order1);
            project.setBusinessUid(order1.getTechnicalId());
            project.setExecCoName(order1.getExecCoName());
            project.setBusinessUnitName(order1.getBusinessUnitName());
            project.setSendDeptId(order1.getBusinessUnitId());
            project.setDistributionDeptName(order1.getDistributionDeptName());
            project.setRegion(order1.getRegion());
            project.setCountry(order1.getCountry());
            project.setProjectStatus(Project.ProjectStatusEnum.SUBMIT.getCode());
            project.setPurchReqCreate(Project.PurchReqCreateEnum.NOT_CREATE.getCode());
            project.setTotalPriceUsd(order1.getTotalPriceUsd());
            project.setOrderCategory(order1.getOrderCategory());
            project.setOverseasSales(order1.getOverseasSales());
            project.setPurchDone(Boolean.FALSE);
            project.setCreateTime(new Date());
            project.setUpdateTime(new Date());
            project.setProcessProgress("1");
            project.setBusinessName(order1.getBusinessName());   //商务技术经办人名称
            //新建项目审批状态为未审核
            project.setAuditingStatus(1);
            //projectAdd.setProjectProfit(projectProfit);
            Project project1 = projectDao.save(project);
            //添加项目利润核算单信息
            ProjectProfit projectProfit = new ProjectProfit();
            projectProfit.setProject(project1);
            projectProfit.setCountry(order1.getCountry());
            projectProfit.setTradeTerm(order1.getTradeTerms());
            projectProfit.setContractAmountUsd(order1.getTotalPriceUsd());
            projectProfit.setExchangeRate(order1.getExchangeRate());
            projectProfitDao.save(projectProfit);
            // 调用CRM系统，触发CRM用户升级任务
            String eruiToken = (String) ThreadLocalUtil.getObject();
            if (StringUtils.isNotBlank(eruiToken)) {
                String jsonParam = "{\"crm_code\":\"" + order.getCrmCode() + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(crmUrl + CRM_URL_METHOD, jsonParam, header);
                logger.info("调用升级CRM用户接口，CRM返回信息：" + s);
            }
            // 销售订单通知：销售订单下达后通知商务技术经办人
            sendSms(order);

            //项目提交的时候判断是否有驳回的信息  如果有删除  “项目驳回” 待办提示
            BackLog backLog = new BackLog();
            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.REJECTORDER.getNum());    //功能访问路径标识
            backLog.setHostId(order.getId());
            backLogService.updateBackLogByDelYn(backLog);

            //订单提交 推送“待办”到项目
            BackLog newBackLog = new BackLog();
            newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getMsg());  //功能名称
            newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getNum());    //功能访问路径标识
            String contractNo = order1.getContractNo();  //销售合同号
            newBackLog.setReturnNo(contractNo);  //返回单号
            String region = order1.getRegion();//地区
            Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
            String country = order1.getCountry();//国家
            Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
            newBackLog.setInformTheContent(bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country));  //提示内容
            newBackLog.setHostId(order.getId());    //父ID，列表页id    项目id
            Integer technicalId = order1.getTechnicalId();   //商务技术经办人id
            newBackLog.setUid(technicalId);   ////经办人id
            backLogService.addBackLogByDelYn(newBackLog);

        }
        return order1.getId();
    }

    private List<Goods> addOrderGoods(AddOrderVo addOrderVo) throws Exception {
        Order order = orderDao.findOne(addOrderVo.getId());
        if (order == null) {
            return null;
        }
        List<PGoods> pGoodsList = addOrderVo.getGoodDesc();
        Goods goods = null;
        List<Goods> goodsList = new ArrayList<>();
        Set<String> skuRepeatSet = new HashSet<>();
        for (PGoods pGoods : pGoodsList) {
            goods = new Goods();
            //goods.setSeq(pGoods.getSeq());
            String sku = pGoods.getSku();
            if (StringUtils.isNotBlank(sku) && !skuRepeatSet.add(sku)) {
                // 已经存在的sku，返回错误
                throw new MyException("同一sku不可以重复添加&&The same sku can not be added repeatedly");
            }
            goods.setSku(sku);
            goods.setOutstockNum(0);
            goods.setMeteType(pGoods.getMeteType());
            goods.setMeteName(pGoods.getMeteName());
            goods.setNameEn(pGoods.getNameEn());
            goods.setNameZh(pGoods.getNameZh());
            goods.setContractGoodsNum(pGoods.getContractGoodsNum());
            goods.setUnit(pGoods.getUnit());
            goods.setModel(pGoods.getModel());
            goods.setClientDesc(pGoods.getClientDesc());
            goods.setBrand(pGoods.getBrand());
            goods.setPurchasedNum(0);
            goods.setPrePurchsedNum(0);
            goods.setInstockNum(0);
            goods.setInspectNum(0);
            goods.setOutstockApplyNum(0);
            goods.setOutstockNum(0);
            goods.setExchanged(false);
            goods.setDepartment(pGoods.getDepartment());
            goods.setPrice(pGoods.getPrice());
            goods.setOrder(order);
            goodsList.add(goods);
            goods.setOrder(order);
        }
        order.setGoodsList(goodsList);
        // 设置商品的项目信息
        List<Goods> goodsList1 = order.getGoodsList();
        goodsList1.parallelStream().forEach(goods1 -> {
            goods1.setProject(order.getProject());
        });
        return goodsList;
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
            if (signingDate != null) {
                orderLog.setOrder(orderDao.findOne(orderId));
                orderLog.setLogType(logType.getCode());
                orderLog.setOperation(StringUtils.defaultIfBlank(operato, logType.getMsg()));
                orderLog.setEnoperation(StringUtils.defaultIfBlank(operato, logType.getEnMsg()));
                orderLog.setCreateTime(new Date());
                orderLog.setBusinessDate(signingDate);  //订单签约日期
                orderLog.setOrdersGoodsId(goodsId);
                orderLogDao.save(orderLog);
            }

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


    //销售订单通知：销售订单下达后通知商务技术经办人
    public void sendSms(Order order) throws Exception {
        //订单下达后通知商务技术经办人
        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        logger.info("发送短信的用户token:" + eruiToken);
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                // 根据id获取商务经办人信息
                String jsonParam = "{\"id\":\"" + order.getTechnicalId() + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + s);

                // 获取商务经办人手机号
                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");
                String mobile = null;  //商务经办人手机号
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    mobile = data.getString("mobile");
                    //发送短信
                    Map<String, String> map = new HashMap();
                    map.put("areaCode", "86");
                    map.put("to", "[\"" + mobile + "\"]");
                    map.put("content", "您好，销售合同号：" + order.getContractNo() + "，市场经办人：" + order.getAgentName() + "，已申请项目执行，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType", "0");
                    map.put("groupSending", "0");
                    map.put("useType", "订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态" + s1);
                }

            } catch (Exception e) {
                throw new MyException(String.format("%s%s%s", "发送短信异常失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Sending SMS exceptions to failure"));
            }

        }
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
                    orderLog2.setBusinessDate(orderLog.getBusinessDate());
                    break;
                case "收到预付款":
                    orderLog2.setOperation("Receive advance payment");
                    orderLog2.setBusinessDate(orderLog.getBusinessDate());
                    break;
                case "商品出库":
                    orderLog2.setOperation("Goods export");
                    orderLog2.setBusinessDate(orderLog.getBusinessDate());
                    break;
                case "已收货":
                    orderLog2.setOperation("received");
                    orderLog2.setBusinessDate(orderLog.getBusinessDate());
                    break;
                case "全部交收完成":
                    orderLog2.setOperation("Completed delivery");
                    orderLog2.setBusinessDate(orderLog.getBusinessDate());
                    break;
                case "订单签约":
                    orderLog2.setOperation("Signing order");
                    orderLog2.setBusinessDate(orderLog.getBusinessDate());
                    break;
                default:
                    orderLog2.setOperation(orderLog.getOperation());
                    orderLog2.setBusinessDate(orderLog.getBusinessDate());
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

    @Transactional(readOnly = true)
    @Override
    public List<Order> findOrderExport(final OrderListCondition condition) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Order> pageList = orderDao.findAll(new Specification<Order>() {
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
               /* if (condition.getSigningDate() != null) {
                    list.add(cb.equal(root.get("signingDate").as(Date.class), NewDateUtil.getDate(condition.getSigningDate())));
                } */
                //根据订单签订时间段查询 开始
                if (condition.getStartTime() != null) {
                    Date startT = DateUtil.getOperationTime(condition.getStartTime(), 0, 0, 0);
                    Predicate startTime = cb.greaterThanOrEqualTo(root.get("signingDate").as(Date.class), startT);
                    list.add(startTime);
                }
                //根据订单签订时间段查询 结束
                if (condition.getEndTime() != null) {
                    Date endT = DateUtil.getOperationTime(condition.getEndTime(), 23, 59, 59);
                    Predicate endTime = cb.lessThanOrEqualTo(root.get("signingDate").as(Date.class), endT);
                    list.add(endTime);
                }
                //根据合同交货日期查询
                if (StringUtil.isNotBlank(condition.getDeliveryDate())) {
                    list.add(cb.equal(root.get("deliveryDate").as(String.class), condition.getDeliveryDate()));
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
                //根据流程进度
                if (StringUtil.isNotBlank(condition.getProcessProgress())) {
                    if (StringUtils.equals("1", condition.getProcessProgress())) {
                        list.add(cb.equal(root.get("processProgress").as(String.class), condition.getProcessProgress()));
                    } else {
                        list.add(cb.greaterThanOrEqualTo(root.get("processProgress").as(String.class), condition.getProcessProgress()));
                    }
                }
                //根据项目号
                if (StringUtil.isNotBlank(condition.getProjectNo())) {
                    list.add(cb.like(root.get("projectNo").as(String.class), "%" + condition.getProjectNo() + "%"));
                }
                //根据是否已生成出口通知单
                if (condition.getDeliverConsignHas() != null) {
                    list.add(cb.equal(root.get("deliverConsignHas").as(Integer.class), condition.getDeliverConsignHas()));
                }
                //商务技术经办人
                if (condition.getTechnicalId02() != null) {
                    list.add(cb.equal(root.get("technicalId").as(Integer.class), condition.getTechnicalId02()));
                }
                //事业部
                if (StringUtils.isNotBlank(condition.getBusinessUnitId02())) {
                    list.add(cb.equal(root.get("businessUnitId").as(String.class), condition.getBusinessUnitId02()));
                }
                //根据区域所在国家查询
               /* String[] country = null;
                if (StringUtils.isNotBlank(condition.getCountry())) {
                    country = condition.getCountry().split(",");
                }*/
                //根据事业部搜索
                String[] bid = null;
                if (StringUtils.isNotBlank(condition.getBusinessUnitId())) {
                    bid = condition.getBusinessUnitId().split(",");
                }
                if (condition.getType() == 1) {
                    Predicate createUserId = null;
                    if (condition.getCreateUserId() != null) {
                        createUserId = cb.equal(root.get("createUserId").as(Integer.class), condition.getCreateUserId());
                    }
                    Predicate businessUnitId = null;
                    if (bid != null) {
                        businessUnitId = root.get("businessUnitId").in(bid);
                    }
                    Predicate technicalId = null;
                    if (condition.getTechnicalId() != null) {
                        technicalId = cb.equal(root.get("technicalId").as(Integer.class), condition.getTechnicalId());
                    }
                    //根据市场经办人查询
                    if (condition.getAgentId() != null) {
                        list.add(cb.equal(root.get("agentId").as(String.class), condition.getAgentId()));
                    }
                    Predicate and = cb.and(businessUnitId, technicalId);
                    if (businessUnitId != null && technicalId != null) {
                        list.add(cb.or(and, createUserId));
                    } else if (businessUnitId != null && technicalId == null) {
                        list.add(cb.or(businessUnitId, createUserId));
                    } else if (technicalId != null && businessUnitId == null) {
                        list.add(cb.or(technicalId, createUserId));
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
                  /*  if (country != null) {
                        list.add(root.get("country").in(country));
                    }*/
                }
                list.add(cb.equal(root.get("deleteFlag"), false));
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, sort);
        try {
            if (pageList.size() > 0) {
                for (Order order : pageList) {
                    order.setAttachmentSet(null);
                    order.setOrderPayments(null);
                    order.setOrderAccountDelivers(null);
                    order.setOrderAccounts(null);
                    if (order.getDeliverConsignC() && order.getStatus() == Order.StatusEnum.EXECUTING.getCode()) {
                        boolean flag = order.getGoodsList().parallelStream().anyMatch(goods -> goods.getOutstockApplyNum() < goods.getContractGoodsNum());
                        order.setDeliverConsignC(flag);
                    } else {
                        order.setDeliverConsignC(Boolean.FALSE);
                    }
                    if (iogisticsDataService.findStatusAndNumber(order.getId()) && order.getDeliverConsignC() == false) {
                        order.setOrderFinish(Boolean.TRUE);
                    }
                    order.setGoodsList(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
        ImportDataResponse response = new ImportDataResponse(new String[]{"projectAccount"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        Order oc = null;
        Project project = null;
        // 订单总数量
        int orderCount = 0;
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2; // 数据从第二行开始
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.ORDER_MANAGE, response, cellIndex)) {
                continue;
            }
            oc = new Order();
            project = new Project();
            if (strArr[0] != null) {
                oc.setOrderCategory(Integer.parseInt(strArr[0]));
            }
            if (strArr[1] != null) {
                oc.setOverseasSales(Integer.parseInt(strArr[1]));
            }
            oc.setContractNo(strArr[2]);
            oc.setFrameworkNo(strArr[3]);
            oc.setContractNoOs(strArr[4]);
            oc.setPoNo(strArr[5]);
            oc.setLogiQuoteNo(strArr[6]);
            oc.setInquiryNo(strArr[7]);
            if (strArr[8] != null) {
                Date signingDate = DateUtil.parseString2DateNoException(strArr[8], "yyyy-MM-dd");
                oc.setSigningDate(signingDate);
            }
            if (strArr[9] != null) {
                oc.setDeliveryDate(strArr[9]);
            }
            if (strArr[10] != null) {
                oc.setAgentId(Integer.parseInt(strArr[10]));
            }
            if (strArr[11] != null) {
                oc.setAcquireId(Integer.parseInt(strArr[11]));
            }
            oc.setSigningCo(strArr[12]);
            /*if (strArr[13] != null) {
                oc.setBusinessUnitId(Integer.parseInt(strArr[13]));
            }*/
            oc.setBusinessUnitName(strArr[13]);
           /* if (strArr[14] != null) {
                oc.setExecCoId(Integer.parseInt(strArr[14]));
            }*/
            //执行分公司
            oc.setExecCoName(strArr[14]);

            oc.setRegion(strArr[15]);
          /*  if (strArr[15] != null) {
                try {
                    oc.setOrderCount(new BigDecimal(strArr[15]).intValue());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), cellIndex, "数量字段非数字");
                    continue;
                }
            }*/
            oc.setDistributionDeptName(strArr[16]);
            // 国家
            oc.setCountry(strArr[17]);
            //订单号
            oc.setCrmCode(strArr[18]);
            //客户类型
            if (strArr[19] != null) {
                oc.setCustomerType(Integer.parseInt(strArr[19]));
            }
            //回款责任人
            oc.setPerLiableRepay(strArr[20]);
            //是否融资
            if (strArr[21] != null) {
                oc.setFinancing(Integer.parseInt(strArr[21]));
            }
            //会员类型
            if (strArr[22] != null) {
                oc.setOrderBelongs(Integer.parseInt(strArr[22]));
            }
            //授信情况
            oc.setGrantType(strArr[23]);
            //订单类型
            if (strArr[24] != null) {
                oc.setOrderType(Integer.parseInt(strArr[24]));
            }
            //贸易术语
            oc.setTradeTerms(strArr[25]);
            //运输方式
            oc.setTransportType(strArr[26]);
            if (strArr[27] != null) {
                try {
                    oc.setTotalPrice(new BigDecimal(strArr[27]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "合同总价（美元）非数字");
                    continue;
                }
            }
            //是否含税
            if (strArr[28] != null) {
                oc.setTaxBearing(Integer.parseInt(strArr[28]));
            }
            //  汇率  strArr[29]
            //合同总价
            if (strArr[30] != null) {
                try {
                    oc.setTotalPriceUsd(new BigDecimal(strArr[30]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "合同总价（美元）非数字");
                    continue;
                }
            }
            oc.setPaymentModeBn(strArr[31]);
            if (strArr[33] != null) {
                try {
                    oc.setQualityFunds(new BigDecimal(strArr[32]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "质保金 非数字");
                    continue;
                }
            }
            OrderPayment orderPayment = new OrderPayment();
            ArrayList<OrderPayment> paymentList = new ArrayList<>();
            if (strArr[33] != null) {
                try {
                    orderPayment.setMoney(new BigDecimal(strArr[33]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "预收款 非数字");
                    continue;
                }
                orderPayment.setType(1);
                orderPayment.setReceiptDate(DateUtil.parseString2DateNoException(strArr[34], "yyyy-MM-dd"));
                paymentList.add(orderPayment);
            }
            if (strArr[35] != null) {
                try {
                    orderPayment.setMoney(new BigDecimal(strArr[35]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "质保金 非数字");
                    continue;
                }
                orderPayment.setType(2);
                orderPayment.setReceiptDate(DateUtil.parseString2DateNoException(strArr[36], "yyyy-MM-dd"));
                paymentList.add(orderPayment);
            }
            oc.setDeliveryRequires(strArr[37]);
            oc.setCustomerContext(strArr[38]);
            Order order = null;
            try {
                order = orderDao.save(oc);
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, e.getMessage());
                continue;
            }
            //添加项目
            project.setOrder(order);
            project.setContractNo(strArr[2]);
            project.setStartDate(DateUtil.parseString2DateNoException(strArr[39], "yyyy-MM-dd"));
            project.setProjectName(strArr[40]);
            // 国家
            project.setCountry(strArr[17]);
            //执行约定交付日期
            project.setDeliveryDate(strArr[41]);
            //合同总价
            if (strArr[42] != null) {
                try {
                    project.setTotalPriceUsd(new BigDecimal(strArr[42]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "合同总价（美元）非数字");
                    continue;
                }
            }
            if (strArr[43] != null) {
                try {
                    project.setProfitPercent(new BigDecimal(strArr[43]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "初步利润（美元）非数字");
                    continue;
                }
            }
            if (strArr[44] != null) {
                try {
                    project.setProfit(new BigDecimal(strArr[44]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "利润（美元）非数字");
                    continue;
                }
            }
            //执行分公司
            if (strArr[14] != null) {
                project.setExecCoName(order.getExecCoName());
            }
            project.setRegion(strArr[15]);
            //执行变更日期
            project.setExeChgDate(DateUtil.parseString2DateNoException(strArr[45], "yyyy-MM-dd"));
            //有无项目经理
            if (strArr[46] != null) {
                project.setHasManager(Integer.parseInt(strArr[46]));
            }
            project.setBusinessUnitName(strArr[47]);
            project.setDistributionDeptName(strArr[16]);
            project.setProjectStatus(strArr[48]);
            /*if (strArr[49] != null) {
                project.setRemarks(strArr[49]);
            }*/
         /*   if (strArr[37] != null) {
                try {
                    oc.setPurchaseContractDate(DateUtil.parseString2Date(strArr[37], "yyyy/M/d", "yyyy/M/d",
                            DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
                } catch (Exception e) {


                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), cellIndex, "采购签合同日期格式错误");
                    continue;
                }
            }*/
            try {
                projectDao.save(project);
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, e.getMessage());
                continue;
            }
            response.incrSuccess();
        }


        response.getFailItems();
        response.getSumMap().put("orderCount", new BigDecimal(orderCount)); // 订单总数量
        response.setDone(true);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImportDataResponse importOrder(List<String[]> datas, boolean testOnly) {
        ImportDataResponse response = new ImportDataResponse(new String[]{"orderManage"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        Order oc = null;
        Project project = null;
        // 订单总数量
        int orderCount = 0;
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2; // 数据从第二行开始
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.ORDER_CHECK, response, cellIndex)) {
                continue;
            }
            Integer orderId = null;
            if (StringUtils.isNotBlank(strArr[0])) {
                orderId = Integer.parseInt(strArr[0]);
                oc = orderDao.findOne(orderId);
                /*if (oc == null) {
                    continue;
                }*/
            }
            if (StringUtils.isNotBlank(strArr[1])) {
                oc.setOrderCategory(Integer.parseInt(strArr[1]));
            }
            if (StringUtils.isNotBlank(strArr[2])) {
                oc.setOverseasSales(Integer.parseInt(strArr[2]));
            }
            if (StringUtils.isNotBlank(strArr[3])) {
                oc.setContractNo(strArr[3]);
            }
            if (StringUtils.isNotBlank(strArr[4])) {
                oc.setFrameworkNo(strArr[4]);
            }
            if (StringUtils.isNotBlank(strArr[5])) {
                oc.setContractNoOs(strArr[5]);
            }
            if (StringUtils.isNotBlank(strArr[6])) {
                oc.setPoNo(strArr[6]);
            }
            if (StringUtils.isNotBlank(strArr[7])) {
                oc.setLogiQuoteNo(strArr[7]);
            }
            if (StringUtils.isNotBlank(strArr[8])) {
                oc.setInquiryNo(strArr[8]);
            }
            if (StringUtils.isNotBlank(strArr[9])) {
                Date signingDate = DateUtil.parseString2DateNoException(strArr[9], "yyyy-MM-dd");
                oc.setSigningDate(signingDate);
            }
            if (StringUtils.isNotBlank(strArr[10])) {
                oc.setDeliveryDate(strArr[10]);
            }
            if (StringUtils.isNotBlank(strArr[11])) {
                oc.setAgentId(Integer.parseInt(strArr[11]));
            }
            if (StringUtils.isNotBlank(strArr[12])) {
                oc.setAgentName(strArr[12]);
            }
            if (StringUtils.isNotBlank(strArr[13])) {
                oc.setAcquireId(Integer.parseInt(strArr[13]));
            }
            if (StringUtils.isNotBlank(strArr[14])) {
                oc.setSigningCo(strArr[14]);
            }
            if (StringUtils.isNotBlank(strArr[15])) {
                oc.setBusinessUnitId(Integer.parseInt(strArr[15]));
            }
            if (StringUtils.isNotBlank(strArr[16])) {
                oc.setBusinessUnitName(strArr[16]);
            }
            if (StringUtils.isNotBlank(strArr[17])) {
                oc.setExecCoId(Integer.parseInt(strArr[17]));
            }
            //执行分公司
            if (StringUtils.isNotBlank(strArr[18])) {
                oc.setExecCoName(strArr[18]);
            }
            if (StringUtils.isNotBlank(strArr[19])) {
                oc.setRegion(strArr[19]);
            }
           /*if (strArr[15] != null) {
                try {
                    oc.setOrderCount(new BigDecimal(strArr[15]).intValue());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), cellIndex, "数量字段非数字");
                    continue;
                }
            }*/
            // 20 分销部id
            //分销部
            if (StringUtils.isNotBlank(strArr[21])) {
                oc.setDistributionDeptName(strArr[21]);
            }
            // 国家
            if (StringUtils.isNotBlank(strArr[22])) {
                oc.setCountry(strArr[22]);
            }
            //订单号
            if (StringUtils.isNotBlank(strArr[23])) {
                oc.setCrmCode(strArr[23]);
            }
            //客户类型
            if (StringUtils.isNotBlank(strArr[24])) {
                oc.setCustomerType(Integer.parseInt(strArr[24]));
            }
            //回款责任人
            if (StringUtils.isNotBlank(strArr[25])) {
                oc.setPerLiableRepay(strArr[25]);
            }
            //商务技术经办人编号
            if (StringUtils.isNotBlank(strArr[26])) {
                oc.setTechnicalId(Integer.parseInt(strArr[26]));
            }
            if (StringUtils.isNotBlank(strArr[27])) {
                oc.setBusinessName(strArr[27]);
            }
            //是否融资
            if (StringUtils.isNotBlank(strArr[28])) {
                oc.setFinancing(Integer.parseInt(strArr[28]));
            }
            //会员类型
            if (StringUtils.isNotBlank(strArr[29])) {
                oc.setOrderBelongs(Integer.parseInt(strArr[29]));
            }
            //授信情况
            if (StringUtils.isNotBlank(strArr[30])) {
                oc.setGrantType(strArr[30]);
            }
            //订单类型
            if (StringUtils.isNotBlank(strArr[31])) {
                oc.setOrderType(Integer.parseInt(strArr[31]));
            }
            //贸易术语
            if (StringUtils.isNotBlank(strArr[32])) {
                oc.setTradeTerms(strArr[32]);
            }
            //运输方式
            if (StringUtils.isNotBlank(strArr[33])) {
                oc.setTransportType(strArr[33]);
            }
            //起运港
            if (StringUtils.isNotBlank(strArr[34])) {
                oc.setFromPort(strArr[34]);
            }
            //起运国
            if (StringUtils.isNotBlank(strArr[35])) {
                oc.setFromCountry(strArr[35]);
            }
            //发运起始地
            if (StringUtils.isNotBlank(strArr[36])) {
                oc.setFromPlace(strArr[36]);
            }
            //目的港
            if (StringUtils.isNotBlank(strArr[37])) {
                oc.setToPort(strArr[37]);
            }
            //目的国
            if (StringUtils.isNotBlank(strArr[38])) {
                oc.setToCountry(strArr[38]);
            }
            //目的地
            if (StringUtils.isNotBlank(strArr[39])) {
                oc.setToPlace(strArr[39]);
            }
            if (StringUtils.isNotBlank(strArr[40])) {
                try {
                    oc.setTotalPrice(new BigDecimal(strArr[40]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_CHECK.getTable(), cellIndex, "合同总价非数字");
                    continue;
                }
            }
            //币种
            if (StringUtils.isNotBlank(strArr[41])) {
                oc.setCurrencyBn(strArr[41]);
            }
            //是否含税
            if (StringUtils.isNotBlank(strArr[42])) {
                oc.setTaxBearing(Integer.parseInt(strArr[42]));
            }
            //  汇率  strArr[43]
            //合同总价
            if (StringUtils.isNotBlank(strArr[44])) {
                try {
                    oc.setTotalPriceUsd(new BigDecimal(strArr[44]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_CHECK.getTable(), cellIndex, "合同总价（美元）非数字");
                    continue;
                }
            }
            if (StringUtils.isNotBlank(strArr[45])) {
                oc.setPaymentModeBn(strArr[45]);
            }
            if (StringUtils.isNotBlank(strArr[46])) {
                try {
                    oc.setQualityFunds(new BigDecimal(strArr[46]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_CHECK.getTable(), cellIndex, "质保金 非数字");
                    continue;
                }
            }
            OrderPayment orderPayment = new OrderPayment();
            ArrayList<OrderPayment> paymentList = new ArrayList<>();
            if (StringUtils.isNotBlank(strArr[47])) {
                try {
                    orderPayment.setMoney(new BigDecimal(strArr[47]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_CHECK.getTable(), cellIndex, "预收款 非数字");
                    continue;
                }
                orderPayment.setType(1);
                if (StringUtils.isNotBlank(strArr[48])) {
                    orderPayment.setReceiptDate(DateUtil.parseString2DateNoException(strArr[48], "yyyy-MM-dd"));
                }
                paymentList.add(orderPayment);
            }
            if (StringUtils.isNotBlank(strArr[49])) {
                try {
                    //发货前收款
                    orderPayment.setMoney(new BigDecimal(strArr[49]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_CHECK.getTable(), cellIndex, "发货前收款 非数字");
                    continue;
                }
                orderPayment.setType(2);
                //收款日期
                if (StringUtils.isNotBlank(strArr[50])) {
                    orderPayment.setReceiptDate(DateUtil.parseString2DateNoException(strArr[50], "yyyy-MM-dd"));
                }
                paymentList.add(orderPayment);
            }

            if (StringUtils.isNotBlank(strArr[51])) {
                int type = Integer.parseInt(strArr[51]);
                orderPayment.setType(type);
                //收款日期
                if (StringUtils.isNotBlank(strArr[52])) {
                    int days = Integer.parseInt(strArr[52]);
                    orderPayment.setReceiptTime(days);
                }
                try {
                    if (StringUtils.isNotBlank(strArr[53])) {
                        orderPayment.setMoney(new BigDecimal(strArr[53]));
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_CHECK.getTable(), cellIndex, "收款金额 非数字");
                    continue;
                }
                orderPayment.setType(1);
                paymentList.add(orderPayment);
            }
            if (StringUtils.isNotBlank(strArr[54])) {
                oc.setDeliveryRequires(strArr[54]);
            } else if (StringUtils.isNotBlank(strArr[55])) {
                oc.setCustomerContext(strArr[55]);
            }
            try {
                orderDao.save(oc);
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_CHECK.getTable(), cellIndex, e.getMessage());
                continue;
            }
            orderCount++;
            response.incrSuccess();
        }
        response.getFailItems();
        response.getSumMap().put("orderCount", new BigDecimal(orderCount)); // 订单总数量
        response.setDone(true);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImportDataResponse importProjectData(List<String[]> datas, boolean testOnly) {
        ImportDataResponse response = new ImportDataResponse(new String[]{"projectAccount"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        Project project = null;
        //Order order = null;
        // 订单总数量
        int projectCount = 0;
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2; // 数据从第二行开始
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.PROJECT_MANAGE, response, cellIndex)) {
                continue;
            }
            if (StringUtils.isNotBlank(strArr[0])) {
                int orderId = Integer.parseInt(strArr[0]);
                //order = orderDao.findOne(orderId);
                project = projectDao.findByIdOrOrderId(null, orderId);
            }
            //添加项目
            if (StringUtils.isNotBlank(strArr[1])) {
                project.setContractNo(strArr[1]);
            }
            //项目开始日期
            if (StringUtils.isNotBlank(strArr[2])) {
                project.setStartDate(DateUtil.parseString2DateNoException(strArr[2], "yyyy-MM-dd"));
            }
            //项目名称
            if (StringUtils.isNotBlank(strArr[3])) {
                project.setProjectName(strArr[3]);
            }
            //执行约定交付日期
            if (StringUtils.isNotBlank(strArr[4])) {
                project.setDeliveryDate(strArr[4]);
            }
            //合同总价
            if (StringUtils.isNotBlank(strArr[5])) {
                try {
                    project.setTotalPriceUsd(new BigDecimal(strArr[5]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROJECT_MANAGE.getTable(), cellIndex, "合同总价（美元）非数字");
                    continue;
                }
            }
            //初步利润
            if (StringUtils.isNotBlank(strArr[6])) {
                try {
                    project.setProfitPercent(new BigDecimal(strArr[6]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROJECT_MANAGE.getTable(), cellIndex, "初步利润（美元）非数字");
                    continue;
                }
            }
            //利率
            if (StringUtils.isNotBlank(strArr[7])) {
                try {
                    project.setProfit(new BigDecimal(strArr[7]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROJECT_MANAGE.getTable(), cellIndex, "利润（美元）非数字");
                    continue;
                }
            }
            //执行变更日期
            if (StringUtils.isNotBlank(strArr[8])) {
                project.setExeChgDate(DateUtil.parseString2DateNoException(strArr[8], "yyyy-MM-dd"));
            }
            //要求采购到货日期
            if (StringUtils.isNotBlank(strArr[9])) {
                project.setRequirePurchaseDate(DateUtil.parseString2DateNoException(strArr[9], "yyyy-MM-dd"));
            }
            //有无项目经理
            if (StringUtils.isNotBlank(strArr[10])) {
                project.setHasManager(Integer.parseInt(strArr[10]));
            }
            // 下发部门id
            if (StringUtils.isNotBlank(strArr[11])) {
                project.setSendDeptId(Integer.parseInt(strArr[11]));
            }
            //下发部门strArr[12]
            //项目状态
            if (StringUtils.isNotBlank(strArr[13])) {
                project.setProjectStatus(strArr[13]);
            }
            //采购经办人id
            if (StringUtils.isNotBlank(strArr[14])) {
                project.setPurchaseUid(Integer.parseInt(strArr[14]));
            }
            //采购经办人
            if (StringUtils.isNotBlank(strArr[15])) {
                project.setPurchaseName(strArr[15]);
            }
            //品控经办人id
            if (StringUtils.isNotBlank(strArr[16])) {
                project.setQualityUid(Integer.parseInt(strArr[16]));
            }
            //品控经办人
            if (StringUtils.isNotBlank(strArr[17])) {
                project.setQualityName(strArr[17]);
            }
            //商务技术经办人id
            if (StringUtils.isNotBlank(strArr[18])) {
                project.setBusinessUid(Integer.parseInt(strArr[18]));
            }
            //商务技术经办人
            if (StringUtils.isNotBlank(strArr[19])) {
                project.setBusinessName(strArr[19]);
            }
            // 交付配送中心经理id
            if (StringUtils.isNotBlank(strArr[20])) {
                project.setManagerUid(Integer.parseInt(strArr[20]));
            }
            //交付配送中心经理 strArr[21]
            if (strArr.length > 22) {
                if (StringUtils.isNotBlank(strArr[22])) {
                    project.setRemarks(strArr[22]);
                }
            }
            try {
                projectDao.save(project);
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.PROJECT_MANAGE.getTable(), cellIndex, e.getMessage());
                continue;
            }
            response.incrSuccess();
        }
        response.getFailItems();
        response.getSumMap().put("projectCount", new BigDecimal(projectCount)); // 订单总数量
        response.setDone(true);
        return response;
    }

}
