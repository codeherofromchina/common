package com.erui.report.service.impl;

import com.erui.report.dao.WeeklyReportMapper;
import com.erui.report.service.WeeklyReportService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public  class WeeklyReportServiceImpl  extends BaseService<WeeklyReportMapper> implements WeeklyReportService{

    private  static final  String[] areas=new String[]{"北美","泛俄","非洲","南美","欧洲","亚太","中东","中国"};

    @Override
    public  Map<String, Object> selectBuyerRegistCountGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(areas));
        //获取本周各地区的注册数 中国算是一个独立的地区
       List<Map<String,Object>> thisWeekList= readMapper.selectRegisterCountGroupByAreaAndChina(params);
        Map<String, Map<String, Object>> thisWeekMap = new HashMap<>();
        Integer totalCount1 = thisWeekList.stream().map(m ->{
            String area = m.get("area").toString();
            thisWeekMap.put(area,m);
            return Integer.parseInt(m.get("registerCount").toString());
        }).reduce(0, (a, b) -> a + b);
        //获取上周各地区的注册数 中国算是一个独立的地区
        Object startTime = params.get("startTime");
        Object chainStartTime = params.get("chainStartTime");
        params.put("startTime",chainStartTime);
        params.put("endTime",startTime);
        List<Map<String,Object>> lastWeekList= readMapper.selectRegisterCountGroupByAreaAndChina(params);
        Map<String, Map<String, Object>> lastWeekMap =new HashMap<>();
        Integer totalCount2= lastWeekList.stream().map(m -> {
            String area = m.get("area").toString();
            lastWeekMap.put(area,m);
           return Integer.parseInt(m.get("registerCount").toString());
        }) .reduce(0, (a, b) -> a + b);

        List<Integer> thisWeekCounts=new ArrayList<>();//存放本周各地区新注册数量
        List<Integer> lastWeekCounts=new ArrayList<>();//存放上周各地区新注册数量
        for(String area:areaList){
            if(thisWeekMap.containsKey(area)){
                Map<String, Object> map = thisWeekMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                thisWeekCounts.add(registerCount);
            }else {
                thisWeekCounts.add(0);
            }
            if(lastWeekMap.containsKey(area)){
                Map<String, Object> map = lastWeekMap.get(area);
                int registerCount = Integer.parseInt(map.get("registerCount").toString());
                lastWeekCounts.add(registerCount);
            }else {
                lastWeekCounts.add(0);
            }
        }
        //添加上合计数据
        areaList.add("合计");
        thisWeekCounts.add(totalCount1);
        lastWeekCounts.add(totalCount2);
        //返回结果
        Map<String,Object> result=new HashMap<>();
        result.put("areas",areaList);
        result.put("thisWeekCounts",thisWeekCounts);
        result.put("lastWeekCounts",lastWeekCounts);
        return result;
    }

    @Override
    public Map<String, Object> selectBuyerCountGroupByArea(Map<String, Object> params) {
        List<String> areaList = new ArrayList<>(Arrays.asList(areas));
        //获取本周各地区的会员数 中国算是一个独立的地区
        List<Map<String,Object>> thisWeekList= readMapper.selectRegisterCountGroupByAreaAndChina(params);
        return null;
    }
}
