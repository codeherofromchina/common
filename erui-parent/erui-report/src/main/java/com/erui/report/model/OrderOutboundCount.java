package com.erui.report.model;

import java.util.Date;

public class OrderOutboundCount {
    private Integer id;

    private Date createAt;

    private String outboundNum;

    private String dispatchNum;

    private String contractNum;

    private String executeNum;

    private Integer packCount;

    private String destination;

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

    public String getDispatchNum() {
        return dispatchNum;
    }

    public void setDispatchNum(String dispatchNum) {
        this.dispatchNum = dispatchNum == null ? null : dispatchNum.trim();
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

    public Integer getPackCount() {
        return packCount;
    }

    public void setPackCount(Integer packCount) {
        this.packCount = packCount;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }
}