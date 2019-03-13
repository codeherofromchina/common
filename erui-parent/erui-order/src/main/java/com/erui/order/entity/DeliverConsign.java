package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 出口发货通知单
 */
@Entity
@Table(name = "deliver_consign")
public class DeliverConsign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 出口通知单号
     */
    @Column(name = "deliver_consign_no")
    private String deliverConsignNo;
    //orderId
    @Transient
    private Integer oId;

    /**
     * 订单ID
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @OneToOne(mappedBy = "deliverConsign", fetch = FetchType.LAZY)
    private DeliverDetail deliverDetail;

    /**
     * 发货申请部门
     */
    @Column(name = "dept_id")
    private Integer deptId;

    /**
     * 发货申请部门名称
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * 报关主体
     */
    @Column(name = "co_id")
    private String coId;

    /**
     *
     */
    @Column(name = "exec_co_name")
    private String execCoName;

    /**
     * 填表日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "write_date")
    private Date writeDate;

    /**
     * 市场订舱要求时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "booking_date")
    private Date bookingDate;

    /**
     * 状态 1:未编辑 2：保存/草稿 3:已提交 4:完成
     */
    private Integer status;

    /**
     * //是否已发货   1:未选中   2：已选中
     */
    @Column(name = "deliver_yn")
    private Integer deliverYn = 1;  //是否已发货      1:未发货  2：已发货

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 国家
     */
    private String country;

    /**
     * 所属地区
     */
    private String region;

    /**
     * 备注
     */
    private String remarks;
    //出口通知单附件
   /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "deliver_consign_attach",
            joinColumns = @JoinColumn(name = "deliver_consign_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))*/
    @Transient
    private List<Attachment> attachmentSet = new ArrayList<>();
    //收款信息
    @JoinColumn(name = "deliver_consign_id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<DeliverConsignPayment> deliverConsignPayments = new ArrayList<>();
    //出口通知单商品
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "deliver_consign_id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<DeliverConsignGoods> deliverConsignGoodsSet = new ArrayList<>();

    @Column(name = "advance_money")
    private BigDecimal advanceMoney;   //预收金额      /应收账款余额

    @Column(name = "this_shipments_money")
    private BigDecimal thisShipmentsMoney;   //本批次发货金额

    @Column(name = "line_of_credit")
    private BigDecimal lineOfCredit;    //授信额度

    @Column(name = "credit_available")
    private BigDecimal creditAvailable;    //可用授信额度

    @Transient
    private BigDecimal exchangeRate;    //订单汇率

    /**
     * 销售合同号
     */
    @Column(name = "contract_no")
    private String contractNo;

    /**
     * 费用承担主体及发票抬头
     */
    @Column(name = "invoice_rise")
    private String invoiceRise;

    /**
     * 业务项目性质，逗号分隔多个 1:一般贸易 2:加工贸易 3:转口贸易 4:展会物资 5:免费赠送 6:对外承包工程 7:其他
     */
    @Column(name = "business_nature")
    private String businessNature;

    /**
     * 业务项目简述及中英货物名称
     */
    @Column(name = "business_sketch")
    private String businessSketch;

    /**
     * 合同总价（美元）
     */
    @Transient
    private BigDecimal totalPriceUsd;

    /**
     * 报关金额
     */
    @Column(name = "declare_customs_money")
    private BigDecimal declareCustomsMoney;

    /**
     * 加工贸易金额
     */
    @Column(name = "trade_money")
    private BigDecimal tradeMoney;

    /**
     * 直接转口金额
     */
    @Column(name = "direct_transfer_money")
    private BigDecimal directTransferMoney;

    /**
     * 间接转口金额
     */
    @Column(name = "indirect_transfer_money")
    private BigDecimal indirectTransferMoney;

    /**
     * 清关金额
     */
    @Column(name = "clear_customs_money")
    private BigDecimal clearCustomsMoney;

    /**
     * 付款方式 1:T/T 2:L/C 3:其他
     */
    @Column(name = "pay_method")
    private Integer payMethod;

    /**
     * 发运批次
     */
    @Column(name = "shipping_batch")
    private String shippingBatch;

    /**
     * 多次发运需注明每批次金额及币种
     */
    @Column(name = "more_batch_explain")
    private String moreBatchExplain;

    /**
     * 应收账款金额（美元）
     */
    @Transient
    private BigDecimal receivablePriceUsd;

    /**
     * 回款责任人
     */
    @Transient
    private String perLiableRepay;

    /**
     * 是否为危险品 1:是 2:否
     */
    @Column(name = "is_dangerous")
    private Integer isDangerous;

    /**
     * 货物存放地
     */
    @Column(name = "goods_deposit_place")
    private String goodsDepositPlace;

    /**
     * 是否投出口信用保险 1:是 2:否
     */
    @Column(name = "has_insurance")
    private Integer hasInsurance;

    /**
     * 订舱信息ID
     */
    @OneToOne(mappedBy = "deliverConsign", fetch = FetchType.LAZY)
    private DeliverConsignBookingSpace deliverConsignBookingSpace;

    /**
     * 国家负责人Id
     */
    @Column(name = "country_leader_id")
    private Integer countryLeaderId;

    /**
     * 国家负责人
     */
    @Column(name = "country_leader")
    private String countryLeader;

    /**
     * 结算专员Id
     */
    @Column(name = "settlement_leader_id")
    private Integer settlementLeaderId;

    /**
     * 结算专员
     */
    @Column(name = "settlement_leader")
    private String settlementLeader;

    /**
     * 物流负责人Id
     */
    @Column(name = "logistics_leader_id")
    private Integer logisticsLeaderId;

    /**
     * 物流负责人
     */
    @Column(name = "logistics_leader")
    private String logisticsLeader;

    /**
     * 事业部项目负责人Id
     */
    @Column(name = "business_leader_id")
    private Integer businessLeaderId;

    /**
     * 事业部项目负责人
     */
    @Column(name = "business_leader")
    private String businessLeader;

    /**
     * 审核状态
     */
    @Column(name = "auditing_status")
    private Integer auditingStatus;

    /**
     * 审核进度
     */
    @Column(name = "auditing_process")
    private String auditingProcess;

    /**
     * 当前审核人ID
     */
    @Column(name = "auditing_user_id")
    private String auditingUserId;

    /**
     * 当前审核人名字
     */
    @Column(name = "auditing_user")
    private String auditingUser;

    /**
     * 客户代码或名称
     */
    @Column(name = "crm_code_or_name")
    private String crmCodeOrName;

    /**
     * 通过的审核人列表
     */
    @Column(name = "audi_remark")
    private String audiRemark;

    /**
     * 创建人名字
     */
    @Column(name = "create_user_name")
    private String createUserName;

    /**
     * 项目审核接口中使用，审核的原因字段
     */
    @Transient
    private String auditingReason;

    /**
     * 项目审核接口中使用，审核的类型字段，审核类型：-1：驳回（驳回必须存在驳回原因参数） 其他或空：正常审核
     */
    @Transient
    private String auditingType;

    /**
     * 审核日志，驳回操作使用
     */
    @Transient
    private Integer checkLogId;

    /**
     * 提报人
     */
    @Transient
    private String agentName;


    public List<DeliverConsignPayment> getDeliverConsignPayments() {
        return deliverConsignPayments;
    }


    public void setDeliverConsignPayments(List<DeliverConsignPayment> deliverConsignPayments) {
        this.deliverConsignPayments = deliverConsignPayments;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliverConsignNo() {
        return deliverConsignNo;
    }

    public void setDeliverConsignNo(String deliverConsignNo) {
        this.deliverConsignNo = deliverConsignNo;
    }

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeliverYn() {
        return deliverYn;
    }

    public void setDeliverYn(Integer deliverYn) {
        this.deliverYn = deliverYn;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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

    public List<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(List<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }

    public List<DeliverConsignGoods> getDeliverConsignGoodsSet() {
        return deliverConsignGoodsSet;
    }

    public void setDeliverConsignGoodsSet(List<DeliverConsignGoods> deliverConsignGoodsSet) {
        this.deliverConsignGoodsSet = deliverConsignGoodsSet;
    }

    public DeliverDetail getDeliverDetail() {
        return deliverDetail;
    }

    public void setDeliverDetail(DeliverDetail deliverDetail) {
        this.deliverDetail = deliverDetail;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getLineOfCredit() {
        return lineOfCredit;
    }

    public void setLineOfCredit(BigDecimal lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
    }

    public BigDecimal getCreditAvailable() {
        return creditAvailable;
    }

    public void setCreditAvailable(BigDecimal creditAvailable) {
        this.creditAvailable = creditAvailable;
    }

    public BigDecimal getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(BigDecimal advanceMoney) {
        this.advanceMoney = advanceMoney;
    }

    public BigDecimal getThisShipmentsMoney() {
        return thisShipmentsMoney;
    }

    public void setThisShipmentsMoney(BigDecimal thisShipmentsMoney) {
        this.thisShipmentsMoney = thisShipmentsMoney;
    }


    public String getInvoiceRise() {
        return invoiceRise;
    }

    public void setInvoiceRise(String invoiceRise) {
        this.invoiceRise = invoiceRise;
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature;
    }

    public String getBusinessSketch() {
        return businessSketch;
    }

    public void setBusinessSketch(String businessSketch) {
        this.businessSketch = businessSketch;
    }

    public BigDecimal getDeclareCustomsMoney() {
        return declareCustomsMoney;
    }

    public void setDeclareCustomsMoney(BigDecimal declareCustomsMoney) {
        this.declareCustomsMoney = declareCustomsMoney;
    }

    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public BigDecimal getDirectTransferMoney() {
        return directTransferMoney;
    }

    public void setDirectTransferMoney(BigDecimal directTransferMoney) {
        this.directTransferMoney = directTransferMoney;
    }

    public BigDecimal getIndirectTransferMoney() {
        return indirectTransferMoney;
    }

    public void setIndirectTransferMoney(BigDecimal indirectTransferMoney) {
        this.indirectTransferMoney = indirectTransferMoney;
    }

    public BigDecimal getClearCustomsMoney() {
        return clearCustomsMoney;
    }

    public void setClearCustomsMoney(BigDecimal clearCustomsMoney) {
        this.clearCustomsMoney = clearCustomsMoney;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getShippingBatch() {
        return shippingBatch;
    }

    public void setShippingBatch(String shippingBatch) {
        this.shippingBatch = shippingBatch;
    }

    public String getMoreBatchExplain() {
        return moreBatchExplain;
    }

    public void setMoreBatchExplain(String moreBatchExplain) {
        this.moreBatchExplain = moreBatchExplain;
    }

    public Integer getIsDangerous() {
        return isDangerous;
    }

    public void setIsDangerous(Integer isDangerous) {
        this.isDangerous = isDangerous;
    }

    public String getGoodsDepositPlace() {
        return goodsDepositPlace;
    }

    public void setGoodsDepositPlace(String goodsDepositPlace) {
        this.goodsDepositPlace = goodsDepositPlace;
    }

    public Integer getHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(Integer hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public DeliverConsignBookingSpace getDeliverConsignBookingSpace() {
        return deliverConsignBookingSpace;
    }

    public void setDeliverConsignBookingSpace(DeliverConsignBookingSpace deliverConsignBookingSpace) {
        this.deliverConsignBookingSpace = deliverConsignBookingSpace;
    }

    public Integer getCountryLeaderId() {
        return countryLeaderId;
    }

    public void setCountryLeaderId(Integer countryLeaderId) {
        this.countryLeaderId = countryLeaderId;
    }

    public String getCountryLeader() {
        return countryLeader;
    }

    public void setCountryLeader(String countryLeader) {
        this.countryLeader = countryLeader;
    }

    public Integer getSettlementLeaderId() {
        return settlementLeaderId;
    }

    public void setSettlementLeaderId(Integer settlementLeaderId) {
        this.settlementLeaderId = settlementLeaderId;
    }

    public String getSettlementLeader() {
        return settlementLeader;
    }

    public void setSettlementLeader(String settlementLeader) {
        this.settlementLeader = settlementLeader;
    }

    public Integer getLogisticsLeaderId() {
        return logisticsLeaderId;
    }

    public void setLogisticsLeaderId(Integer logisticsLeaderId) {
        this.logisticsLeaderId = logisticsLeaderId;
    }

    public String getLogisticsLeader() {
        return logisticsLeader;
    }

    public void setLogisticsLeader(String logisticsLeader) {
        this.logisticsLeader = logisticsLeader;
    }

    public Integer getBusinessLeaderId() {
        return businessLeaderId;
    }

    public void setBusinessLeaderId(Integer businessLeaderId) {
        this.businessLeaderId = businessLeaderId;
    }

    public String getBusinessLeader() {
        return businessLeader;
    }

    public void setBusinessLeader(String businessLeader) {
        this.businessLeader = businessLeader;
    }

    public Integer getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(Integer auditingStatus) {
        this.auditingStatus = auditingStatus;
    }

    public String getAuditingProcess() {
        return auditingProcess;
    }

    public void setAuditingProcess(String auditingProcess) {
        this.auditingProcess = auditingProcess;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId;
    }

    public String getAuditingUser() {
        return auditingUser;
    }

    public void setAuditingUser(String auditingUser) {
        this.auditingUser = auditingUser;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public BigDecimal getTotalPriceUsd() {
        return totalPriceUsd;
    }

    public void setTotalPriceUsd(BigDecimal totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
    }

    public BigDecimal getReceivablePriceUsd() {
        return receivablePriceUsd;
    }

    public void setReceivablePriceUsd(BigDecimal receivablePriceUsd) {
        this.receivablePriceUsd = receivablePriceUsd;
    }

    public String getPerLiableRepay() {
        return perLiableRepay;
    }

    public void setPerLiableRepay(String perLiableRepay) {
        this.perLiableRepay = perLiableRepay;
    }

    public String getCrmCodeOrName() {
        return crmCodeOrName;
    }

    public void setCrmCodeOrName(String crmCodeOrName) {
        this.crmCodeOrName = crmCodeOrName;
    }

    public String getAudiRemark() {
        return audiRemark;
    }

    public void setAudiRemark(String audiRemark) {
        this.audiRemark = audiRemark;
    }

    public String getAuditingReason() {
        return auditingReason;
    }

    public void setAuditingReason(String auditingReason) {
        this.auditingReason = auditingReason;
    }

    public String getAuditingType() {
        return auditingType;
    }

    public void setAuditingType(String auditingType) {
        this.auditingType = auditingType;
    }

    public Integer getCheckLogId() {
        return checkLogId;
    }

    public void setCheckLogId(Integer checkLogId) {
        this.checkLogId = checkLogId;
    }

    /**
     * 出口发货通知单状态枚举
     * 状态 1:未编辑 2：保存/草稿 3:已提交 4:完成
     */
    public static enum StatusEnum {
        READY(1, "未编辑"), BEING(2, "保存/草稿"), SUBMIT(3, "已提交"), DONE(4, "已完成");

        private int code;
        private String msg;

        private StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        /**
         * 通过code码获取采购状态信息
         *
         * @param code
         * @return
         */
        public static StatusEnum fromCode(Integer code) {
            if (code != null) {
                int code02 = code; // 拆箱一次
                for (StatusEnum s : StatusEnum.values()) {
                    if (code02 == s.code) {
                        return s;
                    }
                }
            }
            return null;
        }

    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }


}