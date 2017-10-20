package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface SupplyChainService {
	/**
	 * 导入供应链数据到数据库
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
}