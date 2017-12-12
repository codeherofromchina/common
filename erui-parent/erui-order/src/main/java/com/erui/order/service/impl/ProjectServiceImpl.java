package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.Project;
import com.erui.order.service.AreaService;
import com.erui.order.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public Project findById(Integer id) {
        return projectDao.findOne(id);
    }
}
