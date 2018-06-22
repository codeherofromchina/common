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
}
