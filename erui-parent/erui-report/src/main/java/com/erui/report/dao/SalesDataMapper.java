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
    List<Map<String,Object>> selectInqQuoteTrendData(Map<String,String> params);
    /**
     * 查询各大区和国家的数据明细
     * @param params
     * @return 询单金额 、询单数量、报价数量
     */
    List<Map<String,Object>> selectAreaAndCountryDetail(Map<String,Object> params);

    /**
     * 按照国家分类查询询单数量和询单金额信息
     * @param params
     * @return
     */
    List<Map<String,Object>> selectInqCountAndInqAmountGroupByCountry(Map<String,Object> params);

    /**
     * 按照国家分类查询报价数量和报价金额信息
     * @param params
     * @return
     */
    List<Map<String,Object>> selectQuoteCountAndQuoteAmountGroupByCountry(Map<String,Object> params);

    /**
     *  根据时间查询各分类数据数量在页面求得百分比
     * @param params
     * @return [{category:'钻修井设备',num:6209}...]
     */
    List<Map<String,Object>> selectCategoryNumWhereTime(Map<String,Object> params);




    /**
     * 查询各事业部数据明细
     * @param params
     * @return 询单金额 、询单数量、报价数量、报价用时
     */
    List<Map<String,Object>> selectOrgDetail(Map<String,Object> params);
    /**
     * 查询各分类明细
     * @param params
     * @return 询单金额 、询单数量、报价数量
     */
    List<Map<String,Object>> selectDataGroupByCategory(Map<String,Object> params);

    /**
     * 查询各大区每天的拜访次数
     * @param params
     * @return
     */
    List<Map<String,Object>> selectVisitCountGrupByAreaAndVisitTime(Map<String,String> params);

    /**
     * 查询各大区有多少员工
     * @return
     */
    List<Map<String,Object>> selectEmployeeCountGroupByArea();
}
