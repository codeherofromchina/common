package com.erui.order.v2.dao;

import com.erui.order.v2.model.ProjectProfit;
import com.erui.order.v2.model.ProjectProfitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectProfitMapper {
    int countByExample(ProjectProfitExample example);

    int deleteByExample(ProjectProfitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectProfit record);

    int insertSelective(ProjectProfit record);

    List<ProjectProfit> selectByExample(ProjectProfitExample example);

    ProjectProfit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectProfit record, @Param("example") ProjectProfitExample example);

    int updateByExample(@Param("record") ProjectProfit record, @Param("example") ProjectProfitExample example);

    int updateByPrimaryKeySelective(ProjectProfit record);

    int updateByPrimaryKey(ProjectProfit record);
}