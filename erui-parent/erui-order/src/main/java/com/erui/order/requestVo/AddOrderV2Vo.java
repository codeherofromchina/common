package com.erui.order.requestVo;

import com.erui.order.entity.Attachment;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderPayment;

import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GS on 2017/12/16 0016.
 */
public class AddOrderV2Vo {
    private Integer id;

    // --------------------基本信息字段开始---------------------

    //订单类别 1预投 2 试用 3 现货（出库） 4 订单
    private Integer orderCategory;
    //海外销售类型 1 海外销（装备采购） 2 海外销（易瑞采购） 3 海外销（当地采购） 4 易瑞销 5  装备销
    private Integer overseasSales;
    //框架协议号
    @Size(max = 255, message = "框架协议号填写不规范，请重新输入")
    private String frameworkNo;
    // 询单号，如果从询单选择的询单号，则事业部项目负责人则必须是询单的事业部负责人
    private String inquiryNo;
    // 询单ID
    private String inquiryId;
    // 事业部负责人 （商务技术ID）
    private Integer technicalId;
    // 事业部负责人（商务技术名称）
    private String businessName;
    //海外销售合同号
    @Size(max = 255, message = "海外销售合同号填写不规范，请重新输入")
    private String contractNoOs;
    // PO号
    private String poNo;
    //合同交货日期
    @Size(max = 255, message = "合同交货日期填写不规范，请重新输入")
    private String deliveryDate;
    //订单签约日期
    private Date signingDate;
    //市场经办人
    private Integer agentId;
    //市场经办人
    private String agentName;
    //签约主体公司
    private String signingCo;
    //执行事业部
    private Integer businessUnitId;
    //执行事业部
    private String businessUnitName;
    //获取人
    private Integer acquireId;
    //所属地区 （编码）
    private String region;
    //国家 （编码）
    private String country;
    //执行行分公司 （ID）
    private Integer execCoId;
    //执行分公司
    private String execCoName;
    //CRM客户代码
    private String crmCode;
    //客户类型
    private Integer customerType;
    //回款责任人
    private String perLiableRepay;
    private Integer perLiableRepayId;
    //是否融资
    private Integer financing;
    //授信情况 （类型）
    private String grantType;
    //贸易术语
    private String tradeTerms;
    //运输方式（运输类型）
    private String transportType;
    //起运国
    private String fromCountry;
    //起运港
    private String fromPort;
    //发运起始地
    private String fromPlace;
    //目的国
    private String toCountry;
    //目的港
    private String toPort;
    //目的地
    private String toPlace;
    // --------------------基本信息字段结束---------------------
    // --------------------商品信息字段开始---------------------
    //商品信息
    private List<PGoods> goodDesc = new ArrayList<>();
    // --------------------商品信息字段结束---------------------

    // --------------------合同信息字段开始---------------------
    //合同总价
    private BigDecimal totalPrice;
    //币种（货币类型）
    private String currencyBn;
    //是否含税
    private Integer taxBearing;
    //汇率（利率）
    private BigDecimal exchangeRate;
    //合同总价(USD)（美元）
    private BigDecimal totalPriceUsd;
    //收款方式
    private String paymentModeBn;
    //质保金
    private BigDecimal qualityFunds;
    //结算方式
    private List<OrderPayment> contractDesc = new ArrayList<>();
    // --------------------合同信息字段结束---------------------

    // --------------------其他信息字段开始---------------------
    //交货要求及描述
    private String deliveryRequires;
    //客户及项目背景描述
    private String customerContext;
    // --------------------其他信息字段结束---------------------
    // --------------------附件信息字段开始---------------------
    //附件信息
    private List<Attachment> attachDesc = new ArrayList<>();
    // --------------------附件信息字段结束---------------------


    // --------------------其他未知信息字段开始---------------------
    //订单状态
    private Integer status;
    //订单来源
    private Integer orderSource;
    //是否预投
    private Integer preinvest;
    /**
     * 收款状态 1:未付款 2:部分付款 3:收款完成
     */
    private Integer payStatus = 1;

    //分销部
    private String distributionDeptName;
    private Integer buyerId;

