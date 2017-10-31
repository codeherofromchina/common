package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.erui.comm.RateUtil;
import com.erui.report.model.HrCountExample;
import org.aspectj.apache.bcel.generic.IF_ACMPEQ;
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
	private DecimalFormat df = new DecimalFormat("0.0000");
	/**
	 * @Author:SHIGS
	 * @Description
	 * @Date:16:17 2017/10/25
	 * @modified By
	 */
	@Override
	public Map<String, Object> selectHrCount(int days) {
		// 当前时期
		Date startTime = com.erui.comm.DateUtil.recedeTime(days);
		// 环比时段
		Date chainDate = com.erui.comm.DateUtil.recedeTime(days * 2);
		// 当前时期
		HrCountExample hrCountExample = new HrCountExample();
		hrCountExample.createCriteria().andCreateAtBetween(startTime, new Date());
		// 当前时段
		Map CurHrCountMap = readMapper.selectHrCountByPart(hrCountExample);
		if (CurHrCountMap == null){
			CurHrCountMap = new HashMap<>();
			CurHrCountMap.put("s1",0);
			CurHrCountMap.put("s2",0);
			CurHrCountMap.put("s3",0);
			CurHrCountMap.put("s4",0);
			CurHrCountMap.put("s5",0);
			CurHrCountMap.put("s6",0);
			CurHrCountMap.put("s7",0);
			CurHrCountMap.put("s8",0);
			CurHrCountMap.put("s9",0);
			CurHrCountMap.put("s10",0);

		}
		BigDecimal planCount = new BigDecimal(CurHrCountMap.get("s1").toString());
		BigDecimal regularCount = new BigDecimal(CurHrCountMap.get("s2").toString());
		BigDecimal tryCount = new BigDecimal(CurHrCountMap.get("s3").toString());
		BigDecimal turnRightCount = new BigDecimal(CurHrCountMap.get("s4").toString());
		BigDecimal dimissionCount = new BigDecimal(CurHrCountMap.get("s8").toString());
		BigDecimal turnJobin = new BigDecimal(CurHrCountMap.get("s9").toString());
		BigDecimal turnJobout = new BigDecimal(CurHrCountMap.get("s10").toString());
		// 环比时段
		HrCountExample chainHrCountExample = new HrCountExample();
		chainHrCountExample.createCriteria().andCreateAtBetween(chainDate, startTime);
		Map chainHrCountMap = readMapper.selectHrCountByPart(chainHrCountExample);
		if (chainHrCountMap == null ) {
			chainHrCountMap = new HashMap<>();
			chainHrCountMap.put("s1",0);
			chainHrCountMap.put("s2",0);
			chainHrCountMap.put("s3",0);
			chainHrCountMap.put("s4",0);
			chainHrCountMap.put("s5",0);
			chainHrCountMap.put("s6",0);
			chainHrCountMap.put("s7",0);
			chainHrCountMap.put("s8",0);
			chainHrCountMap.put("s9",0);
			chainHrCountMap.put("s10",0);
		}
		// 环比人数
		BigDecimal chainRegularCount = new BigDecimal(chainHrCountMap.get("s2").toString());
		BigDecimal chainTurnRightCount = new BigDecimal(chainHrCountMap.get("s4").toString());
		// 环比增加人数
		int chainFullAdd = regularCount.intValue() - chainRegularCount.intValue();
		int chainTurnAdd = chainTurnRightCount.intValue() - turnRightCount.intValue();
		// 环比
		double staffFullChainRate = RateUtil.doubleChainRate(chainFullAdd, chainRegularCount.intValue());
		double turnRightChainRate = RateUtil.doubleChainRate(chainTurnAdd, chainTurnRightCount.intValue());
		// 满编率
		double staffFullRate = RateUtil.intChainRate(regularCount.intValue(), planCount.intValue());
		// 转正率
		double turnRightRate = RateUtil.intChainRate(turnRightCount.intValue(), tryCount.intValue());
		Map<String, Object> data = new HashMap<>();
		data.put("staffFullRate", Double.parseDouble(df.format(staffFullRate)));
		data.put("staffFullChainRate", Double.parseDouble(df.format(staffFullChainRate)));
		data.put("turnRightRate", Double.parseDouble(df.format(turnRightRate)));
		data.put("turnRightChainRate", Double.parseDouble(df.format(turnRightChainRate)));
		data.put("onDuty", regularCount);
		data.put("plan", planCount);
		data.put("turnJobin", turnJobin.intValue());
		data.put("turnJobout", turnJobout);
		data.put("try", tryCount);
		data.put("turnRight", turnRightCount);
		data.put("leave", dimissionCount);
		return data;
	}

	/**
	 * @Author:SHIGS
	 * @Description 人力-总览
	 * @Date:18:02 2017/10/24
	 * @modified By
	 */
	@Override
	public Map<String, Object> selectHrCountByPart(int days) {
		Date startTime = com.erui.comm.DateUtil.recedeTime(days);
		// 环比时段
		Date chainDate = com.erui.comm.DateUtil.recedeTime(days * 2);
		// 当前时期
		HrCountExample hrCountExample = new HrCountExample();
		hrCountExample.createCriteria().andCreateAtBetween(startTime, new Date());
		// 当前时段
		Map<String,Object> CurHrCountMap = readMapper.selectHrCountByPart(hrCountExample);
		if (CurHrCountMap == null){
			CurHrCountMap = new HashMap<>();
			CurHrCountMap.put("s1",0);
			CurHrCountMap.put("s2",0);
			CurHrCountMap.put("s3",0);
			CurHrCountMap.put("s4",0);
			CurHrCountMap.put("s5",0);
			CurHrCountMap.put("s6",0);
			CurHrCountMap.put("s7",0);
			CurHrCountMap.put("s8",0);
			CurHrCountMap.put("s9",0);
			CurHrCountMap.put("s10",0);

		}
		BigDecimal planCount = new BigDecimal(CurHrCountMap.get("s1").toString());
		BigDecimal regularCount = new BigDecimal(CurHrCountMap.get("s2").toString());
		BigDecimal tryCount = new BigDecimal(CurHrCountMap.get("s3").toString());
		BigDecimal turnRightCount = new BigDecimal(CurHrCountMap.get("s4").toString());
		BigDecimal chinaCount = new BigDecimal(CurHrCountMap.get("s5").toString());
		BigDecimal foreignCount = new BigDecimal(CurHrCountMap.get("s6").toString());
		BigDecimal newCount = new BigDecimal(CurHrCountMap.get("s7").toString());
		BigDecimal dimissionCount = new BigDecimal(CurHrCountMap.get("s8").toString());
		BigDecimal turnJobin = new BigDecimal(CurHrCountMap.get("s9").toString());
		BigDecimal turnJobout = new BigDecimal(CurHrCountMap.get("s10").toString());
		HrCountExample chainHrCountExample = new HrCountExample();
		chainHrCountExample.createCriteria().andCreateAtBetween(chainDate, startTime);
		// 环比时段
		Map<String,Object> chainHrCountMap = readMapper.selectHrCountByPart(chainHrCountExample);
		System.out.println(chainHrCountMap);
		if (chainHrCountMap == null ) {
			chainHrCountMap = new HashMap<>();
			chainHrCountMap.put("s1",0);
			chainHrCountMap.put("s2",0);
			chainHrCountMap.put("s3",0);
			chainHrCountMap.put("s4",0);
			chainHrCountMap.put("s5",0);
			chainHrCountMap.put("s6",0);
			chainHrCountMap.put("s7",0);
			chainHrCountMap.put("s8",0);
			chainHrCountMap.put("s9",0);
			chainHrCountMap.put("s10",0);
		}
			// 环比人数
			BigDecimal chainRegularCount = new BigDecimal(chainHrCountMap.get("s2").toString());
			BigDecimal chainTurnRightCount = new BigDecimal(chainHrCountMap.get("s4").toString());
			// BigDecimal chainTryCount = new
			// BigDecimal(chainHrCountMap.get("s3").toString());
			BigDecimal chainNewCount = new BigDecimal(chainHrCountMap.get("s7").toString());
			// BigDecimal chainDimissionCount = new
			// BigDecimal(chainHrCountMap.get("s8").toString());
			BigDecimal chainTurnJobin = new BigDecimal(chainHrCountMap.get("s9").toString());
			// BigDecimal chainTurnJobout = new
			// BigDecimal(chainHrCountMap.get("s10").toString());
			BigDecimal chainForeignCount = new BigDecimal(chainHrCountMap.get("s6").toString());

		// 满编率
		double staffFullRate = RateUtil.intChainRate(regularCount.intValue(), planCount.intValue());
		// 试用占比
		double tryRate = RateUtil.intChainRate(tryCount.intValue(), regularCount.intValue());
		// 增长率
		double addRate = RateUtil.intChainRate(newCount.intValue(), regularCount.intValue())
				- RateUtil.intChainRate(dimissionCount.intValue(), regularCount.intValue());
		// 转岗流失率
		double leaveRate = RateUtil.intChainRate(turnJobout.intValue(), regularCount.intValue())
				- RateUtil.intChainRate(turnJobin.intValue(), regularCount.intValue());
		// 外籍占比
		double foreignRate = RateUtil.intChainRate(foreignCount.intValue(), regularCount.intValue());
		// 环比增加人数
		int chainFullAdd = regularCount.intValue() - chainRegularCount.intValue();
		int chainTurnAdd = turnRightCount.intValue() - chainTurnRightCount.intValue();
		int chainAdd = newCount.intValue() - chainNewCount.intValue();
		int chainTurnJobinAdd = turnJobin.intValue() - chainTurnJobin.intValue();
		int chainForeignCountAdd = foreignCount.intValue() - chainForeignCount.intValue();
		// 环比
		double staffFullChainRate = RateUtil.intChainRate(chainFullAdd, chainRegularCount.intValue());
		double tryChainRate = RateUtil.intChainRate(chainTurnAdd, chainTurnRightCount.intValue());
		double chainAddRate = RateUtil.intChainRate(chainAdd, chainNewCount.intValue());
		double groupTransferChainRate = RateUtil.intChainRate(chainTurnJobinAdd, chainTurnJobin.intValue());
		double foreignChainRate = RateUtil.intChainRate(chainForeignCount.intValue(), chainForeignCountAdd);
		Map<String, Object> data = new HashMap<>();
		data.put("newCount", newCount);
		data.put("foreignCount", foreignCount);
		data.put("chinaCount", chinaCount);
		data.put("leaveRate", leaveRate);
		data.put("newAdd", Double.parseDouble(df.format(addRate)));
		data.put("foreignRate", Double.parseDouble(df.format(foreignRate)));
		data.put("foreignChainRate", Double.parseDouble(df.format(foreignChainRate)));
		data.put("groupTransferIn", turnJobin.intValue());
		data.put("groupTransferOut", turnJobout);
		data.put("groupTransferChainRate", Double.parseDouble(df.format(groupTransferChainRate)));
		data.put("newChainRate", Double.parseDouble(df.format(chainAddRate)));
		data.put("staffFullRate", Double.parseDouble(df.format(staffFullRate)));
		data.put("staffFullChainRate", Double.parseDouble(df.format(staffFullChainRate)));
		data.put("tryRate", Double.parseDouble(df.format(tryRate)));
		data.put("turnRightCount", turnRightCount);
		data.put("turnRightChainRate", Double.parseDouble(df.format(tryChainRate)));
		data.put("staffFullCount", regularCount);
		data.put("planCount", planCount);
		data.put("tryCount", tryCount);
		data.put("dimissionCount", dimissionCount);
		return data;
	}

	/**
	 * @Author:SHIGS
	 * @Description 查询部门
	 * @Date:18:02 2017/10/24
	 * @modified By
	 */
	@Override
	public Map selectBigDepart() {
		List<Map> orgMap = readMapper.selectBigDepart();
		List<String> departList = new ArrayList<>();
		for (Map map : orgMap) {
			String area = map.get("big_depart").toString();
			departList.add(area);
		}
		Map<String, List<String>> mapData = new HashMap<>();
		mapData.put("depart", departList);
		return mapData;
	}
     /**
      * @Author:SHIGS
      * @Description 数据对比
      * @Date:11:53 2017/10/30
      * @modified By
      */
	@Override
	public Map<String,Object> selectHrCountByDepart(String depart,int days) {
        //当前时期
        Date startTime = DateUtil.recedeTime(days);
		Map curHrCountMap = new HashMap();
		if ("".equals(depart) || depart == null) {
			HrCountExample hrCountExample  = new HrCountExample();
			hrCountExample.createCriteria().andCreateAtBetween(startTime, new Date());
			curHrCountMap = readMapper.selectHrCountByPart(hrCountExample);
			if (curHrCountMap == null){
				curHrCountMap = new HashMap<>();
				curHrCountMap.put("s1",0);
				curHrCountMap.put("s2",0);
				curHrCountMap.put("s3",0);
				curHrCountMap.put("s4",0);
				curHrCountMap.put("s5",0);
				curHrCountMap.put("s6",0);
				curHrCountMap.put("s7",0);
				curHrCountMap.put("s8",0);
				curHrCountMap.put("s9",0);
				curHrCountMap.put("s10",0);

			}
		} else {
			HrCountExample hrCountExample02 = new HrCountExample();
			hrCountExample02.createCriteria().andBigDepartEqualTo(depart).andCreateAtBetween(startTime, new Date());
			curHrCountMap = readMapper.selectHrCountByPart(hrCountExample02);
			if (curHrCountMap == null){
				curHrCountMap = new HashMap<>();
				curHrCountMap.put("s1",0);
				curHrCountMap.put("s2",0);
				curHrCountMap.put("s3",0);
				curHrCountMap.put("s4",0);
				curHrCountMap.put("s5",0);
				curHrCountMap.put("s6",0);
				curHrCountMap.put("s7",0);
				curHrCountMap.put("s8",0);
				curHrCountMap.put("s9",0);
				curHrCountMap.put("s10",0);

			}
		}
		// 当前时段
		BigDecimal planCount = new BigDecimal(curHrCountMap.get("s1").toString());
		BigDecimal regularCount = new BigDecimal(curHrCountMap.get("s2").toString());
		BigDecimal tryCount = new BigDecimal(curHrCountMap.get("s3").toString());
		BigDecimal turnRightCount = new BigDecimal(curHrCountMap.get("s4").toString());
		BigDecimal chinaCount = new BigDecimal(curHrCountMap.get("s5").toString());
		BigDecimal foreignCount = new BigDecimal(curHrCountMap.get("s6").toString());
		BigDecimal newCount = new BigDecimal(curHrCountMap.get("s7").toString());
		BigDecimal dimissionCount = new BigDecimal(curHrCountMap.get("s8").toString());
		BigDecimal turnJobin = new BigDecimal(curHrCountMap.get("s9").toString());
		BigDecimal turnJobout = new BigDecimal(curHrCountMap.get("s10").toString());
		// 满编率
		double staffFullRate = RateUtil.intChainRate(regularCount.intValue(), planCount.intValue());
		// 试用占比
		double tryRate = RateUtil.intChainRate(tryCount.intValue(), regularCount.intValue());
		// 增长率
		double addRate = RateUtil.intChainRate(newCount.intValue(), regularCount.intValue())
				- RateUtil.intChainRate(dimissionCount.intValue(), regularCount.intValue());
		// 转岗流失率
		double leaveRate = RateUtil.intChainRate(turnJobout.intValue(), regularCount.intValue())
				- RateUtil.intChainRate(turnJobin.intValue(), regularCount.intValue());
		// 外籍占比
		double foreignRate = RateUtil.intChainRate(foreignCount.intValue(), regularCount.intValue());
		List<Integer> seriesList01 = new ArrayList<>();
		List<Integer> seriesList02 = new ArrayList<>();
		seriesList01.add(planCount.intValue());
		seriesList02.add(regularCount.intValue());
		seriesList01.add(turnRightCount.intValue());
		seriesList02.add(tryCount.intValue());
		seriesList01.add(newCount.intValue());
		seriesList02.add(dimissionCount.intValue());
		seriesList01.add(turnJobin.intValue());
		seriesList02.add(turnJobout.intValue());
		seriesList01.add(chinaCount.intValue());
		seriesList02.add(foreignCount.intValue());
		String[] xArray = { "计划编制/到岗编制", "转正/试用", "新进/离职", "转岗(进)/转岗(出)", "中方/外籍" };
		Map<String, Object> data = new HashMap<>();
		data.put("xAxis", xArray);
		data.put("seriesData01", seriesList01);
		data.put("seriesData02", seriesList02);
		data.put("staffFullRate", staffFullRate);
		data.put("tryRate", tryRate);
		data.put("addRate", addRate);
		data.put("leaveRate", leaveRate);
		data.put("foreignRate", foreignRate);
		return data;
	}

	/**
	 * @Author:SHIGS
	 * @Description 部门明细
	 * @Date:11:31 2017/10/25
	 * @modified By
	 */
	@Override
	public List<Map> selectDepartmentCount() {
		List<Map> bigList = readMapper.selectBigDepartCount();
		List<Map> departList = readMapper.selectDepartmentCount();
		Map<String, Map<String, Object>> departMap2 = new HashMap<>();
		List<Map> result = new ArrayList<>();
		for (Map mapBig : bigList) {
				if ("".equals(mapBig.get("big_depart")) || mapBig.get("big_depart") == null){
					continue;
				}
			// 满编率
			double staffFullRate = Double.parseDouble(df.format(mapBig.get("staffFullRate")).toString());
			// 试用占比
			double tryRate = Double.parseDouble(df.format(mapBig.get("tryRate")).toString());
			// 增长率
			double addRate = Double.parseDouble(df.format(mapBig.get("addRate")).toString());
			// 转岗流失率
			double leaveRate = Double.parseDouble(df.format(mapBig.get("leaveRate")).toString());
			// 外籍占比
			double foreignRate = Double.parseDouble(df.format(mapBig.get("foreignRate")).toString());
			Map<String, Object> departMap = new HashMap<>();
			departMap.put("staffFullRate", staffFullRate);
			departMap.put("tryRate", tryRate);
			departMap.put("addRate", addRate);
			departMap.put("leaveRate", leaveRate);
			departMap.put("foreignRate", foreignRate);
			departMap.put("departName", mapBig.get("big_depart"));
			departMap2.put(mapBig.get("big_depart").toString(), departMap);
			result.add(departMap);
		}
			for (Map mapDepart : departList) {
			Map<String, Object> bigDepartment = departMap2.get(mapDepart.get("big_depart").toString());
			// 满编率
			double staffFullRate = Double.parseDouble(df.format(mapDepart.get("staffFullRate")).toString());
			// 试用占比
			double tryRate = Double.parseDouble(df.format(mapDepart.get("tryRate")).toString());
			// 增长率
			double addRate = Double.parseDouble(df.format(mapDepart.get("addRate")).toString());
			// 转岗流失率
			double leaveRate = Double.parseDouble(df.format(mapDepart.get("leaveRate")).toString());
			// 外籍占比
			double foreignRate = Double.parseDouble(df.format(mapDepart.get("foreignRate")).toString());
			Map<String, Object> departMap = new HashMap<>();
			if ("".equals(mapDepart.get("department")) || mapDepart.get("department") == null){
				continue;
			}
			departMap.put("staffFullRate", staffFullRate);
			departMap.put("tryRate", tryRate);
			departMap.put("addRate", addRate);
			departMap.put("leaveRate", leaveRate);
			departMap.put("foreignRate", foreignRate);
			departMap.put("departmentName", mapDepart.get("department").toString());
			Object obj = bigDepartment.get("children");
			if (obj == null) {
				obj = new ArrayList<Map<String, Double>>();
				bigDepartment.put("children", obj);
			}
			((List) obj).add(departMap);
		}
		return result;
	}
	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		HrCount hc = null;
		String preBigDept = null;
		if (!testOnly) {
			writeMapper.truncateTable();
		}
		for (int index = 0; index < size; index++) {
			int cellIndex = index + 2;
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.HR_COUNT, response, cellIndex)) {
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
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "日期字段格式错误");
					continue;
				}
			}

			if (strArr[1] != null) {
				preBigDept = strArr[1];
			}
			hc.setBigDepart(preBigDept);
			hc.setDepartment(strArr[2]);

			if (strArr[3] != null) {
				try {
					hc.setPlanCount(new BigDecimal(strArr[3]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "计划人数不是数字");
					continue;
				}
			}

			if (strArr[4] != null) {
				try {
					hc.setRegularCount(new BigDecimal(strArr[4]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "在编人数不是数字");
					continue;
				}
			}

			if (strArr[5] != null) {
				try {
					hc.setTryCount(new BigDecimal(strArr[5]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "试用期人数不是数字");
					continue;
				}
			}

			if (strArr[6] != null) {
				try {
					hc.setTurnRightCount(new BigDecimal(strArr[6]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "转正人数不是数字");
					continue;
				}
			}

			if (strArr[7] != null) {
				try {
					hc.setChinaCount(new BigDecimal(strArr[7]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "中方字段不是数字");
					continue;
				}
			}
			if (strArr[8] != null) {
				try {
					hc.setForeignCount(new BigDecimal(strArr[8]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "外籍字段不是数字");
					continue;
				}
			}
			if (strArr[9] != null) {
				try {
					hc.setNewCount(new BigDecimal(strArr[9]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "新进字段不是数字");
					continue;
				}
			}
			if (strArr[10] != null) {
				try {
					hc.setGroupTransferIn(new BigDecimal(strArr[10]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "集团转岗（进）字段不是数字");
					continue;
				}
			}
			if (strArr[11] != null) {
				try {
					hc.setGroupTransferOut(new BigDecimal(strArr[11]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "集团转岗（出）字段不是数字");
					continue;
				}
			}
			if (strArr[12] != null) {
				try {
					hc.setDimissionCount(new BigDecimal(strArr[12]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "离职字段不是数字");
					continue;
				}
			}

			try {
				if (!testOnly) {
					writeMapper.insertSelective(hc);
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, e.getMessage());
				continue;
			}
			response.incrSuccess();

		}
		response.setDone(true);

		return response;
	}

}
