package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purch_contract_standard")
public class PurchContractStandard {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 采购合同管理id
	 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purch_contract_id")
    @JsonIgnore
    private PurchContract purchContract;

	/**
	 * 基础信息、用于买受人__，且出卖人
	 */
	@Column(name = "used_for_buyer")
	private String usedForBuyer;

    /**
     * 第二条、质量标准及要求__
     */
    @Column(name = "standard_and_require")
    private String standardAndRequire;

	/**
	 * 第四条、产品包装标准、包装费用与包装物回收__
	 */
	@Column(name = "meet_require")
	private String meetRequire;

	/**
	 * 第五条、质保与售后__
	 */
	@Column(name = "warranty_period")
	private String warrantyPeriod;

	/**
	 * 第六条、出卖人负责于__（前）
	 */
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@Column(name = "delivery_date")
	private Date deliveryDate;

	/**
	 * 第六条、交货地点:__。
	 */
	@Column(name = "delivery_place")
	private String deliveryPlace;

	/**
	 * 第七条、检验期为__天(月)
	 */
	@Column(name = "inspection_period")
	private String inspectionPeriod;

	/**
	 * 第七条、技术资料包括__版本
	 */
	@Column(name = "data_version")
	private String dataVersion;

	/**
	 * 第八条、结算方式及时间__
	 */
	@Column(name = "method_and_time")
	private String methodAndTime;

	/**
	 * 第十二条、收信地址为__
	 */
	@Column(name = "inside_address_s")
	private String insideAddressS;

	/**
	 * 第十二条、收信人为__
	 */
	@Column(name = "addressee_s")
	private String addresseeS;

	/**
	 * 第十二条、电话为__
	 */
	@Column(name = "telephone_s")
	private String telephoneS;

	/**
	 * 第十二条、电子邮件收件邮箱地址为__
	 */
	@Column(name = "inbox_address_s")
	private String inboxAddressS;

	/**
	 * 第十二条、收信地址为__
	 */
	@Column(name = "inside_address_b")
	private String insideAddressB;

	/**
	 * 第十二条、收信人为__
	 */
	@Column(name = "addressee_b")
	private String addresseeB;

	/**
	 * 第十二条、电话为__
	 */
	@Column(name = "telephone_b")
	private String telephoneB;

	/**
	 * 第十二条、电子邮件收件邮箱地址为__
	 */
	@Column(name = "inbox_address_b")
	private String inboxAddressB;

	/**
	 * 第十五条、按下列第__种方式解决
	 */
	private String solution;

	/**
	 * 第十五条、提交__仲裁委员会仲裁
	 */
	@Column(name = "board_arbitration")
	private String boardArbitration;

	/**
	 * 第十六条、本合同一式__份
	 */
	@Column(name = "few_copies")
	private String fewCopies;

	/**
	 * 第十六条、出卖人执__份
	 */
	@Column(name = "seller_few_copies")
	private String sellerFewCopies;

	/**
	 * 第十六条、买受人执__份
	 */
	@Column(name = "buyer_few_copies")
	private String buyerFewCopies;

	/**
	 * 第十六条、合同附件：1.__
	 */
	@Column(name = "appendices_name1")
	private String appendicesName1;

	/**
	 * 第十六条、合同附件：2.__
	 */
	@Column(name = "appendices_name2")
	private String appendicesName2;

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

    public String getStandardAndRequire() {
        return standardAndRequire;
    }

    public void setStandardAndRequire(String standardAndRequire) {
        this.standardAndRequire = standardAndRequire;
    }

    public String getMeetRequire() {
        return meetRequire;
    }

    public void setMeetRequire(String meetRequire) {
        this.meetRequire = meetRequire;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String getInspectionPeriod() {
        return inspectionPeriod;
    }

    public void setInspectionPeriod(String inspectionPeriod) {
        this.inspectionPeriod = inspectionPeriod;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getMethodAndTime() {
        return methodAndTime;
    }

    public void setMethodAndTime(String methodAndTime) {
        this.methodAndTime = methodAndTime;
    }

    public String getInsideAddressS() {
        return insideAddressS;
    }

    public void setInsideAddressS(String insideAddressS) {
        this.insideAddressS = insideAddressS;
    }

    public String getAddresseeS() {
        return addresseeS;
    }

    public void setAddresseeS(String addresseeS) {
        this.addresseeS = addresseeS;
    }

    public String getTelephoneS() {
        return telephoneS;
    }

    public void setTelephoneS(String telephoneS) {
        this.telephoneS = telephoneS;
    }

    public String getInboxAddressS() {
        return inboxAddressS;
    }

    public void setInboxAddressS(String inboxAddressS) {
        this.inboxAddressS = inboxAddressS;
    }

    public String getInsideAddressB() {
        return insideAddressB;
    }

    public void setInsideAddressB(String insideAddressB) {
        this.insideAddressB = insideAddressB;
    }

    public String getAddresseeB() {
        return addresseeB;
    }

    public void setAddresseeB(String addresseeB) {
        this.addresseeB = addresseeB;
    }

    public String getTelephoneB() {
        return telephoneB;
    }

    public void setTelephoneB(String telephoneB) {
        this.telephoneB = telephoneB;
    }

    public String getInboxAddressB() {
        return inboxAddressB;
    }

    public void setInboxAddressB(String inboxAddressB) {
        this.inboxAddressB = inboxAddressB;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getBoardArbitration() {
        return boardArbitration;
    }

    public void setBoardArbitration(String boardArbitration) {
        this.boardArbitration = boardArbitration;
    }

    public String getFewCopies() {
        return fewCopies;
    }

    public void setFewCopies(String fewCopies) {
        this.fewCopies = fewCopies;
    }

    public String getSellerFewCopies() {
        return sellerFewCopies;
    }

    public void setSellerFewCopies(String sellerFewCopies) {
        this.sellerFewCopies = sellerFewCopies;
    }

    public String getBuyerFewCopies() {
        return buyerFewCopies;
    }

    public void setBuyerFewCopies(String buyerFewCopies) {
        this.buyerFewCopies = buyerFewCopies;
    }

    public String getAppendicesName1() {
        return appendicesName1;
    }

    public void setAppendicesName1(String appendicesName1) {
        this.appendicesName1 = appendicesName1;
    }

    public String getAppendicesName2() {
        return appendicesName2;
    }

    public void setAppendicesName2(String appendicesName2) {
        this.appendicesName2 = appendicesName2;
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

    public String getUsedForBuyer() {
        return usedForBuyer;
    }

    public void setUsedForBuyer(String usedForBuyer) {
        this.usedForBuyer = usedForBuyer;
    }
}
