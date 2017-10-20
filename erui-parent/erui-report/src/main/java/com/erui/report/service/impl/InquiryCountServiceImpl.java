package com.erui.report.service.impl;

import com.erui.report.dao.InquiryCountMapper;
import com.erui.report.model.InquiryCountExample;
import com.erui.report.service.InquiryCountService;
import com.erui.report.util.ImportDataResponse;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class InquiryCountServiceImpl extends BaseService<InquiryCountMapper> implements InquiryCountService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}


	// 根据时间统计询单金额
	public Double inquiryAmountByTime(Date startTime, Date endTime) {
		InquiryCountExample example = new InquiryCountExample();
		example.createCriteria().andRollinTimeBetween(startTime, endTime);
		Double amount = readMapper.selectTotalAmountByExample(example);
		return amount;
	}
}