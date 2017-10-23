package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.StorageOrganiCountMapper;
import com.erui.report.model.StorageOrganiCount;
import com.erui.report.service.StorageOrganiCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class StorageOrganiCountServiceImpl extends BaseService<StorageOrganiCountMapper>
		implements StorageOrganiCountService {
	private final static Logger logger = LoggerFactory.getLogger(StorageOrganiCountServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		StorageOrganiCount soc = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			soc = new StorageOrganiCount();
			try {
				soc.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.STORAGE_ORGANI_COUNT.getTable(), index + 1, "时间字段格式错误");
				continue;
			}
			soc.setOrganiName(strArr[1]);
			try {
				soc.setTrayNum(new BigDecimal(strArr[2]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.STORAGE_ORGANI_COUNT.getTable(), index + 1, "托盘数量不是数字");
				continue;
			}

			try {
				soc.setProductNum(new BigDecimal(strArr[3]).intValue());
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.STORAGE_ORGANI_COUNT.getTable(), index + 1, "产品数量不是数字");
				continue;
			}

			soc.setRemark(strArr[4]);

			try {
				writeMapper.insertSelective(soc);
				response.incrSuccess();
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.STORAGE_ORGANI_COUNT.getTable(), index + 1, e.getMessage());
			}

		}
		response.setDone(true);

		return response;
	}
}