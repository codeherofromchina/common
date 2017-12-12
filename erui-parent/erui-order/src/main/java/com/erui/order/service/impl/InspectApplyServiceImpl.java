package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.InspectApplyDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.InspectApply;
import com.erui.order.service.AreaService;
import com.erui.order.service.InspectApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InspectApplyServiceImpl implements InspectApplyService {

    @Autowired
    private InspectApplyDao inspectApplyDao;

    @Override
    public InspectApply findById(Integer id) {
        return inspectApplyDao.findOne(id);
    }
}
