package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;

public interface RequestCreditService {
	 /**
	  * @Author:SHIGS
	  * @Description 应收账款累计
	  * @Date:17:03 2017/10/23
	  * @modified By
	  */
	Map<String,Object> selectTotal();
	 /**
	  * @Author:SHIGS
	  * @Description------ 应收账款
	  * @Date:18:57 2017/10/23
	  * @modified By
	  */
	Map<String,Object> selectRequestTotal(Date startDate,Date endDate);
	 /**
	  * @Author:SHIGS --- 趋势图
	  * @Description
	  * @Date:20:20 2017/10/23
	  * @modified By
	  */
	 Map<String,Object> selectRequestTrend(int days,String receiveName);
	 /**
	  * @Author:SHIGS
	  * @Description 下月应收
	  * @Date:21:10 2017/10/23
	  * @modified By
	  */
	Map<String,Object> selectRequestNext(Date startDate,Date endDate,String area,String country);
	 /**
	  * @Author:SHIGS
	  * @Description 查询所有地区
	  * @Date:22:41 2017/10/23
	  * @modified By
	  */
	 Map<String,Object> selectArea();
	 /**
	  * @Author:SHIGS
	  * @Description 查询所有国家
	  * @Date:13:46 2017/10/24
	  * @modified By
	  */
	 Map<String,Object> selectCountry(String area);
	 /**
	  * @Author:SHIGS
	  * @Description
	  * @Date:13:56 2017/10/24
	  * @modified By
	  */
	Map<String,Object> selectByAreaOrCountry(Date startDate,Date endDate,String area,String country);
	/**
	 * 导入应收账款的数据到数据库
	 * @param datas
	 * @param  testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	ImportDataResponse importData(List<String[]> datas, boolean testOnly);
}