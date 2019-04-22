package com.erui.order.requestVo;

/**
 * 审核订单请求对象
 * @Auther 王晓丹
 * @Date 2019/4/21 下午4:19
 */
public class AuditOrderRequestVo {
    private Integer type; // -1：驳回
    private Integer orderId; // 订单ID
    private String reason; // 批注，驳回时必须存在
    private String rejectNode; // 驳回节点
    private String taskId; // 实例的任务ID


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRejectNode() {
        return rejectNode;
    }

    public void setRejectNode(String rejectNode) {
        this.rejectNode = rejectNode;
    }

    /**
     * 是否是驳回
     * @return
     */
    public boolean isReject() {
        return type != null && type == -1;
    }
}
