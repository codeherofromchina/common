package com.erui.report.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface WeeklyReportService {

    /**
     * 查询各地区时间段内新注册用户数量
     * @param params
     * @return
     */
    Map<String, Object> selectBuyerRegistCountGroupByArea(Map<String,Object> params);
    /**
     * 查询各地区时间段内会员数量
     * @param params
     * @return
     */
    Map<String, Object> selectBuyerCountGroupByArea(Map<String,Object> params);

    /**
     * 查询各地区时间段内询单数
     * @param params    {"chainStartTime":"2018/07/03","startTime":"2018/07/09","endTime":"2018/07/15"}
     *                  chainStartTime:上周开始时间
     *                  startTime：本周开始时间
     *                  endTime:本周结束时间
     * @return
     */
    Map<String,Object> selectInqNumGroupByArea(Map<String, Object> params);

    /**
     * 查询各个地区时间段内的报价数量和金额信息
     * @param params
     * @return
     */
    Map<String,Object> selectQuoteInfoGroupByArea(Map<String, Object> params);

    /**
     * 查询各个地区时间段内的订单数量和金额信息
     * @param params
     * @return
     */
    Map<String,Object> selectOrderInfoGroupByArea(Map<String, Object> params);

    /**
     * 查询各个事业部时间端内的询单数量
     * @param params
     * @return
     */
    Map<String,Object> selectInqNumGroupByOrg(Map<String, Object> params);

    /**
     * 查询各个事业部时间段的报价数量、金额（万美元）、用时信息
     * @param params
     * @return
     */
    Map<String,Object> selectQuoteInfoGroupByOrg(Map<String, Object> params);

    /**
     * 查询各个事业部的报价用时
     * @param params
     * @return
     */
    Map<String,Object> selectQuoteTimeInfoGroupByOrg(Map<String, Object> params);

    /**
     * 查询各个事业部的订单数量和金额
     * @param params
     * @return
     */
    Map<String,Object> selectOrderInfoGroupByOrg(Map<String, Object> params);

    /**
     * 查询各个事业部供应商数量信息
     * @param params
     * @return
     */
    Map<String,Object> selectSupplierNumInfoGroupByOrg(Map<String, Object> params);

    /**
     *
     * @param params
     * @return
     */
    Map<String,Object> selectSpuAndSkuNumInfoGroupByOrg(Map<String, Object> params);

    /**
     * 生成周报-周报页签中的excel数据
     * @param params
     * @return
     */
    HSSFWorkbook genAreaDetailExcel(Map<String, Object> params);

    /**
     * 生成周报-事业部页签中的excel数据
     * @param params
     * @return
     */
    HSSFWorkbook genOrgDetailExcel(Map<String, Object> params);
}
