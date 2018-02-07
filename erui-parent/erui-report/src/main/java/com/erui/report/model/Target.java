package com.erui.report.model;

public class Target {
    private Long id;

    private String targetStatus;

    private String area;

    private String country;

    private String orgnization;

    private Integer targetMoth;

    private Long targetAmmount;

    private Integer targetCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(String targetStatus) {
        this.targetStatus = targetStatus == null ? null : targetStatus.trim();
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

    public String getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(String orgnization) {
        this.orgnization = orgnization == null ? null : orgnization.trim();
    }

    public Integer getTargetMoth() {
        return targetMoth;
    }

    public void setTargetMoth(Integer targetMoth) {
        this.targetMoth = targetMoth;
    }

    public Long getTargetAmmount() {
        return targetAmmount;
    }

    public void setTargetAmmount(Long targetAmmount) {
        this.targetAmmount = targetAmmount;
    }

    public Integer getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(Integer targetCount) {
        this.targetCount = targetCount;
    }
}