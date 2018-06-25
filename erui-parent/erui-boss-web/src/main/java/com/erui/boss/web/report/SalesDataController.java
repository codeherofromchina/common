package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.SalesDataService;
import com.erui.report.util.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param params
     * @return
     */
    @RequestMapping(value = "/inquiryQuoteGeneral",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object inquiryQuoteGeneral(@RequestBody(required = true) Map<String,Object> params){
        // 获取参数并转换成时间格式
        params= ParamsUtils.verifyParam(params, DateUtil.SHORT_SLASH_FORMAT_STR,"analyzeType");
        if (params == null) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String,Object> data=salesDataService.selectInqQuoteTrendData(params);
        return  new Result<>(data);
    }

    /**
     * 询报价数据统计-大区明细
     * @param params
     * @return
     */
    @RequestMapping(value = "/inquiryQuoteArea",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object inquiryQuoteArea(@RequestBody(required = true) Map<String,Object> params){
        // 获取参数并转换成时间格式
        params= ParamsUtils.verifyParam(params, DateUtil.SHORT_SLASH_FORMAT_STR,new String[]{"analyzeType","area","country"});
        if (params == null) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
      Map<String,Object> data= salesDataService.selectAreaDetailByType(params);
        return  new Result<>(data);
    }

    /**
     * 询报价数据统计-事业部明细
     * @param params
     * @return
     */
    @RequestMapping(value = "/inquiryQuoteOrg",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object inquiryQuoteOrg(@RequestBody(required = true) Map<String,Object> params){
        // 获取参数并转换成时间格式
        params= ParamsUtils.verifyParam(params, DateUtil.SHORT_SLASH_FORMAT_STR,"analyzeType");
        if (params == null) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String,Object> data= salesDataService.selectOrgDetailByType(params);
        return  new Result<>(data);
    }

    /**
     * 询报价数据统计- 品类明细
     * @param params
     * @return
     */
    @RequestMapping(value = "/inquiryQuoteCategory",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object inquiryQuoteCategory(@RequestBody(required = true) Map<String,Object> params){
        // 获取参数并转换成时间格式
        params= ParamsUtils.verifyParam(params, DateUtil.SHORT_SLASH_FORMAT_STR,"analyzeType");
        if (params == null) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String,Object> data= salesDataService.selectCategoryDetailByType(params);
        return  new Result<>(data);
    }


}
