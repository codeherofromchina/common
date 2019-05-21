package com.erui.report.service.impl;

import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.report.dao.OrderStatisticsMapper;
import com.erui.report.service.OrderStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            BigDecimal wanDollar = new BigDecimal("10000");
            for (Map<String, Object> yearData : yearsDataList) {
                Integer year02 = (Integer) yearData.get("year");
                BigDecimal money = (BigDecimal) yearData.get("money");
                Long count = ((BigDecimal) yearData.get("count")).longValue();
                xAxisData.add(String.valueOf(year02));

                if (money != null) {
                    money = money.divide(wanDollar).setScale(2, BigDecimal.ROUND_DOWN);
                } else {
                    money = BigDecimal.ZERO;
                }

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
     * @param endYear   所统计的年份
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
            BigDecimal wanDollar = new BigDecimal("10000");

            for (Map<String, Object> yearData : yearsDataList) {
                String areaName = (String) yearData.get("area_name");
                BigDecimal money = (BigDecimal) yearData.get("money");
                Long count = ((BigDecimal) yearData.get("count")).longValue();
                xAxisData.add(areaName);
                if (money != null) {
                    amountData.add(money.divide(wanDollar).setScale(2, BigDecimal.ROUND_DOWN));
                } else {
                    amountData.add(BigDecimal.ZERO);
                }
                countData.add(count);
            }
            result.put("xAxisData", xAxisData);
            result.put("amountData", amountData);
            result.put("countData", countData);
        }
        return result;
    }


    /**
     * 业务业绩统计 - 项目列表 -- 项目金额合计
     *
     * @return
     */
    @Override
    public BigDecimal projectTotalMoney(Map<String, String> params) {
        Map<String, String> p = new HashMap<>(params);
        p.remove("pageNum");
        p.remove("pageSize");
        BigDecimal totalMoney = readMapper.projectTotalMoney(p);
        if (totalMoney != null) {
            totalMoney = totalMoney.divide(new BigDecimal(10000)).setScale(2, RoundingMode.DOWN);
        }
        return totalMoney;
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

    /**
     * 生成
     *
     * @param params
     * @return
     */
    @Override
    public HSSFWorkbook genProjectListExcel(Map<String, String> params) {
        List<Map<String, Object>> projectList = readMapper.projectList(params);
        String[] header = {"序号", "项目开始日期", "销售合同号", "订单类别", "合同标的", "执行分公司", "事业部", "所属地区", "所属国家",
                "CRM客户代码", "项目金额", "初步利润率%", "利润", "获取人", "商务技术经办人"};
        List<Object> excelData = new ArrayList<>();
        if (projectList != null && projectList.size() > 0) {
            int seq = 1;
            for (Map<String, Object> map : projectList) {
                Object[] rowData = new Object[header.length];
                rowData[0] = seq;
                rowData[1] = map.get("projectStartDate");
                rowData[2] = map.get("contractNo");
                rowData[3] = map.get("orderType");
                rowData[4] = map.get("projectName");
                rowData[5] = map.get("execCoName");
                rowData[6] = map.get("orgName");
                rowData[7] = map.get("areaName");
                rowData[8] = map.get("countryName");
                rowData[9] = map.get("crmCode");
                rowData[10] = map.get("money");
                rowData[11] = map.get("profitPercent");
                rowData[12] = map.get("profit");
                rowData[13] = map.get("acquiringUser");
                rowData[14] = map.get("businessName");
                excelData.add(rowData);
                seq++;
            }
        }
        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(excelData, header, null,
                "业务业绩统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, excelData.size());
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        if (params.size() == 0) {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "业务业绩统计");
        } else {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "业务业绩统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        }
        return workbook;
    }
}
