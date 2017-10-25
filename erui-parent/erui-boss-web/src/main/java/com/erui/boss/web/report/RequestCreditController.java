package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.comm.DateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.service.RequestCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
      * @Description 应收账款
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
    @ResponseBody
    @RequestMapping(value= "receiveDetail",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object totalReceive(@RequestBody Map<String,Object> days){
        //当前时期
        Date startTime = DateUtil.recedeTime((int)days.get("days"));
        //环比时段
        Date chainDate = DateUtil.recedeTime((int)days.get("days")*2);
        //当前时段
        Map mapMount =requestCreditService.selectRequestTotal(startTime,new Date());
        //环比
        Map mapChainMount = requestCreditService.selectRequestTotal(chainDate,startTime);
        //应收金额
        BigDecimal orderAmount= new  BigDecimal(mapMount.get("sdT").toString());
        //已收金额
        BigDecimal receiveAmount = new BigDecimal(mapMount.get("sded").toString());
        //应收未收
        BigDecimal notreceiveAmount = new BigDecimal(mapMount.get("sd").toString());
        //环比应收金额
        BigDecimal chainOrderAmount= new  BigDecimal(mapChainMount.get("sdT").toString());
        //环比已收金额
        BigDecimal chainReceiveAmount = new BigDecimal(mapChainMount.get("sded").toString());
        //环比应收未收
        BigDecimal chainNotreceiveAmount = new BigDecimal(mapChainMount.get("sd").toString());
        Double orederAmountAdd = orderAmount.doubleValue() - chainOrderAmount.doubleValue();
        //应收账款
        Map<String,Object> receivable = new HashMap<>();
        receivable.put("receive",df.format(orderAmount)+"万$");
        receivable.put("chainAdd",df.format(orederAmountAdd)+"万$");
        receivable.put("chainRate",RateUtil.doubleChainRate(orederAmountAdd,chainOrderAmount.doubleValue()));
        Double NotReceiveAmountAdd =  notreceiveAmount.doubleValue() - chainNotreceiveAmount.doubleValue() ;
        //应收未收
        Map<String,Object> notReceive = new HashMap<>();
        notReceive.put("receive",df.format(notreceiveAmount)+"万$");
        notReceive.put("chainAdd",df.format(NotReceiveAmountAdd)+"万$");
        notReceive.put("chainRate",RateUtil.doubleChainRate(NotReceiveAmountAdd,chainNotreceiveAmount.doubleValue()));
        Double ReceiveAmountAdd = receiveAmount.doubleValue() - chainReceiveAmount.doubleValue();
        //应收已收
        Map<String,Object> received = new HashMap<>();
        received.put("receive",df.format(receiveAmount)+"万$");
        received.put("chainAdd",df.format(ReceiveAmountAdd)+"万$");
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
      * @Description 趋势图
      * @Date:9:25 2017/10/24
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value= "tendencyChart",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object tendencyChart(@RequestBody Map<String,Object> map){
        //当前时期
        int days = (int)map.get("days");
        Date startTime = DateUtil.recedeTime(days);
        Date nextTime = DateUtil.recedeTime(-30);
        List<Map> requestMap = requestCreditService.selectRequestTrend(startTime,new Date());
        List<Map> nextMap = requestCreditService.selectRequestNext(new Date(),nextTime);
        List<Double> receivableList = new ArrayList<>();
        List<Double> notReceiveList = new ArrayList<>();
        List<Double> receivedList = new ArrayList<>();
        List<Double> nextList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        List<String> nextDate = new ArrayList<>();
        Map<String,Map<String,Double>> sqlDate = null;
        Map<String,Double> linkData = null;
        //遍历下月应收
        for (Map map3:nextMap) {
            sqlDate = new HashMap<>();
            linkData = new HashMap<>();
            BigDecimal nextReceivable = new BigDecimal(map3.get("order_amount").toString());
            Date date2 = (Date) map3.get("back_date");
            String dateString = DateUtil.format("MM月dd日",date2);
            linkData.put("nextReceivable",nextReceivable.doubleValue());
            sqlDate.put(dateString,linkData);
        }
        for (int i = 0; i < 31; i++){
            Date datetime = DateUtil.recedeTime(-i);
            String date = DateUtil.format("MM月dd日",datetime);
            if (sqlDate.containsKey(date)){
                nextDate .add(date);
                nextList.add(sqlDate.get(date).get("nextReceivable"));
            }else {
                nextDate.add(date);
                nextList.add(0.0);
            }
        }
        //应收，已收，未收
        for (Map map2:requestMap) {
            linkData = new HashMap<>();
            sqlDate = new HashMap<>();
            BigDecimal receivable = new BigDecimal(map2.get("order_amount").toString());
            BigDecimal notReceive = new BigDecimal(map2.get("receive_amount").toString());
            BigDecimal received = new BigDecimal(map2.get("received").toString());
            Date date2 = (Date) map2.get("create_at");
            String dateString = DateUtil.format("MM月dd日",date2);
            linkData.put("receivable",receivable.doubleValue());
            linkData.put("notReceive",notReceive.doubleValue());
            linkData.put("received",received.doubleValue());
            sqlDate.put(dateString,linkData);
        }
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.recedeTime(days - (i+1) );
            String date = DateUtil.format("MM月dd日",datetime);
            if (sqlDate.containsKey(date)){
                dateList.add(date);
                receivableList.add(sqlDate.get(date).get("receivable"));
                notReceiveList.add(sqlDate.get(date).get("notReceive"));
                receivedList.add(sqlDate.get(date).get("received"));
            }else {
                dateList.add(date);
                receivableList.add(0.0);
                notReceiveList.add(0.0);
                receivedList.add(0.0);
            }
        }
        String [] s = {"应收账款","应收未收","应收已收","下月应收"};
        Map<String,Object> data = new HashMap();
        if (map.get("receiveName").equals("receivable")){
            data.put("legend",s[0]);
            data.put("xAxis",dateList);
            data.put("yAxis",receivableList);

        }else if (map.get("receiveName").equals("notReceive")){
            data.put("legend",s[1]);
            data.put("xAxis",dateList);
            data.put("yAxis",notReceiveList);
        }else if(map.get("receiveName").equals("received")){
            data.put("legend",s[2]);
            data.put("xAxis",dateList);
            data.put("yAxis",receivedList);
        }else {
            data.put("legend",s[3]);
            data.put("xAxis",nextDate);
            data.put("yAxis",nextList);
        }
        Map<String,Object> result = new HashMap();
        result.put("data",data);
        result.put("code",200);
        return result;
    }
    @ResponseBody
    @RequestMapping(value= "queryArea",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object queryArea(){
        Map<String,Object> areaMap = requestCreditService.selectArea();
        Result<Map<String,Object>> result = new Result<>(areaMap);
        return result;
    }
    @ResponseBody
    @RequestMapping(value= "queryCoutry",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object queryCoutry(@RequestBody Map<String,Object> map){
        Map<String,Object> areaCountry = requestCreditService.selectCountry(map.get("area").toString());
        Result<Map<String,Object>> result = new Result<>(areaCountry);
        return result;
    }
    @ResponseBody
    @RequestMapping(value= "areaDetail",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object areaDetail(@RequestBody Map<String,Object> map){
        Date nextTime = DateUtil.recedeTime(-30);
        //总量
        Map mapTotal = requestCreditService.selectRequestTotal(null,null);
        //应收金额
        BigDecimal totalOrderAmount= new  BigDecimal(mapTotal.get("sdT").toString());
        //已收金额
        BigDecimal totalReceiveAmount = new BigDecimal(mapTotal.get("sded").toString());
        //应收未收
        BigDecimal totalNotreceiveAmount = new BigDecimal(mapTotal.get("sd").toString());
        //根据区域
        Map mapCount = requestCreditService.selectByAreaOrCountry(map.get("area").toString(),map.get("country").toString());
        //应收金额
        BigDecimal acOrderAmount= new  BigDecimal( mapCount.get("oa").toString());
        //已收金额
        BigDecimal acReceiveAmount = new BigDecimal( mapCount.get("received").toString());
        //应收未收
        BigDecimal acNotreceiveAmount = new BigDecimal( mapCount.get("ra").toString());
        //下月
        Map mapNext = requestCreditService.selectRequestTotal(new Date(),nextTime);
        //应收金额
        BigDecimal nextOrderAmount= new  BigDecimal(mapNext.get("sdT").toString());
        List<String> conList = new ArrayList<>();
        List<Double> amountList = new ArrayList<>();
        conList.add("应收金额-占比"+RateUtil.doubleChainRate(acOrderAmount.doubleValue(),totalOrderAmount.doubleValue())*100+"%");
        conList.add("已收金额-占比"+RateUtil.doubleChainRate(acReceiveAmount.doubleValue(),totalReceiveAmount.doubleValue())*100+"%");
        conList.add("应收未收-占比"+RateUtil.doubleChainRate(acNotreceiveAmount.doubleValue(),totalNotreceiveAmount.doubleValue())*100+"%");
        conList.add("下月应收-占比"+RateUtil.doubleChainRate(nextOrderAmount.doubleValue(),totalOrderAmount.doubleValue())*100+"%");
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
