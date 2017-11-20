package com.erui.report.dao;

import com.erui.report.model.HrCount;
import com.erui.report.model.HrCountExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HrCountMapper {
    Date selectStart();

    Date selectEnd();

    List<Map> selectDepartmentCountByExample(HrCountExample example);

    List<Map> selectBigDepartCountByExample(HrCountExample example);

    List<Map> selectBigDepart();

    /**
     * 人力统计数据
     *
     * @param hrCountExample
     * @return {"s1":"计划人数","s2":"在编人数","s3":"试用期人数","s4":"转正人数","s5":"中方人数",
     * "s6":"外籍人数","s7":"新进人数","s8":"离职人数","s9":"集团转进","s10":"集团转出",
     * "staffFullRate":"在编/计划--满编率","tryRate":"试用/在编--试用占比","addRate":"(新进/在编-离职/在编) -- 增长率",
     * "leaveRate":"(转刚出/在编-转岗进/在编)--转岗流失","foreignRate":"外籍/在编--外籍占比"}
     */
    Map selectHrCountByPart(HrCountExample hrCountExample);

    int countByExample(HrCountExample example);

    int deleteByExample(HrCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HrCount record);

    int insertSelective(HrCount record);

    List<HrCount> selectByExample(HrCountExample example);

    HrCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HrCount record, @Param("example") HrCountExample example);

    int updateByExample(@Param("record") HrCount record, @Param("example") HrCountExample example);

    int updateByPrimaryKeySelective(HrCount record);

    int updateByPrimaryKey(HrCount record);

    void truncateTable();
}