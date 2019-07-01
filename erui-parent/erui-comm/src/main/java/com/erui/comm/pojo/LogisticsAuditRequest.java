package com.erui.comm.pojo;



/**
 * 出口通知单物流经办人审核 请求实体
 * @Auther 王晓丹
 * @Date 2019-07-01
 */
public class LogisticsAuditRequest {
    private Integer deliverConsignId;
    private String taskId;
    private Long bookingOfficerId;
    private Long operationSpecialistId;

    public Integer getDeliverConsignId() {
        return deliverConsignId;
    }

    public void setDeliverConsignId(Integer deliverConsignId) {
        this.deliverConsignId = deliverConsignId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getBookingOfficerId() {
        return bookingOfficerId;
    }

    public void setBookingOfficerId(Long bookingOfficerId) {
        this.bookingOfficerId = bookingOfficerId;
    }

    public Long getOperationSpecialistId() {
        return operationSpecialistId;
    }

    public void setOperationSpecialistId(Long operationSpecialistId) {
        this.operationSpecialistId = operationSpecialistId;
    }
}
