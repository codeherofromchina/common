package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.RequestCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author:SHIGS
 * @Description 应收账款
 * @Date Created in 17:06 2017/10/23
 * @modified By
 */
@Controller
@RequestMapping("/report/receivable")
public class RequestCreditController {
    @Autowired
    private RequestCreditService requestCreditService;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * @Author:SHIGS
     * @Description 1.应收账款累计数据图
     * @Date:17:26 2017/10/23
     * @modified By
     */
   /* @ResponseBody
    @RequestMapping(value = "totalReceive", method = RequestMethod.POST)
    public Object totalReceive() {
        Map mapMount = requestCreditService.selectTotal();
        Result<Map<String, Object>> result = new Result<>(mapMount);
        return result;
    }*/

    /**
     * @Author:SHIGS
     * @Description 2.应收账款图
     * @Date:14:40 2017/10/31
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "receiveDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object totalReceive(@RequestBody Map<String, Object> map) throws Exception {
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
        //当前时期
        int days = DateUtil.getDayBetween(startTime, endTime);
        //环比时段
        Date chainTime = DateUtil.sometimeCalendar(startTime, days);
        //当前时段
        Map mapMount = requestCreditService.selectRequestTotal(startTime, endTime);
        if (mapMount == null) {
            mapMount = new HashMap();
            mapMount.put("sdT", 0);
            mapMount.put("sded", 0);
            mapMount.put("sd", 0);
        }
        //应收金额
        BigDecimal orderAmount = BigDecimal.ZERO;
        if (mapMount.containsKey("sdT")) {
            orderAmount = new BigDecimal(mapMount.get("sdT").toString());
        }
        //已收金额
        BigDecimal receiveAmount = BigDecimal.ZERO;
        if (mapMount.containsKey("sded")) {
            receiveAmount = new BigDecimal(mapMount.get("sded").toString());
        }
        //应收未收
        BigDecimal notreceiveAmount = BigDecimal.ZERO;
        if (mapMount.containsKey("sd")) {
            notreceiveAmount = new BigDecimal(mapMount.get("sd").toString());
        }
        //环比
        Map mapChainMount = requestCreditService.selectRequestTotal(chainTime, startTime);
        if (mapChainMount == null) {
            mapChainMount = new HashMap();
            mapChainMount.put("sdT", 0);
            mapChainMount.put("sded", 0);
            mapChainMount.put("sd", 0);
        }
        //环比应收金额
        BigDecimal chainOrderAmount = BigDecimal.ZERO;
        if (mapChainMount.containsKey("sdT")) {
            chainOrderAmount = new BigDecimal(mapChainMount.get("sdT").toString());
        }
        //环比已收金额
        BigDecimal chainReceiveAmount = BigDecimal.ZERO;
        if (mapChainMount.containsKey("sded")) {
            chainReceiveAmount = new BigDecimal(mapChainMount.get("sded").toString());
        }
        //应收未收
        BigDecimal chainNotreceiveAmount = BigDecimal.ZERO;
        if (mapChainMount.containsKey("sd")) {
            chainNotreceiveAmount = new BigDecimal(mapChainMount.get("sd").toString());
        }
        Double orederAmountAdd = orderAmount.doubleValue() - chainOrderAmount.doubleValue();
        //应收账款
        Map<String, Object> receivable = new HashMap<>();
        receivable.put("receive", df.format(orderAmount.doubleValue() / 10000) + "万$");
        receivable.put("chainAdd", df.format(orederAmountAdd / 10000) + "万$");
        receivable.put("chainRate", RateUtil.doubleChainRate(orederAmountAdd, chainOrderAmount.doubleValue()));
        Double NotReceiveAmountAdd = notreceiveAmount.doubleValue() - chainNotreceiveAmount.doubleValue();
        //应收未收
        Map<String, Object> notReceive = new HashMap<>();
        notReceive.put("receive", df.format(notreceiveAmount.doubleValue() / 10000) + "万$");
        notReceive.put("chainAdd", df.format(NotReceiveAmountAdd / 10000) + "万$");
        notReceive.put("chainRate", RateUtil.doubleChainRate(NotReceiveAmountAdd, chainNotreceiveAmount.doubleValue()));
        Double ReceiveAmountAdd = receiveAmount.doubleValue() - chainReceiveAmount.doubleValue();
        //应收已收
        Map<String, Object> received = new HashMap<>();
        received.put("receive", df.format(receiveAmount.doubleValue() / 10000) + "万$");
        received.put("chainAdd", df.format(ReceiveAmountAdd / 10000) + "万$");
        received.put("chainRate", RateUtil.doubleChainRate(ReceiveAmountAdd, chainReceiveAmount.doubleValue()));
        //下周应收
        Date curDate = new Date();
        Date nextWeekEndTime = DateUtil.getWeek(curDate, 5);
        Date nextWeekStartTime = DateUtil.getBeforeWeek(nextWeekEndTime, 6);
        Map<String, Object> nextWeekMount = requestCreditService.selectRequestNextNew(nextWeekStartTime, nextWeekEndTime,"","");
        Date chainWeekStartTime = DateUtil.sometimeCalendar(nextWeekStartTime, 7);
        Date chainWeekEndTime = DateUtil.getWeek(nextWeekEndTime, 5);
        Map<String, Object> chainNextWeekMount = requestCreditService.selectRequestNextNew(chainWeekStartTime, chainWeekEndTime,"","");
        if (nextWeekMount == null) {
            nextWeekMount = new HashMap();
            nextWeekMount.put("sdT", 0);
        }
        BigDecimal nextWeekOrderAmount = BigDecimal.ZERO;
        if (nextWeekMount.containsKey("sdT")) {
            nextWeekOrderAmount = new BigDecimal(nextWeekMount.get("sdT").toString());
        }
        if (chainNextWeekMount == null) {
            chainNextWeekMount = new HashMap();
            chainNextWeekMount.put("sdT", 0);
        }
        BigDecimal chainWeekOrderAmount = BigDecimal.ZERO;
        if (chainNextWeekMount.containsKey("sdT")) {
            chainWeekOrderAmount = new BigDecimal(chainNextWeekMount.get("sdT").toString());
        }
        HashMap<Object, Object> nextWeekReceivable = new HashMap<>();
        Double chainWeekOrderAmountAdd = nextWeekOrderAmount.doubleValue()-chainWeekOrderAmount.doubleValue();
        nextWeekReceivable.put("receive",df.format(nextWeekOrderAmount.doubleValue() / 10000) + "万$");
        nextWeekReceivable.put("chainAdd",df.format(chainWeekOrderAmountAdd / 10000) + "万$");
        nextWeekReceivable.put("chainRate",RateUtil.doubleChainRate(chainWeekOrderAmountAdd, nextWeekOrderAmount.doubleValue()));
        nextWeekReceivable.put("startTime",DateUtil.formatDate2String(nextWeekStartTime,DateUtil.SHORT_SLASH_FORMAT_STR));
        nextWeekReceivable.put("endTime",DateUtil.formatDate2String(nextWeekEndTime,DateUtil.SHORT_SLASH_FORMAT_STR));
        //下月应收
        Date nextMonthStartTime = DateUtil.getNextMonthFirstDay(curDate);
        Date nextMonthEndTime = DateUtil.getNextMonthLastDay(curDate);
        Map<String, Object> nextMonthMount = requestCreditService.selectRequestNextNew(nextMonthStartTime, nextMonthEndTime,"","");
        Date chainMonthFirstDay = DateUtil.getMonthFirstDay(curDate);
        Date chainMonthLastDay = DateUtil.getMonthLastDay(curDate);
        Map<String, Object> chainNextMonthMount = requestCreditService.selectRequestNextNew(chainMonthFirstDay, chainMonthLastDay,"","");
        if (nextMonthMount == null) {
            nextMonthMount = new HashMap();
            nextMonthMount.put("sdT", 0);
        }
        BigDecimal nextMonthOrderAmount = BigDecimal.ZERO;
        if (nextMonthMount.containsKey("sdT")) {
            nextMonthOrderAmount = new BigDecimal(nextMonthMount.get("sdT").toString());
        }
        if (chainNextMonthMount == null) {
            chainNextMonthMount = new HashMap();
            chainNextMonthMount.put("sdT", 0);
        }
        BigDecimal chainMonthOrderAmount = BigDecimal.ZERO;
        if (chainNextMonthMount.containsKey("sdT")) {
            chainMonthOrderAmount = new BigDecimal(chainNextMonthMount.get("sdT").toString());
        }
        HashMap<Object, Object> nextMonthReceivable = new HashMap<>();
        Double chainMonthOrderAmountAdd = nextMonthOrderAmount.doubleValue()-chainMonthOrderAmount.doubleValue();
        nextMonthReceivable.put("receive",df.format(nextMonthOrderAmount.doubleValue() / 10000) + "万$");
        nextMonthReceivable.put("chainAdd",df.format(chainMonthOrderAmountAdd / 10000) + "万$");
        nextMonthReceivable.put("chainRate",RateUtil.doubleChainRate(chainMonthOrderAmountAdd, nextMonthOrderAmount.doubleValue()));
        nextMonthReceivable.put("startTime",DateUtil.formatDate2String(nextMonthStartTime,DateUtil.SHORT_SLASH_FORMAT_STR));
        nextMonthReceivable.put("endTime",DateUtil.formatDate2String(nextMonthEndTime,DateUtil.SHORT_SLASH_FORMAT_STR));
        Map<String, Object> data = new HashMap<>();
        data.put("receive", receivable);
        data.put("received", received);
        data.put("notReceive", notReceive);
        data.put("nextWeekReceivable", nextWeekReceivable);
        data.put("nextMonthReceivable", nextMonthReceivable);
        Result<Map<String, Object>> result = new Result<>(data);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 3.应收账款趋势图
     * @Date:9:25 2017/10/24
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "tendencyChart", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object tendencyChart(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("receiveName")) {
            throw new MissingServletRequestParameterException(" receiveName", "String");
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
        Map<String, Object> dataMap = requestCreditService.selectRequestTrend(startTime, endTime, map.get("receiveName").toString());
        Result<Map<String, Object>> result = new Result<>(dataMap);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 查询销售区域
     * @Date:19:39 2017/10/30
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "queryArea", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object queryArea() {
        Map<String, Object> areaMap = requestCreditService.selectArea();
        Result<Map<String, Object>> result = new Result<>(areaMap);
        return result;
    }
    /**
     * @Author:SHIGS
     * @Description 根据销售区域查询国家
     * @Date:19:40 2017/10/30
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "queryCoutry", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object queryCoutry(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("area")) {
            throw new MissingServletRequestParameterException("area", "String");
        }
        Map<String, Object> areaCountry = requestCreditService.selectCountry(map.get("area").toString());
        Result<Map<String, Object>> result = new Result<>(areaCountry);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 4.区域明细图
     * @Date:14:41 2017/10/31
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "areaDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object areaDetail(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("area") || !map.containsKey("country")) {
            throw new MissingServletRequestParameterException("area || country", "String");
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
        //下月应收
        Date nextTime = DateUtil.recedeTime(-30);
        //应收,已收应收,未收总量
        Map mapTotal = requestCreditService.selectRequestTotal(startTime, endTime);
        if (mapTotal == null) {
            mapTotal = new HashMap();
            mapTotal.put("sdT", 0);
            mapTotal.put("sded", 0);
            mapTotal.put("sd", 0);
        }
        //应收金额
        BigDecimal totalOrderAmount = BigDecimal.ZERO;
        if (mapTotal.containsKey("sdT")) {
            totalOrderAmount = new BigDecimal(mapTotal.get("sdT").toString());
        }
        //已收金额
        BigDecimal totalReceiveAmount = BigDecimal.ZERO;
        if (mapTotal.containsKey("sded")) {
            totalReceiveAmount = new BigDecimal(mapTotal.get("sded").toString());
        }
        //应收未收
        BigDecimal totalNotreceiveAmount = BigDecimal.ZERO;
        if (mapTotal.containsKey("sd")) {
            totalNotreceiveAmount = new BigDecimal(mapTotal.get("sd").toString());
        }
        String country = "";
        Object obj = map.get("country");
        if (obj != null) {
            country = obj.toString();
        }


        //根据区域或者国家
        Map mapCount = requestCreditService.selectByAreaOrCountry(startTime, endTime, map.get("area").toString(), country);
        if (mapCount == null) {
            mapCount = new HashMap();
            mapCount.put("oa", 0);
            mapCount.put("received", 0);
            mapCount.put("ra", 0);
        }
        //应收金额
        BigDecimal acOrderAmount = BigDecimal.ZERO;
        if (mapCount.containsKey("oa")) {
            acOrderAmount = new BigDecimal(mapCount.get("oa").toString());
        }
        //已收金额
        BigDecimal acReceiveAmount = BigDecimal.ZERO;
        if (mapCount.containsKey("received")) {
            acReceiveAmount = new BigDecimal(mapCount.get("received").toString());
        }
        //未收金额
        BigDecimal acNotreceiveAmount = BigDecimal.ZERO;
        if (mapCount.containsKey("ra")) {
            acNotreceiveAmount = new BigDecimal(mapCount.get("ra").toString());
        }
        //下月应收
        Map mapNext = requestCreditService.selectRequestNextNew(new Date(), nextTime, map.get("area").toString(), country);
        if (mapNext == null) {
            mapNext = new HashMap();
            mapNext.put("sdT", 0);
        }
        BigDecimal nextOrderAmount = BigDecimal.ZERO;
        if (mapNext.containsKey("sdT")) {
            nextOrderAmount = new BigDecimal(mapNext.get("sdT").toString());
        }
        List<String> conList = new ArrayList<>();
        List<Double> amountList = new ArrayList<>();
        if (totalOrderAmount.doubleValue() == 0) {
            conList.add("应收金额-占比0.00%");
        } else {
            conList.add("应收金额-占比" + df.format((acOrderAmount.doubleValue() / totalOrderAmount.doubleValue()) * 100) + "%");
        }
        if (totalReceiveAmount.doubleValue() == 0) {
            conList.add("已收金额-占比0.00%");
        } else {
            conList.add("已收金额-占比" + df.format((acReceiveAmount.doubleValue() / totalReceiveAmount.doubleValue()) * 100) + "%");
        }
        if (totalNotreceiveAmount.doubleValue() == 0) {
            conList.add("应收未收-占比0.00%");
        } else {
            conList.add("应收未收-占比" + df.format((acNotreceiveAmount.doubleValue() / totalNotreceiveAmount.doubleValue()) * 100) + "%");
        }
        if (totalOrderAmount.doubleValue() == 0) {
            conList.add("下月应收-占比0.00%");
        } else {
            conList.add("下月应收-占比" + df.format((nextOrderAmount.doubleValue() / totalOrderAmount.doubleValue()) * 100) + "%");
        }
        amountList.add(acOrderAmount.setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        amountList.add(acReceiveAmount.setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        amountList.add(acNotreceiveAmount.setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        amountList.add(nextOrderAmount.setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        Map<String, Object> data = new HashMap();
        data.put("xAxis", conList);
        data.put("yAxis", amountList);
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("code", 200);
        return result;
    }
}
