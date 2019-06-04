package com.erui.order.entity;

import com.erui.comm.NewDateUtil;
import com.erui.order.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采购信息
 */
@Entity
@Table(name = "purch")
public class Purch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 采购合同号
    @Column(name = "purch_no")
    private String purchNo;

    /**
     * 采购合同id
     */
    @Column(name = "purch_contract_id")
    private Integer purchContractId;
    // 采购经办人ID
    @Column(name = "agent_id")
    private Integer agentId;

    //采购经办人名称
    @Column(name = "agent_name")
    private String agentName;

    private String department;

    // 采购合同签订日期
    @Column(name = "signing_date")
    private Date signingDate;
    // 采购合同规定交货日期
    @Column(name = "arrival_date")
    private Date arrivalDate;

    @Column(name = "pur_chg_date")
    private Date purChgDate;


    // 供应商ID
    @Column(name = "supplier_id")
    private Integer supplierId;

    // 供货商名称
    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "currency_bn")
    private String currencyBn;

    @Column(name = "pay_type")
    private Integer payType;

    @Column(name = "other_pay_type_msg")
    private String otherPayTypeMsg;

    @Column(name = "producted_date")
    private Date productedDate;

    @Column(name = "pay_factory_date")
    private Date payFactoryDate;

    @Column(name = "pay_deposit_date")
    private Date payDepositDate;

    @Column(name = "pay_deposit_expired")
    private Date payDepositExpired;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "account_date")
    private Date accountDate;

    /**
     * “未进行”：当新增采购订单时，点击“保存”按钮，状态为“未进行”。点击“提交”按钮，状态由“未执行”变为“进行中”。
     * “已完成”：当报检单中所有商品全部检验合格后，状态由“进行中”，变为“已完成”。
     */
    @Column(name = "`status`")
    private Integer status;

    @Column(name = "create_time")
    @JsonIgnore
    private Date createTime;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "create_user_name")
    private String createUserName;

    @Column(name = "update_time")
    @JsonIgnore
    private Date updateTime;

    @Column(name = "delete_flag")
    @JsonIgnore
    private Boolean deleteFlag = Boolean.FALSE;

    @Column(name = "delete_time")
    @JsonIgnore
    private Date deleteTime;

    // 是否报检完成 true：完成  false：未完成
    private Boolean inspected = Boolean.FALSE;

    private String remarks;

    //销售合同号，多个
    @Transient
    private String contractNos;
    //项目号，多个
    @Transient
    private String projectNos;
    // 分页信息参数
    @Transient
    private int page = 1; // 默认从1开始
    @Transient
    private int rows = 20; // 默认每页20条记录
    //auditing_status   审核状态 4:审核完成
    @Column(name = "auditing_status")
    private Integer auditingStatus;
    //auditing_process  审核流程，多个
    @Column(name = "auditing_process")
    private String auditingProcess;

    //auditing_user_id  审核人id，多个
    @Column(name = "auditing_user_id")
    private String auditingUserId;
    //auditing_user     审核人姓名，多个
    @Column(name = "auditing_user")
    private String auditingUser;

    //purch_auditer_id  采购责任人id
    @Column(name = "purch_auditer_id")
    private Integer purchAuditerId;
    //purch_auditer     采购责任人姓名
    @Column(name = "purch_auditer")
    private String purchAuditer;

    //business_auditer_id 事业部项目负责人审核人id
    @Column(name = "business_auditer_id")
    private Integer businessAuditerId;
    //business_auditer   事业部项目负责人审核人姓名
    @Column(name = "business_auditer")
    private String businessAuditer;

    //legal_auditer_id  法务审核人id
    @Column(name = "legal_auditer_id")
    private Integer legalAuditerId;

    //legal_auditer    法务审核人姓名
    @Column(name = "legal_auditer")
    private String legalAuditer;

    //finance_auditer_id 财务审核人id
    @Column(name = "finance_auditer_id")
    private Integer financeAuditerId;
    //finance_auditer   财务审核人姓名
    @Column(name = "finance_auditer")
    private String financeAuditer;


    //bu_vp_auditer_id  供应链中心总经理审核人id
    @Column(name = "bu_vp_auditer_id")
    private Integer buVpAuditerId;
    //bu_vp_auditer     供应链中心总经理审核人姓名
    @Column(name = "bu_vp_auditer")
    private String buVpAuditer;

    //chairman_id       总裁id
    @Column(name = "chairman_id")
    private Integer chairmanId;
    //chairman          总裁姓名
    private String chairman;
    //chairman_board_id       董事长id
    @Column(name = "chairman_board_id")
    private Integer chairmanBoardId;
    //chairman          董事长姓名
    @Column(name = "chairman_board")
    private String chairmanBoard;

    @Column(name = "audi_remark")
    private String audiRemark;

    // 项目审核接口中使用，审核的原因字段
    @Transient
    private String auditingReason;
    // 项目审核接口中使用，审核的类型字段，审核类型：-1：驳回（驳回必须存在驳回原因参数） 其他或空：正常审核
    @Transient
    private String auditingType;
    // 审核日志，驳回操作使用
    @Transient
    private Integer checkLogId;

    @Column(name = "supply_area")
    private String supplyArea;


    // 合同类型 1:简易合同 2:标准合同 3:非标合同
    @Column(name = "contract_version")
    private String contractVersion;

    @Column(name = "goal_cost")
    private BigDecimal goalCost;

    @Column(name = "save_amount")
    private BigDecimal saveAmount;

    @Column(name = "save_mode")
    private String saveMode;

    @Column(name = "price_mode")
    private String priceMode;

    @Column(name = "contract_tag")
    private String contractTag;

    @Column(name = "profit_percent")
    private BigDecimal profitPercent;

    @Column(name = "tax_bearing")
    private Integer taxBearing;
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate; //汇率


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "purch_project",
            joinColumns = @JoinColumn(name = "purch_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @JsonIgnore
    private List<Project> projects = new ArrayList<>();
/*
    @ManyToMany(mappedBy="purchs")
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();*/

    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "purch_attach",
            joinColumns = @JoinColumn(name = "purch_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))*/
    @Transient
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "purch_id")
    private List<PurchPayment> purchPaymentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "purch_id")
    @OrderBy("id asc")
    private List<PurchGoods> purchGoodsList = new ArrayList<>();

    // 流程实例ID
    @Column(name="process_id")
    private String processId;
    @Column(name="task_id")
    private String taskId;

    /**
     * 质检部重新评估风险等级状态 0：还未重新评估 1：已重新评估
     */
    @Column(name = "quality_inspect_status")
    private Integer qualityInspectStatus;
    /**
     * 设置质检类型的人员ID
     */
    @Column(name = "quality_leader_id")
    @JsonIgnore
    private Integer qualityLeaderId;
    /**
     * 设置质检类型的人员姓名
     */
    @Column(name = "quality_leader_name")
    @JsonIgnore
    private String qualityLeaderName;
    /**
     * 设置商品质检的时间
     */
    @Column(name = "quality_time")
    @JsonIgnore
    private Date qualityTime;

    public Integer getQualityInspectStatus() {
        return qualityInspectStatus;
    }

    public void setQualityInspectStatus(Integer qualityInspectStatus) {
        this.qualityInspectStatus = qualityInspectStatus;
    }

    public Integer getQualityLeaderId() {
        return qualityLeaderId;
    }

    public void setQualityLeaderId(Integer qualityLeaderId) {
        this.qualityLeaderId = qualityLeaderId;
    }

    public String getQualityLeaderName() {
        return qualityLeaderName;
    }

    public void setQualityLeaderName(String qualityLeaderName) {
        this.qualityLeaderName = qualityLeaderName;
    }

    public Date getQualityTime() {
        return qualityTime;
    }

    public void setQualityTime(Date qualityTime) {
        this.qualityTime = qualityTime;
    }

    public Integer getPurchContractId() {
        return purchContractId;
    }

    public void setPurchContractId(Integer purchContractId) {
        this.purchContractId = purchContractId;
    }


    public Integer getChairmanBoardId() {
        return chairmanBoardId;
    }

    public void setChairmanBoardId(Integer chairmanBoardId) {
        this.chairmanBoardId = chairmanBoardId;
    }

    public String getChairmanBoard() {
        return chairmanBoard;
    }

    public void setChairmanBoard(String chairmanBoard) {
        this.chairmanBoard = chairmanBoard;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getSupplyArea() {
        return supplyArea;
    }

    public void setSupplyArea(String supplyArea) {
        this.supplyArea = supplyArea;
    }

    public String getContractVersion() {
        return contractVersion;
    }

    public void setContractVersion(String contractVersion) {
        this.contractVersion = contractVersion;
    }

    public BigDecimal getGoalCost() {
        return goalCost;
    }

    public void setGoalCost(BigDecimal goalCost) {
        this.goalCost = goalCost;
    }

    public BigDecimal getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(BigDecimal saveAmount) {
        this.saveAmount = saveAmount;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getPriceMode() {
        return priceMode;
    }

    public void setPriceMode(String priceMode) {
        this.priceMode = priceMode;
    }

    public String getContractTag() {
        return contractTag;
    }

    public void setContractTag(String contractTag) {
        this.contractTag = contractTag;
    }

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(BigDecimal profitPercent) {
        this.profitPercent = profitPercent;
    }

    public Integer getTaxBearing() {
        return taxBearing;
    }

    public void setTaxBearing(Integer taxBearing) {
        this.taxBearing = taxBearing;
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

    public Integer getPurchAuditerId() {
        return purchAuditerId;
    }

    public void setPurchAuditerId(Integer purchAuditerId) {
        this.purchAuditerId = purchAuditerId;
    }

    public String getPurchAuditer() {
        return purchAuditer;
    }

    public void setPurchAuditer(String purchAuditer) {
        this.purchAuditer = purchAuditer;
    }

    public Integer getBusinessAuditerId() {
        return businessAuditerId;
    }

    public void setBusinessAuditerId(Integer businessAuditerId) {
        this.businessAuditerId = businessAuditerId;
    }

    public String getBusinessAuditer() {
        return businessAuditer;
    }

    public void setBusinessAuditer(String businessAuditer) {
        this.businessAuditer = businessAuditer;
    }

    public Integer getLegalAuditerId() {
        return legalAuditerId;
    }

    public void setLegalAuditerId(Integer legalAuditerId) {
        this.legalAuditerId = legalAuditerId;
    }

    public String getLegalAuditer() {
        return legalAuditer;
    }

    public void setLegalAuditer(String legalAuditer) {
        this.legalAuditer = legalAuditer;
    }

    public Integer getFinanceAuditerId() {
        return financeAuditerId;
    }

    public void setFinanceAuditerId(Integer financeAuditerId) {
        this.financeAuditerId = financeAuditerId;
    }

    public String getFinanceAuditer() {
        return financeAuditer;
    }

    public void setFinanceAuditer(String financeAuditer) {
        this.financeAuditer = financeAuditer;
    }

    public Integer getBuVpAuditerId() {
        return buVpAuditerId;
    }

    public void setBuVpAuditerId(Integer buVpAuditerId) {
        this.buVpAuditerId = buVpAuditerId;
    }

    public String getBuVpAuditer() {
        return buVpAuditer;
    }

    public void setBuVpAuditer(String buVpAuditer) {
        this.buVpAuditer = buVpAuditer;
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

    public String getAudiRemark() {
        return audiRemark;
    }

    public void setAudiRemark(String audiRemark) {
        this.audiRemark = audiRemark;
    }

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

    public void setCheckLogId(Integer checkLogId) {
        this.checkLogId = checkLogId;
    }

    public Integer getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(Integer auditingStatus) {
        this.auditingStatus = auditingStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getContractNos() {
        return contractNos;
    }

    public void setContractNos(String contractNos) {
        this.contractNos = contractNos;
    }

    public Date getPurChgDate() {
        return purChgDate;
    }

    public void setPurChgDate(Date purChgDate) {
        this.purChgDate = purChgDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }


    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOtherPayTypeMsg() {
        return otherPayTypeMsg;
    }

    public void setOtherPayTypeMsg(String otherPayTypeMsg) {
        this.otherPayTypeMsg = otherPayTypeMsg;
    }

    public Date getProductedDate() {
        return productedDate;
    }

    public void setProductedDate(Date productedDate) {
        this.productedDate = productedDate;
    }

    public Date getPayFactoryDate() {
        return payFactoryDate;
    }

    public void setPayFactoryDate(Date payFactoryDate) {
        this.payFactoryDate = payFactoryDate;
    }

    public Date getPayDepositDate() {
        return payDepositDate;
    }

    public void setPayDepositDate(Date payDepositDate) {
        this.payDepositDate = payDepositDate;
    }

    public Date getPayDepositExpired() {
        return payDepositExpired;
    }

    public void setPayDepositExpired(Date payDepositExpired) {
        this.payDepositExpired = payDepositExpired;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        if (this.createTime != null) {
            this.updateTime = this.createTime;
        }
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public Boolean getInspected() {
        return inspected;
    }

    public void setInspected(Boolean inspected) {
        this.inspected = inspected;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProjectNos() {
        return projectNos;
    }

    public void setProjectNos(String projectNos) {
        this.projectNos = projectNos;
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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<PurchPayment> getPurchPaymentList() {
        return purchPaymentList;
    }

    public void setPurchPaymentList(List<PurchPayment> purchPaymentList) {
        this.purchPaymentList = purchPaymentList;
    }

    public List<PurchGoods> getPurchGoodsList() {
        return purchGoodsList;
    }

    public void setPurchGoodsList(List<PurchGoods> purchGoodsList) {
        this.purchGoodsList = purchGoodsList;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /**
     * 采购状态枚举
     */
    public static enum StatusEnum {
        READY(1, "未进行/保存"), BEING(2, "进行中/提交"), DONE(3, "已完成"), CHANGE(5, "已变更");

        private int code;
        private String msg;

        private StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        /**
         * 通过code码获取采购状态信息
         *
         * @param code
         * @return
         */
        public static StatusEnum fromCode(Integer code) {
            if (code != null) {
                int code02 = code; // 拆箱一次
                for (StatusEnum s : StatusEnum.values()) {
                    if (code02 == s.code) {
                        return s;
                    }
                }
            }
            return null;
        }

    }


    /**
     * 给供应商付款方式枚举
     */
    public static enum PayTypeEnum {
        COD(1, "货到验收合格后付款"), PAY_FIRST(2, "款到发货"), OTHER(9, "其他方式");

        private int code;
        private String msg;

        private PayTypeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        /**
         * 通过code码获取给供应商付款方式
         *
         * @param code
         * @return
         */
        public static PayTypeEnum fromCode(Integer code) {
            if (code != null) {
                int code02 = code; // 拆箱一次
                for (PayTypeEnum s : PayTypeEnum.values()) {
                    if (code02 == s.code) {
                        return s;
                    }
                }
            }
            return null;
        }

    }


    public void setBaseInfo(Purch purch) {
        this.setPurchNo(purch.getPurchNo()); // 采购合同号
        this.setAgentId(purch.getAgentId()); // 采购经办人ID
        this.setAgentName(purch.getAgentName()); // 采购经办人名称
        this.setDepartment(purch.getDepartment()); // 下发部门
        this.setSigningDate(NewDateUtil.getDate(purch.getSigningDate())); //采购合同签订日期
        this.setArrivalDate(NewDateUtil.getDate(purch.getArrivalDate()));
        this.setPurChgDate(NewDateUtil.getDate(purch.getPurChgDate()));
        this.setSupplierId(purch.getSupplierId());
        this.setSupplierName(purch.getSupplierName());
        this.setTotalPrice(purch.getTotalPrice());
        this.setCurrencyBn(purch.getCurrencyBn());
        //this.setPayType(purch.getPayType());
        //this.setOtherPayTypeMsg(purch.getOtherPayTypeMsg());
        //this.setProductedDate(purch.getProductedDate()); // 工厂生产完成时间
        //this.setPayFactoryDate(purch.getPayFactoryDate()); // 给工厂付款时间
        //this.setPayDepositDate(purch.getPayDepositDate()); //质保金支付时间
        //this.setPayDepositExpired(purch.getPayDepositExpired()); // 质保金到期时间
        //this.setInvoiceNo(purch.getInvoiceNo()); // 发票号
        //this.setAccountDate(purch.getAccountDate()); // 挂账时间
        this.setRemarks(purch.getRemarks()); // 备注

        this.setPurchAuditerId(purch.getPurchAuditerId());
        this.setPurchAuditer(purch.getPurchAuditer());

        this.setBusinessAuditerId(purch.getBusinessAuditerId());
        this.setBusinessAuditer(purch.getBusinessAuditer());

        this.setFinanceAuditerId(purch.getFinanceAuditerId());
        this.setFinanceAuditer(purch.getFinanceAuditer());

        this.setLegalAuditerId(purch.getLegalAuditerId());
        this.setLegalAuditer(purch.getLegalAuditer());

        this.setBuVpAuditerId(purch.getBuVpAuditerId());
        this.setBuVpAuditer(purch.getBuVpAuditer());

        this.setChairmanId(purch.getChairmanId());
        this.setChairman(purch.getChairman());

        this.setChairmanBoardId(purch.getChairmanBoardId());
        this.setChairmanBoard(purch.getChairmanBoard());

        this.setSupplyArea(purch.getSupplyArea());
        this.setContractVersion(purch.getContractVersion());
        this.setContractTag(purch.getContractTag());
        this.setGoalCost(purch.getGoalCost());
        this.setSaveAmount(purch.getSaveAmount());
        this.setSaveMode(purch.getSaveMode());
        this.setPriceMode(purch.getPriceMode());
        this.setTaxBearing(purch.getTaxBearing());
        this.setProfitPercent(purch.getProfitPercent());
        this.setExchangeRate(purch.getExchangeRate());
        if (this.createUserId == null) {
            this.setCreateUserId(purch.getCreateUserId());
            this.setCreateUserName(purch.getCreateUserName());
        }
        if (this.createTime == null) {
            this.setCreateTime(new Date());
            this.setUpdateTime(new Date());
        }
        this.setStatus(purch.getStatus());

    }


}