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
}