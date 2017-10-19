package com.erui.report.dao;

import com.erui.report.model.OrderCount;
import com.erui.report.model.OrderCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderCountMapper {
    int countByExample(OrderCountExample example);

    int deleteByExample(OrderCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderCount record);

    int insertSelective(OrderCount record);

    List<OrderCount> selectByExample(OrderCountExample example);

    OrderCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderCount record, @Param("example") OrderCountExample example);

    int updateByExample(@Param("record") OrderCount record, @Param("example") OrderCountExample example);

    int updateByPrimaryKeySelective(OrderCount record);

    int updateByPrimaryKey(OrderCount record);
}