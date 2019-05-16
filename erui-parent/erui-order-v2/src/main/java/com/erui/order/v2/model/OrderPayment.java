package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderPayment {
    private Integer id;

    private Integer orderId;

    private Integer type;

    private String topic;

    private BigDecimal money;

    private Date receiptDate;

    private Date createTime;

    private Integer receiptTime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Integer receiptTime) {
        this.receiptTime = receiptTime;
    }
}