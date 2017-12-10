package com.erui.report.model;

import java.util.Date;

public class Member {
    private Long id;

    private Date inputDate;

    private Integer generalMemberCount;

    private Integer generalMemberRebuy;

    private Integer seniorMemberCount;

    private Integer seniorMemberRebuy;

    private Integer goldMemberCount;

    private Integer goldMemberRebuy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Integer getGeneralMemberCount() {
        return generalMemberCount;
    }

    public void setGeneralMemberCount(Integer generalMemberCount) {
        this.generalMemberCount = generalMemberCount;
    }

    public Integer getGeneralMemberRebuy() {
        return generalMemberRebuy;
    }

    public void setGeneralMemberRebuy(Integer generalMemberRebuy) {
        this.generalMemberRebuy = generalMemberRebuy;
    }

    public Integer getSeniorMemberCount() {
        return seniorMemberCount;
    }

    public void setSeniorMemberCount(Integer seniorMemberCount) {
        this.seniorMemberCount = seniorMemberCount;
    }

    public Integer getSeniorMemberRebuy() {
        return seniorMemberRebuy;
    }

    public void setSeniorMemberRebuy(Integer seniorMemberRebuy) {
        this.seniorMemberRebuy = seniorMemberRebuy;
    }

    public Integer getGoldMemberCount() {
        return goldMemberCount;
    }

    public void setGoldMemberCount(Integer goldMemberCount) {
        this.goldMemberCount = goldMemberCount;
    }

    public Integer getGoldMemberRebuy() {
        return goldMemberRebuy;
    }

    public void setGoldMemberRebuy(Integer goldMemberRebuy) {
        this.goldMemberRebuy = goldMemberRebuy;
    }
}