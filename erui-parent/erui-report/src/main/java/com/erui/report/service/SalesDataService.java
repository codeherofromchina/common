package com.erui.report.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Map;

/**
 * 销售数据统计 服务
 */
public interface SalesDataService {

    /**
     * 查询指定类型数据的趋势图数据
     * type ：询单金额 、询单数量、报价数量
     *
     * @param params
     * @return
     */
    Map<String, Object> selectInqQuoteTrendData(Map<String, String> params) throws Exception;

    /**
     * 查询指定分析类型的各大区数据
     * type ：询单金额 、询单数量、报价数量
     *
     * @param params
     * @return
     */
    Map<String, Object> selectAreaDetailByType(Map<String, Object> params);

    /**
     * 询单数量或询单金额
     * analyzeType :询单数量、询单金额
     *
     * @param params
     * @return
     */
    Map<String, Object> selectInquiryInfoByCountry(Map<String, Object> params);

    /**
     * 报价数量或报价金额
     * analyzeType :报价数量、报价金额
     *
     * @param params
     * @return
     */
    Map<String, Object> selectQuoteInfoByCountry(Map<String, Object> params);

    /**
     * 查询指定分析类型的各事业部数据
     * type ：询单金额 、询单数量、报价数量 、报价用时
     *
     * @param params
     * @return
     */
    Map<String, Object> selectOrgDetailByType(Map<String, Object> params);


    /**
     * 查询各个品类的询单数量
     *
     * @param params
     * @return
     */
    Map<String, Object> selectCategoryInquiryNum(Map<String, Object> params);

    /**
     * 查询各个品类的询单金额
     *
     * @param params
     * @return
     */
    Map<String, Object> selectCategoryInquiryAmount(Map<String, Object> params);

    /**
     * 查询各个品类的报价数量
     *
     * @param params
     * @return
     */
    Map<String, Object> selectCategoryQuoteNum(Map<String, Object> params);

    /**
     * 查询指定分析类型的各分类数据
     * type ：询单金额 、询单数量、报价数量
     *
     * @param params
     * @return
     */
    Map<String, Object> selectCategoryDetailByType(Map<String, Object> params);

    /**
     * 客户拜访明细
     *
     * @param params
     * @return
     */
    Map<String, Object> selectCustomerVisitDetail(Map<String, String> params) throws Exception;

    /**
     * 导出 分类明细数据
     *
     * @param params
     * @return
     */
    XSSFWorkbook exportCategoryDetail(Map<String, Object> params);

    /**
     * 导出  事业部明细数据
     *
     * @param params
     * @return
     */
    XSSFWorkbook exportOrgDetail(Map<String, Object> params);

    /**
     * 导出  事业部大区数据
     *
     * @param params
     * @return
     */
    XSSFWorkbook exportAreaDetail(Map<String, Object> params);
}
