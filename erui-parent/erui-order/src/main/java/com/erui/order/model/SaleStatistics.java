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
                          Long nonOilOrderNum, BigDecimal nonOilOrderAmount,
                          Long crmOrderNum, BigDecimal crmOrderAmount) {
        this.region = region;
        this.country = country;
        this.orderNum = orderNum;
        this.orderAmount = orderAmount;
        this.oilOrderNum = oilOrderNum;
        this.oilOrderAmount = oilOrderAmount;
        this.nonOilOrderNum = nonOilOrderNum;
        this.nonOilOrderAmount = nonOilOrderAmount;
        this.crmOrderNum = crmOrderNum;
        this.crmOrderAmount = crmOrderAmount;
    }


    private Date startDate; //
    private Date endDate; //

    private String region;// 所属地区（大区）
    private String country; // 国家

    private String regionZh;// 所属地区——中文（大区）
    private String countryZh; // 国家--中文

    private long orderNum = 0; // 订单总数量
    private BigDecimal orderAmount = BigDecimal.ZERO; // 订单总金额

    private long oilOrderNum = 0; // 油气订单总数量
    private BigDecimal oilOrderAmount = BigDecimal.ZERO; // 油气订单总金额
    private BigDecimal oilOrderNumRate = BigDecimal.ZERO; // 油气订单总数量占比
    private BigDecimal oilOrderAmountRate = BigDecimal.ZERO; // 油气订单总金额占比


    private long nonOilOrderNum = 0; // 非油气订单总数量
    private BigDecimal nonOilOrderAmount = BigDecimal.ZERO; // 非油气订单总金额
    private BigDecimal nonOilOrderNumRate = BigDecimal.ZERO; // 非油气订单总数量占比
    private BigDecimal nonOilOrderAmountRate = BigDecimal.ZERO; // 非油气订单总金额占比
    private long crmOrderNum = 0; //
    private BigDecimal crmOrderAmount = BigDecimal.ZERO;
    private BigDecimal crmOrderNumRate = BigDecimal.ZERO; // 询单总数量占比率
    private BigDecimal crmOrderAmountRate = BigDecimal.ZERO; // 询单总金额占比率

    private long quotationNum = 0; // 询单总数量
    private BigDecimal quotationAmount = BigDecimal.ZERO; // 询单总金额


    private long vipNum = 0; // 会员总数量
    private long oneRePurch = 0; // 1次复购率会员数量
    private long twoRePurch = 0; // 2次复购率会员数量
    private long threeRePurch = 0; // 3次复购率会员数量
    private long moreRePurch = 0; // 3次以上复购率会员数量


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

    public String getRegionZh() {
        return regionZh;
    }

    public void setRegionZh(String regionZh) {
        this.regionZh = regionZh;
    }

    public void setCountryZh(String countryZh) {
        this.countryZh = countryZh;
    }

    public String getCountryZh() {
        return countryZh;
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

    public long getOilOrderNum() {
        return oilOrderNum;
    }

    public void setOilOrderNum(long oilOrderNum) {
        this.oilOrderNum = oilOrderNum;
    }

    public BigDecimal getOilOrderAmount() {
        return oilOrderAmount;
    }

    public void setOilOrderAmount(BigDecimal oilOrderAmount) {
        this.oilOrderAmount = oilOrderAmount;
    }

    public BigDecimal getOilOrderNumRate() {
        if (orderNum > 0) {
            oilOrderNumRate = new BigDecimal(oilOrderNum / 1.0 / orderNum, new MathContext(4, RoundingMode.HALF_UP));
        }
        return oilOrderNumRate;
    }

    public String getOilOrderNumRateStr() {
        BigDecimal oilOrderNumRate = getOilOrderNumRate();
        BigDecimal bigDecimal = oilOrderNumRate.movePointRight(2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString() + "%";
    }


    public BigDecimal getOilOrderAmountRate() {
        if (orderAmount != null && orderAmount.compareTo(BigDecimal.ZERO) > 0) {
            oilOrderAmountRate = oilOrderAmount.divide(orderAmount, 4, RoundingMode.HALF_UP);
        }
        return oilOrderAmountRate;
    }


    public String getOilOrderAmountRateStr() {
        BigDecimal oilOrderNumRate = getOilOrderAmountRate();
        BigDecimal bigDecimal = oilOrderNumRate.movePointRight(2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString() + "%";
    }

    public long getNonOilOrderNum() {
        return nonOilOrderNum;
    }

    public void setNonOilOrderNum(long nonOilOrderNum) {
        this.nonOilOrderNum = nonOilOrderNum;
    }

    public BigDecimal getNonOilOrderAmount() {
        return nonOilOrderAmount;
    }

    public void setNonOilOrderAmount(BigDecimal nonOilOrderAmount) {
        this.nonOilOrderAmount = nonOilOrderAmount;
    }

    public BigDecimal getNonOilOrderNumRate() {
        if (orderNum > 0) {
            nonOilOrderNumRate = new BigDecimal(nonOilOrderNum / 1.0 / orderNum, new MathContext(4, RoundingMode.HALF_UP));
        }
        return nonOilOrderNumRate;
    }


    public String getNonOilOrderNumRateStr() {
        BigDecimal nonOilOrderNumRate = getNonOilOrderNumRate();
        BigDecimal bigDecimal = nonOilOrderNumRate.movePointRight(2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString() + "%";
    }


    public BigDecimal getNonOilOrderAmountRate() {
        if (orderAmount != null && orderAmount.compareTo(BigDecimal.ZERO) > 0) {
            nonOilOrderAmountRate = nonOilOrderAmount.divide(orderAmount, 4, RoundingMode.HALF_UP);
        }
        return nonOilOrderAmountRate;
    }


    public String getNonOilOrderAmountRateStr() {
        BigDecimal nonOilOrderAmountRate = getNonOilOrderAmountRate();
        BigDecimal bigDecimal = nonOilOrderAmountRate.movePointRight(2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString() + "%";
    }

    public long getCrmOrderNum() {
        return crmOrderNum;
    }

    public void setCrmOrderNum(long crmOrderNum) {
        this.crmOrderNum = crmOrderNum;
    }

    public BigDecimal getCrmOrderAmount() {
        return crmOrderAmount;
    }

    public void setCrmOrderAmount(BigDecimal crmOrderAmount) {
        this.crmOrderAmount = crmOrderAmount;
    }

    public BigDecimal getCrmOrderNumRate() {
        if (orderNum > 0) {
            crmOrderNumRate = new BigDecimal(crmOrderNum / 1.0 / orderNum, new MathContext(4, RoundingMode.HALF_UP));
        }
        return crmOrderNumRate;
    }

    public String getCrmOrderNumRateStr() {
        BigDecimal crmOrderNumRate = getCrmOrderNumRate();
        BigDecimal bigDecimal = crmOrderNumRate.movePointRight(2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString() + "%";
    }


    public BigDecimal getCrmOrderAmountRate() {
        if (orderAmount != null && orderAmount.compareTo(BigDecimal.ZERO) > 0) {
            crmOrderAmountRate = crmOrderAmount.divide(orderAmount, 4, RoundingMode.HALF_UP);
        }
        return crmOrderAmountRate;
    }

    public String getCrmOrderAmountRateStr() {
        BigDecimal crmOrderAmountRate = getCrmOrderAmountRate();
        BigDecimal bigDecimal = crmOrderAmountRate.movePointRight(2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString() + "%";
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

    public long getVipNum() {
        return vipNum;
    }

    public void setVipNum(long vipNum) {
        this.vipNum = vipNum;
    }

    public long getOneRePurch() {
        return oneRePurch;
    }

    public void setOneRePurch(long oneRePurch) {
        this.oneRePurch = oneRePurch;
    }

    public long getTwoRePurch() {
        return twoRePurch;
    }

    public void setTwoRePurch(long twoRePurch) {
        this.twoRePurch = twoRePurch;
    }

    public long getThreeRePurch() {
        return threeRePurch;
    }

    public void setThreeRePurch(long threeRePurch) {
        this.threeRePurch = threeRePurch;
    }

    public long getMoreRePurch() {
        return moreRePurch;
    }

    public void setMoreRePurch(long moreRePurch) {
        this.moreRePurch = moreRePurch;
    }
}
