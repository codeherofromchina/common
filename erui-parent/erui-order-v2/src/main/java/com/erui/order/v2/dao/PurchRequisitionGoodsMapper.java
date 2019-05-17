package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchRequisitionGoods;
import com.erui.order.v2.model.PurchRequisitionGoodsExample;
import com.erui.order.v2.model.PurchRequisitionGoodsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchRequisitionGoodsMapper {
    int countByExample(PurchRequisitionGoodsExample example);

    int deleteByExample(PurchRequisitionGoodsExample example);

    int deleteByPrimaryKey(PurchRequisitionGoodsKey key);

    int insert(PurchRequisitionGoods record);

    int insertSelective(PurchRequisitionGoods record);

    List<PurchRequisitionGoods> selectByExample(PurchRequisitionGoodsExample example);

    PurchRequisitionGoods selectByPrimaryKey(PurchRequisitionGoodsKey key);

    int updateByExampleSelective(@Param("record") PurchRequisitionGoods record, @Param("example") PurchRequisitionGoodsExample example);

    int updateByExample(@Param("record") PurchRequisitionGoods record, @Param("example") PurchRequisitionGoodsExample example);

    int updateByPrimaryKeySelective(PurchRequisitionGoods record);

    int updateByPrimaryKey(PurchRequisitionGoods record);
}