package com.erui.report.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
public interface OrderStatisticsMapper {

    /**
     * 查询年度订单业绩列表
     * @param year
     * @return
     */
    List<Map<String, Object>> yearPerformance(@Param("year") Integer year);


    /**
     * 查询年度区域订单业绩列表
     * @param year
     * @return
     */
    List<Map<String, Object>> yearAreaPerformance(@Param("year") Integer year);
}
