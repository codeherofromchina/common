package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 订单-项目信息
 */
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @OneToOne(mappedBy = "project", fetch = FetchType.LAZY)
    private PurchRequisition purchRequisition;

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "project_no")
    private String projectNo;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "start_date")
    private Date startDate;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "delivery_date")
    private Date deliveryDate;

    private BigDecimal profit;

    @Column(name = "currency_bn")
    private String currencyBn;

    @Column(name = "profit_percent")
    private BigDecimal profitPercent;

    @Column(name = "project_status")
    private String projectStatus;

    @Column(name = "purch_req_create")
    private Integer purchReqCreate;

    //是否采购完成，true：完成  false/null：未完成  根据此字段新增采购单
    @Column(name = "purch_done")
    private Boolean purchDone;

    @Column(name = "exe_chg_date")
    private Date exeChgDate;

    @Column(name = "require_purchase_date")
    private Date requirePurchaseDate;

    @Column(name = "has_manager")
    private Integer hasManager;

    @Column(name = "purchase_uid")
    private Integer purchaseUid;

    @Column(name = "quality_uid")
    private Integer qualityUid;

    @Column(name = "business_uid")
    private Integer businessUid;

    @Column(name = "manager_uid")
    private Integer managerUid;

    @Column(name = "logistics_uid")
    private Integer logisticsUid;

    @Column(name = "warehouse_uid")
    private Integer warehouseUid;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "exec_co_name")
    private String execCoName;

    @Column(name = "distribution_dept_name")
    private String distributionDeptName;

    @Column(name = "business_unit_name")
    private String businessUnitName;

    private String region;
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PurchRequisition getPurchRequisition() {
        return purchRequisition;
    }

    public void setPurchRequisition(PurchRequisition purchRequisition) {
        this.purchRequisition = purchRequisition;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(BigDecimal profitPercent) {
        this.profitPercent = profitPercent;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getPurchReqCreate() {
        return purchReqCreate;
    }

    public void setPurchReqCreate(Integer purchReqCreate) {
        this.purchReqCreate = purchReqCreate;
    }

    public Boolean getPurchDone() {
        return purchDone;
    }

    public void setPurchDone(Boolean purchDone) {
        this.purchDone = purchDone;
    }

    public Date getExeChgDate() {
        return exeChgDate;
    }

    public void setExeChgDate(Date exeChgDate) {
        this.exeChgDate = exeChgDate;
    }

    public Date getRequirePurchaseDate() {
        return requirePurchaseDate;
    }

    public void setRequirePurchaseDate(Date requirePurchaseDate) {
        this.requirePurchaseDate = requirePurchaseDate;
    }

    public Integer getHasManager() {
        return hasManager;
    }

    public void setHasManager(Integer hasManager) {
        this.hasManager = hasManager;
    }

    public Integer getPurchaseUid() {
        return purchaseUid;
    }

    public void setPurchaseUid(Integer purchaseUid) {
        this.purchaseUid = purchaseUid;
    }

    public Integer getQualityUid() {
        return qualityUid;
    }

    public void setQualityUid(Integer qualityUid) {
        this.qualityUid = qualityUid;
    }

    public Integer getBusinessUid() {
        return businessUid;
    }

    public void setBusinessUid(Integer businessUid) {
        this.businessUid = businessUid;
    }

    public Integer getManagerUid() {
        return managerUid;
    }

    public void setManagerUid(Integer managerUid) {
        this.managerUid = managerUid;
    }

    public Integer getLogisticsUid() {
        return logisticsUid;
    }

    public void setLogisticsUid(Integer logisticsUid) {
        this.logisticsUid = logisticsUid;
    }

    public Integer getWarehouseUid() {
        return warehouseUid;
    }

    public void setWarehouseUid(Integer warehouseUid) {
        this.warehouseUid = warehouseUid;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
    }

    public String getDistributionDeptName() {
        return distributionDeptName;
    }

    public void setDistributionDeptName(String distributionDeptName) {
        this.distributionDeptName = distributionDeptName;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean copyProjectDesc(Project project) {
        if (project == null) {
            return false;
        }
        project.setStartDate(this.startDate);
        project.setProjectName(this.projectName);
        project.setDeliveryDate(this.deliveryDate);
        project.setProfit(this.profit);
        project.setCurrencyBn(this.currencyBn);
        project.setProfitPercent(this.profitPercent);
        project.setHasManager(this.hasManager);
        project.setExeChgDate(this.exeChgDate);
        project.setRequirePurchaseDate(this.requirePurchaseDate);
        project.setPurchaseUid(this.purchaseUid);
        project.setQualityUid(this.qualityUid);
        project.setBusinessUid(this.businessUid);
        project.setManagerUid(this.managerUid);
        project.setLogisticsUid(this.logisticsUid);
        project.setWarehouseUid(this.warehouseUid);
        project.setRemarks(this.remarks);
        project.setProjectStatus(this.projectStatus);
        return true;
    }

    /**
     * 是否已经创建采购申请单枚举类
     */
    public static enum PurchReqCreateEnum {
        NOT_CREATE(0, "未创建"), CREATED(1, "已创建"), SUBMITED(2, "已提交");

        private int code;
        private String msg;

        PurchReqCreateEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }


}