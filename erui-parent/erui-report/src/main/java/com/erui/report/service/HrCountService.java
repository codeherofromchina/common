package com.erui.report.service;

import com.erui.report.model.HrCount;
import com.erui.report.util.ImportDataResponse;

import java.util.List;

/**
 * Created by lirb on 2017/10/19.
 */
public interface HrCountService {
    /**
     * 人力数据列表
     */
    List<HrCount> findAll();
    
    /**
	 * 导入人力资源数据
	 * @param datas
	 * @return
	 */
	public ImportDataResponse importData(List<String[]> datas) ;
}
