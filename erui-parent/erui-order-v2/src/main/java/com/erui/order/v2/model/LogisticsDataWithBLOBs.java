package com.erui.order.v2.model;

public class LogisticsDataWithBLOBs extends LogisticsData {
    private String logs;

    private String remarks;

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}