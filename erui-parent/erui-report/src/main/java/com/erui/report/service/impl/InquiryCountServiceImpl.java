package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.model.*;
import com.erui.report.service.InquiryCountService;
import com.erui.report.util.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.encrypt.MD5;
import com.erui.report.dao.InqRtnReasonMapper;
import com.erui.report.dao.InquirySkuMapper;
import com.erui.report.model.*;
import com.erui.report.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.InquiryCountMapper;
import com.erui.report.dao.OrderCountMapper;
import com.erui.report.model.InquiryCountExample.Criteria;


/*
 * 客户中心-询单统计  服务实现类
 * */
@Service
public class InquiryCountServiceImpl extends BaseService<InquiryCountMapper> implements InquiryCountService {

    private final static Logger logger = LoggerFactory.getLogger(InquiryCountServiceImpl.class);

    public final String inquiryUrl = "http://api.erui.com/v2/report/getTimeIntervalData";//获取询单数据请求路径

    private static final String key = "9b2a37b7b606c14d43db538487a148c7";

    /**
     * @Author:SHIGS
     * @Description
     * @Date:16:58 2017/11/14
     * @modified By
     */
    @Override
    public Date selectStart() {
        return this.readMapper.selectStart();
    }

    /**
     * @Author:SHIGS
     * @Description
     * @Date:16:58 2017/11/14
     * @modified By
     */
    @Override
    public Date selectEnd() {
        return this.readMapper.selectEnd();
    }

