package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.OrderAccount;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderAccountService {
    /**
     * 根据id查询订单收款信息
     * @param id
     * @return
     */
    OrderAccount findById(Integer id);
}
