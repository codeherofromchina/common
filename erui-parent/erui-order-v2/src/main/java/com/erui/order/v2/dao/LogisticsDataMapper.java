package com.erui.order.v2.dao;

import com.erui.order.v2.model.LogisticsData;
import com.erui.order.v2.model.LogisticsDataExample;
import com.erui.order.v2.model.LogisticsDataWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogisticsDataMapper {
    int countByExample(LogisticsDataExample example);

    int deleteByExample(LogisticsDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogisticsDataWithBLOBs record);

    int insertSelective(LogisticsDataWithBLOBs record);

    List<LogisticsDataWithBLOBs> selectByExampleWithBLOBs(LogisticsDataExample example);

    List<LogisticsData> selectByExample(LogisticsDataExample example);

    LogisticsDataWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogisticsDataWithBLOBs record, @Param("example") LogisticsDataExample example);

    int updateByExampleWithBLOBs(@Param("record") LogisticsDataWithBLOBs record, @Param("example") LogisticsDataExample example);

    int updateByExample(@Param("record") LogisticsData record, @Param("example") LogisticsDataExample example);

    int updateByPrimaryKeySelective(LogisticsDataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LogisticsDataWithBLOBs record);

    int updateByPrimaryKey(LogisticsData record);
}