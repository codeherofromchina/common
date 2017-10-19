package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class CreditExtension {
    private Long id;

    private Date creditDate;

    private String creditArea;

    private String creditCountry;

    private String mailCode;

    private String customerName;

    private BigDecimal relyCredit;

    private Date effectiveDate;

    private Date expiryDate;

    private BigDecimal usedAmount;

    private BigDecimal availableAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(Date creditDate) {
        this.creditDate = creditDate;
    }

    public String getCreditArea() {
        return creditArea;
    }

    public void setCreditArea(String creditArea) {
        this.creditArea = creditArea == null ? null : creditArea.trim();
    }

    public String getCreditCountry() {
        return creditCountry;
    }

    public void setCreditCountry(String creditCountry) {
        this.creditCountry = creditCountry == null ? null : creditCountry.trim();
    }

    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String mailCode) {
        this.mailCode = mailCode == null ? null : mailCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public BigDecimal getRelyCredit() {
        return relyCredit;
    }

    public void setRelyCredit(BigDecimal relyCredit) {
        this.relyCredit = relyCredit;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }
}