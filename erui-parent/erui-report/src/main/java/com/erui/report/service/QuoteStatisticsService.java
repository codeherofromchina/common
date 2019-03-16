package com.erui.report.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 报价统计实现类
 * Created by wangxiaodan on 2019/3/16.
 */
public interface QuoteStatisticsService {
    /**
     * 报价成单统计分页查询
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> quotePerformance(int pageNum, int pageSize, Map<String, Object> params);
}
