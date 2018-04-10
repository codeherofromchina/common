package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;

import com.erui.report.util.InquiryAreaVO;

public interface RequestCreditService {
	/**
	 * @Author:SHIGS
	 * @Description
	 * @Date:17:11 2017/11/14
	 * @modified By
	 */
	Date selectStart();

	/**
	 * @Author:SHIGS
	 * @Description
	 * @Date:17:11 2017/11/14
	 * @modified By
	 */
	Date selectEnd();

	/**
	 * @Author:SHIGS
	 * @Description 应收账款累计
	 * @Date:17:03 2017/10/23
	 * @modified By
	 */
	Map<String, Object> selectTotal();

	/**
	 * @Author:SHIGS
	 * @Description------ 应收账款
	 * @Date:18:57 2017/10/23
	 * @modified By
	 */
	Map<String, Object> selectRequestTotal(Date startDate, Date endDate);

	/**
	 * @Author:SHIGS --- 趋势图
	 * @Description
	 * @Date:20:20 2017/10/23
	 * @modified By
	 */
	Map<String, Object> selectRequestTrend(Date startTime, Date endTime, String receiveName);

	/**
	 * @Author:SHIGS
	 * @Description 下月应收
	 * @Date:21:10 2017/10/23
	 * @modified By
	 */
	Map<String, Object> selectRequestNext(Date startDate, Date endDate, String area, String country);

	/**
	 * @Author:王晓丹
	 * @Description 下月应收 -- 新的，旧方法没动
	 * @Date:21:10 2017/10/23
	 * @modified By
	 */
	public Map<String, Object> selectRequestNextNew(Date startDate, Date endDate, String area, String country);

	/**
	 * @Author:SHIGS
	 * @Description 查询所有地区
	 * @Date:22:41 2017/10/23
	 * @modified By
	 */
	Map<String, Object> selectArea();

	/**
	 * @Author:SHIGS
	 * @Description 查询所有国家
	 * @Date:13:46 2017/10/24
	 * @modified By
	 */
	Map<String, Object> selectCountry(String area);

	/**
	 * @Author:SHIGS
	 * @Description
	 * @Date:13:56 2017/10/24
	 * @modified By
	 */
	Map<String, Object> selectByAreaOrCountry(Date startDate, Date endDate, String area, String country);

	/**
	 * 导入应收账款的数据到数据库
	 *
	 * @param datas
	 * @param testOnly true:只检测数据  false:插入正式库
	 * @return
	 */
	ImportDataResponse importData(List<String[]> datas, boolean testOnly);


	List<InquiryAreaVO> selectAllCompanyAndOrgList();

	/**
	 * @Author:lirb
	 * @Description 根据条件获取 应收余额
	 * @Date:13:56 2017/12/20
	 * @modified By
	 */
	Double selectReceive(Date startTime, Date endTime, String company, String org, String area, String country);

	/**
	 * @Author:lirb
	 * @Description 查询各区域的应收余额
	 * @Date:13:56 2017/12/20
	 * @modified By
	 */
	List<Map<String, Object>> selectReceiveGroupByArea(Date startTime, Date endTime);

	/**
	 * @Author:lirb
	 * @Description 查询各主体公司的应收余额
	 * @Date:13:56 2017/12/20
	 * @modified By
	 */
	List<Map<String, Object>> selectReceiveGroupByCompany(Date startTime, Date endTime);

	/**
	 * @Author:lirb
	 * @Description 查询各事业部的应收余额
	 * @Date:13:56 2017/12/20
	 * @modified By
	 */
	List<Map<String, Object>> selectReceiveGroupByOrg(Date startTime, Date endTime);

	/**
	 * @Author:lirb
	 * @Description 查询账龄总览数据
	 * @Date:13:56 2018/4/3
	 * @modified By
	 */
	Map<String, Object> selectAgingSummary(Map<String, String> map);

	/**
	 * @Author:lirb
	 * @Description 根据主体公司事业部及大区三个维度查询账龄数据
	 * @Date:13:56 2018/4/3
	 * @modified By
	 */
	List<Map<String,Object>> selectAgingSummaryGroupByCompanyAndOrgAndArea(Map<String, String> map);

}