package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.ProcurementCount;
import com.erui.report.model.ProcurementCountExample;
import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.InquiryAreaVO;

/**
 * 采购业务接口
 * @author wangxiaodan
 *
 */
public interface ProcurementCountService {

	/**
	 * 查询 起始时间
	 * @return Date
	 */
	Date selectStart();
	/**
	 * 查询 结束时间
	 * @return  Date
	 */
	Date selectEnd();
	/**
	 * 查询 大区列表
	 * @return  map
	 */
	Map<String, Object> selectArea();
	/**
	 * 根据大区查询国家
	 * @param area
	 */
	Map<String, Object> selectCountry(String area);
	/**
	 * 导入外部数据到采购信息中
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly);

	/**
	 * 获取采购总览数据
	 * @param startTime
	 * @param endTime
	 */
	List<Map<String,Object>> selectProcurPandent(Date startTime,Date endTime);
	/**
	 * 获取采购趋势图
	 * @param startTime
	 * @param endTime
	 * @param queryType
	 */
	Map<String,Object> procurementTrend(Date startTime,Date endTime,String queryType);

	/**
	 * 查询区域列表和国家列表
	 */
	List<InquiryAreaVO> selectAllAreaAndCountryList();
    /**
     * 获取采购明细数据
     * @param startTime
     * @param endTime
     * @param area
     * @param country
     */
    Map<String ,Object>  selectAreaDetailData(Date startTime,Date endTime,String area,Object country);
    /**
     * 获取分类数据
     * @param startTime
     * @param endTime
     */
    List<ProcurementCount>  selectCategoryDetail(Date startTime, Date endTime);
}