package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品信息
 */

@Entity
@Table(name = "goods")
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

    // 是否是替换的商品  true :采购替换商品  false:原商品
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



    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "goods")
    @JsonIgnore
    private List<DeliverConsignGoods> deliverConsignGoodsList;



    private String model;
    /**
     * 订单合同商品数量
     */
    @Column(name = "contract_goods_num")
    private Integer contractGoodsNum;
    /**
     * 客户描述
     */
    @Column(name = "client_desc")
    private String clientDesc;

    /**
     * 要求采购到货日期
     */
    @Column(name = "require_purchase_date")
    private Date requirePurchaseDate;

    /**
     * 技术要求和使用条件
     */
    @Column(name = "tech_require")
    private String techRequire;
    @Column(name = "check_type")
    private String checkType;
    @Column(name = "check_method")
    private String checkMethod;

    private String certificate;
    @Column(name = "tech_audit")
    private String techAudit;

    // 已采购数量
    @Column(name = "purchased_num")
    private Integer purchasedNum;
    // 预采购数量
    @Column(name = "pre_purchased_num")
    private Integer prePurchsedNum;


    @Column(name = "inspect_num")
    private Integer inspectNum;

    //已入库数量
    @Column(name = "instock_num")
    private Integer instockNum;
    @Column(name = "outstock_apply_num")
    private Integer outstockApplyNum;
    //已发货数量
    @Column(name = "outstock_num")
    private Integer outstockNum;
    /*项目sku执行跟踪需要*/
    //项目开始日期
    @Column(name = "start_date")
    private Date startDate;
    //执行单约定交付日期
    @Column(name = "delivery_date")
    private Date deliveryDate;
    //合同变更后到货日期
    @Column(name = "pur_chg_date")
    private Date purChgDate;
    //采购合同签订日期
    @Column(name = "signing_date")
    private Date signingDate;
    //采购经办人
    @Column(name = "agent_id")
    private Integer agentId;
    //合同约定到货日期
    @Column(name = "arrival_date")
    private Date arrivalDate;
    //报检日期
    @Column(name = "inspect_date")
    private Date inspectDate;
    //检验员
    @Column(name = "check_user_id")
    private Integer checkUerId;
    //检验日期
    @Column(name = "check_date")
    private Date checkDate;
    //仓库经办人
    private Integer uid;
    //入库日期
    @Column(name = "instock_date")
    private Date instockDate;
    //放行日期
    @Column(name = "release_date")
    private Date releaseDate;
    //发送看货通知日期
    @Column(name = "send_date")
    private Date sendDate;
    //订舱人
    @Column(name = "sender_id")
    private Integer senderId;
    //订舱日期
    @Column(name = "booking_time")
    private Date bookingTime;
    //船期或航班
    @Column(name = "sailing_date")
    private Date sailingDate;
    //报关放行时间
    @Column(name = "customs_clearance")
    private Date customsClearance;
    //实际离港时间
    @Column(name = "leave_port_time")
    private Date leavePortTime;
    //预计抵达时间
    @Column(name = "arrival_port_time")
    private Date arrivalPortTime;
    //实际完成日期
    @Column(name = "accomplish_date")
    private Date accomplishDate;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getPurChgDate() {
        return purChgDate;
    }

    public void setPurChgDate(Date purChgDate) {
        this.purChgDate = purChgDate;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
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

    public Integer getCheckUerId() {
        return checkUerId;
    }

    public void setCheckUerId(Integer checkUerId) {
        this.checkUerId = checkUerId;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getInstockDate() {
        return instockDate;
    }

    public void setInstockDate(Date instockDate) {
        this.instockDate = instockDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Date getSailingDate() {
        return sailingDate;
    }

    public void setSailingDate(Date sailingDate) {
        this.sailingDate = sailingDate;
    }

    public Date getCustomsClearance() {
        return customsClearance;
    }

    public void setCustomsClearance(Date customsClearance) {
        this.customsClearance = customsClearance;
    }

    public Date getLeavePortTime() {
        return leavePortTime;
    }

    public void setLeavePortTime(Date leavePortTime) {
        this.leavePortTime = leavePortTime;
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


    public List<DeliverConsignGoods> getDeliverConsignGoodsList() {
        return deliverConsignGoodsList;
    }

    public void setDeliverConsignGoodsList(List<DeliverConsignGoods> deliverConsignGoodsList) {
        this.deliverConsignGoodsList = deliverConsignGoodsList;
    }


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

    public Integer getPrePurchsedNum() {
        return prePurchsedNum;
    }

    public void setPrePurchsedNum(Integer prePurchsedNum) {
        this.prePurchsedNum = prePurchsedNum;
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