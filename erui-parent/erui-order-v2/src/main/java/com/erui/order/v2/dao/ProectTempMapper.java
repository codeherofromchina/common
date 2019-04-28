package com.erui.order.v2.dao;

import com.erui.order.v2.model.ProectTemp;
import com.erui.order.v2.model.ProectTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProectTempMapper {
    int countByExample(ProectTempExample example);

    int deleteByExample(ProectTempExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProectTemp record);

    int insertSelective(ProectTemp record);

    List<ProectTemp> selectByExample(ProectTempExample example);

    ProectTemp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProectTemp record, @Param("example") ProectTempExample example);

    int updateByExample(@Param("record") ProectTemp record, @Param("example") ProectTempExample example);

    int updateByPrimaryKeySelective(ProectTemp record);

    int updateByPrimaryKey(ProectTemp record);
}