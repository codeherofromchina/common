package com.erui.order.v2.model;

public class OrderWithBLOBs extends Order {
    private String deliveryRequires;

    private String customerContext;

    public String getDeliveryRequires() {
        return deliveryRequires;
    }

    public void setDeliveryRequires(String deliveryRequires) {
        this.deliveryRequires = deliveryRequires;
    }

    public String getCustomerContext() {
        return customerContext;
    }

    public void setCustomerContext(String customerContext) {
        this.customerContext = customerContext;
    }
}