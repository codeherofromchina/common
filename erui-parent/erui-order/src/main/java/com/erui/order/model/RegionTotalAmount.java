package com.erui.order.model;

import java.math.BigDecimal;

/**
 * 各个大区的金额总计
 * Created by wangxiaodan on 2018/4/3.
 */
public class RegionTotalAmount {

    private String region;
    private BigDecimal totalAmount;
    private int order;

    public RegionTotalAmount(String region,int order){
        this.region = region;
        this.order = order;
    }

    public String getRegion() {
        return region;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getOrder() {
        return order;
    }
}
