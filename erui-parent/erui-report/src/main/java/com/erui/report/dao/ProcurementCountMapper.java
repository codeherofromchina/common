package com.erui.report.dao;

import com.erui.report.model.ProcurementCount;
import com.erui.report.model.ProcurementCountExample;

import java.util.Date;
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
    /**
     * 查询起始时间
     */
    Date selectStart();
    /**
     * 查询结束时间
     */
    Date selectEnd();
    /**
     * 查询国家列表
     */
    List<Map> selectCountry(ProcurementCountExample example);
    /**
     * 查询大区列表
     */
    List<Map> selectArea();
    /**
     * 获取采购申请单数量
     */
    int selectPlanCount(ProcurementCountExample example);
    /**
     * 获取签约合同数量和金额
     */
    List<Map<String, Object>> selectExecutePandent(ProcurementCountExample example);
    /**
     * 获取趋势图 签约合同数量和金额
     */
    List<Map<String,Object>>  selectExecuteDataTrend(ProcurementCountExample example);
    /**
     * 获取趋势图 计划采购单数量
     */
    List<Map<String,Object>>  selectPlanDataTrend(ProcurementCountExample example);
    /**
     * 获取所有国家和所属大区
     */
    List<Map<String, String>> selectAllAreaAndCountryList();
    /**
     * 获取各分类的签约合同数量和金额
     */
    List<ProcurementCount>  selectCategoryExecuteData(ProcurementCountExample example);
    /**
     * 获取分类计划采购单数量
     */
    List<ProcurementCount>  selectCategoryPlanData(ProcurementCountExample example);
}