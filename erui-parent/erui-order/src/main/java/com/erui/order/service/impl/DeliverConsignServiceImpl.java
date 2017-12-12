package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.DeliverConsignDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.DeliverConsign;
import com.erui.order.service.AreaService;
import com.erui.order.service.DeliverConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverConsignServiceImpl implements DeliverConsignService {

    @Autowired
    private DeliverConsignDao deliverConsignDao;

    @Override
    public DeliverConsign findById(Integer id) {
        return deliverConsignDao.findOne(id);
    }
}
