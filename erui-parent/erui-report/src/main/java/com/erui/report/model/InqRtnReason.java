package com.erui.report.model;

public class InqRtnReason {
    private Long id;

    private String inquiryNum;

    private String teturnSeason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInquiryNum() {
        return inquiryNum;
    }

    public void setInquiryNum(String inquiryNum) {
        this.inquiryNum = inquiryNum == null ? null : inquiryNum.trim();
    }

    public String getTeturnSeason() {
        return teturnSeason;
    }

    public void setTeturnSeason(String teturnSeason) {
        this.teturnSeason = teturnSeason == null ? null : teturnSeason.trim();
    }
}