package com.erui.report.service;

import com.erui.report.model.PerformanceIndicators;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 订单统计服务类
 * Created by wangxiaodan on 2019/3/15.
 */
public interface OrderStatisticsService {

    /**
     * 按照年统计整体业绩
     * @param startYear   所统计的开始年份
     * @param endYear   所统计的结束年份
     * @return
     */
    Map<String, Object> yearPerformance(Integer startYear, Integer endYear);


    /**
     * 按照年统计整体区域业绩
     * @param startYear   所统计的开始年份
     * @param endYear   所统计的结束年份
     * @return
     */
    Map<String, Object> yearAreaPerformance(Integer startYear, Integer endYear);

    /**
     * 业务业绩统计 - 项目列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Map<String, Object>> projectList(int pageNum, int pageSize, Map<String, String> params);
}
