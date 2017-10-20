package com.erui.boss.web.report;

import com.erui.comm.DateUtil;
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

import java.util.Date;

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
        System.out.println(startTime);
        int count = inquiryService.inquiryCountByTime(startTime, new Date());
        String endTime = DateUtil.format("yyyy-MM-dd hh:mm:ss", new Date());
        String start = DateUtil.format("yyyy-MM-dd hh:mm:ss", startTime);
        System.out.println(start+"===="+endTime);
        double amount = inquiryService.inquiryAmountByTime(startTime, new Date());

        return amount;
    }



}
