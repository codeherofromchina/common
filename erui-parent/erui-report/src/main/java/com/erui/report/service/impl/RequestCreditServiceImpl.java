package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.RequestCreditExample;
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
	public Map selectTotal() {
		return this.readMapper.selectTotal();
	}
	 /**
	  * @Author:SHIGS
	  * @Description 应收账款统计
	  * @Date:9:14 2017/10/24
	  * @modified By
	  */
	@Override
	public Map selectRequestTotal(Date startDate, Date endDate) {
		RequestCreditExample requestCreditExample = null;
		if (startDate != null && endDate != null){
			requestCreditExample = new RequestCreditExample();
			requestCreditExample.createCriteria().andBackDateBetween(startDate,endDate);
			return this.readMapper.selectRequestTotal(requestCreditExample);
		}
		return this.readMapper.selectRequestTotal(null);
	}
	 /**
	  * @Author:SHIGS
	  * @Description 应收账款趋势图
	  * @Date:9:13 2017/10/24
	  * @modified By
	  */
	@Override
	public List<Map> selectRequestTrend(Date startDate, Date endDate) {
		RequestCreditExample requestCreditExample = new RequestCreditExample();
		requestCreditExample.createCriteria().andCreateAtBetween(startDate,endDate);
		return this.readMapper.selectRequestTrend(requestCreditExample);
	}
	 /**
	  * @Author:SHIGS
	  * @Description 应收账款下月
	  * @Date:9:13 2017/10/24
	  * @modified By
	  */
	@Override
	public List<Map> selectRequestNext(Date startDate, Date endDate) {
		RequestCreditExample requestCreditExample = null;
		if (!startDate.equals("") && !endDate.equals("")){
			requestCreditExample = new RequestCreditExample();
			requestCreditExample.createCriteria().andBackDateBetween(startDate,endDate);
			return this.readMapper.selectRequestTrend(requestCreditExample);
		}
		return this.readMapper.selectRequestTrend(requestCreditExample);
	}
	 /**
	  * @Author:SHIGS
	  * @Description 查询销售大区
	  * @Date:9:13 2017/10/24
	  * @modified By
	  */
	@Override
	public List<Map> selectArea() {
		return this.readMapper.selectArea();
	}
	@Override
	public List<Map> selectCountry(String area) {
		RequestCreditExample requestCreditExample = new RequestCreditExample();
		requestCreditExample.createCriteria().andSalesAreaEqualTo(area);
		return this.readMapper.selectCountry(requestCreditExample);
	}
	@Override
	public Map selectByAreaOrCountry(String area,String country) {
		RequestCreditExample requestCreditExample = null;
		if (country.equals("") || country == null){
			requestCreditExample  = new RequestCreditExample();
			requestCreditExample.createCriteria().andSalesAreaEqualTo(area);
			return this.readMapper.selectByAreaOrCountry(requestCreditExample);
		}else if (!area.equals("") && !country.equals("")){
			requestCreditExample  = new RequestCreditExample();
			requestCreditExample.createCriteria().andSalesAreaEqualTo(area).andSalesCountryEqualTo(country);
			return this.readMapper.selectByAreaOrCountry(requestCreditExample);
		}
		return this.readMapper.selectByAreaOrCountry(requestCreditExample);
	}
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
			try {
				rc.setCreateAt(
						DateUtil.parseString2Date(strArr[9], "yyyy/M/d","yyyy/M/d hh:mm:ss",DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "创建日期日期格式错误");
				continue;
			}
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