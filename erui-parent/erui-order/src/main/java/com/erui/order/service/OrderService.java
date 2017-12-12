package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.Order;

/**
 * 订单业务类
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderService {
    /**
     * 根据id查询订单信息
     * @param id
     * @return
     */
    Order findById(Integer id);
}
