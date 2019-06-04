package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.Date;

public class InspectApplyGoods {
    private Integer id;

    private Integer inspectApplyId;

    private Integer inspectReportId;

    private Integer goodsId;

    private Integer purchGoodsId;

    private Integer purchaseNum;

    private Integer inspectNum;

    private BigDecimal height;

    private String lwh;

    private Integer samples;

    private Integer unqualified;

    private String unqualifiedDesc;

    private Integer instockNum;

    private Date createTime;

    private String tenant;

    private Integer unqualifiedType;

    private String qualityInspectType;

    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInspectApplyId() {
        return inspectApplyId;
    }

    public void setInspectApplyId(Integer inspectApplyId) {
        this.inspectApplyId = inspectApplyId;
    }

    public Integer getInspectReportId() {
        return inspectReportId;
    }

    public void setInspectReportId(Integer inspectReportId) {
        this.inspectReportId = inspectReportId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getPurchGoodsId() {
        return purchGoodsId;
    }

    public void setPurchGoodsId(Integer purchGoodsId) {
        this.purchGoodsId = purchGoodsId;
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

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getLwh() {
        return lwh;
    }

    public void setLwh(String lwh) {
        this.lwh = lwh;
    }

    public Integer getSamples() {
        return samples;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }

    public Integer getUnqualified() {
        return unqualified;
    }

    public void setUnqualified(Integer unqualified) {
        this.unqualified = unqualified;
    }

    public String getUnqualifiedDesc() {
        return unqualifiedDesc;
    }

    public void setUnqualifiedDesc(String unqualifiedDesc) {
        this.unqualifiedDesc = unqualifiedDesc;
    }

    public Integer getInstockNum() {
        return instockNum;
    }

    public void setInstockNum(Integer instockNum) {
        this.instockNum = instockNum;
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

    public Integer getUnqualifiedType() {
        return unqualifiedType;
    }

    public void setUnqualifiedType(Integer unqualifiedType) {
        this.unqualifiedType = unqualifiedType;
    }

    public String getQualityInspectType() {
        return qualityInspectType;
    }

    public void setQualityInspectType(String qualityInspectType) {
        this.qualityInspectType = qualityInspectType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}