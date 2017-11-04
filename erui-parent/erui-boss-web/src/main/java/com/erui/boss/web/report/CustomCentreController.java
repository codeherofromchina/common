package com.erui.boss.web.report;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.CateDetailVo;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.OrderCountService;
import com.erui.report.util.CustomerNumSummaryVO;
import com.erui.report.util.InquiryAreaVO;

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
    @RequestMapping(value = "/inquiryPandect",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object inquiryPandect(@RequestBody(required = true) Map<String, Object> reqMap ){
        Result<Object> result = new Result<>();
        if (!reqMap.containsKey("days")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            result.setData("参数输入有误");
            return result;
        }
        //当前时期
        int days = Integer.parseInt(reqMap.get("days").toString());
        Date startTime = DateUtil.recedeTime(days);
        Date chainDate = DateUtil.recedeTime(days*2);//环比起始时间
        Map<String,Object> Datas=new HashMap<String,Object>();//询单统计信息
        Map<String,Object> inquiryMap=new HashMap<String,Object>();//询单统计信息
        Map<String,Object> proIsOilMap=new HashMap<String,Object>();//油气产品分析
        List<Map<String,Object>> listTop3 = new ArrayList<>();

        //当期询单数
        int count = inquiryService.inquiryCountByTime(startTime, new Date(),null,0,0,"","");
        //当期询单金额
        double amount = inquiryService.inquiryAmountByTime(startTime, new Date(),"");
        //当期询单数环比chain
        int chainCount = inquiryService.inquiryCountByTime(chainDate, startTime,null,0,0,"","");
        int chain=count-chainCount;
        double chainRate = 0.0;
        if(chainCount>0){
            chainRate=  RateUtil.intChainRate(count-chainCount, chainCount);//环比
        }
        inquiryMap.put("count",count);
        inquiryMap.put("amount",df.format(amount/10000)+"万$");
        inquiryMap.put("chainAdd",chain);
        inquiryMap.put("chainRate",chainRate);


        //油气产品统计
        CustomerNumSummaryVO custSummaryVO=inquiryService.selectNumSummaryByExample(startTime,new Date());
        CustomerNumSummaryVO custSummaryVO2=inquiryService.selectNumSummaryByExample(chainDate,startTime);
        if(custSummaryVO!=null&&custSummaryVO.getTotal()>0){
            proIsOilMap.put("oil",custSummaryVO.getOil());
            proIsOilMap.put("notOil",custSummaryVO.getNonoil());
            proIsOilMap.put("oiProportionl",RateUtil.intChainRate(custSummaryVO.getOil(),custSummaryVO.getTotal()));
            if(custSummaryVO2.getOil()>0){
                proIsOilMap.put("chainRate",RateUtil.intChainRate(custSummaryVO.getOil()-custSummaryVO2.getOil(),custSummaryVO2.getOil()));
            }else {
                proIsOilMap.put("chainRate",0);
            }
        }else{
            proIsOilMap.put("oil",0);
            proIsOilMap.put("notOil",0);
            proIsOilMap.put("oiProportionl",0.00);
            proIsOilMap.put("chainRate",0.00);
        }
        //top3统计
        Map<String,Object> params=new HashMap<>();
        params.put("startTime",DateUtil.recedeTime(days));
        params.put("endTime",new Date());
        List<Map<String,Object>> list = inquiryService.selectProTop3(params);
        Map<String,Object> top3TotalMap = inquiryService.selectProTop3Total(startTime,new Date());
        BigDecimal top3Total= null;
        if (!top3TotalMap.containsKey("proCountTotal")){
            top3TotalMap.put("proCountTotal",0);
        }else {
            top3Total  = new BigDecimal(top3TotalMap.get("proCountTotal").toString());
        }

        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                Map<String, Object> top3 = list.get(i);
                if (top3 == null){
                    top3 = new HashMap<>();
                }
                BigDecimal s = null;
                String proCategory = null;
                if (!top3.containsKey("proCategory")){
                    continue;
                }
                if (!top3.containsKey("proCount")){
                    top3.put("proCount",0);
                }else {
                    s =new BigDecimal(top3.get("proCount").toString());
                }
                int top3Count = s.intValue();
                int totalC=top3Total.intValue();
                if(StringUtils.isNoneBlank(proCategory)){
                    int proTotal = inquiryService.selectProCountByExample(startTime, new Date(), "", "");
                    if(proTotal>0){
                        totalC=proTotal;
                    }
                }
                top3.put("proProportionl",RateUtil.intChainRate(top3Count,totalC));
               // proTop3Map.put("top"+(i+1),top3);
                listTop3.add(top3);
            }
        }

        Datas.put("inquiry",inquiryMap);
        Datas.put("isOil",proIsOilMap);
        Datas.put("proTop3",listTop3);
        return result.setData(Datas);
    }


	/*
	 * 订单总览
	 */
	@ResponseBody
	@RequestMapping(value = "/orderPandect", method = RequestMethod.POST, produces = "application/json;charset=utf8")
	public Object orderPandect(@RequestBody(required = true) Map<String, Object> reqMap) {
		Result<Object> result = new Result<>();
		if (!reqMap.containsKey("days")) {
			result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
			result.setData("参数输入有误");
			return result;
		}
		// 当前时期
		int days = Integer.parseInt(reqMap.get("days").toString());
		Date startTime = DateUtil.recedeTime(days);
		Date chainDate = DateUtil.recedeTime(days * 2);// 环比起始时间
		Map<String, Object> Datas = new HashMap<String, Object>();// 订单统计信息

		// 当期订单数
		int count = orderService.orderCountByTime(startTime, new Date(), "", "", "");
		// 当期询单金额
		double amount = orderService.orderAmountByTime(startTime, new Date(), "");
		// 上期订单数
		int chainCount = orderService.orderCountByTime(chainDate, startTime, "", "", "");
		double chainRate = 0.00;
		if (chainCount > 0) {
			chainRate = RateUtil.intChainRate(count - chainCount, chainCount);
		}
		Map<String, Object> orderMap = new HashMap<String, Object>();// 询单统计信息
		orderMap.put("count", count);
		orderMap.put("amount", df.format(amount/10000) + "万$");
		orderMap.put("chainAdd", count - chainCount);
		orderMap.put("chainRate", chainRate);

		// 利润率
		Double profit = orderService.selectProfitRate(startTime, new Date());
        Double chainProfit = orderService.selectProfitRate(chainDate, startTime);
        double profitRate=0.00;
        double chainProfitRate =0.00;
		if(profit!=null){
            profitRate= RateUtil.doubleChainRate(profit, 1);
        }
		if(chainProfit!=null){
            chainProfitRate=RateUtil.doubleChainRate(chainProfit, 1);
        }
		Map<String, Object> profitMap = new HashMap<String, Object>();
		profitMap.put("profitRate", profitRate);
		profitMap.put("chainRate", chainProfitRate);
		// 成单率
		double successOrderRate = 0.00;
		double successOrderChian = 0.00;// 环比
		int successOrderCount = orderService.orderCountByTime(startTime, new Date(), "正常完成", "", "");
		int successInquiryCount = inquiryService.inquiryCountByTime(startTime, new Date(), "已报价", 0, 0, "", "");
		int successOrderChianCount = orderService.orderCountByTime(chainDate, startTime, "正常完成", "", "");
		if (successInquiryCount > 0) {
			successOrderRate = RateUtil.intChainRate(successOrderCount, successInquiryCount);
		}
		if (successOrderChianCount > 0) {
			successOrderChian = RateUtil.intChainRate((successOrderCount - successOrderChianCount),
					successOrderChianCount);
		}
		Map<String, Object> sucessOrderMap = new HashMap<String, Object>();
		sucessOrderMap.put("successOrderRate", successOrderRate);
		sucessOrderMap.put("successOrderChian", successOrderChian);
		// top3
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", new Date());
		int proTotalCount = orderService.selectProCountByExample();
		List<Map<String, Object>> list = orderService.selectOrderProTop3(params);
		Map<String, Object> proTop3Map = new HashMap<String, Object>();
		double topProportion = 0.0;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> top3 = list.get(i);
				BigDecimal s = new BigDecimal(top3.get("orderCount").toString());
				int top3Count = s.intValue();
				if (proTotalCount > 0) {
					topProportion = RateUtil.intChainRate(top3Count, proTotalCount);
				}
				top3.put("topProportion", topProportion);
				proTop3Map.put("top" + (i + 1), top3);
			}
		}

		// 组装数据
		Datas.put("order", orderMap);
		Datas.put("profitRate", profitMap);
		Datas.put("sucessOrderMap", sucessOrderMap);
		Datas.put("top3", proTop3Map);

		result.setData(Datas);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/inquiryDetail")
	public Object inquiryDetail() {
		Map<String, Object> result = new HashMap<String, Object>();
		Date startTime = DateUtil.recedeTime(7);
		int quotedCount = inquiryService.inquiryCountByTime(startTime, new Date(), "已报价", 0, 0, "", "");
		int noQuoteCount = inquiryService.inquiryCountByTime(startTime, new Date(), "未报价", 0, 0, "", "");
		int quotingCount = inquiryService.inquiryCountByTime(startTime, new Date(), "报价中", 0, 0, "", "");
		int totalCount = quotedCount + noQuoteCount + quotingCount;
		double quotedInquiryRate = 0.00;
		double quotingInquiryRate = 0.00;
		double noQuoteInquiryRate = 0.00;
		if (totalCount > 0) {
			quotedInquiryRate = RateUtil.intChainRate(quotedCount, totalCount);
			quotingInquiryRate = RateUtil.intChainRate(quotingCount, totalCount);
			noQuoteInquiryRate = RateUtil.intChainRate(noQuoteCount, totalCount);
		}
		HashMap<String, Object> inquiryDetailMap = new HashMap<>();
		inquiryDetailMap.put("quotedCount", quotedCount);
		inquiryDetailMap.put("noQuoteCount", noQuoteCount);
		inquiryDetailMap.put("quotingCount", quotingCount);
		inquiryDetailMap.put("quotedInquiryRate", quotedInquiryRate);
		inquiryDetailMap.put("quotingInquiryRate", quotingInquiryRate);
		inquiryDetailMap.put("noQuoteInquiryRate", noQuoteInquiryRate);

		result.put("code", 200);
		result.put("data", inquiryDetailMap);
		return result;
	}

	// 询单时间分布分析
	@ResponseBody
	@RequestMapping(value = "/inquiryTimeDistrbute", method = RequestMethod.POST, produces = "application/json;charset=utf8")
	public Object inquiryTimeDistrbute(@RequestBody(required = true) Map<String, Object> reqMap) {
		Result<Object> result = new Result<>();
		if (!reqMap.containsKey("days")) {
			result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
			result.setData("参数输入有误");
			return result;
		}
		// 当前时期
		int days = Integer.parseInt(reqMap.get("days").toString());
		Date startTime = DateUtil.recedeTime(days);
		int totalCount = inquiryService.inquiryCountByTime(startTime, new Date(), "", 0, 0, "", "");
		int count1 = inquiryService.inquiryCountByTime(startTime, new Date(), "", 0, 4, "", "");
		int count2 = inquiryService.inquiryCountByTime(startTime, new Date(), "", 4, 8, "", "");
		int count3 = inquiryService.inquiryCountByTime(startTime, new Date(), "", 8, 16, "", "");
		int count4 = inquiryService.inquiryCountByTime(startTime, new Date(), "", 16, 24, "", "");
		int count5 = inquiryService.inquiryCountByTime(startTime, new Date(), "", 24, 48, "", "");
		int otherCount = inquiryService.inquiryCountByTime(startTime, new Date(), "", 48, 1000, "", "");
		HashMap<String, Object> quoteTimeMap = new HashMap<>();
		quoteTimeMap.put("oneCount", count1);
		quoteTimeMap.put("fourCount", count2);
		quoteTimeMap.put("eightCount", count3);
		quoteTimeMap.put("sixteenCount", count4);
		quoteTimeMap.put("twentyFourCount", count5);
		quoteTimeMap.put("otherCount", otherCount);
		if (totalCount > 0) {
            double oneCountRate = RateUtil.intChainRate(count1, totalCount);
            double fourCountRate = RateUtil.intChainRate(count2, totalCount);
            double eightCountRate = RateUtil.intChainRate(count3, totalCount);
            double sixteenCountRate = RateUtil.intChainRate(count4, totalCount);
            double twentyFourCountRate = RateUtil.intChainRate(count5, totalCount);
            quoteTimeMap.put("oneCountRate",oneCountRate );
			quoteTimeMap.put("fourCountRate",fourCountRate);
			quoteTimeMap.put("eightCountRate",eightCountRate);
			quoteTimeMap.put("sixteenCountRate",sixteenCountRate );
			quoteTimeMap.put("twentyFourCountRate",twentyFourCountRate );
			quoteTimeMap.put("otherCountRate",1-(oneCountRate+fourCountRate+eightCountRate+sixteenCountRate+twentyFourCountRate));
		}else{
            quoteTimeMap.put("oneCountRate",0.00 );
            quoteTimeMap.put("fourCountRate",0.00);
            quoteTimeMap.put("eightCountRate",0.00);
            quoteTimeMap.put("sixteenCountRate",0.00 );
            quoteTimeMap.put("twentyFourCountRate",0.00 );
            quoteTimeMap.put("otherCountRate",0.00);
        }
		result.setStatus(ResultStatusEnum.SUCCESS);
		result.setData(quoteTimeMap);
		return result;
	}

	// 事业部明细
	@ResponseBody
	@RequestMapping(value = "/busUnitDetail", method = RequestMethod.POST)
	public Object busUnitDetail() {
		HashMap<String, Object> result = new HashMap<>();// 结果集
		HashMap<String, Object> data = new HashMap<>();// 数据集
		Date startTime = DateUtil.recedeTime(7);
		// 事业部列表
		List<String> orgList = inquiryService.selectOrgList();
		String[] orgs = new String[orgList.size()];
		Integer[] inqCounts = new Integer[orgList.size()];
		Integer[] ordCounts = new Integer[orgList.size()];
		Double[] successOrdCounts = new Double[orgList.size()];
		for (int i = 0; i < orgList.size(); i++) {
			int inqCount = inquiryService.inquiryCountByTime(startTime, new Date(), "", 0, 0, orgList.get(i), "");
			int ordCount = orderService.orderCountByTime(startTime, new Date(), "", orgList.get(i), "");
            int successInqCount = inquiryService.inquiryCountByTime(startTime, new Date(), "已报价", 0, 0, orgList.get(i), "");
            int successOrdCount = orderService.orderCountByTime(startTime, new Date(), "正常完成", orgList.get(i), "");
			inqCounts[i] = inqCount;
			ordCounts[i] = ordCount;
			double successRate = 0.0;
			if (successInqCount >0) {
				successRate = RateUtil.intChainRateTwo(successOrdCount, successInqCount);
			}
			successOrdCounts[i] = successRate;
			orgs[i] = orgList.get(i);
		}
		data.put("busUnit", orgs);
		data.put("inquiryCount", inqCounts);
		data.put("orderCount", ordCounts);
		data.put("seccessOrderCount", successOrdCounts);

		result.put("code", 200);
		result.put("data", data);
		return result;
	}

	// 区域明细对比
	@ResponseBody
	@RequestMapping("/areaDetailContrast")
	public Object areaDetailContrast() {
		Result<Object> result = new Result<>();
		Date startTime = DateUtil.recedeTime(7);
		// 询单
		//List<String> areaList = inquiryService.selectAreaList();
        List<InquiryAreaVO> areaList1 = inquiryService.selectAllAreaAndCountryList();
        List<String> areaList = areaList1.parallelStream().map(InquiryAreaVO::getAreaName)
                .collect(Collectors.toList());
		String[] areaInqCounts = new String[areaList.size() + 1];// 询单数量区域列表
		String[] areaInqAmounts = new String[areaList.size() + 1];// 询单金额区域列表
		Integer[] inqCounts = new Integer[areaList.size() + 1];// 询单数量列表
		Double[] inqAmounts = new Double[areaList.size() + 1];// 询单金额列表
		areaInqCounts[0] = "询单总数量";
		areaInqAmounts[0] = "询单总金额";
		int inqTotalCount = inquiryService.inquiryCountByTime(startTime, new Date(), "", 0, 0, "", "");
		Double inqTotalAmount = inquiryService.inquiryAmountByTime(startTime, new Date(), "");
		inqCounts[0] = inqTotalCount;
		inqAmounts[0] = inqTotalAmount;
		// 订单
		String[] areaOrdCounts = new String[areaList.size() + 1];// 订单数量区域列表
		String[] areaOrdAmounts = new String[areaList.size() + 1];// 订单金额区域列表
		Integer[] OrdCounts = new Integer[areaList.size() + 1];// 订单数量列表
		Double[] OrdAmounts = new Double[areaList.size() + 1];// 订单金额列表
		areaOrdCounts[0] = "订单总数量";
		areaOrdAmounts[0] = "订单总金额";
		int orderTotalCount = orderService.orderCountByTime(startTime, new Date(), "", "", "");
		Double orderTotalAmount = orderService.orderAmountByTime(startTime, new Date(), "");
		OrdCounts[0] = orderTotalCount;
		OrdAmounts[0] = orderTotalAmount;
		for (int i = 0; i < areaList.size(); i++) {
			areaInqCounts[i + 1] = areaList.get(i);
			areaInqAmounts[i + 1] = areaList.get(i);
			areaOrdCounts[i + 1] = areaList.get(i);
			areaOrdAmounts[i + 1] = areaList.get(i);
			int inqCount = inquiryService.inquiryCountByTime(startTime, new Date(), "", 0, 0, "", areaList.get(i));
			Double inqAmount = inquiryService.inquiryAmountByTime(startTime, new Date(), areaList.get(i));
			int ordCount = orderService.orderCountByTime(startTime, new Date(), "", "", areaList.get(i));
			Double ordAmount = orderService.orderAmountByTime(startTime, new Date(), areaList.get(i));
			inqCounts[i + 1] = inqCount;
			inqAmounts[i + 1] = inqAmount;
			OrdCounts[i + 1] = ordCount;
			OrdAmounts[i + 1] = ordAmount;
		}

		HashMap<String, Object> data = new HashMap<>();// 结果集
		HashMap<String, Object> inqCount = new HashMap<>();
		inqCount.put("marketArea", areaInqCounts);
		inqCount.put("inqCounts", inqCounts);
		HashMap<String, Object> inqAmount = new HashMap<>();
		inqAmount.put("marketArea", areaInqAmounts);
		inqAmount.put("inqAmount", inqAmounts);
		HashMap<String, Object> ordCount = new HashMap<>();
		ordCount.put("marketArea", areaOrdCounts);
		ordCount.put("ordCounts", OrdCounts);
		HashMap<String, Object> orderAmount = new HashMap<>();
		orderAmount.put("marketArea", areaOrdAmounts);
		orderAmount.put("ordAmount", OrdAmounts);
		data.put("inqCount", inqCount);
		data.put("inqAmount", inqAmount);
		data.put("ordCount", ordCount);
		data.put("orderAmount", orderAmount);

		result.setData(data);
		result.setStatus(ResultStatusEnum.SUCCESS);
		return result;
	}

	// 品类明细
	@ResponseBody
	@RequestMapping("/catesDetail")
	public Object catesDetail() {
		Date startTime = DateUtil.recedeTime(7);
		int inqTotalCount = inquiryService.inquiryCountByTime(startTime, new Date(), "", 0, 0, "", "");
		Double inqTotalAmount = inquiryService.inquiryAmountByTime(startTime, new Date(), "");
		int ordTotalCount = orderService.orderCountByTime(startTime, new Date(), "", "", "");
		Double ordTotalAmount = orderService.orderAmountByTime(startTime, new Date(), "");
		List<CateDetailVo> inqList = this.inquiryService.selectInqDetailByCategory(startTime,new Date());
		List<CateDetailVo> ordList = orderService.selecOrdDetailByCategory(startTime,new Date());
		final Map<String, CateDetailVo> ordMap;
		if (inqList != null && ordList != null) {
			ordMap = ordList.parallelStream().collect(Collectors.toMap(CateDetailVo::getCategory, vo -> vo));
		} else {
			ordMap = new HashMap<String, CateDetailVo>();
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
				}else{
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
		return new Result<Object>().setData(inqList);
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
	 * @param map
	 *            大区
	 *
	 *            城市
	 * @return
	 */
	@RequestMapping(value = "/areaDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public Object areaDetail(@RequestBody Map<String,Object> map) {
		String areaName = (String) map.get("area");
		String countryName = (String) map.get("country");
		Date startTime = DateUtil.recedeTime(7);
		CustomerNumSummaryVO orderNumSummary = orderService.numSummary(startTime,new Date(),areaName, countryName);
		CustomerNumSummaryVO inquiryNumSummary = inquiryService.numSummary(startTime,new Date(),areaName, countryName);

		Map<String, Object> numData = new HashMap<String, Object>();

		String[] xTitleArr = new String[] { "询单数量", "油气数量", "非油气数量", "订单数量", "油气数量", "非油气数量", };
		Integer[] yValueArr = new Integer[] { inquiryNumSummary.getTotal(), inquiryNumSummary.getOil(),
				inquiryNumSummary.getNonoil(), orderNumSummary.getTotal(), orderNumSummary.getOil(),
				orderNumSummary.getNonoil() };
		numData.put("x", xTitleArr);
		numData.put("y", yValueArr);

		Map<String, Object> amountData = new HashMap<String, Object>();

		String[] xTitleArr02 = new String[] { "询单总金额", "油气金额", "非油气金额", "订单总金额", "油气金额", "非油气金额", };
		BigDecimal[] yValueArr02 = new BigDecimal[] {
				inquiryNumSummary.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
				inquiryNumSummary.getOilAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
				inquiryNumSummary.getNoNoilAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
				orderNumSummary.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
				orderNumSummary.getOilAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN),
				orderNumSummary.getNoNoilAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) };
		amountData.put("x", xTitleArr02);
		amountData.put("y", yValueArr02);

		Map<String, Object> data = new HashMap<>();
		data.put("amount", amountData);
		data.put("number", numData);

		return new Result<>().setData(data);
	}
}
