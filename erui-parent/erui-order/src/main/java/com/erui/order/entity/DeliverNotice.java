package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * 看货通知单
 */
@Entity
@Table(name = "deliver_notice")
public class DeliverNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 物流-出库单详情
     */
    @OneToOne(mappedBy = "deliverNotice", fetch = FetchType.LAZY)
    @JsonIgnore
    private DeliverDetail deliverDetail;

    /**
     * 货通知单号 自动生成
     */
    @Column(name = "deliver_notice_no")
    private String deliverNoticeNo;

    /**
     * 出口发货通知单
     */
    @OneToOne
    @JoinColumn(name = "deliver_consign_id")
    @JsonIgnore
    private DeliverConsign deliverConsign;

    /**
     * 出口发货通知单ID
     */
    @Transient
    private Integer deliverConsignId;

    /**
     * 出口通知单号
     */
    @Column(name = "deliver_consign_no")
    private String deliverConsignNo;

    /**
     * 出口通知单商品
     */
    @Transient
    private List<DeliverConsignGoods> deliverConsignGoodsSet = new ArrayList<>();

    /**
     * 下单人
     */
    @Column(name = "sender_id")
    private Integer senderId;

    /**
     * 下单人名称
     */
    @Column(name = "sender_name")
    private String senderName;

    /**
     * 项目号
     */
    @Column(name = "project_no")
    private String projectNo;

    /**
     * 销售合同号
     */
    @Column(name = "contract_no")
    private String contractNo;

    /**
     * 单证操作(操作专员ID)
     */
    @Column(name = "operation_specialist_id")
    private Integer operationSpecialistId;

    /**
     * 单证操作(操作专员)
     */
    @Column(name = "operation_specialist")
    private String operationSpecialist;

    /**
     * 下单日期(取点击“提交”看货通知单按钮日期)
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "send_date")
    private Date sendDate;

    /**
     * 货名(业务项目简述及中英货物名称)
     */
    @Column(name = "business_sketch")
    private String businessSketch;

    /**
     * 货物存放地
     */
    @Column(name = "goods_deposit_place")
    private String goodsDepositPlace;

    /**
     * 贸易术语/目的国/目的地
     */
    @Column(name = "trade_terms")
    private String tradeTerms;

    /**
     * 产品件数(计算：订舱通知中所有商品的“本次发运数量”之和)
     */
    private Integer numbers;

    /**
     * 事业部项目负责人(订单中商务技术经办人)
     */
    @Column(name = "technical_uid")
    private Integer technicalUid;

    /**
     * 运输方式
     */
    @Column(name = "transport_type")
    private String transportType;

    /**
     * 项目要求交付日期（去订舱中的要求运抵目的地日期）
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "arrival_date")
    private Date arrivalDate;

    /**
     * 订单紧急程度 1:一般（成本优先） 2:紧急 3:异常紧急（交期优先）
     */
    @Column(name = "order_emergency")
    private Integer orderEmergency;

    /**
     * 其他要求
     */
    @Column(name = "other_req")
    private String otherReq;

    /**
     * 包装要求
     */
    @Column(name = "package_req")
    private String packageReq;

    /**
     * 备货要求
     */
    @Column(name = "prepare_req")
    private String prepareReq;

    /**
     * 客户代码或名称
     */
    @Column(name = "crm_code_or_name")
    private String crmCodeOrName;

    /**
     * 发货申请部门名称
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * 看货通知单状态 1:保存看货通知 2：提交看货通知 3:保存箱单 4:提交箱单/已完成
     */
    private Integer status;

    /**
     * 看货状态 1:未处理  2：已处理
     */
    @Column(name = "handle_status")
    private Integer handleStatus;

    /**
     * 仓库出具发运箱单日期(取点击“提交”上传箱单按钮日期)
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "packing_time")
    private Date packingTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 租户，默认“erui”
     */
    @JsonIgnore
    private String tenant;

    @Transient
    private int page = 1;
    @Transient
    private int rows = 10;

    @Transient
    private Set<Attachment> attachmentSet = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DeliverDetail getDeliverDetail() {
        return deliverDetail;
    }

    public void setDeliverDetail(DeliverDetail deliverDetail) {
        this.deliverDetail = deliverDetail;
    }

    public String getDeliverNoticeNo() {
        return deliverNoticeNo;
    }

    public void setDeliverNoticeNo(String deliverNoticeNo) {
        this.deliverNoticeNo = deliverNoticeNo;
    }

    public DeliverConsign getDeliverConsign() {
        return deliverConsign;
    }

    public void setDeliverConsign(DeliverConsign deliverConsign) {
        this.deliverConsign = deliverConsign;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public Integer getOperationSpecialistId() {
        return operationSpecialistId;
    }

    public void setOperationSpecialistId(Integer operationSpecialistId) {
        this.operationSpecialistId = operationSpecialistId;
    }

    public String getOperationSpecialist() {
        return operationSpecialist;
    }

    public void setOperationSpecialist(String operationSpecialist) {
        this.operationSpecialist = operationSpecialist;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getBusinessSketch() {
        return businessSketch;
    }

    public void setBusinessSketch(String businessSketch) {
        this.businessSketch = businessSketch;
    }

    public String getGoodsDepositPlace() {
        return goodsDepositPlace;
    }

    public void setGoodsDepositPlace(String goodsDepositPlace) {
        this.goodsDepositPlace = goodsDepositPlace;
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }

    public Integer getTechnicalUid() {
        return technicalUid;
    }

    public void setTechnicalUid(Integer technicalUid) {
        this.technicalUid = technicalUid;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getOrderEmergency() {
        return orderEmergency;
    }

    public void setOrderEmergency(Integer orderEmergency) {
        this.orderEmergency = orderEmergency;
    }

    public String getOtherReq() {
        return otherReq;
    }

    public void setOtherReq(String otherReq) {
        this.otherReq = otherReq;
    }

    public String getPackageReq() {
        return packageReq;
    }

    public void setPackageReq(String packageReq) {
        this.packageReq = packageReq;
    }

    public String getPrepareReq() {
        return prepareReq;
    }

    public void setPrepareReq(String prepareReq) {
        this.prepareReq = prepareReq;
    }

    public String getCrmCodeOrName() {
        return crmCodeOrName;
    }

    public void setCrmCodeOrName(String crmCodeOrName) {
        this.crmCodeOrName = crmCodeOrName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Set<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(Set<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }

    public Integer getDeliverConsignId() {
        return deliverConsignId;
    }

    public void setDeliverConsignId(Integer deliverConsignId) {
        this.deliverConsignId = deliverConsignId;
    }

    public String getDeliverConsignNo() {
        return deliverConsignNo;
    }

    public void setDeliverConsignNo(String deliverConsignNo) {
        this.deliverConsignNo = deliverConsignNo;
    }

    /**
     * 看货通知单状态枚举
     * 状态 1:保存看货通知 2：提交看货通知 3:保存箱单 4:提交箱单/已完成
     */
    public static enum StatusEnum {
        SAVE(1, "保存看货通知"), SUBMIT(2, "提交看货通知"), BEING(3, "保存箱单"), DONE(4, "提交箱单/已完成"),
        UNTREATED(1, "未处理"), PROCESSED(2, "已处理");

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
        public static DeliverNotice.StatusEnum fromCode(Integer code) {
            if (code != null) {
                int code02 = code; // 拆箱一次
                for (DeliverNotice.StatusEnum s : DeliverNotice.StatusEnum.values()) {
                    if (code02 == s.code) {
                        return s;
                    }
                }
            }
            return null;
        }

    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public List<DeliverConsignGoods> getDeliverConsignGoodsSet() {
        return deliverConsignGoodsSet;
    }

    public void setDeliverConsignGoodsSet(List<DeliverConsignGoods> deliverConsignGoodsSet) {
        this.deliverConsignGoodsSet = deliverConsignGoodsSet;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public Date getPackingTime() {
        return packingTime;
    }

    public void setPackingTime(Date packingTime) {
        this.packingTime = packingTime;
    }
}
