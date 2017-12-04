package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.SupplyChainReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component("GetDataTesk")
public class GetDataServiceImpl {

    @Autowired
    private  SupplyChainReadService readService;

    private static final String dateFormat="yyyy-MM-dd hh:mm:ss";

    public void getSupplyChainData() throws Exception {
        System.out.println("这就是爱···");
        //获取今天的两个时间点
        String startTime = DateUtil.getStartTime(new Date(), dateFormat);
        String endTime = DateUtil.getEndTime(new Date(), dateFormat);
        readService.getSupplyChainReadData("2017-11-19","2017-11-25");
    }
}
