package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtils;
import com.erui.report.service.SalesDataService;
import com.erui.report.util.ParamsUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 销售数据统计
 */
@RestController
@RequestMapping("/report/salesData")
public class SalesDataController {

    @Autowired
    private SalesDataService salesDataService;

    /**
     * 询报价数据统计-总览统计
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/inquiryQuoteGeneral", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object inquiryQuoteGeneral(@RequestBody(required = true) Map<String, Object> params) {
        // 获取参数并转换成时间格式
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_SLASH_FORMAT_STR, "analyzeType");
        if (params == null) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String, Object> data = salesDataService.selectInqQuoteTrendData(params);
        return new Result<>(data);
    }

    /**
     * 询报价数据统计-大区明细
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/inquiryQuoteArea", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object inquiryQuoteArea(@RequestBody(required = true) Map<String, Object> params) {
        // 获取参数并转换成时间格式
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_SLASH_FORMAT_STR, new String[]{"analyzeType", "area", "country"});
        if (params == null) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String, Object> data = salesDataService.selectAreaDetailByType(params);
        return new Result<>(data);
    }

    /**
     * 询报价数据统计- 导出大区明细
     * @param startTime
     * @param endTime
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportAreaDetail")
    public Object exportAreaDetail(Date startTime, Date endTime,String analyzeType,String area, String country, HttpServletResponse response) throws Exception {
        if (startTime == null || endTime == null || startTime.after(endTime)|| StringUtils.isEmpty(analyzeType)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        endTime = DateUtil.getOperationTime(endTime, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, "yyyy/MM/dd HH:mm:ss");
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", fullStartTime);
        params.put("endTime", fullEndTime);
        params.put("analyzeType", analyzeType);
        params.put("area",area);
        params.put("country",country);
        XSSFWorkbook wb = salesDataService.exportAreaDetail(params);
        //文件名
        String fileName="销售数据大区分析"+System.currentTimeMillis()+".xlsx";
        RequestCreditController.setResponseHeader(response,fileName);
        wb.write(response.getOutputStream());
        return null;
    }
    /**
     * 询报价数据统计-事业部明细
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/inquiryQuoteOrg", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object inquiryQuoteOrg(@RequestBody(required = true) Map<String, Object> params) {
        // 获取参数并转换成时间格式
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_SLASH_FORMAT_STR, "analyzeType");
        if (params == null) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String, Object> data = salesDataService.selectOrgDetailByType(params);
        return new Result<>(data);
    }

    /**
     * 询报价数据统计- 导出事业部明细
     * @param startTime
     * @param endTime
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportOrgDetail")
    public Object exportOrgDetail(Date startTime, Date endTime,String analyzeType, HttpServletResponse response) throws Exception {
        if (startTime == null || endTime == null ||analyzeType==null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        endTime = DateUtil.getOperationTime(endTime, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, "yyyy/MM/dd HH:mm:ss");
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", fullStartTime);
        params.put("endTime", fullEndTime);
        params.put("analyzeType", analyzeType);
        XSSFWorkbook wb = salesDataService.exportOrgDetail(params);
        //文件名
        String fileName="销售数据事业部分析"+System.currentTimeMillis()+".xlsx";
        RequestCreditController.setResponseHeader(response,fileName);
        wb.write(response.getOutputStream());
        return null;
    }
    /**
     * 询报价数据统计- 品类明细
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/inquiryQuoteCategory", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object inquiryQuoteCategory(@RequestBody(required = true) Map<String, Object> params) {
        // 获取参数并转换成时间格式
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_SLASH_FORMAT_STR, "analyzeType");
        if (params == null) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String, Object> data = salesDataService.selectCategoryDetailByType(params);
        return new Result<>(data);
    }

    /**
     * 询报价数据统计- 导出品类明细
     *
     * @param startTime
     * @param endTime
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportCategoryDetail")
    public Object exportCategoryDetail(Date startTime, Date endTime,String analyzeType, HttpServletResponse response) throws Exception {
        if (startTime == null || endTime == null || startTime.after(endTime)|| StringUtils.isEmpty(analyzeType)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        endTime = DateUtil.getOperationTime(endTime, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, "yyyy/MM/dd HH:mm:ss");
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", fullStartTime);
        params.put("endTime", fullEndTime);
        params.put("analyzeType", analyzeType);
        XSSFWorkbook wb = salesDataService.exportCategoryDetail(params);
        //文件名
        String fileName="销售数据品类分析"+System.currentTimeMillis()+".xlsx";
        RequestCreditController.setResponseHeader(response,fileName);
        wb.write(response.getOutputStream());
        return null;
    }

    /**
     * 询报价数据统计- 客户拜访统计
     * @param params
     * @return
     */
    @RequestMapping(value = "/customerVisit", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object customerVisit(@RequestBody(required = true) Map<String, String> params)throws Exception {

        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null||StringUtils.isEmpty(params.get("type"))) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        if(params.get("type").equals("month")){//如果按月查询
            if(end==null|| startTime.after(end)){
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
            params.put("endTime",DateUtil.formatDate2String(endTime,DateUtil.FULL_FORMAT_STR2));
        }else  if(params.get("type").equals("week")){//如果按周查询
            Date end2 = DateUtil.getDateAfter(startTime, 6);
            Date endTime = DateUtil.getOperationTime(end2, 23, 59, 59);
            params.put("endTime",DateUtil.formatDate2String(endTime,DateUtil.FULL_FORMAT_STR2));
        }else if(params.get("type").equals("year")){//如果按年查询
            Date end2 = DateUtil.getYearLastDay(startTime);
            Date endTime = DateUtil.getOperationTime(end2, 23, 59, 59);
            params.put("endTime",DateUtil.formatDate2String(endTime,DateUtil.FULL_FORMAT_STR2));
        }else {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }

        Map<String,Object> data= salesDataService.selectCustomerVisitDetail(params);
        return new Result<>(data);
    }



}
