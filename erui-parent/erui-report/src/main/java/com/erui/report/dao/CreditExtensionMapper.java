package com.erui.report.dao;

import com.erui.report.model.CreditExtension;
import com.erui.report.model.CreditExtensionExample;
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

    //查询授信汇总数据
    Map<String,Object> selectCreditSummary(Map<String,String> params);
}