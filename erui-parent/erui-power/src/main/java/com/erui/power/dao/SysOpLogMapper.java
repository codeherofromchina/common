package com.erui.power.dao;

import com.erui.power.model.SysOpLog;
import com.erui.power.model.SysOpLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysOpLogMapper {
    int countByExample(SysOpLogExample example);

    int deleteByExample(SysOpLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysOpLog record);

    int insertSelective(SysOpLog record);

    List<SysOpLog> selectByExampleWithBLOBs(SysOpLogExample example);

    List<SysOpLog> selectByExample(SysOpLogExample example);

    SysOpLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysOpLog record, @Param("example") SysOpLogExample example);

    int updateByExampleWithBLOBs(@Param("record") SysOpLog record, @Param("example") SysOpLogExample example);

    int updateByExample(@Param("record") SysOpLog record, @Param("example") SysOpLogExample example);

    int updateByPrimaryKeySelective(SysOpLog record);

    int updateByPrimaryKeyWithBLOBs(SysOpLog record);

    int updateByPrimaryKey(SysOpLog record);
}