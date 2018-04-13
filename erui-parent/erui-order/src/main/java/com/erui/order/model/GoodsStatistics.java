package com.erui.order.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品统计实体
 * Created by wangxiaodan on 2018/4/9.
 */
public class GoodsStatistics  {

    private Date startDate;
    private Date endDate;

    private String sku;
    private String proType;
    private String nameEn;
    private String nameZh;
    private String brand;
    private String region;
    private String country;
    private long orderNum = 0;
    private BigDecimal orderAmount = BigDecimal.ZERO;
    private long quotationNum = 0; // 询单总数量
    private BigDecimal quotationAmount = BigDecimal.ZERO; // 询单总金额

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public long getQuotationNum() {
        return quotationNum;
    }

    public void setQuotationNum(long quotationNum) {
        this.quotationNum = quotationNum;
    }

    public BigDecimal getQuotationAmount() {
        return quotationAmount;
    }

    public void setQuotationAmount(BigDecimal quotationAmount) {
        this.quotationAmount = quotationAmount;
    }
}
