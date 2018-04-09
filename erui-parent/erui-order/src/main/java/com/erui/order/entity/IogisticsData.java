package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

//物流表

@Entity
@Table(name="logistics_data")
public class IogisticsData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "the_awb_no")
    private String theAwbNo;    //运单号

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

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "accomplish_date")
    private Date accomplishDate; //实际完成时间

    private String logs;    //动态描述

    private String remarks; //备注

    private Integer status; //物流状态 5：合并出库信息 6：完善物流状态中 7：项目完结


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


    public Date getAccomplishDate() {
        return accomplishDate;
    }

    public void setAccomplishDate(Date accomplishDate) {
        this.accomplishDate = accomplishDate;
    }

    public String getReleaseDateList() {
        return releaseDateList;
    }

    public void setReleaseDateList(String releaseDateList) {
        this.releaseDateList = releaseDateList;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTheAwbNo() {
        return theAwbNo;
    }

    public void setTheAwbNo(String theAwbNo) {
        this.theAwbNo = theAwbNo;
    }
}
