package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;

/**
 * 采购业务接口
 * @author wangxiaodan
 *
 */
public interface ProcurementCountService {
	/**
	 * 导入外部数据到采购信息中
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly);

	/**
	 * 获取采购总览数据
	 * @param startTime
	 * @param endTime
	 */
	List<Map<String,Object>> selectProcurPandent(Date startTime,Date endTime);
	/**
	 * 获取采购趋势图
	 * @param startTime
	 * @param endTime
	 * @param queryType
	 */
	Map<String,Object> procurementTrend(Date startTime,Date endTime,String queryType);
}