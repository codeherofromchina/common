package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.pojo.TodoShowNeedContentRequest;
import com.erui.order.OrderConf;
import com.erui.order.v2.model.Order;
import com.erui.order.v2.service.DeliverConsignService;
import com.erui.order.v2.service.OrderService;
import com.erui.order.v2.service.ProjectService;
import com.erui.order.v2.service.PurchService;
import com.erui.report.model.BpmTaskRuntime;
import com.erui.report.service.BpmTaskRuntimeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/bpmnotify")
public class BpmNotifyController {
    @Autowired
    private OrderConf orderConf;
    @Autowired
    private BpmTaskRuntimeService bpmTaskRuntimeService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DeliverConsignService deliverConsignService;
    @Autowired
    private PurchService purchService;

    /**
     * 获取待办中要显示的必要业务信息
     * @return
     */
    @RequestMapping(value = "todoShowNeedContent", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> todoShowNeedContent(@RequestBody TodoShowNeedContentRequest todoShowNeedContentRequest) {
        Map<String, Object> data = new HashMap<>();
        Result<Object> result = new Result<>(data);
        // 验证安全性，// 如果秘钥不正确，返回失败
        if (!validate(todoShowNeedContentRequest.getKey())) {
            result.setStatus(ResultStatusEnum.FAIL);
            return result;
        }
        if (StringUtils.isBlank(todoShowNeedContentRequest.getBizKey())) {
            result.setStatus(ResultStatusEnum.PARAM_ERROR);
            return result;
        }

        if (todoShowNeedContentRequest.getBizKey().startsWith("order:")) {
            // 订单审核流程
            String orderId = todoShowNeedContentRequest.getBizKey().substring(6);
            if (StringUtils.isNumeric(orderId)) {
                Order order = orderService.findOrderById(Integer.parseInt(orderId));
                if (order != null && StringUtils.isNotBlank(order.getContractNo())) {
                    data.put("needContent", order.getContractNo());
                }
            }
        }

        if (data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }

        return result;
    }

    /**
     * 任务完成通知，删除流程进度
     *
     * @param params
     */
    @RequestMapping(value = "taskCompleted", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result taskCompleted(@RequestBody Map<String, String> params) {
        // 验证安全性，// 如果秘钥不正确，返回失败
        if (!validate(params.get("key"))) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        Result<Object> result = new Result<>();
        // 检查参数
        String processInstanceId = params.get("processInstanceId");
        String taskId = params.get("taskId");
        String taskDefinitionKey = params.get("taskDefinitionKey");
        String businessKey = params.get("businessKey");
        String assignee = params.get("assignee");
        if (StringUtils.isAnyBlank(businessKey, processInstanceId, taskId)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        if (businessKey.startsWith("order:")) {
            // 订单审核流程
            orderService.updateAuditProcessDone(processInstanceId, taskDefinitionKey, assignee);
            projectService.updateAuditProcessDone(processInstanceId, taskDefinitionKey, assignee);
        } else if (businessKey.startsWith("deliver_consign:")) {
            // 订舱审核流程
            deliverConsignService.updateAuditProcessDone(processInstanceId, taskDefinitionKey, assignee);
        } else if (businessKey.startsWith("purch:")) {
            // 采购审核流程
            purchService.updateAuditProcessDone(processInstanceId, taskDefinitionKey, assignee);
        }
        bpmTaskRuntimeService.delBpmTaskRuntime(processInstanceId, taskId);
        return result;
    }



    /**
     * 业务流实例完成通知
     *
     * @param params
     */
    @RequestMapping(value = "processCompleted", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result completedCompleted(@RequestBody Map<String, String> params) {
        // 验证安全性，// 如果秘钥不正确，返回失败
        if (!validate(params.get("key"))) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        Result<Object> result = new Result<>();
        // 检查参数
        String processInstanceId = params.get("processInstanceId");
        String businessKey = params.get("businessKey");
        if (StringUtils.isAnyBlank(businessKey, processInstanceId)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        if (businessKey.startsWith("order:")) {
            // 订单审核流程
            orderService.updateProcessCompleted(processInstanceId);
            projectService.updateProcessCompleted(processInstanceId);
        } else if (businessKey.startsWith("deliver_consign:")) {
            // 订舱审核流程
            deliverConsignService.updateProcessCompleted(processInstanceId);
        } else if (businessKey.startsWith("purch:")) {
            // 采购审核流程
            purchService.updateProcessCompleted(processInstanceId);
        }
        return result;
    }

    /**
     * 任务新建通知，新增流程进度
     */
    @RequestMapping(value = "taskCreated", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result taskCreated(@RequestBody BpmTaskRuntime bpmTaskRuntime) {
        // 验证安全性
        if (!validate(bpmTaskRuntime.getKey())) {
            // 如果秘钥不正确，什么也不返回
            return new Result<>(ResultStatusEnum.FAIL);
        }
        if (StringUtils.equals("order", bpmTaskRuntime.getBizType())) {
            // 订单审核流程
            orderService.updateAuditProcessDoing(bpmTaskRuntime.getPiId(), bpmTaskRuntime.getActId(), bpmTaskRuntime.getTaskId());
            projectService.updateAuditProcessDoing(bpmTaskRuntime.getPiId(), bpmTaskRuntime.getActId(), bpmTaskRuntime.getTaskId());
        } else if (StringUtils.equals("deliver_consign", bpmTaskRuntime.getBizType())) {
            // 订舱审核流程
            deliverConsignService.updateAuditProcessDoing(bpmTaskRuntime.getPiId(), bpmTaskRuntime.getActId(), bpmTaskRuntime.getTaskId());
        } else if (StringUtils.equals("purch", bpmTaskRuntime.getBizType())) {
            // 采购审核流程
            purchService.updateAuditProcessDoing(bpmTaskRuntime.getPiId(), bpmTaskRuntime.getActId(), bpmTaskRuntime.getTaskId());
        }
        bpmTaskRuntimeService.addBpmTaskRuntime(bpmTaskRuntime);
        return new Result();
    }


    /**
     * 验证秘钥是否一样
     *
     * @param key
     * @return
     */
    private boolean validate(String key) {
        return StringUtils.equalsIgnoreCase(key, orderConf.getBpmKey());
    }
}
