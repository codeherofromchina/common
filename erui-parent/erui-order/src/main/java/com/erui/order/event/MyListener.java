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
            order.setProcessProgress(Project.ProjectProgressEnum.SUBMIT.getNum().toString());
        } else if (type == 2) {
            order.setProcessProgress(Project.ProjectProgressEnum.EXECUTING.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.EXECUTING.getNum().toString());
        } else if (type == 3) {
            order.setProcessProgress(Project.ProjectProgressEnum.BUYING.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.BUYING.getNum().toString());
        } else if (type == 4) {
            order.setProcessProgress(Project.ProjectProgressEnum.QUARANTINE.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.QUARANTINE.getNum().toString());
        } else if (type == 5) {
            order.setProcessProgress(Project.ProjectProgressEnum.CHECKING.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.CHECKING.getNum().toString());
        } else if (type == 6) {
            order.setProcessProgress(Project.ProjectProgressEnum.IN_STORAGE.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.IN_STORAGE.getNum().toString());
        } else if (type == 7) {
            order.setProcessProgress(Project.ProjectProgressEnum.QUALITY_INSPECTION.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.QUALITY_INSPECTION.getNum().toString());
        } else if (type == 8) {
            order.setProcessProgress(Project.ProjectProgressEnum.OUTSTORAGE.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.OUTSTORAGE.getNum().toString());
        } else if (type == 9) {
            order.setProcessProgress(Project.ProjectProgressEnum.SHIPED.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.SHIPED.getNum().toString());
        }
        orderDao.flush();
        if (project != null) {
            projectDao.save(project);
        }
    }
}
