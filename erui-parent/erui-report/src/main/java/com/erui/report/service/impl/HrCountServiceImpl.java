package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.erui.report.model.HrCountExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.HrCountMapper;
import com.erui.report.model.HrCount;
import com.erui.report.service.HrCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

/**
 * Created by lirb on 2017/10/19.
 */

@Service
public class HrCountServiceImpl extends BaseService<HrCountMapper> implements HrCountService {
	private final static Logger logger = LoggerFactory.getLogger(HrCountServiceImpl.class);
	private static final DecimalFormat df=new DecimalFormat("0.00");

	 /**
	  * @Author:SHIGS
	  * @Description
	  * @Date:18:02 2017/10/24
	  * @modified By
	  */
	@Override
	public List<HrCount> findAll() {
		return this.readMapper.selectByExample(null);

	}
	 /**
	  * @Author:SHIGS
	  * @Description
	  * @Date:18:02 2017/10/24
	  * @modified By
	  */
	@Override
	public Map<String, Object> selectHrCountByPart(Date startTime, Date endDate) {
		HrCountExample hrCountExample = new HrCountExample();
		hrCountExample.createCriteria().andCreateAtBetween(startTime, endDate);
		return this.readMapper.selectHrCountByPart(hrCountExample);
	}
	 /**
	  * @Author:SHIGS
	  * @Description 查询部门
	  * @Date:18:02 2017/10/24
	  * @modified By
	  */
	@Override
	public List<Map> selectBigDepart() {
		return this.readMapper.selectBigDepart();
	}

	@Override
	public Map selectHrCountByDepart(String depart,Date startTime,Date endDate) {
		HrCountExample hrCountExample = null;
		if (!depart.equals("") || depart!=null){
			hrCountExample = new HrCountExample();
			hrCountExample.createCriteria().andCreateAtBetween(startTime,endDate);
			return this.readMapper.selectHrCountByPart(hrCountExample);
		} else {
			hrCountExample = new HrCountExample();
			hrCountExample.createCriteria().andBigDepartEqualTo(depart).andCreateAtBetween(startTime,endDate);
			return this.readMapper.selectHrCountByPart(hrCountExample);
		}
	}
	 /**
	  * @Author:SHIGS
	  * @Description
	  * @Date:11:31 2017/10/25
	  * @modified By
	  */
	@Override
	public List<Map> selectDepartmentCount() {
		List<Map> bigList = readMapper.selectBigDepartCount();
		List<Map> departList = readMapper.selectDepartmentCount();
		Map<String,Map<String,Object>> departMap2 = new HashMap<>();
		List<Map> result = new ArrayList<>();
		for (Map mapBig:bigList) {
			//满编率
			double staffFullRate = Double.parseDouble(df.format(mapBig.get("staffFullRate")));
			//试用占比
			double tryRate = Double.parseDouble(df.format(mapBig.get("tryRate"))) ;
			//增长率
			double addRate = Double.parseDouble(df.format(mapBig.get("addRate")));
			//转岗流失率
			double leaveRate = Double.parseDouble(df.format(mapBig.get("leaveRate")));
			//外籍占比
			double foreignRate = Double.parseDouble(df.format(mapBig.get("foreignRate")));
			Map<String,Object> departMap = new HashMap<>();
			departMap.put("staffFullRate",staffFullRate);
			departMap.put("tryRate",tryRate);
			departMap.put("addRate",addRate);
			departMap.put("leaveRate",leaveRate);
			departMap.put("foreignRate",foreignRate);
			departMap.put("departName",mapBig.get("big_depart"));
			departMap2.put(mapBig.get("big_depart").toString(),departMap);
			result.add(departMap);
		}
		for (Map mapDepart:departList) {
			Map<String,Object> bigDepartment = departMap2.get(mapDepart.get("big_depart").toString());
			//满编率
			double staffFullRate = Double.parseDouble(df.format(mapDepart.get("staffFullRate")));
			//试用占比
			double tryRate = Double.parseDouble(df.format(mapDepart.get("tryRate"))) ;
			//增长率
			double addRate = Double.parseDouble(df.format(mapDepart.get("addRate")));
			//转岗流失率
			double leaveRate = Double.parseDouble(df.format(mapDepart.get("leaveRate")));
			//外籍占比
			double foreignRate = Double.parseDouble(df.format(mapDepart.get("foreignRate")));
			Map<String,Object> departMap = new HashMap<>();
			departMap.put("staffFullRate",staffFullRate);
			departMap.put("tryRate",tryRate);
			departMap.put("addRate",addRate);
			departMap.put("leaveRate",leaveRate);
			departMap.put("foreignRate",foreignRate);
			departMap.put("departmentName", mapDepart.get("department").toString());
			Object obj = bigDepartment.get("children");
			if (obj == null) {
				obj = new ArrayList<Map<String,Double>>();
				bigDepartment.put("children", obj);
			}
			((List)obj).add(departMap);
		}
		return result;
	}
	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		HrCount hc = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.HR_COUNT, response, index + 1)) {
				continue;
			}
			hc = new HrCount();
			if (strArr[0] != null) {
				try {
					hc.setCreateAt(
							DateUtil.parseString2Date(strArr[0], DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "日期字段格式错误");
					continue;
				}
			}
			hc.setDepartment(strArr[1]);
			try {
				hc.setPlanCount(new BigDecimal(strArr[2]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "计划人数不是数字");
				continue;
			}
			try {
				hc.setRegularCount(new BigDecimal(strArr[3]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "在编人数不是数字");
				continue;
			}
			// TODO 试用期人数 转正人数两字段数据库中还不存在
			// strArr[4];
			// strArr[5];
			try {

				hc.setChinaCount(new BigDecimal(strArr[6]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "中方字段不是数字");
				continue;
			}
			try {
				hc.setForeignCount(new BigDecimal(strArr[7]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "外籍字段不是数字");
				continue;
			}
			try {
				hc.setNewCount(new BigDecimal(strArr[8]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "新进字段不是数字");
				continue;
			}
			try {
				hc.setGroupTransferIn(new BigDecimal(strArr[9]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "集团转岗（进）字段不是数字");
				continue;
			}
			try {
				hc.setGroupTransferOut(new BigDecimal(strArr[10]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "集团转岗（出）字段不是数字");
				continue;
			}
			try {
				hc.setDimissionCount(new BigDecimal(strArr[11]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "离职字段不是数字");
				continue;
			}

			try {
				if (!testOnly) {
					writeMapper.insertSelective(hc);
					response.incrSuccess();
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, e.getMessage());
			}

		}
		response.setDone(true);

		return response;
	}

}
