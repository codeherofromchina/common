package com.erui.report.service;

import java.util.List;
import java.util.Map;

/**
 * 品类报表业务管理
 */
public interface CategoryService {

	/**
	 * 查询品类的询单数量
	 * @param areaBn	大区编码
	 * @param countryBn	国家编码
	 * @param tradeTerms	贸易术语
	 * @param startTime	开始时间 格式为：yyyy-MM-dd HH:mm:ss
	 * @param endTime	结束时间 格式为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	Map<String, List<Object>> selectCategoryInquiryNumByAreaAndCountryAndTradeTerms(String areaBn, String countryBn, String tradeTerms, String startTime, String endTime);

	/**
	 * 查询品类的报价数量
	 * @param areaBn	大区编码
	 * @param countryBn	国家编码
	 * @param tradeTerms	贸易术语
	 * @param startTime	开始时间 格式为：yyyy-MM-dd HH:mm:ss
	 * @param endTime	结束时间 格式为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	Map<String, List<Object>> selectCategoryQuoteNumByAreaAndCountryAndTradeTerms(String areaBn, String countryBn, String tradeTerms, String startTime, String endTime);

	/**
	 * 查询品类的报价金额
	 * @param areaBn	大区编码
	 * @param countryBn	国家编码
	 * @param tradeTerms	贸易术语
	 * @param startTime	开始时间 格式为：yyyy-MM-dd HH:mm:ss
	 * @param endTime	结束时间 格式为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	Map<String, List<Object>> selectCategoryQuoteAmountByAreaAndCountryAndTradeTerms(String areaBn, String countryBn, String tradeTerms, String startTime, String endTime);
}