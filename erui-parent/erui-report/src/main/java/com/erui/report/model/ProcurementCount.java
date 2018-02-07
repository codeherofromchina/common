package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProcurementCount {
    private Integer id;

    private Date assignTime;

    private Date executeTime;

    private String planNum;

    private String executeNum;

    private BigDecimal ammount;

    private String category;

    private String area;

    private String country;

    private String oil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum == null ? null : planNum.trim();
    }

    public String getExecuteNum() {
        return executeNum;
    }

    public void setExecuteNum(String executeNum) {
        this.executeNum = executeNum == null ? null : executeNum.trim();
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil == null ? null : oil.trim();
    }
}