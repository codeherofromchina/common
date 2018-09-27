package com.erui.report.service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/26.
 */
public interface MemberInfoService {
    /**
     * 按照地区统计会员等级
     * @param params
     * @return
     */
    Map<String,List<Object>> membershipGradeByArea(Map<String, Object> params);

    /**
     * 按照国家统计会员等级
     * @param params
     * @return
     */
    Map<String,List<Object>> membershipGradeByCountry(Map<String, Object> params);

    /**
     * 按照地区统计客户拜访统计
     * @param params
     * @return
     */
    Map<String,List<Object>> visitStatisticsByArea(Map<String, Object> params);

    /**
     * 按照国家统计客户拜访统计
     * @param params
     * @return
     */
    Map<String,List<Object>> visitStatisticsByCountry(Map<String, Object> params);

    /**
     * 按照地区统计客户统计
     * @param params
     * @return
     */
    Map<String,List<Object>> membershipByArea(Map<String, Object> params);

    /**
     * 按照国家统计客户统计
     * @param params
     * @return
     */
    Map<String,List<Object>> membershipByCountry(Map<String, Object> params);

    /**
     * 成单客户信息
     * @param params
     * @return
     */
    List<Map<String,Object>> singleCustomer(Map<String, Object> params);

    /**
     * 按照地区统计会员签约主体
     * @param params
     * @return
     */
    Map<String,List<Object>> signingBodyByArea(Map<String, Object> params);

    /**
     * 按照国家统计会员签约主体
     * @param params
     * @return
     */
    Map<String,List<Object>> signingBodyByCountry(Map<String, Object> params);

    /**
     * 按照事业部统计会员签约主体
     * @param params
     * @return
     */
    Map<String,List<Object>> signingBodyByOrg(Map<String, Object> params);

    /**
     * 人均效能统计 - 地区统计
     * @param params
     * @return
     */
    Map<String,List<Object>> efficiencyByArea(Map<String, Object> params);

    /**
     * 人均效能统计 - 国家统计
     * @param params
     * @return
     */
    Map<String,List<Object>> efficiencyByCountry(Map<String, Object> params);
}
