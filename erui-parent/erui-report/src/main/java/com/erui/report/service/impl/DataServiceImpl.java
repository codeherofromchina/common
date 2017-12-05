package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.SupplyChainReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component("DataTesk")
public class DataServiceImpl {

    @Autowired
    private  SupplyChainReadService readService;

    private static final String dateFormat="yyyy-MM-dd hh:mm:ss";

    public void supplyChainData() throws Exception {
        System.out.println("这就是爱···");
        //获取今天的两个时间点
        String startTime = DateUtil.getStartTime(new Date(), dateFormat);
        String endTime = DateUtil.getEndTime(new Date(), dateFormat);
        readService.supplyChainReadData(startTime,endTime);
    }
}
