package com.erui.order.event;

import com.erui.order.entity.Area;
import com.erui.order.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
@Component
public class MyListener implements ApplicationListener<MyEvent> {

    @Autowired
    private AreaService areaService;

    @Override
    @Async
    public void onApplicationEvent(MyEvent event) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Area area = areaService.findByBn("Asia-Pacific");
        //System.out.println("AREA:" + area.getName());
        event.executeBusiness();
    }
}
