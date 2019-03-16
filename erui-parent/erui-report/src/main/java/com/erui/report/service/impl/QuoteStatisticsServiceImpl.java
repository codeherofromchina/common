package com.erui.report.service.impl;

import com.erui.report.dao.QuoteStatisticsMapper;
import com.erui.report.service.QuoteStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/16.
 */
@Service
public class QuoteStatisticsServiceImpl extends BaseService<QuoteStatisticsMapper> implements QuoteStatisticsService {
    @Autowired
    private QuoteStatisticsMapper quoteStatisticsMapper;
    /**
     * 报价成单统计
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> quotePerformance(int pageNum, int pageSize, Map<String, Object> params) {
        // 获取时间段各个用户的询单报出最早时间和报价单个数
        List<Map<String, Object>> quoteMinTimeAndTotalNumList = quoteStatisticsMapper.getQuoteMinTimeAndTotalNum(params);
        // 获取时间段各个用户的项目开始最早日期和订单个数
        List<Map<String, Object>> orderMinTimeAndTotalNumList = quoteStatisticsMapper.getOrderMinTimeAndTotalNum(params);


        // 将list转换为userId为key的map信息
        Map<String, Map<String, Object>> quoteMinTimeAndTotalNumMap  = new HashMap<>();
        Map<String, Map<String, Object>> orderMinTimeAndTotalNumMap  = new HashMap<>();



        // 分页查询获取人
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> acquiringUserList = quoteStatisticsMapper.findAllAcquiringUserList();
        // 数据汇总
        for (Map<String, Object> acquiringUser : acquiringUserList) {
            Long userId = (Long) acquiringUser.get("acquiring_user_id");



        }


        return null;
    }
}