    // 根据时间统计询单单数
    @Override
    public int inquiryCountByTime(Date startTime, Date endTime, String[] quotedStatus, double leastQuoteTime,
                                  double maxQuoteTime, String org, String area) {
        InquiryCountExample inquiryExample = new InquiryCountExample();
        Criteria criteria = inquiryExample.createCriteria();

        BigDecimal ldecimal = new BigDecimal(leastQuoteTime);
        BigDecimal mdecimal = new BigDecimal(maxQuoteTime);
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        if (quotedStatus != null && quotedStatus.length > 0) {
            criteria.andQuotedStatusIn(Arrays.asList(quotedStatus));
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
        // 用于计算询单导入时的统计数据
        Map<String, BigDecimal> sumDataMap = new HashMap<>();
        sumDataMap.put("totalNum", BigDecimal.ZERO); // 询单总数量
        sumDataMap.put("totalAmount", BigDecimal.ZERO);// 询单总金额
        sumDataMap.put("oilGasNum", BigDecimal.ZERO);// 油气数量
        sumDataMap.put("nonOilGasNum", BigDecimal.ZERO);// 非油气数量
        sumDataMap.put("nonQuoteNum", BigDecimal.ZERO);// 未报价询单数
        sumDataMap.put("quoteingNum", BigDecimal.ZERO);// 报价中询单数
        sumDataMap.put("quotedNum", BigDecimal.ZERO);// 已报价询单数
        sumDataMap.put("cancelNum", BigDecimal.ZERO);// 取消询单数

        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2; // excel的数据行从第二行开始
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
            if (strArr[20] != null) {
                try {
                    ic.setClarifyTime(DateUtil.parseString2Date(strArr[20], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                            DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "澄清完成日期格式错误");
                    continue;
                }
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

            if (strArr[40] != null) {
                try {
                    ic.setDeliveryDate(new BigDecimal(strArr[40]).intValue());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "交货期字段非数字");
                    continue;
                }
            }
            if (strArr[41] != null) {
                try {
                    ic.setExpiryDate(new BigDecimal(strArr[41]).intValue());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "有效期字段非数字");
                    continue;
                }
            }

            ic.setStandardTradeItems(strArr[42]);
            ic.setLatestSchedule(strArr[43]);
            ic.setQuotedStatus(strArr[44]);
            if (strArr[45] != null) {
                try {
                    ic.setReturnCount(new BigDecimal(strArr[45]).intValue());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, "有效期字段非数字");
                    continue;
                }
            }
            ic.setRemark(strArr[46]);
            ic.setQuoteOvertimeCategory(strArr[47]);
            ic.setQuoteOvertimeCause(strArr[48]);
            ic.setIsSuccessOrder(strArr[49]);
            ic.setLoseOrderCategory(strArr[50]);
            ic.setLoseOrderCause(strArr[51]);

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(ic);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }
            // 根据日期判断是否需要统计
            if (NewDateUtil.inSaturdayWeek(ic.getRollinTime())) {
                sumDataMap.put("totalNum", sumDataMap.get("totalNum").add(BigDecimal.ONE)); // 询单总数量
                BigDecimal quoteTotalPrice = ic.getQuoteTotalPrice();
                if (quoteTotalPrice != null) {
                    sumDataMap.put("totalAmount", sumDataMap.get("totalAmount").add(quoteTotalPrice));// 询单总金额
                }
                switch (ic.getIsOilGas()) {
                    case "油气":
                        sumDataMap.put("oilGasNum", sumDataMap.get("oilGasNum").add(BigDecimal.ONE));// 油气数量
                        break;
                    default:
                        sumDataMap.put("nonOilGasNum", sumDataMap.get("nonOilGasNum").add(BigDecimal.ONE));// 非油气数量
                }

                switch (ic.getQuotedStatus()) {
                    case "已报价":
                        sumDataMap.put("quotedNum", sumDataMap.get("quotedNum").add(BigDecimal.ONE));// 已报价询单数
                        break;
                    case "报价中":
                        sumDataMap.put("quoteingNum", sumDataMap.get("quoteingNum").add(BigDecimal.ONE));// 报价中询单数
                        break;
                    default:
                        sumDataMap.put("nonQuoteNum", sumDataMap.get("nonQuoteNum").add(BigDecimal.ONE));// 未报价询单数
                }

                if (!StringUtils.contains(ic.getIsSuccessOrder(), "成单")) {
                    sumDataMap.put("cancelNum", sumDataMap.get("cancelNum").add(BigDecimal.ONE));// 取消询单数
                }
            }
            response.incrSuccess();
        }
        response.setSumMap(sumDataMap);
        response.setDone(true);

        return response;
    }

    // 根据时间和产品类别查询产品数量
    @Override
    public int selectProCountByExample(Date startTime, Date endTime, String isOil, String proCategory) {
        InquiryCountExample example = new InquiryCountExample();
        InquiryCountExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
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
        if (list == null) {
            list = new ArrayList<>();
        }
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
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        return this.readMapper.selectProTop3TotalByExample(example);
    }

    // 查询品类明细
    @Override
    public List<CateDetailVo> selectInqDetailByCategory(Date startTime, Date endTime) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        criteria.andProCategoryIsNotNull(); // 按照品类统计，品类不能为空D
        return readMapper.selectInqDetailByCategoryByExample(example);
    }

    // 查询事业部列表
    @Override
    public List<String> selectOrgList() {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        criteria.andOrganizationIsNotNull();
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
    public List<InquiryCount> selectListByTime(Date startTime, Date endTime, String[] quotes, String area, String country) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        if (quotes != null && quotes.length > 0) {
            criteria.andQuotedStatusIn(Arrays.asList(quotes));
        }
        if (StringUtil.isNotBlank(area)) {
            criteria.andInquiryAreaEqualTo(area);
        }
        if (StringUtil.isNotBlank(country)) {
            criteria.andInquiryUnitEqualTo(country);
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
        coverAreaAndCountryData(result, areaAndCountryList, "country");
        coverAreaAndCountryData(result, orderAreaAndCountryList, "country");

        return result;
    }

    /**
     * 查询所有询单中的所有大区和事业部列表（大区1 <-> n事业部）
     *
     * @return
     */
    @Override
    public List<InquiryAreaVO> selectAllAreaAndOrgList() {
        List<InquiryAreaVO> result = new ArrayList<>();
        // 查询所有询单的大区和事业部信息 {'area':'大区名称','org':'事业部名称'}
        List<Map<String, String>> areaAndOrgList = readMapper.selectAllAreaAndOrgList();

        // 查询所有订单的大区和国家信息 {'area':'大区名称','country':'城市名称'}
        OrderCountMapper orderCountMapper = readerSession.getMapper(OrderCountMapper.class);
        List<Map<String, String>> orderAreaAndOrgList = orderCountMapper.selectAllAreaAndOrgList();

        // 数据转换
        coverAreaAndCountryData(result, areaAndOrgList, "org");
        coverAreaAndCountryData(result, orderAreaAndOrgList, "org");

        return result;
    }

    /**
     * 将数据库数据转换为业务vo对象并添加到list中
     *
     * @param list
     * @param areaAndCountryList
     */
    private void coverAreaAndCountryData(List<InquiryAreaVO> list, List<Map<String, String>> areaAndCountryList, String typeName) {
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
                if (typeName.equals("country")) {
                    vo.pushCountry(data.get("country"));
                }
                if (typeName.equals("org")) {
                    vo.pushOrg(data.get("org"));
                }
            });
        }
    }

    // // 根据时间统计询单金额
    public Double inquiryAmountByTime(Date startTime, Date endTime, String area, String country, String[] quotedStatus) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        if (area != null && !"".equals(area)) {
            criteria.andInquiryAreaEqualTo(area);
        }
        if (country != null && !"".equals(country)) {
            criteria.andInquiryUnitEqualTo(country);
        }
        if (quotedStatus != null && quotedStatus.length > 0) {
            criteria.andQuotedStatusIn(Arrays.asList(quotedStatus));
        }
        Double amount = readMapper.selectTotalAmountByExample(example);
        return amount;
    }

    @Override
    public CustomerNumSummaryVO numSummary(Date startTime, Date endTime, String area, String country) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
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
    public Map<String, Object> selectQuoteTimeSummaryData(Map<String, String> params) {
        //查询各个时间段报价询单个数
        params.put("finished","已完成");
        params.put("quoted","已报价");
        Map<String,Object> data=readMapper.selectInqCountGroupByQuoteTime(params);
        Integer totalCount = Integer.parseInt(data.get("totalCount").toString());
        double oneCountRate=0.00,fourCountRate=0.00,eightCountRate=0.00,
                twentyFourCountRate=0.00,otherCountRate=0.00;
        if(totalCount>0){
            oneCountRate=RateUtil.intChainRate(Integer.parseInt(data.get("oneCount").toString()),
                    totalCount);
            fourCountRate=RateUtil.intChainRate(Integer.parseInt(data.get("fourCount").toString()),
                    totalCount);
            eightCountRate=RateUtil.intChainRate(Integer.parseInt(data.get("eightCount").toString()),
                    totalCount);
            twentyFourCountRate=RateUtil.intChainRate(Integer.parseInt(data.get("twentyFourCount").
                    toString()),totalCount);
            otherCountRate=RateUtil.intChainRate(Integer.parseInt(data.get("otherCount").toString())
                    ,totalCount);
        }
        data.put("oneCountRate",oneCountRate);
        data.put("fourCountRate",fourCountRate);
        data.put("eightCountRate",eightCountRate);
        data.put("twentyFourCountRate",twentyFourCountRate);
        data.put("otherCountRate",otherCountRate);
        //查询各事业部的 平均报价用时
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.FULL_FORMAT_STR2);
        Date endTime = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.FULL_FORMAT_STR2);
        InquiryCountExample inqExample = new InquiryCountExample();
        Criteria criteria = inqExample.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        List<String> statusList=new ArrayList<>();
        statusList.add(QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus());
        statusList.add(QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus());
        criteria.andQuotedStatusIn(statusList);
        List<Map<String, Object>> orgData = readMapper.findAvgNeedTimeByRollinTimeGroupOrigation(inqExample);
        orgData.stream().forEach(m->{
            m.put("avgNeedTime",RateUtil.doubleChainRateTwo(Double.
                    parseDouble(m.get("avgNeedTime").toString()),1));
        });

        Map<String,Object> datas=new HashMap<>();
        datas.put("quoteTimeTable",data);
        datas.put("orgTable",orgData);
        return datas;
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

        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        return readMapper.selectNumSummaryByExample(example);
    }


    /**
     * 按照转入日期区间统计事业部的询单数量
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<Map<String, Object>> findCountByRangRollinTimeGroupOrigation(Date startDate, Date endDate,int rtnCount, String[] quotes) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startDate != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startDate);
        }
        if (endDate != null) {
            criteria.andRollinTimeLessThan(endDate);
        }
        if(rtnCount>0){
            criteria.andReturnCountGreaterThanOrEqualTo(rtnCount);
        }
        if (quotes != null && quotes.length > 0) {
            criteria.andQuotedStatusIn(Arrays.asList(quotes));
        }
        List<Map<String, Object>> result = readMapper.findCountByExampleGroupOrigation(example);
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    /**
     * 询订单趋势图
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public InqOrdTrendVo inqOrdTrend(Date startTime, Date endTime) {
        InquiryCountExample example = new InquiryCountExample();
        InquiryCountExample example2 = new InquiryCountExample();
        OrderCountExample ordExample = new OrderCountExample();
        Criteria example2Criteria = example2.createCriteria();
        ArrayList<String> quoteList = new ArrayList<>();
        quoteList.add("已报价");
        quoteList.add("已完成");
        example2Criteria.andQuotedStatusIn(quoteList);
        if (startTime != null && endTime != null) {
            example.createCriteria().andRollinTimeBetween(startTime, endTime);
            example2Criteria.andRollinTimeBetween(startTime, endTime);
            ordExample.createCriteria().andProjectStartBetween(startTime, endTime);
        }

        List<Map<String, Object>> inqTrendList = readMapper.inqTrendByTime(example);
        List<Map<String, Object>> quoteTrendList = readMapper.inqTrendByTime(example2);
        OrderCountMapper ordReadMapper = readerSession.getMapper(OrderCountMapper.class);
        List<Map<String, Object>> ordTrendList = ordReadMapper.ordTrendByTime(ordExample);
        //虚拟一个标准的时间集合
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }
        //封装询订单数据
        Map<String, Map<String, Object>> inqTrend = inqTrendList.parallelStream().collect(Collectors.toMap(vo -> vo.get("datetime").toString(), vo -> vo));
        Map<String, Map<String, Object>> ordTrend = ordTrendList.parallelStream().collect(Collectors.toMap(vo -> vo.get("datetime").toString(), vo -> vo));
        Map<String, Map<String, Object>> quoteTrend = quoteTrendList.parallelStream().collect(Collectors.toMap(vo -> vo.get("datetime").toString(), vo -> vo));

        List<Integer> inqCounts = new ArrayList<>();
        List<Integer> ordCounts = new ArrayList<>();
        List<Integer> quoteCounts = new ArrayList<>();

        for (String date : dates) {
            if (inqTrend.containsKey(date)) {
                inqCounts.add(Integer.parseInt(inqTrend.get(date).get("count").toString()));
            } else {
                inqCounts.add(0);
            }
            if (ordTrend.containsKey(date)) {
                ordCounts.add(Integer.parseInt(ordTrend.get(date).get("count").toString()));
            } else {
                ordCounts.add(0);
            }
            if (quoteTrend.containsKey(date)) {
                quoteCounts.add(Integer.parseInt(quoteTrend.get(date).get("count").toString()));
            } else {
                quoteCounts.add(0);
            }
        }
        InqOrdTrendVo trendVo = new InqOrdTrendVo();
        trendVo.setDate(dates);
        trendVo.setInqCounts(inqCounts);
        trendVo.setOrdCounts(ordCounts);
        trendVo.setQuoteCounts(quoteCounts);
        return trendVo;
    }


    /**
     * 按照转入日期区间统计区域的询单数量和金额
     *
     * @param startTime
     * @param endTime
     * @return {"totalAmount":'金额--BigDecimal',"total":'总询单数量--Long',"area":'区域--String'}
     */
    @Override
    public List<Map<String, Object>> findCountAndPriceByRangRollinTimeGroupArea(Date startTime, Date endTime,int rtnCount, String[] quotes) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        if(rtnCount>0){
            criteria.andReturnCountGreaterThanOrEqualTo(rtnCount);
        }
        if (quotes != null && quotes.length > 0) {
            criteria.andQuotedStatusIn(Arrays.asList(quotes));
        }
        criteria.andInquiryAreaIsNotNull();
        List<Map<String, Object>> result = readMapper.findCountAndPriceByRangRollinTimeGroupArea(example);
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> findAvgNeedTimeByRollinTimeGroupOrigation(Date startDate, Date endDate) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startDate != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startDate);
        }
        if (endDate != null) {
            criteria.andRollinTimeLessThan(endDate);
        }
        criteria.andQuotedStatusBetween(QuotedStatusEnum.STATUS_QUOTED_FINISHED.getQuotedStatus(),
                QuotedStatusEnum.STATUS_QUOTED_ED.getQuotedStatus());

        List<Map<String, Object>> result = readMapper.findAvgNeedTimeByRollinTimeGroupOrigation(example);
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    public void inquiryData(List<HashMap> list) throws Exception {

        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String,Object> map:list) {
                Object serial_no = map.get("serial_no");
                if(serial_no!=null) {
                    clearInquiry(serial_no.toString());
                    addInqData(map);
                }
            }
        }
    }
    //存入询单数据
    private void addInqData(Map<String,Object> map)throws Exception{
        if(MapUtils.isNotEmpty(map)){
            //存入询单
            InquiryCount inquiryCount = new InquiryCount();
            if(map.get("serial_no")!=null){//询单编号
                inquiryCount.setQuotationNum(map.get("serial_no").toString());
            }
            if (map.get("created_at") != null) {//转入日期
                inquiryCount.setRollinTime(DateUtil.parseStringToDate(map.get("created_at").toString(), DateUtil.FULL_FORMAT_STR));
            }
            if (map.get("country_name") != null) {//国家
                inquiryCount.setInquiryUnit(map.get("country_name").toString());
            }
            if (map.get("area_name") != null) {//区域
                inquiryCount.setInquiryArea(map.get("area_name").toString());
            }
            if (map.get("org_name") != null) {//事业部
                inquiryCount.setOrganization(map.get("org_name").toString());
            }
            if (map.get("buyer_code") != null) {//客户编码
                inquiryCount.setCustDescription(map.get("buyer_code").toString());
            }
            if (map.get("gross_profit_rate") != null) {//利润率
                inquiryCount.setProfitMargin(new BigDecimal(map.get("gross_profit_rate").toString()));
            }
            if (map.get("total_quote_price") != null) {//报价总金额
                inquiryCount.setQuotationPrice(new BigDecimal(map.get("total_quote_price").toString()));
            }
            if (map.get("quote_time") != null) {//报价用时
                double quote = Double.parseDouble(map.get("quote_time").toString());//秒
                double hour = quote / 3600;
                inquiryCount.setQuoteNeedTime(new BigDecimal(hour));
            }
            if ( map.get("quote_status") != null) {//报价状态
                inquiryCount.setQuotedStatus( map.get("quote_status").toString());
            }
            if (map.get("reject_count") != null) {//退回次数
                inquiryCount.setReturnCount(Integer.parseInt(map.get("reject_count").toString()));
            }
           writeMapper.insertSelective(inquiryCount);
            //存入退回原因数据
            InqRtnReasonMapper rtnMapper = writerSession.getMapper(InqRtnReasonMapper.class);
            Object other = map.get("other");//询单商品数据
            if (map.get("reject_reason") != null) {//退回原因
                List<String> reasons = JSON.parseArray(map.get("reject_reason").toString(), String.class);
                if (reasons != null && reasons.size() > 0) {
                    List<InqRtnReason> rtnList=new ArrayList<>();
                    for (String rtn : reasons) {
                        InqRtnReason rtnReason = new InqRtnReason();
                        if (map.get("serial_no") != null) {
                            rtnReason.setQuotationNum(map.get("serial_no").toString());
                        }
                        if (map.get("created_at") != null) {
                            rtnReason.setRollinTime(DateUtil.parseStringToDate(map.get("created_at").toString(), DateUtil.FULL_FORMAT_STR));
                        }
                        if (map.get("country_name") != null) {
                            rtnReason.setInquiryUnit(map.get("country_name").toString());
                        }
                        if (map.get("area_name") != null) {
                            rtnReason.setInquiryArea(map.get("area_name").toString());
                        }
                        if (map.get("org_name") != null) {
                            rtnReason.setOrganization(map.get("org_name").toString());
                        }
                        if (StringUtil.isNotBlank(rtn)) {
                            rtnReason.setReturnSeason(rtn);
                        } else {
                            rtnReason.setReturnSeason("其他");
                        }
                        rtnList.add(rtnReason);
                    }
                    if(CollectionUtils.isNotEmpty(rtnList)){
                        rtnMapper.insertRtnReasons(rtnList);
                    }
                }
            }
            //存入询单sku数据
            if (other != null) {
                List<HashMap> cateList = JSON.parseArray(other.toString(), HashMap.class);
                if (cateList != null && cateList.size() > 0) {
                    InquirySkuMapper skuMapper = writerSession.getMapper(InquirySkuMapper.class);
                    List<InquirySku> skuList=new ArrayList<>();
                    for (Map<String, Object> goodsList : cateList) { //询单分类商品
                        InquirySku inquirySku = new InquirySku();
                        if (goodsList.get("category") != null) {
                            inquirySku.setProCategory(goodsList.get("category").toString());
                        }
                        if (goodsList.get("qty") != null) {
                            inquirySku.setCateCount(Integer.parseInt(goodsList.get("qty").toString()));
                        }
                        if (goodsList.get("oil_type") != null && !goodsList.get("oil_type").equals("")) {
                            inquirySku.setIsOilGas(goodsList.get("oil_type").toString());
                        } else {
                            inquirySku.setIsOilGas("油气");
                        }
                        if (goodsList.get("sku_type") != null && !goodsList.get("sku_type").equals("")) {
                            inquirySku.setPlatProCategory(goodsList.get("sku_type").toString());
                        } else {
                            inquirySku.setPlatProCategory("平台");
                        }
                        if (goodsList.get("quote_unit_price") != null) {
                            inquirySku.setQuoteUnitPrice(new BigDecimal(goodsList.get("quote_unit_price").toString()));
                        }
                        if (goodsList.get("total_quote_price") != null) {
                            inquirySku.setQuoteUnitPrice(new BigDecimal(goodsList.get("total_quote_price").toString()));
                        }
                        if (map.get("created_at") != null) {
                            inquirySku.setRollinTime(DateUtil.parseStringToDate(map.get("created_at").toString(), DateUtil.FULL_FORMAT_STR));
                        }
                        if (map.get("serial_no") != null) {
                            inquirySku.setQuotationNum(map.get("serial_no").toString());
                        }
                        skuList.add(inquirySku);
                    }
                    if(CollectionUtils.isNotEmpty(skuList)){
                        skuMapper.insertSKUList(skuList);
                    }
                }


            }
        }
    }

    //保证数据库没有此询单
    private void clearInquiry(String quotationNum) {
        InquiryCountMapper inqMapper = writerSession.getMapper(InquiryCountMapper.class);
        InquirySkuMapper skuMapper = writerSession.getMapper(InquirySkuMapper.class);
        InqRtnReasonMapper rtnMapper = writerSession.getMapper(InqRtnReasonMapper.class);
        if (StringUtils.isNotBlank(quotationNum)) {
            InquiryCountExample example = new InquiryCountExample();
            Criteria criteria = example.createCriteria();
            criteria.andQuotationNumEqualTo(quotationNum);
            List<InquiryCount> list = inqMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(list)) {//清空此数据 inquiry 、sku、 rtnSeason
                inqMapper.deleteByExample(example);
                InquirySkuExample skuExample = new InquirySkuExample();
                InquirySkuExample.Criteria skuCriteria = skuExample.createCriteria();
                skuCriteria.andQuotationNumEqualTo(quotationNum);
                skuMapper.deleteByExample(skuExample);
                InqRtnReasonExample rtnExample = new InqRtnReasonExample();
                InqRtnReasonExample.Criteria rtnCriteria = rtnExample.createCriteria();
                rtnCriteria.andQuotationNumEqualTo(quotationNum);
                rtnMapper.deleteByExample(rtnExample);
            }
        }

    }

    @Override
    public List<Map<String, Object>> selectRejectCount(Date startTime, Date endTime) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        criteria.andReturnCountGreaterThanOrEqualTo(1);
        return readMapper.selectRejectCount(example);
    }

    @Override
    public int selectInqRtnCountByTime(Date startTime, Date endTime) {
        InquiryCountExample example = new InquiryCountExample();
        Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        criteria.andReturnCountGreaterThanOrEqualTo(1);
        return readMapper.selectInqRtnCountByTime(example);
    }

    @Override
    public Map<String, Object> selectInqAndOrdCountAndPassengers(Map<String, String> params) {

        return readMapper.selectInqAndOrdCountAndPassengers(params);
    }

}