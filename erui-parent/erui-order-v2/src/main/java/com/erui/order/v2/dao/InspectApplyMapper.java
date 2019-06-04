package com.erui.order.v2.dao;

import com.erui.order.v2.model.InspectApply;
import com.erui.order.v2.model.InspectApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InspectApplyMapper {
    int countByExample(InspectApplyExample example);

    int deleteByExample(InspectApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InspectApply record);

    int insertSelective(InspectApply record);

    List<InspectApply> selectByExampleWithBLOBs(InspectApplyExample example);

    List<InspectApply> selectByExample(InspectApplyExample example);

    InspectApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InspectApply record, @Param("example") InspectApplyExample example);

    int updateByExampleWithBLOBs(@Param("record") InspectApply record, @Param("example") InspectApplyExample example);

    int updateByExample(@Param("record") InspectApply record, @Param("example") InspectApplyExample example);

    int updateByPrimaryKeySelective(InspectApply record);

    int updateByPrimaryKeyWithBLOBs(InspectApply record);

    int updateByPrimaryKey(InspectApply record);
}