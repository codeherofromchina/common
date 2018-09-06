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
            BigDecimal bigDecimalRate = new BigDecimal(rate, new MathContext(4, RoundingMode.DOWN));
            countries.add(countryName == null ? UNKNOW : countryName);
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


    @Override
    public Map<String, List<Object>> orgQuoteTotalCostTime(Map<String, Object> params) {
        // 事业部报价用时
        List<Map<String, Object>> totalBuyer = salesDataStatisticsMapper.orgQuoteTotalCostTime(params);
        if (totalBuyer == null || totalBuyer.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> names = new ArrayList<>();
        List<Object> costTimeList = new ArrayList<>();
        for (Map<String, Object> map : totalBuyer) {
            String name = (String) map.get("name");
            BigDecimal costTimes = (BigDecimal) map.get("costTimes");
            names.add(name);
            costTimeList.add(costTimes.setScale(4, BigDecimal.ROUND_DOWN));
        }
        result.put("orgNames", names);
        result.put("costTimes", costTimeList);
        return result;
    }

    /**
     * 报价金额
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> quoteAmountGroupOrg(Map<String, Object> params) {
        // 总报价金额
        List<Map<String, Object>> quoteTotalAmount = salesDataStatisticsMapper.quoteTotalAmountGroupByArea(params);
        // 会员报价总金额
        List<Map<String, Object>> memberQuoteAmount = salesDataStatisticsMapper.memberQuoteAmountGroupByArea(params);
        // 处理数据
        if (quoteTotalAmount == null || quoteTotalAmount.size() == 0) {
            return null;
        }
        Map<String, BigDecimal> memberQuoteAmountMap = memberQuoteAmount.parallelStream().collect(Collectors.toMap(vo -> (String) vo.get("areaName"), vo -> (BigDecimal) vo.get("totalAmount")));

        Map<String, List<Object>> result = new HashMap<>();
        List<Object> names = new ArrayList<>();
        List<Object> totalAmounts = new ArrayList<>();
        List<Object> memTotalAmounts = new ArrayList<>();
        for (Map<String, Object> map : quoteTotalAmount) {
            String areaName = (String) map.get("areaName");
            BigDecimal totalAmount = (BigDecimal) map.get("totalAmount");
            names.add(areaName == null ? UNKNOW : areaName);
            totalAmounts.add(totalAmount);
            BigDecimal memTotalAmount = memberQuoteAmountMap.remove(areaName);
            if (memTotalAmount != null) {
                memTotalAmounts.add(memTotalAmount);
            } else {
                memTotalAmounts.add(BigDecimal.ZERO);
            }
        }
        result.put("names", names);
        result.put("totalAmounts", totalAmounts);
        result.put("memTotalAmounts", memTotalAmounts);
        return result;
    }


    @Override
    public Map<String, List<Object>> inquiryNumbersGroupOrg(Map<String, Object> params) {
        // 总询单数量
        List<Map<String, Object>> inquiryTotalNum = salesDataStatisticsMapper.inquiryTotalNumGroupByArea(params);
        // 会员询单总数量
        List<Map<String, Object>> memberInquiryNum = salesDataStatisticsMapper.memberInquiryNumGroupByArea(params);
        // 处理数据
        if (inquiryTotalNum == null || inquiryTotalNum.size() == 0) {
            return null;
        }
        Map<String, Long> memberInquiryNumMap = memberInquiryNum.parallelStream().collect(Collectors.toMap(vo -> (String) vo.get("areaName"), vo -> (Long) vo.get("total")));

        Map<String, List<Object>> result = new HashMap<>();
        List<Object> names = new ArrayList<>();
        List<Object> totalNums = new ArrayList<>();
        List<Object> memTotalNums = new ArrayList<>();
        for (Map<String, Object> map : inquiryTotalNum) {
            String areaName = (String) map.get("areaName");
            Long total = (Long) map.get("total");
            names.add(areaName == null ? UNKNOW : areaName);
            totalNums.add(total);
            Long memTotalNum = memberInquiryNumMap.remove(areaName);
            if (memTotalNum != null) {
                memTotalNums.add(memTotalNum);
            } else {
                memTotalNums.add(0L);
            }
        }
        result.put("names", names);
        result.put("totalNums", totalNums);
        result.put("memTotalNums", memTotalNums);
        return result;
    }

    /**
     * 报价数量按事业部统计
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> quoteNumbersGroupOrg(Map<String, Object> params) {
        // 总报价数量
        List<Map<String, Object>> quoteTotalNum = salesDataStatisticsMapper.quoteTotalNumGroupByArea(params);
        // 会员报价总数量
        List<Map<String, Object>> memberQuoteNum = salesDataStatisticsMapper.memberQuoteNumGroupByArea(params);
        // 处理数据
        if (quoteTotalNum == null || quoteTotalNum.size() == 0) {
            return null;
        }
        Map<String, Long> memberQuoteNumMap = memberQuoteNum.parallelStream().collect(Collectors.toMap(vo -> (String) vo.get("areaName"), vo -> (Long) vo.get("total")));

        Map<String, List<Object>> result = new HashMap<>();
        List<Object> names = new ArrayList<>();
        List<Object> totalNums = new ArrayList<>();
        List<Object> memTotalNums = new ArrayList<>();
        for (Map<String, Object> map : quoteTotalNum) {
            String areaName = (String) map.get("areaName");
            Long total = (Long) map.get("total");
            names.add(areaName == null ? UNKNOW : areaName);
            totalNums.add(total);
            Long memTotalNum = memberQuoteNumMap.remove(areaName);
            if (memTotalNum != null) {
                memTotalNums.add(memTotalNum);
            } else {
                memTotalNums.add(0L);

            }
        }
        result.put("names", names);
        result.put("totalNums", totalNums);
        result.put("memTotalNums", memTotalNums);
        return result;
    }
}
