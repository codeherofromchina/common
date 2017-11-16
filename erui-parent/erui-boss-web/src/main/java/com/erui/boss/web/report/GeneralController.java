package com.erui.boss.web.report;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.*;
import com.erui.report.util.InqOrdTrendVo;
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
    @Autowired
    private RequestCreditService requestCreditService;
    private static DecimalFormat df = new DecimalFormat("0.00");

    /**
     * @Author:SHIGS
     * @Description
     * @Date:17:16 2017/10/20
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "general", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object memberCount(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("startTime")) {
            throw new MissingServletRequestParameterException("startTime", "String");
        }
        if (!map.containsKey("endTime")) {
            throw new MissingServletRequestParameterException("endTime", "String");
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        int curMemberCount = memberService.selectByTime(startTime, endTime);
        Map<String, Object> member = new HashMap<String, Object>();
        member.put("count", curMemberCount);
        // 当期询单数
        int inquiryCount = inquiryService.inquiryCountByTime(startTime, endTime, "", 0, 0, "", "");
        // 当期询单金额
        double inquiryAmount = inquiryService.inquiryAmountByTime(startTime, endTime, "");
        Map<String, Object> inquiry = new HashMap<>();
        inquiry.put("count", inquiryCount);
        inquiry.put("amount", df.format(inquiryAmount / 10000) + "万$");
        // 当期订单数
        int orderCount = orderService.orderCountByTime(startTime, endTime, "", "", "");
        // 当期订单金额
        double orderAmount = orderService.orderAmountByTime(startTime, endTime, "");
        Map<String, Object> order = new HashMap<>();
        order.put("count", orderCount);
        order.put("amount", df.format(orderAmount / 10000) + "万$");
        //判断是否存在环比并求环比
        if (startTime != null && endTime != null && DateUtil.getDayBetween(startTime, endTime) > 0) {
            int days = DateUtil.getDayBetween(startTime, endTime);
            //环比开始
            Date chainEnd = DateUtil.sometimeCalendar(startTime, days);
            // 环比时段数量
            int chainMemberCount = memberService.selectByTime(chainEnd, startTime);
            // 增加
            int addMemberChain = curMemberCount - chainMemberCount;
            // 环比
            double chainMemberRate = RateUtil.intChainRate(addMemberChain, chainMemberCount);
            member.put("add", addMemberChain);
            member.put("chainRate", chainMemberRate);
            // 当期询单数环比chain
            int chainInquiryCount = inquiryService.inquiryCountByTime(chainEnd, startTime, "", 0, 0, "", "");
            int chainInquiryAdd = inquiryCount - chainInquiryCount;
            // 环比
            double chainInquiryRate = RateUtil.intChainRate(chainInquiryAdd, chainInquiryCount);
            inquiry.put("chainAdd", chainInquiryAdd);
            inquiry.put("chainRate", chainInquiryRate);
            // 环比订单数量
            int chainOrderCount = orderService.orderCountByTime(chainEnd, startTime, "", "", "");
            // 环比增加单数
            int chainOrderAdd = orderCount - chainOrderCount;
            double chainOrderRate = 0.00;
            if (chainOrderCount > 0) {
                chainOrderRate = RateUtil.intChainRate(orderCount - chainOrderCount, chainOrderCount);
            }
            order.put("chainAdd", chainOrderAdd);
            order.put("chainRate", chainOrderRate);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("member", member);
        data.put("inquiry", inquiry);
        data.put("order", order);
        Result<Map<String, Object>> result = new Result<>(data);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 战斗力
     * @Date:0:07 2017/10/21
     * @modified By
     */
    @RequestMapping(value = "capacity", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Object capacity(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("startTime")) {
            throw new MissingServletRequestParameterException("startTime", "String");
        }
        if (!map.containsKey("endTime")) {
            throw new MissingServletRequestParameterException("endTime", "String");
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        Map<String, Object> data = hrCountService.selectHrCount(startTime, endTime);
        Result<Map<String, Object>> result = new Result<>(data);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 会员级别
     * @Date:20:21 2017/10/20
     * @modified By
     */
  /*  @ResponseBody
    @RequestMapping(value = "member", method = RequestMethod.POST)
    public Object singleMemberCount() {
        Map<String, Object> member = memberService.selectMemberByTime();
        Result<Map<String, Object>> result = new Result<>(member);
        return result;
    }
*/

    /**
     * @Author:SHIGS
     * @Description 询订单趋势图
     * @Date:11:19 2017/10/23
     * @modified By
     */
    // 询单/订单趋势
    @ResponseBody
    @RequestMapping(value = "/inquiryOrderTrend", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    public Object tendencyChart(@RequestBody(required = true) Map<String, Object> map) throws Exception {
        if (!map.containsKey("startTime")) {
            throw new MissingServletRequestParameterException("startTime", "String");
        }
        if (!map.containsKey("endTime")) {
            throw new MissingServletRequestParameterException("endTime", "String");
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        InqOrdTrendVo trendVo = inquiryService.inqOrdTrend(startTime, endTime);
        Map<String, Object> datas = new HashMap<>();
        datas.put("xAxis", trendVo.getDate());
        datas.put("inquiry", trendVo.getInqCounts());
        datas.put("yAxis", trendVo.getOrdCounts());
        return new Result<>(datas);

    }

    /**
     * @Author:SHIGS
     * @Description 6.趋势图
     * @Date:17:48 2017/10/21
     * @modified By
     */
    @RequestMapping(value = "supplyTrend", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Object inquiryOrderTrend(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("type")) {
            throw new MissingServletRequestParameterException("type", "String");
        }
        if (!map.containsKey("startTime")) {
            throw new MissingServletRequestParameterException("startTime", "String");
        }
        if (!map.containsKey("endTime")) {
            throw new MissingServletRequestParameterException("endTime", "String");
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        Map<String, Object> supplyMap = supplyChainService.selectFinishByDate(startTime, endTime, map.get("type").toString());
        Result<Map<String, Object>> result = new Result<>(supplyMap);
        return result;
    }

    /**
     * 询订单分类 TOP N
     *
     * @return
     */
    @RequestMapping("/inquiryOrderCategoryTopNum")
    @ResponseBody
    public Object inquiryOrderCategoryTopNum(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("topN")) {
            throw new MissingServletRequestParameterException("topN", "String");
        }
        if (!map.containsKey("startTime")) {
            throw new MissingServletRequestParameterException("startTime", "String");
        }
        if (!map.containsKey("endTime")) {
            throw new MissingServletRequestParameterException("endTime", "String");
        }
        Integer topN = Integer.parseInt(map.get("topN").toString());
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        List<CustomerCategoryNumVO> list = inquiryService.inquiryOrderCategoryTopNum(topN, startTime, endTime);
        Result<List<CustomerCategoryNumVO>> result = new Result<List<CustomerCategoryNumVO>>(list);
        return result;
    }
     /**
      * @Author:SHIGS
      * @Description
      * @Date:21:42 2017/11/14
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value = "requestTendencyChart", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object requestTendencyChart(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("receiveName")) {
            throw new MissingServletRequestParameterException("receiveName", "String");
        }
        if (!map.containsKey("startTime")) {
            throw new MissingServletRequestParameterException("startTime", "String");
        }
        if (!map.containsKey("endTime")) {
            throw new MissingServletRequestParameterException("endTime", "String");
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        Map<String, Object> dataMap = requestCreditService.selectRequestTrend(startTime,endTime, map.get("receiveName").toString());
        Result<Map<String, Object>> result = new Result<>(dataMap);
        return result;
    }

}
