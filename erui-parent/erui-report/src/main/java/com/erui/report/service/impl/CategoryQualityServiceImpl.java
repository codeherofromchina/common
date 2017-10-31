package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.report.dao.CategoryQualityMapper;
import com.erui.report.model.CategoryQuality;
import com.erui.report.service.CategoryQualityService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class CategoryQualityServiceImpl extends BaseService<CategoryQualityMapper> implements CategoryQualityService {
	private final static Logger logger = LoggerFactory.getLogger(CategoryQualityServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		CategoryQuality cq = null;
		if (!testOnly) {
			writeMapper.truncateTable();
		}
		for (int index = 0; index < size; index++) {
			int cellIndex = index + 2;
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.CATEGORY_QUALITY, response, cellIndex)){
				continue;
			}
			
			cq = new CategoryQuality();
			cq.setQualityControlDate(strArr[0]);
			if (strArr[1] != null) {
				try {
					cq.setInspectionTotal(new BigDecimal(strArr[1]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "报检总数不是数字");
					continue;
				}
			}
			if (strArr[2] != null) {
				try {
					cq.setProInfactoryCheckPassCount(new BigDecimal(strArr[2]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品入厂首检合格数不是数字");
					continue;
				}
			}
			if (strArr[3] != null) {
				try {
					cq.setProInfactoryCheckPassRate(new BigDecimal(strArr[3]));
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品入厂首检合格率不是数字");
					continue;
				}
			}
			if (strArr[4] != null) {
				try {
					cq.setProOutfactoryTotal(new BigDecimal(strArr[4]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品出厂总数不是数字");
					continue;
				}
			}
			if (strArr[5] != null) {
				try {
					cq.setProOutfactoryCheckCount(new BigDecimal(strArr[5]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "出厂检验合格数不是数字");
					continue;
				}
			}
			if (strArr[6] != null) {
				try {
					cq.setProOutfactoryCheckRate(new BigDecimal(strArr[6]));
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品出厂检验合格率不是数字");
					continue;
				}
			}
			if (strArr[7] != null) {
				try {
					cq.setAssignmentsTotal(new BigDecimal(strArr[7]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "外派监造总数不是数字");
					continue;
				}
			}
			if (strArr[8] != null) {
				try {
					cq.setProductsQualifiedCount(new BigDecimal(strArr[8]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "监造产品出厂合格数不是数字");
					continue;
				}
			}
			if (strArr[9] != null) {
				try {
					cq.setProductsAssignmentsQualifiedRate(new BigDecimal(strArr[9]));
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品外派监造合格率不是数字");
					continue;
				}
			}
			
			try {
				if (!testOnly) {
					writeMapper.insertSelective(cq);
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, e.getMessage());
				continue;
			}
			response.incrSuccess();

		}
		response.setDone(true);

		return response;
	}
}