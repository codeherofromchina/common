package com.erui.report.service;

import com.erui.report.model.BpmTaskRuntime;

import java.util.List;

/**
 * 流程--任务运行时表服务类
 */
public interface BpmTaskRuntimeService {

    /**
     * 删除任务运行时信息
     * @param processInstanceId
     * @param taskId
     */
    void delBpmTaskRuntime(String processInstanceId, String taskId);


    /**
     * 添加任务运行时信息
     * @param bpmTaskRuntime
     */
    void addBpmTaskRuntime(BpmTaskRuntime bpmTaskRuntime);



    List<Long> findBizObjIdList(String actId, String bizType) ;


}
