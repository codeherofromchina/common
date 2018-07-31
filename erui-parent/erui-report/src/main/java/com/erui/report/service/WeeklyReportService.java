package com.erui.report.service;

import java.util.List;
import java.util.Map;

public interface WeeklyReportService {

    /**
     * 查询各地区时间段内新注册用户数量
     * @param params
     * @return
     */
    Map<String, Object> selectBuyerRegistCountGroupByArea(Map<String,Object> params);
    /**
     * 查询各地区时间段内会员数量
     * @param params
     * @return
     */
    Map<String, Object> selectBuyerCountGroupByArea(Map<String,Object> params);

    /**
     * 查询各地区时间段内询单数
     * @param params    {"chainStartTime":"2018/07/03","startTime":"2018/07/09","endTime":"2018/07/15"}
     *                  chainStartTime:上周开始时间
     *                  startTime：本周开始时间
     *                  endTime:本周结束时间
     * @return
     */
    Map<String,Object> selectInqNumGroupByArea(Map<String, Object> params);

    /**
     * 查询各个地区时间段内的报价数量和金额信息
     * @param params
     * @return
     */
    Map<String,Object> selectQuoteInfoGroupByArea(Map<String, Object> params);

    /**
     * 查询各个地区时间段内的订单数量和金额信息
     * @param params
     * @return
     */
    Map<String,Object> selectOrderInfoGroupByArea(Map<String, Object> params);
}
