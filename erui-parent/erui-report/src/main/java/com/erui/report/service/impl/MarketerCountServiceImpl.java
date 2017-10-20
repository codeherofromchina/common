package com.erui.report.service.impl;

import com.erui.report.dao.MarketerCountMapper;
import com.erui.report.service.MarketerCountService;
import com.erui.report.util.ImportDataResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MarketerCountServiceImpl extends BaseService<MarketerCountMapper> implements MarketerCountService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}
}