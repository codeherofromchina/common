package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.QuoteStatisticsMapper;
import com.erui.report.service.QuoteStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    public PageInfo<Map<String, Object>> quotePerformanceByPage(int pageNum, int pageSize, Map<String, Object> params) {
        // 获取时间段各个用户的询单报出最早时间和报价单个数
        List<Map<String, Object>> quoteMinTimeAndTotalNumList = quoteStatisticsMapper.getQuoteMinTimeAndTotalNum(params);
        // 获取时间段各个用户的项目开始最早日期和订单个数
        List<Map<String, Object>> orderMinTimeAndTotalNumList = quoteStatisticsMapper.getOrderMinTimeAndTotalNum(params);

        // 将list转换为userId为key的map信息
        Map<Long, Map<String, Object>> quoteMinTimeAndTotalNumMap = null;
        Map<Long, Map<String, Object>> orderMinTimeAndTotalNumMap  = new HashMap<>();
        if (quoteMinTimeAndTotalNumList != null && quoteMinTimeAndTotalNumList.size() > 0) {
            quoteMinTimeAndTotalNumMap = quoteMinTimeAndTotalNumList.stream().collect(Collectors.toMap(vo -> (Long) vo.get("acquiring_user_id"), vo -> vo));
        } else {
            quoteMinTimeAndTotalNumMap = new HashMap<>();
        }
        if (orderMinTimeAndTotalNumList != null && orderMinTimeAndTotalNumList.size() > 0) {
            orderMinTimeAndTotalNumMap = orderMinTimeAndTotalNumList.stream().collect(Collectors.toMap(vo -> (Long) vo.get("acquiring_user_id"), vo -> vo));
        } else {
            orderMinTimeAndTotalNumMap = new HashMap<>();
        }

        Set<Long> acquireUserIdSet = new HashSet<>();
        acquireUserIdSet.addAll(quoteMinTimeAndTotalNumMap.keySet());
        acquireUserIdSet.addAll(orderMinTimeAndTotalNumMap.keySet());

        // 分页查询获取人
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> acquiringUserList = quoteStatisticsMapper.findAcquiringUserByUserIdSet(new ArrayList(acquireUserIdSet));
        // 数据汇总
        for (Map<String, Object> acquiringUser : acquiringUserList) {
            Long userId = (Long) acquiringUser.get("acquiring_user_id");
            String quoteTime = "";
            int quoteNum = 0;
            String orderTime = "";
            int orderNum = 0;
            if (quoteMinTimeAndTotalNumMap.containsKey(userId)) {
                Map<String, Object> tmpMap01 = quoteMinTimeAndTotalNumMap.get(userId);
                Date d = (Date) tmpMap01.get("min_time");
                Long t = (Long) tmpMap01.get("total_num");
                if (d != null) {
                    quoteTime = DateUtil.format(DateUtil.FULL_FORMAT_STR, d);
                }
                if (t != null) {
                    quoteNum = t.intValue();
                }
            }
            if (orderMinTimeAndTotalNumMap.containsKey(userId)) {
                Map<String, Object> tmpMap02 = orderMinTimeAndTotalNumMap.get(userId);
                Date d = (Date) tmpMap02.get("min_time");
                Long t = (Long) tmpMap02.get("total_num");
                if (d != null) {
                    orderTime = DateUtil.format(DateUtil.FULL_FORMAT_STR, d);
                }
                if (t != null) {
                    orderNum = t.intValue();
                }
            }

            acquiringUser.put("quoteTime", quoteTime);
            acquiringUser.put("orderTime", orderTime);
            acquiringUser.put("quoteNum", quoteNum);
            acquiringUser.put("orderNum", orderNum);
            if (orderNum != 0 && quoteNum != 0) {
                acquiringUser.put("succRate", new BigDecimal(orderNum / (double) quoteNum).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                acquiringUser.put("succRate", BigDecimal.ZERO);
            }


        }
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(acquiringUserList);
        return pageInfo;
    }



    /**
     * 报价成单统计
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> quotePerformance(Map<String, Object> params) {
        // 分页查询获取人
        List<Map<String, Object>> acquiringUserList = quoteStatisticsMapper.findAllAcquiringUserList();
        // 获取时间段各个用户的询单报出最早时间和报价单个数
        List<Map<String, Object>> quoteMinTimeAndTotalNumList = quoteStatisticsMapper.getQuoteMinTimeAndTotalNum(params);
        // 获取时间段各个用户的项目开始最早日期和订单个数
        List<Map<String, Object>> orderMinTimeAndTotalNumList = quoteStatisticsMapper.getOrderMinTimeAndTotalNum(params);

        // 将list转换为userId为key的map信息
        Map<Long, Map<String, Object>> quoteMinTimeAndTotalNumMap = null;
        Map<Long, Map<String, Object>> orderMinTimeAndTotalNumMap  = new HashMap<>();
        if (quoteMinTimeAndTotalNumList != null && quoteMinTimeAndTotalNumList.size() > 0) {
            quoteMinTimeAndTotalNumMap = quoteMinTimeAndTotalNumList.stream().collect(Collectors.toMap(vo -> (Long) vo.get("acquiring_user_id"), vo -> vo));
        } else {
            quoteMinTimeAndTotalNumMap = new HashMap<>();
        }
        if (orderMinTimeAndTotalNumList != null && orderMinTimeAndTotalNumList.size() > 0) {
            orderMinTimeAndTotalNumMap = orderMinTimeAndTotalNumList.stream().collect(Collectors.toMap(vo -> (Long) vo.get("acquiring_user_id"), vo -> vo));
        } else {
            orderMinTimeAndTotalNumMap = new HashMap<>();
        }

        // 数据汇总
        for (Map<String, Object> acquiringUser : acquiringUserList) {
            Long userId = (Long) acquiringUser.get("acquiring_user_id");
            String quoteTime = "";
            int quoteNum = 0;
            String orderTime = "";
            int orderNum = 0;
            if (quoteMinTimeAndTotalNumMap.containsKey(userId)) {
                Map<String, Object> tmpMap01 = quoteMinTimeAndTotalNumMap.get(userId);
                Date d = (Date) tmpMap01.get("min_time");
                Long t = (Long) tmpMap01.get("total_num");
                if (d != null) {
                    quoteTime = DateUtil.format(DateUtil.FULL_FORMAT_STR, d);
                }
                if (t != null) {
                    quoteNum = t.intValue();
                }
            }
            if (orderMinTimeAndTotalNumMap.containsKey(userId)) {
                Map<String, Object> tmpMap02 = orderMinTimeAndTotalNumMap.get(userId);
                Date d = (Date) tmpMap02.get("min_time");
                Long t = (Long) tmpMap02.get("total_num");
                if (d != null) {
                    orderTime = DateUtil.format(DateUtil.FULL_FORMAT_STR, d);
                }
                if (t != null) {
                    orderNum = t.intValue();
                }
            }

            acquiringUser.put("quoteTime", quoteTime);
            acquiringUser.put("orderTime", orderTime);
            acquiringUser.put("quoteNum", quoteNum);
            acquiringUser.put("orderNum", orderNum);
            if (orderNum != 0 && quoteNum != 0) {
                acquiringUser.put("succRate", new BigDecimal(orderNum / (double) quoteNum).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                acquiringUser.put("succRate", BigDecimal.ZERO);
            }
        }
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(acquiringUserList);
        return pageInfo;
    }
}
