package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

public interface OrderOutboundCountService {
	/**
	 * 导入仓储物流-订单出库数据
	 * @param datas
<<<<<<< HEAD
	 * @param flag	true:只检测数据  false:插入正式库
=======
	 * @param testOnly	true:只检测数据  false:插入正式库
>>>>>>> b8e075df61f15b329670e1648b09f902513f7861
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
}