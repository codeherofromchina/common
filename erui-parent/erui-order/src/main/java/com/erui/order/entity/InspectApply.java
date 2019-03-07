package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报检单
 */
@Entity
@Table(name = "inspect_apply")
public class InspectApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 报检单号，自动生成
    @Column(name = "inspect_apply_no")
    private String inspectApplyNo;

    /**
     * 采购
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purch_id")
    @JsonIgnore
    private Purch purch;

    /**
     * 参数使用,不同情况可以使用为采购单ID，或父报检单ID
     */
    @Transient
    private Integer pId;
    @Transient
    private Integer createUserId;
    @Transient
    private String createUserName;

    // 采购合同号
    @Column(name = "purch_no")
    private String purchNo;


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "inspect_apply_id")
    private List<InspectApplyGoods> inspectApplyGoodsList = new ArrayList<>();

    /**
     * 是否是主报检单 true：是 false：否
     */
    @Column(name = "`master`")
    private Boolean master = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private InspectApply parent;
    // 下发部门
    private String department;
    // 采购经办人
    @Column(name = "purchase_name")
    private String purchaseName;
    // 供应商名称
    @Column(name = "supplier_name")
    private String supplierName;
    // 国外分公司
    @Column(name = "abroad_co_name")
    private String abroadCoName;
    // 报检日期
    @Column(name = "inspect_date")
    private Date inspectDate;

    private Boolean direct= false;
    @Column(name = "out_check")
    private Boolean outCheck= false;

    // 报检次数
    private Integer num;

    //质检申请单状态 0：未编辑 1：保存/草稿  2：已提交 3：合格 4:未合格
    private Integer status;

    @Column(name="pub_status")
    private Integer pubStatus;

    // 整改意见
    private String msg;
    // 下一条的整改意见，临时存储
    @Column(name = "tmp_msg")
    private String tmpMsg;

    // 是否存在历史记录
    private Boolean history = false;

    @Column(name = "create_time")
    @JsonIgnore
    private Date createTime;

    private String remark;


    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "inspect_apply_attach",
            joinColumns = @JoinColumn(name = "inspect_apply_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))*/
    //报检单
    @Transient
    private List<Attachment> attachmentList = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInspectApplyNo() {
        return inspectApplyNo;
    }

    public void setInspectApplyNo(String inspectApplyNo) {
        this.inspectApplyNo = inspectApplyNo;
    }

    public Purch getPurch() {
        return purch;
    }

    public void setPurch(Purch purch) {
        this.purch = purch;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getPurchNo() {
        return purchNo;
    }

    public void setPurchNo(String purchNo) {
        this.purchNo = purchNo;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public InspectApply getParent() {
        return parent;
    }

    public void setParent(InspectApply parent) {
        this.parent = parent;
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

    public Boolean getDirect() {
        return direct;
    }

    public void setDirect(Boolean direct) {
        this.direct = direct;
    }

    public Boolean getOutCheck() {
        return outCheck;
    }

    public void setOutCheck(Boolean outCheck) {
        this.outCheck = outCheck;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(Integer pubStatus) {
        this.pubStatus = pubStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTmpMsg() {
        return tmpMsg;
    }

    public void setTmpMsg(String tmpMsg) {
        this.tmpMsg = tmpMsg;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public boolean isHistory() {
        return history;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public List<InspectApplyGoods> getInspectApplyGoodsList() {
        return inspectApplyGoodsList;
    }

    public void setInspectApplyGoodsList(List<InspectApplyGoods> inspectApplyGoodsList) {
        this.inspectApplyGoodsList = inspectApplyGoodsList;
    }

    public static enum StatusEnum {
        NO_EDIT(0, "未编辑"), SAVED(1, "保存"), SUBMITED(2, "已提交"), QUALIFIED(3, "已合格"), UNQUALIFIED(4, "未合格");
        private int code;
        private String msg;

        StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }


        public static StatusEnum fromCode(Integer code) {
            if (code != null) {
                int v = code.intValue();
                for (StatusEnum se : StatusEnum.values()) {
                    if (se.getCode() == v) {
                        return se;
                    }
                }
            }
            return null;
        }
    }

}