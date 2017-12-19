package com.erui.boss.web.report;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.CateDetailVo;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.InquirySKUService;
import com.erui.report.service.OrderCountService;
import com.erui.report.util.CustomerNumSummaryVO;
import com.erui.report.util.InquiryAreaVO;
import com.erui.report.util.IsOilVo;
import com.erui.report.util.QuotedStatusEnum;
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
    @Autowired
    private InquirySKUService inquirySKUService;
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
//        CustomerNumSummaryVO custSummaryVO = inquiryService.selectNumSummaryByExample(startDate, endDate);
//        CustomerNumSummaryVO custSummaryVO2 = inquiryService.selectNumSummaryByExample(rateStartDate, startDate);
       List<IsOilVo>   oilList= inquirySKUService.selectCountGroupByIsOil(startDate,endDate);
       List<IsOilVo>   oilChainList= inquirySKUService.selectCountGroupByIsOil(rateStartDate,startDate);
       Map<String, Object> proIsOilMap = new HashMap<>();//油气产品分析
//        if (custSummaryVO != null && custSummaryVO.getTotal() > 0) {
//            proIsOilMap.put("oil", custSummaryVO.getOil());
//            proIsOilMap.put("notOil", custSummaryVO.getNonoil());
//            proIsOilMap.put("oiProportionl", RateUtil.intChainRate(custSummaryVO.getOil(), custSummaryVO.getTotal()));
//            proIsOilMap.put("chainRate", RateUtil.intChainRate(custSummaryVO.getOil() - custSummaryVO2.getOil(), custSummaryVO2.getOil()));
//        } else {
//            proIsOilMap.put("oil", 0);
//            proIsOilMap.put("notOil", 0);
//            proIsOilMap.put("oiProportionl", 0.00);
//            proIsOilMap.put("chainRate", 0.00);
//        }
        int  oil=0;
        int  oilChain=0;
        int  notOil=0;
        double oiProportionl=0.00;
        double oilChainRate=0.00;
        if(oilList!=null&& oilList.size()>0){
            for (IsOilVo vo:oilList) {
                if(vo.getIsOil().equals("油气")){
                    if(vo.getSkuCount()>0){
                        oil=vo.getSkuCount();
                    }
               }else if(vo.getIsOil().equals("非油气")){
                    if(vo.getSkuCount()>0){
                        notOil=vo.getSkuCount();
                    }
                }
                    }
        }
        if(oilChainList!=null&& oilChainList.size()>0){
            for (IsOilVo vo:oilChainList) {
                if(vo.getIsOil().equals("油气")){
                    if(vo.getSkuCount()>0){
                        oilChain=vo.getSkuCount();
                    }
               }
                    }
        }
        if((oil+notOil)>0){
            oiProportionl=RateUtil.intChainRate(oil,(oil+notOil));
        }
        if(oilChainRate>0){
            oiProportionl=RateUtil.intChainRate(oil-oilChain,oilChain);
        }
        proIsOilMap.put("oil", oil);
            proIsOilMap.put("notOil", notOil);
            proIsOilMap.put("oiProportionl", oiProportionl);
            proIsOilMap.put("chainRate", oilChainRate);
        // 询单Top 3 产品分类
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startDate);
        map.put("endTime", endDate);
