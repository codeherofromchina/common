package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * 出口发货通知单商品
 */
@Entity
@Table(name = "deliver_consign_goods")
public class DeliverConsignGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="goods_id")
    private Goods goods;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliver_consign_id")
    @JsonIgnore
    private DeliverConsign deliverConsign;

    @Transient
    private Integer gId;

    @Column(name = "send_num")
    private Integer sendNum;

    @Column(name = "pack_require")
    private String packRequire;

    @Column(name = "create_time")
    @JsonIgnore
    private Date createTime;

    @Column(name = "create_user_id")
    private Integer createUserId;

    //出库备注
    @Column(name = "outbound_remark")
    private String outboundRemark;

    @Column(name = "outbound_num")
    private Integer outboundNum = 0;  //出库数量

    @Column(name = "straight_num")
    private Integer straightNum = 0; //厂家直发数量

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "deliver_detail_goods",
            joinColumns = @JoinColumn(name = "deliver_consign_goods_id"),
            inverseJoinColumns = @JoinColumn(name = "deliver_detail_id"))
    @JsonIgnore
    private DeliverDetail deliverDetail;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DeliverConsign getDeliverConsign() {
        return deliverConsign;
    }

    public void setDeliverConsign(DeliverConsign deliverConsign) {
        this.deliverConsign = deliverConsign;
    }


    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getgId() {
        return gId;
    }

    public void setgId(Integer gId) {
        this.gId = gId;
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

    public DeliverDetail getDeliverDetail() {
        return deliverDetail;
    }

    public void setDeliverDetail(DeliverDetail deliverDetail) {
        this.deliverDetail = deliverDetail;
    }

    public Integer getOutboundNum() {
        return outboundNum;
    }

    public Integer getStraightNum() {
        return straightNum;
    }

    public void setOutboundNum(Integer outboundNum) {
        this.outboundNum = outboundNum;
    }

    public void setStraightNum(Integer straightNum) {
        this.straightNum = straightNum;
    }
}