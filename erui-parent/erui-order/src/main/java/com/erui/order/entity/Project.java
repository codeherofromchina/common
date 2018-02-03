package com.erui.order.entity;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.StringUtils;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单-项目信息
 */
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;
    @Transient
    private Integer oId;
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

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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

    @Column(name="quality_name")
    private String qualityName;

    @Column(name = "business_uid")
    private Integer businessUid;

    @Column(name = "manager_uid")
    private Integer managerUid;

    @Column(name = "logistics_uid")
    private Integer logisticsUid;

    @Column(name = "warehouse_uid")
    private Integer warehouseUid;


    @Column(name = "warehouse_name")
    private String warehouseName;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "exec_co_name")
    private String execCoName;
    //分销部名称
    @Column(name = "distribution_dept_name")
    private String distributionDeptName;
    //下发部门
    @Column(name = "send_dept_id")
    private Integer sendDeptId;
    @Column(name = "business_unit_name")
    private String businessUnitName;
    //商务技术经办人
    @Column(name = "business_name")
    private String businessName;
    //国际物流经办人
    @Column(name = "logistics_name")
    private String logisticsName;

    private String region;
    private String country;
    private String remarks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private List<Goods> goodsList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
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

    public String getQualityName() {
        return qualityName;
    }

    public void setQualityName(String qualityName) {
        this.qualityName = qualityName;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getDistributionDeptName() {
        return distributionDeptName;
    }

    public void setDistributionDeptName(String distributionDeptName) {
        this.distributionDeptName = distributionDeptName;
    }

    public Integer getSendDeptId() {
        return sendDeptId;
    }

    public void setSendDeptId(Integer sendDeptId) {
        this.sendDeptId = sendDeptId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
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
        project.setQualityName(this.qualityName);
        project.setBusinessUid(this.businessUid);
        project.setBusinessName(this.businessName);
        project.setQualityName(this.qualityName);
        project.setWarehouseName(this.warehouseName);
        project.setLogisticsName(this.logisticsName);
        project.setManagerUid(this.managerUid);
        project.setLogisticsUid(this.logisticsUid);
        project.setWarehouseUid(this.warehouseUid);
        project.setSendDeptId(this.sendDeptId);
        project.setRemarks(this.remarks);
        if (!this.getProjectStatus().equals("SUBMIT")){
            project.setProjectStatus(this.projectStatus);
        }
        return true;
    }

    /**
     * 是否已经创建采购申请单枚举类
     */
    public static enum PurchReqCreateEnum {
        NOT_CREATE(1, "未创建"), CREATED(2, "已创建"), SUBMITED(3, "已提交");

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


        public static PurchReqCreateEnum valueOfCode(Integer code) {
            if (code != null) {
                int codeInt = code.intValue();
                for (PurchReqCreateEnum reqEnum : PurchReqCreateEnum.values()) {
                    if (codeInt == reqEnum.getCode()) {
                        return reqEnum;
                    }
                }
            }
            return null;
        }
    }

    public static enum ProjectStatusEnum {
        HASMANAGER("HASMANAGER", "有项目经理"), SUBMIT("SUBMIT", "未执行"),
        EXECUTING("EXECUTING", "正常执行"), DONE("DONE", "正常完成"), DELAYED_EXECUTION("DELAYED_EXECUTION", "延期执行"),
        DELAYED_COMPLETE("DELAYED_COMPLETE", "延期完成"), UNSHIPPED("UNSHIPPED", "正常待发运"),
        DELAYED_UNSHIPPED("DELAYED_UNSHIPPED", "延期待发运"), PAUSE("PAUSE", "项目暂停"), CANCEL("CANCEL", "项目取消");
        private String code;
        private String msg;

        ProjectStatusEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }


        public static ProjectStatusEnum fromCode(String code) {
            if (StringUtils.isNotBlank(code)) {
                for (ProjectStatusEnum statusEnum : ProjectStatusEnum.values()) {
                    if (statusEnum.getCode().equals(code)) {
                        return statusEnum;

                    }
                }
            }
            return null;

        }
    }

}