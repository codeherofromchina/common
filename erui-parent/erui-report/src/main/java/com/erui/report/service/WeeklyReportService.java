package com.erui.report.service;

import java.util.List;
import java.util.Map;

public interface WeeklyReportService {

    /**
     * 查询各地区时间段内新注册用户数量
     * @param params
     * @return
     */
    Map<String, Object> selectBuyerRegistCountGroupByArea(Map<String,Object> params);
    /**
     * 查询各地区时间段内会员数量
     * @param params
     * @return
     */
    Map<String, Object> selectBuyerCountGroupByArea(Map<String,Object> params);
}
