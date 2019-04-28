package com.erui.order.v2.model;

import java.math.BigDecimal;

public class ProectTemp {
    private Integer id;

    private Integer projectId;

    private BigDecimal preProfit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getPreProfit() {
        return preProfit;
    }

    public void setPreProfit(BigDecimal preProfit) {
        this.preProfit = preProfit;
    }
}