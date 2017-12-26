package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Company;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface CompanyDao extends JpaRepository<Company, Serializable> {
    List<Company> findAll(Specification<Company> specification);
}
