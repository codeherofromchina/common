package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.RequestCreditService;
import com.erui.report.service.RequestReceiveService;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private RequestReceiveService receiveService;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(RequestCreditController.class);

    /**1
     * @Author:lirb
     * @Description 应收账款总览
     * @Date:14:40 2018/4/1
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "receiveDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object totalReceive(@RequestBody Map<String, String> map) throws Exception {
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        //当前时期
        int days = DateUtil.getDayBetween(startTime, endTime);
        //环比时段
        Date chainTime = DateUtil.sometimeCalendar(startTime, days);
        //下周应收
        Date curDate = new Date();
        Date nextWeekStartTime = DateUtil.getWeekSix(6);
        Date nextWeekEndTime = DateUtil.getDateAfter(nextWeekStartTime, 6);
        //下月应收
        Date nextMonthStartTime = DateUtil.getNextMonthFirstDay(curDate);
        Date nextMonthEndTime = DateUtil.getNextMonthLastDay(curDate);

        //1.应收未收 \ 已收、应收账款、应收余额、下周未收、下月未收
        double receive = requestCreditService.selectReceive(null, endTime, null, null, null, null);
        double backAmount = receiveService.selectBackAmount(startTime, endTime, null, null, null, null);
        double totalAmount = receive + backAmount;
        double receiveBalance = receiveService.selectReceiveBalance(startTime, endTime);
        double weekReceive = requestCreditService.selectReceive(nextWeekStartTime, nextWeekEndTime, null, null, null, null);
        double MothReceive = requestCreditService.selectReceive(nextMonthStartTime, nextMonthEndTime, null, null, null, null);

        //2.环比时间的 应收未收 \ 已收、应收账款、应收余额、下周未收、下月未收
        int d = DateUtil.getDayBetween(nextWeekStartTime, nextWeekEndTime);
        Date weekchainTime = DateUtil.sometimeCalendar(nextWeekStartTime, d);//下周环比时间
        int d2 = DateUtil.getDayBetween(nextMonthStartTime, nextMonthEndTime);
        Date mothchainTime = DateUtil.sometimeCalendar(nextMonthStartTime, d2);//下月环比时间
        double chainReceive = requestCreditService.selectReceive(null, startTime, null, null, null, null);
        double chainBackAmount = receiveService.selectBackAmount(chainTime, startTime, null, null, null, null);
        double chainTotalAmount = chainReceive + chainBackAmount;
        double chainBalance = receiveService.selectReceiveBalance(chainTime, startTime);
        double chainWeekReceive = requestCreditService.selectReceive(weekchainTime, nextWeekStartTime, null, null, null, null);
        double chainMothReceive = requestCreditService.selectReceive(mothchainTime, nextMonthStartTime, null, null, null, null);

        //封装结果
        Map<String, Object> total = new HashMap<>();
        total.put("receive", RateUtil.doubleChainRateTwo(totalAmount, 10000) + "万$");
        total.put("chainAdd", RateUtil.doubleChainRateTwo((totalAmount - chainTotalAmount), 10000) + "万$");
        if (chainTotalAmount > 0) {
            total.put("chainRate", RateUtil.doubleChainRate((totalAmount - chainTotalAmount), chainTotalAmount));
        } else {
            total.put("chainRate", 0d);
        }
        Map<String, Object> notReceive = new HashMap<>();
        notReceive.put("receive", RateUtil.doubleChainRateTwo(receive, 10000) + "万$");
        notReceive.put("chainAdd", RateUtil.doubleChainRateTwo(receive - chainReceive, 10000) + "万$");
        if (chainReceive > 0) {
            notReceive.put("chainRate", RateUtil.doubleChainRateTwo(receive - chainReceive, chainReceive));
        } else {
            notReceive.put("chainRate", 0d);
        }
        Map<String, Object> received = new HashMap<>();
        received.put("receive", RateUtil.doubleChainRateTwo(backAmount, 10000) + "万$");
        received.put("chainAdd", RateUtil.doubleChainRateTwo(backAmount - chainBackAmount, 10000) + "万$");
        if (chainBackAmount > 0) {
            received.put("chainRate", RateUtil.doubleChainRateTwo(backAmount - chainBackAmount, chainBackAmount));
        } else {
            received.put("chainRate", 0d);
        }
        Map<String, Object> balanceData = new HashMap<>();
        balanceData.put("receiveBalance", RateUtil.doubleChainRateTwo(receiveBalance, 10000) + "万$");
        balanceData.put("chainAdd", RateUtil.doubleChainRateTwo(receiveBalance - chainBalance, 10000) + "万$");
        if (chainBalance > 0) {
            balanceData.put("chainRate", RateUtil.doubleChainRateTwo(receiveBalance - chainBalance, chainBalance));
        } else {
            balanceData.put("chainRate", 0d);
        }
        Map<String, Object> nextWeekReceivable = new HashMap<>();
        nextWeekReceivable.put("receive", RateUtil.doubleChainRateTwo(weekReceive, 10000) + "万$");
        nextWeekReceivable.put("chainAdd", RateUtil.doubleChainRateTwo(weekReceive - chainWeekReceive, 10000) + "万$");
        if (chainWeekReceive > 0) {
            nextWeekReceivable.put("chainRate", RateUtil.doubleChainRateTwo(weekReceive - chainWeekReceive, chainWeekReceive));
        } else {
            nextWeekReceivable.put("chainRate", 0d);
        }
        nextWeekReceivable.put("startTime", DateUtil.formatDateToString(nextWeekStartTime, DateUtil.SHORT_FORMAT_STR));
        nextWeekReceivable.put("endTime", DateUtil.formatDateToString(nextWeekEndTime, DateUtil.SHORT_FORMAT_STR));

        Map<String, Object> nextMonthReceivable = new HashMap<>();
        nextMonthReceivable.put("receive", RateUtil.doubleChainRateTwo(MothReceive, 10000) + "万$");
        nextMonthReceivable.put("chainAdd", RateUtil.doubleChainRateTwo(MothReceive - chainMothReceive, 10000) + "万$");
        if (chainMothReceive > 0) {
            nextMonthReceivable.put("chainRate", RateUtil.doubleChainRateTwo(MothReceive - chainMothReceive, chainMothReceive));
        } else {
            nextMonthReceivable.put("chainRate", 0d);
        }
        nextMonthReceivable.put("startTime", DateUtil.formatDateToString(nextMonthStartTime, DateUtil.SHORT_FORMAT_STR));
        nextMonthReceivable.put("endTime", DateUtil.formatDateToString(nextMonthEndTime, DateUtil.SHORT_FORMAT_STR));
        Map<String, Object> data = new HashMap<>();
        data.put("receive", total);
        data.put("nextWeekReceivable", nextWeekReceivable);
        data.put("received", received);
        data.put("notReceive", notReceive);
        data.put("balanceData", balanceData);
        data.put("nextMonthReceivable", nextMonthReceivable);
        return new Result<>().setData(data);
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
     * @Author:Lirb
     * @Description 查询主体公司列表和所属事业部
     * @Date:19:40 2017/12/14
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "companyList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object companyList(@RequestBody Map<String, String> map) {
        String companyName = map.get("companyName");
        Result<Object> result = new Result<>();
        List<InquiryAreaVO> list = this.requestCreditService.selectAllCompanyAndOrgList();

        if (StringUtils.isNotBlank(companyName)) {
            if (!companyName.equals("全部") && !companyName.equals("除易瑞全部")) {
                List<InquiryAreaVO> ll = list.parallelStream().filter(vo -> vo.getAreaName().equals(companyName))
                        .collect(Collectors.toList());
                if (ll.size() > 0) {
                    ll.get(0).getCountries().add("除易瑞全部");
                    result.setData(ll.get(0).getCountries());
                } else {
                    return result.setStatus(ResultStatusEnum.COMPANY_NOT_EXIST);
                }
            } else {
                Set<String> countrys = new HashSet<>();
                countrys.add("除易瑞全部");
                for (InquiryAreaVO areaVo : list) {
                    Set<String> set = areaVo.getCountries();
                    for (String country : set) {
                        countrys.add(country);
                    }
                }
                result.setData(countrys);
            }
        } else {
            List<String> companyList = list.parallelStream().map(InquiryAreaVO::getAreaName).collect(Collectors.toList());
            companyList.add("除易瑞全部");
            result.setData(companyList);
        }
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 4.区域明细
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
        Date curDate = new Date();
        Date nextMonthStartTime = DateUtil.getNextMonthFirstDay(curDate);
        Date nextMonthEndTime = DateUtil.getNextMonthLastDay(curDate);
        //应收,已收应收,未收总量
//        Map mapTotal = requestCreditService.selectRequestTotal(startTime, endTime);
        double receive = requestCreditService.selectReceive(null, endTime, null, null, null, null);
        double backAmount = receiveService.selectBackAmount(startTime, endTime, null, null, null, null);
        double totalAmount = receive + backAmount;
        Map<String, Double> mapTotal = new HashMap();
        mapTotal.put("sdT", totalAmount);
        mapTotal.put("sded", backAmount);
        mapTotal.put("sd", receive);
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
//        Map mapCount = requestCreditService.selectByAreaOrCountry(startTime, endTime, map.get("area").toString(), country);
        double ra = requestCreditService.selectReceive(null, endTime, null, null, map.get("area").toString(), country);
        double received = receiveService.selectBackAmount(startTime, endTime, null, null, map.get("area").toString(), country);
        double oa = ra + received;
        Map<String, Double> mapCount = new HashMap();
        mapCount.put("oa", oa);
        mapCount.put("received", received);
        mapCount.put("ra", ra);
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
        double MothReceive = requestCreditService.selectReceive(nextMonthStartTime, nextMonthEndTime, null, null, map.get("area").toString(), country);
        double TotalMothReceive = requestCreditService.selectReceive(nextMonthStartTime, nextMonthEndTime, null, null, null, null);
//        Map mapNext = requestCreditService.selectRequestNextNew(new Date(), nextMonthEndTime, map.get("area").toString(), country);
        Map<String, Double> mapNext = new HashMap();
        mapNext.put("sdT", MothReceive);
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
        if (TotalMothReceive == 0d) {
            conList.add("下月应收-占比0.00%");
        } else {
            conList.add("下月应收-占比" + df.format((nextOrderAmount.doubleValue() / TotalMothReceive) * 100) + "%");
        }
        amountList.add(acOrderAmount.setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
        amountList.add(acReceiveAmount.setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
        amountList.add(acNotreceiveAmount.setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
        amountList.add(nextOrderAmount.setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
        Map<String, Object> data = new HashMap();
        data.put("xAxis", conList);
        data.put("yAxis", amountList);

        return new Result<>().setData(data);
    }

    /**
     * @Author:lirb
     * @Description 主体公司明细
     * @Date:14:41 2017/12/20
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "mainCompanyDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object mainCompanyDetail(@RequestBody Map<String, String> map) throws Exception {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]
        Date curDate = new Date();
        Date nextMonthFirstDay = DateUtil.getNextMonthFirstDay(curDate);
        Date nextMonthEndTime = DateUtil.getNextMonthLastDay(curDate);

        //应收余额
        double receive = this.requestCreditService.selectReceive(null, endDate, map.get("company"), map.get("org"), null, null);
        //回款金额
        double backAmount = receiveService.selectBackAmount(startDate, endDate, map.get("company"), map.get("org"), null, null);
        //应收金额
        double amount = receive + backAmount;
        //获取下月应收
        double nextMothReceive = this.requestCreditService.selectReceive(nextMonthFirstDay, nextMonthEndTime, map.get("company"), map.get("org"), null, null);
        //集团 余额、回款金额、应收金额、下月应收
        double totalReceive = this.requestCreditService.selectReceive(null, endDate, null, null, null, null);
        double TotalBackAmount = receiveService.selectBackAmount(startDate, endDate, null, null, null, null);
        double totalAmount = totalReceive + TotalBackAmount;
        double TotalNextMothReceive = this.requestCreditService.selectReceive(nextMonthFirstDay, nextMonthEndTime, null, null, null, null);

        ArrayList<Double> yAxis = new ArrayList<>();
        yAxis.add(RateUtil.doubleChainRateTwo(receive + backAmount, 1));
        yAxis.add(RateUtil.doubleChainRateTwo(backAmount, 1));
        yAxis.add(RateUtil.doubleChainRateTwo(receive, 1));
        yAxis.add(RateUtil.doubleChainRateTwo(nextMothReceive, 1));

        ArrayList<String> xAxis = new ArrayList<>();
        double totalProportion = 0d;
        double receiveProportion = 0d;
        double backProportion = 0d;
        double nextProportion = 0d;
        if (totalAmount > 0) {
            totalProportion = RateUtil.doubleChainRate(amount, totalAmount);
        }
        if (totalReceive > 0) {
            receiveProportion = RateUtil.doubleChainRate(receive, totalReceive);
        }
        if (TotalBackAmount > 0) {
            backProportion = RateUtil.doubleChainRate(backAmount, TotalBackAmount);
        }
        if (TotalNextMothReceive > 0) {
            nextProportion = RateUtil.doubleChainRate(nextMothReceive, TotalNextMothReceive);
        }
        xAxis.add("应收金额-占比" + df.format(totalProportion * 100) + "%");
        xAxis.add("已收金额-占比" + df.format(backProportion * 100) + "%");
        xAxis.add("应收未收-占比" + df.format(receiveProportion * 100) + "%");
        xAxis.add("下月应收-占比" + df.format(nextProportion * 100) + "%");
        Map<String, Object> data = new HashMap<>();
        data.put("yAxis", yAxis);
        data.put("xAxis", xAxis);
        return new Result<>().setData(data);
    }

    /**
     * @Author:lirb
     * @Description 分类统计
     * @Date:14:41 2017/12/20
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "catesDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object catesDetail(@RequestBody Map<String, String> map) throws Exception {
        // 获取参数并转换成时间格式
        Date startDate = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        endDate = NewDateUtil.plusDays(endDate, 1); // 得到的时间区间为(startDate,endDate]
        //区域应收账款明细
        List<Map<String, Object>> areaReceiveList = this.requestCreditService.selectReceiveGroupByArea(null, endDate);
        List<Map<String, Object>> areaBackList = receiveService.selectBackAmountGroupByArea(startDate, endDate);
        Map<String, Object> areaResult = this.getOrderAmountDetail(startDate, endDate, areaReceiveList, areaBackList, 1);
        //主体公司应收账款明细
        List<Map<String, Object>> companyReceiveList = this.requestCreditService.selectReceiveGroupByCompany(null, endDate);
        List<Map<String, Object>> companyBackList = receiveService.selectBackAmountGroupByCompany(startDate, endDate);
        Map<String, Object> companyResult = this.getOrderAmountDetail(startDate, endDate, companyReceiveList, companyBackList, 2);
        //主体公司应收账款明细
        List<Map<String, Object>> orgReceiveList = this.requestCreditService.selectReceiveGroupByOrg(null, endDate);
        List<Map<String, Object>> orgpanyBackList = receiveService.selectBackAmountGroupByOrg(startDate, endDate);
        Map<String, Object> orgResult = this.getOrderAmountDetail(startDate, endDate, orgReceiveList, orgpanyBackList, 3);
        Map<String, Object> data = new HashMap<>();
        data.put("area", areaResult);
        data.put("company", companyResult);
        data.put("busUnit", orgResult);
        return new Result<>().setData(data);
    }

    /**
     * @Author:lirb
     * @Description 合并应收账款信息
     * @Date:14:41 2017/12/20
     * @modified By
     */
    private Map<String, Object> getOrderAmountDetail(Date startTime, Date endTime, List<Map<String, Object>> receiveList, List<Map<String, Object>> backList, int type) {
        double receive = requestCreditService.selectReceive(null, endTime, null, null, null, null);
        double backAmount = receiveService.selectBackAmount(startTime, endTime, null, null, null, null);
        double totalAmount = receive + backAmount;
        List<String> areas = new ArrayList<>();
        List<Double> areaAmounts = new ArrayList<>();
        if (receiveList != null && receiveList.size() > 0) {
            for (Map<String, Object> m : receiveList) {
                double amount1 = Double.parseDouble(m.get("receiveAmount").toString());
                String groupBy = "";
                if (type == 1) {
                    groupBy = String.valueOf(m.get("area"));
                } else if (type == 2) {
                    groupBy = String.valueOf(m.get("company"));
                } else if (type == 3) {
                    groupBy = String.valueOf(m.get("org"));
                }
                areas.add(groupBy);
                areaAmounts.add(RateUtil.doubleChainRateTwo(amount1, 1d));
            }
        }
        if (backList != null && backList.size() > 0) {
            for (Map<String, Object> m : backList) {
                String groupBy = "";
                if (type == 1) {
                    groupBy = String.valueOf(m.get("area"));
                } else if (type == 2) {
                    groupBy = String.valueOf(m.get("company"));
                } else if (type == 3) {
                    groupBy = String.valueOf(m.get("org"));
                }
                double amount1 = Double.parseDouble(m.get("backAmount").toString());
                if (areas.contains(groupBy)) {
                    int index = 0;
                    for (int i = 0; i < areas.size(); i++) {
                        if (areas.get(i).equals(groupBy)) {
                            index = i;
                        }
                    }
                    double sIndex = 0d;
                    if (areaAmounts.get(index) != null) {
                        sIndex = areaAmounts.get(index);
                    }
                    areaAmounts.set(index, sIndex + RateUtil.doubleChainRateTwo(amount1, 1d));
                } else {
                    areas.add(groupBy);
                    areaAmounts.add(RateUtil.doubleChainRateTwo(amount1, 1d));
                }
            }
        }
        //排序
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (areas.size() > 0 && areaAmounts.size() > 0 && areaAmounts.size() == areas.size()) {
            for (int i = 0; i < areas.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("area", areas.get(i));
                map.put("areaAmount", areaAmounts.get(i));
                mapList.add(map);
            }
        }
        if (mapList.size() > 1) {
            mapList.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                    double areaAmount2 = 0d;
                    double areaAmount1 = 0d;
                    if (o2.get("areaAmount") != null) {
                        areaAmount2 = Double.parseDouble(o2.get("areaAmount").toString());
                    }
                    if (o1.get("areaAmount") != null) {
                        areaAmount1 = Double.parseDouble(o1.get("areaAmount").toString());
                    }
                    return (int) areaAmount2 - (int) areaAmount1;
                }
            });
            areas.clear();
            areaAmounts.clear();
            for (Map<String, Object> m : mapList) {
                String area = null;
                Double areaAmount = 0d;
                if (m.get("area") != null) {
                    area = m.get("area").toString();
                }
                if (m.get("areaAmount") != null) {
                    areaAmount = (Double) m.get("areaAmount");
                }
                if (area != null) {
                    areas.add(area);
                    areaAmounts.add(RateUtil.doubleChainRateTwo(areaAmount, 1d));
                }
            }
        }

        //占比
        List<Double> proportions = new ArrayList<>();
        if (areaAmounts != null && areaAmounts.size() > 0) {
            for (Double amount : areaAmounts) {
                if (totalAmount > 0) {
                    proportions.add(RateUtil.doubleChainRate(amount, totalAmount));
                }
            }
        }
        //封装结果
        Map<String, Object> dataResult = new HashMap<>();
        if (type == 1) {
            dataResult.put("areaAmount", areaAmounts);
            dataResult.put("area", areas);
            dataResult.put("areaProportion", proportions);
        } else if (type == 2) {
            dataResult.put("companyAmount", areaAmounts);
            dataResult.put("company", areas);
            dataResult.put("companyProportion", proportions);
        } else if (type == 3) {
            dataResult.put("busUnitAmount", areaAmounts);
            dataResult.put("busUnit", areas);
            dataResult.put("busUnitProportion", proportions);
        }
        return dataResult;
    }


    /**
     * @Author:lirb
     * @Description 账龄统计饼图
     * @Date:14:41 2018/4/3
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "agingPie", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object agingPie(@RequestBody Map<String, String> map) {
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, "yyyy/MM/dd HH:mm:ss");
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        map.put("startTime", fullStartTime);
        map.put("endTime", fullEndTime);
        Map<String, Object> data = requestCreditService.selectAgingSummary(map);
        return new Result<>(data);
    }


    /**
     * @Author:lirb
     * @Description 账龄统计
     * @Date:14:41 2018/4/3
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "agingDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object agingDetail(@RequestBody Map<String, String> map) {
        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(map.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, "yyyy/MM/dd HH:mm:ss");
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        map.put("startTime", fullStartTime);
        map.put("endTime", fullEndTime);
        List<Map<String, Object>> data = requestCreditService.selectAgingSummaryGroupByCompanyAndOrgAndArea(map);
        return new Result<>(data);
    }

    /**
     * @Author:lirb
     * @Description 导出账龄分析数据到execel
     * @Date:14:41 2018/4/3
     * @modified By
     */
    @RequestMapping(value = "exportAgingToExecel")
    public Object exportAgingToExecel(Date startTime, Date endTime, HttpServletResponse response) {
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        endTime = DateUtil.getOperationTime(endTime, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, "yyyy/MM/dd HH:mm:ss");
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        Map<String, String> map = new HashMap<>();
        map.put("startTime", fullStartTime);
        map.put("endTime", fullEndTime);
        //获取数据
        List<Map<String, Object>> data = requestCreditService.selectAgingSummaryGroupByCompanyAndOrgAndArea(map);
        //excel标题
        String[] title = new String[]{"销售模式", "大区", "30天以内", "占比(%)", "30-60天", "占比(%)",
                "60-90天", "占比(%)", "90-120天", "占比(%)", "120-150天", "占比(%)",
                "150-180天", "占比(%)", "180天以上", "占比(%)", "合计"};

        //excel文件名
        String fileName = "账龄分析统计" + System.currentTimeMillis() + ".xlsx";
        XSSFWorkbook wb = requestCreditService.exportAgingData(data);
        try {
            setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            logger.debug("异常:" + e.getMessage(), e);
            return null;
        }
        //输出Execel


    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) throws Exception {

        fileName = new String(fileName.getBytes(), "ISO8859-1");
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");

    }
}
