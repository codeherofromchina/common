package com.erui.report.service;

import com.erui.report.model.PerformanceIndicators;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
public interface PerformanceIndicatorsService {
    /**
     * 添加业绩指标
     * @param performanceIndicators
     * @return 0：成功  1：失败  2：数据重复
     */
    int add(PerformanceIndicators performanceIndicators);

    /**
     * 删除业绩指标
     * @param ids
     * @return  0：成功  1：失败
     */
    int del(List<Integer> ids);

    /**
     * 更新业绩指标
     * @param performanceIndicators
     * @return 0：成功  1：失败  2：数据重复 3：指标不存在
     */
    int update(PerformanceIndicators performanceIndicators);

    /**
     * 根据条件分页查询业绩指标
     * @param params
     * @return
     */
    PageInfo<PerformanceIndicators> list(Map<String, Object> params);

    /**
     * 查找时间段内的事业部业绩指标信息
     * @param params
     * @return
     */
    Map<Integer,Map<String,Object>> performanceIndicatorsWhereTimeByOrg(Map<String, Object> params);

    /**
     * 查找时间段内的地区业绩指标信息
     * @param params
     * @return
     */
    Map<String,Map<String,Object>> performanceIndicatorsWhereTimeByArea(Map<String, Object> params);


    /**
     * 查找时间段内的国家业绩指标信息
     * @param params
     * @return
     */
    Map<String,Map<String,Object>> performanceIndicatorsWhereTimeByCountry(Map<String, Object> params);
}
