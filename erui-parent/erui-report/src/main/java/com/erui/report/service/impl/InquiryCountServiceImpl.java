package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.erui.report.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.InquiryCountMapper;
import com.erui.report.dao.OrderCountMapper;
import com.erui.report.model.InquiryCountExample.Criteria;
import com.erui.report.service.InquiryCountService;
import com.erui.report.util.CustomerCategoryNumVO;
import com.erui.report.util.CustomerNumSummaryVO;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.InquiryAreaVO;

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
        Criteria criteria = inquiryExample.createCriteria();

        BigDecimal ldecimal = new BigDecimal(leastQuoteTime);
        BigDecimal mdecimal = new BigDecimal(maxQuoteTime);
        if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
            criteria.andRollinTimeBetween(startTime, endTime);
        }

        if (quotedStatus != null && !"".equals(quotedStatus)) {
            criteria.andQuotedStatusEqualTo(quotedStatus);
        }
        if (maxQuoteTime == 4) {
            criteria.andQuoteNeedTimeLessThan(mdecimal);
        } else if (leastQuoteTime >= 48) {
            criteria.andQuoteNeedTimeGreaterThanOrEqualTo(ldecimal);
        } else if (leastQuoteTime >= 4 && maxQuoteTime <= 48) {
            criteria.andQuoteNeedTimeGreaterThanOrEqualTo(ldecimal);
            criteria.andQuoteNeedTimeLessThan(mdecimal);
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
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.INQUIRY_COUNT, response, cellIndex)) {
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
                response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "数量字段非数字");
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
                response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "转入日期格式错误");
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
                response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "澄清完成日期格式错误");
                continue;
            }
            if (strArr[21] != null) {
                try {
                    ic.setSubmitTime(DateUtil.parseString2Date(strArr[21], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                            DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "报出日期格式错误");
                    continue;
                }
            }
            if (strArr[22] != null) {
                try {
                    ic.setQuoteNeedTime(new BigDecimal(strArr[22]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "报价用时字段非数字");
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
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "厂家单价字段非数字");
                    continue;
                }
            }

            if (strArr[31] != null) {
                try {
                    ic.setSuppliTotalPrice(new BigDecimal(strArr[31]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "厂家总价字段非数字");
                    continue;
                }
            }

            if (strArr[32] != null) {
                try {
                    ic.setProfitMargin(new BigDecimal(strArr[32]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "利润率字段非数字");
                    continue;
                }
            }

            if (strArr[33] != null) {
                try {
                    ic.setQuoteUnitPrice(new BigDecimal(strArr[33]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "报价单价字段非数字");
                    continue;
                }
            }

            if (strArr[34] != null) {
                try {
                    ic.setQuoteTotalPrice(new BigDecimal(strArr[34]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "报价总价字段非数字");
                    continue;
                }
            }
            if (strArr[35] != null) {
                try {
                    ic.setQuotationPrice(new BigDecimal(strArr[35]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "报价总金额字段非数字");
                    continue;
                }
            }
            if (strArr[36] != null) {
                try {
                    ic.setPieceWeight(new BigDecimal(strArr[36]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "单重字段非数字");
                    continue;
                }
            }
            if (strArr[37] != null) {
                try {
                    ic.setTotalWeight(new BigDecimal(strArr[37]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "总重字段非数字");
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
                response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "交货期字段非数字");
                continue;
            }
            try {
                ic.setExpiryDate(new BigDecimal(strArr[41]).intValue());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "有效期字段非数字");
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
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }
            response.incrSuccess();
        }
        response.setDone(true);

        return response;
    }

    // 根据时间和产品类别查询产品数量
    @Override
    public int selectProCountByExample(Date startTime, Date endTime, String isOil, String proCategory) {
        InquiryCountExample example = new InquiryCountExample();
        InquiryCountExample.Criteria criteria = example.createCriteria();
        if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
            criteria.andRollinTimeBetween(startTime, endTime);
        }
        if (isOil != null && !isOil.equals("")) {
            criteria.andIsOilGasEqualTo(isOil);
        }
        if (proCategory != null && !proCategory.equals("")) {
            criteria.andPlatProCategoryEqualTo(proCategory);
        }
        int proCount = readMapper.selectProCountByExample(example);
        return proCount;
    }

    // 查询产品Top3
    @Override
    public List<Map<String, Object>> selectProTop3(Map<String, Object> params) {
        List<Map<String, Object>> list = readMapper.selectProTop3(params);
        return list;
    }

    /**
     * @Author:SHIGS
     * @Description 查询产品Top3
     * @Date:2:03 2017/11/2
     * @modified By
     */
    @Override
    public Map<String, Object> selectProTop3Total(Date startTime, Date endTime) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
            criteria.andRollinTimeBetween(startTime, endTime);
        }
        return this.readMapper.selectProTop3TotalByExample(example);
    }

    // 查询品类明细
    @Override
    public List<CateDetailVo> selectInqDetailByCategory(Date startTime, Date endTime) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
            criteria.andRollinTimeBetween(startTime, endTime);
        }
        return readMapper.selectInqDetailByCategoryByExample(example);
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

    // 根据时间查询询单列表
    @Override
    public List<InquiryCount> selectListByTime(Date startTime, Date endTime) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
            criteria.andRollinTimeBetween(startTime, endTime);
        }
        return readMapper.selectByExample(example);
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
        Criteria criteria = example.createCriteria();
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
    public CustomerNumSummaryVO numSummary(Date startTime, Date endTime, String area, String country) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
            criteria.andRollinTimeBetween(startTime, endTime);
        }
        if (StringUtils.isNoneBlank(area)) {
            criteria = criteria.andInquiryAreaEqualTo(area);
        }
        if (StringUtils.isNoneBlank(country)) {
            criteria.andInquiryUnitEqualTo(country);
        }
        CustomerNumSummaryVO vo = readMapper.selectNumSummaryByExample(example);

        return vo;
    }


    @Override
    public List<CustomerCategoryNumVO> inquiryOrderCategoryTopNum(Integer topN, Date startTime, Date endTime, String... platCategory) {

        // 求出排名本星期
        Map<String, Object> condition01 = new HashMap<>();
        if (topN != null) {
            condition01.put("limit", topN);
        }
        InquiryCountExample example01 = new InquiryCountExample();

        if (startTime == null && endTime == null) {
            return readMapper.selectinquiryOrderCategoryNumByCondition(condition01);
        }
        Criteria criteria01 = example01.createCriteria().andRollinTimeBetween(startTime, endTime);
        OrderCountExample example02 = new OrderCountExample();
        com.erui.report.model.OrderCountExample.Criteria criteria02 = example02.createCriteria().andProjectStartBetween(startTime, endTime);
        if (platCategory != null && platCategory.length > 0) {
            List<String> asList = Arrays.asList(platCategory);
            criteria01.andPlatProCategoryIn(asList);

            criteria02.andPlatProCategoryIn(asList);
        }
        condition01.put("inquiryCountExample", example01);
        condition01.put("orderCountExample", example02);
        List<CustomerCategoryNumVO> result = readMapper.selectinquiryOrderCategoryNumByCondition(condition01);

        // 求出上个时段的值，以获取环比
        if (result != null && result.size() > 0) {
            Map<String, Object> condition02 = new HashMap<>();
            int days = DateUtil.getDayBetween(startTime, endTime);
            //环比开始
            Date chainEnd = DateUtil.sometimeCalendar(startTime, days);
            List<String> categoryList = result.parallelStream().map(CustomerCategoryNumVO::getCategory).collect(Collectors.toList());
            example01 = new InquiryCountExample();
            example01.createCriteria().andRollinTimeBetween(chainEnd, startTime).andPlatProCategoryIn(categoryList);
            example02 = new OrderCountExample();
            example02.createCriteria().andProjectStartBetween(chainEnd, startTime).andPlatProCategoryIn(categoryList);
            condition02.put("inquiryCountExample", example01);
            condition02.put("orderCountExample", example02);
            List<CustomerCategoryNumVO> beforeCategory = readMapper.selectinquiryOrderCategoryNumByCondition(condition02);
            final Map<String, CustomerCategoryNumVO> tmpMap;
            if (beforeCategory != null && beforeCategory.size() > 0) {
                tmpMap = beforeCategory.parallelStream().collect(Collectors.toMap(CustomerCategoryNumVO::getCategory, vo -> vo));
            } else {
                tmpMap = new HashMap<>();
            }

            result.stream().forEach(vo -> {
                CustomerCategoryNumVO ccnv = tmpMap.get(vo.getCategory());
                if (ccnv == null || ccnv.getTotalNum() == 0) {
                    vo.setChainrate(1d);
                } else {
                    vo.setChainrate(((double) vo.getTotalNum()) / ccnv.getTotalNum() - 1);
                }
            });
        }

        return result;
    }

    @Override
    public CustomerNumSummaryVO selectNumSummaryByExample(Date startTime, Date endTime) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
            criteria.andRollinTimeBetween(startTime, endTime);
        }
        return readMapper.selectNumSummaryByExample(example);
    }

}