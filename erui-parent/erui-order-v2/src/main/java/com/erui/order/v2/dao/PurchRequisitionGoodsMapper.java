package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchRequisitionGoodsExample;
import com.erui.order.v2.model.PurchRequisitionGoodsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchRequisitionGoodsMapper {
    int countByExample(PurchRequisitionGoodsExample example);

    int deleteByExample(PurchRequisitionGoodsExample example);

    int deleteByPrimaryKey(PurchRequisitionGoodsKey key);

    int insert(PurchRequisitionGoodsKey record);

    int insertSelective(PurchRequisitionGoodsKey record);

    List<PurchRequisitionGoodsKey> selectByExample(PurchRequisitionGoodsExample example);

    int updateByExampleSelective(@Param("record") PurchRequisitionGoodsKey record, @Param("example") PurchRequisitionGoodsExample example);

    int updateByExample(@Param("record") PurchRequisitionGoodsKey record, @Param("example") PurchRequisitionGoodsExample example);
}