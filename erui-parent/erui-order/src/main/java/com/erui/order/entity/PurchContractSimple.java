package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purch_contract_simple")
public class PurchContractSimple {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 采购合同管理
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purch_contract_id")
    @JsonIgnore
    private PurchContract purchContract;

	/**
	 * 1、货物皆为符合__的合格产品
	 */
	@Column(name = "product_requirement")
	private String productRequirement;

	/**
	 * 1、质保期自__ 。
	 */
	@Column(name = "warranty_period")
	private String warrantyPeriod;

	/**
	 * 3、将货物于__前运送至
	 */
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@Column(name = "shipping_date")
	private Date shippingDate;

	/**
	 * 3、指定的地点：__。
	 */
	@Column(name = "designated_location")
	private String designatedLocation;

	/**
	 * 4、费用负担：__运
	 */
	@Column(name = "cost_burden")
	private String costBurden;

	/**
	 * 5、合同第1条在__处检验
	 */
	@Column(name = "inspection_at")
	private String inspectionAt;

	/**
	 * 5、并在__日内提出异议
	 */
	@Column(name = "within_days")
	private String withinDays;

	/**
	 * 6、结算方式及时间：__。
	 */
	@Column(name = "method_and_time")
	private String methodAndTime;

	/**
	 * 9、附《__技术协议》
	 */
	@Column(name = "agreement_name")
	private String agreementName;

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

    public String getProductRequirement() {
        return productRequirement;
    }

    public void setProductRequirement(String productRequirement) {
        this.productRequirement = productRequirement;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getDesignatedLocation() {
        return designatedLocation;
    }

    public void setDesignatedLocation(String designatedLocation) {
        this.designatedLocation = designatedLocation;
    }

    public String getCostBurden() {
        return costBurden;
    }

    public void setCostBurden(String costBurden) {
        this.costBurden = costBurden;
    }

    public String getInspectionAt() {
        return inspectionAt;
    }

    public void setInspectionAt(String inspectionAt) {
        this.inspectionAt = inspectionAt;
    }

    public String getWithinDays() {
        return withinDays;
    }

    public void setWithinDays(String withinDays) {
        this.withinDays = withinDays;
    }

    public String getMethodAndTime() {
        return methodAndTime;
    }

    public void setMethodAndTime(String methodAndTime) {
        this.methodAndTime = methodAndTime;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
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
