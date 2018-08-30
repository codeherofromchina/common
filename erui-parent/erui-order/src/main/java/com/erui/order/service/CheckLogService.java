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
     *
     * @param type  1:订单  2：项目
     * @param refId   项目或订单ID，根据type
     * @return
     */
    CheckLog findLastLog(int type, Integer refId);

    /**
     * 插入审核日志
     * @param checkLog_i
     */
    void insert(CheckLog checkLog_i);
     /**
      * @Author:SHIGS
      * @Description 根据订单查找审批日志
      * @Date:18:38 2018/8/29
      * @modified By
      */
     List<CheckLog> findListByOrderId(int type, Integer orderId);
    /**
     * @Author:SHIGS
     * @Description 根据订单查找已经通过审核的日志
     * @Date:18:38 2018/8/29
     * @modified By
     */
    List<CheckLog> findPassed(Integer orderId);
}
