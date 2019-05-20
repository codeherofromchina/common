package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.report.dao.BuyerStatisticsMapper;
import com.erui.report.service.BuyerStatisticsService;
import com.erui.report.util.AnalyzeTypeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
@Service
public class BuyerStatisticsServiceImpl extends BaseService<BuyerStatisticsMapper> implements BuyerStatisticsService {
    private static final String[] AREAS = new String[]{"北美", "泛俄", "非洲", "南美", "欧洲", "亚太", "中东", "中国" };
    @Autowired
    private BuyerStatisticsMapper buyerStatisticsMapper;

    @Override
    public PageInfo<Map<String, Object>> registerBuyerList(int pageNum, int pageSize, Map<String, String> params) {
        PageHelper.startPage(pageNum, pageSize);

        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryRegisterBuyerList(params);

        PageInfo pageInfo = new PageInfo(buyerList);
        return pageInfo;
    }

    @Override
    public PageInfo<Map<String, Object>> membershipBuyerList(int pageNum, int pageSize, Map<String, String> params) {
        PageHelper.startPage(pageNum, pageSize);
//        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryMembershipBuyerList(params);
        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryAfter2019MembershipBuyerList(params);

        PageInfo pageInfo = new PageInfo(buyerList);
        return pageInfo;
    }


