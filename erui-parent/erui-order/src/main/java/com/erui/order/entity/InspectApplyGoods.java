package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 报检单-商品信息
 */
@Entity
@Table(name = "inspect_apply_goods")
public class InspectApplyGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "inspect_apply_id")
    @JsonIgnore
    private InspectApply inspectApply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspect_report_id")
    private InspectReport inspectReport;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    // 务必没有任何增删改的权限
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purch_goods_id")
    private PurchGoods purchGoods;

    @Transient
    private Integer purchGid; // 采购商品ID


    /**
     * 本次报检对应的采购单商品中的采购数量
     */
    @Column(name = "purchase_num")
    private Integer purchaseNum;

    /**
     * 当次采购已经报检数量，用于返回参数
     */
    @Transient
    private Integer hasInspectNum;

    // 报检数量
    @Column(name = "inspect_num")
    private Integer inspectNum;

    private BigDecimal height;

    private String lwh;

    private Integer samples;

    private String remarks;

    private Integer unqualified;

    @Column(name = "unqualified_desc")
    private String unqualifiedDesc;
    /**
     * 不合格类型
     */
    @Column(name = "unqualified_type")
    private String unqualifiedType;

    @Column(name = "instock_num")
    private Integer instockNum;


    @Column(name = "create_time")
    private Date createTime;
    /**
     * 质量检验类型
     */
    @Column(name = "quality_inspect_type")
    private String qualityInspectType;

    public String getUnqualifiedType() {
        return unqualifiedType;
    }

    public void setUnqualifiedType(String unqualifiedType) {
        this.unqualifiedType = unqualifiedType;
    }

    public String getQualityInspectType() {
        return qualityInspectType;
    }

    public void setQualityInspectType(String qualityInspectType) {
        this.qualityInspectType = qualityInspectType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InspectApply getInspectApply() {
        return inspectApply;
    }

    public void setInspectApply(InspectApply inspectApply) {
        this.inspectApply = inspectApply;
    }

    public InspectReport getInspectReport() {
        return inspectReport;
    }

    public void setInspectReport(InspectReport inspectReport) {
        this.inspectReport = inspectReport;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getPurchGid() {
        return purchGid;
    }

    public void setPurchGid(Integer purchGid) {
        this.purchGid = purchGid;
    }

    public PurchGoods getPurchGoods() {
        return purchGoods;
    }

    public void setPurchGoods(PurchGoods purchGoods) {
        this.purchGoods = purchGoods;
    }

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public Integer getHasInspectNum() {
        return hasInspectNum;
    }

    public void setHasInspectNum(Integer hasInspectNum) {
        this.hasInspectNum = hasInspectNum;
    }

    public Integer getInspectNum() {
        return inspectNum;
    }

    public void setInspectNum(Integer inspectNum) {
        this.inspectNum = inspectNum;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getLwh() {
        return lwh;
    }

    public void setLwh(String lwh) {
        this.lwh = lwh;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}