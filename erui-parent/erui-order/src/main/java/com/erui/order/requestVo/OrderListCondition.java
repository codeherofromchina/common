package com.erui.order.requestVo;

import java.util.Date;

/**
 * Created by GS on 2017/12/14 0014.
 * 订单列表模糊查询条件
 */
public class OrderListCondition {
    //销售合同号
    private String contractNo;
    //po号
    private String poNo;
    //询单号
    private String inquiryNo;
    //市场经办人
    private String agentName;
    //订单签约日期
    private Date signingDate;
    //合同交货日期
    private Date deliveryDate;
    //CRM客户代码
    private String crmCode;
    //订单类型
    private Boolean orderType;
    //款项状态
    private Integer payStatus;
    //订单状态
    private Integer status;
    //订单来源
    private String orderSource;
    //订单来源
    private Boolean deleteFlag;
    // 分页信息参数
    private int page = 0; // 默认从0开始
    private int rows = 20; // 默认每页20条记录

    public Boolean getOrderType() {
        return orderType;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getInquiryNo() {
        return inquiryNo;
    }

    public void setInquiryNo(String inquiryNo) {
        this.inquiryNo = inquiryNo;
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCrmCode() {
        return crmCode;
    }

    public void setCrmCode(String crmCode) {
        this.crmCode = crmCode;
    }

    public Boolean isOrderType() {
        return orderType;
    }

    public void setOrderType(Boolean orderType) {
        this.orderType = orderType;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
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

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String toString() {
        return "OrderListCondition{" +
                "contractNo='" + contractNo + '\'' +
                ", poNo='" + poNo + '\'' +
                ", inquiryNo='" + inquiryNo + '\'' +
                ", agentName='" + agentName + '\'' +
                ", signingDate=" + signingDate +
                ", deliveryDate=" + deliveryDate +
                ", crmCode='" + crmCode + '\'' +
                ", orderType=" + orderType +
                ", payStatus=" + payStatus +
                ", status=" + status +
                ", orderSource='" + orderSource + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
