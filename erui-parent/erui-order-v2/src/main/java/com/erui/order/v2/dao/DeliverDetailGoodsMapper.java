package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverDetailGoods;
import com.erui.order.v2.model.DeliverDetailGoodsExample;
import com.erui.order.v2.model.DeliverDetailGoodsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverDetailGoodsMapper {
    int countByExample(DeliverDetailGoodsExample example);

    int deleteByExample(DeliverDetailGoodsExample example);

    int deleteByPrimaryKey(DeliverDetailGoodsKey key);

    int insert(DeliverDetailGoods record);

    int insertSelective(DeliverDetailGoods record);

    List<DeliverDetailGoods> selectByExample(DeliverDetailGoodsExample example);

    DeliverDetailGoods selectByPrimaryKey(DeliverDetailGoodsKey key);

    int updateByExampleSelective(@Param("record") DeliverDetailGoods record, @Param("example") DeliverDetailGoodsExample example);

    int updateByExample(@Param("record") DeliverDetailGoods record, @Param("example") DeliverDetailGoodsExample example);

    int updateByPrimaryKeySelective(DeliverDetailGoods record);

    int updateByPrimaryKey(DeliverDetailGoods record);
}