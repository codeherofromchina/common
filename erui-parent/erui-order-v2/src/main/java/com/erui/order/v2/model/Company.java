package com.erui.order.v2.model;

import java.util.Date;

public class Company {
    private Integer id;

    private String areaBn;

    private String name;

    private Date createTime;

    private String enName;

    private String tenant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaBn() {
        return areaBn;
    }

    public void setAreaBn(String areaBn) {
        this.areaBn = areaBn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}