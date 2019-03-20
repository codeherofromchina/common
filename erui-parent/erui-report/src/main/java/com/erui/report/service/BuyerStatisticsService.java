package com.erui.report.service;

import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
public interface BuyerStatisticsService {

    /**
     * 查询注册用户列表
     * @param pageNum
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> registerBuyerList(int pageNum, int pageSize, Map<String, String> params);

    /**
     * 查询会员用户列表
     * @param pageNum
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> membershipBuyerList(int pageNum, int pageSize, Map<String, String> params);

    /**
     * 查询入网用户列表
     * @param pageNum
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> applyBuyerList(int pageNum, int pageSize, Map<String, String> params);

    /**
     * 订单用户统计
     * @param params
     * @return
     */
    Map<String, Object> orderBuyerStatistics(Map<String, Object> params);

    /**
     * 获取订单用户统计的excel
     * @param params
     * @return
     */
    HSSFWorkbook genOrderBuyerStatisticsExcel(Map<String, Object> params);

    /**
     * 业绩统计 - 会员统计
     * @param params
     * @return
     */
    HSSFWorkbook genRegisterBuyerListExcel(Map<String, String> params);

    /**
     * 业绩统计 - 交易会员统计
     * @param params
     * @return
     */
    HSSFWorkbook genMembershipBuyerListExcel(Map<String, String> params);

    /**
     * 业绩统计 - 入网会员统计
     * @param params
     * @return
     */
    HSSFWorkbook genApplyBuyerListExcel(Map<String, String> params);
}
