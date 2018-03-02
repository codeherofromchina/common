package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.InquiryAreaVO;

public interface CreditExtensionService {
	/**
	 * 导入授信数据
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/**
	 * 授信数据总览
	 * @param  params
	 * @return
	 */
	Map<String,Object> creditPandect(Map<String,String> params);
	/**
	 * 授信数据趋势图
	 * @param startTime
	 * @param endTime
	 * @param creditType
	 * * @return
	 */
	Map<String,Object> creditTrend(Date startTime,Date endTime,String creditType);
	/**
	 * 大区和国家列表
	 * @return
	 */
	List<InquiryAreaVO> selectAllAreaAndCountryList();
	/**
	 * 区域明细
	 * @return
	 */
	Map<String,Object> areaDetail(Map<String,String> params);
}