package com.erui.report.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquiryCount;
import com.erui.report.util.*;

/*
* 询单统计
* */
public interface InquiryCountService {
	 /**
	  * @Author:SHIGS
	  * @Description
	  * @Date:16:57 2017/11/14
	  * @modified By
	  */
	Date selectStart();
	 /**
	  * @Author:SHIGS
	  * @Description
	  * @Date:16:57 2017/11/14
	  * @modified By
	  */
	Date selectEnd();

    /*
    * 查询询单单数
    * */

     int inquiryCountByTime(Date startTime,Date endTime,String[] quotedStatus,double leastQuoteTime,double maxQuoteTime,String org,String area);
    /*
    * 查询询单总金额
    * */
	 Double inquiryAmountByTime(Date startDate,Date endDate,String area,String country,String[] quotedStatus) ;


	/**
	 * 导入客户中心-询单数据
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	 ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/*
	* 根据油气类别查询产品数量
	* */
	int selectProCountByExample(Date startTime,Date endTime,String isOil,String proCategory);


	/*
	* 查询产品Top3
	* */
	List<Map<String,Object>> selectProTop3(Map<String,Object>params);
	 /**
	  * @Author:SHIGS
	  * @Description 查询产品Top3总数
	  * @Date:2:02 2017/11/2
	  * @modified By
	  */
	Map<String,Object> selectProTop3Total(Date startTime,Date endTime);

    List<CateDetailVo> selectInqDetailByCategory(Date startTime,Date endTime);


	/*
	* 查询事业部列表
	* */
	List<String>   selectOrgList();
	/*
   * 查询销售大区列表
   * */
	  List<String>   selectAreaList();
	/*
   * 根据时间查询询单列表
   * */
	List<InquiryCount> selectListByTime(Date startTime, Date endTime,String[] quotes,String area,String country);
	
	
	/**
     * 查询所有询单中的所有大区和城市列表（
     * @return
     */
     List<InquiryAreaVO> selectAllAreaAndCountryList() ;
	/**
     * 查询所有询单中的所有大区和城市列表（
     * @return
     */
     List<InquiryAreaVO> selectAllAreaAndOrgList() ;

	/**
	 * 获取询单数据汇总
	 * @param area
	 * @param country
	 * @return
	 */
	 CustomerNumSummaryVO numSummary(Date startTime,Date endTime,String area,String country);
	/**
	 * 获取询单报价用时分析数据
	 * @param params
	 * @return
	 */
	Map<String,Object> selectQuoteTimeSummaryData(Map<String,String> params);
	/**
	 * 询订单分类 TOP N
	 * @param topN
	 * @param platCategory	指定分类
	 * @return
	 */
	 List<CustomerCategoryNumVO> inquiryOrderCategoryTopNum(Integer topN, Date startTime, Date endTime, String... platCategory);

	CustomerNumSummaryVO selectNumSummaryByExample(Date startTime,Date endTime);

	/**
	 * 按照转入日期区间统计事业部的询单数量
	 * @param startDate
	 * @param endDate
	 * @return  {"total":'总询单数量--Long',"organization":'事业部--String'}
	 */
	List<Map<String,Object>> findCountByRangRollinTimeGroupOrigation(Date startDate, Date endDate,int rtnCount,String[] quotes);

	/**
	 * 询订单趋势图数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	InqOrdTrendVo inqOrdTrend( Date startTime, Date endTime);

	/**
	 * 按照转入日期区间统计区域的询单数量和金额
	 * @param startTime
	 * @param endTime
	 * @return {"totalAmount":'金额--BigDecimal',"total":'总询单数量--Long',"area":'区域--String'}
	 */
    List<Map<String,Object>> findCountAndPriceByRangRollinTimeGroupArea(Date startTime, Date endTime,int rtnCount,String[] quotes);

	/**
	 * 按照转入日期区间统计事业部的平均报价时间
	 * @param startDate
	 * @param endDate
	 * @return  {"avgNeedTime":'平均响应时间--BigDecimal',"organization":'事业部--String'}
	 */
	List<Map<String,Object>> findAvgNeedTimeByRollinTimeGroupOrigation(Date startDate, Date endDate);
	/**
	 * 从boss读取询单数据
	 * @param list
	 */
	void inquiryData( List<HashMap> list)throws  Exception;
	/**
	 * 获取询单退回的次数和平均次数
	 * @param startTime
	 * @param endTime
	 */
	List<Map<String,Object>> selectRejectCount(Date startTime, Date endTime);
	/**
	 * 获取退回询单数
	 * @param startTime
	 * @param endTime
	 */
	int selectInqRtnCountByTime(Date startTime, Date endTime);
	/**
	 * 查询询单、交易的人数和单数
	 * @param params
	 */
	Map<String,Object> selectInqAndOrdCountAndPassengers(Map<String,String> params);
	/**
	 * 查询询单、交易的人数和单数
	 * @param org
	 */
	 String getStandardOrg(String org);
}