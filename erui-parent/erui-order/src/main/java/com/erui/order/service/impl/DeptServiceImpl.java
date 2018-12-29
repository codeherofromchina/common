package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.DeptDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.Dept;
import com.erui.order.service.AreaService;
import com.erui.order.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;


    @Override
    public Dept findById(Integer id) {
        return deptDao.findOne(id);
    }

    @Override
    public Dept findTop1ByName(String name) {
        return deptDao.findTop1ByName(name);
    }

    @Override
    public Dept findTop1ByEnName(String enName) {
        return deptDao.findTop1ByEnName(enName);
    }
}
