package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.CategoryQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * @Description:品控
 * @Author: lirb
 * @CreateDate: 2018/3/5 9:12
 */
@Controller
@RequestMapping(value = "/report/quality", method = RequestMethod.POST, produces = "application/json;charset-utf-8")
public class CategoryQualityController {
    @Autowired
    private CategoryQualityService qualityService;

    @ResponseBody
    @RequestMapping("/qualityPandect")
    public Result qualityPandect(@RequestBody(required = true) Map<String, String> params) {
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        String fullStartTime = DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2);
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        params.put("startTime", fullStartTime);
        params.put("endTime", fullEndTime);
        Map<String, Object> data = this.qualityService.selectQualitySummaryData(params);
        return result.setData(data);
    }

    @ResponseBody
    @RequestMapping("/qualityTrend")
    public Result qualityTrend(@RequestBody(required = true) Map<String, String> params) {
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (params.get("type") == null || startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2);
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        params.put("startTime", fullStartTime);
        params.put("endTime", fullEndTime);
        Map<String, Object> data = qualityService.selectQualityTrendData(startTime, endTime, params.get("type"));
        return result.setData(data);
    }
}
