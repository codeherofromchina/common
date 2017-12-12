package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.DeliverNoticeDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.DeliverNotice;
import com.erui.order.service.AreaService;
import com.erui.order.service.DeliverNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverNoticeServiceImpl implements DeliverNoticeService {

    @Autowired
    private DeliverNoticeDao deliverNoticeDao;

    @Override
    public DeliverNotice findById(Integer id) {
        return deliverNoticeDao.findOne(id);
    }
}
