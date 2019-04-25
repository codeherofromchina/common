package com.erui.report.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PerformanceIndicators {
    private Integer id;

    private Date startPrescription;

    private Date endPrescription;

    private BigDecimal quota;

    private String priceUnit;

    private Integer ptype;

    private Integer orgId;

    private String orgName;

    private String areaBn;

    private String areaName;

    private String countryBn;

    private String countryName;

    private Integer createUserId;

    private String createUserName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 前端新增时传入的国家地区参数，后台从此参数中解析出地区和国家
     */
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

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getCountryBn() {
        if (StringUtils.isBlank(countryBn)) {
            if (area_country !=null && area_country.size() == 2 && StringUtils.isNotBlank(area_country.get(1))) {
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