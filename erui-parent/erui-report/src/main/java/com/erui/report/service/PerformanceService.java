package com.erui.report.service;

import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.InquiryAreaVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface PerformanceService {

    /**
     * 销售业绩统计-销售业绩表
     *
     * @param datas
     * @param testOnly true:只检测数据  false:插入正式库
     * @return
     */
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly);

    /**
     * 查询日期列表
     * @return
     */
    List<String> selectDateList();

    /**
     * 查询所有的大区和大区下所有的国家
     * @return
     */
    List<InquiryAreaVO> selectAllAreaAndCountryList();

    /**
     * 查询新增会员的明细
     * @param params
     * @return
     */
    List<Map<String,Object>> selectIncrBuyerDetail(Map<String,String>  params);

    /**
     * 查询员工销售业绩明细
     * @param params
     * @return
     */
    List<Map<String, Object>> selectObtainerPerformance(Map<String,String>  params);

    /**
     * 导出新增会员统计数据
     * @param dataList
     * @return
     */
    XSSFWorkbook exportIncrBuyer(List<Map<String,Object>> dataList);

    /**
     * 导出销售业绩统计数据
     * @param dataList
     * @return
     */
    XSSFWorkbook exportSalesPerformance(List<Map<String,Object>> dataList);

    /**
     * 查询用户负责的国家
     * @param userId
     * @return
     */
    List<String> selectCountryByUserId(Integer userId);
}
