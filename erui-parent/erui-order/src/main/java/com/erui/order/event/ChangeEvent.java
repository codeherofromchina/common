package com.erui.order.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 * Created by shigs on 2018/4/8.
 */
public class ChangeEvent extends ApplicationEvent {

    private Integer orderId;
    private final String token;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ChangeEvent(Object source, Integer orderId, String token) {
        super(source);
        this.token = token;
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getToken() {
        return token;
    }
}
