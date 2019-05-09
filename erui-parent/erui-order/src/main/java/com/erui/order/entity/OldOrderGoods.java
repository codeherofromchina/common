package com.erui.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "old_order_goods")
public class OldOrderGoods {
    @Id
    private Long id;

    @Column(name = "order_no")
    private String orderNo;

    private String sku;

    private String lang;

    private String name;
    @Column(name = "name_zh")
    private String nameZh;

    private String brand;

    private String model;
    @Column(name = "spec_attrs")
    private String specAttrs;

    private String symbol;

    private Float price;
    @Column(name = "buy_number")
    private Integer buyNumber;
    @Column(name = "min_pack_naked_qty")
    private Integer minPackNakedQty;
    @Column(name = "nude_cargo_unit")
    private String nudeCargoUnit;
    @Column(name = "min_pack_unit")
    private String minPackUnit;

    private String thumb;
    @Column(name = "buyer_id")
    private Long buyerId;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "deleted_flag")
    private String deletedFlag;

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
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

    public String getSpecAttrs() {
        return specAttrs;
    }

    public void setSpecAttrs(String specAttrs) {
        this.specAttrs = specAttrs;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }

    public Integer getMinPackNakedQty() {
        return minPackNakedQty;
    }

    public void setMinPackNakedQty(Integer minPackNakedQty) {
        this.minPackNakedQty = minPackNakedQty;
    }

    public String getNudeCargoUnit() {
        return nudeCargoUnit;
    }

    public void setNudeCargoUnit(String nudeCargoUnit) {
        this.nudeCargoUnit = nudeCargoUnit;
    }

    public String getMinPackUnit() {
        return minPackUnit;
    }

    public void setMinPackUnit(String minPackUnit) {
        this.minPackUnit = minPackUnit;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }
}