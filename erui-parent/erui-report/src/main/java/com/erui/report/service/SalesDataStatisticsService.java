package com.erui.report.service;

import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 销售数据统计业务类
 */
public interface SalesDataStatisticsService {

    /**
     * 按照国家统计代理商总数信息
     *
     * @param params {"startTime":"2018-01-01 00:00:00","endTime":"2018-01-01 23:59:59","sort":"1"}
     *               sort 1:正序/升序   其他：倒序/降序
     * @return {"name":[],counts:[]}
     */
    Map<String, List<Object>> agencySupplierCountryStatisticsData(Map<String, Object> params);

    // 导出按照国家统计代理商总数信息
    HSSFWorkbook exportAgencySupplierCountryStatisticsData(Map<String, Object> params);

    /**
     * 按照事业部统计代理商总数信息
     *
     * @param params {"startTime":"2018-01-01 00:00:00","endTime":"2018-01-01 23:59:59","sort":"1"}
     *               sort 1:正序/升序   其他：倒序/降序
     * @return {"name":[],counts:[]}
     */
    Map<String, List<Object>> agencyOrgStatisticsData(Map<String, Object> params);

    // 导出按照事业部统计代理商总数信息
    HSSFWorkbook exportAgencyOrgStatisticsData(Map<String, Object> params);

    /**
     * 按照地区统计代理商总数信息
     *
     * @param params {"startTime":"2018-01-01 00:00:00","endTime":"2018-01-01 23:59:59","sort":"1"}
     *               sort 1:正序/升序   其他：倒序/降序
     * @return {"name":[],counts:[]}
     */
    Map<String, List<Object>> agencyAreaStatisticsData(Map<String, Object> params);

    // 导出按照地区统计代理商总数信息
    HSSFWorkbook exportAgencyAreaStatisticsData(Map<String, Object> params);


    /**
     * 分页查询询报价统计-询价失败列表
     *
     * @param params
     */
    PageInfo<Map<String, Object>> inquiryFailListByPage(Map<String, Object> params);

    // 导出询报价统计-询价失败列表
    HSSFWorkbook exportInquiryFailList(Map<String, Object> params);

    /**
     * 查找活跃会员统计信息
     *
     * @param params {"startTime":"","endTime":"","sort":"1"}
     *               sort 1:正序/升序   其他：倒序/降序
     * @return
     */
    Map<String, List<Object>> activeMemberStatistics(Map<String, Object> params);

    // 导出活跃会员
    HSSFWorkbook exportActiveMemberStatistics(Map<String, Object> params);

    /**
     * 查找流失会员统计信息
     *
     * @param params {"startTime":"","endTime":"","sort":"1"}
     *               sort 1:正序/升序   其他：倒序/降序
     * @return
     */
    Map<String, List<Object>> lossMemberStatistics(Map<String, Object> params);

    // 导出流失会员
    HSSFWorkbook exportLossMemberStatistics(Map<String, Object> params);

