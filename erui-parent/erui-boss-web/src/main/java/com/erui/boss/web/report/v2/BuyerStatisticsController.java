package com.erui.boss.web.report.v2;

import com.erui.boss.web.util.Result;
import com.erui.report.service.BuyerStatisticsService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param req
     * @return
     */
    @RequestMapping(value = "registerBuyerList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> registerBuyerList(@RequestBody(required = true) Map<String, String> req) {

        int pageNum = NumberUtils.toInt(req.get("pageNum"), 1);
        int pageSize = NumberUtils.toInt(req.get("pageSize"), 20);

        PageInfo<Map<String, Object>> pageInfo = buyerStatisticsService.registerBuyerList(pageNum, pageSize, req);
        Result<Object> result = new Result<>(pageInfo);
        return result;
    }

    /**
     * 会员用户查询
     * @param req
     * @return
     */
    @RequestMapping(value = "membershipBuyerList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> membershipBuyerList(@RequestBody(required = true) Map<String, String> req) {
        int pageNum = NumberUtils.toInt(req.get("pageNum"), 1);
        int pageSize = NumberUtils.toInt(req.get("pageSize"), 20);

        PageInfo<Map<String, Object>> pageInfo = buyerStatisticsService.membershipBuyerList(pageNum, pageSize, req);
        Result<Object> result = new Result<>(pageInfo);
        return result;
    }

    /**
     * 入网用户查询
     * @param req
     * @return
     */
    @RequestMapping(value = "applyBuyerList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> applyBuyerList(@RequestBody(required = true) Map<String, String> req) {
        int pageNum = NumberUtils.toInt(req.get("pageNum"), 1);
        int pageSize = NumberUtils.toInt(req.get("pageSize"), 20);

        PageInfo<Map<String, Object>> pageInfo = buyerStatisticsService.applyBuyerList(pageNum, pageSize, req);
        Result<Object> result = new Result<>(pageInfo);
        return result;
    }

}
