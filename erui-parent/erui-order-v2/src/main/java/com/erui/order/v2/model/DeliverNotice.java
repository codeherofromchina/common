package com.erui.order.v2.model;

import java.util.Date;

public class DeliverNotice {
    private Integer id;

    private String deliverNoticeNo;

    private Integer senderId;

    private String senderName;

    private Date sendDate;

    private String tradeTerms;

    private String toPlace;

    private String transportTypeBn;

    private Integer technicalUid;

    private String urgency;

    private Date deliveryDate;

    private Integer numers;

    private Date createTime;

    private Integer createUserId;

    private String createUserName;

    private Integer status;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliverNoticeNo() {
        return deliverNoticeNo;
    }

    public void setDeliverNoticeNo(String deliverNoticeNo) {
        this.deliverNoticeNo = deliverNoticeNo;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public String getTransportTypeBn() {
        return transportTypeBn;
    }

    public void setTransportTypeBn(String transportTypeBn) {
        this.transportTypeBn = transportTypeBn;
    }

    public Integer getTechnicalUid() {
        return technicalUid;
    }

    public void setTechnicalUid(Integer technicalUid) {
        this.technicalUid = technicalUid;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getNumers() {
        return numers;
    }

    public void setNumers(Integer numers) {
        this.numers = numers;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}