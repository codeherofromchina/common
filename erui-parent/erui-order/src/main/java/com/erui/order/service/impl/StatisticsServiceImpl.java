package com.erui.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.middle.redis.ShardedJedisUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.model.*;
import com.erui.order.service.CheckLogService;
import com.erui.order.service.StatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    private static final String STATISTICSSERVICEIMPL_GOODSBASESTATISTICS_TOTAL_KEY = "STATISTICSSERVICEIMPL_GOODSBASESTATISTICS_TOTAL_KEY";
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private StatisticsDao statisticsDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CheckLogService checkLogService;
    @Autowired
    private IogisticsDataDao iogisticsDataDao;

    /**
     * 查询销售业绩统计列表信息
     *
     * @param condition {"startDate":"订单签约日期开始时间","endDate":"订单签约日期结束时间","region":"地区","country":"国家"}
     * @return
     */
    @Override
    public List<SaleStatistics> findSaleStatistics(SaleStatistics condition, Set<String> countries) {
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
            Number[] numbers = new Number[5];
            numbers[0] = (BigInteger) rpp[2];
            numbers[1] = (BigDecimal) rpp[3];
            numbers[2] = (BigDecimal) rpp[4];
            numbers[3] = (BigDecimal) rpp[5];
            numbers[4] = (BigDecimal) rpp[6];
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
        // 查询中文地区和国家
        Map<String, String> bnMapZhCountry = this.findBnMapZhCountry();
        Map<String, String> bnMapZhRegion = this.findBnMapZhRegion();
        // 整合到基本统计中
        for (Iterator<SaleStatistics> iterator = list.iterator(); iterator.hasNext(); ) {
            SaleStatistics saleStatistics = iterator.next();
            String iCountry = saleStatistics.getCountry();
            // 过滤地区
            if (StringUtils.isNotBlank(region2) && !StringUtils.equals(region2, saleStatistics.getRegion())) {
                iterator.remove();
                continue;
            }
            // 过滤国家
            if (StringUtils.isNotBlank(country1) && !StringUtils.equals(country1, iCountry)) {
                iterator.remove();
                continue;
            }
            // 必须在传入的国家范围内
            if (countries != null && !countries.contains(iCountry)) {
                iterator.remove();
                continue;
            }

            String region = saleStatistics.getRegion();
            String country = saleStatistics.getCountry();
            String key = StringUtils.defaultIfBlank(region, "") + "&" + StringUtils.defaultIfBlank(country, "");
            saleStatistics.setRegionZh(bnMapZhRegion.get(region));
            saleStatistics.setCountryZh(bnMapZhCountry.get(country));

            Number[] rePurchRateNum = rePurchRateMap.get(key);
            if (rePurchRateNum != null) {
                saleStatistics.setVipNum(rePurchRateNum[0].longValue());
                saleStatistics.setOneRePurch(rePurchRateNum[1].longValue());
                saleStatistics.setTwoRePurch(rePurchRateNum[2].longValue());
                saleStatistics.setThreeRePurch(rePurchRateNum[3].longValue());
                saleStatistics.setMoreRePurch(rePurchRateNum[4].longValue());
            }

            Number[] inquiryStatisInfoNum = inquiryStatisInfoMap.get(key);
            if (inquiryStatisInfoNum != null) {
                saleStatistics.setQuotationNum(inquiryStatisInfoNum[0].longValue());
                saleStatistics.setQuotationAmount((BigDecimal) inquiryStatisInfoNum[1]);
            }
            saleStatistics.setStartDate(startDate);
            saleStatistics.setEndDate(endDate);
        }
        // 返回
        return list;
    }


    @Override
    public HSSFWorkbook generateSaleStatisticsExcel(SaleStatistics condition, Set<String> countries) {
        List<SaleStatistics> saleStatistics = findSaleStatistics(condition, countries);
        String[] header = new String[]{"所属地区", "国家", "订单总数量", "订单总额", "油气数量", "油气订单金额", "油气订单数量占比%", "油气订单金额占比%", "非油气数量", "非油气订单金额", "非油气订单数量占比%", "非油气订单金额占比%", "询单总数量", "询单总金额", "询单订单金额占比%", "询单订单数量占比%", "会员总数", "1次复购率（会员数量）", "2次复购率（会员数量）", "次复购率（会员数量）", "3次以上复购率（会员数量）"};
        String[] keys = new String[]{"regionZh", "countryZh", "orderNum", "orderAmount", "oilOrderNum", "oilOrderAmount", "oilOrderNumRateStr", "oilOrderAmountRateStr", "nonOilOrderNum", "nonOilOrderAmount", "nonOilOrderNumRateStr", "nonOilOrderAmountRateStr", "quotationNum", "quotationAmount", "crmOrderNumRateStr", "crmOrderAmountRateStr", "vipNum", "oneRePurch", "twoRePurch", "threeRePurch", "moreRePurch"};
        BuildExcel buildExcel = new BuildExcelImpl();
        Object objArr = JSON.toJSON(saleStatistics);
        HSSFWorkbook workbook = buildExcel.buildExcel((List) objArr, header, keys, "销售业绩统计");
        return workbook;
    }

    @Override
    public Page<GoodsStatistics> findGoodsStatistics(GoodsStatistics condition, Set<String> countries, int pageNum, int pageSize) {
        LOGGER.info("查询商品统计基本信息");
        // 查询sku的订单统计信息
        Page<GoodsStatistics> goodsStatisticsList = goodsBaseStatistics(condition, countries, pageNum, pageSize);
        LOGGER.info("查询商品统计的询单信息");
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
        // 询单信息的统计
        List<Object> inquiry = statisticsDao.inquiryStatisGroupBySku(handleAfterSdate, handleAfterEdate);
        Map<String, Object[]> inquiryMap = new HashMap<>();
        for (int i = 0; i < inquiry.size(); i++) {
            Object[] objArr = (Object[]) inquiry.get(i);
            String key = StringUtils.defaultString((String) objArr[0], "") + "&" +
                    StringUtils.defaultString((String) objArr[1], "") + "&" +
                    StringUtils.defaultString((String) objArr[2], "");
            inquiryMap.put(key, objArr);
        }
        // 查询中文地区和国家
        Map<String, String> bnMapZhCountry = this.findBnMapZhCountry();
        Map<String, String> bnMapZhRegion = this.findBnMapZhRegion();
        // 合并询单和订单统计信息
        for (GoodsStatistics goodsStatistics1 : goodsStatisticsList) {
            String region = goodsStatistics1.getRegion();
            String country = goodsStatistics1.getCountry();
            goodsStatistics1.setRegionZh(bnMapZhRegion.get(region));
            goodsStatistics1.setCountryZh(bnMapZhCountry.get(country));
            String key = StringUtils.defaultString(goodsStatistics1.getSku(), "") + "&" +
                    StringUtils.defaultString(region, "") + "&" +
                    StringUtils.defaultString(country, "");
            if (inquiryMap.containsKey(key)) {
                Object[] objects = inquiryMap.get(key);
                goodsStatistics1.setQuotationNum(((BigInteger) objects[3]).longValue());
                goodsStatistics1.setQuotationAmount((BigDecimal) objects[4]);
            }
            goodsStatistics1.setStartDate(condition.getStartDate());
            goodsStatistics1.setEndDate(condition.getEndDate());
        }
        LOGGER.info("返回商品统计信息");
        return goodsStatisticsList;
    }


    @Override
    public HSSFWorkbook generateGoodsStatisticsExcel(GoodsStatistics goodsStatistics, Set<String> countries) {
        Page<GoodsStatistics> goodsStatisticsPage = findGoodsStatistics(goodsStatistics, countries, -1, -1);
        String[] header = new String[]{"平台SKU", "产品分类", "英文品名", "中文品名", "品牌", "所属地区", "国家", "询单数量", "询单金额", "订单数量", "订单金额"};
        String[] keys = new String[]{"sku", "proType", "nameEn", "nameZh", "brand", "regionZh", "countryZh", "quotationNum", "quotationAmount", "orderNum", "orderAmount"};
        BuildExcel buildExcel = new BuildExcelImpl();
        List<JSONObject> data = new ArrayList<>();
        for (GoodsStatistics goodsStat : goodsStatisticsPage) {
            data.add((JSONObject) JSON.toJSON(goodsStat));
        }
        HSSFWorkbook workbook = buildExcel.buildExcel(data, header, keys, "产品统计");
        return workbook;
    }

    /**
     * 商品基本统计信息
     * pageNum和pageSize要大于0，否则分页失效
     *
     * @param goodsStatistics
     * @param pageNum
     * @param pageSize
     * @return
     */
    private Page<GoodsStatistics> goodsBaseStatistics(GoodsStatistics goodsStatistics, Set<String> countries, int pageNum, int pageSize) {
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
        Path<BigDecimal> totalPricePath = orderRoot.get("totalPriceUsd");
        Path<Date> signingDatePath = orderRoot.get("signingDate");
        Path<Integer> orderStatus = orderRoot.get("status");
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
        if (countries != null && countries.size() > 0) {
            predicateList.add(countryPath.in(countries.toArray(new String[countries.size()])));
        }
        if (startDate != null) {
            predicateList.add(cb.greaterThanOrEqualTo(signingDatePath, startDate));
        }
        if (endDate != null) {
            endDate = NewDateUtil.plusDays(endDate, 1);
            predicateList.add(cb.lessThan(signingDatePath, endDate));
        }
        predicateList.add(cb.greaterThanOrEqualTo(orderStatus, 3)); // 过滤订单状态
        predicateList.add(cb.lessThanOrEqualTo(orderStatus, 4)); // 订单状态已变更不显示

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicates = predicateList.toArray(predicates);

        // 查询记录
        query.where(predicates);//where条件加上
        query.select(cb.tuple(skuPath, proTypePath, nameEnPath, nameZhPath, brandPath, regionPath, countryPath, cb.count(orderRoot), cb.sum(totalPricePath)));
        query.groupBy(skuPath, proTypePath, nameEnPath, nameZhPath, brandPath, regionPath, countryPath);
        TypedQuery<Tuple> q = entityManager.createQuery(query);

        List<Tuple> tupleList = null;
        Integer totalEles = null;
        if (pageNum > 0 && pageSize > 0) {
            int firstResult = (pageNum - 1) * pageSize;
            totalEles = ShardedJedisUtil.getInteger(getRedisKey(goodsStatistics));
            if (totalEles != null) {
                if (firstResult > totalEles) {
                    tupleList = new ArrayList<>();
                } else {
                    q.setFirstResult(firstResult);
                    q.setMaxResults(pageSize);
                    tupleList = q.getResultList();
                }
            } else {
                List<Tuple> list = q.getResultList();
                totalEles = list.size();
                ShardedJedisUtil.setExpire(getRedisKey(goodsStatistics), String.valueOf(totalEles), "NX", "EX", 30 * 60); // 设置60分钟过期
                if (firstResult > totalEles) {
                    tupleList = new ArrayList<>();
                } else {
                    int endIndex = Math.min(firstResult + pageSize, list.size());
                    tupleList = list.subList(firstResult, endIndex);
                }
            }
        } else {
            tupleList = q.getResultList();

        }

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
        Page<GoodsStatistics> resultPage = null;
        if (pageNum > 0 && pageSize > 0) {
            resultPage = new PageImpl<GoodsStatistics>(result, new PageRequest(pageNum - 1, pageSize), totalEles);
        } else {
            resultPage = new PageImpl<GoodsStatistics>(result, new PageRequest(1, result.size()), result.size());
        }

        return resultPage;
    }


    // TODO
    @Transactional
    public Page<ProjectStatistics> findProjectStatisticsByPage(Map<String, String> condition) {
        // 整理查询条件
        int page = 0;
        int rows = 50;
        String pageStr = condition.get("page");
        String rowsStr = condition.get("rows");
        if (StringUtils.isNumeric(pageStr)) {
            page = Integer.parseInt(pageStr) - 1;
            if (page < 0) {
                page = 0;
            }
        }
        if (StringUtils.isNumeric(rowsStr)) {
            rows = Integer.parseInt(rowsStr);
            if (rows < 1) {
                rows = 50;
            }
        }

        PageRequest pageRequest = new PageRequest(page, rows, new Sort(Sort.Direction.DESC, "id"));
        Page<Project> pageList = projectDao.findAll(specificationCondition(condition), pageRequest);

        List<ProjectStatistics> dataList = new ArrayList<>();
        List<Integer> orderIds = new ArrayList<>();
        // 查询地区的中英文对应列表
        Map<String, String> bnMapZhRegion = this.findBnMapZhRegion();
        for (Project project : pageList) {
            Order order = project.getOrder();
            if (order != null) {
                ProjectStatistics projectStatistics = new ProjectStatistics(project, order);
                projectStatistics.setRegionZh(bnMapZhRegion.get(projectStatistics.getRegion()));
                Integer purchReqCreate = project.getPurchReqCreate();//'是否已经创建采购申请单 1：未创建  2：已创建 3:已创建并提交'
                if (purchReqCreate != null && purchReqCreate == 3) {
                    if (order.getGoodsList().size() > 0) {
                        List<Goods> goodsList = order.getGoodsList();
                        if (goodsList.size() == 1 && goodsList.get(0).getProType() != null) {
                            projectStatistics.setProCate(goodsList.get(0).getProType());
                        } else {
                            List<String> proCateList = goodsList.stream().map(Goods::getProType).collect(Collectors.toList());
                            Set<String> setproCate = new HashSet<>(proCateList);
                            if (setproCate.size() == proCateList.size()) {
                                projectStatistics.setProCate(goodsList.get(0).getProType());
                            } else {
                                int count = 0;
                                for (String proCate : setproCate) {
                                    if (proCate != null) {
                                        int frequency = Collections.frequency(proCateList, proCate);
                                        if (frequency > count) {
                                            count = frequency;
                                            projectStatistics.setProCate(proCate);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                orderIds.add(order.getId());
                dataList.add(projectStatistics);
            }
        }
        // 查询所有订单的回款总金额信息
        if (dataList.size() > 0) {
            List<Object> orderAccountList = statisticsDao.findOrderAccount(orderIds);
            Map<Integer, Object[]> orderAccountMap = orderAccountList.parallelStream().collect(Collectors.toMap(vo -> {
                Object[] objArr = (Object[]) vo;
                return (Integer) objArr[0];
            }, vo -> {
                Object[] objArr = (Object[]) vo;
                return objArr;
            }));
            for (ProjectStatistics projectStatistics : dataList) {
                Integer orderId = projectStatistics.getOrderId();
                Object[] objArr = orderAccountMap.get(orderId);
                if (objArr != null) {
                    projectStatistics.setPaymentDate((Date) objArr[2]); //回款时间
                    BigDecimal money = (BigDecimal) objArr[1];//回款金额
                    projectStatistics.setMoney(money);
                    projectStatistics.setAcquireId((String) objArr[3]); //员工姓名
                    projectStatistics.setAccountCount((BigInteger) objArr[4]);  //收款记录条数
                    String currencyBn = (String) objArr[5];  //货币类型
                    projectStatistics.setCurrencyBn(currencyBn);  //货币类型
                    BigDecimal exchangeRate = (BigDecimal) objArr[6];//利率
                    BigDecimal discount = (BigDecimal) objArr[7];//其他扣款金额
                    if (discount != null) {
                        money = money.add(discount);   //回款金额 加上 其他扣款金额
                    }

                    if (objArr[1] != null) {    //是否有回款金额
                        if (currencyBn != "USD") {    //是否是美元
                            projectStatistics.setCurrencyBnMoney(new DecimalFormat("###,##0.00").format(money.multiply(exchangeRate))); //回款金额
                        } else {
                            projectStatistics.setCurrencyBnMoney(new DecimalFormat("###,##0.00").format(money)); //回款金额
                        }
                    }
                    projectStatistics.setCurrencyBnMoney(projectStatistics.getCurrencyBnMoney() == null ? "0" : projectStatistics.getCurrencyBnMoney());

                }
            }
        }


        Page<ProjectStatistics> result = new PageImpl<ProjectStatistics>(dataList, pageRequest, pageList.getTotalElements());

        return result;
    }

    // TODO
    @Transactional
    public List<ProjectStatistics> findProjectStatistics(Map<String, String> condition) {
        List<Project> pageList = projectDao.findAll(specificationCondition(condition), new Sort(Sort.Direction.DESC, "id"));

        List<ProjectStatistics> dataList = new ArrayList<>();
        List<Integer> orderIds = new ArrayList<>();
        // 查询地区的中英文对应列表
        Map<String, String> bnMapZhRegion = this.findBnMapZhRegion();
        for (Project project : pageList) {
            Order order = project.getOrder();
            if (order != null) {
                ProjectStatistics projectStatistics = new ProjectStatistics(project, order);
                projectStatistics.setRegionZh(bnMapZhRegion.get(projectStatistics.getRegion()));
                Integer purchReqCreate = project.getPurchReqCreate();//'是否已经创建采购申请单 1：未创建  2：已创建 3:已创建并提交'
                if (purchReqCreate != null && purchReqCreate == 3) {
                    if (order.getGoodsList().size() > 0) {
                        List<Goods> goodsList = order.getGoodsList();
                        if (goodsList.size() == 1 && goodsList.get(0).getProType() != null) {
                            projectStatistics.setProCate(goodsList.get(0).getProType());
                        } else {
                            List<String> proCateList = goodsList.stream().map(Goods::getProType).collect(Collectors.toList());
                            Set<String> setproCate = new HashSet<>(proCateList);
                            if (setproCate.size() == proCateList.size()) {
                                projectStatistics.setProCate(goodsList.get(0).getProType());
                            } else {
                                int count = 0;
                                for (String proCate : setproCate) {
                                    if (proCate != null) {
                                        int frequency = Collections.frequency(proCateList, proCate);
                                        if (frequency > count) {
                                            count = frequency;
                                            projectStatistics.setProCate(proCate);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                orderIds.add(order.getId());
                dataList.add(projectStatistics);
            }
        }
        // 查询所有订单的回款总金额信息
        if (dataList.size() > 0) {
            List<Object> orderAccountList = statisticsDao.findOrderAccount(orderIds);
            Map<Integer, Object[]> orderAccountMap = orderAccountList.parallelStream().collect(Collectors.toMap(vo -> {
                Object[] objArr = (Object[]) vo;
                return (Integer) objArr[0];
            }, vo -> {
                Object[] objArr = (Object[]) vo;
                return objArr;
            }));
            for (ProjectStatistics projectStatistics : dataList) {
                Integer orderId = projectStatistics.getOrderId();
                Object[] objArr = orderAccountMap.get(orderId);
                if (objArr != null) {
                    projectStatistics.setPaymentDate((Date) objArr[2]); //回款时间
                    BigDecimal money = (BigDecimal) objArr[1];//回款金额
                    projectStatistics.setMoney(money);
                    projectStatistics.setAcquireId((String) objArr[3]); //员工姓名
                    projectStatistics.setAccountCount((BigInteger) objArr[4]);  //收款记录条数
                    String currencyBn = (String) objArr[5];  //货币类型
                    BigDecimal exchangeRate = (BigDecimal) objArr[6];//利率
                    BigDecimal discount = (BigDecimal) objArr[7];//其他扣款金额
                    if (discount != null) {
                        money = money.add(discount);   //回款金额 加上 其他扣款金额
                    }
                    if (objArr[1] != null) {    //是否有回款金额
                        if (currencyBn != "USD") {    //是否是美元
                            projectStatistics.setCurrencyBnMoney(new DecimalFormat("###,##0.00").format(money.multiply(exchangeRate))); //回款金额
                        } else {
                            projectStatistics.setCurrencyBnMoney(new DecimalFormat("###,##0.00").format(money)); //回款金额
                        }
                    }
                    projectStatistics.setCurrencyBnMoney(projectStatistics.getCurrencyBnMoney() == null ? "0" : projectStatistics.getCurrencyBnMoney());

                }
            }
        }
        return dataList;
    }

    //项目执行统计导出
    @Override
    @Transactional(readOnly = true)
    public HSSFWorkbook generateProjectStatisticsExcel(Map<String, String> condition) {
        List<ProjectStatistics> projectStatistics = findProjectStatistics(condition);

        projectStatistics.stream().forEach(vo -> {
            vo.setGoodsList(null);
            vo.setExecCoName(checkLogService.getSigningCoCn(vo.getExecCoName()));
        });
        String[] header = new String[]{"项目创建日期", "项目开始日期", "销售合同号", "订单类别", "海外销类型", "询单号", "项目号", "合同标的", "海外销售合同号", "物流报价单号",
                "产品分类", "执行分公司", "事业部", "所属地区", "CRM客户代码", "客户类型", "项目金额（美元）",
                "收款方式", "回款时间", "回款金额", "初步利润率%", "授信情况", "执行单约定交付日期",
                "要求采购到货日期", "执行单变更后日期", "分销部(获取人所在分类销售)", "市场经办人", "获取人", "商务技术经办人", "贸易术语",
                "项目状态", "流程进度"};
        String[] keys = new String[]{"createTime", "startDate", "contractNo", "orderCategory", "overseasSalesName", "inquiryNo", "projectNo", "projectName", "contractNoOs", "logiQuoteNo",
                "proCate", "execCoName", "businessUnitName", "regionZh", "crmCode", "customerTypeName", "totalPrice",
                "paymentModeBnName", "paymentDate", "currencyBnMoney", "profitPercentStr", "grantTypeName", "deliveryDate",
                "requirePurchaseDate", "exeChgDate", "businessUnitName", "agentName", "acquireId", "businessName", "tradeTerms",
                "projectStatusName", "processProgressName"};
        BuildExcel buildExcel = new BuildExcelImpl();
        Object objArr = JSON.toJSON(projectStatistics);
        HSSFWorkbook workbook = buildExcel.buildExcel((List) objArr, header, keys, "项目执行统计");
        return workbook;
    }

    //项目商品详情统计导出
    @Override
    public HSSFWorkbook generateProjectDescStatisticsExcel(Map<String, String> condition) {
        List<ProjectStatistics> projectStatistics = findProjectStatistics(condition);
        List<ProjectGoodsStatistics> projectGoodsStatistics = new ArrayList<>();
        int count = 1;
        for (ProjectStatistics p : projectStatistics) {
            ProjectGoodsStatistics projectGood01 = copyProjectDescTo(p);
            projectGood01.setId(count);
            projectGood01.setBusinessUnitName(p.getBusinessUnitName());
            projectGood01.setContractGoodsNum(1);
            projectGood01.setUnit("批");
            projectGood01.setCurrencyBn("USD");
            projectGood01.setProjectName(p.getProjectName());
            projectGood01.setTotalPrice(p.getTotalPrice());
            projectGood01.setProfit(p.getProfit());
            projectGoodsStatistics.add(projectGood01);
            count++;
            if (p.getGoodsList() != null) {
                List<Goods> goodsList = p.getGoodsList();
                for (Goods g : goodsList) {
                    ProjectGoodsStatistics projectGoods = copyProjectDescTo(p);
                    projectGoods.setOrderCategory(g.getProType());
                    projectGoods.setBusinessUnitName(g.getDepartmentName());
                    projectGoods.setNameZh(g.getNameZh());
                    projectGoods.setNameEn(g.getNameEn());
                    projectGoods.setModel(g.getModel());
                    projectGoods.setContractGoodsNum(g.getContractGoodsNum());
                    projectGoods.setUnit(g.getUnit());
                    projectGoods.setTotalPrice(g.getPrice());
                    projectGoods.setCurrencyBn(p.getCurrencyBn());
                    projectGoods.setProCate(g.getMeteName());
                    projectGoodsStatistics.add(projectGoods);

                }
            }

        }
        String[] header = new String[]{"序号", "项目创建日期", "项目开始日期", "销售合同号", "订单类别", "海外销类型", "询单号", "项目号",// "未用易瑞签约原因",
                "是否通过代理商获取", "代理商代码", "PO号", "合同标的", "海外销售合同号", "物流报价单号", "产品分类", "执行分公司", "事业部", "国家",
                "所属地区", "CRM客户代码", "客户类型", "品名中文", "品名外文", "规格", "数量", "单位", "项目金额", "币种",
                "收款方式", "回款时间", "回款金额", "初步利润率%", "利润额", "授信情况", "执行单约定交付日期",
                "要求采购到货日期", "执行单变更后日期", /*"分销部(获取人所在分类销售)", "市场经办人",*/ "获取人", "商务技术经办人", "贸易术语",
                "项目状态"/*, "流程进度"*/};
        String[] keys = new String[]{"id", "createTime", "startDate", "contractNo", "orderCategory", "overseasSales", "inquiryNo", "projectNo",// "nonReson",
                "agent", "agentNo", "poNo", "projectName", "contractNoOs", "logiQuoteNo", "proCate", "execCoName", "businessUnitName", "country",
                "regionZh", "crmCode", "customerType", "nameZh", "nameEn", "model", "contractGoodsNum", "unit", "totalPrice", "currencyBn",
                "paymentModeBnName", "paymentDate", "currencyBnMoney", "profitPercent", "profit", "grantType", "deliveryDate",
                "requirePurchaseDate", "exeChgDate", /*"distributionDeptName", "agentName",*/ "acquireId", "businessName", "tradeTerms",
                "projectStatus"/*, "processProgress"*/};
        BuildExcel buildExcel = new BuildExcelImpl();
        Object objArr = JSON.toJSON(projectGoodsStatistics);
        HSSFWorkbook workbook = buildExcel.buildExcel((List) objArr, header, keys, "项目商品信息统计");
        return workbook;
    }

    private ProjectGoodsStatistics copyProjectDescTo(ProjectStatistics proStatistics) {
        if (proStatistics == null) {
            return null;
        }
        ProjectGoodsStatistics projectGoods = new ProjectGoodsStatistics();
        projectGoods.setCreateTime(proStatistics.getCreateTime());
        projectGoods.setStartDate(proStatistics.getStartDate());
        projectGoods.setContractNo(proStatistics.getContractNo());
        projectGoods.setOrderCategory(proStatistics.getOrderCategory());
        projectGoods.setOverseasSales(proStatistics.getOverseasSalesName());
        projectGoods.setInquiryNo(proStatistics.getInquiryNo());
        projectGoods.setProjectNo(proStatistics.getProjectNo());
        projectGoods.setPoNo(proStatistics.getPoNo());
        projectGoods.setContractNoOs(proStatistics.getContractNoOs());
        projectGoods.setLogiQuoteNo(proStatistics.getLogiQuoteNo());
        //projectGoods.setProCate(proStatistics.getProCate());
        projectGoods.setExecCoName(proStatistics.getExecCoName());
        projectGoods.setRegionZh(proStatistics.getRegionZh());
        projectGoods.setCountry(findBnMapZhCountry().get(proStatistics.getCountry()));
        projectGoods.setCrmCode(proStatistics.getCrmCode());
        projectGoods.setCustomerType(proStatistics.getCustomerTypeName());
        projectGoods.setPaymentModeBn(proStatistics.getPaymentModeBn());
        projectGoods.setPaymentDate(proStatistics.getPaymentDate());
        projectGoods.setCurrencyBnMoney(proStatistics.getCurrencyBnMoney());
        projectGoods.setProfitPercent(proStatistics.getProfitPercentStr());
        projectGoods.setGrantType(proStatistics.getGrantTypeName());
        projectGoods.setDeliveryDate(proStatistics.getDeliveryDate());
        projectGoods.setRequirePurchaseDate(proStatistics.getRequirePurchaseDate());
        projectGoods.setExeChgDate(proStatistics.getExeChgDate());
        //projectGoods.setDistributionDeptName(proStatistics.getDistributionDeptName());
        //projectGoods.setAgentName(proStatistics.getAgentName());
        projectGoods.setAcquireId(proStatistics.getAcquireId());
        projectGoods.setBusinessName(proStatistics.getBusinessName());
        projectGoods.setTradeTerms(proStatistics.getTradeTerms());
        projectGoods.setProjectStatus(proStatistics.getProjectStatusName());
        //projectGoods.setProcessProgress(proStatistics.getProcessProgressName());
        return projectGoods;
    }

    private String getRedisKey(GoodsStatistics goodsStatistics) {
        String sku = goodsStatistics.getSku();
        String proType = goodsStatistics.getProType();
        String brand = goodsStatistics.getBrand();
        String region = goodsStatistics.getRegion();
        String country = goodsStatistics.getCountry();
        Date endDate = goodsStatistics.getEndDate();
        Date startDate = goodsStatistics.getStartDate();

        return STATISTICSSERVICEIMPL_GOODSBASESTATISTICS_TOTAL_KEY +
                "&" + StringUtils.defaultIfBlank(sku, "") +
                "&" + StringUtils.defaultIfBlank(proType, "") +
                "&" + StringUtils.defaultIfBlank(brand, "") +
                "&" + StringUtils.defaultIfBlank(region, "") +
                "&" + StringUtils.defaultIfBlank(country, "") +
                "&" + String.valueOf(startDate == null ? -1 : startDate.getTime()) +
                "&" + String.valueOf(endDate == null ? -1 : endDate.getTime());
    }

    @Transactional(readOnly = true)
    public List<GoodsBookDetail> goodsBookDetail(Integer orderId) throws Exception {
        List<GoodsBookDetail> result = findGoodsListOfOrder(orderId);

        List<Integer> goodsIds = result.parallelStream().map(vo -> vo.getGoodsId()).collect(Collectors.toList());
        // 完善采购信息
        result = mergePurchGoodsInfo(result, statisticsDao.findPurchGoods(goodsIds));
        // 完善报检申请信息
        result = mergeInspectApplyGoodsInfo(result, statisticsDao.findInspectApplyGoods(goodsIds));
        // 完善出库物流信息
        result = mergeDeliverConsignGoodsInfo(result, statisticsDao.findDeliverConsignGoods(goodsIds));

        Collections.sort(result, new Comparator<GoodsBookDetail>() {
            @Override
            public int compare(GoodsBookDetail b1, GoodsBookDetail b2) {
                String purchNo1 = b1.getPurchNo();
                String purchNo2 = b2.getPurchNo();
                if (purchNo1 != null && purchNo2 != null) {

                    Collator ca = Collator.getInstance(Locale.CHINA);
                    if (ca.compare(purchNo1, purchNo2) > 0) {
                        return 1;
                    } else if (ca.compare(purchNo1, purchNo2) < 0) {
                        return -1;
                    }
                    return 0;
                }
                return -1;
            }
        });
        return result;
    }


    @Override
    public HSSFWorkbook generateGoodsBookDetailExcel(Integer orderId) throws Exception {
        List<GoodsBookDetail> goodsBookDetails = goodsBookDetail(orderId);
        String[] header = new String[]{"平台SKU", "产品分类", "外文品名", "中文品名", "单位", "品牌", "规格型号", "采购数量",
                "单价", "采购金额（美元）", "采购合同号", "供应商", "采购合同签订日期", "合同约定到货日期", "采购给供应商付款方式",
                "给工厂付款日期", "采购到货日期", "报检日期", "检验完成日期", "入库日期", "下发订舱日期", "市场要求订舱时间",
                "物流经办人", "包装完成日期", "离厂日期", "物流发票号", "通知市场箱单日期", "船期或航班", "预计抵达日期"};
        String[] keys = new String[]{"sku", "proType", "nameEn", "nameZh", "unit", "brand", "model", "purchaseNum",
                "purchasePrice", "purchaseTotalPrice", "purchNo", "supplierName", "signingDate", "arrivalDate", "payType",
                "payFactoryDate", "inspectDate", "inspectDate", "checkDate", "instockDate", "bookingTime", "requireBookingDate",
                "logisticsUserName", "packDoneDate", "leaveFactory", "logiInvoiceNo", "packingTime", "sailingDate", "arrivalPortTime"};
        BuildExcel buildExcel = new BuildExcelImpl();
        Object data = JSON.toJSON(goodsBookDetails);
        HSSFWorkbook workbook = buildExcel.buildExcel((List) data, header, keys, "项目详情信息");
        return workbook;
    }

    /**
     * 查询订单的基本商品信息
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    private List<GoodsBookDetail> findGoodsListOfOrder(Integer orderId) throws Exception {
        Order order = orderDao.findOne(orderId);
        if (order == null) {
            throw new Exception(String.format("%s%s%s", "订单不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Order does not exist"));
        }
        List<Goods> goodsList = order.getGoodsList();
        Project project = order.getProject();
        List<GoodsBookDetail> resultList = new ArrayList<>();
        if (goodsList != null && goodsList.size() > 0) {
            for (Goods goods : goodsList) {
                GoodsBookDetail goodsBookDetail = new GoodsBookDetail();
                goodsBookDetail.setGoods(goods);
                if (project != null && Project.PurchReqCreateEnum.valueOfCode(project.getPurchReqCreate()) == Project.PurchReqCreateEnum.SUBMITED) {
                    goodsBookDetail.setProType(goods.getProType());
                }
                resultList.add(goodsBookDetail);
            }
        }
        return resultList;
    }

    /**
     * 商品台账合并采购信息并返回
     *
     * @param list
     * @param purchGoodsInfo
     * @return
     */
    private List<GoodsBookDetail> mergePurchGoodsInfo(List<GoodsBookDetail> list, List<Object> purchGoodsInfo) throws CloneNotSupportedException {
        if (purchGoodsInfo == null || purchGoodsInfo.size() == 0) {
            return list;
        }
        List<GoodsBookDetail> result = new ArrayList<>();
        for (GoodsBookDetail goodsBookDetail : list) {
            List<GoodsBookDetail> innerList = new ArrayList<>();
            innerList.add(goodsBookDetail);
            int contractGoodsNum = goodsBookDetail.getContractGoodsNum();
            for (Object obj : purchGoodsInfo) {
                Object[] objArr = (Object[]) obj;
                int goodsId = (Integer) objArr[0];
                if (goodsBookDetail.getGoodsId() != goodsId) {
                    continue;
                }
                int purchaseNum = (Integer) objArr[1];
                BigDecimal purchasePrice = (BigDecimal) objArr[2];
                BigDecimal purchaseTotalPrice = (BigDecimal) objArr[3];
                String purchNo = (String) objArr[4];
                Date signingDate = (Date) objArr[5];
                Date arrivalDate = (Date) objArr[6];
                Integer payType = (Integer) objArr[7];
                Date payFactoryDate = (Date) objArr[8];
                String currencyBn = (String) objArr[9];
                String supplierName = (String) objArr[10];
                contractGoodsNum -= purchaseNum;

                GoodsBookDetail clone = (GoodsBookDetail) goodsBookDetail.clone();
                clone.setContractGoodsNum(purchaseNum);
                clone.setPurchaseNum(purchaseNum);
                clone.setPurchasePrice(purchasePrice);
                clone.setPurchaseTotalPrice(purchaseTotalPrice);
                clone.setPurchNo(purchNo);
                clone.setSigningDate(signingDate);
                clone.setArrivalDate(arrivalDate);
                clone.setPayType(payType);
                clone.setPayFactoryDate(payFactoryDate);
                clone.setCurrencyBn(currencyBn);
                clone.setSupplierName(supplierName);

                innerList.add(clone);
            }
            if (contractGoodsNum > 0) {
                innerList.get(0).setContractGoodsNum(contractGoodsNum);
            } else {
                innerList.remove(0);
            }
            result.addAll(innerList);
        }
        return result;
    }


    /**
     * 商品台账合并报检信息并返回
     *
     * @param list
     * @param inspectApplyGoodsInfo
     * @return
     */
    private List<GoodsBookDetail> mergeInspectApplyGoodsInfo(List<GoodsBookDetail> list, List<Object> inspectApplyGoodsInfo) throws CloneNotSupportedException {
        if (inspectApplyGoodsInfo == null || inspectApplyGoodsInfo.size() == 0) {
            return list;
        }
        List<GoodsBookDetail> result = new ArrayList<>();
        for (GoodsBookDetail goodsBookDetail : list) {
            List<GoodsBookDetail> innerList = new ArrayList<>();
            innerList.add(goodsBookDetail);
            Integer purchaseNum = goodsBookDetail.getPurchaseNum();
            if (purchaseNum == null || purchaseNum < 1) {
                result.addAll(innerList);
                continue;
            }
            for (Object obj : inspectApplyGoodsInfo) {
                Object[] objArr = (Object[]) obj;
                int goodsId = (Integer) objArr[0];
                int iInspectNum = (Integer) objArr[1];
                int qualifiedNum = ((Number) objArr[2]).intValue();
                Date inspectDate = (Date) objArr[3];
                String supplierName = (String) objArr[4];
                Date checkDate = (Date) objArr[5];
                Date instockDate = (Date) objArr[6];
                if (goodsBookDetail.getGoodsId() != goodsId || qualifiedNum == 0) {
                    continue;
                }
                int onceQualifiedNum = 0;
                if (qualifiedNum > purchaseNum) {
                    qualifiedNum -= purchaseNum;
                    onceQualifiedNum = purchaseNum;
                    purchaseNum = 0;
                } else {
                    purchaseNum -= qualifiedNum;
                    onceQualifiedNum = qualifiedNum;
                    qualifiedNum = 0;
                }
                objArr[2] = qualifiedNum;

                GoodsBookDetail clone = (GoodsBookDetail) goodsBookDetail.clone();
                clone.setPurchaseNum(iInspectNum);
                clone.setQualifiedNum(onceQualifiedNum);
                clone.setInspectDate(inspectDate);
                //clone.setSupplierName(supplierName);
                clone.setCheckDate(checkDate);
                clone.setInstockDate(instockDate);
                innerList.add(clone);

                if (purchaseNum == 0) {
                    break;
                }
            }
            if (purchaseNum > 0) {
                innerList.get(0).setPurchaseNum(purchaseNum);
            } else {
                innerList.remove(0);
            }
            result.addAll(innerList);
        }
        return result;
    }


    /**
     * 商品台账合并出库物流信息并返回
     *
     * @param list
     * @param deliverConsignGoodsInfo
     * @return
     */
    private List<GoodsBookDetail> mergeDeliverConsignGoodsInfo(List<GoodsBookDetail> list, List<Object> deliverConsignGoodsInfo) throws CloneNotSupportedException {
        if (deliverConsignGoodsInfo == null || deliverConsignGoodsInfo.size() == 0) {
            return list;
        }
        List<GoodsBookDetail> result = new ArrayList<>();
        for (GoodsBookDetail goodsBookDetail : list) {
            List<GoodsBookDetail> innerList = new ArrayList<>();
            innerList.add(goodsBookDetail);
            Integer qualifiedNum = goodsBookDetail.getQualifiedNum();
            if (qualifiedNum == null || qualifiedNum < 1) {
                result.addAll(innerList);
                continue;
            }
            for (Object obj : deliverConsignGoodsInfo) {
                Object[] objArr = (Object[]) obj;
                int goodsId = (Integer) objArr[0];
                int sendNum = (Integer) objArr[1];
                Date requireBookingDate = (Date) objArr[2];
                Date packDoneDate = (Date) objArr[3];
                Date bookingTime = (Date) objArr[4];
                String logisticsUserName = (String) objArr[5];
                Date leaveFactory = (Date) objArr[6];
                String logiInvoiceNo = (String) objArr[7];
                Date packingTime = (Date) objArr[8];
                Date sailingDate = (Date) objArr[9];
                Date arrivalPortTime = (Date) objArr[10];
                Date accomplishDate = (Date) objArr[11];
                if (goodsBookDetail.getGoodsId() != goodsId || sendNum == 0) {
                    continue;
                }
                int onceSendNum = 0;
                if (sendNum > qualifiedNum) {
                    sendNum -= qualifiedNum;
                    onceSendNum = qualifiedNum;
                    qualifiedNum = 0;
                } else {
                    qualifiedNum -= sendNum;
                    onceSendNum = sendNum;
                    sendNum = 0;
                }
                objArr[1] = sendNum;
                GoodsBookDetail clone = (GoodsBookDetail) goodsBookDetail.clone();
                clone.setSendNum(onceSendNum);
                clone.setQualifiedNum(onceSendNum);
                clone.setRequireBookingDate(requireBookingDate);
                clone.setPackDoneDate(packDoneDate);
                clone.setBookingTime(bookingTime);
                clone.setLogisticsUserName(logisticsUserName);
                clone.setLeaveFactory(leaveFactory);
                clone.setLogiInvoiceNo(logiInvoiceNo);
                clone.setPackingTime(packingTime);
                clone.setSailingDate(sailingDate);
                clone.setArrivalPortTime(arrivalPortTime);
                clone.setAccomplishDate(accomplishDate);
                innerList.add(clone);

                if (qualifiedNum == 0) {
                    break;
                }
            }
            if (qualifiedNum > 0) {
                innerList.get(0).setQualifiedNum(qualifiedNum);
            } else {
                innerList.remove(0);
            }
            result.addAll(innerList);
        }
        return result;
    }


    @Override
    public Map<String, String> findBnMapZhCountry() {
        Map<String, String> result = new HashMap<>();
        List<Object> countrys = statisticsDao.findBnMapZhCountry();
        if (countrys != null && countrys.size() > 0) {
            for (Object vo : countrys) {
                Object[] strArr = (Object[]) vo;
                String s0 = (String) strArr[0];
                String s1 = (String) strArr[1];
                if (StringUtils.isNotBlank(s1)) {
                    result.put(s0, s1);
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, String> findBnMapZhRegion() {
        Map<String, String> result = new HashMap<>();
        List<Object> regions = statisticsDao.findBnMapZhRegion();
        if (regions != null && regions.size() > 0) {
            for (Object vo : regions) {
                Object[] strArr = (Object[]) vo;
                String s0 = (String) strArr[0];
                String s1 = (String) strArr[1];
                if (StringUtils.isNotBlank(s1)) {
                    result.put(s0, s1);
                }
            }
        }
        return result;
    }


    private Specification specificationCondition(Map<String, String> condition) {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                String startDateStr = condition.get("startDate");
                String endDateStr = condition.get("endDate");
                if (StringUtils.isNotBlank(startDateStr)) {
                    Date startDate = DateUtil.parseString2DateNoException(startDateStr, "yyyy-MM-dd");
                    if (startDate != null) {
                        list.add(cb.greaterThanOrEqualTo(root.get("startDate").as(Date.class), startDate));
                    }
                }
                if (StringUtils.isNotBlank(endDateStr)) {
                    Date endDate = DateUtil.parseString2DateNoException(endDateStr, "yyyy-MM-dd");
                    if (endDate != null) {
                        endDate = NewDateUtil.plusDays(endDate, 1);
                        list.add(cb.lessThan(root.get("startDate").as(Date.class), endDate));
                    }
                }
                String startTime = condition.get("startTime");
                String endTime = condition.get("endTime");
                if (StringUtils.isNotBlank(startTime)) {
                    Date startDate = DateUtil.parseString2DateNoException(startTime, "yyyy-MM-dd");
                    Date startT = DateUtil.getOperationTime(startDate, 0, 0, 0);
                    if (startDate != null) {
                        list.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), startT));
                    }
                }
                if (StringUtils.isNotBlank(endTime)) {
                    Date endDate = DateUtil.parseString2DateNoException(endTime, "yyyy-MM-dd");
                    Date endT = DateUtil.getOperationTime(endDate, 23, 59, 59);
                    if (endDate != null) {
                        list.add(cb.lessThan(root.get("createTime").as(Date.class), endT));
                    }
                }
                Join<Project, Order> orderRoot = root.join("order");
              /*  String countriesStr = condition.get("countries");
                if (StringUtils.isNotBlank(countriesStr)) {
                    String[] countriesArr = countriesStr.split(",");
                    list.add(orderRoot.get("country").in(countriesArr));
                }*/
                // 区域
                String region = condition.get("region");
                if (StringUtil.isNotBlank(region)) {
                    list.add(cb.equal(orderRoot.get("region").as(String.class), region));
                }
                // 国家
                String country = condition.get("country");
                if (StringUtil.isNotBlank(country)) {
                    list.add(cb.equal(orderRoot.get("country").as(String.class), country));
                }
                //  crmCode名称
                String crmCode = condition.get("crmCode");
                if (StringUtil.isNotBlank(crmCode)) {
                    list.add(cb.like(orderRoot.get("crmCode").as(String.class), "%" + crmCode + "%"));
                }
                // 销售合同号
                String contractNo = condition.get("contractNo");
                if (StringUtil.isNotBlank(contractNo)) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + contractNo + "%"));
                }
                // 海外销售合同号
                String contractNoOs = condition.get("contractNoOs");
                if (StringUtil.isNotBlank(contractNoOs)) {
                    list.add(cb.like(orderRoot.get("contractNoOs").as(String.class), "%" + contractNoOs + "%"));
                }
                // 项目号
                String projectNo = condition.get("projectNo");
                if (StringUtil.isNotBlank(projectNo)) {
                    list.add(cb.like(root.get("projectNo").as(String.class), "%" + projectNo + "%"));
                }
                //  合同标的
                String projectName = condition.get("projectName");
                if (StringUtil.isNotBlank(projectName)) {
                    list.add(cb.like(root.get("projectName").as(String.class), "%" + projectName + "%"));
                }
                // 分销部
                String sendDeptId = condition.get("sendDeptId");
                if (StringUtil.isNotBlank(sendDeptId)) {
                    list.add(cb.equal(root.get("sendDeptId").as(String.class), sendDeptId));
                }
               /* // 分销部
                String distributionDeptName = condition.get("distributionDeptName");
                if (StringUtil.isNotBlank(distributionDeptName)) {
                    list.add(cb.equal(root.get("distributionDeptName").as(String.class), distributionDeptName));
                }*/
                // 事业部
                String businessUnitName = condition.get("businessUnitName");
                if (StringUtil.isNotBlank(businessUnitName)) {
                    list.add(cb.equal(root.get("businessUnitName").as(String.class), businessUnitName));
                }
                //执行单变更后日期
                String exeChgDate = condition.get("exeChgDate");
                if (StringUtil.isNotBlank(exeChgDate)) {
                    Date date = DateUtil.str2Date(exeChgDate);
                    if (date != null) {
                        list.add(cb.equal(root.get("exeChgDate").as(Date.class), date));
                    }
                }
                //执行单约定交付日期
                String deliveryDate = condition.get("deliveryDate");
                if (StringUtil.isNotBlank(deliveryDate)) {
                    if (StringUtil.isNotBlank(deliveryDate)) {
                        Date date = DateUtil.str2Date(deliveryDate);
                        list.add(cb.equal(root.get("deliveryDate").as(Date.class), date));
                    }
                }
                //根据执行分公司查询
                String execCoName = condition.get("execCoName");
                if (StringUtil.isNotBlank(execCoName)) {
                    list.add(cb.like(root.get("execCoName").as(String.class), "%" + execCoName + "%"));
                }
                //项目状态
                String projectStatus = condition.get("projectStatus");
                if (StringUtil.isNotBlank(projectStatus)) {
                    list.add(cb.equal(root.get("projectStatus").as(String.class), projectStatus));
                }
                list.add(cb.notEqual(root.get("projectStatus").as(String.class), "DRAFT")); // 不等于待确定的
                list.add(cb.notEqual(root.get("projectStatus").as(String.class), "SUBMIT")); // 不等于待确定的
                list.add(cb.notEqual(root.get("projectStatus").as(String.class), "HASMANAGER")); // 不等于有项目经理的的
                //流程进度
                String processProgress = condition.get("processProgress");
                //根据流程进度
                if (StringUtil.isNotBlank(processProgress)) {
                    list.add(cb.greaterThanOrEqualTo(root.get("processProgress").as(String.class), processProgress));
                } else {
                    list.add(cb.notEqual(root.get("processProgress").as(Integer.class), 1));
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        };
    }


    /**
     * 订单主流程监控 数据处理
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findOrderMainProcess(Map<String, String> params) {
        //分页条件
        int page = 0;
        int rows = 50;
        String pageStr = params.get("page");
        String rowsStr = params.get("rows");
        if (StringUtils.isNumeric(pageStr)) {
            page = Integer.parseInt(pageStr) - 1;
            if (page < 0) {
                page = 0;
            }
        }
        if (StringUtils.isNumeric(rowsStr)) {
            rows = Integer.parseInt(rowsStr);
            if (rows < 1) {
                rows = 50;
            }
        }
        //查询数据
        Page<Project> projectList = findProject(page, rows, params);
        List<OrderMainProcess> orderMainProcess = new ArrayList<>();
        //处理数据
        if (projectList.hasContent()) {
            List<Project> content = projectList.getContent();
            for (Project project : content) {
                OrderMainProcess omp = new OrderMainProcess();
                Order order = project.getOrder();   //获取订单
                List<Purch> purchs = project.getPurchs();   //采购列表


                omp.setContractNo(project.getContractNo());     //销售合同号
                omp.setOrderId(order.getId());   //订单id / 点击销售合同号查询
                omp.setTotalPriceUsd(order.getTotalPriceUsd()); // 合同总价(USD)
                omp.setCurrencyBn(order.getCurrencyBn()); //   订单货币类型
                omp.setRegion(project.getOrder().getRegion());     // 地区
                Map<String, String> bnMapZhCountry = findBnMapZhCountry();  //获取国家中英文   kay/vlaue
                omp.setCountry(bnMapZhCountry.get(project.getOrder().getCountry()));   //  国家
                omp.setProjectNo(project.getProjectNo());   //项目号
                omp.setProjectName(project.getProjectName());    //合同标的
                omp.setOrderStatus(order.getStatus()); //订单状态
                omp.setProjectStatus(project.getProjectStatus());   //项目状态
                omp.setPurchStatus(disposePurchsStatus(project, order.getGoodsList()));   //采购状态      根据采购条数，订单商品报检数量来判断
                omp.setProjectId(project.getId());//    项目id / 点击采购状态查询
                if (!"未执行".equals(omp.getPurchStatus())) {    //采购未执行状态   不计算采购订单金额
                    omp.setPurchOrdermoney(disposePurchOrdermoney(purchs)); //采购订单金额
                }
                omp.setInspectReportStatus(disposeInspectReportStatus(order));   //  入库质检状态
                omp.setInstockStatus(disposeInstockStatus(order.getGoodsList()));   //入库状态
                omp.setDeliverConsignStatus(disposeDeliverConsignStatus(order));  //订舱通知状态   / 出口通知单状态
                omp.setDeliverDetailStatus(disposeDeliverDetailStatus(order)); //出库状态
                omp.setLogisticsDataStatus(disposeLogisticsDataStatus(order, null));//发运状态
                omp.setLogisticsPrice(disposeLogisticsPrice(order));    //发运金额
                omp.setConfirmTheStatus(disposeLogisticsDataStatus(order, 1));  //收货状态
                omp.setPayStatus(order.getPayStatus()); //收款状态
                String currencyBn = order.getCurrencyBn();//订单结算币种
                BigDecimal exchangeRate = order.getExchangeRate() == null ? BigDecimal.valueOf(0) : order.getExchangeRate();//订单利率

                BigDecimal alreadyGatheringMoney = order.getAlreadyGatheringMoney() == null ? BigDecimal.valueOf(0) : order.getAlreadyGatheringMoney();  //已收款总金额
                if (currencyBn != "USD") {
                    omp.setAlreadyGatheringMoney(alreadyGatheringMoney.multiply(exchangeRate));//     收款金额  /  已收款总金额
                } else {
                    omp.setAlreadyGatheringMoney(alreadyGatheringMoney);//     收款金额  /  已收款总金额
                }
                BigDecimal receivableAccountRemaining = order.getReceivableAccountRemaining() == null ? BigDecimal.valueOf(0) : order.getReceivableAccountRemaining();  //   应收账款余额
                BigDecimal multiply = receivableAccountRemaining.multiply(exchangeRate);    //应收账款余额*订单利率=应收账款余额(USD)
                omp.setReceivableAccountRemaining(multiply); //   应收账款余额
                if (purchs.size() > 0) {
                    Purch purch = purchs.get(0);
                    if (purch != null) {
                        omp.setPurchCurrencyBn(purch.getCurrencyBn() == null ? "" : purch.getCurrencyBn());   //  采购币种
                    }

                }

                orderMainProcess.add(omp);
            }
        }

        Map<String, Object> resultMap = new HashMap();
        resultMap.put("content", orderMainProcess);  //数据信息


        //处理分页数据
        if (projectList.hasContent()) {
            List<Project> content = projectList.getContent();
            resultMap.put("totalPage", projectList.getTotalPages());    //总页数       Math.ceil 向上取整  总条数/每页条数
            resultMap.put("total", projectList.getTotalElements());    //总条数
            resultMap.put("rows", projectList.getSize());    //每页条数
            resultMap.put("page", pageStr);    //页数
        } else {
            resultMap.put("totalPage", 0);    //总页数       Math.ceil 向上取整  总条数/每页条数
            resultMap.put("total", 0);    //总条数
            resultMap.put("rows", 0);    //每页条数
            resultMap.put("page", 0);    //页数
        }


        return resultMap;
    }


    /**
     * 订单主流程监控 查询数据
     *
     * @param params
     * @return
     */
    public Page<Project> findProject(Integer page, Integer rows, Map<String, String> params) {
        PageRequest pageRequest = new PageRequest(page, rows, new Sort(Sort.Direction.DESC, "id"));

        Page<Project> projectList = projectDao.findAll(new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                //销售合同号
                if (StringUtil.isNotBlank(params.get("contractNo"))) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + params.get("contractNo") + "%"));
                }
                //项目号
                if (StringUtil.isNotBlank(params.get("projectNo"))) {
                    list.add(cb.like(root.get("projectNo").as(String.class), "%" + params.get("projectNo") + "%"));
                }
                //合同标的
                if (StringUtil.isNotBlank(params.get("projectName"))) {
                    list.add(cb.like(root.get("projectName").as(String.class), "%" + params.get("projectName") + "%"));
                }
                //项目状态
                if (StringUtil.isNotBlank(params.get("projectStatus"))) {
                    list.add(cb.equal(root.get("projectStatus").as(String.class), params.get("projectStatus")));
                }
                //地区
                if (StringUtil.isNotBlank(params.get("region"))) {
                    list.add(cb.like(root.get("region").as(String.class), "%" + params.get("region") + "%"));
                }
                //国家
                if (StringUtil.isNotBlank(params.get("country"))) {
                    list.add(cb.like(root.get("country").as(String.class), "%" + params.get("country") + "%"));
                }

                String[] projectStatus = {"EXECUTING", "DONE", "DELAYED_EXECUTION", "DELAYED_COMPLETE", "UNSHIPPED", "DELAYED_UNSHIPPED", "PAUSE", "CANCEL"};
                list.add(root.get("projectStatus").in(projectStatus));

                //关联订单
                Join<Project, Order> orderRoot = root.join("order");
                //订单状态
                if (StringUtil.isNotBlank(params.get("orderStatus"))) {
                    list.add(cb.equal(orderRoot.get("status").as(Integer.class), params.get("orderStatus")));
                }

                list.add(cb.greaterThan(orderRoot.get("status").as(Integer.class), 1));

                //采购状态
                if (StringUtil.isNotBlank(params.get("purchStatus"))) {
                    //获取采购状态条件
                    int purchStatus = Integer.parseInt(params.get("purchStatus"));
                    List<Project> projects = finByPurchStatus(purchStatus);     //根据采购状态查询
                    CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                    if (projects != null && projects.size() > 0) {
                        for (Project p : projects) {
                            idIn.value(p.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);
                }
                //入库质检状态
                if (StringUtil.isNotBlank(params.get("inspectReportStatus"))) {

                    Set<Project> Projects = finByInspectReportStatus(params);     //根据入库质检状态条件查询 or 入库状态条件查询
                    CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                    if (Projects != null && Projects.size() > 0) {
                        for (Project p : Projects) {
                            idIn.value(p.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);

                }
                //入库状态
                if (StringUtil.isNotBlank(params.get("instockStatus"))) {
                    Set<Project> Projects = finByInstockStatus(params);     //根据入库质检状态条件查询 or 入库状态条件查询
                    CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                    if (Projects != null && Projects.size() > 0) {
                        for (Project p : Projects) {
                            idIn.value(p.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);

                }
                //订舱通知状态
                if (StringUtil.isNotBlank(params.get("deliverConsignStatus"))) {
                    Integer deliverConsignStatus = Integer.parseInt(params.get("deliverConsignStatus"));

                    Set<Order> orders = finByDeliverConsignStatus(deliverConsignStatus); //根据订舱通知状态条件查询
                    CriteriaBuilder.In<Object> idIn = cb.in(orderRoot.get("id"));
                    if (orders != null && orders.size() > 0) {
                        for (Order o : orders) {
                            idIn.value(o.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);

                }

                //出库状态
                if (StringUtil.isNotBlank(params.get("deliverDetailStatus"))) {
                    Integer deliverDetailStatus = Integer.parseInt(params.get("deliverDetailStatus"));
                    Set<Order> orders = finByDeliverDetailStatus(deliverDetailStatus); //根据出库状态条件查询
                    CriteriaBuilder.In<Object> idIn = cb.in(orderRoot.get("id"));
                    if (orders != null && orders.size() > 0) {
                        for (Order o : orders) {
                            idIn.value(o.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);
                }

                //发运状态
                if (StringUtil.isNotBlank(params.get("logisticsDataStatus"))) {

                    Integer logisticsDataStatus = Integer.parseInt(params.get("logisticsDataStatus"));  //发运状态条件      1:未执行 2:执行中 3:已完成'

                    Set<Order> orders = finBylogisticsDataStatus(logisticsDataStatus, null); //根据发运状态条件查询
                    CriteriaBuilder.In<Object> idIn = cb.in(orderRoot.get("id"));
                    if (orders != null && orders.size() > 0) {
                        for (Order o : orders) {
                            idIn.value(o.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);
                }

                //收货状态
                if (StringUtil.isNotBlank(params.get("confirmTheStatus"))) {
                    Integer confirmTheStatus = Integer.parseInt(params.get("confirmTheStatus"));  //发运状态条件      1:未执行 2:执行中 3:已完成'

                    Set<Order> orders = finBylogisticsDataStatus(confirmTheStatus, confirmTheStatus); //根据收货状态条件查询
                    CriteriaBuilder.In<Object> idIn = cb.in(orderRoot.get("id"));
                    if (orders != null && orders.size() > 0) {
                        for (Order o : orders) {
                            idIn.value(o.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);
                }

                //收款状态
                if (StringUtil.isNotBlank(params.get("payStatus"))) {
                    list.add(cb.equal(orderRoot.get("payStatus").as(String.class), params.get("payStatus")));
                }


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }


        }, pageRequest);

        return projectList;
    }


    /**
     * 订单主流程监控 根据采购状态查询
     *
     * @param purchStatus
     * @return
     */
    public List<Project> finByPurchStatus(Integer purchStatus) {

        List<Project> page = projectDao.findAll(new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (purchStatus != 1) {
                    //关联采购
                    Join<Project, Purch> purchRoot = root.join("purchs");

                    list.add(cb.equal(purchRoot.get("deleteFlag").as(Integer.class), 0)); //查询未删除
                    list.add(cb.greaterThan(purchRoot.get("status").as(Integer.class), 1)); //大于查询，不查询保存的信息
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);

            }
        });

        List<Project> result = new ArrayList<>();

        if (page.size() > 0) {
            for (Project project : page) {
                Integer purchReqCreate = project.getPurchReqCreate();   // 1：未创建  2：已创建 3:已创建并提交',
                Boolean purchDone = project.getPurchDone();     //是否采购完成，1：完成  0：未完成

                List<Purch> purchs = project.getPurchs();   //获取采购列表
                Integer size = purchs.size();   //获取采购长度
                outer:
                if (purchStatus == 1) {     // 采购状态条件：  1.未执行

                    if (purchReqCreate == 3) {    // 3:已创建并提交'
                        if (size == 0 || size == null) {
                            result.add(project);    //如果没有采购信息   说明没有执行
                            break outer;
                        } else {

                            List<Integer> purchStatusList = new ArrayList();
                            for (Purch purch : purchs) {
                                purchStatusList.add(purch.getStatus());
                            }

                            List<Integer> purchStatusListFlag = new ArrayList();
                            purchStatusListFlag.add(2);
                            purchStatusListFlag.add(3);

                            if (disposeList(purchStatusList, purchStatusListFlag)) {
                                break outer;
                            } else {
                                result.add(project);    //如果没有采购提交   说明没有执行
                                break outer;
                            }
                        }
                    } else {
                        result.add(project);    //如果没有采购信息   说明没有执行
                        break outer;
                    }


                } else external:if (purchStatus == 2) {    //执行中
                    if (purchReqCreate == 3) {
                        if (size == 0 || size == null) { //如果没有采购信息   说明没有执行
                            break external;
                        } else {
                            List<Integer> purchStatusList = new ArrayList();
                            for (Purch purch : purchs) {
                                purchStatusList.add(purch.getStatus());
                            }

                            List<Integer> purchStatusListFlag = new ArrayList();
                            purchStatusListFlag.add(2);
                            purchStatusListFlag.add(3);

                            if (disposeList(purchStatusList, purchStatusListFlag)) {   //判断是否 执行中，已完成都有
                                if (!purchStatusList.contains(2)) {   //判断是否全部是 已完成   ,判断是否有执行中
                                    if (purchStatusList.contains(1)) {    //判断是否还存在未执行的       如果有已完成，有未执行  返回执行中
                                        result.add(project);
                                        break external;
                                    } else {
                                        if (purchDone) {   //如果全部是已完成，判断是否全部采购完成
                                            break external;
                                        } else {
                                            result.add(project);
                                            break external;
                                        }
                                    }
                                } else {
                                    result.add(project);
                                    break external;
                                }

                            } else {
                                break external;
                            }
                        }
                    } else {
                        break external;
                    }


                } else out:if (purchStatus == 3) {    //已完成

                    if (purchReqCreate == 3 && purchDone == true) {
                        if (size == 0 || size == null) { //如果没有采购信息   说明没有执行
                            break out;
                        } else {
                            List<Integer> purchStatusList = new ArrayList();
                            for (Purch purch : purchs) {
                                purchStatusList.add(purch.getStatus());
                            }

                            List<Integer> purchStatusListFlag = new ArrayList();
                            purchStatusListFlag.add(1);
                            purchStatusListFlag.add(2);

                            if (!disposeList(purchStatusList, purchStatusListFlag)) {
                                result.add(project);
                            }
                            break out;
                        }
                    } else {
                        break out;
                    }

                }


            }


        }




       /* List<Project> result = new ArrayList<>();

        for (Project project : page){
            List<Purch> purchs = project.getPurchs();   //获取采购列表
            Integer size = purchs.size();   //获取采购长度
            outer:if(purchStatus == 1){     //未执行
                if(size == 0 || size == null){
                    result.add(project);    //如果没有采购信息   说明没有执行
                    break outer;
                }else {

                    List<Integer> purchStatusList = new ArrayList();
                    for (Purch purch : purchs) {
                        purchStatusList.add(purch.getStatus());
                    }

                    List<Integer> purchStatusListFlag = new ArrayList();
                    purchStatusListFlag.add(2);
                    purchStatusListFlag.add(3);

                    if(disposeList(purchStatusList,purchStatusListFlag)){
                        for (Purch purch : purchs){
                            List<PurchGoods> purchGoodsList = purch.getPurchGoodsList();    //获取采购商品信息
                            if(purchGoodsList != null){
                                Integer contractGoodsNums = 0 ;
                                Integer prePurchsedNums = 0;
                                for (PurchGoods purchGoods : purchGoodsList){
                                    Goods goods = purchGoods.getGoods();    //商品信息
                                    contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                                    prePurchsedNums += goods.getPurchasedNum();//已采购数量
                                }
                                if(prePurchsedNums != 0){    // 不等于0说明是采购中/执行中
                                    break outer;
                                }else {
                                    result.add(project);    //如果没有采购数量   说明没有执行
                                    break outer;
                                }
                            }else {
                                result.add(project);    //如果没有采购信息   说明没有执行
                                break outer;
                            }
                        }
                    }else {
                        result.add(project);    //如果没有采购提交   说明没有执行
                        break outer;
                    }
                }

            }else external:if(purchStatus == 2){    //执行中
                if(size == 0 || size == null){ //如果没有采购信息   说明没有执行
                    break external;
                }else {
                    List<Integer> purchStatusList = new ArrayList();
                    for (Purch purch : purchs) {
                        purchStatusList.add(purch.getStatus());
                    }

                    List<Integer> purchStatusListFlag = new ArrayList();
                    purchStatusListFlag.add(2);
                    purchStatusListFlag.add(3);

                    if(disposeList(purchStatusList,purchStatusListFlag)){

                        for (Purch purch : purchs){
                            List<PurchGoods> purchGoodsList = purch.getPurchGoodsList();    //获取采购商品信息
                            if(purchGoodsList != null){
                                Integer contractGoodsNums = 0 ;
                                Integer prePurchsedNums = 0;
                                Integer inspectNums = 0 ;   //已报检
                                for (PurchGoods purchGoods : purchGoodsList){
                                    Goods goods = purchGoods.getGoods();    //商品信息
                                    contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                                    prePurchsedNums += goods.getPurchasedNum();//已采购数量
                                    inspectNums += goods.getInspectNum();// 已报检数量 / 全部报检合格，才算采购完成
                                }
                                if(contractGoodsNums >= prePurchsedNums && prePurchsedNums > 0 && contractGoodsNums > inspectNums){
                                    result.add(project);
                                    break external;
                                }else {
                                    break external;
                                }
                            }else {
                                break external;
                            }
                        }
                    }
                    break external;
                }
            }else out:if(purchStatus == 3){    //已完成
                if(size == 0 || size == null){ //如果没有采购信息   说明没有执行
                    break out;
                }else {
                    List<Integer> purchStatusList = new ArrayList();
                    for (Purch purch : purchs) {
                        purchStatusList.add(purch.getStatus());
                    }

                    List<Integer> purchStatusListFlag = new ArrayList();
                    purchStatusListFlag.add(2);
                    purchStatusListFlag.add(3);

                    if(disposeList(purchStatusList,purchStatusListFlag)){

                        for (Purch purch : purchs){
                            List<PurchGoods> purchGoodsList = purch.getPurchGoodsList();    //获取采购商品信息
                            if(purchGoodsList != null){
                                Integer contractGoodsNums = 0 ;
                                Integer inspectNums = 0;
                                for (PurchGoods purchGoods : purchGoodsList){
                                    Goods goods = purchGoods.getGoods();    //商品信息
                                        contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                                        inspectNums += goods.getInspectNum();// 已报检数量 / 全部报检合格，才算采购完成
                                }
                                if(contractGoodsNums <= inspectNums){ //不相等没有采购完成
                                    result.add(project);
                                    break out;
                                }else {
                                    break out;
                                }
                            }else {
                                break out;
                            }
                        }
                    }
                    break out;
                }
            }


        }*/


        return result;
    }

    /**
     * 订单主流程监控  入库质检状态条件
     *
     * @return
     */
    private Set<Project> finByInspectReportStatus(Map<String, String> params) {
        List<Project> all = projectDao.findAll();

        //获取入库质检状态条件
        String inspectReportStatus = params.get("inspectReportStatus");

        Set<Project> result = new HashSet<>();
        if (StringUtil.isNotBlank(inspectReportStatus)) {
            Integer inspectReportStatus2 = Integer.parseInt(inspectReportStatus);

            outer:
            for (Project project : all) {
                List<Goods> goodsList = project.getOrder().getGoodsList();
                if (goodsList != null && goodsList.size() > 0) {
                    Integer contractGoodsNums = 0;  // 本订单商品  合同商品数量
                    Integer goodNums = 0;    // 本订单商品 检验合格商品数量

                    for (Goods goods : goodsList) {
                        contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                        List<PurchGoods> purchGoodsList = goods.getPurchGoods();  //已报检商品信息
                        if (purchGoodsList.size() > 0) {
                            for (PurchGoods purchGoods : purchGoodsList) {
                                Integer goodNum = purchGoods.getGoodNum() == null ? 0 : purchGoods.getGoodNum();//检验合格商品数量
                                goodNums += goodNum;
                            }
                        }
                    }
                    //  inspectReportStatus  1:未执行 2:执行中  3:已完成
                    if (inspectReportStatus2 == 1) {
                        if (goodNums == 0) {
                            result.add(project);
                        }

                    } else if (inspectReportStatus2 == 3) {
                        if (goodNums >= contractGoodsNums && goodNums != 0) {
                            result.add(project);
                        }
                    } else if (inspectReportStatus2 == 2) {
                        if (goodNums < contractGoodsNums && goodNums != 0) {
                            result.add(project);
                        }
                    }
                }
            }
        }
        /*else if(StringUtil.isNotBlank(inspectReportStatus) && StringUtil.isNotBlank(instockStatus)){
            Integer inspectReportStatus2 = Integer.parseInt(inspectReportStatus);
            Integer instockStatus2 = Integer.parseInt(instockStatus);
            outer:for (Project project : all){
                List<Goods> goodsList = project.getOrder().getGoodsList();
                if(goodsList != null){
                    Integer contractGoodsNums = 0;  // 本订单商品  合同商品数量
                    Integer prePurchsedNums = 0;
                    Integer inspectNums = 0;    // 本订单商品  已报检数量
                    Integer instockNum = 0;    // 本订单商品  已入库数量
                    for (Goods goods : goodsList){
                        contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                        prePurchsedNums += goods.getPurchasedNum();//已采购数量
                        inspectNums += goods.getInspectNum();// 已报检数量
                        instockNum += goods.getInstockNum();// 已入库数量
                    }
                    //  inspectReportStatus  入库质检状态 1:未执行 2:执行中  3:已完成
                    if(inspectReportStatus2 == 1 && instockStatus2 == 2 || instockStatus2 == 3 ){
                        return null;
                    }else if (inspectReportStatus2 == 1 && instockStatus2 == 1){
                        if(prePurchsedNums == 0 && instockNum == 0){
                            result.add(project);
                        }

                    }else if(inspectReportStatus2 == 2 && instockStatus2 == 2 ){
                        if(!inspectNums.equals(contractGoodsNums)  && inspectNums != 0 && instockNum < contractGoodsNums && instockNum != 0){
                            result.add(project);
                        }

                    }else if(inspectReportStatus2 == 2 && instockStatus2 == 3 ){
                       return null;
                    }else if(inspectReportStatus2 == 2 && instockStatus2 == 1 ){
                        if(!inspectNums.equals(contractGoodsNums)  && inspectNums != 0  && instockNum == 0  ){
                            result.add(project);
                        }

                    }else if(inspectReportStatus2 == 3 && instockStatus2 == 1 ){
                        if(inspectNums >= contractGoodsNums && inspectNums != 0 && instockNum == 0){
                            result.add(project);
                        }

                    }else if(inspectReportStatus2 == 3 && instockStatus2 == 2 ){
                        if(inspectNums >= contractGoodsNums && inspectNums != 0 && instockNum < contractGoodsNums && instockNum != 0){
                            result.add(project);
                        }

                    }else if(inspectReportStatus2 == 3 && instockStatus2 == 3  ){
                        if(inspectNums >= contractGoodsNums && inspectNums != 0 && instockNum >= contractGoodsNums && instockNum != 0){
                            result.add(project);
                        }

                    }

                }
            }
        }*/

        return result;
    }

    /**
     * 订单主流程监控   入库状态条件查询
     *
     * @return
     */
    private Set<Project> finByInstockStatus(Map<String, String> params) {
        List<Project> all = projectDao.findAll();

        //获取入库状态条件
        String instockStatus = params.get("instockStatus");

        Set<Project> result = new HashSet<>();
        if (StringUtil.isNotBlank(instockStatus)) {
            Integer instockStatus2 = Integer.parseInt(instockStatus);
            outer:
            for (Project project : all) {
                List<Goods> goodsList = project.getOrder().getGoodsList();
                if (goodsList != null) {
                    Integer contractGoodsNums = 0;  // 本订单商品  合同商品数量
                    Integer instockNum = 0;    // 本订单商品  已入库数量
                    for (Goods goods : goodsList) {
                        contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                        instockNum += goods.getInstockNum();// 已入库数量
                    }
                    //  inspectReportStatus  1:未执行 2:执行中  3:已完成
                    if (instockStatus2 == 1) {
                        if (instockNum == 0) {
                            result.add(project);
                        }

                    } else if (instockStatus2 == 3) {
                        if (instockNum >= contractGoodsNums && instockNum != 0) {
                            result.add(project);
                        }
                    } else if (instockStatus2 == 2) {
                        if (instockNum < contractGoodsNums && instockNum != 0) {
                            result.add(project);
                        }
                    }
                }
            }
        }

        return result;
    }


    /**
     * 订单主流程监控 根据出库状态条件查询
     *
     * @param deliverConsignStatus
     * @return
     */
    private Set<Order> finByDeliverConsignStatus(Integer deliverConsignStatus) {

        Set<Order> result = new HashSet<>();
        List<Order> orderList = orderDao.findByDeleteFlag(false);   //0:未删除 1：已删除',

        if (orderList.size() > 0) {
            for (Order order : orderList) {
                Integer deliverConsignHas = order.getDeliverConsignHas() == null ? 1 : order.getDeliverConsignHas();   //deliverConsignHas 是否已生成出口通知单 1：未生成 2： 已生成',
                Boolean deliverConsignC = order.getDeliverConsignC(); //deliverConsignC   是否存在商品可以创建发货通知单 0：无 1：有', 0:false    1:true

                if (deliverConsignStatus == 1) {  //1:未执行 2:执行中 3:已完成'
                    if (deliverConsignHas == 1) { //未生成
                        result.add(order);
                    } else {

                        List<Integer> deliverConsignStatusList = new ArrayList<>();

                        List<DeliverConsign> deliverConsignList = order.getDeliverConsign();
                        if (deliverConsignList.size() > 0) {
                            for (DeliverConsign deliverConsign1 : deliverConsignList) {
                                if (deliverConsign1 != null) {
                                    deliverConsignStatusList.add(deliverConsign1.getStatus());
                                }
                            }

                            List<Integer> deliverConsignFlagList = new ArrayList<>();
                            deliverConsignFlagList.add(2);
                            deliverConsignFlagList.add(3);

                            if (!disposeList(deliverConsignFlagList, deliverConsignStatusList)) {
                                result.add(order);
                            }
                        } else {
                            result.add(order);
                        }
                    }

                } else if (deliverConsignStatus == 2) {
                    if (deliverConsignHas == 2) { //有生成
                        List<Integer> deliverConsignStatusList = new ArrayList<>();

                        List<DeliverConsign> deliverConsignList = order.getDeliverConsign();
                        if (deliverConsignList.size() > 0) {
                            for (DeliverConsign deliverConsign1 : deliverConsignList) {
                                if (deliverConsign1 != null) {
                                    deliverConsignStatusList.add(deliverConsign1.getStatus());
                                }
                            }

                            List<Integer> deliverConsignFlagList = new ArrayList<>();
                            deliverConsignFlagList.add(2);
                            deliverConsignFlagList.add(3);

                            if (deliverConsignC) {    //是否存在商品可以创建发货通知单 0：无 1：有', 0:false    1:true
                                if (disposeList(deliverConsignFlagList, deliverConsignStatusList)) {
                                    result.add(order);
                                }
                            } else {
                                List<Integer> deliverConsignFlagList2 = new ArrayList<>();
                                deliverConsignFlagList2.add(2);
                                deliverConsignFlagList2.add(1);
                                if (disposeList(deliverConsignFlagList2, deliverConsignStatusList)) {
                                    result.add(order);
                                }
                            }

                        }
                    }

                } else if (deliverConsignStatus == 3) {
                    if (deliverConsignHas == 2 && deliverConsignC == false) {
                        List<Integer> deliverConsignStatusList = new ArrayList<>();

                        List<DeliverConsign> deliverConsignList = order.getDeliverConsign();
                        if (deliverConsignList.size() > 0) {
                            for (DeliverConsign deliverConsign1 : deliverConsignList) {
                                if (deliverConsign1 != null) {
                                    deliverConsignStatusList.add(deliverConsign1.getStatus());
                                }
                            }

                            List<Integer> deliverConsignFlagList = new ArrayList<>();
                            deliverConsignFlagList.add(1);
                            deliverConsignFlagList.add(2);

                            if (!disposeList(deliverConsignFlagList, deliverConsignStatusList)) {
                                result.add(order);
                            }

                        }
                    }

                }

            }
        }

        return result;
    }


    /**
     * 订单主流程监控 根据出库状态条件查询
     *
     * @param params
     * @return
     */
    public Set<Order> finByDeliverDetailStatus(Integer params) {

        Set<Order> result = new HashSet<>();
        List<Order> orderList = orderDao.findByDeleteFlag(false);   //0:未删除 1：已删除',

        for (Order order : orderList) {
            Integer deliverConsignHas = order.getDeliverConsignHas() == null ? 1 : order.getDeliverConsignHas();   //是否已生成出口通知单 1：未生成 2： 已生成',
            Boolean deliverConsignC = order.getDeliverConsignC();   //是否存在商品可以创建发货通知单 0：无 1：有'

            if (params == 1) {    //未执行
                if (deliverConsignHas == 1) { //判断是否生成过出口通知单 1,，未生成过
                    result.add(order);
                } else {
                    List<DeliverConsign> deliverConsign = order.getDeliverConsign();//获取出口发货通知单
                    if (deliverConsign.size() <= 0) { //如果没有出口通知单，说明没有执行
                        result.add(order);
                    } else {
                        List<Integer> deliverConsignStatusList = new ArrayList<>();   //拿到全部出口发货通知状态
                        List<Integer> deliverDetailStatusList = new ArrayList<>();   //拿到全部出库状态
                        for (DeliverConsign deliverConsign1 : deliverConsign) {
                            Integer status1 = deliverConsign1.getStatus();
                            deliverConsignStatusList.add(status1);
                            if (status1 > 2) {
                                DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                                if (deliverDetail != null) {
                                    Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                                    deliverDetailStatusList.add(status);
                                }
                            }
                        }
                        if (!deliverConsignStatusList.contains(3)) {  //如果出库通知单中没有状态等于已出库说明没有执行
                            result.add(order);
                        }

                        if (!deliverDetailStatusList.contains(5)) {   //如果出库状态中没有确认出库，说明没有执行
                            result.add(order);
                        }
                    }
                }
            } else outer:if (params == 2) {
                if (deliverConsignHas == 1) { //判断是否生成过出口通知单 1,，未生成过
                    break outer;
                } else {
                    List<DeliverConsign> deliverConsign = order.getDeliverConsign();//获取出口发货通知单
                    if (deliverConsign.size() <= 0) { //如果没有出口通知单，说明没有执行
                        break outer;
                    } else {
                        List<Integer> deliverConsignStatusList = new ArrayList<>();   //拿到全部出口发货通知状态
                        List<Integer> deliverDetailStatusList = new ArrayList<>();   //拿到全部出库状态
                        for (DeliverConsign deliverConsign1 : deliverConsign) {
                            Integer status1 = deliverConsign1.getStatus();
                            deliverConsignStatusList.add(status1);
                            if (status1 > 2) {
                                DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                                if (deliverDetail != null) {
                                    Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                                    deliverDetailStatusList.add(status);
                                }
                            }
                        }

                        Boolean flag = false;   //是否全部生成完成  如果没有生成完成是false
                        Boolean flag2 = false;  //是否全部出库        如果等于true说明没有全部出库

                        if (!deliverConsignC) {
                            flag = true;
                        }

                        if (!deliverConsignStatusList.contains(3)) {  //如果出库通知单中没有状态等于已出库说明没有执行
                            break outer;
                        }


                        if (!deliverDetailStatusList.contains(5)) {   //如果出库状态中没有确认出库，说明没有执行
                            break outer;
                        }

                        List<Integer> deliverConsignStatusFlag = new ArrayList<>();   //未出库通知状态
                        deliverConsignStatusFlag.add(1);
                        deliverConsignStatusFlag.add(2);

                        if (disposeList(deliverConsignStatusList, deliverConsignStatusFlag)) {
                            flag2 = true;
                        }

                        List<Integer> deliverDetailStatusListS = new ArrayList<>();   //未出库通知状态
                        deliverDetailStatusListS.add(1);
                        deliverDetailStatusListS.add(2);
                        deliverDetailStatusListS.add(3);
                        deliverDetailStatusListS.add(4);

                        if (disposeList(deliverDetailStatusList, deliverDetailStatusListS)) {
                            result.add(order);
                        } else {
                            if (flag == false) {
                                result.add(order);
                            } else {
                                if (flag2) {
                                    result.add(order);
                                } else {
                                    break outer;
                                }
                            }
                        }

                    }
                }

            } else if (params == 3) {
                if (deliverConsignHas == 1) { //判断是否生成过出口通知单 1,，未生成过
                    break outer;
                } else out:if (!deliverConsignC) {
                    {
                        List<DeliverConsign> deliverConsign = order.getDeliverConsign();//获取出口发货通知单
                        if (deliverConsign.size() <= 0) { //如果没有出口通知单，说明没有执行
                            break out;
                        } else {
                            List<Integer> deliverConsignStatusList = new ArrayList<>();   //拿到全部出口发货通知状态
                            List<Integer> deliverDetailStatusList = new ArrayList<>();   //拿到全部出库状态
                            for (DeliverConsign deliverConsign1 : deliverConsign) {
                                Integer status1 = deliverConsign1.getStatus();
                                deliverConsignStatusList.add(status1);
                                if (status1 > 2) {
                                    DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                                    if (deliverDetail != null) {
                                        Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                                        deliverDetailStatusList.add(status);
                                    }
                                }
                            }


                            if (!deliverConsignStatusList.contains(3)) {  //如果出库通知单中没有状态等于已出库说明没有执行
                                break out;
                            }

                            List<Integer> deliverConsignStatusFlag = new ArrayList<>();   //未出库通知状态
                            deliverConsignStatusFlag.add(1);
                            deliverConsignStatusFlag.add(2);

                            if (disposeList(deliverConsignStatusList, deliverConsignStatusFlag)) {
                                break out;
                            }


                            if (!deliverDetailStatusList.contains(5)) {   //如果出库状态中没有确认出库，说明没有执行
                                break out;
                            }


                            List<Integer> deliverDetailStatusListS = new ArrayList<>();   //未出库通知状态
                            deliverDetailStatusListS.add(1);
                            deliverDetailStatusListS.add(2);
                            deliverDetailStatusListS.add(3);
                            deliverDetailStatusListS.add(4);

                            if (disposeList(deliverDetailStatusList, deliverDetailStatusListS)) {
                                break out;
                            }

                            result.add(order);

                        }
                    }


                }



           /* List<Goods> goodsList = order.getGoodsList();
            if(goodsList.size() > 0 ){

                Integer contractGoodsNums = 0;  // 本订单商品  合同商品数量
                Integer outstockNums = 0;// 本订单中的全部  已发货数量

                for (Goods goods : goodsList){
                    contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                    outstockNums += goods.getOutstockNum();   //  已发货数量
                }

                if(params == 1){        //1:未执行 2:执行中 3:已完成'
                    if(outstockNums == 0){
                        result.add(order);
                    }
                }else if(params == 3){
                    if(outstockNums >= contractGoodsNums && outstockNums != 0){
                        result.add(order);
                    }
                }else {
                    if (outstockNums < contractGoodsNums && outstockNums != 0){
                        result.add(order);
                    }
                }
            }*/
            }

        }
        return result;
    }

    /**
     * 订单主流程监控 根据发运状态状态条件查询
     *
     * @param logisticsDataStatus
     * @return
     */
    public Set<Order> finBylogisticsDataStatus(Integer logisticsDataStatus, Integer confirmTheStatus) {
        List<Order> all = orderDao.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (logisticsDataStatus != 1) {
                    //查询状态前的基本条件
                    list.add(cb.equal(root.get("deliverConsignHas").as(Integer.class), 2)); //deliverConsignHas 是否已生成出口通知单 1：未生成 2： 已生成',

                    if (logisticsDataStatus == 3) {
                        list.add(cb.equal(root.get("deliverConsignC").as(Boolean.class), false));     //deliverConsignC   是否存在商品可以创建发货通知单 0：无 1：有', 0:false    1:true
                    }

                    Join<Order, DeliverConsign> deliverConsignRoot = root.join("deliverConsign");
                    Join<DeliverConsign, DeliverDetail> deliverDetailRoot = deliverConsignRoot.join("deliverDetail");
                    Join<DeliverDetail, Iogistics> iogisticsRoot = deliverDetailRoot.join("iogistics");
                    Join<Iogistics, IogisticsData> iogisticsDataRoot = iogisticsRoot.join("iogisticsData");
                    if (logisticsDataStatus == 3) {
                        list.add(cb.equal(iogisticsDataRoot.get("status").as(Integer.class), 7));    //  status  物流状态 5：合并出库信息 6：完善物流状态中 7：项目完结
                    }

                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        });

        return disposeLogisticsDataStatus(all, logisticsDataStatus, confirmTheStatus);

    }

    /**
     * 订单主流程监控  处理发运状态查询条件  处理收款状态查询条件
     *
     * @param logisticsDataStatus
     * @return
     */
    public Set<Order> disposeLogisticsDataStatus(List<Order> orderList, Integer logisticsDataStatus, Integer confirmTheStatus) {
        Set<Order> set = new HashSet();
        for (Order order : orderList) {
            Integer deliverConsignHas = order.getDeliverConsignHas() == null ? 1 : order.getDeliverConsignHas();   //是否已生成出口通知单 1：未生成 2： 已生成',
            Boolean deliverConsignC = order.getDeliverConsignC();   //是否存在商品可以创建发货通知单 0：无 1：有'
            outer:
            if (logisticsDataStatus == 1) {   //查找未完成
                if (deliverConsignHas == 1) { //判断是否生成过出口通知单 1,，未生成过
                    set.add(order);
                } else {  //判断出口通知单是否已经生成完
                    List<DeliverConsign> deliverConsign = order.getDeliverConsign();//获取出口发货通知单
                    if (deliverConsign.size() <= 0) { //如果没有出口通知单，说明没有执行
                        set.add(order);
                    } else {

                        List<Integer> deliverConsignStatusList = new ArrayList<>();   //拿到全部出口发货通知状态
                        List<Integer> deliverDetailStatusList = new ArrayList<>();   //拿到全部出库状态
                        for (DeliverConsign deliverConsign1 : deliverConsign) {
                            Integer status1 = deliverConsign1.getStatus();
                            deliverConsignStatusList.add(status1);
                            if (status1 > 2) {
                                DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                                if (deliverDetail != null) {
                                    Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                                    deliverDetailStatusList.add(status);
                                }
                            }
                        }

                        if (!deliverConsignStatusList.contains(3)) {  //如果出库通知单中没有状态等于已出库说明没有执行
                            set.add(order);
                        }

                        if (!deliverDetailStatusList.contains(5)) {   //如果出库状态中没有确认出库，说明没有执行
                            set.add(order);
                        }

                        for (DeliverConsign deliverConsign1 : deliverConsign) {  //如果出库状态有确认出库 ，去判断物流是否是执行中，如果有执行中，条数本订单循环
                            Integer status1 = deliverConsign1.getStatus();
                            if (status1 > 2) {
                                DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                                if (deliverDetail != null) {

                                    Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                                    if (status > 4) {
                                        List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                        if (iogisticsList.size() != 0) {
                                            for (Iogistics iogistics : iogisticsList) {
                                                IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                                if (iogisticsData != null) {
                                                    Integer iogisticsDataStatus = iogisticsData.getStatus();    //获取物流状态
                                                    if (confirmTheStatus == null) {
                                                        if (iogisticsDataStatus > 5) {   //如果物流状态有不是待确定， 直接退出循环
                                                            break outer;
                                                        }
                                                    } else {
                                                        if (iogisticsData.getConfirmTheGoods() != null) {   //如果物流状态有不是待确定， 直接退出循环
                                                            break outer;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        set.add(order);
                    }
                }

            } else out:if (logisticsDataStatus == 2) { //查找执行中
                if (deliverConsignHas == 1) { //判断是否生成过出口通知单 1,，未生成过   直接退出本次order
                    break out;
                } else {  //生成过出口通知单，去继续往下判断
                    List<DeliverConsign> deliverConsign = order.getDeliverConsign();//获取出口发货通知单
                    if (deliverConsign.size() <= 0) { //如果没有出口通知单，说明没有执行
                        break out;
                    } else {  //如果有出口通知单，继续判断
                        List<Integer> deliverConsignStatusList = new ArrayList<>();   //拿到全部出口发货通知状态
                        List<Integer> deliverDetailStatusList = new ArrayList<>();   //拿到全部出库状态
                        for (DeliverConsign deliverConsign1 : deliverConsign) {
                            Integer status1 = deliverConsign1.getStatus();
                            deliverConsignStatusList.add(status1);
                            if (status1 > 2) {
                                DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                                if (deliverDetail != null) {
                                    Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                                    deliverDetailStatusList.add(status);
                                }
                            }
                        }

                        List<Boolean> iogisticsDataStatusBoolean = new ArrayList<>();   //物流状态

                        if (deliverConsignC) {
                            iogisticsDataStatusBoolean.add(false);
                        }

                        if (!deliverConsignStatusList.contains(3)) {  //如果出库通知单中没有状态等于已出库说明没有执行
                            break out;
                        }
                        List<Integer> deliverConsignStatusListS = new ArrayList<>();
                        deliverConsignStatusListS.add(1);
                        deliverConsignStatusListS.add(2);

                        if (disposeList(deliverConsignStatusList, deliverConsignStatusListS)) {
                            iogisticsDataStatusBoolean.add(false);
                        }

                        if (!deliverDetailStatusList.contains(5)) {   //如果出库状态中没有确认出库，说明没有执行
                            break out;
                        }

                        List<Integer> deliverDetailStatusListS = new ArrayList<>();   //未出库通知状态
                        deliverDetailStatusListS.add(1);
                        deliverDetailStatusListS.add(2);
                        deliverDetailStatusListS.add(3);
                        deliverDetailStatusListS.add(4);

                        if (disposeList(deliverDetailStatusList, deliverDetailStatusListS)) {
                            iogisticsDataStatusBoolean.add(false);
                        }

                        List<Boolean> iogisticsDataBoolean = new ArrayList<>(); //物流信息


                        for (DeliverConsign deliverConsign1 : deliverConsign) {  //如果出库状态有确认出库 ，去判断物流是否有执行中
                            Integer status1 = deliverConsign1.getStatus();
                            if (status1 > 2) {
                                DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                                if (deliverDetail != null) {

                                    Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                                    if (status > 4) {
                                        List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                        if (iogisticsList.size() != 0) {
                                            for (Iogistics iogistics : iogisticsList) {
                                                IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                                if (iogisticsData == null) {
                                                    iogisticsDataBoolean.add(false);
                                                } else {
                                                    iogisticsDataBoolean.add(true);

                                                    if (confirmTheStatus == null) {   //是否是确认收货查询
                                                        Integer status2 = iogisticsData.getStatus();
                                                        if (status2 < 6) {
                                                            iogisticsDataStatusBoolean.add(false);
                                                        } else {
                                                            iogisticsDataStatusBoolean.add(true);
                                                        }
                                                    } else {
                                                        Integer status2 = iogisticsData.getStatus();
                                                        if (status2 == 7 && iogisticsData.getConfirmTheGoods() != null) { //确认收货时间不为空
                                                            iogisticsDataStatusBoolean.add(true);
                                                        } else {
                                                            iogisticsDataStatusBoolean.add(false);
                                                        }
                                                    }
                                                }

                                                /* if(iogisticsData != null){
                                                    Integer iogisticsDataStatus = iogisticsData.getStatus();    //获取物流状态
                                                    if(confirmTheStatus == null){
                                                        if (iogisticsDataStatus > 5){   //必须大于确认出库
                                                            set.add(order);
                                                            break out;
                                                        }
                                                    }else {
                                                        if (iogisticsDataStatus > 5 && iogisticsData.getConfirmTheGoods() != null){
                                                            set.add(order);
                                                            break out;
                                                        }
                                                    }
                                                }*/
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        List<Boolean> containsList = new ArrayList<>();   //未出库通知状态
                        containsList.add(true);
                        containsList.add(false);

                        if (iogisticsDataBoolean.size() > 0 && iogisticsDataBoolean.contains(true) && iogisticsDataStatusBoolean.containsAll(containsList)) {
                            set.add(order);
                            break out;
                        } else {
                            break out;
                        }
                    }
                }


            } else Jumps:if (logisticsDataStatus == 3) { //查找已完成
                if (deliverConsignHas == 1) { //判断是否生成过出口通知单 1,，未生成过   直接退出本次order
                    break Jumps;
                } else Jump:if (!deliverConsignC) {  //生成过出口通知单，去继续往下判断    0:false    1:true  0：说明已经生成完出口
                    List<DeliverConsign> deliverConsign = order.getDeliverConsign();//获取出口发货通知单
                    if (deliverConsign.size() <= 0) { //如果没有出口通知单，说明没有执行
                        break Jump;
                    } else {  //如果有出口通知单，继续判断
                        List<Integer> deliverConsignStatusList = new ArrayList<>();   //拿到全部出口发货通知状态
                        List<Integer> deliverDetailStatusList = new ArrayList<>();   //拿到全部出库状态
                        for (DeliverConsign deliverConsign1 : deliverConsign) {
                            Integer status1 = deliverConsign1.getStatus();
                            deliverConsignStatusList.add(status1);
                            if (status1 > 2) {
                                DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                                if (deliverDetail != null) {
                                    Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                                    deliverDetailStatusList.add(status);
                                }
                            }
                        }

                        if (!deliverConsignStatusList.contains(3)) {  //如果出库通知单中没有确认状态等于已出库说明没有执行
                            break Jump;
                        }

                        if (!deliverDetailStatusList.contains(5)) {   //如果出库状态中没有确认出库，说明没有执行
                            break Jump;
                        }

                        List<Boolean> iogisticsDataBoolean = new ArrayList<>(); //物流信息
                        List<Boolean> iogisticsDataStatusBoolean = new ArrayList<>();   //物流状态


                        for (DeliverConsign deliverConsign1 : deliverConsign) {  //如果出库状态有确认出库 ，去判断物流是否有执行中

                            List<Integer> deliverConsignStatusFlag = new ArrayList<>();   //未出库通知状态
                            deliverConsignStatusFlag.add(1);
                            deliverConsignStatusFlag.add(2);

                            if (!disposeList(deliverConsignStatusList, deliverConsignStatusFlag)) {  //未出库通知状态  判断如果都已经出库返回false

                                Integer status1 = deliverConsign1.getStatus();
                                if (status1 > 2) {
                                    DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                                    if (deliverDetail != null) {

                                        Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单

                                        List<Integer> statusFlag = new ArrayList<>();   //未出库状态
                                        statusFlag.add(1);
                                        statusFlag.add(2);
                                        statusFlag.add(3);
                                        statusFlag.add(4);

                                        if (!disposeList(deliverDetailStatusList, statusFlag)) {    //判断是否出库和未出库状态都有，如果有的话判断是否是执行中
                                            if (status > 4) {
                                                List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                                if (iogisticsList.size() != 0) {
                                                    for (Iogistics iogistics : iogisticsList) {
                                                        IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                                        if (iogisticsData != null) {
                                                            Integer iogisticsDataStatus = iogisticsData.getStatus();    //获取物流状态
                                                            if (confirmTheStatus == null) {
                                                                if (iogisticsDataStatus != 7) {
                                                                    break Jump;
                                                                }
                                                            } else {
                                                                if (iogisticsDataStatus != 7) {
                                                                    break Jump;
                                                                } else if (iogisticsData.getConfirmTheGoods() == null) {
                                                                    break Jump;
                                                                }
                                                            }
                                                        } else {
                                                            break Jump;
                                                        }
                                                    }
                                                } else {
                                                    break Jump;
                                                }
                                            }
                                        } else {
                                            break Jump;
                                        }
                                    } else {
                                        break Jump;
                                    }
                                }

                            } else {
                                break Jump;
                            }
                        }
                        set.add(order);
                        break Jump;
                    }
                }


            }

        }

        return set;
    }


    /**
     * 订单主流程监控 返回数据处理采购状态
     *
     * @param project
     * @param orderGoodsList
     * @return
     */
    public Integer disposePurchsStatus(Project project, List<Goods> orderGoodsList) {
        Integer purchReqCreate = project.getPurchReqCreate();   // 1：未创建  2：已创建 3:已创建并提交',
        Boolean purchDone = project.getPurchDone();     //是否采购完成，1：完成  0：未完成
        List<Purch> purchs = project.getPurchs();   //获取采购列表
        if (purchReqCreate != null) {
            if (purchReqCreate == 3) {
                if (purchs.size() == 0 || purchs == null) {
                    return 1;
                } else {
                    if (!purchDone) {  //true 已完成  false 未完成
                        List<Integer> purchStatusList = new ArrayList();
                        for (Purch purch : purchs) {
                            purchStatusList.add(purch.getStatus());
                        }

                        List<Integer> purchStatusListFlag = new ArrayList();
                        purchStatusListFlag.add(2);
                        purchStatusListFlag.add(3);

                        if (disposeList(purchStatusList, purchStatusListFlag)) {   //判断是否 执行中，已完成都有
                            return 2;
                        } else {
                            return 1;
                        }
                    } else {
                        List<Integer> purchStatusList = new ArrayList();
                        for (Purch purch : purchs) {
                            purchStatusList.add(purch.getStatus());
                        }

                        List<Integer> purchStatusListFlag = new ArrayList();
                        purchStatusListFlag.add(2);
                        purchStatusListFlag.add(3);

                        if (disposeList(purchStatusList, purchStatusListFlag)) {   //判断是否 执行中，已完成都有
                            if (!purchStatusList.contains(2)) {   //判断是否全部是 已完成   ,判断是否有执行中
                                if (purchStatusList.contains(1)) {    //判断是否还存在未执行的       如果有已完成，有未执行  返回执行中
                                    return 2;
                                } else {
                                    return 3;
                                }
                            } else {
                                return 2;
                            }

                        } else {
                            return 1;
                        }
                    }

                }
            } else {
                return 1;
            }
        } else {
            return 1;
        }


    }

    /**
     * 订单主流程监控 计算采购金额
     *
     * @param purchs
     * @return
     */
    public BigDecimal disposePurchOrdermoney(List<Purch> purchs) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (Purch purch : purchs) {
            totalPrice = totalPrice.add(purch.getTotalPrice() == null ? BigDecimal.valueOf(0) : purch.getTotalPrice()); //单条采购总金额
        }
        return totalPrice;
    }

    /**
     * 订单主流程监控 入库质检状态
     *
     * @param order
     * @return
     */
    public Integer disposeInspectReportStatus(Order order) {
        List<Goods> goodsList = order.getGoodsList();
        if (goodsList != null && goodsList.size() > 0) {
            Integer contractGoodsNums = 0;  // 本订单商品  合同商品数量
            Integer goodNums = 0;    // 本订单商品 检验合格商品数量

            for (Goods goods : goodsList) {
                contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                List<PurchGoods> purchGoodsList = goods.getPurchGoods();  //已报检商品信息
                if (purchGoodsList.size() > 0) {
                    for (PurchGoods purchGoods : purchGoodsList) {
                        Integer goodNum = purchGoods.getGoodNum() == null ? 0 : purchGoods.getGoodNum();//检验合格商品数量
                        goodNums += goodNum;
                    }
                }
            }


            if (goodNums == 0) {
                return 1;
            } else if (contractGoodsNums <= goodNums && goodNums != 0) {
                return 3;
            } else if (contractGoodsNums > goodNums && goodNums != 0) {
                return 2;
            }

        }
        return 1;
    }

    /**
     * 订单主流程监控 入库状态
     *
     * @param goodsList
     * @return
     */
    public Integer disposeInstockStatus(List<Goods> goodsList) {
        if (goodsList != null && goodsList.size() > 0) {
            Integer contractGoodsNums = 0;  // 本订单商品  合同商品数量
            Integer instockNums = 0;    // 本订单商品  已入库数量

            for (Goods goods : goodsList) {
                contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                instockNums += goods.getInstockNum();// 已入库数量
            }
            if (instockNums == 0) {
                return 1;
            } else if (contractGoodsNums <= instockNums && instockNums != 0) {
                return 3;
            } else if (instockNums < contractGoodsNums && instockNums != 0) {
                return 2;
            }

        }
        return 1;
    }

    /**
     * 订单主流程监控 订舱通知状态
     *
     * @param order
     * @return
     */
    public Integer disposeDeliverConsignStatus(Order order) {
        Integer deliverConsignHas = order.getDeliverConsignHas() == null ? 1 : order.getDeliverConsignHas();   //是否已生成出口通知单 1：未生成 2： 已生成',
        Boolean deliverConsignC = order.getDeliverConsignC();   //是否存在商品可以创建发货通知单 0：无 1：有',
        if (deliverConsignHas == 1) {
            return 1;
        } else {
            if (deliverConsignC) {    //0:false    1:true
                List<DeliverConsign> deliverConsignList = order.getDeliverConsign();
                if (deliverConsignList.size() > 0) {
                    for (DeliverConsign deliverConsign : deliverConsignList) {
                        Integer status = deliverConsign.getStatus();
                        if (status > 1) {
                            return 2;
                        }
                    }
                }
                return 1;
            } else {
                List<DeliverConsign> deliverConsignList = order.getDeliverConsign();
                List<Integer> deliverConsignStatusList = new ArrayList<>();
                if (deliverConsignList.size() > 0) {
                    for (DeliverConsign deliverConsign : deliverConsignList) {
                        Integer status = deliverConsign.getStatus();
                        if (status > 1) {
                            deliverConsignStatusList.add(deliverConsign.getStatus());
                        }
                    }
                } else {
                    return 1;
                }
                if (deliverConsignStatusList.size() == 0) {
                    return 1;
                } else if (deliverConsignStatusList.contains(2)) {
                    return 2;
                } else {
                    return 3;
                }
            }
        }
    }


    /**
     * 订单主流程监控 出库状态
     *
     * @param order
     * @return
     */
    public Integer disposeDeliverDetailStatus(Order order) {

        Integer deliverConsignHas = order.getDeliverConsignHas() == null ? 1 : order.getDeliverConsignHas();   //是否已生成出口通知单 1：未生成 2： 已生成',
        Boolean deliverConsignC = order.getDeliverConsignC();   //是否存在商品可以创建发货通知单 0：无 1：有'

        if (deliverConsignHas == 1) { //判断是否生成过出口通知单 1,，未生成过
            return 1;
        } else {
            List<DeliverConsign> deliverConsign = order.getDeliverConsign();//获取出口发货通知单
            if (deliverConsign.size() <= 0) { //如果没有出口通知单，说明没有执行
                return 1;
            } else {
                List<Integer> deliverConsignStatusList = new ArrayList<>();   //拿到全部出口发货通知状态
                List<Integer> deliverDetailStatusList = new ArrayList<>();   //拿到全部出库状态
                for (DeliverConsign deliverConsign1 : deliverConsign) {
                    Integer status1 = deliverConsign1.getStatus();
                    deliverConsignStatusList.add(status1);
                    if (status1 > 2) {
                        DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                        if (deliverDetail != null) {
                            Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                            deliverDetailStatusList.add(status);
                        }
                    }
                }


                if (!deliverConsignStatusList.contains(3)) {  //如果出库通知单中没有状态等于已出库说明没有执行
                    return 1;
                }

                if (!deliverDetailStatusList.contains(5)) {   //如果出库状态中没有确认出库，说明没有执行
                    return 1;
                }

                if (deliverConsignC) {
                    return 2;
                } else {

                    List<Integer> deliverConsignStatusFlag = new ArrayList<>();   //未出库通知状态
                    deliverConsignStatusFlag.add(1);
                    deliverConsignStatusFlag.add(2);

                    if (disposeList(deliverConsignStatusList, deliverConsignStatusFlag)) {  //出口发货通知单如果有没提交的  说明是执行中
                        return 2;
                    }


                    List<Integer> deliverDetailStatusListS = new ArrayList<>();   //未出库通知状态
                    deliverDetailStatusListS.add(1);
                    deliverDetailStatusListS.add(2);
                    deliverDetailStatusListS.add(3);
                    deliverDetailStatusListS.add(4);

                    if (disposeList(deliverDetailStatusList, deliverDetailStatusListS)) {
                        return 2;
                    }

                    return 3;

                }


            }

        }





       /* if (goodsList != null && goodsList.size() > 0){
            Integer contractGoodsNums = 0;  // 本订单商品  合同商品数量
            Integer outstockNums = 0;// 本订单中的全部  已发货数量
            for (Goods goods : goodsList){
                contractGoodsNums += goods.getContractGoodsNum();//合同商品数量
                outstockNums += goods.getOutstockNum();   //  已发货数量
            }
            if(outstockNums == 0){
                return 1;
            }else if (contractGoodsNums <= outstockNums && outstockNums != 0){
                return 3;
            }else if(outstockNums <  contractGoodsNums && outstockNums != 0) {
                return 2;
            }
        }
        return 1;*/
    }

    /**
     * 订单主流程监控 发运状态  /  收款状态
     *
     * @param order
     * @return
     */
    private Integer disposeLogisticsDataStatus(Order order, Integer flag) {
        Integer deliverConsignHas = order.getDeliverConsignHas() == null ? 1 : order.getDeliverConsignHas();   //是否已生成出口通知单 1：未生成 2： 已生成',
        Boolean deliverConsignC = order.getDeliverConsignC();   //是否存在商品可以创建发货通知单 0：无 1：有'
        if (deliverConsignHas == 1) {
            return 1;
        } else {
            if (deliverConsignC) {    //0:false    1:true
                List<DeliverConsign> deliverConsign = order.getDeliverConsign();//出口发货通知单

                if (deliverConsign.size() <= 0) {
                    return 1;
                }

                List<Integer> deliverConsignStatusList = new ArrayList<>();   //全部出口发货通知状态
                List<Integer> deliverDetailStatusList = new ArrayList<>();   //全部出库状态
                for (DeliverConsign deliverConsign1 : deliverConsign) {
                    Integer status1 = deliverConsign1.getStatus();
                    deliverConsignStatusList.add(status1);
                    if (status1 > 2) {
                        DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                        if (deliverDetail != null) {
                            Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                            deliverDetailStatusList.add(status);
                        }
                    }
                }

                if (!deliverConsignStatusList.contains(3)) {
                    return 1;
                }
                if (!deliverDetailStatusList.contains(5)) {
                    return 1;
                }


                for (DeliverConsign deliverConsign1 : deliverConsign) {
                    Integer status1 = deliverConsign1.getStatus();
                    if (status1 > 2) {
                        DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                        if (deliverDetail != null) {

                            Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                            if (status > 4) {
                                List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                if (iogisticsList.size() != 0) {
                                    for (Iogistics iogistics : iogisticsList) {
                                        IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                        if (iogisticsData != null) {
                                            Integer iogisticsDataStatus = iogisticsData.getStatus();    //获取物流状态

                                            if (flag != null) {
                                                if (iogisticsDataStatus > 5 && iogisticsData.getConfirmTheGoods() != null) {
                                                    return 2;
                                                }
                                            } else {
                                                if (iogisticsDataStatus > 5) {
                                                    return 2;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                return 1;
            } else {

                List<DeliverConsign> deliverConsign = order.getDeliverConsign();//出口发货通知单

                if (deliverConsign.size() <= 0) {
                    return 1;
                }

                List<Integer> deliverConsignStatusList = new ArrayList<>();   //全部出口发货通知状态
                List<Integer> deliverDetailStatusList = new ArrayList<>();   //全部出库状态
                for (DeliverConsign deliverConsign1 : deliverConsign) {
                    Integer status1 = deliverConsign1.getStatus();
                    deliverConsignStatusList.add(status1);
                    if (status1 > 2) {
                        DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                        if (deliverDetail != null) {
                            Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                            deliverDetailStatusList.add(status);
                        }
                    }
                }

                if (!deliverConsignStatusList.contains(3)) {
                    return 1;
                }
                if (!deliverDetailStatusList.contains(5)) {
                    return 1;
                }

                List<Boolean> iogisticsDataBoolean = new ArrayList<>(); //物流信息
                List<Boolean> iogisticsDataStatusBoolean = new ArrayList<>();   //物流状态
                List<Boolean> iogisticsDataStatusList = new ArrayList<>();   //物流动态更新状态


                for (DeliverConsign deliverConsign1 : deliverConsign) {
                    List<Integer> deliverConsignStatusFlag = new ArrayList<>();   //未出库通知状态
                    deliverConsignStatusFlag.add(1);
                    deliverConsignStatusFlag.add(2);

                    if (!disposeList(deliverConsignStatusList, deliverConsignStatusFlag)) {   //未出库通知状态

                        Integer deliverConsignStatus = deliverConsign1.getStatus();
                        if (deliverConsignStatus > 2) {
                            DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单

                            if (deliverDetail != null) {
                                Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单

                                List<Integer> statusFlag = new ArrayList<>();   //未出库状态
                                statusFlag.add(1);
                                statusFlag.add(2);
                                statusFlag.add(3);
                                statusFlag.add(4);

                                if (disposeList(deliverDetailStatusList, statusFlag)) {    //判断是否出库和未出库状态都有，如果有的话判断是否是执行中

                                    if (status > 4) {
                                        List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                        if (iogisticsList.size() != 0) {
                                            for (Iogistics iogistics : iogisticsList) {
                                                IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                                if (iogisticsData != null) {
                                                    Integer status1 = iogisticsData.getStatus();    //获取物流状态
                                                    if (flag != null) {
                                                        if (status1 > 5 && iogisticsData.getConfirmTheGoods() != null) {
                                                            return 2;
                                                        }
                                                    } else {
                                                        if (status1 > 5) {
                                                            return 2;
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }

                                } else {
                                    List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                    for (Iogistics iogistics : iogisticsList) {
                                        IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                        if (iogisticsData == null) {
                                            iogisticsDataBoolean.add(false);
                                        } else {
                                            iogisticsDataBoolean.add(true);

                                            Integer status1 = iogisticsData.getStatus();
                                            if (status1 < 7) {
                                                iogisticsDataStatusBoolean.add(false);
                                                if (status1 > 5) {
                                                    iogisticsDataStatusList.add(true);
                                                }
                                            } else {
                                                if (flag != null) {
                                                    if (iogisticsData.getConfirmTheGoods() != null) {
                                                        iogisticsDataStatusBoolean.add(true);
                                                    } else {
                                                        iogisticsDataStatusBoolean.add(false);
                                                    }
                                                } else {
                                                    iogisticsDataStatusBoolean.add(true);
                                                }
                                            }

                                        }
                                    }

                                }
                            }
                        }
                    } else {
                        Integer deliverConsignStatus = deliverConsign1.getStatus();
                        if (deliverConsignStatus > 2) {
                            DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单

                            if (deliverDetail != null) {

                                Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单

                                List<Integer> statusFlag = new ArrayList<>();   //未出库状态
                                statusFlag.add(1);
                                statusFlag.add(2);
                                statusFlag.add(3);
                                statusFlag.add(4);

                                if (disposeList(statusFlag, deliverDetailStatusList)) {    //判断是否出库和未出库状态都有，如果有的话判断是否是执行中

                                    if (status > 4) {
                                        List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                        if (iogisticsList.size() != 0) {
                                            for (Iogistics iogistics : iogisticsList) {
                                                IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                                if (iogisticsData != null) {
                                                    Integer status1 = iogisticsData.getStatus();    //获取物流状态
                                                    if (flag != null) {
                                                        if (status1 > 5 && iogisticsData.getConfirmTheGoods() != null) {
                                                            return 2;
                                                        }
                                                    } else {
                                                        if (status1 > 5) {
                                                            return 2;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        /*return 1;*/
                                    }
                                }
                            }
                        }
                    }
                }


                if (iogisticsDataBoolean.size() > 0 && !iogisticsDataBoolean.contains(true) || iogisticsDataStatusBoolean.size() > 0 && !iogisticsDataStatusBoolean.contains(true) && iogisticsDataStatusList.size() == 0) {
                    return 1;
                } else if (iogisticsDataBoolean.size() > 0 && !iogisticsDataBoolean.contains(false) && iogisticsDataStatusList.size() == 0 || iogisticsDataStatusBoolean.size() > 0 && !iogisticsDataStatusBoolean.contains(false) && iogisticsDataStatusList.size() == 0) {
                    return 3;
                } else if (iogisticsDataStatusBoolean.size() > 0 || iogisticsDataStatusList.size() > 0 && iogisticsDataStatusList.contains(true)) {
                    return 2;
                }

            }
        }
        return 1;
    }

    /**
     * 订单主流程监控 收货状态            和发运状态调用一个方法
     * @param order
     * @return
     */
    /*private String disposeConfirmTheStatus(Order order) {
        Integer deliverConsignHas = order.getDeliverConsignHas() == null ? 1 : order.getDeliverConsignHas();   //是否已生成出口通知单 1：未生成 2： 已生成',
        Boolean deliverConsignC = order.getDeliverConsignC();   //是否存在商品可以创建发货通知单 0：无 1：有'      0:false    1:true

        if (deliverConsignHas == 1) {
            return "未执行";
        } else {
            if(deliverConsignC){
                List<DeliverConsign> deliverConsign = order.getDeliverConsign();

                if(deliverConsign.size() <= 0){
                    return "未执行";
                }

                List<Integer> deliverConsignStatusList = new ArrayList<>();   //全部出口发货通知状态
                List<Integer> deliverDetailStatusList = new ArrayList<>();   //全部出库状态
                for (DeliverConsign deliverConsign1 : deliverConsign){
                    Integer status1 = deliverConsign1.getStatus();
                    deliverConsignStatusList.add(status1);
                    if(status1 > 2){
                        DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                        Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                        deliverDetailStatusList.add(status);
                    }
                }

                if(!deliverConsignStatusList.contains(3)){
                    return "未执行";
                }
                if(!deliverDetailStatusList.contains(5)){
                    return "未执行";
                }

                for (DeliverConsign deliverConsign1 : deliverConsign){
                    Integer status1 = deliverConsign1.getStatus();
                    if(status1 > 2){
                        DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单

                        Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                        if (status > 4){
                            List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                            if (iogisticsList.size() != 0 ){
                                for (Iogistics iogistics : iogisticsList){
                                    IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                    if(iogisticsData != null){
                                        Integer iogisticsDataStatus = iogisticsData.getStatus();    //获取物流状态
                                        if (iogisticsDataStatus > 5 && iogisticsData.getConfirmTheGoods() != null ){
                                            return "执行中";
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                return "未执行";

            }else { //出口发货通知单已生成完
                List<DeliverConsign> deliverConsign = order.getDeliverConsign();//出口发货通知单

                if(deliverConsign.size() <= 0){
                    return "未执行";
                }

                List<Integer> deliverConsignStatusList = new ArrayList<>();   //全部出口发货通知状态
                List<Integer> deliverDetailStatusList = new ArrayList<>();   //全部出库状态
                for (DeliverConsign deliverConsign1 : deliverConsign){
                    Integer status1 = deliverConsign1.getStatus();
                    deliverConsignStatusList.add(status1);
                    if(status1 > 2){
                        DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单
                        Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单
                        deliverDetailStatusList.add(status);
                    }
                }

                if(!deliverConsignStatusList.contains(3)){
                    return "未执行";
                }
                if(!deliverDetailStatusList.contains(5)){
                    return "未执行";
                }

                List<Boolean> iogisticsDataBoolean = new ArrayList<>(); //物流信息
                List<Boolean> iogisticsDataStatusBoolean = new ArrayList<>();   //物流状态


                for (DeliverConsign deliverConsign1 : deliverConsign){
                    List<Integer> deliverConsignStatusFlag = new ArrayList<>();   //未出库通知状态
                    deliverConsignStatusFlag.add(1);
                    deliverConsignStatusFlag.add(2);

                    if (!disposeList(deliverConsignStatusList,deliverConsignStatusFlag)){   //判断出库状态  如果都是3说明都是出库的数据

                        Integer deliverConsignStatus = deliverConsign1.getStatus();
                        if(deliverConsignStatus > 2){
                            DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单


                            Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单

                            List<Integer> statusFlag = new ArrayList<>();   //未出库状态
                            statusFlag.add(1);
                            statusFlag.add(2);
                            statusFlag.add(3);
                            statusFlag.add(4);

                            if(disposeList(deliverDetailStatusList,statusFlag)){    //判断是否出库和未出库状态都有，如果有的话判断是否是执行中

                                if (status > 4){
                                    List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                    if (iogisticsList.size() != 0 ){
                                        for (Iogistics iogistics : iogisticsList){
                                            IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                            if(iogisticsData != null){
                                                Integer status1 = iogisticsData.getStatus();    //获取物流状态
                                                if (status1 > 5 && iogisticsData.getConfirmTheGoods() != null){
                                                    return "执行中";
                                                }
                                            }
                                        }
                                    }
                                    return "未执行";
                                }

                            }else {
                                List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                for (Iogistics iogistics : iogisticsList){
                                    IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                    if(iogisticsData == null){
                                        iogisticsDataBoolean.add(false);
                                    }else {
                                        iogisticsDataBoolean.add(true);

                                        Integer status1 = iogisticsData.getStatus();
                                        if(status1 < 7 || iogisticsData.getConfirmTheGoods() == null){
                                            iogisticsDataStatusBoolean.add(false);
                                        }else {
                                            iogisticsDataStatusBoolean.add(true);
                                        }

                                    }
                                }

                            }
                        }
                    }else { //判断出库状态  如果1,2,3状态都有，说明不是完成状态
                        Integer deliverConsignStatus = deliverConsign1.getStatus();
                        if(deliverConsignStatus > 2) {
                            DeliverDetail deliverDetail = deliverConsign1.getDeliverDetail();    //出库/质检单


                            Integer status = deliverDetail.getStatus(); //出库状态  如果不是确认出库，说明没有推送信息，说明没有走到分单

                            List<Integer> statusFlag = new ArrayList<>();   //未出库状态
                            statusFlag.add(1);
                            statusFlag.add(2);
                            statusFlag.add(3);
                            statusFlag.add(4);

                            if (disposeList(statusFlag,deliverDetailStatusList)) {    //判断是否出库和未出库状态都有，如果有的话判断是否是执行中

                                if (status > 4) {
                                    List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //出库分单信息
                                    if (iogisticsList.size() != 0) {
                                        for (Iogistics iogistics : iogisticsList) {
                                            IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                                            if (iogisticsData != null) {
                                                Integer status1 = iogisticsData.getStatus();    //获取物流状态
                                                if (status1 > 5 && iogisticsData.getConfirmTheGoods() != null ) {
                                                    return "执行中";
                                                }
                                            }
                                        }
                                    }
                                    return "未执行";
                                }
                            }
                        }
                    }
                }

                if(iogisticsDataBoolean.size() > 0  && !iogisticsDataBoolean.contains(true) || iogisticsDataStatusBoolean.size() > 0 && !iogisticsDataStatusBoolean.contains(true) ){
                    return "未执行";
                }else if(iogisticsDataBoolean.size() > 0  && !iogisticsDataBoolean.contains(false) || iogisticsDataStatusBoolean.size() > 0 && !iogisticsDataStatusBoolean.contains(false) ){
                    return "已完成";
                }else if(iogisticsDataStatusBoolean.size() > 0 ){
                    return "执行中";
                }


            }

        }
return  "";

    }*/


    /**
     * 订单主流程监控 发运金额
     *
     * @param order
     * @return
     */
    private BigDecimal disposeLogisticsPrice(Order order) {
        BigDecimal logisticsPriceSum = BigDecimal.valueOf(0);
        Set<Integer> iogisticsDataIdList = new HashSet<>();
        List<DeliverConsign> deliverConsignList = order.getDeliverConsign();    //获取看货通知单表
        if (deliverConsignList.size() > 0) {
            for (DeliverConsign deliverConsign : deliverConsignList) {
                DeliverDetail deliverDetail = deliverConsign.getDeliverDetail();    //获取出库表
                if (deliverDetail != null) {
                    List<Iogistics> iogisticsList = deliverDetail.getIogistics();   //获取出库分单表
                    if (iogisticsList.size() > 0) {
                        for (Iogistics iogistics : iogisticsList) {
                            IogisticsData iogisticsData = iogistics.getIogisticsData(); //获取物流信息
                            if (iogisticsData != null) {
                                if (iogisticsData.getStatus() > 5) {
                                    iogisticsDataIdList.add(iogisticsData.getId()); //获取物流id
                                }
                            }

                        }
                    }
                }

            }
        }

        if (iogisticsDataIdList.size() > 0) {
            List<IogisticsData> iogisticsDatList = iogisticsDataDao.findByIdIn(iogisticsDataIdList);
            if (iogisticsDatList.size() > 0) {
                for (IogisticsData iogisticsData : iogisticsDatList) {
                    BigDecimal logisticsPriceUsd = iogisticsData.getLogisticsPriceUsd() == null ? BigDecimal.valueOf(0) : iogisticsData.getLogisticsPriceUsd();// 获取发运金额  必填项
                    logisticsPriceSum = logisticsPriceSum.add(logisticsPriceUsd);
                }
            }
        }

        return logisticsPriceSum;
    }


    /**
     * 判断是否有相同，相同返回true
     *
     * @param a
     * @param b
     * @return
     */
    public Boolean disposeList(List<Integer> a, List<Integer> b) {
        boolean flag = false;

        for (Integer s2 : a) {
            for (Integer s1 : b) {
                if (s2.equals(s1)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

}
