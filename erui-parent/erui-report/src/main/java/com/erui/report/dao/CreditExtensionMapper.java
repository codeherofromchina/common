package com.erui.report.dao;

import com.erui.report.model.CreditExtension;
import com.erui.report.model.CreditExtensionExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CreditExtensionMapper {
    int countByExample(CreditExtensionExample example);

    int deleteByExample(CreditExtensionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CreditExtension record);

    int insertSelective(CreditExtension record);

    List<CreditExtension> selectByExample(CreditExtensionExample example);

    CreditExtension selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CreditExtension record, @Param("example") CreditExtensionExample example);

    int updateByExample(@Param("record") CreditExtension record, @Param("example") CreditExtensionExample example);

    int updateByPrimaryKeySelective(CreditExtension record);

    int updateByPrimaryKey(CreditExtension record);
    
    void truncateTable();
    /**
     * 查询 起始时间
     * @return Date
     */
    Date selectStart();
    /**
     * 查询 结束时间
     * @return Date
     */
    Date selectEnd();

    //查询授信汇总数据
    Map<String,Object> selectCreditSummary(Map<String,String> params);
    //趋势图 新增授信
    List<Map<String,Object>> selectIncrCreditGroupByEffectiveDate(Map<String,String> params);
    //趋势图 淘汰授信
    List<Map<String,Object>> selectElimiCreditGroupByExpiryDate(Map<String,String> params);
    //趋势图 授信数量 可用授信 已用授信
    List<Map<String,Object>> selectCreditGroupByCreditDate(Map<String,String> params);
    //查询所有大区和国家列表
    List<Map<String, String>> selectAllAreaAndCountryList();
}