package com.erui.report.service;

import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.InquiryAreaVO;

public interface MarketerCountService {
	/**
	 * 导入市场人员数据
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/**
	 * 国家top总览
	 * @param params
	 * @return
	 */
	Map<String,Object> countryTopPandect(Map<String,String> params);
	/**
	 * 区域人员top总览
	 * @param params
	 * @return
	 */
	Map<String,Object> areaMarketerTopPandect(Map<String,String> params);
	/**
	 * 获取所有大区和大区国家列表
	 * @return
	 */
	List<InquiryAreaVO> selectAllAreaAndCountryList();
	/**
	 * 区域明细
	 * @param params
	 * @return
	 */
	Map<String,Object> areaDetail(Map<String,String> params);
}