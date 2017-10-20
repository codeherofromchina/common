package com.erui.report.service.impl;

import com.erui.report.dao.InquiryCountMapper;
import com.erui.report.model.InquiryCountExample;
import com.erui.report.service.InquiryCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
/*
* 客户中心-询单统计  服务实现类
* */
@Service
public class InquiryCountServiceImpl extends BaseService<InquiryCountMapper> implements InquiryCountService {


    private InquiryCountExample inquiryExample = new InquiryCountExample();


//    根据时间统计询单单数
    @Override
    public int inquiryCountByTime(Date startTime, Date endTime) {

        inquiryExample.createCriteria().andRollinTimeBetween(startTime,endTime);
        int count = readMapper.countByExample(inquiryExample);
        return count;
    }

//     根据时间统计询单金额
    public double inquiryAmountByTime(Date startTime,Date endTime){
        InquiryCountExample example = new InquiryCountExample();
        example.createCriteria().andRollinTimeBetween(startTime,endTime);
        double amount = readMapper.selectTotalAmountByExample(example);
        return amount;
    }




}