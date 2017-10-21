package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;

public interface SupplyChainService {
	 /**
	  * @Author:SHIGS 查询spu sku 供应商完成量
	  * @Description
	  * @Date:15:58 2017/10/21
	  * @modified By
	  */
	List<Map> selectFinishByDate(Date startDate, Date endDate);
	/**
	 * 导入供应链数据到数据库
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
}