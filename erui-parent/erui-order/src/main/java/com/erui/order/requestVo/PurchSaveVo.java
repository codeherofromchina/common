package com.erui.order.requestVo;

import com.erui.order.entity.Attachment;
import com.erui.order.entity.Purch;
import com.erui.order.entity.PurchPayment;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 保存/新增/提交采购单提交的参数vo
 * Created by wangxiaodan on 2017/12/12.
 */
public class PurchSaveVo {
    private Integer id;

    private String purchNo;

    private Integer agentId;

    private Date signingDate;

    private Date arrivalDate;

    private Date purChgDate;

    private Date exeChgDate;

    private Integer supplierId;

    private BigDecimal totalPrice;

    private String currencyBn;

    private Integer payType;

    private String otherPayTypeMsg;

    private Date productedDate;

    private Date payFactoryDate;

    private Date payDepositDate;

    private Date payDepositExpired;

    private String invoiceNo;

    private Date accountDate;

    private String remarks;

    private Integer userId;

    private String userName;

    private Integer status;

    protected List<Attachment> attachments = new ArrayList<>();

    protected List<PurchPayment> purchPaymentList = new ArrayList<>();

    protected List<PGoods> purchGoodsList = new ArrayList<>();

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<PGoods> getPurchGoodsList() {
        return purchGoodsList;
    }

    public void setPurchGoodsList(List<PGoods> purchGoodsList) {
        this.purchGoodsList = purchGoodsList;
    }

    public void copyBaseInfoTo(Purch purch) {
        if (purch == null) {
            return;
        }
        purch.setPurchNo(this.purchNo);
        purch.setAgentId(this.agentId);
        purch.setSigningDate(this.signingDate);
        purch.setArrivalDate(this.arrivalDate);
        purch.setPurChgDate(this.purChgDate);
        purch.setExeChgDate(this.exeChgDate);
        purch.setSupplierId(this.supplierId);
        purch.setTotalPrice(this.totalPrice);
        purch.setCurrencyBn(this.currencyBn);
        purch.setPayType(this.payType);
        purch.setOtherPayTypeMsg(this.getOtherPayTypeMsg());
        purch.setProductedDate(this.productedDate);
        purch.setPayFactoryDate(this.payFactoryDate);
        purch.setPayDepositDate(this.payDepositDate);
        purch.setPayDepositExpired(this.payDepositExpired);
        purch.setInvoiceNo(this.invoiceNo);
        purch.setAccountDate(this.accountDate);
        purch.setRemarks(this.remarks);
        purch.setCreateUserId(this.userId);
        Purch.StatusEnum statusEnum = Purch.StatusEnum.fromCode(this.getStatus());
        if (statusEnum == null) {
            statusEnum = Purch.StatusEnum.BEING;
        }
        purch.setStatus(statusEnum.getCode());
    }

    public void copyBaseInfoFrom(Purch purch) {
        if (purch == null) {
            return;
        }
        this.setPurchNo(purch.getPurchNo());
        this.setAgentId(purch.getAgentId());
        this.setSigningDate(purch.getSigningDate());
        this.setArrivalDate(purch.getArrivalDate());
        this.setPurChgDate(purch.getPurChgDate());
        this.setExeChgDate(purch.getExeChgDate());
        this.setSupplierId(purch.getSupplierId());
        this.setTotalPrice(purch.getTotalPrice());
        this.setCurrencyBn(purch.getCurrencyBn());
        this.setPayType(purch.getPayType());
        this.setOtherPayTypeMsg(purch.getOtherPayTypeMsg());
        this.setProductedDate(purch.getProductedDate());
        this.setPayFactoryDate(purch.getPayFactoryDate());
        this.setPayDepositDate(purch.getPayDepositDate());
        this.setPayDepositExpired(purch.getPayDepositExpired());
        this.setInvoiceNo(purch.getInvoiceNo());
        this.setAccountDate(purch.getAccountDate());
        this.setRemarks(purch.getRemarks());
        this.setStatus(purch.getStatus());
    }

    @Override
    public String toString() {
        return "PurchSaveVo{" +
                "id=" + id +
                ", purchNo='" + purchNo + '\'' +
                ", agentId=" + agentId +
                ", signingDate=" + signingDate +
                ", arrivalDate=" + arrivalDate +
                ", purChgDate=" + purChgDate +
                ", exeChgDate=" + exeChgDate +
                ", supplierId=" + supplierId +
                ", totalPrice=" + totalPrice +
                ", currencyBn='" + currencyBn + '\'' +
                ", payType=" + payType +
                ", productedDate=" + productedDate +
                ", payFactoryDate=" + payFactoryDate +
                ", payDepositDate=" + payDepositDate +
                ", payDepositExpired=" + payDepositExpired +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", accountDate=" + accountDate +
                ", remarks='" + remarks + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", attachments=" + attachments +
                ", purchPaymentList=" + purchPaymentList +
                ", purchGoodsList=" + purchGoodsList +
                '}';
    }
}
