package com.erui.report.service.impl;

import com.erui.report.dao.StorageOrganiCountMapper;
import com.erui.report.service.StorageOrganiCountService;
import com.erui.report.util.ImportDataResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StorageOrganiCountServiceImpl extends BaseService<StorageOrganiCountMapper>
		implements StorageOrganiCountService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}
}