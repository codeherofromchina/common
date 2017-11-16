package com.erui.report.service;

import java.util.Date;
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
    public int inquiryCountByTime(Date startTime,Date endTime,String quotedStatus,double leastQuoteTime,double maxQuoteTime,String org,String area);

    /*
    * 查询询单总金额
    * */
	public Double inquiryAmountByTime(Date startDate,Date endDate,String area) ;

	/**
	 * 导入客户中心-询单数据
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/*
	* 根据油气类别查询产品数量
	* */
	public int selectProCountByExample(Date startTime,Date endTime,String isOil,String proCategory);


	/*
	* 查询产品Top3
	* */
	public List<Map<String,Object>> selectProTop3(Map<String,Object>params);
	
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
	 public  List<String>   selectOrgList();
	/*
   * 查询销售大区列表
   * */
	public  List<String>   selectAreaList();
	/*
   * 根据时间查询询单列表
   * */
	List<InquiryCount> selectListByTime(Date startTime, Date endTime);
	
	
	/**
     * 查询所有询单中的所有大区和城市列表（
     * @return
     */
    public List<InquiryAreaVO> selectAllAreaAndCountryList() ;

	/**
	 * 获取询单数据汇总
	 * @param area
	 * @param country
	 * @return
	 */
	public CustomerNumSummaryVO numSummary(Date startTime,Date endTime,String area,String country);

	/**
	 * 询订单分类 TOP N
	 * @param topN
	 * @param platCategory	指定分类
	 * @return
	 */
	public List<CustomerCategoryNumVO> inquiryOrderCategoryTopNum(Integer topN, Date startTime, Date endTime, String... platCategory);

	CustomerNumSummaryVO selectNumSummaryByExample(Date startTime,Date endTime);

	/**
	 * 按照转入日期区间统计事业部的询单数量和响应平均时间
	 * @param startDate
	 * @param endDate
	 * @return  {"avgNeedTime":'平均响应时间--BigDecimal',"total":'总询单数量--Long',"organization":'事业部--String'}
	 */
	List<Map<String,Object>> findCountAndAvgNeedTimeByRangRollinTimeGroupOrigation(Date startDate, Date endDate);

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
    List<Map<String,Object>> findCountAndPriceByRangRollinTimeGroupArea(Date startTime, Date endTime);
}