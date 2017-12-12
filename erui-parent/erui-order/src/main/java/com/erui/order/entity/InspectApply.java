package com.erui.order.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 报检单
 */
@Entity
@Table(name="inspect_apply")
public class InspectApply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 采购
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="purch_id")
    private Purch purch;

    /**
     * 是否是主报检单 true：是 false：否
     */
    private boolean master = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="p_id")
    private InspectApply parent;

    private String department;
    @Column(name = "purchase_name")
    private String purchaseName;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "abroad_co_name")
    private String abroadCoName;
    @Column(name = "inspect_date")
    private Date inspectDate;

    private Boolean direct;
    @Column(name = "out_check")
    private String outCheck;

    private Integer num;

    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    private String remark;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "inspect_apply_attach",
            joinColumns = @JoinColumn(name = "inspect_apply_id"),
            inverseJoinColumns = @JoinColumn(name = "attach_id"))
    private Set<Attachment> attachmentList = new HashSet<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Purch getPurch() {
        return purch;
    }

    public void setPurch(Purch purch) {
        this.purch = purch;
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

    public String getOutCheck() {
        return outCheck;
    }

    public void setOutCheck(String outCheck) {
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


    public Set<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(Set<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}