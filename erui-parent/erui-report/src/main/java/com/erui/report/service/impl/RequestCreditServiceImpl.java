package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.dao.RequestReceiveMapper;
import com.erui.report.model.RequestCreditExample;
import com.erui.report.model.RequestReceiveExample;
import com.erui.report.util.AreaEnum;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.report.dao.RequestCreditMapper;
import com.erui.report.model.RequestCredit;
import com.erui.report.service.RequestCreditService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class RequestCreditServiceImpl extends BaseService<RequestCreditMapper> implements RequestCreditService {
    private final static Logger logger = LoggerFactory.getLogger(RequestCreditServiceImpl.class);
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * @Author:SHIGS
     * @Description
     * @Date:17:11 2017/11/14
     * @modified By
     */
    @Override
    public Date selectStart() {
        return this.readMapper.selectStart();
    }

    /**
     * @Author:SHIGS
     * @Description
     * @Date:17:11 2017/11/14
     * @modified By
     */
    @Override
    public Date selectEnd() {
        return this.readMapper.selectEnd();
    }

    /**
     * @Author:SHIGS
     * @Description 查询
     * @Date:10:45 2017/10/31
     * @modified By
     */
    @Override
    public Map<String, Object> selectTotal() {
        Map<String, Object> mapMount = readMapper.selectTotal();
        BigDecimal receiveAmount = new BigDecimal(mapMount.get("sd").toString());
        BigDecimal orderAmount = new BigDecimal(mapMount.get("ed").toString());
        Double received = orderAmount.doubleValue() - receiveAmount.doubleValue();
        Map<String, Object> data = new HashMap<>();
        data.put("notReceive", df.format(receiveAmount.doubleValue() / 10000) + "万$");
        data.put("received", df.format(received / 10000) + "万$");
        data.put("totalReceive", df.format(orderAmount.doubleValue() / 10000) + "万$");
        return data;
    }

    /**
     * @Author:SHIGS
     * @Description 应收账款统计
     * @Date:9:14 2017/10/24
     * @modified By
     */
    @Override
    public Map<String, Object> selectRequestTotal(Date startDate, Date endDate) {
        RequestCreditExample requestCreditExample = new RequestCreditExample();
        RequestCreditExample.Criteria criteria = requestCreditExample.createCriteria();
        if (startDate != null) {
            criteria.andCreateAtGreaterThanOrEqualTo(startDate);
        }
        if (endDate != null) {
            criteria.andCreateAtLessThan(endDate);
        }
        return this.readMapper.selectRequestTotal(requestCreditExample);
    }

    /**
     * @Author:SHIGS
     * @Description 应收账款趋势图
     * @Date:9:13 2017/10/24
     * @modified By
     */
    @Override
    public Map<String, Object> selectRequestTrend(Date startTime, Date endTime, String receiveName) {
        Date curDate = new Date();
        Date nextMonthFirstDay = DateUtil.getNextMonthFirstDay(curDate);
        Date nextMonthEndTime = DateUtil.getNextMonthLastDay(curDate);
        //虚拟一个标准的时间集合
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }
        //虚拟一个标准的下月时间集合
        List<String> nextDates = new ArrayList<>();
        int day = DateUtil.getDayBetween(nextMonthFirstDay, nextMonthEndTime);
        for (int i = 0; i < day; i++) {
            Date datetime = DateUtil.sometimeCalendar(nextMonthFirstDay, -i);
            nextDates.add(dateFormat.format(datetime));
        }
        //获取 应收余额 列表
        RequestCreditExample requestCreditExample = new RequestCreditExample();//应收余额
        RequestCreditExample.Criteria criteria = requestCreditExample.createCriteria();
        RequestCreditExample Example2 = new RequestCreditExample();//下月应收
        RequestCreditExample.Criteria criteria2 = Example2.createCriteria();
        RequestReceiveExample example = new RequestReceiveExample();//回款
        RequestReceiveExample.Criteria criteria1 = example.createCriteria();
        if (nextMonthFirstDay != null) {
            criteria2.andBackDateGreaterThanOrEqualTo(nextMonthFirstDay);
        }
        if (nextMonthEndTime != null) {
            criteria2.andBackDateLessThan(nextMonthEndTime);
        }
        if (startTime != null) {
            //  criteria.andBackDateGreaterThanOrEqualTo(startTime);
            criteria1.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andBackDateLessThan(endTime);
            criteria1.andBackDateLessThan(endTime);
        }
        List<Double> rList = new ArrayList<>();//应收余额 列表
        List<Double> bList = new ArrayList<>();//回款金额 列表
        List<Double> orderList = new ArrayList<>();//应收列表
        List<Double> nList = new ArrayList<>();//下月应收余额 列表
        RequestReceiveMapper receiveMapper = readerSession.getMapper(RequestReceiveMapper.class);
        List<Map<String, Object>> backAmountList = receiveMapper.selectBackAmountGroupByBackDate(example);
        List<Map<String, Object>> receiveList = readMapper.selectReceiveGroupByBackDate(requestCreditExample);
        List<Map<String, Object>> nextList = readMapper.selectReceiveGroupByBackDate(Example2);
        Map<String, Map<String, Object>> reMap = receiveList.parallelStream().collect(Collectors.toMap(vo -> vo.get("backDate").toString(), vo -> vo));
        Map<String, Map<String, Object>> nextMap = nextList.parallelStream().collect(Collectors.toMap(vo -> vo.get("backDate").toString(), vo -> vo));
        Map<String, Map<String, Object>> backMap = backAmountList.parallelStream().collect(Collectors.toMap(vo -> vo.get("backDate").toString(), vo -> vo));
        for (String date : nextDates) {
            if (nextMap.containsKey(date)) {
                nList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(nextMap.get(date).get("receiveAmount").toString()), 1d));
            } else {
                nList.add(0d);
            }
        }
        for (String date : dates) {
            if (reMap.containsKey(date)) {
                rList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(reMap.get(date).get("receiveAmount").toString()), 1d));
            } else {
                rList.add(0d);
            }
            if (backMap.containsKey(date)) {
                bList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(backMap.get(date).get("backAmount").toString()), 1d));
            } else {
                bList.add(0d);
            }
        }
        for (int i = 0; i < dates.size(); i++) {
            orderList.add(RateUtil.doubleChainRateTwo(rList.get(i) + bList.get(i), 1d));
        }
        Map<String, Object> datas = new HashMap<>();
        String[] types = {"receivable", "notReceive", "received", "nextMonth"};
        if (receiveName.equals(types[0])) {
            datas.put("legend", "应收账款");
            datas.put("xAxis", dates);
            datas.put("yAxis", orderList);
        } else if (receiveName.equals(types[1])) {
            datas.put("legend", "应收未收");
            datas.put("xAxis", dates);
            datas.put("yAxis", rList);
        } else if (receiveName.equals(types[2])) {
            datas.put("legend", "应收已收");
            datas.put("xAxis", dates);
            datas.put("yAxis", bList);
        } else if (receiveName.equals(types[3])) {
            datas.put("legend", "下月应收");
            datas.put("xAxis", nextDates);
            datas.put("yAxis", nList);
        }
        return datas;
    }


    /**
     * @Author:SHIGS
     * @Description 应收账款下月
     * @Date:9:13 2017/10/24
     * @modified By
     */
    @Override
    public Map<String, Object> selectRequestNextNew(Date startDate, Date endDate, String area, String country) {
        RequestCreditExample requestCreditExample = new RequestCreditExample();
        RequestCreditExample.Criteria criteria1 = requestCreditExample.createCriteria();
        if (startDate != null) {
            criteria1.andBackDateGreaterThanOrEqualTo(startDate);
        }
        if (endDate != null) {
            criteria1.andBackDateLessThan(endDate);
//            criteria1.andCreateAtLessThan(endDate);
        }
        if (StringUtils.isNotBlank(area)) {
            criteria1.andSalesAreaEqualTo(area);
        }
        if (StringUtils.isNotBlank(country)) {
            criteria1.andSalesCountryEqualTo(country);
        }

        return this.readMapper.selectRequestTotal(requestCreditExample);
    }

    /**
     * @Author:SHIGS
     * @Description 应收账款下月
     * @Date:9:13 2017/10/24
     * @modified By
     */
    @Override
    public Map<String, Object> selectRequestNext(Date startDate, Date endDate, String area, String country) {
        RequestCreditExample requestCreditExample = new RequestCreditExample();
        RequestCreditExample.Criteria criteria1 = requestCreditExample.createCriteria();
        if (startDate != null) {
            criteria1.andCreateAtGreaterThanOrEqualTo(startDate);
        }
        if (endDate != null) {
            criteria1.andCreateAtLessThan(endDate);
        }
        if (StringUtils.isNotBlank(area)) {
            criteria1.andSalesAreaEqualTo(area);
        }
        if (StringUtils.isNotBlank(country)) {
            criteria1.andSalesCountryEqualTo(country);
        }

        return this.readMapper.selectRequestTotal(requestCreditExample);
    }

    /**
     * @Author:SHIGS
     * @Description 查询销售大区
     * @Date:9:13 2017/10/24
     * @modified By
     */
    @Override
    public Map<String, Object> selectArea() {
        List<Map> areaMap = readMapper.selectArea();
        List<String> areaList = new ArrayList<>();
        for (Map map : areaMap) {
            String area = map.get("sales_area").toString();
            areaList.add(area);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("area", areaList);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 查询国家
     * @Date:17:15 2017/10/27
     * @modified By
     */
    @Override
    public Map<String, Object> selectCountry(String area) {
        RequestCreditExample requestCreditExample = new RequestCreditExample();
        requestCreditExample.createCriteria().andSalesAreaEqualTo(area);
        List<Map> areaCountry = readMapper.selectCountry(requestCreditExample);
        List<String> countryList = new ArrayList<>();
        for (Map map2 : areaCountry) {
            String country = map2.get("sales_country").toString();
            countryList.add(country);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("country", countryList);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 区域明细
     * @Date:17:15 2017/10/27
     * @modified By
     */
    @Override
    public Map<String, Object> selectByAreaOrCountry(Date startDate, Date endDate, String area, String country) {
        RequestCreditExample e = new RequestCreditExample();
        RequestCreditExample.Criteria criteria = e.createCriteria();
        if (startDate != null) {
            criteria.andCreateAtGreaterThanOrEqualTo(startDate);
        }
        if (endDate != null) {
            criteria.andCreateAtLessThan(endDate);
        }
        if (StringUtil.isNotBlank(area)) {
            criteria.andSalesAreaEqualTo(area);
        }
        if (StringUtil.isNotBlank(country)) {
            criteria.andSalesCountryEqualTo(country);
        }
        return this.readMapper.selectByAreaOrCountry(e);
    }

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
        // 应收账款金额 - orderAmount、 应收未收金额 - receiveAmount
        ImportDataResponse response = new ImportDataResponse(new String[]{"receiveAmount"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        RequestCredit rc = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        // 定义下月应收金额
        BigDecimal nextMouthReceiveAmount = BigDecimal.ZERO;
        // 遍历数据
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.REQUEST_CREDIT, response, cellIndex)) {
                continue;
            }
            rc = new RequestCredit();
            rc.setCreditSerialNum(strArr[0]);
            rc.setOrderNum(strArr[1]);
            rc.setSalesMainCompany(strArr[2]);
            rc.setSalesArea(strArr[3]);
            rc.setSalesCountry(strArr[4]);
            rc.setOrganization(strArr[5]);
            rc.setCustomerCode(strArr[6]);
            rc.setExportProName(strArr[7]);
            rc.setTradeTerms(strArr[8]);
            try {
                rc.setCreateAt(
                        DateUtil.parseString2Date(strArr[9], DateUtil.SHORT_FORMAT_STR, "yyyy/M/d", "yyyy/M/d hh:mm:ss", DateUtil.FULL_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), cellIndex, "创建日期日期格式错误");
                continue;
            }
            try {
                rc.setOrderAmount(new BigDecimal(strArr[10]));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), cellIndex, "订单金额字段非数字");
                continue;
            }
            rc.setCreditCurrency(strArr[11]);
            try {
                rc.setReceiveAmount(new BigDecimal(strArr[12]));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), cellIndex, "应收账款余额字段非数字");
                continue;
            }
            rc.setWarnStatus(strArr[13]);
            rc.setBackResponsePerson(strArr[14]);

            try {
                rc.setBackDate(
                        DateUtil.parseString2Date(strArr[15], DateUtil.FULL_FORMAT_STR, "yyyy/M/d", "yyyy/M/d hh:mm:ss", DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), cellIndex, "下次回款日期格式错误");
                continue;
            }

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(rc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), cellIndex, e.getMessage());
                continue;
            }
            // 在上周范围内的数据做统计
            if (NewDateUtil.inSaturdayWeek(rc.getBackDate())) {
                response.sumData(rc);
            }
            // 下自然月应收金额
            if (NewDateUtil.inNextMonth(rc.getBackDate()) && rc.getReceiveAmount() != null) {
                nextMouthReceiveAmount = nextMouthReceiveAmount.add(rc.getReceiveAmount());
            }
            response.incrSuccess();
        }
        //计算已收
        RequestReceiveMapper mapper = readerSession.getMapper(RequestReceiveMapper.class);
        Date[] dates = NewDateUtil.getBeforeSaturdayWeek(null);
        Date startTime = dates[0];
        Date end = dates[1];
        String endTime1 = DateUtil.getEndTime(end, DateUtil.FULL_FORMAT_STR);
        Date endTime = DateUtil.parseString2DateNoException(endTime1, DateUtil.FULL_FORMAT_STR);
        RequestReceiveExample receiveExample = new RequestReceiveExample();
        RequestReceiveExample.Criteria receiveCriteria = receiveExample.createCriteria();
        if (startTime != null) {
            receiveCriteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            receiveCriteria.andBackDateLessThan(endTime);
        }
        BigDecimal hasReceivedAmount = BigDecimal.ZERO;//应收已收
        double backAmount = mapper.selectBackAmount(receiveExample);
        hasReceivedAmount = hasReceivedAmount.add(new BigDecimal(backAmount));
        // 计算应收已收金额，放置到键 hasReceivedAmount 中
        Map<String, BigDecimal> map = response.getSumMap();
        map.put("hasReceivedAmount", hasReceivedAmount);
        map.put("nextMouthReceiveAmount", nextMouthReceiveAmount);
        map.put("orderAmount", hasReceivedAmount.add(map.get("receiveAmount")));
        response.setDone(true);

        return response;
    }

    @Override
    public List<InquiryAreaVO> selectAllCompanyAndOrgList() {
        List<Map<String, String>> dataList = this.readMapper.selectAllCompanyAndOrgList();
        List<InquiryAreaVO> list = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            Map<String, InquiryAreaVO> map = list.parallelStream()
                    .collect(Collectors.toMap(InquiryAreaVO::getAreaName, vo -> vo));
            for (Map<String, String> data : dataList) {
                String area = data.get("area");
                String country = data.get("country");
                InquiryAreaVO vo = null;
                if (map.containsKey(area)) {
                    vo = map.get(area);
                } else {
                    vo = new InquiryAreaVO();
                    vo.setAreaName(area);
                    list.add(vo);
                    map.put(area, vo);
                }

                vo.pushCountry(country);
            }

        }

        return list;
    }

    @Override
    public Double selectReceive(Date startTime, Date endTime, String company, String org, String area, String country) {
        RequestCreditExample example = new RequestCreditExample();
        RequestCreditExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andBackDateLessThan(endTime);
        }
        if (StringUtils.isNotEmpty(company)) {
            if (company.equals("除易瑞全部")) {
                criteria.andSalesMainCompanyNotLike("%易瑞%");
            } else {
                criteria.andSalesMainCompanyEqualTo(company);
            }
        }
        if (StringUtils.isNotEmpty(org)) {
            if (org.equals("除易瑞全部")) {
                criteria.andOrganizationNotLike("%易瑞%");
            } else {
                criteria.andOrganizationEqualTo(org);
            }
        }
        if (StringUtils.isNotEmpty(area)) {
            criteria.andSalesAreaEqualTo(area);
        }
        if (StringUtils.isNotEmpty(country)) {
            criteria.andSalesCountryEqualTo(country);
        }
        return readMapper.selectReceive(example);
    }

    @Override
    public List<Map<String, Object>> selectReceiveGroupByArea(Date startTime, Date endTime) {
        RequestCreditExample example = new RequestCreditExample();
        RequestCreditExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andBackDateLessThan(endTime);
        }
        return readMapper.selectReceiveGroupByArea(example);
    }

    @Override
    public List<Map<String, Object>> selectReceiveGroupByCompany(Date startTime, Date endTime) {
        RequestCreditExample example = new RequestCreditExample();
        RequestCreditExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andBackDateLessThan(endTime);
        }
        return readMapper.selectReceiveGroupByCompany(example);
    }

    @Override
    public List<Map<String, Object>> selectReceiveGroupByOrg(Date startTime, Date endTime) {
        RequestCreditExample example = new RequestCreditExample();
        RequestCreditExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andBackDateLessThan(endTime);
        }
        return readMapper.selectReceiveGroupByOrg(example);
    }


    @Override
    public Map<String, Object> selectAgingSummary(Map<String, String> map) {
        String company = map.get("company");
        String org = map.get("org");
        if(StringUtils.isNotEmpty(company)) {
            if (company.equals("others")) {
                map.put("company", null);
                map.put("removeCompany1", "易瑞");
                map.put("removeCompany2", "易瑞国际");
                map.put("removeCompany3", "易瑞国际电子商务有限公司");
            }
        }
        if(StringUtils.isNotEmpty(org)) {
            if (org.equals("others")) {
                map.put("org", null);
                map.put("removeOrg1", "易瑞");
                map.put("removeOrg2", "易瑞国际");
                map.put("removeOrg3", "易瑞国际电子商务有限公司");
            }
        }
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = readMapper.selectAgingSummary(map);
        List<String> names = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        if (!data.isEmpty()) {
            names = new ArrayList<>(data.keySet());
            values = new ArrayList<>();
            for (String name : names) {
                double value = RateUtil.doubleChainRateTwo(Double.parseDouble(data.get(name).toString()), 1);
                values.add(value);
            }
        }
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals("thirty")) {
                names.set(i, "30天以内");
            } else if (names.get(i).equals("sixty")) {
                names.set(i, "30-60天");
            } else if (names.get(i).equals("ninety")) {
                names.set(i, "60-90天");
            } else if (names.get(i).equals("oneHundredTwenty")) {
                names.set(i, "90-120天");
            } else if (names.get(i).equals("oneHundredFifty")) {
                names.set(i, "120-150天");
            } else if (names.get(i).equals("oneHundredEighty")) {
                names.set(i, "150-180天");
            } else if (names.get(i).equals("other")) {
                names.set(i, "180天以上");
            }
        }
        result.put("names", names);
        result.put("values", values);
        return result;
    }


    @Override
    public List<Map<String, Object>> selectAgingSummaryGroupByCompanyAndOrgAndArea(Map<String, String> map) {
        //查询综合的各大区账龄数据
        map.put("company", null);
        map.put("org", null);
        List<Map<String, Object>> data = readMapper.selectAgingSummaryByConditionGroupByArea(map);
        data = addAgingProportion(data);
        //查询主体公司为易瑞国际 事业部为易瑞国际的各大区账龄数据
        map.put("company", "易瑞国际");
        map.put("org", "易瑞国际");
        List<Map<String, Object>> data1 = readMapper.selectAgingSummaryByConditionGroupByArea(map);
        data1 = addAgingProportion(data1);
        //查询主体公司为易瑞国际 事业部为其他事业部的各大区账龄数据
        map.put("company", "易瑞国际");
        map.put("org", null);
        map.put("removeOrg1", "易瑞");
        map.put("removeOrg2", "易瑞国际");
        map.put("removeOrg3", "易瑞国际电子商务有限公司");
        List<Map<String, Object>> data2 = readMapper.selectAgingSummaryByConditionGroupByArea(map);
        data2 = addAgingProportion(data2);
        //查询主体公司为其他主体公司 事业部为易瑞的各大区账龄数据
        map.put("company", null);
        map.put("removeCompany1", "易瑞");
        map.put("removeCompany2", "易瑞国际");
        map.put("removeCompany3", "易瑞国际电子商务有限公司");
        map.put("org", "易瑞");
        map.put("removeOrg1", null);
        map.put("removeOrg2", null);
        map.put("removeOrg3", null);
        List<Map<String, Object>> data3 = readMapper.selectAgingSummaryByConditionGroupByArea(map);
        data3 = addAgingProportion(data3);

        //主体公司为易瑞 事业部为易瑞  数据
        Map<String, Object> eruiAndEruiMap = new HashMap<>();
        eruiAndEruiMap.put("title", "销售主体为易瑞隶属易瑞");
        eruiAndEruiMap.put("count", data1.size());
        eruiAndEruiMap.put("detail", data1);


        //主体公司为易瑞 事业部为除易瑞全部
        Map<String, Object> eruiAndOtherMap = new HashMap<>();
        eruiAndOtherMap.put("title", "销售主体为易瑞隶属其它事业部");
        eruiAndOtherMap.put("count", data2.size());
        eruiAndOtherMap.put("detail", data2);

        //主体公司为除易瑞全部 事业部为易瑞
        Map<String, Object> otherAndEruiMap = new HashMap<>();
        otherAndEruiMap.put("title", "销售主体为装备及分子公司隶属易瑞");
        otherAndEruiMap.put("count", data3.size());
        otherAndEruiMap.put("detail", data3);

        //全部主体公司和全部事业部
        Map<String, Object> totalMap = new HashMap<>();
        totalMap.put("title", "综合按大区分类");
        totalMap.put("count", data.size());
        totalMap.put("detail", data);
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(eruiAndEruiMap);
        result.add(eruiAndOtherMap);
        result.add(otherAndEruiMap);
        result.add(totalMap);
        return result;
    }

    @Override
    public XSSFWorkbook exportAgingData(List<Map<String, Object>> data) {

        //声明工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置字体居中

        //生成一个表格
        XSSFSheet sheet = wb.createSheet("账龄分析统计表");
        sheet.setDefaultColumnWidth(10);
        sheet.setColumnWidth(0,8192);
        //合并单元格
        CellRangeAddress cra1 = new CellRangeAddress(1, 8, 0, 0);
        CellRangeAddress cra2 = new CellRangeAddress(9, 16, 0, 0);
        CellRangeAddress cra3 = new CellRangeAddress(17, 24, 0, 0);
        CellRangeAddress cra4 = new CellRangeAddress(25, 32, 0, 0);
        sheet.addMergedRegion(cra1);
        sheet.addMergedRegion(cra2);
        sheet.addMergedRegion(cra3);
        sheet.addMergedRegion(cra4);

        //产生标题行
        XSSFRow row = sheet.createRow(0);
        String[] headers = new String[]{"销售模式", "大区", "30天以内", "占比(%)", "30-60天", "占比(%)",
                "60-90天", "占比(%)", "90-120天", "占比(%)", "120-150天", "占比(%)",
                "150-180天", "占比(%)", "180天以上", "占比(%)", "合计"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
        //销售主体为易瑞隶属易瑞 表格并赋值
        for (Map<String, Object> m : data) {
            String title = m.get("title").toString();
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) m.get("detail");
            Map<String, Map<String, Object>> dataMap = dataList.stream().collect(Collectors.toMap(m1 -> m1.get("area").toString(), m1 -> m1));
            if (title.equals("销售主体为易瑞隶属易瑞")) {
                createAppointRow(1, sheet, dataMap,"销售主体为易瑞隶属易瑞");
            } else if (title.equals("销售主体为易瑞隶属其它事业部")) {
                createAppointRow(9, sheet, dataMap,"销售主体为易瑞隶属其它事业部");
            } else if (title.equals("销售主体为装备及分子公司隶属易瑞")) {
                createAppointRow(17, sheet, dataMap,"销售主体为装备及分子公司隶属易瑞");
            } else if (title.equals("综合按大区分类")) {
                createAppointRow(25, sheet, dataMap,"综合按大区分类");
            }
        }
        return wb;

    }
    /**
     * @Author:lirb
     * @Description 创建每一行并赋值
     * @Date:13:56 2018/4/11
     * @Params index : 行索引 ，dataMap 数据  ， message ：销售模式类型
     */
    private void createAppointRow(int index, XSSFSheet sheet, Map<String, Map<String, Object>> dataMap,String message) {
        for (int i = index; i < index + 7; i++) {
            XSSFRow row = sheet.createRow(i);
            XSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(message);
            if (i == 1||i==9||i==17||i==25) {
                setCellValueUtil(row, dataMap, AreaEnum.BEIMEI.getMessage());
            }
            if (i == 2||i==10||i==18||i==26) {
                setCellValueUtil(row, dataMap, AreaEnum.FANE.getMessage());
            }
            if (i == 3||i==11||i==19||i==27) {
                setCellValueUtil(row, dataMap, AreaEnum.FEIZHOU.getMessage());
            }
            if (i == 4||i==12||i==20||i==28) {
                setCellValueUtil(row, dataMap, AreaEnum.NANMEI.getMessage());
            }
            if (i == 5||i==13||i==21||i==29) {
                setCellValueUtil(row, dataMap, AreaEnum.OUZHOU.getMessage());
            }
            if (i == 6||i==14||i==22||i==30) {
                setCellValueUtil(row, dataMap, AreaEnum.YATAI.getMessage());
            }
            if (i == 7||i==15||i==23||i==31) {
                setCellValueUtil(row, dataMap, AreaEnum.ZHONGDONG.getMessage());
            }
        }
        //算出各大区相加的各区间值
        createTotalRow(dataMap, index, sheet);

    }
    /**
     * @Author:lirb
     * @Description 添加各销售模式所有大区的合计数据 行
     * @Date:13:56 2018/4/10
     * @Params index : 销售模式从哪一行开始的 ，dataMap 数据  ， sheet ：表格
     */
    private void createTotalRow(Map<String, Map<String, Object>> dataMap, int index, XSSFSheet sheet) {
        Map<String, Object> totalRowMap = dataMap.get("合计");
        Double thirty = Double.valueOf(totalRowMap.get("thirty").toString());
        Double thirtyProportion = Double.valueOf(totalRowMap.get("thirtyProportion").toString());
        Double thirtyToSixty = Double.valueOf(totalRowMap.get("thirtyToSixty").toString());
        Double thirtyToSixtyProportion = Double.valueOf(totalRowMap.get("thirtyToSixtyProportion").toString());
        Double sixtyToNinety = Double.valueOf(totalRowMap.get("sixtyToNinety").toString());
        Double sixtyToNinetyProportion = Double.valueOf(totalRowMap.get("sixtyToNinetyProportion").toString());
        Double ninetyToOneHundredTwenty = Double.valueOf(totalRowMap.get("ninetyToOneHundredTwenty").toString());
        Double ninetyToOneHundredTwentyProportion = Double.valueOf(totalRowMap.get("ninetyToOneHundredTwentyProportion").toString());
        Double oneHundredTwentyToOneHundredFifty = Double.valueOf(totalRowMap.get("oneHundredTwentyToOneHundredFifty").toString());
        Double oneHundredTwentyToOneHundredFiftyProportion = Double.valueOf(totalRowMap.get("oneHundredTwentyToOneHundredFiftyProportion").toString());
        Double oneHundredFiftyToOneHundredEighty = Double.valueOf(totalRowMap.get("oneHundredFiftyToOneHundredEighty").toString());
        Double oneHundredFiftyToOneHundredEightyProportion = Double.valueOf(totalRowMap.get("oneHundredFiftyToOneHundredEightyProportion").toString());
        Double moreThanOneHundredEighty = Double.valueOf(totalRowMap.get("moreThanOneHundredEighty").toString());
        Double moreThanOneHundredEightyProportion = Double.valueOf(totalRowMap.get("moreThanOneHundredEightyProportion").toString());
        Double total = Double.valueOf(totalRowMap.get("totalAmount").toString());
        XSSFRow row = sheet.createRow(index + 7);
        XSSFCell cell0 = row.createCell(0);
        XSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("合计");

        XSSFCell cell2 = row.createCell(2);
        cell2.setCellValue(thirty);
        XSSFCell cell3 = row.createCell(3);
        cell3.setCellValue(thirtyProportion);
        XSSFCell cell4 = row.createCell(4);
        cell4.setCellValue(thirtyToSixty);
        XSSFCell cell5 = row.createCell(5);
        cell5.setCellValue(thirtyToSixtyProportion);
        XSSFCell cell6 = row.createCell(6);
        cell6.setCellValue(sixtyToNinety);
        XSSFCell cell7 = row.createCell(7);
        cell7.setCellValue(sixtyToNinetyProportion);
        XSSFCell cell8 = row.createCell(8);
        cell8.setCellValue(ninetyToOneHundredTwenty);
        XSSFCell cell9 = row.createCell(9);
        cell9.setCellValue(ninetyToOneHundredTwentyProportion);
        XSSFCell cell10 = row.createCell(10);
        cell10.setCellValue(oneHundredTwentyToOneHundredFifty);
        XSSFCell cell11 = row.createCell(11);
        cell11.setCellValue(oneHundredTwentyToOneHundredFiftyProportion);
        XSSFCell cell12 = row.createCell(12);
        cell12.setCellValue(oneHundredFiftyToOneHundredEighty);
        XSSFCell cell13 = row.createCell(13);
        cell13.setCellValue(oneHundredFiftyToOneHundredEightyProportion);
        XSSFCell cell14 = row.createCell(14);
        cell14.setCellValue(moreThanOneHundredEighty);
        XSSFCell cell15 = row.createCell(15);
        cell15.setCellValue(moreThanOneHundredEightyProportion);
        XSSFCell cell16 = row.createCell(16);
        cell16.setCellValue(RateUtil.doubleChainRateTwo(total,1));


    }
    /**
     * @Author:lirb
     * @Description 给表格添加数据
     * @Date:13:56 2018/4/10
     * @Params row : 行 ，dataMap 数据  ， area ：大区名
     */
    private void setCellValueUtil(XSSFRow row, Map<String, Map<String, Object>> dataMap, String area) {
        XSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(area);
        Map<String, Object> areaMap = dataMap.get(area);
        XSSFCell thirty = row.createCell(2);
        thirty.setCellValue(RateUtil.doubleChainRateTwo(Double.parseDouble(areaMap.get("thirty").toString()),1));
        XSSFCell thirtyPropertion = row.createCell(3);
        thirtyPropertion.setCellValue(Double.parseDouble(areaMap.get("thirtyProportion").toString()));
        XSSFCell thirtyToSixty = row.createCell(4);
        thirtyToSixty.setCellValue(Double.parseDouble(areaMap.get("thirtyToSixty").toString()));
        XSSFCell thirtyToSixtyPropertion = row.createCell(5);
        thirtyToSixtyPropertion.setCellValue(Double.parseDouble(areaMap.get("thirtyToSixtyProportion").toString()));
        XSSFCell sixtyToNinety = row.createCell(6);
        sixtyToNinety.setCellValue(Double.parseDouble(areaMap.get("sixtyToNinety").toString()));
        XSSFCell sixtyToNinetyProportion = row.createCell(7);
        sixtyToNinetyProportion.setCellValue(Double.parseDouble(areaMap.get("sixtyToNinetyProportion").toString()));
        XSSFCell ninetyToOneHundredTwenty = row.createCell(8);
        ninetyToOneHundredTwenty.setCellValue(Double.parseDouble(areaMap.get("ninetyToOneHundredTwenty").toString()));
        XSSFCell ninetyToOneHundredTwentyProportion = row.createCell(9);
        ninetyToOneHundredTwentyProportion.setCellValue(Double.parseDouble(areaMap.get("ninetyToOneHundredTwentyProportion").toString()));
        XSSFCell oneHundredTwentyToOneHundredFifty = row.createCell(10);
        oneHundredTwentyToOneHundredFifty.setCellValue(Double.parseDouble(areaMap.get("oneHundredTwentyToOneHundredFifty").toString()));
        XSSFCell oneHundredTwentyToOneHundredFiftyProportion = row.createCell(11);
        oneHundredTwentyToOneHundredFiftyProportion.setCellValue(Double.parseDouble(areaMap.get("oneHundredTwentyToOneHundredFiftyProportion").toString()));
        XSSFCell oneHundredFiftyToOneHundredEighty = row.createCell(12);
        oneHundredFiftyToOneHundredEighty.setCellValue(Double.parseDouble(areaMap.get("oneHundredFiftyToOneHundredEighty").toString()));
        XSSFCell oneHundredFiftyToOneHundredEightyProportion = row.createCell(13);
        oneHundredFiftyToOneHundredEightyProportion.setCellValue(Double.parseDouble(areaMap.get("oneHundredFiftyToOneHundredEightyProportion").toString()));
        XSSFCell moreThanOneHundredEighty = row.createCell(14);
        moreThanOneHundredEighty.setCellValue(Double.parseDouble(areaMap.get("moreThanOneHundredEighty").toString()));
        XSSFCell moreThanOneHundredEightyProportion = row.createCell(15);
        moreThanOneHundredEightyProportion.setCellValue(Double.parseDouble(areaMap.get("moreThanOneHundredEightyProportion").toString()));
        XSSFCell totalAmount = row.createCell(16);
        totalAmount.setCellValue(Double.parseDouble(areaMap.get("totalAmount").toString()));

    }
    /**
     * @Author:lirb
     * @Description  添加各账龄数据占比数据和添加合计数据
     * @Date:13:56 2018/4/10
     * @modified By
     */
    private List<Map<String, Object>> addAgingProportion(List<Map<String, Object>> data) {
        //合并数据
        if (data.isEmpty()) data = new ArrayList<>();

        Map<String, Map<String, Object>> dataMap = data.stream().collect(Collectors.toMap(m -> m.get("area").toString(), m -> m));
        if (!dataMap.containsKey(AreaEnum.BEIMEI.getMessage())) {
            Map<String, Object> beimeiMap = new HashMap<>();
            beimeiMap.put("area", AreaEnum.BEIMEI.getMessage());
            beimeiMap.put("totalAmount", 0d);
            beimeiMap.put("thirty", 0d);
            beimeiMap.put("thirtyToSixty", 0d);
            beimeiMap.put("sixtyToNinety", 0d);
            beimeiMap.put("ninetyToOneHundredTwenty", 0d);
            beimeiMap.put("oneHundredTwentyToOneHundredFifty", 0d);
            beimeiMap.put("oneHundredFiftyToOneHundredEighty", 0d);
            beimeiMap.put("moreThanOneHundredEighty", 0d);
            data.add(beimeiMap);
        }
        if (!dataMap.containsKey(AreaEnum.NANMEI.getMessage())) {
            Map<String, Object> nameiMap = new HashMap<>();
            nameiMap.put("area", AreaEnum.NANMEI.getMessage());
            nameiMap.put("totalAmount", 0d);
            nameiMap.put("thirty", 0d);
            nameiMap.put("thirtyToSixty", 0d);
            nameiMap.put("sixtyToNinety", 0d);
            nameiMap.put("ninetyToOneHundredTwenty", 0d);
            nameiMap.put("oneHundredTwentyToOneHundredFifty", 0d);
            nameiMap.put("oneHundredFiftyToOneHundredEighty", 0d);
            nameiMap.put("moreThanOneHundredEighty", 0d);
            data.add(nameiMap);
        }
        if (!dataMap.containsKey(AreaEnum.OUZHOU.getMessage())) {
            Map<String, Object> ouzhouMap = new HashMap<>();
            ouzhouMap.put("area", AreaEnum.OUZHOU.getMessage());
            ouzhouMap.put("totalAmount", 0d);
            ouzhouMap.put("thirty", 0d);
            ouzhouMap.put("thirtyToSixty", 0d);
            ouzhouMap.put("sixtyToNinety", 0d);
            ouzhouMap.put("ninetyToOneHundredTwenty", 0d);
            ouzhouMap.put("oneHundredTwentyToOneHundredFifty", 0d);
            ouzhouMap.put("oneHundredFiftyToOneHundredEighty", 0d);
            ouzhouMap.put("moreThanOneHundredEighty", 0d);
            data.add(ouzhouMap);
        }
        if (!dataMap.containsKey(AreaEnum.YATAI.getMessage())) {
            Map<String, Object> yataiMap = new HashMap<>();
            yataiMap.put("area", AreaEnum.YATAI.getMessage());
            yataiMap.put("totalAmount", 0d);
            yataiMap.put("thirty", 0d);
            yataiMap.put("thirtyToSixty", 0d);
            yataiMap.put("sixtyToNinety", 0d);
            yataiMap.put("ninetyToOneHundredTwenty", 0d);
            yataiMap.put("oneHundredTwentyToOneHundredFifty", 0d);
            yataiMap.put("oneHundredFiftyToOneHundredEighty", 0d);
            yataiMap.put("moreThanOneHundredEighty", 0d);
            data.add(yataiMap);
        }
        if (!dataMap.containsKey(AreaEnum.FEIZHOU.getMessage())) {
            Map<String, Object> feizhouMap = new HashMap<>();
            feizhouMap.put("area", AreaEnum.FEIZHOU.getMessage());
            feizhouMap.put("totalAmount", 0d);
            feizhouMap.put("thirty", 0d);
            feizhouMap.put("thirtyToSixty", 0d);
            feizhouMap.put("sixtyToNinety", 0d);
            feizhouMap.put("ninetyToOneHundredTwenty", 0d);
            feizhouMap.put("oneHundredTwentyToOneHundredFifty", 0d);
            feizhouMap.put("oneHundredFiftyToOneHundredEighty", 0d);
            feizhouMap.put("moreThanOneHundredEighty", 0d);
            data.add(feizhouMap);
        }
        if (!dataMap.containsKey(AreaEnum.FANE.getMessage())) {
            Map<String, Object> faneMap = new HashMap<>();
            faneMap.put("area", AreaEnum.FANE.getMessage());
            faneMap.put("totalAmount", 0d);
            faneMap.put("thirty", 0d);
            faneMap.put("thirtyToSixty", 0d);
            faneMap.put("sixtyToNinety", 0d);
            faneMap.put("ninetyToOneHundredTwenty", 0d);
            faneMap.put("oneHundredTwentyToOneHundredFifty", 0d);
            faneMap.put("oneHundredFiftyToOneHundredEighty", 0d);
            faneMap.put("moreThanOneHundredEighty", 0d);
            data.add(faneMap);
        }
        if (!dataMap.containsKey(AreaEnum.ZHONGDONG.getMessage())) {
            Map<String, Object> zhongdongMap = new HashMap<>();
            zhongdongMap.put("area", AreaEnum.ZHONGDONG.getMessage());
            zhongdongMap.put("totalAmount", 0d);
            zhongdongMap.put("thirty", 0d);
            zhongdongMap.put("thirtyToSixty", 0d);
            zhongdongMap.put("sixtyToNinety", 0d);
            zhongdongMap.put("ninetyToOneHundredTwenty", 0d);
            zhongdongMap.put("oneHundredTwentyToOneHundredFifty", 0d);
            zhongdongMap.put("oneHundredFiftyToOneHundredEighty", 0d);
            zhongdongMap.put("moreThanOneHundredEighty", 0d);
            data.add(zhongdongMap);
        }

        data.stream().forEach(m -> {
            double totalAmount = Double.parseDouble(m.get("totalAmount").toString());
            m.put("totalAmount",RateUtil.doubleChainRateTwo(totalAmount,1));
            if (totalAmount > 0) {
                double thirty = Double.parseDouble(m.get("thirty").toString());
                m.put("thirty",RateUtil.doubleChainRateTwo(thirty,1));
                m.put("thirtyProportion", RateUtil.doubleChainRateTwo(thirty*100, totalAmount));
                double sixty = Double.parseDouble(m.get("thirtyToSixty").toString());
                m.put("thirtyToSixty",RateUtil.doubleChainRateTwo(sixty,1));
                m.put("thirtyToSixtyProportion", RateUtil.doubleChainRateTwo(sixty*100, totalAmount));
                double ninety = Double.parseDouble(m.get("sixtyToNinety").toString());
                m.put("sixtyToNinety",RateUtil.doubleChainRateTwo(ninety,1));
                m.put("sixtyToNinetyProportion", RateUtil.doubleChainRateTwo(ninety*100, totalAmount));
                double oneHundredTwenty = Double.parseDouble(m.get("ninetyToOneHundredTwenty").toString());
                m.put("ninetyToOneHundredTwenty",RateUtil.doubleChainRateTwo(oneHundredTwenty,1));
                m.put("ninetyToOneHundredTwentyProportion", RateUtil.doubleChainRateTwo(oneHundredTwenty*100, totalAmount));
                double oneHundredFifty = Double.parseDouble(m.get("oneHundredTwentyToOneHundredFifty").toString());
                m.put("oneHundredTwentyToOneHundredFifty",RateUtil.doubleChainRateTwo(oneHundredFifty,1));
                m.put("oneHundredTwentyToOneHundredFiftyProportion", RateUtil.doubleChainRateTwo(oneHundredFifty*100, totalAmount));
                double oneHundredEighty = Double.parseDouble(m.get("oneHundredFiftyToOneHundredEighty").toString());
                m.put("oneHundredFiftyToOneHundredEighty",RateUtil.doubleChainRateTwo(oneHundredEighty,1));
                m.put("oneHundredFiftyToOneHundredEightyProportion", RateUtil.doubleChainRateTwo(oneHundredEighty*100, totalAmount));
                double other = Double.parseDouble(m.get("moreThanOneHundredEighty").toString());
                m.put("moreThanOneHundredEighty",RateUtil.doubleChainRateTwo(other,1));
                m.put("moreThanOneHundredEightyProportion", RateUtil.doubleChainRateTwo(other*100, totalAmount));

            } else {
                m.put("thirtyProportion", 0d);
                m.put("thirtyToSixtyProportion", 0d);
                m.put("sixtyToNinetyProportion", 0d);
                m.put("ninetyToOneHundredTwentyProportion", 0d);
                m.put("oneHundredTwentyToOneHundredFiftyProportion", 0d);
                m.put("oneHundredFiftyToOneHundredEightyProportion", 0d);
                m.put("moreThanOneHundredEightyProportion", 0d);
            }
        });

        //添加合计数据
       data.add(getTotalRowMap(data));
        return data;
    }

    /**
     * @Author:lirb
     * @Description 获取合计行数据
     * @Date:13:56 2018/4/28
     * @Params data 数据
     * @Return map
     */
    private  Map<String,Object> getTotalRowMap(List<Map<String,Object>> data){
        //添加合计数据
        Map<String,Object> totalMap=new HashMap<>();
        double totalThirty = data.stream().map(m -> {
            double thirty = Double.valueOf(m.get("thirty").toString());
            return thirty;
        }).reduce(0d, (a, b) -> a + b);
        double totalSixty = data.stream().map(m -> {
            double thirtyToSixty = Double.valueOf(m.get("thirtyToSixty").toString());
            return thirtyToSixty;
        }).reduce(0d, (a, b) -> a + b);
        double totalNinety = data.stream().map(m -> {
            double sixtyToNinety = Double.valueOf(m.get("sixtyToNinety").toString());
            return sixtyToNinety;
        }).reduce(0d, (a, b) -> a + b);
        double totalOneHundredTwenty = data.stream().map(m -> {
            double ninetyToOneHundredTwenty = Double.valueOf(m.get("ninetyToOneHundredTwenty").toString());
            return ninetyToOneHundredTwenty;
        }).reduce(0d, (a, b) -> a + b);
        double totalOneHundredFifty = data.stream().map(m -> {
            double oneHundredTwentyToOneHundredFifty = Double.valueOf(m.get("oneHundredTwentyToOneHundredFifty").toString());
            return oneHundredTwentyToOneHundredFifty;
        }).reduce(0d, (a, b) -> a + b);
        double totalOneHundredEighty = data.stream().map(m -> {
            double oneHundredFiftyToOneHundredEighty = Double.valueOf(m.get("oneHundredFiftyToOneHundredEighty").toString());
            return oneHundredFiftyToOneHundredEighty;
        }).reduce(0d, (a, b) -> a + b);
        double totalMoreThanOneHundredEighty = data.stream().map(m -> {
            double moreThanOneHundredEighty = Double.valueOf(m.get("moreThanOneHundredEighty").toString());
            return moreThanOneHundredEighty;
        }).reduce(0d, (a, b) -> a + b);
        double total=totalThirty+totalSixty+totalNinety+totalOneHundredTwenty+
                totalOneHundredFifty+totalOneHundredEighty+totalMoreThanOneHundredEighty;
        totalMap.put("thirty",RateUtil.doubleChainRateTwo(totalThirty,1));
        totalMap.put("thirtyProportion",RateUtil.doubleChainRateTwo(totalThirty*100,total));
        totalMap.put("thirtyToSixty",RateUtil.doubleChainRateTwo(totalSixty,1));
        totalMap.put("thirtyToSixtyProportion",RateUtil.doubleChainRateTwo(totalSixty*100,total));
        totalMap.put("sixtyToNinety",RateUtil.doubleChainRateTwo(totalNinety,1));
        totalMap.put("sixtyToNinetyProportion",RateUtil.doubleChainRateTwo(totalNinety*100,total));
        totalMap.put("ninetyToOneHundredTwenty",RateUtil.doubleChainRateTwo(totalOneHundredTwenty,1));
        totalMap.put("ninetyToOneHundredTwentyProportion",RateUtil.doubleChainRateTwo(totalOneHundredTwenty*100,total));
        totalMap.put("oneHundredTwentyToOneHundredFifty",RateUtil.doubleChainRateTwo(totalOneHundredFifty,1));
        totalMap.put("oneHundredTwentyToOneHundredFiftyProportion",RateUtil.doubleChainRateTwo(totalOneHundredFifty*100,total));
        totalMap.put("oneHundredFiftyToOneHundredEighty",RateUtil.doubleChainRateTwo(totalOneHundredEighty,1));
        totalMap.put("oneHundredFiftyToOneHundredEightyProportion",RateUtil.doubleChainRateTwo(totalOneHundredEighty*100,total));
        totalMap.put("moreThanOneHundredEighty",RateUtil.doubleChainRateTwo(totalMoreThanOneHundredEighty,1));
        totalMap.put("moreThanOneHundredEightyProportion",RateUtil.doubleChainRateTwo(totalThirty*100,total));
        totalMap.put("totalAmount",RateUtil.doubleChainRateTwo(total,1));
        totalMap.put("area","合计");

        return  totalMap;
    }
}