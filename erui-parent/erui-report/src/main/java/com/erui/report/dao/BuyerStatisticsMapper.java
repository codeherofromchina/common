package com.erui.report.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
public interface BuyerStatisticsMapper {

    /**
     * 查询国家注册用户列表
     * @param params
     * @return
     */
    List<Map<String, Object>> findCountryRegisterBuyerList(Map<String, String> params);


    /**
     * 查询国家会员用户列表
     * @param params
     * @return
     */
    List<Map<String, Object>> findCountryMembershipBuyerList(Map<String, String> params);



    /**
     * 查询国家入网用户列表
     * @param params
     * @return
     */
    List<Map<String, Object>> findCountryApplyBuyerList(Map<String, String> params);

    /**
     * 会员统计数量
     * @param params
     * @return
     */
    List<Map<String, Object>> orderBuyerStatistics(Map<String, Object> params);
}
