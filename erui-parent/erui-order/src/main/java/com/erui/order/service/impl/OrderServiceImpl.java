package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.Order;
import com.erui.order.service.AreaService;
import com.erui.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order findById(Integer id) {
        return orderDao.findOne(id);
    }
}
