package com.erui.order.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 仓库-入库-质检报告
 */
@Entity
@Table(name = "inspect_report")
public class InspectReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "inspect_apply_id")
    private InspectApply inspectApply;

    // 是否还在质检中 true:进行中  false:已结束
    private boolean process = true;


    @Column(name = "check_user_id")
    private Integer checkUserId;

    /**
     * 质检部门ID
     */
    @Column(name = "check_dept_id")
    private Integer checkDeptId;

    @Column(name = "ncr_no")
    private String ncrNo;

    @Column(name = "check_date")
    private Date checkDate;

    @Column(name = "done_date")
    private Date doneDate;

    @Column(name = "report_remarks")
    private String reportRemarks;

    private String msg;


    private int checkTimes = 1;

    /**
     * 质检报告状态 0:未编辑 1:进行中可办理 2:质检完成 3:进行中不可办理
     */
    private int status = 0;

    @Column(name = "create_time")
    private Date createTime;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "inspect_report_attach",
            joinColumns = @JoinColumn(name = "inspect_report_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    private Set<Attachment> attachmentSet = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InspectApply getInspectApply() {
        return inspectApply;
    }

    public void setInspectApply(InspectApply inspectApply) {
        this.inspectApply = inspectApply;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public int getCheckTimes() {
        return checkTimes;
    }


    public void setCheckTimes(int checkTimes) {
        this.checkTimes = checkTimes;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Integer getCheckDeptId() {
        return checkDeptId;
    }

    public void setCheckDeptId(Integer checkDeptId) {
        this.checkDeptId = checkDeptId;
    }

    public String getNcrNo() {
        return ncrNo;
    }

    public void setNcrNo(String ncrNo) {
        this.ncrNo = ncrNo;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public String getReportRemarks() {
        return reportRemarks;
    }

    public void setReportRemarks(String reportRemarks) {
        this.reportRemarks = reportRemarks;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Set<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(Set<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }
}