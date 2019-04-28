package com.erui.order.v2.dao;

import com.erui.order.v2.model.OrderAttach;
import com.erui.order.v2.model.OrderAttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderAttachMapper {
    int countByExample(OrderAttachExample example);

    int deleteByExample(OrderAttachExample example);

    int insert(OrderAttach record);

    int insertSelective(OrderAttach record);

    List<OrderAttach> selectByExample(OrderAttachExample example);

    int updateByExampleSelective(@Param("record") OrderAttach record, @Param("example") OrderAttachExample example);

    int updateByExample(@Param("record") OrderAttach record, @Param("example") OrderAttachExample example);
}