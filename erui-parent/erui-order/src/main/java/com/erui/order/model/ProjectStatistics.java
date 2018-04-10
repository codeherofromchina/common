package com.erui.order.model;

import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目执行统计
 * Created by wangxiaodan on 2018/4/10.
 */
public class ProjectStatistics {

    public ProjectStatistics() {
    }

    public ProjectStatistics(Project project, Order order) {
        this.orderId = order.getId();
        this.startDate = project.getStartDate();
        this.contractNo = order.getContractNo();
        this.inquiryNo = order.getInquiryNo();
        this.projectNo = project.getProjectNo();
        this.projectName = project.getProjectName();
        this.contractNoOs = order.getContractNoOs();
        this.logiQuoteNo = order.getLogiQuoteNo();
        this.poNo = order.getPoNo();
        this.execCoName = order.getExecCoName();
        this.distributionDeptName = order.getDistributionDeptName();
        this.businessUnitName = order.getBusinessUnitName();
        this.region = order.getRegion();
        this.crmCode = order.getCrmCode();
        this.customerType = order.getCustomerType();
        this.orderType = order.getOrderType();
        this.totalPrice = order.getTotalPrice();
        this.profitPercent = project.getProfitPercent();
        this.grantType = order.getGrantType();
        this.deliveryDate = order.getDeliveryDate();
        this.requirePurchaseDate = project.getRequirePurchaseDate();
        this.exeChgDate = project.getExeChgDate();
        this.agentName = order.getAgentName();
        this.acquireId = order.getAcquireId();
        this.businessName = order.getBusinessName();
        this.tradeTerms = order.getTradeTerms();
        this.projectStatus = project.getProjectStatus();

    }

    //订单ID
    private Integer orderId;
    // 项目开始日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    //销售合同号
    private String contractNo;
    //询单号
    private String inquiryNo;
    //项目号
    private String projectNo;
    //项目名称
    private String projectName;
    //海外销售合同号
    private String contractNoOs;
    //物流报价单号
    private String logiQuoteNo;
    //PO号
    private String poNo;
    //执行分公司
    private String execCoName;
    //分销部
    private String distributionDeptName;
    //事业部
    private String businessUnitName;
    //所属地区
    private String region;
    //CRM客户代码
    private String crmCode;
    //客户类型
    private Integer customerType;
    //订单类型
    private Integer orderType;
    //海外销类型 TODO 待完善

    //项目金额（美元）
    private BigDecimal totalPrice;
    //初步利润率
    private BigDecimal profitPercent;
    //授信情况
    private String grantType;
    //执行单约定交付日期
    private String deliveryDate;
    //要求采购到货日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date requirePurchaseDate;
    //执行单变更后日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exeChgDate;
    //分销部(获取人所在分类销售)
    //private String distributionDeptName;
    //市场经办人
    private String agentName;
    //获取人
    private Integer acquireId;
    //商务技术经办人
    private String businessName;
    //贸易术语
    private String tradeTerms;
    //项目状态
    private String projectStatus;
    //流程进度
    private String processProgress;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getInquiryNo() {
        return inquiryNo;
    }

    public void setInquiryNo(String inquiryNo) {
        this.inquiryNo = inquiryNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContractNoOs() {
        return contractNoOs;
    }

    public void setContractNoOs(String contractNoOs) {
        this.contractNoOs = contractNoOs;
    }

    public String getLogiQuoteNo() {
        return logiQuoteNo;
    }

    public void setLogiQuoteNo(String logiQuoteNo) {
        this.logiQuoteNo = logiQuoteNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
    }

    public String getDistributionDeptName() {
        return distributionDeptName;
    }

    public void setDistributionDeptName(String distributionDeptName) {
        this.distributionDeptName = distributionDeptName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getAcquireId() {
        return acquireId;
    }

    public void setAcquireId(Integer acquireId) {
        this.acquireId = acquireId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProcessProgress() {
        return processProgress;
    }

    public void setProcessProgress(String processProgress) {
        this.processProgress = processProgress;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCrmCode() {
        return crmCode;
    }

    public void setCrmCode(String crmCode) {
        this.crmCode = crmCode;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(BigDecimal profitPercent) {
        this.profitPercent = profitPercent;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getRequirePurchaseDate() {
        return requirePurchaseDate;
    }

    public void setRequirePurchaseDate(Date requirePurchaseDate) {
        this.requirePurchaseDate = requirePurchaseDate;
    }

    public Date getExeChgDate() {
        return exeChgDate;
    }

    public void setExeChgDate(Date exeChgDate) {
        this.exeChgDate = exeChgDate;
    }


}
