package com.erui.order.event;

import org.springframework.context.ApplicationEvent;

/**
 * 通知事件
 * Created by wangxiaodan on 2018/4/2.
 */
public abstract class BaseEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BaseEvent(Object source) {
        super(source);
    }


    public abstract void execute();
}
