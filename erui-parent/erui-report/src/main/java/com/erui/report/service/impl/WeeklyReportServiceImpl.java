package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.WeeklyReportMapper;
import com.erui.report.service.WeeklyReportService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeeklyReportServiceImpl extends BaseService<WeeklyReportMapper> implements WeeklyReportService {

    private static final String[] AREAS = new String[]{"北美", "泛俄", "非洲", "南美", "欧洲", "亚太", "中东", "中国"};
    private static final String[] ORGS = new String[]{"易瑞-钻完井设备", "易瑞-钻完井设备", "易瑞-电力电工", "易瑞-工业品设备", "易瑞-安防和劳保设备", "油田设备", "康博瑞"};
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
        Map<String, Map<String, Object>> thisWeekMap = thisWeekList.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekMap = lastWeekList.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));

        List<Integer> currentWeekCounts = new ArrayList<>();//存放本周各地区新注册数量
        List<Integer> lastWeekCounts = new ArrayList<>();//存放上周各地区新注册数量
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
        result.put("AREAS", areaList);
        result.put("currentWeekCounts", currentWeekCounts);
        result.put("lastWeekCounts", lastWeekCounts);
        return result;
    }

    @Override
    public Map<String, Object> selectBuyerCountGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(AREAS));
        // 获取本周和上周的注册用户数量以及总数
        Map<String, Object> buyerRegistCountGroupByArea = this.selectBuyerRegistCountGroupByArea(params);
        // 查询从2018.1.1开始后注册的所有用户数
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", "2018-01-01 00:00:00");
        params02.put("endTime", params.get("endTime"));
        List<Map<String, Object>> allAddUpList = readMapper.selectRegisterCountGroupByAreaAndChina(params02);
        Map<String, Map<String, Object>> allAddUpMap = allAddUpList.stream().collect(Collectors.toMap(vo -> (String) vo.get("area"), vo -> vo));

        List<Integer> allAddUpCounts = new ArrayList<>(); //存放从18.1.1开始的各地区新注册数量
        int totalCount = 0;
        for (String area : areaList) {
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
        BigDecimal wanDollor = new BigDecimal("10000");
        for (String area : areaList) {
            if (currentWeekDataMap.containsKey(area)) {
                Map<String, Object> map = currentWeekDataMap.get(area);
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                BigDecimal totalPrice = (BigDecimal) map.get("total_price");
                totalPrice = totalPrice.divide(wanDollor, 2, RoundingMode.HALF_DOWN);
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
                totalPrice = totalPrice.divide(wanDollor, 2, RoundingMode.HALF_DOWN);
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
        params03.put("startTime", "2018/01/01 00:00:00");
        params03.put("endTime", params.get("endTime"));
        List<Map<String, Object>> historyData = readMapper.selectOrderInfoWhereTimeGroupByCountry(params03); // 历史订单数据
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
        BigDecimal wanDollor = new BigDecimal("10000");
        for (String area : areaList) {
            if (currentWeekDataMap.containsKey(area)) {
                Map<String, Object> map = currentWeekDataMap.get(area);
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                BigDecimal totalPrice = (BigDecimal) map.get("total_price");
                totalPrice = totalPrice.divide(wanDollor, 2, BigDecimal.ROUND_DOWN);
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
                totalPrice = totalPrice.divide(wanDollor, 2, BigDecimal.ROUND_DOWN);
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
                totalPrice = totalPrice.divide(wanDollor, 2, BigDecimal.ROUND_DOWN);
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
            lastWeekData = readMapper.selectOrderInfoWhereTimeGroupByCountry(params02); // 上周订单数据
        } else {
            lastWeekData = new ArrayList<>();
        }
        // 将数据转换为map数据，方便遍历事业部时查找数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));


        List<String> orgList = new ArrayList<>();//存放事业部列表
        List<Integer> currentWeekCounts = new ArrayList<>();//存放本周各事业部询单数量
        List<Integer> lastWeekCounts = new ArrayList<>();//存放上周个事业部询单数量
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
        Integer curWeekOtherOrgCount = currentWeekDataMap.values().parallelStream().map(vo -> ((Long) vo.get("total")).intValue()).reduce(0, (a, b) -> a + b);
        Integer lastWeekOtherOrgCount = lastWeekDataMap.values().parallelStream().map(vo -> ((Long) vo.get("total")).intValue()).reduce(0, (a, b) -> a + b);
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
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));

        List<String> orgList = new ArrayList<>();//存放事业部列表
        List<Integer> currentWeekCounts = new ArrayList<>();//存放本周各事业部报价数量
        List<BigDecimal> currentWeekAmounts = new ArrayList<>();//存放本周事业部报价金额
        for (String org : ORGS) {
            orgList.add(org);
            // 本周指定事业部报价数量处理
            Map<String, Object> curMap = currentWeekDataMap.remove(org);
            if (curMap != null) {
                Long totalNum = (Long) curMap.get("total_num");
                BigDecimal totalPrice = (BigDecimal) curMap.get("total_price");
                totalPrice = totalPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_DOWN);
                currentWeekCounts.add(totalNum.intValue());
                currentWeekAmounts.add(totalPrice);
            } else {
                currentWeekCounts.add(0);
                currentWeekAmounts.add(BigDecimal.ZERO);
            }
        }

        //处理其它事业部数据
        Integer curWeekOtherOrgCount = currentWeekDataMap.values().parallelStream().map(vo -> ((Long) vo.get("total_num")).intValue()).reduce(0, (a, b) -> a + b);
        BigDecimal curWeekOtherOrgAmount = currentWeekDataMap.values().parallelStream().map(vo -> (BigDecimal) vo.get("total_price")).reduce(BigDecimal.ZERO, (a, b) -> a.add(b.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_DOWN)));
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
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        // 处理数据
        List<String> orgList = new ArrayList<>();//存放事业部列表
        List<Long> currentWeekTimes = new ArrayList<>();//存放本周各事业部报价用时
        List<Long> lastWeekTimes = new ArrayList<>();//存放上周各事业部报价用时
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
                long totalTime = ((BigDecimal) curMap.get("diff")).longValue();
                lastWeekTimes.add(totalTime);
            } else {
                lastWeekTimes.add(0L);
            }
        }
        //处理其它事业部数据
        Long curWeekOtherOrgTime = currentWeekDataMap.values().parallelStream().map(vo -> ((BigDecimal) vo.get("diff")).longValue()).reduce(0L, (a, b) -> a + b);
        Long lastWeekOtherOrgTime = lastWeekDataMap.values().parallelStream().map(vo -> ((BigDecimal) vo.get("diff")).longValue()).reduce(0L, (a, b) -> a + b);
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
        List<Map<String, Object>> currentWeekData = readMapper.selectOrderInfoWhereTimeGroupByOrg(params);// 本周订单金额和数量
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
        params03.put("startTime", "2018-01-01 00:00:00");
        params03.put("endTime", "2018-07-09 23:59:59");
        List<Map<String, Object>> historyWeekData = readMapper.selectOrderInfoWhereTimeGroupByOrg(params03); // 历史订单数据

        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> lastWeekDataMap = lastWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> historyDataMap = historyWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));

        // 处理数据
        List<String> orgList = new ArrayList<>();//存放事业部列表
        List<Integer> currentWeekCounts = new ArrayList<>();//存放本周各事业部订单数量
        List<Integer> lastWeekCounts = new ArrayList<>();//存放上周各事业部订单数量
        List<BigDecimal> currentWeekAmount = new ArrayList<>();//存放本周各事业部订单金额
        List<BigDecimal> lastWeekAmount = new ArrayList<>();//存放上周各事业部订单金额
        List<BigDecimal> historyAmount = new ArrayList<>();//存放各事业部订单金额

        for (String org : ORGS) {
            orgList.add(org);
            // 本周
            Map<String, Object> curMap = currentWeekDataMap.remove(org);
            if (curMap != null) {
                int totalNum = ((Long) curMap.get("total_num")).intValue();
                BigDecimal totalPrice = (BigDecimal) curMap.get("total_price");
                totalPrice = totalPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_DOWN);
                currentWeekCounts.add(totalNum);
                currentWeekAmount.add(totalPrice);
            } else {
                currentWeekCounts.add(0);
                currentWeekAmount.add(BigDecimal.ZERO);
            }
            // 上周指定事业部报价用时处理
            Map<String, Object> lastMap = lastWeekDataMap.remove(org);
            if (lastMap != null) {
                int totalNum = ((Long) lastMap.get("total_num")).intValue();
                BigDecimal totalPrice = (BigDecimal) lastMap.get("total_price");
                totalPrice = totalPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_DOWN);
                lastWeekCounts.add(totalNum);
                lastWeekAmount.add(totalPrice);
            } else {
                lastWeekCounts.add(0);
                lastWeekAmount.add(BigDecimal.ZERO);
            }

            Map<String, Object> historyMap = historyDataMap.remove(org);
            if (historyMap != null) {
                BigDecimal totalPrice = (BigDecimal) historyMap.get("total_price");
                totalPrice = totalPrice.divide(WAN_DOLLOR, 2, BigDecimal.ROUND_DOWN);
                historyAmount.add(totalPrice);
            } else {
                historyAmount.add(BigDecimal.ZERO);
            }
        }
        // 其他数据处理
        Integer curWeekOtherOrgCount = currentWeekDataMap.values().parallelStream().map(vo -> ((Long) vo.get("total_num")).intValue()).reduce(0, (a, b) -> a + b);
        BigDecimal curWeekOtherOrgPrice = currentWeekDataMap.values().parallelStream().map(vo -> (BigDecimal) vo.get("total_price")).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        Integer lastWeekOtherOrgCount = lastWeekDataMap.values().parallelStream().map(vo -> ((Long) vo.get("diff")).intValue()).reduce(0, (a, b) -> a + b);
        BigDecimal lastWeekOtherOrgPrice = lastWeekDataMap.values().parallelStream().map(vo -> (BigDecimal) vo.get("total_price")).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        BigDecimal historyOtherOrgPrice = historyDataMap.values().parallelStream().map(vo -> (BigDecimal) vo.get("total_price")).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        orgList.add("其他");
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
        params02.put("startTime", "2018-01-01 00:00:00");
        params02.put("endTime", "2018-07-19 23:59:59");
        List<Map<String, Object>> historyData = readMapper.selectSupplierNumWhereTimeGroupByOrg(params02); // 上周报价用时数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> historyDataMap = historyData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        // 处理数据
        List<String> orgList = new ArrayList<>();//存放事业部列表
        List<Integer> currentWeekCounts = new ArrayList<>();//存放本周各事业部供应商数
        List<Integer> historyCounts = new ArrayList<>();//存放历史个事业部供应商数
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
        Integer curWeekOtherOrgNum = currentWeekDataMap.values().parallelStream().map(vo -> ((Long) vo.get("total")).intValue()).reduce(0, (a, b) -> a + b);
        Integer historyOtherOrgNum = historyDataMap.values().parallelStream().map(vo -> ((Long) vo.get("total")).intValue()).reduce(0, (a, b) -> a + b);
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
    public Map<String, Object> selectSpuAndSkuNumInfoGroupByOrg(Map<String, Object> params) {
        // 准备数据
        List<Map<String, Object>> currentWeekData = readMapper.selectSpuAndSkuNumWhereTimeGroupByOrg(params);
        Map<String, Object> params02 = new HashMap<>();
        params02.put("startTime", "2018-01-01 00:00:00");
        params02.put("endTime", "2018-07-19 23:59:59");
        List<Map<String, Object>> historyData = readMapper.selectSpuAndSkuNumWhereTimeGroupByOrg(params02); // 累计spu/sku数据
        Map<String, Map<String, Object>> currentWeekDataMap = currentWeekData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        Map<String, Map<String, Object>> historyDataMap = historyData.stream().collect(Collectors.toMap(vo -> (String) vo.get("name"), vo -> vo));
        // 处理数据
        List<String> orgList = new ArrayList<>();//存放事业部列表
        List<Integer> currentWeekSpuCounts = new ArrayList<>();
        List<Integer> currentWeekSkuCounts = new ArrayList<>();
        List<Integer> historySpuCounts = new ArrayList<>();
        List<Integer> historySkuCounts = new ArrayList<>();
        for (String org : ORGS) {
            orgList.add(org);
            // 本周指定事业部报价用时处理
            Map<String, Object> curMap = currentWeekDataMap.remove(org);
            if (curMap != null) {
                int totalSpu = ((Long) curMap.get("total_spu")).intValue();
                int totalSku = ((BigDecimal) curMap.get("total_sku")).intValue();
                currentWeekSpuCounts.add(totalSpu);
                currentWeekSkuCounts.add(totalSku);
            } else {
                currentWeekSpuCounts.add(0);
                currentWeekSkuCounts.add(0);
            }
            // 上周指定事业部报价用时处理
            Map<String, Object> historyMap = historyDataMap.remove(org);
            if (historyMap != null) {
                int totalSpu = ((Long) historyMap.get("total_spu")).intValue();
                int totalSku = ((BigDecimal) historyMap.get("total_sku")).intValue();
                historySpuCounts.add(totalSpu);
                historySkuCounts.add(totalSku);
            } else {
                historySpuCounts.add(0);
                historySkuCounts.add(0);
            }
        }
        //处理其它事业部数据
        Integer curWeekOtherOrgSpuNum = currentWeekDataMap.values().parallelStream().map(vo -> ((Long) vo.get("total_spu")).intValue()).reduce(0, (a, b) -> a + b);
        Integer curWeekOtherOrgSkuNum = currentWeekDataMap.values().parallelStream().map(vo -> ((BigDecimal) vo.get("total_sku")).intValue()).reduce(0, (a, b) -> a + b);
        Integer historyOtherOrgSpuNum = historyDataMap.values().parallelStream().map(vo -> ((Long) vo.get("total_spu")).intValue()).reduce(0, (a, b) -> a + b);
        Integer historyOtherOrgSkuNum = historyDataMap.values().parallelStream().map(vo -> ((BigDecimal) vo.get("total_sku")).intValue()).reduce(0, (a, b) -> a + b);
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
}
