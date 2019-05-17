package com.erui.order.v2.model;

public class PurchRequisitionAttach {
    private Integer purchRequisitionId;

    private Integer attachId;

    private String tenant;

    public Integer getPurchRequisitionId() {
        return purchRequisitionId;
    }

    public void setPurchRequisitionId(Integer purchRequisitionId) {
        this.purchRequisitionId = purchRequisitionId;
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