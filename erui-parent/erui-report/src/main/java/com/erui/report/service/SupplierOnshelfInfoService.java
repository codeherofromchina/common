package com.erui.report.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface SupplierOnshelfInfoService {

    /**
     * 保存已上架明细数据
     * @param startTime
     * @param onshelfInfoList
     * @throws Exception
     */
    void  insertSupplierOnshelfInfoList(String startTime,List<HashMap> onshelfInfoList ) throws Exception;

    /**
     * 根据日期查询各供应商的已上架数据
     * @param params
     * @return
     */
    List<Map<String,Object>> selectOnshelfDetailGroupBySupplier(Map<String,String> params);

    /**
     * 导出各供应商的已上架数据
     * @param dataList
     * @param startTime
     * @param endTime
     * @return
     */
    XSSFWorkbook  exportSupplierOnshelfDetail(Date startTime,Date endTime, List<Map<String,Object>> dataList);
}
