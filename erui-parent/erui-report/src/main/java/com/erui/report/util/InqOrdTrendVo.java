package com.erui.report.util;

import java.util.List;

/**
 * Created by lirb on 2017/11/15.
 */
public class InqOrdTrendVo {
    private List<String> date;
    private List<Integer> inqCounts;
    private List<Integer> ordCounts;

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<Integer> getInqCounts() {
        return inqCounts;
    }

    public void setInqCounts(List<Integer> inqCounts) {
        this.inqCounts = inqCounts;
    }

    public List<Integer> getOrdCounts() {
        return ordCounts;
    }

    public void setOrdCounts(List<Integer> ordCounts) {
        this.ordCounts = ordCounts;
    }
}
