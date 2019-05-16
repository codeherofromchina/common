package com.erui.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 项目执行统计
 * Created by wangxiaodan on 2018/4/10.
 */
public class ProjectGoodsStatistics {

    public ProjectGoodsStatistics() {
    }

    //序号
    private Integer id;
    //未用易瑞签约原因
    //private String nonReson;
    //是否通过代理商获取
    private String agent;
    //代理商代码
    private String agentNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentNo() {
        return agentNo;
    }

    public void setAgentNo(String agentNo) {
        this.agentNo = agentNo;
    }

    //订单ID
    private Integer orderId;
    // 项目开始日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate; // 项目开始时间
    //销售合同号
    private String contractNo;
    //询单号
    private String inquiryNo;
    //项目号
    private String projectNo;
    //合同标的
    private String projectName;
    //海外销售合同号
    private String contractNoOs;
    //物流报价单号
    private String logiQuoteNo;
    //PO号
    private String poNo;
    //执行分公司
    private String execCoName;
    //分销部 / 分销部(获取人所在分类销售)
    private String distributionDeptName;
    //事业部
    private String businessUnitName;
    //所属地区
    private String region;
    private String regionZh;
    //国家
    private String country;
    //CRM客户代码
    private String crmCode;
    //客户类型
    private String customerType;
    //中文品名
    private String nameZh;
    //外文品名
    private String nameEn;
    //规格
    private String model;
    //合同数量
    private Integer contractGoodsNum;
    //单位
    private String unit;
    //订单类型
    private Integer orderType;
    //海外销类型 TODO
    private String overseasSales;
    //项目金额（美元）
    private BigDecimal totalPrice;
    // 前期报价（美元）  TODO
    // 前期物流报价（美元） TODO
    // 收款方式
    private String paymentModeBn;
    // 回款时间
    private Date paymentDate;   //回款时间
    // 回款金额（美元）
    private BigDecimal money;   //回款金额
    //回款记录条数
    private BigInteger accountCount; //回款记录
    //回款记录条数
    private String proCate; //产品分类
    //初步利润率
    private String profitPercent;
    //利润额
    private BigDecimal profit;
    //授信情况
    private String grantType;
    //执行单约定交付日期
    private String deliveryDate;
    //要求采购到货日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date requirePurchaseDate;
    //执行单变更后日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exeChgDate;
    //市场经办人
    private String agentName;
    //获取人 -- 由integer改为String,前端不用修改
    private String acquireId;
    //商务技术经办人
    private String businessName;
    //贸易术语
    private String tradeTerms;
    // 采购延期时间（天）  TODO 待完善
    // 物流延期时间（天）   TODO 待完善
    //项目状态
    private String projectStatus;
    //流程进度
    private String processProgress;
    // 原因类型  TODO
    // 原因描述  TODO

    // 金额类型 回款金额
    private String currencyBnMoney;   //金额类型  回款金额
    //货币类型
    private String currencyBn;
    //项目创建时间
    private Date createTime;
    //订单类别 1预投 2 售后回 3 试用 4 现货（出库） 5 订单
    private String orderCategory;
    //订单商品信息

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProCate() {
        return proCate;
    }

    public void setProCate(String proCate) {
        this.proCate = proCate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getInquiryNo() {
        return inquiryNo;
    }

    public void setInquiryNo(String inquiryNo) {
        this.inquiryNo = inquiryNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContractNoOs() {
        return contractNoOs;
    }

    public void setContractNoOs(String contractNoOs) {
        this.contractNoOs = contractNoOs;
    }

    public String getLogiQuoteNo() {
        return logiQuoteNo;
    }

    public void setLogiQuoteNo(String logiQuoteNo) {
        this.logiQuoteNo = logiQuoteNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
    }

    public String getDistributionDeptName() {
        return distributionDeptName;
    }

    public void setDistributionDeptName(String distributionDeptName) {
        this.distributionDeptName = distributionDeptName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAcquireId() {
        return acquireId;
    }

    public void setAcquireId(String acquireId) {
        this.acquireId = acquireId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }


    public String getProcessProgress() {
        return processProgress;
    }

    public void setProcessProgress(String processProgress) {
        this.processProgress = processProgress;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionZh() {
        return regionZh;
    }

    public void setRegionZh(String regionZh) {
        this.regionZh = regionZh;
    }

    public String getCrmCode() {
        return crmCode;
    }

    public void setCrmCode(String crmCode) {
        this.crmCode = crmCode;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeName() {
        if (orderType != null) {
            return coverOilInt2Str(orderType);
        }
        return null;
    }


    private String coverOilInt2Str(int code) {
        return code == 1 ? "油气" : (code == 2 ? "非油气" : null);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentModeBn() {
        return paymentModeBn;
    }

    public void setPaymentModeBn(String paymentModeBn) {
        this.paymentModeBn = paymentModeBn;
    }


    public String getPaymentModeBnName() {
        String result = null;
        if (StringUtils.isNotBlank(paymentModeBn)) {
            switch (paymentModeBn) {
                case "1":
                    result = "信用证";
                    break;
                case "2":
                    result = "托收";
                    break;
                case "3":
                    result = "电汇";
                    break;
                case "4":
                    result = "信汇";
                    break;
                case "5":
                    result = "票汇";
                    break;
                case "6":
                    result = "现金";
                    break;
            }
        }
        return result;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(String profitPercent) {
        this.profitPercent = profitPercent;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getGrantTypeName() {
        if (StringUtils.isNotBlank(grantType)) {
            switch (grantType) {
                case "1":
                    return "中信保";
                case "2":
                    return "集团授信";
                default:
                    return "其他";
            }
        }
        return null;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getRequirePurchaseDate() {
        return requirePurchaseDate;
    }

    public void setRequirePurchaseDate(Date requirePurchaseDate) {
        this.requirePurchaseDate = requirePurchaseDate;
    }

    public Date getExeChgDate() {
        return exeChgDate;
    }

    public void setExeChgDate(Date exeChgDate) {
        this.exeChgDate = exeChgDate;
    }

    public BigInteger getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(BigInteger accountCount) {
        this.accountCount = accountCount;
    }

    public String getOverseasSales() {
        return overseasSales;
    }

    public void setOverseasSales(String overseasSales) {
        this.overseasSales = overseasSales;
    }

    public String getCurrencyBnMoney() {
        return currencyBnMoney;
    }

    public void setCurrencyBnMoney(String currencyBnMoney) {
        this.currencyBnMoney = currencyBnMoney;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public String getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }
}

