package com.erui.report.dao;

import com.erui.report.model.PerformanceAssign;

import java.util.List;
import java.util.Map;

public interface PerformanceAssignMapper {


    int deleteByPrimaryKey(Long id);

    int insert(PerformanceAssign record);

    int insertSelective(PerformanceAssign record);

    PerformanceAssign selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PerformanceAssign record);

    /**
     * 批量更新业绩分配数据
     * @param record
     */
    void updatePerformanceAssign(PerformanceAssign record);

    /**
     * 根据月份查询国家业绩分配明细
     * @param params
     * @return
     */
    List<PerformanceAssign> selectCountryAssignDetailByTime(Map<String, String> params);

    /**
     * 查询指定国家正在审核的业绩分配数据
     * @param params
     * @return
     */
    List<PerformanceAssign> selectAuditingPerformanceByCountry(Map<String, String> params);

    /**
     * 审核 指定国家的业绩分配： 通过 、驳回
     * @param params
     */
    void auditPerformance(Map<String, String> params);

}