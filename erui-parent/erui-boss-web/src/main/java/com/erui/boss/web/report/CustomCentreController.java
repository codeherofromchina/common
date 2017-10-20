package com.erui.boss.web.report;

import com.erui.comm.DateUtil;
import com.erui.report.model.InquiryCount;
import com.erui.report.model.InquiryCountVo;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.OrderCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping("/inquiryPandect")
    public Object inquiryPandect(int day){

        Date startTime = DateUtil.recedeTime(day);
//        Date chainDate = DateUtil.recedeTime(day*2);//环比起始时间
//        //当期询单数
//        int count = inquiryService.inquiryCountByTime(startTime, new Date());
//        //当期询单数环比chain
//
//        int chainCount = inquiryService.inquiryCountByTime(chainDate, startTime);
//        int chain=count-chainCount;
//        //当期询单金额
//        double amount = inquiryService.inquiryAmountByTime(startTime, new Date());
//
//        //当前产品总数量
//        int proCount = inquiryService.selectProCountByIsOil(startTime, new Date(),null);
//        int notOilCount = inquiryService.selectProCountByIsOil(startTime, new Date(),"非油气");
//        //油气类占比
//        System.out.println(proCount);
        //同期产品总数量
//        int chainProCount = inquiryService.selectProCountByIsOil(chainDate, startTime,null);
//        int chainNotOilCount = inquiryService.selectProCountByIsOil(chainDate, startTime,"非油气");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("startTime",DateUtil.recedeTime(day));
        params.put("endTime",new Date());
//        List<InquiryCountVo> list = inquiryService.selectProTop3(startTime, new Date());
        List<InquiryCountVo> list = inquiryService.selectProTop3(params);
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).getProCategory()+list.get(i).getProCount());
        }


        //查询Top3产品数据

        return 4;
    }



}
