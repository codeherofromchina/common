package com.erui.comm.pojo;



/**
 * 出口通知单订舱专员审核 请求实体
 * @Auther 王晓丹
 * @Date 2019/6/12 下午5:15
 */
public class BookingSpaceAuditRequest {
    private Integer deliverConsignId;
    private String taskId;
    private AttachmentVo attachment;


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

    public AttachmentVo getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentVo attachment) {
        this.attachment = attachment;
    }
}
