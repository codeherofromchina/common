package com.erui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class InquiryCount {
    private Long id;

    private String inquiryNum;

    private String quotationNum;

    private String inquiryUnit;

    private String inquiryArea;

    private String organization;

    private String custName;

    private String custDescription;

    private String proName;

    private String proNameForeign;

    private String specification;

    private String figureNum;

    private Integer proCount;

    private String proUnit;

    private String isOilGas;

    private String platProCategory;

    private String proCategory;

    private String isKeruiEquipParts;

    private String isBid;

    private Date rollinTime;

    private Date needTime;

    private Date clarifyTime;

    private Date submitTime;

    private BigDecimal quoteNeedTime;

    private String marketPrincipal;

    private String busTechnicalBidder;

    private String busUnitPrincipal;

    private String proBrand;

    private String quonationSuppli;

    private String suppliBidder;

    private String bidderPhone;

    private BigDecimal suppliUnitPrice;

    private BigDecimal suppliTotalPrice;

    private BigDecimal profitMargin;

    private BigDecimal quoteUnitPrice;

    private BigDecimal quoteTotalPrice;

    private BigDecimal quotationPrice;

    private BigDecimal pieceWeight;

    private BigDecimal totalWeight;

    private String packagingVolume;

    private String packagingWay;

    private Integer deliveryDate;

    private Integer expiryDate;

    private String standardTradeItems;

    private String latestSchedule;

    private String quotedStatus;

    private Integer returnCount;

    private String remark;

    private String quoteOvertimeCategory;

    private String quoteOvertimeCause;

    private String isSuccessOrder;

    private String loseOrderCategory;

    private String loseOrderCause;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInquiryNum() {
        return inquiryNum;
    }

    public void setInquiryNum(String inquiryNum) {
        this.inquiryNum = inquiryNum == null ? null : inquiryNum.trim();
    }

    public String getQuotationNum() {
        return quotationNum;
    }

    public void setQuotationNum(String quotationNum) {
        this.quotationNum = quotationNum == null ? null : quotationNum.trim();
    }

    public String getInquiryUnit() {
        return inquiryUnit;
    }

    public void setInquiryUnit(String inquiryUnit) {
        this.inquiryUnit = inquiryUnit == null ? null : inquiryUnit.trim();
    }

    public String getInquiryArea() {
        return inquiryArea;
    }

    public void setInquiryArea(String inquiryArea) {
        this.inquiryArea = inquiryArea == null ? null : inquiryArea.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getCustDescription() {
        return custDescription;
    }

    public void setCustDescription(String custDescription) {
        this.custDescription = custDescription == null ? null : custDescription.trim();
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName == null ? null : proName.trim();
    }

    public String getProNameForeign() {
        return proNameForeign;
    }

    public void setProNameForeign(String proNameForeign) {
        this.proNameForeign = proNameForeign == null ? null : proNameForeign.trim();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public String getFigureNum() {
        return figureNum;
    }

    public void setFigureNum(String figureNum) {
        this.figureNum = figureNum == null ? null : figureNum.trim();
    }

    public Integer getProCount() {
        return proCount;
    }

    public void setProCount(Integer proCount) {
        this.proCount = proCount;
    }

    public String getProUnit() {
        return proUnit;
    }

    public void setProUnit(String proUnit) {
        this.proUnit = proUnit == null ? null : proUnit.trim();
    }

    public String getIsOilGas() {
        return isOilGas;
    }

    public void setIsOilGas(String isOilGas) {
        this.isOilGas = isOilGas == null ? null : isOilGas.trim();
    }

    public String getPlatProCategory() {
        return platProCategory;
    }

    public void setPlatProCategory(String platProCategory) {
        this.platProCategory = platProCategory == null ? null : platProCategory.trim();
    }

    public String getProCategory() {
        return proCategory;
    }

    public void setProCategory(String proCategory) {
        this.proCategory = proCategory == null ? null : proCategory.trim();
    }

    public String getIsKeruiEquipParts() {
        return isKeruiEquipParts;
    }

    public void setIsKeruiEquipParts(String isKeruiEquipParts) {
        this.isKeruiEquipParts = isKeruiEquipParts == null ? null : isKeruiEquipParts.trim();
    }

    public String getIsBid() {
        return isBid;
    }

    public void setIsBid(String isBid) {
        this.isBid = isBid == null ? null : isBid.trim();
    }

    public Date getRollinTime() {
        return rollinTime;
    }

    public void setRollinTime(Date rollinTime) {
        this.rollinTime = rollinTime;
    }

    public Date getNeedTime() {
        return needTime;
    }

    public void setNeedTime(Date needTime) {
        this.needTime = needTime;
    }

    public Date getClarifyTime() {
        return clarifyTime;
    }

    public void setClarifyTime(Date clarifyTime) {
        this.clarifyTime = clarifyTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public BigDecimal getQuoteNeedTime() {
        return quoteNeedTime;
    }

    public void setQuoteNeedTime(BigDecimal quoteNeedTime) {
        this.quoteNeedTime = quoteNeedTime;
    }

    public String getMarketPrincipal() {
        return marketPrincipal;
    }

    public void setMarketPrincipal(String marketPrincipal) {
        this.marketPrincipal = marketPrincipal == null ? null : marketPrincipal.trim();
    }

    public String getBusTechnicalBidder() {
        return busTechnicalBidder;
    }

    public void setBusTechnicalBidder(String busTechnicalBidder) {
        this.busTechnicalBidder = busTechnicalBidder == null ? null : busTechnicalBidder.trim();
    }

    public String getBusUnitPrincipal() {
        return busUnitPrincipal;
    }

    public void setBusUnitPrincipal(String busUnitPrincipal) {
        this.busUnitPrincipal = busUnitPrincipal == null ? null : busUnitPrincipal.trim();
    }

    public String getProBrand() {
        return proBrand;
    }

    public void setProBrand(String proBrand) {
        this.proBrand = proBrand == null ? null : proBrand.trim();
    }

    public String getQuonationSuppli() {
        return quonationSuppli;
    }

    public void setQuonationSuppli(String quonationSuppli) {
        this.quonationSuppli = quonationSuppli == null ? null : quonationSuppli.trim();
    }

    public String getSuppliBidder() {
        return suppliBidder;
    }

    public void setSuppliBidder(String suppliBidder) {
        this.suppliBidder = suppliBidder == null ? null : suppliBidder.trim();
    }

    public String getBidderPhone() {
        return bidderPhone;
    }

    public void setBidderPhone(String bidderPhone) {
        this.bidderPhone = bidderPhone == null ? null : bidderPhone.trim();
    }

    public BigDecimal getSuppliUnitPrice() {
        return suppliUnitPrice;
    }

    public void setSuppliUnitPrice(BigDecimal suppliUnitPrice) {
        this.suppliUnitPrice = suppliUnitPrice;
    }

    public BigDecimal getSuppliTotalPrice() {
        return suppliTotalPrice;
    }

    public void setSuppliTotalPrice(BigDecimal suppliTotalPrice) {
        this.suppliTotalPrice = suppliTotalPrice;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        this.profitMargin = profitMargin;
    }

    public BigDecimal getQuoteUnitPrice() {
        return quoteUnitPrice;
    }

    public void setQuoteUnitPrice(BigDecimal quoteUnitPrice) {
        this.quoteUnitPrice = quoteUnitPrice;
    }

    public BigDecimal getQuoteTotalPrice() {
        return quoteTotalPrice;
    }

    public void setQuoteTotalPrice(BigDecimal quoteTotalPrice) {
        this.quoteTotalPrice = quoteTotalPrice;
    }

    public BigDecimal getQuotationPrice() {
        return quotationPrice;
    }

    public void setQuotationPrice(BigDecimal quotationPrice) {
        this.quotationPrice = quotationPrice;
    }

    public BigDecimal getPieceWeight() {
        return pieceWeight;
    }

    public void setPieceWeight(BigDecimal pieceWeight) {
        this.pieceWeight = pieceWeight;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getPackagingVolume() {
        return packagingVolume;
    }

    public void setPackagingVolume(String packagingVolume) {
        this.packagingVolume = packagingVolume == null ? null : packagingVolume.trim();
    }

    public String getPackagingWay() {
        return packagingWay;
    }

    public void setPackagingWay(String packagingWay) {
        this.packagingWay = packagingWay == null ? null : packagingWay.trim();
    }

    public Integer getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Integer deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Integer expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStandardTradeItems() {
        return standardTradeItems;
    }

    public void setStandardTradeItems(String standardTradeItems) {
        this.standardTradeItems = standardTradeItems == null ? null : standardTradeItems.trim();
    }

    public String getLatestSchedule() {
        return latestSchedule;
    }

    public void setLatestSchedule(String latestSchedule) {
        this.latestSchedule = latestSchedule == null ? null : latestSchedule.trim();
    }

    public String getQuotedStatus() {
        return quotedStatus;
    }

    public void setQuotedStatus(String quotedStatus) {
        this.quotedStatus = quotedStatus == null ? null : quotedStatus.trim();
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getQuoteOvertimeCategory() {
        return quoteOvertimeCategory;
    }

    public void setQuoteOvertimeCategory(String quoteOvertimeCategory) {
        this.quoteOvertimeCategory = quoteOvertimeCategory == null ? null : quoteOvertimeCategory.trim();
    }

    public String getQuoteOvertimeCause() {
        return quoteOvertimeCause;
    }

    public void setQuoteOvertimeCause(String quoteOvertimeCause) {
        this.quoteOvertimeCause = quoteOvertimeCause == null ? null : quoteOvertimeCause.trim();
    }

    public String getIsSuccessOrder() {
        return isSuccessOrder;
    }

    public void setIsSuccessOrder(String isSuccessOrder) {
        this.isSuccessOrder = isSuccessOrder == null ? null : isSuccessOrder.trim();
    }

    public String getLoseOrderCategory() {
        return loseOrderCategory;
    }

    public void setLoseOrderCategory(String loseOrderCategory) {
        this.loseOrderCategory = loseOrderCategory == null ? null : loseOrderCategory.trim();
    }

    public String getLoseOrderCause() {
        return loseOrderCause;
    }

    public void setLoseOrderCause(String loseOrderCause) {
        this.loseOrderCause = loseOrderCause == null ? null : loseOrderCause.trim();
    }
}