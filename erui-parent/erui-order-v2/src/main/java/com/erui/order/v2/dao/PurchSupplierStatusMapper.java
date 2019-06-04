package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchSupplierStatus;
import com.erui.order.v2.model.PurchSupplierStatusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchSupplierStatusMapper {
    int countByExample(PurchSupplierStatusExample example);

    int deleteByExample(PurchSupplierStatusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PurchSupplierStatus record);

    int insertSelective(PurchSupplierStatus record);

    List<PurchSupplierStatus> selectByExample(PurchSupplierStatusExample example);

    PurchSupplierStatus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PurchSupplierStatus record, @Param("example") PurchSupplierStatusExample example);

    int updateByExample(@Param("record") PurchSupplierStatus record, @Param("example") PurchSupplierStatusExample example);

    int updateByPrimaryKeySelective(PurchSupplierStatus record);

    int updateByPrimaryKey(PurchSupplierStatus record);
}