package com.erui.order.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 采购信息
 */
@Entity
@Table(name = "purch")
public class Purch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "purch_no")
    private String purchNo;

    @Column(name = "agent_id")
    private Integer agentId;

    @Column(name = "signing_date")
    private Date signingDate;

    @Column(name = "arrival_date")
    private Date arrivalDate;

    @Column(name = "pur_chg_date")
    private Date purChgDate;

    @Column(name = "exe_chg_date")
    private Date exeChgDate;

    @Column(name = "supplier_id")
    private Integer supplierId;

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

    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    @Column(name = "delete_time")
    private Date deleteTime;

    /**
     * 是否报检完成 true：完成  false：未完成
     */
    private boolean inspected = false;

    private String remarks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "purch_attach",
            joinColumns = @JoinColumn(name = "purch_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchPayment> purchPaymentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="purch_id")
    @OrderBy("id asc")
    private List<PurchGoods> purchGoodsList = new ArrayList<>();


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

    public Date getPurChgDate() {
        return purChgDate;
    }

    public void setPurChgDate(Date purChgDate) {
        this.purChgDate = purChgDate;
    }

    public Date getExeChgDate() {
        return exeChgDate;
    }

    public void setExeChgDate(Date exeChgDate) {
        this.exeChgDate = exeChgDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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

    public boolean getInspected() {
        return inspected;
    }

    public void setInspected(boolean inspected) {
        this.inspected = inspected;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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


    /**
     * 采购状态枚举
     */
    public static enum StatusEnum {
        READY(0, "未开始"), BEING(1, "进行中"), DONE(2, "已完成");

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
                int code02 = code;
                StatusEnum[] values = StatusEnum.values();
                for (StatusEnum s : StatusEnum.values()) {
                    if (code02 == s.code) {
                        return s;
                    }
                }
            }
            return null;
        }

    }
}