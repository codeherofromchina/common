package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface MarketerCountService {
	/**
	 * 导入市场人员数据
	 * @param datas
	 * @param flag	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
}