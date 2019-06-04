package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchContractGoods;
import com.erui.order.v2.model.PurchContractGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchContractGoodsMapper {
    int countByExample(PurchContractGoodsExample example);

    int deleteByExample(PurchContractGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PurchContractGoods record);

    int insertSelective(PurchContractGoods record);

    List<PurchContractGoods> selectByExample(PurchContractGoodsExample example);

    PurchContractGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PurchContractGoods record, @Param("example") PurchContractGoodsExample example);

    int updateByExample(@Param("record") PurchContractGoods record, @Param("example") PurchContractGoodsExample example);

    int updateByPrimaryKeySelective(PurchContractGoods record);

    int updateByPrimaryKey(PurchContractGoods record);
}