package com.erui.report.service.impl;

import com.erui.report.dao.SupplyChainMapper;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.ImportDataResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SupplyChainServiceImpl extends BaseService<SupplyChainMapper> implements SupplyChainService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}
}