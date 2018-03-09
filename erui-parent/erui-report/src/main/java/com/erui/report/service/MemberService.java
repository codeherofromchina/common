package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.Member;
import com.erui.report.model.MemberExample;
import com.erui.report.util.ImportDataResponse;

public interface MemberService {
	/**
	 * 导入运营数据
	 * @param datas
	 * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
	 /**
	  * @Author:SHIGS
	  * @Description:
	  * @Date:15:49 2017/10/20
	  * @modified By
	  */
	public int selectByTime(Date startTime,Date endDate);
	 /**
	  * @Author:SHIGS
	  * @Description 分别查询各级会员数量
	  * @Date:17:59 2017/10/20
	  * @modified By
	  */
	public Map<String,Object> selectMemberByTime();
	/**
	 * @Author:lirb
	 * @Description 查询会员数据汇总 门户，后台，普通，高级
	 * @Date:17:59 2018/03/06
	 * @modified By
	 */
	Map<String,Object> selectOperateSummaryData(Map<String,String> params);
	/**
	 * @Author:lirb
	 * @Description 运营数据趋势图
	 * @Date:17:59 2018/03/06
	 * @modified By
	 */
	Map<String,Object> selectOperateTrend(Date startTime,Date endTime );
	/**
	 * @Author:lirb
	 * @Description 查询注册详情汇总数据： 注册人数、注册人数询单量、注册人数订单量、高级会员量
	 * @Date:17:59 2018/03/08
	 * @modified By
	 */
	Map<String,Integer> selectRegisterSummaryData(Map<String,String> params);
	/**
	 * @Author:lirb
	 * @Description 查询各区域的注册数量
	 * @Date:17:59 2018/03/08
	 * @modified By
	 */
	Map<String, Object> selectRegisterCountGroupByArea(Map<String,String> params);
	/**
	 * @Author:lirb
	 * @Description 查询询单频率数据
	 * @Date:17:59 2018/03/08
	 * @modified By
	 */
	List<Map<String, Integer>> selectInqFrequencyData(Map<String,String> params);
	/**
	 * @Author:lirb
	 * @Description 查询交易频率数据
	 * @Date:17:59 2018/03/08
	 * @modified By
	 */
	List<Map<String, Integer>> selectOrdFrequencyData(Map<String,String> params);
}