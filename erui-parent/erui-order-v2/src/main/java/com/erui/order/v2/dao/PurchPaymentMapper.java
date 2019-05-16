package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchPayment;
import com.erui.order.v2.model.PurchPaymentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchPaymentMapper {
    int countByExample(PurchPaymentExample example);

    int deleteByExample(PurchPaymentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PurchPayment record);

    int insertSelective(PurchPayment record);

    List<PurchPayment> selectByExample(PurchPaymentExample example);

    PurchPayment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PurchPayment record, @Param("example") PurchPaymentExample example);

    int updateByExample(@Param("record") PurchPayment record, @Param("example") PurchPaymentExample example);

    int updateByPrimaryKeySelective(PurchPayment record);

    int updateByPrimaryKey(PurchPayment record);
}