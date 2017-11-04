package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.*;
import com.erui.report.util.ImportDataResponse;

public interface SupplyChainService {
	 /**
	  * @Author:SHIGS 查询spu sku 供应商完成量
	  * @Description
	  * @Date:15:58 2017/10/21
	  * @modified By
	  */
	 Map<String, Object> selectFinishByDate(int days,String type);
	/**
	 * 导入供应链数据到数据库
	 * @param datas
	 * @param testOnly true:只检测数据  false:插入正式库
	 * @return
	 */
	ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	/*
		根据时间查询列表
	*/
	List<SupplyChain> queryListByDate(Date startTime,Date endTime);
	/**
	 *
	 * 事业部供应链明细列表
	 * @return
	 */
	List<SuppliyChainOrgVo> selectOrgSuppliyChain(Date startTime,Date endTime);
	/**
	 *
	 * 品类供应链明细列表
	 * @return
	 */
	List<SuppliyChainItemClassVo> selectItemCalssSuppliyChain(Date startTime,Date endTime);
	/**
	 *
	 * 根据品类查询供应链明细
	 * @return
	 */
	SuppliyChainItemClassVo selectSuppliyChainByItemClass(Date startTime,Date endTime,String itemClass);
	/**
	 *
	 * 品类部供应链明细列表
	 * @return
	 */
	List<SuppliyChainCateVo> selectCateSuppliyChain(Date startTime,Date endTime);
	/**
	 *
	 * 供应链趋势图
	 * @param days
	 * @param type
	 * @return
	 */
	SupplyTrendVo supplyTrend(int days,int type);
	/**
	 *
	 * 品类部列表
	 * @return
	 */
	List<String>  selectCategoryList();
	/**
	 *
	 * 事业部列表
	 * @return
	 */
	List<String>  selectOrgList();


}