    @Override
    public PageInfo<Map<String, Object>> applyBuyerList(int pageNum, int pageSize, Map<String, String> params) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryApplyBuyerList(params);
        PageInfo pageInfo = new PageInfo(buyerList);
        return pageInfo;
    }


    /**
     * 订单会员统计
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> orderBuyerStatistics(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        String date2018 = "2018-01-01 00:00:00";
        String areaBn = (String) params.get("areaBn");
        String countryBn = (String) params.get("countryBn");
        String startTime = null;
        String endTime = null;
        if (params != null) {
            startTime = (String) params.get("startTime");
            endTime = (String) params.get("endTime");
        } else {
            params = new HashMap<>();
        }
        List<Map<String, Object>> curStatisticsList = null;
        List<Map<String, Object>> befStatisticsList = null;
        List<Map<String, Object>> aftStatisticsList = null;
        curStatisticsList = buyerStatisticsMapper.orderBuyerStatistics(params);
        Map<String, Object> params2 = new HashMap<>();
        params2.put("startTime", date2018);
        params2.put("endTime", endTime);
        params2.put("areaBn", areaBn);
        params2.put("countryBn", countryBn);
        aftStatisticsList = buyerStatisticsMapper.orderBuyerStatistics(params2);
        if (startTime == null) {
            befStatisticsList = new ArrayList<>(aftStatisticsList);
        } else {
            Date oneBefDay = DateUtil.getDateAfter(DateUtil.parseString2DateNoException(startTime, DateUtil.FULL_FORMAT_STR), -1);
            Map<String, Object> params3 = new HashMap<>();
            params3.put("startTime", date2018);
            params3.put("endTime", DateUtil.format(DateUtil.FULL_FORMAT_STR, oneBefDay));
            params3.put("areaBn", areaBn);
            params3.put("countryBn", countryBn);
            befStatisticsList = buyerStatisticsMapper.orderBuyerStatistics(params3);
        }
        // 合并结果集
        Map<String, Long> befMap = befStatisticsList.stream().collect(Collectors.toMap(vo -> vo.get("area_bn") + "_" + vo.get("area_name") + "_" + vo.get("country_bn") + "_" + vo.get("country_name"), vo -> (Long) vo.get("num")));
        Map<String, Long> aftMap = aftStatisticsList.stream().collect(Collectors.toMap(vo -> vo.get("area_bn") + "_" + vo.get("area_name") + "_" + vo.get("country_bn") + "_" + vo.get("country_name"), vo -> (Long) vo.get("num")));

        long befCount = 0;
        long curCount = 0;
        long aftCount = 0;
        // 合并操作
        for (Map<String, Object> map : curStatisticsList) {
            String key = map.get("area_bn") + "_" + map.get("area_name") + "_" + map.get("country_bn") + "_" + map.get("country_name");
            if (befMap.containsKey(key)) {
                Long befNum = befMap.remove(key);
                befCount += befNum.longValue();
                map.put("befNum", befNum);
            } else {
                map.put("befNum", 0L);
            }
            if (aftMap.containsKey(key)) {
                Long aftNum = aftMap.remove(key);
                aftCount += aftNum.longValue();
                map.put("aftNum", aftNum);
            } else {
                map.put("aftNum", 0L);
            }
            curCount += ((Long) map.get("num")).longValue();
        }
        for (Map.Entry<String, Long> map : befMap.entrySet()) {
            String key = map.getKey();
            Long value = map.getValue();
            String[] split = key.split("_");

            Map<String, Object> map02 = new HashMap<>();
            map02.put("area_bn", split[0]);
            map02.put("area_name", split[1]);
            map02.put("country_bn", split[2]);
            map02.put("country_name", split[3]);
            map02.put("befNum", value);
            befCount += value.longValue();
            if (aftMap.containsKey(key)) {
                Long aftNum = aftMap.remove(key);
                aftCount += aftNum.longValue();
                map02.put("aftNum", aftNum);
            } else {
                map02.put("aftNum", 0L);
            }
            map02.put("num", 0L);
            curStatisticsList.add(map02);
        }
        for (Map.Entry<String, Long> map : aftMap.entrySet()) {
            String key = map.getKey();
            Long value = map.getValue();
            String[] split = key.split("_");

            Map<String, Object> map02 = new HashMap<>();
            map02.put("area_bn", split[0]);
            map02.put("area_name", split[1]);
            map02.put("country_bn", split[2]);
            map02.put("country_name", split[3]);
            map02.put("aftNum", value);
            map02.put("num", 0L);
            map02.put("befNum", 0L);
            aftCount += value.longValue();
            curStatisticsList.add(map02);
        }
        if (curStatisticsList.size() > 0) {
            result.put("list", curStatisticsList);
            Map<String, Long> totalMap = new HashMap<>();
            totalMap.put("befCount", befCount);
            totalMap.put("curCount", curCount);
            totalMap.put("aftCount", aftCount);
            result.put("total", totalMap);
        }
        return result;
    }


    @Override
    public Map<String, Object> selectBuyerCountDetail(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(AREAS));
        //查询各地区的时间段内新增会员数
        List<Map<String, Object>> curDataList = readMapper.selectBuyerNumGroupByArea(params);
        List<Map<String, Object>> lastWeekDataList = null; // 上周数据内容
        //获取历史数据
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", "2019-01-01 00:00:00");
        params02.put("endTime", params.get("endTime"));
        List<Map<String, Object>> historyDataList = readMapper.selectBuyerNumGroupByArea(params02); // 历史数据
        if (params.get("chainStartTime") != null) { // 存在上周数据
            Map<String, Object> params03 = new HashMap<>();
            params03.put("startTime", params.get("chainStartTime"));
            params03.put("endTime", params.get("chainEndTime"));
            lastWeekDataList = readMapper.selectBuyerNumGroupByArea(params03);
        } else {
            lastWeekDataList = new ArrayList<>();
        }
        List<Integer> buyerCounts = new ArrayList<>(); //存放各地区 会员数
        List<Integer> historyBuyerCounts = new ArrayList<>(); //存放从19.1.1开始的各地区会员数量
        List<Integer> lastWeekBuyerCounts = new ArrayList<>(); //存放各地区 上周会员数

        Map<Object, Number> curMap = curDataList.stream().collect(Collectors.toMap(vo -> vo.get("area_name"), vo -> (Number) vo.get("num")));
        Map<Object, Number> lastMap = lastWeekDataList.stream().collect(Collectors.toMap(vo -> vo.get("area_name"), vo -> (Number) vo.get("num")));
        Map<Object, Number> historyMap = curDataList.stream().collect(Collectors.toMap(vo -> vo.get("area_name"), vo -> (Number) vo.get("num")));

        int buyerCountsCount = 0;
        int lastWeekBuyerCountsCount = 0;
        int historyBuyerCount = 0;

        for (String area : areaList) {
            Number aLong = curMap.get(area);
            if (aLong == null) {
                buyerCounts.add(Integer.valueOf(0));
            } else {
                int num = aLong.intValue();
                buyerCountsCount += num ;
                buyerCounts.add(num);
            }

            Number bLong = lastMap.get(area);
            if (bLong == null) {
                lastWeekBuyerCounts.add(Integer.valueOf(0));
            } else {
                int num = bLong.intValue();
                lastWeekBuyerCountsCount += num;
                lastWeekBuyerCounts.add(num);
            }

            Number cLong = historyMap.get(area);
            if (cLong == null) {
                historyBuyerCounts.add(Integer.valueOf(0));
            } else {
                int num = cLong.intValue();
                historyBuyerCount += num;
                historyBuyerCounts.add(num);
            }
        }
        areaList.add("合计");
        buyerCounts.add(buyerCountsCount);
        lastWeekBuyerCounts.add(lastWeekBuyerCountsCount);
        historyBuyerCounts.add(historyBuyerCount);


        //返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("areaList", areaList);
        result.put("currentWeekCounts", buyerCounts);
        result.put("lastWeekCounts", lastWeekBuyerCounts);
        result.put("historyCounts", historyBuyerCounts);
        return result;
    }

    @Override
    public HSSFWorkbook genOrderBuyerStatisticsExcel(Map<String, Object> params) {
        Map<String, Object> data = orderBuyerStatistics(params);
        if (params == null) {
            params = new HashMap<>();
        }
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        Date oneBefDay = null;
        Date startDay = null;
        Date endDay = null;
        if (StringUtils.isNotBlank(startTime)) {
            startDay = DateUtil.parseString2DateNoException(startTime, DateUtil.FULL_FORMAT_STR);
            oneBefDay = DateUtil.getDateAfter(startDay, -1);
        }
        if (StringUtil.isNotBlank(endTime)) {
            endDay = DateUtil.parseString2DateNoException(endTime, DateUtil.FULL_FORMAT_STR);
        }

        String[] header = {
                "序号",
                "地区",
                "国家",
                "累计截止到上周末2018.1.1-" + (oneBefDay == null ? "今" : DateUtil.format(DateUtil.SHORT_FORMAT_DOT_STR, oneBefDay)),
                "本周新增",
                "累计截止到本周末2018.1.1-" + (endDay == null ? "今" : DateUtil.format(DateUtil.SHORT_FORMAT_DOT_STR, endDay)),
                "备注",
        };
        List<Object[]> excelData = new ArrayList<Object[]>();
        if (data.size() > 0) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("list");
            int seq = 1;
            for (Map<String, Object> map : list) {
                Object[] rowData = new Object[header.length];
                rowData[0] = seq;
                rowData[1] = map.get("area_name");
                rowData[2] = map.get("country_name");
                rowData[3] = map.get("befNum");
                rowData[4] = map.get("num");
                rowData[5] = map.get("aftNum");
                excelData.add(rowData);
                seq++;
            }
            excelData.remove(excelData.size() - 1);
            // 最后的总计
            Map<String, Long> totalMap = (Map<String, Long>) data.get("total");
            Object[] rowData = new Object[header.length];
            rowData[0] = "合计";
            rowData[3] = totalMap.get("befCount");
            rowData[4] = totalMap.get("curCount");
            rowData[5] = totalMap.get("aftCount");
            excelData.add(rowData);
        }
        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(excelData, header, null,
                "开发会员统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, excelData.size());
        // 合并总计单元格
        ExcelCustomStyle.mergedCell(workbook, 0, excelData.size(), excelData.size(), 0, 2);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        if (params.size() == 0) {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "开发会员统计");
        } else {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "开发会员统计（" + DateUtil.format(DateUtil.SHORT_FORMAT_DOT_STR, startDay) + "-" + DateUtil.format(DateUtil.SHORT_FORMAT_DOT_STR, endDay) + "）");
        }
        return workbook;
    }


    @Override
    public HSSFWorkbook genRegisterBuyerListExcel(Map<String, String> params) {
        String[] header = {"序号", "注册客户代码", "注册时间", "获取人", "国家", "地区"};
        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryRegisterBuyerList(params);
        List<Object[]> excelData = new ArrayList<Object[]>();
        if (buyerList != null && buyerList.size() > 0) {
            int seq = 1;
            for (Map<String, Object> map : buyerList) {
                Object[] obj = new Object[header.length];
                obj[0] = seq;
                obj[1] = map.get("buyerCode");
                obj[2] = map.get("registerTime");
                obj[3] = map.get("acquiringUserName");
                obj[4] = map.get("countryName");
                obj[5] = map.get("areaName");
                excelData.add(obj);
                seq++;
            }
        }
        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(excelData, header, null,
                "业绩统计-会员统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, excelData.size());
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        if (params.containsKey("startTime")) {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "业绩统计-会员统计");
        } else {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "业绩统计-会员统计（" + params.get("registerStartTime") + "-" + params.get("registerEndTime") + "）");
        }
        return workbook;
    }


    @Override
    public HSSFWorkbook genApplyBuyerListExcel(Map<String, String> params) {
        String[] header = {"序号", "客户代码", "国家", "地区", "入网时间"};

        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryApplyBuyerList(params);
        List<Object[]> excelData = new ArrayList<Object[]>();
        if (buyerList != null && buyerList.size() > 0) {
            int seq = 1;
            for (Map<String, Object> map : buyerList) {
                Object[] obj = new Object[header.length];
                obj[0] = seq;
                obj[1] = map.get("buyerCode");
                obj[2] = map.get("countryName");
                obj[3] = map.get("areaName");
                obj[4] = map.get("applyTime");
                excelData.add(obj);
                seq++;
            }
        }
        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(excelData, header, null,
                "业绩统计-入网会员统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, excelData.size());
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        if (params.size() == 0) {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "业绩统计-入网会员统计");
        } else {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "业绩统计-入网会员统计（" + params.get("applyStartTime") + "--" + params.get("applyEndTime") + "）");
        }
        return workbook;
    }

    @Override
    public HSSFWorkbook genMembershipBuyerListExcel(Map<String, String> params) {
        String[] header = {"序号", "客户代码", "第一次交易时间", "获取人", "国家", "地区"};
//        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryMembershipBuyerList(params);
        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryAfter2019MembershipBuyerList(params);

        List<Object[]> excelData = new ArrayList<Object[]>();
        if (buyerList != null && buyerList.size() > 0) {
            int seq = 1;
            for (Map<String, Object> map : buyerList) {
                Object[] obj = new Object[header.length];
                obj[0] = seq;
                obj[1] = map.get("buyerCode");
                obj[2] = map.get("membershipTime");
                obj[3] = map.get("acquiringUserName");
                obj[4] = map.get("countryName");
                obj[5] = map.get("areaName");
                excelData.add(obj);
                seq++;
            }
        }
        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(excelData, header, null,
                "业绩统计-交易会员统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, excelData.size());
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        if (params.size() == 0 || StringUtils.isBlank(params.get("membershipStartTime")) || StringUtils.isBlank(params.get("membershipEndTime"))) {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "业绩统计-交易会员统计");
        } else {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "业绩统计-交易会员统计（" + params.get("membershipStartTime") + "--" + params.get("membershipEndTime") + "）");
        }
        return workbook;
    }
}
