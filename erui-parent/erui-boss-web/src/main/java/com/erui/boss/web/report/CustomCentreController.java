package com.erui.boss.web.report;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquiryVo;
import com.erui.report.service.*;
import com.erui.report.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
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
    @Autowired
    private InquirySKUService inquirySKUService;

    @Autowired
    private InqRtnReasonService inqRtnReasonService;
    @Autowired
    private TargetService targetService;

    private static DecimalFormat df = new DecimalFormat("0.00");


    /*
     * 询单总览
     * */
    @ResponseBody
    @RequestMapping(value = "/inquiryPandect", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> inquiryPandect(@RequestBody(required = true) Map<String, Object> params) {
        //验证参数
        params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR2, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }

        //获取询单基本信息  询单数量、金额 、环比新增、环比率
        Map<String, Object> inqInfo = this.inquiryService.selectInqInfoByCondition(params);
        //获取询单商品信息   商品数量、环比新增 、环比率
        params = ParamsUtils.getCurrentParams(params);
        Map<String, Object> goodsMap = inquirySKUService.selectInqGoodsInfoByCondition(params);
        //获取询单商品平台数据  平台数量 、非平台数量、平台占比、平台环比新增、平台环比率
        params = ParamsUtils.getCurrentParams(params);
        Map<String, Object> platMap = inquirySKUService.selectPlatInfoByCondition(params);
        //获取分类 询价商品次数 Top3
        params = ParamsUtils.getCurrentParams(params);
        List<Map<String, Object>> listTop3 = inquirySKUService.selectProTop3(params);
        Map<String, Object> datas = new HashMap<>();
        datas.put("inquiry", inqInfo);
        datas.put("proTop3", listTop3);
        datas.put("goodsMap", goodsMap);
        datas.put("platMap", platMap);
        return new Result<>(datas);
    }


    /*
     * 报价总览
     * */
    @ResponseBody
    @RequestMapping(value = "/quotePandect", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> quotePandect(@RequestBody(required = true) Map<String, Object> params) {
        //验证请求参数
        params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR2, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }

        //获取报价询单基本信息  询单数量、金额 、环比新增、环比率
        String[] quotes = new String[]{QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus(),
                QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus()};
        params.put("quotes", quotes);
        Map<String, Object> inquiryMap = this.inquiryService.selectInqInfoByCondition(params);
        //获取报价询单商品油气数据
        params = ParamsUtils.getCurrentParams(params);
        Map<String, Object> proIsOilMap = inquirySKUService.selectIsOilInfoByCondition(params);
        //获取报价询单商品次数分类 Top3
        params = ParamsUtils.getCurrentParams(params);
        List<Map<String, Object>> listTop3 = inquirySKUService.selectProTop3(params);
        Map<String, Object> datas = new HashMap<>();
        datas.put("quote", inquiryMap);
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
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        // 获取需要环比的开始时间
        long differenTime = endDate.getTime() - startDate.getTime();
        Date rateStartDate = DateUtil.getBeforTime(startDate, differenTime);

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
            if (chainProfit != null) {
                chainProfitRate = RateUtil.doubleChainRate(profit - chainProfit, chainProfit);
            }
        }

        Map<String, Object> profitMap = new HashMap<>();
        profitMap.put("profitRate", profitRate);
        profitMap.put("chainRate", chainProfitRate);

        // 成单率
        double successOrderRate = 0.00;
        int successOrderCount = orderService.orderCountByTime(startDate, endDate, null, "", "");
        int successInquiryCount = inquiryService.inquiryCountByTime(startDate, endDate,
                new String[]{QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus(), QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus()}
                , 0, 0, "", "");
        if (successInquiryCount > 0) {
            successOrderRate = RateUtil.intChainRate(successOrderCount, successInquiryCount);
        }
        Map<String, Object> sucessOrderMap = new HashMap<>();
        sucessOrderMap.put("successOrderRate", successOrderRate);

        // top3
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startDate);
        map.put("endTime", endDate);
        List<Map<String, Object>> proTop3 = orderService.selectOrderProTop3(map);
        Map<String, Object> top3Container = new HashMap(); /// 定义要返回结果的top容器
        for (int i = 0; i < proTop3.size(); i++) {
            Map<String, Object> m = proTop3.get(i);
            BigDecimal s = new BigDecimal(String.valueOf(m.get("orderCount")));
            m.put("topProportion", RateUtil.intChainRate(s.intValue(), count));
            top3Container.put("top" + (i + 1), m);
        }

        // 组装数据
        Map<String, Object> datas = new HashMap<>();// 订单统计信息
        datas.put("order", orderMap);
        datas.put("profitRate", profitMap);
        datas.put("sucessOrderMap", sucessOrderMap);
        datas.put("top3", top3Container);

        return new Result<>().setData(datas);
    }


    // 询单分析
    @ResponseBody
    @RequestMapping(value = "/inquiryDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    public Object inquiryDetail(@RequestBody(required = true) Map<String, Object> params) {
        //验证请求参数
        params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR2, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }
        //获取已完成询单基本信息
        String[] quotes = new String[]{QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus(),
                QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus()};
        params.put("quotes", quotes);
        Map<String, Object> quotedMap = this.inquiryService.selectInqInfoByCondition(params);

        //获取报价中询单基本信息
        params = ParamsUtils.getCurrentParams(params);
        quotes = new String[]{QuotedStatusEnum.STATUS_QUOTED_NO.getQuotedStatus(),
                QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus()};
        params.put("quotes", quotes);
        Map<String, Object> quotingMap = this.inquiryService.selectInqInfoByCondition(params);

        //获取取消询单基本信息
        params = ParamsUtils.getCurrentParams(params);
        quotes = new String[]{QuotedStatusEnum.STATUS_QUOTED_CANCEL.getQuotedStatus()};
        params.put("quotes", quotes);
        Map<String, Object> cancelMap = this.inquiryService.selectInqInfoByCondition(params);

        //获取 已完成、报价中、询单取消 各自的询单数量和占比
        int quotedCount = Integer.parseInt(String.valueOf(quotedMap.get("count")));
        int quotingCount = Integer.parseInt(String.valueOf(quotingMap.get("count")));
        int cancelCount = Integer.parseInt(String.valueOf(cancelMap.get("count")));
        int totalCount = quotedCount + quotingCount + cancelCount;
        Double quotedInquiryRate = 0d;
        Double quotingInquiryRate = 0d;
        Double cancelInquiryRate = 0d;
        if (totalCount > 0) {
            quotedInquiryRate = RateUtil.intChainRate(quotedCount, totalCount);
            quotingInquiryRate = RateUtil.intChainRate(quotingCount, totalCount);
            cancelInquiryRate = RateUtil.intChainRate(cancelCount, totalCount);
        }

        //组装询单状态总览数据
        HashMap<String, Object> inquiryDetailMap = new HashMap<>();
        inquiryDetailMap.put("quotedCount", quotedCount);
        inquiryDetailMap.put("quotingCount", quotingCount);
        inquiryDetailMap.put("cancelCount", cancelCount);
        inquiryDetailMap.put("quotedInquiryRate", quotedInquiryRate);
        inquiryDetailMap.put("quotingInquiryRate", quotingInquiryRate);
        inquiryDetailMap.put("cancelInquiryRate", cancelInquiryRate);

        //获取退回询单数量
        params = ParamsUtils.getCurrentParams(params);
        int rtnCount = inquiryService.selectInqRtnCountByTime(params);//询单退回数量

        //获取询单退回原因分析数据
        params = ParamsUtils.getCurrentParams(params);
        Map<String, Object> rtnMap = inqRtnReasonService.selectCountGroupByRtnSeason(params);
        //获取项目澄清数据 projectClear
        int projectClearCount = Integer.parseInt(rtnMap.get("projectClearCount").toString());
        //获取非事业部业务范围 notOrg
        int notOrgCount = Integer.parseInt(rtnMap.get("notOrgCount").toString());
        //获取无供应渠道 notSupply
        int notSupplyCount = Integer.parseInt(rtnMap.get("notSupplyCount").toString());
        //获取系统问题 systemProblems
        int systemProblemsCount = Integer.parseInt(rtnMap.get("systemProblemsCount").toString());
        //获取其他  other
        int otherCount = Integer.parseInt(rtnMap.get("otherCount").toString());
        List<Map<String, Object>> tableData = getRtnTable(rtnMap);

        //获取退回询单汇总数据  {退回询单总数，退回总次数，平均退回次数，退回询单总占比}
        int totalRtnCount = projectClearCount + notOrgCount + notSupplyCount + systemProblemsCount + otherCount;
        Double avgRtnCount = 0d;
        Double rtnInqProportion = 0d;
        if (rtnCount > 0) {
            avgRtnCount = RateUtil.intChainRateTwo(totalRtnCount, rtnCount);
        }
        if (totalCount > 0) {
            rtnInqProportion = RateUtil.intChainRate(rtnCount, totalCount);
        }
        Map<String, Object> rtnSummary = new HashMap<>();
        rtnSummary.put("totalRtnInqCount", rtnCount);
        rtnSummary.put("totalRtnCount", totalRtnCount);
        rtnSummary.put("avgRtnCount", avgRtnCount);
        rtnSummary.put("rtnInqProportion", rtnInqProportion);
        //组长退回原因表格数据

        //组装数据
        HashMap<String, Object> data = new HashMap<>();
        data.put("quoteSummary", inquiryDetailMap);
        data.put("rtnTable", tableData);
        data.put("rtnSummary", rtnSummary);
        return new Result<>(data);
    }

    // 订单分析
    @ResponseBody
    @RequestMapping(value = "/orderDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    public Object orderDetail(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
//        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]
        //1.计算订单汇总数据
        Map<String, Object> ordSummary = new HashMap<>();
        List<Map<String, Object>> targetMaps = targetService.selectTargetGroupByOrg();
        double targetAmount = targetMaps.stream().map(map -> {
            double ammount = Double.parseDouble(map.get("ammount").toString());
            return ammount;
        }).reduce(0d, (a, b) -> a + b);//指标 万美元
        double fAmount = orderService.orderAmountByTime(null, endDate, null);//累计完成金额
        double finishedAmount = RateUtil.doubleChainRateTwo(fAmount, 10000d);//万美元
        double finishRate = 0d;//完成率
        if (targetAmount > 0) {
            finishRate = RateUtil.doubleChainRate(finishedAmount, targetAmount);
        }
        double rePurRate = 0d;//复购率
        int custCount = 0;
        int rePurCustCount = 0;
        List<Map<String, Object>> rePurList = orderService.selectRePurchaseDetail(startDate, endDate, null, null);
        List<Map<String, Object>> buyCounts = rePurList.stream().filter(map -> Integer.parseInt(map.get("buyCount").toString()) > 1).collect(Collectors.toList());

        if (rePurList != null) {
            custCount = rePurList.size();
        }
        if (buyCounts != null) {
            rePurCustCount = buyCounts.size();
        }
        if (custCount > 0) {
            rePurRate = RateUtil.intChainRate(rePurCustCount, custCount);
        }
        ordSummary.put("target", RateUtil.doubleChainRateTwo(targetAmount, 1d));
        ordSummary.put("ordAmount", RateUtil.doubleChainRateTwo(finishedAmount, 1d));
        ordSummary.put("finishedRate", finishRate);
        ordSummary.put("rePurRate", rePurRate);
        //2.计算事业部和大区详细数据
        List<Map<String, Object>> areaTargets = targetService.selectTargetGroupByArea();//查询各地区的年度指标
        List<Map<String, Object>> orgTargets = targetService.selectTargetGroupByOrg();//查询各事业部的年度指标
        List<Map<String, Object>> areaDatas = orderService.selectDataGroupByArea(startDate, endDate);//查询各地区的订单数量和金额
        List<Map<String, Object>> orgDatas = orderService.selectDataGroupByOrg(startDate, endDate);//查询各事业部的订单数量和金额
        Map<String, Map<String, Object>> areaTargetMap = areaTargets.stream().collect(Collectors.toMap(vo -> vo.get("area").toString(), vo -> vo));
        Map<String, Map<String, Object>> orgTargetMap = orgTargets.stream().collect(Collectors.toMap(vo -> vo.get("org").toString(), vo -> vo));
        List<String> areas = new ArrayList<>();
        List<Double> areaFinisheds = new ArrayList<>();
        List<Double> areaUnfinisheds = new ArrayList<>();
        List<String> orgs = new ArrayList<>();
        List<Double> orgFinisheds = new ArrayList<>();
        List<Double> orgUnfinisheds = new ArrayList<>();
        areaDatas.stream().forEach(map -> {
            String area = String.valueOf(map.get("area"));
            double oAmount = Double.parseDouble(map.get("ordAmmount").toString());
            areas.add(area);
            areaFinisheds.add(RateUtil.doubleChainRateTwo(oAmount, 1d));
            if (areaTargetMap.containsKey(area)) {
                Map<String, Object> m = areaTargetMap.get(area);
                double ammount = Double.parseDouble(m.get("ammount").toString());
                areaUnfinisheds.add(RateUtil.doubleChainRateTwo(ammount * 10000 - oAmount, 1d));
            } else {
                areaUnfinisheds.add(RateUtil.doubleChainRateTwo(-oAmount, 1d));
            }
        });
        orgDatas.stream().forEach(map -> {
            String org = String.valueOf(map.get("org"));
            double oAmount = Double.parseDouble(map.get("ordAmmount").toString());
            orgs.add(org);
            orgFinisheds.add(RateUtil.doubleChainRateTwo(oAmount, 1d));
            if (orgTargetMap.containsKey(org)) {
                Map<String, Object> m = orgTargetMap.get(org);
                double ammount = Double.parseDouble(m.get("ammount").toString());
                orgUnfinisheds.add(RateUtil.doubleChainRateTwo(ammount * 10000 - oAmount, 1d));
            } else {
                orgUnfinisheds.add(RateUtil.doubleChainRateTwo(-oAmount, 1d));
            }
        });

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> areaDetails = new HashMap<>();
        Map<String, Object> orgDetails = new HashMap<>();
        areaDetails.put("areas", areas);
        areaDetails.put("finisheds", areaFinisheds);
        areaDetails.put("unfinisheds", areaUnfinisheds);
        orgDetails.put("orgs", orgs);
        orgDetails.put("finisheds", orgFinisheds);
        orgDetails.put("unfinisheds", orgUnfinisheds);
        data.put("areaDetail", areaDetails);
        data.put("orgDetail", orgDetails);
        data.put("ordSummary", ordSummary);
        return new Result<>(data);
    }


    // 询单时间分布分析
    @ResponseBody
    @RequestMapping(value = "/inquiryTimeDistrbute", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    public Object inquiryTimeDistrbute(@RequestBody(required = true) Map<String, Object> params) {
        // 获取参数并转换成时间格式
        params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR2, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        String[] quotes = new String[]{"已报价", "已完成"};
        params.put("quotes", quotes);
        Map<String, Object> data = this.inquiryService.selectQuoteTimeSummaryData(params);
        return new Result<>(data);
    }

    // 事业部明细
    @ResponseBody
    @RequestMapping(value = "/busUnitDetail", method = RequestMethod.POST)
    public Object busUnitDetail(@RequestBody(required = true) Map<String, Object> params) {
        //验证参数
        params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR2, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        //1查询给定时间的事业部询单数量
        List<Map<String, Object>> quiryList = inquiryService.findCountByRangRollinTimeGroupOrigation(params);
        // 查询给定时间段的事业部订单数量和金额
        List<Map<String, Object>> derList = orderService.findCountAndAmountByRangProjectStartGroupOrigation(params);
        //查询给定时间段的事业部平均报价时间
        String[] quotes = new String[]{QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus(),
                QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus()};
        params.put("quotes", quotes);
        List<Map<String, Object>> NeedTimeList = inquiryService.findAvgNeedTimeByRollinTimeGroupOrigation(params);

        //2.整理数据成标准的事业部数据
        List<Map<String, Object>> inquiryList = new ArrayList<>();
        Map<String, Map<String, Object>> iiMap = new HashMap<>();
        quiryList.stream().forEach(map -> {
            String organization = String.valueOf(map.get("organization"));
            String standardOrg = inquiryService.getStandardOrg(organization);
            Long total = (Long) map.get("total");
            if (iiMap.containsKey(standardOrg)) {
                Map<String, Object> m2 = iiMap.get(standardOrg);
                Long tota2 = (Long) m2.get("total");
                m2.put("total", tota2 + total);
            } else {
                map.put("organization", standardOrg);
                iiMap.put(standardOrg, map);
            }
        });
        if (iiMap.size() > 0) {
            for (String key : iiMap.keySet()) {
                inquiryList.add(iiMap.get(key));
            }
        }
        //询单平均报价时间数据
        List<Map<String, Object>> avgNeedTimeList = new ArrayList<>();
        Map<String, Map<String, Object>> aaMap = new HashMap<>();
        NeedTimeList.stream().forEach(map -> {
            String org = String.valueOf(map.get("organization"));
            String standardOrg = inquiryService.getStandardOrg(org);
            BigDecimal avg1 = (BigDecimal) map.get("avgNeedTime");
            Long tota1 = (Long) map.get("total");
            if (aaMap.containsKey(standardOrg)) {
                Map<String, Object> m2 = aaMap.get(standardOrg);
                BigDecimal avg2 = (BigDecimal) m2.get("avgNeedTime");
                Long tota2 = (Long) m2.get("total");
                Double avgNTime = RateUtil.doubleChainRateTwo(avg1.intValue() * tota1 + avg2.intValue() * tota2, tota1 + tota2);
                m2.put("avgNeedTime", new BigDecimal(avgNTime));
            } else {
                map.put("organization", standardOrg);
                aaMap.put(standardOrg, map);
            }
        });
        if (aaMap.size() > 0) {
            for (String key : aaMap.keySet()) {
                avgNeedTimeList.add(aaMap.get(key));
            }
        }
        //订单数据
        List<Map<String, Object>> orderList = new ArrayList<>();
        Map<String, Map<String, Object>> ooMap = new HashMap<>();
        derList.stream().forEach(map -> {
            String org = String.valueOf(map.get("organization"));
            String standardOrg = inquiryService.getStandardOrg(org);
            Long total = (Long) map.get("totalNum");
            if (ooMap.containsKey(standardOrg)) {
                Map<String, Object> m2 = ooMap.get(standardOrg);
                Long total2 = (Long) map.get("totalNum");
                m2.put("totalNum", total + total2);
            } else {
                map.put("organization", standardOrg);
                ooMap.put(standardOrg, map);
            }
        });
        if (ooMap.size() > 0) {
            for (String key : ooMap.keySet()) {
                orderList.add(ooMap.get(key));
            }
        }
        //3 所有事业部(这里都是辅助变量，下面构建表格数据要使用)
        Set<String> organizations = new HashSet<>();
        Integer inquiryCount = 0;
        Integer orderCount = 0;
        // 将列表转换为以事业部名称为键的map
        Map<String, Map<String, Object>> inquiryMap = new HashedMap();
        Map<String, Map<String, Object>> orderMap = new HashedMap();

        // 询单事业部和总询单数
        inquiryCount = inquiryList.stream().map(m -> {
            String organization = String.valueOf(m.get("organization"));
            String standardOrg = inquiryService.getStandardOrg(organization);
            Long total = (Long) m.get("total");
            inquiryMap.put(organization, m);
            organizations.add(organization);
            return total.intValue();
        }).reduce(0, (a, b) -> a + b);
        Map<String, Map<String, Object>> avgMap = avgNeedTimeList.parallelStream().collect(Collectors.toMap(m -> (String) m.get("organization"), m -> m));
        for (String org : inquiryMap.keySet()) {
            if (!avgMap.containsKey(org)) {
                Map<String, Object> m = inquiryMap.get(org);
                m.put("avgNeedTime", BigDecimal.valueOf(0d));
                inquiryMap.put(org, m);
            }
        }
        avgNeedTimeList.parallelStream().forEach(m -> {
            String org = String.valueOf(m.get("organization"));
            if (inquiryMap.containsKey(org)) {
                Map<String, Object> map = inquiryMap.get(org);
                BigDecimal avgNeedTime = (BigDecimal) m.get("avgNeedTime");
                map.put("avgNeedTime", avgNeedTime);
                inquiryMap.put(org, map);
            }
        });


        orderCount = orderList.stream().map(m -> {
            String organization = String.valueOf(m.get("organization"));
            Long total = (Long) m.get("totalNum");
            orderMap.put(organization, m);
            organizations.add(organization);
            return total.intValue();
        }).reduce(0, (a, b) -> a + b);


        // 事业部列表
        List<String> busUnitList = new ArrayList<>(organizations);
        // 事业部的询单占比、平均报价时间、订单占比、成单金额表格数据组合
        List<Map<String, Object>> tableData = new ArrayList<>();
        // 询单数数据列表
        List<Integer> inquiryCountList = new ArrayList<>();
        // 订单数数据列表
        List<Integer> orderCountList = new ArrayList<>();
        // 事业部成单金额列表
        List<Double> seccessOrderCountList = new ArrayList<>();

        for (String org : busUnitList) {
            Map<String, Object> iMap = inquiryMap.get(org);
            Map<String, Object> oMap = orderMap.get(org);

            double avgNeedTime = 0;
            int inquiryTotal = 0;
            double orderTotalAmount = 0;
            int orderTotal = 0;
            if (iMap != null) {
                inquiryTotal = ((Long) iMap.get("total")).intValue();
                avgNeedTime = RateUtil.doubleChainRateTwo(((BigDecimal) iMap.get("avgNeedTime")).doubleValue(), 1);
            }
            if (oMap != null) {
                orderTotalAmount = RateUtil.doubleChainRateTwo(((BigDecimal) oMap.get("totalAmount")).doubleValue(), 1);
                orderTotal = ((Long) oMap.get("totalNum")).intValue();
            }

            // 事业部表格信息
            Map<String, Object> tableMap = new HashedMap();
            tableMap.put("name", org);
            tableMap.put("avgNeedTime", avgNeedTime);
            tableMap.put("inquiryTotal", inquiryTotal);
            tableMap.put("inquiryNumRate", RateUtil.intChainRate(inquiryTotal, inquiryCount));
            tableMap.put("totalAmount", orderTotalAmount);
            tableMap.put("orderNumRate", RateUtil.intChainRate(orderTotal, orderCount));
            tableData.add(tableMap);
            // 事业部询单数数据
            inquiryCountList.add(inquiryTotal);
            // 事业部订单数数据
            orderCountList.add(orderTotal);
            /// 事业部订单成单金额数据
            seccessOrderCountList.add(orderTotalAmount);
        }


        Map<String, Object> data = new HashedMap();
        //// 事业部列表
        data.put("busUnit", busUnitList);
        // 询单数数据列表
        data.put("inquiryCount", inquiryCountList);
        // 订单数数据列表
        data.put("orderCount", orderCountList);
        // 事业部成单金额列表
        data.put("seccessOrderCount", seccessOrderCountList);
        // 事业部表格信息
        tableData.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return (Integer) o2.get("inquiryTotal") - (Integer) o1.get("inquiryTotal");
            }
        });
        data.put("tableData", tableData);
        return new Result<>(data);
    }


    // 区域明细对比
    @ResponseBody
    @RequestMapping(value = "/areaDetailContrast", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object areaDetailContrast(@RequestBody Map<String, String> map) throws ParseException {

        // 获取参数并转换成时间格式
        //开始时间
        Date startDate = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endDate = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.PARAM_TYPE_ERROR);
        }
        // 结束时间
