package com.erui.order.v2.dao;

import com.erui.order.v2.model.OrderPayment;
import com.erui.order.v2.model.OrderPaymentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderPaymentMapper {
    int countByExample(OrderPaymentExample example);

    int deleteByExample(OrderPaymentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderPayment record);

    int insertSelective(OrderPayment record);

    List<OrderPayment> selectByExample(OrderPaymentExample example);

    OrderPayment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderPayment record, @Param("example") OrderPaymentExample example);

    int updateByExample(@Param("record") OrderPayment record, @Param("example") OrderPaymentExample example);

    int updateByPrimaryKeySelective(OrderPayment record);

    int updateByPrimaryKey(OrderPayment record);
}