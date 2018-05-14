package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class PerformanceCount {
    private Long id;

    private Integer serialNumber;

    private String executeNum;

    private String overseasSalesNum;

    private String signCompany;

    private String notEruiReason;

    private String isThroughAgent;

    private String agentCode;

    private String organization;

    private String executeCompany;

    private String area;

    private String buyerCode;

    private String goodsCh;

    private String category;

    private Integer goodsCount;

    private String unit;

    private BigDecimal projectAmount;

    private BigDecimal initialProfitMargin;

    private String receivedMode;

    private Date startTime;

    private String obtainMainCompany;

    private String obtainer;

    private String marketer;

    private String businesser;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getExecuteNum() {
        return executeNum;
    }

    public void setExecuteNum(String executeNum) {
        this.executeNum = executeNum == null ? null : executeNum.trim();
    }

    public String getOverseasSalesNum() {
        return overseasSalesNum;
    }

    public void setOverseasSalesNum(String overseasSalesNum) {
        this.overseasSalesNum = overseasSalesNum == null ? null : overseasSalesNum.trim();
    }

    public String getSignCompany() {
        return signCompany;
    }

    public void setSignCompany(String signCompany) {
        this.signCompany = signCompany == null ? null : signCompany.trim();
    }

    public String getNotEruiReason() {
        return notEruiReason;
    }

    public void setNotEruiReason(String notEruiReason) {
        this.notEruiReason = notEruiReason == null ? null : notEruiReason.trim();
    }

    public String getIsThroughAgent() {
        return isThroughAgent;
    }

    public void setIsThroughAgent(String isThroughAgent) {
        this.isThroughAgent = isThroughAgent == null ? null : isThroughAgent.trim();
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode == null ? null : agentCode.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getExecuteCompany() {
        return executeCompany;
    }

    public void setExecuteCompany(String executeCompany) {
        this.executeCompany = executeCompany == null ? null : executeCompany.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode == null ? null : buyerCode.trim();
    }

    public String getGoodsCh() {
        return goodsCh;
    }

    public void setGoodsCh(String goodsCh) {
        this.goodsCh = goodsCh == null ? null : goodsCh.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(BigDecimal projectAmount) {
        this.projectAmount = projectAmount;
    }

    public BigDecimal getInitialProfitMargin() {
        return initialProfitMargin;
    }

    public void setInitialProfitMargin(BigDecimal initialProfitMargin) {
        this.initialProfitMargin = initialProfitMargin;
    }

    public String getReceivedMode() {
        return receivedMode;
    }

    public void setReceivedMode(String receivedMode) {
        this.receivedMode = receivedMode == null ? null : receivedMode.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getObtainMainCompany() {
        return obtainMainCompany;
    }

    public void setObtainMainCompany(String obtainMainCompany) {
        this.obtainMainCompany = obtainMainCompany == null ? null : obtainMainCompany.trim();
    }

    public String getObtainer() {
        return obtainer;
    }

    public void setObtainer(String obtainer) {
        this.obtainer = obtainer == null ? null : obtainer.trim();
    }

    public String getMarketer() {
        return marketer;
    }

    public void setMarketer(String marketer) {
        this.marketer = marketer == null ? null : marketer.trim();
    }

    public String getBusinesser() {
        return businesser;
    }

    public void setBusinesser(String businesser) {
        this.businesser = businesser == null ? null : businesser.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}