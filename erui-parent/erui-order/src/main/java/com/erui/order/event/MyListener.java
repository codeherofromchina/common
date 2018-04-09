package com.erui.order.event;

import com.erui.order.dao.OrderDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.service.AreaService;
import com.erui.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
@Component
public class MyListener implements ApplicationListener<OrderProgressEvent> {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    @Async
    public void onApplicationEvent(OrderProgressEvent orderProgressEvent) {
        Order order = (Order) orderProgressEvent.getSource();
        Project project = order.getProject();
        Integer type = orderProgressEvent.getType();
        if (type == 1) {
            //未执行
            order.setProcessProgress("未执行");
            project.setProcessProgress("未执行");
        } else if (type == 2) {
            order.setProcessProgress("正常执行");
            project.setProcessProgress("正常执行");
        } else if (type == 3) {
            order.setProcessProgress("采购中");
            project.setProcessProgress("采购中");
        } else if (type == 4) {
            order.setProcessProgress("已报检");
            project.setProcessProgress("已报检");
        } else if (type == 5) {
            order.setProcessProgress("质检中");
            project.setProcessProgress("质检中");
        } else if (type == 6) {
            order.setProcessProgress("已入库");
            project.setProcessProgress("已入库");
        } else if (type == 7) {
            order.setProcessProgress("出库质检");
            project.setProcessProgress("出库质检");
        } else if (type == 8) {
            order.setProcessProgress("已出库");
            project.setProcessProgress("已出库");
        } else if (type == 9) {
            order.setProcessProgress("已发运");
            project.setProcessProgress("已发运");
        }
        orderDao.save(order);
        projectDao.save(project);
    }
}
