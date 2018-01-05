package com.erui.report.dao;

import com.erui.report.model.RequestReceive;
import com.erui.report.model.RequestReceiveExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RequestReceiveMapper {
    int countByExample(RequestReceiveExample example);

    int deleteByExample(RequestReceiveExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RequestReceive record);

    int insertSelective(RequestReceive record);

    List<RequestReceive> selectByExample(RequestReceiveExample example);

    RequestReceive selectByPrimaryKey(Long id);
    Double selectBackAmount(RequestReceiveExample example);
    List<Map<String, Object>> selectBackAmountGroupByArea(RequestReceiveExample example);
    List<Map<String, Object>> selectBackAmountGroupByCompany(RequestReceiveExample example);
    List<Map<String, Object>> selectBackAmoutGroupByOrg(RequestReceiveExample example);
    List<Map<String, Object>> selectBackAmountGroupByBackDate(RequestReceiveExample example);

    int updateByExampleSelective(@Param("record") RequestReceive record, @Param("example") RequestReceiveExample example);

    int updateByExample(@Param("record") RequestReceive record, @Param("example") RequestReceiveExample example);

    int updateByPrimaryKeySelective(RequestReceive record);

    int updateByPrimaryKey(RequestReceive record);
     void truncateTable();
}