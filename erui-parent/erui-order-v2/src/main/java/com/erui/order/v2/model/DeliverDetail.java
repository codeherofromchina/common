package com.erui.order.v2.model;

import java.util.Date;

public class DeliverDetail {
    private Integer id;

    private String deliverDetailNo;

    private Integer deliverNoticeId;

    private Date billingDate;

    private String carrierCo;

    private String driver;

    private String plateNo;

    private Date pickupDate;

    private String contactTel;

    private String handleDepartment;

    private Integer wareHouseman;

    private String wareHousemanName;

    private Date sendDate;

    private Integer sender;

    private String senderName;

    private Integer reviewer;

    private String reviewerName;

    private String goodsChkStatus;

    private String billsChkStatus;

    private Integer checkerUid;

    private String checkerName;

    private String checkDept;

    private Date checkDate;

    private Date releaseDate;

    private String releaseName;

    private Integer releaseUid;

    private Integer qualityLeaderId;

    private String qualityLeaderName;

    private Integer applicant;

    private String applicantName;

    private Date applicantDate;

    private Integer approver;

    private String approverName;

    private Date approvalDate;

    private String opinion;

    private Date leaveDate;

    private Integer logisticsUserId;

    private String logisticsUserName;

    private Date logisticsDate;

    private Date bookingTime;

    private String logiInvoiceNo;

    private Date packingTime;

    private Date leaveFactory;

    private Date sailingDate;

    private Date customsClearance;

    private Date leavePortTime;

    private Date arrivalPortTime;

    private String createUserName;

    private Integer createUserId;

    private Integer status;

    private Integer packTotal;

    private Date theEndTime;

    private Date confirmTheGoods;

    private Date accomplishDate;

    private Integer deliverConsignId;

    private Integer outCheck;

    private String submenuName;

    private Integer submenuId;

    private String tenant = "erui";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliverDetailNo() {
        return deliverDetailNo;
    }

    public void setDeliverDetailNo(String deliverDetailNo) {
        this.deliverDetailNo = deliverDetailNo;
    }

    public Integer getDeliverNoticeId() {
        return deliverNoticeId;
    }

    public void setDeliverNoticeId(Integer deliverNoticeId) {
        this.deliverNoticeId = deliverNoticeId;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
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

    public String getHandleDepartment() {
        return handleDepartment;
    }

    public void setHandleDepartment(String handleDepartment) {
        this.handleDepartment = handleDepartment;
    }

    public Integer getWareHouseman() {
        return wareHouseman;
    }

    public void setWareHouseman(Integer wareHouseman) {
        this.wareHouseman = wareHouseman;
    }

    public String getWareHousemanName() {
        return wareHousemanName;
    }

    public void setWareHousemanName(String wareHousemanName) {
        this.wareHousemanName = wareHousemanName;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
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

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getCheckDept() {
        return checkDept;
    }

    public void setCheckDept(String checkDept) {
        this.checkDept = checkDept;
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

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
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

    public String getQualityLeaderName() {
        return qualityLeaderName;
    }

    public void setQualityLeaderName(String qualityLeaderName) {
        this.qualityLeaderName = qualityLeaderName;
    }

    public Integer getApplicant() {
        return applicant;
    }

    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
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

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
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

    public String getLogisticsUserName() {
        return logisticsUserName;
    }

    public void setLogisticsUserName(String logisticsUserName) {
        this.logisticsUserName = logisticsUserName;
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

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPackTotal() {
        return packTotal;
    }

    public void setPackTotal(Integer packTotal) {
        this.packTotal = packTotal;
    }

    public Date getTheEndTime() {
        return theEndTime;
    }

    public void setTheEndTime(Date theEndTime) {
        this.theEndTime = theEndTime;
    }

    public Date getConfirmTheGoods() {
        return confirmTheGoods;
    }

    public void setConfirmTheGoods(Date confirmTheGoods) {
        this.confirmTheGoods = confirmTheGoods;
    }

    public Date getAccomplishDate() {
        return accomplishDate;
    }

    public void setAccomplishDate(Date accomplishDate) {
        this.accomplishDate = accomplishDate;
    }

    public Integer getDeliverConsignId() {
        return deliverConsignId;
    }

    public void setDeliverConsignId(Integer deliverConsignId) {
        this.deliverConsignId = deliverConsignId;
    }

    public Integer getOutCheck() {
        return outCheck;
    }

    public void setOutCheck(Integer outCheck) {
        this.outCheck = outCheck;
    }

    public String getSubmenuName() {
        return submenuName;
    }

    public void setSubmenuName(String submenuName) {
        this.submenuName = submenuName;
    }

    public Integer getSubmenuId() {
        return submenuId;
    }

    public void setSubmenuId(Integer submenuId) {
        this.submenuId = submenuId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    /**
     * 出库到物流的状态1：出库保存/草稿 2：出库提交 3：出库质检保存  4：出库质检提交 5：确认出库 6：完善物流状态中 7：项目完结
     */
    public static enum StatusEnum {
        SAVED_OUTSTOCK(1, "出库保存"), SUBMITED_OUTSTOCK(2, "出库提交"), SAVED_OUT_INSPECT(3, "出库质检保存"),
        SUBMITED_OUT_INSPECT(4, "出库质检提交"), PROCESS_LOGI_PERSON(5, "确认出库"), PROCESS_LOGI(6, "完善物流状态中"), DONE_PROJECT(7, "项目完结");

        private int statusCode;
        private String statusMsg;

        StatusEnum(int statusCode, String statusMsg) {
            this.statusCode = statusCode;
            this.statusMsg = statusMsg;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

        public static StatusEnum fromStatusCode(Integer statusCode) {
            if (statusCode != null) {
                int sInt = statusCode.intValue();
                for (StatusEnum se : StatusEnum.values()) {
                    if (se.statusCode == sInt) {
                        return se;
                    }
                }
            }
            return null;
        }
    }
}