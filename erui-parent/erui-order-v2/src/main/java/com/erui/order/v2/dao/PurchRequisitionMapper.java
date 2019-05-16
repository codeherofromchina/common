package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchRequisition;
import com.erui.order.v2.model.PurchRequisitionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchRequisitionMapper {
    int countByExample(PurchRequisitionExample example);

    int deleteByExample(PurchRequisitionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PurchRequisition record);

    int insertSelective(PurchRequisition record);

    List<PurchRequisition> selectByExampleWithBLOBs(PurchRequisitionExample example);

    List<PurchRequisition> selectByExample(PurchRequisitionExample example);

    PurchRequisition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PurchRequisition record, @Param("example") PurchRequisitionExample example);

    int updateByExampleWithBLOBs(@Param("record") PurchRequisition record, @Param("example") PurchRequisitionExample example);

    int updateByExample(@Param("record") PurchRequisition record, @Param("example") PurchRequisitionExample example);

    int updateByPrimaryKeySelective(PurchRequisition record);

    int updateByPrimaryKeyWithBLOBs(PurchRequisition record);

    int updateByPrimaryKey(PurchRequisition record);
}