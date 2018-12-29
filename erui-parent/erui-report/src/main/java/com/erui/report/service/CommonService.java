package com.erui.report.service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
public interface CommonService {

    /**
     * 事业部列表
     * @return
     */
    public List<Map<String,Object>> orgList();

    /**
     * 地区列表
     * @return
     */
    public List<Map<String,Object>> areaList();

    /**
     * 国家列表
     * @param  {"areaBn":"地区编码"}
     * @return
     */
    public List<Map<String,Object>> countryList(Map<String,Object> params);

    /**
     * 通过事业部ID查询事业部信息
     * @param orgId
     * @return
     */
    Map<String,Object> findOrgInfoById(Integer orgId);

    /**
     * 通过国家编码查询国家信息
     * @param countryBn
     * @return
     */
    Map<String,Object> findCountryInfoByBn(String countryBn);
}
