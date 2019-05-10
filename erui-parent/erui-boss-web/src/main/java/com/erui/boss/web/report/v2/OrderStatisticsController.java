package com.erui.boss.web.report.v2;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.report.service.OrderStatisticsService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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
 * 订单统计控制器
 * 主要负责订单相关数据信息的统计
 * Created by wangxiaodan on 2019/3/15.
 */
@RestController
@RequestMapping("/report/v2/orderStatistics")
public class OrderStatisticsController {
    @Autowired
    private OrderStatisticsService orderStatisticsService;

    /**
     * 订单统计-整体业绩
     *
     * @return
     */
    @RequestMapping(value = "yearPerformance", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> yearPerformance(@RequestBody(required = true) Map<String, String> req) {
        Result<Object> result = new Result<>();
        Integer startYear = NumberUtils.isNumber(req.get("startYear")) ? NumberUtils.createInteger(req.get("startYear")) : null;
        Integer endYear = NumberUtils.isNumber(req.get("endYear")) ? NumberUtils.createInteger(req.get("endYear")) : null;

        // 查找数据
        Map<String, Object> data = orderStatisticsService.yearPerformance(startYear, endYear);
        if (data.size() > 0) {
            result.setData(data);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        return result;
    }


    /**
     * 订单统计-整体地区业绩
     *
     * @return
     */
    @RequestMapping(value = "yearAreaPerformance", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> yearAreaPerformance(@RequestBody(required = true) Map<String, String> req) {
        Result<Object> result = new Result<>();
        Integer startYear = NumberUtils.isNumber(req.get("startYear")) ? NumberUtils.createInteger(req.get("startYear")) : null;
        Integer endYear = NumberUtils.isNumber(req.get("endYear")) ? NumberUtils.createInteger(req.get("endYear")) : null;
        // 查找数据
        Map<String, Object> data = orderStatisticsService.yearAreaPerformance(startYear, endYear);
        if (data.size() > 0) {
            result.setData(data);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        return result;
    }


    /**
     * 订单统计-业务业绩统计-项目列表
     *
     * @return
     */
    @RequestMapping(value = "projectList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> projectList(@RequestBody(required = true) Map<String, Object> req) {
        Map<String, String> params = reqCover(req);

        Result<Object> result = new Result<>();
        int pageNum = NumberUtils.toInt(params.get("pageNum"), 1);
        int pageSize = NumberUtils.toInt(params.get("pageSize"), 20);

        BigDecimal totalMoney = orderStatisticsService.projectTotalMoney(params);
        totalMoney = totalMoney.setScale(2, BigDecimal.ROUND_DOWN);
        PageInfo<Map<String, Object>> pageInfo = orderStatisticsService.projectList(pageNum, pageSize, params);
        Map<String, Object> data = new HashMap<>();
        data.put("totalMoney", totalMoney);
        data.put("pageInfo", pageInfo);
        result.setData(data);
        return result;
    }


    private Map<String,String> reqCover(Map<String,Object> reqParams) {
        Map<String, String> result = new HashMap<>();
        if (reqParams != null && reqParams.size() > 0) {
            for (Map.Entry<String,Object> entry : reqParams.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof String) {
                    result.put(entry.getKey(), (String) value);
                }
            }
            // 提取国家和地区信息
            Object obj = reqParams.get("area_country");
            if (obj != null && obj.getClass().isArray()) {
                Object[] areaCountry = (Object[])obj;
                if (areaCountry.length == 2) {
                    if (areaCountry[0] != null && StringUtils.isNotBlank(String.valueOf(areaCountry[0]))) {
                        result.put("areaBn", String.valueOf(areaCountry[0]));
                    }
                    if (areaCountry[1] != null && StringUtils.isNotBlank(String.valueOf(areaCountry[1]))) {
                        result.put("countryBn", String.valueOf(areaCountry[1]));
                    }
                }
            }
        }
        return result;
    }
}
