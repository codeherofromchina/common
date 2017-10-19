package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class MarketerCount {
    private Long id;

    private Date createTime;

    private String area;

    private String country;

    private String marketer;

    private Integer inquiryCount;

    private Integer quoteCount;

    private Integer bargainCount;

    private BigDecimal bargainAmount;

    private BigDecimal inquiryAmount;

    private Integer newMemberCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getMarketer() {
        return marketer;
    }

    public void setMarketer(String marketer) {
        this.marketer = marketer == null ? null : marketer.trim();
    }

    public Integer getInquiryCount() {
        return inquiryCount;
    }

    public void setInquiryCount(Integer inquiryCount) {
        this.inquiryCount = inquiryCount;
    }

    public Integer getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(Integer quoteCount) {
        this.quoteCount = quoteCount;
    }

    public Integer getBargainCount() {
        return bargainCount;
    }

    public void setBargainCount(Integer bargainCount) {
        this.bargainCount = bargainCount;
    }

    public BigDecimal getBargainAmount() {
        return bargainAmount;
    }

    public void setBargainAmount(BigDecimal bargainAmount) {
        this.bargainAmount = bargainAmount;
    }

    public BigDecimal getInquiryAmount() {
        return inquiryAmount;
    }

    public void setInquiryAmount(BigDecimal inquiryAmount) {
        this.inquiryAmount = inquiryAmount;
    }

    public Integer getNewMemberCount() {
        return newMemberCount;
    }

    public void setNewMemberCount(Integer newMemberCount) {
        this.newMemberCount = newMemberCount;
    }
}