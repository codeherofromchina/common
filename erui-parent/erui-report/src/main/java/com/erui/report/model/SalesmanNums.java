package com.erui.report.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

public class SalesmanNums {
    private Integer id;

    private Date startPrescription;

    private Date endPrescription;

    private String countryBn;

    private String countryName;

    private String areaBn;

    private String areaName;

    private Integer num;

    private Integer createUserId;

    private String createUserName;

    private Date createTime;

    private List<String> area_country;

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
        if (StringUtils.isBlank(countryBn)) {
            if (area_country != null && area_country.size() == 2 && StringUtils.isNotBlank(area_country.get(1))) {
                return area_country.get(1);
            }
        }
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

    public String getAreaBn() {
        return areaBn;
    }

    public void setAreaBn(String areaBn) {
        this.areaBn = areaBn;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public List<String> getArea_country() {
        return area_country;
    }

    public void setArea_country(List<String> area_country) {
        this.area_country = area_country;
    }
}