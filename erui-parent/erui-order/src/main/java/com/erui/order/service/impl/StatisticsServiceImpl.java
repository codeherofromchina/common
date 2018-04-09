package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.StatisticsDao;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.model.GoodsStatistics;
import com.erui.order.model.SaleStatistics;
import com.erui.order.service.GoodsService;
import com.erui.order.service.StatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StatisticsDao statisticsDao;


    /**
     * 查询销售业绩统计列表信息
     *
     * @param condition {"startDate":"订单签约日期开始时间","endDate":"订单签约日期结束时间","region":"地区","country":"国家"}
     * @return
     */
    @Override
    public List<SaleStatistics> findSaleStatistics(SaleStatistics condition) {
        Date startDate = condition.getStartDate();
        Date endDate = condition.getEndDate();
        String region2 = condition.getRegion();
        String country1 = condition.getCountry();
        Date handleAfterSdate = startDate;
        Date handleAfterEdate = endDate;
        if (startDate == null) {
            handleAfterSdate = DateUtil.parseString2DateNoException("2000-01-01", "yyyy-MM-dd");
        }
        if (endDate == null) {
            handleAfterEdate = DateUtil.parseString2DateNoException("2099-01-01", "yyyy-MM-dd");
        }
        // 对最后日期时间+1天处理
        handleAfterEdate = NewDateUtil.plusDays(handleAfterEdate, 1);


        // 查询基本订单统计信息
        List<SaleStatistics> list = statisticsDao.orderBaseSaleStatisInfo(handleAfterSdate, handleAfterEdate);
        // 查询复购率
        List<Object> rePurchRateList = statisticsDao.rePurchRate(handleAfterSdate, handleAfterEdate);
        // 查询询单数量
        List<Object> inquiryStatisInfoList = statisticsDao.inquiryStatisInfo(handleAfterSdate, handleAfterEdate);
        // 将list转换为map信息，以便于信息查询检索
        Map<String, Number[]> rePurchRateMap = rePurchRateList.parallelStream().collect(Collectors.toMap(vo -> {
            Object[] rpp = (Object[]) vo;
            return StringUtils.defaultIfEmpty((String) rpp[0], "") + "&" + StringUtils.defaultIfEmpty((String) rpp[1], "");
        }, vo -> {
            Object[] rpp = (Object[]) vo;
            Number[] numbers = new Number[4];
            numbers[0] = (BigInteger) rpp[2];
            numbers[1] = (BigDecimal) rpp[3];
            numbers[2] = (BigDecimal) rpp[4];
            numbers[3] = (BigDecimal) rpp[5];
            return numbers;
        }));
        Map<String, Number[]> inquiryStatisInfoMap = inquiryStatisInfoList.parallelStream().collect(Collectors.toMap(vo -> {
            Object[] rpp = (Object[]) vo;
            return StringUtils.defaultIfEmpty((String) rpp[0], "") + "&" + StringUtils.defaultIfEmpty((String) rpp[1], "");
        }, vo -> {
            Object[] rpp = (Object[]) vo;
            Number[] numbers = new Number[2];
            numbers[0] = (BigInteger) rpp[2];
            numbers[1] = (BigDecimal) rpp[3];
            return numbers;
        }));
        // 整合到基本统计中
        for (Iterator<SaleStatistics> iterator = list.iterator(); iterator.hasNext(); ) {
            SaleStatistics saleStatistics = iterator.next();
            // 过滤地区
            if (StringUtils.isNotBlank(region2) && !StringUtils.equals(region2, saleStatistics.getRegion())) {
                iterator.remove();
                continue;
            }
            // 过滤国家
            if (StringUtils.isNotBlank(country1) && !StringUtils.equals(country1, saleStatistics.getCountry())) {
                iterator.remove();
                continue;
            }

            String region = saleStatistics.getRegion();
            String country = saleStatistics.getCountry();
            String key = StringUtils.defaultIfBlank(region, "") + "&" + StringUtils.defaultIfBlank(country, "");

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
                saleStatistics.setQuotationAmount((BigDecimal) inquiryStatisInfoNum[1]);
            }
            saleStatistics.setStartDate(startDate);
            saleStatistics.setEndDate(endDate);

            saleStatistics.computRateInfo();
        }
        // 返回
        return list;
    }

    @Override
    public List<GoodsStatistics> findGoodsStatistics(GoodsStatistics condition) {
        // 查询sku的订单统计信息
        List<GoodsStatistics> goodsStatisticsList = goodsBaseStatistics(condition);

        // 查询sku的询单统计信息
        Date handleAfterSdate = condition.getStartDate();
        Date handleAfterEdate = condition.getEndDate();
        if (handleAfterSdate == null) {
            handleAfterSdate = DateUtil.parseString2DateNoException("2000-01-01", "yyyy-MM-dd");
        }
        if (handleAfterEdate == null) {
            handleAfterEdate = DateUtil.parseString2DateNoException("2099-01-01", "yyyy-MM-dd");
        }
        // 对最后日期时间+1天处理
        handleAfterEdate = NewDateUtil.plusDays(handleAfterEdate, 1);
        List<Object> inquiry = statisticsDao.inquiryStatisGroupBySku(handleAfterSdate, handleAfterEdate);
        Map<String, Object[]> inquiryMap = inquiry.parallelStream().collect(Collectors.toMap(vo -> {
            Object[] objArr = (Object[]) vo;
            return StringUtils.defaultString((String) objArr[0], "") + "&" +
                    StringUtils.defaultString((String) objArr[1], "") + "&" +
                    StringUtils.defaultString((String) objArr[2], "");
        }, vo -> {
            return (Object[]) vo;
        }));
        // 合并询单和订单统计信息
        for (GoodsStatistics goodsStatistics1 : goodsStatisticsList) {
            String key = StringUtils.defaultString(goodsStatistics1.getSku(), "") + "&" +
                    StringUtils.defaultString(goodsStatistics1.getRegion(), "") + "&" +
                    StringUtils.defaultString(goodsStatistics1.getCountry(), "");
            if (inquiryMap.containsKey(key)) {
                Object[] objects = inquiryMap.get(key);
                goodsStatistics1.setQuotationNum(((BigInteger)objects[3]).longValue());
                goodsStatistics1.setQuotationAmount(new BigDecimal((double)objects[4],new MathContext(2, RoundingMode.HALF_UP)));
            }
            goodsStatistics1.setStartDate(condition.getStartDate());
            goodsStatistics1.setEndDate(condition.getEndDate());
        }

        return goodsStatisticsList;
    }

    /**
     * 商品基本统计信息
     *
     * @param goodsStatistics
     * @return
     */
    private List<GoodsStatistics> goodsBaseStatistics(GoodsStatistics goodsStatistics) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Goods> root = query.from(Goods.class);

        Path<String> skuPath = root.get("sku");
        Path<String> proTypePath = root.get("proType");
        Path<String> nameEnPath = root.get("nameEn");
        Path<String> nameZhPath = root.get("nameZh");
        Path<String> brandPath = root.get("brand");
        Join<Goods, Order> orderRoot = root.join("order");
        Path<String> regionPath = orderRoot.get("region");
        Path<String> countryPath = orderRoot.get("country");
        Path<BigDecimal> totalPricePath = orderRoot.get("totalPrice");
        Path<Date> signingDatePath = orderRoot.get("signingDate");

        List<Predicate> predicateList = new ArrayList<>();
        String sku = goodsStatistics.getSku();
        String proType = goodsStatistics.getProType();
        String brand = goodsStatistics.getBrand();
        String region = goodsStatistics.getRegion();
        String country = goodsStatistics.getCountry();
        Date endDate = goodsStatistics.getEndDate();
        Date startDate = goodsStatistics.getStartDate();
        if (StringUtils.isNotBlank(sku)) {
            predicateList.add(cb.like(skuPath, "%" + sku + "%"));
        }
        if (StringUtils.isNotBlank(proType)) {
            predicateList.add(cb.equal(proTypePath, proType));
        }
        if (StringUtils.isNotBlank(brand)) {
            predicateList.add(cb.equal(brandPath, brand));
        }
        if (StringUtils.isNotBlank(region)) {
            predicateList.add(cb.equal(regionPath, region));
        }
        if (StringUtils.isNotBlank(country)) {
            predicateList.add(cb.equal(countryPath, country));
        }
        if (startDate != null) {
            predicateList.add(cb.greaterThanOrEqualTo(signingDatePath, startDate));
        }
        if (endDate != null) {
            endDate = NewDateUtil.plusDays(endDate, 1);
            predicateList.add(cb.lessThan(signingDatePath, endDate));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicates = predicateList.toArray(predicates);
        query.where(predicates);//where条件加上

        query.select(cb.tuple(skuPath, proTypePath, nameEnPath, nameZhPath, brandPath, regionPath, countryPath, cb.count(orderRoot), cb.sum(totalPricePath)));
        query.groupBy(skuPath, proTypePath, nameEnPath, nameZhPath, brandPath, regionPath, countryPath);

        TypedQuery<Tuple> q = entityManager.createQuery(query);
        List<Tuple> tupleList = q.getResultList();

        List<GoodsStatistics> result = new ArrayList<>();
        if (tupleList.size() > 0) {
            for (Tuple tp : tupleList) {
                GoodsStatistics gs = new GoodsStatistics();
                gs.setSku((String) tp.get(0));
                gs.setProType((String) tp.get(1));
                gs.setNameEn((String) tp.get(2));
                gs.setNameZh((String) tp.get(3));
                gs.setBrand((String) tp.get(4));
                gs.setRegion((String) tp.get(5));
                gs.setCountry((String) tp.get(6));
                gs.setOrderNum((long) tp.get(7));
                gs.setOrderAmount((BigDecimal) tp.get(8));

                result.add(gs);
            }
        }
        return result;
    }
}
