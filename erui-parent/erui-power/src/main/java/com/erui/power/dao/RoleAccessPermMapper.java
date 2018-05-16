package com.erui.power.dao;

import com.erui.power.model.RoleAccessPerm;
import com.erui.power.model.RoleAccessPermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleAccessPermMapper {
    int countByExample(RoleAccessPermExample example);

    int deleteByExample(RoleAccessPermExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleAccessPerm record);

    int insertSelective(RoleAccessPerm record);

    List<RoleAccessPerm> selectByExample(RoleAccessPermExample example);

    RoleAccessPerm selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleAccessPerm record, @Param("example") RoleAccessPermExample example);

    int updateByExample(@Param("record") RoleAccessPerm record, @Param("example") RoleAccessPermExample example);

    int updateByPrimaryKeySelective(RoleAccessPerm record);

    int updateByPrimaryKey(RoleAccessPerm record);
}