package com.erui.order.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.CompanyDao;
import com.erui.order.entity.Company;
import com.erui.order.service.CompanyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;


    @Override
    @Transactional(readOnly = true)
    public Company findByIdLazy(Integer id) {
        return companyDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Company findById(Integer id) {
        Company companyDaoOne = companyDao.findOne(id);
        if (companyDaoOne != null) {
            companyDaoOne.getDeptSet().size();
        }
        return companyDaoOne;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> findAll(String areaBn, String name) {
        List<Company> companyList = companyDao.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据区域
                String[] area = null;
                if (!StringUtils.isBlank(areaBn)) {
                    area = areaBn.split(",");
                }
                if (area.length > 0) {
                    list.add(root.get("areaBn").in(area));
                }
                //根据公司名称
                if (StringUtil.isNotBlank(name)) {
                    list.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        });
        return companyList;
    }
}
