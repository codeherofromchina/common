package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverConsignPayment;
import com.erui.order.v2.model.DeliverConsignPaymentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverConsignPaymentMapper {
    int countByExample(DeliverConsignPaymentExample example);

    int deleteByExample(DeliverConsignPaymentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeliverConsignPayment record);

    int insertSelective(DeliverConsignPayment record);

    List<DeliverConsignPayment> selectByExample(DeliverConsignPaymentExample example);

    DeliverConsignPayment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeliverConsignPayment record, @Param("example") DeliverConsignPaymentExample example);

    int updateByExample(@Param("record") DeliverConsignPayment record, @Param("example") DeliverConsignPaymentExample example);

    int updateByPrimaryKeySelective(DeliverConsignPayment record);

    int updateByPrimaryKey(DeliverConsignPayment record);
}