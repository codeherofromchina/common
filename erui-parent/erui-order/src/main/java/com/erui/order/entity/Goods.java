package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;

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

    @ManyToOne(fetch = FetchType.EAGER)
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


    /* @Column(name = "send_num")*/
    @Transient
    private Integer sendNum;    //本批次发货数量

    private String unit;

    private String brand;

    @Transient
    private String remarks; //备注

    @Transient
    private String packRequire;  //包装要求


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "goods")
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
     * 采购需用日期--采购申请中
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
    //采购合同 预采购的数量
    @Column(name = "pre_purch_contract_num")
    private Integer prePurchContractNum;

    @Column(name = "inspect_num")
    private Integer inspectNum;

    //已入库数量
    @Column(name = "instock_num")
    private Integer instockNum;
    @Column(name = "outstock_apply_num")
    private Integer outstockApplyNum = 0;
    //已发货数量
    @Column(name = "outstock_num")
    private Integer outstockNum = 0;
    /*项目sku执行跟踪需要*/
    //项目开始日期
    @Column(name = "start_date")
    private Date startDate;
    //执行单约定交付日期
    @Column(name = "delivery_date")
    private String deliveryDate;
    // 执行单变更后日期
    @Column(name = "exe_chg_date")
    private Date exeChgDate;

    // 执行单变更后日期 - 项目中的日期
    @Column(name = "project_require_purchase_date")
    private Date projectRequirePurchaseDate;

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
    //检验完成日期
    @Column(name = "check_done_date")
    private Date checkDoneDate;
    //入库经办人
    private Integer uid;
    //入库日期
    @Column(name = "instock_date")
    private Date instockDate;
    //放行日期
    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "ware_houseman")
    private Integer wareHouseman;  //出库经办人id

    @Column(name = "leave_date")
    private Date leaveDate;   //出库日期

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

    @Column(name = "inspect_instock_num")
    private Integer inspectInstockNum = 0;    //质检入库总数量

    @Column(name = "null_instock_num")
    private Integer nullInstockNum = 0;    //厂家直发总数量

    @Transient
    private Integer outboundNum = 0;  //出库数量

    @Transient
    private Integer straightNum = 0; //厂家直发数量

    @Transient
    private String deliverDetailNo; //产品放行单号
    private String department;//所属事业部
    private BigDecimal price; //商品价格
    @Column(name = "mete_name")
    private String meteName;//物料分类名称


    // 务必没有修改PurchGoods权限的能力
    @OneToMany(mappedBy = "goods", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PurchGoods> purchGoods;

    public String getMeteName() {
        return meteName;
    }

    public void setMeteName(String meteName) {
        this.meteName = meteName;
    }

    public String getDepartment() {
        return department;
    }

    public String getDepartmentName() {
        String result = null;
        if (StringUtils.isNotBlank(department)) {
            switch (department) {
                case "9931":
                    result = "易瑞-工业工具";
                    break;
                case "9929":
                    result = "易瑞-工业品设备";
                    break;
                case "9928":
                    result = "易瑞-安防和劳保设备";
                    break;
                case "9930":
                    result = "易瑞-电力电工（含钢丝绳索具）";
                    break;
                case "9907":
                    result = "易瑞-钻完井设备事业部";
                    break;
                case "9917":
                    result = "康博瑞-商务技术";
                    break;
                case "9902":
                    result = "康博瑞-配件中心";
                    break;
                case "9904":
                    result = "油田设备事业部-石油钢管公司";
                    break;
                case "9905":
                    result = "油田设备事业部-通用设备公司";
                    break;
                case "9903":
                    result = "油气增产装备事业部";
                    break;
                case "9901":
                    result = "运维共享服务中心（钻采装备集团）";
                    break;
                case "9899":
                    result = "WEFIC公司事业部-商务技术一部";
                    break;
                case "9916":
                    result = "WEFIC公司事业部-商务技术二部";
                    break;
                case "9915":
                    result = "美国压缩机技术服务公司";
                    break;
                case "9908":
                    result = "物流";
                    break;
                case "9942":
                    result = "易瑞-国内销售部";
                    break;
                case "9937":
                    result = "财务金融中心";
                    break;
                case "9936":
                    result = "营运中心";
                    break;
                case "9948":
                    result = "测试二级部门";
                    break;
                case "9969":
                    result = "易瑞-工业品事业部";
                    break;
                case "9970":
                    result = "易瑞-采油工程事业部";
                    break;
                default:
                    return department;

            }
        }
        return result;
    }

    public Integer getPrePurchContractNum() {
        return prePurchContractNum;
    }

    public void setPrePurchContractNum(Integer prePurchContractNum) {
        this.prePurchContractNum = prePurchContractNum;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getExeChgDate() {
        return exeChgDate;
    }

    public void setExeChgDate(Date exeChgDate) {
        this.exeChgDate = exeChgDate;
    }

    public Date getProjectRequirePurchaseDate() {
        return projectRequirePurchaseDate;
    }

    public void setProjectRequirePurchaseDate(Date projectRequirePurchaseDate) {
        this.projectRequirePurchaseDate = projectRequirePurchaseDate;
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

    public Date getCheckDoneDate() {
        return checkDoneDate;
    }

    public void setCheckDoneDate(Date checkDoneDate) {
        this.checkDoneDate = checkDoneDate;
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
        if (exchanged == null) {
            return false;
        }
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

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public Integer getInspectInstockNum() {
        return inspectInstockNum;
    }

    public void setInspectInstockNum(Integer inspectInstockNum) {
        this.inspectInstockNum = inspectInstockNum;
    }

    public void setNullInstockNum(Integer nullInstockNum) {
        this.nullInstockNum = nullInstockNum;
    }

    public Integer getNullInstockNum() {
        return nullInstockNum;
    }

    public Integer getOutboundNum() {
        return outboundNum;
    }

    public void setOutboundNum(Integer outboundNum) {
        this.outboundNum = outboundNum;
    }

    public void setStraightNum(Integer straightNum) {
        this.straightNum = straightNum;
    }

    public Integer getStraightNum() {
        return straightNum;
    }

    public String getDeliverDetailNo() {
        return deliverDetailNo;
    }

    public void setDeliverDetailNo(String deliverDetailNo) {
        this.deliverDetailNo = deliverDetailNo;
    }

    public Integer getWareHouseman() {
        return wareHouseman;
    }

    public void setWareHouseman(Integer wareHouseman) {
        this.wareHouseman = wareHouseman;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public List<PurchGoods> getPurchGoods() {
        return purchGoods;
    }

    public void setPurchGoods(List<PurchGoods> purchGoods) {
        this.purchGoods = purchGoods;
    }
}