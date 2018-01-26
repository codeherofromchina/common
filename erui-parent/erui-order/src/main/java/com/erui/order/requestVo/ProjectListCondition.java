package com.erui.order.requestVo;

import java.math.BigDecimal;
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
    private Date startDate;
    //分销部
    private String distributionDeptName;
    //事业部
    private String businessUnitName;
    //所属区域
    private String region;
    //执行单约定交付日期
    private Date deliveryDate;
    //要求采购到货日期
    private Date requirePurchaseDate;
    //执行单变更后日期
    private Date exeChgDate;
    //项目状态
    private String projectStatus;
    //国家查询
    private String country;
    // 分页信息参数
    private int page = 0; // 默认从0开始
    private int rows = 20; // 默认每页20条记录

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
