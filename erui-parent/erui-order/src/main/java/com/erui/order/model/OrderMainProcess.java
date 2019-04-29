package com.erui.order.model;

import java.math.BigDecimal;
import java.security.PrivateKey;

public class OrderMainProcess {


    private String contractNo; //销售合同号
    private Integer orderId; //订单id / 点击销售合同号查询
    private BigDecimal totalPriceUsd;    // 合同金额（USD）
    private String  region; //地区
    private String country; //国家
    private String  projectNo; //项目号
    private String  projectName;    //项目名称
    private Integer  orderStatus; //订单状态
    private String  projectStatus; //项目状态
    private Integer  purchStatus;    //采购状态
    private Integer projectId; //项目id / 点击采购状态查询
    private BigDecimal purchOrdermoney;   //采购订单金额
    private Integer inspectReportStatus;    // 入库质检状态
    private Integer instockStatus; //入库状态
    private Integer deliverConsignStatus; //订舱通知状态   / 出口通知单状态
    private Integer deliverDetailStatus; //出库状态
    private Integer logisticsDataStatus; //发运状态 TODO 条件查询有问题
    private BigDecimal logisticsPrice; //发运金额
    private Integer confirmTheStatus; //收货状态 TODO 条件查询未做
    private Integer payStatus;   //收款状态
    private BigDecimal alreadyGatheringMoney; //    收款金额
    private BigDecimal receivableAccountRemaining;   //  应收账款余额
    private String currencyBn; //订单货币类型
    private String purchCurrencyBn; //采购币种


    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getPurchStatus() {
        return purchStatus;
    }

    public void setPurchStatus(Integer purchStatus) {
        this.purchStatus = purchStatus;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getPurchOrdermoney() {
        return purchOrdermoney;
    }

    public void setPurchOrdermoney(BigDecimal purchOrdermoney) {
        this.purchOrdermoney = purchOrdermoney;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getTotalPriceUsd() {
        return totalPriceUsd;
    }

    public void setTotalPriceUsd(BigDecimal totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
    }

    public Integer getInspectReportStatus() {
        return inspectReportStatus;
    }

    public void setInspectReportStatus(Integer inspectReportStatus) {
        this.inspectReportStatus = inspectReportStatus;
    }

    public Integer getInstockStatus() {
        return instockStatus;
    }

    public void setInstockStatus(Integer instockStatus) {
        this.instockStatus = instockStatus;
    }

    public Integer getDeliverConsignStatus() {
        return deliverConsignStatus;
    }

    public void setDeliverConsignStatus(Integer deliverConsignStatus) {
        this.deliverConsignStatus = deliverConsignStatus;
    }

    public Integer getDeliverDetailStatus() {
        return deliverDetailStatus;
    }

    public void setDeliverDetailStatus(Integer deliverDetailStatus) {
        this.deliverDetailStatus = deliverDetailStatus;
    }

    public Integer getLogisticsDataStatus() {
        return logisticsDataStatus;
    }

    public void setLogisticsDataStatus(Integer logisticsDataStatus) {
        this.logisticsDataStatus = logisticsDataStatus;
    }

    public BigDecimal getLogisticsPrice() {
        return logisticsPrice;
    }

    public void setLogisticsPrice(BigDecimal logisticsPrice) {
        this.logisticsPrice = logisticsPrice;
    }

    public Integer getConfirmTheStatus() {
        return confirmTheStatus;
    }

    public void setConfirmTheStatus(Integer confirmTheStatus) {
        this.confirmTheStatus = confirmTheStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public BigDecimal getAlreadyGatheringMoney() {
        return alreadyGatheringMoney;
    }

    public void setAlreadyGatheringMoney(BigDecimal alreadyGatheringMoney) {
        this.alreadyGatheringMoney = alreadyGatheringMoney;
    }

    public BigDecimal getReceivableAccountRemaining() {
        return receivableAccountRemaining;
    }

    public void setReceivableAccountRemaining(BigDecimal receivableAccountRemaining) {
        this.receivableAccountRemaining = receivableAccountRemaining;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public String getPurchCurrencyBn() {
        return purchCurrencyBn;
    }

    public void setPurchCurrencyBn(String purchCurrencyBn) {
        this.purchCurrencyBn = purchCurrencyBn;
    }
}
