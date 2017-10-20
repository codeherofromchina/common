package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface OrderCountService {
	/**
	 * 导入客户中心-订单数据
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
}