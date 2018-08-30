package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.CheckLog;
import com.erui.order.service.CheckLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by GS on 2017/12/15 0015.
 */
@RestController
@RequestMapping("order/checkLog")
public class CheckLogController {
    private final static Logger logger = LoggerFactory.getLogger(CheckLogController.class);
    @Autowired
    private CheckLogService checkLogService;

    /**
     * @Author:SHIGS
     * @Description 获取审批流日志
     * @Date:20:09 2018/8/29
     * @modified By
     */
    @RequestMapping(value = "checkLogAll", method = RequestMethod.GET)
    public Result<Object> checkLogAll(Integer orderId) {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        if (orderId == null || orderId < 0) {
            return result.setStatus(ResultStatusEnum.FAIL).setMsg("订单号错误");
        }
        List<CheckLog> listByOrderId = checkLogService.findListByOrderId(orderId);
        if (listByOrderId.size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(listByOrderId);
    }

    /**
     * @Author:SHIGS
     * @Description 获取审批流通过日志
     * @Date:20:09 2018/8/29
     * @modified By
     */
    @RequestMapping(value = "checkLogPassed", method = RequestMethod.GET)
    public Result<Object> checkLogPassed(Integer orderId) {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        if (orderId == null || orderId < 0) {
            return result.setStatus(ResultStatusEnum.FAIL).setMsg("订单号错误");
        }
        List<CheckLog> listByOrderId = checkLogService.findPassed(orderId);
        if (listByOrderId.size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(listByOrderId);
    }
}
