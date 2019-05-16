package com.erui.order.v2.dao;

import com.erui.order.v2.model.InspectReport;
import com.erui.order.v2.model.InspectReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InspectReportMapper {
    int countByExample(InspectReportExample example);

    int deleteByExample(InspectReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InspectReport record);

    int insertSelective(InspectReport record);

    List<InspectReport> selectByExample(InspectReportExample example);

    InspectReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InspectReport record, @Param("example") InspectReportExample example);

    int updateByExample(@Param("record") InspectReport record, @Param("example") InspectReportExample example);

    int updateByPrimaryKeySelective(InspectReport record);

    int updateByPrimaryKey(InspectReport record);
}