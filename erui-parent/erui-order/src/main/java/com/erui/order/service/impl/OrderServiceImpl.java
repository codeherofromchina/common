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
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    @Value("#{orderProp[DING_SEND_SMS]}")
    private String dingSendSms;  //发钉钉通知接口

    @Autowired
    private ComplexOrderDao complexOrderDao;

    @Override
    @Transactional(readOnly = true)
    public Order findById(Integer id) {
        Order order = orderDao.findOne(id);
        if (order != null) {
            if (order.getProject() != null && order.getProject().getAuditingStatus() != null && order.getProject().getAuditingStatus() == 4) {
                order.setProAuditStatus(1);
            }
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
            if (order.getProject() != null && order.getProject().getAuditingStatus() != null && order.getProject().getAuditingStatus() == 4) {
                order.setProAuditStatus(1);
            }
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
        if(subtract.compareTo(BigDecimal.valueOf(0)) != -1 ){    //-1 小于     0 等于      1 大于x
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
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), new Sort(Sort.Direction.DESC, "createTime"));
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
                            if (condition.getCreateUserId() != null) {
                                list.add(cb.or(cb.equal(root.get("createUserId").as(Integer.class),
                                        condition.getCreateUserId())));
                            }
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
                if (vo.getDeliverConsignC() && vo.getStatus() != null && vo.getStatus() == Order.StatusEnum.EXECUTING.getCode()) {
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
            CheckLog checkLog = checkLogService.findLogOne(order.getId());
            switch (curAuditProcess) {
                case 0:
                   /* auditingProcess_i = "1";
                    auditingUserId_i = addOrderVo.getPerLiableRepayId().toString();
                    auditorIds.append("," + auditingUserId_i + ",");
                    addOrderVo.copyBaseInfoTo(order);
                    if (addOrderVo.getTotalPriceUsd() != null && addOrderVo.getOrderCategory() != 6) {
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
                    order.setOrderPayments(addOrderVo.getContractDesc());
                    order.setAttachmentSet(addOrderVo.getAttachDesc());
                    if (order.getId() != null) {
                        order.getProject().setExecCoName(order.getExecCoName());
                        order.getProject().setBusinessUid(order.getTechnicalId());
                        order.getProject().setExecCoName(order.getExecCoName());
                        order.getProject().setBusinessUnitName(order.getBusinessUnitName());
                        order.getProject().setSendDeptId(order.getBusinessUnitId());
                        order.getProject().setRegion(order.getRegion());
                        order.getProject().setCountry(order.getCountry());
                        order.getProject().setTotalPriceUsd(order.getTotalPriceUsd());
                        order.getProject().setDistributionDeptName(order.getDistributionDeptName());
                        order.getProject().setBusinessName(order.getBusinessName());
                        order.getProject().setCreateTime(new Date());
                    }*/
                    break;
                case 1:
                    /* if (checkLog != null && "-1".equals(checkLog.getOperation())) {
                        auditingProcess_i = checkLog.getNextAuditingProcess();
                        auditingUserId_i = checkLog.getNextAuditingUserId();
                        auditorIds.append("," + auditingUserId_i + ",");

                    } else {
                    }*/
                    //如果是国内订单 没有国家负责人 直接法务审核
                    if (order.getOrderCategory() == 6) {
                        auditingProcess_i = "8";
                        auditingUserId_i = "28107";
                        auditorIds.append("," + auditingUserId_i + ",");
                    } else {
                        auditingProcess_i = "2";
                        auditingUserId_i = order.getCountryLeaderId().toString();
                        auditorIds.append("," + auditingUserId_i + ",");
                    }
                    break;
                //国家负责人审批
                case 2:
                    //根据订单金额判断 填写审批人级别
                    //国家负责人审核完成交给法务审核
                    auditingProcess_i = "8";
                    auditingUserId_i = "28107";
                    auditorIds.append("," + auditingUserId_i + ",");
                    break;
                case 8: // 法务审核 20181211法务审核由 31025 崔荣光修改为 赵明 28107
                    Map<String, Integer> companyMap = new ImmutableMap.Builder<String, Integer>()
                            .put("Erui International USA, LLC", 1)
                            .put("Erui International (Canada) Co., Ltd.", 2)
                            .put("Erui Intemational Electronic Commerce (HK) Co., Lirnited", 3)
                            .put("PT ERUI INTERNATIONAL INDONESIA", 4)
                            .put("Erui Intemational Electronic Commerce (Peru) S.A.C", 5)
                            .build();
                    // 添加销售合同号
                    String contractNo = null;
                    if (companyMap.containsKey(order.getSigningCo())) {
                        String prefix = "YRX" + DateUtil.format("yyyyMMdd", new Date());
                        String lastContractNo = orderDao.findLastContractNo(prefix);
                        if (StringUtils.isBlank(lastContractNo)) {
                            contractNo = StringUtil.genContractNo(null);
                        } else {
                            contractNo = StringUtil.genContractNo(lastContractNo);
                        }

                    } else if (StringUtils.equals("Erui International Electronic Commerce Co., Ltd.", order.getSigningCo())) {
                        String prefix = "YRHWX" + DateUtil.format("yyyyMMdd", new Date());
                        String lastContractNo = orderDao.findLastContractNo(prefix);
                        if (StringUtils.isBlank(lastContractNo)) {
                            contractNo = StringUtil.genContractNo02(null);
                        } else {
                            contractNo = StringUtil.genContractNo02(lastContractNo);
                        }
                    } else {
                        contractNo = addOrderVo.getContractNo();
                    }
                    if (order.getOrderCategory() != 3 && !StringUtils.isBlank(contractNo)) {
                        // 销售合同号不能为空
                        // 判断销售合同号不能重复
                        List<Integer> contractNoProjectIds = orderDao.findByContractNo(contractNo);
                        if (contractNoProjectIds != null && contractNoProjectIds.size() > 0) {
                            Integer orderId = order.getId();
                            for (Integer oId : contractNoProjectIds) {
                                if (oId.intValue() != orderId.intValue()) {
                                    return false;
                                }
                            }
                        }
                        order.setContractNo(contractNo);
                        order.getProject().setContractNo(contractNo);
                    }
                    //根据订单金额判断 填写审批人级别
                    if (order.getOrderCategory() == 6) {
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
                    if (STEP_ONE_PRICE.doubleValue() <= order.getTotalPriceUsd().doubleValue() && order.getTotalPriceUsd().doubleValue() < STEP_TWO_PRICE.doubleValue()) {
                        if (order.getFinancing() == null || order.getFinancing() == 0) {
                            //若不是融资项目 且订单金额小于10万-300万美元 提交至商品添加
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
                        //订单金额大于300万 交给区域VP审核
                        auditingProcess_i = "4";
                        if (order.getAreaVpId() != null)
                            if (order.getAreaVpId() != null)
                                auditingUserId_i = order.getAreaVpId().toString();
                        auditorIds.append("," + auditingUserId_i + ",");
                    }
                    break;
                //区域VP
                case 4:
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
                    break;
                //是否融资项目 是 融资审核
                case 5:
                    auditingProcess_i = "6";
                    //设置项目审核流程
                    order.getProject().setAuditingProcess(auditingProcess_i);
                    auditingUserId_i = order.getTechnicalId().toString();//提交到商务技术经办人
                    auditorIds.append("," + auditingUserId_i + ",");
                    break;
                //提交商品
                case 6:
                    order.setGoodsList(updateOrderGoods(addOrderVo));
                    order.setLogiQuoteNo(addOrderVo.getLogiQuoteNo());
                    //订单审核完成后项目才能办理项目
                    order.getProject().setAuditingStatus(1);

                    auditingStatus_i = 4; // 完成
                    auditingProcess_i = "6";// 订单审核完成 无下一审核进度和审核人
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
        sendDingtalk(order, auditingUserId_i);
        order.setAuditingStatus(auditingStatus_i);
        order.setAudiRemark(auditorIds.toString());
        orderDao.save(order);
        return true;
    }

    private void reProject(CheckLog checkLog, Project project, Order order) {
        project.setAuditingStatus(2); // 设置项目为审核中
        project.setAuditingUserId(checkLog.getNextAuditingUserId());
        project.setAuditingProcess(checkLog.getNextAuditingProcess());
        order.setStatus(2);
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

        if (addOrderVo.getTotalPriceUsd() != null && addOrderVo.getOrderCategory() != 6) {
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
            order.setAuditingProcess(1);
            order.setAuditingUserId(addOrderVo.getPerLiableRepayId().toString());
            //20181022需求变更 预投和试用回款责任人必填
           /* if (StringUtils.isNotBlank(addOrderVo.getPerLiableRepay()) && (addOrderVo.getOrderCategory() != 1 && addOrderVo.getOrderCategory() != 2)) {
            } else {
                order.setAuditingUserId(addOrderVo.getCountryLeaderId().toString());
                order.setAuditingProcess(2);
            }*/
        }
        CheckLog checkLog_i = null; // 审核日志
        Order orderUpdate = orderDao.saveAndFlush(order);
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            checkLog_i = fullCheckLogInfo(order.getId(), 0, orderUpdate.getCreateUserId(), orderUpdate.getCreateUserName(), orderUpdate.getAuditingProcess().toString(), orderUpdate.getPerLiableRepayId().toString(), addOrderVo.getAuditingReason(), "1", 1);
           /* if (orderUpdate.getPerLiableRepayId() != null) {
            } else {
                checkLog_i = fullCheckLogInfo(order.getId(), 0, orderUpdate.getCreateUserId(), orderUpdate.getCreateUserName(), orderUpdate.getAuditingProcess().toString(), orderUpdate.getCountryLeaderId().toString(), null, "1", 1);
            }*/
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
            Project projectAdd = null;
            if (order.getProject() == null) {
                projectAdd = new Project();
            } else {
                projectAdd = order.getProject();
            }
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
            projectAdd.setAuditingStatus(0);
            //商务技术经办人名称
            Project project = projectDao.save(projectAdd);
            //添加项目利润核算单信息
            ProjectProfit projectProfit = null;
            if (project.getProjectProfit() == null) {
                projectProfit = new ProjectProfit();
            } else {
                projectProfit = project.getProjectProfit();
            }
            projectProfit.setProject(project);
            projectProfit.setCountry(orderUpdate.getCountry());
            projectProfit.setTradeTerm(orderUpdate.getTradeTerms());
            projectProfit.setContractAmountUsd(orderUpdate.getTotalPriceUsd());
            //projectProfit.setExchangeRate(orderUpdate.getExchangeRate());
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
            //钉钉通知回款责任人审批
            sendDingtalk(order, addOrderVo.getPerLiableRepayId().toString());
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
        if (addOrderVo.getTotalPriceUsd() != null && addOrderVo.getOrderCategory() != null && addOrderVo.getOrderCategory() != 6) {
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
            order.setAuditingProcess(1);
            order.setAuditingStatus(2);
            order.setAuditingUserId(addOrderVo.getPerLiableRepayId().toString());
        }
        CheckLog checkLog_i = null;//审批流日志
        Order order1 = orderDao.save(order);
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            checkLog_i = fullCheckLogInfo(order.getId(), 0, order1.getCreateUserId(), order1.getCreateUserName(), order1.getAuditingProcess().toString(), order1.getPerLiableRepayId().toString(), addOrderVo.getAuditingReason(), "1", 1);
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
            project.setAuditingStatus(0);
            //projectAdd.setProjectProfit(projectProfit);
            Project project1 = projectDao.save(project);
            //添加项目利润核算单信息
            ProjectProfit projectProfit = new ProjectProfit();
            projectProfit.setProject(project1);
            projectProfit.setCountry(order1.getCountry());
            projectProfit.setTradeTerm(order1.getTradeTerms());
            projectProfit.setContractAmountUsd(order1.getTotalPriceUsd());
            //projectProfit.setExchangeRate(order1.getExchangeRate());
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
            //钉钉通知回款责任人审批人
            sendDingtalk(order, addOrderVo.getPerLiableRepayId().toString());
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
        //获取token
        final String eruiToken = (String) ThreadLocalUtil.getObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //订单下达后通知商务技术经办人
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
        }).start();

    }

    //销售订单钉钉通知 审批人
    public void sendDingtalk(Order order, String user) throws Exception {
        //获取token
        final String eruiToken = (String) ThreadLocalUtil.getObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("发送短信的用户token:" + eruiToken);
                //if (StringUtils.isNotBlank(eruiToken)) {
                //try {
                // 根据id获取商务经办人信息
                String jsonParam = "{\"id\":\"" + user + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                //header.put("Cookie", "auth=adf73QWyZ/BwVqlooWdK0mUHiVS/iEEESkGlt8PrD1C1zDU18EqWBm5QUvA; language=zh; eruirsakey=ed55d2b71d144c0eb6ef45e6793730f9; eruitoken=952c880ba8bef88952307839250094e9; JSESSIONID=4C76CAEF4EDE097918BA1D8E42E2C554");
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
                String userName = null;
                String userNo = null;
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    //获取通知者姓名
                    userName = data.getString("name");
                    userNo = data.getString("user_no");
                    //发送钉钉通知
                    Long startTime = System.currentTimeMillis();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("type=userNo");
                    stringBuffer.append("&message=您好！" + order.getAgentName() + "的订单，已申请销售合同审批。CRM客户代码：" + order.getCrmCode() + "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！" +
                            "" + startTime + "");
                    stringBuffer.append("&toUser=").append(userNo);
                    String s1 = HttpRequest.sendPost(dingSendSms, stringBuffer.toString(), header2);
                    Long endTime = System.currentTimeMillis();
                    System.out.println("发送通知耗费时间：" + (endTime - startTime) / 1000);
                    logger.info("发送钉钉通知返回状态" + s1);
                }
                //} catch (Exception e) {
                //    throw new MyException(String.format("%s%s%s", "发送钉钉通知异常失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Sending SMS exceptions to failure"));
                //}

                //  }
            }
        }).start();

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
        StringBuilder existsContractNo = new StringBuilder();
        StringBuilder errorContractNo = new StringBuilder();
        Map<String, Integer> map = new HashMap<String, Integer>();
        int size = datas.size();
        Order oc = null;
        //PurchRequisition purchRequisition = null;
        // 订单总数量
        int orderCount = 0;
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2; // 数据从第二行开始
            String[] strArr = datas.get(index);
            if (map.containsKey(strArr[2])) {
                response.setOtherMsg("列表中重复的销售合同号：" + strArr[2]);
                return response;
            }
            map.put(strArr[2], index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.ORDER_MANAGE, response, cellIndex)) {
                continue;
            }
            List<Integer> contractNoProjectIds = orderDao.findByContractNo(strArr[2]);
            if (contractNoProjectIds != null && contractNoProjectIds.size() > 0) {
                existsContractNo.append(strArr[2] + ";");
                if (contractNoProjectIds.size() > 1) {
                    errorContractNo.append(strArr[2] + ";");
                    continue;
                }
                Order byContractNoOrder = orderDao.findByContractNoOrId(strArr[2], null);
                oc = orderDao.save(byContractNoOrder);
                  /*  List<Goods> goodsList = order.getGoodsList();
                    for (Goods gs:goodsList) {
                        goodsDao.delete(gs);
                    }*/
            } else {
                oc = new Order();
            }
            //purchRequisition = new PurchRequisition();

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
            oc.setInquiryNo(strArr[6]);
            //订单签约日期
            if (strArr[7] != null) {
                Date signingDate = DateUtil.parseString2DateNoException(strArr[7], "yyyy-MM-dd");
                oc.setSigningDate(signingDate);
            }
            //合同交货日期
            if (strArr[8] != null) {
                oc.setDeliveryDate(strArr[8]);
            }
            //市场经办人员工编号
            if (strArr[9] != null) {
                oc.setAgentId(Integer.parseInt(strArr[9]));
            }
            if (strArr[10] != null) {
                oc.setAcquireId(Integer.parseInt(strArr[10]));
            }
            oc.setSigningCo(strArr[11]);
            /*if (strArr[13] != null) {
                oc.setBusinessUnitId(Integer.parseInt(strArr[13]));
            }*/
            if (strArr[12] != null) {
                oc.setBusinessUnitId(Integer.parseInt(strArr[12]));
            }
            oc.setBusinessUnitName(strArr[56]);
           /* if (strArr[14] != null) {
                oc.setExecCoId(Integer.parseInt(strArr[14]));
            }*/
            //执行分公司id
            if (strArr[13] != null) {
                oc.setExecCoId(Integer.parseInt(strArr[13]));
            }
            oc.setRegion(strArr[14]);
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
            oc.setDistributionDeptName(strArr[15]);
            // 国家
            oc.setCountry(strArr[16]);
            //订单号
            oc.setCrmCode(strArr[17]);
            //客户类型
            if (strArr[18] != null) {
                oc.setCustomerType(Integer.parseInt(strArr[18]));
            }
            //回款责任人
            if (strArr[19] != null) {
                oc.setPerLiableRepayId(Integer.parseInt(strArr[19]));
            }
            if (strArr[20] != null) {
                oc.setTechnicalId(Integer.parseInt(strArr[20]));
                oc.setTechnicalId(Integer.parseInt(strArr[20]));
            }
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
            oc.setToPort(strArr[27]);
            oc.setToCountry(strArr[28]);
            oc.setToPlace(strArr[29]);
            //合同总价
            if (strArr[30] != null) {
                try {
                    oc.setTotalPrice(new BigDecimal(strArr[30]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "合同总价非数字");
                    continue;
                }
            }
            oc.setCurrencyBn(strArr[31]);
            //是否含税
            if (strArr[32] != null) {
                oc.setTaxBearing(Integer.parseInt(strArr[32]));
            }
            //  汇率  strArr[29]
            if (strArr[33] != null) {
                oc.setExchangeRate(new BigDecimal(strArr[33]));
            }
            if (strArr[34] != null) {
                try {
                    oc.setTotalPrice(new BigDecimal(strArr[34]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "合同总价（美元）非数字");
                    continue;
                }
            }
            oc.setPaymentModeBn(strArr[35]);
            if (strArr[36] != null) {
                try {
                    oc.setQualityFunds(new BigDecimal(strArr[36]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "质保金 非数字");
                    continue;
                }
            }
            OrderPayment orderPayment = new OrderPayment();
            ArrayList<OrderPayment> paymentList = new ArrayList<>();
            if (strArr[37] != null) {
                try {
                    orderPayment.setMoney(new BigDecimal(strArr[37]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "预收款 非数字");
                    continue;
                }
                orderPayment.setType(1);
                orderPayment.setReceiptDate(DateUtil.parseString2DateNoException(strArr[38], "yyyy-MM-dd"));
                paymentList.add(orderPayment);
            }
            oc.setDeliveryRequires(strArr[39]);
            oc.setCustomerContext(strArr[40]);
            //执行分公司
            oc.setExecCoName(strArr[57]);
            //订单创建日期
            if (strArr[41] != null) {
                Date createDate = DateUtil.parseString2DateNoException(strArr[41], "yyyy-MM-dd");
                oc.setCreateTime(createDate);
            }
            oc.setBusinessUnitId(9970);
            oc.setAuditingProcess(null);
            oc.setAuditingStatus(4);
            oc.setStatus(3);
            oc.setDeleteFlag(Boolean.FALSE);
            Order order = null;
            try {
                order = orderDao.save(oc);
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, e.getMessage());
                continue;
            }
            //添加项目
            Project project = null;
            if (order.getProject() == null) {
                project = new Project();
            } else {
                project = order.getProject();
            }
            project.setOrder(order);
            project.setContractNo(strArr[2]);
            //项目创建日期和开始日期
            if (strArr[41] != null) {
                Date createDate = DateUtil.parseString2DateNoException(strArr[41], "yyyy-MM-dd");
                project.setCreateTime(createDate);
                project.setStartDate(createDate);
            }
            project.setProjectName(strArr[42]);
            // 国家
            project.setCountry(strArr[16]);
            //执行约定交付日期
            project.setDeliveryDate(strArr[43]);
            //合同总价
            if (strArr[44] != null) {
                try {
                    project.setTotalPriceUsd(new BigDecimal(strArr[44]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "合同总价（美元）非数字");
                    continue;
                }
            }
            if (strArr[45] != null) {
                try {
                    project.setProfitPercent(new BigDecimal(strArr[45]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "初步利润（美元）非数字");
                    continue;
                }
            }
            if (strArr[46] != null) {
                try {
                    project.setProfit(new BigDecimal(strArr[46]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "利润额（美元）非数字");
                    continue;
                }
            }
            //执行分公司
            if (strArr[14] != null) {
                project.setExecCoName(order.getExecCoName());
            }
            project.setRegion(strArr[15]);
            //执行变更日期
            if (strArr[47] != null) {
                project.setExeChgDate(DateUtil.parseString2DateNoException(strArr[47], "yyyy-MM-dd"));
            }
            //有无项目经理
            if (strArr[48] != null) {
                project.setHasManager(Integer.parseInt(strArr[48]));
            }
            project.setBusinessUnitName(strArr[56]);
            if (strArr[49] != null) {
                project.setSendDeptId(Integer.parseInt(strArr[49]));
            }
            project.setProjectStatus(strArr[50]);
            if (strArr[51] != null) {
                project.setPurchaseUid(Integer.parseInt(strArr[51]));
            }
            if (strArr[52] != null) {
                project.setQualityUid(Integer.parseInt(strArr[52]));
            }
            if (strArr[43] != null) {
                project.setBusinessUid(Integer.parseInt(strArr[53]));
            }
            if (project.getHasManager() == 1 && strArr[54] != null) {
                project.setManagerUid(Integer.parseInt(strArr[54]));
            }
            if (strArr[0] != null) {
                project.setOrderCategory(Integer.parseInt(strArr[0]));
            }
            if (strArr[1] != null) {
                project.setOverseasSales(Integer.parseInt(strArr[1]));
            }
            project.setRemarks(strArr[55]);
            project.setAuditingProcess(null);
            project.setPurchDone(false);
            project.setAuditingStatus(4);
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
        response.setOtherMsg("销售合同号大于1" + errorContractNo.toString() + "; " + "销售合同号已存在：" + existsContractNo.toString());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImportDataResponse importGoods(List<String[]> datas, boolean testOnly) {
        ImportDataResponse response = new ImportDataResponse(new String[]{"projectAccount"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        //问题销售合同号
        Set<String> problemContractNo = new HashSet<>();
        Set<String> problemContractNo2 = new HashSet<>();
        List<Goods> data = new ArrayList<>();
        //PurchRequisition purchRequisition = null;
        // 订单总数量
        int orderCount = 0;
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2; // 数据从第二行开始
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.GOODS_MANAGE, response, cellIndex)) {
                continue;
            }
            List<Integer> contractNoProjectIds = new ArrayList<>();
            if (orderDao.findByContractNo(strArr[0]) != null) {
                contractNoProjectIds = orderDao.findByContractNo(strArr[0]);
            }
            if (contractNoProjectIds != null && contractNoProjectIds.size() == 1) {
                Order byContractNoOrId = orderDao.findOne(contractNoProjectIds.get(0));
                if (byContractNoOrId.getProject().getPurchRequisition() == null) {
                    if (byContractNoOrId.getGoodsList() != null && byContractNoOrId.getGoodsList().size() > 0) {
                        goodsDao.deleteInBatch(byContractNoOrId.getGoodsList());
                    }
                    Goods goods = new Goods();
                    goods.setOrder(byContractNoOrId);
                    goods.setProject(byContractNoOrId.getProject());
                    if (strArr[0] != null) {
                        goods.setContractNo(strArr[0]);
                    }
                    if (strArr[2] != null) {
                        goods.setSku(strArr[2]);
                    }
                    goods.setNameEn(strArr[3]);
                    goods.setNameZh(strArr[4]);
                    goods.setMeteName(strArr[5]);
                    if (strArr[6] != null) {
                        goods.setContractGoodsNum(Integer.parseInt(strArr[6]));
                    }
                    //单价
                    if (strArr[7] != null) {
                        try {
                            goods.setPrice(new BigDecimal(strArr[7]));
                        } catch (Exception ex) {
                            logger.error(ex.getMessage());
                            response.incrFail();
                            response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), cellIndex, "商品价格非数字");
                            continue;
                        }
                    }
                    goods.setDepartment(strArr[8]);
                    goods.setUnit(strArr[9]);
                    goods.setBrand(strArr[10]);
                    goods.setModel(strArr[11]);
                    goods.setSendNum(0);
                    goods.setPurchasedNum(0);
                    goods.setPrePurchsedNum(0);
                    goods.setInspectNum(0);
                    goods.setInstockNum(0);
                    goods.setOutstockApplyNum(0);
                    goods.setExchanged(false);
                    try {
                        if (data.size() == 50) {
                            goodsDao.save(data);
                            data.clear();
                        }
                        data.add(goods);
                    } catch (Exception e) {
                        response.incrFail();
                        response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), data.size(), e.getMessage());
                    }
                } else {
                    problemContractNo.add(strArr[0]);
                }

            } else {
                problemContractNo2.add(strArr[0]);
            }
            response.incrSuccess();
            orderCount++;
        }
        try {
            if (!data.isEmpty()) {
                goodsDao.save(data);
            }
        } catch (Exception e) {
            response.incrFail();
            response.pushFailItem(ExcelUploadTypeEnum.ORDER_MANAGE.getTable(), data.size(), e.getMessage());
        }
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        for (String s : problemContractNo) {
            stringBuilder.append(s + ";");
        }
        for (String s : problemContractNo2) {
            stringBuilder2.append(s + ";");
        }
        System.out.println("库里不存在" + stringBuilder2.toString());
        response.setOtherMsg("已走到采购申请" + stringBuilder.toString());
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

    @Override
    public void addOrderContract(XSSFWorkbook workbook, Map<String, Object> results) {
        Order orderDec = (Order) results.get("orderDesc");
        // 获取第二个sheet页
        Sheet sheet1 = workbook.getSheetAt(0);
        //替换 填写签约主体公司
        if (orderDec.getSigningCo() != null) {
            String stringCell0 = sheet1.getRow(0).getCell(0).getStringCellValue().replace("填写签约主体公司", Order.COM.getByEn(orderDec.getSigningCo()).getMsg());
            sheet1.getRow(0).getCell(0).setCellValue(stringCell0);
        }
        if (orderDec.getExecCoId() != null) {
            Company company = companyService.findByIdLazy(orderDec.getExecCoId());
            String stringRC2 = sheet1.getRow(2).getCell(2).getStringCellValue().replace("执行分公司", company.getName());
            sheet1.getRow(2).getCell(2).setCellValue(stringRC2);
        }
        //项目名称
        if (orderDec.getProject() != null && orderDec.getProject().getProjectName() != null) {
            String stringR2C6 = sheet1.getRow(2).getCell(6).getStringCellValue().replace("项目名称", orderDec.getProject().getProjectName());
            sheet1.getRow(2).getCell(6).setCellValue(stringR2C6);
        }
        //销售合同号
        if (orderDec.getContractNo() != null) {
            String stringR2C6 = sheet1.getRow(2).getCell(9).getStringCellValue().replace("法律事务部取号", orderDec.getContractNo());
            sheet1.getRow(2).getCell(9).setCellValue(stringR2C6);
        }
        //客户代码
        if (orderDec.getCrmCode() != null) {
            String stringR3C1 = sheet1.getRow(3).getCell(2).getStringCellValue().replace("市场填写", orderDec.getCrmCode());
            sheet1.getRow(3).getCell(2).setCellValue(stringR3C1);
        }
        //询单号
        if (orderDec.getInquiryNo() != null) {
            String stringR3C4 = sheet1.getRow(3).getCell(4).getStringCellValue().replace("询单号", orderDec.getInquiryNo());
            sheet1.getRow(3).getCell(4).setCellValue(stringR3C4);
        }
        //是否预投
        if (orderDec.getOrderCategory() != null && orderDec.getOrderCategory() == 1) {
            String stringR3C6 = sheet1.getRow(3).getCell(6).getStringCellValue().replace("□", sheet1.getRow(3).getCell(11).getStringCellValue());
            sheet1.getRow(3).getCell(6).setCellValue(stringR3C6);
        } else {
            String stringR3C7 = sheet1.getRow(3).getCell(7).getStringCellValue().replace("□", sheet1.getRow(3).getCell(11).getStringCellValue());
            sheet1.getRow(3).getCell(7).setCellValue(stringR3C7);
        }
        //项目编号：业务单元项目执行单号
        if (orderDec.getProjectNo() != null) {
            String stringR3C9 = sheet1.getRow(3).getCell(9).getStringCellValue().replace("业务单元项目执行单号", orderDec.getProjectNo());
            sheet1.getRow(3).getCell(9).setCellValue(stringR3C9);
        }
        //是否融资
        if (orderDec.getFinancing() != null && orderDec.getFinancing() == 1) {
            String stringR4C2 = sheet1.getRow(4).getCell(2).getStringCellValue().replace("□", sheet1.getRow(3).getCell(11).getStringCellValue());
            sheet1.getRow(4).getCell(2).setCellValue(stringR4C2);
        } else {
            String stringR4C4 = sheet1.getRow(4).getCell(4).getStringCellValue().replace("□", sheet1.getRow(3).getCell(11).getStringCellValue());
            sheet1.getRow(4).getCell(4).setCellValue(stringR4C4);
        }
        //物流报价单编号：市场填写
        if (orderDec.getLogiQuoteNo() != null) {
            String stringR4C8 = sheet1.getRow(4).getCell(8).getStringCellValue().replace("市场填写", orderDec.getLogiQuoteNo());
            sheet1.getRow(4).getCell(8).setCellValue(stringR4C8);
        }
        //贸易术语
        if (orderDec.getTradeTerms() != null) {
            sheet1.getRow(4).getCell(10).setCellValue(orderDec.getTradeTerms());
        }
        //物流报价单编号：市场填写
        if (orderDec.getCurrencyBn() != null) {
            String stringR5C2 = sheet1.getRow(5).getCell(2).getStringCellValue().replace("币种：              ", "币种：" + orderDec.getCurrencyBn() + "  ");
            sheet1.getRow(5).getCell(2).setCellValue(stringR5C2);
        }
        //合同总价
        if (orderDec.getTotalPrice() != null) {
            String stringR5C2 = sheet1.getRow(5).getCell(2).getStringCellValue().replace("小写：        ", "小写：" + orderDec.getTotalPrice() + "  ");
            sheet1.getRow(5).getCell(2).setCellValue(stringR5C2);
        }
        //是否含税
        if (orderDec.getTaxBearing() != null && orderDec.getTaxBearing() == 1) {
            String stringR5C2 = sheet1.getRow(5).getCell(2).getStringCellValue().replace("□ 含税", sheet1.getRow(3).getCell(11).getStringCellValue() + "含税");
            sheet1.getRow(5).getCell(2).setCellValue(stringR5C2);
        } else {
            String stringR5C2 = sheet1.getRow(5).getCell(2).getStringCellValue().replace("□不含税", sheet1.getRow(3).getCell(11).getStringCellValue() + "不含税");
            sheet1.getRow(5).getCell(2).setCellValue(stringR5C2);
        }
        //合同交货日期
        if (orderDec.getDeliveryDate() != null) {
            sheet1.getRow(6).getCell(2).setCellValue(orderDec.getDeliveryDate());
        }
        //是有预收款
        if (orderDec.getOrderPayments() != null) {
            List<OrderPayment> orderPayments = orderDec.getOrderPayments();
            StringBuilder stringBuilder = new StringBuilder();
            switch (orderDec.getPaymentModeBn()) {
                case "1":
                    stringBuilder.append("收款方式：信用证  质保金：" + orderDec.getQualityFunds() + ";");
                    break;
                case "2":
                    stringBuilder.append("收款方式：托收  质保金：" + orderDec.getQualityFunds() + ";");
                    break;
                case "3":
                    stringBuilder.append("收款方式：电汇  质保金：" + orderDec.getQualityFunds() + ";");
                    break;
                case "4":
                    stringBuilder.append("收款方式：信汇  质保金：" + orderDec.getQualityFunds() + ";");
                    break;
                case "5":
                    stringBuilder.append("收款方式：票汇  质保金：" + orderDec.getQualityFunds() + ";");
                    break;
                case "6":
                    stringBuilder.append("收款方式：现金  质保金：" + orderDec.getQualityFunds() + ";");
                    break;
                default:
                    break;
            }
            for (OrderPayment op : orderPayments) {
                String format = DateUtil.format(DateUtil.SHORT_FORMAT_STR, op.getReceiptDate());
                String receiptDate = "";
                if (format != null) {
                    receiptDate = "  收款日期：" + format;
                }
                String getMoney = "收款金额：" + op.getMoney() + orderDec.getCurrencyBn() + receiptDate;
                if (op.getType() == 1) {
                    String stringR6C5 = sheet1.getRow(6).getCell(5).getStringCellValue().replace("□ 是", sheet1.getRow(3).getCell(11).getStringCellValue() + " 是");
                    sheet1.getRow(6).getCell(5).setCellValue(stringR6C5);
                    if (op.getReceiptDate() != null) {
                        String stringR6C502 = sheet1.getRow(6).getCell(5).getStringCellValue().replace("        年      月      日", DateUtil.format(DateUtil.SHORT_FORMAT_STR, op.getReceiptDate()));
                        sheet1.getRow(6).getCell(5).setCellValue(stringR6C502);
                    }
                    //[{"text":"请选择","value":0},{"text":"发货后","value":4},{"text":"货到后","value":5},
                    // {"text":"提单日后","value":6},{"text":"交货后","value":7},{"text":"验收后","value":8}]
                    stringBuilder.append("预收货款：" + op.getMoney() + orderDec.getCurrencyBn() + " 收款日期：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, op.getReceiptDate()) + ";");
                } else if (op.getType() == 4) {
                    stringBuilder.append("收款方式：发货后 " + op.getReceiptTime() + "天 " + getMoney + ";");
                } else if (op.getType() == 5) {
                    stringBuilder.append("收款方式：货到后 " + op.getReceiptTime() + "天 " + getMoney);
                } else if (op.getType() == 6) {
                    stringBuilder.append("收款方式：提单日后 " + op.getReceiptTime() + "天 " + getMoney + ";");
                } else if (op.getType() == 7) {
                    stringBuilder.append("收款方式：交货后 " + op.getReceiptTime() + "天 " + getMoney + ";");
                } else if (op.getType() == 8) {
                    stringBuilder.append("收款方式：验收后 " + op.getReceiptTime() + "天 " + getMoney + ";");
                }
            }
            String stringR7C2 = sheet1.getRow(7).getCell(2).getStringCellValue().replace("请注明收款方式、预付货款、支付节点及对应金额、质保金等。", stringBuilder.toString());
            sheet1.getRow(7).getCell(2).setCellValue(stringR7C2);
            //不是预投
            if (orderPayments.stream().filter(orderPayment -> orderPayment.getType().equals(1)).collect(Collectors.toList()).size() < 1) {
                String stringR6C5 = sheet1.getRow(6).getCell(5).getStringCellValue().replace("□ 否", sheet1.getRow(3).getCell(11).getStringCellValue() + " 否");
                sheet1.getRow(6).getCell(5).setCellValue(stringR6C5);
            }
        }

        //市场经办人
        if (orderDec.getAgentName() != null) {
            String stringR14C2 = sheet1.getRow(14).getCell(2).getStringCellValue().replace("经办人：市场经办人", "经办人：" + orderDec.getAgentName());
            sheet1.getRow(14).getCell(2).setCellValue(stringR14C2);
        }
        //回款责任人
        if (orderDec.getPerLiableRepay() != null) {
            String stringR14C2 = sheet1.getRow(14).getCell(2).getStringCellValue().replace("回款负责人：指定或分类销售总监", "回款负责人：" + orderDec.getPerLiableRepay());
            sheet1.getRow(14).getCell(2).setCellValue(stringR14C2);
        }
        //单位总：国家负责人
        if (orderDec.getCountryLeader() != null) {
            String stringR14C2 = sheet1.getRow(14).getCell(2).getStringCellValue().replace("单位总：", "单位总：" + orderDec.getCountryLeader());
            sheet1.getRow(14).getCell(2).setCellValue(stringR14C2);
        }
        List<CheckLog> passed = new ArrayList<>();
        if (orderDec.getId() != null) {
            passed = checkLogService.findPassed2(orderDec.getId());
        }
        for (CheckLog cl : passed) {
            //只有金额大于10万美元 且不是国内订单才有区域审核
            if (orderDec.getOrderCategory() != 6 && STEP_ONE_PRICE.doubleValue() <= orderDec.getTotalPriceUsd().doubleValue()) {
                //区域审核接受时间
                if (cl.getAuditingProcess() == 3) {
                    String stringR15C10 = sheet1.getRow(15).getCell(4).getStringCellValue().replace("接收时间：", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                    sheet1.getRow(15).getCell(4).setCellValue(stringR15C10);
                }
                //区域审核取走时间 如果大于1000万美元则是 区域vp审核时间 否则 若为融资则是融资生成时间 否则为提交商品时间
                if (orderDec.getTotalPriceUsd().doubleValue() >= STEP_THREE_PRICE.doubleValue()) {
                    if (cl.getAuditingProcess() == 4) {
                        String stringR16C4 = sheet1.getRow(16).getCell(4).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                        sheet1.getRow(16).getCell(4).setCellValue(stringR16C4);
                    }

                } else if (orderDec.getFinancing() != null && orderDec.getFinancing() == 1) {
                    if (cl.getAuditingProcess() == 5) {
                        String stringR16C4 = sheet1.getRow(16).getCell(4).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                        sheet1.getRow(16).getCell(4).setCellValue(stringR16C4);
                    }
                } else {
                    if (cl.getAuditingProcess() == 6) {
                        String stringR16C4 = sheet1.getRow(16).getCell(4).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                        sheet1.getRow(16).getCell(4).setCellValue(stringR16C4);
                    }
                }
            }
            //区域vp
            if (orderDec.getOrderCategory() != 6 && STEP_TWO_PRICE.doubleValue() <= orderDec.getTotalPriceUsd().doubleValue()) {
                //区域审核接受时间
                if (cl.getAuditingProcess() == 4) {
                    String stringR15C10 = sheet1.getRow(15).getCell(10).getStringCellValue().replace("接收时间：审核流入时间", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                    sheet1.getRow(15).getCell(10).setCellValue(stringR15C10);
                }
                //区域vp审核取走时间  若为融资则是融资生成时间 否则为提交商品时间
                if (orderDec.getFinancing() != null && orderDec.getFinancing() == 1) {
                    if (cl.getAuditingProcess() == 5) {
                        String stringR16C4 = sheet1.getRow(16).getCell(10).getStringCellValue().replace("取走时间：审核流出时间", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                        sheet1.getRow(16).getCell(10).setCellValue(stringR16C4);
                    }
                } else {
                    if (cl.getAuditingProcess() == 6) {
                        String stringR16C4 = sheet1.getRow(16).getCell(10).getStringCellValue().replace("取走时间：审核流出时间", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                        sheet1.getRow(16).getCell(10).setCellValue(stringR16C4);
                    }
                }
            }
            //法务审核取走时间 如果大于10万美元则是 区域vp审核时间 否则 若为融资则是融资生成时间 否则为提交商品时间
            if (orderDec.getOrderCategory() != 6 && STEP_ONE_PRICE.doubleValue() <= orderDec.getTotalPriceUsd().doubleValue()) {
                if (cl.getAuditingProcess() == 3) {
                    String stringR26C10 = sheet1.getRow(26).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                    sheet1.getRow(26).getCell(10).setCellValue(stringR26C10);
                }

            } else if (orderDec.getFinancing() != null && orderDec.getFinancing() == 1) {
                if (cl.getAuditingProcess() == 5) {
                    String stringR26C10 = sheet1.getRow(26).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                    sheet1.getRow(26).getCell(10).setCellValue(stringR26C10);
                }
            } else {
                if (cl.getAuditingProcess() == 6) {
                    String stringR26C10 = sheet1.getRow(26).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                    sheet1.getRow(26).getCell(10).setCellValue(stringR26C10);
                }
            }
            //国际金融审核时间
            if (orderDec.getFinancing() != null && orderDec.getFinancing() == 1 && cl.getAuditingProcess() == 5) {
                //国际金融接受时间
                String stringR17C10 = sheet1.getRow(17).getCell(10).getStringCellValue().replace("接收时间：", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(17).getCell(10).setCellValue(stringR17C10);
            }
            //国际金融审核时间
            if (cl.getAuditingProcess() == 6) {
                //国际金融取走时间
                if (orderDec.getFinancing() != null && orderDec.getFinancing() == 1) {
                    String stringR18C10 = sheet1.getRow(18).getCell(10).getStringCellValue().replace("时间：", "时间： " + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                    sheet1.getRow(18).getCell(10).setCellValue(stringR18C10);
                }
                //商务技术接受时间
                String stringR19C10 = sheet1.getRow(19).getCell(10).getStringCellValue().replace("接收时间：", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(19).getCell(10).setCellValue(stringR19C10);

                String stringR21C10 = sheet1.getRow(21).getCell(10).getStringCellValue().replace("接收时间：", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(21).getCell(10).setCellValue(stringR21C10);
            }

            //财务或国际结算
            if (cl.getAuditingProcess() == 13) {
                //商务技术取走时间
                String stringR20C10 = sheet1.getRow(20).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(20).getCell(10).setCellValue(stringR20C10);
                String stringR22C10 = sheet1.getRow(22).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(22).getCell(10).setCellValue(stringR22C10);
                //国际结算审核人
                String stringR23C1 = sheet1.getRow(23).getCell(1).getStringCellValue().replace("审核人：", "审核人： " + cl.getAuditingUserName() + ",郑效明");
                sheet1.getRow(23).getCell(1).setCellValue(stringR23C1);
                //财务或国际结算接受时间
                String stringR23C10 = sheet1.getRow(23).getCell(10).getStringCellValue().replace("接收时间", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(23).getCell(10).setCellValue(stringR23C10);
            }

            //财务或国际结算取走时间
            if (cl.getAuditingProcess() == 14) {
                String stringR24C10 = sheet1.getRow(24).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(24).getCell(10).setCellValue(stringR24C10);
            }
            //法务
            if (cl.getAuditingProcess() == 8) {
                String stringR25C1 = sheet1.getRow(25).getCell(1).getStringCellValue().replace("审核人：", "审核人： " + cl.getAuditingUserName());
                sheet1.getRow(25).getCell(1).setCellValue(stringR25C1);
                //法务审核接收时间
                String stringR25C10 = sheet1.getRow(25).getCell(10).getStringCellValue().replace("接收时间", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(25).getCell(10).setCellValue(stringR25C10);
            }

            //物流审核接收时间
            if (orderDec.getProject().getLogisticsAudit() == 1 && cl.getAuditingProcess() == 15) {
                String stringR27C10 = sheet1.getRow(27).getCell(10).getStringCellValue().replace("接收时间：", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(27).getCell(10).setCellValue(stringR27C10);
            }
            //物流审核取走时间
            if (cl.getAuditingProcess() == 16) {
                String stringR27C10 = sheet1.getRow(28).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(28).getCell(10).setCellValue(stringR27C10);
                //事业部总监审核接收时间
                String stringR29C10 = sheet1.getRow(29).getCell(10).getStringCellValue().replace("接收时间：", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(29).getCell(10).setCellValue(stringR29C10);
            }
            //事业部总监审核取走时间
            if (cl.getAuditingProcess() == 17) {
                String stringR30C10 = sheet1.getRow(30).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(30).getCell(10).setCellValue(stringR30C10);
            } else {
                String stringR29C10 = sheet1.getRow(29).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(29).getCell(10).setCellValue(stringR29C10);
            }

            //事业部总裁审核
            if (cl.getAuditingProcess() == 18) {
                String stringR33C1 = sheet1.getRow(31).getCell(2).getStringCellValue().replace("                                                        ＞50万美金", "杨海涛                      ＞50万美金");
                sheet1.getRow(31).getCell(2).setCellValue(stringR33C1);
            }
            //事业部总裁审核接收时间
            if (cl.getAuditingProcess() == 18) {
                String stringR32C10 = sheet1.getRow(31).getCell(10).getStringCellValue().replace("接收时间：", "接收时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(31).getCell(10).setCellValue(stringR32C10);
            }
            //事业部总裁审核取走时间
            if (cl.getAuditingProcess() == 19) {
                String stringR32C10 = sheet1.getRow(32).getCell(10).getStringCellValue().replace("取走时间：", "取走时间：" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, cl.getCreateTime()));
                sheet1.getRow(32).getCell(10).setCellValue(stringR32C10);
            }
        }
        //区域负责人
        if (orderDec.getOrderCategory() != 5 && STEP_ONE_PRICE.doubleValue() <= orderDec.getTotalPriceUsd().doubleValue() && orderDec.getAreaLeader() != null) {
            String stringR15C2 = sheet1.getRow(15).getCell(2).getStringCellValue().replace("金额在10万美金以上的", "  " + orderDec.getAreaLeader());
            sheet1.getRow(15).getCell(2).setCellValue(stringR15C2);
        }
        //区域vp 分管领导
        if (orderDec.getOrderCategory() != 5 && STEP_TWO_PRICE.doubleValue() <= orderDec.getTotalPriceUsd().doubleValue() && orderDec.getAreaVp() != null) {
            sheet1.getRow(15).getCell(7).setCellValue(orderDec.getAreaVp());
        }
        //国际金融
        if (orderDec.getFinancing() != null && orderDec.getFinancing() == 1) {
            String stringR17C1 = sheet1.getRow(17).getCell(1).getStringCellValue().replace("审核人：", "审核人： 郭永涛");
            sheet1.getRow(17).getCell(1).setCellValue(stringR17C1);
        }
        //商务技术
        if (orderDec.getBusinessName() != null) {
            String stringR19C1 = sheet1.getRow(19).getCell(1).getStringCellValue().replace("审核人：", "审核人： " + orderDec.getBusinessName());
            sheet1.getRow(19).getCell(1).setCellValue(stringR19C1);
        }
        //商务技术
        if (orderDec.getBusinessName() != null) {
            String stringR21C1 = sheet1.getRow(21).getCell(1).getStringCellValue().replace("审核人：", "审核人： " + orderDec.getBusinessName());
            sheet1.getRow(21).getCell(1).setCellValue(stringR21C1);
        }
        if (orderDec.getProject() != null) {
            //是否物流审核 1:不需要  2：需要
            if (orderDec.getProject().getLogisticsAudit() != null && orderDec.getProject().getLogisticsAudit() == 2 && orderDec.getProject().getLogisticsAuditer() != null) {
                String stringR27C1 = sheet1.getRow(27).getCell(1).getStringCellValue().replace("□ 是", sheet1.getRow(3).getCell(11).getStringCellValue() + " 是");
                sheet1.getRow(27).getCell(1).setCellValue(stringR27C1);
                //审核人
                String stringR27C11 = sheet1.getRow(27).getCell(1).getStringCellValue().replace("审核人：", "审核人：" + orderDec.getProject().getLogisticsAuditer());
                sheet1.getRow(27).getCell(1).setCellValue(stringR27C11);
            } else {
                String stringR27C1 = sheet1.getRow(27).getCell(1).getStringCellValue().replace("□ 否", sheet1.getRow(3).getCell(11).getStringCellValue() + " 否");
                sheet1.getRow(27).getCell(1).setCellValue(stringR27C1);
            }
            //事业部总监审核
            if (orderDec.getProject().getBuAuditer() != null) {
                String stringR29C2 = sheet1.getRow(29).getCell(2).getStringCellValue().replace("                                              ≤50万美金", orderDec.getProject().getBuAuditer() + "                    ≤50万美金");
                sheet1.getRow(29).getCell(2).setCellValue(stringR29C2);
            }

        }
        sheet1.getRow(3).getCell(11).setCellValue("");
    }
}