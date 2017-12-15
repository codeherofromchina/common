package com.erui.report.service;

import com.erui.report.model.CateDetailVo;
import com.erui.report.util.IsOilVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InquirySKUService {

    Integer selectSKUCountByTime(Date startDate, Date endDate, List<String> inquiryNums);

    List<IsOilVo> selectCountGroupByIsOil(Date startDate, Date endDate, List<String> inquiryNums);

    /*
     * 查询产品Top3
     * */
    public List<Map<String, Object>> selectProTop3(Date startTime, Date endTime, List<String> numList);

    /*
     * sku品类明细
     * */
    List<CateDetailVo> selectSKUDetailByCategory(Date startTime, Date endTime);

    /*
     * 查询平台非平台产品数量
     * */
    List<Map<String, Object>> selectCountGroupByIsPlat(Date startTime, Date endTime);




}