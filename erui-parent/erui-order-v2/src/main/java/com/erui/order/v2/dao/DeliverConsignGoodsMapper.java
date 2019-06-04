package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverConsignGoods;
import com.erui.order.v2.model.DeliverConsignGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverConsignGoodsMapper {
    int countByExample(DeliverConsignGoodsExample example);

    int deleteByExample(DeliverConsignGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeliverConsignGoods record);

    int insertSelective(DeliverConsignGoods record);

    List<DeliverConsignGoods> selectByExample(DeliverConsignGoodsExample example);

    DeliverConsignGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeliverConsignGoods record, @Param("example") DeliverConsignGoodsExample example);

    int updateByExample(@Param("record") DeliverConsignGoods record, @Param("example") DeliverConsignGoodsExample example);

    int updateByPrimaryKeySelective(DeliverConsignGoods record);

    int updateByPrimaryKey(DeliverConsignGoods record);
}