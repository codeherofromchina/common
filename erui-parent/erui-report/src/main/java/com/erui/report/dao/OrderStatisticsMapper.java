package com.erui.report.dao;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
public interface OrderStatisticsMapper {

    /**
     * 查询年度订单业绩列表
     * @param startYear
     * @param endYear
     * @return
     */
    List<Map<String, Object>> yearPerformance(@Param("startYear") Integer startYear, @Param("endYear") Integer endYear);


    /**
     * 查询年度区域订单业绩列表
     * @param startYear
     * @param endYear
     * @return
     */
    List<Map<String, Object>> yearAreaPerformance(@Param("startYear") Integer startYear, @Param("endYear") Integer endYear);


    /**
     * 查询订单中项目列表
     * @param params
     * @return
     */
    BigDecimal projectTotalMoney(Map<String, String> params);

    /**
     * 查询订单中项目列表
     * @param params
     * @return
     */
    List<Map<String, Object>> projectList(Map<String, String> params);
}
