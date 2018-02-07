package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * 入库-记录
 */
@Entity
@Table(name = "instock")
public class Instock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 质检报告
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspect_report_id")
    @JsonIgnore
    private InspectReport inspectReport;

    @Column(name = "inspect_apply_no")
    private String inspectApplyNo;

    @Column(name = "supplier_name")
    private String supplierName;
    // 仓库经办人ID
    private Integer uid;

    // 仓库经办人名称
    private String uname;
    // 下发部门，和仓库经办人管理
    private String department;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "instock_date")
    private Date instockDate;   //入库日期

    private Integer status = 1;

    private String remarks;

    @Column(name = "create_time")
    @JsonIgnore
    private Date createTime;

    @Column(name = "current_user_id")
    private Integer currentUserId;
    @Column(name = "current_user_name")
    private String currentUserName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "instock_id")
    private List<InstockGoods> instockGoodsList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "instock_attach",
            joinColumns = @JoinColumn(name = "instock_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    private List<Attachment> attachmentList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInspectApplyNo() {
        return inspectApplyNo;
    }

    public void setInspectApplyNo(String inspectApplyNo) {
        this.inspectApplyNo = inspectApplyNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public InspectReport getInspectReport() {
        return inspectReport;
    }

    public void setInspectReport(InspectReport inspectReport) {
        this.inspectReport = inspectReport;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getInstockDate() {
        return instockDate;
    }

    public void setInstockDate(Date instockDate) {
        this.instockDate = instockDate;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<InstockGoods> getInstockGoodsList() {
        return instockGoodsList;
    }

    public void setInstockGoodsList(List<InstockGoods> instockGoodsList) {
        this.instockGoodsList = instockGoodsList;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public Integer getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Integer currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public static enum StatusEnum {
        INIT(1, "未编辑"), SAVED(2, "保存"), SUBMITED(3, "提交");
        private int status;
        private String msg;

        StatusEnum(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public static StatusEnum formStatus(Integer status) {
            if (status != null) {
                int s = status.intValue();
                for (StatusEnum se : StatusEnum.values()) {
                    if (se.getStatus() == status) {
                        return se;
                    }
                }
            }
            return null;
        }
    }
}