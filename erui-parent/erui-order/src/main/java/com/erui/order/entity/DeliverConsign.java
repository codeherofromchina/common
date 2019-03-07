package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

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

    @OneToOne(mappedBy = "deliverConsign", fetch = FetchType.LAZY)
    private DeliverDetail deliverDetail;

    @Column(name = "dept_id")
    private Integer deptId;
    @Column(name = "co_id")
    private String coId;
    @Column(name = "exec_co_name")
    private String execCoName;
    @Column(name = "write_date")
    private Date writeDate;
    @Column(name = "arrival_date")
    private Date arrivalDate;
    @Column(name = "booking_date")
    private Date bookingDate;
    private Integer status;
    @Column(name = "deliver_yn")
    private Integer deliverYn =1;  //是否已发货      1:未发货  2：已发货
    @Column(name = "create_user_id")
    private Integer createUserId;
    @Column(name = "create_time")
    private Date createTime;
    private String country;
    private String region;
    private String remarks;
    //出口通知单附件
   /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "deliver_consign_attach",
            joinColumns = @JoinColumn(name = "deliver_consign_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))*/
   @Transient
    private List<Attachment> attachmentSet = new ArrayList<>();
    //收款信息
    @JoinColumn(name = "deliver_consign_id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<DeliverConsignPayment> deliverConsignPayments = new ArrayList<>();
    //出口通知单商品
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "deliver_consign_id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<DeliverConsignGoods> deliverConsignGoodsSet = new ArrayList<>();

    @Column(name = "advance_money")
    private  BigDecimal advanceMoney;   //预收金额      /应收账款余额

    @Column(name = "this_shipments_money")
    private  BigDecimal thisShipmentsMoney;   //本批次发货金额

    @Column(name = "line_of_credit")
    private BigDecimal lineOfCredit;    //授信额度

    @Column(name = "credit_available")
    private BigDecimal creditAvailable;    //可用授信额度

    @Transient
    private BigDecimal exchangeRate;    //订单汇率




    public List<DeliverConsignPayment> getDeliverConsignPayments() {
        return deliverConsignPayments;
    }


    public void setDeliverConsignPayments(List<DeliverConsignPayment> deliverConsignPayments) {
        this.deliverConsignPayments = deliverConsignPayments;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliverConsignNo() {
        return deliverConsignNo;
    }

    public void setDeliverConsignNo(String deliverConsignNo) {
        this.deliverConsignNo = deliverConsignNo;
    }

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
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

    public List<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(List<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }

    public List<DeliverConsignGoods> getDeliverConsignGoodsSet() {
        return deliverConsignGoodsSet;
    }

    public void setDeliverConsignGoodsSet(List<DeliverConsignGoods> deliverConsignGoodsSet) {
        this.deliverConsignGoodsSet = deliverConsignGoodsSet;
    }

    public DeliverDetail getDeliverDetail() {
        return deliverDetail;
    }

    public void setDeliverDetail(DeliverDetail deliverDetail) {
        this.deliverDetail = deliverDetail;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getLineOfCredit() {
        return lineOfCredit;
    }

    public void setLineOfCredit(BigDecimal lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
    }

    public BigDecimal getCreditAvailable() {
        return creditAvailable;
    }

    public void setCreditAvailable(BigDecimal creditAvailable) {
        this.creditAvailable = creditAvailable;
    }

    public BigDecimal getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(BigDecimal advanceMoney) {
        this.advanceMoney = advanceMoney;
    }

    public BigDecimal getThisShipmentsMoney() {
        return thisShipmentsMoney;
    }

    public void setThisShipmentsMoney(BigDecimal thisShipmentsMoney) {
        this.thisShipmentsMoney = thisShipmentsMoney;
    }
}