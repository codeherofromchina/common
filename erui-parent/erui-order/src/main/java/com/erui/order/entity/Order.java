package com.erui.order.entity;

import com.erui.order.util.GoodsUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单表
 */
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contract_no")
    @Size(max = 255, message = "销售合同号填写不规范，请重新输入")
    private String contractNo;

    @Column(name = "framework_no")
    @Size(max = 255, message = "框架协议号填写不规范，请重新输入")
    private String frameworkNo;

    @Column(name = "contract_no_os")
    @Size(max = 255, message = "海外销售合同号填写不规范，请重新输入")
    private String contractNoOs;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "logi_quote_no")
    @Size(max = 255, message = "物流报价单号填写不规范，请重新输入")
    private String logiQuoteNo;

    @Column(name = "inquiry_no")
    private String inquiryNo;

    @Column(name = "order_type")
    private Integer orderType;

    @Column(name = "order_source")
    private Integer orderSource;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "signing_date")
    private Date signingDate;

    @Column(name = "delivery_date")
    private String deliveryDate;

    @Column(name = "signing_co")
    @Size(max = 255, message = "合同交货日期填写不规范，请重新输入")
    private String signingCo;

    @Column(name = "agent_id")
    private Integer agentId;

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "exec_co_id")
    private Integer execCoId;

    private String region;

    @Column(name = "distribution_dept_name")
    private String distributionDeptName;

    private String country;

    @Column(name = "crm_code")
    private String crmCode;

    @Column(name = "customer_type")
    private Integer customerType;

    @Column(name = "per_liable_repay")
    private String perLiableRepay;

    @Column(name = "business_unit_id")
    private Integer businessUnitId;

    @Column(name = "technical_id")
    private Integer technicalId;

    @Column(name = "business_name")
    private String businessName;   //商务技术经办人名称

   /* @Column(name = "technical_id_dept")
    private String technicalIdDept;*/

    @Column(name = "grant_type")
    private String grantType;

    @Column(name = "preinvest")
    private Integer preinvest;

    @Column(name = "financing")
    private Integer financing;

    @Column(name = "trade_terms")
    private String tradeTerms;

    @Column(name = "transport_type")
    private String transportType;

    @Column(name = "from_country")
    private String fromCountry;

    @Column(name = "from_port")
    private String fromPort;

    @Column(name = "from_place")
    private String fromPlace;

    @Column(name = "to_country")
    private String toCountry;

    @Column(name = "to_port")
    private String toPort;

    @Column(name = "to_place")
    private String toPlace;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "currency_bn")
    private String currencyBn;

    @Column(name = "tax_bearing")
    private Integer taxBearing;

    @Column(name = "payment_mode_bn")
    private String paymentModeBn;

    @Column(name = "quality_funds")
    private BigDecimal qualityFunds;

    /**
     * 收款状态 1:未付款 2:部分付款 3:收款完成
     */
    @Column(name = "pay_status")
    private Integer payStatus = 1;

    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "deliver_consign_c")
    private Boolean deliverConsignC = true;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "create_user_name")
    private String createUserName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "delete_time")
    private Date deleteTime;

    @Column(name = "receivable_account_remaining")
    private BigDecimal receivableAccountRemaining;  //应收账款余额


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "order_attach",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<Attachment> attachmentSet = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @OrderBy("id asc")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<Goods> goodsList = new ArrayList<>();

    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    // @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<OrderPayment> orderPayments = new ArrayList<>();

    @Column(name = "delivery_requires")
    private String deliveryRequires;

    @Column(name = "customer_context")
    private String customerContext;

    @Column(name = "exec_co_name")
    private String execCoName;

    @Column(name = "business_unit_name")
    private String businessUnitName;
    @Column(name = "acquire_id")
    private Integer acquireId;
    //合同总价（美元）
    @Column(name = "total_price_usd")
    private BigDecimal totalPriceUsd;
    //项目号
    @Column(name = "project_no")
    private String projectNo;
    //利率
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;
    //流程进度
    @Column(name = "process_progress")
    private String processProgress;
    //是否已生成出口通知单
    @Column(name = "deliver_consign_has")
    private Integer deliverConsignHas;
    @Transient
    private int page = 0;
    @Transient
    private int rows = 50;
    //订单列表增加确认收货按钮标识
    @Transient
    private Boolean orderFinish = false;//true时可以确认收货
    /*@Column(name = "delivery_date_no")
     private Date deliveryDateNo;    //执行单约定交付日期*/
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "order")
    @JsonIgnore
    private Project project;
    @Column(name = "buyer_id")
    private Integer buyerId;
    @Column(name = "inquiry_id")
    private String inquiryId;
    //会员类别 1科瑞订单 2 易瑞订单
    @Column(name = "order_belongs")
    private Integer orderBelongs;

    //订单类别 1预投 2 售后回 3 试用 4 现货（出库） 5 订单 6 国内订单
    @Column(name = "order_category")
    private Integer orderCategory;
    //海外销售类型 1 海外销（装备采购） 2 海外销（易瑞采购） 3 海外销（当地采购） 4 易瑞销 5  装备销
    @Column(name = "overseas_sales")
    private Integer overseasSales;

    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderAccount> orderAccounts = new ArrayList<>();

    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderAccountDeliver> orderAccountDelivers = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private List<DeliverConsign> deliverConsign;

    // 已发货总金额 （财务管理）
    @Column(name = "shipments_money")
    private BigDecimal shipmentsMoney;   //已发货总金额 （财务管理）

    //已收款总金额  （财务管理）
    @Column(name = "already_gathering_money")
    private BigDecimal alreadyGatheringMoney;   //已收款总金额  （财务管理）

    @Transient
    // 已发货总金额USD （财务管理）
    private BigDecimal alreadyGatheringMoneyUSD;   //已发货总金额 （财务管理）

    @Transient
    // 已发货总金额 （财务管理）
    private String currencyBnShipmentsMoney;   //已发货总金额 （财务管理）

    @Transient
    //已收款总金额  （财务管理）
    private String currencyBnAlreadyGatheringMoney;   //已收款总金额  （财务管理）

    @Transient
    // 已发货总金额USD （财务管理）
    private String currencyBnReceivableAccountRemaining;   //应收账款余额
    //'是否已经创建采购申请单 1：未创建  2：已创建 3:已创建并提交'
    @Column(name = "purch_req_create")
    private Integer purchReqCreate;
    //是否已经提交出库质检 1 未提交 2 已提交
    @Column(name = "inspect_n")
    private Integer inspectN;
    //审批流所需字段
    @Column(name = "auditing_status")
    private Integer auditingStatus;   //审核状态

    @Column(name = "auditing_process")
    private Integer auditingProcess; //审核进度

    @Column(name = "auditing_user_id")
    private String auditingUserId;   //当前审核人ID，逗号分隔多个

    @Column(name = "country_leader_id")
    private Integer countryLeaderId; //国家负责人ID

    @Column(name = "country_leader")
    private String countryLeader;   //国家负责人

    @Column(name = "area_leader_id")
    private Integer areaLeaderId;   //区域负责Id

    @Column(name = "area_leader")
    private String areaLeader;      //区域负责人

    @Column(name = "area_vp_id")
    private Integer areaVpId;       //区域VP ID

    @Column(name = "area_vp")
    private String areaVp;          //区域VP

    @Column(name = "per_liable_repay_id")
    private Integer perLiableRepayId; //回款责任人
    @Column(name = "audi_remark")
    private String audiRemark;//所经过审核人
    @Column(name = "financing_commissioner_id")
    private Integer financingCommissionerId;//融资专员Id

    public Integer getFinancingCommissionerId() {
        return financingCommissionerId;
    }

    public void setFinancingCommissionerId(Integer financingCommissionerId) {
        this.financingCommissionerId = financingCommissionerId;
    }

    public String getAudiRemark() {
        return audiRemark;
    }

    public void setAudiRemark(String audiRemark) {
        this.audiRemark = audiRemark;
    }

    public Integer getPerLiableRepayId() {
        return perLiableRepayId;
    }

    public void setPerLiableRepayId(Integer perLiableRepayId) {
        this.perLiableRepayId = perLiableRepayId;
    }

    public Integer getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(Integer auditingStatus) {
        this.auditingStatus = auditingStatus;
    }

    public Integer getAuditingProcess() {
        return auditingProcess;
    }

    public void setAuditingProcess(Integer auditingProcess) {
        this.auditingProcess = auditingProcess;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId;
    }

    public Integer getCountryLeaderId() {
        return countryLeaderId;
    }

    public void setCountryLeaderId(Integer countryLeaderId) {
        this.countryLeaderId = countryLeaderId;
    }

    public String getCountryLeader() {
        return countryLeader;
    }

    public void setCountryLeader(String countryLeader) {
        this.countryLeader = countryLeader;
    }

    public Integer getAreaLeaderId() {
        return areaLeaderId;
    }

    public void setAreaLeaderId(Integer areaLeaderId) {
        this.areaLeaderId = areaLeaderId;
    }

    public String getAreaLeader() {
        return areaLeader;
    }

    public void setAreaLeader(String areaLeader) {
        this.areaLeader = areaLeader;
    }

    public Integer getAreaVpId() {
        return areaVpId;
    }

    public void setAreaVpId(Integer areaVpId) {
        this.areaVpId = areaVpId;
    }

    public String getAreaVp() {
        return areaVp;
    }

    public void setAreaVp(String areaVp) {
        this.areaVp = areaVp;
    }

    public Integer getInspectN() {
        return inspectN;
    }

    @Column(name = "advance_money")
    private BigDecimal advanceMoney;    //预收金额

    @Transient
    private BigDecimal lineOfCredit;    //授信额度

    @Transient
    private BigDecimal creditAvailable;    //可用授信额度


    @Transient
    private BigDecimal thisShipmentsMoney;    //本批次发货金额   未用到返回字段使用


    public void setInspectN(Integer inspectN) {
        this.inspectN = inspectN;
    }

    public Integer getPurchReqCreate() {
        return purchReqCreate;
    }

    public void setPurchReqCreate(Integer purchReqCreate) {
        this.purchReqCreate = purchReqCreate;
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

    public Integer getOrderBelongs() {
        return orderBelongs;
    }

    public void setOrderBelongs(Integer orderBelongs) {
        this.orderBelongs = orderBelongs;
    }

    public Integer getDeliverConsignHas() {
        return deliverConsignHas;
    }

    public void setDeliverConsignHas(Integer deliverConsignHas) {
        this.deliverConsignHas = deliverConsignHas;
    }

    public String getProcessProgressName() {
        Project.ProjectProgressEnum projectProgressEnum = Project.ProjectProgressEnum.ProjectProgressFromCode(getProcessProgress());
        if (projectProgressEnum != null) {
            return projectProgressEnum.getMsg();
        }
        return null;
    }

    public String getEnProcessProgressName() {
        Project.enProjectProgressEnum enProjectProgressEnum = Project.enProjectProgressEnum.enProjectProgressFromCode(getProcessProgress());
        if (enProjectProgressEnum != null) {
            return enProjectProgressEnum.getMsg();
        }
        return null;
    }

    public String getProcessProgress() {
        return processProgress;
    }

    public void setProcessProgress(String processProgress) {
        this.processProgress = processProgress;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public BigDecimal getTotalPriceUsd() {
        return totalPriceUsd;
    }

    //金额千位以逗号分割
    /*public String getTotalPriceUsdSplit() {
        if (getTotalPriceUsd() != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###.#");
            String format = decimalFormat.format(getTotalPriceUsd());
            return format;
        }
        return null;
    }*/
    //去掉千位分隔符
    public BigDecimal getTotalPriceUsdSplit() {
        if (getTotalPriceUsd() != null) {
            return getTotalPriceUsd();
        }
        return null;
    }

    public void setTotalPriceUsd(BigDecimal totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public Boolean getOrderFinish() {
        return orderFinish;
    }

    public void setOrderFinish(Boolean orderFinish) {
        this.orderFinish = orderFinish;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getAcquireId() {
        return acquireId;
    }

    public void setAcquireId(Integer acquireId) {
        this.acquireId = acquireId;
    }

    public Integer getPreinvest() {
        return preinvest;
    }

    public void setPreinvest(Integer preinvest) {
        this.preinvest = preinvest;
    }

    public Integer getFinancing() {
        return financing;
    }

    public void setFinancing(Integer financing) {
        this.financing = financing;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getExecCoName() {
        return execCoName;
    }

    public void setPage(int page) {
        this.page = page;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getInquiryNo() {
        if (inquiryNo == null)
            setInquiryNo("");
        return inquiryNo;
    }

    public void setInquiryNo(String inquiryNo) {
        this.inquiryNo = inquiryNo;
    }


    public Integer getOrderSource() {
        return orderSource;
    }

    public String getOrderSourceName() {
        // 1 门户订单 2 门户询单 3 线下订单',
        if (getOrderSource() != null) {
            if (getOrderSource() == 1) {
                return "门户订单";
            } else if (getOrderSource() == 2) {
                return "市场询单";
            } else if (getOrderSource() == 3) {
                return "线下订单";
            }
        }
        return null;
    }


    public String getEnOrderSourceName() {
        // 1 门户订单 2 门户询单 3 线下订单',
        if (getOrderSource() != null) {
            if (getOrderSource() == 1) {
                return "Online orders";
            } else if (getOrderSource() == 2) {
                return "BOSS inquiry";
            } else if (getOrderSource() == 3) {
                return "Offline orders";
            }
        }
        return null;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getFrameworkNo() {
        return frameworkNo;
    }

    public void setFrameworkNo(String frameworkNo) {
        this.frameworkNo = frameworkNo;
    }

    public String getContractNoOs() {
        return contractNoOs;
    }

    public void setContractNoOs(String contractNoOs) {
        this.contractNoOs = contractNoOs;
    }

    public String getPoNo() {
        if (poNo == null) {
            setPoNo("");
        }
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getLogiQuoteNo() {
        return logiQuoteNo;
    }

    public void setLogiQuoteNo(String logiQuoteNo) {
        this.logiQuoteNo = logiQuoteNo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public String getOrderTypeName() {
        if (getOrderType() != null) {
            if (getOrderType() == 1) {
                return "油气";
            } else {
                return "非油气";
            }
        }
        return null;
    }

    public String getEnOrderTypeName() {
        if (getOrderType() != null) {
            if (getOrderType() == 1) {
                return "Oil & gas";
            } else {
                return "Non-oil or gas";
            }
        }
        return null;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getSigningCo() {
        return signingCo;
    }

    public void setSigningCo(String signingCo) {
        this.signingCo = signingCo;

    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getExecCoId() {
        return execCoId;
    }

    public void setExecCoId(Integer execCoId) {
        this.execCoId = execCoId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCrmCode() {
        return crmCode;
    }

    public void setCrmCode(String crmCode) {
        this.crmCode = crmCode;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getPerLiableRepay() {
        return perLiableRepay;
    }

    public void setPerLiableRepay(String perLiableRepay) {
        this.perLiableRepay = perLiableRepay;
    }

    public Integer getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(Integer businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public Integer getTechnicalId() {
        return technicalId;
    }

    public void setTechnicalId(Integer technicalId) {
        this.technicalId = technicalId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public int getPage() {
        return page;
    }

    public int getRows() {
        return rows;
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getFromPort() {
        return fromPort;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getToPort() {
        return toPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
      /*  if (totalPrice == null) {
            BigDecimal bigDecimal = new BigDecimal("0.00");
            totalPrice = bigDecimal;
        }*/
        this.totalPrice = totalPrice;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public Integer getTaxBearing() {
        return taxBearing;
    }

    public void setTaxBearing(Integer taxBearing) {
        this.taxBearing = taxBearing;
    }

    public String getPaymentModeBn() {
        return paymentModeBn;
    }

    public void setPaymentModeBn(String paymentModeBn) {
        this.paymentModeBn = paymentModeBn;
    }

    public BigDecimal getQualityFunds() {
        return qualityFunds;
    }

    public void setQualityFunds(BigDecimal qualityFunds) {
        this.qualityFunds = qualityFunds;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public String getPayStatusName() {
        // 1:未付款 2:部分付款 3:收款完成
        if (getPayStatus() == 1) {
            return "未收款";
        } else if (getPayStatus() == 2) {
            return "部分收款";
        } else if (getPayStatus() == 3) {
            return "收款完成";
        }
        return null;
    }

    public String getEnPayStatusName() {
        // 1:未付款 2:部分付款 3:收款完成
        if (getPayStatus() == 1) {
            return "Uncollected";
        } else if (getPayStatus() == 2) {
            return "Partly collected";
        } else if (getPayStatus() == 3) {
            return "Totally collected";
        }
        return null;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public String getOrderStatusName() {
        Order.StatusEnum statusEnum = Order.fromCode(getStatus());
        if (statusEnum != null) {
            return statusEnum.getMsg();
        }
        return null;
    }

    public String getEnOrderStatusName() {
        Order.enStatusEnum enStatusEnum = Order.enFromCode(getStatus());
        if (enStatusEnum != null) {
            return enStatusEnum.getMsg();
        }
        return null;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeliverConsignC() {
        return deliverConsignC;
    }

    public void setDeliverConsignC(Boolean deliverConsignC) {
        this.deliverConsignC = deliverConsignC;
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

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public List<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(List<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }

    public BigDecimal getReceivableAccountRemaining() {
        return receivableAccountRemaining;
    }

    public void setReceivableAccountRemaining(BigDecimal receivableAccountRemaining) {
        this.receivableAccountRemaining = receivableAccountRemaining;
    }

    public List<Goods> getGoodsList() {
        GoodsUtils.sortGoodsByParentAndSon(goodsList);
        return goodsList;
    }


    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }


    public List<OrderPayment> getOrderPayments() {
        return orderPayments;
    }

    public void setOrderPayments(List<OrderPayment> orderPayments) {
        this.orderPayments = orderPayments;
    }

    public String getDeliveryRequires() {
        return deliveryRequires;
    }

    public void setDeliveryRequires(String deliveryRequires) {
        this.deliveryRequires = deliveryRequires;
    }

    public String getCustomerContext() {
        return customerContext;
    }

    public void setCustomerContext(String customerContext) {
        this.customerContext = customerContext;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public BigDecimal getShipmentsMoney() {
        return shipmentsMoney;
    }

    public void setShipmentsMoney(BigDecimal shipmentsMoney) {
        this.shipmentsMoney = shipmentsMoney;
    }

    public void setAlreadyGatheringMoney(BigDecimal alreadyGatheringMoney) {
        this.alreadyGatheringMoney = alreadyGatheringMoney;
    }


    public BigDecimal getAlreadyGatheringMoney() {
        return alreadyGatheringMoney;
    }

    public BigDecimal getAlreadyGatheringMoneyUSD() {
        return alreadyGatheringMoneyUSD;
    }

    public void setAlreadyGatheringMoneyUSD(BigDecimal alreadyGatheringMoneyUSD) {
        this.alreadyGatheringMoneyUSD = alreadyGatheringMoneyUSD;
    }

    public List<OrderAccount> getOrderAccounts() {
        return orderAccounts;
    }

    public void setOrderAccounts(List<OrderAccount> orderAccounts) {
        this.orderAccounts = orderAccounts;
    }

    public void setOrderAccountDelivers(List<OrderAccountDeliver> orderAccountDelivers) {
        this.orderAccountDelivers = orderAccountDelivers;
    }

    public String getCurrencyBnShipmentsMoney() {
        return currencyBnShipmentsMoney;
    }

    public void setCurrencyBnShipmentsMoney(String currencyBnShipmentsMoney) {
        this.currencyBnShipmentsMoney = currencyBnShipmentsMoney;
    }

    public void setCurrencyBnAlreadyGatheringMoney(String currencyBnAlreadyGatheringMoney) {
        this.currencyBnAlreadyGatheringMoney = currencyBnAlreadyGatheringMoney;
    }


    public String getCurrencyBnAlreadyGatheringMoney() {
        return currencyBnAlreadyGatheringMoney;
    }

    public String getCurrencyBnReceivableAccountRemaining() {
        return currencyBnReceivableAccountRemaining;
    }

    public void setCurrencyBnReceivableAccountRemaining(String currencyBnReceivableAccountRemaining) {
        this.currencyBnReceivableAccountRemaining = currencyBnReceivableAccountRemaining;
    }

    public List<OrderAccountDeliver> getOrderAccountDelivers() {
        return orderAccountDelivers;
    }

    public List<DeliverConsign> getDeliverConsign() {
        return deliverConsign;
    }

    public void setDeliverConsign(List<DeliverConsign> deliverConsign) {
        this.deliverConsign = deliverConsign;
    }

    public static enum StatusEnum {
        INIT(1, "待确认"), UNEXECUTED(2, "未执行"), EXECUTING(3, "执行中"), DONE(4, "完成");

        public int code;
        public String msg;

        StatusEnum(int code, String msg) {
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

    public static StatusEnum fromCode(Integer code) {
        if (code != null) {
            for (StatusEnum statusEnum : StatusEnum.values()) {
                if (statusEnum.getCode() == code) {
                    return statusEnum;
                }
            }
        }
        return null;
    }

    public static enum enStatusEnum {
        INIT(1, "To be confirmed"), UNEXECUTED(2, "Not executed"), EXECUTING(3, "Being executed"), DONE(4, "Finished");

        public int code;
        public String msg;

        enStatusEnum(int code, String msg) {
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

    public static enStatusEnum enFromCode(Integer code) {
        if (code != null) {
            for (enStatusEnum enStatusEnum : enStatusEnum.values()) {
                if (enStatusEnum.getCode() == code) {
                    return enStatusEnum;
                }
            }
        }
        return null;
    }


    /**
     * 审核状态
     * //1：待审核、2：审核中、3：驳回、4：通过
     */
    public static enum AuditingStatusEnum {
        WAIT(1), PROCESSING(2), REJECT(3), THROUGH(4);

        private int status;

        private AuditingStatusEnum(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    public BigDecimal getLineOfCredit() {
        return lineOfCredit;
    }

    public void setLineOfCredit(BigDecimal lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
    }

    public BigDecimal getCreditAvailable() {
        return creditAvailable;
    }

    public void setCreditAvailable(BigDecimal creditAvailable) {
        this.creditAvailable = creditAvailable;
    }

    public BigDecimal getThisShipmentsMoney() {
        return thisShipmentsMoney;
    }

    public void setThisShipmentsMoney(BigDecimal thisShipmentsMoney) {
        this.thisShipmentsMoney = thisShipmentsMoney;
    }

    public BigDecimal getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(BigDecimal advanceMoney) {
        this.advanceMoney = advanceMoney;
    }
}