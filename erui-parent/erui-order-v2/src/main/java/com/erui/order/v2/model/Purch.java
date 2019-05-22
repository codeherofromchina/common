package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.Date;

public class Purch {
    private Integer id;

    private String purchNo;

    private Integer purchContractId;

    private Integer agentId;

    private String agentName;

    private String department;

    private Date signingDate;

    private Date arrivalDate;

    private Date purChgDate;

    private Integer supplierId;

    private String supplierName;

    private BigDecimal totalPrice;

    private String currencyBn;

    private Integer payType;

    private String otherPayTypeMsg;

    private Date productedDate;

    private Date payFactoryDate;

    private Date payDepositDate;

    private Date payDepositExpired;

    private String invoiceNo;

    private Date accountDate;

    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private String createUserName;

    private Date updateTime;

    private Boolean deleteFlag;

    private Date deleteTime;

    private Boolean inspected;

    private Integer auditingStatus;

    private String auditingProcess;

    private String auditingUserId;

    private String auditingUser;

    private Integer purchAuditerId;

    private String purchAuditer;

    private Integer businessAuditerId;

    private String businessAuditer;

    private Integer legalAuditerId;

    private String legalAuditer;

    private Integer financeAuditerId;

    private String financeAuditer;

    private Integer buVpAuditerId;

    private String buVpAuditer;

    private Integer chairmanId;

    private String chairman;

    private String audiRemark;

    private String supplyArea;

    private String contractVersion;

    private BigDecimal goalCost;

    private BigDecimal saveAmount;

    private String saveMode;

    private String priceMode;

    private BigDecimal profitPercent;

    private String contractTag;

    private Integer taxBearing;

    private BigDecimal exchangeRate;

    private Integer chairmanBoardId;

    private String chairmanBoard;

    private String tenant;

    private Integer qualityInspectStatus;

    private Integer qualityLeaderId;

    private String qualityLeaderName;

    private Date qualityTime;

    private String processId;

