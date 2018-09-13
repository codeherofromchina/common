package com.erui.report.dao;

import com.erui.report.model.PerformanceIndicators;
import com.erui.report.model.PerformanceIndicatorsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PerformanceIndicatorsMapper {
    int countByExample(PerformanceIndicatorsExample example);

    int deleteByExample(PerformanceIndicatorsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PerformanceIndicators record);

    int insertSelective(PerformanceIndicators record);

    List<PerformanceIndicators> selectByExample(PerformanceIndicatorsExample example);

    PerformanceIndicators selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PerformanceIndicators record, @Param("example") PerformanceIndicatorsExample example);

    int updateByExample(@Param("record") PerformanceIndicators record, @Param("example") PerformanceIndicatorsExample example);

    int updateByPrimaryKeySelective(PerformanceIndicators record);

    int updateByPrimaryKey(PerformanceIndicators record);
}