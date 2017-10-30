package com.erui.report.dao;

import com.erui.report.model.OrderEntryCount;
import com.erui.report.model.OrderEntryCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderEntryCountMapper {
    int countByExample(OrderEntryCountExample example);

    int deleteByExample(OrderEntryCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderEntryCount record);

    int insertSelective(OrderEntryCount record);

    List<OrderEntryCount> selectByExample(OrderEntryCountExample example);

    OrderEntryCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderEntryCount record, @Param("example") OrderEntryCountExample example);

    int updateByExample(@Param("record") OrderEntryCount record, @Param("example") OrderEntryCountExample example);

    int updateByPrimaryKeySelective(OrderEntryCount record);

    int updateByPrimaryKey(OrderEntryCount record);
    
    void truncateTable();
}