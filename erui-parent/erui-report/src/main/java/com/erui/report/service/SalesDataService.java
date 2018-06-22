package com.erui.report.service;

import java.util.Map;

/**
 * 销售数据统计 服务
 */
public interface SalesDataService {

    /**
     * 查询指定类型数据的趋势图数据
     * type ：询单金额 、询单数量、报价数量
     * @param params
     * @return
     */
    Map<String,Object>  selectInqQuoteTrendData(Map<String,Object> params);
    /**
     * 查询指定分析类型的各大区数据
     * type ：询单金额 、询单数量、报价数量
     * @param params
     * @return
     */
    Map<String,Object>  selectAreaDetailByType(Map<String,Object> params);
    /**
     * 查询指定分析类型的各事业部数据
     * type ：询单金额 、询单数量、报价数量 、报价用时
     * @param params
     * @return
     */
    Map<String,Object>  selectOrgDetailByType(Map<String,Object> params);
    /**
     * 查询指定分析类型的各分类数据
     * type ：询单金额 、询单数量、报价数量
     * @param params
     * @return
     */
    Map<String,Object>  selectCategoryDetailByType(Map<String,Object> params);

}
