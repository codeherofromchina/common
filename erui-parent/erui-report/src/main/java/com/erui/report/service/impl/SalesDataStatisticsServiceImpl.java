package com.erui.report.service.impl;

import com.erui.report.dao.SalesDataStatisticsMapper;
import com.erui.report.service.SalesDataStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 供应商业务实现类
 */
@Service
public class SalesDataStatisticsServiceImpl implements SalesDataStatisticsService {
    private static final String UNKNOW = "未知";
    @Autowired
    private SalesDataStatisticsMapper salesDataStatisticsMapper;

    @Override
    public Map<String, List<Object>> agencySupplierCountryStatisticsData(Map<String, Object> params) {
        Map<String, List<Object>> result = null;
        List<Map<String, Object>> datas = salesDataStatisticsMapper.agencySupplierCountryStatisticsData(params);
        result = _handleSimpleData(result, datas);
        return result;
    }

    @Override
    public Map<String, List<Object>> agencyOrgStatisticsData(Map<String, Object> params) {
        Map<String, List<Object>> result = null;
        List<Map<String, Object>> datas = salesDataStatisticsMapper.agencySupplierOrgStatisticsData(params);
        result = _handleSimpleData(result, datas);
        return result;
    }

    /**
     * 分页查询询报价统计-询价失败列表
     *
     * @param params
     */
    public PageInfo<Map<String, Object>> inquiryFailListByPage(Map<String, Object> params) {
        PageHelper.startPage(params);
        List<Map<String, Object>> failList = salesDataStatisticsMapper.inquiryFailListByPage(params);
        PageInfo pageInfo = new PageInfo(failList);
        return pageInfo;
    }


    /**
     * 处理简单的名称和总数统计数据结果
     *
     * @param result
     * @param datas
     */
    private Map<String, List<Object>> _handleSimpleData(Map<String, List<Object>> result, List<Map<String, Object>> datas) {
        if (datas != null && datas.size() > 0) {
            result = new HashMap<>();
            List<Object> names = new ArrayList<>();
            List<Object> countNums = new ArrayList<>();
            for (Map<String, Object> data : datas) {
                Object name = data.get("name");
                names.add(name == null ? UNKNOW : name);
                countNums.add(data.get("total"));
            }
            result.put("name", names);
            result.put("counts", countNums);
        }
        return result;
    }

    /**
     * 活跃会员统计信息
     *
     * @param params {"startTime":"","endTime":"","sort":"1"}
     *               sort 1:正序/升序   其他：倒序/降序
     * @return
     */
    @Override
    public Map<String, List<Object>> activeMemberStatistics(Map<String, Object> params) {
        // 查找会员总数
        List<Map<String, Object>> totalBuyer = salesDataStatisticsMapper.buyerTotalCountGoupByCountry(params);
        // 查找活跃会员数
        List<Map<String, Object>> activeBuyer = salesDataStatisticsMapper.buyerActiveCountGoupByCountry(params);
        if (totalBuyer == null || totalBuyer.size() == 0 || activeBuyer == null || activeBuyer.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = _handleMemberNum(totalBuyer, activeBuyer);
        return result;
    }

    /**
     * 流失会员统计信息
     *
     * @param params {"startTime":"","endTime":"","sort":"1"}
     *               sort 1:正序/升序   其他：倒序/降序
     * @return
     */
    @Override
    public Map<String, List<Object>> lossMemberStatistics(Map<String, Object> params) {
        // 查找会员总数
        List<Map<String, Object>> totalBuyer = salesDataStatisticsMapper.buyerTotalCountGoupByCountry(params);
        // 查找流失会员数
        List<Map<String, Object>> lossBuyer = salesDataStatisticsMapper.buyerLossCountGoupByCountry(params);
        if (totalBuyer == null || totalBuyer.size() == 0 || lossBuyer == null || lossBuyer.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = _handleMemberNum(totalBuyer, lossBuyer);
        return result;
    }

    /**
     * 处理会员活跃和流失会员辅助方法
     *
     * @param totalBuyer
     * @param otherBuyer
     * @return
     */
    private Map<String, List<Object>> _handleMemberNum(List<Map<String, Object>> totalBuyer, List<Map<String, Object>> otherBuyer) {
        Map<String, Long> numMap = otherBuyer.parallelStream().collect(Collectors.toMap(vo -> (String) vo.get("countryName"), vo -> (Long) vo.get("total")));
        List<Object> countries = new ArrayList<>();
        List<Object> totalNums = new ArrayList<>();
        List<Object> nums = new ArrayList<>();
        List<Object> rateList = new ArrayList<>();
        for (Map<String, Object> map : totalBuyer) {
            String countryName = (String) map.get("countryName");
            Long total = (Long) map.get("total");
            Long num = numMap.get(countryName);
            if (num == null) {
                num = 0L;
            }
            double rate = num / (double) total;
            BigDecimal bigDecimalRate = new BigDecimal(rate,new MathContext(4, RoundingMode.DOWN));
            countries.add(countryName == null?"未知":countryName);
            totalNums.add(total);
            nums.add(num);
            rateList.add(bigDecimalRate);
        }
        Map<String, List<Object>> result = new HashMap<>();
        result.put("countries", countries);
        result.put("totalNums", totalNums);
        result.put("nums", nums);
        result.put("rateList", rateList);
        return result;
    }
}
