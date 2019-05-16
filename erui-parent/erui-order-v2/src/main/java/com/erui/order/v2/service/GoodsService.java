package com.erui.order.v2.service;

/**
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:16
 */
public interface GoodsService {


    /**
     * 判断订单商品是否已经全部出库
     * @param orderId
     * @return
     */
    boolean isAllOrderGoodsOutstock(Integer orderId);
}
