package com.erui.order.v2.model;

public class GoodsWithBLOBs extends Goods {
    private String model;

    private String clientDesc;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc;
    }
}