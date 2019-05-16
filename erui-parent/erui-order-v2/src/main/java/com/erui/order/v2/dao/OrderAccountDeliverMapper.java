package com.erui.order.v2.dao;

import com.erui.order.v2.model.OrderAccountDeliver;
import com.erui.order.v2.model.OrderAccountDeliverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderAccountDeliverMapper {
    int countByExample(OrderAccountDeliverExample example);

    int deleteByExample(OrderAccountDeliverExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderAccountDeliver record);

    int insertSelective(OrderAccountDeliver record);

    List<OrderAccountDeliver> selectByExample(OrderAccountDeliverExample example);

    OrderAccountDeliver selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderAccountDeliver record, @Param("example") OrderAccountDeliverExample example);

    int updateByExample(@Param("record") OrderAccountDeliver record, @Param("example") OrderAccountDeliverExample example);

    int updateByPrimaryKeySelective(OrderAccountDeliver record);

    int updateByPrimaryKey(OrderAccountDeliver record);
}