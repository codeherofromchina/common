package com.erui.power.dao;

import com.erui.power.model.RoleMember;
import com.erui.power.model.RoleMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMemberMapper {
    int countByExample(RoleMemberExample example);

    int deleteByExample(RoleMemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleMember record);

    int insertSelective(RoleMember record);

    List<RoleMember> selectByExample(RoleMemberExample example);

    RoleMember selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleMember record, @Param("example") RoleMemberExample example);

    int updateByExample(@Param("record") RoleMember record, @Param("example") RoleMemberExample example);

    int updateByPrimaryKeySelective(RoleMember record);

    int updateByPrimaryKey(RoleMember record);
}