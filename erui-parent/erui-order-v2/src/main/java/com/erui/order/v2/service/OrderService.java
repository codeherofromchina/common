package com.erui.order.v2.service;

import com.erui.order.v2.model.Order;

import java.util.List;
import java.util.Map;

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
     * @param orderId
     * @param auditingProcess
     * @param rejected    是否是驳回
     */
    void updateAuditProcessDoing(Long orderId, String auditingProcess, String taskId, boolean rejected);

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

    /**
     * 通过订单号查询采购合同号和订单ID的对应关系
     * @param orderIds
     * @return
     */
    Map<Integer, String> findContractsByOrderIds(List<Integer> orderIds);

    /**
     * 设置当前审核人
     * @param orderId
     * @param userId
     * @param userName
     */
    void updateAuditUser(Long orderId, Long userId, String userName, String actId);

    /**
     * 取消进度的当前审核人
     * @param orderId
     * @param actId
     */
    void deleteAuditUser(Long orderId, String actId);
}
