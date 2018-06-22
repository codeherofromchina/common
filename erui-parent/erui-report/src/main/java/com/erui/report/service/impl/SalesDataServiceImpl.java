package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.SalesDataMapper;
import com.erui.report.service.SalesDataService;
import com.erui.report.util.AnalyzeTypeEnum;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesDataServiceImpl extends BaseService<SalesDataMapper> implements SalesDataService {


    @Override
    public Map<String, Object> selectInqQuoteTrendData(Map<String, Object> params){

        //虚拟一个标准的时间集合
        List<String> dates = new ArrayList<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime").toString(), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date endTime = DateUtil.parseString2DateNoException(params.get("endTime").toString(), DateUtil.FULL_FORMAT_STR2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }
        //查询趋势图相关数据
       List<Map<String,Object>> data= readMapper.selectInqQuoteTrendData(params);
        Map<String, Map<String, Object>> dataMap = data.stream().collect(Collectors.toMap(m -> m.get("datetime").toString(), m -> m));
        List<Integer> inqCounts = new ArrayList<>();
        List<Double> inqAmounts = new ArrayList<>();
        List<Integer> quoteCounts = new ArrayList<>();
        for (String date : dates) {
            if (dataMap.containsKey(date)) {
                inqCounts.add(Integer.parseInt(dataMap.get(date).get("inqCount").toString()));
                inqAmounts.add(Double.parseDouble(dataMap.get(date).get("inqAmount").toString()));
                quoteCounts.add(Integer.parseInt(dataMap.get(date).get("quoteCount").toString()));
            } else {
                inqCounts.add(0);
                inqAmounts.add(0d);
                quoteCounts.add(0);
            }
        }

        Map<String,Object> result=new HashMap<>();
        result.put("xAxis",dates);
        String analyzeType = params.get("analyzeType").toString();
        if(analyzeType.equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())){//询单数量
            result.put("yAxis",inqCounts);
        }else if(analyzeType.equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())){//询单金额
            result.put("yAxis",inqAmounts);
        }else if(analyzeType.equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())){//报价数量
            result.put("yAxis",quoteCounts);
        }else {
           return null;
        }
        return result;
    }
}
