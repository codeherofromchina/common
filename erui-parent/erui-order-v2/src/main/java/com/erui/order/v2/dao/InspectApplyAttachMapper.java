package com.erui.order.v2.dao;

import com.erui.order.v2.model.InspectApplyAttach;
import com.erui.order.v2.model.InspectApplyAttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InspectApplyAttachMapper {
    int countByExample(InspectApplyAttachExample example);

    int deleteByExample(InspectApplyAttachExample example);

    int insert(InspectApplyAttach record);

    int insertSelective(InspectApplyAttach record);

    List<InspectApplyAttach> selectByExample(InspectApplyAttachExample example);

    int updateByExampleSelective(@Param("record") InspectApplyAttach record, @Param("example") InspectApplyAttachExample example);

    int updateByExample(@Param("record") InspectApplyAttach record, @Param("example") InspectApplyAttachExample example);
}