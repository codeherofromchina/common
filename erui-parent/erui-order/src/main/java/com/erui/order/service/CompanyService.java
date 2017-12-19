package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.Company;

import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface CompanyService {
    /**
     * 根据id查询分公司信息
     * @param id
     * @return
     */
    Company findById(Integer id);
    /**
     * c查询所有分公司
     * @param
     * @return
     */
    List<Company> findAll();
}
