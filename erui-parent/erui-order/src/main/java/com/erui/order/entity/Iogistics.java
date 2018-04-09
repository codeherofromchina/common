package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;

//物流表

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

    @Column(name="logistics_no")
    private String logisticsNo; //物流单号

    @Column(name = "contract_no")
     private String contractNo; //销售合同号

    @Column(name = "deliver_consign_no")
    private String deliverConsignNo;  //出口通知单号

     @Column(name = "project_no")
      private String projectNo;  // 项目号

    @Column(name = "logistics_user_id")
    private Integer logisticsUserId;    //物流经办人

    @Column(name = "logistics_user_name")
    private String logisticsUserName;    //物流经办人名称

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "logistics_date")
    private Date logisticsDate; //物流经办时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "booking_time")
    private Date bookingTime;   //订舱时间

    @Column(name = "logi_invoice_no")
    private String logiInvoiceNo;   //物流发票号

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "packing_time")
    private Date packingTime;   //通知市场箱单时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "leave_factory")
    private Date leaveFactory;  //离厂时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "sailing_date")
    private Date sailingDate;   //船期或航班

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "customs_clearance")
    private Date customsClearance;  //报关放行时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "leave_port_time")
    private Date leavePortTime; //实际离港时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "arrival_port_time")
    private Date arrivalPortTime;   //预计抵达时间

    private String logs;    //动态描述

    private String remarks; //备注

    private Integer status; //物流状态 5：确认出库 6：完善物流状态中 7：项目完结

    private Integer pid;    //父信息id

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

    public void setLogisticsUserId(Integer logisticsUserId) {
        this.logisticsUserId = logisticsUserId;
    }

    public void setLogisticsUserName(String logisticsUserName) {
        this.logisticsUserName = logisticsUserName;
    }

    public void setLogisticsDate(Date logisticsDate) {
        this.logisticsDate = logisticsDate;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setLogiInvoiceNo(String logiInvoiceNo) {
        this.logiInvoiceNo = logiInvoiceNo;
    }

    public void setPackingTime(Date packingTime) {
        this.packingTime = packingTime;
    }

    public void setLeaveFactory(Date leaveFactory) {
        this.leaveFactory = leaveFactory;
    }

    public void setSailingDate(Date sailingDate) {
        this.sailingDate = sailingDate;
    }

    public void setCustomsClearance(Date customsClearance) {
        this.customsClearance = customsClearance;
    }

    public void setLeavePortTime(Date leavePortTime) {
        this.leavePortTime = leavePortTime;
    }

    public void setArrivalPortTime(Date arrivalPortTime) {
        this.arrivalPortTime = arrivalPortTime;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public Integer getLogisticsUserId() {
        return logisticsUserId;
    }

    public String getLogisticsUserName() {
        return logisticsUserName;
    }

    public Date getLogisticsDate() {
        return logisticsDate;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public String getLogiInvoiceNo() {
        return logiInvoiceNo;
    }

    public Date getPackingTime() {
        return packingTime;
    }

    public Date getLeaveFactory() {
        return leaveFactory;
    }

    public Date getSailingDate() {
        return sailingDate;
    }

    public Date getCustomsClearance() {
        return customsClearance;
    }

    public Date getLeavePortTime() {
        return leavePortTime;
    }

    public Date getArrivalPortTime() {
        return arrivalPortTime;
    }

    public String getLogs() {
        return logs;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getPid() {
        return pid;
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

    public void setOutYn(Integer outYn) {
        this.outYn = outYn;
    }
}
