package com.erui.order.requestVo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by GS on 2017/12/19 0019.
 */
public class ProjectListCondition {
    private Integer id;
    //销售合同号
    private String contractNo;
    //项目名称
    private String projectName;
    //执行分公司ID
    private String execCoId;
    //执行分公司名称
    private String execCoName;
    //项目开始时期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    //分销部
    private String distributionDeptName;
    //事业部
    private String businessUnitName;
    //所属区域
    private String region;
    //执行单约定交付日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date deliveryDate;
    //要求采购到货日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date requirePurchaseDate;
    //执行单变更后日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exeChgDate;
    //项目状态
    private String projectStatus;
    //国家查询
    private String country;
    //项目号
    private String projectNo;
    //流程进度
    private String processProgress;
    //是否已生成出口通知单
    private Integer deliverConsignHas;
    //采购经办人
    private Integer purchaseUid;
    //品控经办人
    private Integer qualityUid;
    //
    private Integer managerUid;
    //物流经办人
    private Integer logisticsUid;
    //仓库经办人
    private Integer warehouseUid;
    //商务技术
    private Integer businessUid;
    //项目创建开始日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;
    //项目创建结束日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
    //下发部门
    private String sendDeptId;
    // 分页信息参数
    private int page = 0; // 默认从0开始
    private int rows = 20; // 默认每页20条记录

    public String getSendDeptId() {
        return sendDeptId;
    }

    public void setSendDeptId(String sendDeptId) {
        this.sendDeptId = sendDeptId;
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

    public Integer getBusinessUid() {
        return businessUid;
    }

    public void setBusinessUid(Integer businessUid) {
        this.businessUid = businessUid;
    }

    public Integer getPurchaseUid() {
        return purchaseUid;
    }

    public void setPurchaseUid(Integer purchaseUid) {
        this.purchaseUid = purchaseUid;
    }

    public Integer getQualityUid() {
        return qualityUid;
    }

    public void setQualityUid(Integer qualityUid) {
        this.qualityUid = qualityUid;
    }

    public Integer getManagerUid() {
        return managerUid;
    }

    public void setManagerUid(Integer managerUid) {
        this.managerUid = managerUid;
    }

    public Integer getLogisticsUid() {
        return logisticsUid;
    }

    public void setLogisticsUid(Integer logisticsUid) {
        this.logisticsUid = logisticsUid;
    }

    public Integer getWarehouseUid() {
        return warehouseUid;
    }

    public void setWarehouseUid(Integer warehouseUid) {
        this.warehouseUid = warehouseUid;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getExecCoId() {
        return execCoId;
    }

    public void setExecCoId(String execCoId) {
        this.execCoId = execCoId;
    }

    public String getExecCoName() {
        return execCoName;
    }

    public void setExecCoName(String execCoName) {
        this.execCoName = execCoName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDistributionDeptName() {
        return distributionDeptName;
    }

    public void setDistributionDeptName(String distributionDeptName) {
        this.distributionDeptName = distributionDeptName;
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
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

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
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
}
