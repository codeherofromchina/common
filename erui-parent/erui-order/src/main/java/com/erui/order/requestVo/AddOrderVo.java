package com.erui.order.requestVo;

import com.erui.order.entity.Attachment;
import com.erui.order.entity.Goods;
import com.erui.order.entity.OrderPayment;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by GS on 2017/12/16 0016.
 */
public class AddOrderVo {
    private Integer id;

    //销售合同号
    private String contractNo;

    //框架协议号
    private String frameworkNo;
    //海外销售合同号
    private String contractNoOs;

    //PO号
    private String poNo;

    //物流协议号
    private String logiQuoteNo;
    //询报价
    private String inquiryNo;

    //订单类型
    private boolean orderType = true;

    //订单来源
    private String orderSource;

    //订单签订日期
    private Date signingDate;

    //合同签订日期
    private Date deliveryDate;

    //签约主体公司
    private Integer signingCo;
    //市场经办人id
    private Integer agentId;

    private String agentName;

    //执行行分公司
    private Integer execCoId;
    //所属地区
    private String region;

    //分销部
    private Integer distributionDeptId;
    //国家
    private String country;

    //CRM客户代码
    private String crmCode;

    //客户类型
    private boolean customerType;

    //汇款责任人
    private String perLiableRepay;

    //事业部
    private Integer businessUnitId;

    //商务技术经办人
    private Integer technicalId;

    //授信类型
    private String grantType;

    //是否预投
    private boolean isPreinvest = false;

    //是否融资
    private boolean isFinancing = false;

    //贸易术语
    private String tradeTerms;

    //运输类型
    private String transportType;

    //发运国
    private String fromCountry;

    //发运港
    private String fromPort;

    //起始发运地
    private String fromPlace;

    //目的国
    private String toCountry;

    //目的港
    private String toPort;

    //目的地
    private String toPlace;

    //合同总价
    private BigDecimal totalPrice;

    //货币类型
    private String currencyBn;

    //是否含税
    private Boolean taxBearing;

    //收款方式
    private String paymentModeBn;

    //质保金
    private BigDecimal qualityFunds;

    /**
     * 收款状态 0:未付款 1:部分付款 2:收款完成
     */
    private Integer payStatus;
    //订单状态
    private Integer status;

    //订单创建时间
    private Date createTime;

    //创建人
    //   private Integer createUserId;

    //修改时间
    //   private Date updateTime;

    //交货要求及描述
    private String deliveryRequires;

    //客户及项目背景描述
    private String customerContext;
   //执行分公司
    private String execCoName;

    //分销部
    private String distributionDeptName;

   //事业部
    private String businessUnitName;

    //附件信息
    private Set<Attachment> attachDesc = new HashSet<>();

    //商品信息
    private List<PGoods> goodDesc = new ArrayList<>();
    //合同信息
    private List<OrderPayment> contractDesc = new ArrayList<>();

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

    public String getInquiryNo() {
        return inquiryNo;
    }

    public void setInquiryNo(String inquiryNo) {
        this.inquiryNo = inquiryNo;
    }

    public boolean isOrderType() {
        return orderType;
    }

    public void setOrderType(boolean orderType) {
        this.orderType = orderType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
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

    public Integer getSigningCo() {
        return signingCo;
    }

    public void setSigningCo(Integer signingCo) {
        this.signingCo = signingCo;
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

    public Integer getDistributionDeptId() {
        return distributionDeptId;
    }

    public void setDistributionDeptId(Integer distributionDeptId) {
        this.distributionDeptId = distributionDeptId;
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

    public boolean isCustomerType() {
        return customerType;
    }

    public void setCustomerType(boolean customerType) {
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

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public boolean isPreinvest() {
        return isPreinvest;
    }

    public void setPreinvest(boolean preinvest) {
        isPreinvest = preinvest;
    }

    public boolean isFinancing() {
        return isFinancing;
    }

    public void setFinancing(boolean financing) {
        isFinancing = financing;
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

    public Set<Attachment> getAttachDesc() {
        return attachDesc;
    }

    public void setAttachDesc(Set<Attachment> attachDesc) {
        this.attachDesc = attachDesc;
    }

    public List<PGoods> getGoodDesc() {
        return goodDesc;
    }

    public void setGoodDesc(List<PGoods> goodDesc) {
        this.goodDesc = goodDesc;
    }

    public List<OrderPayment> getContractDesc() {
        return contractDesc;
    }

    public void setContractDesc(List<OrderPayment> contractDesc) {
        this.contractDesc = contractDesc;
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
}
