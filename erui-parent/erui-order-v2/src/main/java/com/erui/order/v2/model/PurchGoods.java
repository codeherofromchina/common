package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.Date;

public class PurchGoods {
    private Integer id;

    private Integer projectId;

    private Integer purchContractId;

    private Integer purchContractGoodsId;

    private String projectNo;

    private Integer parentId;

    private String contractNo;

    private Integer purchId;

    private Integer goodsId;

    private Boolean exchanged;

    private Integer purchaseNum;

    private Integer inspectNum;

    private Integer goodNum;

    private BigDecimal nonTaxPrice;

    private BigDecimal purchasePrice;

    private BigDecimal purchaseTotalPrice;

    private String purchaseRemark;

    private BigDecimal taxPrice;

    private BigDecimal totalPrice;

    private Date createTime;

    private Integer preInspectNum;

    private String tenant;

    private String qualityInspectType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getPurchContractId() {
        return purchContractId;
    }

    public void setPurchContractId(Integer purchContractId) {
        this.purchContractId = purchContractId;
    }

    public Integer getPurchContractGoodsId() {
        return purchContractGoodsId;
    }

    public void setPurchContractGoodsId(Integer purchContractGoodsId) {
        this.purchContractGoodsId = purchContractGoodsId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getPurchId() {
        return purchId;
    }

    public void setPurchId(Integer purchId) {
        this.purchId = purchId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Boolean getExchanged() {
        return exchanged;
    }

    public void setExchanged(Boolean exchanged) {
        this.exchanged = exchanged;
    }

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public Integer getInspectNum() {
        return inspectNum;
    }

    public void setInspectNum(Integer inspectNum) {
        this.inspectNum = inspectNum;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public BigDecimal getNonTaxPrice() {
        return nonTaxPrice;
    }

    public void setNonTaxPrice(BigDecimal nonTaxPrice) {
        this.nonTaxPrice = nonTaxPrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchaseTotalPrice() {
        return purchaseTotalPrice;
    }

    public void setPurchaseTotalPrice(BigDecimal purchaseTotalPrice) {
        this.purchaseTotalPrice = purchaseTotalPrice;
    }

    public String getPurchaseRemark() {
        return purchaseRemark;
    }

    public void setPurchaseRemark(String purchaseRemark) {
        this.purchaseRemark = purchaseRemark;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPreInspectNum() {
        return preInspectNum;
    }

    public void setPreInspectNum(Integer preInspectNum) {
        this.preInspectNum = preInspectNum;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getQualityInspectType() {
        return qualityInspectType;
    }

    public void setQualityInspectType(String qualityInspectType) {
        this.qualityInspectType = qualityInspectType;
    }
}