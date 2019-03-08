package com.erui.order.requestVo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by GS on 2017/12/14 0014.
 * 订单列表模糊查询条件
 */
public class DeliverConsignListCondition {
    //出口通知单号
    private String deliverConsignNo;

    //客户代码或名称
    private String crmCodeOrName;

    //销售同号
    private String contractNo;

    //审核进度
    private Integer auditingProcess;

    //审核状态
    private Integer auditingStatus;

    // 发货申请部门
    private String execCoName;

    //国家负责人Id
    private Integer countryLeaderId;

    //结算专员Id
    private Integer settlementLeaderId;

    //物流负责人Id
    private Integer logisticsLeaderId;

    //当前审核人ID，逗号分隔多个
    private String auditingUserId;

    //国家
    private String country;

    // 分页信息参数
    private int page = 1; // 默认从0开始
    private int rows = 20; // 默认每页20条记录
    // 语言 en / zh
    private String lang;


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

    public String getDeliverConsignNo() {
        return deliverConsignNo;
    }

    public void setDeliverConsignNo(String deliverConsignNo) {
        this.deliverConsignNo = deliverConsignNo;
    }

    public String getCrmCodeOrName() {
        return crmCodeOrName;
    }

    public void setCrmCodeOrName(String crmCodeOrName) {
        this.crmCodeOrName = crmCodeOrName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getAuditingProcess() {
        return auditingProcess;
    }

    public void setAuditingProcess(Integer auditingProcess) {
        this.auditingProcess = auditingProcess;
    }

    public Integer getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(Integer auditingStatus) {
        this.auditingStatus = auditingStatus;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
    }

    public Integer getCountryLeaderId() {
        return countryLeaderId;
    }

    public void setCountryLeaderId(Integer countryLeaderId) {
        this.countryLeaderId = countryLeaderId;
    }

    public Integer getSettlementLeaderId() {
        return settlementLeaderId;
    }

    public void setSettlementLeaderId(Integer settlementLeaderId) {
        this.settlementLeaderId = settlementLeaderId;
    }

    public Integer getLogisticsLeaderId() {
        return logisticsLeaderId;
    }

    public void setLogisticsLeaderId(Integer logisticsLeaderId) {
        this.logisticsLeaderId = logisticsLeaderId;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "DeliverConsignListCondition{" +
                "deliverConsignNo='" + deliverConsignNo + '\'' +
                ", crmCodeOrName='" + crmCodeOrName + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", auditingProcess=" + auditingProcess +
                ", auditingStatus=" + auditingStatus +
                ", execCoName='" + execCoName + '\'' +
                ", countryLeaderId=" + countryLeaderId +
                ", settlementLeaderId=" + settlementLeaderId +
                ", logisticsLeaderId=" + logisticsLeaderId +
                ", auditingUserId='" + auditingUserId + '\'' +
                ", country='" + country + '\'' +
                ", page=" + page +
                ", rows=" + rows +
                ", lang='" + lang + '\'' +
                '}';
    }
}
