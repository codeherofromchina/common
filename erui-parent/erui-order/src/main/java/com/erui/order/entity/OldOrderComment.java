package com.erui.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "old_order_comment")
public class OldOrderComment {
    @Id
    private Long id;

    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "comment_group")
    private String commentGroup;
    @Column(name = "is_read")
    private String isRead;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "created_by")
    private Long createdBy;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCommentGroup() {
        return commentGroup;
    }

    public void setCommentGroup(String commentGroup) {
        this.commentGroup = commentGroup;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}