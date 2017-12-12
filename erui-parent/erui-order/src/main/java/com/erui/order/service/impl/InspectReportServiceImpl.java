package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.InspectReportDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.InspectReport;
import com.erui.order.service.AreaService;
import com.erui.order.service.InspectReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InspectReportServiceImpl implements InspectReportService {

    @Autowired
    private InspectReportDao inspectReportDao;

    @Override
    public InspectReport findById(Integer id) {
        return inspectReportDao.findOne(id);
    }
}
