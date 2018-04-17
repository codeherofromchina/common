package com.erui.order.requestVo;

import java.util.Date;


/**
 * 看货通知单前台传参
 */
public class DeliverD {


    private String deliverDetailNo; //产品放行单号

    private String contractNo; //销售合同号

    private String projectNo;    //项目号

    private Date billingDate;   //开单日期

    private Date releaseDate; //放行日期

    private Integer wareHouseman;   //仓库经办人

    private Integer status;   //仓库出库管理状态

    private Date leaveDate; //出库时间

    private Integer page;

    private Integer rows;

    public String getDeliverDetailNo() {
        return deliverDetailNo;
    }

    public void setDeliverDetailNo(String deliverDetailNo) {
        this.deliverDetailNo = deliverDetailNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Integer getWareHouseman() {
        return wareHouseman;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setWareHouseman(Integer wareHouseman) {
        this.wareHouseman = wareHouseman;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }
}
