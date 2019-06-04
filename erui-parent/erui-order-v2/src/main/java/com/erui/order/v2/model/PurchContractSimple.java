package com.erui.order.v2.model;

import java.util.Date;

public class PurchContractSimple {
    private Integer id;

    private Integer purchContractId;

    private String productRequirement;

    private String warrantyPeriod;

    private Date shippingDate;

    private String designatedLocation;

    private String costBurden;

    private String inspectionAt;

    private String withinDays;

    private String methodAndTime;

    private String agreementName;

    private Date updateTime;

    private Date createTime;

    private String tenant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPurchContractId() {
        return purchContractId;
    }

    public void setPurchContractId(Integer purchContractId) {
        this.purchContractId = purchContractId;
    }

    public String getProductRequirement() {
        return productRequirement;
    }

    public void setProductRequirement(String productRequirement) {
        this.productRequirement = productRequirement;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getDesignatedLocation() {
        return designatedLocation;
    }

    public void setDesignatedLocation(String designatedLocation) {
        this.designatedLocation = designatedLocation;
    }

    public String getCostBurden() {
        return costBurden;
    }

    public void setCostBurden(String costBurden) {
        this.costBurden = costBurden;
    }

    public String getInspectionAt() {
        return inspectionAt;
    }

    public void setInspectionAt(String inspectionAt) {
        this.inspectionAt = inspectionAt;
    }

    public String getWithinDays() {
        return withinDays;
    }

    public void setWithinDays(String withinDays) {
        this.withinDays = withinDays;
    }

    public String getMethodAndTime() {
        return methodAndTime;
    }

    public void setMethodAndTime(String methodAndTime) {
        this.methodAndTime = methodAndTime;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}