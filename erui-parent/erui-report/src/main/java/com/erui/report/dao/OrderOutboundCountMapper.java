package com.erui.report.dao;

import com.erui.report.model.OrderOutboundCount;
import com.erui.report.model.OrderOutboundCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderOutboundCountMapper {
    int countByExample(OrderOutboundCountExample example);

    int deleteByExample(OrderOutboundCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderOutboundCount record);

    int insertSelective(OrderOutboundCount record);

    List<OrderOutboundCount> selectByExample(OrderOutboundCountExample example);

    OrderOutboundCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderOutboundCount record, @Param("example") OrderOutboundCountExample example);

    int updateByExample(@Param("record") OrderOutboundCount record, @Param("example") OrderOutboundCountExample example);

    int updateByPrimaryKeySelective(OrderOutboundCount record);

    int updateByPrimaryKey(OrderOutboundCount record);
}