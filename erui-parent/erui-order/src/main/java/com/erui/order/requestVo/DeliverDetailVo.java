package com.erui.order.requestVo;

import com.erui.order.entity.DeliverDetail;

/**
 * Created by wangxiaodan on 2017/12/16.
 */
public class DeliverDetailVo {
    private Integer id;

    // 操作（列表、保存等操作）类型  1：出库列表  2：出库质检列表 3：物流跟踪列表
    private Integer opType ;

    /**
     * 出库质检列表中 1：保存  2：已提交
     */
    private Integer status ; // 状态

    private int page;
    private int pageSize;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    // TODO 待实现并完善
    public void copyFrom(DeliverDetail deliverDetail) {
        this.id = deliverDetail.getId();
    }
}
