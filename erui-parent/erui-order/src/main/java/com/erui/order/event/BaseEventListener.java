package com.erui.order.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 同步事件监听器
 * Created by wangxiaodan on 2018/4/2.
 */
@Component
public class BaseEventListener implements ApplicationListener<BaseEvent> {

    @Override
    public void onApplicationEvent(BaseEvent event) {
        event.execute();
    }
}
