package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class RequestReceive {
    private Long id;

    private String serialNum;

    private String orderNum;

    private String salesMainCompany;

    private String salesArea;

    private String salesCountry;

    private String organization;

    private String customerCode;

    private String exportProName;

    private String tradeTerms;

    private Date createAt;

    private BigDecimal orderAmount;

    private String creditCurrency;

    private BigDecimal receiveAmount;

    private String backResponsePerson;

    private Date nextBackDate;

    private Date backDate;

    private BigDecimal backAmount;

    private BigDecimal otherAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum == null ? null : serialNum.trim();
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public String getSalesMainCompany() {
        return salesMainCompany;
    }

    public void setSalesMainCompany(String salesMainCompany) {
        this.salesMainCompany = salesMainCompany == null ? null : salesMainCompany.trim();
    }

    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea == null ? null : salesArea.trim();
    }

    public String getSalesCountry() {
        return salesCountry;
    }

    public void setSalesCountry(String salesCountry) {
        this.salesCountry = salesCountry == null ? null : salesCountry.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getExportProName() {
        return exportProName;
    }

    public void setExportProName(String exportProName) {
        this.exportProName = exportProName == null ? null : exportProName.trim();
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms == null ? null : tradeTerms.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCreditCurrency() {
        return creditCurrency;
    }

    public void setCreditCurrency(String creditCurrency) {
        this.creditCurrency = creditCurrency == null ? null : creditCurrency.trim();
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getBackResponsePerson() {
        return backResponsePerson;
    }

    public void setBackResponsePerson(String backResponsePerson) {
        this.backResponsePerson = backResponsePerson == null ? null : backResponsePerson.trim();
    }

    public Date getNextBackDate() {
        return nextBackDate;
    }

    public void setNextBackDate(Date nextBackDate) {
        this.nextBackDate = nextBackDate;
    }

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    public BigDecimal getBackAmount() {
        return backAmount;
    }

    public void setBackAmount(BigDecimal backAmount) {
        this.backAmount = backAmount;
    }

    public BigDecimal getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        this.otherAmount = otherAmount;
    }
}