package com.erui.order.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 入库-商品信息
 */
@Entity
@Table(name="instock_goods")
public class InstockGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 入库-记录
     */
    @ManyToOne
    @JoinColumn(name="instock_id")
    private Instock instock;

    /**
     * 质检单商品
     */
    @OneToOne
    @JoinColumn(name="inspect_apply_goods_id")
    private InspectApplyGoods inspectApplyGoods;

    @Column(name = "instock_num")
    private Integer instockNum;

    @Column(name = "instock_stock")
    private String instockStock;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private String createUserId;

    @Column(name = "update_time")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}