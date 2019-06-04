package com.erui.order.v2.model;

public class PurchAttach {
    private Integer purchId;

    private Integer attachId;

    private String tenant;

    public Integer getPurchId() {
        return purchId;
    }

    public void setPurchId(Integer purchId) {
        this.purchId = purchId;
    }

    public Integer getAttachId() {
        return attachId;
    }

    public void setAttachId(Integer attachId) {
        this.attachId = attachId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}