package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.CateDetailVo;
import com.erui.report.model.OrderCount;
import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.CustomerNumSummaryVO;

public interface OrderCountService {
	/**
	 * 导入客户中心-订单数据
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/*
	* 订单数
	* */
	public int orderCountByTime(Date startTime,Date endTime,String projectStatus,String org,String area);
	/*
	* 订单金额
	* */
	public Double orderAmountByTime(Date startTime,Date endTime,String area);
	/*
	* 订单产品类别数量Top3
	* */
	public  List<Map<String,Object>> selectOrderProTop3(Map<String,Object> params);

	public Double selectProfitRate(Date startTime,Date endTime);
	/*
     * 根据时间查询订单列表
     * */
	List<OrderCount> selectListByTime(Date startTime, Date endTime);

	/**
	 * 获取数据汇总
	 * @param area
	 * @param country
	 * @return
	 */
	public CustomerNumSummaryVO numSummary(Date startTime, Date endTime,String area,String country);
	//订单品类明细
	List<CateDetailVo> selecOrdDetailByCategory(Date startTime, Date endTime);

	int  selectProCountByExample();

	/**
	 * 按照项目开始区间统计事业部的订单数量和金额
	 *
	 * @param startDate
	 * @param endDate
	 * @return  {"totalAmount":'总订单金额',"totalNum":'总订单数量',"organization":'事业部'}
	 */
    List<Map<String,Object>> findCountAndAmountByRangProjectStartGroupOrigation(Date startDate, Date endDate);
}