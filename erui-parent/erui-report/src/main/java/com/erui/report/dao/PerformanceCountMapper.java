package com.erui.report.dao;


import com.erui.report.model.PerformanceAssign;
import com.erui.report.model.PerformanceCount;

import java.util.List;
import java.util.Map;

public interface PerformanceCountMapper {

    /**
     * 清空表
     */
    void truncateTable();

    /**
     * 新增数据
     */
    void insertSelective(PerformanceCount pc);

    /**
     * 获取日期列表
     * @return
     */
    List<String> selectDateList();

    /**
     * 查询所有大区和国家列表
     * @return
     */
    List<Map<String, String>> selectAllAreaAndCountryList();

    /**
     * 根据条件查询新增的会员
     * @param params
     * @return
     */
    List<Map<String,Object>> selectIncrBuyerByCondition(Map<String,String> params);

    /**
     * 查询人员 销售业绩明细
     * @param params
     * @return
     */
    List<Map<String,Object>> selectObtainerPerformance(Map<String,String> params);

    /**
     * 查询用户负责的国家
     * @param userId
     * @return
     */
    List<String> selectCountryByUserId(Integer userId);

    /**
     * 查询国家的销售人员信息
     * @param country
     * @return
     */
    List<PerformanceAssign> selectSalesmanByCountry(String country);

    /**
     * 查询指定国家和月份的总业绩
     * @param params
     * @return
     */

    double selectTotalPerformanceByCountryAndTime(Map<String,String> params);
}