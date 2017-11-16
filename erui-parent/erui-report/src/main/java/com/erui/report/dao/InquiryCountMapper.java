package com.erui.report.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquiryCount;
import com.erui.report.model.InquiryCountExample;
import com.erui.report.util.CustomerCategoryNumVO;
import com.erui.report.util.CustomerNumSummaryVO;

public interface InquiryCountMapper {
    Date selectStart();
    Date selectEnd();
    int countByExample(InquiryCountExample example);

    int deleteByExample(InquiryCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InquiryCount record);

    int insertSelective(InquiryCount record);

    List<InquiryCount> selectByExample(InquiryCountExample example);

    InquiryCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InquiryCount record, @Param("example") InquiryCountExample example);

    int updateByExample(@Param("record") InquiryCount record, @Param("example") InquiryCountExample example);

    int updateByPrimaryKeySelective(InquiryCount record);

    int updateByPrimaryKey(InquiryCount record);

    //查询总金额
    Double selectTotalAmountByExample(InquiryCountExample example);
    //查询油气类产品数量
    int     selectProCountByExample(InquiryCountExample example);
    //查询产品Top3
    List<Map<String,Object>> selectProTop3(Map<String,Object>params);
     /**
      * @Author:SHIGS
      * @Description 查询产品Top3
      * @Date:2:02 2017/11/2
      * @modified By
      */
    Map<String,Object> selectProTop3TotalByExample(InquiryCountExample example);
    //品类明细
    List<CateDetailVo> selectInqDetailByCategoryByExample(InquiryCountExample example);
    //查询事业部列表
    List<String>  selectOrgListByExample(InquiryCountExample example);
    //查询事业部列表
    List<String>  selectAreaListByExample(InquiryCountExample example);
    
    /**
     * 查询询单的所有大区和城市列表
     * @return	Map<String,String> -> {'area':'大区名称','country':'城市名称'}
     */
    List<Map<String,String>>  selectAllAreaAndCountryList();

    /**
     * 查询询单的数量汇总数据
     * @param example
     * @return
     */
	CustomerNumSummaryVO selectNumSummaryByExample(InquiryCountExample example);
	
	/**
	 * 根据分类查询询单和订单的数量汇总数据
	 * @param condition {limit:'返回条数',inquiryCountExample:'询单example条件',orderCountExample:'订单Example条件'}
	 * @return
	 */
	List<CustomerCategoryNumVO> selectinquiryOrderCategoryNumByCondition(Map<String,Object> condition);
	
	void truncateTable();


    /**
     * 统计事业部的询单数量和响应平均时间
     * @param example
     * @return  {"avgNeedTime":'平均响应时间',"total":'总询单数量',"organization":'事业部'}
     */
    List<Map<String,Object>> findCountAndAvgNeedTimeByExampleGroupOrigation(InquiryCountExample example);
    /**
     * 询单趋势图
     * @param example
     * @return
     */
    List<Map<String,Object>> inqTrendByTime(InquiryCountExample example);
}