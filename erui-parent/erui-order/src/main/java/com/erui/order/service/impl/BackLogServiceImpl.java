package com.erui.order.service.impl;

import com.erui.order.dao.BackLogDao;
import com.erui.order.entity.BackLog;
import com.erui.order.entity.Company;
import com.erui.order.service.BackLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BackLogServiceImpl implements BackLogService{

    @Autowired
    private BackLogDao backLogDao;


}
