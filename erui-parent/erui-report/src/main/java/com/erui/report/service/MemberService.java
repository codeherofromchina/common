package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface MemberService {
	/**
	 * 导入运营数据
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
}