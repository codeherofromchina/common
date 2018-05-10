package com.erui.power.dao;

import com.erui.power.model.RoleAttrCountry;
import com.erui.power.model.RoleAttrCountryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleAttrCountryMapper {
    int countByExample(RoleAttrCountryExample example);

    int deleteByExample(RoleAttrCountryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleAttrCountry record);

    int insertSelective(RoleAttrCountry record);

    List<RoleAttrCountry> selectByExample(RoleAttrCountryExample example);

    RoleAttrCountry selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleAttrCountry record, @Param("example") RoleAttrCountryExample example);

    int updateByExample(@Param("record") RoleAttrCountry record, @Param("example") RoleAttrCountryExample example);

    int updateByPrimaryKeySelective(RoleAttrCountry record);

    int updateByPrimaryKey(RoleAttrCountry record);
}