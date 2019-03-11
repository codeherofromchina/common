package com.erui.order.requestVo;

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

    //审核进度 1提交审核 2国家负责人审核 3结算专员审核 4物流负责人审核 5事业部项目负责人审核
    private Integer auditingProcess;

    //审核状态 1未审核 2审核中 3已完成
    private Integer auditingStatus;

    //款项状态 1未收款 2部分收款 3收款完成
    private Integer payStatus;

    //流程进度 1未执行 2执行中 3已采购 4已报检 5已入库质检 6已入库 7已出库质检 8已出库 9已发运 10已完成
    private Integer processProgress;

    //国家
    private String country;

    // 分页信息参数
    private int page = 1; // 默认从0开始
    private int rows = 20; // 默认每页20条记录
    // 语言 en / zh
    private String lang;

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

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getProcessProgress() {
        return processProgress;
    }

    public void setProcessProgress(Integer processProgress) {
        this.processProgress = processProgress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "DeliverConsignListCondition{" +
                "deliverConsignNo='" + deliverConsignNo + '\'' +
                ", crmCodeOrName='" + crmCodeOrName + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", auditingProcess=" + auditingProcess +
                ", auditingStatus=" + auditingStatus +
                ", payStatus=" + payStatus +
                ", processProgress=" + processProgress +
                ", country='" + country + '\'' +
                ", page=" + page +
                ", rows=" + rows +
                ", lang='" + lang + '\'' +
                '}';
    }
}
