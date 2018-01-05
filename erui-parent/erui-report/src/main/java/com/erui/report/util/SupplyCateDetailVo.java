package com.erui.report.util;

public class SupplyCateDetailVo {
    private String itemClass;
    private int spuCount;
    private int  skuCount;
    private int supplierCount;
    private Double spuCompletionRate;
    private Double skuCompletionRate;
    private Double supplierCompletionRate;

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public int getSpuCount() {
        return spuCount;
    }

    public void setSpuCount(int spuCount) {
        this.spuCount = spuCount;
    }

    public int getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(int skuCount) {
        this.skuCount = skuCount;
    }

    public int getSupplierCount() {
        return supplierCount;
    }

    public void setSupplierCount(int supplierCount) {
        this.supplierCount = supplierCount;
    }

    public Double getSpuCompletionRate() {
        return spuCompletionRate;
    }

    public void setSpuCompletionRate(Double spuCompletionRate) {
        this.spuCompletionRate = spuCompletionRate;
    }

    public Double getSkuCompletionRate() {
        return skuCompletionRate;
    }

    public void setSkuCompletionRate(Double skuCompletionRate) {
        this.skuCompletionRate = skuCompletionRate;
    }

    public Double getSupplierCompletionRate() {
        return supplierCompletionRate;
    }

    public void setSupplierCompletionRate(Double supplierCompletionRate) {
        this.supplierCompletionRate = supplierCompletionRate;
    }
}
