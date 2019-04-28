package com.erui.order.v2.dao;

import com.erui.order.v2.model.Order;
import com.erui.order.v2.model.OrderExample;
import com.erui.order.v2.model.OrderWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderWithBLOBs record);

    int insertSelective(OrderWithBLOBs record);

    List<OrderWithBLOBs> selectByExampleWithBLOBs(OrderExample example);

    List<Order> selectByExample(OrderExample example);

    OrderWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderWithBLOBs record, @Param("example") OrderExample example);

    int updateByExampleWithBLOBs(@Param("record") OrderWithBLOBs record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(OrderWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(OrderWithBLOBs record);

    int updateByPrimaryKey(Order record);
}