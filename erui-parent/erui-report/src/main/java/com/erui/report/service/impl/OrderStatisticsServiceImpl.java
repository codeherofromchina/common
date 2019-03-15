package com.erui.report.service.impl;

import com.erui.report.dao.OrderStatisticsMapper;
import com.erui.report.service.OrderStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单统计服务实现类
 * Created by wangxiaodan on 2019/3/15.
 */
@Service
public class OrderStatisticsServiceImpl extends BaseService<OrderStatisticsMapper> implements OrderStatisticsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatisticsServiceImpl.class);

    /**
     * 查询年度整体业绩
     *
     * @param year 所统计的年份，如果为null，则统计所有年份（2016、2017、2018、2019）
     * @return
     */
    @Override
    public Map<String, Object> yearPerformance(Integer year) {
        LOGGER.info("params -> {}", year);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> yearsDataList = readMapper.yearPerformance(year);
        if (yearsDataList.size() > 0) {
            LOGGER.info("数据：{}", yearsDataList);
            List<String> xAxisData = new ArrayList<>(); // 年份
            List<BigDecimal> amountData = new ArrayList<>(); // 金额，美元
            List<Long> countData = new ArrayList<>(); // 订单数量
            for (Map<String, Object> yearData : yearsDataList) {
                Integer year02 = (Integer) yearData.get("year");
                BigDecimal money = (BigDecimal) yearData.get("money");
                Long count = ((BigDecimal) yearData.get("count")).longValue();
                xAxisData.add(String.valueOf(year02));
                amountData.add(money);
                countData.add(count);
            }
            result.put("xAxisData", xAxisData);
            result.put("amountData", amountData);
            result.put("countData", countData);
        }
        return result;
    }


    /**
     * 查询年度整体业绩
     *
     * @param year 所统计的年份，如果为null，则统计所有年份（2016、2017、2018、2019）
     * @return
     */
    @Override
    public Map<String, Object> yearAreaPerformance(Integer year) {
        LOGGER.info("params -> {}", year);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> yearsDataList = readMapper.yearAreaPerformance(year);
        if (yearsDataList.size() > 0) {
            LOGGER.info("数据：{}", yearsDataList);
            List<String> xAxisData = new ArrayList<>(); // 区域
            List<BigDecimal> amountData = new ArrayList<>(); // 金额，美元
            List<Long> countData = new ArrayList<>(); // 订单数量
            for (Map<String, Object> yearData : yearsDataList) {
                String areaName = (String) yearData.get("area_name");
                BigDecimal money = (BigDecimal) yearData.get("money");
                Long count = ((BigDecimal) yearData.get("count")).longValue();
                xAxisData.add(areaName);
                amountData.add(money);
                countData.add(count);
            }
            result.put("xAxisData", xAxisData);
            result.put("amountData", amountData);
            result.put("countData", countData);
        }
        return result;
    }
}
