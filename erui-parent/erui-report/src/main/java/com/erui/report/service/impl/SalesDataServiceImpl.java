package com.erui.report.service.impl;

import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.report.dao.SalesDataMapper;
import com.erui.report.service.CommonService;
import com.erui.report.service.SalesDataService;
import com.erui.report.util.AnalyzeTypeEnum;
import com.erui.report.util.SetList;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesDataServiceImpl extends BaseService<SalesDataMapper> implements SalesDataService {

    @Autowired
    private CommonService commonService;

    @Override
    public Map<String, Object> selectInqQuoteTrendData(Map<String, String> params) throws Exception {

        String analyzeType = params.get("analyzeType").toString();
        Map<String, Object> result = new HashMap<>();
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
        List<Double> quoteAmounts = new ArrayList<>();
        if (params.get("type").equals("week")) {
            for (String date : dates) {
                if (dataMap.containsKey(date)) {
                    inqCounts.add(Integer.parseInt(dataMap.get(date).get("inqCount").toString()));
                    inqAmounts.add(RateUtil.doubleChainRateTwo(Double.parseDouble(dataMap.get(date).get("inqAmount").toString()), 10000));
                    quoteCounts.add(Integer.parseInt(dataMap.get(date).get("quoteCount").toString()));
                    quoteAmounts.add(RateUtil.doubleChainRateTwo(Double.parseDouble(dataMap.get(date).get("quoteAmount").toString()), 10000));

                } else {
                    inqCounts.add(0);
                    inqAmounts.add(0d);
                    quoteCounts.add(0);
                    quoteAmounts.add(0d);
                }
            }
            result.put("xAxis", dates);
        } else if (params.get("type").equals("month")) { //处理月的数据

            List<String> dateList = handleMonthToWeekList(dates); //获取每周列表
            Map<String, Map<String, Object>> dMap = new HashMap<>(); //将每个周的数据暂时整理到dMap中
            for (String date : dateList) {
                if (CollectionUtils.isNotEmpty(data)) {
                    for (Map<String, Object> m : data) {
                        int inqCount = Integer.parseInt(m.get("inqCount").toString());
                        double inqAmount = Double.parseDouble(m.get("inqAmount").toString());
                        int quoteCount = Integer.parseInt(m.get("quoteCount").toString());
                        double quoteAmount = Double.parseDouble(m.get("quoteAmount").toString());
                        String datetime = m.get("datetime").toString();
                        SimpleDateFormat format = new SimpleDateFormat(DateUtil.SHORT_FORMAT_STR);
                        Date date1 = format.parse(datetime);
                        String weekNumber = DateUtil.getYearAndWeekNumber(date1);
                        if (date.equals(weekNumber)) {
                            if (dMap.containsKey(date)) {
                                Map<String, Object> mm = dMap.get(date);
                                int inqCount2 = Integer.parseInt(mm.get("inqCount").toString());
                                double inqAmount2 = Double.parseDouble(mm.get("inqAmount").toString());
                                int quoteCount2 = Integer.parseInt(mm.get("quoteCount").toString());
                                double quoteAmount2 = Double.parseDouble(mm.get("quoteAmount").toString());
                                mm.put("inqCount", inqCount2 + inqCount);
                                mm.put("inqAmount", inqAmount2 + inqAmount);
                                mm.put("quoteCount", quoteCount2 + quoteCount);
                                mm.put("quoteAmount2", quoteAmount2 + quoteAmount);
                            } else {
                                dMap.put(date, m);
                            }
                        }
                    }
                }
            }
            //按照顺序将每个类型的数据放入各自的结合
            for (String date : dateList) {
                if (dMap.containsKey(date)) {
                    Map<String, Object> map = dMap.get(date);
                    inqCounts.add(Integer.parseInt(map.get("inqCount").toString()));
                    inqAmounts.add(RateUtil.doubleChainRateTwo(Double.parseDouble(map.get("inqAmount").toString()), 10000));
                    quoteCounts.add(Integer.parseInt(map.get("quoteCount").toString()));
                    quoteAmounts.add(RateUtil.doubleChainRateTwo(Double.parseDouble(map.get("quoteAmount").toString()), 10000));
                } else {
                    inqCounts.add(0);
                    inqAmounts.add(0d);
                    quoteCounts.add(0);
                    quoteAmounts.add(0d);
                }
            }
            result.put("xAxis", dateList);
        } else if (params.get("type").equals("year")) { //如果为年 ，展示12个月份的
            List<String> monthList = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                monthList.add(i + "月");
            }
            Map<String, Map<String, Object>> dMap = new HashMap<>(); //将每个周的数据暂时整理到dMap中
            for (String month : monthList) {
                if (CollectionUtils.isNotEmpty(data)) {
                    for (Map<String, Object> m : data) {
                        int inqCount = Integer.parseInt(m.get("inqCount").toString());
                        double inqAmount = Double.parseDouble(m.get("inqAmount").toString());
                        int quoteCount = Integer.parseInt(m.get("quoteCount").toString());
                        double quoteAmount = Double.parseDouble(m.get("quoteAmount").toString());
                        String datetime = m.get("datetime").toString();
                        SimpleDateFormat format = new SimpleDateFormat(DateUtil.SHORT_FORMAT_STR);
                        Date date1 = format.parse(datetime);
                        int month1 = DateUtil.getMonth(date1);
                        if (month.equals(month1 + "月")) {
                            if (dMap.containsKey(month)) {
                                Map<String, Object> mm = dMap.get(month);
                                int inqCount2 = Integer.parseInt(mm.get("inqCount").toString());
                                double inqAmount2 = Double.parseDouble(mm.get("inqAmount").toString());
                                int quoteCount2 = Integer.parseInt(mm.get("quoteCount").toString());
                                double quoteAmount2 = Double.parseDouble(mm.get("quoteAmount").toString());
                                mm.put("inqCount", inqCount2 + inqCount);
                                mm.put("inqAmount", inqAmount2 + inqAmount);
                                mm.put("quoteCount", quoteCount2 + quoteCount);
                                mm.put("quoteAmount", quoteAmount2 + quoteAmount);
                            } else {
                                dMap.put(month, m);
                            }
                        }
                    }
                }
            }
            //按照顺序将每个类型的数据放入各自的结合
            for (String date : monthList) {
                if (dMap.containsKey(date)) {
                    Map<String, Object> map = dMap.get(date);
                    inqCounts.add(Integer.parseInt(map.get("inqCount").toString()));
                    inqAmounts.add(RateUtil.doubleChainRateTwo(Double.parseDouble(map.get("inqAmount").toString()), 10000));
                    quoteCounts.add(Integer.parseInt(map.get("quoteCount").toString()));
                    quoteAmounts.add(RateUtil.doubleChainRateTwo(Double.parseDouble(map.get("quoteAmount").toString()), 10000));
                } else {
                    inqCounts.add(0);
                    inqAmounts.add(0d);
                    quoteCounts.add(0);
                    quoteAmounts.add(0d);
                }
            }
            result.put("xAxis", monthList);
        }

        //处理结果

        if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) { //询单数量
            result.put("yAxis", inqCounts);
        } else if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) { //询单金额
//            result.put("yAxis", inqAmounts); // TODO 这里的询单金额暂时返回报价金额的数据，前端版本冲突，暂时无法修改
            result.put("yAxis", quoteAmounts);
        } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) { //报价数量
            result.put("yAxis", quoteCounts);
        } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName())) { //报价金额
            result.put("yAxis", quoteAmounts);
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
        List<Object> dList = new ArrayList<>(); //各大区数据列表存放
        Set<String> keySet = new HashSet<>();

        if (CollectionUtils.isNotEmpty(dataList)) {
            String area = params.get("area") == null ? "" : String.valueOf(params.get("area"));
            String country = params.get("country") == null ? "" : String.valueOf(params.get("country"));
            Map<String, Map<String, Object>> dataMap = new HashMap<>();
            if (StringUtils.isEmpty(area) && StringUtils.isEmpty(country)) { //如果大区和国家都没有指定 显示各大区数据
                for (Map<String, Object> m : dataList) {
                    String areaName = m.get("area").toString();
                    int inqCount = Integer.parseInt(m.get("inqCount").toString());
                    int quoteCount = Integer.parseInt(m.get("quoteCount").toString());
                    double inqAmount = Double.parseDouble(m.get("inqAmount").toString());
                    double quoteAmount = Double.parseDouble(m.get("quoteAmount").toString());
                    if (dataMap.containsKey(areaName)) {
                        Map<String, Object> map = dataMap.get(areaName);
                        map.put("inqCount", Integer.parseInt(map.get("inqCount").toString()) + inqCount);
                        map.put("quoteCount", Integer.parseInt(map.get("quoteCount").toString()) + quoteCount);
                        map.put("inqAmount", Double.parseDouble(map.get("inqAmount").toString()) + inqAmount);
                        map.put("quoteAmount", Double.parseDouble(map.get("quoteAmount").toString()) + quoteAmount);
                    } else {
                        dataMap.put(areaName, m);
                    }
                }
            } else if (StringUtils.isNotEmpty(area) && StringUtils.isEmpty(country)) { //如果只指定了大区，显示该大区下所有国家的数据
                for (Map<String, Object> m : dataList) {
                    String area1 = m.get("area").toString();
                    String country1 = m.get("country").toString();
                    if (area1.equals(area)) {
                        dataMap.put(country1, m);
                    }
                }
            } else { //指定了大区和国家，显示指定国家的数据
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
                if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) { //分析类型为询单数量
                    for (String key : keySet) {
                        int inqCount = Integer.parseInt(dataMap.get(key).get("inqCount").toString());
                        dList.add(inqCount);
                    }
                }
                if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) { //分析类型为询单金额
//                    for (String key : keySet) {
//                        Double inqAmount = Double.parseDouble(dataMap.get(key).get("inqAmount").toString());
//                        dList.add(RateUtil.doubleChainRateTwo(inqAmount, 10000));
//                    }
                    // TODO 暂时返回报价金额内容，前端版本问题无法修正，后端暂时修改
                    for (String key : keySet) {
                        Double quoteAmount = Double.parseDouble(dataMap.get(key).get("quoteAmount").toString());
                        dList.add(RateUtil.doubleChainRateTwo(quoteAmount, 10000));
                    }
                }
                if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) { //分析类型为报价数量
                    for (String key : keySet) {
                        int quoteCount = Integer.parseInt(dataMap.get(key).get("quoteCount").toString());
                        dList.add(quoteCount);
                    }
                }
                if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName())) { //分析类型为报价金额
                    for (String key : keySet) {
                        Double quoteAmount = Double.parseDouble(dataMap.get(key).get("quoteAmount").toString());
                        dList.add(RateUtil.doubleChainRateTwo(quoteAmount, 10000));
                    }
                }
            }
        }

        result.put("areas", keySet);
        result.put("datas", dList);
        return result;
    }

    @Override
    public Map<String, Object> selectInquiryInfoByCountry(Map<String, Object> params) {
        List<Map<String, Object>> datasList = readMapper.selectInqCountAndInqAmountGroupByCountry(params); // 获取数据
        Map<String, Object> result = new HashMap<>(); // 声明结果集
        if (datasList == null || datasList.size() == 0) {
            return result;
        }
        String analyzeType = String.valueOf(params.get("analyzeType"));
        final boolean sortFlag = "1".equals(String.valueOf(params.get("sort"))); // true:正序  false:倒序
        List<Object> names = new ArrayList<>();
        List<Object> datas = new ArrayList<>();
        if (AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) {
            datasList.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Long inqCount1 = (Long) o1.get("inqCount");
                    Long inqCount2 = (Long) o2.get("inqCount");
                    if (sortFlag) {
                        return inqCount1 > inqCount2 ? 1 : -1;
                    } else {
                        return inqCount1 >= inqCount2 ? -1 : 1;
                    }
                }
            });
            datasList.forEach(vo -> {
                names.add(vo.get("name"));
                datas.add(vo.get("inqCount"));
            });
        } else if (AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) {
            datasList.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    BigDecimal inqAmount1 = (BigDecimal) o1.get("inqAmount");
                    BigDecimal inqAmount2 = (BigDecimal) o2.get("inqAmount");
                    if (sortFlag) {
                        return inqAmount1.compareTo(inqAmount2);
                    } else {
                        return -inqAmount1.compareTo(inqAmount2);
                    }
                }
            });
            datasList.forEach(vo -> {
                names.add(vo.get("name"));
                datas.add(((BigDecimal) vo.get("inqAmount")).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_DOWN)); // 转换为万美元
            });
        }
        result.put("names", names);
        result.put("datas", datas);
        return result;
    }

    @Override
    public Map<String, Object> selectQuoteInfoByCountry(Map<String, Object> params) {
        List<Map<String, Object>> datasList = readMapper.selectQuoteCountAndQuoteAmountGroupByCountry(params);
        Map<String, Object> result = new HashMap<>(); // 声明结果集
        if (datasList == null || datasList.size() == 0) {
            return result;
        }
        String analyzeType = String.valueOf(params.get("analyzeType"));
        final boolean sortFlag = "1".equals(String.valueOf(params.get("sort"))); // true:正序  false:倒序
        List<Object> names = new ArrayList<>();
        List<Object> datas = new ArrayList<>();
        if (AnalyzeTypeEnum.QUOTE_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) {
            datasList.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Long quoteCount1 = (Long) o1.get("quoteCount");
                    Long quoteCount2 = (Long) o2.get("quoteCount");
                    if (sortFlag) {
                        return quoteCount1 > quoteCount2 ? 1 : -1;
                    } else {
                        return quoteCount1 >= quoteCount2 ? -1 : 1;
                    }
                }
            });
            datasList.forEach(vo -> {
                names.add(vo.get("name"));
                datas.add(vo.get("quoteCount"));
            });
        } else if (AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType) || AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) {
            datasList.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    BigDecimal quoteAmount1 = (BigDecimal) o1.get("quoteAmount");
                    BigDecimal quoteAmount2 = (BigDecimal) o2.get("quoteAmount");
                    if (sortFlag) {
                        return quoteAmount1.compareTo(quoteAmount2);
                    } else {
                        return -quoteAmount1.compareTo(quoteAmount2);
                    }
                }
            });
            datasList.forEach(vo -> {
                names.add(vo.get("name"));
                datas.add(((BigDecimal) vo.get("quoteAmount")).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_DOWN)); // 转换为万美元
            });
        }
        result.put("names", names);
        result.put("datas", datas);
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
        List<Double> quoteAmountList = new ArrayList<>();
        List<Double> quoteTimeList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(data)) {
            for (Map<String, Object> m : data) {
                orgList.add(m.get("org").toString());
                inqCountList.add(Integer.parseInt(m.get("inqCount").toString()));
                inqAmountList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("inqAmount").toString()), 10000));
                quoteCountList.add(Integer.parseInt(m.get("quoteCount").toString()));
                quoteAmountList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("quoteAmount").toString()), 10000));
                quoteTimeList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("quoteTime").toString()), 1));
            }
        }
        //分析类型 ：默认 、询单数量、询单金额、报价数量、报价金额、报价用时
        String analyzeType = params.get("analyzeType").toString();
        if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) { //如果分析类型为询单数量
            result.put("orgs", orgList);
            result.put("inqCountList", inqCountList);
        } else if (analyzeType.equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) { //分析类型为询单金额
            result.put("orgs", orgList);
//            result.put("inqAmountList", inqAmountList);
            // TODO 临时返回报价金额内容
            result.put("inqAmountList", quoteAmountList);
        } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) { //分析类型为报价数量
            result.put("orgs", orgList);
            result.put("quoteCountList", quoteCountList);
        } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName())) { //分析类型为报价金额
            result.put("orgs", orgList);
            result.put("quoteAmountList", quoteAmountList);
        } else if (analyzeType.equals(AnalyzeTypeEnum.QUOTE_TIME_COST.getTypeName())) { //分析类型为报价用时
            result.put("orgs", orgList);
            result.put("inqAmountList", quoteTimeList);
        } else { //默认的总览
            result.put("orgs", orgList);
            result.put("inqCountList", inqCountList);
            result.put("quoteCountList", quoteCountList);
            result.put("inqAmountList", inqAmountList);
        }

        return result;
    }

    /**
     * 导出品类信息 - 询报价数据统计 - 品类、品类事业部、品类地区
     * @param params
     * @param analyzeType
     * @return
     */
    @Override
    public HSSFWorkbook exportSelectCategoryNum(Map<String, Object> params, String analyzeType) {
        // 查询数据
        Map<String, Object> data = null;
        if (AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 询单数量
            data = selectCategoryInquiryNum(params);
        } else if (AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 报价金额
            data = selectCategoryQuoteAmount(params);
        } else if (AnalyzeTypeEnum.QUOTE_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) {  // 报价数量
            data = selectCategoryQuoteNum(params);
        } else if (AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 报价金额
            data = selectCategoryQuoteAmount(params);
        }

        // 组织数据
        if (data == null || data.size() == 0 || ((List) data.get("names")).size() == 0) {
            return null;
        }

        List<Object> headerList = (List<Object>) data.get("names");
        headerList.add(0, "");
        List<Object> row01 = (List<Object>) data.get("nums");

        List<Object> row02 = new ArrayList<>();

        Integer total = row01.parallelStream().map(vo -> ((BigDecimal) vo).intValue()).reduce(0,( a , b) -> a + b);
        BigDecimal totalBigDecimal = BigDecimal.ZERO;
        if (total != null && total != 0) {
            totalBigDecimal = new BigDecimal(total);
        }
        for (Object obj:row01) {
            if (totalBigDecimal == totalBigDecimal.ZERO) {
                row02.add(BigDecimal.ZERO);
            } else {
                row02.add(((BigDecimal) obj).divide(totalBigDecimal,4,BigDecimal.ROUND_DOWN));
            }
        }
        row01.add(0, "品类数量");
        row02.add(0, "品类占比");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        String orgId = (String) params.get("orgId");
        String areaBn = (String) params.get("areaBn");
        String latitudeName = "";
        if (StringUtils.isNotBlank(orgId) && StringUtils.isNumeric(orgId)) {
            Map<String, Object> orgInfo = commonService.findOrgInfoById(Integer.parseInt(orgId));
            latitudeName = "-事业部";
            if (orgInfo.get("orgName") != null) {
                String orgName = (String) orgInfo.get("orgName");
                latitudeName += "-" + orgName;
            }
        } else if (StringUtils.isNotBlank(areaBn)) {
            latitudeName = "-地区";
            Map<String, Object> orgInfo = commonService.findAreaInfoByBn(areaBn);
            if (orgInfo.get("areaName") != null) {
                String areaName = (String) orgInfo.get("areaName");
                latitudeName += "-" + areaName;
            }
        }

        // 生成excel并返回
        String workbookName = "询报价数据统计-品类占比" + latitudeName;
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                workbookName);
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 1);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, workbookName + "（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

    /**
     * 根据时间查询各分类询单数量
     *
     * @param params
     * @return [{category:'钻修井设备',num:6209}...]
     */
    @Override
    public Map<String, Object> selectCategoryInquiryNum(Map<String, Object> params) {
        List<Map<String, Object>> maps = readMapper.selectCategoryInquiryNum(params);
        Map<String, Object> result = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<Long> nums = new ArrayList<>();
        for (Map<String, Object> entry : maps) {
            names.add(String.valueOf(entry.get("category")));
            nums.add((Long) entry.get("num"));
        }
        result.put("names", names);
        result.put("nums", nums);

        return result;
    }

    /**
     * 根据时间查询各分类询单金额
     *
     * @param params
     * @return [{category:'钻修井设备',num:2.4532}...]
     */
    @Override
    public Map<String, Object> selectCategoryInquiryAmount(Map<String, Object> params) {
        List<Map<String, Object>> maps = readMapper.selectCategoryInquiryAmount(params);
        Map<String, Object> result = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<BigDecimal> nums = new ArrayList<>();
        for (Map<String, Object> entry : maps) {
            names.add(String.valueOf(entry.get("category")));
            nums.add(((BigDecimal) entry.get("num")).setScale(2, BigDecimal.ROUND_DOWN));
        }
        result.put("names", names);
        result.put("nums", nums);

        return result;
    }


    /**
     * 根据时间查询各分类报价数量
     *
     * @param params
     * @return [{category:'钻修井设备',num:6209}...]
     */
    @Override
    public Map<String, Object> selectCategoryQuoteNum(Map<String, Object> params) {
        List<Map<String, Object>> maps = readMapper.selectCategoryQuoteNum(params);
        Map<String, Object> result = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<Long> nums = new ArrayList<>();
        for (Map<String, Object> entry : maps) {
            names.add(String.valueOf(entry.get("category")));
            nums.add((Long) entry.get("num"));
        }
        result.put("names", names);
        result.put("nums", nums);

        return result;
    }


    /**
     * 根据时间查询各分类报价金额
     *
     * @param params
     * @return [{category:'钻修井设备',num:500.03}...]
     */
    @Override
    public Map<String, Object> selectCategoryQuoteAmount(Map<String, Object> params) {
        List<Map<String, Object>> maps = readMapper.selectCategoryQuoteAmount(params);
        Map<String, Object> result = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<BigDecimal> nums = new ArrayList<>();
        for (Map<String, Object> entry : maps) {
            names.add(String.valueOf(entry.get("category")));
            nums.add(((BigDecimal) entry.get("num")).setScale(2, BigDecimal.ROUND_DOWN));
        }
        result.put("names", names);
        result.put("nums", nums);

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
                data.stream().sorted(new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        int inqCount1 = Integer.parseInt(o1.get("inqCount").toString());
                        int inqCount2 = Integer.parseInt(o2.get("inqCount").toString());
                        return inqCount1 - inqCount2;
                    }
                });
                for (int i = 0; i < data.size(); i++) {
                    int inqCount = Integer.parseInt(data.get(i).get("inqCount").toString());
                    if (cateList.size() < 8) {
                        if (inqCount > 0) {
                            cateList.add(data.get(i).get("category").toString());
                            dataList.add(inqCount);
                        }
                    } else {
                        otherCateList.add(data.get(i).get("category").toString());
                        otherDataList.add(inqCount);
                        if (otherInqCount != null) {
                            otherInqCount += inqCount;
                        } else {
                            otherInqCount = inqCount;
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
                Collections.sort(data, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        double inqAmount1 = Double.parseDouble(o1.get("inqAmount").toString());
                        double inqAmount2 = Double.parseDouble(o2.get("inqAmount").toString());
                        double v = inqAmount1 - inqAmount2;
                        if (v < 0) {
                            return -1;
                        }
                        return 1;
                    }
                });
                for (int i = 0; i < data.size(); i++) {
                    double inqAmount = RateUtil.doubleChainRateTwo(Double.parseDouble(data.get(i).get("inqAmount").toString()), 10000);
                    if (cateList.size() < 8) {
                        if (inqAmount > 0d) {
                            cateList.add(data.get(i).get("category").toString());
                            dataList.add(inqAmount);
                        }

                    } else {
                        if (inqAmount > 0d) {
                            otherCateList.add(data.get(i).get("category").toString());
                            otherDataList.add(inqAmount);
                        }

                        if (otherInqAmount != null) {
                            otherInqAmount += inqAmount;
                        } else {
                            if (inqAmount > 0) {
                                otherInqAmount = inqAmount;
                            }
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
                Collections.sort(data, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        int quoteCount1 = Integer.parseInt(o1.get("quoteCount").toString());
                        int quoteCount2 = Integer.parseInt(o2.get("quoteCount").toString());
                        return quoteCount1 - quoteCount2;
                    }
                });
                for (int i = 0; i < data.size(); i++) {
                    int quoteCount = Integer.parseInt(data.get(i).get("quoteCount").toString());
                    if (cateList.size() < 8) {
                        if (quoteCount > 0) {
                            cateList.add(data.get(i).get("category").toString());
                            dataList.add(data.get(i).get("quoteCount"));
                        }
                    } else {
                        if (quoteCount > 0) {
                            otherCateList.add(data.get(i).get("category").toString());
                            otherDataList.add(quoteCount);
                        }
                        if (otherQuoteCount != null) {
                            otherQuoteCount += quoteCount;
                        } else {
                            if (quoteCount > 0) {
                                otherQuoteCount = quoteCount;
                            }
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
                    double quoteAmount = RateUtil.doubleChainRateTwo(Double.parseDouble(data.get(i).get("quoteAmount").toString()), 10000);
                    if (cateList.size() < 8) {
                        if (quoteAmount > 0d) {
                            cateList.add(data.get(i).get("category").toString());
                            dataList.add(quoteAmount);
                        }
                    } else {
                        if (quoteAmount > 0d) {
                            otherCateList.add(data.get(i).get("category").toString());
                            otherDataList.add(quoteAmount);
                        }
                        if (otherQuoteAmount != null) {
                            otherQuoteAmount += quoteAmount;
                        } else {
                            if (quoteAmount > 0) {
                                otherQuoteAmount = quoteAmount;
                            }
                        }

                    }
                }
                if (otherQuoteAmount != null) {
                    cateList.add("其他");
                    dataList.add(otherQuoteAmount);
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
    public Map<String, Object> selectCustomerVisitDetail(Map<String, String> params) throws Exception {

        Map<String, Object> result = new HashMap<>();//结果集

        //虚拟一个标准的时间集合
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endTime = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }
        //查询各大区每天的拜访次数
        List<Map<String, Object>> dataList = readMapper.selectVisitCountGrupByAreaAndVisitTime(params);
        //查询各大区有多少员工
        List<Map<String, Object>> employeeList = readMapper.selectEmployeeCountGroupByArea();
        Map<String, Integer> eMap = employeeList.stream().collect(Collectors.toMap(m -> m.get("area").toString(), m -> Integer.parseInt(m.get("employeeCount").toString())));
        //获取大区集合
        List<String> areaList = employeeList.stream().map(m -> m.get("area").toString()).collect(Collectors.toList());

        if (params.get("type").equals("week")) {//处理为周的数据

            //获取每一天的数据
            Map<String, List<Double>> dataMap = new HashMap<>();
            for (String date : dates) {
                List<Double> data = new ArrayList<>();
                Map<String, Double> areaDataMap = new HashMap<>();//存放指定日期各大区数据
                if (CollectionUtils.isNotEmpty(dataList)) {
                    for (Map<String, Object> m : dataList) {
                        String visitAt = m.get("visitAt").toString();
                        if (date.equals(visitAt)) {
                            String area = m.get("area").toString();
                            double visitCount = Double.parseDouble(m.get("visitCount").toString());
                            //设置成平均拜访数
                            if (eMap.get(area) > 0) {
                                visitCount = RateUtil.doubleChainRateTwo(visitCount, eMap.get(area));
                            }
                            areaDataMap.put(area, visitCount);
                        }
                    }
                }

                //遍历固定的大区集合
                if (CollectionUtils.isNotEmpty(areaList)) {
                    for (String area : areaList) {
                        if (areaDataMap.containsKey(area)) {
                            data.add(areaDataMap.get(area));
                        } else {
                            data.add(0d);
                        }
                    }
                }
                dataMap.put(date, data);

            }
            //整理每一天的数据
            List<Map<String, Object>> data = new ArrayList<>();
            for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
                Map<String, Object> dayMap = new HashMap<>();
                dayMap.put("dateName", entry.getKey());
                dayMap.put("data", entry.getValue());
                data.add(dayMap);
            }
            //为数据排序
            sortMapList(data);
            result.put("dateList", dates);
            result.put("areaList", areaList);
            result.put("data", data);
            return result;

        } else if (params.get("type").equals("month")) {//处理月的数据
            List<String> dateList = handleMonthToWeekList(dates);
            //获取每一周的数据
            Map<String, List<Double>> dataMap = new HashMap<>();
            for (String date : dateList) {//遍历周集合
                List<Double> data = new ArrayList<>();
                Map<String, Double> areaDataMap = new HashMap<>();//存放指定日期各大区数据
                if (CollectionUtils.isNotEmpty(dataList)) {
                    for (Map<String, Object> m : dataList) {
                        String visitAt = m.get("visitAt").toString();
                        SimpleDateFormat format = new SimpleDateFormat(DateUtil.SHORT_FORMAT_STR);
                        Date date1 = format.parse(visitAt);
                        String weekNumber = DateUtil.getYearAndWeekNumber(date1);
                        if (date.equals(weekNumber)) {
                            String area = m.get("area").toString();
                            double visitCount = Double.parseDouble(m.get("visitCount").toString());
                            if (!areaDataMap.containsKey(area)) {
                                areaDataMap.put(area, visitCount);
                            } else {
                                areaDataMap.put(area, areaDataMap.get(area) + visitCount);
                            }
                        }
                    }
                }

                //遍历固定的大区集合
                if (CollectionUtils.isNotEmpty(areaList)) {
                    for (String area : areaList) {
                        if (areaDataMap.containsKey(area)) {
                            //设置成平均拜访数
                            double visitCount = areaDataMap.get(area);
                            if (eMap.get(area) > 0) {
                                visitCount = RateUtil.doubleChainRateTwo(areaDataMap.get(area), eMap.get(area));
                            }
                            data.add(visitCount);
                        } else {
                            data.add(0d);
                        }
                    }
                }
                dataMap.put(date, data);

            }
            //整理每一周的数据
            List<Map<String, Object>> data = new ArrayList<>();
            for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
                Map<String, Object> dayMap = new HashMap<>();
                dayMap.put("dateName", entry.getKey());
                dayMap.put("data", entry.getValue());
                data.add(dayMap);
            }
            //为数据按日期排序
            sortMapList(data);
            result.put("dateList", dateList);
            result.put("areaList", areaList);
            result.put("data", data);
            return result;
        } else if (params.get("type").equals("year")) {//如果为年 ，展示12个月份的
            List<String> monthList = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                monthList.add(i + "月");
            }
            //获取每一月的数据
            Map<String, List<Double>> dataMap = new HashMap<>();
            for (String month : monthList) {
                List<Double> data = new ArrayList<>();
                Map<String, Double> areaDataMap = new HashMap<>();//存放指定日期各大区数据
                if (CollectionUtils.isNotEmpty(dataList)) {
                    for (Map<String, Object> m : dataList) {
                        String visitAt = m.get("visitAt").toString();
                        SimpleDateFormat format = new SimpleDateFormat(DateUtil.SHORT_FORMAT_STR);
                        Date date1 = format.parse(visitAt);
                        int month1 = DateUtil.getMonth(date1);
                        if (month.equals(month1 + "月")) {
                            String area = m.get("area").toString();
                            double visitCount = Double.parseDouble(m.get("visitCount").toString());
                            if (!areaDataMap.containsKey(area)) {
                                areaDataMap.put(area, visitCount);
                            } else {
                                areaDataMap.put(area, areaDataMap.get(area) + visitCount);
                            }
                        }
                    }
                }

                //遍历固定的大区集合
                if (CollectionUtils.isNotEmpty(areaList)) {
                    for (String area : areaList) {
                        if (areaDataMap.containsKey(area)) {
                            //设置成平均拜访数
                            double visitCount = areaDataMap.get(area);
                            if (eMap.get(area) > 0) {
                                visitCount = RateUtil.doubleChainRateTwo(areaDataMap.get(area), eMap.get(area));
                            }
                            data.add(visitCount);
                        } else {
                            data.add(0d);
                        }
                    }
                }
                dataMap.put(month, data);

            }
            //整理每一天的数据
            List<Map<String, Object>> data = new ArrayList<>();
            for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
                Map<String, Object> dayMap = new HashMap<>();
                dayMap.put("dateName", entry.getKey());
                dayMap.put("data", entry.getValue());
                data.add(dayMap);
            }
            //为数据按日期排序
            sortMapList(data);
            result.put("dateList", monthList);
            result.put("areaList", areaList);
            result.put("data", data);
            return result;
        }


        return null;
    }

    /**
     * 把月处理成  一周一周的的集合
     *
     * @param dateList
     * @return
     */
    public List<String> handleMonthToWeekList(List<String> dateList) throws ParseException {

        Set<String> weekSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(dateList)) {
            SimpleDateFormat format = new SimpleDateFormat(DateUtil.SHORT_FORMAT_STR);
            for (String date : dateList) {
                Date datetime = format.parse(date);
                int yearNumber = DateUtil.getYearNumber(datetime);
                int weekNumber = DateUtil.getWeekNumber(datetime);
                String key = yearNumber + "年第" + weekNumber + "周";
                weekSet.add(key);
            }
        }
        SetList setList = new SetList(new ArrayList<>(weekSet));
        return setList.getSetList();
    }

    public void sortMapList(List<Map<String, Object>> mapList) {
        //按日期数据排序
        mapList.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                o1.get("dateName");
                return SetList.getNum(o1.get("dateName").toString()) - SetList.getNum(o2.get("dateName").toString());
            }
        });

    }

    @Override
    public XSSFWorkbook exportCategoryDetail(Map<String, Object> params) {

        //声明工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER); //设置字体居中

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
                if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())) { //分析类型为询单数量
                    cell1.setCellValue(Integer.parseInt(m.get("inqCount").toString()));
                } else if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())) { //分析类型为询单金额
                    cell1.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("inqAmount").toString()), 10000));
                } else if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())) { //分析类型为报价数量
                    cell1.setCellValue(Integer.parseInt(m.get("quoteCount").toString()));
                } else if (params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName())) { //分析类型为报价金额
                    cell1.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("quoteAmount").toString()), 10000));
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
        cellStyle.setAlignment(HorizontalAlignment.CENTER); //设置字体居中

        //生成一个表格
        XSSFSheet sheet = wb.createSheet("询报价事业部明细");
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
                        cell1.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("inqAmount").toString()), 10000));
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
                    cell3.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("quoteAmount").toString()), 10000));

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
        if (params.get("area") == null || StringUtils.isEmpty(params.get("area").toString())) {
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
