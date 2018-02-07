package com.erui.order.requestVo;

import java.util.Date;

public class DeliverW {

    private Integer id;

    private String deliverDetailNo; //产品放行单号

    private String contractNo; //销售合同号

    private Date releaseDate;   //放行日期

    private Integer logisticsUserId;    //物流经办人

    private Integer logisticsUid;    //国际物流经办人

    private Date logisticsDate; //物流经办时间

    private Integer status ; //状态

    private Integer page;

    private Integer rows;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setLogisticsUserId(Integer logisticsUserId) {
        this.logisticsUserId = logisticsUserId;
    }

    public void setLogisticsDate(Date logisticsDate) {
        this.logisticsDate = logisticsDate;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }


    public String getContractNo() {
        return contractNo;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Integer getLogisticsUserId() {
        return logisticsUserId;
    }

    public Date getLogisticsDate() {
        return logisticsDate;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDeliverDetailNo() {
        return deliverDetailNo;
    }

    public void setDeliverDetailNo(String deliverDetailNo) {
        this.deliverDetailNo = deliverDetailNo;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getLogisticsUid() {
        return logisticsUid;
    }

    public void setLogisticsUid(Integer logisticsUid) {
        this.logisticsUid = logisticsUid;
    }
}
