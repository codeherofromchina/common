package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单-发货信息
 */
@Entity
@Table(name = "order_account_deliver")
public class OrderAccountDeliver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     //订单-收款信息id

    @ManyToOne(fetch = FetchType.LAZY  )
    @JoinColumn(name = "order_id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Order order;

    private Integer seq;    //序号

    @Column(name = "`desc`")
    private String desc;    //描述

    @Column(name = "goods_price")
    private BigDecimal goodsPrice;  //发货金额

    @Column(name = "deliver_date")
    private Date deliverDate;   //发货时间

    @Column(name = "create_user_id")
    private Integer createUserId;   //创建人

    @Column(name = "create_time")
    private Date createTime;    //创建时间

    @Column(name = "delete_time")
    private Date deleteTime;    //删除时间

    @Column(name = "del_yn")
    private Integer delYn =1;  //删除标识    0：删除   1：存在'''

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
    public Integer getDelYn() {
        return delYn;
    }

    public void setDelYn(Integer delYn) {
        this.delYn = delYn;
    }
}