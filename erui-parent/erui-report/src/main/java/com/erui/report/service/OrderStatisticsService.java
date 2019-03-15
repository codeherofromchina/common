package com.erui.report.service;

import java.util.Map;

/**
 * 订单统计服务类
 * Created by wangxiaodan on 2019/3/15.
 */
public interface OrderStatisticsService {

    /**
     * 按照年统计整体业绩
     * @param year   所统计的年份，如果为null，则统计所有年份（2016、2017、2018、2019）
     * @return
     */
    Map<String, Object> yearPerformance(Integer year);


    /**
     * 按照年统计整体区域业绩
     * @param year   所统计的年份，如果为null，则统计所有年份（2016、2017、2018、2019）
     * @return
     */
    Map<String, Object> yearAreaPerformance(Integer year);
}
