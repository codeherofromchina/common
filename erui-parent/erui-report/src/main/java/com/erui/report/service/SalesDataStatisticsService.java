package com.erui.report.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 销售数据统计业务类
 */
public interface SalesDataStatisticsService {

    /**
     * 按照国家统计代理商总数信息
     * @param params {"startTime":"2018-01-01 00:00:00","endTime":"2018-01-01 23:59:59","sort":"1"}
     *                  sort 1:正序/升序   其他：倒序/降序
     * @return
     * {"name":[],counts:[]}
     */
    Map<String,List<Object>> agencySupplierCountryStatisticsData(Map<String, Object> params);

    /**
     * 按照事业部统计代理商总数信息
     * @param params {"startTime":"2018-01-01 00:00:00","endTime":"2018-01-01 23:59:59","sort":"1"}
     *                  sort 1:正序/升序   其他：倒序/降序
     * @return
     *  {"name":[],counts:[]}
     */
    Map<String,List<Object>> agencyOrgStatisticsData(Map<String, Object> params);

    /**
     * 分页查询询报价统计-询价失败列表
     * @param params
     */
    PageInfo<Map<String,Object>> inquiryFailListByPage(Map<String, Object> params);

    /**
     * 查找活跃会员统计信息
     * @param params {"startTime":"","endTime":"","sort":"1"}
     *          sort 1:正序/升序   其他：倒序/降序
     * @return
     */
    Map<String,List<Object>> activeMemberStatistics(Map<String, Object> params);

    /**
     * 查找流失会员统计信息
     * @param params {"startTime":"","endTime":"","sort":"1"}
     *          sort 1:正序/升序   其他：倒序/降序
     * @return
     */
    Map<String,List<Object>> lossMemberStatistics(Map<String, Object> params);

    /**
     * 事业部报价用时信息
     * @param params
     * @return
     */
    Map<String,List<Object>> orgQuoteTotalCostTime(Map<String, Object> params);
}
