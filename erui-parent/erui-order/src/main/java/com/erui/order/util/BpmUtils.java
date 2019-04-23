package com.erui.order.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.OrderConf;
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
//    private static String bpmUrl = OrderConf.getInstance().getActivitiUrl();
private static String bpmUrl = "";
    private static final String tenantId = "erui"; // 租户名称


    /**
     * 查询流程定义节点的所有UserTask节点列表
     *
     * @param definitionKey
     * @param token
     * @param user
     * @return
     */
    public static JSONArray processDefinitionUsertasks(String definitionKey, String token, String user) throws Exception {
        JSONObject params = new JSONObject();
        params.put("tenantId", tenantId);
        params.put("callerId", user);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
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
    public static JSONObject startProcessInstanceByKey(String definitionKey, String callerId, String token, String businessKey) throws Exception {
        JSONObject params = new JSONObject();
        params.put("tenantId", tenantId);
        params.put("callerId", callerId);
        params.put("definitionKey", definitionKey);
        params.put("bizKey", businessKey);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Cookie", "eruitoken=" + token);

        String resp = HttpRequest.sendPost(bpmUrl + "/process/instance/start", params.toJSONString(), header);
        LOGGER.info("启动流程实例返回内容为：{}", resp);
        JSONObject respJson = JSONObject.parseObject(resp);
        if (respJson != null && respJson.getInteger("code") == 0) {
            return respJson;
        }
        throw new Exception("业务流程启动失败");
    }

    /**
     * 完成任务
     *
     * @param taskId
     * @param token
     * @param userId
     * @param localVariables
     * @return
     */
    public static JSONObject completeTask(String taskId, String token, String userId, Map<String, Object> localVariables, String comment) throws Exception {
        List<Map<String, Object>> variables = new ArrayList<>();
        if (localVariables != null && localVariables.size() > 0) {
            for (Map.Entry<String, Object> entry : localVariables.entrySet()) {
                Map<String, Object> var = new HashMap<>();
                var.put("scope", "LOCAL");
                var.put("name", entry.getKey());
                var.put("value", entry.getValue());
            }
        }
        JSONObject params = new JSONObject();
        params.put("tenantId", tenantId);
        params.put("callerId", userId);
        params.put("assigneeId", userId);
        params.put("variables", variables);
        params.put("opinion", comment);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Cookie", "eruitoken=" + token);

        String resp = HttpRequest.sendPost(bpmUrl + "/task/instance/" + taskId + "/complete", params.toJSONString(), header);
        LOGGER.info("完成流程任务返回内容为：{}", resp);
        JSONObject respJson = JSONObject.parseObject(resp);
        if (respJson != null && respJson.getInteger("code") == 0) {
            return respJson;
        }
        throw new Exception("完成任务失败");
    }

    public static JSONObject processLogs(String processId, String token, String userId) throws Exception {
        JSONObject params = new JSONObject();
        params.put("tenantId", tenantId);
        params.put("callerId", userId);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Cookie", "eruitoken=" + token);

        String resp = HttpRequest.sendPost(bpmUrl + "/process/instance/" + processId + "/runtime", params.toJSONString(), header);
        LOGGER.info("完成流程任务返回内容为：{}", resp);
        JSONObject respJson = JSONObject.parseObject(resp);
        if (respJson != null && respJson.getInteger("code") == 0) {
            return respJson;
        }
        throw new Exception("流程日志获取失败");
    }


    public static void main2(String[] args) throws Exception {
        bpmUrl = "http://bpm.eruidev.com";
        Map<String, Object> localVariables = new HashMap<>();
        localVariables.put("audit_status", "APPROVED");
        localVariables.put("order_amount", 1000000);
        JSONObject resp = completeTask("5a0b5c50-6417-11e9-b7ac-0242c0a80102", "", "017340", localVariables, "同意");
        System.out.println(resp);


    }


    public static void main(String[] args) throws Exception {
        bpmUrl = "http://bpm.eruibpm.com";
//        JSONObject resp = processLogs("c1e035e1-56b0-11e9-8b8c-72d8a874a2b8", "", "017340");
//        System.out.println(resp);


        JSONArray process_order = processDefinitionUsertasks("process_order", "", "017340");
        System.out.println(process_order);
    }
}
