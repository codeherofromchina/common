package com.erui.order.model;

import com.erui.order.entity.Goods;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wangxiaodan on 2018/4/12.
 */
public class GoodsBookDetail implements Cloneable {
    private Integer goodsId;
    private String sku;
    private Integer contractGoodsNum; // 销售合同数量
    private Integer purchaseNum; // 采购数量
    private BigDecimal purchasePrice; // 采购单价
    private BigDecimal purchaseTotalPrice; // 采购总金额
    private String purchNo; // 采购合同号

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

    // 设置商品信息 step 1
    public void setGoods(Goods goods) {
        this.goodsId = goods.getId();
        this.sku = goods.getSku();
        this.contractGoodsNum = goods.getContractGoodsNum();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
