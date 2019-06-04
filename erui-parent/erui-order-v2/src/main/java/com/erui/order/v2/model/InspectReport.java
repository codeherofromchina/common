package com.erui.order.v2.model;

import java.util.Date;

public class InspectReport {
    private Integer id;

    private Integer inspectApplyId;

    private String inspectApplyNo;

    private Integer checkTimes;

    private Date checkDate;

    private Boolean reportFirst;

    private Integer checkDeptId;

    private String checkDeptName;

    private Integer checkUserId;

    private String checkUserName;

    private Date createTime;

    private Date doneDate;

    private String msg;

    private String ncrNo;

    private Boolean process;

    private String reportRemarks;

    private String supplierName;

    private Integer status;

    private Date lastDoneDate;

    private String tenant;

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

    public String getInspectApplyNo() {
        return inspectApplyNo;
    }

    public void setInspectApplyNo(String inspectApplyNo) {
        this.inspectApplyNo = inspectApplyNo;
    }

    public Integer getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(Integer checkTimes) {
        this.checkTimes = checkTimes;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Boolean getReportFirst() {
        return reportFirst;
    }

    public void setReportFirst(Boolean reportFirst) {
        this.reportFirst = reportFirst;
    }

    public Integer getCheckDeptId() {
        return checkDeptId;
    }

    public void setCheckDeptId(Integer checkDeptId) {
        this.checkDeptId = checkDeptId;
    }

    public String getCheckDeptName() {
        return checkDeptName;
    }

    public void setCheckDeptName(String checkDeptName) {
        this.checkDeptName = checkDeptName;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNcrNo() {
        return ncrNo;
    }

    public void setNcrNo(String ncrNo) {
        this.ncrNo = ncrNo;
    }

    public Boolean getProcess() {
        return process;
    }

    public void setProcess(Boolean process) {
        this.process = process;
    }

    public String getReportRemarks() {
        return reportRemarks;
    }

    public void setReportRemarks(String reportRemarks) {
        this.reportRemarks = reportRemarks;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastDoneDate() {
        return lastDoneDate;
    }

    public void setLastDoneDate(Date lastDoneDate) {
        this.lastDoneDate = lastDoneDate;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}