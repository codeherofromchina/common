package com.erui.report.dao;

import java.util.List;
import java.util.Map;

public interface SalesDataMapper {

    /**
     * 查询销售数据的趋势图
     * type ：询单金额 、询单数量、报价数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectInqQuoteTrendData(Map<String,Object> params);
}
