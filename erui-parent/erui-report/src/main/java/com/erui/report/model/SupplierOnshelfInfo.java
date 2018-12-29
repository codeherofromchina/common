package com.erui.report.model;

import java.util.Date;

public class SupplierOnshelfInfo {
    private Integer id;

    private Date createAt;

    private String supplier;

    private Integer onshelfSpuNum;

    private Integer onshelfSkuNum;

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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public Integer getOnshelfSpuNum() {
        return onshelfSpuNum;
    }

    public void setOnshelfSpuNum(Integer onshelfSpuNum) {
        this.onshelfSpuNum = onshelfSpuNum;
    }

    public Integer getOnshelfSkuNum() {
        return onshelfSkuNum;
    }

    public void setOnshelfSkuNum(Integer onshelfSkuNum) {
        this.onshelfSkuNum = onshelfSkuNum;
    }
}