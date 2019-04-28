package com.erui.order.v2.dao;

import com.erui.order.v2.model.OrderAccount;
import com.erui.order.v2.model.OrderAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderAccountMapper {
    int countByExample(OrderAccountExample example);

    int deleteByExample(OrderAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderAccount record);

    int insertSelective(OrderAccount record);

    List<OrderAccount> selectByExample(OrderAccountExample example);

    OrderAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderAccount record, @Param("example") OrderAccountExample example);

    int updateByExample(@Param("record") OrderAccount record, @Param("example") OrderAccountExample example);

    int updateByPrimaryKeySelective(OrderAccount record);

    int updateByPrimaryKey(OrderAccount record);
}