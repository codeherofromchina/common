package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.Date;

public class CheckLog {
    private Integer id;

    private Integer orderId;

    private Date createTime;

    private String auditingProcess;

    private Integer auditingUserId;

    private String auditingUserName;

    private String nextAuditingProcess;

    private String nextAuditingUserId;

    private String auditingMsg;

    private String operation;

    private Integer type;

    private Integer joinId;

    private BigDecimal auditSeq;

    private String category;

    private String tenant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAuditingProcess() {
        return auditingProcess;
    }

    public void setAuditingProcess(String auditingProcess) {
        this.auditingProcess = auditingProcess;
    }

    public Integer getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(Integer auditingUserId) {
        this.auditingUserId = auditingUserId;
    }

    public String getAuditingUserName() {
        return auditingUserName;
    }

    public void setAuditingUserName(String auditingUserName) {
        this.auditingUserName = auditingUserName;
    }

    public String getNextAuditingProcess() {
        return nextAuditingProcess;
    }

    public void setNextAuditingProcess(String nextAuditingProcess) {
        this.nextAuditingProcess = nextAuditingProcess;
    }

    public String getNextAuditingUserId() {
        return nextAuditingUserId;
    }

    public void setNextAuditingUserId(String nextAuditingUserId) {
        this.nextAuditingUserId = nextAuditingUserId;
    }

    public String getAuditingMsg() {
        return auditingMsg;
    }

    public void setAuditingMsg(String auditingMsg) {
        this.auditingMsg = auditingMsg;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }

    public BigDecimal getAuditSeq() {
        return auditSeq;
    }

    public void setAuditSeq(BigDecimal auditSeq) {
        this.auditSeq = auditSeq;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}