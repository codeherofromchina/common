package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.StatisticsDao;
import com.erui.order.model.SaleStatistics;
import com.erui.order.service.StatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;


    /**
     * 查询销售业绩统计列表信息
     * @param condition {"startDate":"订单签约日期开始时间","endDate":"订单签约日期结束时间","region":"地区","country":"国家"}
     * @return
     */
    @Override
    public List<SaleStatistics> findSaleStatisticsByDate(SaleStatistics condition) {
        Date startDate = condition.getStartDate();
        Date endDate = condition.getEndDate();
        String region2 = condition.getRegion();
        String country1 = condition.getCountry();
        Date handleAfterSdate  = startDate;
        Date handleAfterEdate  = endDate;
        if (startDate == null) {
            handleAfterSdate = DateUtil.parseString2DateNoException("2000-01-01","yyyy-MM-dd");
        }
        if (endDate == null) {
            handleAfterEdate = DateUtil.parseString2DateNoException("2099-01-01","yyyy-MM-dd");
        }
        // 对最后日期时间+1天处理
        handleAfterEdate = NewDateUtil.plusDays(handleAfterEdate,1);


        // 查询基本订单统计信息
        List<SaleStatistics> list = statisticsDao.orderBaseSaleStatisInfo(handleAfterSdate,handleAfterEdate);
        // 查询复购率
        List<Object> rePurchRateList = statisticsDao.rePurchRate(handleAfterSdate,handleAfterEdate);
        // 查询询单数量
        List<Object> inquiryStatisInfoList = statisticsDao.inquiryStatisInfo(handleAfterSdate,handleAfterEdate);
        // 将list转换为map信息，以便于信息查询检索
        Map<String,Number[]> rePurchRateMap = rePurchRateList.parallelStream().collect(Collectors.toMap(vo -> {
            Object[] rpp = (Object[])vo;
            return StringUtils.defaultIfEmpty((String)rpp[0],"") + "&" + StringUtils.defaultIfEmpty((String)rpp[1],"");
        },vo -> {
            Object[] rpp = (Object[])vo;
            Number[] numbers = new Number[4];
            numbers[0] = (BigInteger) rpp[2];
            numbers[1] = (BigDecimal)rpp[3];
            numbers[2] = (BigDecimal)rpp[4];
            numbers[3] = (BigDecimal)rpp[5];
            return numbers;
        }));
        Map<String,Number[]> inquiryStatisInfoMap =  inquiryStatisInfoList.parallelStream().collect(Collectors.toMap(vo -> {
            Object[] rpp = (Object[])vo;
            return StringUtils.defaultIfEmpty((String)rpp[0],"") + "&" + StringUtils.defaultIfEmpty((String)rpp[1],"");
        },vo -> {
            Object[] rpp = (Object[])vo;
            Number[] numbers = new Number[2];
            numbers[0] = (BigInteger) rpp[2];
            numbers[1] = (BigDecimal)rpp[3];
            return numbers;
        }));
        // 整合到基本统计中
        for(Iterator<SaleStatistics> iterator = list.iterator();iterator.hasNext();) {
            SaleStatistics saleStatistics = iterator.next();
            // 过滤地区
            if (StringUtils.isNotBlank(region2) && !StringUtils.equals(region2,saleStatistics.getRegion())) {
                iterator.remove();
                continue;
            }
            // 过滤国家
            if (StringUtils.isNotBlank(country1) && !StringUtils.equals(country1,saleStatistics.getCountry())) {
                iterator.remove();
                continue;
            }

            String region = saleStatistics.getRegion();
            String country = saleStatistics.getCountry();
            String key = StringUtils.defaultIfBlank(region,"") + "&" + StringUtils.defaultIfBlank(country,"");

            Number[] rePurchRateNum = rePurchRateMap.get(key);
            if (rePurchRateNum != null) {
                saleStatistics.setVipNum(rePurchRateNum[0].longValue());
                saleStatistics.setTwo_re_purch(rePurchRateNum[1].longValue());
                saleStatistics.setThree_re_purch(rePurchRateNum[2].longValue());
                saleStatistics.setMore_re_purch(rePurchRateNum[3].longValue());
            }

            Number[] inquiryStatisInfoNum = inquiryStatisInfoMap.get(key);
            if (inquiryStatisInfoNum != null) {
                saleStatistics.setQuotationNum(inquiryStatisInfoNum[0].longValue());
                saleStatistics.setQuotationAmount((BigDecimal)inquiryStatisInfoNum[1]);
            }
            saleStatistics.setStartDate(startDate);
            saleStatistics.setEndDate(endDate);

            saleStatistics.computRateInfo();
        }
        // 返回
        return list;
    }
}
