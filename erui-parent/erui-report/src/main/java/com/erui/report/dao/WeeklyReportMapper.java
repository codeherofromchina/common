package com.erui.report.dao;


import java.util.List;
import java.util.Map;

/**
 * 周报mapper 接口
 */
public interface WeeklyReportMapper {

    /**
     * 根据时间过去各地区的新注册人数 中国算是各独立的地区
     * @param params
     * @return
     */
    List<Map<String,Object>> selectRegisterCountGroupByAreaAndChina(Map<String,Object> params);
}