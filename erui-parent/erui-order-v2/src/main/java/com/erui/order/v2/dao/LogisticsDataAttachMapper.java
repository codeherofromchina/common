package com.erui.order.v2.dao;

import com.erui.order.v2.model.LogisticsDataAttach;
import com.erui.order.v2.model.LogisticsDataAttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogisticsDataAttachMapper {
    int countByExample(LogisticsDataAttachExample example);

    int deleteByExample(LogisticsDataAttachExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogisticsDataAttach record);

    int insertSelective(LogisticsDataAttach record);

    List<LogisticsDataAttach> selectByExample(LogisticsDataAttachExample example);

    LogisticsDataAttach selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogisticsDataAttach record, @Param("example") LogisticsDataAttachExample example);

    int updateByExample(@Param("record") LogisticsDataAttach record, @Param("example") LogisticsDataAttachExample example);

    int updateByPrimaryKeySelective(LogisticsDataAttach record);

    int updateByPrimaryKey(LogisticsDataAttach record);
}