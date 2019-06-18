package com.erui.order.v2.service;

import com.erui.order.v2.model.Goods;

import java.util.List;

/**
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:16
 */
public interface GoodsService {


    /**
     * 判断订单商品是否已经全部出库
     *
     * @param orderId
     * @return
     */
    boolean isAllOrderGoodsOutstock(Integer orderId);

    /**
     * 根据订单id获取商品
     *
     * @param orderId
     * @return
     */
    List<Goods> findGoodsByOrderId(Integer orderId);
}
