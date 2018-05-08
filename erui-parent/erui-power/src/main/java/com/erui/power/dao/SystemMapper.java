package com.erui.power.dao;

import com.erui.power.model.System;
import com.erui.power.model.SystemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemMapper {
    int countByExample(SystemExample example);

    int deleteByExample(SystemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(System record);

    int insertSelective(System record);

    List<System> selectByExample(SystemExample example);

    System selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") System record, @Param("example") SystemExample example);

    int updateByExample(@Param("record") System record, @Param("example") SystemExample example);

    int updateByPrimaryKeySelective(System record);

    int updateByPrimaryKey(System record);
}