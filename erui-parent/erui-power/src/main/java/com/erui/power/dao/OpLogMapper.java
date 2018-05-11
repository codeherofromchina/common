package com.erui.power.dao;

import com.erui.power.model.OpLog;
import com.erui.power.model.OpLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpLogMapper {
    int countByExample(OpLogExample example);

    int deleteByExample(OpLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpLog record);

    int insertSelective(OpLog record);

    List<OpLog> selectByExampleWithBLOBs(OpLogExample example);

    List<OpLog> selectByExample(OpLogExample example);

    OpLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpLog record, @Param("example") OpLogExample example);

    int updateByExampleWithBLOBs(@Param("record") OpLog record, @Param("example") OpLogExample example);

    int updateByExample(@Param("record") OpLog record, @Param("example") OpLogExample example);

    int updateByPrimaryKeySelective(OpLog record);

    int updateByPrimaryKeyWithBLOBs(OpLog record);

    int updateByPrimaryKey(OpLog record);
}