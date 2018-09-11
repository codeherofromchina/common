package com.erui.report.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 销售数据统计业务类
 */
public interface SalesDataStatisticsService {

    /**
     * 按照国家统计代理商总数信息
     * @param params {"startTime":"2018-01-01 00:00:00","endTime":"2018-01-01 23:59:59","sort":"1"}
     *                  sort 1:正序/升序   其他：倒序/降序
     * @return
     * {"name":[],counts:[]}
     */
    Map<String,List<Object>> agencySupplierCountryStatisticsData(Map<String, Object> params);

    /**
     * 按照事业部统计代理商总数信息
     * @param params {"startTime":"2018-01-01 00:00:00","endTime":"2018-01-01 23:59:59","sort":"1"}
     *                  sort 1:正序/升序   其他：倒序/降序
     * @return
     *  {"name":[],counts:[]}
     */
    Map<String,List<Object>> agencyOrgStatisticsData(Map<String, Object> params);

    /**
     * 分页查询询报价统计-询价失败列表
     * @param params
     */
    PageInfo<Map<String,Object>> inquiryFailListByPage(Map<String, Object> params);

    /**
     * 查找活跃会员统计信息
     * @param params {"startTime":"","endTime":"","sort":"1"}
     *          sort 1:正序/升序   其他：倒序/降序
     * @return
     */
    Map<String,List<Object>> activeMemberStatistics(Map<String, Object> params);

    /**
     * 查找流失会员统计信息
     * @param params {"startTime":"","endTime":"","sort":"1"}
     *          sort 1:正序/升序   其他：倒序/降序
     * @return
     */
    Map<String,List<Object>> lossMemberStatistics(Map<String, Object> params);

    /**
     * 事业部报价用时信息
     * @param params
     * @return
     */
    Map<String,List<Object>> orgQuoteTotalCostTime(Map<String, Object> params);

    /**
     *报价金额按事业部统计
     * @param params
     * @return
     */
    Map<String,List<Object>> quoteAmountGroupOrg(Map<String, Object> params);

    /**
     * 询单数量按事业部统计
     * @param params
     * @return
     */
    Map<String,List<Object>> inquiryNumbersGroupOrg(Map<String, Object> params);

    /**
     * 报价数量按事业部统计
     * @param params
     * @return
     */
    Map<String,List<Object>> quoteNumbersGroupOrg(Map<String, Object> params);

    /**
     * 订单数据统计-整体-按事业部分析
     * @param params
     * @return
     */
    Map<String,List<Object>> orderStatisticsWholeInfoGroupByOrg(Map<String, Object> params);

    /**
     * 订单数据统计-整体-按地区分析
     * @param params
     * @return
     */
    Map<String,List<Object>> orderStatisticsWholeInfoGroupByArea(Map<String, Object> params);

    /**
     * 订单数据统计-整体-按国家分析
     * @param params
     * @return
     */
    Map<String,List<Object>> orderStatisticsWholeInfoGroupByCountry(Map<String, Object> params);

    /**
     * 订单数据统计-利润-事业部利润率
     * @param params
     * @return
     */
    Map<String,List<Object>> orderStatisticsProfitPercentGroupByOrg(Map<String, Object> params);

    /**
     * 订单数据统计-利润-地区利润率
     * @param params
     * @return
     */
    Map<String,List<Object>> orderStatisticsProfitPercentGroupByArea(Map<String, Object> params);


    /**
     * 订单数据统计-利润-国家利润率
     * @param params
     * @return
     */
    Map<String,List<Object>> orderStatisticsProfitPercentGroupByCountry(Map<String, Object> params);

    /**
     * 事业部成单率
     * @param params
     * @return
     */
    Map<String,List<Object>> orderStatisticsMonoRateGroupByOrg(Map<String, Object> params);

    /**
     * 地区成单率
     * @param params
     * @return
     */
    Map<String,List<Object>> orderStatisticsMonoRateGroupByArea(Map<String, Object> params);


    /**
     * 国家成单率
     * @param params
     * @return
     */
    Map<String,List<Object>> orderStatisticsMonoRateGroupByCountry(Map<String, Object> params);

    /**
     * 订单数据统计--购买力
     * @param params
     * @return
     */
    PageInfo<Map<String,Object>> orderInfoPurchasingPower(Map<String, Object> params);
}
