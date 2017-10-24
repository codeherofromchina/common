package com.erui.report.model;

import java.util.Date;

public class SupplyChain {
    private Integer id;

    private Date createAt;

    private String organization;

    private String category;

    private String itemClass;

    private Integer planSkuNum;

    private Integer finishSkuNum;

    private Integer planSpuNum;

    private Integer finishSpuNum;

    private Integer planSuppliNum;

    private Integer finishSuppliNum;

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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass == null ? null : itemClass.trim();
    }

    public Integer getPlanSkuNum() {
        return planSkuNum;
    }

    public void setPlanSkuNum(Integer planSkuNum) {
        this.planSkuNum = planSkuNum;
    }

    public Integer getFinishSkuNum() {
        return finishSkuNum;
    }

    public void setFinishSkuNum(Integer finishSkuNum) {
        this.finishSkuNum = finishSkuNum;
    }

    public Integer getPlanSpuNum() {
        return planSpuNum;
    }

    public void setPlanSpuNum(Integer planSpuNum) {
        this.planSpuNum = planSpuNum;
    }

    public Integer getFinishSpuNum() {
        return finishSpuNum;
    }

    public void setFinishSpuNum(Integer finishSpuNum) {
        this.finishSpuNum = finishSpuNum;
    }

    public Integer getPlanSuppliNum() {
        return planSuppliNum;
    }

    public void setPlanSuppliNum(Integer planSuppliNum) {
        this.planSuppliNum = planSuppliNum;
    }

    public Integer getFinishSuppliNum() {
        return finishSuppliNum;
    }

    public void setFinishSuppliNum(Integer finishSuppliNum) {
        this.finishSuppliNum = finishSuppliNum;
    }
}