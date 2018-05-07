package com.erui.power.dao;

import com.erui.power.model.FuncPermResources;
import com.erui.power.model.FuncPermResourcesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FuncPermResourcesMapper {
    int countByExample(FuncPermResourcesExample example);

    int deleteByExample(FuncPermResourcesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FuncPermResources record);

    int insertSelective(FuncPermResources record);

    List<FuncPermResources> selectByExample(FuncPermResourcesExample example);

    FuncPermResources selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FuncPermResources record, @Param("example") FuncPermResourcesExample example);

    int updateByExample(@Param("record") FuncPermResources record, @Param("example") FuncPermResourcesExample example);

    int updateByPrimaryKeySelective(FuncPermResources record);

    int updateByPrimaryKey(FuncPermResources record);
}