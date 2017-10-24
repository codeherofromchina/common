package com.erui.report.model;

/**
 * Created by lirb on 2017/10/23.
 * 品类明细包装类
 */
public class CateDetailVo {
    private String category;
    private Integer inqCateCount;
    private Double inqCatePrice;
    private Double ordCatePrice;
    private Integer ordCateCount;

    public CateDetailVo() {
    }

    public CateDetailVo(String category,Integer inqCateCount,Double inqCatePrice, Double ordCatePrice,Integer ordCateCount) {
        this.category = category;
        this.inqCateCount=inqCateCount;
        this.inqCatePrice=inqCatePrice;
        this.ordCateCount=ordCateCount;
        this.ordCatePrice=ordCatePrice;
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

    public Double getOrdCatePrice() {
        return ordCatePrice;
    }

    public void setOrdCatePrice(Double ordCatePrice) {
        this.ordCatePrice = ordCatePrice;
    }

    public Integer getOrdCateCount() {
        return ordCateCount;
    }

    public void setOrdCateCount(Integer ordCateCount) {
        this.ordCateCount = ordCateCount;
    }

    @Override
    public String toString() {
        return "CateDetailVo{" +
                "category='" + category + '\'' +
                ", inqCateCount=" + inqCateCount +
                ", inqCatePrice=" + inqCatePrice +
                ", ordCatePrice=" + ordCatePrice +
                ", ordCateCount=" + ordCateCount +
                '}';
    }
}
