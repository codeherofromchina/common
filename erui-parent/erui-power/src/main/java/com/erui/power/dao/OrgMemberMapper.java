package com.erui.power.dao;

import com.erui.power.model.OrgMember;
import com.erui.power.model.OrgMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgMemberMapper {
    int countByExample(OrgMemberExample example);

    int deleteByExample(OrgMemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrgMember record);

    int insertSelective(OrgMember record);

    List<OrgMember> selectByExample(OrgMemberExample example);

    OrgMember selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrgMember record, @Param("example") OrgMemberExample example);

    int updateByExample(@Param("record") OrgMember record, @Param("example") OrgMemberExample example);

    int updateByPrimaryKeySelective(OrgMember record);

    int updateByPrimaryKey(OrgMember record);
}