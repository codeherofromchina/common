package com.erui.order.model;

import com.erui.order.entity.Goods;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目执行统计
 * Created by wangxiaodan on 2018/4/10.
 */
public class ProjectStatistics {

    public ProjectStatistics() {
    }

    public ProjectStatistics(Project project, Order order) {
        this.orderId = order.getId();
        this.poNo = order.getPoNo();
        this.startDate = project.getStartDate();
        this.contractNo = order.getContractNo();
        this.inquiryNo = order.getInquiryNo();
        this.projectNo = project.getProjectNo();
        this.projectName = project.getProjectName();
        this.contractNoOs = order.getContractNoOs();
        this.logiQuoteNo = order.getLogiQuoteNo();
        this.execCoName = order.getExecCoName();
        this.distributionDeptName = order.getDistributionDeptName();
        this.businessUnitName = project.getBusinessUnitName();
        this.region = order.getRegion();
        this.country = order.getCountry();
        this.crmCode = order.getCrmCode();
        this.customerType = order.getCustomerType();
        this.orderType = order.getOrderType();
        this.totalPrice = project.getTotalPriceUsd();
        this.paymentModeBn = order.getPaymentModeBn();
        this.profitPercent = project.getProfitPercent();
        this.profit = project.getProfit();
        this.grantType = order.getGrantType();
        if (project.getDeliveryDate() != null) {
            // this.deliveryDate = DateUtil.format(DateUtil.SHORT_FORMAT_STR, project.getDeliveryDate());
            this.deliveryDate = project.getDeliveryDate();
        }
        this.requirePurchaseDate = project.getRequirePurchaseDate();
        this.exeChgDate = project.getExeChgDate();
        this.agentName = order.getAgentName();
        this.businessName = order.getBusinessName();
        this.tradeTerms = order.getTradeTerms();
        this.projectStatus = project.getProjectStatus();
        this.processProgress = project.getProcessProgress();
        this.overseasSales = order.getOverseasSales();
        this.currencyBn = order.getCurrencyBn();
        this.createTime = project.getCreateTime();
        this.orderCategory = order.getOrderCategory();
        this.goodsList = order.getGoodsList();
        this.sendDeptId = project.getSendDeptId();
    }

    //订单ID
    private Integer orderId;
    // 项目开始日期

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    //事业部ID
    private Integer sendDeptId;
    //所属地区
    private String region;
    private String regionZh;
    //国家
    private String country;
    //CRM客户代码
    private String crmCode;
    //客户类型
    private Integer customerType;
    //订单类型
    private Integer orderType;
    //海外销类型 TODO
    private Integer overseasSales;
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
    private BigDecimal profitPercent;
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
    private Integer orderCategory;
    //订单商品
    private List<Goods> goodsList = new ArrayList<>();

    /**
     * 项目执行统计模板导出：增加如下字段：总计增加27个字段
     */
    // 2、物流成本总计 IogisticsData物流表 物流费用
    private BigDecimal totalLogisticsCost;

    // 3、要求采购到货日期 Purch中的"合同约定到货日期"
    private Date arrivalDate;

    // 4、采购申请生成日期 PurchRequisition中的"创建时间createTime"
    private String purchRequisitionTime;

    // 5、采购合同号 Purch中的"采购合同号"
    private String purchNo;

    // 6、采购合同签订日期 Purch中的"合同签订日期"
    private String signingDate;

    // 7、采购要求交货时间 Purch中的"货物于__前运送" shippingDate或deliveryDate
    private Date shippingDate;

    // 8、供应商名称 Purch中的"供应商名称"
    private String supplierName;

    // 9、采购单价 PurchContractGoods中的"采购单价"
    private BigDecimal purchasePrice;

    // 10、采购总金额 Purch中的"合同总价"
    private BigDecimal PurchTotalPrice;

    // 11、采购实际到货日期 InspectReport入库质检的创建时间
    private Date arrivaledDate;

    // 12、采购经办人 Purch中的"采购经办人agentName"
    private String purchAgentName;

    // 13、报检日期 InspectReport入库质检的检验日期
    private Date checkDate;

    // 14、检验完成日期 InspectReport入库质检的检验完成日期
    private Date doneDate;

    // 15、检验人 InspectReport入库质检的检验人
    private String checkUserName;

    // 16、入库日期 Instock入库中的入库日期
    private Date instockDate;

    // 17、出库检验日期 DeliverDetail出库质检 checkDate
    private Date deliverDetailDate;

    // 18、出库日期 DeliverDetail出库质检出库日期
    private Date leaveDate;

    // 19、仓库经办人 DeliverDetail出库中的仓库经办人
    private String wareHousemanName;

    // 20、物流费用金额 IogisticsData物流表 物流费用
    private BigDecimal logisticsCost;

    // 21、物流经办人 IogisticsData物流表 物流经办人
    private String logisticsUserName;

    // 22、市场要求订舱日期 DeliverConsign 市场要求订舱日期
    private Date bookingDate;

    // 23、物流订舱日期 IogisticsData物流表 订舱时间
    private Date bookingTime;

    // 24、货物发运时间 IogisticsData物流表 实际离港时间
    private Date leavePortTime;

    // 25、货物到达时间 IogisticsData物流表 预计抵达时间
    private Date arrivalPortTime;

    // 26、客户接收时间 IogisticsData物流表 实际完成时间
    private Date accomplishDate;

