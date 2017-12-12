package com.erui.order.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 报检单-商品信息
 */
@Entity
@Table(name="inspect_apply_goods")
public class InspectApplyGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="inspect_apply_id")
    private InspectApply inspectApply;

    @OneToOne
    @JoinColumn(name="goods_id")
    private Goods goods;
    @Column(name = "inspect_num")
    private Integer inspectNum;
    @Column(name = "inspect_times")
    private Integer inspectTimes;

    private Integer samples;

    private Integer unqualified;
    @Column(name = "unqualified_desc")
    private String unqualifiedDesc;
    @Column(name = "instock_num")
    private Integer instockNum;


    @Column(name="user_id")
    private Integer userId;

    /**
     * 质检部门ID
     */
    @Column(name="dept_id")
    private Integer deptId;

    @Column(name="ncr_no")
    private String ncrNo;

    @Column(name="check_date")
    private Date checkDate;

    @Column(name="done_date")
    private Date doneDate;

    @Column(name="report_remarks")
    private String reportRemarks;

    private String msg;

    /**
     * 检验状态 0：未编辑 1：保存  2:合格，入库 3:不合格回退
     */
    private Integer status;
    @Column(name = "inspect_time")
    private Date inspectTime;

    @Column(name = "create_time")
    private Date createTime;

    private String remarks;

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

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getInspectNum() {
        return inspectNum;
    }

    public void setInspectNum(Integer inspectNum) {
        this.inspectNum = inspectNum;
    }

    public Integer getInspectTimes() {
        return inspectTimes;
    }

    public void setInspectTimes(Integer inspectTimes) {
        this.inspectTimes = inspectTimes;
    }

    public Integer getSamples() {
        return samples;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }

    public Integer getUnqualified() {
        return unqualified;
    }

    public void setUnqualified(Integer unqualified) {
        this.unqualified = unqualified;
    }

    public String getUnqualifiedDesc() {
        return unqualifiedDesc;
    }

    public void setUnqualifiedDesc(String unqualifiedDesc) {
        this.unqualifiedDesc = unqualifiedDesc;
    }

    public Integer getInstockNum() {
        return instockNum;
    }

    public void setInstockNum(Integer instockNum) {
        this.instockNum = instockNum;
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

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getMsg() {
        return msg;
    }

    public String getNcrNo() {
        return ncrNo;
    }

    public String getReportRemarks() {
        return reportRemarks;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public void setNcrNo(String ncrNo) {
        this.ncrNo = ncrNo;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setReportRemarks(String reportRemarks) {
        this.reportRemarks = reportRemarks;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(Date inspectTime) {
        this.inspectTime = inspectTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}