//        List<Map<String, Object>> listTop3 = inquiryService.selectProTop3(map);
        List<Map<String, Object>> listTop3 = inquirySKUService.selectProTop3(map);
        listTop3.parallelStream().forEach(m -> {
            BigDecimal s = new BigDecimal(String.valueOf(m.get("proCount")));
            m.put("proProportionl", RateUtil.intChainRate(s.intValue(), count));
        });

        //询价商品数
        Integer skuCount =inquirySKUService.selectSKUCountByTime(startDate,endDate);
        Integer skuCountChain =inquirySKUService.selectSKUCountByTime(rateStartDate,startDate);
        Map<String, Object> goodsMap = new HashMap<>();
        goodsMap.put("goodsCount",skuCount);
        if(skuCount!=null&&skuCountChain!=null){
            goodsMap.put("goodsChainAdd",skuCount-skuCountChain);
        }else if(skuCountChain==null&&skuCount!=null){
            goodsMap.put("goodsChainAdd",skuCount);
        }else if(skuCount==null&&skuCountChain!=null){
            goodsMap.put("goodsChainAdd",-skuCountChain);
        }else {
            goodsMap.put("goodsChainAdd",0.00);
        }
        Map<String, Object> datas = new HashMap<>();
        datas.put("inquiry", inquiryMap);
        datas.put("isOil", proIsOilMap);
        datas.put("proTop3", listTop3);
        datas.put("goodsMap", goodsMap);
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


        int quotedCount = inquiryService.inquiryCountByTime(startDate, endDate,
                new String[]{QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus(), QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus()},
                0, 0, "", "");//已完成询单数量
        int quotingCount = inquiryService.inquiryCountByTime(startDate, endDate,
                new String[]{QuotedStatusEnum.STATUS_QUOTED_QUOTEING.getQuotedStatus(),QuotedStatusEnum.STATUS_QUOTED_NO.getQuotedStatus(),QuotedStatusEnum.STATUS_QUOTED_ING.getQuotedStatus()},
                0, 0, "", "");//报价中询单数量
        int cancelCount = inquiryService.inquiryCountByTime(startDate, endDate,
                new String[]{QuotedStatusEnum.STATUS_QUOTED_CANCEL.getQuotedStatus()},
                0, 0, "", "");//询单取消数量
        int totalCount = quotedCount + quotingCount + cancelCount;
        Double quotedInquiryRate = null;
        Double quotingInquiryRate = null;
        Double cancelInquiryRate = null;
        if (totalCount > 0) {
            quotedInquiryRate = RateUtil.intChainRate(quotedCount, totalCount);
            quotingInquiryRate = RateUtil.intChainRate(quotingCount, totalCount);
            cancelInquiryRate = RateUtil.intChainRate(cancelCount, totalCount);
        }
        HashMap<String, Object> inquiryDetailMap = new HashMap<>();
        inquiryDetailMap.put("quotedCount", quotedCount);
        inquiryDetailMap.put("quotingCount", quotingCount);
        inquiryDetailMap.put("cancelCount", cancelCount);
        inquiryDetailMap.put("quotedInquiryRate", quotedInquiryRate);
        inquiryDetailMap.put("quotingInquiryRate", quotingInquiryRate);
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

        // 定义已完成询单的所有状态数组
        String[] statusArr = new String[]{QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus(), QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus()};
        int totalCount = inquiryService.inquiryCountByTime(startDate, endDate,statusArr, 0, 0, "", "");
        int count1 = inquiryService.inquiryCountByTime(startDate, endDate, statusArr, 0, 4, "", "");
        int count2 = inquiryService.inquiryCountByTime(startDate, endDate, statusArr, 4, 8, "", "");
        int count3 = inquiryService.inquiryCountByTime(startDate, endDate, statusArr, 8, 24, "", "");
        int count5 = inquiryService.inquiryCountByTime(startDate, endDate, statusArr, 24, 48, "", "");
        int otherCount = totalCount - (count1 + count2 + count3 + count5);
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
        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]x`

        ///查询给定时间的事业部询单数量
        List<Map<String, Object>> inquiryList = inquiryService.findCountByRangRollinTimeGroupOrigation(startDate, endDate);
        // 查询给定时间段的事业部订单数量和金额
        List<Map<String, Object>> orderList = orderService.findCountAndAmountByRangProjectStartGroupOrigation(startDate, endDate);
        //查询给定时间段的事业部平均报价时间
        List<Map<String, Object>> avgNeedTimeList = inquiryService.findAvgNeedTimeByRollinTimeGroupOrigation(startDate, endDate);

        // 所有事业部(这里都是辅助变量，下面构建表格数据要使用)
        Set<String> organizations = new HashSet<>();
        Integer inquiryCount = 0;
        Integer orderCount = 0;
        // 将列表转换为以事业部名称为键的map
        Map<String, Map<String, Object>> inquiryMap = new HashedMap();
        Map<String, Map<String, Object>> orderMap = new HashedMap();

        // 询单事业部和总询单数
        inquiryCount = inquiryList.stream().map(m -> {
            String organization = String.valueOf(m.get("organization"));
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
        Date startDate = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.PARAM_TYPE_ERROR);
        }
        // 结束时间
        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]

        Result<Object> result = new Result<>();
        ///查询给定时间的区域询单数量和金额
        List<Map<String, Object>> inquiryList = inquiryService.findCountAndPriceByRangRollinTimeGroupArea(startDate, endDate);
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
        inqCount.put("marketArea",areaInqCounts);
        inqCount.put("inqCounts", inqCounts);
        data.put("inqCount", inqCount);
        // 询单金额
        Map<String, Object> inqAmount = new HashedMap();
        inqAmount.put("marketArea",areaInqAmounts);
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

        //int inqTotalCount = inquiryService.inquiryCountByTime(startTime, endTime, "", 0, 0, "", "");
        //Double inqTotalAmount = inquiryService.inquiryAmountByTime(startTime, endTime, "");
        //int ordTotalCount = orderService.orderCountByTime(startTime, endTime, "", "", "");
        //Double ordTotalAmount = orderService.orderAmountByTime(startTime, endTime, "");
        int inqTotalCount = 0;
        int ordTotalCount = 0;
        BigDecimal inqTotalAmount = BigDecimal.ZERO;
        BigDecimal ordTotalAmount = BigDecimal.ZERO;

      //  List<CateDetailVo> inqList = this.inquiryService.selectInqDetailByCategory(startTime, endTime);
        List<CateDetailVo> inqList = inquirySKUService.selectSKUDetailByCategory(startTime, endTime);
        List<CateDetailVo> ordList = orderService.selecOrdDetailByCategory(startTime, endTime);
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
                if(vo.getInqCateCount()>0){
                    inqTotalCount += vo.getInqCateCount();
                }
                if(vo.getInqCatePrice()>0) {
                    inqTotalAmount = inqTotalAmount.add(new BigDecimal(vo.getInqCatePrice()));
                }
            }
        }

        List<CateDetailVo> data = new ArrayList<>();
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
            data.add(vo);
        }


        data.sort((vo1, vo2) -> {
            int count1 = vo2.getInqCateCount() + vo2.getOrdCateCount();
            int count2 = vo1.getInqCateCount() + vo1.getOrdCateCount();

            return count1 - count2;
        });
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
