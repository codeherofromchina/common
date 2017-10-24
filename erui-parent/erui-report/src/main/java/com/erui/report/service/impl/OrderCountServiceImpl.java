package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;



import com.erui.report.model.OrderCountExample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.OrderCountMapper;
import com.erui.report.model.OrderCount;

import com.erui.report.service.OrderCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class OrderCountServiceImpl extends BaseService<OrderCountMapper> implements OrderCountService {
	private final static Logger logger = LoggerFactory.getLogger(OrderCountServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		OrderCount oc = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.ORDER_COUNT, response, index + 1)) {
				continue;
			}
			oc = new OrderCount();

			oc.setOrderSerialNum(strArr[0]);
			oc.setProNoticeNum(strArr[1]);
			oc.setExecuteNum(strArr[2]);
			oc.setPoNum(strArr[3]);
			oc.setOrganization(strArr[4]);
			oc.setExeCompany(strArr[5]);

			oc.setOrderArea(strArr[6]);
			oc.setCustDescription(strArr[7]);

			oc.setProName(strArr[8]);
			// TODO 缺少品名外文strArr[9]
			oc.setSpecification(strArr[10]);
			oc.setIsOilGas(strArr[11]);
			oc.setPlatProCategory(strArr[12]);
			oc.setProCategory(strArr[13]);
			if (strArr[14] != null) {
				try {
					oc.setOrderCount(new BigDecimal(strArr[14]).intValue());
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "数量字段非数字");
					continue;
				}
			}
			oc.setOrderUnit(strArr[15]);

			// TODO 应该string 类型
			// oc.setSaleNum(Integer.parseInt(strArr[16]));
			// TODO 应该string 类型
			// oc.setOrderNum(Integer.parseInt(strArr[17]));

			if (strArr[18] != null) {
				try {
					oc.setProjectAccount(new BigDecimal(strArr[18]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "项目金额（美元）非数字");
					continue;
				}
			}

			oc.setPreProfit(strArr[19]);
			oc.setBackForm(strArr[20]);
			if (strArr[21] != null) {
				try {
					oc.setBackDate(DateUtil.parseString2Date(strArr[21], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "回款时间格式错误");
					continue;
				}
			}
			if (strArr[22] != null) {
				try {
					oc.setBackAmount(new BigDecimal(strArr[22]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "回款金额非数字");
					continue;
				}
			}
			oc.setCreditExtension(strArr[23]);
			if (strArr[24] != null) {
				try {
					oc.setProjectStart(DateUtil.parseString2Date(strArr[24], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "项目开始时间格式错误");
					continue;
				}
			}
			if (strArr[25] != null) {
				try {
					oc.setExePromiseDate(DateUtil.parseString2Date(strArr[25], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "执行单约定交付时间格式错误");
					continue;
				}
			}
			if (strArr[26] != null) {
				try {
					oc.setExePromiseUpdateDate(DateUtil.parseString2Date(strArr[26], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "变更后日期时间格式错误");
					continue;
				}
			}
			if (strArr[27] != null) {
				try {
					oc.setRequirePurchaseGetDate(DateUtil.parseString2Date(strArr[27], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "要求采购到货时间格式错误");
					continue;
				}
			}

			oc.setMarketManager(strArr[28]);
			oc.setBusinessManager(strArr[29]);
			oc.setPurchaseManager(strArr[30]);
			oc.setSupplier(strArr[31]);
			oc.setPurchaseContractNu(strArr[32]);
			oc.setPreQuotesAccount(strArr[33]);
			oc.setPurchaseAccountCny(strArr[34]);
			oc.setPurchaseAccountUsd(strArr[35]);
			if (strArr[36] != null) {
				try {
					oc.setPurchaseContractDate(DateUtil.parseString2Date(strArr[36], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "采购签合同日期格式错误");
					continue;
				}
			}
			if (strArr[37] != null) {
				try {
					oc.setPurchaseContractDeliveryDate(DateUtil.parseString2Date(strArr[37], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "采购合同规定交货期格式错误");
					continue;
				}
			}

			oc.setPurchasePaymentMode(strArr[38]);
			if (strArr[39] != null) {
				try {
					oc.setToFactoryPaymentDate(DateUtil.parseString2Date(strArr[39], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "给工厂付款时间格式错误");
					continue;
				}
			}

			if (strArr[40] != null) {
				try {
					oc.setPurchaseGetDate(DateUtil.parseString2Date(strArr[40], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "采购到货日期格式错误");
					continue;
				}
			}
			if (strArr[41] != null) {
				try {
					oc.setInspectionDate(DateUtil.parseString2Date(strArr[41], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "报检时间格式错误");
					continue;
				}
			}
			if (strArr[42] != null) {
				try {
					oc.setInspectionFinashDate(DateUtil.parseString2Date(strArr[42], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "检验完成时间格式错误");
					continue;
				}
			}

			if (strArr[43] != null) {
				try {
					oc.setToStorageDate(DateUtil.parseString2Date(strArr[43], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "入库时间格式错误");
					continue;
				}
			}

			if (strArr[44] != null) {
				try {
					oc.setBookingDate(DateUtil.parseString2Date(strArr[44], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "下发订舱时间格式错误");
					continue;
				}
			}
			if (strArr[45] != null) {
				try {
					oc.setMarketRequestDate(DateUtil.parseString2Date(strArr[45], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "市场要求时间格式错误");
					continue;
				}
			}

			oc.setForwardManager(strArr[46]);
			if (strArr[47] != null) {
				try {
					oc.setPackageDate(DateUtil.parseString2Date(strArr[47], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "包装完成时间格式错误");
					continue;
				}
			}

			if (strArr[48] != null) {
				try {
					oc.setStorageOutDate(DateUtil.parseString2Date(strArr[48], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "货物离开仓库日期格式错误");
					continue;
				}
			}
			if (strArr[49] != null) {
				try {
					oc.setAckageNoticeDate(DateUtil.parseString2Date(strArr[49], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "箱单通知时间格式错误");
					continue;
				}
			}

			if (strArr[50] != null) {
				try {
					oc.setShipAirDate(DateUtil.parseString2Date(strArr[50], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "船期或航班时间格式错误");
					continue;
				}
			}

			if (strArr[51] != null) {
				try {
					oc.setArriveDate(DateUtil.parseString2Date(strArr[51], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "到达日时间格式错误");
					continue;
				}
			}

			oc.setLogisticsNum(strArr[52]);

			if (strArr[53] != null) {
				try {
					oc.setPreLogisticsAmount(new BigDecimal(strArr[53]));
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "前期物流报价非数字");
					continue;
				}
			}
			if (strArr[54] != null) {
				try {
					oc.setLogisticsForwardAmount(new BigDecimal(strArr[54]).longValue());
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "物流发运金额非数字");
					continue;
				}

			}
			if (strArr[55] != null) {
				try {
					oc.setFinishDate(DateUtil.parseString2Date(strArr[55], "yyyy/M/d", "yyyy/M/d",
							DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
				} catch (Exception e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "实际完成时间格式错误");
					continue;
				}
			}

			oc.setTradeTerms(strArr[56]);
			if (strArr[57] != null) {
				try {
					oc.setPurchaseDelayDate(new BigDecimal(strArr[57]).intValue());
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "采购延期时间非数字");
					continue;
				}
			}
			if (strArr[58] != null) {
				try {
					oc.setLogisticsDelayDate(new BigDecimal(strArr[58]).intValue());
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, "物流延期时间非数字");
					continue;
				}
			}
			oc.setProjectStatus(strArr[59]);
			oc.setReasonType(strArr[60]);
			oc.setReasonCategory(strArr[61]);
			oc.setRealityReason(strArr[62]);
			oc.setProjectProgress(strArr[63]);
			oc.setRemark(strArr[64]);

			try {
				if (!testOnly) {
					writeMapper.insertSelective(oc);
					response.incrSuccess();
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), index + 1, e.getMessage());
			}
		}
		response.setDone(true);

		return response;
	}

	@Override
	public int orderCountByTime(Date startTime, Date endTime,String proStatus,String org,String area) {
        OrderCountExample example= new OrderCountExample();
        OrderCountExample.Criteria criteria = example.createCriteria();
        if(startTime!=null&&!"".equals(startTime)&&endTime!=null&&!"".equals(endTime)){
            criteria.andProjectStartBetween(startTime,endTime);
        }
        if(proStatus!=null&&!proStatus.equals("")){
            criteria.andProjectStatusEqualTo(proStatus);
        }
        if(org!=null&&!"".equals(org)){
            criteria.andProjectStatusEqualTo(org);
        }
        if(area!=null&&!"".equals(area)){
            criteria.andProjectStatusEqualTo(area);
        }

		int count = readMapper.countByExample(example);
		return count;

	}

	@Override
	public Double orderAmountByTime(Date startTime, Date endTime,String area) {
        OrderCountExample example= new OrderCountExample();
        OrderCountExample.Criteria criteria = example.createCriteria();
        if(startTime!=null&&!"".equals(startTime)&&endTime!=null&&!"".equals(endTime)){
            criteria.andProjectStartBetween(startTime,endTime);
        }
        if(area!=null&&!"".equals(area)){
            criteria.andProjectStatusEqualTo(area);
        }
        Double amount = readMapper.selectTotalAmountByExample(example);
        return amount;

	}

	@Override
	public List<Map<String, Object>> selectOrderProTop3(Map<String, Object> params) {
		List<Map<String, Object>> list = readMapper.selectOrderProTop3(params);
		return list;
	}


	//订单利润率
    @Override
    public Double selectProfitRate(Date startTime, Date endTime) {
        OrderCountExample example= new OrderCountExample();
        example.createCriteria().andProjectStartBetween(startTime,  endTime);
        Double profitRate = readMapper.selectProfitRateByExample(example);
        return profitRate;
    }


}