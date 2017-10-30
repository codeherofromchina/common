package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.OrderOutboundCountMapper;
import com.erui.report.model.OrderOutboundCount;
import com.erui.report.service.OrderOutboundCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class OrderOutboundCountServiceImpl extends BaseService<OrderOutboundCountMapper>
		implements OrderOutboundCountService {
	private final static Logger logger = LoggerFactory.getLogger(StorageOrganiCountServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		OrderOutboundCount ooc = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT, response, index + 1)) {
				continue;
			}
			ooc = new OrderOutboundCount();

			try {
				ooc.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), index + 1, "时间字段格式错误");
				continue;
			}
			ooc.setOutboundNum(strArr[1]);
			ooc.setContractNum(strArr[2]);
			ooc.setDestination(strArr[3]);
			ooc.setExecuteNum(strArr[4]);

			try {
				ooc.setPackCount(new BigDecimal(strArr[5]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), index + 1, "包装件数字段不是数字");
				continue;
			}

			try {
				ooc.setOutboundDate(DateUtil.parseString2Date(strArr[6], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), index + 1, "出库时间字段格式错误");
				continue;
			}
			try {
				ooc.setAmounts(new BigDecimal(strArr[7]));
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), index + 1, "金额字段不是数字");
				continue;
			}
			ooc.setRemark(strArr[8]);

			try {
				if (!testOnly) {
					writeMapper.deleteByExample(null);
					writeMapper.insertSelective(ooc);
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), index + 1, e.getMessage());
				continue;
			}
			response.incrSuccess();

		}
		response.setDone(true);

		return response;
	}
}