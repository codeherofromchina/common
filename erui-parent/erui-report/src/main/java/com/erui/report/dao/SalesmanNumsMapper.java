package com.erui.report.dao;

import com.erui.report.model.SalesmanNums;
import com.erui.report.model.SalesmanNumsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalesmanNumsMapper {
    int countByExample(SalesmanNumsExample example);

    int deleteByExample(SalesmanNumsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SalesmanNums record);

    int insertSelective(SalesmanNums record);

    List<SalesmanNums> selectByExample(SalesmanNumsExample example);

    SalesmanNums selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SalesmanNums record, @Param("example") SalesmanNumsExample example);

    int updateByExample(@Param("record") SalesmanNums record, @Param("example") SalesmanNumsExample example);

    int updateByPrimaryKeySelective(SalesmanNums record);

    int updateByPrimaryKey(SalesmanNums record);
}