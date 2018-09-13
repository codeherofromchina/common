package com.erui.report.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 公共信息数据库接口操作类，例如：事业部列表、地区列表、国家列表
 */
public interface CommonMapper {
    /**
     * 国家列表
     * @param params
     * @return
     */
    List<Map<String,Object>> countryList(Map<String, Object> params);

    /**
     * 地区列表
     * @param params
     * @return
     */
    List<Map<String,Object>> areaList(Map<String, Object> params);

    /**
     * 事业部列表
     * @param params
     * @return
     */
    List<Map<String,Object>> orgList(Map<String, Object> params);
}
