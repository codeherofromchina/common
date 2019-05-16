package com.erui.order.v2.dao;

import com.erui.order.v2.model.ProjectAttach;
import com.erui.order.v2.model.ProjectAttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectAttachMapper {
    int countByExample(ProjectAttachExample example);

    int deleteByExample(ProjectAttachExample example);

    int insert(ProjectAttach record);

    int insertSelective(ProjectAttach record);

    List<ProjectAttach> selectByExample(ProjectAttachExample example);

    int updateByExampleSelective(@Param("record") ProjectAttach record, @Param("example") ProjectAttachExample example);

    int updateByExample(@Param("record") ProjectAttach record, @Param("example") ProjectAttachExample example);
}