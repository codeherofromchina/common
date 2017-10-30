package com.erui.report.dao;

import com.erui.report.model.MarketerCount;
import com.erui.report.model.MarketerCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketerCountMapper {
    int countByExample(MarketerCountExample example);

    int deleteByExample(MarketerCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MarketerCount record);

    int insertSelective(MarketerCount record);

    List<MarketerCount> selectByExample(MarketerCountExample example);

    MarketerCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MarketerCount record, @Param("example") MarketerCountExample example);

    int updateByExample(@Param("record") MarketerCount record, @Param("example") MarketerCountExample example);

    int updateByPrimaryKeySelective(MarketerCount record);

    int updateByPrimaryKey(MarketerCount record);
    
    void truncateTable();
}