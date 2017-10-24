package com.erui.report.dao;

import com.erui.report.model.RequestCredit;
import com.erui.report.model.RequestCreditExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RequestCreditMapper {
    List<Map> selectArea();
    Map selectTotal();
    Map selectRequestTotal(RequestCreditExample example);
    List<Map> selectRequestTrend(RequestCreditExample example);
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
}