package com.erui.order.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 * Created by wangxiaodan on 2018/4/2.
 */
public class MyEvent extends ApplicationEvent {

    private Integer type;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MyEvent(Object source,Integer type) {
        super(source);
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
