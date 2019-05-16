package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.ChineseAndEnglish;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.OrderConf;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.requestVo.AddOrderV2Vo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.requestVo.OrderV2ListRequest;
import com.erui.order.requestVo.PGoods;
import com.erui.order.service.*;
import com.erui.order.util.BpmUtils;
import com.erui.order.util.CrmUtils;
import com.erui.order.util.SsoUtils;
import com.erui.order.util.exception.MyException;
import com.erui.order.v2.service.EmployeeService;
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
    @Autowired
    private ProjectV2Service projectV2Service;

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口
    @Value("#{orderProp[DING_SEND_SMS]}")
    private String dingSendSms;  //发钉钉通知接口
    @Autowired
    private EmployeeService employeeService;


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
            LOGGER.info("CRM返回信息：" + e);
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
            if (StringUtils.isBlank(addOrderVo.getTaskId())) {
                // 调用业务流，开启业务审核流程系统 // 非国内订单审批流程
                Map<String, Object> bpmInitVar = new HashMap<>();
                bpmInitVar.put("order_amount", addOrderVo.getTotalPriceUsd().doubleValue());
                String task_fn_check = "N";
                if (addOrderVo.getFinancing() != null && addOrderVo.getFinancing() == 1) {
                    task_fn_check = "Y";
                }
                bpmInitVar.put("task_fn_check", task_fn_check);
                JSONObject processResp = null;
                switch (addOrderVo.getOrderCategory()) {
                    case 1:
                        // 预投订单
                        processResp = BpmUtils.startProcessInstanceByKey("stocking_order", null, eruiToken, "order:" + orderUpdate.getId(), bpmInitVar);
                        break;
                    case 3:
                        // 试用订单
                        processResp = BpmUtils.startProcessInstanceByKey("sample_order", null, eruiToken, "order:" + orderUpdate.getId(), bpmInitVar);
                        break;
                    case 4:
                        // 现货订单
                        processResp = BpmUtils.startProcessInstanceByKey("spot_order", null, eruiToken, "order:" + orderUpdate.getId(), bpmInitVar);
                        break;
                    case 6:
                        // 国内订单
                        processResp = BpmUtils.startProcessInstanceByKey("domestic_order", null, eruiToken, "order:" + orderUpdate.getId(), bpmInitVar);
                        break;
                    default:
                        Integer overseasSales = addOrderVo.getOverseasSales();
                        if (overseasSales != null && overseasSales == 3) {
                            // 海外销售类型 为3 海外销（当地采购 走现货审核流程
                            processResp = BpmUtils.startProcessInstanceByKey("spot_order", null, eruiToken, "order:" + orderUpdate.getId(), bpmInitVar);
                        } else {
                            // 非国内订单审批流程 process_order
                            processResp = BpmUtils.startProcessInstanceByKey("overseas_order", null, eruiToken, "order:" + orderUpdate.getId(), bpmInitVar);
                        }
                }
                orderUpdate.setProcessId(processResp.getString("instanceId"));
                orderUpdate.setAuditingProcess("task_cm"); // 第一个节点通知失败，写固定第一个节点
                orderUpdate.setAuditingStatus(Order.AuditingStatusEnum.PROCESSING.getStatus());
            } else {
                Map<String, Object> bpmVar = new HashMap<>();
                bpmVar.put("audit_status", "APPROVED");
                // 完善订单节点，完成任务
                BpmUtils.completeTask(addOrderVo.getTaskId(), eruiToken, null, bpmVar, "同意");
            }

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
            projectAdd.setProcessId(orderUpdate.getProcessId());
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
        order.setAuditingStatus(Order.AuditingStatusEnum.WAIT.getStatus());
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
            // 调用业务流，开启业务审核流程系统
            JSONObject processResp = null;
            Map<String, Object> bpmInitVar = new HashMap<>();
            bpmInitVar.put("order_amount", addOrderVo.getTotalPriceUsd().doubleValue()); // 订单金额
            // 国内订单
            String task_fn_check = "N";
            if (addOrderVo.getFinancing() != null && addOrderVo.getFinancing() == 1) {
                // 判断是否需要融资审批
                task_fn_check = "Y";
            }
            bpmInitVar.put("task_fn_check", task_fn_check);
            // 事业部项目负责人
            Integer technicalId = order1.getTechnicalId();
            String techicalUserNo = null;
            if (technicalId != null) {
                techicalUserNo = employeeService.findUserNoById(technicalId.longValue());
            }
            if (StringUtils.isNotBlank(techicalUserNo)) {
                // ID -> userNo
                bpmInitVar.put("assignee_pm",techicalUserNo);
            }

            switch (addOrderVo.getOrderCategory()) {
                case 1:
                    // 预投订单
                    processResp = BpmUtils.startProcessInstanceByKey("stocking_order", null, eruiToken, "order:" + order1.getId(), bpmInitVar);
                    break;
                case 3:
                    // 试用订单
                    processResp = BpmUtils.startProcessInstanceByKey("sample_order", null, eruiToken, "order:" + order1.getId(), bpmInitVar);
                    break;
                case 4:
                    // 现货订单
                    processResp = BpmUtils.startProcessInstanceByKey("spot_order", null, eruiToken, "order:" + order1.getId(), bpmInitVar);
                    break;
                case 6:
                    // 国内订单
                    processResp = BpmUtils.startProcessInstanceByKey("domestic_order", null, eruiToken, "order:" + order1.getId(), bpmInitVar);
                    break;
                default:
                    Integer overseasSales = addOrderVo.getOverseasSales();
                    if (overseasSales != null && overseasSales == 3) {
                        // 海外销售类型 为3 海外销（当地采购 走现货审核流程
                        processResp = BpmUtils.startProcessInstanceByKey("spot_order", null, eruiToken, "order:" + order1.getId(), bpmInitVar);
                    } else {
                        // 非国内订单审批流程 process_order
                        processResp = BpmUtils.startProcessInstanceByKey("overseas_order", null, eruiToken, "order:" + order1.getId(), bpmInitVar);
                    }
            }
            // 设置订单和业务流标示关联
            order1.setProcessId(processResp.getString("instanceId"));
            order1.setAuditingProcess("task_cm"); //第一个节点通知失败，写固定第一个节点
            order1.setAuditingStatus(Order.AuditingStatusEnum.PROCESSING.getStatus());

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
            project.setProcessId(order1.getProcessId());
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
            LOGGER.error("日志记录失败 {}", orderLog.toString());
            LOGGER.error("错误", ex);
            ex.printStackTrace();
        }
    }


    @Transactional
    @Override
    public Page<Order> findByPage(OrderV2ListRequest condition) {
        LOGGER.info("findByPage -> params : {}", condition);
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), new Sort(Sort.Direction.DESC, "createTime"));
        // 2019-01-30 增加需求 如果登录用户存在o34角色（国家负责人角色）则用户只能查看他所在国家的订单内容
        String[] countryArr = getCountryHeaderByRole();
        LOGGER.info("findByPage -> countryArr : {}", Arrays.toString(countryArr));
        Page<Order> pageList = orderDao.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>(); // 相当于前台查询条件
                List<Predicate> orList = new ArrayList<>(); // 后台默认增加的条件
                // 根据销售同号模糊查询
                if (StringUtil.isNotBlank(condition.getContractNo())) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                //根据项目号
                if (StringUtil.isNotBlank(condition.getProjectNo())) {
                    list.add(cb.like(root.get("projectNo").as(String.class), "%" + condition.getProjectNo() + "%"));
                }
                //根据合同交货日期查询
                if (StringUtil.isNotBlank(condition.getDeliveryDate())) {
                    list.add(cb.like(root.get("deliveryDate").as(String.class), "%" + condition.getDeliveryDate() + "%"));
                }
                //根据crm客户代码查询
                if (StringUtil.isNotBlank(condition.getCrmCode())) {
                    list.add(cb.like(root.get("crmCode").as(String.class), "%" + condition.getCrmCode() + "%"));
                }
                // 根据订单审核状态
                if (StringUtils.isNotBlank(condition.getAuditingProcess())) {
                    if ("999".equals(condition.getAuditingProcess())) {
                        // 999 定位审核完成的查询
                        list.add(cb.equal(root.get("auditingStatus").as(Integer.class), Order.AuditingStatusEnum.THROUGH.getStatus()));
                    } else {
                        list.add(cb.like(root.get("auditingProcess").as(String.class), "%" + condition.getAuditingProcess() + "%"));
                    }
                }
                //根据Po号模糊查询
                if (StringUtil.isNotBlank(condition.getPoNo())) {
                    list.add(cb.like(root.get("poNo").as(String.class), "%" + condition.getPoNo() + "%"));
                }
                //根据询单号查询
                if (StringUtil.isNotBlank(condition.getInquiryNo())) {
                    list.add(cb.like(root.get("inquiryNo").as(String.class), "%" + condition.getInquiryNo() + "%"));
                }
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
                if (countryArr != null) {
                    orList.add(root.get("country").in(countryArr));
                }
                //根据事业部
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
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            if (audiRemark != null) {
                                orList.add(cb.or(and, createUserId, auditingUserId, perLiableRepayId, audiRemark));
                            } else {
                                orList.add(cb.or(and, createUserId, auditingUserId, perLiableRepayId));
                            }
                        } else {
                            orList.add(cb.or(and, createUserId));
                        }
                    } else if (businessUnitId != null && technicalId == null) {
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            if (audiRemark != null) {
                                orList.add(cb.or(businessUnitId, createUserId, auditingUserId, perLiableRepayId, audiRemark));
                            } else {
                                orList.add(cb.or(businessUnitId, createUserId, auditingUserId, perLiableRepayId));
                            }
                        } else {
                            orList.add(cb.or(businessUnitId, createUserId));
                        }
                    } else if (technicalId != null && businessUnitId == null) {
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            if (audiRemark != null) {
                                orList.add(cb.or(technicalId, createUserId, auditingUserId, perLiableRepayId, audiRemark));
                            } else {
                                orList.add(cb.or(technicalId, createUserId, auditingUserId, perLiableRepayId));
                            }
                        } else {
                            orList.add(cb.or(technicalId, createUserId));
                        }
                    }
                } else {
                    //根据市场经办人查询
                    if (condition.getAgentId() != null && condition.getCreateUserId() != null) {
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            if (audiRemark != null) {
                                orList.add(cb.or(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()), cb.equal(root.get("createUserId").as(Integer.class),
                                        condition.getCreateUserId()), auditingUserId, perLiableRepayId, audiRemark));
                            } else {
                                orList.add(cb.or(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()), cb.equal(root.get("createUserId").as(Integer.class),
                                        condition.getCreateUserId()), auditingUserId, perLiableRepayId));
                            }
                        } else {
                            orList.add(cb.or(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()), cb.equal(root.get("createUserId").as(Integer.class),
                                    condition.getCreateUserId())));
                        }
                    } else if (condition.getAgentId() == null && condition.getCreateUserId() != null) {
                        if (StringUtils.isNotBlank(condition.getAuditingUserId()) && condition.getPerLiableRepayId() != null) {
                            Predicate auditingUserId = cb.equal(root.get("auditingUserId").as(String.class), condition.getAuditingUserId());
                            Predicate audiRemark = cb.like(root.get("audiRemark").as(String.class), "%" + condition.getCreateUserId() + "%");
                            Predicate perLiableRepayId = cb.equal(root.get("perLiableRepayId").as(Integer.class), condition.getPerLiableRepayId());
                            if (audiRemark != null) {
                                orList.add(cb.or(cb.equal(root.get("createUserId").as(Integer.class), condition.getCreateUserId()), auditingUserId, perLiableRepayId, audiRemark));
                            } else {
                                orList.add(cb.or(cb.equal(root.get("createUserId").as(Integer.class), condition.getCreateUserId()), auditingUserId, perLiableRepayId));
                            }
                        } else {
                            if (condition.getCreateUserId() != null) {
                                orList.add(cb.or(cb.equal(root.get("createUserId").as(Integer.class),
                                        condition.getCreateUserId())));
                            }
                        }
                    }
                }
                list.add(cb.equal(root.get("deleteFlag"), false));

                if (orList.size() > 0) {
                    Predicate[] predicatesBacks = new Predicate[orList.size()];
                    predicatesBacks = orList.toArray(predicatesBacks);
                    Predicate or = cb.or(predicatesBacks);
                    list.add(or);
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, pageRequest);
        if (pageList.hasContent()) {
            pageList.getContent().forEach(vo -> {
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
                if (vo.getDeliverConsignC() == false && iogisticsDataService.findStatusAndNumber(vo.getId())) {
                    vo.setOrderFinish(Boolean.TRUE);
                }
            });
        }
        return pageList;
    }


    // 2019-01-30 增加需求，如果登录用户存在o34角色（国家负责人角色），则用户只能查看他所在国家的订单内容
    private String[] getCountryHeaderByRole() {
        String[] countryArr = null;
        // 2019-01-30 增加需求，如果登录用户存在o34角色（国家负责人角色），则用户只能查看他所在国家的订单内容
        String token = (String) ThreadLocalUtil.getObject();
        JSONObject userInfo = SsoUtils.ssoUserInfo(orderConf.getSsoUser(), token);
        JSONArray roloNos = null;
        if (userInfo != null) {
            roloNos = userInfo.getJSONArray("role_no");
        }
        if (roloNos != null && roloNos.size() > 0) {
            boolean o34Exist = roloNos.stream().anyMatch(vo -> "O34".equals(vo));
            if (o34Exist) {
                JSONArray countryBns = userInfo.getJSONArray("country_bn");
                countryArr = countryBns.toArray(new String[countryBns.size()]);
            }
        }
        return countryArr;
    }

}


