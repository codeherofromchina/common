package com.erui.order.v2.dao;

import com.erui.order.v2.model.Backlog;
import com.erui.order.v2.model.BacklogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BacklogMapper {
    int countByExample(BacklogExample example);

    int deleteByExample(BacklogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Backlog record);

    int insertSelective(Backlog record);

    List<Backlog> selectByExample(BacklogExample example);

    Backlog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Backlog record, @Param("example") BacklogExample example);

    int updateByExample(@Param("record") Backlog record, @Param("example") BacklogExample example);

    int updateByPrimaryKeySelective(Backlog record);

    int updateByPrimaryKey(Backlog record);
}