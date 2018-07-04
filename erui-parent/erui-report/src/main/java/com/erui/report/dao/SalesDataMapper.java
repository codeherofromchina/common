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
    List<Map<String,Object>> selectAreaAndCountryDetail(Map<String,String> params);
    /**
     * 查询各事业部数据明细
     * @param params
     * @return 询单金额 、询单数量、报价数量、报价用时
     */
    List<Map<String,Object>> selectOrgDetail(Map<String,String> params);
    /**
     * 查询各分类明细
     * @param params
     * @return 询单金额 、询单数量、报价数量
     */
    List<Map<String,Object>> selectDataGroupByCategory(Map<String,String> params);

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
