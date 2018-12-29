package com.erui.report.model;

import java.util.Date;

public class InqRtnReason {
    private Long id;

    private String quotationNum;

    private Date rollinTime;

    private String inquiryUnit;

    private String inquiryArea;

    private String organization;

    private String returnSeason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuotationNum() {
        return quotationNum;
    }

    public void setQuotationNum(String quotationNum) {
        this.quotationNum = quotationNum == null ? null : quotationNum.trim();
    }

    public Date getRollinTime() {
        return rollinTime;
    }

    public void setRollinTime(Date rollinTime) {
        this.rollinTime = rollinTime;
    }

    public String getInquiryUnit() {
        return inquiryUnit;
    }

    public void setInquiryUnit(String inquiryUnit) {
        this.inquiryUnit = inquiryUnit == null ? null : inquiryUnit.trim();
    }

    public String getInquiryArea() {
        return inquiryArea;
    }

    public void setInquiryArea(String inquiryArea) {
        this.inquiryArea = inquiryArea == null ? null : inquiryArea.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getReturnSeason() {
        return returnSeason;
    }

    public void setReturnSeason(String returnSeason) {
        this.returnSeason = returnSeason == null ? null : returnSeason.trim();
    }
}