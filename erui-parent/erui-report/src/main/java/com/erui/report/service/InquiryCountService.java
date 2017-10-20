package com.erui.report.service;

import java.util.Date;
import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface InquiryCountService {
	
	/**
	 * 导入客户中心-询单数据
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
	
	
	public Double inquiryAmountByTime(Date startDate,Date endDate) ;
}