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
    private String auditingProcess;

    //审核状态 1未审核 2审核中 3已完成
    private Integer auditingStatus;

    //发货申请部门
    private String deptId;

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

    public String getAuditingProcess() {
        return auditingProcess;
    }

    public void setAuditingProcess(String auditingProcess) {
        this.auditingProcess = auditingProcess;
    }

    public Integer getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(Integer auditingStatus) {
        this.auditingStatus = auditingStatus;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
