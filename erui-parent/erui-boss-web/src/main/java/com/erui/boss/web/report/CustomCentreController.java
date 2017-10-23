package com.erui.boss.web.report;

import com.erui.comm.DateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.model.InquiryCount;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.OrderCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 客户中心
 * Created by lirb on 2017/10/20.
 */

@Controller
@RequestMapping("/customCentre")
public class CustomCentreController {
    @Autowired
    private InquiryCountService inquiryService;
    @Autowired
    private OrderCountService orderService;


    /*
    * 询单总览
    * */
    @ResponseBody
    @RequestMapping(value = "/inquiryPandect")
    public Object inquiryPandect(int day){
        Map<String,Object> result=new HashMap<String,Object>();


        Date startTime = DateUtil.recedeTime(day);
        Date chainDate = DateUtil.recedeTime(day*2);//环比起始时间
        Map<String,Object> Datas=new HashMap<String,Object>();//询单统计信息
        Map<String,Object> inquiryMap=new HashMap<String,Object>();//询单统计信息
        Map<String,Object> proIsOilMap=new HashMap<String,Object>();//油气产品分析
        Map<String,Object> proTop3Map=new HashMap<String,Object>();//产品分类Top3

        //当期询单数
        int count = inquiryService.inquiryCountByTime(startTime, new Date(),null,0,0);
        //当期询单金额
        double amount = inquiryService.inquiryAmountByTime(startTime, new Date());
        //当期询单数环比chain
        int chainCount = inquiryService.inquiryCountByTime(chainDate, startTime,null,0,0);
        int chain=count-chainCount;
        double chainRate = RateUtil.intChainRate(chain, chainCount);//环比
        inquiryMap.put("count",count);
        inquiryMap.put("amount",amount);
        inquiryMap.put("chainAdd",chain);
        inquiryMap.put("chainRate",count);


        //当前产品总数量
        int proCount = inquiryService.selectProCountByIsOil(startTime, new Date(),"");
        int oilCount = inquiryService.selectProCountByIsOil(startTime, new Date(),"油气");
        int notOilCount = inquiryService.selectProCountByIsOil(startTime, new Date(),"非油气");
        //同期产品总数量
        int chainOilCount = inquiryService.selectProCountByIsOil(chainDate, startTime,"油气");
        int chainNotOilCount = inquiryService.selectProCountByIsOil(chainDate, startTime,"非油气");
        proIsOilMap.put("oil",oilCount);
        proIsOilMap.put("notOil",notOilCount);
        proIsOilMap.put("oiProportionl",RateUtil.intChainRate(oilCount,oilCount+notOilCount));
        proIsOilMap.put("chainRate",RateUtil.intChainRate(oilCount-chainOilCount,chainOilCount));


        Map<String,Object> params=new HashMap<String,Object>();
        params.put("startTime",DateUtil.recedeTime(day));
        params.put("endTime",new Date());
        List<Map<String,Object>> list = inquiryService.selectProTop3(params);
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                Map<String, Object> top3 = list.get(i);
                BigDecimal s = new BigDecimal(top3.get("proCount").toString());
                int top3Count = s.intValue();
                top3.put("proProportionl",RateUtil.intChainRate(top3Count,proCount));
                proTop3Map.put("top"+(i+1),top3);
            }
        }

        Datas.put("inquiry",inquiryMap);
        Datas.put("isOil",proIsOilMap);
        Datas.put("proTop3",proTop3Map);
        //查询Top3产品数据
        result.put("code",200);
        result.put("data",Datas);
        return result;
    }

    /*
  * 订单总览
  * */
    @ResponseBody
    @RequestMapping(value = "/orderPandect")
    public Object orderPandect(int day){
        Map<String,Object> result=new HashMap<String,Object>();

        Date startTime = DateUtil.recedeTime(day);
        Date chainDate = DateUtil.recedeTime(day*2);//环比起始时间
        Map<String,Object> Datas=new HashMap<String,Object>();//订单统计信息


        //当期订单数
        int count = orderService.orderCountByTime(startTime, new Date(),"");
        //当期询单金额
        double amount = orderService.orderAmountByTime(startTime, new Date());
        //上期订单数
        int chainCount = orderService.orderCountByTime(chainDate,startTime,"");
        double chainRate=0.00;
        if(chainCount>0){
            chainRate=RateUtil.intChainRate(count-chainCount,chainCount);
        }
        Map<String,Object> orderMap=new HashMap<String,Object>();//询单统计信息
        orderMap.put("count",count);
        orderMap.put("amount",amount);
        orderMap.put("chainAdd",count-chainCount);
        orderMap.put("chainRate",chainRate);

        //利润率
        Double profitRate = orderService.selectProfitRate(startTime, new Date());
        Double chainProfitRate = orderService.selectProfitRate(chainDate, startTime);
        Map<String,Object> profitMap=new HashMap<String,Object>();
        profitMap.put("profitRate",profitRate);
        profitMap.put("chainRate",chainProfitRate);
        //成单率
        double successOrderRate=0.00;
        double successOrderChian=0.00;//环比
        int successOrderCount = orderService.orderCountByTime(startTime, new Date(),"正常完成");
        int successInquiryCount = inquiryService.inquiryCountByTime(startTime, new Date(), "已报价",0,0);
        int successOrderChianCount = orderService.orderCountByTime(chainDate,startTime,"正常完成");
        if(successInquiryCount>0){
            successOrderRate = RateUtil.intChainRate(successOrderCount, successInquiryCount);
        }
        if(successOrderChianCount>0){
            successOrderChian=RateUtil.intChainRate((successOrderCount-successOrderChianCount),successOrderChianCount);
        }
        Map<String,Object> sucessOrderMap=new HashMap<String,Object>();
        sucessOrderMap.put("successOrderRate",successOrderRate);
        sucessOrderMap.put("successOrderChian",successOrderChian);
        //top3
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("startTime",startTime);
        params.put("endTime",new Date());
        List<Map<String, Object>> list = orderService.selectOrderProTop3(params);
        Map<String,Object> proTop3Map=new HashMap<String,Object>();
        for (int i = 0; i <list.size() ; i++) {
            proTop3Map.put("top"+(i+1),list.get(i));
                }
        //组装数据
        Datas.put("order",orderMap);
        Datas.put("profitRate",profitMap);
        Datas.put("sucessOrderMap",sucessOrderMap);
        Datas.put("top3",proTop3Map);

        result.put("code",200);
        result.put("data",Datas);
        return result;
    }

    @ResponseBody
    @RequestMapping("/inquiryDetail")
    public Object inquiryDetail(){
        Map<String,Object> result=new HashMap<String,Object>();
        int quotedCount = inquiryService.inquiryCountByTime(null, null, "已报价",0,0);
        int noQuoteCount = inquiryService.inquiryCountByTime(null, null, "未报价",0,0);
        int quotingCount = inquiryService.inquiryCountByTime(null, null, "报价中",0,0);
        int totalCount=quotedCount+noQuoteCount+quotingCount;
        double quotedInquiryRate=0.00;
        double quotingInquiryRate=0.00;
        double noQuoteInquiryRate=0.00;
        if(totalCount>0){
            quotedInquiryRate=RateUtil.intChainRate(quotedCount,totalCount);
            quotingInquiryRate=RateUtil.intChainRate(quotingCount,totalCount);
            noQuoteInquiryRate=RateUtil.intChainRate(noQuoteCount,totalCount);
        }
        HashMap<String, Object> inquiryDetailMap = new HashMap<>();
        inquiryDetailMap.put("quotedCount",quotedCount);
        inquiryDetailMap.put("noQuoteCount",noQuoteCount);
        inquiryDetailMap.put("quotingCount",quotingCount);
        inquiryDetailMap.put("quotedInquiryRate",quotedInquiryRate);
        inquiryDetailMap.put("quotingInquiryRate",quotingInquiryRate);
        inquiryDetailMap.put("noQuoteInquiryRate",noQuoteInquiryRate);

        result.put("code",200);
        result.put("data",inquiryDetailMap);
        return result;
    }

    //询单时间分布分析
    @ResponseBody
    @RequestMapping(value = "/inquiryTimeDistrbute")
    public Object inquiryTimeDistrbute(int day){

        Map<String,Object> result=new HashMap<String,Object>();
        Date startTime = DateUtil.recedeTime(day);
        int totalCount = inquiryService.inquiryCountByTime(null, null, "",0,0);
        int count1=inquiryService.inquiryCountByTime(null, null, "",1,4);
        int count2=inquiryService.inquiryCountByTime(null, null, "",4,8);
        int count3=inquiryService.inquiryCountByTime(null, null, "",8,16);
        int count4=inquiryService.inquiryCountByTime(null, null, "",16,24);
        int count5=inquiryService.inquiryCountByTime(null, null, "",24,48);
        HashMap<String, Object> quoteTimeMap = new HashMap<>();
        quoteTimeMap.put("oneCount",count1);
        quoteTimeMap.put("fourCount",count2);
        quoteTimeMap.put("eightCount",count3);
        quoteTimeMap.put("sixteenCount",count4);
        quoteTimeMap.put("twentyFourCount",count5);
        if(totalCount>0){
            quoteTimeMap.put("oneCountRate",RateUtil.intChainRate(count1,totalCount));
            quoteTimeMap.put("fourCountRate",RateUtil.intChainRate(count2,totalCount));
            quoteTimeMap.put("eightCountRate",RateUtil.intChainRate(count3,totalCount));
            quoteTimeMap.put("sixteenCountRate",RateUtil.intChainRate(count4,totalCount));
            quoteTimeMap.put("twentyFourCountRate",RateUtil.intChainRate(count5,totalCount));
        }
        result.put("code",200);
        result.put("data",quoteTimeMap);
        return result;
    }

    //询订单趋势图
    @ResponseBody
    @RequestMapping("/tendencyChart")
    public Object tendencyChart(int day){
        Map<String,Object> result=new HashMap<String,Object>();
        Map<String,Object> data=new HashMap<String,Object>();
        //封装日期,X轴
        if(day<=30){
            String[] dates=new String[day];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
            ArrayList<Date> dateList = new ArrayList<>();
            for (int i = 0; i < dates.length; i++) {
                Date datetime = DateUtil.recedeTime(day - (i+1) );
                dateList.add(datetime);
                String date = dateFormat.format(datetime);
                dates[i]=date;
            }
            //封装查询订单和询单数据
            Date sTime = DateUtil.recedeTime(day);
            Integer[] inCounts=new Integer[dateList.size()];//询单数数组
            Integer[] orCounts=new Integer[dateList.size()];//订单数数组

            for (int i = 0; i < dateList.size(); i++) {
                if(i==0){
                    int inquiryCount=inquiryService.inquiryCountByTime(sTime,dateList.get(i),"",0,0);
                    inCounts[i]=inquiryCount;
                    int orCount = orderService.orderCountByTime(sTime, dateList.get(i), "");
                    orCounts[i]=orCount;
                }else {
                    int inquiryCount=inquiryService.inquiryCountByTime(dateList.get(i-1),dateList.get(i),"",0,0);
                    inCounts[i]=inquiryCount;
                    int orCount = orderService.orderCountByTime(dateList.get(i-1), dateList.get(i), "");
                    orCounts[i]=orCount;
                }

            }
            data.put("dates",dates);
            data.put("inquiry",inCounts);
            data.put("order",orCounts);
        }

        result.put("code",200);
        result.put("data",data);
        return  result;
    }

    //事业部明细
    @ResponseBody
    @RequestMapping("/busUnitDetail")
    public Object busUnitDetail(){
        //事业部列表
        List<String> list=inquiryService.selectOrgList();



        return list;
    }

}
