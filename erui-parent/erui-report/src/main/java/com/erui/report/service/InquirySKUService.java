package com.erui.report.service;

import com.erui.report.model.InquirySku;
import com.erui.report.util.IsOilVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InquirySKUService {

    Integer selectSKUCountByTime(Date startDate,Date endDate);
    List<IsOilVo> selectCountGroupByIsOil(Date startDate, Date endDate);
    /*
     * 查询产品Top3
     * */
    public List<Map<String,Object>> selectProTop3(Map<String,Object>params);

}