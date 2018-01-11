package com.erui.report.dao;

import com.erui.report.model.InqRtnReason;
import com.erui.report.model.InqRtnReasonExample;
import java.util.List;
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
}