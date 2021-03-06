package com.erui.order.service;

import com.erui.order.entity.CheckLog;

import java.util.List;

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 15:49 2018/8/27
 * @modified By
 */
public interface CheckLogService {

    /**
     * @param type  1:订单  2：项目
     * @param refId 项目或订单ID，根据type
     * @return
     */
    CheckLog findLastLog(int type, Integer refId);

    /**
     * @param refId 项目或订单ID，根据type
     * @return
     */
    CheckLog findLogOne(Integer refId);

    /**
     * 插入审核日志
     *
     * @param checkLog_i
     */
    void insert(CheckLog checkLog_i);

    /**
     * @Author:SHIGS
     * @Description 根据订单查找审批日志
     * @Date:18:38 2018/8/29
     * @modified By
     */
    List<CheckLog> findListByOrderId(Integer orderId);


    List<CheckLog> findListByOrderIdAndType(Integer orderId, Integer type);

    /**
     * @Author:SHIGS
     * @Description 根据订单查找审批日志
     * @Date:18:38 2018/8/29
     * @modified By
     */
    List<CheckLog> findListByJoinId(String category, Integer joinId, Integer type);

    /**
     * @Author:SHIGS
     * @Description 根据订单查找已经通过审核的日志
     * @Date:18:38 2018/8/29
     * @modified By
     */
    List<CheckLog> findPassed(Integer orderId);

    /**
     * @Author:SHIGS
     * @Description 根据订单查找已经通过审核的日志导出销售合同审批表用
     * @Date:18:38 2018/8/29
     * @modified By
     */
    List<CheckLog> findPassed2(Integer orderId);

    /**
     * 查询采购的所有审核记录
     *
     * @param purchId
     * @return
     */
    List<CheckLog> findCheckLogsByPurchId(int purchId);


    /**
     * 查询新BPM业务流日志并适配成现有平台日志形式
     * @param processId
     * @param type 1 订单  2 采购订单
     * @return
     */
    List<CheckLog> findAdapterListByProcessId(String processId, Integer type);
    /**
     * 签约主体 英文转中文
     *
     * @param signingCo 签约主体
     */
    String getSigningCoCn(String signingCo);
}
