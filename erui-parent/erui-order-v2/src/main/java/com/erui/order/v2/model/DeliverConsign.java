package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.Date;

public class DeliverConsign {
    private Integer id;

    private String deliverConsignNo;

    private Integer orderId;

    private Integer deptId;

    private String coId;

    private String execCoName;

    private Date writeDate;

    private Date bookingDate;

    private Integer status;

    private String country;

    private String region;

    private Integer createUserId;

    private Date createTime;

    private Integer deliverYn;

    private BigDecimal lineOfCredit;

    private BigDecimal creditAvailable;

    private BigDecimal advanceMoney;

    private BigDecimal thisShipmentsMoney;

    private String invoiceRise;

    private String businessNature;

    private String businessSketch;

    private BigDecimal declareCustomsMoney;

    private BigDecimal tradeMoney;

    private BigDecimal directTransferMoney;

    private BigDecimal indirectTransferMoney;

    private BigDecimal clearCustomsMoney;

    private Integer payMethod;

    private String shippingBatch;

    private String moreBatchExplain;

    private Integer isDangerous;

    private String goodsDepositPlace;

    private Integer hasInsurance;

    private Integer countryLeaderId;

    private String countryLeader;

    private Integer settlementLeaderId;

    private String settlementLeader;

    private Integer logisticsLeaderId;

    private String logisticsLeader;

    private Integer businessLeaderId;

    private String businessLeader;

    private Integer auditingStatus;

    private String auditingProcess;

    private String crmCodeOrName;

    private String audiRemark;

    private String auditingUser;

    private String createUserName;

    private String auditingUserId;

    private String deptName;

    private String contractNo;

    private Integer bookingOfficerId;

    private String bookingOfficer;

    private Integer operationSpecialistId;

    private String operationSpecialist;

    private String tenant;

    private String processId;

    private String taskId;

    private String remarks;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
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

    public Integer getDeliverYn() {
        return deliverYn;
    }

    public void setDeliverYn(Integer deliverYn) {
        this.deliverYn = deliverYn;
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

    public String getInvoiceRise() {
        return invoiceRise;
    }

    public void setInvoiceRise(String invoiceRise) {
        this.invoiceRise = invoiceRise;
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature;
    }

    public String getBusinessSketch() {
        return businessSketch;
    }

    public void setBusinessSketch(String businessSketch) {
        this.businessSketch = businessSketch;
    }

    public BigDecimal getDeclareCustomsMoney() {
        return declareCustomsMoney;
    }

    public void setDeclareCustomsMoney(BigDecimal declareCustomsMoney) {
        this.declareCustomsMoney = declareCustomsMoney;
    }

    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public BigDecimal getDirectTransferMoney() {
        return directTransferMoney;
    }

    public void setDirectTransferMoney(BigDecimal directTransferMoney) {
        this.directTransferMoney = directTransferMoney;
    }

    public BigDecimal getIndirectTransferMoney() {
        return indirectTransferMoney;
    }

    public void setIndirectTransferMoney(BigDecimal indirectTransferMoney) {
        this.indirectTransferMoney = indirectTransferMoney;
    }

    public BigDecimal getClearCustomsMoney() {
        return clearCustomsMoney;
    }

    public void setClearCustomsMoney(BigDecimal clearCustomsMoney) {
        this.clearCustomsMoney = clearCustomsMoney;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getShippingBatch() {
        return shippingBatch;
    }

    public void setShippingBatch(String shippingBatch) {
        this.shippingBatch = shippingBatch;
    }

    public String getMoreBatchExplain() {
        return moreBatchExplain;
    }

    public void setMoreBatchExplain(String moreBatchExplain) {
        this.moreBatchExplain = moreBatchExplain;
    }

    public Integer getIsDangerous() {
        return isDangerous;
    }

    public void setIsDangerous(Integer isDangerous) {
        this.isDangerous = isDangerous;
    }

    public String getGoodsDepositPlace() {
        return goodsDepositPlace;
    }

    public void setGoodsDepositPlace(String goodsDepositPlace) {
        this.goodsDepositPlace = goodsDepositPlace;
    }

    public Integer getHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(Integer hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public Integer getCountryLeaderId() {
        return countryLeaderId;
    }

    public void setCountryLeaderId(Integer countryLeaderId) {
        this.countryLeaderId = countryLeaderId;
    }

    public String getCountryLeader() {
        return countryLeader;
    }

    public void setCountryLeader(String countryLeader) {
        this.countryLeader = countryLeader;
    }

    public Integer getSettlementLeaderId() {
        return settlementLeaderId;
    }

    public void setSettlementLeaderId(Integer settlementLeaderId) {
        this.settlementLeaderId = settlementLeaderId;
    }

    public String getSettlementLeader() {
        return settlementLeader;
    }

    public void setSettlementLeader(String settlementLeader) {
        this.settlementLeader = settlementLeader;
    }

    public Integer getLogisticsLeaderId() {
        return logisticsLeaderId;
    }

    public void setLogisticsLeaderId(Integer logisticsLeaderId) {
        this.logisticsLeaderId = logisticsLeaderId;
    }

    public String getLogisticsLeader() {
        return logisticsLeader;
    }

    public void setLogisticsLeader(String logisticsLeader) {
        this.logisticsLeader = logisticsLeader;
    }

    public Integer getBusinessLeaderId() {
        return businessLeaderId;
    }

    public void setBusinessLeaderId(Integer businessLeaderId) {
        this.businessLeaderId = businessLeaderId;
    }

    public String getBusinessLeader() {
        return businessLeader;
    }

    public void setBusinessLeader(String businessLeader) {
        this.businessLeader = businessLeader;
    }

    public Integer getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(Integer auditingStatus) {
        this.auditingStatus = auditingStatus;
    }

    public String getAuditingProcess() {
        return auditingProcess;
    }

    public void setAuditingProcess(String auditingProcess) {
        this.auditingProcess = auditingProcess;
    }

    public String getCrmCodeOrName() {
        return crmCodeOrName;
    }

    public void setCrmCodeOrName(String crmCodeOrName) {
        this.crmCodeOrName = crmCodeOrName;
    }

    public String getAudiRemark() {
        return audiRemark;
    }

    public void setAudiRemark(String audiRemark) {
        this.audiRemark = audiRemark;
    }

    public String getAuditingUser() {
        return auditingUser;
    }

    public void setAuditingUser(String auditingUser) {
        this.auditingUser = auditingUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getBookingOfficerId() {
        return bookingOfficerId;
    }

    public void setBookingOfficerId(Integer bookingOfficerId) {
        this.bookingOfficerId = bookingOfficerId;
    }

    public String getBookingOfficer() {
        return bookingOfficer;
    }

    public void setBookingOfficer(String bookingOfficer) {
        this.bookingOfficer = bookingOfficer;
    }

    public Integer getOperationSpecialistId() {
        return operationSpecialistId;
    }

    public void setOperationSpecialistId(Integer operationSpecialistId) {
        this.operationSpecialistId = operationSpecialistId;
    }

    public String getOperationSpecialist() {
        return operationSpecialist;
    }

    public void setOperationSpecialist(String operationSpecialist) {
        this.operationSpecialist = operationSpecialist;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}