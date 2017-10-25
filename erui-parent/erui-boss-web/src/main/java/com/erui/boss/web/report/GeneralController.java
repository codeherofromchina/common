package com.erui.boss.web.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erui.boss.web.util.Result;
import com.erui.comm.DateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.service.HrCountService;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.MemberService;
import com.erui.report.service.OrderCountService;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.CustomerCategoryNumVO;

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 16:08 2017/10/20
 * @modified By
 */

@Controller
@RequestMapping("/report/general")
public class GeneralController {
	@Autowired
	private MemberService memberService;

	@Autowired
	private InquiryCountService inquiryService;

	@Autowired
	private OrderCountService orderService;
	@Autowired
	private HrCountService hrCountService;
	@Autowired
	private SupplyChainService supplyChainService;

	/**
	 * @Author:SHIGS
	 * @Description
	 * @Date:17:16 2017/10/20
	 * @modified By
	 */
	@ResponseBody
	@RequestMapping(value = "general", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	public Object memberCount(@RequestBody Map<String, Object> days) {
		// 当前时期
		Date startTime = DateUtil.recedeTime((int) days.get("days"));
		// 环比时段
		Date chainDate = DateUtil.recedeTime((int) days.get("days") * 2);
		int curMemberCount = memberService.selectByTime(startTime, new Date());
		// 环比时段数量
		int chainMemberCount = memberService.selectByTime(chainDate, startTime);
		// 增加
		int addMemberChain = curMemberCount - chainMemberCount;
		// 环比
		double chainMemberRate = RateUtil.intChainRate(addMemberChain, chainMemberCount);
		Map<String, Object> member = new HashMap<String, Object>();
		member.put("count", curMemberCount);
		member.put("add", addMemberChain);
		member.put("chainRate", chainMemberRate);
		// 当期询单数
		int inquiryCount = inquiryService.inquiryCountByTime(startTime, new Date(), "", 0, 0, "", "");
		// 当期询单数环比chain
		int chainInquiryCount = inquiryService.inquiryCountByTime(chainDate, startTime, "", 0, 0, "", "");

		int chainInquiryAdd = inquiryCount - chainInquiryCount;
		// 当期询单金额
		double inquiryAmount = inquiryService.inquiryAmountByTime(startTime, new Date(), "");
		// 环比
		double chainInquiryRate = RateUtil.intChainRate(chainInquiryAdd, chainInquiryCount);
		Map<String, Object> inquiry = new HashMap<>();
		inquiry.put("count", inquiryCount);
		inquiry.put("amount", RateUtil.doubleChainRate(inquiryAmount, 10000) + "万$");
		inquiry.put("chainAdd", chainInquiryAdd);
		inquiry.put("chainRate", chainInquiryRate);

		// 当期订单数
		int orderCount = orderService.orderCountByTime(startTime, new Date(), "", "", "");
		// 环比订单数量
		int chainOrderCount = orderService.orderCountByTime(chainDate, startTime, "", "", "");
		// 当期询单金额
		double orderAmount = orderService.orderAmountByTime(startTime, new Date(), "");
		// 环比增加单数
		int chainOrderAdd = chainOrderCount - orderCount;
		double chainOrderRate = 0.00;
		if (chainOrderCount > 0) {
			chainOrderRate = RateUtil.intChainRate(orderCount - chainOrderCount, chainOrderCount);
		}

		Map<String, Object> order = new HashMap<>();
		order.put("count", orderCount);
		order.put("amount", RateUtil.doubleChainRate(inquiryAmount, 10000) + "万$");
		order.put("chainAdd", chainOrderAdd);
		order.put("chainRate", chainOrderRate);
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		data.put("member", member);
		data.put("inquiry", inquiry);
		data.put("order", order);
		result.put("data", data);
		result.put("code", 200);
		return result;
	}

	/**
	 * @Author:SHIGS
	 * @Description 会员级别
	 * @Date:20:21 2017/10/20
	 * @modified By
	 */
	@ResponseBody
	@RequestMapping(value = "member", method = RequestMethod.POST)
	public Object singleMemberCount() {
		Map member = memberService.selectMemberByTime();
		// 黄金会员 高级会员 一般会员
		BigDecimal goldMember = new BigDecimal(member.get("s5").toString());
		BigDecimal seniorMember = new BigDecimal(member.get("s3").toString());
		BigDecimal generalMember = new BigDecimal(member.get("s1").toString());
		int totalMember = goldMember.intValue() + seniorMember.intValue() + generalMember.intValue();
		double goldMemberRate = RateUtil.intChainRate(goldMember.intValue(), totalMember);
		double seniorMemberRate = RateUtil.intChainRate(seniorMember.intValue(), totalMember);
		double generalMemberRate = RateUtil.intChainRate(seniorMember.intValue(), totalMember);
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> gold = new HashMap<>();
		Map<String, Object> senior = new HashMap<>();
		Map<String, Object> general = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		gold.put("count", goldMember);
		gold.put("rebuyRate", goldMemberRate);
		senior.put("count", seniorMember);
		senior.put("rebuyRate", seniorMemberRate);
		general.put("count", generalMember);
		general.put("rebuyRate", generalMemberRate);
		data.put("goldMember", gold);
		data.put("seniorMember", senior);
		data.put("generalMember", general);
		result.put("data", data);
		result.put("code", 200);
		return result;
	}

	/**
	 * @Author:SHIGS
	 * @Description 战斗力
	 * @Date:0:07 2017/10/21
	 * @modified By
	 */
	@RequestMapping(value = "capacity", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Object capacity(@RequestBody Map<String, Object> days) {
		// 当前时期
		Date startTime = DateUtil.recedeTime((int) days.get("days"));
		// 环比时段
		Date chainDate = DateUtil.recedeTime((int) days.get("days") * 2);
		// 当前时段
		Map CurHrCountMap = hrCountService.selectHrCountByPart(startTime, new Date());
		// 环比时段
		Map chainHrCountMap = hrCountService.selectHrCountByPart(chainDate, startTime);
		BigDecimal planCount = new BigDecimal(CurHrCountMap.get("s1").toString());
		BigDecimal regularCount = new BigDecimal(CurHrCountMap.get("s2").toString());
		BigDecimal tryCount = new BigDecimal(CurHrCountMap.get("s3").toString());
		BigDecimal turnRightCount = new BigDecimal(CurHrCountMap.get("s4").toString());
		BigDecimal dimissionCount = new BigDecimal(CurHrCountMap.get("s8").toString());
		BigDecimal turnJobin = new BigDecimal(CurHrCountMap.get("s9").toString());
		BigDecimal turnJobout = new BigDecimal(CurHrCountMap.get("s10").toString());
		// 环比人数
		BigDecimal chainRegularCount = new BigDecimal(chainHrCountMap.get("s2").toString());
		BigDecimal chainTurnRightCount = new BigDecimal(chainHrCountMap.get("s4").toString());
		// 环比增加人数
		int chainFullAdd = regularCount.intValue() - chainRegularCount.intValue();
		int chainTurnAdd = chainTurnRightCount.intValue() - turnRightCount.intValue();
		// 环比
		double staffFullChainRate = RateUtil.intChainRate(chainFullAdd, chainRegularCount.intValue());
		double turnRightChainRate = RateUtil.intChainRate(chainTurnAdd, chainTurnRightCount.intValue());
		// 满编率
		double staffFullRate = RateUtil.intChainRate(regularCount.intValue(), planCount.intValue());
		// 转正率
		double turnRightRate = RateUtil.intChainRate(turnRightCount.intValue(), tryCount.intValue());
		Map<String, Object> data = new HashMap<>();
		data.put("staffFullRate", staffFullRate);
		data.put("staffFullChainRate", staffFullChainRate);
		data.put("turnRightRate", turnRightRate);
		data.put("turnRightChainRate", turnRightChainRate);
		data.put("onDuty", regularCount);
		data.put("plan", planCount);
		data.put("turnJobin", turnJobin.intValue());
		data.put("turnJobout", turnJobout);
		data.put("try", tryCount);
		data.put("turnRight", turnRightCount);
		data.put("leave", dimissionCount);
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("data", data);
		return result;
	}

	/**
	 * @Author:SHIGS
	 * @Description 询订单趋势图
	 * @Date:11:19 2017/10/23
	 * @modified By
	 */
	// 询单/订单趋势
	@ResponseBody
	@RequestMapping(value = "/inquiryOrderTrend", method = RequestMethod.POST, produces = "application/json;charset=utf8")
	public Object tendencyChart(@RequestBody(required = true) Map<String, Object> reqMap) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		int days = (int) reqMap.get("days");
		// 封装日期,X轴
		if (days <= 30) {
			String[] dates = new String[days];
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			ArrayList<Date> dateList = new ArrayList<>();
			for (int i = 0; i < dates.length; i++) {
				Date datetime = DateUtil.recedeTime(days - (i + 1));
				dateList.add(datetime);
				String date = dateFormat.format(datetime);
				dates[i] = date;
			}
			// 封装查询订单和询单数据
			Date sTime = DateUtil.recedeTime(days);
			Integer[] inCounts = new Integer[dateList.size()];// 询单数数组
			Integer[] orderCounts = new Integer[dateList.size()];// 订单数数组
			for (int i = 0; i < dateList.size(); i++) {
				if (i == 0) {
					int inquiryCount = inquiryService.inquiryCountByTime(sTime, dateList.get(i), "", 0, 0, "", "");
					inCounts[i] = inquiryCount;
					int orderCount = orderService.orderCountByTime(sTime, dateList.get(i), "", "", "");
					orderCounts[i] = orderCount;
				} else {
					int inquiryCount = inquiryService.inquiryCountByTime(dateList.get(i - 1), dateList.get(i), "", 0, 0,
							"", "");
					inCounts[i] = inquiryCount;
					int orCount = orderService.orderCountByTime(dateList.get(i - 1), dateList.get(i), "", "", "");
					orderCounts[i] = orCount;
				}

			}
			data.put("xAxis", dates);
			data.put("inquiry", inCounts);
			data.put("yAxis", orderCounts);
		}
		result.put("code", 200);
		result.put("data", data);
		return result;
	}

	/**
	 * @Author:SHIGS
	 * @Description
	 * @Date:17:48 2017/10/21
	 * @modified By
	 */
	@RequestMapping(value = "supplyTrend", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Object inquiryOrderTrend(@RequestBody Map<String, Object> map) {
		// 当前时期
		int days = (int) map.get("days");
		Date startTime = DateUtil.recedeTime(days);
		List<Map> supplyMap = supplyChainService.selectFinishByDate(startTime, new Date());

		List<Integer> spuList = new ArrayList<>();
		List<Integer> skuList = new ArrayList<>();
		List<Integer> supplierList = new ArrayList<>();
		List<String> dateList = new ArrayList<>();
		Map<String, Map<String, Integer>> sqlDate = new HashMap<>();
		Map<String, Integer> lintData = null;
		for (Map map2 : supplyMap) {
			lintData = new HashMap<>();
			BigDecimal spu = new BigDecimal(map2.get("finish_spu_num").toString());
			BigDecimal sku = new BigDecimal(map2.get("finish_sku_num").toString());
			BigDecimal supplier = new BigDecimal(map2.get("finish_suppli_num").toString());
			Date date2 = (Date) map2.get("create_at");
			String dateString = DateUtil.format("MM月dd日", date2);
			lintData.put("spu", spu.intValue());
			lintData.put("sku", sku.intValue());
			lintData.put("supplier", supplier.intValue());
			sqlDate.put(dateString, lintData);
		}
		for (int i = 0; i < days; i++) {
			Date datetime = DateUtil.recedeTime(days - (i + 1));
			String date = DateUtil.format("MM月dd日", datetime);
			if (sqlDate.containsKey(date)) {
				dateList.add(date);
				spuList.add(sqlDate.get(date).get("spu"));
				skuList.add(sqlDate.get(date).get("sku"));
				supplierList.add(sqlDate.get(date).get("supplier"));
			} else {
				dateList.add(date);
				spuList.add(0);
				skuList.add(0);
				supplierList.add(0);
			}
		}
		String[] s = { "SPU完成量", "SKU完成量", "供应商完成量" };
		Map<String, Object> data = new HashMap<>();
		if (map.get("type").equals("spu")) {
			data.put("legend", s[0]);
			data.put("xAxis", dateList);
			data.put("yAxis", spuList);

		} else if (map.get("type").equals("sku")) {
			data.put("legend", s[1]);
			data.put("xAxis", dateList);
			data.put("yAxis", skuList);
		} else {
			data.put("legend", s[2]);
			data.put("xAxis", dateList);
			data.put("yAxis", supplierList);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("data", data);
		result.put("code", 200);
		return result;
	}

	/**
	 * 询订单分类 TOP N
	 * 
	 * @param category
	 * @return
	 */
	@RequestMapping("/inquiryOrderCategoryTopNum")
	@ResponseBody
	public Object inquiryOrderCategoryTopNum(@RequestParam(value = "topN", required = false) Integer topN,
			@RequestParam(value = "category", required = false) String category) {

		List<CustomerCategoryNumVO> list = inquiryService.inquiryOrderCategoryTopNum(topN);

		Result<List<CustomerCategoryNumVO>> result = new Result<List<CustomerCategoryNumVO>>(list);

		return result;
	}
}
