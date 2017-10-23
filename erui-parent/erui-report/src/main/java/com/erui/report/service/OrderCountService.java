package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;

public interface OrderCountService {
	/**
	 * 导入客户中心-订单数据
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
	/*
	* 订单数
	* */
	public int orderCountByTime(Date startTime,Date endTime,String projectStatus);
	/*
	* 订单金额
	* */
	public Double orderAmountByTime(Date startTime,Date endTime);
	/*
	* 订单产品类别数量Top3
	* */
	public  List<Map<String,Object>> selectOrderProTop3(Map<String,Object> params);

	public Double selectProfitRate(Date startTime,Date endTime);
}