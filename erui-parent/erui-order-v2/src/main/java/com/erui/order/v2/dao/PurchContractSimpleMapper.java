package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchContractSimple;
import com.erui.order.v2.model.PurchContractSimpleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchContractSimpleMapper {
    int countByExample(PurchContractSimpleExample example);

    int deleteByExample(PurchContractSimpleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PurchContractSimple record);

    int insertSelective(PurchContractSimple record);

    List<PurchContractSimple> selectByExample(PurchContractSimpleExample example);

    PurchContractSimple selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PurchContractSimple record, @Param("example") PurchContractSimpleExample example);

    int updateByExample(@Param("record") PurchContractSimple record, @Param("example") PurchContractSimpleExample example);

    int updateByPrimaryKeySelective(PurchContractSimple record);

    int updateByPrimaryKey(PurchContractSimple record);
}