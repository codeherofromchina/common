package com.erui.boss.web.report;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.erui.comm.util.data.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erui.boss.web.util.Result;
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
	private static DecimalFormat df = new DecimalFormat("0.00");
	/**
	 * @Author:SHIGS
	 * @Description
	 * @Date:17:16 2017/10/20
	 * @modified By
	 */
	@ResponseBody
	@RequestMapping(value = "general", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	public Object memberCount(@RequestBody Map<String, Object> map) throws Exception {
		if (!map.containsKey("days")) {
			throw new MissingServletRequestParameterException("days","String");
		}
		//当前时期
		int days = Integer.parseInt(map.get("days").toString());
		// 当前时期
		Date startTime = DateUtil.recedeTime(days);
		// 环比时段
		Date chainDate = DateUtil.recedeTime( days * 2);
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
		inquiry.put("amount", df.format(inquiryAmount/10000)+"万$");
		inquiry.put("chainAdd", chainInquiryAdd);
		inquiry.put("chainRate", chainInquiryRate);

		// 当期订单数
		int orderCount = orderService.orderCountByTime(startTime, new Date(), "", "", "");
		// 环比订单数量
		int chainOrderCount = orderService.orderCountByTime(chainDate, startTime, "", "", "");
		// 当期询单金额
		double orderAmount = orderService.orderAmountByTime(startTime, new Date(), "");
		// 环比增加单数
		int chainOrderAdd = orderCount -  chainOrderCount;
		double chainOrderRate = 0.00;
		if (chainOrderCount > 0) {
			chainOrderRate = RateUtil.intChainRate(orderCount - chainOrderCount, chainOrderCount);
		}
		Map<String, Object> order = new HashMap<>();
		order.put("count", orderCount);
		order.put("amount", df.format(orderAmount/10000)+"万$");
		order.put("chainAdd", chainOrderAdd);
		order.put("chainRate", chainOrderRate);
		Map<String, Object> data = new HashMap<>();
		data.put("member", member);
		data.put("inquiry", inquiry);
		data.put("order", order);
		Result<Map<String,Object>> result = new Result<>(data);
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
	public Object capacity(@RequestBody Map<String, Object> map) throws MissingServletRequestParameterException {
		if (!map.containsKey("days")) {
			throw new MissingServletRequestParameterException("days","String");
		}
		//当前时期
		int days = Integer.parseInt(map.get("days").toString());
		Map<String,Object> data = hrCountService.selectHrCount(days);
		Result<Map<String,Object>> result = new Result<>(data);
		return result;
	}
     /**
      * @Author:SHIGS
      * @Description 会员级别
      * @Date:20:21 2017/10/20
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value = "member",method = RequestMethod.POST)
    public Object singleMemberCount(){
        Map<String,Object> member =  memberService.selectMemberByTime();
		Result<Map<String,Object>> result = new Result<>(member);
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
	public Object tendencyChart(@RequestBody(required = true) Map<String, Object> reqMap) throws Exception {
		if (!reqMap.containsKey("days")) {
			throw new MissingServletRequestParameterException("days","String");
		}
		//当前时期
		int days = Integer.parseInt(reqMap.get("days").toString());
		Map<String, Object> data = new HashMap<>();
		// 封装日期,X轴
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
		Result<Map<String,Object>> result = new Result<>(data);
		return result;
	}

	/**
	 * @Author:SHIGS
	 * @Description 6.趋势图
	 * @Date:17:48 2017/10/21
	 * @modified By
	 */
	@RequestMapping(value = "supplyTrend", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Object inquiryOrderTrend(@RequestBody Map<String, Object> map) throws Exception {
		if (!map.containsKey("days") || !map.containsKey("type")) {
			throw new MissingServletRequestParameterException("days","String");
		}
		//当前时期
		int days = Integer.parseInt(map.get("days").toString());
		Map<String,Object> supplyMap = supplyChainService.selectFinishByDate(days,map.get("type").toString());
		Result<Map<String,Object>> result = new Result<>(supplyMap);
		return result;
	}
    /**
     * 询订单分类 TOP N
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
