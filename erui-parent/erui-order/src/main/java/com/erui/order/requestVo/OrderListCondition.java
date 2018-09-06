package com.erui.order.requestVo;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    //市场经办人id
    private Integer agentId;
    //市场经办人
    private String agentName;
    //订单签约日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signingDate;
    //订单签约开始日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;
    //订单签约结束日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
    //合同交货日期
    private String deliveryDate;
    //CRM客户代码
    private String crmCode;
    //框架协议号
    private String frameworkNo;
    //订单类型
    private Integer orderType;
    //款项状态
    private Integer payStatus;
    //订单状态
    private Integer status;
    //订单来源
    private String orderSource;
    //订单来源
    private Boolean deleteFlag;
    //区域国家
    //private String country;
    //判断权限
    private Integer type;
    //创建人id
    private Integer createUserId;
    //项目号
    private String projectNo;
    //流程进度
    private String processProgress;
    //是否已生成出口通知单
    private Integer deliverConsignHas;
    //商务技术经办人
    private Integer technicalId;
    //商务技术经办人
    private Integer technicalId02;
    // 语言 en / zh
    private String lang;
    //所属地区
    private String region;
    //应收账款余额
    private Integer receivableAccountRemaining;
    //执行事业部
    private String businessUnitId;
    //执行事业部
    private String businessUnitId02;
    private Integer perLiableRepayId; //回款责任人
    private String auditingUserId;   //当前审核人ID，逗号分隔多个
    private Integer auditingStatus; //审核状态

    public Integer getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(Integer auditingStatus) {
        this.auditingStatus = auditingStatus;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId;
    }

    // 分页信息参数
    private int page = 1; // 默认从0开始
    private int rows = 20; // 默认每页20条记录

    public Integer getPerLiableRepayId() {
        return perLiableRepayId;
    }

    public void setPerLiableRepayId(Integer perLiableRepayId) {
        this.perLiableRepayId = perLiableRepayId;
    }

    public Integer getTechnicalId02() {
        return technicalId02;
    }

    public void setTechnicalId02(Integer technicalId02) {
        this.technicalId02 = technicalId02;
    }

    public String getBusinessUnitId02() {
        return businessUnitId02;
    }

    public void setBusinessUnitId02(String businessUnitId02) {
        this.businessUnitId02 = businessUnitId02;
    }

    public String getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(String businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTechnicalId() {
        return technicalId;
    }

    public void setTechnicalId(Integer technicalId) {
        this.technicalId = technicalId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getDeliverConsignHas() {
        return deliverConsignHas;
    }

    public void setDeliverConsignHas(Integer deliverConsignHas) {
        this.deliverConsignHas = deliverConsignHas;
    }

    public String getProcessProgress() {
        return processProgress;
    }

    public void setProcessProgress(String processProgress) {
        this.processProgress = processProgress;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
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


    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCrmCode() {
        return crmCode;
    }

    public void setCrmCode(String crmCode) {
        this.crmCode = crmCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
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

    public String getFrameworkNo() {
        return frameworkNo;
    }

    public void setFrameworkNo(String frameworkNo) {
        this.frameworkNo = frameworkNo;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getReceivableAccountRemaining() {
        return receivableAccountRemaining;
    }

    public void setReceivableAccountRemaining(Integer receivableAccountRemaining) {
        this.receivableAccountRemaining = receivableAccountRemaining;
    }

    @Override
    public String toString() {
        return "OrderListCondition{" +
                "contractNo='" + contractNo + '\'' +
                ", poNo='" + poNo + '\'' +
                ", inquiryNo='" + inquiryNo + '\'' +
                ", agentId=" + agentId +
                ", agentName='" + agentName + '\'' +
                ", signingDate=" + signingDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", crmCode='" + crmCode + '\'' +
                ", frameworkNo='" + frameworkNo + '\'' +
                ", orderType=" + orderType +
                ", payStatus=" + payStatus +
                ", status=" + status +
                ", orderSource='" + orderSource + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", type=" + type +
                ", createUserId=" + createUserId +
                ", projectNo='" + projectNo + '\'' +
                ", processProgress='" + processProgress + '\'' +
                ", deliverConsignHas=" + deliverConsignHas +
                ", technicalId=" + technicalId +
                ", lang='" + lang + '\'' +
                ", region='" + region + '\'' +
                ", receivableAccountRemaining='" + receivableAccountRemaining + '\'' +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
