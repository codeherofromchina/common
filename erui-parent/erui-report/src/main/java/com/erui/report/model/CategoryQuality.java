package com.erui.report.model;

import java.util.Date;

public class CategoryQuality {
    private Long id;

    private Date qualityControlDate;

    private Integer inspectionTotal;

    private Integer proInfactoryCheckPassCount;

    private String proInfactoryCheckPassRate;

    private Integer proOutfactoryTotal;

    private Integer proOutfactoryCheckCount;

    private String proOutfactoryCheckRate;

    private Integer assignmentsTotal;

    private Integer productsQualifiedCount;

    private String productsAssignmentsQualifiedRate;

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

    public String getProInfactoryCheckPassRate() {
        return proInfactoryCheckPassRate;
    }

    public void setProInfactoryCheckPassRate(String proInfactoryCheckPassRate) {
        this.proInfactoryCheckPassRate = proInfactoryCheckPassRate == null ? null : proInfactoryCheckPassRate.trim();
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

    public String getProOutfactoryCheckRate() {
        return proOutfactoryCheckRate;
    }

    public void setProOutfactoryCheckRate(String proOutfactoryCheckRate) {
        this.proOutfactoryCheckRate = proOutfactoryCheckRate == null ? null : proOutfactoryCheckRate.trim();
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

    public String getProductsAssignmentsQualifiedRate() {
        return productsAssignmentsQualifiedRate;
    }

    public void setProductsAssignmentsQualifiedRate(String productsAssignmentsQualifiedRate) {
        this.productsAssignmentsQualifiedRate = productsAssignmentsQualifiedRate == null ? null : productsAssignmentsQualifiedRate.trim();
    }
}