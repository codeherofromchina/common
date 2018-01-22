package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

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
    private String contractNo;

    @Column(name = "framework_no")
    private String frameworkNo;

    @Column(name = "contract_no_os")
    private String contractNoOs;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "logi_quote_no")
    private String logiQuoteNo;

    @Column(name = "inquiry_no")
    private String inquiryNo;

    @Column(name = "order_type")
    private Integer orderType;

    @Column(name = "order_source")
    private Integer orderSource;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "signing_date")
    private Date signingDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "signing_co")
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
   /* @Column(name = "technical_id_dept")
    private String technicalIdDept;*/

    @Column(name = "grant_type")
    private String grantType;

    @Column(name = "preinvest")
    private Boolean preinvest = false;

    @Column(name = "financing")
    private Boolean financing = false;

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
    private Boolean taxBearing;

    @Column(name = "payment_mode_bn")
    private String paymentModeBn;

    @Column(name = "quality_funds")
    private BigDecimal qualityFunds;

    /**
     * 收款状态 1:未付款 2:部分付款 3:收款完成
     */
    @Column(name = "pay_status")
    private Integer payStatus;

    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "deliver_consign_c")
    private Boolean deliverConsignC = true;

    @Column(name = "create_user_id")
    private Integer createUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "delete_time")
    private Date deleteTime;

    @Column(name = "receivable_account_remaining")
    private BigDecimal receivableAccountRemaining;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "order_attach",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    // @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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

    @Transient
    private int page = 0;

    @Transient
    private int rows = 50;

   /*@Column(name = "delivery_date_no")
    private Date deliveryDateNo;    //执行单约定交付日期*/


    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "order")
    @JsonIgnore
    private Project project;

    public Boolean getPreinvest() {
        return preinvest;
    }

    public void setPreinvest(Boolean preinvest) {
        this.preinvest = preinvest;
    }

    public Boolean getFinancing() {
        return financing;
    }

    public void setFinancing(Boolean financing) {
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

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
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
        this.totalPrice = totalPrice;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public Boolean getTaxBearing() {
        return taxBearing;
    }

    public void setTaxBearing(Boolean taxBearing) {
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

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
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


    public static enum StatusEnum{
        INIT(1,"待确认"),UNEXECUTED(2,"未执行"),EXECUTING(3,"执行中"),DONE(4,"完成");

        public int code;
        public String msg;
        StatusEnum(int code,String msg) {
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