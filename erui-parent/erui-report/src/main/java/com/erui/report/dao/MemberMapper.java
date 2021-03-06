package com.erui.report.dao;

import com.erui.report.model.Member;
import com.erui.report.model.MemberExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    int countByExample(MemberExample example);

    int deleteByExample(MemberExample example);

    int deleteByPrimaryKey(Long id);
    int selectByTime(MemberExample example);
    Map selectMemberByTime();
    int insert(Member record);

    int insertSelective(Member record);

    List<Member> selectByExample(MemberExample example);

    Member selectByPrimaryKey(Long id);
    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
    
    void truncateTable();

    //查询会员数据汇总  门户、前台、普通会员和高级会员
    Map<String, Object> selectOperateSummaryData(Map<String, Object> params);
    //查询趋势图数据
    List<Map<String, Object>> selectOperateTrend(Map<String, String> params);
    //查询注册人数询单量和注册人数订单量
    Map<String,Integer> selectRegisterInqAndOrdCount(Map<String, Object> params);
    //查询各区域的注册数量
    List<Map<String, Object>> selectRegisterCountGroupByArea(Map<String, String> params);
    //查询会员交易频率明细
    List<Map<String, Object>>  selectCustOrdRateDetail(Map<String, String> params);
    //查询会员询单频率明细
    List<Map<String, Object>>  selectCustInqRateDetail(Map<String, String> params);
    //查询会员询单汇总数据
    Map<String,Integer> selectCustInqSummaryData(Map<String, Object> params);
    //查询各区域的会员的询单数据
    List<Map<String, Object>> selectCustInqDataGroupByArea(Map<String, String> params);
    //查询各区域的客户编码为null的询单数据
    List<Map<String, Object>> selectCustIsNullInqDataGroupByArea(Map<String, String> params);
    //查询各时间段内询单人数量
    Map<String,Object> selectInqCustRegistTimeSummary(Map<String,String> params);
    //查询会员订单汇总数据
    Map<String,Object> selectCustOrdSummaryData(Map<String, String> params);
    //查询各区域的会员的询单数据
    List<Map<String, Object>> selectCustOrdDataGroupByArea(Map<String, String> params);
    //查询各区域客户编码为null的询单数据
    List<Map<String, Object>> selectCustIsNullOrdDataGroupByArea(Map<String, String> params);
}