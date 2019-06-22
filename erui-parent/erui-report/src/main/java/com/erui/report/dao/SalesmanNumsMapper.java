package com.erui.report.dao;

import com.erui.report.model.SalesmanNums;
import com.erui.report.model.SalesmanNumsExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SalesmanNumsMapper {
    int countByExample(SalesmanNumsExample example);

    int deleteByExample(SalesmanNumsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SalesmanNums record);

    int insertSelective(SalesmanNums record);

    List<SalesmanNums> selectByExample(SalesmanNumsExample example);

    SalesmanNums selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SalesmanNums record, @Param("example") SalesmanNumsExample example);

    int updateByExample(@Param("record") SalesmanNums record, @Param("example") SalesmanNumsExample example);

    int updateByPrimaryKeySelective(SalesmanNums record);

    int updateByPrimaryKey(SalesmanNums record);

    /**
     * 根据给定参数的开始时间和结束时间查询销售人员数量、国家、地区等信息
     * @param params
     * @return
     */
    @Deprecated
    List<Map<String,Object>> selectNumsWhereTime(Map<String, Object> params);
    
    /**
     * 按地区分组，查找给定时间段内的各个地区销售人员平均数量
     * @param params
     *        {startTime:'',endTime:''}
     *        日期格式要处理成 yyyy-MM-01；无论日期是几号，都处理成1号。 
     *        例如：页面选择日期段 从2019-05-20到2019-10-15，要将日期处理成从2019-05-01到2019-10-01 
     * @return
     *  [{name:地区名称,avgNum:销售人员平均人数}]
     */
    List<Map<String,Object>> avgManNumInMonthByArea(Map<String, Object> params);
    
    /**
     * 按国家分组，查找给定时间段内的各个国家销售人员平均数量
     * @param params
     *        {startTime:'',endTime:''}
     *        日期格式要处理成 yyyy-MM-01；无论日期是几号，都处理成1号。 
     *        例如：页面选择日期段 从2019-05-20到2019-10-15，要将日期处理成从2019-05-01到2019-10-01 
     * @return
     *  [{name:国家名称,avgNum:销售人员平均人数}]
     */
    List<Map<String,Object>> avgManNumInMonthByCountry(Map<String, Object> params);
    
    /**
     * 按事业部分组，查找给定时间段内的各个事业部销售人员平均数量
     * @param params
     *        {startTime:'',endTime:''}
     *        日期格式要处理成 yyyy-MM-01；无论日期是几号，都处理成1号。 
     *        例如：页面选择日期段 从2019-05-20到2019-10-15，要将日期处理成从2019-05-01到2019-10-01 
     * @return
     *  [{name:事业部名称,avgNum:销售人员平均人数}]
     */
    List<Map<String,Object>> avgManNumInMonthByOrg(Map<String, Object> params);
}