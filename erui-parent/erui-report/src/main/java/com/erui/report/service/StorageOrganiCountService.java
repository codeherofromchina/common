package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;

public interface StorageOrganiCountService {

	/**
	 * 查询 起始时间
	 * @return Date
	 */
	Date selectStart();
	/**
	 * 查询 结束时间
	 * @return Date
	 */
	Date selectEnd();
	/**
	 * 导入仓储物流-事业部数据到数据库
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/**
	 * 仓储物流总览数据
	 * @param params startTime ,endTime
	 * @return
	 */
	Map<String,Object> selectStorageSummaryData(Map<String,String> params);
	/**
	 * 仓储物流总览数据
	 * @param  startTime
	 * @param  endTime
	 * @return
	 */
	Map<String,Object> selectStorageTrend(Date startTime,Date endTime);
	/**
	 * 出库目的国明细
	 * @param  params startTime,endTime
	 * @return
	 */
	Map<String,Object> selectCountryOutStoreSummary(Map<String,String> params);
	/**
	 * 仓储物流总览数据
	 * @param  params startTime,endTime
	 * @return
	 */
	Map<String,Object> orgStocks(Map<String,String> params);
}