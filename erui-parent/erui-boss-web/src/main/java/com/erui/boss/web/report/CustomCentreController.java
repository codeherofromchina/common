package com.erui.boss.web.report;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.CateDetailVo;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.OrderCountService;
import com.erui.report.util.CustomerNumSummaryVO;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户中心 Created by lirb on 2017/10/20.
 */
@Controller
@RequestMapping("/report/customCentre")
public class CustomCentreController {
    @Autowired
    private InquiryCountService inquiryService;
    @Autowired
    private OrderCountService orderService;
    private static DecimalFormat df = new DecimalFormat("0.00");


    /*
    * 询单总览
    * */
    @ResponseBody
    @RequestMapping(value = "/inquiryPandect", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> inquiryPandect(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]
        // 获取需要环比的开始时间
        Date rateStartDate = NewDateUtil.getBeforeRateDate(endDate, startDate);

        //当期询单数量和金额
        int count = inquiryService.inquiryCountByTime(startDate, endDate, null, 0, 0, "", "");
        double amount = inquiryService.inquiryAmountByTime(startDate, endDate, "");
        // 上期询单数量
        int chainCount = inquiryService.inquiryCountByTime(rateStartDate, startDate, null, 0, 0, "", "");
        Integer chain = count - chainCount;
        Double chainRate = null;
        if (chainCount > 0) {
            chainRate = RateUtil.intChainRate(chain, chainCount);//环比
        }
        // 询单数据修改
        Map<String, Object> inquiryMap = new HashMap<>();//询单统计信息
        inquiryMap.put("count", count);
        inquiryMap.put("amount", df.format(amount / 10000) + "万$");
        inquiryMap.put("chainAdd", chain);
        inquiryMap.put("chainRate", chainRate);

        //油气产品统计
        CustomerNumSummaryVO custSummaryVO = inquiryService.selectNumSummaryByExample(startDate, endDate);
        CustomerNumSummaryVO custSummaryVO2 = inquiryService.selectNumSummaryByExample(rateStartDate, startDate);
        Map<String, Object> proIsOilMap = new HashMap<>();//油气产品分析
        if (custSummaryVO != null && custSummaryVO.getTotal() > 0) {
            proIsOilMap.put("oil", custSummaryVO.getOil());
            proIsOilMap.put("notOil", custSummaryVO.getNonoil());
            proIsOilMap.put("oiProportionl", RateUtil.intChainRate(custSummaryVO.getOil(), custSummaryVO.getTotal()));
            proIsOilMap.put("chainRate", RateUtil.intChainRate(custSummaryVO.getOil() - custSummaryVO2.getOil(), custSummaryVO2.getOil()));
        } else {
            proIsOilMap.put("oil", 0);
            proIsOilMap.put("notOil", 0);
            proIsOilMap.put("oiProportionl", 0.00);
            proIsOilMap.put("chainRate", 0.00);
        }

        // 询单Top 3 产品分类
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startDate);
        map.put("endTime", endDate);
        List<Map<String, Object>> listTop3 = inquiryService.selectProTop3(map);

        listTop3.parallelStream().forEach(m -> {
            BigDecimal s = new BigDecimal(String.valueOf(m.get("proCount")));
            m.put("proProportionl", RateUtil.intChainRate(s.intValue(), count));
        });