//        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]

        Result<Object> result = new Result<>();
        ///查询给定时间的区域询单数量和金额
        List<Map<String, Object>> inquiryList = inquiryService.findCountAndPriceByRangRollinTimeGroupArea(startDate, endDate, 0, null);
        // 查询给定时间段的区域订单数量和金额
        List<Map<String, Object>> orderList = orderService.findCountAndAmountByRangProjectStartGroupArea(startDate, endDate);

        // 组织询单数据结果集
        int inquirySize = inquiryList.size() + 1; // 全部+总计1
        String[] areaInqCounts = new String[inquirySize];// 询单数量区域列表
        String[] areaInqAmounts = new String[inquirySize];// 询单金额区域列表
        int[] inqCounts = new int[inquirySize];// 询单数量列表
        Double[] inqAmounts = new Double[inquirySize];// 询单金额列表
        int inqTotalCount = 0;
        BigDecimal inqTotalAmounts = BigDecimal.ZERO;
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
            inqTotalAmounts = inqTotalAmounts.add(totalAmount);
        }
        areaInqCounts[0] = "询单总数量";
        areaInqAmounts[0] = "询单总金额";
        inqCounts[0] = inqTotalCount;
        inqAmounts[0] = inqTotalAmounts.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


        // 组织订单数据结果集
        int orderSize = orderList.size() + 1; // 全部+总计1
        String[] areaOrdCounts = new String[orderSize];// 订单数量区域列表
        String[] areaOrdAmounts = new String[orderSize];// 订单金额区域列表
        int[] ordCounts = new int[orderSize];// 订单数量列表
        Double[] ordAmounts = new Double[orderSize];// 订单金额列表
        int orderTotalCount = 0;
        BigDecimal orderTotalAmounts = BigDecimal.ZERO;
        for (int i = 0; i < orderSize - 1; i++) {
            Map<String, Object> vo = orderList.get(i);
            String area = (String) vo.get("area"); // 大区
            BigDecimal totalAmount = (BigDecimal) vo.get("totalAmount"); // 金额
            Long total = (Long) vo.get("totalNum"); // 数量
            areaOrdCounts[i + 1] = area;
            areaOrdAmounts[i + 1] = area;
            ordCounts[i + 1] = total.intValue();

            ordAmounts[i + 1] = totalAmount.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();

            orderTotalCount += ordCounts[i + 1];
            orderTotalAmounts = orderTotalAmounts.add(totalAmount);
        }
        areaOrdCounts[0] = "订单总数量";
        areaOrdAmounts[0] = "订单总金额";
        ordCounts[0] = orderTotalCount;
        ordAmounts[0] = orderTotalAmounts.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();

        // 将组织数据放入结果数据中
        Map<String, Object> data = new HashedMap();
        // 询单数量
        Map<String, Object> inqCount = new HashedMap();
        inqCount.put("marketArea", areaInqCounts);
        inqCount.put("inqCounts", inqCounts);
        data.put("inqCount", inqCount);
        // 询单金额
        Map<String, Object> inqAmount = new HashedMap();
        inqAmount.put("marketArea", areaInqAmounts);
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
        //开始时间
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime").toString(), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime").toString(), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.PARAM_TYPE_ERROR);
        }

        int inqTotalCount = 0;
        int ordTotalCount = 0;
        BigDecimal inqTotalAmount = BigDecimal.ZERO;
        BigDecimal ordTotalAmount = BigDecimal.ZERO;

        List<CateDetailVo> inqList = inquirySKUService.selectSKUDetailByCategory(map);
        List<CateDetailVo> ordList = orderService.selecOrdDetailByCategory(startTime, endTime);
        //1.饼图数据 inqCateCount,category
        List<String> inqCateList = new ArrayList<>();
        List<String> ordCateList = new ArrayList<>();
        List<Integer> inqCountList = new ArrayList<>();
        List<Integer> ordCountList = new ArrayList<>();
        //排序
        inqList.sort(new Comparator<CateDetailVo>() {
            @Override
            public int compare(CateDetailVo o1, CateDetailVo o2) {
                return o2.getInqCateCount() - o1.getInqCateCount();
            }
        });
        ordList.sort(new Comparator<CateDetailVo>() {
            @Override
            public int compare(CateDetailVo o1, CateDetailVo o2) {
                return o2.getOrdCateCount() - o1.getOrdCateCount();
            }
        });
        inqList.stream().forEach(vo -> {
            inqCateList.add(vo.getCategory());
            inqCountList.add(vo.getInqCateCount());
        });
        ordList.stream().forEach(vo -> {
            ordCateList.add(vo.getCategory());
            ordCountList.add(vo.getOrdCateCount());
        });
        Map<String, Object> inqPie = new HashMap<>();
        Map<String, Object> ordPie = new HashMap<>();
        inqPie.put("categoryList", inqCateList);
        inqPie.put("countList", inqCountList);
        ordPie.put("categoryList", ordCateList);
        ordPie.put("countList", ordCountList);

        //2.品类明细数据
        Map<String, CateDetailVo> ordMap = new HashMap<>();
        Map<String, CateDetailVo> inqMap = new HashMap<>();
        final Set<String> category = new HashSet<>();
        if (ordList != null) {
            for (CateDetailVo vo : ordList) {
                String category1 = vo.getCategory();
                category.add(category1);
                ordMap.put(category1, vo);
                ordTotalCount += vo.getOrdCateCount();
                ordTotalAmount = ordTotalAmount.add(new BigDecimal(vo.getOrdCatePrice()));
            }
        }

        if (inqList != null) {
            for (CateDetailVo vo : inqList) {
                String category1 = vo.getCategory();
                category.add(category1);
                inqMap.put(category1, vo);
                if (vo.getInqCateCount() > 0) {
                    inqTotalCount += vo.getInqCateCount();
                }
                if (vo.getInqCatePrice() > 0) {
                    inqTotalAmount = inqTotalAmount.add(new BigDecimal(vo.getInqCatePrice()));
                }
            }
        }

        List<CateDetailVo> cateDetailList = new ArrayList<>();
        for (String c : category) {
            CateDetailVo vo = new CateDetailVo();
            vo.setCategory(c);

            // 询单数据
            CateDetailVo inqVo = inqMap.get(c);
            if (inqVo != null) {
                int count = inqVo.getInqCateCount();
                double price = inqVo.getInqCatePrice();
                vo.setInqCateCount(count);
                vo.setInqProportion(RateUtil.intChainRate(count, inqTotalCount));
                vo.setInqCatePrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
                vo.setInqAmountProportion(RateUtil.doubleChainRate(price, inqTotalAmount.doubleValue()));
            } else {
                vo.setInqCateCount(0);
                vo.setInqProportion(0d);
                vo.setInqCatePrice(0d);
                vo.setInqAmountProportion(0d);
            }

            // 订单数据
            CateDetailVo ordVo = ordMap.get(c);
            if (ordVo != null) {
                int count = ordVo.getOrdCateCount();
                double price = ordVo.getOrdCatePrice();
                vo.setOrdCateCount(count);
                vo.setOrdProportion(RateUtil.intChainRate(count, ordTotalCount));
                vo.setOrdCatePrice(price);
                vo.setOrdAmountProportion(RateUtil.doubleChainRate(price, ordTotalAmount.doubleValue()));
            } else {
                vo.setOrdCateCount(0);
                vo.setOrdProportion(0d);
                vo.setOrdCatePrice(0d);
                vo.setOrdAmountProportion(0d);
            }
            cateDetailList.add(vo);
        }


        cateDetailList.sort((vo1, vo2) -> {
            int count1 = vo2.getInqCateCount() + vo2.getOrdCateCount();
            int count2 = vo1.getInqCateCount() + vo1.getOrdCateCount();

            return count1 - count2;
        });
        Map<String, Object> data = new HashMap<>();
        data.put("inqPie", inqPie);
        data.put("ordPie", ordPie);
        data.put("cateDetail", cateDetailList);

        result.setStatus(ResultStatusEnum.SUCCESS);
        result.setData(data);
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
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd HH:mm:ss");
        //截止时间
        Date endTime = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd HH:mm:ss");
        String areaName = (String) map.get("area");
        String countryName = (String) map.get("country");
        CustomerNumSummaryVO orderNumSummary = orderService.numSummary(startTime, endTime, areaName, countryName);
        //询单数量和金额
        Map<String, Object> inqMap = inquiryService.selectInqInfoByCondition(map);
        double inAmount = Double.parseDouble(inqMap.get("inqAmount").toString());
        int inCount = Integer.parseInt(inqMap.get("count").toString());
        //油气分类数量和金额
        Map<String, Object> oilMap = inquirySKUService.selectIsOilInfoByCondition(map);
        int oil = Integer.parseInt(oilMap.get("oil").toString());
        int notOil = Integer.parseInt(oilMap.get("notOil").toString());
        BigDecimal oilAmount = new BigDecimal(Double.parseDouble(oilMap.get("oilAmount").toString()));
        BigDecimal notOilAmount = new BigDecimal(Double.parseDouble(oilMap.get("notOilAmount").toString()));

        Map<String, Object> numData = new HashMap<>();

        String[] xTitleArr = new String[]{"询单数量", "油气数量", "非油气数量", "订单数量", "油气数量", "非油气数量",};
        Integer[] yValueArr = new Integer[]{inCount, oil,
                notOil, orderNumSummary.getTotal(), orderNumSummary.getOil(),
                orderNumSummary.getNonoil()};
        numData.put("x", xTitleArr);
        numData.put("y", yValueArr);

        Map<String, Object> amountData = new HashMap<>();

        String[] xTitleArr02 = new String[]{"询单总金额", "油气金额", "非油气金额", "订单总金额", "油气金额", "非油气金额",};
        BigDecimal[] yValueArr02 = new BigDecimal[]{
                new BigDecimal(inAmount).setScale(2, BigDecimal.ROUND_HALF_DOWN),
                oilAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN),
                notOilAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN),
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
    /**
     * 客户中心-询单详细分析：所有大区和大区中的所有事业部列表
     *
     * @return
     */
    @RequestMapping("/inqDetailAreaAndOrgList")
    @ResponseBody
    public Object inqDetailAreaAndOrgList(String areaName) {
        Result<Object> result = new Result<>();

        List<InquiryAreaVO> arayList = inquiryService.selectAllAreaAndOrgList();
        if (StringUtils.isNotBlank(areaName)) {
            List<InquiryAreaVO> ll = arayList.stream().filter(vo -> vo.getAreaName().equals(areaName))
                    .collect(Collectors.toList());
            if (ll.size() > 0) {
                result.setData(ll.get(0).getOrgs());
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
     * 客户中心-询单详细分析：所有事业部列表
     *
     * @return
     */
    @RequestMapping("/orgList")
    @ResponseBody
    public Object orgList() {
        Result<Object> result = new Result<>();
        List<String> orgList = inquiryService.selectOrgList();
        return result.setData(orgList);
    }

    /**
     * 客户中心-询单详细分析: 询单状态总览
     *
     * @param map 大区
     *            <p>
     *            城市
     * @return
     */
    @RequestMapping(value = "/inqDetailQuotePandent", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object inqDetailQuotePandent(@RequestBody Map<String, String> map) {

        Result<Object> result = new Result<>();

        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }

//       1 处理退回询单的数据
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        int rtnInqCount = inquiryService.selectInqRtnCountByTime(params);//已退回询单数
        int inqCount = inquiryService.inquiryCountByTime(startTime, endTime, null, 0, 0, null, null);//总询单数

        double rtnInqProportion = 0d;
        if (inqCount > 0) {
            rtnInqProportion = RateUtil.intChainRate(rtnInqCount, inqCount);
        }

        Map<String, Object> dataMap = inqRtnReasonService.selectCountGroupByRtnSeason(params);
        //退回次数和平均退回次数
        int rejectCount = getRtnCount(dataMap);//退回次数
        double avgRejectCount = 0d;//平均退回次数
        if (rtnInqCount > 0) {
            avgRejectCount = RateUtil.intChainRateTwo(rejectCount, rtnInqCount);
        }
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("rtnInqCount", rtnInqCount);
        returnData.put("rtnInqProportion", rtnInqProportion);
        returnData.put("rtnCount", rejectCount);
        returnData.put("avgRtnCount", avgRejectCount);
//        2 处理已完成询单数据
        String[] finishedQuote = {QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus()};
        String[] Quoted = {QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus()};
        int finishedInqCount = inquiryService.inquiryCountByTime(startTime, endTime, finishedQuote, 0, 0, null, null);//已完成询单数
        int quotedInqCount = inquiryService.inquiryCountByTime(startTime, endTime, Quoted, 0, 0, null, null);//已报价询单数
        int totalFinishCount = finishedInqCount + quotedInqCount;
        double finishProportion = 0d;
        if (inqCount > 0) {
            finishProportion = RateUtil.intChainRate(totalFinishCount, inqCount);
        }
        Map<String, Object> finishedData = new HashMap<>();
        finishedData.put("finishedInqCount", finishedInqCount);
        finishedData.put("quotedInqCount", quotedInqCount);
        finishedData.put("totalFinishCount", totalFinishCount);
        finishedData.put("finishProportion", finishProportion);
//        3. 处理报价中的数据
        int noQuoteInqCount = inquiryService.inquiryCountByTime(startTime, endTime, new String[]{QuotedStatusEnum.STATUS_QUOTED_NO.getQuotedStatus()},
                0, 0, null, null);//未报价询单数
        int quotingInqCount = inquiryService.inquiryCountByTime(startTime, endTime, new String[]{QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus()},
                0, 0, null, null);//报价中询单数
        int totalQuotingCount = noQuoteInqCount + quotingInqCount;
        double quotingProportion = 0d;
        if (inqCount > 0) {
            quotingProportion = RateUtil.intChainRate(totalQuotingCount, inqCount);
        }
        Map<String, Object> quotingData = new HashMap<>();
        quotingData.put("noQuoteInqCount", noQuoteInqCount);
        quotingData.put("quotingInqCount", quotingInqCount);
        quotingData.put("totalQuotingCount", totalQuotingCount);
        quotingData.put("quotingProportion", quotingProportion);

        Map<String, Object> data = new HashMap<>();
        data.put("returnData", returnData);
        data.put("finishedData", finishedData);
        data.put("quotingData", quotingData);
        return result.setData(data);

    }
    /**
     * 客户中心-询单详细分析: 询单详细分析饼图
     *
     * @param map 报价状态
     * @return
     */
    @RequestMapping(value = "/inqDetailPie", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object inqDetailPie(@RequestBody Map<String, Object> map) {

        Result<Object> result = new Result<>();
        InqDetailPievo inqDetailPievo = new InqDetailPievo();

        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime").toString(), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime").toString(), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime) || !map.containsKey("quoteStatus")) {
            return new Result<>(ResultStatusEnum.FAIL);
        }

        String quoteStatus = map.get("quoteStatus").toString();

        //1.获取报价状态中各状态数据  如 报价中 ： 未报价 、报价中
        String[] quotes = null;
        Integer[] quoteCounts = null;
        if (quoteStatus.equals(QuotedStatusEnum.STATUS_QUOTED_RETURNED.getQuotedStatus())) {//已退回
            Map<String, Object> rtnMap = this.inqRtnReasonService.selectCountGroupByRtnSeason(map);
            List<Map<String, Object>> rtnSeasonList = getRtnTable(rtnMap);
            List<String> reasons = new ArrayList<>();
            List<Integer> reasonCounts = new ArrayList<>();
            for (Map<String, Object> m : rtnSeasonList) {
                String reason = String.valueOf(m.get("reason"));
                Integer count = Integer.valueOf(m.get("total").toString());
                reasons.add(reason);
                reasonCounts.add(count);
            }
            inqDetailPievo.setRtnDescrList(reasons.toArray(new String[reasons.size()]));
            inqDetailPievo.setRtnDecrCountList(reasonCounts.toArray(new Integer[reasonCounts.size()]));

        } else if (quoteStatus.equals(QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus())) {//已完成
            quotes = new String[]{QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus(),
                    QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus()};
            map.put("quoteStatus", QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus());
            List<InquiryVo> finishList = inquiryService.selectListByTime(map);
            map.put("quoteStatus", QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus());
            List<InquiryVo> quotedList = inquiryService.selectListByTime(map);
            quoteCounts = new Integer[]{finishList.size(), quotedList.size()};
            inqDetailPievo.setFinishQuoteList(quotes);
            inqDetailPievo.setFinishQuoteCountList(quoteCounts);
        } else if (quoteStatus.equals(QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus())) {//报价中
            quotes = new String[]{QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus(),
                    QuotedStatusEnum.STATUS_QUOTED_NO.getQuotedStatus()};
            map.put("quoteStatus", QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus());
            List<InquiryVo> quotingList = inquiryService.selectListByTime(map);
            map.put("quoteStatus", QuotedStatusEnum.STATUS_QUOTED_NO.getQuotedStatus());
            List<InquiryVo> quotNoList = inquiryService.selectListByTime(map);
            quoteCounts = new Integer[]{quotingList.size(), quotNoList.size()};
            inqDetailPievo.setQuotingQuoteList(quotes);
            inqDetailPievo.setQuotingQuoteCountList(quoteCounts);

        }

        //2.根据状态获取各大区的询单数据
        List<Map<String, Object>> areaDataList = null;
        if (quoteStatus.equals(QuotedStatusEnum.STATUS_QUOTED_RETURNED.getQuotedStatus())) {//已退回
            areaDataList = this.inquiryService.findCountAndPriceByRangRollinTimeGroupArea(startTime, endTime, 1, quotes);
        } else {
            areaDataList = this.inquiryService.findCountAndPriceByRangRollinTimeGroupArea(startTime, endTime, 0, quotes);
        }
        List<String> areas = new ArrayList<>();//大区列表
        List<Integer> areaCounts = new ArrayList<>();//各大区数量列表
        List<Map<String, Object>> areaTableList = new ArrayList<>();//大区表格明细数据
        Integer totalCount = 0;//询单总数量
        if (areaDataList != null && areaDataList.size() > 0) {
            totalCount = areaDataList.stream().map(m -> {
                String area = String.valueOf(m.get("area"));
                int total = Integer.valueOf(m.get("total").toString());
                areas.add(area);
                areaCounts.add(total);
                return total;
            }).reduce(0, (a, b) -> a + b);
            if (areaCounts.size() > 0) {
                if (totalCount > 0) {
                    for (int i = 0; i < areaCounts.size(); i++) {
                        double areaPro = RateUtil.intChainRate(areaCounts.get(i), totalCount);
                        Map<String, Object> areadata = new HashMap<>();
                        areadata.put("areaProportion", areaPro);
                        areadata.put("areaCount", areaCounts.get(i));
                        areadata.put("area", areas.get(i));
                        areaTableList.add(areadata);
                    }
                }
            }
        }
        //3.根据状态获取获取各事业部的数据
        List<Map<String, Object>> orgDataList = null;
        if (quoteStatus.equals(QuotedStatusEnum.STATUS_QUOTED_RETURNED.getQuotedStatus())) {//已退回
            map.put("quotes", quotes);
            map.put("rtnCount", 1);
            orgDataList = this.inquiryService.findCountByRangRollinTimeGroupOrigation(map);
        } else {
            map.put("quotes", quotes);
            orgDataList = this.inquiryService.findCountByRangRollinTimeGroupOrigation(map);
        }

        List<String> orgs = new ArrayList<>();//事业部列表
        List<Integer> orgCounts = new ArrayList<>();//各事业部数量
        List<Map<String, Object>> orgTableList = new ArrayList<>();//事业部表格明细数据

        Map<String, Map<String, Object>> oMap = new HashMap<>();//用于存放整合成标准的事业部数据
        if (orgDataList != null && orgDataList.size() > 0) {
            orgDataList.stream().forEach(m -> {
                String org = String.valueOf(m.get("organization"));
                String standardOrg = inquiryService.getStandardOrg(org);
                Integer count = Integer.valueOf(m.get("total").toString());
                if (oMap.containsKey(standardOrg)) {
                    Map<String, Object> map1 = oMap.get(standardOrg);
                    map1.put("total", Integer.parseInt(map1.get("total").toString()) + count);
                } else {
                    m.put("organization", standardOrg);
                    oMap.put(standardOrg, m);
                }
            });
            if (oMap.size() > 0) {
                Set<String> strings = oMap.keySet();
                orgs = new ArrayList<>(strings);
                if (CollectionUtils.isNotEmpty(orgs)) {
                    for (String org1 : orgs) {
                        Map<String, Object> mm = oMap.get(org1);
                        Integer count3 = Integer.valueOf(mm.get("total").toString());
                        orgCounts.add(count3);
                    }
                }
            }

            if (orgCounts.size() > 0 && orgs.size() == orgCounts.size()) {
                if (totalCount > 0) {
                    for (int i = 0; i < orgCounts.size(); i++) {
                        double orgPro = RateUtil.intChainRate(orgCounts.get(i), totalCount);
                        Map<String, Object> orgdata = new HashMap<>();
                        orgdata.put("orgProportion", orgPro);
                        orgdata.put("orgCount", orgCounts.get(i));
                        orgdata.put("org", orgs.get(i));
                        orgTableList.add(orgdata);
                    }
                }
            }
        }
        //封装table 数据
        Map<String, Object> tableData = new HashMap<>();
        tableData.put("orgTable", orgTableList);
        tableData.put("areaTable", areaTableList);
        inqDetailPievo.setTableData(tableData);
        inqDetailPievo.setQuoteStatus(quoteStatus);
        if (!quoteStatus.equals(QuotedStatusEnum.STATUS_QUOTED_RETURNED.getQuotedStatus())) {
            inqDetailPievo.setQuotingQuoteList(quotes);
        }
        inqDetailPievo.setAreaList(areas.toArray(new String[areas.size()]));
        inqDetailPievo.setAreaCountList(areaCounts.toArray(new Integer[areaCounts.size()]));
        inqDetailPievo.setOrgList(orgs.toArray(new String[orgs.size()]));
        inqDetailPievo.setOrgCountList(orgCounts.toArray(new Integer[orgCounts.size()]));
        return result.setData(inqDetailPievo);

    }

    /**
     * 客户中心-询单详细分析: 退回原因饼图
     *
     * @param params 大区
     *            <p>
     *            事业部
     * @return
     */
    @RequestMapping(value = "/inqDetailRtnPie", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object inqDetailRtnPie(@RequestBody Map<String, Object> params) {
        String[] other=new String[]{"area"};
        params=ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR2,other);
        if (params == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        Map<String, Object> rtnMap = inqRtnReasonService.selectCountGroupByRtnSeason(params);
        List<Map<String, Object>> tableData = getRtnTable(rtnMap);
        List<String> reasons = tableData.stream().map(m -> m.get("reason").toString()).collect(Collectors.toList());
        List<String> totals = tableData.stream().map(m -> m.get("total").toString()).collect(Collectors.toList());
        Map<String, Object> pieData = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        pieData.put("reasons", reasons);
        pieData.put("counts", totals);
        data.put("tableData", tableData);
        data.put("pieData", pieData);
        return new Result<>(data);
    }

    //处理结果获取退回表格数据
    private  List<Map<String, Object>> getRtnTable(Map<String, Object> rtnMap) {
        //获取项目澄清数据 projectClear
        int projectClearInqCount = Integer.parseInt(rtnMap.get("projectClearInqCount").toString());
        int projectClearCount = Integer.parseInt(rtnMap.get("projectClearCount").toString());
        //获取非事业部业务范围 notOrg
        int notOrgInqCount = Integer.parseInt(rtnMap.get("notOrgInqCount").toString());
        int notOrgCount = Integer.parseInt(rtnMap.get("notOrgCount").toString());
        //获取无供应渠道 notSupply
        int notSupplyInqCount = Integer.parseInt(rtnMap.get("notSupplyInqCount").toString());
        int notSupplyCount = Integer.parseInt(rtnMap.get("notSupplyCount").toString());
        //获取系统问题 systemProblems
        int systemProblemsInqCount = Integer.parseInt(rtnMap.get("systemProblemsInqCount").toString());
        int systemProblemsCount = Integer.parseInt(rtnMap.get("systemProblemsCount").toString());
        //获取其他  other
        int otherInqCount = Integer.parseInt(rtnMap.get("otherInqCount").toString());
        int otherCount = Integer.parseInt(rtnMap.get("otherCount").toString());
        //总退回次数
        int totalRtnCount = projectClearCount + notOrgCount + notSupplyCount + systemProblemsCount + otherCount;
        List<Map<String, Object>> dataList = new ArrayList<>();

        //添加项目澄清数据
        Map<String, Object> mm = new HashMap<>();
        mm.put("reason", InqRtnSeasonEnum.PROJECT_CLEAR.getCh());
        mm.put("total", projectClearCount);
        mm.put("inqCount", projectClearInqCount);
        if (totalRtnCount > 0) {
            mm.put("totalProportion", RateUtil.intChainRate(projectClearCount, totalRtnCount));
        } else {
            mm.put("totalProportion", 0d);
        }
        dataList.add(mm);
        //非事业部业务范围
        Map<String, Object> mm2 = new HashMap<>();
        mm2.put("reason", InqRtnSeasonEnum.NOT_ORG.getCh());
        mm2.put("total", notOrgCount);
        mm2.put("inqCount", notOrgInqCount);
        if (totalRtnCount > 0) {
            mm2.put("totalProportion", RateUtil.intChainRate(notOrgCount, totalRtnCount));
        } else {
            mm2.put("totalProportion", 0d);
        }
        dataList.add(mm2);
        //无供应渠道
        Map<String, Object> mm3 = new HashMap<>();
        mm3.put("reason", InqRtnSeasonEnum.NOT_SUPPLY.getCh());
        mm3.put("total", notSupplyCount);
        mm3.put("inqCount", notSupplyInqCount);
        if (totalRtnCount > 0) {
            mm3.put("totalProportion", RateUtil.intChainRate(notSupplyCount, totalRtnCount));
        } else {
            mm3.put("totalProportion", 0d);
        }
        dataList.add(mm3);
        //系统问题
        Map<String, Object> mm4 = new HashMap<>();
        mm4.put("reason", InqRtnSeasonEnum.SYSTEM_PROBLEMS.getCh());
        mm4.put("total", systemProblemsCount);
        mm4.put("inqCount", systemProblemsInqCount);
        if (totalRtnCount > 0) {
            mm4.put("totalProportion", RateUtil.intChainRate(systemProblemsCount, totalRtnCount));
        } else {
            mm4.put("totalProportion", 0d);
        }
        dataList.add(mm4);
        //添其他
        Map<String, Object> mm5 = new HashMap<>();
        mm5.put("reason", InqRtnSeasonEnum.OTHER.getCh());
        mm5.put("total", otherCount);
        mm5.put("inqCount", otherInqCount);
        if (totalRtnCount > 0) {
            mm5.put("totalProportion", RateUtil.intChainRate(otherCount, totalRtnCount));
        } else {
            mm5.put("totalProportion", 0d);
        }
        dataList.add(mm5);
        return dataList;
    }
    //获取退回迅询单总次数
    private int getRtnCount(Map<String, Object> rtnMap) {
        //获取项目澄清数据 projectClear
        int projectClearCount = 0;
        int notOrgCount = 0;
        int notSupplyCount = 0;
        int systemProblemsCount = 0;
        int otherCount = 0;
        if (rtnMap.containsKey("projectClearCount")) {
            projectClearCount = Integer.parseInt(rtnMap.get("projectClearCount").toString());
        }
        if (rtnMap.containsKey("notOrgCount")) {
            notOrgCount = Integer.parseInt(rtnMap.get("notOrgCount").toString());
        }
        if (rtnMap.containsKey("notSupplyCount")) {
            notSupplyCount = Integer.parseInt(rtnMap.get("notSupplyCount").toString());
        }

        if (rtnMap.containsKey("systemProblemsCount")) {
            systemProblemsCount = Integer.parseInt(rtnMap.get("systemProblemsCount").toString());
        }
        if (rtnMap.containsKey("otherCount")) {
            otherCount = Integer.parseInt(rtnMap.get("otherCount").toString());
        }
        int totalRtnCount = projectClearCount + notOrgCount + notSupplyCount + systemProblemsCount + otherCount;
        return totalRtnCount;
    }

    /**
     * 客户中心-询单详细分析: 区域和事业部退回原因明细
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/inqDetailRtnDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object inqDetailRtnDetail(@RequestBody Map<String, String> map) {
        Result<Object> result = new Result<>();
        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        //1.获取各大区的数据
        List<Map<String, Object>> dataList = inqRtnReasonService.selectCountGroupByRtnSeasonAndArea(startTime, endTime);
        Map<String, Map<String, Object>> areaData = new HashMap<>();
        Integer totalCount = dataList.stream().map(m -> {
            Integer total = Integer.valueOf(m.get("total").toString());
            return total;
        }).reduce(0, (a, b) -> a + b);

        dataList.stream().forEach(m -> {
            if (totalCount != null && totalCount > 0) {
                if (!areaData.containsKey(m.get("area").toString())) {
                    Map<String, Object> mm = new HashMap<>();
                    mm.put("area", m.get("area").toString());
                    String reasonEn = this.getReasonEn(String.valueOf(m.get("reason")));
                    mm.put(reasonEn, Integer.valueOf(m.get("total").toString()));
                    areaData.put(String.valueOf(m.get("area")), mm);
                } else {
                    Map<String, Object> areaMap = areaData.get(m.get("area").toString());
                    String reasonEn = this.getReasonEn(String.valueOf(m.get("reason")));
                    if(areaMap.containsKey(reasonEn)){
                        Integer t = Integer.valueOf(areaMap.get(reasonEn).toString());
                        areaMap.put(reasonEn,t+ Integer.valueOf(m.get("total").toString()));
                    }else {
                        areaMap.put(reasonEn,Integer.valueOf(m.get("total").toString()));
                    }
                }
            }
        });
        List<Map<String, Object>> areas = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : areaData.entrySet()) {
            Map<String, Object> ll = entry.getValue();
            Map<String, Object> adata = addNoReasonData(ll);
            if (adata.containsKey(null)) {
                adata.remove(null);
            }
            areas.add(adata);
        }

        //2.获取各事业部数据
        List<Map<String, Object>> orgdataList = inqRtnReasonService.selectCountGroupByRtnSeasonAndOrg(startTime, endTime);
        Map<String, Map<String, Object>> orgData = new HashMap<>();
        Integer orgTotalCount = orgdataList.stream().map(m -> {
            Integer total = Integer.valueOf(m.get("total").toString());
            return total;
        }).reduce(0, (a, b) -> a + b);
        for (Map<String, Object> m : orgdataList) {
            if (orgTotalCount != null && orgTotalCount > 0) {
                String standardOrg = inquiryService.getStandardOrg(m.get("org").toString());
                String reasonEn = this.getReasonEn(String.valueOf(m.get("reason")));
                if (!orgData.containsKey(standardOrg)) {
                    Map<String, Object> mm = new HashMap<>();
                    mm.put("org", standardOrg);
                    mm.put(reasonEn, Integer.valueOf(m.get("total").toString()));
                    orgData.put(standardOrg, mm);
                } else {
                    Map<String, Object> orgMap = orgData.get(standardOrg);
                    if (StringUtil.isNotBlank(reasonEn)) {
                        if (orgMap.containsKey(reasonEn)) {
                            int total = Integer.parseInt(orgMap.get(reasonEn).toString());
                            orgMap.put(reasonEn, Integer.valueOf(m.get("total").toString()) + total);
                        } else {
                            orgMap.put(reasonEn, Integer.valueOf(m.get("total").toString()));
                        }
                    }
                }
            }
        }
        List<Map<String, Object>> org = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : orgData.entrySet()) {
            Map<String, Object> ll = entry.getValue();
            Map<String, Object> odata = addNoReasonData(ll);
            if (odata.containsKey(null)) {
                odata.remove(null);
            }
            org.add(odata);
        }

        //封装数据
        Map<String, Object> data = new HashMap<>();
        data.put("orgData", org);
        data.put("areaData", areas);
        return result.setData(data);

    }

    //获取退回原因的英文名
    private String getReasonEn(String reason) {

        if (reason.contains(InqRtnSeasonEnum.NOT_ORG.getCh())) {
            return InqRtnSeasonEnum.NOT_ORG.getEn();
        } else if (reason.contains(InqRtnSeasonEnum.NOT_SUPPLY.getCh())) {
            return InqRtnSeasonEnum.NOT_SUPPLY.getEn();
        } else if (reason.contains(InqRtnSeasonEnum.OTHER.getCh())) {
            return InqRtnSeasonEnum.OTHER.getEn();
        } else if (reason.contains(InqRtnSeasonEnum.PROJECT_CLEAR.getCh())) {
            return InqRtnSeasonEnum.PROJECT_CLEAR.getEn();
        } else if (reason.contains(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getCh())) {
            return InqRtnSeasonEnum.SYSTEM_PROBLEMS.getEn();
        } else {
            return InqRtnSeasonEnum.OTHER.getEn();
        }

    }

    //添加没有原因的数据
    private Map<String, Object> addNoReasonData(Map<String, Object> map) {
        if (MapUtils.isNotEmpty(map)) {
            int total = 0;
            if (!map.containsKey(InqRtnSeasonEnum.NOT_ORG.getEn())) {
                map.put(InqRtnSeasonEnum.NOT_ORG.getEn(), 0);
                map.put(InqRtnSeasonEnum.NOT_ORG.getEn() + "Proportion", 0d);
            } else {
                total += Integer.parseInt(map.get(InqRtnSeasonEnum.NOT_ORG.getEn()).toString());
            }
            if (!map.containsKey(InqRtnSeasonEnum.NOT_SUPPLY.getEn())) {
                map.put(InqRtnSeasonEnum.NOT_SUPPLY.getEn(), 0);
                map.put(InqRtnSeasonEnum.NOT_SUPPLY.getEn() + "Proportion", 0d);
            } else {
                total += Integer.parseInt(map.get(InqRtnSeasonEnum.NOT_SUPPLY.getEn()).toString());
            }
            if (!map.containsKey(InqRtnSeasonEnum.OTHER.getEn())) {
                map.put(InqRtnSeasonEnum.OTHER.getEn(), 0);
                map.put(InqRtnSeasonEnum.OTHER.getEn() + "Proportion", 0d);
            } else {
                total += Integer.parseInt(map.get(InqRtnSeasonEnum.OTHER.getEn()).toString());
            }
            if (!map.containsKey(InqRtnSeasonEnum.PROJECT_CLEAR.getEn())) {
                map.put(InqRtnSeasonEnum.PROJECT_CLEAR.getEn(), 0);
                map.put(InqRtnSeasonEnum.PROJECT_CLEAR.getEn() + "Proportion", 0d);
            } else {
                total += Integer.parseInt(map.get(InqRtnSeasonEnum.PROJECT_CLEAR.getEn()).toString());
            }
            if (!map.containsKey(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getEn())) {
                map.put(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getEn(), 0);
                map.put(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getEn() + "Proportion", 0d);
            } else {
                total += Integer.parseInt(map.get(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getEn()).toString());
            }

            if (!map.containsKey(InqRtnSeasonEnum.NOT_ORG.getEn() + "Proportion") && total > 0) {
                map.put(InqRtnSeasonEnum.NOT_ORG.getEn() + "Proportion", RateUtil.intChainRate(Integer.parseInt(map.get(InqRtnSeasonEnum.NOT_ORG.getEn()).toString()), total));
            } else {
                map.put(InqRtnSeasonEnum.NOT_ORG.getEn() + "Proportion", 0d);
            }
            if (!map.containsKey(InqRtnSeasonEnum.NOT_SUPPLY.getEn() + "Proportion") && total > 0) {
                map.put(InqRtnSeasonEnum.NOT_SUPPLY.getEn() + "Proportion", RateUtil.intChainRate(Integer.parseInt(map.get(InqRtnSeasonEnum.NOT_SUPPLY.getEn()).toString()), total));
            } else {
                map.put(InqRtnSeasonEnum.NOT_SUPPLY.getEn() + "Proportion", 0d);
            }
            if (!map.containsKey(InqRtnSeasonEnum.OTHER.getEn() + "Proportion") && total > 0) {
                map.put(InqRtnSeasonEnum.OTHER.getEn() + "Proportion", RateUtil.intChainRate(Integer.parseInt(map.get(InqRtnSeasonEnum.OTHER.getEn()).toString()), total));
            } else {
                map.put(InqRtnSeasonEnum.OTHER.getEn() + "Proportion", 0d);
            }
            if (!map.containsKey(InqRtnSeasonEnum.PROJECT_CLEAR.getEn() + "Proportion") && total > 0) {
                map.put(InqRtnSeasonEnum.PROJECT_CLEAR.getEn() + "Proportion", RateUtil.intChainRate(Integer.parseInt(map.get(InqRtnSeasonEnum.PROJECT_CLEAR.getEn()).toString()), total));
            } else {
                map.put(InqRtnSeasonEnum.PROJECT_CLEAR.getEn() + "Proportion", 0d);
            }
            if (!map.containsKey(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getEn() + "Proportion") && total > 0) {
                map.put(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getEn() + "Proportion", RateUtil.intChainRate(Integer.parseInt(map.get(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getEn()).toString()), total));
            } else {
                map.put(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getEn() + "Proportion", 0d);
            }
            return map;
        }
        return null;
    }

    /**
     * 客户中心-订单详细分析: 订单详情总览
     *
     * @param map 大区
     *            <p>
     *            城市
     * @return
     */
    @RequestMapping(value = "/ordDetailPandent", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object ordDetailPandent(@RequestBody Map<String, String> map) {
        Result<Object> result = new Result<>();
        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        int count = orderService.orderCountByTime(startTime, endTime, null, null, null);
        double amount = orderService.orderAmountByTime(startTime, endTime, null);
        List<Map<String, Object>> targetMaps = targetService.selectTargetGroupByOrg();
        double target = targetMaps.stream().map(m -> {
            double ammount = Double.parseDouble(m.get("ammount").toString());
            return ammount;
        }).reduce(0d, (a, b) -> a + b);//指标 万美元
        Double profitRate = orderService.selectProfitRate(startTime, endTime);
        //油气/非油气的复购率
        Double oilRePurRate = 0d;
        Double notOilRePurRate = 0d;
        int oilCount = 0, notOilCount = 0, reOilCount = 0, reNotOilCount = 0;
        List<Map<String, Object>> ReList = orderService.selectRePurchaseCustGroupByCustCategory(startTime, endTime);
        List<Map<String, Object>> totalList = orderService.selectCustCountGroupByCustCategory(startTime, endTime);
        if (ReList != null && ReList.size() > 0) {
            for (Map<String, Object> m : ReList) {
                if (m != null) {
                    String oil = m.get("oil").toString();
                    Integer custCount = Integer.valueOf(m.get("custCount").toString());
                    if ("油气".equals(oil) && custCount != null) {
                        reOilCount = custCount;
                    }
                    if ("非油气".equals(oil) && custCount != null) {
                        reNotOilCount = custCount;
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(totalList)) {
            for (Map<String, Object> m : totalList) {
                String oil = String.valueOf(m.get("oil"));
                Integer custCount = Integer.valueOf(m.get("custCount").toString());
                if ("油气".equals(oil) && custCount != null) {
                    oilCount = custCount;
                }
                if ("非油气".equals(oil) && custCount != null) {
                    notOilCount = custCount;
                }
            }

        }
        if (oilCount > 0) {
            oilRePurRate = RateUtil.intChainRateTwo(reOilCount, oilCount);
        }
        if (notOilCount > 0) {
            notOilRePurRate = RateUtil.intChainRateTwo(reNotOilCount, notOilCount);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("ordCount", count);
        data.put("ordAmmount", RateUtil.doubleChainRateTwo(amount, 10000d));//保留单位 万美元
        data.put("targetAmmount", RateUtil.doubleChainRateTwo(target, 1d));//万美元
        if (profitRate != null) {
            data.put("profitRate", RateUtil.doubleChainRate(profitRate, 1d));
        } else {
            data.put("profitRate", 0d);
        }
        data.put("oilRePurchaseRate", oilRePurRate);
        data.put("notOilRePurchaseRate", notOilRePurRate);
        return result.setData(data);
    }

    /**
     * 客户中心-订单详细分析: 区域和事业部明细
     *
     * @param map 大区
     *            <p>
     *            城市
     * @return
     */
    @RequestMapping(value = "/ordDetailAreaAndOrgDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object ordDetailAreaAndOrgDetail(@RequestBody Map<String, String> map) {
        Result<Object> result = new Result<>();
        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        //1.获取各事业部相关数据
        List<Map<String, Object>> orgTargets = targetService.selectTargetGroupByOrg();//各事业部年度指标
        List<Map<String, Object>> orgDataList = orderService.selectDataGroupByOrg(startTime, endTime);//各事业部数量和金额
        List<Map<String, Object>> reOrgList = orderService.selectRePurchaseCustGroupByOrg(startTime, endTime);//各事业部复购客户数
        List<Map<String, Object>> orgCustList = orderService.selectCustCountGroupByOrg(startTime, endTime);//各事业部客户数
        //处理标准的事业部表格数据
        Map<String, Map<String, Object>> orgCustMap = orgCustList.stream().collect(Collectors.toMap(vo -> String.valueOf(vo.get("org")), vo -> vo));
        reOrgList.stream().forEach(map1 -> {
            if (orgCustMap.containsKey(map1.get("org").toString())) {
                Map<String, Object> m2 = orgCustMap.get(String.valueOf(map1.get("org")));
                int custCount = Integer.parseInt(m2.get("custCount").toString());
                int reCount = Integer.parseInt(map1.get("custCount").toString());
                if (custCount > 0) {
                    map1.put("RePurProportion", RateUtil.intChainRate(reCount, custCount));
                } else {
                    map1.put("RePurProportion", 0d);
                }
            }
        });
        Integer orgTotalCount = null;
        Map<String, Map<String, Object>> oMap = reOrgList.stream().collect(Collectors.toMap(m -> String.valueOf(m.get("org")), vo -> vo));
        Map<String, Map<String, Object>> orMap = new HashMap<>();//用于存放标准的事业部数据
        if (CollectionUtils.isNotEmpty(orgDataList)) {
            orgTotalCount = orgDataList.stream().map(m -> {
                int orgCount = Integer.valueOf(m.get("ordCount").toString());
                return orgCount;
            }).reduce(0, (a, b) -> a + b);
            for (Map<String, Object> data : orgDataList) {
                String org = String.valueOf(data.get("org"));
                String standardOrg = inquiryService.getStandardOrg(org);
                int orgCount = Integer.parseInt(data.get("ordCount").toString());
                Double ordAmmount = Double.parseDouble(data.get("ordAmmount").toString());//金额保留两位小数
                if (orgTotalCount != null && orgTotalCount > 0) {
                    data.put("proportion", RateUtil.intChainRate(orgCount, orgTotalCount));
                } else {
                    data.put("proportion", 0d);
                }
                //事业部指标
                for (Map<String, Object> m : orgTargets) {
                    if (org.equals(m.get("org").toString())) {
                        data.put("target", RateUtil.doubleChainRate(Double.parseDouble(m.get("ammount").toString()), 1d));
                    }
                }
                if (!data.containsKey("target")) {
                    data.put("target", 0d);
                }
                //复购率
                if (oMap.containsKey(org)) {
                    Map<String, Object> m2 = oMap.get(org);
                    Double rr = Double.parseDouble(m2.get("RePurProportion").toString());
                    if (rr != null) {
                        data.put("RePurProportion", rr);
                    }
                } else {
                    data.put("RePurProportion", 0d);
                }

                //处理成标准表格数据
                double proportion = Double.parseDouble(data.get("proportion").toString());
                double target = Double.parseDouble(data.get("target").toString());
                double RePurProportion = Double.parseDouble(data.get("RePurProportion").toString());
                if (orMap.containsKey(standardOrg)) {
                    Map<String, Object> m1 = orMap.get(standardOrg);
                    int orgCount2 = Integer.parseInt(m1.get("ordCount").toString());
                    Double ordAmmount2 = Double.parseDouble(m1.get("ordAmmount").toString());
                    double proportion2 = Double.parseDouble(m1.get("proportion").toString());
                    double RePurProportion2 = Double.parseDouble(m1.get("RePurProportion").toString());
                    m1.put("ordCount", orgCount + orgCount2);
                    m1.put("ordAmmount", RateUtil.doubleChainRateTwo(ordAmmount + ordAmmount2, 1d));
                    m1.put("proportion", RateUtil.doubleChainRate(proportion + proportion2, 1d));
                    m1.put("RePurProportion", RateUtil.doubleChainRate(RePurProportion + RePurProportion2, 1d));
                } else {
                    data.put("org", standardOrg);
                    orMap.put(standardOrg, data);
                }
            }

        }
        //获取标准事业部饼图数据
        orgDataList = new ArrayList<>(orMap.values());
        List<String> orgs = new ArrayList<>(orMap.keySet());//事业部列表
        List<Integer> orgCounts = new ArrayList<>();//事业部订单数量列表
        if (CollectionUtils.isNotEmpty(orgs)) {
            for (String org : orgs) {
                int orgCount = Integer.parseInt(orMap.get(org).get("ordCount").toString());
                orgCounts.add(orgCount);
            }
        }
        //2.获取各地区相关数据
        List<Map<String, Object>> areaDataList = orderService.selectDataGroupByArea(startTime, endTime);//各地区数量和金额
        List<Map<String, Object>> areaTargets = targetService.selectTargetGroupByArea();//各地区年度指标
        List<Map<String, Object>> reAreaList = orderService.selectRePurchaseCustGroupByArea(startTime, endTime);//各地区复购客户数
        List<Map<String, Object>> areaCustList = orderService.selectCustCountGroupByArea(startTime, endTime);//各地区客户数
        //组装复购率的数据
        Map<String, Map<String, Object>> areaCustMap = areaCustList.stream().collect(Collectors.toMap(vo -> String.valueOf(vo.get("area")), vo -> vo));
        reAreaList.stream().forEach(map1 -> {
            if (areaCustMap.containsKey(map1.get("area").toString())) {
                Map<String, Object> m2 = areaCustMap.get(String.valueOf(map1.get("area")));
                int custCount = Integer.parseInt(m2.get("custCount").toString());
                int reCount = Integer.parseInt(map1.get("custCount").toString());
                if (custCount > 0) {
                    map1.put("RePurProportion", RateUtil.intChainRate(reCount, custCount));
                } else {
                    map1.put("RePurProportion", 0d);
                }
            }
        });

        //组装饼图的数据 和表格数据
        Map<String, Map<String, Object>> aMap = reAreaList.stream().collect(Collectors.toMap(m -> String.valueOf(m.get("area")), vo -> vo));
        List<String> areas = new ArrayList<>();
        List<Integer> areaCounts = new ArrayList<>();
        Integer areaTotalCount = null;
        if (CollectionUtils.isNotEmpty(areaDataList)) {
            areaTotalCount = areaDataList.stream().map(m -> {
                String area = String.valueOf(m.get("area"));
                int areaCount = Integer.parseInt(m.get("ordCount").toString());
                areas.add(area);
                areaCounts.add(areaCount);
                return areaCount;
            }).reduce(0, (a, b) -> a + b);
            for (Map<String, Object> data : areaDataList) {
                String area = data.get("area").toString();
                int areaCount = Integer.parseInt(data.get("ordCount").toString());
                Double ordAmmount = Double.parseDouble(data.get("ordAmmount").toString());//金额保留两位小数
                data.put("ordAmmount", RateUtil.doubleChainRateTwo(ordAmmount, 1d));
                if (areaTotalCount != null && areaTotalCount > 0) {
                    data.put("proportion", RateUtil.intChainRate(areaCount, areaTotalCount));
                } else {
                    data.put("proportion", 0d);
                }
                //地区指标
                for (Map<String, Object> m : areaTargets) {
                    if (area.equals(m.get("area").toString())) {
                        data.put("target", RateUtil.doubleChainRate(Double.parseDouble(m.get("ammount").toString()), 1d));
                    }
                }
                if (!data.containsKey("target")) {
                    data.put("target", 0d);
                }
                //复购率
                if (aMap.containsKey(area)) {
                    Map<String, Object> m2 = aMap.get(area);
                    Double rePurProportion = Double.parseDouble(m2.get("RePurProportion").toString());
                    data.put("RePurProportion", rePurProportion);
                } else {
                    data.put("RePurProportion", 0d);
                }
            }

        }
        //3封装数据
        Map<String, Object> areaPie = new HashMap<>();
        areaPie.put("areas", areas);
        areaPie.put("areaCounts", areaCounts);
        Map<String, Object> orgPie = new HashMap<>();
        orgPie.put("orgs", orgs);
        orgPie.put("orgCounts", orgCounts);
        Map<String, Object> data = new HashMap<>();
        data.put("areaTable", areaDataList);
        data.put("orgTable", orgDataList);
        data.put("areaPie", areaPie);
        data.put("orgPie", orgPie);
        return result.setData(data);
    }

    /**
     * 客户中心-订单详细分析: 品类明细
     *
     * @param map 大区
     *            <p>
     *            城市
     * @return
     */
    @RequestMapping(value = "/ordDetailItemClassDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object ordDetailItemClassDetail(@RequestBody Map<String, String> map) {
        Result<Object> result = new Result<>();
        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        List<Map<String, Object>> ordList = orderService.selecOrdDetailGroupByCategory(startTime, endTime);
        Integer totalOrdCount = ordList.stream().map(vo -> {
            int ordCount = Integer.parseInt(vo.get("ordCount").toString());
            Double profit = Double.parseDouble(vo.get("profit").toString());
            vo.put("profit", RateUtil.doubleChainRate(profit, 1d));
            return ordCount;
        }).reduce(0, (a, b) -> a + b);
        ordList.stream().forEach(map1 -> {
            int ordCount = Integer.parseInt(map1.get("ordCount").toString());
            if (totalOrdCount != null && totalOrdCount > 0) {
                map1.put("proportion", RateUtil.intChainRate(ordCount, totalOrdCount));
            }
        });
        return result.setData(ordList);
    }

    /**
     * 客户中心-订单详细分析: 复购客户明细
     *
     * @param map 大区
     *            <p>
     *            城市
     * @return
     */
    @RequestMapping(value = "/ordDetailRePurchaseDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object ordDetailRePurchaseDetail(@RequestBody Map<String, String> map) {
        Result<Object> result = new Result<>();
        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime) || !map.containsKey("area")) {
            return new Result<>(ResultStatusEnum.FAIL);
        }

        List<Map<String, Object>> maps = this.orderService.selectRePurchaseDetail(startTime, endTime, map.get("area"), map.get("isOil"));
        //buyCount custName
        Map<Integer, List<String>> dataMap = new HashMap<>();
        maps.stream().forEach(map1 -> {
            String custName = String.valueOf(map1.get("custName"));
            int buyCount = Integer.parseInt(map1.get("buyCount").toString());
            if (dataMap.containsKey(buyCount)) {
                dataMap.get(buyCount).add(custName);
            } else {
                List<String> names = new ArrayList<>();
                names.add(custName);
                dataMap.put(buyCount, names);
            }
        });
        //返回数据
        List<Map<String, Object>> data = new ArrayList<>();
        if (MapUtils.isNotEmpty(dataMap)) {
            for (Map.Entry<Integer, List<String>> entry : dataMap.entrySet()) {
                Map<String, Object> dd = new HashMap<>();
                dd.put("buyCount", entry.getKey());
                dd.put("custCount", entry.getValue().size());
                dd.put("custList", entry.getValue());
                data.add(dd);
            }
        }
        data.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return (Integer) o1.get("buyCount") - (Integer) o2.get("buyCount");
            }
        });
        return result.setData(data);
    }

}
