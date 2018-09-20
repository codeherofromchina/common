package com.erui.report.model;

import java.util.Date;

public class SalesmanNums {
    private Integer id;

    private Date startPrescription;

    private Date endPrescription;

    private String countryBn;

    private String countryName;

    private Integer num;

    private Integer createUserId;

    private String createUserName;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartPrescription() {
        return startPrescription;
    }

    public void setStartPrescription(Date startPrescription) {
        this.startPrescription = startPrescription;
    }

    public Date getEndPrescription() {
        return endPrescription;
    }

    public void setEndPrescription(Date endPrescription) {
        this.endPrescription = endPrescription;
    }

    public String getCountryBn() {
        return countryBn;
    }

    public void setCountryBn(String countryBn) {
        this.countryBn = countryBn;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}