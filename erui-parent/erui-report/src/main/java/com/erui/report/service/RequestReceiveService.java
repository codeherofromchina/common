package com.erui.report.service;


import com.erui.report.util.ImportDataResponse;

import java.util.List;

public interface RequestReceiveService {

    /**
     * 导入应收账款的数据到数据库
     * @param datas
     * @param  testOnly	true:只检测数据  false:插入正式库
     * @return
     */
    ImportDataResponse importData(List<String[]> datas, boolean testOnly);

}