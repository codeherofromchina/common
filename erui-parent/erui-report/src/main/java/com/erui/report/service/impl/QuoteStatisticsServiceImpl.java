package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.report.dao.QuoteStatisticsMapper;
import com.erui.report.service.QuoteStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
     *
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
        Map<Integer, Map<String, Object>> quoteMinTimeAndTotalNumMap = null;
        Map<Integer, Map<String, Object>> orderMinTimeAndTotalNumMap = null;
        if (quoteMinTimeAndTotalNumList != null && quoteMinTimeAndTotalNumList.size() > 0) {
            quoteMinTimeAndTotalNumMap = quoteMinTimeAndTotalNumList.stream().collect(Collectors.toMap(vo -> ((Long) vo.get("acquiring_user_id")).intValue(), vo -> vo));
        } else {
            quoteMinTimeAndTotalNumMap = new HashMap<>();
        }
        if (orderMinTimeAndTotalNumList != null && orderMinTimeAndTotalNumList.size() > 0) {
            orderMinTimeAndTotalNumMap = orderMinTimeAndTotalNumList.stream().collect(Collectors.toMap(vo -> (Integer) vo.get("acquiring_user_id"), vo -> vo));
        } else {
            orderMinTimeAndTotalNumMap = new HashMap<>();
        }

        Set<Integer> acquireUserIdSet = new HashSet<>();
        acquireUserIdSet.addAll(quoteMinTimeAndTotalNumMap.keySet());
        acquireUserIdSet.addAll(orderMinTimeAndTotalNumMap.keySet());

        List<Map<String, Object>> acquiringUserList = null;
        if (acquireUserIdSet.size() > 0) {
            // 分页查询获取人
            PageHelper.startPage(pageNum, pageSize);
            acquiringUserList = quoteStatisticsMapper.findAcquiringUserByUserIdSet(new ArrayList(acquireUserIdSet));
        } else {
            return null;
        }
        // 数据汇总
        for (Map<String, Object> acquiringUser : acquiringUserList) {
            Integer userId = (Integer) acquiringUser.get("acquiring_user_id");
            String quoteTime = "";
            int quoteNum = 0;
            String orderTime = "";
            int orderNum = 0;
            if (quoteMinTimeAndTotalNumMap.containsKey(userId)) {
                Map<String, Object> tmpMap01 = quoteMinTimeAndTotalNumMap.get(userId);
                Date d = (Date) tmpMap01.get("min_time");
                BigDecimal t = (BigDecimal) tmpMap01.get("total_num");
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
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> quotePerformance(Map<String, Object> params) {
        // 分页查询获取人
        List<Map<String, Object>> acquiringUserList = quoteStatisticsMapper.findAllAcquiringUserList();
        // 获取时间段各个用户的询单报出最早时间和报价单个数
        List<Map<String, Object>> quoteMinTimeAndTotalNumList = quoteStatisticsMapper.getQuoteMinTimeAndTotalNum(params);
        // 获取时间段各个用户的项目开始最早日期和订单个数
        List<Map<String, Object>> orderMinTimeAndTotalNumList = quoteStatisticsMapper.getOrderMinTimeAndTotalNum(params);

        // 将list转换为userId为key的map信息
        Map<Integer, Map<String, Object>> quoteMinTimeAndTotalNumMap = null;
        Map<Integer, Map<String, Object>> orderMinTimeAndTotalNumMap = null;
        if (quoteMinTimeAndTotalNumList != null && quoteMinTimeAndTotalNumList.size() > 0) {
            quoteMinTimeAndTotalNumMap = quoteMinTimeAndTotalNumList.stream().collect(Collectors.toMap(vo -> ((Long) vo.get("acquiring_user_id")).intValue(), vo -> vo));
        } else {
            quoteMinTimeAndTotalNumMap = new HashMap<>();
        }
        if (orderMinTimeAndTotalNumList != null && orderMinTimeAndTotalNumList.size() > 0) {
            orderMinTimeAndTotalNumMap = orderMinTimeAndTotalNumList.stream().collect(Collectors.toMap(vo -> (Integer) vo.get("acquiring_user_id"), vo -> vo));
        } else {
            orderMinTimeAndTotalNumMap = new HashMap<>();
        }

        // 数据汇总
        for (Map<String, Object> acquiringUser : acquiringUserList) {
            Integer userId = (Integer) acquiringUser.get("acquiring_user_id");
            String quoteTime = "";
            int quoteNum = 0;
            String orderTime = "";
            int orderNum = 0;
            if (quoteMinTimeAndTotalNumMap.containsKey(userId)) {
                Map<String, Object> tmpMap01 = quoteMinTimeAndTotalNumMap.get(userId);
                Date d = (Date) tmpMap01.get("min_time");
                BigDecimal t = (BigDecimal) tmpMap01.get("total_num");
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
        return acquiringUserList;
    }


    @Override
    public HSSFWorkbook genQuotePerformanceExcel(Map<String, Object> params) {
        List<Map<String, Object>> datas = quotePerformance(params);
        String[] header = {"序号", "获取人", "询单报出时间", "项目开始日期", "所属地区", "国家", "执行分公司", "报价个数", "订单个数", "成单率" };
        List<Object> excelData = new ArrayList<>();
        if (datas.size() > 0) {
            int seq = 1;
            for (Map<String, Object> map : datas) {
                Object[] rowData = new Object[header.length];
                rowData[0] = seq;
                rowData[1] = map.get("acquiring_user_name");
                rowData[2] = map.get("quoteTime");
                rowData[3] = map.get("orderTime");
                rowData[4] = map.get("area_name");
                rowData[5] = map.get("country_name");
                rowData[7] = map.get("quoteNum");
                rowData[8] = map.get("orderNum");
                rowData[9] = map.get("succRate");
                excelData.add(rowData);
                seq++;
            }
        }
        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(excelData, header, null,
                "报价成单统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, excelData.size());
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        if (params.size() == 0) {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "报价成单统计");
        } else {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "报价成单统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        }
        return workbook;
    }
}
