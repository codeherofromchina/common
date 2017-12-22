package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface OrderOutboundCountService {
	/**
	 * 导入仓储物流-订单出库数据
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
}