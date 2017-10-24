package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.InquiryCountMapper;
import com.erui.report.dao.OrderCountMapper;
import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquiryCount;
import com.erui.report.model.InquiryCountExample;
import com.erui.report.model.InquiryCountExample.Criteria;
import com.erui.report.service.InquiryCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.InquiryAreaVO;
import com.erui.report.util.NumSummaryVO;

/*
* 客户中心-询单统计  服务实现类
* */
@Service
public class InquiryCountServiceImpl extends BaseService<InquiryCountMapper> implements InquiryCountService {

	private final static Logger logger = LoggerFactory.getLogger(InquiryCountServiceImpl.class);

	// 根据时间统计询单单数
	@Override
	public int inquiryCountByTime(Date startTime, Date endTime, String quotedStatus, double leastQuoteTime,
			double maxQuoteTime, String org, String area) {
		InquiryCountExample inquiryExample = new InquiryCountExample();
		InquiryCountExample.Criteria criteria = inquiryExample.createCriteria();
		if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
			criteria.andRollinTimeBetween(startTime, endTime);
		}

		if (quotedStatus != null && !"".equals(quotedStatus)) {
			criteria.andQuotedStatusEqualTo(quotedStatus);
		}
		if (leastQuoteTime > 0 && maxQuoteTime > 0) {
			BigDecimal ldecimal = new BigDecimal(leastQuoteTime);
			BigDecimal mdecimal = new BigDecimal(maxQuoteTime);
			criteria.andQuoteNeedTimeBetween(ldecimal, mdecimal);
		}
		if (org != null && !"".equals(org)) {
			criteria.andOrganizationEqualTo(org);
		}
		if (area != null && !"".equals(area)) {
			criteria.andInquiryAreaEqualTo(area);
		}
		int count = readMapper.countByExample(inquiryExample);
		return count;
	}

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		InquiryCount ic = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.INQUIRY_COUNT, response, index + 1)) {
				continue;
			}
			ic = new InquiryCount();
			ic.setInquiryNum(strArr[0]);
			ic.setQuotationNum(strArr[1]);
			ic.setInquiryUnit(strArr[2]);
			ic.setInquiryArea(strArr[3]);
			ic.setOrganization(strArr[4]);
			ic.setCustName(strArr[5]);
			ic.setCustDescription(strArr[6]);
			ic.setProName(strArr[7]);
			ic.setProNameForeign(strArr[8]);
			ic.setSpecification(strArr[9]);
			ic.setFigureNum(strArr[10]);
			try {
				ic.setProCount(new BigDecimal(strArr[11]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "数量字段非数字");
				continue;
			}
			ic.setProUnit(strArr[12]);
			ic.setIsOilGas(strArr[13]);
			ic.setPlatProCategory(strArr[14]);
			ic.setProCategory(strArr[15]);
			ic.setIsKeruiEquipParts(strArr[16]);
			ic.setIsBid(strArr[17]);
			try {
				ic.setRollinTime(DateUtil.parseString2Date(strArr[18], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "转入日期格式错误");
				continue;
			}
			try {
				ic.setNeedTime(DateUtil.parseString2Date(strArr[19], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error("需用日期:" + strArr[19] + ";" + e.getMessage());
			}
			try {
				ic.setClarifyTime(DateUtil.parseString2Date(strArr[20], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "澄清完成日期格式错误");
				continue;
			}
			if (strArr[21] != null) {
				try {
					ic.setSubmitTime(DateUtil.parseString2Date(strArr[21], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "报出日期格式错误");
					continue;
				}
			}
			if (strArr[22] != null) {
				try {
					ic.setQuoteNeedTime(new BigDecimal(strArr[22]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "报价用时字段非数字");
					continue;
				}
			}

			ic.setMarketPrincipal(strArr[23]);
			ic.setBusTechnicalBidder(strArr[24]);
			ic.setBusUnitPrincipal(strArr[25]);
			ic.setProBrand(strArr[26]);
			ic.setQuonationSuppli(strArr[27]);
			ic.setSuppliBidder(strArr[28]);
			ic.setBidderPhone(strArr[29]);
			if (strArr[30] != null) {
				try {
					ic.setSuppliUnitPrice(new BigDecimal(strArr[30]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "厂家单价字段非数字");
					continue;
				}
			}

			if (strArr[31] != null) {
				try {
					ic.setSuppliTotalPrice(new BigDecimal(strArr[31]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "厂家总价字段非数字");
					continue;
				}
			}

			if (strArr[32] != null) {
				try {
					ic.setProfitMargin(new BigDecimal(strArr[32]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "利润率字段非数字");
					continue;
				}
			}

			if (strArr[33] != null) {
				try {
					ic.setQuoteUnitPrice(new BigDecimal(strArr[33]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "报价单价字段非数字");
					continue;
				}
			}

			if (strArr[34] != null) {
				try {
					ic.setQuoteTotalPrice(new BigDecimal(strArr[34]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "报价总价字段非数字");
					continue;
				}
			}
			if (strArr[35] != null) {
				try {
					ic.setQuotationPrice(new BigDecimal(strArr[35]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "报价总金额字段非数字");
					continue;
				}
			}
			if (strArr[36] != null) {
				try {
					ic.setPieceWeight(new BigDecimal(strArr[36]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "单重字段非数字");
					continue;
				}
			}
			if (strArr[37] != null) {
				try {
					ic.setTotalWeight(new BigDecimal(strArr[37]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "总重字段非数字");
					continue;
				}
			}

			ic.setPackagingVolume(strArr[38]);
			ic.setPackagingWay(strArr[39]);

			try {
				ic.setDeliveryDate(new BigDecimal(strArr[40]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "交货期字段非数字");
				continue;
			}
			try {
				ic.setExpiryDate(new BigDecimal(strArr[41]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, "有效期字段非数字");
				continue;
			}

			ic.setStandardTradeItems(strArr[42]);
			ic.setLatestSchedule(strArr[43]);
			ic.setQuotedStatus(strArr[44]);
			ic.setRemark(strArr[45]);
			ic.setQuoteOvertimeCategory(strArr[46]);
			ic.setQuoteOvertimeCause(strArr[47]);
			ic.setIsSuccessOrder(strArr[48]);
			ic.setLoseOrderCategory(strArr[49]);
			ic.setLoseOrderCause(strArr[50]);

			try {
				if (!testOnly) {
					writeMapper.insertSelective(ic);
					response.incrSuccess();
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), index + 1, e.getMessage());
			}
		}
		response.setDone(true);

		return response;
	}

	// 根据时间和产品类别查询产品数量
	@Override
	public int selectProCountByIsOil(Date startTime, Date endTime, String isOil) {
		InquiryCountExample example = new InquiryCountExample();
		InquiryCountExample.Criteria criteria = example.createCriteria();
		criteria.andRollinTimeBetween(startTime, endTime);
		if (isOil != null && !isOil.equals("")) {
			criteria.andIsOilGasEqualTo(isOil);
		}
		int proCount = readMapper.selectProCountByIsOil(example);
		return proCount;
	}

	// 查询产品Top3
	@Override
	public List<Map<String, Object>> selectProTop3(Map<String, Object> params) {
		List<Map<String, Object>> list = readMapper.selectProTop3(params);
		return list;
	}

	// 查询品类明细
	@Override
	public List<CateDetailVo> selectInqDetailByCategory() {
		return readMapper.selectInqDetailByCategory();
	}

	// 查询事业部列表
	@Override
	public List<String> selectOrgList() {
		InquiryCountExample example = new InquiryCountExample();
		return readMapper.selectOrgListByExample(example);
	}

	// 销售大区列表
	@Override
	public List<String> selectAreaList() {
		InquiryCountExample example = new InquiryCountExample();
		return readMapper.selectAreaListByExample(example);
	}

	/**
	 * 查询所有询单中的所有大区和城市列表（大区1 <-> n城市）
	 * 
	 * @return
	 */
	@Override
	public List<InquiryAreaVO> selectAllAreaAndCountryList() {
		List<InquiryAreaVO> result = new ArrayList<>();
		// 查询所有询单的大区和国家信息 {'area':'大区名称','country':'城市名称'}
		List<Map<String, String>> areaAndCountryList = readMapper.selectAllAreaAndCountryList();

		// 查询所有订单的大区和国家信息 {'area':'大区名称','country':'城市名称'}
		OrderCountMapper orderCountMapper = readerSession.getMapper(OrderCountMapper.class);
		List<Map<String, String>> orderAreaAndCountryList = orderCountMapper.selectAllAreaAndCountryList();

		// 数据转换
		coverAreaAndCountryData(result, areaAndCountryList);
		coverAreaAndCountryData(result, orderAreaAndCountryList);

		return result;
	}

	/**
	 * 将数据库数据转换为业务vo对象并添加到list中
	 * 
	 * @param list
	 * @param areaAndCountryList
	 */
	private void coverAreaAndCountryData(List<InquiryAreaVO> list, List<Map<String, String>> areaAndCountryList) {
		if (areaAndCountryList != null && areaAndCountryList.size() > 0) {

			Map<String, InquiryAreaVO> map = list.parallelStream()
					.collect(Collectors.toMap(InquiryAreaVO::getAreaName, vo -> vo));

			areaAndCountryList.stream().forEach(data -> {
				String area = data.get("area");
				InquiryAreaVO vo = null;
				if (map.containsKey(area)) {
					vo = map.get(area);
				} else {
					vo = new InquiryAreaVO();
					vo.setAreaName(area);
					list.add(vo);
					map.put(area, vo);
				}
				vo.pushCountry(data.get("country"));
			});
		}
	}

	// // 根据时间统计询单金额
	public Double inquiryAmountByTime(Date startTime, Date endTime, String area) {
		InquiryCountExample example = new InquiryCountExample();
		InquiryCountExample.Criteria criteria = example.createCriteria();
		if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
			criteria.andRollinTimeBetween(startTime, endTime);
		}
		if (area != null && !"".equals(area)) {
			criteria.andInquiryAreaEqualTo(area);
		}
		Double amount = readMapper.selectTotalAmountByExample(example);
		return amount;
	}

	@Override
	public NumSummaryVO numSummary(String area, String country) {
		InquiryCountExample example = new InquiryCountExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNoneBlank(area)) {
    		criteria = criteria.andInquiryAreaEqualTo(area);
    	}
    	if (StringUtils.isNoneBlank(country)) {
    		criteria.andInquiryUnitEqualTo(country);
    	}
		NumSummaryVO vo = readMapper.selectNumSummaryByExample(example);

		return vo;
	}

}