package com.erui.report.service.impl;

import com.erui.report.dao.OrderOutboundCountMapper;
import com.erui.report.service.OrderOutboundCountService;
import com.erui.report.util.ImportDataResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OrderOutboundCountServiceImpl extends BaseService<OrderOutboundCountMapper>
		implements OrderOutboundCountService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}
}