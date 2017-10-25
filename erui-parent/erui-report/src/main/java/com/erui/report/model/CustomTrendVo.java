package com.erui.report.model;

/**
 * Created by lirb on 2017/10/25.
 */
public class CustomTrendVo {
    private  String[] datetime=null;
    private Integer[] inqCount=null;
    private Integer[] ordCount=null;

    public String[] getDatetime() {
        return datetime;
    }

    public void setDatetime(String[] datetime) {
        this.datetime = datetime;
    }

    public Integer[] getInqCount() {
        return inqCount;
    }

    public void setInqCount(Integer[] inqCount) {
        this.inqCount = inqCount;
    }

    public Integer[] getOrdCount() {
        return ordCount;
    }

    public void setOrdCount(Integer[] ordCount) {
        this.ordCount = ordCount;
    }
}
