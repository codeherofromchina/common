package com.erui.power.model;

import java.util.Date;

public class OpLog {
    private Long id;

    private Long opId;

    private String category;

    private String action;

    private Long objId;

    private String opLog;

    private String opResult;

    private Date opAt;

    private Date createdAt;

    private String opNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOpId() {
        return opId;
    }

    public void setOpId(Long opId) {
        this.opId = opId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public String getOpLog() {
        return opLog;
    }

    public void setOpLog(String opLog) {
        this.opLog = opLog;
    }

    public String getOpResult() {
        return opResult;
    }

    public void setOpResult(String opResult) {
        this.opResult = opResult;
    }

    public Date getOpAt() {
        return opAt;
    }

    public void setOpAt(Date opAt) {
        this.opAt = opAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getOpNote() {
        return opNote;
    }

    public void setOpNote(String opNote) {
        this.opNote = opNote;
    }
}