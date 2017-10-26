package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderEntryCount {
    private Integer id;

    private Date createAt;

    private String entryNum;

    private String contractNum;

    private String executeNum;

    private String suppliName;

    private String buyer;

    private Integer entryCount;

    private BigDecimal amounts;

    private Date storageDate;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getEntryNum() {
        return entryNum;
    }

    public void setEntryNum(String entryNum) {
        this.entryNum = entryNum == null ? null : entryNum.trim();
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum == null ? null : contractNum.trim();
    }

    public String getExecuteNum() {
        return executeNum;
    }

    public void setExecuteNum(String executeNum) {
        this.executeNum = executeNum == null ? null : executeNum.trim();
    }

    public String getSuppliName() {
        return suppliName;
    }

    public void setSuppliName(String suppliName) {
        this.suppliName = suppliName == null ? null : suppliName.trim();
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer == null ? null : buyer.trim();
    }

    public Integer getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(Integer entryCount) {
        this.entryCount = entryCount;
    }

    public BigDecimal getAmounts() {
        return amounts;
    }

    public void setAmounts(BigDecimal amounts) {
        this.amounts = amounts;
    }

    public Date getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(Date storageDate) {
        this.storageDate = storageDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}