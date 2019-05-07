package com.erui.order.v2.service;

import com.erui.order.v2.model.DeliverDetail;
import com.erui.order.v2.model.DeliverDetailWithBLOBs;

import java.util.List;

/**
 * @Auther 王晓丹
 * @Date 2019/5/6 下午7:33
 */
public interface DeliverdetailService {
    /**
     * 查询最新的产品放行单号
     * @return
     */
    String findNewestDeliverDetailNo();

    /**
     * 插入出库信息
     * @param deliverDetail
     */
    void insert(DeliverDetailWithBLOBs deliverDetail);

    /**
     * 插入出库详情和出库商品的关联关系
     * @param deliverDetailId
     * @param deliverConsignGoodsIds
     */
    void insertDeliverGoodsRelation(Integer deliverDetailId, List<Integer> deliverConsignGoodsIds);
}
