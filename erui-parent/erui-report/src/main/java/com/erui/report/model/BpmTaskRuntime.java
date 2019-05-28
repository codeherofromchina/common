package com.erui.report.model;

import java.util.Date;

/**
 * 流程--任务运行时
 */
public class BpmTaskRuntime {
    private Long id;
    // '业务类型'
    private String bizType;
    // '业务表主键ID'
    private Long bizObjId;
    // '流程定义ID'
    private String pdId;
    // '流程实例ID'
    private String piId;
    // '流程任务ID'
    private String taskId;
    // '流程节点ID'
    private String actId;
    // '流程节点名称'
    private String actName;
    // '执行人'
    private String assignee;
    // '候选人'
    private String candidateUsers;
    // '候选组'
    private String candidateGroups;
    // '创建时间'
    private Date createdAt;
    private String key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Long getBizObjId() {
        return bizObjId;
    }

    public void setBizObjId(Long bizObjId) {
        this.bizObjId = bizObjId;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCandidateUsers() {
        return candidateUsers;
    }

    public void setCandidateUsers(String candidateUsers) {
        this.candidateUsers = candidateUsers;
    }

    public String getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(String candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

