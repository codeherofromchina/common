package com.erui.order.v2.service;

import com.erui.order.v2.model.Purch;

import java.util.List;
import java.util.Map;

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


    Purch findPurchByProcessId(String processId);
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
    Integer updateProcessCompleted(String processInstanceId);

    /**
     * 唯一标识查找采购
     * @param id
     * @return
     */
    Purch selectById(Integer id);


    /**
     * 采购ID查询采购号和采购ID的对应关系
     * @param purchIds
     * @return
     */
    Map<Integer, String> findPurchNoByPurchIds(List<Integer> purchIds);

    /**
     * 驳回采购订单返还已采购数量
     *
     * @param id
     */
    void rejectPurch(Integer id);

    /**
     * 设置当前审核人
     * @param purchId
     * @param userId
     * @param userName
     */
    void updateAuditUser(Long purchId, Long userId, String userName, String actId);

    /**
     * 取消进度的当前审核人
     * @param purchId
     * @param actId
     */
    void deleteAuditUser(Long purchId, String actId);
}
