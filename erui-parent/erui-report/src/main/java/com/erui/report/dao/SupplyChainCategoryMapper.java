package com.erui.report.dao;

import com.erui.report.model.SupplyChainCategory;
import com.erui.report.model.SupplyChainCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplyChainCategoryMapper {
    int countByExample(SupplyChainCategoryExample example);

    int deleteByExample(SupplyChainCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupplyChainCategory record);

    int insertSelective(SupplyChainCategory record);

    List<SupplyChainCategory> selectByExample(SupplyChainCategoryExample example);

    SupplyChainCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupplyChainCategory record, @Param("example") SupplyChainCategoryExample example);

    int updateByExample(@Param("record") SupplyChainCategory record, @Param("example") SupplyChainCategoryExample example);

    int updateByPrimaryKeySelective(SupplyChainCategory record);

    int updateByPrimaryKey(SupplyChainCategory record);
}