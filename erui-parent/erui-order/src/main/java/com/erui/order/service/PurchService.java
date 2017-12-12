package com.erui.order.service;

import com.erui.order.entity.Purch;
import com.erui.order.requestVo.PurchListCondition;
import org.springframework.data.domain.Page;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface PurchService {
    /**
     * 根据id查询采购信息
     * @param id
     * @return
     */
    Purch findById(Integer id);


    /**
     * 根据条件分页查询采购信息列表
     * @param condition
     * @return
     */
    Page<Purch> findByPage(PurchListCondition condition) ;
}
