package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.InstockDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.Instock;
import com.erui.order.service.AreaService;
import com.erui.order.service.InstockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InstockServiceImpl implements InstockService {

    @Autowired
    private InstockDao instockDao;

    @Override
    public Instock findById(Integer id) {
        return instockDao.findOne(id);
    }
}
