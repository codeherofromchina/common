package com.erui.report.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erui.report.dao.ProcurementCountMapper;
import com.erui.report.service.ProcurementCountService;
import com.erui.report.util.ImportDataResponse;

@Service
public class ProcurementCountServiceImpl extends BaseService<ProcurementCountMapper>
		implements ProcurementCountService {

	/**
	 * 具体采购数据的导入实现
	 */
	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		
		
		
		return null;
	}
}