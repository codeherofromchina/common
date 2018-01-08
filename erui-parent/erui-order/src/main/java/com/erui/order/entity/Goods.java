package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 */

@Entity
@Table(name = "goods")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "parent_id")
    private Integer parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    private Boolean exchanged;

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "project_no")
    private String projectNo;

    private String sku;

    @Column(name = "mete_type")
    private String meteType; // 物料分类
    @Column(name = "pro_type")
    private String proType; // 产品分类
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "name_zh")
    private String nameZh;


  /*  @Column(name = "send_num")
    private Integer sendNum;    //本批次发货数量*/

    private String unit;

    private String brand;

    @Transient
    private String remarks; //备注

    @Transient
    private String packRequire;  //包装要求

   /* @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL},mappedBy = "goods")
    @JsonIgnore
    private  DeliverConsignGoods deliverConsignGoods;*/


    private String model;
    @Column(name = "contract_goods_num")
    private Integer contractGoodsNum;
    @Column(name = "client_desc")
    private String clientDesc;
    @Column(name = "require_purchase_date")
    private Date requirePurchaseDate;
    @Column(name = "tech_require")
    private String techRequire;
    @Column(name = "check_type")
    private String checkType;
    @Column(name = "check_method")
    private String checkMethod;

    private String certificate;
    @Column(name = "tech_audit")
    private String techAudit;

    @Column(name = "purchased_num")
    private Integer purchasedNum;
    @Column(name = "inspect_num")
    private Integer inspectNum;

    //已入库数量
    @Column(name = "instock_num")
    private Integer instockNum;
    @Column(name = "outstock_apply_num")
    private Integer outstockApplyNum;
    @Column(name = "outstock_num")
    private Integer outstockNum;

    public Integer getInspectNum() {
        return inspectNum;
    }

    public void setInspectNum(Integer inspectNum) {
        this.inspectNum = inspectNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getRemarks() {
        return remarks;
    }

  /*  public DeliverConsignGoods getDeliverConsignGoods() {
        return deliverConsignGoods;
    }

    public void setDeliverConsignGoods(DeliverConsignGoods deliverConsignGoods) {
        this.deliverConsignGoods = deliverConsignGoods;
    }*/

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPackRequire() {
        return packRequire;
    }

    public void setPackRequire(String packRequire) {
        this.packRequire = packRequire;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Boolean getExchanged() {
        return exchanged;
    }

    public void setExchanged(Boolean exchanged) {
        this.exchanged = exchanged;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
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

    public String getClientDesc() {
        return clientDesc;
    }

    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc;
    }

    public Date getRequirePurchaseDate() {
        return requirePurchaseDate;
    }

    public void setRequirePurchaseDate(Date requirePurchaseDate) {
        this.requirePurchaseDate = requirePurchaseDate;
    }

    public String getTechRequire() {
        return techRequire;
    }

    public void setTechRequire(String techRequire) {
        this.techRequire = techRequire;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getTechAudit() {
        return techAudit;
    }

    public void setTechAudit(String techAudit) {
        this.techAudit = techAudit;
    }


    public Integer getPurchasedNum() {
        return purchasedNum;
    }

    public void setPurchasedNum(Integer purchasedNum) {
        this.purchasedNum = purchasedNum;
    }

    public Integer getInstockNum() {
        return instockNum;
    }

    public void setInstockNum(Integer instockNum) {
        this.instockNum = instockNum;
    }

    public Integer getOutstockApplyNum() {
        return outstockApplyNum;
    }

    public void setOutstockApplyNum(Integer outstockApplyNum) {
        this.outstockApplyNum = outstockApplyNum;
    }

    public Integer getOutstockNum() {
        return outstockNum;
    }

    public void setOutstockNum(Integer outstockNum) {
        this.outstockNum = outstockNum;
    }

}