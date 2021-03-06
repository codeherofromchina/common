package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class CategoryQuality {
    private Long id;

    private Date qualityControlDate;

    private Integer inspectionTotal;

    private Integer proInfactoryCheckPassCount;

    private BigDecimal proInfactoryCheckPassRate;

    private Integer proOutfactoryTotal;

    private Integer proOutfactoryCheckCount;

    private BigDecimal proOutfactoryCheckRate;

    private Integer assignmentsTotal;

    private Integer productsQualifiedCount;

    private BigDecimal productsAssignmentsQualifiedRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getQualityControlDate() {
        return qualityControlDate;
    }

    public void setQualityControlDate(Date qualityControlDate) {
        this.qualityControlDate = qualityControlDate;
    }

    public Integer getInspectionTotal() {
        return inspectionTotal;
    }

    public void setInspectionTotal(Integer inspectionTotal) {
        this.inspectionTotal = inspectionTotal;
    }

    public Integer getProInfactoryCheckPassCount() {
        return proInfactoryCheckPassCount;
    }

    public void setProInfactoryCheckPassCount(Integer proInfactoryCheckPassCount) {
        this.proInfactoryCheckPassCount = proInfactoryCheckPassCount;
    }

    public BigDecimal getProInfactoryCheckPassRate() {
        return proInfactoryCheckPassRate;
    }

    public void setProInfactoryCheckPassRate(BigDecimal proInfactoryCheckPassRate) {
        this.proInfactoryCheckPassRate = proInfactoryCheckPassRate;
    }

    public Integer getProOutfactoryTotal() {
        return proOutfactoryTotal;
    }

    public void setProOutfactoryTotal(Integer proOutfactoryTotal) {
        this.proOutfactoryTotal = proOutfactoryTotal;
    }

    public Integer getProOutfactoryCheckCount() {
        return proOutfactoryCheckCount;
    }

    public void setProOutfactoryCheckCount(Integer proOutfactoryCheckCount) {
        this.proOutfactoryCheckCount = proOutfactoryCheckCount;
    }

    public BigDecimal getProOutfactoryCheckRate() {
        return proOutfactoryCheckRate;
    }

    public void setProOutfactoryCheckRate(BigDecimal proOutfactoryCheckRate) {
        this.proOutfactoryCheckRate = proOutfactoryCheckRate;
    }

    public Integer getAssignmentsTotal() {
        return assignmentsTotal;
    }

    public void setAssignmentsTotal(Integer assignmentsTotal) {
        this.assignmentsTotal = assignmentsTotal;
    }

    public Integer getProductsQualifiedCount() {
        return productsQualifiedCount;
    }

    public void setProductsQualifiedCount(Integer productsQualifiedCount) {
        this.productsQualifiedCount = productsQualifiedCount;
    }

    public BigDecimal getProductsAssignmentsQualifiedRate() {
        return productsAssignmentsQualifiedRate;
    }

    public void setProductsAssignmentsQualifiedRate(BigDecimal productsAssignmentsQualifiedRate) {
        this.productsAssignmentsQualifiedRate = productsAssignmentsQualifiedRate;
    }
}