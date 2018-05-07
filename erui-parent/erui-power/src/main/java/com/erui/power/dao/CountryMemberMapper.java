package com.erui.power.dao;

import com.erui.power.model.CountryMember;
import com.erui.power.model.CountryMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountryMemberMapper {
    int countByExample(CountryMemberExample example);

    int deleteByExample(CountryMemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CountryMember record);

    int insertSelective(CountryMember record);

    List<CountryMember> selectByExample(CountryMemberExample example);

    CountryMember selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CountryMember record, @Param("example") CountryMemberExample example);

    int updateByExample(@Param("record") CountryMember record, @Param("example") CountryMemberExample example);

    int updateByPrimaryKeySelective(CountryMember record);

    int updateByPrimaryKey(CountryMember record);
}