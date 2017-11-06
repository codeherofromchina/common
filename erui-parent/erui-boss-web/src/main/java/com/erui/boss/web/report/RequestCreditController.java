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
    @ResponseBody
    @RequestMapping(value= "totalReceive",method = RequestMethod.POST)
    public Object totalReceive(){
        Map mapMount =requestCreditService.selectTotal();
        Result<Map<String,Object>> result = new Result<>(mapMount);
        return result;
    }
    /**
     * @Author:SHIGS
     * @Description 2.应收账款图
     * @Date:14:40 2017/10/31
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value= "receiveDetail",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object totalReceive(@RequestBody Map<String,Object> map) throws Exception {
        if (!map.containsKey("days")) {
            throw new MissingServletRequestParameterException("days","String");
        }
        //当前时期
        int days = Integer.parseInt(map.get("days").toString());
        //当前时期
        Date startTime = DateUtil.recedeTime(days);
        //环比时段
        Date chainDate = DateUtil.recedeTime(days*2);
        //当前时段
        Map mapMount =requestCreditService.selectRequestTotal(startTime,new Date());
        if(mapMount == null){
            mapMount = new HashMap();
            mapMount.put("sdT",0);
            mapMount.put("sded",0);
            mapMount.put("sd",0);
        }
        //应收金额
        BigDecimal orderAmount = null;
        if(!mapMount.containsKey("sdT")){
            mapMount.put("sdT",0);
            orderAmount = new  BigDecimal(mapMount.get("sdT").toString());
        }else {
            orderAmount = new  BigDecimal(mapMount.get("sdT").toString());
        }
        //已收金额
        BigDecimal receiveAmount = null;
        if(!mapMount.containsKey("sded")){
            mapMount.put("sded",0);
            receiveAmount = new  BigDecimal(mapMount.get("sded").toString());
        }else {
            receiveAmount = new  BigDecimal(mapMount.get("sded").toString());
        }
        //应收未收
        BigDecimal notreceiveAmount = null;
        if(!mapMount.containsKey("sd")){
            mapMount.put("sd",0);
            notreceiveAmount = new  BigDecimal(mapMount.get("sd").toString());
        }else {
            notreceiveAmount = new  BigDecimal(mapMount.get("sd").toString());
        }
        //环比
        Map mapChainMount = requestCreditService.selectRequestTotal(chainDate,startTime);
        if (mapChainMount == null){
            mapChainMount = new HashMap();
            mapChainMount.put("sdT",0);
            mapChainMount.put("sded",0);
            mapChainMount.put("sd",0);
        }
        //环比应收金额
        BigDecimal chainOrderAmount = null;
        if(!mapChainMount.containsKey("sdT")){
            mapChainMount.put("sdT",0);
            chainOrderAmount = new  BigDecimal(mapChainMount.get("sdT").toString());
        }else {
            chainOrderAmount = new  BigDecimal(mapChainMount.get("sdT").toString());
        }
        //环比已收金额
        BigDecimal chainReceiveAmount = null;
        if(!mapChainMount.containsKey("sded")){
            mapChainMount.put("sded",0);
            chainReceiveAmount = new  BigDecimal(mapChainMount.get("sded").toString());
        }else {
            chainReceiveAmount = new  BigDecimal(mapChainMount.get("sded").toString());
        }
        //应收未收
        BigDecimal chainNotreceiveAmount = null;
        if(!mapChainMount.containsKey("sd")){
            mapChainMount.put("sd",0);
            chainNotreceiveAmount = new  BigDecimal(mapChainMount.get("sd").toString());
        }else {
            chainNotreceiveAmount = new  BigDecimal(mapChainMount.get("sd").toString());
        }
        Double orederAmountAdd = orderAmount.doubleValue() - chainOrderAmount.doubleValue();
        //应收账款
        Map<String,Object> receivable = new HashMap<>();
        receivable.put("receive",df.format(orderAmount.doubleValue()/10000)+"万$");
        receivable.put("chainAdd",df.format(orederAmountAdd/10000)+"万$");
        receivable.put("chainRate",RateUtil.doubleChainRate(orederAmountAdd,chainOrderAmount.doubleValue()));
        Double NotReceiveAmountAdd =  notreceiveAmount.doubleValue() - chainNotreceiveAmount.doubleValue() ;
        //应收未收
        Map<String,Object> notReceive = new HashMap<>();
        notReceive.put("receive",df.format(notreceiveAmount.doubleValue()/10000)+"万$");
        notReceive.put("chainAdd",df.format(NotReceiveAmountAdd/10000)+"万$");
        notReceive.put("chainRate",RateUtil.doubleChainRate(NotReceiveAmountAdd,chainNotreceiveAmount.doubleValue()));
        Double ReceiveAmountAdd = receiveAmount.doubleValue() - chainReceiveAmount.doubleValue();
        //应收已收
        Map<String,Object> received = new HashMap<>();
        received.put("receive",df.format(receiveAmount.doubleValue()/10000)+"万$");
        received.put("chainAdd",df.format(ReceiveAmountAdd/10000)+"万$");
        received.put("chainRate",RateUtil.doubleChainRate(ReceiveAmountAdd,chainReceiveAmount.doubleValue()));

        Map<String,Object> result = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        data.put("receive",receivable);
        data.put("received",received);
        data.put("notReceive",notReceive);
        result.put("data",data);
        result.put("code",200);
        return result;
    }
     /**
      * @Author:SHIGS
      * @Description 3.应收账款趋势图
      * @Date:9:25 2017/10/24
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value= "tendencyChart",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object tendencyChart(@RequestBody Map<String,Object> map) throws Exception {
        if (!map.containsKey("days")||!map.containsKey("receiveName")) {
            throw new MissingServletRequestParameterException("days || receiveName","String");
        }
        //当前时期
        int days = Integer.parseInt(map.get("days").toString());
        Map<String,Object> dataMap = requestCreditService.selectRequestTrend(days,map.get("receiveName").toString());
        Result<Map<String,Object>> result = new Result<>(dataMap);
        return result;
    }
     /**
      * @Author:SHIGS
      * @Description 查询销售区域
      * @Date:19:39 2017/10/30
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value= "queryArea",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object queryArea(){
        Map<String,Object> areaMap = requestCreditService.selectArea();
        Result<Map<String,Object>> result = new Result<>(areaMap);
        return result;
    }
     /**
      * @Author:SHIGS
      * @Description 根据销售区域查询国家
      * @Date:19:40 2017/10/30
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value= "queryCoutry",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object queryCoutry(@RequestBody Map<String,Object> map) throws Exception {
        if (!map.containsKey("area")) {
            throw new MissingServletRequestParameterException("area","String");
        }
        Map<String,Object> areaCountry = requestCreditService.selectCountry(map.get("area").toString());
        Result<Map<String,Object>> result = new Result<>(areaCountry);
        return result;
    }
     /**
      * @Author:SHIGS
      * @Description 4.区域明细图
      * @Date:14:41 2017/10/31
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value= "areaDetail",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object areaDetail(@RequestBody Map<String,Object> map) throws Exception {
        if (!map.containsKey("area")||!map.containsKey("country")) {
            throw new MissingServletRequestParameterException("area || country","String");
        }
        Date startTime = DateUtil.recedeTime(7);
        Date nextTime = DateUtil.recedeTime(-30);
        //应收,已收应收,未收总量
        Map mapTotal = requestCreditService.selectRequestTotal(startTime,new Date());
        if(mapTotal == null){
            mapTotal = new HashMap();
            mapTotal.put("sdT",0);
            mapTotal.put("sded",0);
            mapTotal.put("sd",0);
        }
        //应收金额
        BigDecimal totalOrderAmount = null;
        if(!mapTotal.containsKey("sdT")){
            mapTotal.put("sdT",0);
            totalOrderAmount = new  BigDecimal(mapTotal.get("sdT").toString());
        }else {
            totalOrderAmount = new  BigDecimal(mapTotal.get("sdT").toString());
        }
        //已收金额
        BigDecimal totalReceiveAmount = null;
        if(!mapTotal.containsKey("sded")){
            mapTotal.put("sded",0);
            totalReceiveAmount = new  BigDecimal(mapTotal.get("sded").toString());
        }else {
            totalReceiveAmount = new  BigDecimal(mapTotal.get("sded").toString());
        }
        //应收未收
        BigDecimal totalNotreceiveAmount = null;
        if(!mapTotal.containsKey("sd")){
            mapTotal.put("sd",0);
            totalNotreceiveAmount = new  BigDecimal(mapTotal.get("sd").toString());
        }else {
            totalNotreceiveAmount = new  BigDecimal(mapTotal.get("sd").toString());
        }
        String country = "";
        Object obj=map.get("country");
        if(obj!=null){
            country =obj.toString();
        }
        //根据区域或者国家
        Map mapCount = requestCreditService.selectByAreaOrCountry(startTime,new Date(),map.get("area").toString(),country);
        if (mapCount == null){
            mapTotal = new HashMap();
            mapTotal.put("oa",0);
            mapTotal.put("received",0);
            mapTotal.put("ra",0);
        }
        //应收金额
        BigDecimal acOrderAmount =  null;
        if (!mapCount.containsKey("oa")){
            mapCount.put("oa",0);
            acOrderAmount = new  BigDecimal( mapCount.get("oa").toString());
        }else {
            acOrderAmount = new  BigDecimal( mapCount.get("oa").toString());
        }
        //已收金额
        BigDecimal acReceiveAmount =  null;
        if (!mapCount.containsKey("received")){
            mapCount.put("oa",0);
            acReceiveAmount = new  BigDecimal( mapCount.get("received").toString());
        }else {
            acReceiveAmount = new  BigDecimal( mapCount.get("received").toString());
        }
        BigDecimal acNotreceiveAmount = null;
        if (!mapCount.containsKey("ra")){
            mapCount.put("ra",0);
            acNotreceiveAmount = new  BigDecimal( mapCount.get("ra").toString());
        }else {
            acNotreceiveAmount = new  BigDecimal( mapCount.get("ra").toString());
        }
        //下月应收
        Map mapNext = requestCreditService.selectRequestNext(new Date(),nextTime,map.get("area").toString(),country);
        if (mapNext == null){
            mapNext.put("sdT",0);
        }
        BigDecimal nextOrderAmount = null;
        if (!mapNext.containsKey("sdT")){
            mapNext.put("sdT",0);
            nextOrderAmount = new  BigDecimal(mapNext.get("sdT").toString());
        }else {
            nextOrderAmount = new  BigDecimal(mapNext.get("sdT").toString());
        }
        List<String> conList = new ArrayList<>();
        List<Double> amountList = new ArrayList<>();
        conList.add("应收金额-占比"+df.format((acOrderAmount.doubleValue()/totalOrderAmount.doubleValue())*100)+"%");
        conList.add("已收金额-占比"+df.format((acReceiveAmount.doubleValue()/totalReceiveAmount.doubleValue())*100)+"%");
        conList.add("应收未收-占比"+df.format((acNotreceiveAmount.doubleValue()/totalNotreceiveAmount.doubleValue())*100)+"%");
        conList.add("下月应收-占比"+df.format((nextOrderAmount.doubleValue()/totalOrderAmount.doubleValue())*100)+"%");
        amountList.add(acOrderAmount.doubleValue());
        amountList.add(acReceiveAmount.doubleValue());
        amountList.add(acNotreceiveAmount.doubleValue());
        amountList.add(nextOrderAmount.doubleValue());
        Map<String,Object> data = new HashMap();
        data.put("xAxis",conList);
        data.put("yAxis",amountList);
        Map<String,Object> result = new HashMap<>();
        result.put("data",data);
        result.put("code",200);
       return result;
    }
}
