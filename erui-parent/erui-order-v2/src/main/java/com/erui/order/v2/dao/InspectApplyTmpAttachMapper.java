package com.erui.order.v2.dao;

import com.erui.order.v2.model.InspectApplyTmpAttach;
import com.erui.order.v2.model.InspectApplyTmpAttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InspectApplyTmpAttachMapper {
    int countByExample(InspectApplyTmpAttachExample example);

    int deleteByExample(InspectApplyTmpAttachExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InspectApplyTmpAttach record);

    int insertSelective(InspectApplyTmpAttach record);

    List<InspectApplyTmpAttach> selectByExample(InspectApplyTmpAttachExample example);

    InspectApplyTmpAttach selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InspectApplyTmpAttach record, @Param("example") InspectApplyTmpAttachExample example);

    int updateByExample(@Param("record") InspectApplyTmpAttach record, @Param("example") InspectApplyTmpAttachExample example);

    int updateByPrimaryKeySelective(InspectApplyTmpAttach record);

    int updateByPrimaryKey(InspectApplyTmpAttach record);
}