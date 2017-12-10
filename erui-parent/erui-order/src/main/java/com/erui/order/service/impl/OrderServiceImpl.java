package com.erui.order.service.impl;

import com.erui.order.dao.OrderDao;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import com.erui.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangxiaodan on 2017/12/8.
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Resource(name="orderDao")
    private OrderDao orderDao;

    @Override
    public OrderLog findById(Long id) {
        return orderDao.findById(id);
    }
}
