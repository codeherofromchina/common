package com.erui.order.v2.dao;

import com.erui.order.v2.model.InspectReportAttach;
import com.erui.order.v2.model.InspectReportAttachExample;
import com.erui.order.v2.model.InspectReportAttachKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InspectReportAttachMapper {
    int countByExample(InspectReportAttachExample example);

    int deleteByExample(InspectReportAttachExample example);

    int deleteByPrimaryKey(InspectReportAttachKey key);

    int insert(InspectReportAttach record);

    int insertSelective(InspectReportAttach record);

    List<InspectReportAttach> selectByExample(InspectReportAttachExample example);

    InspectReportAttach selectByPrimaryKey(InspectReportAttachKey key);

    int updateByExampleSelective(@Param("record") InspectReportAttach record, @Param("example") InspectReportAttachExample example);

    int updateByExample(@Param("record") InspectReportAttach record, @Param("example") InspectReportAttachExample example);

    int updateByPrimaryKeySelective(InspectReportAttach record);

    int updateByPrimaryKey(InspectReportAttach record);
}