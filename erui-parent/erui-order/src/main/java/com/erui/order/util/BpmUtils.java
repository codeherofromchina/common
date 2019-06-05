package com.erui.order.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.OrderConf;
import com.erui.order.entity.CheckLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther 王晓丹
 * @Date 2019/4/21 上午11:07
 */
public class BpmUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BpmUtils.class);
    private static String bpmUrl = OrderConf.getInstance().getActivitiUrl();
    private static final String tenantId = "erui"; // 租户名称


    /**
     * 查询流程定义节点的所有UserTask节点列表
     *
     * @param definitionKey
     * @param token
     * @param callerId
     * @return
     */
    public static JSONArray processDefinitionUsertasks(String definitionKey, String token, String callerId) throws Exception {
        JSONObject params = new JSONObject();
        params.put("tenantId", tenantId);
        if (StringUtils.isBlank(callerId) && StringUtils.isNotBlank(token) && token.length() >= 6) {
            callerId = token.substring(token.length() - 6);
        }
        params.put("callerId", callerId);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=utf-8");
        header.put("Cookie", "eruitoken=" + token);

        String resp = HttpRequest.sendPost(bpmUrl + "/process/definition/" + definitionKey + "/userTasks", params.toJSONString(), header);
        LOGGER.info("查询流程定义节点的所有UserTask节点列表：{}", resp);
        JSONObject respJson = JSONObject.parseObject(resp);
        if (respJson != null && respJson.getInteger("code") == 0) {
            return respJson.getJSONArray("response");
        }
        throw new Exception("查询流程定义节点的所有UserTask节点异常");
    }

    /**
     * 启动流程实例
     *
     * @param definitionKey
     */
    public static JSONObject startProcessInstanceByKey(String definitionKey, String callerId, String token, String businessKey, Map<String, Object> initVar) throws Exception {
        JSONObject params = new JSONObject();
        params.put("tenantId", tenantId);
        if (StringUtils.isBlank(callerId) && StringUtils.isNotBlank(token) && token.length() >= 6) {
            callerId = token.substring(token.length() - 6);
        }
        params.put("callerId", StringUtils.isNoneBlank(callerId) ? callerId : null);
        params.put("definitionKey", definitionKey);
        params.put("bizKey", businessKey);
        if (initVar != null && initVar.size() > 0) {
            // 添加流程实例启动变量
            List<Map<String, Object>> variables = new ArrayList<>();
            for (Map.Entry<String, Object> entry : initVar.entrySet()) {
                Map<String, Object> var = new HashMap<>();
                var.put("name", entry.getKey());
                var.put("value", entry.getValue());
                var.put("scope", "GLOBAL");
                variables.add(var);
            }
            params.put("variables", variables);
        }

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=utf-8");
        header.put("Cookie", "eruitoken=" + token);

        String resp = HttpRequest.sendPost(bpmUrl + "/process/instance/start", params.toJSONString(), header);
        LOGGER.info("启动流程实例返回内容为：{}", resp);
        JSONObject respJson = JSONObject.parseObject(resp);
        if (respJson != null && respJson.getInteger("code") == 0) {
            return respJson.getJSONObject("response");
        }
        throw new Exception("业务流程启动失败");
    }


    /**
     * 终止正在执行的流程实例
     *
     * @param instanceId
     * @param token
     * @return
     */
    public static void stopProcessInstance(String instanceId, String token) {
        try {
            if (StringUtils.isBlank(instanceId)) {
                return;
            }
            JSONObject params = new JSONObject();
            params.put("tenantId", tenantId);
            String callerId;
            if (StringUtils.isNotBlank(token) && token.length() >= 6) {
                callerId = token.substring(token.length() - 6);
            } else {
                callerId = "";
            }
            params.put("callerId", callerId);
            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/json;charset=utf-8");
            header.put("Cookie", "eruitoken=" + token);

            String resp = HttpRequest.sendPost(bpmUrl + "/process/instance/" + instanceId + "/stop", params.toJSONString(), header);
            LOGGER.info("终止正在运行的流程实例返回内容为：{}", resp);
        }catch (Exception ex) {
            // 为不引入影响正常流程，不引入异常
            ex.printStackTrace();
        }
    }


    /**
     * 完成任务
     *
     * @param taskId
     * @param token
     * @param callerId
     * @param localVariables
     * @return
     */
    public static JSONObject completeTask(String taskId, String token, String callerId, Map<String, Object> localVariables, String comment) throws Exception {
        List<Map<String, Object>> variables = new ArrayList<>();
        if (localVariables != null && localVariables.size() > 0) {
            for (Map.Entry<String, Object> entry : localVariables.entrySet()) {
                Map<String, Object> var = new HashMap<>();
                var.put("scope", "LOCAL");
                var.put("name", entry.getKey());
                var.put("value", entry.getValue());
                variables.add(var);
            }
        }
        JSONObject params = new JSONObject();
        params.put("tenantId", tenantId);
        if (StringUtils.isBlank(callerId) && StringUtils.isNotBlank(token) && token.length() >= 6) {
            callerId = token.substring(token.length() - 6);
        }
        params.put("callerId", callerId);
        params.put("assigneeId", callerId);
        params.put("variables", variables);
        params.put("opinion", comment);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=utf-8");
        header.put("Cookie", "eruitoken=" + token);
        String resp = HttpRequest.sendPost(bpmUrl + "/task/instance/" + taskId + "/complete", params.toJSONString(), header);
        LOGGER.info("完成流程任务返回内容为：{}", resp);
        JSONObject respJson = JSONObject.parseObject(resp);
        if (respJson != null && respJson.getInteger("code") == 0) {
            return respJson;
        }
        throw new Exception("完成任务失败");
    }

    public static JSONObject processLogs(String processId, String token, String callerId) throws Exception {
        JSONObject params = new JSONObject();
        params.put("tenantId", tenantId);
        if (StringUtils.isBlank(callerId) && StringUtils.isNotBlank(token) && token.length() >= 6) {
            callerId = token.substring(token.length() - 6);
        }
        params.put("callerId", callerId);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=utf-8");
        header.put("Cookie", "eruitoken=" + token);

        String resp = HttpRequest.sendPost(bpmUrl + "/process/instance/" + processId + "/runtime", params.toJSONString(), header);
        LOGGER.info("完成流程任务返回内容为：{}", resp);
        JSONObject respJson = JSONObject.parseObject(resp);
        if (respJson != null && respJson.getInteger("code") == 0) {
            return respJson.getJSONObject("response");
        }
        throw new Exception("流程日志获取失败");
    }


    public static void main2(String[] args) throws Exception {
        bpmUrl = "http://bpm.beta.erui.com";
        Map<String, Object> localVariables = new HashMap<>();
        localVariables.put("audit_status", "APPROVED");
        JSONObject resp = completeTask("5a0b5c50-6417-11e9-b7ac-0242c0a80102", "", "017340", localVariables, "同意");
        System.out.println(resp);


    }


    public static void main(String[] args) throws Exception {
        bpmUrl = "http://bpm.beta.erui.com";
        String token = "862361785dbbda5c66b5dba278bc1a3a_018410";

        JSONObject jsonObject = processLogs("012f7207-85fc-11e9-b18f-0242ac120002", token, null);
        JSONArray data = jsonObject.getJSONArray("instanceLogs");
        List<CheckLog> result = null;
        if (data != null && data.size() > 0) {
            result = new ArrayList<>();
            for (int n = 0; n < data.size(); ++n) {
                CheckLog tmp = coverBpmLog2CheckLog(data.getJSONObject(n));
                result.add(tmp);
            }
        }

        System.out.println("---------------");
        System.out.println(JSONObject.toJSONString(result));
    }

    private static CheckLog coverBpmLog2CheckLog(JSONObject bpmLog) {
        CheckLog checkLog = new CheckLog();
        checkLog.setCreateTime(bpmLog.getDate("createTime"));
        Integer auditProcess = newCheckLog2oldCheckLogCodeMap.get(bpmLog.getString("taskDefKey"));
        checkLog.setAuditingProcess(auditProcess);
        if (auditProcess != null) {
            checkLog.setType(auditProcess/100);
        }
        checkLog.setAuditingUserName(bpmLog.getString("userName"));
        checkLog.setOperation(StringUtils.equalsIgnoreCase("APPROVED",bpmLog.getString("approvalResult"))?"2":"-1");
        return checkLog;
    }

    private static Map<String, Integer> newCheckLog2oldCheckLogCodeMap = new HashMap<String, Integer>(){{
        put("task_mm",Integer.valueOf(100)); // '完善订单信息'
        put("task_cm",Integer.valueOf(101)); // '国家负责人审核'
        put("task_rm",Integer.valueOf(102)); // '地区总经理审核'
        put("task_vp",Integer.valueOf(103)); // '分管领导审核'
        put("task_fn",Integer.valueOf(104)); // '融资负责人审核'
        put("task_la",Integer.valueOf(105)); // '法务负责人审核'
        put("task_fa",Integer.valueOf(106)); // '结算负责人审核'
        put("task_pm",Integer.valueOf(201)); // '事业部项目负责人审核'
        put("task_lg",Integer.valueOf(202)); // '物流经办人审核'
        put("task_pu",Integer.valueOf(204)); // '采购经办人审核'
        put("task_pc",Integer.valueOf(205)); // '品控经办人审核'
        put("task_gm",Integer.valueOf(206)); // '事业部总经理审核'
        put("task_ceo",Integer.valueOf(207)); // '总裁审核'
        put("task_ed",Integer.valueOf(208)); // '董事长审核'
    }};
}
