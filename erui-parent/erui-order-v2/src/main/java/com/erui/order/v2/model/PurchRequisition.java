package com.erui.order.v2.model;

import java.util.Date;

public class PurchRequisition {
    private Integer id;

    private Integer orderId;

    private Integer projectId;

    private String projectNo;

    private String contractNo;

    private Integer pmUid;

    private Integer department;

    private Date submitDate;

    private String tradeMethod;

    private String transModeBn;

    private Boolean factorySend;

    private String deliveryPlace;

    private String requirements;

    private Integer status;

    private Integer purchStatus;

    private Date createTime;

    private String purchaseName;

    private Integer purchaseUid;

    private Integer singlePersonId;

    private String singlePerson;

    private Date updateTime;

    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getPmUid() {
        return pmUid;
    }

    public void setPmUid(Integer pmUid) {
        this.pmUid = pmUid;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getTradeMethod() {
        return tradeMethod;
    }

    public void setTradeMethod(String tradeMethod) {
        this.tradeMethod = tradeMethod;
    }

    public String getTransModeBn() {
        return transModeBn;
    }

    public void setTransModeBn(String transModeBn) {
        this.transModeBn = transModeBn;
    }

    public Boolean getFactorySend() {
        return factorySend;
    }

    public void setFactorySend(Boolean factorySend) {
        this.factorySend = factorySend;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPurchStatus() {
        return purchStatus;
    }

    public void setPurchStatus(Integer purchStatus) {
        this.purchStatus = purchStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public Integer getPurchaseUid() {
        return purchaseUid;
    }

    public void setPurchaseUid(Integer purchaseUid) {
        this.purchaseUid = purchaseUid;
    }

    public Integer getSinglePersonId() {
        return singlePersonId;
    }

    public void setSinglePersonId(Integer singlePersonId) {
        this.singlePersonId = singlePersonId;
    }

    public String getSinglePerson() {
        return singlePerson;
    }

    public void setSinglePerson(String singlePerson) {
        this.singlePerson = singlePerson;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}