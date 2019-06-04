package com.erui.order.v2.dao;

import com.erui.order.v2.model.InstockGoods;
import com.erui.order.v2.model.InstockGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstockGoodsMapper {
    int countByExample(InstockGoodsExample example);

    int deleteByExample(InstockGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InstockGoods record);

    int insertSelective(InstockGoods record);

    List<InstockGoods> selectByExample(InstockGoodsExample example);

    InstockGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InstockGoods record, @Param("example") InstockGoodsExample example);

    int updateByExample(@Param("record") InstockGoods record, @Param("example") InstockGoodsExample example);

    int updateByPrimaryKeySelective(InstockGoods record);

    int updateByPrimaryKey(InstockGoods record);
}