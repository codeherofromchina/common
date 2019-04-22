package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购商品信息
 */
@Entity
@Table(name = "purch_goods")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    @Transient
    private PurchGoods son;

    @Column(name = "project_no")
    private String projectNo;

    @Column(name = "contract_no")
    private String contractNo;

    @ManyToOne
    @JoinColumn(name = "purch_id")
    @JsonIgnore
    private Purch purch;

    /**
     * 采购合同
     */
    @ManyToOne
    @JoinColumn(name = "purch_contract_id")
    @JsonIgnore
    private PurchContract purchContract;

    // 务必没有修改goods权限的能力
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goods_id")
    private Goods goods;
    /**
     * 商品ID
     */
    @Transient
    private Integer gId;

    private Boolean exchanged;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private PurchGoods parent;

    // 采购数量
    @Column(name = "purchase_num")
    private Integer purchaseNum;

    // 已报检数量，报检提交才增加此数量
    @Column(name = "inspect_num")
    private Integer inspectNum;

    // 预报检数量，报检保存就修改此数量
    @Column(name = "pre_inspect_num")
    private Integer preInspectNum;

    // 检验合格商品数量
    @Column(name = "good_num")
    private Integer goodNum;


    // 采购单价
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    // 采购总金额
    @Column(name = "purchase_total_price")
    private BigDecimal purchaseTotalPrice;
    // 采购备注
    @Column(name = "purchase_remark")
    private String purchaseRemark;

    // 含税单价,提交采购时计算
    @Column(name = "tax_price")
    private BigDecimal taxPrice;
    //
    @Column(name = "non_tax_price")
    private BigDecimal nonTaxPrice;
    @Column(name = "total_price")
    private BigDecimal totalPrice;

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

    public PurchGoods getSon() {
        return son;
    }

    public void setSon(PurchGoods son) {
        this.son = son;
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

    public Purch getPurch() {
        return purch;
    }

    public void setPurch(Purch purch) {
        this.purch = purch;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getgId() {
        return gId;
    }

    public void setgId(Integer gId) {
        this.gId = gId;
    }

    public Boolean getExchanged() {
        return exchanged;
    }

    public void setExchanged(Boolean exchanged) {
        this.exchanged = exchanged;
    }

    public PurchGoods getParent() {
        return parent;
    }

    public void setParent(PurchGoods parent) {
        this.parent = parent;
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

    public Integer getPreInspectNum() {
        return preInspectNum;
    }

    public void setPreInspectNum(Integer preInspectNum) {
        this.preInspectNum = preInspectNum;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
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

    public BigDecimal getNonTaxPrice() {
        return nonTaxPrice;
    }

    public void setNonTaxPrice(BigDecimal nonTaxPrice) {
        this.nonTaxPrice = nonTaxPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PurchContract getPurchContract() {
        return purchContract;
    }

    public void setPurchContract(PurchContract purchContract) {
        this.purchContract = purchContract;
    }
}