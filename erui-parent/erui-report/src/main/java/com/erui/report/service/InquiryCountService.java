package com.erui.report.service;

import java.util.Date;

/*
* 询单统计
* */
public interface InquiryCountService {

    /*
    * 根据时间查询询单单数
    * */
    public int inquiryCountByTime(Date startTime,Date endTime);

    /*
    * 根据时间查询询单总金额
    * */
    public double inquiryAmountByTime(Date startTime,Date endTime);

}