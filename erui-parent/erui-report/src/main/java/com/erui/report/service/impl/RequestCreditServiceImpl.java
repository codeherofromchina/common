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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
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
            if(company.equals("除易瑞全部")){
                criteria.andSalesMainCompanyNotLike("%易瑞%");
            }else {
                criteria.andSalesMainCompanyEqualTo(company);
            }
        }
        if (StringUtils.isNotEmpty(org)) {
            if(org.equals("除易瑞全部")){
                criteria.andOrganizationNotLike("%易瑞%");
            }else {
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
        Map<String, Object> result=new HashMap<>();
        Map<String, Object> data = readMapper.selectAgingSummary(map);
        List<String> names=new ArrayList<>();
        List<Double> values=new ArrayList<>();
        if(MapUtils.isNotEmpty(data)) {
             names = new ArrayList<>(data.keySet());
             values = new ArrayList<>();
            for (String name : names) {
                double value = RateUtil.doubleChainRateTwo(Double.parseDouble(data.get(name).toString()), 1);
                values.add(value);
            }
        }
        result.put("names",names);
        result.put("values",values);
        return  result;
    }

    @Override
    public  List<Map<String,Object>> selectAgingSummaryGroupByCompanyAndOrgAndArea(Map<String, String> map) {
       //查询综合的各大区账龄数据
        map.put("company",null);
        map.put("org",null);
        List<Map<String,Object>> data =readMapper.selectAgingSummaryByConditionGroupByArea(map);
        data= addAgingProportion(data);
       //查询主体公司为易瑞国际 事业部为易瑞国际的各大区账龄数据
        map.put("company","易瑞国际");
        map.put("org","易瑞国际");
        List<Map<String,Object>> data1 =readMapper.selectAgingSummaryByConditionGroupByArea(map);
        data1= addAgingProportion(data1);
       //查询主体公司为易瑞国际 事业部为其他事业部的各大区账龄数据
        map.put("company","易瑞国际");
        map.put("org",null);
        map.put("removeOrg1","易瑞");
        map.put("removeOrg2","易瑞国际");
        map.put("removeOrg3","易瑞国际电子商务有限公司");
        List<Map<String,Object>> data2 =readMapper.selectAgingSummaryByConditionGroupByArea(map);
        data2= addAgingProportion(data2);
        //查询主体公司为其他主体公司 事业部为易瑞的各大区账龄数据
        map.put("company",null);
        map.put("removeCompany1","易瑞");
        map.put("removeCompany2","易瑞国际");
        map.put("removeCompany3","易瑞国际电子商务有限公司");
        map.put("org","易瑞");
        map.put("removeOrg1",null);
        map.put("removeOrg2",null);
        map.put("removeOrg3",null);
        List<Map<String,Object>> data3 =readMapper.selectAgingSummaryByConditionGroupByArea(map);
        data3= addAgingProportion(data3);

        //主体公司为易瑞 事业部为易瑞  数据
        Map<String,Object> eruiAndEruiMap=new HashMap<>();
        eruiAndEruiMap.put("title","销售主体为易瑞隶属易瑞");
        if(CollectionUtils.isNotEmpty(data1)){
            eruiAndEruiMap.put("count",data1.size());
        }else {
            eruiAndEruiMap.put("count",0);
        }
        eruiAndEruiMap.put("detail",data1);

        //主体公司为易瑞 事业部为除易瑞全部
        Map<String,Object> eruiAndOtherMap=new HashMap<>();
        eruiAndOtherMap.put("title","销售主体为易瑞隶属其它事业部");
        if(CollectionUtils.isNotEmpty(data2)){
            eruiAndOtherMap.put("count",data2.size());
        }else {
            eruiAndOtherMap.put("count",0);
        }
        eruiAndOtherMap.put("detail",data2);

        //主体公司为除易瑞全部 事业部为易瑞
        Map<String,Object> otherAndEruiMap=new HashMap<>();
        otherAndEruiMap.put("title","销售主体为装备及分子公司隶属易瑞");
        if(CollectionUtils.isNotEmpty(data3)){
            otherAndEruiMap.put("count",data3.size());
        }else {
            otherAndEruiMap.put("count",0);
        }
        otherAndEruiMap.put("detail",data3);

        //全部主体公司和全部事业部
        Map<String,Object> totalMap=new HashMap<>();
        totalMap.put("title","综合按大区分类");
        if(CollectionUtils.isNotEmpty(data)){
            totalMap.put("count",data.size());
        }else {
            totalMap.put("count",0);
        }
        totalMap.put("detail",data);
        List<Map<String,Object>> result=new ArrayList<>();
        result.add(eruiAndEruiMap);
        result.add(eruiAndOtherMap);
        result.add(otherAndEruiMap);
        result.add(totalMap);
        return result;
    }

    private  List<Map<String,Object>> addAgingProportion( List<Map<String,Object>> data){
        //合并数据
        data.stream().forEach(m->{
            double totalAmount = Double.parseDouble(m.get("totalAmount").toString());
            if(totalAmount>0){
                double thirty = Double.parseDouble(m.get("thirty").toString());
                m.put("thirtyProportion",RateUtil.doubleChainRate(thirty,totalAmount));
                double sixty = Double.parseDouble(m.get("thirtyToSixty").toString());
                m.put("thirtyToSixtyProportion",RateUtil.doubleChainRate(sixty,totalAmount));
                double ninety = Double.parseDouble(m.get("sixtyToNinety").toString());
                m.put("sixtyToNinetyProportion",RateUtil.doubleChainRate(ninety,totalAmount));
                double oneHundredTwenty = Double.parseDouble(m.get("ninetyToOneHundredTwenty").toString());
                m.put("ninetyToOneHundredTwentyProportion",RateUtil.doubleChainRate(oneHundredTwenty,totalAmount));
                double oneHundredFifty = Double.parseDouble(m.get("oneHundredTwentyToOneHundredFifty").toString());
                m.put("oneHundredTwentyToOneHundredFiftyProportion",RateUtil.doubleChainRate(oneHundredFifty,totalAmount));
                double oneHundredEighty = Double.parseDouble(m.get("oneHundredFiftyToOneHundredEighty").toString());
                m.put("oneHundredFiftyToOneHundredEightyProportion",RateUtil.doubleChainRate(oneHundredEighty,totalAmount));
                double other = Double.parseDouble(m.get("moreThanOneHundredEighty").toString());
                m.put("moreThanOneHundredEightyProportion",RateUtil.doubleChainRate(other,totalAmount));

            }else {
                m.put("thirtyProportion",0d);
                m.put("thirtyToSixtyProportion",0d);
                m.put("sixtyToNinetyProportion",0d);
                m.put("ninetyToOneHundredTwentyProportion",0d);
                m.put("oneHundredTwentyToOneHundredFiftyProportion",0d);
                m.put("oneHundredFiftyToOneHundredEightyProportion",0d);
                m.put("moreThanOneHundredEightyProportion",0d);
            }
        });

        return  data;
    }
}