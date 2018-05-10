package com.erui.power.dao;

import com.erui.power.model.FuncPerm;
import com.erui.power.model.FuncPermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FuncPermMapper {
    int countByExample(FuncPermExample example);

    int deleteByExample(FuncPermExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FuncPerm record);

    int insertSelective(FuncPerm record);

    List<FuncPerm> selectByExample(FuncPermExample example);

    List<FuncPerm> selectByEmployeeId(Integer empId);

    FuncPerm selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FuncPerm record, @Param("example") FuncPermExample example);

    int updateByExample(@Param("record") FuncPerm record, @Param("example") FuncPermExample example);

    int updateByPrimaryKeySelective(FuncPerm record);

    int updateByPrimaryKey(FuncPerm record);
}