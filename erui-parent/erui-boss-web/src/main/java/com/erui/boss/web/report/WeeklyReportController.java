package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.service.WeeklyReportService;
import com.erui.report.util.ParamsUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 周报
 */
@Controller
@RequestMapping("/report/weeklyReport")
public class WeeklyReportController {

    @Autowired
    private WeeklyReportService weeklyReportService;

    /**
     * 地区明细
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/areaDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object areaDetail(@RequestBody(required = true) Map<String, Object> params) {
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            params.put("startTime", "2018-01-01 00:00:00"); // 从2018年开始查询
            params.put("endTime", DateUtil.format(DateUtil.FULL_FORMAT_STR, new Date())); // 到当前时间结束
        } else {
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
        }

        //查询各地区的时间段内新用户注册数，中国算一个地区
        Map<String, Object> registerData = weeklyReportService.selectBuyerRegistCountGroupByArea(params);
        //查询各地区的时间段内会员数 中国算一个地区
        Map<String, Object> buyerData = weeklyReportService.selectBuyerCountGroupByArea(params);
        // 查询各地区时间段内询单数
        Map<String, Object> inqNumInfoData = weeklyReportService.selectInqNumGroupByArea(params);
        // 查询各个地区时间段内的报价数量和金额信息
        Map<String, Object> quoteInfoData = weeklyReportService.selectQuoteInfoGroupByArea(params);
        // 查询各个地区时间段内的订单数量和金额信息
        Map<String, Object> orderInfoData = weeklyReportService.selectOrderInfoGroupByArea(params);


        Map<String, Map> data = new HashMap<>();
        data.put("registerInfo", registerData); // 新用户注册数据信息
        data.put("memberNumInfo", buyerData); // 会员数数据信息
        data.put("inqNumInfo", inqNumInfoData); // 询单数量数据信息
        data.put("quoteInfo", quoteInfoData); // 报价数量/金额数据信息
        data.put("orderInfo", orderInfoData); // 订单数量/金额数据信息

        return new Result<>(data);
    }


    /**
     * 导出地区明细
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/exportAreaDetail")
    public Object exportAreaDetail(HttpServletResponse response, String startTime, String endTime) {
        Map<String, Object> params = new HashMap();
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            params.put("startTime", "2018-01-01 00:00:00"); // 从2018年开始查询
            params.put("endTime", DateUtil.format(DateUtil.FULL_FORMAT_STR, new Date())); // 到当前时间结束
        } else {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
        }

        HSSFWorkbook wb = weeklyReportService.genAreaDetailExcel(params);
        //excel文件名
        String fileName = "地区周报明细" + System.currentTimeMillis() + ".xls";
        try {
            RequestCreditController.setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 事业部明细
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orgDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object orgDetail(@RequestBody(required = true) Map<String, Object> params) {
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            params.put("startTime", "2018-01-01 00:00:00"); // 从2018年开始查询
            params.put("endTime", DateUtil.format(DateUtil.FULL_FORMAT_STR, new Date())); // 到当前时间结束
        } else {
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
        }

        // 询单数量信息
        Map<String, Object> inqNumInfoData = weeklyReportService.selectInqNumGroupByOrg(params);
        // 报价数量、金额、用时
        Map<String, Object> quoteInfoData = weeklyReportService.selectQuoteInfoGroupByOrg(params);
        // 查询订单数量、金额
        Map<String, Object> orderInfoData = weeklyReportService.selectOrderInfoGroupByOrg(params);
        // 查询合格供应商数量信息
        Map<String, Object> supplierNumInfoData = weeklyReportService.selectSupplierNumInfoGroupByOrg(params);
        // 查询事业部spu和sku数量信息
        Map<String, Object> spuSkuNumInfoData = weeklyReportService.selectSpuAndSkuNumInfoGroupByOrg(params);

        Map<String, Map> data = new HashMap<>();
        data.put("inqNumInfo", inqNumInfoData); // 询单数量数据信息
        data.put("quoteInfo", quoteInfoData); // 报价数据信息
        data.put("orderInfo", orderInfoData); // 订单数据信息
        data.put("supplierNumInfo", supplierNumInfoData); // 供应商数量数据信息
        data.put("spuSkuNumInfoData", spuSkuNumInfoData); // spu/sku数量数据信息

        return new Result<>(data);
    }


    /**
     * 导出事业部明细
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/exportOrgDetail")
    public Object exportOrgDetail(HttpServletResponse response, String startTime, String endTime) {
        Map<String, Object> params = new HashMap();
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            params.put("startTime", "2018-01-01 00:00:00"); // 从2018年开始查询
            params.put("endTime", DateUtil.format(DateUtil.FULL_FORMAT_STR, new Date())); // 到当前时间结束
        } else {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
        }

        HSSFWorkbook wb = weeklyReportService.genOrgDetailExcel(params);
        //excel文件名
        String fileName = "事业部周报明细" + System.currentTimeMillis() + ".xls";
        try {
            RequestCreditController.setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 平台数据分析
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/platformDataDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object platformDataDetail(@RequestBody(required = true) Map<String, Object> params) {
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            params.put("startTime", "2018-01-01 00:00:00"); // 从2018年开始查询
            params.put("endTime", DateUtil.format(DateUtil.FULL_FORMAT_STR, new Date())); // 到当前时间结束
        } else {
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
        }

        // 查询时间段内询单数
        Map<String, Object> inqNumInfoData = weeklyReportService.selectInqNumGroupByAreaTotal(params);
        // 查询时间段内的报价数量
        Map<String,Object> quoteInfoData = weeklyReportService.selectQuoteInfoGroupByAreaTotal(params);
        // 查询时间段内的订单数量
        Map<String,Object> orderInfoData = weeklyReportService.selectOrderInfoGroupByAreaTotal(params);
        // 谷歌统计信息
        Map<String,Object> googleStatistics = weeklyReportService.googleStatisticsInfo(params);

        Map<String,Map> data = new HashMap<>();
        data.put("googleStatistics",googleStatistics); // 谷歌统计信息
        data.put("inqNumInfo",inqNumInfoData); // 询单数量数据信息
        data.put("quoteInfo",quoteInfoData); // 报价数量数据信息
        data.put("orderInfo",orderInfoData); // 订单数量数据信息


        return new Result<>(data);
    }



    /**
     * 导出平台数据分析
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/exportPlatformDataDetail")
    public Object exportPlatformDataDetail(HttpServletResponse response, String startTime, String endTime) {
        Map<String, Object> params = new HashMap();
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            params.put("startTime", "2018-01-01 00:00:00"); // 从2018年开始查询
            params.put("endTime", DateUtil.format(DateUtil.FULL_FORMAT_STR, new Date())); // 到当前时间结束
        } else {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
        }

        HSSFWorkbook wb = weeklyReportService.genPlatformDataDetail(params);
        //excel文件名
        String fileName = "平台数据分析" + System.currentTimeMillis() + ".xls";
        try {
            RequestCreditController.setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
