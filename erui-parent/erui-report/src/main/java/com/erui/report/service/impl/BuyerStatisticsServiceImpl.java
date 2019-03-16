package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.BuyerStatisticsMapper;
import com.erui.report.service.BuyerStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
@Service
public class BuyerStatisticsServiceImpl extends BaseService<BuyerStatisticsMapper> implements BuyerStatisticsService {
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

        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryMembershipBuyerList(params);

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
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> orderBuyerStatistics(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        String date2019 = "2019-01-01 00:00:00";
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
        params.put("startTime", date2019);
        aftStatisticsList = buyerStatisticsMapper.orderBuyerStatistics(params);
        if (startTime == null) {
            befStatisticsList = new ArrayList<>(aftStatisticsList);
        } else {
            Date oneBefDay = DateUtil.getDateAfter(DateUtil.parseString2DateNoException(startTime, DateUtil.FULL_FORMAT_STR), -1);
            params.put("endTime", DateUtil.format(DateUtil.FULL_FORMAT_STR, oneBefDay));
            befStatisticsList = buyerStatisticsMapper.orderBuyerStatistics(params);
        }
        // 合并结果集
        Map<String, Long> befMap = befStatisticsList.stream().collect(Collectors.toMap(vo -> vo.get("area_bn") + "_"  + vo.get("area_name") + "_"  + vo.get("country_bn") + "_"  + vo.get("country_name"), vo -> (Long) vo.get("num")));
        Map<String, Long> aftMap = aftStatisticsList.stream().collect(Collectors.toMap(vo -> vo.get("area_bn") + "_"  + vo.get("area_name") + "_"  + vo.get("country_bn") + "_"  + vo.get("country_name"), vo -> (Long) vo.get("num")));

        long befCount = 0;
        long curCount = 0;
        long aftCount = 0;
        // 合并操作
        for (Map<String, Object> map : curStatisticsList) {
            String key = map.get("area_bn") + "_"  + map.get("area_name") + "_"  + map.get("country_bn") + "_"  + map.get("country_name");
            if (befMap.containsKey(key)) {
                Long befNum = befMap.remove(key);
                befCount += befNum.longValue();
                map.put("befNum", befNum);
            }
            if (aftMap.containsKey(key)) {
                Long aftNum = aftMap.remove(key);
                aftCount += aftNum.longValue();
                map.put("aftNum", aftNum);
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
            }
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


}
