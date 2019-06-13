package com.erui.order.v2.service;

import com.erui.order.v2.model.Project;

import java.util.List;

/**
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:16
 */
public interface ProjectService {


    /**
     * 更新项目审核进度完成
     * @param processInstanceId
     * @param auditingProcess
     * @param assignee  审核人
     */
    void updateAuditProcessDone(String processInstanceId, String auditingProcess, String assignee);

    /**
     * 更新项目审核进度进行中
     * @param processId
     * @param auditingProcess
     * @param taskId    当前进行的项目
     * @param rejected    是否是驳回
     */
    void updateAuditProcessDoing(String processId, String auditingProcess, String taskId, boolean rejected);

    /**
     * 业务流实例执行结束更新
     * @param processInstanceId
     */
    void updateProcessCompleted(String processInstanceId);

    /**
     * 根据订单号查找项目
     * @param orderId
     * @return
     */
    Project findProjectByOrderId(Integer orderId);

    /**
     * 查找所有采购相关的项目列表
     * @param purchId
     * @return
     */
    List<Project> findProjectsByPurchId(Integer purchId);

    /**
     * 设置当前审核人
     * @param orderId
     * @param userId
     * @param userName
     * @param actId 审核进度
     */
    void updateAuditUser(Long orderId, Long userId, String userName, String actId);

    /**
     * 取消进度的当前审核人
     * @param orderId
     * @param actId
     */
    void deleteAuditUser(Long orderId, String actId);
}
