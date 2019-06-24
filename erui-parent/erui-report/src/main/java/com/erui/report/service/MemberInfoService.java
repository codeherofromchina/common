package com.erui.report.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/26.
 */
public interface MemberInfoService {
    /**
     * 按照地区统计会员等级
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> membershipGradeByArea(Map<String, Object> params);

    // 导出按照地区统计会员等级
    HSSFWorkbook exportMembershipGradeByArea(Map<String, Object> params);

    /**
     * 按照国家统计会员等级
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> membershipGradeByCountry(Map<String, Object> params);

    // 导出按照国家统计会员等级
    HSSFWorkbook exportMembershipGradeByCountry(Map<String, Object> params);

    /**
     * 按照事业部统计客户拜访统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> visitStatisticsByOrg(Map<String, Object> params);
    HSSFWorkbook exportVisitStatisticsByOrg(Map<String, Object> params);
    /**
     * 按照地区统计客户拜访统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> visitStatisticsByArea(Map<String, Object> params);

    // 导出按照地区统计客户拜访统计
    HSSFWorkbook exportVisitStatisticsByArea(Map<String, Object> params);

    /**
     * 按照国家统计客户拜访统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> visitStatisticsByCountry(Map<String, Object> params);

    // 导出按照国家统计客户拜访统计
    HSSFWorkbook exportVisitStatisticsByCountry(Map<String, Object> params);


    /**
     * 按照地区统计客户统计
     *
     * @param params
     * @return
     * @see MemberInfoService#statisticsAddMembershipByArea
     */
    @Deprecated
    Map<String, List<Object>> membershipByArea(Map<String, Object> params);

    /**
     * 按照地区统计新增客户
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> statisticsAddMembershipByArea(Map<String, Object> params);

    // 导出按照地区统计客户统计
    HSSFWorkbook exportMembershipByArea(Map<String, Object> params);

    /**
     * 按照国家统计客户统计
     *
     * @param params
     * @return
     * @see MemberInfoService#statisticsAddMembershipByCountry(Map)
     */
    @Deprecated
    Map<String, List<Object>> membershipByCountry(Map<String, Object> params);

    /**
     * 按照国家统计新增客户统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> statisticsAddMembershipByCountry(Map<String, Object> params);

    // 导出按照国家统计客户统计
    HSSFWorkbook exportMembershipByCountry(Map<String, Object> params);

    /**
     * 成单客户信息
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> singleCustomer(Map<String, Object> params);

    // 导出成单客户信息
    HSSFWorkbook exportSingleCustomer(Map<String, Object> params);

    /**
     * 按照地区统计会员签约主体
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> signingBodyByArea(Map<String, Object> params);

    // 导出按照地区统计会员签约主体
    HSSFWorkbook exportSigningBodyByArea(Map<String, Object> params);

    /**
     * 按照国家统计会员签约主体
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> signingBodyByCountry(Map<String, Object> params);

    // 导出按照国家统计会员签约主体
    HSSFWorkbook exportSigningBodyByCountry(Map<String, Object> params);

    /**
     * 按照事业部统计会员签约主体
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> signingBodyByOrg(Map<String, Object> params);

    // 导出按照事业部统计会员签约主体
    HSSFWorkbook exportSigningBodyByOrg(Map<String, Object> params);

    /**
     * 人均效能统计 - 地区统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> efficiencyByArea(Map<String, Object> params);

    // 导出人均效能统计 - 地区统计
    HSSFWorkbook exportEfficiencyByArea(Map<String, Object> params);

    /**
     * 人均效能统计 - 国家统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> efficiencyByCountry(Map<String, Object> params);

    //导出人均效能统计 - 国家统计
    HSSFWorkbook exportEfficiencyByCountry(Map<String, Object> params);
    
    /**
     * 人均效能统计 - 事业部统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> efficiencyByOrg(Map<String, Object> params);

    //导出人均效能统计 - 事业部统计
    HSSFWorkbook exportEfficiencyByOrg(Map<String, Object> params);

}
