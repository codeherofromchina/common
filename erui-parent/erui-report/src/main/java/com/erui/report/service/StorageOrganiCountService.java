package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface StorageOrganiCountService {
	/**
	 * 导入仓储物流-事业部数据到数据库
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
}