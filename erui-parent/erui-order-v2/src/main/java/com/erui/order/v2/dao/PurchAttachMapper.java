package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchAttach;
import com.erui.order.v2.model.PurchAttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchAttachMapper {
    int countByExample(PurchAttachExample example);

    int deleteByExample(PurchAttachExample example);

    int insert(PurchAttach record);

    int insertSelective(PurchAttach record);

    List<PurchAttach> selectByExample(PurchAttachExample example);

    int updateByExampleSelective(@Param("record") PurchAttach record, @Param("example") PurchAttachExample example);

    int updateByExample(@Param("record") PurchAttach record, @Param("example") PurchAttachExample example);
}