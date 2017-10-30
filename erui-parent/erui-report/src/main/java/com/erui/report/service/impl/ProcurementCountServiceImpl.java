package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.ProcurementCountMapper;
import com.erui.report.model.ProcurementCount;
import com.erui.report.service.ProcurementCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class ProcurementCountServiceImpl extends BaseService<ProcurementCountMapper>
		implements ProcurementCountService {
	private final static Logger logger = LoggerFactory.getLogger(ProcurementCountServiceImpl.class);

	/**
	 * 具体采购数据的导入实现
	 */
	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		ProcurementCount pc = null;
		if (!testOnly) {
			writeMapper.truncateTable();
		}
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.PROCUREMENT_COUNT, response, index + 1)) {
				continue;
			}
			pc = new ProcurementCount();
			try {
				pc.setAssignTime(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d", DateUtil.FULL_FORMAT_STR,
						DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), index + 1, "采购下达时间格式错误");
				continue;
			}
			pc.setPlanNum(strArr[1]);
			pc.setExecuteNum(strArr[2]);
			try {
				pc.setAmmount(new BigDecimal(strArr[3]));
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), index + 1, "金额字段非数字");
				continue;
			}
			pc.setCategory(strArr[4]);
			pc.setArea(strArr[5]);
			pc.setCountry(strArr[6]);
			pc.setOil(strArr[7]);
			try {
				if (!testOnly) {
					writeMapper.insertSelective(pc);
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), index + 1, e.getMessage());
				continue;
			}
			response.incrSuccess();
		}
		response.setDone(true);

		return response;
	}
}