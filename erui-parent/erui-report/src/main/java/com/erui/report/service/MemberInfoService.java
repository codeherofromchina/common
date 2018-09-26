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
}
