package com.erui.report.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 销售数据统计数据库接口操作类
 */
public interface SalesDataStatisticsMapper {
    /**
     * 查询代理供应商按照国家纬度、时间条件统计总数
     * @param params
     * @return
     */
    List<Map<String,Object>> agencySupplierCountryStatisticsData(Map<String, Object> params);

    /**
     * 查询代理供应商按照事业部纬度、时间条件统计总数
     * @param params
     * @return
     */
    List<Map<String,Object>> agencySupplierOrgStatisticsData(Map<String, Object> params);

    /**
     *  分页查询询报价统计-询价失败列表
     * @param params
     * @return
     */
    List<Map<String,Object>> inquiryFailListByPage(Map<String, Object> params);


    /**
     * 会员总数
     * @param params
     * @return
     */
    List<Map<String,Object>> buyerTotalCountGoupByCountry(Map<String, Object> params);


    /**
     * 会员流失数
     * @param params
     * @return
     */
    List<Map<String,Object>> buyerLossCountGoupByCountry(Map<String, Object> params);


    /**
     * 会员活跃数
     * @param params
     * @return
     */
    List<Map<String,Object>> buyerActiveCountGoupByCountry(Map<String, Object> params);

    /**
     * 事业部报价用时
     * @param params
     * @return
     */
    List<Map<String,Object>> orgQuoteTotalCostTime(Map<String, Object> params);

    /**
     * 总报价数量
     * @param params
     * @return
     */
    List<Map<String,Object>> quoteTotalNumGroupByArea(Map<String, Object> params);

    /**
     * 会员报价总数量
     * @param params
     * @return
     */
    List<Map<String,Object>> memberQuoteNumGroupByArea(Map<String, Object> params);

    /**
     * 总询单数量
     * @param params
     * @return
     */
    List<Map<String,Object>> inquiryTotalNumGroupByArea(Map<String, Object> params);

    /**
     * 会员询单总数量
     * @param params
     * @return
     */
    List<Map<String,Object>> memberInquiryNumGroupByArea(Map<String, Object> params);

    /**
     * 总报价金额
     * @param params
     * @return
     */
    List<Map<String,Object>> quoteTotalAmountGroupByArea(Map<String, Object> params);

    /**
     * 会员报价总金额
     * @param params
     * @return
     */
    List<Map<String,Object>> memberQuoteAmountGroupByArea(Map<String, Object> params);

    /**
     * 订单数据统计 - 整体 - 按地区纬度查询订单的金额和数量
     * @param params
     * @return
     */
    List<Map<String,Object>> orderStatisticsWholeInfoGroupByArea(Map<String, Object> params);

    /**
     * 订单数据统计 - 整体 - 按事业部纬度查询订单的金额和数量
     * @param params
     * @return
     */
    List<Map<String,Object>> orderStatisticsWholeInfoGroupByOrg(Map<String, Object> params);

    /**
     * 订单数据统计 - 整体 - 按国家纬度查询订单的金额和数量
     * @param params
     * @return
     */
    List<Map<String,Object>> orderStatisticsWholeInfoGroupByCountry(Map<String, Object> params);

    /**
     * 订单信息的国家利润率
     * @param params
     * @return
     */
    List<Map<String,Object>> orderStatisticsProfitPercentGroupByCountry(Map<String, Object> params);

    /**
     * 订单信息的地区利润率
     * @param params
     * @return
     */
    List<Map<String,Object>> orderStatisticsProfitPercentGroupByArea(Map<String, Object> params);

    /**
     * 订单信息的事业部利润率
     * @param params
     * @return
     */
    List<Map<String,Object>> orderStatisticsProfitPercentGroupByOrg(Map<String, Object> params);

    /**
     * 订单信息的事业部成单率
     * @param params
     * @return
     */
    List<Map<String,Object>> orderStatisticsMonoRateGroupByOrg(Map<String, Object> params);

    /**
     * 订单信息的地区成单率
     * @param params
     * @return
     */
    List<Map<String,Object>> orderStatisticsMonoRateGroupByArea(Map<String, Object> params);

    /**
     * 订单信息的国家成单率
     * @param params
     * @return
     */
    List<Map<String,Object>> orderStatisticsMonoRateGroupByCountry(Map<String, Object> params);

    /**
     * 订单数据统计-购买力
     * @param params
     *
     * @return
     * {"id":"记录ID","buyer_code":"客户编码","country_bn":"国家编码","countryName":"国家名称","area_bn":"地区编码","areaName":"地区名称","created_at":"采购时间","maxQuotePrice":"单笔订单最大金额","totalQuotePrice":"采购总金额"}
     */
    List<Map<String,Object>> orderInfoPurchasingPower(Map<String, Object> params);

    /**
     * 复购周期
     * @param params
     *
     * @return
     */
    List<Map<String,Object>> orderInfoBuyCycle(Map<String, Object> params);

    /**
     * 全部会员成单总额
     * @param params
     * @return
     */
    List<Map<String,Object>> allMembersContribution(Map<String, Object> params);

    /**
     * 新会员成单总额
     * @param params
     * @return
     */
    List<Map<String,Object>> newMembersContribution(Map<String, Object> params);
}
