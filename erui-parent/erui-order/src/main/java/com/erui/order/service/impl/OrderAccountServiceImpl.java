package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.OrderAccountDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.OrderAccount;
import com.erui.order.service.AreaService;
import com.erui.order.service.OrderAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class OrderAccountServiceImpl implements OrderAccountService {

    @Autowired
    private OrderAccountDao orderAccountDao;

    @Override
    public OrderAccount findById(Integer id) {
        return orderAccountDao.findOne(id);
    }
}
