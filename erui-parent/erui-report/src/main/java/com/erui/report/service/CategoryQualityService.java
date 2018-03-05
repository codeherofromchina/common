package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;

public interface CategoryQualityService {
	/**
	 * 导入品控数据
	 * @param datas
	 * @param onlyTest	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas,boolean onlyTest) ;
	/**
	 * 查询品控总览数据
	 * @param params startTime ，endTime
	 * @return
	 */
	Map<String,Object> selectQualitySummaryData(Map<String,String> params);
	/**
	 * 查询品控趋势图数据
	 * @param  startTime
	 * @param  endTime
	 * @param  type
	 * @return
	 */
	Map<String,Object> selectQualityTrendData(Date startTime, Date endTime, String type);
}