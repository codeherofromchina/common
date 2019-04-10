package com.erui.report.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.report.dao.WeeklyReportMapper;
import com.erui.report.service.WeeklyReportService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeeklyReportServiceImpl extends BaseService<WeeklyReportMapper> implements WeeklyReportService {

    private static final String[] AREAS = new String[]{"北美", "泛俄", "非洲", "南美", "欧洲", "亚太", "中东", "中国"};
//    private static final String[] ORGS = new String[]{"易瑞-钻完井设备", "易瑞-工业工具", "易瑞-电力电工", "易瑞-工业品设备", "易瑞-安防和劳保设备", "油田设备", "康博瑞"};
//    private static final String[] ORGS = new String[]{"易瑞-钻完井设备", "易瑞-采油工程事业部", "易瑞-工业品事业部", "油田设备", "康博瑞"};
    private static final String[] ORGS = new String[]{"易瑞-钻完井设备事业部", "易瑞-采油工程事业部", "易瑞-工业品事业部"};
    private static final BigDecimal WAN_DOLLOR = new BigDecimal("10000");

    @Override
    public Map<String, Object> selectBuyerRegistCountGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(AREAS));
        //获取本周各地区的注册数 中国算是一个独立的地区
        List<Map<String, Object>> thisWeekList = readMapper.selectRegisterCountGroupByAreaAndChina(params);
        //获取上周各地区的注册数 中国算是一个独立的地区
        List<Map<String, Object>> lastWeekList = null;
        if (params.get("chainStartTime") != null) { // 存在上周数据
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekList = readMapper.selectRegisterCountGroupByAreaAndChina(params02);
        } else {
            lastWeekList = new ArrayList<>();
        }
        Map<String, Map<String, Object>> thisWeekMap = thisWeekList.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));
        Map<String, Map<String, Object>> lastWeekMap = lastWeekList.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));

        List<Integer> currentWeekCounts = new ArrayList<>(); //存放本周各地区新注册数量
        List<Integer> lastWeekCounts = new ArrayList<>(); //存放上周各地区新注册数量
        int currentWeekTotal = 0;
        int lastWeekTotal = 0;
        for (String area : areaList) {
            if (thisWeekMap.containsKey(area)) {
                Map<String, Object> map = thisWeekMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                currentWeekTotal += registerCount;
                currentWeekCounts.add(registerCount);
            } else {
                currentWeekCounts.add(0);
            }
            if (lastWeekMap.containsKey(area)) {
                Map<String, Object> map = lastWeekMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                lastWeekTotal += registerCount;
                lastWeekCounts.add(registerCount);
            } else {
                lastWeekCounts.add(0);
            }
        }
        //添加上合计数据
        areaList.add("合计");
        currentWeekCounts.add(currentWeekTotal);
        lastWeekCounts.add(lastWeekTotal);
        //返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("areaList", areaList);
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("lastWeekCounts", lastWeekCounts);
        return result;
    }

    @Deprecated
    @Override
    public Map<String, Object> selectBuyerCountGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(AREAS));
        //获取本周各地区的会员数数 中国算是一个独立的地区
        List<Map<String, Object>> thisWeekList = readMapper.selectBuyerCountGroupByAreaAndChina(params);
        //获取上周各地区的会员数 中国算是一个独立的地区
        List<Map<String, Object>> lastWeekList = null;
        if (params.get("chainStartTime") != null) { // 存在上周数据
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekList = readMapper.selectBuyerCountGroupByAreaAndChina(params02);
        } else {
            lastWeekList = new ArrayList<>();
        }
        //获取历史数据
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", "2018-01-01 00:00:00");
        params02.put("endTime", params.get("endTime"));
        List<Map<String, Object>> allAddUpList = readMapper.selectBuyerCountGroupByAreaAndChina(params02);
        Map<String, Map<String, Object>> thisWeekMap = thisWeekList.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));
        Map<String, Map<String, Object>> lastWeekMap = lastWeekList.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));
        Map<String, Map<String, Object>> allAddUpMap = allAddUpList.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));
        List<Integer> currentWeekCounts = new ArrayList<>(); //存放本周各地区会员数量
        List<Integer> lastWeekCounts = new ArrayList<>(); //存放上周各地区会员数量
        List<Integer> allAddUpCounts = new ArrayList<>(); //存放从18.1.1开始的各地区会员数量
        int currentWeekTotal = 0;
        int lastWeekTotal = 0;
        int totalCount = 0;
        for (String area : areaList) {
            if (thisWeekMap.containsKey(area)) {
                Map<String, Object> map = thisWeekMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                currentWeekTotal += registerCount;
                currentWeekCounts.add(registerCount);
            } else {
                currentWeekCounts.add(0);
            }
            if (lastWeekMap.containsKey(area)) {
                Map<String, Object> map = lastWeekMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                lastWeekTotal += registerCount;
                lastWeekCounts.add(registerCount);
            } else {
                lastWeekCounts.add(0);
            }

            if (allAddUpMap.containsKey(area)) {
                Map<String, Object> map = allAddUpMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                totalCount += registerCount;
                allAddUpCounts.add(registerCount);
            } else {
                allAddUpCounts.add(0);
            }
        }
        //添加上合计数据
        areaList.add("合计");
        currentWeekCounts.add(currentWeekTotal);
        lastWeekCounts.add(lastWeekTotal);
        allAddUpCounts.add(totalCount);
        //返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("areaList", areaList);
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("lastWeekCounts", lastWeekCounts);
        result.put("historyCounts", allAddUpCounts);

        return result;
    }

    @Override
    public Map<String, Object> selectBuyerCountDetail(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(AREAS));
        //查询各地区的时间段内普通会员Erui注册数、普通会员ERUI&KERUI注册数、高级会员Erui注册数、高级会员ERUI&KERUI注册数
        List<Map<String, Object>> dataList = readMapper.selectBuyerCountDetail(params);
        //获取历史数据
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", "2019-01-01 00:00:00");
        params02.put("endTime", params.get("endTime"));
        List<Map<String, Object>> allAddUpList = readMapper.selectBuyerCountDetail(params02);
        List<Integer> buyerCounts = new ArrayList<>(); //存放各地区 会员数
        List<Integer> allAddUpCounts = new ArrayList<>(); //存放从18.1.1开始的各地区会员数量
        List<Integer> normalEruiCounts = new ArrayList<>(); //存放各地区 普通会员Erui类型的会员数
        List<Integer> normalEruiAndKeruiCounts = new ArrayList<>(); //存放各地区 普通会员EruiAndKerui类型的会员数
        List<Integer> seniorEruiCounts = new ArrayList<>(); //存放各地区 高级会员Erui类型的会员数
        List<Integer> seniorEruiAndKeruiCounts = new ArrayList<>(); //存放各地区 高级会员EruiAndKerui类型的会员数
        int buyerCountsCount = 0;
        int normalEruiCountsCount = 0;
        int normalEruiAndKeruiCountsCount = 0;
        int seniorEruiCountsCount = 0;
        int seniorEruiAndKeruiCountsCount = 0;
        int allAddUpCountsCount = 0;

        for (String area : areaList) {
            int totalCount = 0; //各地区总会员数

            int normalEruiCode = 0;
            int normalEruiAndKeruiCode = 0;
            int seniorEruiCode = 0;
            int seniorEruiAndKeruiCode = 0;
            for (Map<String, Object> m : dataList) {
                String area1 = String.valueOf(m.get("area"));
                String level = String.valueOf(m.get("level"));
                String buyerType = String.valueOf(m.get("buyerType"));
                if (area.equals(area1)) {
                    int registerCount = Integer.parseInt(m.get("registerCount").toString());
                    if ("普通会员".equals(level) && "ERUI".equals(buyerType)) {
                        normalEruiCounts.add(registerCount);
                        normalEruiCountsCount += registerCount;
                        totalCount += registerCount;
                        normalEruiCode++;
                    }
                    if ("普通会员".equals(level) && "ERUIANDKERUI".equals(buyerType)) {
                        normalEruiAndKeruiCounts.add(registerCount);
                        normalEruiAndKeruiCountsCount += registerCount;
                        totalCount += registerCount;
                        normalEruiAndKeruiCode++;
                    }
                    if ("高级会员".equals(level) && "ERUI".equals(buyerType)) {
                        seniorEruiCounts.add(registerCount);
                        seniorEruiCountsCount += registerCount;
                        totalCount += registerCount;
                        seniorEruiCode++;
                    }
                    if ("高级会员".equals(level) && "ERUIANDKERUI".equals(buyerType)) {
                        seniorEruiAndKeruiCounts.add(registerCount);
                        seniorEruiAndKeruiCountsCount += registerCount;
                        totalCount += registerCount;
                        seniorEruiAndKeruiCode++;
                    }
                }
            }
            //判断该地区的四个数据是否已添加
            if (normalEruiCode != 1) {
                normalEruiCounts.add(0);
            }
            if (normalEruiAndKeruiCode != 1) {
                normalEruiAndKeruiCounts.add(0);
            }
            if (seniorEruiCode != 1) {
                seniorEruiCounts.add(0);
            }
            if (seniorEruiAndKeruiCode != 1) {
                seniorEruiAndKeruiCounts.add(0);
            }
            //添加总会员数
            buyerCounts.add(totalCount);
            buyerCountsCount += totalCount;

            //处理累计数据
            int allAddUpCount = 0; //各地区历史会员数
            for (Map<String, Object> m : allAddUpList) {
                String area1 = String.valueOf(m.get("area"));
                String buyerType = String.valueOf(m.get("buyerType"));
                if (area.equals(area1) && !"KERUI".equals(buyerType)) {
                    int registerCount = Integer.parseInt(m.get("registerCount").toString());
                    allAddUpCount += registerCount;
                }
            }
            allAddUpCounts.add(allAddUpCount);
            allAddUpCountsCount += allAddUpCount;
        }
        areaList.add("合计");
        buyerCounts.add(buyerCountsCount);
        normalEruiCounts.add(normalEruiCountsCount);
        normalEruiAndKeruiCounts.add(normalEruiAndKeruiCountsCount);
        seniorEruiCounts.add(seniorEruiCountsCount);
        seniorEruiAndKeruiCounts.add(seniorEruiAndKeruiCountsCount);
        allAddUpCounts.add(allAddUpCountsCount);

        //返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("areaList", areaList);
        result.put("currentWeekCounts", buyerCounts);
        result.put("normalEruiCounts", normalEruiCounts);
        result.put("normalEruiAndKeruiCounts", normalEruiAndKeruiCounts);
        result.put("seniorEruiCounts", seniorEruiCounts);
        result.put("seniorEruiAndKeruiCounts", seniorEruiAndKeruiCounts);
        result.put("historyCounts", allAddUpCounts);
        return result;
    }

    /**
     * 查询询单数量
     *
     * @param params {"chainStartTime":"2018/07/03","startTime":"2018/07/09","endTime":"2018/07/15"}
     *               chainStartTime:上周开始时间
     *               startTime：本周开始时间
     *               endTime:本周结束时间
     * @return
     */
    @Override
    public Map<String, Object> selectInqNumGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(AREAS));

        List<Map<String, Object>> currentWeekData = readMapper.selectInquiryCountWhereTimeGroupByCountry(params); // 本周数据
        List<Map<String, Object>> lastWeekData = null;
        if (params.get("chainStartTime") != null) {
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekData = readMapper.selectInquiryCountWhereTimeGroupByCountry(params02); // 上周数据
        } else {
            lastWeekData = new ArrayList<>();
        }
        // 将数据转换为map数据，方便遍历地区时查找数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));

        // 编辑需要数据的地区，并填充返回结果
        List<Integer> currentWeekCounts = new ArrayList<>(); //存放本周各地区新注册数量
        List<Integer> lastWeekCounts = new ArrayList<>(); //存放上周各地区新注册数量
        int currentWeekTotal = 0;
        int lastWeekTotal = 0;
        for (String area : areaList) {
            if (currentWeekDataMap.containsKey(area)) {
                Map<String, Object> map = currentWeekDataMap.get(area);
                int total = Integer.parseInt(map.get("total").toString());
                currentWeekTotal += total;
                currentWeekCounts.add(total);
            } else {
                currentWeekCounts.add(0);
            }
            if (lastWeekDataMap.containsKey(area)) {
                Map<String, Object> map = lastWeekDataMap.get(area);
                int total = Integer.parseInt(map.get("total").toString());
                lastWeekTotal += total;
                lastWeekCounts.add(total);
            } else {
                lastWeekCounts.add(0);
            }


        }
        areaList.add("合计");
        currentWeekCounts.add(currentWeekTotal);
        lastWeekCounts.add(lastWeekTotal);

        Map<String, Object> result = new HashMap<>();
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("lastWeekCounts", lastWeekCounts);
        result.put("areaList", areaList);

        return result;
    }


    @Override
    public Map<String, Object> selectQuoteInfoGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(AREAS));

        List<Map<String, Object>> currentWeekData = readMapper.selectQuoteInfoWhereTimeGroupByCountry(params); // 本周报价数据
        List<Map<String, Object>> lastWeekData = null;
        if (params.get("chainStartTime") != null) {
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekData = readMapper.selectQuoteInfoWhereTimeGroupByCountry(params02); // 上周报价数据
        } else {
            lastWeekData = new ArrayList<>();
        }
        // 将数据转换为map数据，方便遍历地区时查找数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));

        // 编辑需要数据的地区，并填充返回结果
        List<Integer> currentWeekCounts = new ArrayList<>(); //存放本周各地区报价数量
        List<BigDecimal> currentWeekAmounts = new ArrayList<>(); //存放本周各地区报价金额
        List<Integer> lastWeekCounts = new ArrayList<>(); //存放上周各地区报价数量
        List<BigDecimal> lastWeekAmounts = new ArrayList<>(); //存放上周各地区报价金额
        int currentWeekTotalNum = 0;
        BigDecimal currentWeekTotalPrice = BigDecimal.ZERO;
        int lastWeekTotalNum = 0;
        BigDecimal lastWeekTotalPrice = BigDecimal.ZERO;
        BigDecimal wanDollor = new BigDecimal("10000");
        for (String area : areaList) {
            if (currentWeekDataMap.containsKey(area)) {
                Map<String, Object> map = currentWeekDataMap.get(area);
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                BigDecimal totalPrice = (BigDecimal) map.get("total_price");
                totalPrice = totalPrice.divide(wanDollor, 2, BigDecimal.ROUND_HALF_UP);
                currentWeekTotalNum += totalNum;
                currentWeekTotalPrice = currentWeekTotalPrice.add(totalPrice);
                currentWeekCounts.add(totalNum);
                currentWeekAmounts.add(totalPrice);
            } else {
                currentWeekCounts.add(0);
                currentWeekAmounts.add(BigDecimal.ZERO);
            }
            if (lastWeekDataMap.containsKey(area)) {
                Map<String, Object> map = lastWeekDataMap.get(area);
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                BigDecimal totalPrice = (BigDecimal) map.get("total_price");
                totalPrice = totalPrice.divide(wanDollor, 2, BigDecimal.ROUND_HALF_UP);
                lastWeekTotalNum += totalNum;
                lastWeekTotalPrice = lastWeekTotalPrice.add(totalPrice);
                lastWeekCounts.add(totalNum);
                lastWeekAmounts.add(totalPrice);
            } else {
                lastWeekCounts.add(0);
                lastWeekAmounts.add(BigDecimal.ZERO);
            }
        }
        // 在结尾添加总数信息
        areaList.add("合计");
        currentWeekCounts.add(currentWeekTotalNum);
        currentWeekAmounts.add(currentWeekTotalPrice);
        lastWeekCounts.add(lastWeekTotalNum);
        lastWeekAmounts.add(lastWeekTotalPrice);

        Map<String, Object> result = new HashMap<>();
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("currentWeekAmounts", currentWeekAmounts);
        result.put("lastWeekCounts", lastWeekCounts);
        result.put("lastWeekAmounts", lastWeekAmounts);
        result.put("areaList", areaList);

        return result;
    }


    @Override
    public Map<String, Object> selectOrderInfoGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(AREAS));

        List<Map<String, Object>> currentWeekData = readMapper.selectOrderInfoWhereTimeGroupByCountry(params); // 本周订单数据
        List<Map<String, Object>> lastWeekData = null;
        if (params.get("chainStartTime") != null) {
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekData = readMapper.selectOrderInfoWhereTimeGroupByCountry(params02); // 上周订单数据
        } else {
            lastWeekData = new ArrayList<>();
        }
        Map<String, Object> params03 = new HashMap<>();
        params03.put("startTime", "2019/01/01 00:00:00");
        params03.put("endTime", params.get("endTime"));
        List<Map<String, Object>> historyData = readMapper.selectOrderInfoWhereTimeGroupByCountry(params03); // 历史订单数据
        // 将数据转换为map数据，方便遍历地区时查找数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));
        Map<String, Map<String, Object>> historyDataMap = historyData.stream().collect(Collectors.toMap(vo -> vo.get("area").toString().trim(), vo -> vo));

        // 编辑需要数据的地区，并填充返回结果
        List<Integer> currentWeekCounts = new ArrayList<>(); //存放本周各地区订单数量
        List<BigDecimal> currentWeekAmounts = new ArrayList<>(); //存放本周各地区订单金额
        List<Integer> lastWeekCounts = new ArrayList<>(); //存放上周各地区订单数量
        List<BigDecimal> lastWeekAmounts = new ArrayList<>(); //存放上周各地区订单金额
        List<Integer> historyCounts = new ArrayList<>(); //存放历史各地区订单数量
        List<BigDecimal> historyAmounts = new ArrayList<>(); //存放历史各地区订单金额
        int currentWeekTotalNum = 0;
        BigDecimal currentWeekTotalPrice = BigDecimal.ZERO;
        int lastWeekTotalNum = 0;
        BigDecimal lastWeekTotalPrice = BigDecimal.ZERO;
        int historyTotalNum = 0;
        BigDecimal historyTotalPrice = BigDecimal.ZERO;
        BigDecimal wanDollor = new BigDecimal("10000");
        for (String area : areaList) {
            if (currentWeekDataMap.containsKey(area)) {
                Map<String, Object> map = currentWeekDataMap.get(area);
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                BigDecimal totalPrice = (BigDecimal) map.get("total_price");
                totalPrice = totalPrice.divide(wanDollor, 2, BigDecimal.ROUND_HALF_UP);
                currentWeekTotalNum += totalNum;
                currentWeekTotalPrice = currentWeekTotalPrice.add(totalPrice);
                currentWeekCounts.add(totalNum);
                currentWeekAmounts.add(totalPrice);
            } else {
                currentWeekCounts.add(0);
                currentWeekAmounts.add(BigDecimal.ZERO);
            }
            if (lastWeekDataMap.containsKey(area)) {
                Map<String, Object> map = lastWeekDataMap.get(area);
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                BigDecimal totalPrice = (BigDecimal) map.get("total_price");
                totalPrice = totalPrice.divide(wanDollor, 2, BigDecimal.ROUND_HALF_UP);
                lastWeekTotalNum += totalNum;
                lastWeekTotalPrice = lastWeekTotalPrice.add(totalPrice);
                lastWeekCounts.add(totalNum);
                lastWeekAmounts.add(totalPrice);
            } else {
                lastWeekCounts.add(0);
                lastWeekAmounts.add(BigDecimal.ZERO);
            }

            if (historyDataMap.containsKey(area)) {
                Map<String, Object> map = historyDataMap.get(area);
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                BigDecimal totalPrice = (BigDecimal) map.get("total_price");
                totalPrice = totalPrice.divide(wanDollor, 2, BigDecimal.ROUND_HALF_UP);
                historyTotalNum += totalNum;
                historyTotalPrice = historyTotalPrice.add(totalPrice);
                historyCounts.add(totalNum);
                historyAmounts.add(totalPrice);
            } else {
                historyCounts.add(0);
                historyAmounts.add(BigDecimal.ZERO);
            }
        }
        // 在结尾添加总数信息
        areaList.add("合计");
        currentWeekCounts.add(currentWeekTotalNum);
        currentWeekAmounts.add(currentWeekTotalPrice);
        lastWeekCounts.add(lastWeekTotalNum);
        lastWeekAmounts.add(lastWeekTotalPrice);
        historyCounts.add(historyTotalNum);
        historyAmounts.add(historyTotalPrice);

        Map<String, Object> result = new HashMap<>();
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("currentWeekAmounts", currentWeekAmounts);
        result.put("lastWeekCounts", lastWeekCounts);
        result.put("lastWeekAmounts", lastWeekAmounts);
        result.put("historyCounts", historyCounts);
        result.put("historyAmounts", historyAmounts);
        result.put("areaList", areaList);

        return result;
    }


    /**
     * 查询事业部的询单数量信息
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> selectInqNumGroupByOrg(Map<String, Object> params) {
        List<Map<String, Object>> currentWeekData = readMapper.selectInquiryCountWhereTimeGroupByOrg(params);
        List<Map<String, Object>> lastWeekData = null;
        if (params.get("chainStartTime") != null) {
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekData = readMapper.selectInquiryCountWhereTimeGroupByOrg(params02); // 上周询单数据
        } else {
            lastWeekData = new ArrayList<>();
        }
        // 将数据转换为map数据，方便遍历事业部时查找数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));


        List<String> orgList = new ArrayList<>(); // 存放事业部列表
        List<Integer> currentWeekCounts = new ArrayList<>(); // 存放本周各事业部询单数量
        List<Integer> lastWeekCounts = new ArrayList<>(); // 存放上周个事业部询单数量
        for (String org : ORGS) {
            orgList.add(org);
            // 本周指定事业部数据处理
            Map<String, Object> curMap = currentWeekDataMap.remove(org);
            if (curMap != null) {
                Long total = (Long) curMap.get("total");
                currentWeekCounts.add(total.intValue());
            } else {
                currentWeekCounts.add(0);
            }
            // 上周指定事业部数据处理
            Map<String, Object> lastMap = lastWeekDataMap.remove(org);
            if (lastMap != null) {
                Long total = (Long) lastMap.get("total");
                lastWeekCounts.add(total.intValue());
            } else {
                lastWeekCounts.add(0);
            }
        }

        // 其他事业部处理
        Integer curWeekOtherOrgCount = currentWeekDataMap.values()
                .parallelStream().map(vo -> ((Long) vo.get("total")).intValue()).reduce(0, (a, b) -> a + b);
        Integer lastWeekOtherOrgCount = lastWeekDataMap.values()
                .parallelStream().map(vo -> ((Long) vo.get("total")).intValue()).reduce(0, (a, b) -> a + b);
        orgList.add("其他");
        currentWeekCounts.add(curWeekOtherOrgCount);
        lastWeekCounts.add(lastWeekOtherOrgCount);

        Map<String, Object> result = new HashMap<>();
        result.put("orgList", orgList);
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("lastWeekCounts", lastWeekCounts);
        return result;
    }


    @Override
    public Map<String, Object> selectQuoteInfoGroupByOrg(Map<String, Object> params) {
        List<Map<String, Object>> currentWeekData = readMapper.selectQuoteInfoWhereTimeGroupByOrg(params);
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));

        List<String> orgList = new ArrayList<>(); //存放事业部列表
        List<Integer> currentWeekCounts = new ArrayList<>(); //存放本周各事业部报价数量
        List<BigDecimal> currentWeekAmounts = new ArrayList<>(); //存放本周事业部报价金额
        for (String org : ORGS) {
            orgList.add(org);
            // 本周指定事业部报价数量处理
            Map<String, Object> curMap = currentWeekDataMap.remove(org);
            if (curMap != null) {
                Long totalNum = (Long) curMap.get("total_num");
                BigDecimal totalPrice = (BigDecimal) curMap.get("total_price");
                totalPrice = totalPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_HALF_UP);
                currentWeekCounts.add(totalNum.intValue());
                currentWeekAmounts.add(totalPrice);
            } else {
                currentWeekCounts.add(0);
                currentWeekAmounts.add(BigDecimal.ZERO);
            }
        }

        //处理其它事业部数据
        Integer curWeekOtherOrgCount = currentWeekDataMap.values()
                .parallelStream().map(vo -> ((Long) vo.get("total_num")).intValue())
                .reduce(0, (a, b) -> a + b);
        BigDecimal curWeekOtherOrgAmount = currentWeekDataMap.values()
                .parallelStream().map(vo -> (BigDecimal) vo.get("total_price"))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_DOWN)));
        orgList.add("其他");
        currentWeekCounts.add(curWeekOtherOrgCount);
        currentWeekAmounts.add(curWeekOtherOrgAmount);


        Map<String, Object> result = new HashMap<>();
        result.put("orgList", orgList);
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("currentWeekAmounts", currentWeekAmounts);
        // 查询用时
        Map<String, Object> orgTimesMapInfo = selectQuoteTimeInfoGroupByOrg(params);
        result.putAll(orgTimesMapInfo);
        return result;
    }


    @Override
    public Map<String, Object> selectQuoteTimeInfoGroupByOrg(Map<String, Object> params) {
        // 准备数据
        List<Map<String, Object>> currentWeekData = readMapper.selectQuoteTimeWhereTimeGroupByOrg(params);
        List<Map<String, Object>> lastWeekData = null;
        if (params.get("chainStartTime") != null) {
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekData = readMapper.selectQuoteTimeWhereTimeGroupByOrg(params02); // 上周报价用时数据
        } else {
            lastWeekData = new ArrayList<>();
        }
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        // 处理数据
        List<String> orgList = new ArrayList<>(); //存放事业部列表
        List<Long> currentWeekTimes = new ArrayList<>(); //存放本周各事业部报价用时
        List<Long> lastWeekTimes = new ArrayList<>(); //存放上周各事业部报价用时
        for (String org : ORGS) {
            orgList.add(org);
            // 本周指定事业部报价用时处理
            Map<String, Object> curMap = currentWeekDataMap.remove(org);
            if (curMap != null) {
                long totalTime = ((BigDecimal) curMap.get("diff")).longValue();
                currentWeekTimes.add(totalTime);
            } else {
                currentWeekTimes.add(0L);
            }
            // 上周指定事业部报价用时处理
            Map<String, Object> lastMap = lastWeekDataMap.remove(org);
            if (lastMap != null) {
                long totalTime = ((BigDecimal) lastMap.get("diff")).longValue();
                lastWeekTimes.add(totalTime);
            } else {
                lastWeekTimes.add(0L);
            }
        }
        //处理其它事业部数据
        Long curWeekOtherOrgTime = currentWeekDataMap.values().parallelStream()
                .map(vo -> ((BigDecimal) vo.get("diff")).longValue()).reduce(0L, (a, b) -> a + b);
        Long lastWeekOtherOrgTime = lastWeekDataMap.values().parallelStream()
                .map(vo -> ((BigDecimal) vo.get("diff")).longValue()).reduce(0L, (a, b) -> a + b);
        orgList.add("其他");
        currentWeekTimes.add(curWeekOtherOrgTime);
        lastWeekTimes.add(lastWeekOtherOrgTime);

        Map<String, Object> result = new HashMap<>();
        result.put("orgList", orgList);
        result.put("currentWeekTimes", currentWeekTimes);
        result.put("lastWeekTimes", lastWeekTimes);
        return result;
    }


    @Override
    public Map<String, Object> selectOrderInfoGroupByOrg(Map<String, Object> params) {
        // 准备数据
        List<Map<String, Object>> currentWeekData = readMapper.selectOrderInfoWhereTimeGroupByOrg(params); // 本周订单金额和数量
        List<Map<String, Object>> lastWeekData = null;
        if (params.get("chainStartTime") != null) {
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekData = readMapper.selectOrderInfoWhereTimeGroupByOrg(params02); // 上周订单数据
        } else {
            lastWeekData = new ArrayList<>();
        }
        Map<String, Object> params03 = new HashMap<>();
        params03.put("startTime", "2019-01-01 00:00:00");
        params03.put("endTime", params.get("endTime"));
        List<Map<String, Object>> historyWeekData = readMapper.selectOrderInfoWhereTimeGroupByOrg(params03); // 历史订单数据

        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> historyDataMap = historyWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        // 处理数据
        List<String> orgList = new ArrayList<>(); //存放事业部列表
        List<Integer> currentWeekCounts = new ArrayList<>(); //存放本周各事业部订单数量
        List<Integer> lastWeekCounts = new ArrayList<>(); //存放上周各事业部订单数量
        List<BigDecimal> currentWeekAmount = new ArrayList<>(); //存放本周各事业部订单金额
        List<BigDecimal> lastWeekAmount = new ArrayList<>(); //存放上周各事业部订单金额
        List<BigDecimal> historyAmount = new ArrayList<>(); //存放各事业部订单金额

        for (String org : ORGS) {
            orgList.add(org);
            // 本周数据
            Map<String, Object> curMap = currentWeekDataMap.remove(org);
            if (curMap != null) {
                int totalNum = ((Long) curMap.get("total_num")).intValue();
                BigDecimal totalPrice = (BigDecimal) curMap.get("total_price");
                totalPrice = totalPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_HALF_UP);
                currentWeekCounts.add(totalNum);
                currentWeekAmount.add(totalPrice);
            } else {
                currentWeekCounts.add(0);
                currentWeekAmount.add(BigDecimal.ZERO);
            }
            // 上周数据
            Map<String, Object> lastMap = lastWeekDataMap.remove(org);
            if (lastMap != null) {
                int totalNum = ((Long) lastMap.get("total_num")).intValue();
                BigDecimal totalPrice = (BigDecimal) lastMap.get("total_price");
                totalPrice = totalPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_HALF_UP);
                lastWeekCounts.add(totalNum);
                lastWeekAmount.add(totalPrice);
            } else {
                lastWeekCounts.add(0);
                lastWeekAmount.add(BigDecimal.ZERO);
            }
            //历史数据
            Map<String, Object> historyMap = historyDataMap.remove(org);
            if (historyMap != null) {
                BigDecimal totalPrice = (BigDecimal) historyMap.get("total_price");
                totalPrice = totalPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_HALF_UP);
                historyAmount.add(totalPrice);
            } else {
                historyAmount.add(BigDecimal.ZERO);
            }
        }
        // 其他数据处理
        Integer curWeekOtherOrgCount = currentWeekDataMap.values()
                .parallelStream().map(vo -> ((Long) vo.get("total_num")).intValue())
                .reduce(0, (a, b) -> a + b);
        BigDecimal curWeekOtherOrgPrice = currentWeekDataMap.values()
                .parallelStream().map(vo -> (BigDecimal) vo.get("total_price"))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        Integer lastWeekOtherOrgCount = lastWeekDataMap.values()
                .parallelStream().map(vo -> ((Long) vo.get("total_num")).intValue())
                .reduce(0, (a, b) -> a + b);
        BigDecimal lastWeekOtherOrgPrice = lastWeekDataMap.values()
                .parallelStream().map(vo -> (BigDecimal) vo.get("total_price"))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        BigDecimal historyOtherOrgPrice = historyDataMap.values()
                .parallelStream().map(vo -> (BigDecimal) vo.get("total_price"))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        orgList.add("其他");
        //单位万美元
        curWeekOtherOrgPrice = curWeekOtherOrgPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_HALF_UP);
        lastWeekOtherOrgPrice = lastWeekOtherOrgPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_HALF_UP);
        historyOtherOrgPrice = historyOtherOrgPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_HALF_UP);
        currentWeekCounts.add(curWeekOtherOrgCount);
        currentWeekAmount.add(curWeekOtherOrgPrice);
        lastWeekCounts.add(lastWeekOtherOrgCount);
        lastWeekAmount.add(lastWeekOtherOrgPrice);
        historyAmount.add(historyOtherOrgPrice);

        // 存放结果
        Map<String, Object> result = new HashMap<>();
        result.put("orgList", orgList);
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("lastWeekCounts", lastWeekCounts);
        result.put("currentWeekAmount", currentWeekAmount);
        result.put("lastWeekAmount", lastWeekAmount);
        result.put("historyAmount", historyAmount);
        return result;
    }



    @Override
    public Map<String, Object> selectSupplierNumInfoGroupByOrg(Map<String, Object> params) {
        // 准备数据
        List<Map<String, Object>> currentWeekData = readMapper.selectSupplierNumWhereTimeGroupByOrg(params);
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", "2019-01-01 00:00:00");
        params02.put("endTime", params.get("endTime"));
        List<Map<String, Object>> historyData = readMapper.selectSupplierNumWhereTimeGroupByOrg(params02); // 上周报价用时数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> historyDataMap = historyData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        // 处理数据
        List<String> orgList = new ArrayList<>(); //存放事业部列表
        List<Integer> currentWeekCounts = new ArrayList<>(); //存放本周各事业部供应商数
        List<Integer> historyCounts = new ArrayList<>(); //存放历史个事业部供应商数
        for (String org : ORGS) {
            orgList.add(org);
            // 本周指定事业部报价用时处理
            Map<String, Object> curMap = currentWeekDataMap.remove(org);
            if (curMap != null) {
                int total = ((Long) curMap.get("total")).intValue();
                currentWeekCounts.add(total);
            } else {
                currentWeekCounts.add(0);
            }
            // 上周指定事业部报价用时处理
            Map<String, Object> historyMap = historyDataMap.remove(org);
            if (historyMap != null) {
                int total = ((Long) historyMap.get("total")).intValue();
                historyCounts.add(total);
            } else {
                historyCounts.add(0);
            }
        }
        //处理其它事业部数据
        Integer curWeekOtherOrgNum = currentWeekDataMap.values().parallelStream()
                .map(vo -> ((Long) vo.get("total")).intValue()).reduce(0, (a, b) -> a + b);
        Integer historyOtherOrgNum = historyDataMap.values().parallelStream()
                .map(vo -> ((Long) vo.get("total")).intValue()).reduce(0, (a, b) -> a + b);
        orgList.add("其他");
        currentWeekCounts.add(curWeekOtherOrgNum);
        historyCounts.add(historyOtherOrgNum);

        Map<String, Object> result = new HashMap<>();
        result.put("orgList", orgList);
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("historyCounts", historyCounts);
        return result;
    }


    @Override
    @Deprecated
    public Map<String, Object> selectSpuAndSkuNumInfoGroupByOrg(Map<String, Object> params) {
        // 准备数据
//        List<Map<String, Object>> currentWeekData = readMapper.selectSpuAndSkuNumWhereTimeGroupByOrg(params);
        List<Map<String, Object>> currentSpuWeekData = readMapper.selectSpuNumWhereTimeGroupByOrg(params);
        List<Map<String, Object>> currentSkuWeekData = readMapper.selectSkuNumWhereTimeGroupByOrg(params);
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", "2019-01-01 00:00:00");
        params02.put("endTime", params.get("endTime"));
//        List<Map<String, Object>> historyData = readMapper.selectSpuAndSkuNumWhereTimeGroupByOrg(params02); // 累计spu/sku数据
        List<Map<String, Object>> historySpuData = readMapper.selectSpuNumWhereTimeGroupByOrg(params02);
        List<Map<String, Object>> historySkuData = readMapper.selectSkuNumWhereTimeGroupByOrg(params02);
        Map<String, Map<String, Object>> currentSpuWeekDataMap = currentSpuWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> currentSkuWeekDataMap = currentSkuWeekData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> historySpuDataMap = historySpuData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> historySkuDataMap = historySkuData.stream()
                .collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));

        // 处理数据
        List<String> orgList = new ArrayList<>(); //存放事业部列表
        List<Integer> currentWeekSpuCounts = new ArrayList<>();
        List<Integer> currentWeekSkuCounts = new ArrayList<>();
        List<Integer> historySpuCounts = new ArrayList<>();
        List<Integer> historySkuCounts = new ArrayList<>();
        for (String org : ORGS) {
            orgList.add(org);
            // 本周spu数据
            Map<String, Object> curSpuMap = currentSpuWeekDataMap.remove(org);
            if (curSpuMap != null) {
                int totalSpu = ((Long) curSpuMap.get("total_spu")).intValue();
                currentWeekSpuCounts.add(totalSpu);
            } else {
                currentWeekSpuCounts.add(0);
            }
            // 本周sku数据
            Map<String, Object> curSkuMap = currentSkuWeekDataMap.remove(org);
            if (curSkuMap != null) {
                int totalSku = ((Long) curSkuMap.get("total_sku")).intValue();
                currentWeekSkuCounts.add(totalSku);
            } else {
                currentWeekSkuCounts.add(0);
            }
            // 上周spu数据
            Map<String, Object> historySpuMap = historySpuDataMap.remove(org);
            if (historySpuMap != null) {
                int totalSpu = ((Long) historySpuMap.get("total_spu")).intValue();
                historySpuCounts.add(totalSpu);
            } else {
                historySpuCounts.add(0);
            }
            // 上周sku数据
            Map<String, Object> historySkuMap = historySkuDataMap.remove(org);
            if (historySkuMap != null) {
                int totalSku = ((Long) historySkuMap.get("total_sku")).intValue();
                historySkuCounts.add(totalSku);
            } else {
                historySkuCounts.add(0);
            }
        }
        //处理其它事业部数据
        Integer curWeekOtherOrgSpuNum = currentSpuWeekDataMap.values().parallelStream()
                .map(vo -> ((Long) vo.get("total_spu")).intValue()).reduce(0, (a, b) -> a + b);
        Integer curWeekOtherOrgSkuNum = currentSkuWeekDataMap.values().parallelStream()
                .map(vo -> ((Long) vo.get("total_sku")).intValue()).reduce(0, (a, b) -> a + b);
        Integer historyOtherOrgSpuNum = historySpuDataMap.values().parallelStream()
                .map(vo -> ((Long) vo.get("total_spu")).intValue()).reduce(0, (a, b) -> a + b);
        Integer historyOtherOrgSkuNum = historySkuDataMap.values().parallelStream()
                .map(vo -> ((Long) vo.get("total_sku")).intValue()).reduce(0, (a, b) -> a + b);
        orgList.add("其他");
        currentWeekSpuCounts.add(curWeekOtherOrgSpuNum);
        currentWeekSkuCounts.add(curWeekOtherOrgSkuNum);
        historySpuCounts.add(historyOtherOrgSpuNum);
        historySkuCounts.add(historyOtherOrgSkuNum);

        Map<String, Object> result = new HashMap<>();
        result.put("orgList", orgList);
        result.put("currentWeekSpuCounts", currentWeekSpuCounts);
        result.put("currentWeekSkuCounts", currentWeekSkuCounts);
        result.put("historySpuCounts", historySpuCounts);
        result.put("historySkuCounts", historySkuCounts);
        return result;
    }

    @Override
    public Map<String, Object> handleSpuSkuResult(List<Map<String, Object>> spuList,
                                                  List<Map<String, Object>> skuList,
                                                  List<Map<String, Object>> spuHistoryList,
                                                  List<Map<String, Object>> skuHistoryList) {
        //获取产品线列表 ，skuList、spuList都为产品线id 转变成产品线name
        List<Map<String, Object>> pLineList = readMapper.selectProductLineList();
        Map<String, Map<String, Object>> pLineMap = pLineList.stream()
                .collect(Collectors.toMap(m -> m.get("id").toString(), m -> m));
        if (CollectionUtils.isNotEmpty(spuList)) {
            Iterator<Map<String, Object>> iterator = spuList.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> m = iterator.next();
                String key = m.get("key").toString();
                if (pLineMap.containsKey(key)) {
                    Map<String, Object> lineMap = pLineMap.get(key);
                    String name = lineMap.get("name").toString();
                    m.put("name", name);
                } else {
                    iterator.remove();
                }
            }

        }
        if (CollectionUtils.isNotEmpty(skuList)) {
            Iterator<Map<String, Object>> iterator = skuList.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> m = iterator.next();
                String key = m.get("key").toString();
                if (pLineMap.containsKey(key)) {
                    Map<String, Object> lineMap = pLineMap.get(key);
                    String name = lineMap.get("name").toString();
                    m.put("name", name);
                } else {
                    iterator.remove();
                }
            }
        }
        if (CollectionUtils.isNotEmpty(spuHistoryList)) {
            Iterator<Map<String, Object>> iterator = spuHistoryList.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> m = iterator.next();
                String key = m.get("key").toString();
                if (pLineMap.containsKey(key)) {
                    Map<String, Object> lineMap = pLineMap.get(key);
                    String name = lineMap.get("name").toString();
                    m.put("name", name);
                } else {
                    iterator.remove();
                }
            }

        }
        if (CollectionUtils.isNotEmpty(skuHistoryList)) {
            Iterator<Map<String, Object>> iterator = skuHistoryList.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> m = iterator.next();
                String key = m.get("key").toString();
                if (pLineMap.containsKey(key)) {
                    Map<String, Object> lineMap = pLineMap.get(key);
                    String name = lineMap.get("name").toString();
                    m.put("name", name);
                } else {
                    iterator.remove();
                }
            }
        }

        Map<String, Map<String, Object>> currentSpuWeekDataMap = spuList.stream()
                .collect(Collectors.toMap(vo -> vo.get("name").toString(), vo -> vo));
        Map<String, Map<String, Object>> currentSkuWeekDataMap = skuList.stream()
                .collect(Collectors.toMap(vo -> vo.get("name").toString(), vo -> vo));
        Map<String, Map<String, Object>> historySpuDataMap = spuHistoryList.stream()
                .collect(Collectors.toMap(vo -> vo.get("name").toString(), vo -> vo));
        Map<String, Map<String, Object>> historySkuDataMap = skuHistoryList.stream()
                .collect(Collectors.toMap(vo -> vo.get("name").toString(), vo -> vo));

        // 处理数据
        List<String> orgList = new ArrayList<>(); //存放事业部列表
        List<Integer> currentWeekSpuCounts = new ArrayList<>();
        List<Integer> currentWeekSkuCounts = new ArrayList<>();
        List<Integer> historySpuCounts = new ArrayList<>();
        List<Integer> historySkuCounts = new ArrayList<>();
        for (String org : ORGS) {
            orgList.add(org);
            // 本周spu数据
            Map<String, Object> curSpuMap = currentSpuWeekDataMap.remove(org);
            if (curSpuMap != null) {
                int totalSpu = Integer.parseInt(curSpuMap.get("doc_count").toString());
                currentWeekSpuCounts.add(totalSpu);
            } else {
                currentWeekSpuCounts.add(0);
            }
            // 本周sku数据
            Map<String, Object> curSkuMap = currentSkuWeekDataMap.remove(org);
            if (curSkuMap != null) {
                int totalSku = Integer.parseInt(curSkuMap.get("doc_count").toString());
                currentWeekSkuCounts.add(totalSku);
            } else {
                currentWeekSkuCounts.add(0);
            }
            // 上周spu数据
            Map<String, Object> historySpuMap = historySpuDataMap.remove(org);
            if (historySpuMap != null) {
                int totalSpu = Integer.parseInt(historySpuMap.get("doc_count").toString());
                historySpuCounts.add(totalSpu);
            } else {
                historySpuCounts.add(0);
            }
            // 上周sku数据
            Map<String, Object> historySkuMap = historySkuDataMap.remove(org);
            if (historySkuMap != null) {
                int totalSku = Integer.parseInt(historySkuMap.get("doc_count").toString());
                historySkuCounts.add(totalSku);
            } else {
                historySkuCounts.add(0);
            }
        }
        //处理其它事业部数据
        Integer curWeekOtherOrgSpuNum = currentSpuWeekDataMap.values().parallelStream()
                .map(vo -> (Integer.parseInt(vo.get("doc_count").toString()))).reduce(0, (a, b) -> a + b);
        Integer curWeekOtherOrgSkuNum = currentSkuWeekDataMap.values().parallelStream()
                .map(vo -> (Integer.parseInt(vo.get("doc_count").toString()))).reduce(0, (a, b) -> a + b);
        Integer historyOtherOrgSpuNum = historySpuDataMap.values().parallelStream()
                .map(vo -> (Integer.parseInt(vo.get("doc_count").toString()))).reduce(0, (a, b) -> a + b);
        Integer historyOtherOrgSkuNum = historySkuDataMap.values().parallelStream()
                .map(vo -> (Integer.parseInt(vo.get("doc_count").toString()))).reduce(0, (a, b) -> a + b);
        orgList.add("其他");
        currentWeekSpuCounts.add(curWeekOtherOrgSpuNum);
        currentWeekSkuCounts.add(curWeekOtherOrgSkuNum);
        historySpuCounts.add(historyOtherOrgSpuNum);
        historySkuCounts.add(historyOtherOrgSkuNum);

        Map<String, Object> result = new HashMap<>();
        result.put("orgList", orgList);
        result.put("currentWeekSpuCounts", currentWeekSpuCounts);
        result.put("currentWeekSkuCounts", currentWeekSkuCounts);
        result.put("historySpuCounts", historySpuCounts);
        result.put("historySkuCounts", historySkuCounts);
        return result;
    }


    @Override
    public Map<String, Object> selectInqNumGroupByAreaTotal(Map<String, Object> params) {
        Integer currentWeekTotal = readMapper.selectInquiryCountWhereTimeGroupByCountryTotal(params); // 本周数据
        Integer lastWeekTotal = null;
        if (params.get("chainStartTime") != null) {
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekTotal = readMapper.selectInquiryCountWhereTimeGroupByCountryTotal(params02); // 上周数据
        }
        Map<String, Object> result = new HashMap<>();
        result.put("currentWeekTotal", currentWeekTotal == null ? 0 : currentWeekTotal);
        result.put("lastWeekTotal", lastWeekTotal == null ? 0 : lastWeekTotal);
        return result;
    }

    @Override
    public Map<String, Object> selectQuoteInfoGroupByAreaTotal(Map<String, Object> params) {
        Integer currentWeekTotal = readMapper.selectQuoteInfoWhereTimeGroupByCountryTotal(params); // 本周报价数据
        Integer lastWeekTotal = null;
        if (params.get("chainStartTime") != null) {
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekTotal = readMapper.selectQuoteInfoWhereTimeGroupByCountryTotal(params02); // 上周数据
        }
        Map<String, Object> result = new HashMap<>();
        result.put("currentWeekTotal", currentWeekTotal == null ? 0 : currentWeekTotal);
        result.put("lastWeekTotal", lastWeekTotal == null ? 0 : lastWeekTotal);
        return result;
    }

    @Override
    public Map<String, Object> selectOrderInfoGroupByAreaTotal(Map<String, Object> params) {
        Integer currentWeekTotal = readMapper.selectOrderInfoWhereTimeGroupByCountryTotal(params); // 本周报价数据
        Integer lastWeekTotal = null;
        if (params.get("chainStartTime") != null) {
            Map<String, Object> params02 = new HashMap<>();
            params02.put("startTime", params.get("chainStartTime"));
            params02.put("endTime", params.get("chainEndTime"));
            lastWeekTotal = readMapper.selectOrderInfoWhereTimeGroupByCountryTotal(params02); // 上周数据
        }
        Map<String, Object> result = new HashMap<>();
        result.put("currentWeekTotal", currentWeekTotal == null ? 0 : currentWeekTotal);
        result.put("lastWeekTotal", lastWeekTotal == null ? 0 : lastWeekTotal);
        return result;
    }

    /**
     * 导出地区统计的周报信息
     *
     * @param params
     * @return
     */
    @Override
    public HSSFWorkbook genAreaDetailExcel(Map<String, Object> params) {
        // 准备数据
        //查询各地区的时间段内新用户注册数，中国算一个地区
        Map<String, Object> registerData = selectBuyerRegistCountGroupByArea(params);
        //查询各地区的时间段内会员数 中国算一个地区
        Map<String, Object> buyerData = selectBuyerCountDetail(params);
        // 查询各地区时间段内询单数
        Map<String, Object> inqNumInfoData = selectInqNumGroupByArea(params);
        // 查询各个地区时间段内的报价数量和金额信息
        Map<String, Object> quoteInfoData = selectQuoteInfoGroupByArea(params);
        // 查询各个地区时间段内的订单数量和金额信息
        Map<String, Object> orderInfoData = selectOrderInfoGroupByArea(params);
        // 标题
        String[] header = new String[]{"", "北美", "泛俄", "非洲", "南美", "欧洲", "亚太", "中东", "中国", "合计"};
        // 处理数据
        // 第一行数据
        List<Object> row01 = new ArrayList<>();
        row01.add("新用户注册");
        row01.addAll((List<Object>) registerData.get("currentWeekCounts"));
        // 第二行数据
        List<Object> row02 = new ArrayList<>();
        row02.add("会员数");
        row02.addAll((List<Object>) buyerData.get("currentWeekCounts"));
        // 第三行数据
        List<Object> row03 = new ArrayList<>();
        row03.add("2019.1.1-" + params.get("endTime") + "累计会员数量");
        row03.addAll((List<Object>) buyerData.get("historyCounts"));
        // 第四行数据
        List<Object> row04 = new ArrayList<>();
        row04.add("普通用户ERUI");
        row04.addAll((List<Object>) buyerData.get("normalEruiCounts"));
        // 第五行数据
        List<Object> row05 = new ArrayList<>();
        row05.add("普通用户 ERUI&KERUI");
        row05.addAll((List<Object>) buyerData.get("normalEruiAndKeruiCounts"));
        // 第六行数据
        List<Object> row06 = new ArrayList<>();
        row06.add("高级用户ERUI");
        row06.addAll((List<Object>) buyerData.get("seniorEruiCounts"));
        // 第7行数据
        List<Object> row07 = new ArrayList<>();
        row07.add("高级用户ERUI&KERUI");
        row07.addAll((List<Object>) buyerData.get("seniorEruiAndKeruiCounts"));
        // 第7行数据
        List<Object> row08 = new ArrayList<>();
        row08.add("询单数量(个)");
        row08.addAll((List<Object>) inqNumInfoData.get("currentWeekCounts"));
        // 第9行数据
        List<Object> row09 = new ArrayList<>();
        row09.add("报价数量(个)");
        row09.addAll((List<Object>) quoteInfoData.get("currentWeekCounts"));
        // 第十行数据
        List<Object> row10 = new ArrayList<>();
        row10.add("报价金额（万美元）");
        row10.addAll((List<Object>) quoteInfoData.get("currentWeekAmounts"));
        // 第十一行数据
        List<Object> row11 = new ArrayList<>();
        row11.add("订单数量(个)");
        row11.addAll((List<Object>) orderInfoData.get("currentWeekCounts"));
        // 第12行数据
        List<Object> row12 = new ArrayList<>();
        row12.add("订单金额（万美元）");
        row12.addAll((List<Object>) orderInfoData.get("currentWeekAmounts"));
        // 第十三行数据
        List<Object> row13 = new ArrayList<>();
        row13.add("2019.1.1-" + params.get("endTime") + "累计订单金额");
        row13.addAll((List<Object>) orderInfoData.get("historyAmounts"));
        List<Object[]> datas = new ArrayList<>();
        datas.add(row01.toArray());
        datas.add(row02.toArray());
        datas.add(row03.toArray());
        datas.add(row04.toArray());
        datas.add(row05.toArray());
        datas.add(row06.toArray());
        datas.add(row07.toArray());
        datas.add(row08.toArray());
        datas.add(row09.toArray());
        datas.add(row10.toArray());
        datas.add(row11.toArray());
        datas.add(row12.toArray());
        datas.add(row13.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(datas, header, null,
                "地区周报");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 17);
        // 合并单元格
//        ExcelCustomStyle.mergedCell(workbook,0,1,2,0,0);
//        ExcelCustomStyle.mergedCell(workbook,0,3,5,0,0);
//        ExcelCustomStyle.mergedCell(workbook,0,6,7,0,0);
//        ExcelCustomStyle.mergedCell(workbook,0,8,9,0,0);
//        ExcelCustomStyle.mergedCell(workbook,0,10,11,0,0);
//        ExcelCustomStyle.mergedCell(workbook,0,12,13,0,0);
//        ExcelCustomStyle.mergedCell(workbook,0,14,16,0,0);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0,
                "地区周报（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }


    @Override
    public HSSFWorkbook genOrgDetailExcel(Map<String, Object> params, Map<String, Object> spuSkuNumInfoData) {
        String endTime = (String) params.get("endTime");
        if (StringUtils.isBlank(endTime)) {
            endTime = "";
        } else {
            endTime = endTime.substring(0, 10).replace("-", ".");
        }
        // 准备数据
        // 询单数量信息
        Map<String, Object> inqNumInfoData = selectInqNumGroupByOrg(params);
        // 报价数量、金额、用时
        Map<String, Object> quoteInfoData = selectQuoteInfoGroupByOrg(params);
        // 查询订单数量、金额
        Map<String, Object> orderInfoData = selectOrderInfoGroupByOrg(params);
        // 查询合格供应商数量信息
        Map<String, Object> supplierNumInfoData = selectSupplierNumInfoGroupByOrg(params);

        // 标题
        String[] header = new String[]{"", "易瑞-钻完井设备", "易瑞-工业工具", "易瑞-电力电工",
                "易瑞-工业品设备", "易瑞-安防和劳保设备", "油田设备", "康博瑞", "其他"};
        // 处理数据
        List<Object> row01 = new ArrayList<>();
        row01.add("询单数量");
        row01.addAll((List<Object>) inqNumInfoData.get("currentWeekCounts"));
        List<Object> row03 = new ArrayList<>();
        row03.add("报价数量");
        row03.addAll((List<Object>) quoteInfoData.get("currentWeekCounts"));
        List<Object> row04 = new ArrayList<>();
        row04.add("报价金额（万美元）");
        row04.addAll((List<Object>) quoteInfoData.get("currentWeekAmounts"));
        List<Object> row05 = new ArrayList<>();
        row05.add("报价用时");
        row05.addAll((List<Object>) quoteInfoData.get("currentWeekTimes"));
        List<Object> row07 = new ArrayList<>();
        row07.add("订单数量");
        row07.addAll((List<Object>) orderInfoData.get("currentWeekCounts"));
        List<Object> row09 = new ArrayList<>();
        row09.add("订单金额(万美元)");
        row09.addAll((List<Object>) orderInfoData.get("currentWeekAmount"));
        List<Object> row10 = new ArrayList<>();
        row10.add("2019.1.1-" + endTime +
                "累计订单金额");
        row10.addAll((List<Object>) orderInfoData.get("historyAmount"));
        List<Object> row12 = new ArrayList<>();
        row12.add("合格供应商数量");
        row12.addAll((List<Object>) supplierNumInfoData.get("currentWeekCounts"));
        List<Object> row13 = new ArrayList<>();
        row13.add("2019.1.1-" + endTime +
                "总数");
        row13.addAll((List<Object>) supplierNumInfoData.get("historyCounts"));
        List<Object> row14 = new ArrayList<>();
        row14.add("上架SKU数量");
        row14.addAll((List<Object>) spuSkuNumInfoData.get("currentWeekSkuCounts"));
        List<Object> row15 = new ArrayList<>();
        row15.add("2019.1.1-" + endTime +
                "总数");
        row15.addAll((List<Object>) spuSkuNumInfoData.get("historySkuCounts"));
        List<Object> row16 = new ArrayList<>();
        row16.add("上架SPU数量");
        row16.addAll((List<Object>) spuSkuNumInfoData.get("currentWeekSpuCounts"));
        List<Object> row17 = new ArrayList<>();
        row17.add("2019.1.1-" + endTime +
                "总数");
        row17.addAll((List<Object>) spuSkuNumInfoData.get("historySpuCounts"));
        // 填充数据
        List<Object[]> datas = new ArrayList<>();
        datas.add(row01.toArray());
        datas.add(row03.toArray());
        datas.add(row04.toArray());
        datas.add(row05.toArray());
        datas.add(row07.toArray());
        datas.add(row09.toArray());
        datas.add(row10.toArray());
        datas.add(row12.toArray());
        datas.add(row13.toArray());
        datas.add(row14.toArray());
        datas.add(row15.toArray());
        datas.add(row16.toArray());
        datas.add(row17.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(datas, header, null,
                "事业部周报");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 13);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "事业部周报（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

    /**
     * 谷歌统计信息
     * TODO 待实现
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> googleStatisticsInfo(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        result.put("currentWeekPV", 0);
        result.put("lastWeekPV", 0);
        result.put("currentWeekUV", 0);
        result.put("lastWeekUV", 0);
        result.put("currentWeekJUMP", 0);
        result.put("lastWeekJUMP", 0);
        result.put("currentWeekAVG", 0);
        result.put("lastWeekAVG", 0);
        return result;
    }


    @Override
    public HSSFWorkbook genPlatformDataDetail(Map<String, Object> params) {
        // 准备数据
        // 查询时间段内询单数
        Map<String, Object> inqNumInfoData = selectInqNumGroupByAreaTotal(params);
        // 查询时间段内的报价数量
        Map<String, Object> quoteInfoData = selectQuoteInfoGroupByAreaTotal(params);
        // 查询时间段内的订单数量
        Map<String, Object> orderInfoData = selectOrderInfoGroupByAreaTotal(params);
        // 谷歌统计信息
        Map<String, Object> googleStatistics = googleStatisticsInfo(params);
        // 标题
        String[] header = new String[]{"PV", "UV", "询单", "报价", "订单", "跳出率（%）", "平均会话时长"};
        // 处理数据
        Object[] row02 = new Object[]{
                googleStatistics.get("currentWeekPV"),
                googleStatistics.get("currentWeekUV"),
                inqNumInfoData.get("currentWeekTotal"),
                quoteInfoData.get("currentWeekTotal"),
                orderInfoData.get("currentWeekTotal"),
                googleStatistics.get("currentWeekJUMP"),
                googleStatistics.get("currentWeekAVG")
        };
        List<Object[]> datas = new ArrayList<>();
        datas.add(row02);

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(datas, header, null,
                "平台数据分析周报");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 1);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0,
                "平台数据分析周报（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }
}
