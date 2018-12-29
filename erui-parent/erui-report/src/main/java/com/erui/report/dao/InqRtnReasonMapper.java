package com.erui.report.dao;

import com.erui.report.model.InqRtnReason;
import com.erui.report.model.InqRtnReasonExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface InqRtnReasonMapper {
    int countByExample(InqRtnReasonExample example);

    int deleteByExample(InqRtnReasonExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InqRtnReason record);

    int insertSelective(InqRtnReason record);

    List<InqRtnReason> selectByExample(InqRtnReasonExample example);

    InqRtnReason selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InqRtnReason record, @Param("example") InqRtnReasonExample example);

    int updateByExample(@Param("record") InqRtnReason record, @Param("example") InqRtnReasonExample example);

    int updateByPrimaryKeySelective(InqRtnReason record);

    int updateByPrimaryKey(InqRtnReason record);

    /*
     * 查询退回原因明细
     * */
    Map<String, Object> selectCountGroupByRtnSeason(Map<String,Object> params);

    /*
     * 查询各地区的退回原因明细
     * */
    List<Map<String, Object>> selectCountGroupByRtnSeasonAndArea(Map<String,Object> params);
    /*
     * 查询事业部退回原因明细
     * */
    List<Map<String, Object>> selectCountGroupByRtnSeasonAndOrg(Map<String,Object> params);
    /*
     * 批量插入数据
     * */
    void insertRtnReasons(List<InqRtnReason> reasons);
}