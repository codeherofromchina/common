package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purch_contract_signatories")
public class PurchContractSignatories {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 采购合同管理id
	 */
    @ManyToOne
    @JoinColumn(name = "purch_contract_id")
    @JsonIgnore
	private PurchContract purchContract;

	/**
	 * 类型 1:出卖人 2:买受人
	 */
	private Integer type;

	/**
	 * 出卖人/买受人（单位名称）
	 */
	@Column(name = "seller_buyer")
	private String sellerBuyer;

	/**
	 * 邮政编码
	 */
	@Column(name = "postal_code")
	private String postalCode;

	/**
	 * 法定代表人或授权代表
	 */
	@Column(name = "legal_representative")
	private String legalRepresentative;

	/**
	 * 委托代理人
	 */
	private String agent;

	/**
	 * 单位地址
	 */
	private String address;

	/**
	 * 开户银行
	 */
	@Column(name = "opening_bank")
	private String openingBank;

	/**
	 * 账号
	 */
	@Column(name = "account_number")
	private String accountNumber;

	/**
	 * 统一社会信用代码证
	 */
	@Column(name = "credit_code")
	private String creditCode;

	/**
	 * 电话/传真
	 */
	@Column(name = "telephone_fax")
	private String telephoneFax;

	/**
	 * 税号
	 */
	@Column(name = "duty_paragraph")
	private String dutyParagraph;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PurchContract getPurchContract() {
        return purchContract;
    }

    public void setPurchContract(PurchContract purchContract) {
        this.purchContract = purchContract;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSellerBuyer() {
        return sellerBuyer;
    }

    public void setSellerBuyer(String sellerBuyer) {
        this.sellerBuyer = sellerBuyer;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getTelephoneFax() {
        return telephoneFax;
    }

    public void setTelephoneFax(String telephoneFax) {
        this.telephoneFax = telephoneFax;
    }

    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
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
}
