package com.erui.order.v2.model;

import java.util.Date;

public class InstockGoods {
    private Integer id;

    private Integer instockId;

    private String contractNo;

    private String projectNo;

    private Integer qualifiedNum;

    private Integer inspectApplyGoodsId;

    private Integer instockNum;

    private String instockStock;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String tenant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInstockId() {
        return instockId;
    }

    public void setInstockId(Integer instockId) {
        this.instockId = instockId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Integer getQualifiedNum() {
        return qualifiedNum;
    }

    public void setQualifiedNum(Integer qualifiedNum) {
        this.qualifiedNum = qualifiedNum;
    }

    public Integer getInspectApplyGoodsId() {
        return inspectApplyGoodsId;
    }

    public void setInspectApplyGoodsId(Integer inspectApplyGoodsId) {
        this.inspectApplyGoodsId = inspectApplyGoodsId;
    }

    public Integer getInstockNum() {
        return instockNum;
    }

    public void setInstockNum(Integer instockNum) {
        this.instockNum = instockNum;
    }

    public String getInstockStock() {
        return instockStock;
    }

    public void setInstockStock(String instockStock) {
        this.instockStock = instockStock;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}