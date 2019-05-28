package com.erui.order.v2.service;

import com.erui.order.v2.model.DeliverConsign;
import com.erui.order.v2.model.Purch;

import java.util.List;
import java.util.Map;

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
     * @param assignee  审核人
     */
    void updateAuditProcessDone(String processInstanceId, String auditingProcess, String assignee);

    /**
     * 更新出口通知单审核进度进行中
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
     * 通过id查找出口通知单信息
     * @param id
     * @return
     */
    DeliverConsign selectById(Integer id);

    /**
     * 通过出口通知单ID查询出口通知单号和出口通知单ID的对应关系
     * @param deliverConsignIds
     * @return
     */
    Map<Integer, String> findDeliverConsignNoByDeliverConsignIds(List<Integer> deliverConsignIds);

    /**
     * 驳回出口通知单补偿授信额度
     *
     * @param id
     */
    void rejectDeliverConsign(Integer id) throws Exception;

    /**
     * 设置当前审核人
     * @param deliverConsignId
     * @param userId
     * @param userName
     */
    void updateAuditUser(Long deliverConsignId, Long userId, String userName, String actId);
}
