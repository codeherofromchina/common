package com.erui.order.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 * Created by shigs on 2018/4/8.
 */
public class OrderProgressEvent extends ApplicationEvent {

    private Integer type;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OrderProgressEvent(Object source, Integer type) {
        super(source);
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
