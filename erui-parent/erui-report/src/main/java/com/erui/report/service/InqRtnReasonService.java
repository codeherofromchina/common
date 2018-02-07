package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InqRtnReasonService {
    /*
    * 查询退回原因明细
    * */
    List<Map<String,Object>>   selectCountGroupByRtnSeason(Date startTime,Date endTime,Object area,Object org);
    /*
    * 查询各地区的退回原因明细
    * */
    List<Map<String,Object>>    selectCountGroupByRtnSeasonAndArea(Date startTime,Date endTime);
    /*
     * 查询各事业部的退回原因明细
     * */
    List<Map<String,Object>>    selectCountGroupByRtnSeasonAndOrg(Date startTime,Date endTime);
}
