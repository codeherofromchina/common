package com.erui.report.util;

import java.math.BigDecimal;

public class IsOilVo {
    private String  isOil;
    private int skuCount;
    private int cateCount;
    private BigDecimal skuAmount;

    public String getIsOil() {
        return isOil;
    }

    public void setIsOil(String isOil) {
        this.isOil = isOil;
    }

    public int getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(int skuCount) {
        this.skuCount = skuCount;
    }

    public int getCateCount() {
        return cateCount;
    }

    public void setCateCount(int cateCount) {
        this.cateCount = cateCount;
    }

    public BigDecimal getSkuAmount() {
        return skuAmount;
    }

    public void setSkuAmount(BigDecimal skuAmount) {
        this.skuAmount = skuAmount;
    }
}
