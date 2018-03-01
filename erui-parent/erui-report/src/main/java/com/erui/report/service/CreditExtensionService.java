package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;

public interface CreditExtensionService {
	/**
	 * 导入授信数据
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/**
	 * 导入授信数据
	 * @param  params
	 * @return
	 */
	Map<String,Object> creditPandect(Map<String,String> params);
}