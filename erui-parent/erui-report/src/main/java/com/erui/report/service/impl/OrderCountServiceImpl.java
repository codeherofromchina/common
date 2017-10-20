package com.erui.report.service.impl;

import com.erui.report.dao.OrderCountMapper;
import com.erui.report.service.OrderCountService;
import com.erui.report.util.ImportDataResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OrderCountServiceImpl extends BaseService<OrderCountMapper> implements OrderCountService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}
}