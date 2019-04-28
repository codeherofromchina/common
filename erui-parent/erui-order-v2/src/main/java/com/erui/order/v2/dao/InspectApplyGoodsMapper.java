package com.erui.order.v2.dao;

import com.erui.order.v2.model.InspectApplyGoods;
import com.erui.order.v2.model.InspectApplyGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InspectApplyGoodsMapper {
    int countByExample(InspectApplyGoodsExample example);

    int deleteByExample(InspectApplyGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InspectApplyGoods record);

    int insertSelective(InspectApplyGoods record);

    List<InspectApplyGoods> selectByExampleWithBLOBs(InspectApplyGoodsExample example);

    List<InspectApplyGoods> selectByExample(InspectApplyGoodsExample example);

    InspectApplyGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InspectApplyGoods record, @Param("example") InspectApplyGoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") InspectApplyGoods record, @Param("example") InspectApplyGoodsExample example);

    int updateByExample(@Param("record") InspectApplyGoods record, @Param("example") InspectApplyGoodsExample example);

    int updateByPrimaryKeySelective(InspectApplyGoods record);

    int updateByPrimaryKeyWithBLOBs(InspectApplyGoods record);

    int updateByPrimaryKey(InspectApplyGoods record);
}