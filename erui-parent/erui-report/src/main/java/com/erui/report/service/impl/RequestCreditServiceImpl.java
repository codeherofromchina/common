package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.erui.comm.RateUtil;
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
	private static final DecimalFormat df = new DecimalFormat("0.00");
	@Override
	public Map<String,Object> selectTotal() {
		Map<String,Object> mapMount=readMapper.selectTotal();
		BigDecimal receiveAmount = new  BigDecimal(mapMount.get("sd").toString());
		BigDecimal orderAmount = new BigDecimal(mapMount.get("ed").toString());
		Double received = orderAmount.doubleValue() - receiveAmount.doubleValue();
		String s = df.format(2);
		Map<String,Object> data = new HashMap<>();
		data.put("notReceive", df.format(receiveAmount.doubleValue()/10000)+"万$");
		data.put("received", df.format(received/10000)+"万$");
		data.put("totalReceive", df.format(orderAmount.doubleValue()/10000)+"万$");
		return data;
	}
	 /**
	  * @Author:SHIGS
	  * @Description 应收账款统计
	  * @Date:9:14 2017/10/24
	  * @modified By
	  */
	@Override
	public Map<String,Object> selectRequestTotal(Date startDate, Date endDate) {
		RequestCreditExample requestCreditExample = null;
		if (startDate != null && endDate != null){
			requestCreditExample = new RequestCreditExample();
			requestCreditExample.createCriteria().andCreateAtBetween(startDate,endDate);
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
	public Map<String,Object> selectRequestTrend(int days,String receiveName) {
		Date startDate = DateUtil.recedeTime(days);
		Date nextTime =  DateUtil.recedeTime(-30);
		List<Map> nextMap = null;
		if (!startDate.equals("") ){
			RequestCreditExample nextCreditExample = new RequestCreditExample();
			nextCreditExample.createCriteria().andBackDateBetween(new Date(),nextTime);
			nextMap = readMapper.selectRequestTrend(nextCreditExample);
		}
		List<Double> receivableList = new ArrayList<>();
		List<Double> notReceiveList = new ArrayList<>();
		List<Double> receivedList = new ArrayList<>();
		List<Double> nextList = new ArrayList<>();
		List<String> dateList = new ArrayList<>();
		List<String> nextDate = new ArrayList<>();
		Map<String,Map<String,Double>> sqlDate = new HashMap<>();
		Map<String,Map<String,Double>> sqlDate02 = new HashMap<>();
		Map<String,Double> linkData = null;
		//遍历下月应收
		for (Map map3:nextMap) {
			linkData = new HashMap<>();
			BigDecimal nextReceivable = new BigDecimal(map3.get("order_amount").toString());
			Date date2 = (Date) map3.get("back_date");
			String dateString = com.erui.comm.DateUtil.format("MM月dd日",date2);
			linkData.put("nextReceivable",nextReceivable.doubleValue());
			sqlDate.put(dateString,linkData);
		}
		for (int i = 0; i < 31; i++){
			Date datetime = com.erui.comm.DateUtil.recedeTime(-i);
			String date = com.erui.comm.DateUtil.format("MM月dd日",datetime);
			if (sqlDate.containsKey(date)){
				nextDate .add(date);
				nextList.add(sqlDate.get(date).get("nextReceivable"));
			}else {
				nextDate.add(date);
				nextList.add(0.00);
			}
		}
		RequestCreditExample requestCreditExample = new RequestCreditExample();
		requestCreditExample.createCriteria().andCreateAtBetween(startDate,new Date());
		List<Map> requestMap = readMapper.selectRequestTrend(requestCreditExample);
		//应收，已收，未收
		for (Map map2:requestMap) {
			linkData = new HashMap<>();
			BigDecimal receivable = new BigDecimal(map2.get("order_amount").toString());
			BigDecimal notReceive = new BigDecimal(map2.get("receive_amount").toString());
			BigDecimal received = new BigDecimal(map2.get("received").toString());
			Date date2 = (Date) map2.get("create_at");
			String dateString = com.erui.comm.DateUtil.format("MM月dd日",date2);
			linkData.put("receivable",receivable.doubleValue());
			linkData.put("notReceive",notReceive.doubleValue());
			linkData.put("received",received.doubleValue());
			sqlDate02.put(dateString,linkData);
		}
		for (int i = 0; i < days; i++) {
			Date datetime = com.erui.comm.DateUtil.recedeTime(days - (i+1) );
			String date = com.erui.comm.DateUtil.format("MM月dd日",datetime);
			if (sqlDate02.containsKey(date)){
				dateList.add(date);
				receivableList.add(sqlDate02.get(date).get("receivable"));
				notReceiveList.add(sqlDate02.get(date).get("notReceive"));
				receivedList.add(sqlDate02.get(date).get("received"));
			}else {
				dateList.add(date);
				receivableList.add(0.00);
				notReceiveList.add(0.00);
				receivedList.add(0.00);
			}
		}
		String [] s = {"应收账款","应收未收","应收已收","下月应收"};
		Map<String,Object> data = new HashMap();
		if (receiveName.equals("receivable")){
			data.put("legend",s[0]);
			data.put("xAxis",dateList);
			data.put("yAxis",receivableList);

		}else if (receiveName.equals("notReceive")){
			data.put("legend",s[1]);
			data.put("xAxis",dateList);
			data.put("yAxis",notReceiveList);
		}else if(receiveName.equals("received")){
			data.put("legend",s[2]);
			data.put("xAxis",dateList);
			data.put("yAxis",receivedList);
		}else {
			data.put("legend",s[3]);
			data.put("xAxis",nextDate);
			data.put("yAxis",nextList);
		}
		return data;
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
	public Map<String,Object> selectArea() {
		List<Map> areaMap = readMapper.selectArea();
		List<String> areaList = new ArrayList<>();
		for (Map map:areaMap) {
			String area = map.get("sales_area").toString();
			areaList.add(area);
		}
		Map<String,Object> result = new HashMap<>();
		result.put("area",areaList);
		return result;
	}
	 /**
	  * @Author:SHIGS
	  * @Description 查询国家
	  * @Date:17:15 2017/10/27
	  * @modified By
	  */
	@Override
	public Map<String,Object> selectCountry(String area) {
		RequestCreditExample requestCreditExample = new RequestCreditExample();
		requestCreditExample.createCriteria().andSalesAreaEqualTo(area);
		List<Map> areaCountry = readMapper.selectCountry(requestCreditExample);
		List<String> countryList = new ArrayList<>();
		for (Map map2:areaCountry) {
			String country = map2.get("sales_country").toString();
			countryList.add(country);
		}
		Map<String,Object> result = new HashMap<>();
		result.put("country",countryList);
		return result;
	}
	 /**
	  * @Author:SHIGS
	  * @Description 区域明细
	  * @Date:17:15 2017/10/27
	  * @modified By
	  */
	@Override
	public Map<String,Object> selectByAreaOrCountry(String area,String country) {
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
						DateUtil.parseString2Date(strArr[9], DateUtil.SHORT_FORMAT_STR , "yyyy/M/d","yyyy/M/d hh:mm:ss",DateUtil.FULL_FORMAT_STR));
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
						DateUtil.parseString2Date(strArr[15], DateUtil.FULL_FORMAT_STR,"yyyy/M/d","yyyy/M/d hh:mm:ss",DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "下次回款日期格式错误");
				continue;
			}

			try {
				if (!testOnly) {
					writeMapper.deleteByExample(null);
					writeMapper.insertSelective(rc);
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, e.getMessage());
				continue;
			}
			response.incrSuccess();

		}
		response.setDone(true);

		return response;
	}

}