package com.erui.order.service;

import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;

/**
 * Created by wangxiaodan on 2017/12/8.
 */
public interface OrderService {

    OrderLog findById(Long id);
}
