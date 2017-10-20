package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.RequestCreditMapper;
import com.erui.report.model.RequestCredit;
import com.erui.report.service.RequestCreditService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class RequestCreditServiceImpl extends BaseService<RequestCreditMapper> implements RequestCreditService {
	private final static Logger logger = LoggerFactory.getLogger(RequestCreditServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		RequestCredit rc = null;
		boolean insertFlag = true; // 是否插入标志
		String errorMsg = null; // 插入失败后的原因
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			insertFlag = true;
			errorMsg = null;
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
				errorMsg = "订单金额字段非数字";
				insertFlag = false;
			}
			rc.setCreditCurrency(strArr[11]);
			try {
				rc.setReceiveAmount(new BigDecimal(strArr[12]));
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				errorMsg = "应收账款余额字段非数字";
				insertFlag = false;
			}
			rc.setWarnStatus(strArr[13]);
			rc.setBackResponsePerson(strArr[14]);

			try {
				rc.setBackDate(DateUtil.parseString2Date(strArr[15], DateUtil.FULL_FORMAT_STR,DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				errorMsg = "下次回款日期格式错误";
				insertFlag = false;
			}

			if (insertFlag) {
				try {
					writeMapper.insertSelective(rc);
				} catch (Exception e) {
					errorMsg = e.getMessage();
					insertFlag = false;
				}
			}

			if (insertFlag) {
				response.incrSuccess();
			} else {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, errorMsg);
			}
		}
		response.setDone(true);

		return response;
	}
	

}