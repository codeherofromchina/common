package com.erui.report.dao;

import com.erui.report.model.PerformanceIndicators;
import com.erui.report.model.PerformanceIndicatorsExample;
import java.util.List;
import java.util.Map;

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

    /**
     * 查找时间段内的事业部业绩指标信息
     * @param params
     * @return
     */
    List<Map<String,Object>> selectPerformanceIndicatorsWhereTimeByOrg(Map<String, Object> params);

    /**
     * 查找时间段内的地区业绩指标信息
     * @param params
     * @return
     */
    List<Map<String,Object>> selectPerformanceIndicatorsWhereTimeByArea(Map<String, Object> params);

    /**
     * 查找时间段内的国家业绩指标信息
     * @param params
     * @return
     */
    List<Map<String,Object>> selectPerformanceIndicatorsWhereTimeByCountry(Map<String, Object> params);
}