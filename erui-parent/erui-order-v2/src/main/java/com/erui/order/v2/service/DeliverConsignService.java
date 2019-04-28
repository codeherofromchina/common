package com.erui.order.v2.service;

/**
 * @Auther 王晓丹
 * @Date 2019/4/28 下午3:34
 */
public interface DeliverConsignService {
    /**
     * 更新出口通知单审核进度完成
     *
     * @param processInstanceId
     * @param auditingProcess
     */
    void updateAuditProcessDone(String processInstanceId, String auditingProcess);

    /**
     * 更新出口通知单审核进度进行中
     *
     * @param processInstanceId
     * @param auditingProcess
     */
    void updateAuditProcessDoing(String processInstanceId, String auditingProcess, String taskId);
}
