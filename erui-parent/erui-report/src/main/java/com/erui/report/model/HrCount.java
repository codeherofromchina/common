package com.erui.report.model;

import java.util.Date;

public class HrCount {
    private Integer id;

    private Date createAt;

    private String department;

    private Integer planCount;

    private Integer regularCount;

    private Integer chinaCount;

    private Integer foreignCount;

    private Integer newCount;

    private Integer groupTransferIn;

    private Integer groupTransferOut;

    private Integer dimissionCount;

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public Integer getPlanCount() {
        return planCount;
    }

    public void setPlanCount(Integer planCount) {
        this.planCount = planCount;
    }

    public Integer getRegularCount() {
        return regularCount;
    }

    public void setRegularCount(Integer regularCount) {
        this.regularCount = regularCount;
    }

    public Integer getChinaCount() {
        return chinaCount;
    }

    public void setChinaCount(Integer chinaCount) {
        this.chinaCount = chinaCount;
    }

    public Integer getForeignCount() {
        return foreignCount;
    }

    public void setForeignCount(Integer foreignCount) {
        this.foreignCount = foreignCount;
    }

    public Integer getNewCount() {
        return newCount;
    }

    public void setNewCount(Integer newCount) {
        this.newCount = newCount;
    }

    public Integer getGroupTransferIn() {
        return groupTransferIn;
    }

    public void setGroupTransferIn(Integer groupTransferIn) {
        this.groupTransferIn = groupTransferIn;
    }

    public Integer getGroupTransferOut() {
        return groupTransferOut;
    }

    public void setGroupTransferOut(Integer groupTransferOut) {
        this.groupTransferOut = groupTransferOut;
    }

    public Integer getDimissionCount() {
        return dimissionCount;
    }

    public void setDimissionCount(Integer dimissionCount) {
        this.dimissionCount = dimissionCount;
    }
}