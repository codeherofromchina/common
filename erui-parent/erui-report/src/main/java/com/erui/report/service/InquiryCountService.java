package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquiryCount;
import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.InquiryAreaVO;
import com.erui.report.util.CustomerCategoryNumVO;
import com.erui.report.util.CustomerNumSummaryVO;

/*
* 询单统计
* */
public interface InquiryCountService {

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
	 * @param flag	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/*
	* 根据油气类别查询产品数量
	* */
	public int selectProCountByIsOil(Date startTime,Date endTime,String isOil);


	/*
	* 查询产品Top3
	* */
	public List<Map<String,Object>> selectProTop3(Map<String,Object>params);


	public List<CateDetailVo> selectInqDetailByCategory();


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
	public CustomerNumSummaryVO numSummary(String area,String country);

	/**
	 * 询订单分类 TOP N
	 * @param topN
	 * @param platCategory	指定分类
	 * @return
	 */
	public List<CustomerCategoryNumVO> inquiryOrderCategoryTopNum(Integer topN,String ...platCategory);

	CustomerNumSummaryVO selectNumSummaryByExample(Date startTime,Date endTime);

}