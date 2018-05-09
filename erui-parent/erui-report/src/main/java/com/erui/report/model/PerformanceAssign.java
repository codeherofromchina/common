package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售业绩分配 实体
 */
public class PerformanceAssign {

    private Long id;

    private Date startTime;

    private String workNum;

    private String nameCh;

    private String salesmanType;

    private String twoLevelDepartment;

    private String threeLevelDepartment;

    private String organization;

    private String station;

    private BigDecimal countryPerformance;

    private BigDecimal assignRate;

    private BigDecimal salesmanPerformance;

    private Integer assignStatus;

    private String rejectReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum == null ? null : workNum.trim();
    }

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh == null ? null : nameCh.trim();
    }

    public String getSalesmanType() {
        return salesmanType;
    }

    public void setSalesmanType(String salesmanType) {
        this.salesmanType = salesmanType == null ? null : salesmanType.trim();
    }

    public String getTwoLevelDepartment() {
        return twoLevelDepartment;
    }

    public void setTwoLevelDepartment(String twoLevelDepartment) {
        this.twoLevelDepartment = twoLevelDepartment == null ? null : twoLevelDepartment.trim();
    }

    public String getThreeLevelDepartment() {
        return threeLevelDepartment;
    }

    public void setThreeLevelDepartment(String threeLevelDepartment) {
        this.threeLevelDepartment = threeLevelDepartment == null ? null : threeLevelDepartment.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station == null ? null : station.trim();
    }

    public BigDecimal getCountryPerformance() {
        return countryPerformance;
    }

    public void setCountryPerformance(BigDecimal countryPerformance) {
        this.countryPerformance = countryPerformance;
    }

    public BigDecimal getAssignRate() {
        return assignRate;
    }

    public void setAssignRate(BigDecimal assignRate) {
        this.assignRate = assignRate;
    }

    public BigDecimal getSalesmanPerformance() {
        return salesmanPerformance;
    }

    public void setSalesmanPerformance(BigDecimal salesmanPerformance) {
        this.salesmanPerformance = salesmanPerformance;
    }

    public Integer getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(Integer assignStatus) {
        this.assignStatus = assignStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }
}