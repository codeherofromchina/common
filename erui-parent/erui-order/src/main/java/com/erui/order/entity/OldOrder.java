package com.erui.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 老订单表
 */
@Entity
@Table(name="old_order")
public class OldOrder {
    @Id
    private Long id;

    @Column(name="order_no")
    private String orderNo;

    @Column(name="po_no")
    private String poNo;

    @Column(name="execute_no")
    private String executeNo;

    @Column(name="contract_date")
    private Date contractDate;

    @Column(name="execute_date")
    private Date executeDate;

    private String source;

    @Column(name="buyer_id")
    private Long buyerId;

    @Column(name="agent_id")
    private Long agentId;

    @Column(name="order_agent")
    private String orderAgent;

    @Column(name="order_contact_id")
    private Long orderContactId;

    @Column(name="buyer_contact_id")
    private Long buyerContactId;

    private BigDecimal amount;

    @Column(name="currency_bn")
    private String currencyBn;

    @Column(name="trade_terms_bn")
    private String tradeTermsBn;

    @Column(name="trans_mode_bn")
    private String transModeBn;

    @Column(name="from_country_bn")
    private String fromCountryBn;

    @Column(name="from_port_bn")
    private String fromPortBn;

    @Column(name="to_country_bn")
    private String toCountryBn;

    @Column(name="to_port_bn")
    private String toPortBn;

    private Integer status;

    @Column(name="show_status")
    private String showStatus;

    @Column(name="pay_status")
    private String payStatus;

    private Boolean quality;

    private Boolean distributed;

    @Column(name="created_by")
    private Long createdBy;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="complete_at")
    private Date completeAt;

    @Column(name="deleted_flag")
    private String deletedFlag;

    @Column(name="is_reply")
    private Boolean isReply;

    @Column(name="expected_receipt_date")
    private Date expectedReceiptDate;

    @Column(name="comment_flag")
    private String commentFlag;

    private String address;

    private String remark;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getExecuteNo() {
        return executeNo;
    }

    public void setExecuteNo(String executeNo) {
        this.executeNo = executeNo;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getOrderAgent() {
        return orderAgent;
    }

    public void setOrderAgent(String orderAgent) {
        this.orderAgent = orderAgent;
    }

    public Long getOrderContactId() {
        return orderContactId;
    }

    public void setOrderContactId(Long orderContactId) {
        this.orderContactId = orderContactId;
    }

    public Long getBuyerContactId() {
        return buyerContactId;
    }

    public void setBuyerContactId(Long buyerContactId) {
        this.buyerContactId = buyerContactId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public String getTradeTermsBn() {
        return tradeTermsBn;
    }

    public void setTradeTermsBn(String tradeTermsBn) {
        this.tradeTermsBn = tradeTermsBn;
    }

    public String getTransModeBn() {
        return transModeBn;
    }

    public void setTransModeBn(String transModeBn) {
        this.transModeBn = transModeBn;
    }

    public String getFromCountryBn() {
        return fromCountryBn;
    }

    public void setFromCountryBn(String fromCountryBn) {
        this.fromCountryBn = fromCountryBn;
    }

    public String getFromPortBn() {
        return fromPortBn;
    }

    public void setFromPortBn(String fromPortBn) {
        this.fromPortBn = fromPortBn;
    }

    public String getToCountryBn() {
        return toCountryBn;
    }

    public void setToCountryBn(String toCountryBn) {
        this.toCountryBn = toCountryBn;
    }

    public String getToPortBn() {
        return toPortBn;
    }

    public void setToPortBn(String toPortBn) {
        this.toPortBn = toPortBn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Boolean getQuality() {
        return quality;
    }

    public void setQuality(Boolean quality) {
        this.quality = quality;
    }

    public Boolean getDistributed() {
        return distributed;
    }

    public void setDistributed(Boolean distributed) {
        this.distributed = distributed;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCompleteAt() {
        return completeAt;
    }

    public void setCompleteAt(Date completeAt) {
        this.completeAt = completeAt;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public Boolean getIsReply() {
        return isReply;
    }

    public void setIsReply(Boolean isReply) {
        this.isReply = isReply;
    }

    public Date getExpectedReceiptDate() {
        return expectedReceiptDate;
    }

    public void setExpectedReceiptDate(Date expectedReceiptDate) {
        this.expectedReceiptDate = expectedReceiptDate;
    }

    public String getCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(String commentFlag) {
        this.commentFlag = commentFlag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}