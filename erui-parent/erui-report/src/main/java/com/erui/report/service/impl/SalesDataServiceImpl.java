package com.erui.report.service.impl;

import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.dao.SalesDataMapper;
import com.erui.report.service.SalesDataService;
import com.erui.report.util.AnalyzeTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
                inqAmounts.add(RateUtil.doubleChainRateTwo(Double.parseDouble(dataMap.get(date).get("inqAmount").toString()),10000));
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

    @Override
    public Map<String, Object> selectAreaDetailByType(Map<String, Object> params) {

        //查询各大区和国家的数据明细
        List<Map<String,Object>> dataList= readMapper.selectAreaAndCountryDetail(params);

        if(CollectionUtils.isNotEmpty(dataList)) {
            String area = String.valueOf(params.get("area"));
            String country = String.valueOf(params.get("country"));
            Map<String, Map<String, Object>> dataMap = new HashMap<>();
            if (StringUtils.isEmpty(area) && StringUtils.isEmpty(country)) { //如果大区和国家都没有指定 显示各大区数据
                for(Map<String,Object> m :dataList){
                    String areaName = m.get("area").toString();
                    int inqCount = Integer.parseInt(m.get("inqCount").toString());
                    int quoteCount = Integer.parseInt(m.get("quoteCount").toString());
                    double inqAmount = Double.parseDouble(m.get("inqAmount").toString());
                    if(dataMap.containsKey(areaName)){
                        Map<String, Object> map = dataMap.get(areaName);
                        map.put("inqCount",Integer.parseInt(map.get("inqCount").toString())+inqCount);
                        map.put("quoteCount",Integer.parseInt(map.get("quoteCount").toString())+quoteCount);
                        map.put("inqAmount",Double.parseDouble(map.get("inqAmount").toString())+inqAmount);
                    }else{
                        dataMap.put(areaName,m);
                    }
                }
            }else if(StringUtils.isNotEmpty(area) && StringUtils.isEmpty(country)){//如果只指定了大区，显示该大区下所有国家的数据
                for(Map<String,Object> m :dataList){
                    String area1 = m.get("area").toString();
                    String country1 = m.get("country").toString();
                    if(area1.equals(area)){
                        dataMap.put(country1,m);
                    }
                }
            }else{//指定了大区和国家，显示指定国家的数据
                for(Map<String,Object> m :dataList){
                    String area1 = m.get("area").toString();
                    String country1 = m.get("country").toString();
                    if(area1.equals(area)&&country.equals(country1)){
                        dataMap.put(country1,m);
                    }
                }
            }
            //如果有数据
            if(MapUtils.isNotEmpty(dataMap)){
                Map<String,Object> result=new HashMap<>();
                List<Object> dList=new ArrayList<>();//各大区数据列表存放
                Set<String> keySet = dataMap.keySet();

                if(params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())){//分析类型为询单数量
                    for (String key:keySet  ) {
                        int inqCount = Integer.parseInt(dataMap.get(key).get("inqCount").toString());
                        dList.add(inqCount);
                    }
                }
                if(params.get("analyzeType").toString().equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())){//分析类型为询单金额
                    for (String key:keySet  ) {
                        Double inqAmount = Double.parseDouble(dataMap.get(key).get("inqAmount").toString());
                        dList.add(inqAmount);
                    }
                }
                if(params.get("analyzeType").toString().equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())){//分析类型为报价数量
                    for (String key:keySet  ) {
                        int quoteCount = Integer.parseInt(dataMap.get(key).get("quoteCount").toString());
                        dList.add(quoteCount);
                    }
                }

                result.put("areas",keySet);
                result.put("datas",dList);
                return result;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> selectOrgDetailByType(Map<String, Object> params) {
        //查询各事业部的相关数据
        List<Map<String,Object>> data =readMapper.selectOrgDetail(params);
        if(CollectionUtils.isNotEmpty(data)){
            Map<String,Object> result=new HashMap<>();
           List<String> orgList=new ArrayList<>();
           List<Integer> inqCountList=new ArrayList<>();
           List<Double> inqAmountList=new ArrayList<>();
           List<Integer> quoteCountList=new ArrayList<>();
           List<Double> quoteTimeList=new ArrayList<>();
           for(Map<String,Object> m:data){
               orgList.add(m.get("org").toString());
               inqCountList.add(Integer.parseInt(m.get("inqCount").toString()));
               inqAmountList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("inqAmount").toString()),1));
               quoteCountList.add(Integer.parseInt(m.get("quoteCount").toString()));
               quoteTimeList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("quoteTime").toString()),1));
           }
           //分析类型 ：默认 、询单数量、询单金额、报价数量、报价用时
            String analyzeType = params.get("analyzeType").toString();
           if(analyzeType.equals(AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName())){//如果分析类型为询单数量
               result.put("orgs",orgList);
               result.put("inqCountList",inqCountList);
           }else if(analyzeType.equals(AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName())){//分析类型为询单金额
               result.put("orgs",orgList);
               result.put("inqAmountList",inqAmountList);
           }else if(analyzeType.equals(AnalyzeTypeEnum.QUOTE_COUNT.getTypeName())){//分析类型为报价数量
               result.put("orgs",orgList);
               result.put("quoteCountList",quoteCountList);
           }else if(analyzeType.equals(AnalyzeTypeEnum.QUOTE_TIME_COST.getTypeName())){//分析类型为报价用时
               result.put("orgs",orgList);
               result.put("inqAmountList",quoteTimeList);
           }else{//默认的总览
               result.put("orgs",orgList);
               result.put("inqCountList",inqCountList);
               result.put("quoteCountList",quoteCountList);
               result.put("inqAmountList",inqAmountList);
           }

           return  result;
        }
        return null;
    }

    @Override
    public Map<String, Object> selectCategoryDetailByType(Map<String, Object> params) {
        //查询各分类数据的相关数据
       List<Map<String,Object>> dataList= readMapper.selectDataGroupByCategory(params);
        return null;
    }


}
