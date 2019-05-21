package com.erui.boss.web.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.pojo.CheckLogResponse;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.CheckLog;
import com.erui.order.service.CheckLogService;
import com.erui.order.util.BpmUtils;
import com.erui.order.v2.model.DeliverConsign;
import com.erui.order.v2.model.Order;
import com.erui.order.v2.model.Purch;
import com.erui.order.v2.service.DeliverConsignService;
import com.erui.order.v2.service.OrderService;
import com.erui.order.v2.service.PurchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private PurchService purchService;
    @Autowired
    private DeliverConsignService deliverConsignService;


    /**
     * @Author:SHIGS
     * @Description 获取审批流日志
     * @Date:20:09 2018/8/29
     * @modified By
     */
    @RequestMapping(value = "checkLogAll", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> checkLogAll(HttpServletRequest request, @RequestBody Map<String, Integer> map) {
        String eruiToken = CookiesUtil.getEruiToken(request);
        Integer orderId = null;
        if (map.containsKey("orderId")) {
            orderId = map.get("orderId");
        }
        Result<Object> result = new Result<>();
        if (orderId == null || orderId < 0) {
            return result.setStatus(ResultStatusEnum.FAIL).setMsg("订单号错误");
        }
        Order order = orderService.findOrderById(orderId);
        if (order == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        try {
            String processId = order.getProcessId();
            if (StringUtils.isBlank(processId)) {
                // 不存在processId，调用原来老审核历史
                List<CheckLog> listByOrderId = checkLogService.findListByOrderId(orderId);
                if (listByOrderId == null || listByOrderId.size() == 0) {
                    return new Result<>(new ArrayList<>());
                }
                List<CheckLogResponse> data = cover(listByOrderId);
                result.setData(data);
            } else {
                // 查询业务流中的审核日志信息
                JSONObject jsonObject = BpmUtils.processLogs(processId, eruiToken, null);
                JSONArray data = jsonObject.getJSONArray("instanceLogs");
                result.setData(data);
            }
        } catch (Exception ex) {
            LOGGER.error("错误", ex);
            return new Result<>(ResultStatusEnum.FAIL);
        }
        return result;
    }


    /**
     * 根据订单id查询(进行中/已完成)采购列表
     *
     * @param params {orderId:"订单ID"}
     * @return
     */
    @RequestMapping(value = "queryCheckLogs", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> byPurchId(HttpServletRequest request, @RequestBody Map<String, String> params) {
        String eruiToken = CookiesUtil.getEruiToken(request);
        String category = params.get("category");
        Integer joinId = null;
        Integer type = null;
        if (StringUtils.isNumeric(params.get("joinId"))) {
            joinId = Integer.parseInt(params.get("joinId"));
        }
        if (StringUtils.isNumeric(params.get("type"))) {
            type = Integer.parseInt(params.get("type"));
        }
        Result<Object> result = new Result<>();
        try {
            if (joinId != null && type != null && joinId > 0) {
                String processId = null;
                if (type == 3) {
                    // 通过标识查询采购信息，获取采购对应的业务流标识
                    Purch purch = purchService.selectById(joinId);
                    processId = purch.getProcessId();
                } else if (type == 4) {
                    // 通过标识查询出口通知单信息，获取采购对应的业务流标识
                    DeliverConsign deliverConsign = deliverConsignService.selectById(joinId);
                    processId = deliverConsign.getProcessId();
                } else {
                    return new Result<>(ResultStatusEnum.FAIL);
                }
                if (StringUtils.isBlank(processId)) {
                    List<CheckLog> data = checkLogService.findListByJoinId(category, joinId, type);
                    result.setData(cover(data));
                } else {
                    // 查询业务流中的审核日志信息
                    JSONObject jsonObject = BpmUtils.processLogs(processId, eruiToken, null);
                    JSONArray data = jsonObject.getJSONArray("instanceLogs");
                    result.setData(data);
                }
            } else {
                result.setData(new ArrayList<>());
            }
        } catch (Exception e) {
            LOGGER.error("错误", e);
            return new Result<>(ResultStatusEnum.FAIL);
        }
        return result;
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


    /**
     * 日志转换为返回的pojo
     *
     * @param checkLogs
     * @return
     */
    private List<CheckLogResponse> cover(List<CheckLog> checkLogs) {
        List<CheckLogResponse> result = new ArrayList<>();
        for (CheckLog checkLog : checkLogs) {
            result.add(cover(checkLog));
        }
        return result;
    }

    /**
     * 日志转换为返回的pojo
     *
     * @param checkLog
     * @return
     */
    private CheckLogResponse cover(CheckLog checkLog) {
        if (checkLog != null) {
            CheckLogResponse response = new CheckLogResponse();
            response.setEndTime(checkLog.getCreateTime());
            CheckLog.AuditProcessingEnum checkLogAuditProcessingEnum = CheckLog.AuditProcessingEnum.findEnum(checkLog.getType(), checkLog.getAuditingProcess());
            if (checkLogAuditProcessingEnum != null) {
                response.setTaskName(checkLogAuditProcessingEnum.getName());
            } else {
                response.setTaskName("--");
            }
            response.setUserName(checkLog.getAuditingUserName());
            // 1：立项   2：通过  -1：驳回
            String operation = checkLog.getOperation();
            if ("-1".equals(operation)) {
                response.setApprovalResult("REJECTED");
            } else {
                // 除了驳回都是通过
                response.setApprovalResult("APPROVED");
            }
            response.setOpinion(checkLog.getAuditingMsg());
            return response;
        }
        return null;
    }
}
