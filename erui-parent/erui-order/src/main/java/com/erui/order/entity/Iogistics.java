package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//出库分单表

@Entity
@Table(name="logistics")
public class Iogistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name ="deliver_detail_id")
    @JsonIgnore
    private DeliverDetail deliverDetail;  //出库表id

    @Column(name="deliver_detail_no")
    private String deliverDetailNo; //产品放行单号

    @Column(name="logistics_no")
    private String logisticsNo; //物流单号

    @Column(name = "contract_no")
     private String contractNo; //销售合同号

    @Column(name = "logistics_user_id")
    private Integer logisticsUserId;    //物流经办人

    @Column(name = "logistics_user_name")
    private String logisticsUserName;    //物流经办人名称


    @Column(name = "deliver_consign_no")
    private String deliverConsignNo;  //出口通知单号

     @Column(name = "project_no")
      private String projectNo;  // 项目号

    @ManyToOne()
    @JoinColumn(name ="pid")
    @JsonIgnore
    private IogisticsData iogisticsData;    //父信息id

    @Column(name = "out_check")
    private Integer outCheck;  //是否外键（1：是 0：否）

    @Column(name = "out_yn")
    private Integer outYn = 0;  //是否已合并  （0：否  1：是）

    @Transient
    private String handleDepartment;    //经办部门

    @Transient
    private Date billingDate;    //开单日期

    @Transient
    private Date releaseDate;    //放行日期

    @Transient
    private String releaseDateList;    //放行日期List

    @Transient
    private Integer wareHouseman;    //仓库经办人id

    @Transient
    private Integer page;

    @Transient
    private Integer rows;



    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getRows() {
        return rows;
    }

    public void setIogisticsData(IogisticsData iogisticsData) {
        this.iogisticsData = iogisticsData;
    }

    public void setOutCheck(Integer outCheck) {
        this.outCheck = outCheck;
    }

    public Integer getId() {
        return id;
    }

    public DeliverDetail getDeliverDetailId() {
        return deliverDetail;
    }

    public void setDeliverDetailId(DeliverDetail deliverDetailId) {
        this.deliverDetail = deliverDetailId;
    }


    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public IogisticsData getIogisticsData() {
        return iogisticsData;
    }

    public Integer getOutCheck() {
        return outCheck;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public void setDeliverConsignNo(String deliverConsignNo) {
        this.deliverConsignNo = deliverConsignNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getDeliverConsignNo() {
        return deliverConsignNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public DeliverDetail getDeliverDetail() {
        return deliverDetail;
    }

    public String getHandleDepartment() {
        return handleDepartment;
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

    public void setDeliverDetail(DeliverDetail deliverDetail) {
        this.deliverDetail = deliverDetail;
    }

    public void setHandleDepartment(String handleDepartment) {
        this.handleDepartment = handleDepartment;
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

    public Integer getOutYn() {
        return outYn;
    }

    public String getReleaseDateList() {
        return releaseDateList;
    }

    public void setReleaseDateList(String releaseDateList) {
        this.releaseDateList = releaseDateList;
    }

    public void setOutYn(Integer outYn) {
        this.outYn = outYn;
    }

    public String getDeliverDetailNo() {
        return deliverDetailNo;
    }

    public void setDeliverDetailNo(String deliverDetailNo) {
        this.deliverDetailNo = deliverDetailNo;
    }


    public Integer getLogisticsUserId() {
        return logisticsUserId;
    }

    public void setLogisticsUserId(Integer logisticsUserId) {
        this.logisticsUserId = logisticsUserId;
    }

    public void setLogisticsUserName(String logisticsUserName) {
        this.logisticsUserName = logisticsUserName;
    }

    public String getLogisticsUserName() {
        return logisticsUserName;
    }
}
