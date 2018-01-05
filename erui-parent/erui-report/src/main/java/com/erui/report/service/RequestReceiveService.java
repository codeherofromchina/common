package com.erui.report.service;


import com.erui.report.util.ImportDataResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RequestReceiveService {

    /**
     * 导入应收已收的数据到数据库
     * @param datas
     * @param  testOnly	true:只检测数据  false:插入正式库
     * @return
     */
    ImportDataResponse importData(List<String[]> datas, boolean testOnly);
    /**
     *根据条件查询回款金额
     */
    Double selectBackAmount(Date startTime, Date endTime,String company,String org,String area,String country);
    /**
     * @Author:lirb
     * @Description 查询各区域的回款金额
     * @Date:13:56 2017/12/20
     * @modified By
     */
    List<Map<String,Object>> selectBackAmountGroupByArea(Date startTime, Date endTime);
    /**
     * @Author:lirb
     * @Description 查询各主体公司的回款金额
     * @Date:13:56 2017/12/20
     * @modified By
     */
    List<Map<String,Object>> selectBackAmountGroupByCompany(Date startTime,Date endTime);
    /**
     * @Author:lirb
     * @Description 查询各事业部的回款金额
     * @Date:13:56 2017/12/20
     * @modified By
     */
    List<Map<String,Object>> selectBackAmountGroupByOrg(Date startTime,Date endTime);
}