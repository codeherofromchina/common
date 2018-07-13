package com.erui.order.requestVo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * Created by wangxiaodan on 2017/12/13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PGoods {
    private Integer id;
    private Integer seq;
    private Integer goodsId;
    private String projectNo; // 项目号
    private String contractNo;
    private PGoods son;
    private Integer purchaseNum; // 采购数量
    private Integer inspectNum; // 已报检数量/报检数量
    private BigDecimal purchasePrice; // 采购金额
    private BigDecimal purchaseTotalPrice; // 采购总金额
    private String remark; // 采购备注
    private String sku;
    private String meteType; // 物料分类
    private String proType; // 产品分类
    private String nameEn;
    private String nameZh;
    private String unit;
    private String brand;
    private String model;
    private Integer contractGoodsNum; // 合同商品数量
    private String purchasedum; // 已采购数量
    private String clientDesc;//客户描述
    private Integer samples;
    private Integer unqualified;
    private String unqualifiedDesc;
    private Boolean unqualifiedFlag; // 是否不合格  true:不合格  false:合格
    private Integer instockNum;
    private String department;//所属事业部
    private BigDecimal price; //商品价格
    private String meteName;//物料分类名称

    public String getMeteName() {
        return meteName;
    }

    public void setMeteName(String meteName) {
        this.meteName = meteName;
    }

    public void setUnqualifiedFlag(Boolean unqualifiedFlag) {
        this.unqualifiedFlag = unqualifiedFlag;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public PGoods getSon() {
        return son;
    }

    public void setSon(PGoods son) {
        this.son = son;
    }

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public Integer getInspectNum() {
        return inspectNum;
    }

    public void setInspectNum(Integer inspectNum) {
        this.inspectNum = inspectNum;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMeteType() {
        return meteType;
    }

    public void setMeteType(String meteType) {
        this.meteType = meteType;
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

    public Integer getContractGoodsNum() {
        return contractGoodsNum;
    }

    public void setContractGoodsNum(Integer contractGoodsNum) {
        this.contractGoodsNum = contractGoodsNum;
    }

    public String getPurchasedum() {
        return purchasedum;
    }

    public void setPurchasedum(String purchasedum) {
        this.purchasedum = purchasedum;
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc;
    }

    public Integer getSamples() {
        return samples;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }

    public Integer getUnqualified() {
        return unqualified;
    }

    public void setUnqualified(Integer unqualified) {
        this.unqualified = unqualified;
        if (unqualified != null) {
            if (unqualified > 0) {
                this.unqualifiedFlag = true;
            } else {
                this.unqualifiedFlag = false;
            }
        }
    }

    public Boolean getUnqualifiedFlag() {
        return unqualifiedFlag;
    }

    public String getUnqualifiedDesc() {
        return unqualifiedDesc;
    }

    public void setUnqualifiedDesc(String unqualifiedDesc) {
        this.unqualifiedDesc = unqualifiedDesc;
    }

    public Integer getInstockNum() {
        return instockNum;
    }

    public void setInstockNum(Integer instockNum) {
        this.instockNum = instockNum;
    }
}
