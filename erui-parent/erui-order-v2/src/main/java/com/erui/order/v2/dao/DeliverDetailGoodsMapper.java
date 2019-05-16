package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverDetailGoodsExample;
import com.erui.order.v2.model.DeliverDetailGoodsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverDetailGoodsMapper {
    int countByExample(DeliverDetailGoodsExample example);

    int deleteByExample(DeliverDetailGoodsExample example);

    int deleteByPrimaryKey(DeliverDetailGoodsKey key);

    int insert(DeliverDetailGoodsKey record);

    int insertSelective(DeliverDetailGoodsKey record);

    List<DeliverDetailGoodsKey> selectByExample(DeliverDetailGoodsExample example);

    int updateByExampleSelective(@Param("record") DeliverDetailGoodsKey record, @Param("example") DeliverDetailGoodsExample example);

    int updateByExample(@Param("record") DeliverDetailGoodsKey record, @Param("example") DeliverDetailGoodsExample example);
}