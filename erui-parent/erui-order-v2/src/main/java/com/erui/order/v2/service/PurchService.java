package com.erui.order.v2.service;

import com.erui.order.v2.model.Purch;

/**
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:16
 */
public interface PurchService {


    /**
     * 更新项目审核进度完成
     * @param processInstanceId
     * @param auditingProcess
     * @param assignee  审核人
     */
    void updateAuditProcessDone(String processInstanceId, String auditingProcess, String assignee);

    /**
     * 更新项目审核进度进行中
     * @param processInstanceId
     * @param auditingProcess
     * @param taskId    当前进行的项目
     */
    void updateAuditProcessDoing(String processInstanceId, String auditingProcess, String taskId);

    /**
     * 业务流实例执行结束更新
     * @param processInstanceId
     */
    void updateProcessCompleted(String processInstanceId);

    /**
     * 唯一标识查找采购
     * @param id
     * @return
     */
    Purch selectById(Integer id);
}
