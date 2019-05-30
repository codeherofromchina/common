package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.pojo.TodoShowNeedContentRequest;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.OrderConf;
import com.erui.order.v2.model.DeliverConsign;
import com.erui.order.v2.model.Order;
import com.erui.order.v2.model.Purch;
import com.erui.order.v2.model.User;
import com.erui.order.v2.service.*;
import com.erui.report.model.BpmTaskRuntime;
import com.erui.report.service.BpmTaskRuntimeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;


    /**
     * 获取待办中要显示的必要业务信息
     *
     * @return
     */
    @RequestMapping(value = "todoShowNeedContent", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> todoShowNeedContent(@RequestBody TodoShowNeedContentRequest todoShowNeedContentRequest) {
        final Map<String, Object> data = new HashMap<>();
        Result<Object> result = new Result<>(data);
        // 验证安全性，// 如果秘钥不正确，返回失败
        if (!validate(todoShowNeedContentRequest.getKey())) {
            result.setStatus(ResultStatusEnum.FAIL);
            return result;
        }
        List<String> bizKeys = todoShowNeedContentRequest.getBizKeys();
        if (bizKeys == null || bizKeys.size() == 0) {
            result.setStatus(ResultStatusEnum.PARAM_ERROR);
            return result;
        }

        // 将业务标识分类
        final Map<String, List<Integer>> bizMap = new HashMap<>();
        bizKeys.stream().forEach(bizKey -> {
            String[] split = bizKey.split(":");
            if (split.length == 2) {
                if (StringUtils.isNumeric(split[1])) {
                    List<Integer> list = bizMap.get(split[0]);
                    if (list == null) {
                        list = new ArrayList<>();
                        bizMap.put(split[0], list);
                    }
                    list.add(Integer.parseInt(split[1]));
                }
            }
        });

        // 查询订单的采购号
        List<Integer> orderIds = bizMap.get("order");
        if (orderIds != null && orderIds.size() > 0) {
            Map<Integer, String> contractMap = orderService.findContractsByOrderIds(orderIds);
            for (Map.Entry<Integer, String> entry : contractMap.entrySet()) {
                data.put("order:" + entry.getKey(), entry.getValue());
            }
        }
        // 查询订舱号
        List<Integer> deliverConsignIds = bizMap.get("deliver_consign");
        if (deliverConsignIds != null && deliverConsignIds.size() > 0) {
            Map<Integer, String> deliverConsignNoMap = deliverConsignService.findDeliverConsignNoByDeliverConsignIds(deliverConsignIds);
            for (Map.Entry<Integer, String> entry : deliverConsignNoMap.entrySet()) {
                data.put("deliver_consign:" + entry.getKey(), entry.getValue());
            }
        }
        // 查询采购号
        List<Integer> purchIds = bizMap.get("purch");
        if (purchIds != null && purchIds.size() > 0) {
            Map<Integer, String> purchNoMap = purchService.findPurchNoByPurchIds(purchIds);
            for (Map.Entry<Integer, String> entry : purchNoMap.entrySet()) {
                data.put("purch:" + entry.getKey(), entry.getValue());
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
     * 任务领取通知，设置相应审核的当前审核人
     */
    @RequestMapping(value = "taskAssigned", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result taskAssigned(@RequestBody BpmTaskRuntime bpmTaskRuntime) {
        // 验证安全性
        if (!validate(bpmTaskRuntime.getKey())) {
            // 如果秘钥不正确，什么也不返回
            return new Result<>(ResultStatusEnum.FAIL);
        }
        if (StringUtils.isAnyBlank(bpmTaskRuntime.getAssignee(), bpmTaskRuntime.getBizType(), bpmTaskRuntime.getActId()) || bpmTaskRuntime.getBizObjId() == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }
        User user = userService.findUserNoByUserNo(bpmTaskRuntime.getAssignee());
        if (user == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        String actId = bpmTaskRuntime.getActId();

        if (StringUtils.equals("order", bpmTaskRuntime.getBizType())) {
            // 订单审核流程
            orderService.updateAuditUser(bpmTaskRuntime.getBizObjId(), user.getId(), user.getName(), actId);
            projectService.updateAuditUser(bpmTaskRuntime.getBizObjId(), user.getId(), user.getName(), actId);
        } else if (StringUtils.equals("deliver_consign", bpmTaskRuntime.getBizType())) {
            // 订舱审核流程
            deliverConsignService.updateAuditUser(bpmTaskRuntime.getBizObjId(), user.getId(), user.getName(), actId);
        } else if (StringUtils.equals("purch", bpmTaskRuntime.getBizType())) {
            // 采购审核流程
            purchService.updateAuditUser(bpmTaskRuntime.getBizObjId(), user.getId(), user.getName(), actId);
        }
        return new Result();
    }



    /**
     * 任务放回任务池通知，删除相应审核的当前审核人
     */
    @RequestMapping(value = "taskUnAssigned", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result taskUnAssigned(@RequestBody BpmTaskRuntime bpmTaskRuntime) {
        // 验证安全性

        if (!validate(bpmTaskRuntime.getKey())) {
            // 如果秘钥不正确，什么也不返回
            return new Result<>(ResultStatusEnum.FAIL);
        }
        if (StringUtils.isAnyBlank(bpmTaskRuntime.getBizType(), bpmTaskRuntime.getActId()) || bpmTaskRuntime.getBizObjId() == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }

        String actId = bpmTaskRuntime.getActId();
        if (StringUtils.equals("order", bpmTaskRuntime.getBizType())) {
            // 订单审核流程
            orderService.deleteAuditUser(bpmTaskRuntime.getBizObjId(), actId);
            projectService.deleteAuditUser(bpmTaskRuntime.getBizObjId(), actId);
        } else if (StringUtils.equals("deliver_consign", bpmTaskRuntime.getBizType())) {
            // 订舱审核流程
            deliverConsignService.deleteAuditUser(bpmTaskRuntime.getBizObjId(), actId);
        } else if (StringUtils.equals("purch", bpmTaskRuntime.getBizType())) {
            // 采购审核流程
            purchService.deleteAuditUser(bpmTaskRuntime.getBizObjId(), actId);
        }
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

    /**
     * 驳回出口通知单补偿授信额度
     *
     * @param params
     */
    @RequestMapping(value = "rejectDeliverConsign", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result rejectDeliverConsign(@RequestBody Map<String, String> params) {
        // 验证安全性，// 如果秘钥不正确，返回失败
        if (!validate(params.get("key"))) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        Result<Object> result = new Result<>();
        // 检查参数
        String eruiToken = params.get("eruiToken");
        if (StringUtils.isAnyBlank(eruiToken)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        ThreadLocalUtil.setObject(eruiToken);
        String id = params.get("id");
        if (StringUtils.isAnyBlank(id)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        try {
            if (id == null) {
                return new Result<>(ResultStatusEnum.FAIL).setMsg("id不能为空！");
            } else {
                deliverConsignService.rejectDeliverConsign(Integer.parseInt(id));
            }
        } catch (Exception e) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 驳回采购订单返还已采购数量
     *
     * @param params
     */
    @RequestMapping(value = "rejectPurch", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result rejectPurch(@RequestBody Map<String, String> params) {
        // 验证安全性，
        // 如果秘钥不正确，返回失败
        if (!validate(params.get("key"))) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        Result<Object> result = new Result<>();
        // 检查参数
        String id = params.get("id");
        if (StringUtils.isAnyBlank(id)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        try {
            if (id == null) {
                return new Result<>(ResultStatusEnum.FAIL).setMsg("id不能为空！");
            } else {
                purchService.rejectPurch(Integer.parseInt(id));
            }
        } catch (Exception e) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg(e.getMessage());
        }
        return result;
    }
}
