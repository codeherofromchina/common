package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "project_profit")
public class ProjectProfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 项目ID
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;
    /**
     * 国家
     */
    private String country;

    /**
     * 贸易术语
     */
    @Column(name = "trade_term")
    private String tradeTerm;

    /**
     * 项目类型
     */
    @Column(name = "project_type")
    private String projectType;

    /**
     * 合同金额（美元）
     */
    @Column(name = "contract_amount_usd")
    private BigDecimal contractAmountUsd;

    /**
     * 合同金额（人民币）
     */
    @Column(name = "contract_amount")
    private BigDecimal contractAmount;

    /**
     * 汇率
     */
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    /**
     * 退税
     */
    @Column(name = "tax_refund")
    private BigDecimal taxRefund;

    /**
     * 陆运费
     */
    @Column(name = "land_freight")
    private BigDecimal landFreight;

    /**
     * 陆运险
     */
    @Column(name = "land_insurance")
    private BigDecimal landInsurance;

    /**
     * 港杂费
     */
    @Column(name = "port_charges")
    private BigDecimal portCharges;

    /**
     * 商检险
     */
    @Column(name = "inspection_fee")
    private BigDecimal inspectionFee;

    @Column(name = "international_transport")
    private BigDecimal internationalTransport;

    /**
     * 关税
     */
    private BigDecimal tariff;

    /**
     * 增值税
     */
    private BigDecimal vat;

    /**
     * 清关杂税
     */
    @Column(name = "customs_clear_fee")
    private BigDecimal customsClearFee;

    /**
     * 清关代理费
     */
    @Column(name = "customs_agent_fee")
    private BigDecimal customsAgentFee;

    /**
     * 货物运输保险
     */
    @Column(name = "transportion_insurance")
    private BigDecimal transportionInsurance;

    /**
     * 出口信用险
     */
    @Column(name = "export_credit_insurance")
    private BigDecimal exportCreditInsurance;

    /**
     * 其他信用
     */
    @Column(name = "other_credit")
    private String otherCredit;

    /**
     * 差旅费、业务费等
     */
    @Column(name = "travel_expenses")
    private BigDecimal travelExpenses;

    /**
     * 项目成本小计
     */
    @Column(name = "project_cost")
    private BigDecimal projectCost;

    /**
     * 毛利润
     */
    @Column(name = "gross_profit")
    private BigDecimal grossProfit;
    /**
     * 毛利率
     */
    @Column(name = "gross_profit_margin")
    private BigDecimal grossProfitMargin;

    /**
     * 项目佣金
     */
    @Column(name = "agent_fee")
    private BigDecimal agentFee;

    @Column(name = "raise_fee")
    private BigDecimal raiseFee;

    /**
     * 信用证，保函费
     */
    @Column(name = "guarance_fee")
    private BigDecimal guaranceFee;

    /**
     * 融资利息
     */
    @Column(name = "financing_interest")
    private BigDecimal financingInterest;

    /**
     * 银行手续费
     */
    @Column(name = "bank_fees")
    private BigDecimal bankFees;

    /**
     * 国内税费
     */
    @Column(name = "domestic_taxs")
    private BigDecimal domesticTaxs;

    /**
     * 国外税费
     */
    @Column(name = "foreign_taxes")
    private BigDecimal foreignTaxes;

    /**
     * 管理费用
     */
    @Column(name = "manage_fee")
    private BigDecimal manageFee;

    /**
     * 市场提点后报价利润
     */
    @Column(name = "after_profit")
    private BigDecimal afterProfit;

    /**
     * 市场提点前报价利润
     */
    @Column(name = "before_profit")
    private BigDecimal beforeProfit;

    /**
     * 报价利润率
     */
    @Column(name = "quotation_profit")
    private BigDecimal quotationProfit;
    /**
     * 采购成本-国内
     */
    @Column(name = "purchasing_costs_d")
    private BigDecimal purchasingCostsD;
    /**
     * 采购成本-国外
     */
    @Column(name = "purchasing_costs_f")
    private BigDecimal purchasingCostsF;
    /**
     * 直接人工
     */
    @Column(name = "direct_labor")
    private BigDecimal directLabor;

    /**
     * 制造费用
     */
    @Column(name = "manufacturing_costs")
    private BigDecimal manufacturingCosts;

    /**
     * 项目核算利润提点比例
     */
    @Column(name = "raise_rate")
    private BigDecimal raiseRate;

    /**
     * 项目成本总计
     */
    @Column(name = "total_project_cost")
    private BigDecimal totalProjectCost;

    /**
     * 后方提点费用
     */
    @Column(name = "rear_fee")
    private BigDecimal rearFee;

    /**
     * 平台代理费用
     */
    @Column(name = "platform_agent_cost")
    private BigDecimal platformAgentCost;

    public BigDecimal getRearFee() {
        return rearFee;
    }

    public void setRearFee(BigDecimal rearFee) {
        this.rearFee = rearFee;
    }

    public BigDecimal getTotalProjectCost() {
        return totalProjectCost;
    }

    public void setTotalProjectCost(BigDecimal totalProjectCost) {
        this.totalProjectCost = totalProjectCost;
    }

    public BigDecimal getRaiseRate() {
        return raiseRate;
    }

    public void setRaiseRate(BigDecimal raiseRate) {
        this.raiseRate = raiseRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public BigDecimal getGrossProfitMargin() {
        return grossProfitMargin;
    }

    public void setGrossProfitMargin(BigDecimal grossProfitMargin) {
        this.grossProfitMargin = grossProfitMargin;
    }

    public BigDecimal getPurchasingCostsD() {
        return purchasingCostsD;
    }

    public void setPurchasingCostsD(BigDecimal purchasingCostsD) {
        this.purchasingCostsD = purchasingCostsD;
    }

    public BigDecimal getPurchasingCostsF() {
        return purchasingCostsF;
    }

    public void setPurchasingCostsF(BigDecimal purchasingCostsF) {
        this.purchasingCostsF = purchasingCostsF;
    }

    public BigDecimal getDirectLabor() {
        return directLabor;
    }

    public void setDirectLabor(BigDecimal directLabor) {
        this.directLabor = directLabor;
    }

    public BigDecimal getManufacturingCosts() {
        return manufacturingCosts;
    }

    public void setManufacturingCosts(BigDecimal manufacturingCosts) {
        this.manufacturingCosts = manufacturingCosts;
    }

    /**
     * 获取国家
     *
     * @return country - 国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置国家
     *
     * @param country 国家
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * 获取贸易术语
     *
     * @return trade_term - 贸易术语
     */
    public String getTradeTerm() {
        return tradeTerm;
    }

    /**
     * 设置贸易术语
     *
     * @param tradeTerm 贸易术语
     */
    public void setTradeTerm(String tradeTerm) {
        this.tradeTerm = tradeTerm == null ? null : tradeTerm.trim();
    }

    /**
     * 获取项目类型
     *
     * @return project_type - 项目类型
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * 设置项目类型
     *
     * @param projectType 项目类型
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    /**
     * 获取合同金额（美元）
     *
     * @return contract_amount_usd - 合同金额（美元）
     */
    public BigDecimal getContractAmountUsd() {
        return contractAmountUsd;
    }

    /**
     * 设置合同金额（美元）
     *
     * @param contractAmountUsd 合同金额（美元）
     */
    public void setContractAmountUsd(BigDecimal contractAmountUsd) {
        this.contractAmountUsd = contractAmountUsd;
    }

    /**
     * 获取合同金额（人民币）
     *
     * @return contract_amount - 合同金额（人民币）
     */
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    /**
     * 设置合同金额（人民币）
     *
     * @param contractAmount 合同金额（人民币）
     */
    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    /**
     * 获取汇率
     *
     * @return exchange_rate - 汇率
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * 设置汇率
     *
     * @param exchangeRate 汇率
     */
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * 获取退税
     *
     * @return tax_refund - 退税
     */
    public BigDecimal getTaxRefund() {
        return taxRefund;
    }

    /**
     * 设置退税
     *
     * @param taxRefund 退税
     */
    public void setTaxRefund(BigDecimal taxRefund) {
        this.taxRefund = taxRefund;
    }

    /**
     * 获取陆运费
     *
     * @return land_freight - 陆运费
     */
    public BigDecimal getLandFreight() {
        return landFreight;
    }

    /**
     * 设置陆运费
     *
     * @param landFreight 陆运费
     */
    public void setLandFreight(BigDecimal landFreight) {
        this.landFreight = landFreight;
    }

    /**
     * 获取陆运险
     *
     * @return land_insurance - 陆运险
     */
    public BigDecimal getLandInsurance() {
        return landInsurance;
    }

    /**
     * 设置陆运险
     *
     * @param landInsurance 陆运险
     */
    public void setLandInsurance(BigDecimal landInsurance) {
        this.landInsurance = landInsurance;
    }

    /**
     * 获取港杂费
     *
     * @return port_charges - 港杂费
     */
    public BigDecimal getPortCharges() {
        return portCharges;
    }

    /**
     * 设置港杂费
     *
     * @param portCharges 港杂费
     */
    public void setPortCharges(BigDecimal portCharges) {
        this.portCharges = portCharges;
    }

    /**
     * 获取商检险
     *
     * @return inspection_fee - 商检险
     */
    public BigDecimal getInspectionFee() {
        return inspectionFee;
    }

    /**
     * 设置商检险
     *
     * @param inspectionFee 商检险
     */
    public void setInspectionFee(BigDecimal inspectionFee) {
        this.inspectionFee = inspectionFee;
    }

    /**
     * @return international_transport
     */
    public BigDecimal getInternationalTransport() {
        return internationalTransport;
    }

    /**
     * @param internationalTransport
     */
    public void setInternationalTransport(BigDecimal internationalTransport) {
        this.internationalTransport = internationalTransport;
    }

    /**
     * 获取关税
     *
     * @return tariff - 关税
     */
    public BigDecimal getTariff() {
        return tariff;
    }

    /**
     * 设置关税
     *
     * @param tariff 关税
     */
    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
    }

    /**
     * 获取增值税
     *
     * @return vat - 增值税
     */
    public BigDecimal getVat() {
        return vat;
    }

    /**
     * 设置增值税
     *
     * @param vat 增值税
     */
    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    /**
     * 获取清关杂税
     *
     * @return customs_clear_fee - 清关杂税
     */
    public BigDecimal getCustomsClearFee() {
        return customsClearFee;
    }

    /**
     * 设置清关杂税
     *
     * @param customsClearFee 清关杂税
     */
    public void setCustomsClearFee(BigDecimal customsClearFee) {
        this.customsClearFee = customsClearFee;
    }

    /**
     * 获取清关代理费
     *
     * @return customs_agent_fee - 清关代理费
     */
    public BigDecimal getCustomsAgentFee() {
        return customsAgentFee;
    }

    /**
     * 设置清关代理费
     *
     * @param customsAgentFee 清关代理费
     */
    public void setCustomsAgentFee(BigDecimal customsAgentFee) {
        this.customsAgentFee = customsAgentFee;
    }

    /**
     * 获取货物运输保险
     *
     * @return transportion_insurance - 货物运输保险
     */
    public BigDecimal getTransportionInsurance() {
        return transportionInsurance;
    }

    /**
     * 设置货物运输保险
     *
     * @param transportionInsurance 货物运输保险
     */
    public void setTransportionInsurance(BigDecimal transportionInsurance) {
        this.transportionInsurance = transportionInsurance;
    }

    /**
     * 获取出口信用险
     *
     * @return export_credit_insurance - 出口信用险
     */
    public BigDecimal getExportCreditInsurance() {
        return exportCreditInsurance;
    }

    /**
     * 设置出口信用险
     *
     * @param exportCreditInsurance 出口信用险
     */
    public void setExportCreditInsurance(BigDecimal exportCreditInsurance) {
        this.exportCreditInsurance = exportCreditInsurance;
    }

    /**
     * 获取其他信用
     *
     * @return other_credit - 其他信用
     */
    public String getOtherCredit() {
        return otherCredit;
    }

    /**
     * 设置其他信用
     *
     * @param otherCredit 其他信用
     */
    public void setOtherCredit(String otherCredit) {
        this.otherCredit = otherCredit == null ? null : otherCredit.trim();
    }

    /**
     * 获取差旅费、业务费等
     *
     * @return travel_expenses - 差旅费、业务费等
     */
    public BigDecimal getTravelExpenses() {
        return travelExpenses;
    }

    /**
     * 设置差旅费、业务费等
     *
     * @param travelExpenses 差旅费、业务费等
     */
    public void setTravelExpenses(BigDecimal travelExpenses) {
        this.travelExpenses = travelExpenses;
    }

    /**
     * 获取项目成本小计
     *
     * @return project_cost - 项目成本小计
     */
    public BigDecimal getProjectCost() {
        return projectCost;
    }

    /**
     * 设置项目成本小计
     *
     * @param projectCost 项目成本小计
     */
    public void setProjectCost(BigDecimal projectCost) {
        this.projectCost = projectCost;
    }

    /**
     * 获取毛利率
     *
     * @return gross_profit - 毛利率
     */
    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    /**
     * 设置毛利率
     *
     * @param grossProfit 毛利率
     */
    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    /**
     * @return agent_fee
     */
    public BigDecimal getAgentFee() {
        return agentFee;
    }

    /**
     * @param agentFee
     */
    public void setAgentFee(BigDecimal agentFee) {
        this.agentFee = agentFee;
    }

    /**
     * @return raise_fee
     */
    public BigDecimal getRaiseFee() {
        return raiseFee;
    }

    /**
     * @param raiseFee
     */
    public void setRaiseFee(BigDecimal raiseFee) {
        this.raiseFee = raiseFee;
    }

    /**
     * 获取信用证，保函费
     *
     * @return guarance_fee - 信用证，保函费
     */
    public BigDecimal getGuaranceFee() {
        return guaranceFee;
    }

    /**
     * 设置信用证，保函费
     *
     * @param guaranceFee 信用证，保函费
     */
    public void setGuaranceFee(BigDecimal guaranceFee) {
        this.guaranceFee = guaranceFee;
    }

    /**
     * 获取融资利息
     *
     * @return financing_interest - 融资利息
     */
    public BigDecimal getFinancingInterest() {
        return financingInterest;
    }

    /**
     * 设置融资利息
     *
     * @param financingInterest 融资利息
     */
    public void setFinancingInterest(BigDecimal financingInterest) {
        this.financingInterest = financingInterest;
    }

    /**
     * 获取银行手续费
     *
     * @return bank_fees - 银行手续费
     */
    public BigDecimal getBankFees() {
        return bankFees;
    }

    /**
     * 设置银行手续费
     *
     * @param bankFees 银行手续费
     */
    public void setBankFees(BigDecimal bankFees) {
        this.bankFees = bankFees;
    }

    /**
     * 获取国内税费
     *
     * @return domestic_taxs - 国内税费
     */
    public BigDecimal getDomesticTaxs() {
        return domesticTaxs;
    }

    /**
     * 设置国内税费
     *
     * @param domesticTaxs 国内税费
     */
    public void setDomesticTaxs(BigDecimal domesticTaxs) {
        this.domesticTaxs = domesticTaxs;
    }

    /**
     * 获取国外税费
     *
     * @return foreign_taxes - 国外税费
     */
    public BigDecimal getForeignTaxes() {
        return foreignTaxes;
    }

    /**
     * 设置国外税费
     *
     * @param foreignTaxes 国外税费
     */
    public void setForeignTaxes(BigDecimal foreignTaxes) {
        this.foreignTaxes = foreignTaxes;
    }

    /**
     * 获取管理费用
     *
     * @return manage_fee - 管理费用
     */
    public BigDecimal getManageFee() {
        return manageFee;
    }

    /**
     * 设置管理费用
     *
     * @param manageFee 管理费用
     */
    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    /**
     * 获取市场提点后报价利润
     *
     * @return after_profit - 市场提点后报价利润
     */
    public BigDecimal getAfterProfit() {
        return afterProfit;
    }

    /**
     * 设置市场提点后报价利润
     *
     * @param afterProfit 市场提点后报价利润
     */
    public void setAfterProfit(BigDecimal afterProfit) {
        this.afterProfit = afterProfit;
    }

    /**
     * 获取市场提点前报价利润
     *
     * @return before_profit - 市场提点前报价利润
     */
    public BigDecimal getBeforeProfit() {
        return beforeProfit;
    }

    /**
     * 设置市场提点前报价利润
     *
     * @param beforeProfit 市场提点前报价利润
     */
    public void setBeforeProfit(BigDecimal beforeProfit) {
        this.beforeProfit = beforeProfit;
    }

    /**
     * 获取报价利润率
     *
     * @return quotation_profit - 报价利润率
     */
    public BigDecimal getQuotationProfit() {
        return quotationProfit;
    }

    /**
     * 设置报价利润率
     *
     * @param quotationProfit 报价利润率
     */
    public void setQuotationProfit(BigDecimal quotationProfit) {
        this.quotationProfit = quotationProfit;
    }

    public BigDecimal getPlatformAgentCost() {
        return platformAgentCost;
    }

    public void setPlatformAgentCost(BigDecimal platformAgentCost) {
        this.platformAgentCost = platformAgentCost;
    }
}