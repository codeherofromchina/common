package com.erui.report.model;

import java.util.Date;

public class StorageOrganiCount {
    private Integer id;

    private Date createAt;

    private String organiName;

    private Integer trayNum;

    private Integer productNum;

    private String remark;
    private String docType;

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

    public String getOrganiName() {
        return organiName;
    }

    public void setOrganiName(String organiName) {
        this.organiName = organiName == null ? null : organiName.trim();
    }

    public Integer getTrayNum() {
        return trayNum;
    }

    public void setTrayNum(Integer trayNum) {
        this.trayNum = trayNum;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null? null:docType.trim();
    }
}