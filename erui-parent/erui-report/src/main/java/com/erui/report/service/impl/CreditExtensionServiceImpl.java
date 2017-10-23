package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.CreditExtensionMapper;
import com.erui.report.model.CreditExtension;
import com.erui.report.service.CreditExtensionService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class CreditExtensionServiceImpl extends BaseService<CreditExtensionMapper> implements CreditExtensionService {
	private final static Logger logger = LoggerFactory.getLogger(CreditExtensionServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		CreditExtension ce = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.CREDIT_EXTENSION, response, index + 1)){
				continue;
			}
			ce = new CreditExtension();
			try {
				ce.setCreditDate(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), index + 1, "时间字段格式错误");
				continue;
			}
			ce.setCreditArea(strArr[1]);
			ce.setCreditCountry(strArr[2]);
			ce.setMailCode(strArr[3]);
			ce.setCustomerName(strArr[4]);
			try {
				ce.setRelyCredit(new BigDecimal(strArr[5]));
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), index + 1, "批复信用额度不是数字");
				continue;
			}
			try {
				ce.setEffectiveDate(DateUtil.parseString2Date(strArr[6], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), index + 1, "限额生效日期格式错误");
				continue;
			}
			// TODO 字符串变量
			if (strArr[7] != null && !"无期使用".equals(strArr[7])) {
				try {
					ce.setExpiryDate(DateUtil.parseString2Date(strArr[7], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), index + 1, "限额失效日期格式错误");
					continue;
				}
			}
			try {
				ce.setUsedAmount(new BigDecimal(strArr[8]));
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), index + 1, "已用额度不是数字");
				continue;
			}
			try {
				ce.setAvailableAmount(new BigDecimal(strArr[9]));
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), index + 1, "可用额度不是数字");
				continue;
			}

			try {
				if (!testOnly) {
					writeMapper.insertSelective(ce);
					response.incrSuccess();
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), index + 1, e.getMessage());
			}

		}
		response.setDone(true);

		return response;
	}
}