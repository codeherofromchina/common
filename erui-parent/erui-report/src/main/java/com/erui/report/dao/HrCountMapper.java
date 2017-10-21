package com.erui.report.dao;

import com.erui.report.model.HrCount;
import com.erui.report.model.HrCountExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HrCountMapper {
    Map selectHrCountByPart(HrCountExample hrCountExample);
    int countByExample(HrCountExample example);

    int deleteByExample(HrCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HrCount record);

    int insertSelective(HrCount record);

    List<HrCount> selectByExample(HrCountExample example);

    HrCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HrCount record, @Param("example") HrCountExample example);

    int updateByExample(@Param("record") HrCount record, @Param("example") HrCountExample example);

    int updateByPrimaryKeySelective(HrCount record);

    int updateByPrimaryKey(HrCount record);
}