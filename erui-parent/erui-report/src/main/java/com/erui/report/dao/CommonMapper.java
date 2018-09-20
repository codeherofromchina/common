package com.erui.report.dao;

import org.apache.ibatis.annotations.Param;

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
     * @return
     */
    List<Map<String,Object>> areaList();

    /**
     * 事业部列表
     * @return
     */
    List<Map<String,Object>> orgList();

    /**
     * 查询事业部信息
     * @param orgId
     * @return {orgId:1,orgName:'事业部名称'}
     */
    Map<String,Object> findOrgInfoById(@Param("orgId") Integer orgId);

    /**
     * 查询国家信息
     * @param countryBn
     * @return {countryBn:'国家编码',countryName:'国家名称',areaBn:'地区编码',areaName:'地区名称'}
     */
    Map<String,Object> findCountryInfoByBn(@Param("countryBn") String countryBn);
}