    //订单会员类别 1科瑞订单 2 易瑞订单
    private Integer orderBelongs;
    //订单类型
    private Integer orderType;
    // --------------------其他未知信息字段结束---------------------
    // --------------------不要传入的信息字段开始---------------------
    private Integer createUserId;
    private String createUserName;
    // --------------------不要传入的信息字段结束---------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOverseasSales() {
        return overseasSales;
    }

    public void setOverseasSales(Integer overseasSales) {
        this.overseasSales = overseasSales;
    }

    public String getFrameworkNo() {
        return frameworkNo;
    }

    public void setFrameworkNo(String frameworkNo) {
        this.frameworkNo = frameworkNo;
    }

    public String getInquiryNo() {
        return inquiryNo;
    }

    public void setInquiryNo(String inquiryNo) {
        this.inquiryNo = inquiryNo;
    }

    public Integer getTechnicalId() {
        return technicalId;
    }

    public void setTechnicalId(Integer technicalId) {
        this.technicalId = technicalId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getContractNoOs() {
        return contractNoOs;
    }

    public void setContractNoOs(String contractNoOs) {
        this.contractNoOs = contractNoOs;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
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

    public String getSigningCo() {
        return signingCo;
    }

    public void setSigningCo(String signingCo) {
        this.signingCo = signingCo;
    }

    public Integer getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(Integer businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public Integer getAcquireId() {
        return acquireId;
    }

    public void setAcquireId(Integer acquireId) {
        this.acquireId = acquireId;
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

    public Integer getExecCoId() {
        return execCoId;
    }

    public void setExecCoId(Integer execCoId) {
        this.execCoId = execCoId;
    }

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
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

    public Integer getPerLiableRepayId() {
        return perLiableRepayId;
    }

    public void setPerLiableRepayId(Integer perLiableRepayId) {
        this.perLiableRepayId = perLiableRepayId;
    }

    public Integer getFinancing() {
        return financing;
    }

    public void setFinancing(Integer financing) {
        this.financing = financing;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
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

    public List<PGoods> getGoodDesc() {
        return goodDesc;
    }

    public void setGoodDesc(List<PGoods> goodDesc) {
        this.goodDesc = goodDesc;
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

    public Integer getTaxBearing() {
        return taxBearing;
    }

    public void setTaxBearing(Integer taxBearing) {
        this.taxBearing = taxBearing;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getTotalPriceUsd() {
        return totalPriceUsd;
    }

    public void setTotalPriceUsd(BigDecimal totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
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

    public List<OrderPayment> getContractDesc() {
        return contractDesc;
    }

    public void setContractDesc(List<OrderPayment> contractDesc) {
        this.contractDesc = contractDesc;
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

    public List<Attachment> getAttachDesc() {
        return attachDesc;
    }

    public void setAttachDesc(List<Attachment> attachDesc) {
        this.attachDesc = attachDesc;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public Integer getPreinvest() {
        return preinvest;
    }

    public void setPreinvest(Integer preinvest) {
        this.preinvest = preinvest;
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

    public String getDistributionDeptName() {
        return distributionDeptName;
    }

    public void setDistributionDeptName(String distributionDeptName) {
        this.distributionDeptName = distributionDeptName;
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

    public Integer getOrderBelongs() {
        return orderBelongs;
    }

    public void setOrderBelongs(Integer orderBelongs) {
        this.orderBelongs = orderBelongs;
    }

    public Integer getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(Integer orderCategory) {
        this.orderCategory = orderCategory;
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

    public void copyBaseInfoTo(Order order) {
        if (order == null) {
            return;
        }
        order.setFrameworkNo(this.frameworkNo);
        order.setPoNo(this.poNo);
        order.setContractNoOs(this.contractNoOs);
        order.setInquiryNo(this.inquiryNo);
        order.setOrderType(this.orderType);
        order.setOrderSource(this.orderSource);
        order.setSigningDate(this.signingDate);
        order.setDeliveryDate(this.deliveryDate);
        order.setSigningCo(this.signingCo);
        order.setAgentId(this.agentId);
        order.setAgentName(this.agentName);
        order.setExecCoId(this.execCoId);
        order.setRegion(this.region);
        order.setCountry(this.country);
        order.setCrmCode(this.crmCode);
        order.setCustomerType(this.customerType);
        order.setPerLiableRepayId(this.perLiableRepayId);
        order.setPerLiableRepay(this.perLiableRepay);
        order.setBusinessUnitId(this.businessUnitId);
        order.setTechnicalId(this.technicalId);
        order.setBuyerId(this.buyerId);
        order.setInquiryId(this.inquiryId);
        order.setGrantType(this.grantType);
        order.setPreinvest(this.preinvest);
        order.setFinancing(this.financing);
        order.setTradeTerms(this.tradeTerms);
        order.setTransportType(this.transportType);
        order.setFromCountry(this.fromCountry);
        order.setFromPlace(this.fromPlace);
        order.setFromPort(this.fromPort);
        order.setToCountry(this.toCountry);
        order.setToPlace(this.toPlace);
        order.setToPort(this.toPort);
        order.setTotalPrice(this.totalPrice);
        order.setTotalPriceUsd(this.totalPriceUsd);
        order.setExchangeRate(this.exchangeRate);
        order.setCurrencyBn(this.currencyBn);
        order.setTaxBearing(this.taxBearing);
        order.setPaymentModeBn(this.paymentModeBn);
        order.setQualityFunds(this.qualityFunds);
        order.setPayStatus(this.payStatus);
        order.setStatus(this.status);
        order.setDeliveryRequires(this.deliveryRequires);
        order.setCustomerContext(this.customerContext);
        order.setBusinessUnitName(this.businessUnitName);
        order.setExecCoName(this.execCoName);
        order.setDistributionDeptName(this.distributionDeptName);
        order.setAcquireId(this.acquireId);
        order.setBusinessName(this.businessName);
        order.setOrderBelongs(this.orderBelongs);
        order.setOrderCategory(this.orderCategory);
        order.setOverseasSales(this.overseasSales);
    }
}
