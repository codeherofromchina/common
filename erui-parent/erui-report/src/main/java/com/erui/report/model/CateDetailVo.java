package com.erui.report.model;

/**
 * Created by lirb on 2017/10/23.
 * 品类明细包装类
 */
public class CateDetailVo {
    private String category;
    private Integer inqCateCount;
    private Double inqProportion;
    private Double inqCatePrice;
    private Double inqAmountProportion;
    private Integer ordCateCount;
    private Double ordProportion;
    private Double ordCatePrice;
    private Double ordAmountProportion;

    public Double getInqProportion() {
        return inqProportion;
    }

    public void setInqProportion(Double inqProportion) {
        this.inqProportion = inqProportion;
    }

    public Double getInqAmountProportion() {
        return inqAmountProportion;
    }

    public void setInqAmountProportion(Double inqAmountProportion) {
        this.inqAmountProportion = inqAmountProportion;
    }

    public Double getOrdProportion() {
        return ordProportion;
    }

    public void setOrdProportion(Double ordProportion) {
        this.ordProportion = ordProportion;
    }

    public Double getOrdAmountProportion() {
        return ordAmountProportion;
    }

    public void setOrdAmountProportion(Double ordAmountProportion) {
        this.ordAmountProportion = ordAmountProportion;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getInqCateCount() {
        return inqCateCount;
    }

    public void setInqCateCount(Integer inqCateCount) {
        this.inqCateCount = inqCateCount;
    }

    public Double getInqCatePrice() {
        return inqCatePrice;
    }

    public void setInqCatePrice(Double inqCatePrice) {
        this.inqCatePrice = inqCatePrice;
    }

    public Integer getOrdCateCount() {
        return ordCateCount;
    }

    public void setOrdCateCount(Integer ordCateCount) {
        this.ordCateCount = ordCateCount;
    }

    public Double getOrdCatePrice() {
        return ordCatePrice;
    }

    public void setOrdCatePrice(Double ordCatePrice) {
        this.ordCatePrice = ordCatePrice;
    }
}