        Map<String, Object> datas = new HashMap<>();
        datas.put("inquiry", inquiryMap);
        datas.put("isOil", proIsOilMap);
        datas.put("proTop3", listTop3);
        return new Result<>(ResultStatusEnum.SUCCESS).setData(datas);
    }


    /*
     * 订单总览
     */
    @ResponseBody
    @RequestMapping(value = "/orderPandect", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    public Object orderPandect(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]
        // 获取需要环比的开始时间
        Date rateStartDate = NewDateUtil.getBeforeRateDate(endDate, startDate);

        // 当期订单数量和金额
        int count = orderService.orderCountByTime(startDate, endDate, "", "", "");
        double amount = orderService.orderAmountByTime(startDate, endDate, "");
        // 上期订单数量
        int chainCount = orderService.orderCountByTime(rateStartDate, startDate, "", "", "");
        Double chainRate = null;
        if (chainCount > 0) {
            chainRate = RateUtil.intChainRate(count - chainCount, chainCount);
        }
        Map<String, Object> orderMap = new HashMap<>();// 订单统计信息
        orderMap.put("count", count);
        orderMap.put("amount", df.format(amount / 10000) + "万$");
        orderMap.put("chainAdd", count - chainCount);
        orderMap.put("chainRate", chainRate);

        // 利润率
        Double profit = orderService.selectProfitRate(startDate, endDate);
        Double chainProfit = orderService.selectProfitRate(rateStartDate, startDate);
        double profitRate = 0.00;
        Double chainProfitRate = null;
        if (profit != null) {
            profitRate = RateUtil.doubleChainRate(profit, 1);
        }
        if (chainProfit != null) {
            chainProfitRate = RateUtil.doubleChainRate(chainProfit, 1);
        }
        Map<String, Object> profitMap = new HashMap<>();
        profitMap.put("profitRate", profitRate);
        profitMap.put("chainRate", chainProfitRate);

        // 成单率
        double successOrderRate = 0.00;
        double successOrderChian = 0.00;// 环比
        int successOrderCount = orderService.orderCountByTime(startDate, endDate, "正常完成", "", "");
        int successInquiryCount = inquiryService.inquiryCountByTime(startDate, endDate, "已报价", 0, 0, "", "");
        int successOrderChianCount = orderService.orderCountByTime(rateStartDate, startDate, "正常完成", "", "");
        if (successInquiryCount > 0) {
            successOrderRate = RateUtil.intChainRate(successOrderCount, successInquiryCount);
        }
        if (successOrderChianCount > 0) {
            successOrderChian = RateUtil.intChainRate((successOrderCount - successOrderChianCount),
                    successOrderChianCount);
        }
        Map<String, Object> sucessOrderMap = new HashMap<>();
        sucessOrderMap.put("successOrderRate", successOrderRate);
        sucessOrderMap.put("successOrderChian", successOrderChian);

        // top3
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startDate);
        map.put("endTime", endDate);
        List<Map<String, Object>> proTop3 = orderService.selectOrderProTop3(map);
        proTop3.parallelStream().forEach(m -> {
            BigDecimal s = new BigDecimal(String.valueOf(m.get("orderCount")));
            m.put("topProportion", RateUtil.intChainRate(s.intValue(), count));
        });

        // 组装数据
        Map<String, Object> datas = new HashMap<>();// 订单统计信息
        datas.put("order", orderMap);
        datas.put("profitRate", profitMap);
        datas.put("sucessOrderMap", sucessOrderMap);
        datas.put("top3", proTop3);

        return new Result<>().setData(datas);
    }

    @ResponseBody
    @RequestMapping(value = "/inquiryDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    public Object inquiryDetail(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]


        int quotedCount = inquiryService.inquiryCountByTime(startDate, endDate, "已报价", 0, 0, "", "");
        int noQuoteCount = inquiryService.inquiryCountByTime(startDate, endDate, "未报价", 0, 0, "", "");
        int quotingCount = inquiryService.inquiryCountByTime(startDate, endDate, "报价中", 0, 0, "", "");
        int cancelCount = inquiryService.inquiryCountByTime(startDate, endDate, "询单取消", 0, 0, "", "");
        int totalCount = quotedCount + noQuoteCount + quotingCount + cancelCount;
        Double quotedInquiryRate = null;
        Double quotingInquiryRate = null;
        Double noQuoteInquiryRate = null;
        Double cancelInquiryRate = null;
        if (totalCount > 0) {
            quotedInquiryRate = RateUtil.intChainRate(quotedCount, totalCount);
            quotingInquiryRate = RateUtil.intChainRate(quotingCount, totalCount);
            noQuoteInquiryRate = RateUtil.intChainRate(noQuoteCount, totalCount);
            cancelInquiryRate = RateUtil.intChainRate(cancelCount, totalCount);
        }
        HashMap<String, Object> inquiryDetailMap = new HashMap<>();
        inquiryDetailMap.put("quotedCount", quotedCount);
        inquiryDetailMap.put("noQuoteCount", noQuoteCount);
        inquiryDetailMap.put("quotingCount", quotingCount);
        inquiryDetailMap.put("cancelCount", cancelCount);
        inquiryDetailMap.put("quotedInquiryRate", quotedInquiryRate);
        inquiryDetailMap.put("quotingInquiryRate", quotingInquiryRate);
        inquiryDetailMap.put("noQuoteInquiryRate", noQuoteInquiryRate);
        inquiryDetailMap.put("cancelInquiryRate", cancelInquiryRate);

        return new Result<>(inquiryDetailMap);
    }

    // 询单时间分布分析
    @ResponseBody
    @RequestMapping(value = "/inquiryTimeDistrbute", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    public Object inquiryTimeDistrbute(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]

        int totalCount = inquiryService.inquiryCountByTime(startDate, endDate, "", 0, 0, "", "");
        int count1 = inquiryService.inquiryCountByTime(startDate, endDate, "", 0, 4, "", "");
        int count2 = inquiryService.inquiryCountByTime(startDate, endDate, "", 4, 8, "", "");
        int count3 = inquiryService.inquiryCountByTime(startDate, endDate, "", 8, 24, "", "");
        int count5 = inquiryService.inquiryCountByTime(startDate, endDate, "", 24, 48, "", "");
        int otherCount = inquiryService.inquiryCountByTime(startDate, endDate, "", 48, 1000, "", "");
        HashMap<String, Object> quoteTimeMap = new HashMap<>();
        quoteTimeMap.put("oneCount", count1);
        quoteTimeMap.put("fourCount", count2);
        quoteTimeMap.put("eightCount", count3);
        quoteTimeMap.put("twentyFourCount", count5);
        quoteTimeMap.put("otherCount", otherCount);
        Double oneCountRate = null;
        Double fourCountRate = null;
        Double eightCountRate = null;
        Double twentyFourCountRate = null;
        Double otherCountRate = null;
        if (totalCount > 0) {
            oneCountRate = RateUtil.intChainRate(count1, totalCount);
            fourCountRate = RateUtil.intChainRate(count2, totalCount);
            eightCountRate = RateUtil.intChainRate(count3, totalCount);
            twentyFourCountRate = RateUtil.intChainRate(count5, totalCount);
            otherCountRate = RateUtil.intChainRate(otherCount, totalCount);
        }
        quoteTimeMap.put("oneCountRate", oneCountRate);
        quoteTimeMap.put("fourCountRate", fourCountRate);
        quoteTimeMap.put("eightCountRate", eightCountRate);
        quoteTimeMap.put("twentyFourCountRate", twentyFourCountRate);
        quoteTimeMap.put("otherCountRate", otherCountRate);

        return new Result<>(quoteTimeMap);
    }

    // 事业部明细
    @ResponseBody
    @RequestMapping(value = "/busUnitDetail", method = RequestMethod.POST)
    public Object busUnitDetail(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]

        ///查询给定时间的事业部询单数量和平均响应时间
        List<Map<String, Object>> inquiryList = inquiryService.findCountAndAvgNeedTimeByRangRollinTimeGroupOrigation(startDate, endDate);
        // 查询给定时间段的事业部订单数量和金额
        List<Map<String, Object>> orderList = orderService.findCountAndAmountByRangProjectStartGroupOrigation(startDate, endDate);

        // 所有事业部(这里都是辅助变量，下面构建表格数据要使用)
        Set<String> organizations = new HashSet<>();
        Map<String, Map<String, Object>> inquiryMap = new HashedMap();
        Integer inquiryCount = 0;
        Map<String, Map<String, Object>> orderMap = new HashedMap();
        Integer orderCount = 0;
        BigDecimal bg = null;


        // 事业部询单占比饼图数据组合
        List<Map<String, Object>> inquiryPie = new ArrayList<>();
        inquiryCount = inquiryList.stream().map(m -> {
            String organization = String.valueOf(m.get("organization"));
            Long total = (Long) m.get("total");
            Map<String, Object> mmm = new HashedMap();
            mmm.put("name", organization);
            mmm.put("value", total);
            inquiryPie.add(mmm);


            organizations.add(organization);

            inquiryMap.put(organization, m);
            return total.intValue();
        }).reduce(0, (a, b) -> a + b);

        // 事业部订单占比饼图数据组合
        List<Map<String, Object>> orderPie = new ArrayList<>();
        //事业部成单金额占比数据组合
        List<Map<String, Object>> orderAmountPie = new ArrayList<>();

        orderCount = orderList.stream().map(m -> {
            String organization = String.valueOf(m.get("organization"));
            Long total = (Long) m.get("totalNum");
            Map<String, Object> map01 = new HashedMap();
            map01.put("name", organization);
            map01.put("value", m.get("totalNum"));
            orderPie.add(map01);

            Map<String, Object> map02 = new HashedMap();
            map02.put("name", organization);
            map02.put("value", m.get("totalAmount"));
            orderAmountPie.add(map02);

            organizations.add(organization);

            orderMap.put(organization, m);
            return total.intValue();
        }).reduce(0, (a, b) -> a + b);


        // 事业部的询单占比、平均报价时间、订单占比、成单金额表格数据组合
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (String org : organizations) {
            Map<String, Object> map = new HashedMap();
            Map<String, Object> iMap = inquiryMap.get(org);
            Map<String, Object> oMap = orderMap.get(org);
            map.put("name", org);

            if (iMap != null) { // 当前事业部中有此询单信息
                map.put("avgNeedTime", RateUtil.doubleChainRateTwo(((BigDecimal) iMap.get("avgNeedTime")).doubleValue(), 1));
                map.put("inquiryNumRate", RateUtil.intChainRate(((Long) iMap.get("total")).intValue(), inquiryCount));
            } else { // 当前事业部没有此询单信息
                map.put("avgNeedTime", 0);
                map.put("inquiryNumRate", 0);
            }

            if (oMap != null) {
                map.put("totalAmount", RateUtil.doubleChainRateTwo(((BigDecimal) oMap.get("totalAmount")).doubleValue(), 1));
                map.put("orderNumRate", RateUtil.intChainRate(((Long) oMap.get("totalNum")).intValue(), orderCount));
            } else {
                map.put("totalAmount", 0);
                map.put("orderNumRate", 0);
            }

            tableData.add(map);
        }


        Map<String, Object> data = new HashedMap();
        data.put("inquiryPie", inquiryPie);
        data.put("orderPie", orderPie);
        data.put("orderAmountPie", orderAmountPie);
        data.put("tableData", tableData);
        return new Result<>(data);
    }


    // 区域明细对比
    @ResponseBody
    @RequestMapping(value = "/areaDetailContrast", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object areaDetailContrast(@RequestBody Map<String, Object> map) throws ParseException {
        Result<Object> result = new Result<>();
        if (!map.containsKey("startTime") || !map.containsKey("endTime")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        ///查询给定时间的区域询单数量和金额
        List<Map<String, Object>> inquiryList = inquiryService.findCountAndPriceByRangRollinTimeGroupArea(startTime, endTime);
        // 查询给定时间段的区域订单数量和金额
        List<Map<String, Object>> orderList = orderService.findCountAndAmountByRangProjectStartGroupArea(startTime, endTime);


        // 组织询单数据结果集
        int inquirySize = inquiryList.size() + 1; // 全部+总计1
        String[] areaInqCounts = new String[inquirySize];// 询单数量区域列表
        String[] areaInqAmounts = new String[inquirySize];// 询单金额区域列表
        int[] inqCounts = new int[inquirySize];// 询单数量列表
        Double[] inqAmounts = new Double[inquirySize];// 询单金额列表
        int inqTotalCount = 0;
        double inqTotalAmounts = 0;
        for (int i = 0; i < inquirySize - 1; i++) {
            Map<String, Object> vo = inquiryList.get(i);
            String area = (String) vo.get("area"); // 大区
            BigDecimal totalAmount = (BigDecimal) vo.get("totalAmount"); // 金额
            Long total = (Long) vo.get("total"); // 数量
            areaInqCounts[i + 1] = area;
            areaInqAmounts[i + 1] = area;
            inqCounts[i + 1] = total.intValue();
            inqAmounts[i + 1] = totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            inqTotalCount += inqCounts[i + 1];
            inqTotalAmounts += inqAmounts[i + 1];
        }
        areaInqCounts[0] = "询单总数量";
        areaInqAmounts[0] = "询单总金额";
        inqCounts[0] = inqTotalCount;
        inqAmounts[0] = inqTotalAmounts;


        // 组织订单数据结果集
        int orderSize = orderList.size() + 1; // 全部+总计1
        String[] areaOrdCounts = new String[orderSize];// 订单数量区域列表
        String[] areaOrdAmounts = new String[orderSize];// 订单金额区域列表
        int[] ordCounts = new int[orderSize];// 订单数量列表
        Double[] ordAmounts = new Double[orderSize];// 订单金额列表
        int orderTotalCount = 0;
        double orderTotalAmounts = 0;
        for (int i = 0; i < orderSize - 1; i++) {
            Map<String, Object> vo = orderList.get(i);
            String area = (String) vo.get("area"); // 大区
            BigDecimal totalAmount = (BigDecimal) vo.get("totalAmount"); // 金额
            Long total = (Long) vo.get("totalNum"); // 数量
            areaOrdCounts[i + 1] = area;
            areaOrdAmounts[i + 1] = area;
            ordCounts[i + 1] = total.intValue();

            ordAmounts[i + 1] = totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            orderTotalCount += ordCounts[i + 1];
            orderTotalAmounts += ordAmounts[i + 1];
        }
        areaOrdCounts[0] = "订单总数量";
        areaOrdAmounts[0] = "订单总金额";
        ordCounts[0] = orderTotalCount;
        ordAmounts[0] = orderTotalAmounts;

        // 将组织数据放入结果数据中
        Map<String, Object> data = new HashedMap();
        // 询单数量
        Map<String, Object> inqCount = new HashedMap();
        inqCount.put("marketArea", areaInqAmounts);
        inqCount.put("inqCounts", inqCounts);
        data.put("inqCount", inqCount);
        // 询单金额
        Map<String, Object> inqAmount = new HashedMap();
        inqAmount.put("marketArea", areaInqCounts);
        inqAmount.put("inqAmount", inqAmounts);
        data.put("inqAmount", inqAmount);
        // 订单数量
        Map<String, Object> ordCount = new HashedMap();
        ordCount.put("marketArea", areaOrdCounts);
        ordCount.put("ordCounts", ordCounts);
        data.put("ordCount", ordCount);
        // 订单金额
        Map<String, Object> orderAmount = new HashedMap();
        orderAmount.put("marketArea", areaOrdAmounts);
        orderAmount.put("ordAmount", ordAmounts);
        data.put("orderAmount", orderAmount);

        return result.setData(data);
    }

    // 品类明细
    @ResponseBody
    @RequestMapping(value = "/catesDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object catesDetail(@RequestBody Map<String, Object> map) throws Exception {
        Result<Object> result = new Result<>();

        if (!map.containsKey("startTime") || !map.containsKey("endTime")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        int inqTotalCount = inquiryService.inquiryCountByTime(startTime, endTime, "", 0, 0, "", "");
        Double inqTotalAmount = inquiryService.inquiryAmountByTime(startTime, endTime, "");
        int ordTotalCount = orderService.orderCountByTime(startTime, endTime, "", "", "");
        Double ordTotalAmount = orderService.orderAmountByTime(startTime, endTime, "");
        List<CateDetailVo> inqList = this.inquiryService.selectInqDetailByCategory(startTime, endTime);
        List<CateDetailVo> ordList = orderService.selecOrdDetailByCategory(startTime, endTime);
        final Map<String, CateDetailVo> ordMap;
        if (inqList != null && ordList != null) {
            ordMap = ordList.parallelStream().collect(Collectors.toMap(CateDetailVo::getCategory, vo -> vo));
        } else {
            ordMap = new HashMap<>();
        }
        if (inqList != null) {
            for (CateDetailVo inqDetailVo : inqList) {
                String category = inqDetailVo.getCategory();
                CateDetailVo catevo = ordMap.get(category);
                if (catevo != null) {
                    if (catevo.getOrdCateCount() > 0) {
                        inqDetailVo.setOrdCateCount(catevo.getOrdCateCount());
                    }
                    if (ordTotalCount > 0) {
                        inqDetailVo.setOrdProportion(RateUtil.intChainRate(catevo.getOrdCateCount(), ordTotalCount));
                    }
                    if (catevo.getOrdCatePrice() > 0) {
                        inqDetailVo.setOrdCatePrice(catevo.getOrdCatePrice());
                    }
                    if (ordTotalAmount > 0) {
                        inqDetailVo.setOrdAmountProportion(
                                RateUtil.doubleChainRate(catevo.getOrdCatePrice(), ordTotalAmount));
                    }
                } else {
                    inqDetailVo.setOrdCateCount(0);
                    inqDetailVo.setOrdProportion(0.00);
                    inqDetailVo.setOrdCatePrice(0.00);
                    inqDetailVo.setOrdAmountProportion(0.00);
                }
                if (inqTotalCount > 0) {
                    inqDetailVo.setInqProportion(RateUtil.intChainRate(inqDetailVo.getInqCateCount(), inqTotalCount));
                }
                if (inqTotalAmount > 0) {
                    inqDetailVo.setInqAmountProportion(
                            RateUtil.doubleChainRate(inqDetailVo.getInqCatePrice(), inqTotalAmount));
                }
            }
        }
        inqList.sort((vo1, vo2) -> {
            int count1 = vo2.getInqCateCount() + vo2.getOrdCateCount();
            int count2 = vo1.getInqCateCount() + vo1.getOrdCateCount();

            return count1 - count2;
        });
        result.setStatus(ResultStatusEnum.SUCCESS);
        result.setData(inqList);
        return result;

    }

    /**
     * 获取区域明细中的所有大区和大区中的所有国家列表
     *
     * @return
     */
    @RequestMapping("/areaList")
    @ResponseBody
    public Object areaList(String areaName) {
        Result<Object> result = new Result<>();

        List<InquiryAreaVO> arayList = inquiryService.selectAllAreaAndCountryList();
        if (StringUtils.isNotBlank(areaName)) {
            List<InquiryAreaVO> ll = arayList.parallelStream().filter(vo -> vo.getAreaName().equals(areaName))
                    .collect(Collectors.toList());
            if (ll.size() > 0) {
                result.setData(ll.get(0).getCountries());
            } else {
                return result.setStatus(ResultStatusEnum.AREA_NOT_EXIST);
            }
        } else {
            List<String> areaList = arayList.parallelStream().map(InquiryAreaVO::getAreaName)
                    .collect(Collectors.toList());
            result.setData(areaList);
        }
        return result;
    }

    /**
     * 客户中心的订单和询单数据明细
     *
     * @param map 大区
     *            <p>
     *            城市
     * @return
     */
    @RequestMapping(value = "/areaDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object areaDetail(@RequestBody Map<String, Object> map) throws Exception {
        Result<Object> result = new Result<>();

        if (!map.containsKey("startTime") || !map.containsKey("endTime") || !map.containsKey("area") || !map.containsKey("country")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        String areaName = (String) map.get("area");
        String countryName = (String) map.get("country");
        CustomerNumSummaryVO orderNumSummary = orderService.numSummary(startTime, endTime, areaName, countryName);
        CustomerNumSummaryVO inquiryNumSummary = inquiryService.numSummary(startTime, endTime, areaName, countryName);

        Map<String, Object> numData = new HashMap<String, Object>();

        String[] xTitleArr = new String[]{"询单数量", "油气数量", "非油气数量", "订单数量", "油气数量", "非油气数量",};
        Integer[] yValueArr = new Integer[]{inquiryNumSummary.getTotal(), inquiryNumSummary.getOil(),
                inquiryNumSummary.getNonoil(), orderNumSummary.getTotal(), orderNumSummary.getOil(),
                orderNumSummary.getNonoil()};
        numData.put("x", xTitleArr);
        numData.put("y", yValueArr);

        Map<String, Object> amountData = new HashMap<>();

        String[] xTitleArr02 = new String[]{"询单总金额", "油气金额", "非油气金额", "订单总金额", "油气金额", "非油气金额",};
        BigDecimal[] yValueArr02 = new BigDecimal[]{
                inquiryNumSummary.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
                inquiryNumSummary.getOilAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
                inquiryNumSummary.getNoNoilAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
                orderNumSummary.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
                orderNumSummary.getOilAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
                orderNumSummary.getNoNoilAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN)};
        amountData.put("x", xTitleArr02);
        amountData.put("y", yValueArr02);

        Map<String, Object> data = new HashMap<>();
        data.put("amount", amountData);
        data.put("number", numData);

        return new Result<>().setData(data);
    }
}
