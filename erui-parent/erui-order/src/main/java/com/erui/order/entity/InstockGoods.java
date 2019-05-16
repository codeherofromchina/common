package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * 入库-商品信息
 */
@Entity
@Table(name="instock_goods")
public class InstockGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 入库-记录
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="instock_id")
    private Instock instock;

    // 订单商品中的销售合同号
    @Column(name="contract_no")
    private String contractNo;
    // 项目商品中的项目号
    @Column(name="project_no")
    private String projectNo;

    /**
     * 质检单商品
     */
    @OneToOne
    @JoinColumn(name="inspect_apply_goods_id")
    @JsonIgnore
    private InspectApplyGoods inspectApplyGoods;

    @Column(name = "qualified_num")
    private Integer qualifiedNum;

    @Column(name = "instock_num")
    private Integer instockNum;

    @Column(name = "instock_stock")
    private String instockStock;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "update_time")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQualifiedNum() {
        return qualifiedNum;
    }

    public void setQualifiedNum(Integer qualifiedNum) {
        this.qualifiedNum = qualifiedNum;
    }

    public InspectApplyGoods getInspectApplyGoods() {
        return inspectApplyGoods;
    }

    public void setInspectApplyGoods(InspectApplyGoods inspectApplyGoods) {
        this.inspectApplyGoods = inspectApplyGoods;
    }

    public Instock getInstock() {
        return instock;
    }

    public void setInstock(Instock instock) {
        this.instock = instock;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Integer getInstockNum() {
        return instockNum;
    }

    public void setInstockNum(Integer instockNum) {
        this.instockNum = instockNum;
    }


    public String getInstockStock() {
        return instockStock;
    }

    public void setInstockStock(String instockStock) {
        this.instockStock = instockStock;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}