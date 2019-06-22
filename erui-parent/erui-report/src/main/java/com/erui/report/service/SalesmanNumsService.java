package com.erui.report.service;

import com.erui.report.model.SalesmanNums;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
public interface SalesmanNumsService {
    /**
     * 添加销售人员数据
     * @param salesmanNumsList
     * @return 0：成功  1：失败  2：数据重复
     */
    int add(List<SalesmanNums> salesmanNumsList) throws Exception;

    /**
     * 删除销售人员数据
     * @param ids
     * @return  0：成功  1：失败
     */
    int del(List<Integer> ids);

    /**
     * 更新销售人员数据
     * @param salesmanNums
     * @return 0：成功  1：失败  2：数据重复 3：指标不存在
     */
    int update(SalesmanNums salesmanNums);

    /**
     * 根据条件分页查询销售人员数据
     * @param params
     * @return
     */
    PageInfo<SalesmanNums> list(Map<String, Object> params);


    /**
     * 按国家查找给定时间段内的销售人员总数量
     * @param params
     *        {startTime:'',endTime:''}
     * @return
     *  {countryName:{salesManNum:n,dayNum:x}}
     */
    @Deprecated
    Map<String, Map<String,Object>> manTotalNumByCountry(Map<String,Object> params);

    /**
     * 按地区查找给定时间段内的销售人员总数量
     * @param params
     *        {startTime:'',endTime:''}
     * @return
     *  {areaName:{salesManNum:n,dayNum:x}}
     */
    @Deprecated
    Map<String, Map<String,Object>> manTotalNumByArea(Map<String, Object> params);
}
