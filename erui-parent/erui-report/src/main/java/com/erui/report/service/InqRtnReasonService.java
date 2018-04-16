package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InqRtnReasonService {
    /*
    * 查询各退回原因 的退回次数 和 询单数
    * */
    List<Map<String,Object>>   selectCountGroupByRtnSeason(Map<String,Object> params);
    /*
    * 查询各地区的退回原因明细
    * */
    List<Map<String,Object>>    selectCountGroupByRtnSeasonAndArea(Date startTime,Date endTime);
    /*
     * 查询各事业部的退回原因明细
     * */
    List<Map<String,Object>>    selectCountGroupByRtnSeasonAndOrg(Date startTime,Date endTime);
}
