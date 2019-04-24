package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.OrderConf;
import com.erui.order.service.OrderV2Service;
import com.erui.report.model.BpmTaskRuntime;
import com.erui.report.service.BpmTaskRuntimeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private OrderV2Service orderV2Service;

    /**
     * 任务完成通知，删除流程进度
     *
     * @param params
     */
    @RequestMapping(value = "onCompleted", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result onCompleted(@RequestBody Map<String, String> params) {
        // 验证安全性
        if (!validate(params.get("key"))) {
            // 如果秘钥不正确，什么也不返回
            return null;
        }
        Result<Object> result = new Result<>();
        // 检查参数
        String processInstanceId = params.get("processInstanceId");
        String taskId = params.get("taskId");
        String taskDefinitionKey = params.get("taskDefinitionKey");
        if (StringUtils.isAnyBlank(processInstanceId, taskId)) {
            result.setStatus(ResultStatusEnum.PARAM_ERROR);
        }

        orderV2Service.updateAuditProcessDone(processInstanceId,taskDefinitionKey);
        bpmTaskRuntimeService.delBpmTaskRuntime(processInstanceId, taskId);

        return result;
    }

    /**
     * 任务新建通知，新增流程进度
     */
    @RequestMapping(value = "onCreated", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result onCreated(@RequestBody BpmTaskRuntime bpmTaskRuntime) {
        // 验证安全性
        if (!validate(bpmTaskRuntime.getKey())) {
            // 如果秘钥不正确，什么也不返回
            return null;
        }

        orderV2Service.updateAuditProcessDoing(bpmTaskRuntime.getPiId(),bpmTaskRuntime.getActId(),bpmTaskRuntime.getTaskId());
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
