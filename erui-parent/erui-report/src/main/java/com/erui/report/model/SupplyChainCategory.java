package com.erui.report.model;

import java.util.Date;

public class SupplyChainCategory {
    private Integer id;

    private Date createAt;

    private String itemClass;

    private Integer spuNum;

    private Integer skuNum;

    private Integer suppliNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass == null ? null : itemClass.trim();
    }

    public Integer getSpuNum() {
        return spuNum;
    }

    public void setSpuNum(Integer spuNum) {
        this.spuNum = spuNum;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

    public Integer getSuppliNum() {
        return suppliNum;
    }

    public void setSuppliNum(Integer suppliNum) {
        this.suppliNum = suppliNum;
    }
}