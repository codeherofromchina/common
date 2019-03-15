package com.erui.report.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
public interface BuyerStatisticsService {

    /**
     * 查询区域用户列表
     * @param pageNum
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> buyerList(int pageNum, int pageSize, Map<String, String> params);
}
