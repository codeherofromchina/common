package com.erui.order.v2.model;

import java.util.Date;

public class Instock {
    private Integer id;

    private Integer inspectReportId;

    private Integer uid;

    private Date instockDate;

    private Integer status;

    private Integer currentUserId;

    private String currentUserName;

    private Date createTime;

    private String department;

    private String inspectApplyNo;

    private String supplierName;

    private String uname;

    private Integer outCheck;

    private String submenuName;

    private Integer submenuId;

    private String tenant;

    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInspectReportId() {
        return inspectReportId;
    }

    public void setInspectReportId(Integer inspectReportId) {
        this.inspectReportId = inspectReportId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getInstockDate() {
        return instockDate;
    }

    public void setInstockDate(Date instockDate) {
        this.instockDate = instockDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Integer currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInspectApplyNo() {
        return inspectApplyNo;
    }

    public void setInspectApplyNo(String inspectApplyNo) {
        this.inspectApplyNo = inspectApplyNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getOutCheck() {
        return outCheck;
    }

    public void setOutCheck(Integer outCheck) {
        this.outCheck = outCheck;
    }

    public String getSubmenuName() {
        return submenuName;
    }

    public void setSubmenuName(String submenuName) {
        this.submenuName = submenuName;
    }

    public Integer getSubmenuId() {
        return submenuId;
    }

    public void setSubmenuId(Integer submenuId) {
        this.submenuId = submenuId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}