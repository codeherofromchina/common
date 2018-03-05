package com.erui.report.dao;

import com.erui.report.model.CategoryQuality;
import com.erui.report.model.CategoryQualityExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CategoryQualityMapper {
    int countByExample(CategoryQualityExample example);

    int deleteByExample(CategoryQualityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CategoryQuality record);

    int insertSelective(CategoryQuality record);

    List<CategoryQuality> selectByExample(CategoryQualityExample example);

    CategoryQuality selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CategoryQuality record, @Param("example") CategoryQualityExample example);

    int updateByExample(@Param("record") CategoryQuality record, @Param("example") CategoryQualityExample example);

    int updateByPrimaryKeySelective(CategoryQuality record);

    int updateByPrimaryKey(CategoryQuality record);
    
    void truncateTable();
    /**
     * 查询品控总览数据
     * @param params startTime ，endTime
     * @return
     */
    Map<String, Object> selectQualitySummaryData(Map<String, String> params);
    /**
     * 趋势图数据
     * @param params startTime ，endTime
     * @return
     */
    List<Map<String, Object>> selectTrendData(Map<String, String> params);
}