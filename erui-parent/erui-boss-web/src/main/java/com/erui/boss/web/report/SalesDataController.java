package com.erui.boss.web.report;

import com.erui.boss.web.util.HttpUtils;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtils;
import com.erui.report.service.SalesDataService;
import com.erui.report.util.AnalyzeTypeEnum;
import com.erui.report.util.ParamsUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    public Object inquiryQuoteGeneral(@RequestBody(required = true) Map<String, String> params) throws Exception {
        //处理参数
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || StringUtils.isEmpty(params.get("type")) || StringUtils.isEmpty(params.get("analyzeType"))) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        if (params.get("type").equals("month")) { //如果按月查询
            if (end == null || startTime.after(end)) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
            params.put("endTime", DateUtil.formatDate2String(endTime, DateUtil.FULL_FORMAT_STR2));
        } else if (params.get("type").equals("week")) { //如果按周查询
            if (end == null || startTime.after(end)) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
            params.put("endTime", DateUtil.formatDate2String(endTime, DateUtil.FULL_FORMAT_STR2));
        } else if (params.get("type").equals("year")) { //如果按年查询
            Date end2 = DateUtil.getYearLastDay(startTime);
            Date endTime = DateUtil.getOperationTime(end2, 23, 59, 59);
            params.put("endTime", DateUtil.formatDate2String(endTime, DateUtil.FULL_FORMAT_STR2));
        } else {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }

        Map<String, Object> data = salesDataService.selectInqQuoteTrendData(params);
        if (data == null || data.size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
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
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        if (params.get("analyzeType") == null || StringUtils.isEmpty(String.valueOf(params.get("analyzeType")))) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String, Object> data = salesDataService.selectAreaDetailByType(params);
        if (data == null || data.size() == 0 || ((List) data.get("datas")).size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(data);
    }

    /**
     * 查询国家的询报价信息（询单数量、询单金额、报价数量、报价金额）
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/inquiryQuoteByCountry", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> inquiryQuoteByCountry(@RequestBody(required = true) Map<String, Object> params) {
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        if (params.get("analyzeType") == null || StringUtils.isEmpty(String.valueOf(params.get("analyzeType")))) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        String analyzeType = String.valueOf(params.get("analyzeType"));
        Map<String, Object> data = null;
        if (AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) {
            // 询单数量或询单金额
            data = salesDataService.selectInquiryInfoByCountry(params);
        } else if (AnalyzeTypeEnum.QUOTE_COUNT.getTypeName().equalsIgnoreCase(analyzeType) || AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)
                || AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) {
            // TODO  || AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)   这里应该在上面的if中，临时修改为请求询单金额时返回报价金额内容，service中也有修改
            // 报价数量或报价金额
            data = salesDataService.selectQuoteInfoByCountry(params);
        } else {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }

        if (data == null || data.size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }

        return new Result<>(data);
    }

    /**
     * 询报价数据统计- 导出大区明细
     *
     * @param startTime
     * @param endTime
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportAreaDetail")
    public Object exportAreaDetail(Date startTime, Date endTime, String analyzeType, String area, String country, HttpServletResponse response) throws Exception {
        if (startTime == null || endTime == null || startTime.after(endTime) || StringUtils.isEmpty(analyzeType)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        endTime = DateUtil.getOperationTime(endTime, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR);
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", fullStartTime);
        params.put("endTime", fullEndTime);
        params.put("analyzeType", analyzeType);
        params.put("area", area);
        params.put("country", country);
        XSSFWorkbook wb = salesDataService.exportAreaDetail(params);
        //文件名
        String fileName = "销售数据大区分析" + System.currentTimeMillis() + ".xlsx";
        RequestCreditController.setResponseHeader(response, fileName);
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
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        if (params.get("analyzeType") == null || StringUtils.isEmpty(String.valueOf(params.get("analyzeType")))) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String, Object> data = salesDataService.selectOrgDetailByType(params);
        if (data == null || data.size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(data);
    }

    /**
     * 询报价数据统计- 导出事业部明细
     *
     * @param startTime
     * @param endTime
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportOrgDetail")
    public Object exportOrgDetail(Date startTime, Date endTime, String analyzeType, HttpServletResponse response) throws Exception {
        if (startTime == null || endTime == null || analyzeType == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        endTime = DateUtil.getOperationTime(endTime, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR);
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", fullStartTime);
        params.put("endTime", fullEndTime);
        params.put("analyzeType", analyzeType);
        XSSFWorkbook wb = salesDataService.exportOrgDetail(params);
        //文件名
        String fileName = "销售数据事业部分析" + System.currentTimeMillis() + ".xlsx";
        RequestCreditController.setResponseHeader(response, fileName);
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
    public Result<Object> inquiryQuoteCategory(@RequestBody(required = true) Map<String, Object> params) {
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        if (params.get("analyzeType") == null || StringUtils.isEmpty(String.valueOf(params.get("analyzeType")))) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }

        Map<String, Object> data = salesDataService.selectCategoryDetailByType(params);
        if (data == null || data.size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(data);
    }


    /**
     * 询报价数据统计- 品类比率
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/selectCategoryNum", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> selectCategoryNum(@RequestBody(required = true) Map<String, Object> params) {
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Map<String, Object> data = null;
        String analyzeType = String.valueOf(params.get("type"));
        if (AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 询单数量
            data = salesDataService.selectCategoryInquiryNum(params);
        } else if (AnalyzeTypeEnum.INQUIRY_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 报价金额
            //data = salesDataService.selectCategoryInquiryAmount(params);
            data = salesDataService.selectCategoryQuoteAmount(params);
        } else if (AnalyzeTypeEnum.QUOTE_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) {  // 报价数量
            data = salesDataService.selectCategoryQuoteNum(params);
        } else if (AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 报价金额
            data = salesDataService.selectCategoryQuoteAmount(params);
        }

        if (data == null || data.size() == 0 || ((List) data.get("names")).size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(data);
    }


    /**
     * 导出询报价数据统计- 品类比率
     *
     * @return
     */
    @RequestMapping(value = "/exportSelectCategoryNum", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Result<Object> exportSelectCategoryNum(HttpServletResponse response,
                                                  String startTime,
                                                  String endTime,
                                                  String type,
                                                  String orgId,
                                                  String areaBn)
            throws Exception {
        Map<String, Object> params = new HashMap();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("type", type);
        params.put("orgId", orgId);
        params.put("areaBn", areaBn);
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        HSSFWorkbook wb = salesDataService.exportSelectCategoryNum(params, type);


        if (wb == null) {
            response.setContentType("text/html;charset=UTF-8");
            new Result<>(ResultStatusEnum.DATA_NULL).printResult(response.getOutputStream());
            return null;
        }
        try {
            String fileName = "询报价数据统计-品类信息" + System.currentTimeMillis() + ".xls";
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    public Result<Object> exportCategoryDetail(Date startTime, Date endTime, String analyzeType, HttpServletResponse response) throws Exception {
        if (startTime == null || endTime == null || startTime.after(endTime) || StringUtils.isEmpty(analyzeType)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        endTime = DateUtil.getOperationTime(endTime, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR);
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", fullStartTime);
        params.put("endTime", fullEndTime);
        params.put("analyzeType", analyzeType);
        XSSFWorkbook wb = salesDataService.exportCategoryDetail(params);
        //文件名
        String fileName = "销售数据品类分析" + System.currentTimeMillis() + ".xlsx";
        RequestCreditController.setResponseHeader(response, fileName);
        wb.write(response.getOutputStream());
        return null;
    }

    /**
     * 询报价数据统计- 客户拜访统计
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/customerVisit", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object customerVisit(@RequestBody(required = true) Map<String, String> params) throws Exception {

        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || StringUtils.isEmpty(params.get("type"))) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        if (params.get("type").equals("month")) { //如果按月查询
            if (end == null || startTime.after(end)) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
            params.put("endTime", DateUtil.formatDate2String(endTime, DateUtil.FULL_FORMAT_STR2));
        } else if (params.get("type").equals("week")) { //如果按周查询
            Date end2 = DateUtil.getDateAfter(startTime, 6);
            Date endTime = DateUtil.getOperationTime(end2, 23, 59, 59);
            params.put("endTime", DateUtil.formatDate2String(endTime, DateUtil.FULL_FORMAT_STR2));
        } else if (params.get("type").equals("year")) { //如果按年查询
            Date end2 = DateUtil.getYearLastDay(startTime);
            Date endTime = DateUtil.getOperationTime(end2, 23, 59, 59);
            params.put("endTime", DateUtil.formatDate2String(endTime, DateUtil.FULL_FORMAT_STR2));
        } else {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }

        Map<String, Object> data = salesDataService.selectCustomerVisitDetail(params);
        if (data == null || data.size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(data);
    }


}
