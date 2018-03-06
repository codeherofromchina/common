package com.erui.report.dao;

import com.erui.report.model.StorageOrganiCount;
import com.erui.report.model.StorageOrganiCountExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StorageOrganiCountMapper {
    int countByExample(StorageOrganiCountExample example);

    int deleteByExample(StorageOrganiCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StorageOrganiCount record);

    int insertSelective(StorageOrganiCount record);

    List<StorageOrganiCount> selectByExample(StorageOrganiCountExample example);

    StorageOrganiCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StorageOrganiCount record, @Param("example") StorageOrganiCountExample example);

    int updateByExample(@Param("record") StorageOrganiCount record, @Param("example") StorageOrganiCountExample example);

    int updateByPrimaryKeySelective(StorageOrganiCount record);

    int updateByPrimaryKey(StorageOrganiCount record);
    
    void truncateTable();
    /**
     * 查询 起始时间
     * @return Date
     */
    Date selectStart();
    /**
     * 查询 结束时间
     * @return Date
     */
    Date selectEnd();
    //查询出库量和入库量
    Map<String,Object> selectEntryAndOutData(Map<String,String> params);
    //查询库存总量
    Map<String,Object> selectTotalStack(Map<String,String> params);
    //查询入库趋势图数据
    List<Map<String,Object>> selectEntryDataGroupByTime(Map<String,String> params);
    //查询出库趋势图数据
    List<Map<String,Object>> selectOutDataGroupByTime(Map<String,String> params);
    //查询各事业部的库存
    List<Map<String,Object>> orgStocks(Map<String,String> params);
}