package com.erui.order.v2.model;

import java.util.Date;

public class OrderLog {
    private Integer id;

    private Integer orderId;

    private Integer logType;

    private String operation;

    private Integer createId;

    private Integer ordersGoodsId;

    private String createName;

    private Date createTime;

    private Integer orderAccountId;

    private Date businessDate;

    private Integer deliverDetailId;

    private Integer logisticsDataId;

    private String enOperation;

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

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getOrdersGoodsId() {
        return ordersGoodsId;
    }

    public void setOrdersGoodsId(Integer ordersGoodsId) {
        this.ordersGoodsId = ordersGoodsId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderAccountId() {
        return orderAccountId;
    }

    public void setOrderAccountId(Integer orderAccountId) {
        this.orderAccountId = orderAccountId;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Integer getDeliverDetailId() {
        return deliverDetailId;
    }

    public void setDeliverDetailId(Integer deliverDetailId) {
        this.deliverDetailId = deliverDetailId;
    }

    public Integer getLogisticsDataId() {
        return logisticsDataId;
    }

    public void setLogisticsDataId(Integer logisticsDataId) {
        this.logisticsDataId = logisticsDataId;
    }

    public String getEnOperation() {
        return enOperation;
    }

    public void setEnOperation(String enOperation) {
        this.enOperation = enOperation;
    }
}