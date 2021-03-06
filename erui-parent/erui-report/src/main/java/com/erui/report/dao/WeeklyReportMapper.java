package com.erui.report.dao;


import java.util.List;
import java.util.Map;

/**
 * 周报mapper 接口
 */
public interface WeeklyReportMapper {

    /**
     * 根据时间过去各地区的新注册人数 中国算是各独立的地区
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> selectRegisterCountGroupByAreaAndChina(Map<String, Object> params);
    /**
     * 根据时间过去各地区的普通和高级会员数 中国算是各独立的地区
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> selectBuyerCountGroupByAreaAndChina(Map<String, Object> params);
    /**
     * 查询各地区的时间段内普通会员Erui注册数、普通会员ERUI&KERUI注册数、高级会员Erui注册数、高级会员ERUI&KERUI注册数
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> selectBuyerCountDetail(Map<String, Object> params);


    /**
     * 根据时间查询各个地区询单总数量
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> selectInquiryCountWhereTimeGroupByCountry(Map<String, Object> params);


    /**
     * 根据时间查询各个地区的报价数量和金额
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> selectQuoteInfoWhereTimeGroupByCountry(Map<String, Object> params);


    /**
     * 根据时间查询各个地区的订单数量和金额
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> selectOrderInfoWhereTimeGroupByCountry(Map<String, Object> params);


    /**
     * 根据时间查询各个事业部的询单数量信息
     * @param params
     * @return
     */
    List<Map<String,Object>> selectInquiryCountWhereTimeGroupByOrg(Map<String, Object> params);

    /**
     * 根据时间查询各个事业部的报价数量和金额信息
     * @param params
     * @return
     */
    List<Map<String,Object>> selectQuoteInfoWhereTimeGroupByOrg(Map<String, Object> params);

    /**
     * 根据时间查询各个事业部的报价用时
     * @param params
     * @return
     */
    List<Map<String,Object>> selectQuoteTimeWhereTimeGroupByOrg(Map<String, Object> params);

    /**
     * 查询事业部订单的数量和金额
     * @param params
     * @return
     */
    List<Map<String,Object>> selectOrderInfoWhereTimeGroupByOrg(Map<String, Object> params);

    /**
     * 查询事业部的合格供应商数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectSupplierNumWhereTimeGroupByOrg(Map<String, Object> params);

    /**
     * 分别查询spu和sku的数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectSpuAndSkuNumWhereTimeGroupByOrg(Map<String, Object> params);
    /**
     * 各产品组的spu数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectSpuNumWhereTimeGroupByOrg(Map<String, Object> params);
    /**
     * 各产品组的sku数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectSkuNumWhereTimeGroupByOrg(Map<String, Object> params);

    /**
     * 产品线列表
     * @return
     */
    List<Map<String,Object>> selectProductLineList();

    /**
     * 根据时间查询各个地区的总和订单数量
     *
     * @param params
     * @return
     */
    Integer selectInquiryCountWhereTimeGroupByCountryTotal(Map<String, Object> params);


    /**
     * 根据时间查询各个地区的总和报价
     *
     * @param params
     * @return
     */
    Integer selectQuoteInfoWhereTimeGroupByCountryTotal(Map<String, Object> params);

    /**
     * 根据时间查询各个地区的总和订单数量
     *
     * @param params
     * @return
     */
    Integer selectOrderInfoWhereTimeGroupByCountryTotal(Map<String, Object> params);



    List<Map<String, Object>> selectWebStatisticsInfo(Map<String, Object> params);

    List<Map<String, Object>> selectUvCountGroupByArea(Map<String, Object> params);

}