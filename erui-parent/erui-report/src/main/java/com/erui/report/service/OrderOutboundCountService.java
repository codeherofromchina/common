package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface OrderOutboundCountService {
	/**
	 * 导入仓储物流-订单出库数据
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
}