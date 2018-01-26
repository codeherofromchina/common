package com.erui.order.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.AreaDao;
import com.erui.order.dao.CompanyDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.Company;
import com.erui.order.service.AreaService;
import com.erui.order.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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
    public Company findById(Integer id) {
        Company companyDaoOne = companyDao.findOne(id);
        companyDaoOne.getDeptSet().size();
        return companyDaoOne;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Company> findAll(String areaBn) {
        List<Company> companyList = companyDao.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据销售同号模糊查询
                if (StringUtil.isNotBlank(areaBn)) {
                    list.add(cb.equal(root.get("areaBn").as(String.class), areaBn));
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        });
        return companyList;
    }
}
