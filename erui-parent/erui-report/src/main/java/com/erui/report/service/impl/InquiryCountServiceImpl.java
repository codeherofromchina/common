package com.erui.report.service.impl;

import com.erui.report.dao.InquiryCountMapper;
import com.erui.report.model.InquiryCountExample;
import com.erui.report.service.InquiryCountService;



import com.erui.report.util.ImportDataResponse;

import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Service;


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






	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}


	// //     根据时间统计询单金额
	public Double inquiryAmountByTime(Date startTime, Date endTime) {
		InquiryCountExample example = new InquiryCountExample();
		example.createCriteria().andRollinTimeBetween(startTime, endTime);
		Double amount = readMapper.selectTotalAmountByExample(example);
		return amount;
	}

}