    // 27、应收账款余额（美元） order表中的
    private String currencyBnReceivableAccountRemaining;

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

    public Integer getSendDeptId() {
        return sendDeptId;
    }

    public void setSendDeptId(Integer sendDeptId) {
        this.sendDeptId = sendDeptId;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
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

    public BigDecimal getTotalLogisticsCost() {
        return totalLogisticsCost;
    }

    public void setTotalLogisticsCost(BigDecimal totalLogisticsCost) {
        this.totalLogisticsCost = totalLogisticsCost;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getPurchRequisitionTime() {
        return purchRequisitionTime;
    }

    public void setPurchRequisitionTime(String purchRequisitionTime) {
        this.purchRequisitionTime = purchRequisitionTime;
    }

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo;
    }

    public String getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(String signingDate) {
        this.signingDate = signingDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchTotalPrice() {
        return PurchTotalPrice;
    }

    public void setPurchTotalPrice(BigDecimal purchTotalPrice) {
        PurchTotalPrice = purchTotalPrice;
    }

    public Date getArrivaledDate() {
        return arrivaledDate;
    }

    public void setArrivaledDate(Date arrivaledDate) {
        this.arrivaledDate = arrivaledDate;
    }

    public String getPurchAgentName() {
        return purchAgentName;
    }

    public void setPurchAgentName(String purchAgentName) {
        this.purchAgentName = purchAgentName;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public Date getInstockDate() {
        return instockDate;
    }

    public void setInstockDate(Date instockDate) {
        this.instockDate = instockDate;
    }

    public Date getDeliverDetailDate() {
        return deliverDetailDate;
    }

    public void setDeliverDetailDate(Date deliverDetailDate) {
        this.deliverDetailDate = deliverDetailDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getWareHousemanName() {
        return wareHousemanName;
    }

    public void setWareHousemanName(String wareHousemanName) {
        this.wareHousemanName = wareHousemanName;
    }

    public BigDecimal getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(BigDecimal logisticsCost) {
        this.logisticsCost = logisticsCost;
    }

    public String getLogisticsUserName() {
        return logisticsUserName;
    }

    public void setLogisticsUserName(String logisticsUserName) {
        this.logisticsUserName = logisticsUserName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Date getLeavePortTime() {
        return leavePortTime;
    }

    public void setLeavePortTime(Date leavePortTime) {
        this.leavePortTime = leavePortTime;
    }

    public Date getArrivalPortTime() {
        return arrivalPortTime;
    }

    public void setArrivalPortTime(Date arrivalPortTime) {
        this.arrivalPortTime = arrivalPortTime;
    }

    public Date getAccomplishDate() {
        return accomplishDate;
    }

    public void setAccomplishDate(Date accomplishDate) {
        this.accomplishDate = accomplishDate;
    }

    public String getCurrencyBnReceivableAccountRemaining() {
        return currencyBnReceivableAccountRemaining;
    }

    public void setCurrencyBnReceivableAccountRemaining(String currencyBnReceivableAccountRemaining) {
        this.currencyBnReceivableAccountRemaining = currencyBnReceivableAccountRemaining;
    }

    public String getProjectStatusName() {
        Project.ProjectStatusEnum projectStatusEnum = Project.ProjectStatusEnum.fromCode(projectStatus);
        if (projectStatusEnum != null) {
            return projectStatusEnum.getMsg();
        }
        return null;
    }

    public String getProcessProgress() {
        return processProgress;
    }

    public void setProcessProgress(String processProgress) {
        this.processProgress = processProgress;
    }

    public String getProcessProgressName() {
        Project.ProjectProgressEnum projectProgressEnum = Project.ProjectProgressEnum.ProjectProgressFromCode(processProgress);
        if (projectProgressEnum != null) {
            return projectProgressEnum.getMsg();
        }
        return null;
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

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getCustomerTypeName() {
        if (customerType != null) {
            return coverOilInt2Str(customerType);
        }
        return null;
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

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public String getProfitPercentStr() {
        BigDecimal profitPercent = getProfitPercent();
        if (profitPercent == null) {
            profitPercent = BigDecimal.ZERO;
        }
        profitPercent = profitPercent.setScale(2, BigDecimal.ROUND_HALF_UP);
        return profitPercent.toString() + "%";
    }

    public void setProfitPercent(BigDecimal profitPercent) {
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

    public Integer getOverseasSales() {
        return overseasSales;
    }

    public void setOverseasSales(Integer overseasSales) {
        this.overseasSales = overseasSales;
    }

    public String getOverseasSalesName() {
        if (overseasSales != null) {
            switch (overseasSales) {
                case 1:
                    return "海外销（装备采购）";
                case 2:
                    return "海外销（易瑞采购）";
                case 3:
                    return "海外销（当地采购）";
                case 4:
                    return "易瑞销";
                case 5:
                    return "装备销";
            }
        }
        return null;
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
        if (orderCategory != null) {
            switch (orderCategory) {
                case 1:
                    return "预投";
                case 2:
                    return "售后";
                case 3:
                    return "试用";
                case 4:
                    return "现货（出库）";
                case 5:
                    return "订单";
                case 6:
                    return "国内订单";
            }
        }
        return null;
    }

    public void setOrderCategory(Integer orderCategory) {
        this.orderCategory = orderCategory;
    }
}

