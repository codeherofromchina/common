package com.erui.report.service.impl;

import com.erui.report.dao.CategoryQualityMapper;
import com.erui.report.service.CategoryQualityService;
import com.erui.report.util.ImportDataResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CategoryQualityServiceImpl extends BaseService<CategoryQualityMapper> implements CategoryQualityService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}
}