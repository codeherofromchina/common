package com.erui.boss.web.report.v2;

import com.erui.boss.web.util.Result;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.QuoteStatisticsService;
import com.erui.report.util.ParamsUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 报价统计
 * Created by wangxiaodan on 2019/3/16.
 */
@RestController
@RequestMapping("/report/v2/quoteStatistics")
public class QuoteStatisticsController {
    @Autowired
    private QuoteStatisticsService quoteStatisticsService;

    /**
     * 报价业绩统计
     *
     * @return
     */
    @RequestMapping(value = "quotePerformance", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> quotePerformance(@RequestBody(required = true) Map<String, Object> req) {
        int pageNum = NumberUtils.toInt((String) req.get("pageNum"), 1);
        int pageSize = NumberUtils.toInt((String) req.get("pageSize"), 1);
        Map<String, Object> params = ParamsUtils.verifyParam(req, DateUtil.SHORT_FORMAT_STR, null);
        PageInfo<Map<String, Object>> pageInfo = quoteStatisticsService.quotePerformance(pageNum, pageSize, params);
        Result<Object> result = new Result<>(pageInfo);
        return result;
    }
}
