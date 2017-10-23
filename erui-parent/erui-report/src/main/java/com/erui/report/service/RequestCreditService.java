package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface RequestCreditService {

	/**
	 * 导入应收账款的数据到数据库
	 * @param datas
	 * @param flag	true:只检测数据  false:插入正式库
	 * @return
	 */
	ImportDataResponse importData(List<String[]> datas, boolean testOnly);
}