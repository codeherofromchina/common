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
import com.erui.report.model.RequestReceive;
import com.erui.report.model.RequestReceiveExample;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.lang3.StringUtils;
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
        if(nextMonthFirstDay!=null){
            criteria2.andBackDateGreaterThanOrEqualTo(nextMonthFirstDay);
        }
        if(nextMonthEndTime!=null){
            criteria2.andBackDateLessThan(nextMonthEndTime);
        }
        if (startTime != null) {
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
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
        for (String date:nextDates ) {
            if(nextMap.containsKey(date)){
                nList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(nextMap.get(date).get("receiveAmount").toString()),1d));
            }else {
                nList.add(0d);
            }
        }
        for (String date : dates) {
            if (reMap.containsKey(date)) {
                rList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(reMap.get(date).get("receiveAmount").toString()),1d));
            } else {
                rList.add(0d);
            }
            if (backMap.containsKey(date)) {
                bList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(backMap.get(date).get("backAmount").toString()),1d));
            } else {
                bList.add(0d);
            }
        }
        for (int i = 0; i < dates.size(); i++) {
            orderList.add(RateUtil.doubleChainRateTwo(rList.get(i) + bList.get(i),1d));
        }
        Map<String,Object> datas=new HashMap<>();
        String[] types={"receivable","notReceive","received","nextMonth"};
        if(receiveName.equals(types[0])){
            datas.put("legend","应收账款");
            datas.put("xAxis",dates);
            datas.put("yAxis",orderList);
        }else if(receiveName.equals(types[1])){
            datas.put("legend","应收未收");
            datas.put("xAxis",dates);
            datas.put("yAxis",rList);
        }else if(receiveName.equals(types[2])){
            datas.put("legend","应收已收");
            datas.put("xAxis",dates);
            datas.put("yAxis",bList);
        }else if(receiveName.equals(types[3])){
            datas.put("legend","下月应收");
            datas.put("xAxis",nextDates);
            datas.put("yAxis",nList);
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
            criteria.andSalesMainCompanyEqualTo(company);
        }
        if (StringUtils.isNotEmpty(org)) {
            criteria.andOrganizationEqualTo(org);
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
}