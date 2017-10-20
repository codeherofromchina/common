package com.erui.report.service.impl;

import com.erui.report.dao.OrderEntryCountMapper;
import com.erui.report.service.OrderEntryCountService;
import com.erui.report.util.ImportDataResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OrderEntryCountServiceImpl extends BaseService<OrderEntryCountMapper> implements OrderEntryCountService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}
}