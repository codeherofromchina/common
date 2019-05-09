package com.erui.order.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步事件监听器
 * Created by wangxiaodan on 2018/4/2.
 */
@Component
public class AsyncBaseEventListener implements ApplicationListener<AsyncBaseEvent> {

    @Override
    @Async
    public void onApplicationEvent(AsyncBaseEvent event) {
        event.execute();
    }
}
