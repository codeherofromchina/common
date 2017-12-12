package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.Instock;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface InstockService {
    /**
     * 根据id查询出库信息
     * @param id
     * @return
     */
    Instock findById(Integer id);
}
