package com.erui.order.requestVo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderAcciuntAdd {

    private Integer orderId;    //订单id

    private String desc;    //描述

    private BigDecimal money;   //回款金额

    private BigDecimal discount;    //其他扣款金额

    private Date paymentDate;   //回款时间

    private BigDecimal goodsPrice;  //发货金额

    private Date deliverDate;

    private Integer createUserId;   //创建人

    private Integer updateUserId;   //创建人

    private Date updateTime;    //更新时间


    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getDesc() {
        return desc;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }
}
