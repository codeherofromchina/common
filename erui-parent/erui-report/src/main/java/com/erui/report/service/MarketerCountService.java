package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface MarketerCountService {
	/**
	 * 导入市场人员数据
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
}