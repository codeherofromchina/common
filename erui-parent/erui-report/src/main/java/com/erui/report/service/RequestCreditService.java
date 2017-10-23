package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.util.ImportDataResponse;

public interface RequestCreditService {
	 /**
	  * @Author:SHIGS
	  * @Description
	  * @Date:17:03 2017/10/23
	  * @modified By
	  */
	Map selectTotal();
	 /**
	  * @Author:SHIGS
	  * @Description------ 应收账款
	  * @Date:18:57 2017/10/23
	  * @modified By
	  */
	Map selectRequestTotal(Date startDate,Date endDate);
	 /**
	  * @Author:SHIGS --- 趋势图
	  * @Description
	  * @Date:20:20 2017/10/23
	  * @modified By
	  */
	List<Map> selectRequestTrend(Date startDate,Date endDate);
	 /**
	  * @Author:SHIGS
	  * @Description 下月应收
	  * @Date:21:10 2017/10/23
	  * @modified By
	  */
	List<Map> selectRequestNext(Date startDate,Date endDate);
	 /**
	  * @Author:SHIGS
	  * @Description
	  * @Date:22:41 2017/10/23
	  * @modified By
	  */
	List<Map> selectArea();
	/**
	 * 导入应收账款的数据到数据库
	 * @param datas
	 * @param flag	true:只检测数据  false:插入正式库
	 * @return
	 */
	ImportDataResponse importData(List<String[]> datas, boolean testOnly);
}