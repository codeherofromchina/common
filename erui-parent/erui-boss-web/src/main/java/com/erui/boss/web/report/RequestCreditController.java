package com.erui.boss.web.report;

import com.erui.boss.web.util.DefaultExceptionHandler;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.DateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.service.RequestCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
        //环比
        Map mapChainMount = requestCreditService.selectRequestTotal(chainDate,startTime);
        if (mapChainMount == null){
            mapChainMount = new HashMap();
            mapChainMount.put("sdT",0);
            mapChainMount.put("sded",0);
            mapChainMount.put("sd",0);
        }
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
      * @Description 应收账款趋势图
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
    @ResponseBody
    @RequestMapping(value= "areaDetail",method = RequestMethod.POST,produces={"application/json;charset=utf-8"})
    public Object areaDetail(@RequestBody Map<String,Object> map) throws Exception {
        if (!map.containsKey("area")||!map.containsKey("country")) {
            throw new MissingServletRequestParameterException("area || country","String");
        }
        Date nextTime = DateUtil.recedeTime(-30);
        //总量
        Map mapTotal = requestCreditService.selectRequestTotal(null,null);
        if(mapTotal == null){
            mapTotal = new HashMap();
            mapTotal.put("sdT",0);
            mapTotal.put("sded",0);
            mapTotal.put("sd",0);
        }
        //应收金额
        BigDecimal totalOrderAmount= new  BigDecimal(mapTotal.get("sdT").toString());
        //已收金额
        BigDecimal totalReceiveAmount = new BigDecimal(mapTotal.get("sded").toString());
        //应收未收
        BigDecimal totalNotreceiveAmount = new BigDecimal(mapTotal.get("sd").toString());
        //根据区域
        Map mapCount = requestCreditService.selectByAreaOrCountry(map.get("area").toString(),map.get("country").toString());
        if (mapCount == null){
            mapTotal = new HashMap();
            mapTotal.put("oa",0);
            mapTotal.put("received",0);
            mapTotal.put("ra",0);
        }
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
