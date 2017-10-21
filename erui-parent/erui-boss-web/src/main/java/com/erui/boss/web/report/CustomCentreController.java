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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户中心
 * Created by lirb on 2017/10/20.
 *
 * {
 "message":"ok",
 "code":200,
 "data":{
 "inquiry":{
 "count":2263,
 "amount":"500万",
 "chainAdd":23,
 "chainRate":"0.01%"
 },
 "isOil":{
 "oil":2000,
 "notOil":263,
 "oiProportionl":"80%",
 "chainRate":2000
 },
 "proTop3":{
 "top1":{
 "proCategory":"石油专用管材",
 "proCount":1000,
 "proProportionl":"40%"
 },
 "top2":{
 "proCategory":"仪器、仪表",
 "proCount":800,
 "proProportionl":"30%"
 },
 "top3":{
 "proCategory":"紧固件",
 "proCount":10,
 "proProportionl":"5%"
 }
 }
 }
 }
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

        Date startTime = DateUtil.recedeTime(day);
        Date chainDate = DateUtil.recedeTime(day*2);//环比起始时间
        Map<String,Object> Datas=new HashMap<String,Object>();//询单统计信息
        Map<String,Object> inquiryMap=new HashMap<String,Object>();//询单统计信息
        Map<String,Object> proIsOilMap=new HashMap<String,Object>();//油气产品分析
        Map<String,Object> proTop3Map=new HashMap<String,Object>();//产品分类Top3

        //当期询单数
        int count = inquiryService.inquiryCountByTime(startTime, new Date());
        //当期询单金额
        double amount = inquiryService.inquiryAmountByTime(startTime, new Date());
        //当期询单数环比chain
        int chainCount = inquiryService.inquiryCountByTime(chainDate, startTime);
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
//        同期产品总数量
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

        return Datas;
    }



}
