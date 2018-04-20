package com.erui.order.model;

import com.erui.order.entity.Goods;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品台账信息
 * Created by wangxiaodan on 2018/4/12.
 */
public class GoodsBookDetail implements Cloneable {
    private Integer goodsId;
    private String sku;
    private Integer contractGoodsNum; // 销售合同数量
    private String proType; // 产品分类
    private String nameEn; // 外文品名
    private String nameZh; // 中文品名
    private String unit; // 单位
    private String brand; // 品牌
    private String model; // 规格型号
    private Integer purchaseNum; // 采购数量
    private BigDecimal purchasePrice; // 采购单价
    private BigDecimal purchaseTotalPrice; // 采购总金额
    private String purchNo; // 采购合同号
    private Date signingDate; // 采购合同签订日期
    private Date arrivalDate; // 合同约定到货日期
    private Date inspectDate; // 报检时间 / 采购到货日期
    private Integer payType; // 采购给供应商付款方式
    private Date payFactoryDate; // 给工厂付款日期
    private String currencyBn; // 货币类型
    private Integer inspectNum; // 报检数量
    private Integer qualifiedNum; // 合格数量
    private String supplierName; // 供应商
    private Date checkDate; // 检验完成日期
    private Date instockDate; // 入库日期
    private Integer sendNum; // 发货数量
    private Date requireBookingDate; // 市场要求订舱时间
    private Date packDoneDate; // 包装完成日期   // 出库质检提交/出库质检日期    deliver_detail - check_date
    private Date bookingTime;   //下发订舱日期
    private String logisticsUserName;    //物流经办人名称
    private Date leaveFactory;  //离厂时间
    private String logiInvoiceNo;   //物流发票号
    private Date packingTime;   //通知市场箱单时间
    private Date sailingDate;   //船期或航班
    private Date arrivalPortTime;   //预计抵达时间
    private Date accomplishDate; //实际完成时间

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getContractGoodsNum() {
        return contractGoodsNum;
    }

    public void setContractGoodsNum(Integer contractGoodsNum) {
        this.contractGoodsNum = contractGoodsNum;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchaseTotalPrice() {
        return purchaseTotalPrice;
    }

    public void setPurchaseTotalPrice(BigDecimal purchaseTotalPrice) {
        this.purchaseTotalPrice = purchaseTotalPrice;
    }

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo;
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

    public Date getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
    }


    public Integer getPayType() {
        return payType;
    }


    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getPayFactoryDate() {
        return payFactoryDate;
    }

    public void setPayFactoryDate(Date payFactoryDate) {
        this.payFactoryDate = payFactoryDate;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public Integer getInspectNum() {
        return inspectNum;
    }

    public void setInspectNum(Integer inspectNum) {
        this.inspectNum = inspectNum;
    }

    public Integer getQualifiedNum() {
        return qualifiedNum;
    }

    public void setQualifiedNum(Integer qualifiedNum) {
        this.qualifiedNum = qualifiedNum;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getInstockDate() {
        return instockDate;
    }

    public void setInstockDate(Date instockDate) {
        this.instockDate = instockDate;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public Date getRequireBookingDate() {
        return requireBookingDate;
    }

    public void setRequireBookingDate(Date requireBookingDate) {
        this.requireBookingDate = requireBookingDate;
    }

    public Date getPackDoneDate() {
        return packDoneDate;
    }

    public void setPackDoneDate(Date packDoneDate) {
        this.packDoneDate = packDoneDate;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getLogisticsUserName() {
        return logisticsUserName;
    }

    public void setLogisticsUserName(String logisticsUserName) {
        this.logisticsUserName = logisticsUserName;
    }

    public Date getLeaveFactory() {
        return leaveFactory;
    }

    public void setLeaveFactory(Date leaveFactory) {
        this.leaveFactory = leaveFactory;
    }

    public String getLogiInvoiceNo() {
        return logiInvoiceNo;
    }

    public void setLogiInvoiceNo(String logiInvoiceNo) {
        this.logiInvoiceNo = logiInvoiceNo;
    }

    public Date getPackingTime() {
        return packingTime;
    }

    public void setPackingTime(Date packingTime) {
        this.packingTime = packingTime;
    }

    public Date getSailingDate() {
        return sailingDate;
    }

    public void setSailingDate(Date sailingDate) {
        this.sailingDate = sailingDate;
    }

    public Date getArrivalPortTime() {
        return arrivalPortTime;
    }

    public void setArrivalPortTime(Date arrivalPortTime) {
        this.arrivalPortTime = arrivalPortTime;
    }

    public Date getAccomplishDate() {
        return accomplishDate;
    }

    public void setAccomplishDate(Date accomplishDate) {
        this.accomplishDate = accomplishDate;
    }

    // 设置商品信息
    public void setGoods(Goods goods) {
        this.goodsId = goods.getId();
        this.sku = goods.getSku();
        this.contractGoodsNum = goods.getContractGoodsNum();
        this.proType = goods.getProType();
        this.nameEn = goods.getNameEn();
        this.nameZh = goods.getNameZh();
        this.unit = goods.getUnit();
        this.brand = goods.getBrand();
        this.model = goods.getModel();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
