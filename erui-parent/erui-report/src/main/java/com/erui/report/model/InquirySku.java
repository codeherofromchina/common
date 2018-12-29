package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class InquirySku {
    private Long id;

    private Date rollinTime;

    private String quotationNum;

    private String isOilGas;

    private String platProCategory;

    private String proCategory;

    private Integer cateCount;

    private String isKeruiEquipParts;

    private String proBrand;

    private BigDecimal quoteUnitPrice;

    private BigDecimal quoteTotalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRollinTime() {
        return rollinTime;
    }

    public void setRollinTime(Date rollinTime) {
        this.rollinTime = rollinTime;
    }

    public String getQuotationNum() {
        return quotationNum;
    }

    public void setQuotationNum(String quotationNum) {
        this.quotationNum = quotationNum == null ? null : quotationNum.trim();
    }

    public String getIsOilGas() {
        return isOilGas;
    }

    public void setIsOilGas(String isOilGas) {
        this.isOilGas = isOilGas == null ? null : isOilGas.trim();
    }

    public String getPlatProCategory() {
        return platProCategory;
    }

    public void setPlatProCategory(String platProCategory) {
        this.platProCategory = platProCategory == null ? null : platProCategory.trim();
    }

    public String getProCategory() {
        return proCategory;
    }

    public void setProCategory(String proCategory) {
        this.proCategory = proCategory == null ? null : proCategory.trim();
    }

    public Integer getCateCount() {
        return cateCount;
    }

    public void setCateCount(Integer cateCount) {
        this.cateCount = cateCount;
    }

    public String getIsKeruiEquipParts() {
        return isKeruiEquipParts;
    }

    public void setIsKeruiEquipParts(String isKeruiEquipParts) {
        this.isKeruiEquipParts = isKeruiEquipParts == null ? null : isKeruiEquipParts.trim();
    }

    public String getProBrand() {
        return proBrand;
    }

    public void setProBrand(String proBrand) {
        this.proBrand = proBrand == null ? null : proBrand.trim();
    }

    public BigDecimal getQuoteUnitPrice() {
        return quoteUnitPrice;
    }

    public void setQuoteUnitPrice(BigDecimal quoteUnitPrice) {
        this.quoteUnitPrice = quoteUnitPrice;
    }

    public BigDecimal getQuoteTotalPrice() {
        return quoteTotalPrice;
    }

    public void setQuoteTotalPrice(BigDecimal quoteTotalPrice) {
        this.quoteTotalPrice = quoteTotalPrice;
    }
}