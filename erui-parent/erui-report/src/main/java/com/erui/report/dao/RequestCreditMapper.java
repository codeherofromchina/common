package com.erui.report.dao;

import com.erui.report.model.RequestCredit;
import com.erui.report.model.RequestCreditExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RequestCreditMapper {
    Date selectStart();

    Date selectEnd();

    Map selectByAreaOrCountry(RequestCreditExample example);

    List<Map> selectCountry(RequestCreditExample example);

    List<Map> selectArea();

    Map selectTotal();

    Map<String, Object> selectRequestTotal(RequestCreditExample example);

    List<Map> selectRequestTrend(RequestCreditExample example);

    List<Map> selectNextRequestTrend(RequestCreditExample example);

    List<Map<String, Object>> selectReceiveGroupByArea(RequestCreditExample example);
    List<Map<String, Object>> selectReceiveGroupByCompany(RequestCreditExample example);
    List<Map<String, Object>> selectReceiveGroupByOrg(RequestCreditExample example);
    List<Map<String, Object>> selectReceiveGroupByBackDate(RequestCreditExample example);

    //查询账龄分析数据
    Map<String, Object> selectAgingSummary(Map<String, String> map);
    //根据条件查询个大区的账龄数据
    List<Map<String,Object>> selectAgingSummaryByConditionGroupByArea(Map<String, String> map);

    int countByExample(RequestCreditExample example);

    int deleteByExample(RequestCreditExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RequestCredit record);

    int insertSelective(RequestCredit record);

    List<RequestCredit> selectByExample(RequestCreditExample example);

    RequestCredit selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RequestCredit record, @Param("example") RequestCreditExample example);

    int updateByExample(@Param("record") RequestCredit record, @Param("example") RequestCreditExample example);

    int updateByPrimaryKeySelective(RequestCredit record);

    int updateByPrimaryKey(RequestCredit record);

    void truncateTable();

    List<Map<String,String>>   selectAllCompanyAndOrgList();

    Double selectReceive(RequestCreditExample example);
}