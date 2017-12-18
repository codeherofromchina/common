package com.erui.order.requestVo;

import com.erui.order.entity.Attachment;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InspectReportVo {
    private Integer id;

    private Integer checkUserId; // 质检员ID
    private Integer checkDeptId; // 质检部门ID


    private String reportRemarks;

    private Integer status; // 1：保存  2：质检完成
    private Boolean unqualifiedFlag; // 是否不合格  true:不合格  false:合格

    private Date applyDate; // 报检日期

    private List<PGoods> pGoodsList = new ArrayList<>();

    private List<Attachment> attachments = new ArrayList<>();

    private int page = 0;

    private int pageSize = 20;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getReportRemarks() {
        return reportRemarks;
    }

    public void setReportRemarks(String reportRemarks) {
        this.reportRemarks = reportRemarks;
    }



    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getUnqualifiedFlag() {
        return unqualifiedFlag;
    }

    public void setUnqualifiedFlag(Boolean unqualifiedFlag) {
        this.unqualifiedFlag = unqualifiedFlag;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
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

    public List<PGoods> getpGoodsList() {
        return Collections.unmodifiableList(this.pGoodsList);
    }

    public void setpGoodsList(List<PGoods> pGoodsList) {
        this.pGoodsList = pGoodsList;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
