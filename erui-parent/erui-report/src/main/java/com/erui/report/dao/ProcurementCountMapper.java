package com.erui.report.dao;

import com.erui.report.model.ProcurementCount;
import com.erui.report.model.ProcurementCountExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ProcurementCountMapper {
    int countByExample(ProcurementCountExample example);

    int deleteByExample(ProcurementCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProcurementCount record);

    int insertSelective(ProcurementCount record);

    List<ProcurementCount> selectByExample(ProcurementCountExample example);

    ProcurementCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProcurementCount record, @Param("example") ProcurementCountExample example);

    int updateByExample(@Param("record") ProcurementCount record, @Param("example") ProcurementCountExample example);

    int updateByPrimaryKeySelective(ProcurementCount record);

    int updateByPrimaryKey(ProcurementCount record);
    
    void truncateTable();

    List<Map<String, Object>> selectProcurPandent(ProcurementCountExample example);
    List<Map<String,Object>>  selectProcurTrend(ProcurementCountExample example);
    List<Map<String, String>> selectAllAreaAndCountryList();
    List<ProcurementCount>  selectCategoryDetail(ProcurementCountExample example);
}