package com.erui.report.dao;

import java.util.List;
import java.util.Map;

/**
 * 报价统计数据库查询接口
 * Created by wangxiaodan on 2019/3/16.
 */
public interface QuoteStatisticsMapper {

    /**
     * 查找获取人列表
     * @return
     */
    List<Map<String, Object>> findAllAcquiringUserList();

    /**
     * 根据给定id列表查询获取人
     * @param list
     * @return
     */
    List<Map<String, Object>> findAcquiringUserByUserIdSet(List<Integer> list);

    /**
     * 查询时间段内
     * @param params
     * @return
     */
    List<Map<String, Object>> getQuoteMinTimeAndTotalNum(Map<String, Object> params);

    /**
     * 查询时间段内
     * @param params
     * @return
     */
    List<Map<String, Object>> getOrderMinTimeAndTotalNum(Map<String, Object> params);
}
