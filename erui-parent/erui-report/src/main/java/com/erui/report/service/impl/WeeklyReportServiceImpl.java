package com.erui.report.service.impl;

import com.erui.report.dao.WeeklyReportMapper;
import com.erui.report.service.WeeklyReportService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeeklyReportServiceImpl extends BaseService<WeeklyReportMapper> implements WeeklyReportService {

    private static final String[] areas = new String[]{"北美", "泛俄", "非洲", "南美", "欧洲", "亚太", "中东", "中国"};

    @Override
    public Map<String, Object> selectBuyerRegistCountGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(areas));
        //获取本周各地区的注册数 中国算是一个独立的地区
        List<Map<String, Object>> thisWeekList = readMapper.selectRegisterCountGroupByAreaAndChina(params);
        Map<String, Map<String, Object>> thisWeekMap = new HashMap<>();

        Integer totalCount1 = thisWeekList.stream().map(m -> {
            String area = m.get("area").toString();
            thisWeekMap.put(area, m);
            return Integer.parseInt(m.get("registerCount").toString());
        }).reduce(0, (a, b) -> a + b);
        //获取上周各地区的注册数 中国算是一个独立的地区
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", params.get("chainStartTime"));
        params02.put("endTime", params.get("startTime"));
        List<Map<String, Object>> lastWeekList = readMapper.selectRegisterCountGroupByAreaAndChina(params02);
        Map<String, Map<String, Object>> lastWeekMap = new HashMap<>();
        Integer totalCount2 = lastWeekList.stream().map(m -> {
            String area = m.get("area").toString();
            lastWeekMap.put(area, m);
            return Integer.parseInt(m.get("registerCount").toString());
        }).reduce(0, (a, b) -> a + b);

        List<Integer> thisWeekCounts = new ArrayList<>();//存放本周各地区新注册数量
        List<Integer> lastWeekCounts = new ArrayList<>();//存放上周各地区新注册数量
        for (String area : areaList) {
            if (thisWeekMap.containsKey(area)) {
                Map<String, Object> map = thisWeekMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                thisWeekCounts.add(registerCount);
            } else {
                thisWeekCounts.add(0);
            }
            if (lastWeekMap.containsKey(area)) {
                Map<String, Object> map = lastWeekMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                lastWeekCounts.add(registerCount);
            } else {
                lastWeekCounts.add(0);
            }
        }
        //添加上合计数据
        areaList.add("合计");
        thisWeekCounts.add(totalCount1);
        lastWeekCounts.add(totalCount2);
        //返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("areas", areaList);
        result.put("currentWeekCounts", thisWeekCounts);
        result.put("lastWeekCounts", lastWeekCounts);
        return result;
    }

    @Override
    public Map<String, Object> selectBuyerCountGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(areas));
        // 获取本周和上周的注册用户数量以及总数
        Map<String, Object> buyerRegistCountGroupByArea = this.selectBuyerRegistCountGroupByArea(params);
        // 查询从2018.1.1开始后注册的所有用户数
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", "2018-01-01 00:00:00");
        params02.put("endTime", params.get("endTime"));
        List<Map<String, Object>> allAddUpList = readMapper.selectRegisterCountGroupByAreaAndChina(params02);
        Map<String, Map<String, Object>> allAddUpMap = new HashMap<>();
        List<Integer> allAddUpCounts = new ArrayList<>(); //存放从18.1.1开始的各地区新注册数量
        Integer totalCount = allAddUpList.stream().map(m -> {
            String area = m.get("area").toString();
            allAddUpMap.put(area, m);
            return Integer.parseInt(m.get("registerCount").toString());
        }).reduce(0, (a, b) -> a + b);

        for (String area : areaList) {
            if (allAddUpMap.containsKey(area)) {
                Map<String, Object> map = allAddUpMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                allAddUpCounts.add(registerCount);
            } else {
                allAddUpCounts.add(0);
            }
        }
        //添加上合计数据
        allAddUpCounts.add(totalCount);
        // 添加上累计注册会员数量
        buyerRegistCountGroupByArea.put("historyCounts", allAddUpCounts);

        return buyerRegistCountGroupByArea;
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
        List<String> areaList = new ArrayList<>(Arrays.asList(areas));

        List<Map<String, Object>> currentWeekData = readMapper.selectInquiryCountWhereTimeGroupByCountry(params); // 本周数据
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", params.get("chainStartTime"));
        params02.put("endTime", params.get("startTime"));
        List<Map<String, Object>> lastWeekData = readMapper.selectInquiryCountWhereTimeGroupByCountry(params02); // 上周数据
        // 将数据转换为map数据，方便遍历地区时查找数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));

        // 编辑需要数据的地区，并填充返回结果
        List<Integer> currentWeekCounts = new ArrayList<>();//存放本周各地区新注册数量
        List<Integer> lastWeekCounts = new ArrayList<>();//存放上周各地区新注册数量
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
        List<String> areaList = new ArrayList<>(Arrays.asList(areas));

        List<Map<String, Object>> currentWeekData = readMapper.selectQuoteInfoWhereTimeGroupByCountry(params); // 本周报价数据
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", params.get("chainStartTime"));
        params02.put("endTime", params.get("startTime"));
        List<Map<String, Object>> lastWeekData = readMapper.selectQuoteInfoWhereTimeGroupByCountry(params02); // 上周报价数据
        // 将数据转换为map数据，方便遍历地区时查找数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));

        // 编辑需要数据的地区，并填充返回结果
        List<Integer> currentWeekCounts = new ArrayList<>();//存放本周各地区报价数量
        List<BigDecimal> currentWeekAmounts = new ArrayList<>();//存放本周各地区报价金额
        List<Integer> lastWeekCounts = new ArrayList<>();//存放上周各地区报价数量
        List<BigDecimal> lastWeekAmounts = new ArrayList<>();//存放上周各地区报价金额
        int currentWeekTotalNum = 0;
        BigDecimal currentWeekTotalPrice = BigDecimal.ZERO;
        int lastWeekTotalNum = 0;
        BigDecimal lastWeekTotalPrice = BigDecimal.ZERO;
        for (String area : areaList) {
            if (currentWeekDataMap.containsKey(area)) {
                Map<String, Object> map = currentWeekDataMap.get(area);
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                BigDecimal totalPrice = (BigDecimal) map.get("total_price");
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
        List<String> areaList = new ArrayList<>(Arrays.asList(areas));

        List<Map<String, Object>> currentWeekData = readMapper.selectQuoteInfoWhereTimeGroupByCountry(params); // 本周订单数据
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", params.get("chainStartTime"));
        params02.put("endTime", params.get("startTime"));
        List<Map<String, Object>> lastWeekData = readMapper.selectQuoteInfoWhereTimeGroupByCountry(params02); // 上周订单数据
        Map<String, Object> params03 = new HashMap<>();
        params03.put("startTime", "2018/01/01 00:00:00");
        params03.put("endTime", params.get("endTime"));
        List<Map<String, Object>> historyData = readMapper.selectQuoteInfoWhereTimeGroupByCountry(params03); // 历史订单数据
        // 将数据转换为map数据，方便遍历地区时查找数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));
        Map<String, Map<String, Object>> historyDataMap = historyData.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));

        // 编辑需要数据的地区，并填充返回结果
        List<Integer> currentWeekCounts = new ArrayList<>();//存放本周各地区订单数量
        List<BigDecimal> currentWeekAmounts = new ArrayList<>();//存放本周各地区订单金额
        List<Integer> lastWeekCounts = new ArrayList<>();//存放上周各地区订单数量
        List<BigDecimal> lastWeekAmounts = new ArrayList<>();//存放上周各地区订单金额
        List<Integer> historyCounts = new ArrayList<>();//存放历史各地区订单数量
        List<BigDecimal> historyAmounts = new ArrayList<>();//存放历史各地区订单金额
        int currentWeekTotalNum = 0;
        BigDecimal currentWeekTotalPrice = BigDecimal.ZERO;
        int lastWeekTotalNum = 0;
        BigDecimal lastWeekTotalPrice = BigDecimal.ZERO;
        int historyTotalNum = 0;
        BigDecimal historyTotalPrice = BigDecimal.ZERO;
        for (String area : areaList) {
            if (currentWeekDataMap.containsKey(area)) {
                Map<String, Object> map = currentWeekDataMap.get(area);
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                BigDecimal totalPrice = (BigDecimal) map.get("total_price");
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
}
