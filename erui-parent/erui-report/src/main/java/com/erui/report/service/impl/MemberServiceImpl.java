package com.erui.report.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.InquiryCountMapper;
import com.erui.report.dao.MemberMapper;
import com.erui.report.model.MarketerCount;
import com.erui.report.model.Member;
import com.erui.report.model.MemberExample;
import com.erui.report.service.MemberService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends BaseService<MemberMapper> implements MemberService {
    private final static Logger logger = LoggerFactory.getLogger(InquiryCountServiceImpl.class);

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse(
                new String[]{"generalMemberCount", "seniorMemberCount", "goldMemberCount"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        Member member = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.MEMBER, response, cellIndex)) {
                continue;
            }
            member = new Member();
            try {
                member.setInputDate(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "日期字段格式错误");
                continue;
            }

            if (strArr[1] != null) {
                try {
                    member.setGeneralMemberCount(new BigDecimal(strArr[1]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "普通会员数量不是数字");
                    continue;
                }
            }
            if (strArr[2] != null) {
                try {
                    member.setGeneralMemberRebuy(new BigDecimal(strArr[2]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "普通会员二次购买数量不是数字");
                    continue;
                }
            }
            if (strArr[3] != null) {
                try {
                    member.setSeniorMemberCount(new BigDecimal(strArr[3]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "高级会员数量不是数字");
                    continue;
                }
            }
            if (strArr[4] != null) {
                try {
                    member.setSeniorMemberRebuy(new BigDecimal(strArr[4]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "高级会员二次购买数量不是数字");
                    continue;
                }
            }
            if (strArr[5] != null) {
                try {
                    member.setGoldMemberCount(new BigDecimal(strArr[5]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "黄金会员数量不是数字");
                    continue;
                }
            }
            if (strArr[6] != null) {
                try {
                    member.setGoldMemberRebuy(new BigDecimal(strArr[6]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "黄金会员二次购买数量不是数字");
                    continue;
                }
            }

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(member);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, e.getMessage());
                continue;
            }
            if (NewDateUtil.inSaturdayWeek(member.getInputDate())) {
                response.sumData(member);
            }
            response.incrSuccess();
        }
        Map<String, BigDecimal> sumMap = response.getSumMap();
        sumMap.put("memberCount", sumMap.get("generalMemberCount").add(sumMap.get("seniorMemberCount")).add(sumMap.get("goldMemberCount")));
        response.setDone(true);

        return response;

    }

    @Override
    public int selectByTime(Date startTime, Date endDate) {
        MemberExample example = new MemberExample();
        MemberExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andInputDateGreaterThanOrEqualTo(startTime);
        }
        if (endDate != null) {
            criteria.andInputDateLessThan(endDate);
        }
        return this.readMapper.selectByTime(example);
    }

    @Override
    @Deprecated
    public Map<String, Object> selectMemberByTime() {
        Map<String, Object> member = readMapper.selectMemberByTime();
        if (member == null) {
            member = new HashMap<>();
            member.put("s1", 0);
            member.put("s2", 0);
            member.put("s3", 0);
            member.put("s4", 0);
            member.put("s5", 0);
            member.put("s6", 0);
        }
        //黄金会员 高级会员 一般会员
        BigDecimal goldMember = null;
        BigDecimal seniorMember = null;
        BigDecimal generalMember = null;
        if (!member.containsKey("s1") || !member.containsKey("s3") || !member.containsKey("s5")) {
            member.put("s1", 0);
            member.put("s3", 0);
            member.put("s5", 0);
        } else {
            goldMember = new BigDecimal(member.get("s5").toString());
            seniorMember = new BigDecimal(member.get("s3").toString());
            generalMember = new BigDecimal(member.get("s1").toString());
        }
        //黄金会员、高级会员、一般会员的复购率
        BigDecimal regoldMember = null;
        BigDecimal reseniorMember = null;
        BigDecimal regeneralMember = null;
        if (!member.containsKey("s2") || !member.containsKey("s4") || !member.containsKey("s6")) {
            member = new HashMap<>();
            member.put("s2", 0);
            member.put("s4", 0);
            member.put("s6", 0);
        } else {
            regoldMember = new BigDecimal(member.get("s6").toString());
            reseniorMember = new BigDecimal(member.get("s4").toString());
            regeneralMember = new BigDecimal(member.get("s2").toString());
        }
        double goldMemberRate = RateUtil.intChainRate(regoldMember.intValue(), goldMember.intValue());
        double seniorMemberRate = RateUtil.intChainRate(reseniorMember.intValue(), seniorMember.intValue());
        double generalMemberRate = RateUtil.intChainRate(regeneralMember.intValue(), generalMember.intValue());
        Map<String, Object> gold = new HashMap<>();
        Map<String, Object> senior = new HashMap<>();
        Map<String, Object> general = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        gold.put("count", goldMember);
        gold.put("rebuyRate", goldMemberRate);
        senior.put("count", seniorMember);
        senior.put("rebuyRate", seniorMemberRate);
        general.put("count", generalMember);
        general.put("rebuyRate", generalMemberRate);
        data.put("goldMember", gold);
        data.put("seniorMember", senior);
        data.put("generalMember", general);
        return data;
    }

    @Override
    public Map<String, Object> selectOperateSummaryData(Map<String, String> params) {
        Map<String, Object> custData = readMapper.selectOperateSummaryData(params);
        InquiryCountMapper inqMapper = readerSession.getMapper(InquiryCountMapper.class);
        Map<String, Object> inqAndOrdData = inqMapper.selectInqAndOrdCountAndPassengers(params);
        Map<String, Object> data = new HashMap<>();
        data.put("member", custData);
        data.put("inqAndOrd", inqAndOrdData);
        return data;
    }

    @Override
    public Map<String, Object> selectOperateTrend(Date startTime, Date endTime) {
        //1.构建一个标准的时间集合
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }
        //2.获取趋势图数据
        Map<String, String> params = new HashMap<>();
        params.put("startTime", DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2));
        params.put("endTime", DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2));
        //获取会员趋势图数据
        List<Map<String, Object>> dataList = readMapper.selectOperateTrend(params);
        //3整理数据
        List<Integer> totalList = new ArrayList<>();//注册数列表
        List<Integer> generalList = new ArrayList<>();//普通会员数列表
        List<Integer> seniorList = new ArrayList<>();//高级会员数列表
        Map<String, Map<String, Object>> dataMap = dataList.stream().
                collect(Collectors.toMap(vo -> vo.get("creatTime").toString(), vo -> vo));
        for (String date : dates) {
            if (dataMap.containsKey(date)) {
                Map<String, Object> data = dataMap.get(date);
                totalList.add(Integer.parseInt(data.get("totalCount").toString()));
                generalList.add(Integer.parseInt(data.get("generalCount").toString()));
                seniorList.add(Integer.parseInt(data.get("seniorCount").toString()));
            } else {
                totalList.add(0);
                generalList.add(0);
                seniorList.add(0);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("datetime", dates);
        result.put("registerCount", totalList);
        result.put("regulerMembers", generalList);
        result.put("seniorMembers", seniorList);
        return result;
    }

    @Override
    public Map<String, Integer> selectRegisterSummaryData(Map<String, String> params) {
        Map<String, Object> operateData = readMapper.selectOperateSummaryData(params);
        int registerCount = Integer.parseInt(operateData.get("registerCount").toString());//注册会员量
        int seniorCount = Integer.parseInt(operateData.get("seniorCount").toString());//高级会员量
        //查询注册人数询单量和注册人数订单量
        Map<String, Integer> data = readMapper.selectRegisterInqAndOrdCount(params);
        data.put("registers", registerCount);
        data.put("seniorMembers", seniorCount);
        return data;
    }

    @Override
    public Map<String, Object> selectRegisterCountGroupByArea(Map<String, String> params) {
        //各区域的注册数量 registerCount ,area
        List<Map<String, Object>> dataList = readMapper.selectRegisterCountGroupByArea(params);
        List<String> areaList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        dataList.stream().forEach(m -> {
            String area = m.get("area").toString();
            int registerCount = Integer.parseInt(m.get("registerCount").toString());
            areaList.add(area);
            countList.add(registerCount);
        });

        Map<String, Object> data = new HashMap<>();
        data.put("area", areaList);
        data.put("registers", countList);
        return data;
    }

    @Override
    public List<Map<String, Integer>> selectCustInqFrequencyData(Map<String, String> params) {

        //查询交易频率明细 inqCount ，custName
        List<Map<String, Object>> inqList = readMapper.selectCustInqRateDetail(params);

        Map<Integer, List<String>> dataMap = new HashMap<>();
        inqList.stream().forEach(map1 -> {
            String custName = String.valueOf(map1.get("custName"));
            int inqCount = Integer.parseInt(map1.get("inqCount").toString());
            if (dataMap.containsKey(inqCount)) {
                dataMap.get(inqCount).add(custName);
            } else {
                List<String> names = new ArrayList<>();
                names.add(custName);
                dataMap.put(inqCount, names);
            }
        });
        List<Map<String, Integer>> data = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : dataMap.entrySet()) {
            Map<String, Integer> map = new HashMap<>();
            map.put("inqRate", entry.getKey());
            map.put("custCount", entry.getValue().size());
            data.add(map);
        }
        return data;
    }

    @Override
    public List<Map<String, Integer>> selectCustOrdFrequencyData(Map<String, String> params) {

        //查询交易频率明细 buyCount ，custName
        List<Map<String, Object>> ordList = readMapper.selectCustOrdRateDetail(params);

        Map<Integer, List<String>> dataMap = new HashMap<>();
        ordList.stream().forEach(map1 -> {
            String custName = String.valueOf(map1.get("custName"));
            int buyCount = Integer.parseInt(map1.get("buyCount").toString());
            if (dataMap.containsKey(buyCount)) {
                dataMap.get(buyCount).add(custName);
            } else {
                List<String> names = new ArrayList<>();
                names.add(custName);
                dataMap.put(buyCount, names);
            }
        });
        List<Map<String, Integer>> data = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : dataMap.entrySet()) {
            Map<String, Integer> map = new HashMap<>();
            map.put("ordRate", entry.getKey());
            map.put("custCount", entry.getValue().size());
            data.add(map);
        }
        return data;
    }

    @Override
    public Map<String, Integer> selectCustInqSummaryData(Map<String, String> params) {

        //查询会员询单总览数据firstInqCount,seniorCount
        Map<String, Integer> data = readMapper.selectCustInqSummaryData(params);
        //查询询单人数和询单次数 inqCount inqCustCount
        InquiryCountMapper inqMapper = readerSession.getMapper(InquiryCountMapper.class);
        Map<String, Object> inqAndOrdData = inqMapper.selectInqAndOrdCountAndPassengers(params);
        data.put("inqTimes",Integer.parseInt(inqAndOrdData.get("inqCount").toString()));
        data.put("custCount",Integer.parseInt(inqAndOrdData.get("inqCustCount").toString()));
        return data;
    }

    @Override
    public List<Map<String, Object>> selectCustInqDataGroupByArea(Map<String, String> params) {
        return readMapper.selectCustInqDataGroupByArea(params);
    }

    @Override
    public Map<String, Object> selectInqCustRegistTimeDetail(Map<String, String> params) {
        //查询各个时间段内的询单人数量 totalCount,oneMothCount,threeMothCount ,moreThanThreeMothCount
        Map<String, Object> data = readMapper.selectInqCustRegistTimeSummary(params);
        int totalCount = Integer.parseInt(data.get("totalCount").toString());//总询单人数量
        int oneMothCount = Integer.parseInt(data.get("oneMothCount").toString());//一个月内询单人数量
        int threeMothCount = Integer.parseInt(data.get("threeMothCount").toString());//1-3个月内询单人数量
        int moreThanThreeMothCount = Integer.parseInt(data.get("moreThanThreeMothCount").toString());//三个月以上询单人数量
        double oneProportion=0.00,threeProportion=0.00,moreThanThreePro=0.00;
        //饼图数据
        List<String> regisTime=new ArrayList<>();
        List<Integer> custCount=new ArrayList<>();
        regisTime.add("一个月内");
        regisTime.add("1-3个月内");
        regisTime.add("3个月以上");
        custCount.add(oneMothCount);
        custCount.add(threeMothCount);
        custCount.add(moreThanThreeMothCount);
        Map<String,Object> regisTimePie=new HashMap<>();
        regisTimePie.put("regisTime",regisTime);
        regisTimePie.put("custCount",custCount);
        //封装表格数据
        if(totalCount>0){
           oneProportion=RateUtil.intChainRate(oneMothCount,totalCount);
           threeProportion=RateUtil.intChainRate(threeMothCount,totalCount);
           moreThanThreePro=RateUtil.intChainRate(moreThanThreeMothCount,totalCount);
       }
       List<Map<String,Object>> regisTimeTable=new ArrayList<>();
       Map<String,Object> one =new HashMap<>();
       Map<String,Object> three =new HashMap<>();
       Map<String,Object> moreThree =new HashMap<>();
       one.put("regisTime","一个月内");
       one.put("custCount",oneMothCount);
       one.put("proportion",oneProportion);
        three.put("regisTime","1-3个月内");
        three.put("custCount",threeMothCount);
        three.put("proportion",threeProportion);
        moreThree.put("regisTime","3个月以上");
        moreThree.put("custCount",moreThanThreeMothCount);
        moreThree.put("proportion",moreThanThreePro);
        regisTimeTable.add(one);
        regisTimeTable.add(three);
        regisTimeTable.add(moreThree);

        Map<String,Object> result=new HashMap<>();
        result.put("regisTimeTable",regisTimeTable);
        result.put("regisTimePie",regisTimePie);
        return result;
    }

    @Override
    public Map<String, Object> selectCustOrdSummaryData(Map<String, String> params) {

        return readMapper.selectCustOrdSummaryData(params);
    }

    @Override
    public List<Map<String, Object>> selectCustOrdDataGroupByArea(Map<String, String> params) {
        return readMapper.selectCustOrdDataGroupByArea(params);
    }

}