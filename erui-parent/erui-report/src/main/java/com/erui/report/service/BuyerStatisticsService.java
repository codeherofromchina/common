package com.erui.report.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
public interface BuyerStatisticsService {

    /**
     * 查询注册用户列表
     * @param pageNum
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> registerBuyerList(int pageNum, int pageSize, Map<String, String> params);

    /**
     * 查询会员用户列表
     * @param pageNum
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> membershipBuyerList(int pageNum, int pageSize, Map<String, String> params);

    /**
     * 查询入网用户列表
     * @param pageNum
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> applyBuyerList(int pageNum, int pageSize, Map<String, String> params);

    /**
     * 订单用户统计
     * @param params
     * @return
     */
    Map<String, Object> orderBuyerStatistics(Map<String, Object> params);
}
