package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.SupplyChainReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component("DataTesk")
public class DataServiceImpl {

    @Autowired
    private SupplyChainReadService readService;
    @Autowired
    private InquiryCountService inquiryService;


    public void supplyChainData() throws Exception {
        System.out.println("这就是爱···");
        //获取前一天的两个时间点
        Date date = DateUtil.sometimeCalendar(new Date(), 1);
        String startTime = DateUtil.getStartTime(date, DateUtil.FULL_FORMAT_STR);
        String endTime = DateUtil.getEndTime(date, DateUtil.FULL_FORMAT_STR);
        readService.supplyChainReadData(startTime, endTime);
        inquiryService.inquiryData("2017-12-08 00:00:00", "2017-12-08 23:59:59");
//            totalData();

    }

    public void totalData() throws Exception {
        Date day = DateUtil.parseStringToDate("2017-07-01 00:00:00", DateUtil.FULL_FORMAT_STR);
        List<Map<String, String>> list = new ArrayList<>();
        Date lastDay = DateUtil.getNextMonthLastDay(day);
        for (int i = 30; i >= 0; i--) {
            HashMap<String, String> dateMap = new HashMap<>();
            Date date1 = DateUtil.sometimeCalendar(lastDay, i);
            String startTime = DateUtil.getStartTime(date1, DateUtil.FULL_FORMAT_STR);
            String endTime = DateUtil.getEndTime(date1, DateUtil.FULL_FORMAT_STR);
            dateMap.put("startTime", startTime);
            dateMap.put("endTime", endTime);
            list.add(dateMap);
        }

        Date day2 = DateUtil.parseStringToDate("2017-08-01 00:00:00", DateUtil.FULL_FORMAT_STR);
        Date lastDay2 = DateUtil.getNextMonthLastDay(day);
        for (int i = 29; i >= 0; i--) {
            HashMap<String, String> dateMap = new HashMap<>();
            Date date1 = DateUtil.sometimeCalendar(lastDay2, i);
            String startTime = DateUtil.getStartTime(date1, DateUtil.FULL_FORMAT_STR);
            String endTime = DateUtil.getEndTime(date1, DateUtil.FULL_FORMAT_STR);
            dateMap.put("startTime", startTime);
            dateMap.put("endTime", endTime);
            list.add(dateMap);
        }


        Date day3 = DateUtil.parseStringToDate("2017-09-01 00:00:00", DateUtil.FULL_FORMAT_STR);
        Date lastDay3 = DateUtil.getNextMonthLastDay(day3);
        for (int i = 30; i >= 0; i--) {
            HashMap<String, String> dateMap = new HashMap<>();
            Date date1 = DateUtil.sometimeCalendar(lastDay3, i);
            String startTime = DateUtil.getStartTime(date1, DateUtil.FULL_FORMAT_STR);
            String endTime = DateUtil.getEndTime(date1, DateUtil.FULL_FORMAT_STR);
            dateMap.put("startTime", startTime);
            dateMap.put("endTime", endTime);
            list.add(dateMap);
        }

        Date day4 = DateUtil.parseStringToDate("2017-10-01 00:00:00", DateUtil.FULL_FORMAT_STR);
        Date lastDay4 = DateUtil.getNextMonthLastDay(day4);
        for (int i = 29; i >= 0; i--) {
            HashMap<String, String> dateMap = new HashMap<>();
            Date date1 = DateUtil.sometimeCalendar(lastDay4, i);
            String startTime = DateUtil.getStartTime(date1, DateUtil.FULL_FORMAT_STR);
            String endTime = DateUtil.getEndTime(date1, DateUtil.FULL_FORMAT_STR);
            dateMap.put("startTime", startTime);
            dateMap.put("endTime", endTime);
            list.add(dateMap);
        }

        Date date = new Date();
        Date firstDay = DateUtil.getMonthFirstDay(date);
        int dayBetween = DateUtil.getDayBetween(firstDay, date);
        if(dayBetween>0){
            for (int i = dayBetween-1; i >= 0; i--) {
                HashMap<String, String> dateMap = new HashMap<>();
                Date date2 = DateUtil.sometimeCalendar(new Date(), i);
                String startTime = DateUtil.getStartTime(date2, DateUtil.FULL_FORMAT_STR);
                String endTime = DateUtil.getEndTime(date2, DateUtil.FULL_FORMAT_STR);
                dateMap.put("startTime", startTime);
                dateMap.put("endTime", endTime);
                list.add(dateMap);
            }
        }

        System.out.println(list.size());


        for (Map<String, String> map : list) {
            String startTime = map.get("startTime");
            String endTime = map.get("endTime");
           // readService.supplyChainReadData(startTime, endTime);
            inquiryService.inquiryData(startTime, endTime);
        }
    }


}
