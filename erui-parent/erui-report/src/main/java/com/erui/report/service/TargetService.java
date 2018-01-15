package com.erui.report.service;
/*
* 年度指标 接口
* */
public interface TargetService {
    Double selectTargetAmountByCondition(Integer leastMoth,Integer maxMoth,String area,String org);

}
