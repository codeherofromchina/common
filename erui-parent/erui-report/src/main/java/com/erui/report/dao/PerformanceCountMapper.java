package com.erui.report.dao;


import com.erui.report.model.PerformanceCount;

public interface PerformanceCountMapper {

    /**
     * 清空表
     */
    void truncateTable();
    /**
     * 新增数据
     */
    void insertSelective(PerformanceCount pc);

}