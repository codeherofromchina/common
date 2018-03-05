package com.erui.report.dao;

import com.erui.report.model.MarketerCount;
import com.erui.report.model.MarketerCountExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MarketerCountMapper {
    int countByExample(MarketerCountExample example);

    int deleteByExample(MarketerCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MarketerCount record);

    int insertSelective(MarketerCount record);

    List<MarketerCount> selectByExample(MarketerCountExample example);

    MarketerCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MarketerCount record, @Param("example") MarketerCountExample example);

    int updateByExample(@Param("record") MarketerCount record, @Param("example") MarketerCountExample example);

    int updateByPrimaryKeySelective(MarketerCount record);

    int updateByPrimaryKey(MarketerCount record);
    
    void truncateTable();
    //市场询单top1
    Map<String,Object> marketInqTop1(Map<String,String> params);
    //市场成单top1
    Map<String,Object> marketSucessOrdTop1(Map<String,String> params);
    //市场新增会员top1
    Map<String,Object> marketIncrMemberTop1(Map<String,String> params);
    //区域人员询单top1
    Map<String,Object> areaMarketerInqTop1(Map<String,String> params);
    //区域人员成单top1
    Map<String,Object> areaMarketerSucessOrdTop1(Map<String,String> params);
    //区域人员新增会员top1
    Map<String,Object> areaMarketerIncrMemberTop1(Map<String,String> params);
    //区域和国家列表
    List<Map<String, String>> selectAllAreaAndCountryList();
    //查询市场汇总数据
    Map<String,Object> selectMarketerCountSummary(Map<String,String> params);
}