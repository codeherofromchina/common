package com.erui.boss.web.report;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquiryCount;
import com.erui.report.model.InquiryVo;
import com.erui.report.service.*;
import com.erui.report.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DateFormat;
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
    public Result<Object> inquiryPandect(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        // 获取需要环比的开始时间
        long differenTime = endDate.getTime() - startDate.getTime();
        Date rateStartDate = DateUtil.getBeforTime(startDate, differenTime);
        //当期询单数量和金额
        int count = inquiryService.inquiryCountByTime(startDate, endDate, null, 0, 0, "", "");
        double amount = inquiryService.inquiryAmountByTime(startDate, endDate, "", null, null);
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

        //平台产品统计
        Map<String, Object> platMap = new HashMap<>();
        List<Map<String, Object>> platList = inquirySKUService.selectCountGroupByIsPlat(startDate, endDate);
        List<Map<String, Object>> chainPlatList = inquirySKUService.selectCountGroupByIsPlat(rateStartDate, startDate);
        int platCount = 0, notPlatCount = 0, chainPlatCount = 0;
        double platProportion = 0.00, platChainRate = 0.00;
        if (platList != null && platList.size() > 0) {
            for (Map<String, Object> map : platList) {
                String plat = map.get("isPlat").toString();
                int skuCount = Integer.parseInt(map.get("skuCount").toString());
                if ("平台".equals(plat)) {
                    platCount = skuCount;
                } else {
                    notPlatCount += skuCount;
                }
            }
        }
        if (chainPlatList != null && chainPlatList.size() > 0) {
            for (Map<String, Object> map : chainPlatList) {
                String plat = map.get("isPlat").toString();
                if (plat.equals("平台")) {
                    chainPlatCount = Integer.parseInt(map.get("skuCount").toString());
                }
            }
        }
        if (platCount > 0) {
            platProportion = RateUtil.intChainRate(platCount, platCount + notPlatCount);
        }
        if (chainPlatCount > 0) {
            platChainRate = RateUtil.intChainRate(platCount - chainPlatCount, chainPlatCount);
        }
        platMap.put("platCount", platCount);
        platMap.put("notPlatCount", notPlatCount);
        platMap.put("platProportion", platProportion);
        platMap.put("planChainRate", platChainRate);
        // 询单Top 3 产品分类
        int skuCount = inquirySKUService.selectSKUCountByTime(startDate, endDate, null);
        List<Map<String, Object>> listTop3 = inquirySKUService.selectProTop3(startDate, endDate, null);
        if (listTop3 != null && listTop3.size() > 0) {
            listTop3.parallelStream().forEach(m -> {
                BigDecimal s = new BigDecimal(String.valueOf(m.get("proCount")));
                if (skuCount > 0) {
                    m.put("proProportionl", RateUtil.intChainRate(s.intValue(), skuCount));
                } else {
                    m.put("proProportionl", 0d);
                }
            });
        }
        //询价商品数
        Integer skuCountChain = inquirySKUService.selectSKUCountByTime(rateStartDate, startDate, null);
        Map<String, Object> goodsMap = new HashMap<>();
        if (skuCountChain == null) {
            skuCountChain = 0;
        }
        goodsMap.put("goodsCount", skuCount);
        goodsMap.put("goodsChainAdd", skuCount - skuCountChain);
        Map<String, Object> datas = new HashMap<>();
        datas.put("inquiry", inquiryMap);
        datas.put("proTop3", listTop3);
        datas.put("goodsMap", goodsMap);
        datas.put("platMap", platMap);
        return new Result<>(ResultStatusEnum.SUCCESS).setData(datas);
    }

    /*
     * 报价总览
     * */
    @ResponseBody
    @RequestMapping(value = "/quotePandect", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> quotePandect(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        // 获取需要环比的开始时间
        long differenTime = endDate.getTime() - startDate.getTime();
        Date rateStartDate = DateUtil.getBeforTime(startDate, differenTime);
        String[] quotes = {QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus(), QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus()};
        //当期报价询单数量和金额
        int count = inquiryService.inquiryCountByTime(startDate, endDate, quotes, 0, 0, "", "");
        double amount = inquiryService.inquiryAmountByTime(startDate, endDate, "", null, quotes);
        // 上期报价询单数量
        int chainCount = inquiryService.inquiryCountByTime(rateStartDate, startDate, quotes, 0, 0, "", "");
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
        Map<String,Object> map=new HashMap<>();
        map.put("startTime",params.get("startTime"));
        map.put("endTime",params.get("endTime"));
        List<InquiryVo> inquiryCounts = inquiryService.selectListByTime(map);
        map.put("endTime",params.get("startTime"));
        map.put("startTime",DateUtil.formatDateToString(rateStartDate,DateUtil.FULL_FORMAT_STR2));
        List<InquiryVo> chainCounts = inquiryService.selectListByTime(map);
        List<IsOilVo> oilList = null;
        List<IsOilVo> oilChainList = null;
        List<String> nums = null;
        List<String> chainNums = null;
        if (inquiryCounts != null && inquiryCounts.size() > 0) {
            nums = inquiryCounts.parallelStream().map(vo -> vo.getQuotationNum()).collect(Collectors.toList());//获取时间内报价的询单号
            chainNums = chainCounts.parallelStream().map(vo -> vo.getQuotationNum()).collect(Collectors.toList());//获取时间内报价的询单号
            if (nums != null && nums.size() > 0) {
                oilList = inquirySKUService.selectCountGroupByIsOil(startDate, endDate, nums);
                oilChainList = inquirySKUService.selectCountGroupByIsOil(rateStartDate, startDate, chainNums);
            }
        }
        Map<String, Object> proIsOilMap = new HashMap<>();//油气产品分析
        int oil = 0;
        int oilChain = 0;
        int notOil = 0;
        double oiProportionl = 0.00;
        double oilChainRate = 0.00;
        if (oilList != null && oilList.size() > 0) {
            for (IsOilVo vo : oilList) {
                if (vo.getIsOil().equals("油气")) {
                    oil = vo.getSkuCount();
                } else if (vo.getIsOil().equals("非油气")) {
                    notOil = vo.getSkuCount();
                }
            }
        }
        if (oilChainList != null && oilChainList.size() > 0) {
            for (IsOilVo vo : oilChainList) {
                if (vo.getIsOil().equals("油气")) {
                    oilChain = vo.getSkuCount();
                }
            }
        }
        if (oil > 0) {
            oiProportionl = RateUtil.intChainRate(oil, (oil + notOil));
        }
        if (oilChain > 0) {
            oilChainRate = RateUtil.intChainRate(oil - oilChain, oilChain);
        }
        proIsOilMap.put("oil", oil);
        proIsOilMap.put("notOil", notOil);
        proIsOilMap.put("oiProportionl", oiProportionl);
        proIsOilMap.put("chainRate", oilChainRate);
        // 询单Top 3 产品分类
        int skuCount = inquirySKUService.selectSKUCountByTime(startDate, endDate, nums);
        List<Map<String, Object>> listTop3 = null;
        if (nums != null && nums.size() > 0) {
            listTop3 = inquirySKUService.selectProTop3(startDate, endDate, nums);
        }
        if (listTop3 != null && listTop3.size() > 0) {
            listTop3.parallelStream().forEach(m -> {
                BigDecimal s = new BigDecimal(String.valueOf(m.get("proCount")));
                if (skuCount > 0) {
                    m.put("proProportionl", RateUtil.intChainRate(s.intValue(), skuCount));
                } else {
                    m.put("proProportionl", 0d);
                }
            });
        }
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
    public Object inquiryDetail(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
//        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]
        int quotedCount = inquiryService.inquiryCountByTime(startDate, endDate,
                new String[]{QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus(), QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus()},
                0, 0, "", "");//已完成询单数量
        int quotingCount = inquiryService.inquiryCountByTime(startDate, endDate,
                new String[]{QuotedStatusEnum.STATUS_QUOTED_NO.getQuotedStatus(), QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus()},
                0, 0, "", "");//报价中询单数量
        int cancelCount = inquiryService.inquiryCountByTime(startDate, endDate,
                new String[]{QuotedStatusEnum.STATUS_QUOTED_CANCEL.getQuotedStatus()},
                0, 0, "", "");//询单取消数量
        int rtnCount = inquiryService.selectInqRtnCountByTime(startDate, endDate);//询单退回数量
        int totalCount = quotedCount + quotingCount + cancelCount;
        Double quotedInquiryRate = null;
        Double quotingInquiryRate = null;
        Double cancelInquiryRate = null;
        if (totalCount > 0) {
            quotedInquiryRate = RateUtil.intChainRate(quotedCount, totalCount);
            quotingInquiryRate = RateUtil.intChainRate(quotingCount, totalCount);
            cancelInquiryRate = RateUtil.intChainRate(cancelCount, totalCount);
        }

        //获取询单退回原因分析数据
        List<Map<String, Object>> dataList = inqRtnReasonService.selectCountGroupByRtnSeason(startDate, endDate, null, null);
        List<Map<String, Object>> tableData = getRtnTable(dataList);

        //获取退回询单汇总数据  {退回询单总数，退回总次数，平均退回次数，退回询单总占比}
        int totalRtnCount = 0;
        Double avgRtnCount = 0d;
        Double rtnInqProportion = 0d;
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (Map<String, Object> m : dataList) {
                if (m.get("total") != null) {
                    totalRtnCount += Integer.parseInt(m.get("total").toString());//退回次数
                }
            }
        }
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
        //组装数据
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> inquiryDetailMap = new HashMap<>();
        inquiryDetailMap.put("quotedCount", quotedCount);
        inquiryDetailMap.put("quotingCount", quotingCount);
        inquiryDetailMap.put("cancelCount", cancelCount);
        inquiryDetailMap.put("quotedInquiryRate", quotedInquiryRate);
        inquiryDetailMap.put("quotingInquiryRate", quotingInquiryRate);
        inquiryDetailMap.put("cancelInquiryRate", cancelInquiryRate);
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
    public Object inquiryTimeDistrbute(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
       Map<String,Object> data= this.inquiryService.selectQuoteTimeSummaryData(params);
        return new Result<>(data);
    }

    // 事业部明细
    @ResponseBody
    @RequestMapping(value = "/busUnitDetail", method = RequestMethod.POST)
    public Object busUnitDetail(@RequestBody(required = true) Map<String, String> params) {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
//        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]x`

        //1查询给定时间的事业部询单数量
        List<Map<String, Object>> quiryList = inquiryService.findCountByRangRollinTimeGroupOrigation(startDate, endDate,0, null);
        // 查询给定时间段的事业部订单数量和金额
        List<Map<String, Object>> derList = orderService.findCountAndAmountByRangProjectStartGroupOrigation(startDate, endDate);
        //查询给定时间段的事业部平均报价时间
        List<Map<String, Object>> NeedTimeList = inquiryService.findAvgNeedTimeByRollinTimeGroupOrigation(startDate, endDate);

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
        List<Map<String, Object>> inquiryList = inquiryService.findCountAndPriceByRangRollinTimeGroupArea(startDate, endDate,0,null);
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
    public Object catesDetail(@RequestBody Map<String, String> map) throws Exception {
        Result<Object> result = new Result<>();
        //开始时间
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.PARAM_TYPE_ERROR);
        }

        int inqTotalCount = 0;
        int ordTotalCount = 0;
        BigDecimal inqTotalAmount = BigDecimal.ZERO;
        BigDecimal ordTotalAmount = BigDecimal.ZERO;

        List<CateDetailVo> inqList = inquirySKUService.selectSKUDetailByCategory(startTime, endTime);
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
                return o2.getInqCateCount()-o1.getInqCateCount();
            }
        });
        ordList.sort(new Comparator<CateDetailVo>() {
            @Override
            public int compare(CateDetailVo o1, CateDetailVo o2) {
                return o2.getOrdCateCount()-o1.getOrdCateCount();
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
//        CustomerNumSummaryVO inquiryNumSummary = inquiryService.numSummary(startTime, endTime, areaName, countryName);
        //询单数量和金额
        double inAmount = inquiryService.inquiryAmountByTime(startTime, endTime, areaName, countryName, null);
        List<InquiryVo> inList = inquiryService.selectListByTime(map);
        //定义询单数量
        int inCount = 0;
        List<String> nums = new ArrayList<>();
        if (inList != null && inList.size() > 0) {
            inCount = inList.size();
            for (InquiryVo inq : inList) {
                nums.add(inq.getQuotationNum());
            }
        }
        //油气分类数量和金额
        int oil = 0;
        int notOil = 0;
        BigDecimal oilAmount = new BigDecimal(0);
        BigDecimal notOilAmount = new BigDecimal(0);
        List<IsOilVo> isOilList = inquirySKUService.selectCountGroupByIsOil(startTime, endTime, nums);
        if (isOilList != null && isOilList.size() > 0) {
            for (IsOilVo vo : isOilList) {
                if (vo.getIsOil().equals("油气")) {
                    oil = vo.getSkuCount();
                    oilAmount = vo.getSkuAmount();
                } else if (vo.getIsOil().equals("非油气")) {
                    notOil = vo.getSkuCount();
                    notOilAmount = vo.getSkuAmount();
                }
            }
        }
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
    public Object inqDetailQuotePandent(@RequestBody Map<String, String> map){

        Result<Object> result = new Result<>();

        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }

//       1 处理退回询单的数据

        int rtnInqCount = inquiryService.selectInqRtnCountByTime(startTime, endTime);//已退回询单数
        int inqCount = inquiryService.inquiryCountByTime(startTime, endTime, null, 0, 0, null, null);//总询单数

        double rtnInqProportion = 0d;
        if (inqCount > 0) {
            rtnInqProportion = RateUtil.intChainRate(rtnInqCount, inqCount);
        }
        //退回次数和平均退回次数
        int rejectCount = 0;//退回次数
        double avgRejectCount = 0d;//平均退回次数

        List<Map<String, Object>> dataList = inqRtnReasonService.selectCountGroupByRtnSeason(startTime, endTime, null, null);
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (Map<String, Object> m : dataList) {
                if (m.get("total") != null) {
                    rejectCount += Integer.parseInt(m.get("total").toString());//退回次数
                }
            }
        }
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
    public Object inqDetailPie(@RequestBody Map<String, Object> map){

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
            List<Map<String, Object>> rtnSeasonList = this.inqRtnReasonService.selectCountGroupByRtnSeason(startTime, endTime, null, null);
            if (rtnSeasonList != null && rtnSeasonList.size() > 0) {
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
            }
        } else if (quoteStatus.equals(QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus())) {//已完成
            quotes = new String[]{QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus(),
                    QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus()};
            map.put("quoteStatus", QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus());
            List<InquiryVo> finishList = inquiryService.selectListByTime(map);
            map.put("quoteStatus",QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus());
            List<InquiryVo> quotedList = inquiryService.selectListByTime(map);
            quoteCounts = new Integer[]{finishList.size(), quotedList.size()};
            inqDetailPievo.setFinishQuoteList(quotes);
            inqDetailPievo.setFinishQuoteCountList(quoteCounts);
        } else if (quoteStatus.equals(QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus())) {//报价中
            quotes = new String[]{QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus(),
                    QuotedStatusEnum.STATUS_QUOTED_NO.getQuotedStatus()};
            map.put("quoteStatus",QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus());
            List<InquiryVo> quotingList = inquiryService.selectListByTime(map);
            map.put("quoteStatus",QuotedStatusEnum.STATUS_QUOTED_NO.getQuotedStatus());
            List<InquiryVo> quotNoList = inquiryService.selectListByTime(map);
            quoteCounts = new Integer[]{quotingList.size(), quotNoList.size()};
            inqDetailPievo.setQuotingQuoteList(quotes);
            inqDetailPievo.setQuotingQuoteCountList(quoteCounts);

        }

        //2.根据状态获取各大区的询单数据
        List<Map<String, Object>> areaDataList =null;
        if (quoteStatus.equals(QuotedStatusEnum.STATUS_QUOTED_RETURNED.getQuotedStatus())) {//已退回
            areaDataList= this.inquiryService.findCountAndPriceByRangRollinTimeGroupArea(startTime, endTime, 1, quotes);
        }else{
            areaDataList= this.inquiryService.findCountAndPriceByRangRollinTimeGroupArea(startTime, endTime, 0, quotes);
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
            orgDataList= this.inquiryService.findCountByRangRollinTimeGroupOrigation(startTime, endTime,1, quotes);
        }else {
            orgDataList= this.inquiryService.findCountByRangRollinTimeGroupOrigation(startTime, endTime, 0,quotes);
        }

        List<String> orgs = new ArrayList<>();//事业部列表
        List<Integer> orgCounts = new ArrayList<>();//各事业部数量
        List<Map<String, Object>> orgTableList = new ArrayList<>();//事业部表格明细数据

        Map<String, Map<String, Object>> oMap = new HashMap<>();//用于存放整合成标准的事业部数据
        if (orgDataList != null && orgDataList.size() > 0) {
            orgDataList.stream().forEach(m->{
                String org = String.valueOf(m.get("organization"));
                String standardOrg = inquiryService.getStandardOrg(org);
                Integer count = Integer.valueOf(m.get("total").toString());
                if(oMap.containsKey(standardOrg)){
                    Map<String, Object> map1 = oMap.get(standardOrg);
                    map1.put("total",Integer.parseInt(map1.get("total").toString())+count);
                }else {
                    m.put("organization", standardOrg);
                    oMap.put(standardOrg,m);
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
     * @param map 大区
     *            <p>
     *            事业部
     * @return
     */
    @RequestMapping(value = "/inqDetailRtnPie", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object inqDetailRtnPie(@RequestBody Map<String, String> map) {
        Result<Object> result = new Result<>();
        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        if (startTime == null || endTime == null || startTime.after(endTime) || !map.containsKey("area")) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        List<Map<String, Object>> dataList = inqRtnReasonService.selectCountGroupByRtnSeason(startTime, endTime, map.get("area"), map.get("org"));
        List<Map<String, Object>> tableData = getRtnTable(dataList);
        List<String> reasons = dataList.stream().map(m -> m.get("reason").toString()).collect(Collectors.toList());
        List<String> totals = dataList.stream().map(m -> m.get("total").toString()).collect(Collectors.toList());
        Map<String, Object> pieData = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        pieData.put("reasons", reasons);
        pieData.put("counts", totals);
        data.put("tableData", tableData);
        data.put("pieData", pieData);
        return result.setData(data);
    }

    //处理结果获取退回表格数据
    public List<Map<String, Object>> getRtnTable(List<Map<String, Object>> dataList) {
        Integer totalCount = dataList.stream().map(m -> {
            Integer total = Integer.valueOf(m.get("total").toString());
            return total;
        }).reduce(0, (a, b) -> a + b);

        dataList.stream().forEach(m -> {
            if (totalCount != null && totalCount > 0) {
                m.put("totalProportion", RateUtil.intChainRate(Integer.valueOf(m.get("total").toString()), totalCount));
            }
        });
        //原因排序
        List<Map<String, Object>> tableData = reasonDataListSort(dataList);
        return tableData;
    }

    //给退回原因顺序排序
    private List<Map<String, Object>> reasonDataListSort(List<Map<String, Object>> dataList) {
        Map<String, Map<String, Object>> dataMap = dataList.parallelStream().collect(Collectors.toMap(v -> String.valueOf(v.get("reason")), v -> v));
        List<Map<String, Object>> tableData = new ArrayList<>();
        if (dataMap.containsKey(InqRtnSeasonEnum.PROJECT_CLEAR.getCh())) {
            Map<String, Object> m1 = dataMap.get(InqRtnSeasonEnum.PROJECT_CLEAR.getCh());
            tableData.add(m1);
        } else {
            Map<String, Object> mm = new HashMap<>();
            mm.put("reason", InqRtnSeasonEnum.PROJECT_CLEAR.getCh());
            mm.put("total", 0);
            mm.put("inqCount", 0);
            mm.put("totalProportion", 0d);
            tableData.add(mm);
        }
        if (dataMap.containsKey(InqRtnSeasonEnum.NOT_ORG.getCh())) {
            Map<String, Object> m1 = dataMap.get(InqRtnSeasonEnum.NOT_ORG.getCh());
            tableData.add(m1);
        } else {
            Map<String, Object> mm = new HashMap<>();
            mm.put("reason", InqRtnSeasonEnum.NOT_ORG.getCh());
            mm.put("total", 0);
            mm.put("inqCount", 0);
            mm.put("totalProportion", 0d);
            tableData.add(mm);
        }
        if (dataMap.containsKey(InqRtnSeasonEnum.NOT_SUPPLY.getCh())) {
            Map<String, Object> m1 = dataMap.get(InqRtnSeasonEnum.NOT_SUPPLY.getCh());
            tableData.add(m1);
        } else {
            Map<String, Object> mm = new HashMap<>();
            mm.put("reason", InqRtnSeasonEnum.NOT_SUPPLY.getCh());
            mm.put("total", 0);
            mm.put("inqCount", 0);
            mm.put("totalProportion", 0d);
            tableData.add(mm);
        }
        if (dataMap.containsKey(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getCh())) {
            Map<String, Object> m1 = dataMap.get(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getCh());
            tableData.add(m1);
        } else {
            Map<String, Object> mm = new HashMap<>();
            mm.put("reason", InqRtnSeasonEnum.SYSTEM_PROBLEMS.getCh());
            mm.put("total", 0);
            mm.put("inqCount", 0);
            mm.put("totalProportion", 0d);
            tableData.add(mm);
        }
        if (dataMap.containsKey(InqRtnSeasonEnum.OTHER.getCh())) {
            Map<String, Object> m1 = dataMap.get(InqRtnSeasonEnum.OTHER.getCh());
            tableData.add(m1);
        } else {
            Map<String, Object> mm = new HashMap<>();
            mm.put("reason", InqRtnSeasonEnum.OTHER.getCh());
            mm.put("total", 0);
            mm.put("inqCount", 0);
            mm.put("totalProportion", 0d);
            tableData.add(mm);
        }
        return tableData;
    }

    /**
     * 客户中心-询单详细分析: 区域和事业部退回原因明细
     *
     * @param map
     * @return
     */
        @RequestMapping(value = "/inqDetailRtnDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object inqDetailRtnDetail(@RequestBody Map<String, String> map){
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
                    areaMap.put(reasonEn, Integer.valueOf(m.get("total").toString()));
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
                        if(orgMap.containsKey(reasonEn)) {
                            int total = Integer.parseInt(orgMap.get(reasonEn).toString());
                            orgMap.put(reasonEn, Integer.valueOf(m.get("total").toString()) + total);
                        }else {
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

        if (reason.equals(InqRtnSeasonEnum.NOT_ORG.getCh())) {
            return InqRtnSeasonEnum.NOT_ORG.getEn();
        } else if (reason.equals(InqRtnSeasonEnum.NOT_SUPPLY.getCh())) {
            return InqRtnSeasonEnum.NOT_SUPPLY.getEn();
        } else if (reason.equals(InqRtnSeasonEnum.OTHER.getCh())) {
            return InqRtnSeasonEnum.OTHER.getEn();
        } else if (reason.equals(InqRtnSeasonEnum.PROJECT_CLEAR.getCh())) {
            return InqRtnSeasonEnum.PROJECT_CLEAR.getEn();
        } else if (reason.equals(InqRtnSeasonEnum.SYSTEM_PROBLEMS.getCh())) {
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
    public Object ordDetailPandent(@RequestBody Map<String, String> map)  {
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
    public Object ordDetailAreaAndOrgDetail(@RequestBody Map<String, String> map)  {
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
                    m1.put("ordAmmount", RateUtil.doubleChainRateTwo(ordAmmount + ordAmmount2,1d));
                    m1.put("proportion", RateUtil.doubleChainRate(proportion + proportion2,1d));
                    m1.put("RePurProportion",RateUtil.doubleChainRate( RePurProportion + RePurProportion2,1d));
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
    public Object ordDetailRePurchaseDetail(@RequestBody Map<String, String> map)  {
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
