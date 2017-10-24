package com.erui.boss.web.report;

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
import java.util.*;

/**
 * @Author:SHIGS
 * @Description 应收账款
 * @Date Created in 17:06 2017/10/23
 * @modified By
 */
@Controller
@RequestMapping("receivable")
public class RequestCreditController {
    @Autowired
    private RequestCreditService requestCreditService;

     /**
      * @Author:SHIGS
      * @Description 应收累计
      * @Date:17:26 2017/10/23
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value= "totalReceive",method = RequestMethod.POST)
    public Object totalReceive(){
        Map mapMount =requestCreditService.selectTotal();
        BigDecimal receiveAmount = new  BigDecimal(mapMount.get("sd").toString());
        BigDecimal orderAmount = new BigDecimal(mapMount.get("ed").toString());
        Double received = orderAmount.doubleValue() - receiveAmount.doubleValue();
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        data.put("notReceive", RateUtil.doubleChainRate(receiveAmount.doubleValue(),10000)+"万$");
        data.put("received", RateUtil.doubleChainRate(received,10000)+"万$");
        data.put("totalReceive", RateUtil.doubleChainRate(orderAmount.doubleValue(),10000)+"万$");
        result.put("data",data);
        result.put("code",200);
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
        Double orederAmountAdd = orderAmount.doubleValue() - chainOrderAmount.doubleValue()  ;
        //应收账款
        Map<String,Object> receivable = new HashMap<>();
        receivable.put("receive",orderAmount);
        receivable.put("chainAdd",orederAmountAdd+"万$");
        receivable.put("chainRate",RateUtil.doubleChainRate(orederAmountAdd,chainOrderAmount.doubleValue()));
        Double NotReceiveAmountAdd =  notreceiveAmount.doubleValue() - chainNotreceiveAmount.doubleValue() ;
        //应收未收
        Map<String,Object> notReceive = new HashMap<>();
        notReceive.put("receive",notreceiveAmount);
        notReceive.put("chainAdd",NotReceiveAmountAdd);
        notReceive.put("chainRate",RateUtil.doubleChainRate(NotReceiveAmountAdd,chainNotreceiveAmount.doubleValue()));
        Double ReceiveAmountAdd = receiveAmount.doubleValue() - chainReceiveAmount.doubleValue();
        //应收已收
        Map<String,Object> received = new HashMap<>();
        received.put("receive",receiveAmount);
        received.put("chainAdd",ReceiveAmountAdd+"万$");
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
        //下月应收，已收，未收
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
}
