package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.MarketerCountMapper;
import com.erui.report.model.MarketerCount;
import com.erui.report.service.MarketerCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class MarketerCountServiceImpl extends BaseService<MarketerCountMapper> implements MarketerCountService {
	private final static Logger logger = LoggerFactory.getLogger(InquiryCountServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		MarketerCount mc = null;
		if (!testOnly) {
			writeMapper.truncateTable();
		}
		for (int index = 0; index < size; index++) {
			int cellIndex = index + 2;
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.MARKETER_COUNT, response, cellIndex)) {
				continue;
			}
			mc = new MarketerCount();
			try {
				mc.setCreateTime(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "时间字段格式错误");
				continue;
			}
			mc.setArea(strArr[1]);
			mc.setCountry(strArr[2]);
			mc.setMarketer(strArr[3]);
			if (strArr[9] != null) {
				try {
					mc.setInquiryCount(new BigDecimal(strArr[4]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "询单数量不是数字");
					continue;
				}
			}
			if (strArr[9] != null) {
				try {
					mc.setQuoteCount(new BigDecimal(strArr[5]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "报价数量不是数字");
					continue;
				}
			}
			if (strArr[9] != null) {
				try {
					mc.setBargainCount(new BigDecimal(strArr[6]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "成单数量不是数字");
					continue;
				}
			}
			if (strArr[9] != null) {
				try {
					mc.setBargainAmount(new BigDecimal(strArr[7]));
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "成单金额不是数字");
					continue;
				}
			}
			if (strArr[9] != null) {
				try {
					mc.setInquiryAmount(new BigDecimal(strArr[8]));
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "询单金额不是数字");
					continue;
				}
			}
			if (strArr[9] != null) {
				try {
					mc.setNewMemberCount(new BigDecimal(strArr[9]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "新增会员不是数字");
					continue;
				}
			}

			try {
				if (!testOnly) {
					writeMapper.insertSelective(mc);
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, e.getMessage());
				continue;
			}
			response.incrSuccess();

		}
		response.setDone(true);

		return response;

	}
}