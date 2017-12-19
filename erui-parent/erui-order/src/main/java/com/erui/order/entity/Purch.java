package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 采购信息
 */
@Entity
@Table(name = "purch")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Purch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purch_no")
    private String purchNo;

    @Column(name = "agent_id")
    private Integer agentId;

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "signing_date")
    private Date signingDate;

    @Column(name = "arrival_date")
    private Date arrivalDate;

    @Column(name = "pur_chg_date")
    private Date purChgDate;

    @Column(name = "exe_chg_date")
    private Date exeChgDate;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "currency_bn")
    private String currencyBn;

    @Column(name = "pay_type")
    private Integer payType;

    @Column(name = "other_pay_type_msg")
    private String otherPayTypeMsg;

    @Column(name = "producted_date")
    private Date productedDate;

    @Column(name = "pay_factory_date")
    private Date payFactoryDate;

    @Column(name = "pay_deposit_date")
    private Date payDepositDate;

    @Column(name = "pay_deposit_expired")
    private Date payDepositExpired;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "account_date")
    private Date accountDate;

    /**
     * “未进行”：当新增采购订单时，点击“保存”按钮，状态为“未进行”。点击“提交”按钮，状态由“未执行”变为“进行中”。
     * “已完成”：当报检单中所有商品全部检验合格后，状态由“进行中”，变为“已完成”。
     */
    @Column(name = "`status`")
    private Integer status;

    @Column(name = "create_time")
    @JsonIgnore
    private Date createTime;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "create_user_name")
    private String createUserName;

    @Column(name = "update_time")
    @JsonIgnore
    private Date updateTime;

    @Column(name = "delete_flag")
    @JsonIgnore
    private Boolean deleteFlag;

    @Column(name = "delete_time")
    @JsonIgnore
    private Date deleteTime;


    private String remarks;

    //销售合同号，多个
    @Transient
    private String contractNos;
    //项目号，多个
    @Transient
    private String projectNos;
    // 分页信息参数
    @Transient
    private int page = 0; // 默认从0开始
    @Transient
    private int rows = 20; // 默认每页20条记录


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "purch_project",
            joinColumns = @JoinColumn(name = "purch_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @JsonIgnore
    private List<Project> projects = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "purch_attach",
            joinColumns = @JoinColumn(name = "purch_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "purch_id")
    private List<PurchPayment> purchPaymentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "purch_id")
    @OrderBy("id asc")
    private List<PurchGoods> purchGoodsList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getContractNos() {
        return contractNos;
    }

    public void setContractNos(String contractNos) {
        this.contractNos = contractNos;
    }

    public Date getPurChgDate() {
        return purChgDate;
    }

    public void setPurChgDate(Date purChgDate) {
        this.purChgDate = purChgDate;
    }

    public Date getExeChgDate() {
        return exeChgDate;
    }

    public void setExeChgDate(Date exeChgDate) {
        this.exeChgDate = exeChgDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }


    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOtherPayTypeMsg() {
        return otherPayTypeMsg;
    }

    public void setOtherPayTypeMsg(String otherPayTypeMsg) {
        this.otherPayTypeMsg = otherPayTypeMsg;
    }

    public Date getProductedDate() {
        return productedDate;
    }

    public void setProductedDate(Date productedDate) {
        this.productedDate = productedDate;
    }

    public Date getPayFactoryDate() {
        return payFactoryDate;
    }

    public void setPayFactoryDate(Date payFactoryDate) {
        this.payFactoryDate = payFactoryDate;
    }

    public Date getPayDepositDate() {
        return payDepositDate;
    }

    public void setPayDepositDate(Date payDepositDate) {
        this.payDepositDate = payDepositDate;
    }

    public Date getPayDepositExpired() {
        return payDepositExpired;
    }

    public void setPayDepositExpired(Date payDepositExpired) {
        this.payDepositExpired = payDepositExpired;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
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

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProjectNos() {
        return projectNos;
    }

    public void setProjectNos(String projectNos) {
        this.projectNos = projectNos;
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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<PurchPayment> getPurchPaymentList() {
        return purchPaymentList;
    }

    public void setPurchPaymentList(List<PurchPayment> purchPaymentList) {
        this.purchPaymentList = purchPaymentList;
    }

    public List<PurchGoods> getPurchGoodsList() {
        return purchGoodsList;
    }

    public void setPurchGoodsList(List<PurchGoods> purchGoodsList) {
        this.purchGoodsList = purchGoodsList;
    }


    /**
     * 采购状态枚举
     */
    public static enum StatusEnum {
        READY(0, "未进行"), BEING(1, "进行中"), DONE(2, "已完成");

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


    /**
     * 给供应商付款方式枚举
     */
    public static enum PayTypeEnum {
        COD(1, "货到验收合格后付款"), PAY_FIRST(2, "款到发货"), OTHER(9, "其他方式");

        private int code;
        private String msg;

        private PayTypeEnum(int code, String msg) {
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
         * 通过code码获取给供应商付款方式
         *
         * @param code
         * @return
         */
        public static PayTypeEnum fromCode(Integer code) {
            if (code != null) {
                int code02 = code; // 拆箱一次
                for (PayTypeEnum s : PayTypeEnum.values()) {
                    if (code02 == s.code) {
                        return s;
                    }
                }
            }
            return null;
        }

    }


    public void setBaseInfo(Purch purch) {
        this.setPurchNo(purch.getPurchNo());
        this.setAgentId(purch.getAgentId());
        this.setAgentName(purch.getAgentName());
        this.setSigningDate(purch.getSigningDate());
        this.setArrivalDate(purch.getArrivalDate());
        this.setPurChgDate(purch.getPurChgDate());
        this.setExeChgDate(purch.getExeChgDate());
        this.setSupplierId(purch.getSupplierId());
        this.setSupplierName(purch.getSupplierName());
        this.setTotalPrice(purch.getTotalPrice());
        this.setCurrencyBn(purch.getCurrencyBn());
        this.setPayType(purch.getPayType());
        this.setOtherPayTypeMsg(purch.getOtherPayTypeMsg());
        this.setProductedDate(purch.getProductedDate());
        this.setPayFactoryDate(purch.getPayFactoryDate());
        this.setPayDepositDate(purch.getPayDepositDate());
        this.setPayDepositExpired(purch.getPayDepositExpired());
        this.setInvoiceNo(purch.getInvoiceNo());
        this.setAccountDate(purch.getAccountDate());
        this.setRemarks(purch.getRemarks());
        if (this.createUserId == null) {
            this.setCreateUserId(purch.getCreateUserId());
            this.setCreateUserName(purch.getCreateUserName());
        }
        if (this.createTime == null) {
            this.setCreateTime(new Date());
            this.setUpdateTime(new Date());
        }
        this.setStatus(purch.getStatus());

    }


}