package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.beans.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 订单-项目信息
 */
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "order_id")
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
    @Size(max = 255, message = "合同标的填写不规范，请重新输入")
    private String projectName;

    @Column(name = "start_date")//0307修改时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;
    //20180711因事业部订单导入需要修改为文本格式
    //@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "delivery_date")
    private String deliveryDate;

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

    @Column(name = "purchase_name") //采购经办人名称
    private String purchaseName;

    @Column(name = "quality_uid")
    private Integer qualityUid;

    @Column(name = "quality_name")
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
    //删除字段 暂未删除
    @Column(name = "exec_co_name")
    private String execCoName;
    //分销部名称 删除
    @Column(name = "distribution_dept_name")
    private String distributionDeptName;
    //下发部门
    @Column(name = "send_dept_id")
    private Integer sendDeptId;
    //事业部id
    /*@Column(name = "business_unit_id")
    private Integer businessNnitId;*/
    @Column(name = "business_unit_name")
    private String businessUnitName;
    //商务技术经办人
    @Column(name = "business_name")
    private String businessName;
    //国际物流经办人
    @Column(name = "logistics_name")
    private String logisticsName;
    //合同总价（美元）
    @Column(name = "total_price_usd")
    private BigDecimal totalPriceUsd;
    @Transient
    private String region;
    //删除字段
    private String country;
    private String remarks;
    //流程进度
    @Column(name = "process_progress")
    private String processProgress;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private List<Goods> goodsList = new ArrayList<>();
    //是否已生成出口通知单 删除字段
    @Column(name = "deliver_consign_has")
    private Integer deliverConsignHas;
    //订单类别 1预投 2 售后 3 试用 4 现货（出库） 5 订单 6 国内订单
    @Column(name = "order_category")
    private Integer orderCategory;

    //海外销售类型 1 海外销（装备采购） 2 海外销（易瑞采购） 3 海外销（当地采购） 4 易瑞销 5  装备销
    @Column(name = "overseas_sales")
    private Integer overseasSales;
    @Column(name = "purch_time")
    private Date purchTime;
    //采购
    @ManyToMany(targetEntity = Purch.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "purch_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "purch_id"))
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<Purch> purchs = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "project")
    private ProjectProfit projectProfit;
    /**
     * 审核人列表信息
     */
    @Column(name = "audi_remark")
    private String audiRemark;

    @Column(name = "logistics_audit")
    private Integer logisticsAudit; //'是否需要物流审核  1/其他:不需要  2：需要',

    @Column(name = "auditing_level")
    private Integer auditingLevel;  //'审批分级    2、3、4级'

    // 0：为驳回到订单  2：审核中 3：驳回到项目
    @Column(name = "auditing_status")
    private Integer auditingStatus; //'审核状态 --   添加一个驳回到订单中的状态（驳回1、驳回2）',

    @Column(name = "auditing_process")
    private String auditingProcess;//'-- 审核进度',

    @Column(name = "auditing_user_id")
    private String auditingUserId;//'当前审核人ID',

    @Column(name = "auditing_user")
    private String auditingUser;//'当前审核人',

    @Column(name = "logistics_auditer_id")
    private Integer logisticsAuditerId;//'物流审核人id',

    @Column(name = "logistics_auditer")
    private String logisticsAuditer; //'物流审核人',

    @Column(name = "bu_auditer_id")
    private Integer buAuditerId;      //'事业部审核人id',

    @Column(name = "bu_auditer")
    private String buAuditer;         //'事业部审核人',

    @Column(name = "bu_vp_auditer")
    private String buVpAuditer;      //'事业部vp审核人/ 总经理经办人',

    @Column(name = "bu_vp_auditer_id")
    private Integer buVpAuditerId;   //'事业部vp审核人/总经理经办人',

    @Column(name = "ceo_id")
    private Integer ceoId;             //'ceo审核人id',

    private String ceo;                 //  'ceo审核人',

    @Column(name = "chairman_id")
    private Integer chairmanId;        //董事长审核人id

    private String chairman;           //董事长审核人
    /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     @JoinTable(name = "project_attach",
             joinColumns = @JoinColumn(name = "project_id"),
             inverseJoinColumns = @JoinColumn(name = "attach_id"))
     @JsonInclude(JsonInclude.Include.NON_DEFAULT)*/
    @Transient
    private List<Attachment> attachmentList = new ArrayList<>();
    /**
     * 质量检验类型
     */
    @Column(name = "quality_inspect_type")
    private String qualityInspectType;

    public String getQualityInspectType() {
        return qualityInspectType;
    }

    public void setQualityInspectType(String qualityInspectType) {
        this.qualityInspectType = qualityInspectType;
    }

    // 流程实例ID
    @Column(name="process_id")
    private String processId;
    @Column(name="task_id")
    private String taskId;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public ProjectProfit getProjectProfit() {
        return projectProfit;
    }

    public void setProjectProfit(ProjectProfit projectProfit) {
        this.projectProfit = projectProfit;
    }

    public Integer getLogisticsAudit() {
        return logisticsAudit;
    }

    public void setLogisticsAudit(Integer logisticsAudit) {
        this.logisticsAudit = logisticsAudit;
    }

    public Integer getAuditingLevel() {
        return auditingLevel;
    }

    public void setAuditingLevel(Integer auditingLevel) {
        this.auditingLevel = auditingLevel;
    }

    public Integer getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(Integer auditingStatus) {
        this.auditingStatus = auditingStatus;
    }

    public String getAuditingProcess() {
        return auditingProcess;
    }

    public void setAuditingProcess(String auditingProcess) {
        this.auditingProcess = auditingProcess;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId;
    }

    public String getAuditingUser() {
        return auditingUser;
    }

    public void setAuditingUser(String auditingUser) {
        this.auditingUser = auditingUser;
    }

    public Integer getLogisticsAuditerId() {
        return logisticsAuditerId;
    }

    public void setLogisticsAuditerId(Integer logisticsAuditerId) {
        this.logisticsAuditerId = logisticsAuditerId;
    }

    public String getLogisticsAuditer() {
        return logisticsAuditer;
    }

    public void setLogisticsAuditer(String logisticsAuditer) {
        this.logisticsAuditer = logisticsAuditer;
    }

    public Integer getBuAuditerId() {
        return buAuditerId;
    }

    public void setBuAuditerId(Integer buAuditerId) {
        this.buAuditerId = buAuditerId;
    }

    public String getBuAuditer() {
        return buAuditer;
    }

    public void setBuAuditer(String buAuditer) {
        this.buAuditer = buAuditer;
    }

    public String getBuVpAuditer() {
        return buVpAuditer;
    }

    public void setBuVpAuditer(String buVpAuditer) {
        this.buVpAuditer = buVpAuditer;
    }

    public Integer getBuVpAuditerId() {
        return buVpAuditerId;
    }

    public void setBuVpAuditerId(Integer buVpAuditerId) {
        this.buVpAuditerId = buVpAuditerId;
    }

    public Integer getCeoId() {
        return ceoId;
    }

    public void setCeoId(Integer ceoId) {
        this.ceoId = ceoId;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public Integer getChairmanId() {
        return chairmanId;
    }

    public void setChairmanId(Integer chairmanId) {
        this.chairmanId = chairmanId;
    }

    public String getChairman() {
        return chairman;
    }

    public void setChairman(String chairman) {
        this.chairman = chairman;
    }

    public Date getPurchTime() {
        return purchTime;
    }

    public void setPurchTime(Date purchTime) {
        this.purchTime = purchTime;
    }

    public Integer getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(Integer orderCategory) {
        this.orderCategory = orderCategory;
    }

    public Integer getOverseasSales() {
        return overseasSales;
    }

    public void setOverseasSales(Integer overseasSales) {
        this.overseasSales = overseasSales;
    }

    public Integer getDeliverConsignHas() {
        return deliverConsignHas;
    }

    public void setDeliverConsignHas(Integer deliverConsignHas) {
        this.deliverConsignHas = deliverConsignHas;
    }

    public String getProcessProgress() {
        return processProgress;
    }

    public String getProcessProgressName() {
        ProjectProgressEnum projectProgressEnum = ProjectProgressEnum.ProjectProgressFromCode(getProcessProgress());
        if (projectProgressEnum != null) {
            return projectProgressEnum.getMsg();
        }
        return null;
    }

    /*  public String getEnProcessProgressName() {
          Project.enProjectProgressEnum enProjectProgressEnum = Project.enProjectProgressEnum.enProjectProgressFromCode(getProcessProgress());
          if (enProjectProgressEnum != null) {
              return enProjectProgressEnum.getMsg();
          }
          return null;
      }
  */
    public void setProcessProgress(String processProgress) {
        this.processProgress = processProgress;
    }

    public BigDecimal getTotalPriceUsd() {
        return totalPriceUsd;
    }

    public void setTotalPriceUsd(BigDecimal totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
    }

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
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

    public String getProjectStatusName() {
        ProjectStatusEnum statusEnum = null;
        if (StringUtils.equals(getProjectStatus(), "HASMANAGER")) {
            statusEnum = ProjectStatusEnum.fromCode("SUBMIT");
        } else {
            statusEnum = ProjectStatusEnum.fromCode(getProjectStatus());
        }
        if (statusEnum != null) {
            return statusEnum.getMsg();
        }
        return null;
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

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public List<Purch> getPurchs() {
        return purchs;
    }

    public void setPurchs(List<Purch> purchs) {
        this.purchs = purchs;
    }

    public boolean copyProjectDescTo(Project project) {
        if (project == null) {
            return false;
        }
        project.setStartDate(this.startDate);
        project.setProjectName(this.projectName);
        project.setDeliveryDate(this.deliveryDate);
        project.setProfit(this.profit);
        project.setCurrencyBn(this.currencyBn);
        project.setProfitPercent(this.profitPercent);
        project.setTotalPriceUsd(this.totalPriceUsd);
        project.setHasManager(this.hasManager);
        if (project.exeChgDate == null) {
            project.setExeChgDate(this.exeChgDate);
        }
        project.setRequirePurchaseDate(this.requirePurchaseDate);
        project.setPurchaseUid(this.purchaseUid);
        project.setPurchaseName(this.purchaseName);
        project.setQualityUid(this.qualityUid);
        project.setQualityName(this.qualityName);
        //project.setBusinessUid(this.businessUid);
        //project.setBusinessName(this.businessName);
        project.setQualityName(this.qualityName);
        project.setWarehouseName(this.warehouseName);
        project.setLogisticsName(this.logisticsName);
        project.setManagerUid(this.managerUid);
        project.setLogisticsUid(this.logisticsUid);
        project.setWarehouseUid(this.warehouseUid);
        project.setSendDeptId(this.sendDeptId);
        project.setRemarks(this.remarks);
        if (!"AUDIT".equals(this.projectStatus)) {
            project.setProjectStatus(this.projectStatus);
        }
        project.setLogisticsAudit(this.logisticsAudit);
        project.setLogisticsAuditerId(this.getLogisticsAuditerId());
        project.setLogisticsAuditer(this.logisticsAuditer);
        project.setBuAuditerId(this.buAuditerId);
        project.setBuAuditer(this.buAuditer);
        return true;
    }

    // 项目审核接口中使用，审核的原因字段
    @Transient
    private String auditingReason;
    // 项目审核接口中使用，审核的类型字段，审核类型：-1：驳回（驳回必须存在驳回原因参数） 其他或空：正常审核
    @Transient
    private String auditingType;
    // 审核日志，驳回操作使用
    @Transient
    private Integer checkLogId;
    // 海外销售合同号
    @Transient
    private String contractNoOs;

    public String getAuditingReason() {
        return auditingReason;
    }

    public void setAuditingReason(String auditingReason) {
        this.auditingReason = auditingReason;
    }

    public String getAuditingType() {
        return auditingType;
    }

    public void setAuditingType(String auditingType) {
        this.auditingType = auditingType;
    }

    public Integer getCheckLogId() {
        return checkLogId;
    }

    public String getContractNoOs() {
        return contractNoOs;
    }

    public void setContractNoOs(String contractNoOs) {
        this.contractNoOs = contractNoOs;
    }

    public void setCheckLogId(Integer checkLogId) {
        this.checkLogId = checkLogId;
    }

    public void setAudiRemark(String audiRemark) {
        this.audiRemark = audiRemark;
    }

    public String getAudiRemark() {
        return audiRemark;
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

        AUDIT("AUDIT", "未执行", 1), SUBMIT("SUBMIT", "未执行", 1), HASMANAGER("HASMANAGER", "有项目经理", 2),
        EXECUTING("EXECUTING", "正常执行", 3), DONE("DONE", "正常完成", 4), DELAYED_EXECUTION("DELAYED_EXECUTION", "延期执行", 5),
        DELAYED_COMPLETE("DELAYED_COMPLETE", "延期完成", 6), UNSHIPPED("UNSHIPPED", "正常待发运", 7),
        DELAYED_UNSHIPPED("DELAYED_UNSHIPPED", "延期待发运", 8), PAUSE("PAUSE", "项目暂停", 9), CANCEL("CANCEL", "项目取消", 10), ORDERCANCEL("ORDERCANCEL", "订单取消", 11), TURNDOWN("TURNDOWN", "驳回", 12);
        private String code;
        private String msg;
        private Integer num;

        ProjectStatusEnum(String code, String msg, Integer num) {
            this.code = code;
            this.msg = msg;
            this.num = num;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public Integer getNum() {
            return num;
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

    //流程进度
    public static enum ProjectProgressEnum {
        SUBMIT("SUBMIT", "未执行", 1), EXECUTING("EXECUTING", "正常执行", 2),
        BUYING("BUYING", "已采购", 3), QUARANTINE("DONE", "已报检", 4), CHECKING("CHECKING", "已入库质检", 5),
        IN_STORAGE("IN_STORAGE", "已入库", 6), QUALITY_INSPECTION("QUALITY_INSPECTION", "已出库质检", 7),
        OUTSTORAGE("DELAYED_UNSHIPPED", "已出库", 8), SHIPED("SHIPED", "已发运", 9);
        private String code;
        private String msg;
        private Integer num;

        ProjectProgressEnum(String code, String msg, Integer num) {
            this.code = code;
            this.msg = msg;
            this.num = num;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public Integer getNum() {
            return num;
        }

        public static ProjectProgressEnum ProjectProgressFromCode(String code) {
            if (StringUtils.isNotBlank(code)) {
                for (ProjectProgressEnum statusEnum : ProjectProgressEnum.values()) {
                    if (statusEnum.getNum() == Integer.valueOf(code)) {
                        return statusEnum;
                    }
                }
            }
            return null;
        }
    }

    //流程进度
    public static enum enProjectProgressEnum {
        SUBMIT("SUBMIT", "Not executed", 1), EXECUTING("EXECUTING", "Normal executing", 2),
        BUYING("BUYING", "Purchased", 3), QUARANTINE("DONE", "Applied for inspection", 4), CHECKING("CHECKING", "Warehouse-in inspected", 5),
        IN_STORAGE("IN_STORAGE", "In-storage", 6), QUALITY_INSPECTION("QUALITY_INSPECTION", "EX-warehouse inspected", 7),
        OUTSTORAGE("DELAYED_UNSHIPPED", "Warehouse-out", 8), SHIPED("SHIPED", "Shipped", 9);
        private String code;
        private String msg;

        private Integer num;

        enProjectProgressEnum(String code, String msg, Integer num) {

            this.code = code;
            this.msg = msg;
            this.num = num;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public Integer getNum() {
            return num;
        }

        public static enProjectProgressEnum enProjectProgressFromCode(String code) {
            if (StringUtils.isNotBlank(code)) {
                for (enProjectProgressEnum statusEnum : enProjectProgressEnum.values()) {
                    if (statusEnum.getNum() == Integer.valueOf(code)) {
                        return statusEnum;
                    }
                }
            }
            return null;
        }
    }
}