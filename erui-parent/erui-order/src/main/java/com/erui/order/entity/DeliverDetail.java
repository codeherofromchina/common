package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @OneToOne
    @JoinColumn(name = "deliver_notice_id")
    @JsonIgnore
    private DeliverNotice deliverNotice;    //看货通知单ID

    // 销售合同号
    @Transient
    private String contractNo;

    // 项目号
    @Transient
    private String projectNo;



    @Column(name = "product_discharged_no")
    private String productDischargedNo;    //产品放行单号

    @Column(name = "carrier_co")
    private String carrierCo;   //承运单位名称

    @Column(name = "Billing_date")
    private Date BillingDate;   //开单日期

    private String driver;  //司机姓名

    @Column(name = "plate_no")
    private String plateNo; //车牌号码

    @Column(name = "pickup_date")
    private Date pickupDate;    //取货日期

    @Column(name = "contact_tel")
    private String contactTel;  //联系电话

    @Column(name="handle_department")
    private String handleDepartment;    //经办部门

    @Column(name = "ware_houseman")
    private Integer wareHouseman;   //仓库经办人

    @Column(name = "send_date")
    private Date sendDate;  ///发运日期

    private Integer sender; //发运人员

    private Integer reviewer;   //协办/复核人

    @Column(name = "goods_chk_status")
    private String goodsChkStatus;  //实物检验结论

    @Column(name = "bills_chk_status")
    private String billsChkStatus;  //资料检验结论

    @Column(name = "checker_uid")
    private Integer checkerUid; //检验工程师

    @Column(name = "check_date")
    private Date checkDate; //检验日期

    @Column(name = "release_date")
    private Date releaseDate;   //放行日期

    @Column(name = "release_uid")
    private Integer releaseUid; //最终放行人

    @Column(name = "quality_leader_id")
    private Integer qualityLeaderId;    //质量分管领导

    private Integer applicant;  //特殊放行申请人

    @Column(name = "applicant_date")
    private Date applicantDate; //特殊放行申请日期

    private Integer approver;   //批准人

    @Column(name = "approval_date")
    private Date approvalDate;  //批准日期


    private String opinion; //审批意见

    @Column(name = "leave_date")
    private Date leaveDate; //出库时间

    @Column(name = "logistics_user_id")
    private Integer logisticsUserId;    //物流经办人

    @Column(name = "logistics_date")
    private Date logisticsDate; //物流经办时间

    @Column(name = "booking_time")
    private Date bookingTime;   //订舱时间

    @Column(name = "logi_invoice_no")
    private String logiInvoiceNo;   //物流发票号

    @Column(name = "packing_time")
    private Date packingTime;   //通知市场箱单时间

    @Column(name = "leave_factory")
    private Date leaveFactory;  //离厂时间

    @Column(name = "sailing_date")
    private Date sailingDate;   //船期或航班

    @Column(name = "customs_clearance")
    private Date customsClearance;  //报关放行时间

    @Column(name = "leave_port_time")
    private Date leavePortTime; //实际离港时间

    @Column(name = "accomplish_date")
    private Date accomplishDate; //实际完成时间

    @Column(name = "arrival_port_time")
    private Date arrivalPortTime;   //预计抵达时间

    /**
     * 出库到物流的状态 0：出库保存/草稿  1：出库提交  2：出库质检保存  3：出库质检提交 4：物流人已完整 5：完善物流状态中 6：项目完结
     */
    private int status = 0;

    @Column(name = "create_user_id")
    private Integer createUserId;   //创建人id

    @Column(name = "create_user_name")
    private String createUserName;  //创建人名字



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "deliver_detail_attach",
            joinColumns = @JoinColumn(name = "deliver_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
   /* @JsonIgnore*/
    private Set<Attachment> attachmentList = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "deliver_detail_goods",
            joinColumns = @JoinColumn(name = "deliver_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "deliver_consign_goods_id"))
    @JsonIgnore
    private Set<DeliverConsignGoods> deliverConsignGoodsSet = new HashSet<>();



    private String reason; //特殊情况产品放行原因

    private String logs;    //动态描述


    private String remarks; //备注


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductDischargedNo(String productDischargedNo) {
        this.productDischargedNo = productDischargedNo;
    }

    public String getProductDischargedNo() {
        return productDischargedNo;
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

    public void setHandleDepartment(String handleDepartment) {
        this.handleDepartment = handleDepartment;
    }

    public String getHandleDepartment() {
        return handleDepartment;
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

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
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

    public Date getAccomplishDate() {
        return accomplishDate;
    }

    public void setAccomplishDate(Date accomplishDate) {
        this.accomplishDate = accomplishDate;
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

    public void setBillingDate(Date billingDate) {
        BillingDate = billingDate;
    }

    public Date getBillingDate() {
        return BillingDate;
    }


    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectNo() {
        return projectNo;
    }
}