package com.erui.order.v2.model;

public class DeliverNoticeWithBLOBs extends DeliverNotice {
    private String prepareReq;

    private String packageReq;

    public String getPrepareReq() {
        return prepareReq;
    }

    public void setPrepareReq(String prepareReq) {
        this.prepareReq = prepareReq;
    }

    public String getPackageReq() {
        return packageReq;
    }

    public void setPackageReq(String packageReq) {
        this.packageReq = packageReq;
    }
}