package com.erui.order.v2.model;

public class LogisticsDataAttach {
    private Integer id;

    private Integer logisticsDataId;

    private Integer attachId;

    private String tenant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogisticsDataId() {
        return logisticsDataId;
    }

    public void setLogisticsDataId(Integer logisticsDataId) {
        this.logisticsDataId = logisticsDataId;
    }

    public Integer getAttachId() {
        return attachId;
    }

    public void setAttachId(Integer attachId) {
        this.attachId = attachId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}