package com.erui.order.v2.model;

import java.util.Date;

public class DeliverConsignGoods {
    private Integer id;

    private Integer deliverConsignId;

    private Integer goodsId;

    private Integer sendNum;

    private String packRequire;

    private Date createTime;

    private Integer createUserId;

    private String outboundRemark;

    private Integer outboundNum;

    private Integer straightNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeliverConsignId() {
        return deliverConsignId;
    }

    public void setDeliverConsignId(Integer deliverConsignId) {
        this.deliverConsignId = deliverConsignId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public String getPackRequire() {
        return packRequire;
    }

    public void setPackRequire(String packRequire) {
        this.packRequire = packRequire;
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

    public String getOutboundRemark() {
        return outboundRemark;
    }

    public void setOutboundRemark(String outboundRemark) {
        this.outboundRemark = outboundRemark;
    }

    public Integer getOutboundNum() {
        return outboundNum;
    }

    public void setOutboundNum(Integer outboundNum) {
        this.outboundNum = outboundNum;
    }

    public Integer getStraightNum() {
        return straightNum;
    }

    public void setStraightNum(Integer straightNum) {
        this.straightNum = straightNum;
    }
}