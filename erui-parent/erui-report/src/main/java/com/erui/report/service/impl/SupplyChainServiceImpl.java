package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.SupplyChainMapper;
import com.erui.report.model.SupplyChain;
import com.erui.report.model.SupplyChainExample;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class SupplyChainServiceImpl extends BaseService<SupplyChainMapper> implements SupplyChainService {
	private final static Logger logger = LoggerFactory.getLogger(RequestCreditServiceImpl.class);

	/**
	 * @Author:SHIGS
	 * @Description 查询 spu sku 供应商完成量
	 * @Date:16:02 2017/10/21
	 * @modified By
	 */
	@Override
	public List<Map> selectFinishByDate(Date startDate, Date endDate) {
		SupplyChainExample supplyChainExample = new SupplyChainExample();
		supplyChainExample.createCriteria().andCreateAtBetween(startDate, endDate);
		return this.readMapper.selectFinishByDate(supplyChainExample);
	}

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		SupplyChain sc = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.REQUEST_CREDIT, response, index + 1)) {
				continue;
			}
			sc = new SupplyChain();

			try {
				sc.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d hh:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "日期字段格式错误");
				continue;
			}
			sc.setOrganization(strArr[1]);
			sc.setCategory(strArr[2]);
			sc.setItemClass(strArr[3]);

			try {
				sc.setPlanSkuNum(new BigDecimal(strArr[4]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "计划SKU字段非数字");
				continue;
			}
			try {
				sc.setFinishSkuNum(new BigDecimal(strArr[5]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "SKU实际完成字段非数字");
				continue;
			}
			try {
				sc.setPlanSpuNum(new BigDecimal(strArr[6]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "计划SPU字段非数字");
				continue;
			}
			try {
				sc.setFinishSpuNum(new BigDecimal(strArr[7]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "SPU实际完成字段非数字");
				continue;
			}
			try {
				sc.setPlanSuppliNum(new BigDecimal(strArr[8]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "计划供应商数量非数字");
				continue;
			}
			try {
				sc.setFinishSuppliNum(new BigDecimal(strArr[9]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "供应商数量实际开发字段非数字");
				continue;
			}

			try {
				if (!testOnly) {
					writeMapper.insertSelective(sc);
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