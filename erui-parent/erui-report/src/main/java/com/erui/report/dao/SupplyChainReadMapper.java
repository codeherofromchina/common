package com.erui.report.dao;

import com.erui.report.model.SupplyChainRead;
import com.erui.report.model.SupplyChainReadExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplyChainReadMapper {
    Date selectStart();
    Date selectEnd();
    int countByExample(SupplyChainReadExample example);

    int deleteByExample(SupplyChainReadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupplyChainRead record);

    int insertSelective(SupplyChainRead record);

    List<SupplyChainRead> selectByExample(SupplyChainReadExample example);

    SupplyChainRead selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupplyChainRead record, @Param("example") SupplyChainReadExample example);

    int updateByExample(@Param("record") SupplyChainRead record, @Param("example") SupplyChainReadExample example);

    int updateByPrimaryKeySelective(SupplyChainRead record);

    int updateByPrimaryKey(SupplyChainRead record);
    SupplyChainRead getSupplyChainReadDataByTime(SupplyChainReadExample example);
}