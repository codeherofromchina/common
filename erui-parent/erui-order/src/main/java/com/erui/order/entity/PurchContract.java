package com.erui.order.entity;

import com.erui.comm.NewDateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purch_contract")
public class PurchContract {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 采购合同号
	 */
	@Column(name = "purch_contract_no")
	private String purchContractNo;

    /**
     * 项目ID
     */
    @Transient
    private String projectId;

	/**
	 * 合同类型 1:简易合同 2:标准合同 3:非标合同
	 */
	private Integer type;
    /**
     * 简易合同
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "purchContract")
    private PurchContractSimple purchContractSimple;

    /**
     * 标准合同
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "purchContract")
    private PurchContractStandard purchContractStandard;

    /**
     * 采购合同买卖双方信息
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "purch_contract_id")
    @OrderBy("id asc")
    private List<PurchContractSignatories> purchContractSignatoriesList = new ArrayList<>();

    /**
     * 附件信息
     */
    @Transient
    private List<Attachment> attachments = new ArrayList<>();

    /**
     * 采购合同商品信息
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "purch_contract_id")
    @OrderBy("id asc")
    private List<PurchContractGoods> purchContractGoodsList = new ArrayList<>();

	/**
	 * 状态 1:待确认 2:未执行 3:已执行 4:已完成 5:已删除
	 */
	private Integer status;

	/**
	 * 合同签订日期
	 */
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@Column(name = "signing_date")
	private Date signingDate;

	/**
	 * 供货商ID
	 */
	@Column(name = "supplier_id")
	private Integer supplierId;

	/**
	 * 供货商名称
	 */
	@Column(name = "supplier_name")
	private String supplierName;

	/**
	 * 合同签订地点
	 */
	@Column(name = "signing_place")
	private String signingPlace;

	/**
	 * 采购经办人
	 */
	@Column(name = "agent_id")
	private Integer agentId;

	/**
	 * 采购经办人名称
	 */
	@Column(name = "agent_name")
	private String agentName;

	/**
	 * 税点%,0到100数字
	 */
	@Column(name = "tax_point")
	private BigDecimal taxPoint;

	/**
	 * 采购总金额（大写）
	 */
	@Column(name = "capitalized_price")
	private String capitalizedPrice;

	/**
	 * 采购总金额（小写）
	 */
	@Column(name = "lowercase_price")
	private BigDecimal lowercasePrice;

	/**
	 * 商品备注
	 */
	@Column(name = "goods_remarks")
	private String goodsRemarks;

	/**
	 * 货币类型（币种）
	 */
	@Column(name = "currency_bn")
	private String currencyBn;

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 所属地区
	 */
	private String region;

	/**
	 * 合同版本,如201904
	 */
	private Integer version;

	/**
	 * 创建人id
	 */
	@Column(name = "create_user_id")
	private Integer createUserId;

	/**
	 * 创建人
	 */
	@Column(name = "create_user_name")
	private String createUserName;

	/**
	 * 修改时间
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

    // 分页信息参数
    @Transient
    private int page = 1; // 默认从1开始
    @Transient
    private int rows = 20; // 默认每页20条记录

    /**
     * 采购合同状态枚举
     */
    public static enum StatusEnum {
        READY(1, "未进行/保存"), BEING(2, "进行中/提交"), EXECUTED(3, "已执行"), DONE(4, "已完成"), DELETE(5, "已删除");

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
        public static PurchContract.StatusEnum fromCode(Integer code) {
            if (code != null) {
                int code02 = code; // 拆箱一次
                for (PurchContract.StatusEnum s : PurchContract.StatusEnum.values()) {
                    if (code02 == s.code) {
                        return s;
                    }
                }
            }
            return null;
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchContractNo() {
        return purchContractNo;
    }

    public void setPurchContractNo(String purchContractNo) {
        this.purchContractNo = purchContractNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public PurchContractSimple getPurchContractSimple() {
        return purchContractSimple;
    }

    public void setPurchContractSimple(PurchContractSimple purchContractSimple) {
        this.purchContractSimple = purchContractSimple;
    }

    public PurchContractStandard getPurchContractStandard() {
        return purchContractStandard;
    }

    public void setPurchContractStandard(PurchContractStandard purchContractStandard) {
        this.purchContractStandard = purchContractStandard;
    }

    public List<PurchContractSignatories> getPurchContractSignatoriesList() {
        return purchContractSignatoriesList;
    }

    public void setPurchContractSignatoriesList(List<PurchContractSignatories> purchContractSignatoriesList) {
        this.purchContractSignatoriesList = purchContractSignatoriesList;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<PurchContractGoods> getPurchContractGoodsList() {
        return purchContractGoodsList;
    }

    public void setPurchContractGoodsList(List<PurchContractGoods> purchContractGoodsList) {
        this.purchContractGoodsList = purchContractGoodsList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
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

    public String getSigningPlace() {
        return signingPlace;
    }

    public void setSigningPlace(String signingPlace) {
        this.signingPlace = signingPlace;
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

    public BigDecimal getTaxPoint() {
        return taxPoint;
    }

    public void setTaxPoint(BigDecimal taxPoint) {
        this.taxPoint = taxPoint;
    }

    public String getCapitalizedPrice() {
        return capitalizedPrice;
    }

    public void setCapitalizedPrice(String capitalizedPrice) {
        this.capitalizedPrice = capitalizedPrice;
    }

    public BigDecimal getLowercasePrice() {
        return lowercasePrice;
    }

    public void setLowercasePrice(BigDecimal lowercasePrice) {
        this.lowercasePrice = lowercasePrice;
    }

    public String getGoodsRemarks() {
        return goodsRemarks;
    }

    public void setGoodsRemarks(String goodsRemarks) {
        this.goodsRemarks = goodsRemarks;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setBaseInfo(PurchContract purchContract) {
        this.setStatus(purchContract.getStatus()); // 状态
        this.setSigningDate(NewDateUtil.getDate(purchContract.getSigningDate())); //采购合同签订日期
        this.setSigningPlace(purchContract.getSigningPlace()); //采购合同签订地点
        this.setSupplierId(purchContract.getSupplierId());
        this.setSupplierName(purchContract.getSupplierName());
        this.setAgentId(purchContract.getAgentId()); // 采购经办人ID
        this.setAgentName(purchContract.getAgentName()); // 采购经办人名称
        this.setTaxPoint(purchContract.getTaxPoint()); // 税点%,0到100数字
        this.setCapitalizedPrice(purchContract.getCapitalizedPrice()); // 采购总金额（大写）
        this.setLowercasePrice(purchContract.getLowercasePrice()); // 采购总金额（小写）
        this.setGoodsRemarks(purchContract.getGoodsRemarks()); // 商品备注
        this.setCurrencyBn(purchContract.getCurrencyBn()); // 货币类型（币种）
        this.setCountry(purchContract.getCountry()); // 国家
        this.setRegion(purchContract.getRegion()); // 所属地区
        this.setVersion(purchContract.getVersion()); // 合同版本
    }
}
