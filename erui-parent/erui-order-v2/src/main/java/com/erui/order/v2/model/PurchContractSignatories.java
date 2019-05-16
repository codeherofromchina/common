package com.erui.order.v2.model;

import java.util.Date;

public class PurchContractSignatories {
    private Integer id;

    private Integer purchContractId;

    private Integer type;

    private String sellerBuyer;

    private String postalCode;

    private String legalRepresentative;

    private String agent;

    private String address;

    private String openingBank;

    private String accountNumber;

    private String creditCode;

    private String telephoneFax;

    private String dutyParagraph;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSellerBuyer() {
        return sellerBuyer;
    }

    public void setSellerBuyer(String sellerBuyer) {
        this.sellerBuyer = sellerBuyer;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getTelephoneFax() {
        return telephoneFax;
    }

    public void setTelephoneFax(String telephoneFax) {
        this.telephoneFax = telephoneFax;
    }

    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
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