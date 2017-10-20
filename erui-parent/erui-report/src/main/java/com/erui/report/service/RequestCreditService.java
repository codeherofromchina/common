package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface RequestCreditService {

	/**
	 * 导入应收账款的数据到数据库
	 * @param datas
	 * @return
	 */
	ImportDataResponse importData(List<String[]> datas);
}