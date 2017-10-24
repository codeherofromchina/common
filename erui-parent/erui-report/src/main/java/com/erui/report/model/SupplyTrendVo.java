package com.erui.report.model;

/**
 * Created by lirb on 2017/10/24.
 */
public class SupplyTrendVo {


     private String[] datetime;
     private Integer[] suppliyFinishCount=null;
     private Integer[]  SPUFinishCount=null;
     private Integer[]  SKUFinishCount=null;

    public SupplyTrendVo(String[] datetime, Integer[] suppliyFinishCount, Integer[] SPUFinishCount, Integer[] SKUFinishCount) {
        this.datetime = datetime;
        this.suppliyFinishCount = suppliyFinishCount;
        this.SPUFinishCount = SPUFinishCount;
        this.SKUFinishCount = SKUFinishCount;
    }

    public String[] getDatetime() {
        return datetime;
    }

    public void setDatetime(String[] datetime) {
        this.datetime = datetime;
    }

    public Integer[] getSuppliyFinishCount() {
        return suppliyFinishCount;
    }

    public void setSuppliyFinishCount(Integer[] suppliyFinishCount) {
        this.suppliyFinishCount = suppliyFinishCount;
    }

    public Integer[] getSPUFinishCount() {
        return SPUFinishCount;
    }

    public void setSPUFinishCount(Integer[] SPUFinishCount) {
        this.SPUFinishCount = SPUFinishCount;
    }

    public Integer[] getSKUFinishCount() {
        return SKUFinishCount;
    }

    public void setSKUFinishCount(Integer[] SKUFinishCount) {
        this.SKUFinishCount = SKUFinishCount;
    }
}
