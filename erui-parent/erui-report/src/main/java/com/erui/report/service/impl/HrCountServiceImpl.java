package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

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

	/*
	 * 人力资源数据列表
	 *
	 */
	@Override
	public List<HrCount> findAll() {
		return this.readMapper.findAll();

	}

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		HrCount hc = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			hc = new HrCount();
			try {
				hc.setCreateAt(
						DateUtil.parseString2Date(strArr[0], DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), index + 1, "日期字段格式错误");
				continue;
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
			//TODO 试用期人数	转正人数两字段数据库中还不存在
//			strArr[4];
//			strArr[5];
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

		}
		response.setDone(true);

		return response;
	}

	
	public static void main(String[] args) {
		String str = "55.0a";
		Number integer = new BigDecimal(str);
		System.out.println(integer.intValue());
	}
}
