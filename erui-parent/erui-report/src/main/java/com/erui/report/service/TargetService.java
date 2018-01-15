package com.erui.report.service;

import java.util.List;
import java.util.Map;

/*
* 年度指标 接口
* */
public interface TargetService {
    //按条件查询指标
    Double selectTargetAmountByCondition(Integer leastMoth,Integer maxMoth,String area,String org);
    //查询各事业部的年度指标
    List<Map<String,Object>> selectTargetGroupByOrg();
    //查询各地区的年度指标
    List<Map<String,Object>> selectTargetGroupByArea();

}
