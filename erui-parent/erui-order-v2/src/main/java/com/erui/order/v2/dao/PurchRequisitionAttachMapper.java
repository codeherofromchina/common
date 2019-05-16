package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchRequisitionAttach;
import com.erui.order.v2.model.PurchRequisitionAttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchRequisitionAttachMapper {
    int countByExample(PurchRequisitionAttachExample example);

    int deleteByExample(PurchRequisitionAttachExample example);

    int insert(PurchRequisitionAttach record);

    int insertSelective(PurchRequisitionAttach record);

    List<PurchRequisitionAttach> selectByExample(PurchRequisitionAttachExample example);

    int updateByExampleSelective(@Param("record") PurchRequisitionAttach record, @Param("example") PurchRequisitionAttachExample example);

    int updateByExample(@Param("record") PurchRequisitionAttach record, @Param("example") PurchRequisitionAttachExample example);
}