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


}
