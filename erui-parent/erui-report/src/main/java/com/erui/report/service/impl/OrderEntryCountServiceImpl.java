package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.OrderEntryCountMapper;
import com.erui.report.model.OrderEntryCount;
import com.erui.report.service.OrderEntryCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class OrderEntryCountServiceImpl extends BaseService<OrderEntryCountMapper> implements OrderEntryCountService {
	private final static Logger logger = LoggerFactory.getLogger(StorageOrganiCountServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		OrderEntryCount oec = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.ORDER_ENTRY_COUNT, response, index + 1)) {
				continue;
			}
			oec = new OrderEntryCount();

			try {
				oec.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), index + 1, "时间字段格式错误");
				continue;
			}
			oec.setEntryNum(strArr[1]);
			oec.setExecuteNum(strArr[2]);

			oec.setContractNum(strArr[3]);

			try {
				oec.setEntryCount(new BigDecimal(strArr[4]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), index + 1, "数量字段不是数字");
				continue;
			}
			try {
				oec.setAmounts(new BigDecimal(strArr[5]));
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), index + 1, "金额字段不是数字");
				continue;
			}
			try {
				oec.setStorageDate(DateUtil.parseString2Date(strArr[6], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), index + 1, "入库时间格式错误");
				continue;
			}
			oec.setBuyer(strArr[7]);
			oec.setSuppliName(strArr[8]);
			oec.setRemark(strArr[9]);

			try {
				if (!testOnly) {
					writeMapper.deleteByExample(null);
					writeMapper.insertSelective(oec);
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), index + 1, e.getMessage());
				continue;
			}
			response.incrSuccess();

		}
		response.setDone(true);

		return response;
	}
}