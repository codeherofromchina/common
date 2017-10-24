package com.erui.report.service;

import com.erui.report.model.HrCount;
import com.erui.report.util.ImportDataResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lirb on 2017/10/19.
 */
public interface HrCountService {
    /**
     * 人力数据列表
     */
    List<HrCount> findAll();
     /**
      * @Author:SHIGS
      * @Description
      * @Date:0:03 2017/10/21
      * @modified By
      */
	public Map<String,Object> selectHrCountByPart(Date startTime, Date endDate);
    
    /**
	 * 导入人力资源数据
	 * @param datas
     * @param flag	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
}