    /**
     * 事业部报价用时信息
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orgQuoteTotalCostTime(Map<String, Object> params);

    // 导出事业部报价用时信息
    HSSFWorkbook exportOrgQuoteTotalCostTime(Map<String, Object> params);

    /**
     * 报价金额按地区统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> quoteAmountGroupArea(Map<String, Object> params);

    // 导出报价金额按地区统计
    HSSFWorkbook exportQuoteAmountGroupArea(Map<String, Object> params);

    /**
     * 询单数量按地区统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> inquiryNumbersGroupArea(Map<String, Object> params);

    // 导出询单数量按地区统计
    HSSFWorkbook exportInquiryNumbersGroupArea(Map<String, Object> params);

    /**
     * 报价数量按地区统计
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> quoteNumbersGroupArea(Map<String, Object> params);

    // 导出报价数量按地区统计
    HSSFWorkbook exportQuoteNumbersGroupArea(Map<String, Object> params);

    /**
     * 订单数据统计-整体-按事业部分析
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderStatisticsWholeInfoGroupByOrg(Map<String, Object> params);

    // 导出订单数据统计-整体-按事业部
    HSSFWorkbook exportOrderStatisticsWholeInfoGroupByOrg(Map<String, Object> params);

    /**
     * 订单数据统计-整体-按地区分析
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderStatisticsWholeInfoGroupByArea(Map<String, Object> params);

    // 导出订单数据统计-整体-按地区
    HSSFWorkbook exportOrderStatisticsWholeInfoGroupByArea(Map<String, Object> params);

    /**
     * 订单数据统计-整体-按国家分析
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderStatisticsWholeInfoGroupByCountry(Map<String, Object> params);

    // 导出订单数据统计-整体-按国家
    HSSFWorkbook exportOrderStatisticsWholeInfoGroupByCountry(Map<String, Object> params);

    /**
     * 订单数据统计-利润-事业部利润率
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderStatisticsProfitPercentGroupByOrg(Map<String, Object> params);

    // 导出订单数据统计-利润-事业部利润率
    HSSFWorkbook exportOrderStatisticsProfitPercentGroupByOrg(Map<String, Object> params);

    /**
     * 订单数据统计-利润-地区利润率
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderStatisticsProfitPercentGroupByArea(Map<String, Object> params);

    // 导出订单数据统计-利润-事业部利润率
    HSSFWorkbook exportOrderStatisticsProfitPercentGroupByArea(Map<String, Object> params);


    /**
     * 订单数据统计-利润-国家利润率
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderStatisticsProfitPercentGroupByCountry(Map<String, Object> params);

    // 导出订单数据统计-利润-国家利润率
    HSSFWorkbook exportOrderStatisticsProfitPercentGroupByCountry(Map<String, Object> params);

    /**
     * 事业部成单率
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderStatisticsMonoRateGroupByOrg(Map<String, Object> params);

    // 导出事业部成单率
    HSSFWorkbook exportOrderStatisticsMonoRateGroupByOrg(Map<String, Object> params);

    /**
     * 地区成单率
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderStatisticsMonoRateGroupByArea(Map<String, Object> params);

    // 导出地区成单率
    HSSFWorkbook exportOrderStatisticsMonoRateGroupByArea(Map<String, Object> params);


    /**
     * 国家成单率
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderStatisticsMonoRateGroupByCountry(Map<String, Object> params);

    // 导出国家成单率
    HSSFWorkbook exportOrderStatisticsMonoRateGroupByCountry(Map<String, Object> params);

    /**
     * 订单数据统计--购买力
     *
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> orderInfoPurchasingPower(Map<String, Object> params);

    // 导出订单数据统计--购买力
    HSSFWorkbook exportOrderInfoPurchasingPower(Map<String, Object> params);

    /**
     * 订单数据统计 -- 复购周期
     *
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> orderInfoBuyCycle(Map<String, Object> params);

    // 导出订单数据统计 -- 复购周期
    HSSFWorkbook exportOrderInfoBuyCycle(Map<String, Object> params);

    /**
     * 订单数据统计 -- 新老会员贡献度
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderInfoMembersContribution(Map<String, Object> params);

    // 导出订单数据统计 -- 新老会员贡献度
    HSSFWorkbook exportOrderInfoMembersContribution(Map<String, Object> params);

    /**
     * 事业部完成率
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderInfoDoneRateGroupbyOrg(Map<String, Object> params);

    /**
     * 地区完成率
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderInfoDoneRateGroupbyArea(Map<String, Object> params);

    /**
     * 国家完成率
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> orderInfoDoneRateGroupbyCountry(Map<String, Object> params);


    /**
     * 事业部完成率/按照天计算公式计算
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> dayOrderInfoDoneRateGroupbyOrg(Map<String, Object> params);

    // 导出事业部完成率
    HSSFWorkbook exportDayOrderInfoDoneRateGroupbyOrg(Map<String, Object> params);

    /**
     * 地区完成率/按照天计算公式计算
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> dayOrderInfoDoneRateGroupbyArea(Map<String, Object> params);

    // 导出地区完成率
    HSSFWorkbook exportDayOrderInfoDoneRateGroupbyArea(Map<String, Object> params);

    /**
     * 国家完成率/按照天计算公式计算
     *
     * @param params
     * @return
     */
    Map<String, List<Object>> dayOrderInfoDoneRateGroupbyCountry(Map<String, Object> params);

    // 导出国家完成率
    HSSFWorkbook exportDayOrderInfoDoneRateGroupbyCountry(Map<String, Object> params);
}
