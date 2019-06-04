package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchSupplierLog;
import com.erui.order.v2.model.PurchSupplierLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchSupplierLogMapper {
    int countByExample(PurchSupplierLogExample example);

    int deleteByExample(PurchSupplierLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PurchSupplierLog record);

    int insertSelective(PurchSupplierLog record);

    List<PurchSupplierLog> selectByExample(PurchSupplierLogExample example);

    PurchSupplierLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PurchSupplierLog record, @Param("example") PurchSupplierLogExample example);

    int updateByExample(@Param("record") PurchSupplierLog record, @Param("example") PurchSupplierLogExample example);

    int updateByPrimaryKeySelective(PurchSupplierLog record);

    int updateByPrimaryKey(PurchSupplierLog record);
}