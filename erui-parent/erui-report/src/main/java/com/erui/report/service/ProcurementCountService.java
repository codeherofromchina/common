package com.erui.report.service;

import java.util.List;

import com.erui.report.util.ImportDataResponse;

/**
 * 采购业务接口
 * @author wangxiaodan
 *
 */
public interface ProcurementCountService {
	/**
	 * 导入外部数据到采购信息中
	 * @param datas
	 * @param flag	true:只检测数据  false:插入正式库
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly);
}