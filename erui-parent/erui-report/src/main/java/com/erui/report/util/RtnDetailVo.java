package com.erui.report.util;

public class RtnDetailVo {
    private  String area;
    private  String  org;
    private Integer[] rtnCounts;
    private Double[] reasonProportions;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Integer[] getRtnCounts() {
        return rtnCounts;
    }

    public void setRtnCounts(Integer[] rtnCounts) {
        this.rtnCounts = rtnCounts;
    }

    public Double[] getReasonProportions() {
        return reasonProportions;
    }

    public void setReasonProportions(Double[] reasonProportions) {
        this.reasonProportions = reasonProportions;
    }
}
