package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.WeeklyReportService;
import com.erui.report.util.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 周报
 */
@RestController
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
    @RequestMapping(value = "/areaDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object areaDetail(@RequestBody(required = true) Map<String, Object> params) {
        //处理时间参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_SLASH_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }

        //查询各地区的时间段内新用户注册数，中国算一个地区
        Map<String, Object> registerData = weeklyReportService.selectBuyerRegistCountGroupByArea(params);
        //查询各地区的时间段内会员数 中国算一个地区
        Map<String, Object> buyerData = weeklyReportService.selectBuyerCountGroupByArea(params);
        return new Result<>(registerData);
    }

    /**
     * 事业部明细
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/orgDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object orgDetail(@RequestBody(required = true) Map<String, String> params) {

        return null;
    }

    /**
     * 平台数据分析
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/platformDataDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object platformDataDetail(@RequestBody(required = true) Map<String, String> params) {

        return null;
    }

}
