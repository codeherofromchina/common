package com.erui.order.v2.service;

import com.erui.order.v2.model.Project;

/**
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:16
 */
public interface ProjectService {


    /**
     * 更新项目审核进度完成
     * @param processInstanceId
     * @param auditingProcess
     */
    void updateAuditProcessDone(String processInstanceId, String auditingProcess);

    /**
     * 更新项目审核进度进行中
     * @param processInstanceId
     * @param auditingProcess
     * @param taskId    当前进行的项目
     */
    void updateAuditProcessDoing(String processInstanceId, String auditingProcess, String taskId);
}
