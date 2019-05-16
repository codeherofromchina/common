package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.Date;

public class PurchContract {
    private Integer id;

    private String purchContractNo;

    private Integer type;

    private Integer status;

    private Date signingDate;

    private Integer supplierId;

    private String supplierName;

    private String signingPlace;

    private Integer agentId;

    private String agentName;

    private BigDecimal taxPoint;

    private String capitalizedPrice;

    private BigDecimal lowercasePrice;

    private String currencyBn;

    private String country;

    private String region;

    private Integer version;

    private Integer createUserId;

    private String createUserName;

    private Date updateTime;

    private Date createTime;

    private String tenant;

    private String goodsRemarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchContractNo() {
        return purchContractNo;
    }

    public void setPurchContractNo(String purchContractNo) {
        this.purchContractNo = purchContractNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSigningPlace() {
        return signingPlace;
    }

    public void setSigningPlace(String signingPlace) {
        this.signingPlace = signingPlace;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public BigDecimal getTaxPoint() {
        return taxPoint;
    }

    public void setTaxPoint(BigDecimal taxPoint) {
        this.taxPoint = taxPoint;
    }

    public String getCapitalizedPrice() {
        return capitalizedPrice;
    }

    public void setCapitalizedPrice(String capitalizedPrice) {
        this.capitalizedPrice = capitalizedPrice;
    }

    public BigDecimal getLowercasePrice() {
        return lowercasePrice;
    }

    public void setLowercasePrice(BigDecimal lowercasePrice) {
        this.lowercasePrice = lowercasePrice;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    public String getGoodsRemarks() {
        return goodsRemarks;
    }

    public void setGoodsRemarks(String goodsRemarks) {
        this.goodsRemarks = goodsRemarks;
    }
}