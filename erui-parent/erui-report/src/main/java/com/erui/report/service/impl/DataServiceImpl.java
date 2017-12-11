package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.SupplyChainReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component("DataTesk")
public class DataServiceImpl {

    @Autowired
    private  SupplyChainReadService readService;
    @Autowired
    private InquiryCountService inquiryService;

    private static final String dateFormat="yyyy-MM-dd hh:mm:ss";

    public void supplyChainData() throws Exception {
        System.out.println("这就是爱···");
        //获取今天的两个时间点
        Date date = DateUtil.parseStringToDate("2017-11-27 00:00:00", null);
        String startTime = DateUtil.getStartTime(date, dateFormat);
        String endTime = DateUtil.getEndTime(new Date(), dateFormat);
      //  readService.supplyChainReadData(startTime,endTime);
        inquiryService.inquiryData(startTime,endTime);
    }
}
