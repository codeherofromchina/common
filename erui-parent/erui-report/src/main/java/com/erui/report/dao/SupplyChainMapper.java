package com.erui.report.dao;

import com.erui.report.model.*;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SupplyChainMapper {
    List<Map> selectFinishByDate(SupplyChainExample supplyChainExample);
    int countByExample(SupplyChainExample example);

    int deleteByExample(SupplyChainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupplyChain record);

    int insertSelective(SupplyChain record);

    List<SupplyChain> selectByExample(SupplyChainExample example);

    SupplyChain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupplyChain record, @Param("example") SupplyChainExample example);

    int updateByExample(@Param("record") SupplyChain record, @Param("example") SupplyChainExample example);

    int updateByPrimaryKeySelective(SupplyChain record);

    int updateByPrimaryKey(SupplyChain record);

    List<SuppliyChainOrgVo> selectOrgSuppliyChain();
    List<SuppliyChainItemClassVo> selectItemCalssSuppliyChainByExample(SupplyChainExample example);
    SuppliyChainItemClassVo  selectSuppliyChainByItemClassByExample(SupplyChainExample example);
    List<SuppliyChainCateVo> selectCateSuppliyChain();
}