package com.erui.report.dao;

import com.erui.report.model.Target;
import com.erui.report.model.TargetExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TargetMapper {
    int countByExample(TargetExample example);

    int deleteByExample(TargetExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Target record);

    int insertSelective(Target record);

    List<Target> selectByExample(TargetExample example);

    Target selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Target record, @Param("example") TargetExample example);

    int updateByExample(@Param("record") Target record, @Param("example") TargetExample example);

    int updateByPrimaryKeySelective(Target record);

    int updateByPrimaryKey(Target record);

    Double selectTargetAmountByCondition(TargetExample example);
    //查询各事业部的年度指标
    List<Map<String,Object>> selectTargetGroupByOrg();
    //查询各地区的年度指标
    List<Map<String,Object>> selectTargetGroupByArea();
}