    private String taskId;

    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo;
    }

    public Integer getPurchContractId() {
        return purchContractId;
    }

    public void setPurchContractId(Integer purchContractId) {
        this.purchContractId = purchContractId;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getPurChgDate() {
        return purChgDate;
    }

    public void setPurChgDate(Date purChgDate) {
        this.purChgDate = purChgDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOtherPayTypeMsg() {
        return otherPayTypeMsg;
    }

    public void setOtherPayTypeMsg(String otherPayTypeMsg) {
        this.otherPayTypeMsg = otherPayTypeMsg;
    }

    public Date getProductedDate() {
        return productedDate;
    }

    public void setProductedDate(Date productedDate) {
        this.productedDate = productedDate;
    }

    public Date getPayFactoryDate() {
        return payFactoryDate;
    }

    public void setPayFactoryDate(Date payFactoryDate) {
        this.payFactoryDate = payFactoryDate;
    }

    public Date getPayDepositDate() {
        return payDepositDate;
    }

    public void setPayDepositDate(Date payDepositDate) {
        this.payDepositDate = payDepositDate;
    }

    public Date getPayDepositExpired() {
        return payDepositExpired;
    }

    public void setPayDepositExpired(Date payDepositExpired) {
        this.payDepositExpired = payDepositExpired;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
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

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    public Boolean getInspected() {
        return inspected;
    }

    public void setInspected(Boolean inspected) {
        this.inspected = inspected;
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

    public String getAuditingUser() {
        return auditingUser;
    }

    public void setAuditingUser(String auditingUser) {
        this.auditingUser = auditingUser;
    }

    public Integer getPurchAuditerId() {
        return purchAuditerId;
    }

    public void setPurchAuditerId(Integer purchAuditerId) {
        this.purchAuditerId = purchAuditerId;
    }

    public String getPurchAuditer() {
        return purchAuditer;
    }

    public void setPurchAuditer(String purchAuditer) {
        this.purchAuditer = purchAuditer;
    }

    public Integer getBusinessAuditerId() {
        return businessAuditerId;
    }

    public void setBusinessAuditerId(Integer businessAuditerId) {
        this.businessAuditerId = businessAuditerId;
    }

    public String getBusinessAuditer() {
        return businessAuditer;
    }

    public void setBusinessAuditer(String businessAuditer) {
        this.businessAuditer = businessAuditer;
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

    public Integer getFinanceAuditerId() {
        return financeAuditerId;
    }

    public void setFinanceAuditerId(Integer financeAuditerId) {
        this.financeAuditerId = financeAuditerId;
    }

    public String getFinanceAuditer() {
        return financeAuditer;
    }

    public void setFinanceAuditer(String financeAuditer) {
        this.financeAuditer = financeAuditer;
    }

    public Integer getBuVpAuditerId() {
        return buVpAuditerId;
    }

    public void setBuVpAuditerId(Integer buVpAuditerId) {
        this.buVpAuditerId = buVpAuditerId;
    }

    public String getBuVpAuditer() {
        return buVpAuditer;
    }

    public void setBuVpAuditer(String buVpAuditer) {
        this.buVpAuditer = buVpAuditer;
    }

    public Integer getChairmanId() {
        return chairmanId;
    }

    public void setChairmanId(Integer chairmanId) {
        this.chairmanId = chairmanId;
    }

    public String getChairman() {
        return chairman;
    }

    public void setChairman(String chairman) {
        this.chairman = chairman;
    }

    public String getAudiRemark() {
        return audiRemark;
    }

    public void setAudiRemark(String audiRemark) {
        this.audiRemark = audiRemark;
    }

    public String getSupplyArea() {
        return supplyArea;
    }

    public void setSupplyArea(String supplyArea) {
        this.supplyArea = supplyArea;
    }

    public String getContractVersion() {
        return contractVersion;
    }

    public void setContractVersion(String contractVersion) {
        this.contractVersion = contractVersion;
    }

    public BigDecimal getGoalCost() {
        return goalCost;
    }

    public void setGoalCost(BigDecimal goalCost) {
        this.goalCost = goalCost;
    }

    public BigDecimal getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(BigDecimal saveAmount) {
        this.saveAmount = saveAmount;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getPriceMode() {
        return priceMode;
    }

    public void setPriceMode(String priceMode) {
        this.priceMode = priceMode;
    }

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(BigDecimal profitPercent) {
        this.profitPercent = profitPercent;
    }

    public String getContractTag() {
        return contractTag;
    }

    public void setContractTag(String contractTag) {
        this.contractTag = contractTag;
    }

    public Integer getTaxBearing() {
        return taxBearing;
    }

    public void setTaxBearing(Integer taxBearing) {
        this.taxBearing = taxBearing;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getChairmanBoardId() {
        return chairmanBoardId;
    }

    public void setChairmanBoardId(Integer chairmanBoardId) {
        this.chairmanBoardId = chairmanBoardId;
    }

    public String getChairmanBoard() {
        return chairmanBoard;
    }

    public void setChairmanBoard(String chairmanBoard) {
        this.chairmanBoard = chairmanBoard;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Integer getQualityInspectStatus() {
        return qualityInspectStatus;
    }

    public void setQualityInspectStatus(Integer qualityInspectStatus) {
        this.qualityInspectStatus = qualityInspectStatus;
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

    public Date getQualityTime() {
        return qualityTime;
    }

    public void setQualityTime(Date qualityTime) {
        this.qualityTime = qualityTime;
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

    /**
     * 采购状态枚举
     */
    public static enum StatusEnum {
        READY(1, "未进行/保存"), BEING(2, "进行中/提交"), DONE(3, "已完成");

        private int code;
        private String msg;

        private StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        /**
         * 通过code码获取采购状态信息
         *
         * @param code
         * @return
         */
        public static StatusEnum fromCode(Integer code) {
            if (code != null) {
                int code02 = code; // 拆箱一次
                for (StatusEnum s : StatusEnum.values()) {
                    if (code02 == s.code) {
                        return s;
                    }
                }
            }
            return null;
        }

    }
}