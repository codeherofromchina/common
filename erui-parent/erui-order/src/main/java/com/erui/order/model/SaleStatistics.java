package com.erui.order.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 销售业绩统计模型
 * Created by wangxiaodan on 2018/4/3.
 */
public class SaleStatistics {

    public SaleStatistics() {
    }


    public SaleStatistics(String region, String country,
                          Long orderNum, BigDecimal orderAmount,
                          Long oilOrderNum, BigDecimal oilOrderAmount,
                          Long nonOilOrderNum, BigDecimal nonOilOrderAmount) {
        this.region = region;
        this.country = country;
        this.orderNum = orderNum;
        this.orderAmount = orderAmount;
        this.oilOrderNum = oilOrderNum;
        this.oilOrderAmount = oilOrderAmount;
        this.nonOilOrderNum = nonOilOrderNum;
        this.nonOilOrderAmount = nonOilOrderAmount;
    }


    private Date startDate; //
    private Date endDate; //


    private String region;// 地区（大区）
    private String country; // 国家

    private long orderNum = 0; // 订单总数量
    private BigDecimal orderAmount = BigDecimal.ZERO; // 订单总金额

    private long oilOrderNum = 0; // 油气订单总数量
    private BigDecimal oilOrderAmount = BigDecimal.ZERO; // 油气订单总金额
    private BigDecimal oilOrderNumRate = BigDecimal.ZERO; // 油气订单总数量占比
    private BigDecimal oilOrderAmountRate = BigDecimal.ZERO; // 油气订单总金额占比


    private long nonOilOrderNum = 0; // 非油气订单总数量
    private BigDecimal nonOilOrderAmount = BigDecimal.ZERO; // 非油气订单总金额
    private BigDecimal nonOilOrderNumRate = BigDecimal.ZERO; // 油气订单总数量占比
    private BigDecimal nonOilOrderAmountRate = BigDecimal.ZERO; // 非油气订单总金额占比

    private long quotationNum = 0; // 询单总数量
    private BigDecimal quotationAmount = BigDecimal.ZERO; // 询单总金额
    private BigDecimal quotationNumConversionRate = BigDecimal.ZERO; // 询单总数量转化率
    private BigDecimal quotationAmountConversionRate = BigDecimal.ZERO; // 询单总金额转化率

    private long vipNum = 0; // 会员总数量
    private long two_re_purch = 0; // 2次复购率会员数量
    private long three_re_purch = 0; // 3次复购率会员数量
    private long more_re_purch = 0; // 3次以上复购率会员数量


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

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getOrderAmount() {
        if (orderAmount == null) {
            return BigDecimal.ZERO;
        }
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getOilOrderNum() {
        return oilOrderNum;
    }

    public void setOilOrderNum(Long oilOrderNum) {
        this.oilOrderNum = oilOrderNum;
    }

    public BigDecimal getOilOrderAmount() {
        return oilOrderAmount;
    }

    public void setOilOrderAmount(BigDecimal oilOrderAmount) {
        this.oilOrderAmount = oilOrderAmount;
    }

    public BigDecimal getOilOrderNumRate() {
        return oilOrderNumRate;
    }

    public void setOilOrderNumRate(BigDecimal oilOrderNumRate) {
        this.oilOrderNumRate = oilOrderNumRate;
    }

    public BigDecimal getOilOrderAmountRate() {
        return oilOrderAmountRate;
    }

    public void setOilOrderAmountRate(BigDecimal oilOrderAmountRate) {
        this.oilOrderAmountRate = oilOrderAmountRate;
    }

    public Long getNonOilOrderNum() {
        return nonOilOrderNum;
    }

    public void setNonOilOrderNum(Long nonOilOrderNum) {
        this.nonOilOrderNum = nonOilOrderNum;
    }

    public BigDecimal getNonOilOrderAmount() {
        return nonOilOrderAmount;
    }

    public void setNonOilOrderAmount(BigDecimal nonOilOrderAmount) {
        this.nonOilOrderAmount = nonOilOrderAmount;
    }

    public BigDecimal getNonOilOrderNumRate() {
        return nonOilOrderNumRate;
    }

    public void setNonOilOrderNumRate(BigDecimal nonOilOrderNumRate) {
        this.nonOilOrderNumRate = nonOilOrderNumRate;
    }

    public BigDecimal getNonOilOrderAmountRate() {
        return nonOilOrderAmountRate;
    }

    public void setNonOilOrderAmountRate(BigDecimal nonOilOrderAmountRate) {
        this.nonOilOrderAmountRate = nonOilOrderAmountRate;
    }

    public Long getQuotationNum() {
        return quotationNum;
    }

    public void setQuotationNum(Long quotationNum) {
        this.quotationNum = quotationNum;
    }

    public BigDecimal getQuotationAmount() {
        return quotationAmount;
    }

    public void setQuotationAmount(BigDecimal quotationAmount) {
        this.quotationAmount = quotationAmount;
    }

    public BigDecimal getQuotationNumConversionRate() {
        return quotationNumConversionRate;
    }

    public void setQuotationNumConversionRate(BigDecimal quotationNumConversionRate) {
        this.quotationNumConversionRate = quotationNumConversionRate;
    }

    public BigDecimal getQuotationAmountConversionRate() {
        return quotationAmountConversionRate;
    }

    public void setQuotationAmountConversionRate(BigDecimal quotationAmountConversionRate) {
        this.quotationAmountConversionRate = quotationAmountConversionRate;
    }

    public Long getVipNum() {
        return vipNum;
    }

    public void setVipNum(Long vipNum) {
        this.vipNum = vipNum;
    }

    public Long getTwo_re_purch() {
        return two_re_purch;
    }

    public void setTwo_re_purch(Long two_re_purch) {
        this.two_re_purch = two_re_purch;
    }

    public Long getThree_re_purch() {
        return three_re_purch;
    }

    public void setThree_re_purch(Long three_re_purch) {
        this.three_re_purch = three_re_purch;
    }

    public Long getMore_re_purch() {
        return more_re_purch;
    }

    public void setMore_re_purch(Long more_re_purch) {
        this.more_re_purch = more_re_purch;
    }


    // 计算占比例等信息
    public void computRateInfo() {
        // 计算  油气/非油气订单总数量占比
        if (orderNum > 0) {
            oilOrderNumRate = new BigDecimal(oilOrderNum / 1.0 / orderNum, new MathContext(4, RoundingMode.HALF_UP));
            nonOilOrderNumRate = new BigDecimal(nonOilOrderNum / 1.0 / orderNum, new MathContext(4, RoundingMode.HALF_UP));

        }
        // 询单总数量转化率
        if (quotationNum > 0) {
            quotationNumConversionRate = new BigDecimal(orderNum / 1.0 / quotationNum, new MathContext(4, RoundingMode.HALF_UP));
        }
        // 计算  油气/非油气订单总金额占比
        if (orderAmount != null && orderAmount.compareTo(BigDecimal.ZERO) > 0) {
            oilOrderAmountRate = oilOrderAmount.divide(orderAmount, 4, RoundingMode.HALF_UP);
            nonOilOrderAmountRate = nonOilOrderAmount.divide(orderAmount, 4, RoundingMode.HALF_UP);
        }
        // 询单总金额转化率
        if (orderAmount != null && quotationAmount != null && quotationAmount.compareTo(BigDecimal.ZERO) > 0) {
            quotationAmountConversionRate = orderAmount.divide(quotationAmount, 4, RoundingMode.HALF_UP);
        }
    }
}
