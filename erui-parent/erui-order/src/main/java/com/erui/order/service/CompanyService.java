package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.Company;

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
}
