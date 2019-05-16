package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Integer id;

    private String contractNo;

    private String frameworkNo;

    private String contractNoOs;

    private String poNo;

    private String logiQuoteNo;

    private String inquiryNo;

    private Integer orderType;

    private Integer orderSource;

    private Date signingDate;

    private String deliveryDate;

    private String signingCo;

    private Integer agentId;

    private String agentName;

    private Integer execCoId;

    private String execCoName;

    private String region;

    private String distributionDeptName;

    private String country;

    private String crmCode;

    private Integer customerType;

    private String perLiableRepay;

    private Integer businessUnitId;

    private String businessUnitName;

    private Integer technicalId;

    private String grantType;

    private Integer preinvest;

    private Integer financing;

    private String tradeTerms;

    private String transportType;

    private String fromCountry;

    private String fromPort;

    private String fromPlace;

    private String toCountry;

    private String toPort;

    private String toPlace;

    private BigDecimal totalPrice;

    private Integer distributionDeptId;

    private String technicalIdDept;

    private String currencyBn;

    private Integer taxBearing;

    private String paymentModeBn;

    private BigDecimal qualityFunds;

    private Integer payStatus;

    private Integer status;

    private Date createTime;

    private Boolean deliverConsignC;

    private Integer createUserId;

    private Date updateTime;

    private Boolean deleteFlag;

    private Date deleteTime;

    private BigDecimal receivableAccountRemaining;

    private Integer acquireId;

    private Date exeChgDate;

    private String createUserName;

    private Integer buyerId;

    private String inquiryId;

    private String businessName;

    private String projectNo;

    private BigDecimal totalPriceUsd;

    private BigDecimal exchangeRate;

    private String processProgress;

    private Integer deliverConsignHas;

    private Integer orderBelongs;

    private Integer orderCategory;

    private Integer overseasSales;

    private BigDecimal shipmentsMoney;

    private BigDecimal alreadyGatheringMoney;

    private Integer purchReqCreate;

    private Integer inspectN;

    private BigDecimal advanceMoney;

    private Integer auditingStatus;

    private String auditingProcess;

    private String auditingUserId;

    private Integer countryLeaderId;

    private String countryLeader;

    private Integer areaLeaderId;

    private String areaLeader;

    private Integer areaVpId;

    private String areaVp;

    private Integer perLiableRepayId;

    private Integer financingCommissionerId;

    private String audiRemark;

    private String cancelReason;

    private Integer settlementLeaderId;

    private String settlementLeader;

    private Integer legalAuditerId;

    private String legalAuditer;

    private String financingCommissioner;

    private String processId;

    private String taskId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getFrameworkNo() {
        return frameworkNo;
    }

    public void setFrameworkNo(String frameworkNo) {
        this.frameworkNo = frameworkNo;
    }

    public String getContractNoOs() {
        return contractNoOs;
    }

    public void setContractNoOs(String contractNoOs) {
        this.contractNoOs = contractNoOs;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getLogiQuoteNo() {
        return logiQuoteNo;
    }

    public void setLogiQuoteNo(String logiQuoteNo) {
        this.logiQuoteNo = logiQuoteNo;
    }

    public String getInquiryNo() {
        return inquiryNo;
    }

    public void setInquiryNo(String inquiryNo) {
        this.inquiryNo = inquiryNo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getSigningCo() {
        return signingCo;
    }

    public void setSigningCo(String signingCo) {
        this.signingCo = signingCo;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getExecCoId() {
        return execCoId;
    }

    public void setExecCoId(Integer execCoId) {
        this.execCoId = execCoId;
    }

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistributionDeptName() {
        return distributionDeptName;
    }

    public void setDistributionDeptName(String distributionDeptName) {
        this.distributionDeptName = distributionDeptName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCrmCode() {
        return crmCode;
    }

    public void setCrmCode(String crmCode) {
        this.crmCode = crmCode;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getPerLiableRepay() {
        return perLiableRepay;
    }

    public void setPerLiableRepay(String perLiableRepay) {
        this.perLiableRepay = perLiableRepay;
    }

    public Integer getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(Integer businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public Integer getTechnicalId() {
        return technicalId;
    }

    public void setTechnicalId(Integer technicalId) {
        this.technicalId = technicalId;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public Integer getPreinvest() {
        return preinvest;
    }

    public void setPreinvest(Integer preinvest) {
        this.preinvest = preinvest;
    }

    public Integer getFinancing() {
        return financing;
    }

    public void setFinancing(Integer financing) {
        this.financing = financing;
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getFromPort() {
        return fromPort;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getToPort() {
        return toPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDistributionDeptId() {
        return distributionDeptId;
    }

    public void setDistributionDeptId(Integer distributionDeptId) {
        this.distributionDeptId = distributionDeptId;
    }

    public String getTechnicalIdDept() {
        return technicalIdDept;
    }

    public void setTechnicalIdDept(String technicalIdDept) {
        this.technicalIdDept = technicalIdDept;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public Integer getTaxBearing() {
        return taxBearing;
    }

    public void setTaxBearing(Integer taxBearing) {
        this.taxBearing = taxBearing;
    }

    public String getPaymentModeBn() {
        return paymentModeBn;
    }

    public void setPaymentModeBn(String paymentModeBn) {
        this.paymentModeBn = paymentModeBn;
    }

    public BigDecimal getQualityFunds() {
        return qualityFunds;
    }

    public void setQualityFunds(BigDecimal qualityFunds) {
        this.qualityFunds = qualityFunds;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeliverConsignC() {
        return deliverConsignC;
    }

    public void setDeliverConsignC(Boolean deliverConsignC) {
        this.deliverConsignC = deliverConsignC;
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

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public BigDecimal getReceivableAccountRemaining() {
        return receivableAccountRemaining;
    }

    public void setReceivableAccountRemaining(BigDecimal receivableAccountRemaining) {
        this.receivableAccountRemaining = receivableAccountRemaining;
    }

    public Integer getAcquireId() {
        return acquireId;
    }

    public void setAcquireId(Integer acquireId) {
        this.acquireId = acquireId;
    }

    public Date getExeChgDate() {
        return exeChgDate;
    }

    public void setExeChgDate(Date exeChgDate) {
        this.exeChgDate = exeChgDate;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public BigDecimal getTotalPriceUsd() {
        return totalPriceUsd;
    }

    public void setTotalPriceUsd(BigDecimal totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getProcessProgress() {
        return processProgress;
    }

    public void setProcessProgress(String processProgress) {
        this.processProgress = processProgress;
    }

    public Integer getDeliverConsignHas() {
        return deliverConsignHas;
    }

    public void setDeliverConsignHas(Integer deliverConsignHas) {
        this.deliverConsignHas = deliverConsignHas;
    }

    public Integer getOrderBelongs() {
        return orderBelongs;
    }

    public void setOrderBelongs(Integer orderBelongs) {
        this.orderBelongs = orderBelongs;
    }

    public Integer getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(Integer orderCategory) {
        this.orderCategory = orderCategory;
    }

    public Integer getOverseasSales() {
        return overseasSales;
    }

    public void setOverseasSales(Integer overseasSales) {
        this.overseasSales = overseasSales;
    }

    public BigDecimal getShipmentsMoney() {
        return shipmentsMoney;
    }

    public void setShipmentsMoney(BigDecimal shipmentsMoney) {
        this.shipmentsMoney = shipmentsMoney;
    }

    public BigDecimal getAlreadyGatheringMoney() {
        return alreadyGatheringMoney;
    }

    public void setAlreadyGatheringMoney(BigDecimal alreadyGatheringMoney) {
        this.alreadyGatheringMoney = alreadyGatheringMoney;
    }

    public Integer getPurchReqCreate() {
        return purchReqCreate;
    }

    public void setPurchReqCreate(Integer purchReqCreate) {
        this.purchReqCreate = purchReqCreate;
    }

    public Integer getInspectN() {
        return inspectN;
    }

    public void setInspectN(Integer inspectN) {
        this.inspectN = inspectN;
    }

    public BigDecimal getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(BigDecimal advanceMoney) {
        this.advanceMoney = advanceMoney;
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

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId;
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

    public Integer getAreaLeaderId() {
        return areaLeaderId;
    }

    public void setAreaLeaderId(Integer areaLeaderId) {
        this.areaLeaderId = areaLeaderId;
    }

    public String getAreaLeader() {
        return areaLeader;
    }

    public void setAreaLeader(String areaLeader) {
        this.areaLeader = areaLeader;
    }

    public Integer getAreaVpId() {
        return areaVpId;
    }

    public void setAreaVpId(Integer areaVpId) {
        this.areaVpId = areaVpId;
    }

    public String getAreaVp() {
        return areaVp;
    }

    public void setAreaVp(String areaVp) {
        this.areaVp = areaVp;
    }

    public Integer getPerLiableRepayId() {
        return perLiableRepayId;
    }

    public void setPerLiableRepayId(Integer perLiableRepayId) {
        this.perLiableRepayId = perLiableRepayId;
    }

    public Integer getFinancingCommissionerId() {
        return financingCommissionerId;
    }

    public void setFinancingCommissionerId(Integer financingCommissionerId) {
        this.financingCommissionerId = financingCommissionerId;
    }

    public String getAudiRemark() {
        return audiRemark;
    }

    public void setAudiRemark(String audiRemark) {
        this.audiRemark = audiRemark;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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

    public Integer getLegalAuditerId() {
        return legalAuditerId;
    }

    public void setLegalAuditerId(Integer legalAuditerId) {
        this.legalAuditerId = legalAuditerId;
    }

    public String getLegalAuditer() {
        return legalAuditer;
    }

    public void setLegalAuditer(String legalAuditer) {
        this.legalAuditer = legalAuditer;
    }

    public String getFinancingCommissioner() {
        return financingCommissioner;
    }

    public void setFinancingCommissioner(String financingCommissioner) {
        this.financingCommissioner = financingCommissioner;
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
}