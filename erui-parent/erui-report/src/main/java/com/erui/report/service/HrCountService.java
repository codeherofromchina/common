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
     * @Author:SHIGS
     * @Description general 战斗力
     * @Date:16:16 2017/10/25
     * @modified By
     */
    Map<String,Object> selectHrCount(int days);
     /**
      * @Author:SHIGS
      * @Description
      * @Date:0:03 2017/10/21
      * @modified By
      */
	Map<String,Object> selectHrCountByPart(int days);
	 /**
	  * @Author:SHIGS
	  * @Description 查询组织结构模块
	  * @Date:17:51 2017/10/24
	  * @modified By
	  */
	 Map<String,List<String>> selectBigDepart();
	 /**
	  * @Author:SHIGS
	  * @Description
	  * @Date:18:04 2017/10/24
	  * @modified By
	  */
	Map <String,Object>selectHrCountByDepart(String depart,int days);
	 /**
	  * @Author:SHIGS
	  * @Description 查询组织部门数量
	  * @Date:21:25 2017/10/24
	  * @modified By
	  */
	List<Map> selectDepartmentCount();
    /**
	 * 导入人力资源数据
	 * @param datas
     * @param testOnly	true:只检测数据  false:插入正式库
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) ;
}
