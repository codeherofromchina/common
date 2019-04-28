package com.erui.order.v2.model;

import java.util.Date;

public class Backlog {
    private Integer id;

    private String createDate;

    private String placeSystem;

    private String functionExplainName;

    private Integer functionExplainId;

    private String returnNo;

    private String informTheContent;

    private Integer hostId;

    private Integer followId;

    private Integer uid;

    private Integer delYn;

    private Date deleteTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPlaceSystem() {
        return placeSystem;
    }

    public void setPlaceSystem(String placeSystem) {
        this.placeSystem = placeSystem;
    }

    public String getFunctionExplainName() {
        return functionExplainName;
    }

    public void setFunctionExplainName(String functionExplainName) {
        this.functionExplainName = functionExplainName;
    }

    public Integer getFunctionExplainId() {
        return functionExplainId;
    }

    public void setFunctionExplainId(Integer functionExplainId) {
        this.functionExplainId = functionExplainId;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public String getInformTheContent() {
        return informTheContent;
    }

    public void setInformTheContent(String informTheContent) {
        this.informTheContent = informTheContent;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getDelYn() {
        return delYn;
    }

    public void setDelYn(Integer delYn) {
        this.delYn = delYn;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}