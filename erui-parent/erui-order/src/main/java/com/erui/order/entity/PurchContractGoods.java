package com.erui.order.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "purch_contract_goods")
public class PurchContractGoods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    /**
     * 采购合同
     */
    @ManyToOne
    @JoinColumn(name = "purch_contract_id")
    @JsonIgnore
    private PurchContract purchContract;

    /**
     * 商品ID
     */
    @Transient
    private Integer gId;

    /**
     * 采购合同ID
     */
    @Transient
    private Integer pcId;

    /**
     * 采购合同商品ID
     */
    @Transient
    private Integer pcgId;

    /**
     * 替换的商品
     */
    @Transient
    @JsonIgnore
    private PurchContractGoods son;

    /**
     * 务必没有修改goods权限的能力
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private PurchContractGoods parent;

    /**
     * 项目号
     */
    @Column(name = "project_no")
    private String projectNo;

    /**
     * 合同号
     */
    @Column(name = "contract_no")
    private String contractNo;

    /**
     * 合同总价
     */
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    /**
     * 是否是替换的商品  true :采购替换商品  false:原商品
     */
    private Boolean exchanged;

    /**
     * 采购数量
     */
    @Column(name = "purchase_num")
    private Integer purchaseNum;

    /**
     * 预采购数量
     */
    @Column(name = "pre_purch_contract_num")
    private Integer prePurchContractNum = 0;

    /**
     * 已采购数量
     */
    @Column(name = "purchased_num")
    private Integer PurchasedNum = 0;

    /**
     * 不含税单价
     */
    @Column(name = "non_tax_price")
    private BigDecimal nonTaxPrice;

    /**
     * 采购单价
     */
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    /**
     * 采购金额
     */
    @Column(name = "purchase_total_price")
    private BigDecimal purchaseTotalPrice;

    /**
     * 采购备注
     */
    @Column(name = "purchase_remark")
    private String purchaseRemark;

    /**
     * 含税单价,提交采购时计算
     */
    @Column(name = "tax_price")
    private BigDecimal taxPrice;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PurchContract getPurchContract() {
        return purchContract;
    }

    public void setPurchContract(PurchContract purchContract) {
        this.purchContract = purchContract;
    }

    public Integer getgId() {
        return gId;
    }

    public void setgId(Integer gId) {
        this.gId = gId;
    }

    public PurchContractGoods getSon() {
        return son;
    }

    public void setSon(PurchContractGoods son) {
        this.son = son;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getExchanged() {
        return exchanged;
    }

    public void setExchanged(Boolean exchanged) {
        this.exchanged = exchanged;
    }

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public Integer getPrePurchContractNum() {
        return prePurchContractNum;
    }

    public void setPrePurchContractNum(Integer prePurchContractNum) {
        prePurchContractNum = prePurchContractNum;
    }

    public Integer getPurchasedNum() {
        return PurchasedNum;
    }

    public void setPurchasedNum(Integer purchasedNum) {
        PurchasedNum = purchasedNum;
    }

    public BigDecimal getNonTaxPrice() {
        return nonTaxPrice;
    }

    public void setNonTaxPrice(BigDecimal nonTaxPrice) {
        this.nonTaxPrice = nonTaxPrice;
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

    public String getPurchaseRemark() {
        return purchaseRemark;
    }

    public void setPurchaseRemark(String purchaseRemark) {
        this.purchaseRemark = purchaseRemark;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PurchContractGoods getParent() {
        return parent;
    }

    public void setParent(PurchContractGoods parent) {
        this.parent = parent;
    }

    public Integer getPcId() {
        return pcId;
    }

    public void setPcId(Integer pcId) {
        this.pcId = pcId;
    }

    public Integer getPcgId() {
        return pcgId;
    }

    public void setPcgId(Integer pcgId) {
        this.pcgId = pcgId;
    }
}
