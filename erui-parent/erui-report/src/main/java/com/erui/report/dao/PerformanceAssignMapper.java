package com.erui.report.dao;

import com.erui.report.model.PerformanceAssign;

import java.util.List;
import java.util.Map;

public interface PerformanceAssignMapper {


    int deleteByPrimaryKey(Long id);

    int insert(PerformanceAssign record);

    int insertSelective(PerformanceAssign record);

    PerformanceAssign selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PerformanceAssign record);

    /**
     * 根据月份查询国家业绩分配明细
     * @param params
     * @return
     */
    List<PerformanceAssign> selectCountryAssignDetailByTime(Map<String, String> params);

}