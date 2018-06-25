package com.erui.report.service.impl;

import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.SalesDataMapper;
import com.erui.report.service.SalesDataService;
import com.erui.report.util.AnalyzeTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesDataServiceImpl extends BaseService<SalesDataMapper> implements SalesDataService {


    @Override
    public Map<String, Object> selectInqQuoteTrendData(Map<String, Object> params) {

        //虚拟一个标准的时间集合
        List<String> dates = new ArrayList<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime").toString(), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endTime = DateUtil.parseString2DateNoException(params.get("endTime").toString(), DateUtil.FULL_FORMAT_STR2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }
        //查询趋势图相关数据
        List<Map<String, Object>> data = readMapper.selectInqQuoteTrendData(params);
        Map<String, Map<String, Object>> dataMap = data.stream().collect(Collectors.toMap(m -> m.get("datetime").toString(), m -> m));
        List<Integer> inqCounts = new ArrayList<>();
        List<Double> inqAmounts = new ArrayList<>();
        List<Integer> quoteCounts = new ArrayList<>();
        for (String date : dates) {
            if (dataMap.containsKey(date)) {
                inqCounts.add(Integer.parseInt(dataMap.get(date).get("inqCount").toString()));
                inqAmounts.add(RateUtil.doubleChainRateTwo(Double.parseDouble(dataMap.get(date).get("inqAmount").toString()), 1));
                quoteCounts.add(Integer.parseInt(dataMap.get(date).get("quoteCount").toString()));
            } else {
                inqCounts.add(0);
                inqAmounts.add(0d);
                quoteCounts.add(0);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("xAxis", dates);
        String analyzeType = params.get("analyzeType").toString();
        if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) {//询单数量
            result.put("yAxis", inqCounts);
        } else if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) {//询单金额
            result.put("yAxis", inqAmounts);
        } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) {//报价数量
            result.put("yAxis", quoteCounts);
        } else {
            return null;
        }
        return result;
    }

    @Override
    public Map<String, Object> selectAreaDetailByType(Map<String, Object> params) {

        //查询各大区和国家的数据明细
        List<Map<String, Object>> dataList = readMapper.selectAreaAndCountryDetail(params);
        Map<String, Object> result = new HashMap<>();
        List<Object> dList = new ArrayList<>();//各大区数据列表存放
        Set<String> keySet = new HashSet<>();

        if (CollectionUtils.isNotEmpty(dataList)) {
            String area = String.valueOf(params.get("area"));
            String country = String.valueOf(params.get("country"));
            Map<String, Map<String, Object>> dataMap = new HashMap<>();
            if (StringUtils.isEmpty(area) && StringUtils.isEmpty(country)) { //如果大区和国家都没有指定 显示各大区数据
                for (Map<String, Object> m : dataList) {
                    String areaName = m.get("area").toString();
                    int inqCount = Integer.parseInt(m.get("inqCount").toString());
                    int quoteCount = Integer.parseInt(m.get("quoteCount").toString());
                    double inqAmount = Double.parseDouble(m.get("inqAmount").toString());
                    if (dataMap.containsKey(areaName)) {
                        Map<String, Object> map = dataMap.get(areaName);
                        map.put("inqCount", Integer.parseInt(map.get("inqCount").toString()) + inqCount);
                        map.put("quoteCount", Integer.parseInt(map.get("quoteCount").toString()) + quoteCount);
                        map.put("inqAmount", Double.parseDouble(map.get("inqAmount").toString()) + inqAmount);
                    } else {
                        dataMap.put(areaName, m);
                    }
                }
            } else if (StringUtils.isNotEmpty(area) && StringUtils.isEmpty(country)) {//如果只指定了大区，显示该大区下所有国家的数据
                for (Map<String, Object> m : dataList) {
                    String area1 = m.get("area").toString();
                    String country1 = m.get("country").toString();
                    if (area1.equals(area)) {
                        dataMap.put(country1, m);
                    }
                }
            } else {//指定了大区和国家，显示指定国家的数据
                for (Map<String, Object> m : dataList) {
                    String area1 = m.get("area").toString();
                    String country1 = m.get("country").toString();
                    if (area1.equals(area) && country.equals(country1)) {
                        dataMap.put(country1, m);
                    }
                }
            }
            //如果有数据
            if (MapUtils.isNotEmpty(dataMap)) {
                keySet = dataMap.keySet();
                if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) {//分析类型为询单数量
                    for (String key : keySet) {
                        int inqCount = Integer.parseInt(dataMap.get(key).get("inqCount").toString());
                        dList.add(inqCount);
                    }
                }
                if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) {//分析类型为询单金额
                    for (String key : keySet) {
                        Double inqAmount = Double.parseDouble(dataMap.get(key).get("inqAmount").toString());
                        dList.add(RateUtil.doubleChainRateTwo(inqAmount, 1));
                    }
                }
                if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) {//分析类型为报价数量
                    for (String key : keySet) {
                        int quoteCount = Integer.parseInt(dataMap.get(key).get("quoteCount").toString());
                        dList.add(quoteCount);
                    }
                }

            }
        }

        result.put("areas", keySet);
        result.put("datas", dList);
        return result;
    }

    @Override
    public Map<String, Object> selectOrgDetailByType(Map<String, Object> params) {
        //查询各事业部的相关数据
        List<Map<String, Object>> data = readMapper.selectOrgDetail(params);
        Map<String, Object> result = new HashMap<>();
        List<String> orgList = new ArrayList<>();
        List<Integer> inqCountList = new ArrayList<>();
        List<Double> inqAmountList = new ArrayList<>();
        List<Integer> quoteCountList = new ArrayList<>();
        List<Double> quoteTimeList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(data)) {
            for (Map<String, Object> m : data) {
                orgList.add(m.get("org").toString());
                inqCountList.add(Integer.parseInt(m.get("inqCount").toString()));
                inqAmountList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("inqAmount").toString()), 1));
                quoteCountList.add(Integer.parseInt(m.get("quoteCount").toString()));
                quoteTimeList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("quoteTime").toString()), 1));
            }
        }
        //分析类型 ：默认 、询单数量、询单金额、报价数量、报价用时
        String analyzeType = params.get("analyzeType").toString();
        if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) {//如果分析类型为询单数量
            result.put("orgs", orgList);
            result.put("inqCountList", inqCountList);
        } else if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) {//分析类型为询单金额
            result.put("orgs", orgList);
            result.put("inqAmountList", inqAmountList);
        } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) {//分析类型为报价数量
            result.put("orgs", orgList);
            result.put("quoteCountList", quoteCountList);
        } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_TIME_COST.getTypeName())) {//分析类型为报价用时
            result.put("orgs", orgList);
            result.put("inqAmountList", quoteTimeList);
        } else {//默认的总览
            result.put("orgs", orgList);
            result.put("inqCountList", inqCountList);
            result.put("quoteCountList", quoteCountList);
            result.put("inqAmountList", inqAmountList);
        }

        return result;
    }

    @Override
    public Map<String, Object> selectCategoryDetailByType(Map<String, Object> params) {
        //查询各分类数据的相关数据
        List<Map<String, Object>> data = readMapper.selectDataGroupByCategory(params);
        Map<String, Object> result = new HashMap<>(); //结果集
        List<String> cateList = new ArrayList<>(); //存放分类的集合
        List<Object> dataList = new ArrayList<>();//存放数据的集合
        List<String> otherCateList = new ArrayList<>(); //存放其他分类的集合
        List<Object> otherDataList = new ArrayList<>();//存放其他分类数据的集合
        if (CollectionUtils.isNotEmpty(data)) {

            if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) {//分析类型为询单数量
                Integer otherInqCount = null;
                for (int i = 0; i < data.size(); i++) {
                    if (i < 8) {
                        cateList.add(data.get(i).get("category").toString());
                        dataList.add(data.get(i).get("inqCount"));
                    } else {
                        otherCateList.add(data.get(i).get("category").toString());
                        otherDataList.add(data.get(i).get("inqCount"));
                        if (otherInqCount != null) {
                            otherInqCount += Integer.parseInt(data.get(i).get("inqCount").toString());
                        } else {
                            otherInqCount = Integer.parseInt(data.get(i).get("inqCount").toString());
                        }

                    }
                }
                if (otherInqCount != null) {
                    cateList.add("其他");
                    dataList.add(otherInqCount);
                }

            }

            if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) {//分析类型为询单金额
                Double otherInqAmount = null;
                for (int i = 0; i < data.size(); i++) {
                    if (i < 8) {
                        cateList.add(data.get(i).get("category").toString());
                        double inqAmount = RateUtil.doubleChainRateTwo(Double.parseDouble(data.get(i).get("inqAmount").toString()), 1);
                        if (inqAmount < 0) inqAmount = 0d;
                        dataList.add(inqAmount);

                    } else {
                        otherCateList.add(data.get(i).get("category").toString());
                        double inqAmount = RateUtil.doubleChainRateTwo(Double.parseDouble(data.get(i).get("inqAmount").toString()), 1);
                        if (inqAmount < 0) inqAmount = 0d;
                        otherDataList.add(inqAmount);

                        if (otherInqAmount != null) {
                            otherInqAmount += Double.parseDouble(data.get(i).get("inqAmount").toString());
                        } else {
                            otherInqAmount = Double.parseDouble(data.get(i).get("inqAmount").toString());
                        }

                    }
                }
                if (otherInqAmount != null) {
                    cateList.add("其他");
                    if (otherInqAmount < 0) otherInqAmount = 0d;
                    dataList.add(RateUtil.doubleChainRateTwo(otherInqAmount, 1));
                }

            }

            if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) {//分析类型为报价数量
                Integer otherQuoteCount = null;
                for (int i = 0; i < data.size(); i++) {
                    if (i < 8) {
                        cateList.add(data.get(i).get("category").toString());
                        dataList.add(data.get(i).get("quoteCount"));
                    } else {
                        otherCateList.add(data.get(i).get("category").toString());
                        int quoteCount = Integer.parseInt(data.get(i).get("quoteCount").toString());
                        otherDataList.add(quoteCount);
                        if (otherQuoteCount != null) {
                            otherQuoteCount += quoteCount;
                        } else {
                            otherQuoteCount = quoteCount;
                        }
                    }
                }
                if (otherQuoteCount != null) {
                    cateList.add("其他");
                    dataList.add(otherQuoteCount);
                }

            }
            if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName())) {//分析类型为报价金额
                Double otherQuoteAmount = null;
                for (int i = 0; i < data.size(); i++) {
                    if (i < 8) {
                        cateList.add(data.get(i).get("category").toString());
                        double quoteAmount = RateUtil.doubleChainRateTwo(Double.parseDouble(data.get(i).get("quoteAmount").toString()), 1);
                        if (quoteAmount < 0) quoteAmount = 0d;
                        dataList.add(quoteAmount);
                    } else {
                        otherCateList.add(data.get(i).get("category").toString());
                        double quoteAmount = RateUtil.doubleChainRateTwo(Double.parseDouble(data.get(i).get("quoteAmount").toString()), 1);
                        if (quoteAmount < 0) quoteAmount = 0d;
                        otherDataList.add(quoteAmount);

                        if (otherQuoteAmount != null) {
                            otherQuoteAmount += Double.parseDouble(data.get(i).get("quoteAmount").toString());
                        } else {
                            otherQuoteAmount = Double.parseDouble(data.get(i).get("quoteAmount").toString());
                        }

                    }
                }
                if (otherQuoteAmount != null) {
                    cateList.add("其他");
                    double oAmount = RateUtil.doubleChainRateTwo(otherQuoteAmount, 1);
                    if (oAmount < 0) oAmount = 0d;
                    dataList.add(oAmount);
                }

            }

        }
        //封装数据
        Map<String, Object> others = new HashMap<>();
        others.put("cateList", otherCateList);
        others.put("datas", otherDataList);

        result.put("cateList", cateList);
        result.put("datas", dataList);
        result.put("others", others);
        return result;
    }

    @Override
    public XSSFWorkbook exportCategoryDetail(Map<String, Object> params) {

        //声明工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置字体居中

        //生成一个表格
        XSSFSheet sheet = wb.createSheet("销售数据统计-询报价品类明细");
        sheet.setDefaultColumnWidth(20);

        //产生标题行
        XSSFRow row = sheet.createRow(0);
        String[] headers = new String[]{"品类名称", params.get("analyzeType").toString()};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
        //获取分类数据
        List<Map<String, Object>> data = readMapper.selectDataGroupByCategory(params);
        if (CollectionUtils.isNotEmpty(data)) {
            for (int i = 0; i < data.size(); i++) {
                Map<String, Object> m = data.get(i);
                XSSFRow row1 = sheet.createRow(i + 1);
                XSSFCell cell0 = row1.createCell(0);
                cell0.setCellValue(m.get("category").toString());
                XSSFCell cell1 = row1.createCell(1);
                if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) {//分析类型为询单数量
                    cell1.setCellValue(Integer.parseInt(m.get("inqCount").toString()));
                } else if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) {//分析类型为询单金额
                    cell1.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("inqAmount").toString()), 1));
                } else if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) {//分析类型为报价数量
                    cell1.setCellValue(Integer.parseInt(m.get("quoteCount").toString()));
                } else if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName())) {//分析类型为报价金额
                    cell1.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("quoteAmount").toString()), 1));
                } else {
                    cell1.setCellValue(0);
                }
            }
        }

        return wb;
    }

    @Override
    public XSSFWorkbook exportOrgDetail(Map<String, Object> params) {

        String analyzeType = params.get("analyzeType").toString();
        //声明工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置字体居中

        //生成一个表格
        XSSFSheet sheet = wb.createSheet("销售数据统计-询报价事业部明细");
        sheet.setDefaultColumnWidth(20);

        //产生标题行
        XSSFRow row = sheet.createRow(0);
        String[] headers = null;
        if (StringUtils.isNotEmpty(analyzeType)) {
            headers = new String[]{"事业部名称", analyzeType};
        } else {
            headers = new String[]{"事业部名称", "询单数量", "报价数量", "报价金额"};
        }
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }

        //查询各事业部的相关数据
        List<Map<String, Object>> data = readMapper.selectOrgDetail(params);
        if (CollectionUtils.isNotEmpty(data)) {
            for (int i = 0; i < data.size(); i++) {
                Map<String, Object> m = data.get(i);
                XSSFRow row1 = sheet.createRow(i + 1);
                XSSFCell cell0 = row1.createCell(0);
                cell0.setCellValue(m.get("org").toString());
                if (StringUtils.isNotEmpty(analyzeType)) {
                    XSSFCell cell1 = row1.createCell(1);
                    if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) {
                        cell1.setCellValue(Integer.parseInt(m.get("inqCount").toString()));
                    } else if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) {
                        cell1.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("inqAmount").toString()), 1));
                    } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) {
                        cell1.setCellValue(Integer.parseInt(m.get("quoteCount").toString()));
                    } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_TIME_COST.getTypeName())) {
                        cell1.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("quoteTime").toString()), 1));
                    }
                } else {
                    XSSFCell cell1 = row1.createCell(1);
                    cell1.setCellValue(Integer.parseInt(m.get("inqCount").toString()));
                    XSSFCell cell2 = row1.createCell(2);
                    cell2.setCellValue(Integer.parseInt(m.get("quoteCount").toString()));
                    XSSFCell cell3 = row1.createCell(3);
                    cell3.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("quoteAmount").toString()), 1));

                }
            }
        }
        return wb;
    }

    @Override
    public XSSFWorkbook exportAreaDetail(Map<String, Object> params) {

        String analyzeType = params.get("analyzeType").toString();
        //声明工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置字体居中

        //生成一个表格
        XSSFSheet sheet = wb.createSheet("询报价大区明细");
        sheet.setDefaultColumnWidth(20);
        String[] headers = null;
        if (StringUtils.isEmpty(params.get("area").toString())) {
            headers = new String[]{"大区名称", analyzeType};
        } else {
            headers = new String[]{"国家名称", analyzeType};
        }
        //产生标题行
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }

        //获取数据
        Map<String, Object> data = this.selectAreaDetailByType(params);
        List<String> areas = Arrays.asList(data.get("areas").toString());
        List<Object> datas = Arrays.asList(data.get("datas"));
        if (CollectionUtils.isNotEmpty(areas) && CollectionUtils.isNotEmpty(datas)) {
            for (int i = 0; i < areas.size(); i++) {
                XSSFRow row1 = sheet.createRow(i + 1);
                XSSFCell cell0 = row1.createCell(0);
                cell0.setCellValue(areas.get(i));
                XSSFCell cell1 = row1.createCell(1);
                if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName()) ||
                        analyzeType.equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) {
                    cell1.setCellValue(Integer.parseInt(datas.get(i).toString()));
                } else {
                    cell1.setCellValue(Double.parseDouble(datas.get(i).toString()));
                }
            }
        }
        return wb;
    }


}
