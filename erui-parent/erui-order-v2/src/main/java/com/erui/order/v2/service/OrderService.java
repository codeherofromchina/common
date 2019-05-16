package com.erui.order.v2.service;

import com.erui.order.v2.model.Order;

/**
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:16
 */
public interface OrderService {

    /**
     * 通过业务流程实例标识查找唯一订单信息
     *
     * @param processId
     * @return
     */
    Order findOrderByProcessId(String processId);

    /**
     * 更新订单审核进度完成
     *
     * @param processInstanceId
     * @param auditingProcess
     * @param assignee  审核人
     */
    void updateAuditProcessDone(String processInstanceId, String auditingProcess, String assignee);

    /**
     * 更新订单审核进度进行中
     *
     * @param processInstanceId
     * @param auditingProcess
     */
    void updateAuditProcessDoing(String processInstanceId, String auditingProcess, String taskId);

    /**
     * 业务流实例执行结束更新
     * @param processInstanceId
     */
    void updateProcessCompleted(String processInstanceId);

    /**
     * 通过ID查找订单
     * @param orderId
     * @return
     */
    Order findOrderById(Integer orderId);

    /**
     * 更新订单
     * @param order
     */
    void updateById(Integer id,Order order);
}
