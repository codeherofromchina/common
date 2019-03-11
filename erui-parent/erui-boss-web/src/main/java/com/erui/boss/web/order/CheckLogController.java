package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.CheckLog;
import com.erui.order.service.CheckLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by GS on 2017/12/15 0015.
 */
@RestController
@RequestMapping("order/checkLog")
public class CheckLogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckLogController.class);
    @Autowired
    private CheckLogService checkLogService;

    /**
     * @Author:SHIGS
     * @Description 获取审批流日志
     * @Date:20:09 2018/8/29
     * @modified By
     */
    @RequestMapping(value = "checkLogAll", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> checkLogAll(@RequestBody Map<String, Integer> map) {
        Integer orderId = null;
        if (map.containsKey("orderId")) {
            orderId = map.get("orderId");
        }
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        if (orderId == null || orderId < 0) {
            return result.setStatus(ResultStatusEnum.FAIL).setMsg("订单号错误");
        }
        List<CheckLog> listByOrderId = checkLogService.findListByOrderId(orderId);
        if (listByOrderId == null || listByOrderId.size() < 1) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(listByOrderId);
    }


    /**
     * 根据订单id查询(进行中/已完成)采购列表
     *
     * @param params {orderId:"订单ID"}
     * @return
     */
    @RequestMapping(value = "byPurchId", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> byPurchId(@RequestBody Map<String, String> params) {

        List<CheckLog> data = null;
        String category = params.get("category");
        Integer joinId = null;
        Integer type = null;
        if (params.containsKey("joinId") && params.get("joinId") != null) {
            joinId = Integer.parseInt(params.get("joinId"));
        }
        if (params.containsKey("type") && params.get("type") != null) {
            type = Integer.parseInt(params.get("type"));
        }

        if (joinId != null && type != null && joinId > 0) {
            try {
                data = checkLogService.findListByJoinId(category, joinId, type);
            } catch (Exception e) {
                LOGGER.error("错误", e);
                return new Result<>(ResultStatusEnum.FAIL);
            }
        } else {
            data = new ArrayList<>();
        }


        return new Result<>(data);
    }

    /**
     * @Author:SHIGS
     * @Description 获取审批流通过日志
     * @Date:20:09 2018/8/29
     * @modified By
     */
    @RequestMapping(value = "checkLogPassed", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> checkLogPassed(@RequestBody Map<String, Integer> map) {
        Integer orderId = null;
        if (map.containsKey("orderId")) {
            orderId = map.get("orderId");
        }
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        if (orderId == null || orderId < 0) {
            return result.setStatus(ResultStatusEnum.FAIL).setMsg("订单号错误");
        }
        List<CheckLog> listByOrderId = checkLogService.findPassed(orderId);
        if (listByOrderId == null || listByOrderId.size() < 1) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(listByOrderId);
    }
}
