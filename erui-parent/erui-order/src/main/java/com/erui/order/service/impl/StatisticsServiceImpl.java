package com.erui.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.middle.redis.ShardedJedisUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.order.dao.OrderDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.dao.StatisticsDao;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.model.GoodsBookDetail;
import com.erui.order.model.GoodsStatistics;
import com.erui.order.model.ProjectStatistics;
import com.erui.order.model.SaleStatistics;
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
        String[] keys = new String[]{"regionZh", "countryZh", "orderNum", "orderAmount", "oilOrderNum", "oilOrderAmount", "100*oilOrderNumRate,oilOrderNumRate", "oilOrderAmountRate*100,oilOrderAmountRate", "nonOilOrderNum", "nonOilOrderAmount", "nonOilOrderNumRate*100,nonOilOrderNumRate", "nonOilOrderAmountRate*100,nonOilOrderAmountRate", "quotationNum", "quotationAmount", "crmOrderNumRate*100,crmOrderNumRate", "crmOrderAmountRate*100,crmOrderAmountRate", "vipNum", "oneRePurch", "twoRePurch", "threeRePurch", "moreRePurch"};
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
        String[] header = new String[]{"平台SKU", "产品分类", "英文品名", "中文品牌", "品牌", "所属地区", "国家", "询单数量", "询单金额", "订单数量", "订单金额"};
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
                    projectStatistics.setPaymentDate((Date) objArr[2]);
                    projectStatistics.setMoney((BigDecimal) objArr[1]);
                    projectStatistics.setAcquireId((String) objArr[3]);
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
                    projectStatistics.setPaymentDate((Date) objArr[2]);
                    projectStatistics.setMoney((BigDecimal) objArr[1]);
                    projectStatistics.setAcquireId((String) objArr[3]);
                }
            }
        }

        return dataList;
    }


    @Override
    public HSSFWorkbook generateProjectStatisticsExcel(Map<String, String> condition) {
        List<ProjectStatistics> projectStatistics = findProjectStatistics(condition);
        String[] header = new String[]{"项目开始日期", "销售合同号", "询单号", "项目号", "项目名称", "海外销售合同号", "物流报价单号",
                "PO号", "执行分公司", "事业部", "所属地区", "CRM客户代码", "客户类型", "订单类型", "海外销类型", "项目金额（美元）",
                "前期报价（美元）", "前期物流报价（美元）", "收款方式", "回款时间", "回款金额（美元）", "初步利润率%", "授信情况", "执行单约定交付日期",
                "要求采购到货日期", "执行单变更后日期", "分销部(获取人所在分类销售)", "市场经办人", "获取人", "商务技术经办人", "贸易术语",
                "采购延期时间（天）", "物流延期时间（天）", "项目状态", "流程进度", "原因类型", "原因描述"};
        String[] keys = new String[]{"startDate", "contractNo", "inquiryNo", "projectNo", "projectName", "contractNoOs", "logiQuoteNo",
                "poNo", "execCoName", "businessUnitName", "regionZh", "crmCode", "customerTypeName", "orderTypeName", "XXXXXXX", "totalPrice",
                "XXXXXXX", "XXXXXXX", "paymentModeBnName", "paymentDate", "money", "100*profitPercent,profitPercent", "grantType", "deliveryDate",
                "requirePurchaseDate", "exeChgDate", "distributionDeptName", "agentName", "acquireId", "businessName", "tradeTerms",
                "XXXXXXX", "XXXXXXX", "projectStatusName", "processProgressName", "XXXXXXX", "XXXXXXX"};
        BuildExcel buildExcel = new BuildExcelImpl();
        Object objArr = JSON.toJSON(projectStatistics);
        HSSFWorkbook workbook = buildExcel.buildExcel((List) objArr, header, keys, "项目执行统计");
        return workbook;
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
            throw new Exception("订单不存在");
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

                Join<Project, Order> orderRoot = root.join("order");
                String countriesStr = condition.get("countries");
                if (StringUtils.isNotBlank(countriesStr)) {
                    String[] countriesArr = countriesStr.split(",");
                    list.add(orderRoot.get("country").in(countriesArr));
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
                //  项目名称
                String projectName = condition.get("projectName");
                if (StringUtil.isNotBlank(projectName)) {
                    list.add(cb.like(root.get("projectName").as(String.class), "%" + projectName + "%"));
                }
                // 分销部
                String distributionDeptName = condition.get("distributionDeptName");
                if (StringUtil.isNotBlank(distributionDeptName)) {
                    list.add(cb.equal(root.get("distributionDeptName").as(String.class), distributionDeptName));
                }
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
                if (StringUtil.isNotBlank(processProgress)) {
                    list.add(cb.equal(root.get("processProgress").as(String.class), processProgress));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        };
    }
}
