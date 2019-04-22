package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.ChineseAndEnglish;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.OrderConf;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.event.NotifyPointProjectEvent;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.event.TasksAddEvent;
import com.erui.order.requestVo.*;
import com.erui.order.service.*;
import com.erui.order.util.BpmUtils;
import com.erui.order.util.CrmUtils;
import com.erui.order.util.SsoUtils;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service("orderV2ServiceImpl")
@Transactional
public class OrderV2ServiceImpl implements OrderV2Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderV2ServiceImpl.class);
    // 用户升级方法
    static final BigDecimal STEP_ONE_PRICE = new BigDecimal("30000");
    static final BigDecimal STEP_TWO_PRICE = new BigDecimal("200000");
    private static Logger logger = LoggerFactory.getLogger(OrderV2ServiceImpl.class);
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
    private OrderConf orderConf;
    @Autowired
    private IogisticsDataService iogisticsDataService;
    @Autowired
    private DeliverConsignService deliverConsignService;
    @Autowired
    private BackLogService backLogService;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private AttachmentService attachmentService;

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
            List<Attachment> orderAttachment = attachmentDao.findByRelObjIdAndCategory(id, Attachment.AttachmentCategory.ORDER.getCode());
            if (orderAttachment != null && orderAttachment.size() > 0) {
                order.setAttachmentSet(orderAttachment);
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
            String distributionDeptName = null;
            if (StringUtils.isNotBlank(order.getDistributionDeptName())) {
                distributionDeptName = getDeptNameByLang(lang, order.getDistributionDeptName());
            }
            order.setDistributionDeptName(distributionDeptName);
        }

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
    @Transactional(rollbackFor = Exception.class)
    public Integer updateOrder(Integer orderId, AddOrderV2Vo addOrderVo) throws Exception {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        // 更新订单的相关字段
        Order order = findByIdLang(orderId, "zh");
        if ((order.getOverseasSales() != null && order.getOverseasSales() != 2 && order.getOverseasSales() != 4) && (addOrderVo.getOverseasSales() == 2 || addOrderVo.getOverseasSales() == 4)) {
            order.setContractNo("");
        } else if ((addOrderVo.getOverseasSales() == 2 || addOrderVo.getOverseasSales() == 4) && order.getSigningCo() != null && !order.getSigningCo().equals(addOrderVo.getSigningCo())) {
            order.setContractNo("");
        } else if (order.getOrderCategory() != null && order.getOrderCategory() == 6
                && StringUtils.equals("Erui International Electronic Commerce Co., Ltd.", addOrderVo.getSigningCo())
                && !StringUtils.equals(order.getSigningCo(), addOrderVo.getSigningCo())) {
            order.setContractNo("");

        }
        addOrderVo.copyBaseInfoTo(order);
        order.setOrderPayments(addOrderVo.getContractDesc());
        order.setDeleteFlag(false);
        //订单商品添加修改
        order.setGoodsList(updateOrderGoods(order, addOrderVo.getGoodDesc()));

        Order orderUpdate = orderDao.saveAndFlush(order);
        // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
        List<Attachment> attachmentList = null;
        if (addOrderVo.getAttachDesc() != null && addOrderVo.getAttachDesc().size() > 0) {
            attachmentList = addOrderVo.getAttachDesc();
        } else {
            attachmentList = new ArrayList<>();
        }
        if (order.getAttachmentSet() != null && order.getAttachmentSet().size() > 0) {
            Map<Integer, Attachment> dbAttahmentsMap = order.getAttachmentSet().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
            attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, orderUpdate.getId(), Attachment.AttachmentCategory.ORDER.getCode());
        } else {
            attachmentService.addAttachments(attachmentList, order.getId(), Attachment.AttachmentCategory.ORDER.getCode());
        }
        //审核日志 钉钉通知 和待办
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            // 初始化订单提交后的后续工作
            // 调用业务流，开启业务审核流程系统 // 非国内订单审批流程 TODO 这里的用户编号待获取
            JSONObject processResp = BpmUtils.startProcessInstanceByKey("process_order", "017340", eruiToken, "order:" + orderUpdate.getId());
            orderUpdate.setProcessId(processResp.getJSONObject("response").getString("instanceId"));


            List<OrderLog> orderLog = orderLogDao.findByOrderIdOrderByCreateTimeAsc(orderUpdate.getId());
            if (orderLog.size() > 0) {
                Map<String, OrderLog> collect = orderLog.stream().collect(Collectors.toMap(vo -> vo.getLogType().toString(), vo -> vo));
                if (collect.containsKey("1")) {
                    orderLogDao.delete(collect.get("1").getId());
                }
            }
            addLog(OrderLog.LogTypeEnum.CREATEORDER, orderUpdate.getId(), null, null, orderUpdate.getSigningDate());
            // 流程进度
            applicationContext.publishEvent(new OrderProgressEvent(orderUpdate, 1, eruiToken));
            Project projectAdd = null;
            if (orderUpdate.getProject() == null) {
                projectAdd = new Project();
            } else {
                projectAdd = orderUpdate.getProject();
            }
            projectAdd.setOrder(orderUpdate);
            projectAdd.setBusinessUid(orderUpdate.getTechnicalId());
            projectAdd.setExecCoName(orderUpdate.getExecCoName());
            projectAdd.setBusinessUnitName(orderUpdate.getBusinessUnitName());
            projectAdd.setSendDeptId(orderUpdate.getBusinessUnitId());
            projectAdd.setTotalPriceUsd(orderUpdate.getTotalPriceUsd());
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
            List<Goods> goodsList1 = orderUpdate.getGoodsList();
            goodsList1.parallelStream().forEach(goods1 -> {
                goods1.setProject(project);
                goods1.setProjectNo(project.getProjectNo());
            });
            goodsDao.save(goodsList1);
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
            projectProfitDao.save(projectProfit);
            // 调用CRM系统，触发CRM用户升级任务
            if (StringUtils.isNotBlank(eruiToken)) {
                CrmUtils.autoUpgrade(order.getCrmCode(), eruiToken);
            }

        }
        return order.getId();
    }

    private List<Goods> updateOrderGoods(Order order, List<PGoods> pGoodsList) {
        List<Goods> goodsList = new ArrayList<>();
        Map<Integer, Goods> dbGoodsMap = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        Set<String> skuRepeatSet = new HashSet<>();
        for (PGoods pGoods : pGoodsList) {
            Goods goods = null;
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
            goods.setContractNo(order.getContractNo());
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
        goodsDao.delete(dbGoodsMap.values());
        // 设置商品的项目信息
        return goodsList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrder(AddOrderV2Vo addOrderVo) throws Exception {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        // 设置订单字段信息
        Order order = new Order();
        addOrderVo.copyBaseInfoTo(order);
        order.setCreateUserId(addOrderVo.getCreateUserId());
        order.setCreateUserName(addOrderVo.getCreateUserName());
        order.setOrderPayments(addOrderVo.getContractDesc());
        order.setCreateTime(new Date());
        order.setDeleteFlag(false);
        //订单商品添加
        List<PGoods> pGoodsList = addOrderVo.getGoodDesc();
        List<Goods> goodsList = new ArrayList<>();
        Set<String> skuRepeatSet = new HashSet<>();
        for (PGoods pGoods : pGoodsList) {
            Goods goods = new Goods();
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
        }
        order.setGoodsList(goodsList);
        Order order1 = orderDao.save(order);
        //添加附件
        if (addOrderVo.getAttachDesc() != null) {
            attachmentService.addAttachments(addOrderVo.getAttachDesc(), order1.getId(), Attachment.AttachmentCategory.ORDER.getCode());
        }
        // 订单字段设置结束

        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) {
            // 初始化订单提交后的后续工作
            // 调用业务流，开启业务审核流程系统 // 非国内订单审批流程 TODO 这里的用户编号待获取
            JSONObject processResp = BpmUtils.startProcessInstanceByKey("process_order", "017340", eruiToken, "order:" + order1.getId());
            order1.setProcessId(processResp.getJSONObject("response").getString("instanceId"));
            //添加订单未执行事件，设置订单流程进度信息
            applicationContext.publishEvent(new OrderProgressEvent(order1, 1, eruiToken));
            // 记录订单日志信息
            List<OrderLog> orderLog = orderLogDao.findByOrderIdOrderByCreateTimeAsc(order1.getId());
            if (orderLog.size() > 0) {
                Map<String, OrderLog> collect = orderLog.stream().collect(Collectors.toMap(vo -> vo.getLogType().toString(), vo -> vo));
                if (collect.containsKey("1")) {
                    orderLogDao.delete(collect.get("1").getId());
                }
            }
            addLog(OrderLog.LogTypeEnum.CREATEORDER, order1.getId(), null, null, order1.getSigningDate());
            // 订单提交时推送项目信息
            Project project = new Project();
            project.setOrder(order1);
            project.setBusinessUid(order1.getTechnicalId());
            project.setExecCoName(order1.getExecCoName());
            project.setBusinessUnitName(order1.getBusinessUnitName());
            project.setSendDeptId(order1.getBusinessUnitId());
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
            Project project1 = projectDao.save(project);
            // 设置商品的项目信息
            List<Goods> goodsList1 = order1.getGoodsList();
            goodsList1.parallelStream().forEach(goods1 -> {
                goods1.setProject(project1);
            });
            goodsDao.save(goodsList1);
            //添加项目利润核算单信息
            ProjectProfit projectProfit = new ProjectProfit();
            projectProfit.setProject(project1);
            projectProfit.setCountry(order1.getCountry());
            projectProfit.setTradeTerm(order1.getTradeTerms());
            projectProfit.setContractAmountUsd(order1.getTotalPriceUsd());
            projectProfitDao.save(projectProfit);
            // 调用CRM系统，触发CRM用户升级任务
            if (StringUtils.isNotBlank(eruiToken)) {
                CrmUtils.autoUpgrade(order.getCrmCode(), eruiToken);
            }
        }
        return order1.getId();
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


    /**
     * 审核任务
     *
     * @param auditOrderRequestVo
     * @param userId
     * @param userName
     */
    @Override
    public void audit(AuditOrderRequestVo auditOrderRequestVo, Integer userId, String userName) throws Exception {
        // TODO 这里还有一个问题是变量如何添加

        String token = (String) ThreadLocalUtil.getObject();
        Map<String, Object> variables = new HashMap<>();
        variables.put("audit_status", auditOrderRequestVo.isReject() ? "REJECTED" : "APPROVED");
        if (StringUtils.isNotBlank(auditOrderRequestVo.getRejectNode())) {
            variables.put("audit_forward_to", auditOrderRequestVo.getRejectNode());
        }
        JSONObject bpmResp = BpmUtils.completeTask(auditOrderRequestVo.getTaskId(), token, userName, variables, auditOrderRequestVo.getReason());

        // TODO 这里判断流程是否审核完成

    }
}