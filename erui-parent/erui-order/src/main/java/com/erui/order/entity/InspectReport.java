package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * 仓库-入库-质检报告
 */
@Entity
@Table(name = "inspect_report")
public class InspectReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "inspect_apply_id")
    @JsonIgnore
    private InspectApply inspectApply;

    /**
     * 报检单号
     */
    @Column(name = "inspect_apply_no")
    private String inspectApplyNo;


    // 是否还在质检中 true:进行中  false:已结束
    private Boolean process;

    // 是否是报检单的第一次质检  true:第一次  false：非第一次
    @Column(name = "report_first")
    private Boolean reportFirst;


    /**
     * 质检员ID
     */
    @Column(name = "check_user_id")
    private Integer checkUserId;

    /**
     * 质检员名称
     */
    @Column(name = "check_user_name")
    private String checkUserName;

    /**
     * 质检部门ID
     */
    @Column(name = "check_dept_id")
    private Integer checkDeptId;

    /**
     * 质检部门名称
     */
    @Column(name = "check_dept_name")
    private String checkDeptName;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "ncr_no")
    private String ncrNo;

    // 检验日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "check_date")
    private Date checkDate;

    // 检验完成日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "done_date")
    private Date doneDate;

    // 最后的检验完成日期，取最后的报检日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "last_done_date")
    private Date lastDoneDate;

    // 报检日期 -- 列表中需要，来源：报检单-报检日期
    @Transient
    private Date inspectDate;

    @Column(name = "report_remarks")
    private String reportRemarks;

    private String msg;


    @Column(name = "check_times")
    private Integer checkTimes = 1;

    /**
     * 质检报告状态 0:未编辑 1:草稿/保存 2:质检完成
     */
    private Integer status ;

    @Column(name = "create_time")
    @JsonIgnore
    private Date createTime;

    /**
     * 是否显示 1显示 0不显示
     */
    @Column(name = "is_show")
    private Integer isShow ;


   /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "inspect_report_attach",
            joinColumns = @JoinColumn(name = "inspect_report_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))*/
   //INSTOCKQUALITY
   @Transient
    private List<Attachment> attachments = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "inspect_report_id")
    private List<InspectApplyGoods> inspectGoodsList = new ArrayList<>();


    // 销售合同号
    @Transient
    private String contractNo;
    // 采购合同号
    @Transient
    private String purchNo;
    // 项目号
    @Transient
    private String projectNo;
    // 是否外检
    @Transient
    private Boolean direct;
    @Transient
    private Integer createUserId;
    @Transient
    private String createUserName;

    @Transient
    private Integer inspectApplyID;  //到货报检单id

    @Transient
    private int page = 0;
    @Transient
    private int pageSize = 20;


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

    public String getInspectApplyNo() {
        return inspectApplyNo;
    }

    public void setInspectApplyNo(String inspectApplyNo) {
        this.inspectApplyNo = inspectApplyNo;
    }

    public Boolean getProcess() {
        return process;
    }

    public void setProcess(Boolean process) {
        this.process = process;
    }

    public Boolean getReportFirst() {
        return reportFirst;
    }

    public void setReportFirst(Boolean reportFirst) {
        this.reportFirst = reportFirst;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public Integer getCheckDeptId() {
        return checkDeptId;
    }

    public void setCheckDeptId(Integer checkDeptId) {
        this.checkDeptId = checkDeptId;
    }

    public String getCheckDeptName() {
        return checkDeptName;
    }

    public void setCheckDeptName(String checkDeptName) {
        this.checkDeptName = checkDeptName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public Date getLastDoneDate() {
        return lastDoneDate;
    }

    public void setLastDoneDate(Date lastDoneDate) {
        this.lastDoneDate = lastDoneDate;
    }

    public Date getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
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

    public Integer getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(Integer checkTimes) {
        this.checkTimes = checkTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Boolean getDirect() {
        return direct;
    }

    public void setDirect(Boolean direct) {
        this.direct = direct;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public List<InspectApplyGoods> getInspectGoodsList() {
        return inspectGoodsList;
    }

    public void setInspectGoodsList(List<InspectApplyGoods> inspectGoodsList) {
        this.inspectGoodsList = inspectGoodsList;
    }

    public Integer getInspectApplyID() {
        return inspectApplyID;
    }

    public void setInspectApplyID(Integer inspectApplyID) {
        this.inspectApplyID = inspectApplyID;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public static enum StatusEnum {
        INIT(0, "未编辑"), SAVED(1, "保存"), DONE(2, "已完成");


        private int code;
        private String msg;

        StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static StatusEnum fromCode(Integer code) {
            if (code != null) {
                int cd = code.intValue();
                for (StatusEnum se : StatusEnum.values()) {
                    if (se.getCode() == cd) {
                        return se;
                    }
                }
            }
            return null;
        }
    }
}