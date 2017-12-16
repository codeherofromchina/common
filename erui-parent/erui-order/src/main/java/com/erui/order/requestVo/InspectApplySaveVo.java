package com.erui.order.requestVo;

import com.erui.order.entity.Attachment;
import com.erui.order.entity.Purch;

import java.util.*;

/**
 * 新增报检单的参数实体
 */
public class InspectApplySaveVo {

    private Integer id;
    private Integer purchId; // 采购单ID
    private String department; // 下发部门
    private String purchaseName; // 采购经办人
    private String supplierName; // 供应商名称
    private String abroadCoName; // 国外分公司
    private Date inspectDate; // 报检时间
    private boolean direct = false; // 是否厂家直接发货
    private boolean outCheck = true; // 是否外检
    private String remark; // 备注
    private String msg; // 整改意见
    private int status = 1; // 1:保存 2：提交 3：重新报检

    // 商品列表信息
    /*{
        "id":"1",
        "goodsId":"1",
        "inspectNum":"10", ## 报检数量
    }*/
    private List<PGoods> goodsList = new ArrayList<>();
    // 附件列表信息
    /*{
        "group": "部门名称",
        "title": "附件名称",
        "userName": "创建人",
        "userId": "创建人id",
        "frontDate": "日期"
    }*/
    private List<Attachment> attachmentList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPurchId() {
        return purchId;
    }

    public void setPurchId(Integer purchId) {
        this.purchId = purchId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAbroadCoName() {
        return abroadCoName;
    }

    public void setAbroadCoName(String abroadCoName) {
        this.abroadCoName = abroadCoName;
    }

    public Date getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
    }

    public boolean isDirect() {
        return direct;
    }

    public boolean isOutCheck() {
        return outCheck;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public void setOutCheck(boolean outCheck) {
        this.outCheck = outCheck;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<PGoods> goodsList) {
        this.goodsList = goodsList;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}