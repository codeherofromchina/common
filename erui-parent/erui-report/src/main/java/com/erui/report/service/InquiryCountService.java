package com.erui.report.service;

import com.erui.report.util.ImportDataResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

/*
* 询单统计
* */
public interface InquiryCountService {

    /*
    * 查询询单单数
    * */
    public int inquiryCountByTime(Date startTime,Date endTime,String quotedStatus,double leastQuoteTime,double maxQuoteTime);

    /*
    * 查询询单总金额
    * */
	public Double inquiryAmountByTime(Date startDate,Date endDate) ;

	/**
	 * 导入客户中心-询单数据
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
	/*
	* 根据油气类别查询产品数量
	* */
	public int selectProCountByIsOil(Date startTime,Date endTime,String isOil);


	/*
	* 查询产品Top3
	* */
	public List<Map<String,Object>> selectProTop3(Map<String,Object>params);

}