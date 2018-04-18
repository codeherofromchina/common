package com.erui.report.service;

import com.erui.report.model.CateDetailVo;
import com.erui.report.util.IsOilVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InquirySKUService {

    Integer selectSKUCountByTime(Date startDate, Date endDate, List<String> inquiryNums);

    List<IsOilVo> selectCountGroupByIsOil(Map<String,Object> map);


    /**
     * 获取分类询价商品次数top3
     * @param params
     * @return  proCount : 次数   proCategory ：分类 proProportionl：占比
     */
    List<Map<String, Object>> selectProTop3(Map<String,Object> params);

    /*
     * sku品类明细
     * */
    List<CateDetailVo> selectSKUDetailByCategory(Date startTime, Date endTime);

    /*
     * 查询平台非平台产品数量
     * */
    List<Map<String, Object>> selectCountGroupByIsPlat(Date startTime, Date endTime);

    /**
     * 获取询单商品基本信息根据条件
     * @param params
     * @return  goodsCount : 数量   chainAdd ：环比新增  ，chainRate ：环比率
     */
    Map<String,Object> selectInqGoodsInfoByCondition(Map<String,Object> params);
    /**
     * 获取询单平台分类基本信息根据条件
     * @param params
     * @return  platCount:平台数量 ,notPlatCount:非平台数量, platProportion:平台占比,planChainAdd:平台环比新增, planChainRate:平台环比
     */
    Map<String,Object> selectPlatInfoByCondition(Map<String,Object> params);
    /**
     * 获取询单油气分类基本信息根据条件
     * @param params
     * @return  oil:油气商品次数 ,notOil:非油气次数, oiProportionl:油气占比,oilChainAdd:油气环比新增, chainRate:油气环比
     */
    Map<String,Object> selectIsOilInfoByCondition(Map<String,Object> params);


}