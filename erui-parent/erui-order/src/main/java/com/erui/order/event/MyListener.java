package com.erui.order.event;

import com.erui.order.dao.OrderDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
@Component
public class MyListener implements ApplicationListener<OrderProgressEvent> {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public void onApplicationEvent(OrderProgressEvent orderProgressEvent) {
        Order order = (Order) orderProgressEvent.getSource();
        Project project = order.getProject();
        Integer type = orderProgressEvent.getType();
        Integer processProgress = 0;
        if (!StringUtils.isEmpty(order.getProcessProgress())) {
            processProgress = Integer.valueOf(order.getProcessProgress());
        }
        if (type == 1 && StringUtils.isEmpty(order.getProcessProgress())) {
            //未执行
            order.setProcessProgress(Project.ProjectProgressEnum.SUBMIT.getNum().toString());
        } else if (type == 2 && processProgress < 2) {
            // 现货出库和海外销（当地采购）的单子流程状态改为 已发运 20180726 需求修改
            /*if ((order.getOverseasSales() == 3 || order.getOrderCategory() == 4) && project.getAuditingStatus() == 4) {
                order.setProcessProgress(Project.ProjectProgressEnum.SHIPED.getNum().toString());
                project.setProcessProgress(Project.ProjectProgressEnum.SHIPED.getNum().toString());
            } else {
            }*/
            // 10需求，所有类型都正常走
            order.setProcessProgress(Project.ProjectProgressEnum.EXECUTING.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.EXECUTING.getNum().toString());
        } else if (type == 3 && processProgress < 3) {
            order.setProcessProgress(Project.ProjectProgressEnum.BUYING.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.BUYING.getNum().toString());
        } else if (type == 4 && processProgress < 4) {
            order.setProcessProgress(Project.ProjectProgressEnum.QUARANTINE.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.QUARANTINE.getNum().toString());
        } else if (type == 5 && processProgress < 5) {
            order.setProcessProgress(Project.ProjectProgressEnum.CHECKING.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.CHECKING.getNum().toString());
        } else if (type == 6 && processProgress < 6) {
            order.setProcessProgress(Project.ProjectProgressEnum.IN_STORAGE.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.IN_STORAGE.getNum().toString());
        } else if (type == 7 && processProgress < 7) {
            order.setProcessProgress(Project.ProjectProgressEnum.QUALITY_INSPECTION.getNum().toString());
            order.setInspectN(2);
            project.setProcessProgress(Project.ProjectProgressEnum.QUALITY_INSPECTION.getNum().toString());
        } else if (type == 8 && processProgress < 8) {
            order.setProcessProgress(Project.ProjectProgressEnum.OUTSTORAGE.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.OUTSTORAGE.getNum().toString());
        } else if (type == 9 && processProgress < 9) {
            order.setProcessProgress(Project.ProjectProgressEnum.SHIPED.getNum().toString());
            project.setProcessProgress(Project.ProjectProgressEnum.SHIPED.getNum().toString());
        } else {
            if ((order.getOverseasSales() == 3 || order.getOrderCategory() == 4) && project.getAuditingStatus() == 4) {
                order.setProcessProgress(Project.ProjectProgressEnum.SHIPED.getNum().toString());
                order.setStatus(Order.StatusEnum.DONE.getCode());
                if (order.getPayStatus() == 3) {
                    // 调用积分系统
                    applicationContext.publishEvent(new NotifyPointProjectEvent(applicationContext, order.getId(), orderProgressEvent.getToken()));
                }
                project.setProcessProgress(Project.ProjectProgressEnum.SHIPED.getNum().toString());
                project.setProjectStatus(Project.ProjectStatusEnum.DONE.getCode());
            }
        }
        orderDao.flush();
        if (project != null) {
            projectDao.save(project);
        }
    }
}
