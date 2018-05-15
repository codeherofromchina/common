package com.erui.order.entity;

import com.erui.order.util.IReceiverDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单-结算方式
 */
@Entity
@Table(name="order_payment")
public class DeliverConsignPayment implements IReceiverDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer type;

    private String topic;
    //出口通知单ID
    @Column(name = "deliver_consign_id")
    private Integer deliverConsignId;
    //订单ID
    @Column(name = "order_id")
    private Integer orderId;

    private BigDecimal money;

    @Column(name = "receipt_date")
    private Date receiptDate;
    @Column(name = " receipt_time")
    private Date  receiptTime;


    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

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

}