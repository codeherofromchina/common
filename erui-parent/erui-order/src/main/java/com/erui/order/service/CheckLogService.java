package com.erui.order.service;

import com.erui.order.entity.CheckLog;

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
}
