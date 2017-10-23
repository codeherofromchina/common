package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.erui.comm.util.data.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.report.dao.RequestCreditMapper;
import com.erui.report.model.RequestCredit;
import com.erui.report.service.RequestCreditService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class RequestCreditServiceImpl extends BaseService<RequestCreditMapper> implements RequestCreditService {
	private final static Logger logger = LoggerFactory.getLogger(RequestCreditServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		RequestCredit rc = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.REQUEST_CREDIT, response, index + 1)) {
				continue;
			}
			rc = new RequestCredit();
			rc.setCreditSerialNum(strArr[0]);
			rc.setOrderNum(strArr[1]);
			rc.setSalesMainCompany(strArr[2]);
			rc.setSalesArea(strArr[3]);
			rc.setSalesCountry(strArr[4]);
			rc.setOrganization(strArr[5]);
			rc.setCustomerCode(strArr[6]);
			rc.setExportProName(strArr[7]);
			rc.setTradeTerms(strArr[8]);
			rc.setCreateAt(strArr[9]);
			try {
				rc.setOrderAmount(new BigDecimal(strArr[10]));
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "订单金额字段非数字");
				continue;
			}
			rc.setCreditCurrency(strArr[11]);
			try {
				rc.setReceiveAmount(new BigDecimal(strArr[12]));
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "应收账款余额字段非数字");
				continue;
			}
			rc.setWarnStatus(strArr[13]);
			rc.setBackResponsePerson(strArr[14]);

			try {
				rc.setBackDate(
						DateUtil.parseString2Date(strArr[15], DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "下次回款日期格式错误");
				continue;
			}

			try {
				if (!testOnly) {
					writeMapper.insertSelective(rc);
					response.incrSuccess();
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, e.getMessage());
			}

		}
		response.setDone(true);

		return response;
	}

}