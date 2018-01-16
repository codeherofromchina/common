package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.*;

/**
 * 采购申请单
 */
@Entity
@Table(name = "purch_requisition")
public class PurchRequisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;
    /**
     * 项目
     */
    @OneToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    @Transient
    private Integer proId;
    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "pm_uid")
    private Integer pmUid;

    private Integer department;

    @Column(name = "submit_date")
    private Date submitDate;

    @Column(name = "trade_method")
    private String tradeMethod;

    @Column(name = "trans_mode_bn")
    private String transModeBn;

    @Column(name = "factory_send")
    private boolean factorySend = false;

    @Column(name = "delivery_place")
    private String deliveryPlace;

    private String requirements;

    private Integer status;

    @Column(name = "create_time")
    @JsonIgnore
    private Date createTime;

    private String remarks;
    //
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "purch_requisition_attach",
            joinColumns = @JoinColumn(name = "purch_requisition_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    private Set<Attachment> attachmentSet = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "purch_requisition_goods",
            joinColumns = @JoinColumn(name = "purch_requisition_id"),
            inverseJoinColumns = @JoinColumn(name = "goods_id"))
    private List<Goods> goodsList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getPmUid() {
        return pmUid;
    }

    public void setPmUid(Integer pmUid) {
        this.pmUid = pmUid;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getTradeMethod() {
        return tradeMethod;
    }

    public void setTradeMethod(String tradeMethod) {
        this.tradeMethod = tradeMethod;
    }

    public String getTransModeBn() {
        return transModeBn;
    }

    public void setTransModeBn(String transModeBn) {
        this.transModeBn = transModeBn;
    }

    public boolean isFactorySend() {
        return factorySend;
    }

    public void setFactorySend(boolean factorySend) {
        this.factorySend = factorySend;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Set<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(Set<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }


    public static enum StatusEnum {
        SAVED(1, "保存"), SUBMITED(2, "提交");

        public int code;
        public String msg;

        StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}