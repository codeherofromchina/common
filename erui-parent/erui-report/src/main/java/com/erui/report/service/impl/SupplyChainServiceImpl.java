package com.erui.report.service.impl;

import com.erui.report.dao.SupplyChainMapper;
import com.erui.report.model.SupplyChainExample;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.ImportDataResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SupplyChainServiceImpl extends BaseService<SupplyChainMapper> implements SupplyChainService {
	 /**
	  * @Author:SHIGS
	  * @Description 查询 spu sku 供应商完成量
	  * @Date:16:02 2017/10/21
	  * @modified By
	  */
	@Override
	public List<Map>  selectFinishByDate(Date startDate, Date endDate) {
		SupplyChainExample supplyChainExample =new SupplyChainExample();
		supplyChainExample.createCriteria().andCreateAtBetween(startDate,endDate);
		return this.readMapper.selectFinishByDate(supplyChainExample);
	}
	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}
}