package com.erui.power.dao;

import com.erui.power.model.RoleAttrOrg;
import com.erui.power.model.RoleAttrOrgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleAttrOrgMapper {
    int countByExample(RoleAttrOrgExample example);

    int deleteByExample(RoleAttrOrgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleAttrOrg record);

    int insertSelective(RoleAttrOrg record);

    List<RoleAttrOrg> selectByExample(RoleAttrOrgExample example);

    RoleAttrOrg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleAttrOrg record, @Param("example") RoleAttrOrgExample example);

    int updateByExample(@Param("record") RoleAttrOrg record, @Param("example") RoleAttrOrgExample example);

    int updateByPrimaryKeySelective(RoleAttrOrg record);

    int updateByPrimaryKey(RoleAttrOrg record);
}