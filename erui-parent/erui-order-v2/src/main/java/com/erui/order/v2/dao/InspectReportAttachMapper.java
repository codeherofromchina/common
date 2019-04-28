package com.erui.order.v2.dao;

import com.erui.order.v2.model.InspectReportAttachExample;
import com.erui.order.v2.model.InspectReportAttachKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InspectReportAttachMapper {
    int countByExample(InspectReportAttachExample example);

    int deleteByExample(InspectReportAttachExample example);

    int deleteByPrimaryKey(InspectReportAttachKey key);

    int insert(InspectReportAttachKey record);

    int insertSelective(InspectReportAttachKey record);

    List<InspectReportAttachKey> selectByExample(InspectReportAttachExample example);

    int updateByExampleSelective(@Param("record") InspectReportAttachKey record, @Param("example") InspectReportAttachExample example);

    int updateByExample(@Param("record") InspectReportAttachKey record, @Param("example") InspectReportAttachExample example);
}