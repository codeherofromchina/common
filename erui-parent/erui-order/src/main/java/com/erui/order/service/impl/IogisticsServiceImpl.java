package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.IogisticsDao;
import com.erui.order.service.IogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class IogisticsServiceImpl implements IogisticsService {

    @Autowired
    private IogisticsDao iogisticsDao;

}
