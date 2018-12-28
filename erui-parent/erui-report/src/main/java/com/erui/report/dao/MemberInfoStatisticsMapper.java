package com.erui.report.dao;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
     * 按照事业部统计客户拜访统计
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> visitStatisticsByOrg(Map<String, Object> params);

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
     * 按照地区查找新增客户
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> findAllNewMembershipByArea(Map<String, Object> params);
    List<Map<String, Object>> findAllNewMembershipByCountry(Map<String, Object> params);

    /**
     * 查找指定地区和客户的成单金额
     *
     * @param params
     * @param regionBn
     * @param crmCodes
     * @return
     */
    BigDecimal findAmountByAreaAndCrm(@Param("params") Map<String, Object> params, @Param("region") String regionBn, @Param("crmCodes") List<String> crmCodes);
    BigDecimal findAmountByCountryAndCrm(@Param("params") Map<String, Object> params, @Param("country") String countryBn, @Param("crmCodes") List<String> crmCodes);

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
