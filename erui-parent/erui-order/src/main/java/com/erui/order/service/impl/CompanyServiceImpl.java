package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.CompanyDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.Company;
import com.erui.order.service.AreaService;
import com.erui.order.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    @Transactional
    public Company findById(Integer id) {
        Company companyDaoOne = companyDao.findOne(id);
        companyDaoOne.getDeptSet().size();
        return companyDaoOne;
    }

    @Override
    @Transactional
    public List<Company> findAll() {
        return companyDao.findAll();
    }
}
