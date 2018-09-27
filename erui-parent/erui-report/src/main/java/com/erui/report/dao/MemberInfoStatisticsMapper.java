package com.erui.report.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 会员数据统计数据库接口操作类
 */
public interface MemberInfoStatisticsMapper {
    /**
     * 按地区统计会员等级（普通会员、高级会员）
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> membershipGradeByArea(Map<String, Object> params);

    /**
     * 按国家统计会员等级（普通会员、高级会员）
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> membershipGradeByCountry(Map<String, Object> params);

    /**
     * 按照地区统计客户拜访统计
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> visitStatisticsByArea(Map<String, Object> params);

    /**
     * 按照国家统计客户拜访统计
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> visitStatisticsByCountry(Map<String, Object> params);

    /**
     * 按照地区统计客户统计
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> membershipByArea(Map<String, Object> params);

    /**
     * 按照国家统计客户统计
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> membershipByCountry(Map<String, Object> params);

    /**
     * 成单客户信息
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> singleCustomer(Map<String, Object> params);

    /**
     * 按照地区统计会员签约主体
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> signingBodyByArea(Map<String, Object> params);

    /**
     * 按照国家统计会员签约主体
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> signingBodyByCountry(Map<String, Object> params);

    /**
     * 按照事业部统计会员签约主体
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> signingBodyByOrg(Map<String, Object> params);

    /**
     * 按地区获取订单的总金额
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> orderTotalPriceByArea(Map<String, Object> params);

    /**
     * 按国家获取订单的总金额
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> orderTotalPriceByCountry(Map<String, Object> params);
}
