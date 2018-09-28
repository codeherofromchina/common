package com.erui.report.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.SalesDataStatisticsMapper;
import com.erui.report.model.PerformanceIndicators;
import com.erui.report.service.PerformanceIndicatorsService;
import com.erui.report.service.SalesDataStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 供应商业务实现类
 */
@Service
public class SalesDataStatisticsServiceImpl implements SalesDataStatisticsService {
    private static final String UNKNOW = "其他";
    @Autowired
    private SalesDataStatisticsMapper salesDataStatisticsMapper;
    // 业绩指标业务类
    @Autowired
    private PerformanceIndicatorsService performanceIndicatorsService;

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


    @Override
    public Map<String, List<Object>> agencyAreaStatisticsData(Map<String, Object> params) {
        Map<String, List<Object>> result = null;
        List<Map<String, Object>> datas = salesDataStatisticsMapper.agencySupplierAreaStatisticsData(params);
        result = _handleSimpleData(result, datas);
        return result;
    }

    /**
     * 分页查询询报价统计-询价失败列表
     *
     * @param params
     */
    public PageInfo<Map<String, Object>> inquiryFailListByPage(Map<String, Object> params) {
        PageHelper.startPage((Integer) params.get("pageNum"), (Integer) params.get("pageSize"));
        List<Map<String, Object>> failList = salesDataStatisticsMapper.inquiryFailListByPage(params);
        for (Map<String, Object> failMap : failList) {
            Date createdDate = (Date) failMap.get("created_at");
            if (createdDate != null) {


                failMap.put("created_at", DateUtil.format(DateUtil.FULL_FORMAT_STR, createdDate));


            }
        }
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
        List<Map<String, Object>> totalBuyer = salesDataStatisticsMapper.buyerTotalCountGoupByArea(params);
        // 查找活跃会员数
        List<Map<String, Object>> activeBuyer = salesDataStatisticsMapper.buyerActiveCountGoupByArea(params);
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
        List<Map<String, Object>> totalBuyer = salesDataStatisticsMapper.buyerTotalCountGoupByArea(params);
        // 查找流失会员数
        List<Map<String, Object>> lossBuyer = salesDataStatisticsMapper.buyerLossCountGoupByArea(params);
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
            double rate = num / (double) total * 100;
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
    public Map<String, List<Object>> quoteAmountGroupArea(Map<String, Object> params) {
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
        BigDecimal tenThousand = new BigDecimal(10000);
        for (Map<String, Object> map : quoteTotalAmount) {
            String areaName = (String) map.get("areaName");
            BigDecimal totalAmount = (BigDecimal) map.get("totalAmount");
            names.add(areaName == null ? UNKNOW : areaName);
            totalAmounts.add(totalAmount.divide(tenThousand, 2, BigDecimal.ROUND_DOWN));
            BigDecimal memTotalAmount = memberQuoteAmountMap.remove(areaName);
            if (memTotalAmount != null) {
                memTotalAmounts.add(memTotalAmount.divide(tenThousand, 2, BigDecimal.ROUND_DOWN));
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
    public Map<String, List<Object>> inquiryNumbersGroupArea(Map<String, Object> params) {
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
    public Map<String, List<Object>> quoteNumbersGroupArea(Map<String, Object> params) {
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


    @Override
    public Map<String, List<Object>> orderStatisticsWholeInfoGroupByArea(Map<String, Object> params) {
        // 总询单数量
        List<Map<String, Object>> orderInfoGroupArea = salesDataStatisticsMapper.orderStatisticsWholeInfoGroupByArea(params);
        if (orderInfoGroupArea == null || orderInfoGroupArea.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> areaNames = new ArrayList<>();
        List<Object> totalNums = new ArrayList<>();
        List<Object> totalAmounts = new ArrayList<>();
        for (Map<String, Object> map : orderInfoGroupArea) {
            String orgName = (String) map.get("areaName");
            BigDecimal totalAmount = (BigDecimal) map.get("totalAmount");
            Long totalNum = (Long) map.get("totalNum");
            areaNames.add(orgName == null ? UNKNOW : orgName);
            totalNums.add(totalNum == null ? 0L : totalNum);
            totalAmounts.add(totalAmount == null ? BigDecimal.ZERO : totalAmount.setScale(4,BigDecimal.ROUND_DOWN));
        }
        result.put("names", areaNames);
        result.put("totalNums", totalNums);
        result.put("totalAmounts", totalAmounts);
        return result;
    }

    @Override
    public Map<String, List<Object>> orderStatisticsWholeInfoGroupByCountry(Map<String, Object> params) {
        // 总询单数量
        List<Map<String, Object>> orderInfoGroupCountry = salesDataStatisticsMapper.orderStatisticsWholeInfoGroupByCountry(params);
        if (orderInfoGroupCountry == null || orderInfoGroupCountry.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> names = new ArrayList<>();
        List<Object> totalNums = new ArrayList<>();
        List<Object> totalAmounts = new ArrayList<>();
        for (Map<String, Object> map : orderInfoGroupCountry) {
            String countryName = (String) map.get("countryName");
            BigDecimal totalAmount = (BigDecimal) map.get("totalAmount");
            Long totalNum = (Long) map.get("totalNum");
            names.add(countryName == null ? UNKNOW : countryName);
            totalNums.add(totalNum == null ? 0L : totalNum);
            totalAmounts.add(totalAmount == null ? BigDecimal.ZERO : totalAmount.setScale(4,BigDecimal.ROUND_DOWN));
        }
        result.put("names", names);
        result.put("totalNums", totalNums);
        result.put("totalAmounts", totalAmounts);
        return result;
    }


    @Override
    public Map<String, List<Object>> orderStatisticsWholeInfoGroupByOrg(Map<String, Object> params) {
        // 总询单数量
        List<Map<String, Object>> orderInfoGroupOrg = salesDataStatisticsMapper.orderStatisticsWholeInfoGroupByOrg(params);
        if (orderInfoGroupOrg == null || orderInfoGroupOrg.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> orgNames = new ArrayList<>();
        List<Object> totalNums = new ArrayList<>();
        List<Object> totalAmounts = new ArrayList<>();
        for (Map<String, Object> map : orderInfoGroupOrg) {
            String orgName = (String) map.get("orgName");
            BigDecimal totalAmount = (BigDecimal) map.get("totalAmount");
            Long totalNum = (Long) map.get("totalNum");
            orgNames.add(orgName == null ? UNKNOW : orgName);
            totalNums.add(totalNum == null ? 0L : totalNum);
            totalAmounts.add(totalAmount == null ? BigDecimal.ZERO : totalAmount.setScale(4,BigDecimal.ROUND_DOWN));
        }
        result.put("names", orgNames);
        result.put("totalNums", totalNums);
        result.put("totalAmounts", totalAmounts);
        return result;
    }

    /**
     * 订单数据统计-利润-事业部利润率
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> orderStatisticsProfitPercentGroupByOrg(Map<String, Object> params) {
        // 订单信息的事业部利润率
        List<Map<String, Object>> orgProfitPercent = salesDataStatisticsMapper.orderStatisticsProfitPercentGroupByOrg(params);
        if (orgProfitPercent == null || orgProfitPercent.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = _handleOrderProfitPercent(orgProfitPercent);
        return result;
    }


    @Override
    public Map<String, List<Object>> orderStatisticsProfitPercentGroupByArea(Map<String, Object> params) {
        // 订单信息的地区利润率
        List<Map<String, Object>> areaProfitPercent = salesDataStatisticsMapper.orderStatisticsProfitPercentGroupByArea(params);
        if (areaProfitPercent == null || areaProfitPercent.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = _handleOrderProfitPercent(areaProfitPercent);
        return result;
    }


    @Override
    public Map<String, List<Object>> orderStatisticsProfitPercentGroupByCountry(Map<String, Object> params) {
        // 订单信息的国家利润率
        List<Map<String, Object>> countryProfitPercent = salesDataStatisticsMapper.orderStatisticsProfitPercentGroupByCountry(params);
        if (countryProfitPercent == null || countryProfitPercent.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = _handleOrderProfitPercent(countryProfitPercent);
        return result;
    }


    /**
     * 处理订单信息的利润率
     *
     * @param profitPercentInfo
     * @return
     */
    private Map<String, List<Object>> _handleOrderProfitPercent(List<Map<String, Object>> profitPercentInfo) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> names = new ArrayList<>();
        List<Object> profitPercentList = new ArrayList<>();
        for (Map<String, Object> map : profitPercentInfo) {
            String name = (String) map.get("name");
            BigDecimal profitPercent = (BigDecimal) map.get("profitPercent");
            names.add(name == null ? UNKNOW : name);
            profitPercentList.add(profitPercent == null ? BigDecimal.ZERO : profitPercent.setScale(2, BigDecimal.ROUND_DOWN));
        }
        result.put("names", names);
        result.put("profitPercents", profitPercentList);
        return result;
    }


    /**
     * 事业部成单数量和成单率信息
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> orderStatisticsMonoRateGroupByOrg(Map<String, Object> params) {
        // 订单信息的事业部成单率
        List<Map<String, Object>> countryMonoRate = salesDataStatisticsMapper.orderStatisticsMonoRateGroupByOrg(params);
        if (countryMonoRate == null || countryMonoRate.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = _handleOrderMonoRate(countryMonoRate);
        return result;
    }

    /**
     * 地区成单数量和成单率信息
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> orderStatisticsMonoRateGroupByArea(Map<String, Object> params) {
        // 订单信息的地区成单率
        List<Map<String, Object>> countryMonoRate = salesDataStatisticsMapper.orderStatisticsMonoRateGroupByArea(params);
        if (countryMonoRate == null || countryMonoRate.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = _handleOrderMonoRate(countryMonoRate);
        return result;
    }

    /**
     * 国家成单数量和成单率信息
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> orderStatisticsMonoRateGroupByCountry(Map<String, Object> params) {
        // 订单信息的国家利润率
        List<Map<String, Object>> countryMonoRate = salesDataStatisticsMapper.orderStatisticsMonoRateGroupByCountry(params);
        if (countryMonoRate == null || countryMonoRate.size() == 0) {
            return null;
        }
        Map<String, List<Object>> result = _handleOrderMonoRate(countryMonoRate);
        return result;
    }


    /**
     * 处理订单信息的成单率率
     *
     * @param monoRateInfo
     * @return
     */
    private Map<String, List<Object>> _handleOrderMonoRate(List<Map<String, Object>> monoRateInfo) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> names = new ArrayList<>();
        List<Object> quoteNums = new ArrayList<>();
        List<Object> doneNums = new ArrayList<>();
        List<Object> rates = new ArrayList<>();
        BigDecimal oneHundred = new BigDecimal(100);
        for (Map<String, Object> map : monoRateInfo) {
            String name = (String) map.get("name");
            BigDecimal quoteNum = (BigDecimal) map.get("quoteNum");
            BigDecimal doneNum = (BigDecimal) map.get("doneNum");
            BigDecimal rate = (BigDecimal) map.get("rate");
            names.add(name == null ? UNKNOW : name);
            quoteNums.add(quoteNum == null ? 0L : quoteNum.longValue());
            doneNums.add(doneNum == null ? 0L : doneNum.longValue());
            rates.add(rate == null ? BigDecimal.ZERO : rate.multiply(oneHundred, new MathContext(2, RoundingMode.HALF_UP)));
        }
        result.put("names", names);
        result.put("quoteNums", quoteNums);
        result.put("doneNums", doneNums);
        result.put("rates", rates);
        return result;
    }


    /**
     * 订单数据统计--购买力
     *
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> orderInfoPurchasingPower(Map<String, Object> params) {
        PageHelper.startPage((Integer) params.get("pageNum"), (Integer) params.get("pageSize"));
        List<Map<String, Object>> purchasingPowerList = salesDataStatisticsMapper.orderInfoPurchasingPower(params);
        PageInfo pageInfo = new PageInfo(purchasingPowerList);
        return pageInfo;
    }

    /**
     * 订单数据统计--复购周期
     *
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> orderInfoBuyCycle(Map<String, Object> params) {
        PageHelper.startPage((Integer) params.get("pageNum"), (Integer) params.get("pageSize"));
        List<Map<String, Object>> purchasingPowerList = salesDataStatisticsMapper.orderInfoBuyCycle(params);
        for(Map<String, Object> map:purchasingPowerList){
            BigDecimal cycle = (BigDecimal) map.get("cycle");
            if (cycle == null) {
                cycle = cycle.setScale(0, BigDecimal.ROUND_HALF_UP);
            } else {
                cycle = BigDecimal.ZERO;
            }
            map.put("cycle", cycle);
        }
        PageInfo pageInfo = new PageInfo(purchasingPowerList);
        return pageInfo;
    }


    @Override
    public Map<String, List<Object>> orderInfoMembersContribution(Map<String, Object> params) {
        List<Map<String, Object>> allContributionList = salesDataStatisticsMapper.allMembersContribution(params);
        List<Map<String, Object>> newMembersContributionList = salesDataStatisticsMapper.newMembersContribution(params);
        if (allContributionList != null && allContributionList.size() > 0) {
            Map<String, BigDecimal> newMembersContributionMap = newMembersContributionList.parallelStream().collect(Collectors.toMap(vo -> (String) vo.get("areaName"), vo -> (BigDecimal) vo.get("totalAmount")));
            BigDecimal oneThousand = new BigDecimal(10000);

            Map<String, List<Object>> result = new HashMap<>();
            List<Object> names = new ArrayList<>();
            List<Object> allMember = new ArrayList<>();
            List<Object> newMember = new ArrayList<>();
            List<Object> oldMember = new ArrayList<>();
            for (Map<String, Object> data : allContributionList) {
                String areaName = (String) data.get("areaName");
                BigDecimal allMemberDecimal = (BigDecimal) data.get("totalAmount");
                allMemberDecimal = allMemberDecimal == null ? BigDecimal.ZERO : allMemberDecimal.divide(oneThousand).setScale(2, BigDecimal.ROUND_DOWN);
                BigDecimal newMemberDecimal = newMembersContributionMap.get(areaName);
                newMemberDecimal = newMemberDecimal == null ? BigDecimal.ZERO : newMemberDecimal.divide(oneThousand).setScale(2, BigDecimal.ROUND_DOWN);
                BigDecimal oldMemberDecimal = allMemberDecimal.subtract(newMemberDecimal);
                names.add(areaName == null ? UNKNOW : areaName);
                allMember.add(allMemberDecimal);
                newMember.add(newMemberDecimal);
                oldMember.add(oldMemberDecimal);
            }
            result.put("names", names);
            result.put("allMember", allMember);
            result.put("newMember", newMember);
            result.put("oldMember", oldMember);
            return result;
        }
        return null;
    }


    /**
     * 订单数据统计 - 事业部完成率
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> orderInfoDoneRateGroupbyOrg(Map<String, Object> params) {
        boolean ascFlag = "1".equals(params.get("sort"));
        // 查找事业部指标
        Map<Integer, Map<String, Object>> planPriceMap = performanceIndicatorsService.performanceIndicatorsWhereTimeByOrg(params);
        if (planPriceMap == null || planPriceMap.size() == 0) {
            return null;
        }
        List<Map<String, Object>> donePriceList = salesDataStatisticsMapper.orderInfoDonePriceGroupbyOrg(params);
        Map<Object, Map<String, Object>> planPriceMap02 = new HashMap<>(planPriceMap);
        Map<String, List<Object>> result = _handleOrderInfoDoneRateData(donePriceList, planPriceMap02, ascFlag);
        return result;
    }


    /**
     * 订单数据统计 - 地区完成率
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> orderInfoDoneRateGroupbyArea(Map<String, Object> params) {
        boolean ascFlag = "1".equals(params.get("sort"));
        // 查找地区指标
        Map<String, Map<String, Object>> planPriceMap = performanceIndicatorsService.performanceIndicatorsWhereTimeByArea(params);
        if (planPriceMap == null || planPriceMap.size() == 0) {
            return null;
        }
        List<Map<String, Object>> donePriceList = salesDataStatisticsMapper.orderInfoDonePriceGroupbyArea(params);
        Map<Object, Map<String, Object>> planPriceMap02 = new HashMap<>(planPriceMap);
        Map<String, List<Object>> result = _handleOrderInfoDoneRateData(donePriceList, planPriceMap02, ascFlag);
        return result;
    }


    /**
     * 订单数据统计 - 国家完成率
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> orderInfoDoneRateGroupbyCountry(Map<String, Object> params) {
        boolean ascFlag = "1".equals(params.get("sort"));
        // 查找国家指标
        Map<String, Map<String, Object>> planPriceMap = performanceIndicatorsService.performanceIndicatorsWhereTimeByCountry(params);
        if (planPriceMap == null || planPriceMap.size() == 0) {
            return null;
        }
        List<Map<String, Object>> donePriceList = salesDataStatisticsMapper.orderInfoDonePriceGroupbyCountry(params);
        Map<Object, Map<String, Object>> planPriceMap02 = new HashMap<>(planPriceMap);
        Map<String, List<Object>> result = _handleOrderInfoDoneRateData(donePriceList, planPriceMap02, ascFlag);
        return result;
    }


    /**
     * 处理完成率数据信息
     */
    private Map<String, List<Object>> _handleOrderInfoDoneRateData(List<Map<String, Object>> donePriceList, Map<Object, Map<String, Object>> planPriceMap, boolean ascFlag) {
        Map<String, List<Object>> result = new HashMap<>();
        Set<Object> keyList = new HashSet<>();
        keyList.addAll(planPriceMap.keySet());
        Map<Object, Map<String, Object>> donePriceMap = donePriceList.parallelStream().collect(Collectors.toMap(vo -> {
            Object key = vo.get("key");
            keyList.add(key);
            return key;
        }, vo -> vo));


        List<Object[]> list = new ArrayList<>();
        BigDecimal oneHundred = new BigDecimal(100);
        for (Object key : keyList) {
            Map<String, Object> planPrice = planPriceMap.get(key);
            Map<String, Object> donePrice = donePriceMap.get(key);
            Object[] objArr = new Object[2];
            String name = donePrice == null ? (String) planPrice.get("name") : (String) donePrice.get("name");
            objArr[0] = name;
            if (donePrice != null && planPrice != null) {
                BigDecimal doneTotalPrice = (BigDecimal) donePrice.get("totalPrice"); // 单位是美元
                BigDecimal planTotalPrice = (BigDecimal) planPrice.get("totalPrice");// 单位是万美元

                if (planTotalPrice.equals(BigDecimal.ZERO)) {
                    objArr[1] = BigDecimal.ZERO;
                } else {
                    // 实际金额/计划金额/10000*100  --- 转换单位和百分比
                    objArr[1] = doneTotalPrice.divide(planTotalPrice, 0, BigDecimal.ROUND_DOWN).divide(oneHundred, 2, BigDecimal.ROUND_DOWN);
                }
            } else {
                // 没有实际完成信息或没有计划信息，则完成率设置为0
                objArr[1] = BigDecimal.ZERO;
            }
            list.add(objArr);
        }

        // 排序
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
        List<Object> rateList = new ArrayList<>();
        list.stream().forEach(vo -> {
            nameList.add((String) vo[0]);
            rateList.add((BigDecimal) vo[1]);
        });
        result.put("nameList", nameList);
        result.put("rateList", rateList);
        return result;
    }

}
