package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.report.dao.SupplyChainMapper;
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
	public Map<String, Object> selectFinishByDate(int days, String type) {
		Date startDate = com.erui.comm.DateUtil.recedeTime(days);
		SupplyChainExample supplyChainExample = new SupplyChainExample();
		supplyChainExample.createCriteria().andCreateAtBetween(startDate, new Date());
		List<Map> supplyMap = this.readMapper.selectFinishByDate(supplyChainExample);

		List<Integer> spuList = new ArrayList<>();
		List<Integer> skuList = new ArrayList<>();
		List<Integer> supplierList = new ArrayList<>();
		List<String> dateList = new ArrayList<>();
		Map<String, Map<String, Integer>> sqlDate = new HashMap<>();
		Map<String, Integer> lintData = null;
		for (Map map2 : supplyMap) {
			lintData = new HashMap<>();
			BigDecimal spu = new BigDecimal(map2.get("finish_spu_num").toString());
			BigDecimal sku = new BigDecimal(map2.get("finish_sku_num").toString());
			BigDecimal supplier = new BigDecimal(map2.get("finish_suppli_num").toString());
			Date date2 = (Date) map2.get("create_at");
			String dateString = com.erui.comm.DateUtil.format("MM月dd日", date2);
			lintData.put("spu", spu.intValue());
			lintData.put("sku", sku.intValue());
			lintData.put("supplier", supplier.intValue());
			sqlDate.put(dateString, lintData);
		}
		for (int i = 0; i < days; i++) {
			Date datetime = DateUtil.recedeTime(days - (i + 1));
			String date = com.erui.comm.DateUtil.format("MM月dd日", datetime);
			if (sqlDate.containsKey(date)) {
				dateList.add(date);
				spuList.add(sqlDate.get(date).get("spu"));
				skuList.add(sqlDate.get(date).get("sku"));
				supplierList.add(sqlDate.get(date).get("supplier"));
			} else {
				dateList.add(date);
				spuList.add(0);
				skuList.add(0);
				supplierList.add(0);
			}
		}
		String[] s = { "SPU完成量", "SKU完成量", "供应商完成量" };
		Map<String, Object> data = new HashMap<>();
		if (type.equals("spu")) {
			data.put("legend", s[0]);
			data.put("xAxis", dateList);
			data.put("yAxis", spuList);

		} else if (type.equals("sku")) {
			data.put("legend", s[1]);
			data.put("xAxis", dateList);
			data.put("yAxis", skuList);
		} else {
			data.put("legend", s[2]);
			data.put("xAxis", dateList);
			data.put("yAxis", supplierList);
		}
		return data;
	}

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		SupplyChain sc = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.SUPPLY_CHAIN, response, index + 1)) {
				continue;
			}
			sc = new SupplyChain();

			try {
				sc.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d hh:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), index + 1, "日期字段格式错误");
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
				response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), index + 1, "计划SKU字段非数字");
				continue;
			}
			try {
				sc.setFinishSkuNum(new BigDecimal(strArr[5]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), index + 1, "SKU实际完成字段非数字");
				continue;
			}
			try {
				sc.setPlanSpuNum(new BigDecimal(strArr[6]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), index + 1, "计划SPU字段非数字");
				continue;
			}
			try {
				sc.setFinishSpuNum(new BigDecimal(strArr[7]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), index + 1, "SPU实际完成字段非数字");
				continue;
			}
			try {
				sc.setPlanSuppliNum(new BigDecimal(strArr[8]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), index + 1, "计划供应商数量非数字");
				continue;
			}
			try {
				sc.setFinishSuppliNum(new BigDecimal(strArr[9]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), index + 1, "供应商数量实际开发字段非数字");
				continue;
			}

			try {
				if (!testOnly) {
					writeMapper.deleteByExample(null);
					writeMapper.insertSelective(sc);
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), index + 1, e.getMessage());
				continue;
			}
			response.incrSuccess();

		}
		response.setDone(true);

		return response;
	}

	@Override
	public List<SupplyChain> queryListByDate(Date startTime, Date endTime) {
		SupplyChainExample example = new SupplyChainExample();
		SupplyChainExample.Criteria criteria = example.createCriteria();
		if (startTime != null && !startTime.equals("") && startTime != null && !startTime.equals("")) {
			criteria.andCreateAtBetween(startTime, endTime);
		}
		return readMapper.selectByExample(example);
	}

	// 事业部-供应链明细列表
	@Override
	public List<SuppliyChainOrgVo> selectOrgSuppliyChain() {
		List<SuppliyChainOrgVo> list = readMapper.selectOrgSuppliyChain();
		return list;
	}

	@Override
	public List<SuppliyChainItemClassVo> selectItemCalssSuppliyChain(Date startTime, Date endTime) {
		SupplyChainExample supplyChainExample = new SupplyChainExample();
		SupplyChainExample.Criteria criteria = supplyChainExample.createCriteria();
		criteria.andCreateAtBetween(startTime, endTime);
		return readMapper.selectItemCalssSuppliyChainByExample(supplyChainExample);
	}

	@Override
	public SuppliyChainItemClassVo selectSuppliyChainByItemClass(Date startTime, Date endTime, String itemClass) {
		SupplyChainExample supplyChainExample = new SupplyChainExample();
		SupplyChainExample.Criteria criteria = supplyChainExample.createCriteria();
		criteria.andCreateAtBetween(startTime, endTime);
		criteria.andItemClassEqualTo(itemClass);
		return readMapper.selectSuppliyChainByItemClassByExample(supplyChainExample);
	}

	@Override
	public List<SuppliyChainCateVo> selectCateSuppliyChain() {
		return readMapper.selectCateSuppliyChain();
	}

	// 供应链趋势图
	@Override
	public SupplyTrendVo supplyTrend(int days, int type) {
		SupplyChainExample example = new SupplyChainExample();
		SupplyChainExample.Criteria criteria = example.createCriteria();
		if (days > 0) {
			criteria.andCreateAtBetween(DateUtil.recedeTime(days), new Date());
		}
		List<SupplyChain> list = readMapper.selectByExample(example);

		String[] DateTime = new String[days];
		Integer[] suppliyFinishCount = new Integer[days];
		Integer[] SPUFinishCount = new Integer[days];
		Integer[] SKUFinishCount = new Integer[days];

		if (list != null && list.size() > 0) {
			Map<String, Map<String, Integer>> dateMap = new HashMap<>();
			Map<String, Integer> datamap;
			for (int i = 0; i < list.size(); i++) {
				String date2 = DateUtil.formatDate2String(list.get(i).getCreateAt(), "M月d日");
				datamap = new HashMap<>();
				datamap.put("sku", list.get(i).getFinishSkuNum());
				datamap.put("spu", list.get(i).getFinishSpuNum());
				datamap.put("suppliy", list.get(i).getFinishSuppliNum());
				dateMap.put(date2, datamap);

			}
			for (int i = 0; i < days; i++) {
				Date date = com.erui.comm.DateUtil.recedeTime(days - (i + 1));
				String datet2 = com.erui.comm.DateUtil.format("MM月dd日", date);
				if (dateMap.containsKey(datet2)) {
					DateTime[i] = (datet2);
					SPUFinishCount[i] = (dateMap.get(datet2).get("spu"));
					SKUFinishCount[i] = (dateMap.get(datet2).get("sku"));
					suppliyFinishCount[i] = (dateMap.get(datet2).get("suppliy"));
				} else {
					DateTime[i] = (datet2);
					SPUFinishCount[i] = (0);
					SKUFinishCount[i] = (0);
					suppliyFinishCount[i] = (0);
				}
			}

        }else {
            for (int i = 0; i <days ; i++) {
                Date date = DateUtil.recedeTime(days - (i + 1));
                String datet2 = com.erui.comm.DateUtil.format("MM月dd日", date);
                DateTime[i]=datet2;
                suppliyFinishCount[i]=0;
                SPUFinishCount[i]=0;
                SKUFinishCount[i]=0;
            }
        }
        SupplyTrendVo trend = new SupplyTrendVo(DateTime,suppliyFinishCount,SPUFinishCount,SKUFinishCount);

		return trend;
	}

	@Override
	public List<String> selectCategoryList() {
		return readMapper.selectCategoryList();
	}

	@Override
	public List<String> selectOrgList() {
		return readMapper.selectOrgList();
	}
}