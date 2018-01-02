package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.SupplyChainReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;

@Component("dataService")
public class DataServiceImpl {

    @Autowired
    private SupplyChainReadService readService;
    @Autowired
    private InquiryCountService inquiryService;


    public void supplyChainData() throws  Exception{
        //获取前一天的两个时间点
        System.out.println("执行一次调度");
        Date date = DateUtil.sometimeCalendar(new Date(), 1);
        String startTime = DateUtil.getStartTime(date, DateUtil.FULL_FORMAT_STR);
        String endTime = DateUtil.getEndTime(date, DateUtil.FULL_FORMAT_STR);
        readService.supplyChainReadData(startTime, endTime);
        inquiryService.inquiryData(startTime, endTime);
    }

    public void totalData() throws Exception {
        Date day = DateUtil.parseStringToDate("2017-07-01 00:00:00", DateUtil.FULL_FORMAT_STR);
        List<Map<String, String>> list = new ArrayList<>();
        for (int j = 0; j < 6; j++) {
            Date   lastDay = DateUtil.getNextMonthLastDay(day);
            Date firstDay = DateUtil.getNextMonthFirstDay(day);
            day=lastDay;
            int days = DateUtil.getDayBetween(firstDay, lastDay);
            if(j==5){
                days=DateUtil.getDayBetween(firstDay,new Date())-1;
                lastDay=DateUtil.sometimeCalendar(new Date(),1);
            }
            for (int i = days - 1; i >= 0; i--) {
                HashMap<String, String> dateMap = new HashMap<>();
               Date date1 = DateUtil.sometimeCalendar(lastDay, i);
                String startTime = DateUtil.getStartTime(date1, DateUtil.FULL_FORMAT_STR);
                String endTime = DateUtil.getEndTime(date1, DateUtil.FULL_FORMAT_STR);
                dateMap.put("startTime", startTime);
               dateMap.put("endTime", endTime);
               list.add(dateMap);
            }
        }
       for (Map<String, String> map : list) {
           String startTime = map.get("startTime");
            String endTime = map.get("endTime");
           readService.supplyChainReadData(startTime, endTime);
            inquiryService.inquiryData(startTime, endTime);
        }
    }




}
