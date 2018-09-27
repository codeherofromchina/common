package com.erui.report.service.impl;

import com.erui.report.dao.MemberInfoStatisticsMapper;
import com.erui.report.service.MemberInfoService;
import com.erui.report.service.SalesmanNumsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
@Service
public class MemberInfoServiceImpl implements MemberInfoService {

    @Autowired
    private MemberInfoStatisticsMapper memberInfoStatisticsMapper;
    @Autowired
    private SalesmanNumsService salesmanNumsService;

    /**
     * 按照地区统计会员等级
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> membershipGradeByArea(Map<String, Object> params) {
        List<Map<String, Object>> gradeList = memberInfoStatisticsMapper.membershipGradeByArea(params);
        Map<String, List<Object>> result = _handleMemberGradeData(gradeList);
        return result;
    }

    @Override
    public Map<String, List<Object>> membershipGradeByCountry(Map<String, Object> params) {
        List<Map<String, Object>> gradeList = memberInfoStatisticsMapper.membershipGradeByCountry(params);
        Map<String, List<Object>> result = _handleMemberGradeData(gradeList);
        return result;
    }

    @Override
    public Map<String, List<Object>> visitStatisticsByArea(Map<String, Object> params) {
        List<Map<String, Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByArea(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(visitStatisticsData);
        return result;
    }

    @Override
    public Map<String, List<Object>> visitStatisticsByCountry(Map<String, Object> params) {
        List<Map<String, Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByCountry(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(visitStatisticsData);
        return result;
    }

    @Override
    public Map<String, List<Object>> membershipByArea(Map<String, Object> params) {
        List<Map<String, Object>> membershipNumList = memberInfoStatisticsMapper.membershipByArea(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(membershipNumList);
        return result;
    }

    @Override
    public Map<String, List<Object>> membershipByCountry(Map<String, Object> params) {
        List<Map<String, Object>> membershipNumList = memberInfoStatisticsMapper.membershipByCountry(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(membershipNumList);
        return result;
    }


    @Override
    public List<Map<String, Object>> singleCustomer(Map<String, Object> params) {
        List<Map<String, Object>> singleCustomerData = memberInfoStatisticsMapper.singleCustomer(params);
        List<Map<String, Object>> resultData = new ArrayList<>();
        int totalInquiryNum = 0;
        int totalOrderNum = 0;
        BigDecimal oneHundred = new BigDecimal(100);
        for (Map<String, Object> map : singleCustomerData) {
            String name = (String) map.get("name");
            if (StringUtils.isBlank(name)) {
                map.put("name", "其他");
            }
            BigDecimal inquiryNum = (BigDecimal) map.get("inquiryNum");
            totalInquiryNum += inquiryNum.intValue();
            BigDecimal orderNum = (BigDecimal) map.get("orderNum");
            totalOrderNum += orderNum.intValue();
            BigDecimal bigDecimal = (BigDecimal) map.get("rate");
            bigDecimal = bigDecimal.multiply(oneHundred, new MathContext(2, RoundingMode.DOWN));
            map.put("rate", bigDecimal);
            resultData.add(map);
        }
        Map<String, Object> total = new HashMap<>();
        total.put("name", "合计");
        total.put("inquiryNum", totalInquiryNum);
        total.put("orderNum", totalOrderNum);
        BigDecimal totalBigDecimal;
        if (totalInquiryNum == 0) {
            totalBigDecimal = BigDecimal.ZERO;
        } else {
            totalBigDecimal = new BigDecimal(totalOrderNum / totalInquiryNum * 100, new MathContext(2, RoundingMode.DOWN));
        }
        total.put("rate", totalBigDecimal);
        resultData.add(total);
        return resultData;
    }

    @Override
    public Map<String, List<Object>> signingBodyByArea(Map<String, Object> params) {
        List<Map<String, Object>> signingBodyList = memberInfoStatisticsMapper.signingBodyByArea(params);
        Map<String, List<Object>> result = _handleSigningBodyData(signingBodyList);
        return result;
    }

    @Override
    public Map<String, List<Object>> signingBodyByCountry(Map<String, Object> params) {
        List<Map<String, Object>> signingBodyList = memberInfoStatisticsMapper.signingBodyByCountry(params);
        Map<String, List<Object>> result = _handleSigningBodyData(signingBodyList);
        return result;
    }

    @Override
    public Map<String, List<Object>> signingBodyByOrg(Map<String, Object> params) {
        List<Map<String, Object>> signingBodyList = memberInfoStatisticsMapper.signingBodyByOrg(params);
        Map<String, List<Object>> result = _handleSigningBodyData(signingBodyList);
        return result;
    }

    @Override
    public Map<String, List<Object>> efficiencyByArea(Map<String, Object> params) {
        // 获取数据
        List<Map<String, Object>> orderTotalPriceList = memberInfoStatisticsMapper.orderTotalPriceByArea(params);
        Map<String, Integer> totalNumMap = salesmanNumsService.manTotalNumByArea(params);
        boolean ascFlag = "1".equals(params.get("sort"));
        // 处理数据
        Map<String, List<Object>> resultMap = _handleEfficiencyData(orderTotalPriceList, totalNumMap, ascFlag);
        return resultMap;
    }

    @Override
    public Map<String, List<Object>> efficiencyByCountry(Map<String, Object> params) {
        // 获取数据
        List<Map<String, Object>> orderTotalPriceList = memberInfoStatisticsMapper.orderTotalPriceByCountry(params);
        Map<String, Integer> totalNumMap = salesmanNumsService.manTotalNumByCountry(params);
        boolean ascFlag = "1".equals(params.get("sort"));
        // 处理数据
        Map<String, List<Object>> resultMap = _handleEfficiencyData(orderTotalPriceList, totalNumMap, ascFlag);
        return resultMap;
    }

    /**
     * 处理会员等级数据
     *
     * @param gradeList
     * @return
     */
    private Map<String, List<Object>> _handleMemberGradeData(List<Map<String, Object>> gradeList) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> nameList = new ArrayList<>();
        List<Object> normalGradeList = new ArrayList<>();
        List<Object> topGradeList = new ArrayList<>();
        for (Map<String, Object> map : gradeList) {
            Object name = map.get("name");
            Object normalGrade = map.get("normalGrade");
            Object topGrade = map.get("topGrade");
            nameList.add(name);
            normalGradeList.add(normalGrade);
            topGradeList.add(topGrade);
        }
        result.put("nameList", nameList);
        result.put("normalGradeList", normalGradeList);
        result.put("topGradeList", topGradeList);
        return result;
    }


    /**
     * 处理会员等级数据
     *
     * @param gradeList
     * @return
     */
    private Map<String, List<Object>> _handleVisitStatisticsData(List<Map<String, Object>> gradeList) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> nameList = new ArrayList<>();
        List<Object> numList = new ArrayList<>();
        for (Map<String, Object> map : gradeList) {
            Object name = map.get("name");
            Object num = map.get("num");
            nameList.add(name);
            numList.add(num);
        }
        result.put("nameList", nameList);
        result.put("numList", numList);
        return result;
    }

    /**
     * 处理签约主体数据
     *
     * @param signingBodyList
     * @return
     */
    private Map<String, List<Object>> _handleSigningBodyData(List<Map<String, Object>> signingBodyList) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> nameList = new ArrayList<>();
        List<Object> eruiNumList = new ArrayList<>();
        List<Object> otherNumList = new ArrayList<>();
        for (Map<String, Object> map : signingBodyList) {
            Object name = map.get("name");
            Object eruiNum = map.get("eruiNum");
            Object otherNum = map.get("otherNum");
            nameList.add(name == null ? "其他" : name);
            eruiNumList.add(eruiNum);
            otherNumList.add(otherNum);
        }
        result.put("nameList", nameList);
        result.put("eruiNumList", eruiNumList);
        result.put("otherNumList", otherNumList);
        return result;
    }

    /**
     * 处理人均效能统计数据
     *
     * @param orderTotalPriceList
     * @param totalNumMap
     * @param ascFlag             true:升序  false:降序
     * @return
     */
    private Map<String, List<Object>> _handleEfficiencyData(List<Map<String, Object>> orderTotalPriceList, Map<String, Integer> totalNumMap, boolean ascFlag) {
        Set<String> areaNameList = new HashSet<>();
        areaNameList.addAll(totalNumMap.keySet());
        Map<String, BigDecimal> orderTotalPriceMap = orderTotalPriceList.stream().collect(Collectors.toMap(vo -> {
            String name = (String) vo.get("name");
            areaNameList.add(name);
            return name;
        }, vo -> (BigDecimal) vo.get("totalPriceUsd")));

        int totalNum = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal tenThousand = new BigDecimal(10000);
        List<Object[]> list = new ArrayList<>();
        for (String areaName : areaNameList) {
            Integer num = totalNumMap.get(areaName);
            BigDecimal price = orderTotalPriceMap.get(areaName);
            if (num == null) {
                num = 0;
            }
            if (price == null) {
                price = BigDecimal.ZERO;
            }

            Object[] objArr = new Object[2];
            objArr[0] = areaName;
            if (num == 0) {
                objArr[1] = BigDecimal.ZERO;
            } else {
                objArr[1] = price.divide(new BigDecimal(num),0, BigDecimal.ROUND_DOWN).divide(tenThousand, 2, BigDecimal.ROUND_DOWN);
            }
            list.add(objArr);

            totalNum += num;
            totalPrice = totalPrice.add(price);
        }

        list.sort(new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                BigDecimal bd01 = (BigDecimal) o1[1];
                BigDecimal bd02 = (BigDecimal) o2[1];
                if (ascFlag) {
                    return bd01.compareTo(bd02);
                } else {
                    return -bd01.compareTo(bd02);
                }
            }
        });

        List<Object> nameList = new ArrayList<>();
        List<Object> dataList = new ArrayList<>();
        list.stream().forEach(vo -> {
            nameList.add((String) vo[0]);
            dataList.add((BigDecimal) vo[1]);
        });
        nameList.add("合计");
        dataList.add(totalNum == 0 ? BigDecimal.ZERO : totalPrice.divide(new BigDecimal(totalNum),0, BigDecimal.ROUND_DOWN).divide(tenThousand, 2, BigDecimal.ROUND_DOWN));
        Map<String, List<Object>> result = new HashMap<>();
        result.put("nameList", nameList);
        result.put("dataList", dataList);

        return result;
    }


}
