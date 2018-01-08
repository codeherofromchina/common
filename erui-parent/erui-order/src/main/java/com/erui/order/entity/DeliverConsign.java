package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 出口发货通知单
 */
@Entity
@Table(name = "deliver_consign")
public class DeliverConsign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 出口通知单号
     */
    @Column(name = "deliver_consign_no")
    private String deliverConsignNo;
    //orderId
    @Transient
    private Integer oId;
    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;
    @Column(name = "dept_id")
    private Integer deptId;
    @Column(name = "co_id")
    private Integer coId;
    @Column(name = "write_date")
    private Date writeDate;
    @Column(name = "arrival_date")
    private Date arrivalDate;
    @Column(name = "booking_date")
    private Date bookingDate;
    private Integer status;
    @Column(name = "deliver_yn")
    private Integer deliverYn;  //是否已发货
    @Column(name = "create_user_id")
    private Integer createUserId;
    @Column(name = "create_time")
    private Date createTime;
    private String remarks;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "deliver_consign_attach",
            joinColumns = @JoinColumn(name = "deliver_consign_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    private Set<Attachment> attachmentSet = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "deliver_consign_id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Set<DeliverConsignGoods> deliverConsignGoodsSet = new HashSet<>();

    public Integer getDeliverYn() {
        return deliverYn;
    }

    public DeliverConsign setDeliverYn(Integer deliverYn) {
        this.deliverYn = deliverYn;
        return this;
    }

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

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public String getDeliverConsignNo() {
        return deliverConsignNo;
    }

    public void setDeliverConsignNo(String deliverConsignNo) {
        this.deliverConsignNo = deliverConsignNo;
    }


    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getCoId() {
        return coId;
    }

    public void setCoId(Integer coId) {
        this.coId = coId;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeliverYn() {
        return deliverYn;
    }

    public void setDeliverYn(Integer deliverYn) {
        this.deliverYn = deliverYn;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Set<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(Set<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }

    public Set<DeliverConsignGoods> getDeliverConsignGoodsSet() {
        return deliverConsignGoodsSet;
    }

    public void setDeliverConsignGoodsSet(Set<DeliverConsignGoods> deliverConsignGoodsSet) {
        this.deliverConsignGoodsSet = deliverConsignGoodsSet;
    }

    public void setDeliverYn(int deliverYn) {
        this.deliverYn = deliverYn;
    }
}