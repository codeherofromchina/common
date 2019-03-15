package com.erui.report.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
public interface BuyerStatisticsMapper {

    /**
     * 查询国家用户列表
     * @param params
     * @return
     */
    List<Map<String, Object>> findCountryBuyerList(Map<String, String> params);
}
