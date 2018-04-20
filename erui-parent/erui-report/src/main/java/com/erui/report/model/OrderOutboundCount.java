package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderOutboundCount {
    private Integer id;

    private Date createAt;

    private String outboundNum;

    private String contractNum;

    private String destination;

    private String executeNum;

    private Integer packCount;

    private Date outboundDate;

    private BigDecimal amounts;

    private String remark;
    private String docType;
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

    public String getOutboundNum() {
        return outboundNum;
    }

    public void setOutboundNum(String outboundNum) {
        this.outboundNum = outboundNum == null ? null : outboundNum.trim();
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum == null ? null : contractNum.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public String getExecuteNum() {
        return executeNum;
    }

    public void setExecuteNum(String executeNum) {
        this.executeNum = executeNum == null ? null : executeNum.trim();
    }

    public Integer getPackCount() {
        return packCount;
    }

    public void setPackCount(Integer packCount) {
        this.packCount = packCount;
    }

    public Date getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(Date outboundDate) {
        this.outboundDate = outboundDate;
    }

    public BigDecimal getAmounts() {
        return amounts;
    }

    public void setAmounts(BigDecimal amounts) {
        this.amounts = amounts;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType ==null? null:docType.trim();
    }
}