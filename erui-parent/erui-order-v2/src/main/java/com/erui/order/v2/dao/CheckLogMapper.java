package com.erui.order.v2.dao;

import com.erui.order.v2.model.CheckLog;
import com.erui.order.v2.model.CheckLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CheckLogMapper {
    int countByExample(CheckLogExample example);

    int deleteByExample(CheckLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CheckLog record);

    int insertSelective(CheckLog record);

    List<CheckLog> selectByExample(CheckLogExample example);

    CheckLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CheckLog record, @Param("example") CheckLogExample example);

    int updateByExample(@Param("record") CheckLog record, @Param("example") CheckLogExample example);

    int updateByPrimaryKeySelective(CheckLog record);

    int updateByPrimaryKey(CheckLog record);
}