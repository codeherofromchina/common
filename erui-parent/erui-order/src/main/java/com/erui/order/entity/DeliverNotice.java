package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 看货通知单
 */
@Entity
@Table(name = "deliver_notice")
public class DeliverNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 货通知单号 自动生成
     */
    @Column(name = "deliver_notice_no")
    private String deliverNoticeNo;
    @OneToOne(mappedBy = "deliverNotice", fetch = FetchType.LAZY)
    private DeliverDetail deliverDetail;
    // 销售合同号
    @Transient
    private String contractNo;
    // 出口通知单号
    @Transient
    private String deliverConsignNo;

    @Transient
    private int page = 0;
    @Transient
    private int rows = 50;


    private int status; //看货通知单状态


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)  //看货通知单，出口发货通知单关联表
    @JoinTable(name = "deliver_notice_consign",
            joinColumns = @JoinColumn(name = "deliver_notice_id"),
            inverseJoinColumns = @JoinColumn(name = "deliver_consign_id"))
    @JsonIgnore
    private Set<DeliverConsign> deliverConsigns = new HashSet<>();

    @Column(name = "sender_id")
    private Integer senderId;   //下单人

    @Transient
    private String deliverConsignIds;   //出口通知单id

    /**
     * 下单人名称
     */
    @Column(name = "sender_name")
    private String senderName;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "send_date") //下单日期
    private Date sendDate;
    @Column(name = "trade_terms")
    private String tradeTerms;
    @Column(name = "to_place")
    private String toPlace;
    @Column(name = "transport_type_bn")
    private String transportTypeBn;
    @Column(name = "technical_uid")
    private Integer technicalUid;

    /**
     * 紧急程度 COMMONLY:一般 URGENT:紧急 PARTICULAR:异常紧急
     */
    private String urgency;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "delivery_date")
    private Date deliveryDate;

    private Integer numers;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "create_user_name")
    private String createUserName;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "prepare_req")
    private String prepareReq;
    @Column(name = "package_req")
    private String packageReq;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "deliver_notice_attach",
            joinColumns = @JoinColumn(name = "deliver_notice_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    private Set<Attachment> attachmentSet = new HashSet<>();


    public String getDeliverConsignIds() {
        return deliverConsignIds;
    }

    public void setDeliverConsignIds(String deliverConsignIds) {
        this.deliverConsignIds = deliverConsignIds;
    }

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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getDeliverConsignNo() {
        return deliverConsignNo;
    }

    public void setDeliverConsignNo(String deliverConsignNo) {
        this.deliverConsignNo = deliverConsignNo;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {

        return status;
    }

    public Set<DeliverConsign> getDeliverConsigns() {
        return deliverConsigns;
    }

    public void setDeliverConsigns(Set<DeliverConsign> deliverConsigns) {
        this.deliverConsigns = deliverConsigns;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPrepareReq() {
        return prepareReq;
    }

    public void setPrepareReq(String prepareReq) {
        this.prepareReq = prepareReq;
    }

    public String getPackageReq() {
        return packageReq;
    }

    public void setPackageReq(String packageReq) {
        this.packageReq = packageReq;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Set<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(Set<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }
}