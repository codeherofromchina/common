package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.PurchRequisitionDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.PurchRequisition;
import com.erui.order.service.AreaService;
import com.erui.order.service.PurchRequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class PurchRequisitionServiceImpl implements PurchRequisitionService {

    @Autowired
    private PurchRequisitionDao purchRequisitionDao;

    @Override
    public PurchRequisition findById(Integer id) {
        return purchRequisitionDao.findOne(id);
    }
}
