package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchContract;
import com.erui.order.v2.model.PurchContractExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchContractMapper {
    int countByExample(PurchContractExample example);

    int deleteByExample(PurchContractExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PurchContract record);

    int insertSelective(PurchContract record);

    List<PurchContract> selectByExampleWithBLOBs(PurchContractExample example);

    List<PurchContract> selectByExample(PurchContractExample example);

    PurchContract selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PurchContract record, @Param("example") PurchContractExample example);

    int updateByExampleWithBLOBs(@Param("record") PurchContract record, @Param("example") PurchContractExample example);

    int updateByExample(@Param("record") PurchContract record, @Param("example") PurchContractExample example);

    int updateByPrimaryKeySelective(PurchContract record);

    int updateByPrimaryKeyWithBLOBs(PurchContract record);

    int updateByPrimaryKey(PurchContract record);
}