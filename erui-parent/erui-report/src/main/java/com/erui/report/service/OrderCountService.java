package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.NumSummaryVO;

public interface OrderCountService {
	/**
	 * 导入客户中心-订单数据
	 * @param datas
	 * @param flag	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/*
	* 订单数
	* */
	public int orderCountByTime(Date startTime,Date endTime,String projectStatus,String org,String area);
	/*
	* 订单金额
	* */
	public Double orderAmountByTime(Date startTime,Date endTime,String area);
	/*
	* 订单产品类别数量Top3
	* */
	public  List<Map<String,Object>> selectOrderProTop3(Map<String,Object> params);

	public Double selectProfitRate(Date startTime,Date endTime);

	/**
	 * 获取数据汇总
	 * @param area
	 * @param country
	 * @return
	 */
	public NumSummaryVO numSummary(String area,String country);

}