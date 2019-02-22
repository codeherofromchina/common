package com.erui.order.requestVo;

import com.erui.comm.NewDateUtil;
import com.erui.order.entity.Attachment;
import com.erui.order.entity.Project;
import com.erui.order.entity.PurchGoods;
import com.erui.order.entity.PurchPayment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采购审批
 */
public class PurchParam {
    private Integer id;
    // 项目审核接口中使用，审核的原因字段
    private String auditingReason;
    // 项目审核接口中使用，审核的类型字段，审核类型：-1：驳回（驳回必须存在驳回原因参数） 其他或空：正常审核
    private String auditingType;
    // 审核日志，驳回操作使用
    private Integer checkLogId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuditingReason() {
        return auditingReason;
    }

    public void setAuditingReason(String auditingReason) {
        this.auditingReason = auditingReason;
    }

    public String getAuditingType() {
        return auditingType;
    }

    public void setAuditingType(String auditingType) {
        this.auditingType = auditingType;
    }

    public Integer getCheckLogId() {
        return checkLogId;
    }

    public void setCheckLogId(Integer checkLogId) {
        this.checkLogId = checkLogId;
    }
}