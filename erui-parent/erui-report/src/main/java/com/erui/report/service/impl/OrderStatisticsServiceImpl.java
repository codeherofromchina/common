package com.erui.report.service.impl;

import com.erui.report.dao.OrderStatisticsMapper;
import com.erui.report.service.OrderStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
     * @return
     */
    @Override
    public Map<String, Object> yearPerformance(Integer startYear, Integer endYear) {
        LOGGER.info("params -> {} - {}", startYear, endYear);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> yearsDataList = readMapper.yearPerformance(startYear, endYear);
        if (yearsDataList.size() > 0) {
            LOGGER.info("数据：{}", yearsDataList);
            List<String> xAxisData = new ArrayList<>(); // 年份
            List<BigDecimal> amountData = new ArrayList<>(); // 金额，美元
            List<Long> countData = new ArrayList<>(); // 订单数量
            BigDecimal totalAmount = new BigDecimal("0");
            long totalCount = 0L;
            for (Map<String, Object> yearData : yearsDataList) {
                Integer year02 = (Integer) yearData.get("year");
                BigDecimal money = (BigDecimal) yearData.get("money");
                Long count = ((BigDecimal) yearData.get("count")).longValue();
                xAxisData.add(String.valueOf(year02));
                amountData.add(money);
                countData.add(count);
                totalAmount = totalAmount.add(money);
                totalCount += count;
            }
            result.put("xAxisData", xAxisData);
            result.put("amountData", amountData);
            result.put("countData", countData);
            result.put("totalAmount", totalAmount);
            result.put("totalCount", totalCount);
        }
        return result;
    }


    /**
     * 查询年度整体业绩
     *
     * @param startYear 所统计的年份
     * @param endYear 所统计的年份
     * @return
     */
    @Override
    public Map<String, Object> yearAreaPerformance(Integer startYear, Integer endYear) {
        LOGGER.info("params -> {} - {}", startYear, endYear);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> yearsDataList = readMapper.yearAreaPerformance(startYear, endYear);
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

    /**
     * 业务业绩统计 - 项目列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> projectList(int pageNum, int pageSize, Map<String, String> params) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> projectList = readMapper.projectList(params);

        PageInfo pageInfo = new PageInfo(projectList);
        return pageInfo;
    }
}
