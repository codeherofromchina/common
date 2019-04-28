package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchGoods;
import com.erui.order.v2.model.PurchGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchGoodsMapper {
    int countByExample(PurchGoodsExample example);

    int deleteByExample(PurchGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PurchGoods record);

    int insertSelective(PurchGoods record);

    List<PurchGoods> selectByExample(PurchGoodsExample example);

    PurchGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PurchGoods record, @Param("example") PurchGoodsExample example);

    int updateByExample(@Param("record") PurchGoods record, @Param("example") PurchGoodsExample example);

    int updateByPrimaryKeySelective(PurchGoods record);

    int updateByPrimaryKey(PurchGoods record);
}