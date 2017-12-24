package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.transaction.annotation.Transactional;

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
    @JoinColumn(name="parent_id")
    @JsonIgnore
    private PurchGoods parent;

    // 采购数量
    @Column(name = "purchase_num")
    private Integer purchaseNum;

    // 已报检数量
    @Column(name = "inspect_num")
    private Integer inspectNum;

    /**
     * 已报检提交数量
     */
    @Column(name = "inspect_submit_num")
    private Integer inspectSubmitNum;

    /**
     * 已入库数量
     */
    @Column(name = "instock_num")
    private Integer instockNum;

    // 采购单价
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    // 采购总金额
    @Column(name = "purchase_total_price")
    private BigDecimal purchaseTotalPrice;
    // 采购备注
    @Column(name = "purchase_remark")
    private String purchaseRemark;

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


    public Integer getInspectSubmitNum() {
        return inspectSubmitNum;
    }

    public void setInspectSubmitNum(Integer inspectSubmitNum) {
        this.inspectSubmitNum = inspectSubmitNum;
    }

    public Integer getInstockNum() {
        return instockNum;
    }

    public void setInstockNum(Integer instockNum) {
        this.instockNum = instockNum;
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