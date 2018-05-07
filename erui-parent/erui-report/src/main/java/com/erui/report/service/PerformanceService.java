package com.erui.report.service;

import com.erui.report.util.ImportDataResponse;

import java.util.List;

public interface PerformanceService {

    /**
     * 销售业绩统计-销售业绩表
     *
     * @param datas
     * @param testOnly true:只检测数据  false:插入正式库
     * @return
     */
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly);
}
