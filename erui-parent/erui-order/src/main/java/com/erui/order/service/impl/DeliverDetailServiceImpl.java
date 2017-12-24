package com.erui.order.service.impl;

import com.erui.order.dao.DeliverDetailDao;
import com.erui.order.entity.DeliverDetail;
import com.erui.order.service.DeliverDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverDetailServiceImpl implements DeliverDetailService {

    @Autowired
    private DeliverDetailDao deliverDetailDao;

    @Override
    public DeliverDetail findById(Integer id) {
        return deliverDetailDao.findOne(id);
    }


    @Override
    @Transactional
    public DeliverDetail findDetailById(Integer id) {
        DeliverDetail deliverDetail = deliverDetailDao.findOne(id);
        deliverDetail.getDeliverNotice();
        return deliverDetail;
    }


}
