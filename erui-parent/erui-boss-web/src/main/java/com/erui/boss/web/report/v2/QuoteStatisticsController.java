package com.erui.boss.web.report.v2;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
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

import java.math.BigDecimal;
import java.util.HashMap;
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
        int pageSize = NumberUtils.toInt((String) req.get("pageSize"), 10);
        Map<String, Object> params = ParamsUtils.verifyParam(req, DateUtil.SHORT_FORMAT_STR, null);
        PageInfo<Map<String, Object>> pageInfo = quoteStatisticsService.quotePerformanceByPage(pageNum, pageSize, params);
        if (pageInfo == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        // 查询总报价个数和订单个数
        Map<String,Long> totalOutParams = new HashMap<>();
        quoteStatisticsService.quotePerformance(params, totalOutParams);
        Long totalQuoteNum = totalOutParams.get("totalQuoteNum"); // 总报价个数
        Long totalOrderNum = totalOutParams.get("totalOrderNum"); // 总订单个数
        BigDecimal totalRate = null;
        String totalRateStr = null;
        if (totalQuoteNum != null && totalOrderNum != null) {
            totalRate = new BigDecimal(totalOrderNum / (double) totalQuoteNum).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            totalRateStr = totalRate.setScale(2, BigDecimal.ROUND_HALF_UP) + "%";
        } else {
            totalRate = BigDecimal.ZERO;
            totalRateStr = totalRate.setScale(2,BigDecimal.ROUND_DOWN) + "%";
        }

        Map<String, Object> data = new HashMap<>();
        data.put("pageInfo", pageInfo);
        data.put("totalQuoteNum", totalQuoteNum);
        data.put("totalOrderNum", totalOrderNum);
        data.put("totalRate2", totalRate);
        data.put("totalRate", totalRateStr);
        data.put("totalRateStr", totalRateStr);
        return new Result<>(data);

    }
}
