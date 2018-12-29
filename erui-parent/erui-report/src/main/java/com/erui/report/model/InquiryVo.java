package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class InquiryVo {

    private String quotationNum;

    private String inquiryUnit;

    private String inquiryArea;

    private String organization;

    private String custName;

    private String custDescription;

    private Date rollinTime;

    private BigDecimal profitMargin;

    private BigDecimal quotationPrice;

    private BigDecimal quoteNeedTime;

    private BigDecimal wholeQuoteTime;

    private BigDecimal businessQuoteTime;

    private BigDecimal clearQuoteTime;

    private BigDecimal logisticsQuoteTime;

    private String quotedStatus;

    private Integer returnCount;

    public String getQuotationNum() {
        return quotationNum;
    }

    public void setQuotationNum(String quotationNum) {
        this.quotationNum = quotationNum;
    }

    public String getInquiryUnit() {
        return inquiryUnit;
    }

    public void setInquiryUnit(String inquiryUnit) {
        this.inquiryUnit = inquiryUnit;
    }

    public String getInquiryArea() {
        return inquiryArea;
    }

    public void setInquiryArea(String inquiryArea) {
        this.inquiryArea = inquiryArea;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustDescription() {
        return custDescription;
    }

    public void setCustDescription(String custDescription) {
        this.custDescription = custDescription;
    }

    public Date getRollinTime() {
        return rollinTime;
    }

    public void setRollinTime(Date rollinTime) {
        this.rollinTime = rollinTime;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        this.profitMargin = profitMargin;
    }

    public BigDecimal getQuotationPrice() {
        return quotationPrice;
    }

    public void setQuotationPrice(BigDecimal quotationPrice) {
        this.quotationPrice = quotationPrice;
    }

    public BigDecimal getQuoteNeedTime() {
        return quoteNeedTime;
    }

    public void setQuoteNeedTime(BigDecimal quoteNeedTime) {
        this.quoteNeedTime = quoteNeedTime;
    }

    public BigDecimal getWholeQuoteTime() {
        return wholeQuoteTime;
    }

    public void setWholeQuoteTime(BigDecimal wholeQuoteTime) {
        this.wholeQuoteTime = wholeQuoteTime;
    }

    public BigDecimal getBusinessQuoteTime() {
        return businessQuoteTime;
    }

    public void setBusinessQuoteTime(BigDecimal businessQuoteTime) {
        this.businessQuoteTime = businessQuoteTime;
    }

    public BigDecimal getClearQuoteTime() {
        return clearQuoteTime;
    }

    public void setClearQuoteTime(BigDecimal clearQuoteTime) {
        this.clearQuoteTime = clearQuoteTime;
    }

    public BigDecimal getLogisticsQuoteTime() {
        return logisticsQuoteTime;
    }

    public void setLogisticsQuoteTime(BigDecimal logisticsQuoteTime) {
        this.logisticsQuoteTime = logisticsQuoteTime;
    }

    public String getQuotedStatus() {
        return quotedStatus;
    }

    public void setQuotedStatus(String quotedStatus) {
        this.quotedStatus = quotedStatus;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }
}
