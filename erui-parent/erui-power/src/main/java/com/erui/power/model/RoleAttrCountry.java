package com.erui.power.model;

import java.util.Date;

public class RoleAttrCountry {
    private Long id;

    private Long roleId;

    private Long employeeId;

    private String countryBn;

    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getCountryBn() {
        return countryBn;
    }

    public void setCountryBn(String countryBn) {
        this.countryBn = countryBn;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}