package com.erui.boss.web.report.v2;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.quartz.ReportBaseDataExecute;
import com.erui.report.service.BuyerStatisticsService;
import com.erui.report.util.ParamsUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
@RestController
@RequestMapping("/report/v2/buyerStatistics")
public class BuyerStatisticsController {
    @Autowired
    private BuyerStatisticsService buyerStatisticsService;

    /**
     * 注册用户查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "registerBuyerList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> registerBuyerList(@RequestBody(required = true) Map<String, Object> req) {
        Map<String, String> params = reqCover(req); // 转换地区和国家的数组信息参数
        int pageNum = NumberUtils.toInt(params.get("pageNum"), 1);
        int pageSize = NumberUtils.toInt(params.get("pageSize"), 20);


        PageInfo<Map<String, Object>> pageInfo = buyerStatisticsService.registerBuyerList(pageNum, pageSize, params);
        Result<Object> result = new Result<>(pageInfo);
        return result;
    }

    /**
     * 会员用户查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "membershipBuyerList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> membershipBuyerList(@RequestBody(required = true) Map<String, Object> req) {
        Map<String, String> params = reqCover(req);

        int pageNum = NumberUtils.toInt(params.get("pageNum"), 1);
        int pageSize = NumberUtils.toInt(params.get("pageSize"), 20);

        PageInfo<Map<String, Object>> pageInfo = buyerStatisticsService.membershipBuyerList(pageNum, pageSize, params);
        Result<Object> result = new Result<>(pageInfo);
        return result;
    }

    /**
     * 入网用户查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "applyBuyerList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> applyBuyerList(@RequestBody(required = true) Map<String, Object> req) {
        Map<String, String> params = reqCover(req);
        int pageNum = NumberUtils.toInt(params.get("pageNum"), 1);
        int pageSize = NumberUtils.toInt(params.get("pageSize"), 20);

        PageInfo<Map<String, Object>> pageInfo = buyerStatisticsService.applyBuyerList(pageNum, pageSize, params);
        Result<Object> result = new Result<>(pageInfo);
        return result;
    }


    /**
     * 开发会员统计  （订单会员统计）
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "orderBuyerStatistics", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderBuyerStatistics(@RequestBody(required = true) Map<String, Object> req) {
        Map<String, Object> params = ParamsUtils.verifyParam(req, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            params = new HashMap<>();
        }
        // 提取国家和地区信息
        Object obj = req.get("area_country");
        if (obj != null && obj instanceof ArrayList) {
            ArrayList areaCountry = (ArrayList)obj;
            if (areaCountry.size() == 2) {
                Object areaBn = areaCountry.get(0);
                if (areaBn != null && StringUtils.isNotBlank(String.valueOf(areaBn))) {
                    req.put("areaBn", String.valueOf(areaBn));
                }
                Object country = areaCountry.get(1);
                if (country != null && StringUtils.isNotBlank(String.valueOf(country))) {
                    req.put("countryBn", String.valueOf(country));
                }
            }
        }
        // 此接口没有分页信息
        params.remove("pageSize");
        params.remove("pageNum");
        Result<Object> result = new Result<>();
        Map<String, Object> data = buyerStatisticsService.orderBuyerStatistics(params);
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    @RequestMapping(value = "/timer/2019-03-21", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public String timer() {
        double random = Math.random();
        System.out.println("调用定时任务开始(" + random + ")");
        try {
            ReportBaseDataExecute reportBaseQuartz = new ReportBaseDataExecute();
            reportBaseQuartz.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
        System.out.println("调用定时任务结束(" + random + ")");
        return "OK";
    }


    private Map<String,String> reqCover(Map<String,Object> reqParams) {
        Map<String, String> result = new HashMap<>();
        if (reqParams != null && reqParams.size() > 0) {
            // 页码信息
            Object pageNum = reqParams.get("pageNum");
            if (pageNum != null) {
                result.put("pageNum",String.valueOf(pageNum));
            }
            Object pageSize = reqParams.get("pageSize");
            if (pageSize != null) {
                result.put("pageSize",String.valueOf(pageSize));
            }
            // 其他字符参数
            for (Map.Entry<String,Object> entry : reqParams.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof String) {
                    result.put(entry.getKey(), (String) value);
                }
            }
            // 提取国家和地区信息
            Object obj = reqParams.get("area_country");
            if (obj != null && obj instanceof ArrayList) {
                ArrayList areaCountry = (ArrayList)obj;
                if (areaCountry.size() == 2) {
                    Object areaBn = areaCountry.get(0);
                    if (areaBn != null && StringUtils.isNotBlank(String.valueOf(areaBn))) {
                        result.put("areaBn", String.valueOf(areaBn));
                    }
                    Object country = areaCountry.get(1);
                    if (country != null && StringUtils.isNotBlank(String.valueOf(country))) {
                        result.put("countryBn", String.valueOf(country));
                    }
                }
            }
        }
        return result;
    }
}
