package com.erui.report.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 销售数据统计数据库接口操作类
 */
public interface SalesDataStatisticsMapper {
    /**
     * 查询代理供应商按照国家纬度、时间条件统计总数
     * @param params
     * @return
     */
    List<Map<String,Object>> agencySupplierCountryStatisticsData(Map<String, Object> params);

    /**
     * 查询代理供应商按照事业部纬度、时间条件统计总数
     * @param params
     * @return
     */
    List<Map<String,Object>> agencySupplierOrgStatisticsData(Map<String, Object> params);

    /**
     *  分页查询询报价统计-询价失败列表
     * @param params
     * @return
     */
    List<Map<String,Object>> inquiryFailListByPage(Map<String, Object> params);


    /**
     * 会员总数
     * @param params
     * @return
     */
    List<Map<String,Object>> buyerTotalCountGoupByCountry(Map<String, Object> params);
}
