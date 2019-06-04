package com.erui.order.v2.dao;

import com.erui.order.v2.model.OrderLog;
import com.erui.order.v2.model.OrderLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderLogMapper {
    int countByExample(OrderLogExample example);

    int deleteByExample(OrderLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderLog record);

    int insertSelective(OrderLog record);

    List<OrderLog> selectByExample(OrderLogExample example);

    OrderLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderLog record, @Param("example") OrderLogExample example);

    int updateByExample(@Param("record") OrderLog record, @Param("example") OrderLogExample example);

    int updateByPrimaryKeySelective(OrderLog record);

    int updateByPrimaryKey(OrderLog record);
}