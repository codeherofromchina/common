package com.erui.order.requestVo;

import java.util.Date;
import java.util.UUID;

/**
 * 采购列表中的条件请求VO
 * Created by wangxiaodan on 2017/12/12.
 */
public class PurchListCondition {

    // 采购合同号
    private String purchNo;
    //采购经办人
    private Integer agentId;
    // 采购合同签约日期 只关心年月日
    private Date signingDate;
    //采购合同规定交货日期 只关心年月日
    private Date arrivalDate;
    //销售合同号
    private String contractNo;
    //项目号
    private String projectNo;
    //供应商名称
    private Integer supplierId;
    // 采购状态
    private Integer status ; //  采购单状态 0:未进行 1:进行中 2:已完成

    // 分页信息参数
    private int page = 0; // 默认从0开始
    private int rows = 20; // 默认每页20条记录

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PurchListCondition{" +
                "purchNo='" + purchNo + '\'' +
                ", agentId=" + agentId +
                ", signingDate=" + signingDate +
                ", arrivalDate=" + arrivalDate +
                ", contractNo='" + contractNo + '\'' +
                ", projectNo='" + projectNo + '\'' +
                ", supplierId=" + supplierId +
                ", status=" + status +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
