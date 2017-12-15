package com.erui.order.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购商品信息
 */
@Entity
@Table(name="purch_goods")
public class PurchGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @Column(name="project_no")
    private String projectNo;

    @Column(name="contract_no")
    private String contractNo;

    @ManyToOne
    @JoinColumn(name="purch_id")
    private Purch purch;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="goods_id")
    private Goods goods;

    private boolean son = false;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private PurchGoods parent;

    @Column(name="purchase_num")
    private Integer purchaseNum;

    @Column(name="inspect_num")
    private Integer inspectNum;

    @Column(name="purchase_price")
    private BigDecimal purchasePrice;

    @Column(name="purchase_total_price")
    private BigDecimal purchaseTotalPrice;

    @Column(name="purchase_remark")
    private String purchaseRemark;

    @Column(name="create_time")
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

    public boolean isSon() {
        return son;
    }

    public void setSon(boolean son) {
        this.son = son;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}