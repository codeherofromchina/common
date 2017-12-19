package com.erui.order.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 物流-出库单详情
 */
@Entity
@Table(name = "deliver_detail")
public class DeliverDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @OneToOne
    @JoinColumn(name = "deliver_notice_id")
    private DeliverNotice deliverNotice;

    @Column(name = "carrier_co")
    private String carrierCo;

    private String driver;

    @Column(name = "plate_no")
    private String plateNo;
    @Column(name = "pickup_date")
    private Date pickupDate;
    @Column(name = "contact_tel")
    private String contactTel;
    @Column(name = "ware_houseman")
    private Integer wareHouseman;
    @Column(name = "send_date")
    private Date sendDate;

    private Integer sender;

    private Integer reviewer;
    @Column(name = "goods_chk_status")
    private String goodsChkStatus;
    @Column(name = "bills_chk_status")
    private String billsChkStatus;
    @Column(name = "checker_uid")
    private Integer checkerUid;
    @Column(name = "check_date")
    private Date checkDate;
    @Column(name = "release_date")
    private Date releaseDate;
    @Column(name = "release_uid")
    private Integer releaseUid;
    @Column(name = "quality_leader_id")
    private Integer qualityLeaderId;

    private Integer applicant;
    @Column(name = "applicant_date")
    private Date applicantDate;

    private Integer approver;
    @Column(name = "approval_date")
    private Date approvalDate;

    private String opinion;
    @Column(name = "leave_date")
    private Date leaveDate;
    @Column(name = "logistics_user_id")
    private Integer logisticsUserId;
    @Column(name = "logistics_date")
    private Date logisticsDate;
    @Column(name = "booking_time")
    private Date bookingTime;
    @Column(name = "logi_invoice_no")
    private String logiInvoiceNo;
    @Column(name = "packing_time")
    private Date packingTime;
    @Column(name = "leave_factory")
    private Date leaveFactory;
    @Column(name = "sailing_date")
    private Date sailingDate;
    @Column(name = "customs_clearance")
    private Date customsClearance;
    @Column(name = "leave_port_time")
    private Date leavePortTime;
    @Column(name = "arrival_port_time")
    private Date arrivalPortTime;

    /**
     * 出库到物流的状态 0：出库保存/草稿  1：出库提交  2：出库质检保存  3：出库质检提交 4：物流人已完整 5：完善物流状态中 6：项目完结
     */
    private int status = 0;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "deliver_detail_attach",
            joinColumns = @JoinColumn(name = "deliver_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    private Set<Attachment> attachmentList = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "deliver_detail_goods",
            joinColumns = @JoinColumn(name = "deliver_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "deliver_consign_goods_id"))
    private Set<DeliverConsignGoods> deliverConsignGoodsSet = new HashSet<>();



    private String reason;

    private String logs;


    private String remarks;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public DeliverNotice getDeliverNotice() {
        return deliverNotice;
    }

    public void setDeliverNotice(DeliverNotice deliverNotice) {
        this.deliverNotice = deliverNotice;
    }

    public String getCarrierCo() {
        return carrierCo;
    }

    public void setCarrierCo(String carrierCo) {
        this.carrierCo = carrierCo;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public Integer getWareHouseman() {
        return wareHouseman;
    }

    public void setWareHouseman(Integer wareHouseman) {
        this.wareHouseman = wareHouseman;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }

    public String getGoodsChkStatus() {
        return goodsChkStatus;
    }

    public void setGoodsChkStatus(String goodsChkStatus) {
        this.goodsChkStatus = goodsChkStatus;
    }

    public String getBillsChkStatus() {
        return billsChkStatus;
    }

    public void setBillsChkStatus(String billsChkStatus) {
        this.billsChkStatus = billsChkStatus;
    }

    public Integer getCheckerUid() {
        return checkerUid;
    }

    public void setCheckerUid(Integer checkerUid) {
        this.checkerUid = checkerUid;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getReleaseUid() {
        return releaseUid;
    }

    public void setReleaseUid(Integer releaseUid) {
        this.releaseUid = releaseUid;
    }

    public Integer getQualityLeaderId() {
        return qualityLeaderId;
    }

    public void setQualityLeaderId(Integer qualityLeaderId) {
        this.qualityLeaderId = qualityLeaderId;
    }

    public Integer getApplicant() {
        return applicant;
    }

    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    public Date getApplicantDate() {
        return applicantDate;
    }

    public void setApplicantDate(Date applicantDate) {
        this.applicantDate = applicantDate;
    }

    public Integer getApprover() {
        return approver;
    }

    public void setApprover(Integer approver) {
        this.approver = approver;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Integer getLogisticsUserId() {
        return logisticsUserId;
    }

    public void setLogisticsUserId(Integer logisticsUserId) {
        this.logisticsUserId = logisticsUserId;
    }

    public Date getLogisticsDate() {
        return logisticsDate;
    }

    public void setLogisticsDate(Date logisticsDate) {
        this.logisticsDate = logisticsDate;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getLogiInvoiceNo() {
        return logiInvoiceNo;
    }

    public void setLogiInvoiceNo(String logiInvoiceNo) {
        this.logiInvoiceNo = logiInvoiceNo;
    }

    public Date getPackingTime() {
        return packingTime;
    }

    public void setPackingTime(Date packingTime) {
        this.packingTime = packingTime;
    }

    public Date getLeaveFactory() {
        return leaveFactory;
    }

    public void setLeaveFactory(Date leaveFactory) {
        this.leaveFactory = leaveFactory;
    }

    public Date getSailingDate() {
        return sailingDate;
    }

    public void setSailingDate(Date sailingDate) {
        this.sailingDate = sailingDate;
    }

    public Date getCustomsClearance() {
        return customsClearance;
    }

    public void setCustomsClearance(Date customsClearance) {
        this.customsClearance = customsClearance;
    }

    public Date getLeavePortTime() {
        return leavePortTime;
    }

    public void setLeavePortTime(Date leavePortTime) {
        this.leavePortTime = leavePortTime;
    }

    public Date getArrivalPortTime() {
        return arrivalPortTime;
    }

    public void setArrivalPortTime(Date arrivalPortTime) {
        this.arrivalPortTime = arrivalPortTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Set<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(Set<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public Set<DeliverConsignGoods> getDeliverConsignGoodsSet() {
        return deliverConsignGoodsSet;
    }

    public void setDeliverConsignGoodsSet(Set<DeliverConsignGoods> deliverConsignGoodsSet) {
        this.deliverConsignGoodsSet = deliverConsignGoodsSet;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}