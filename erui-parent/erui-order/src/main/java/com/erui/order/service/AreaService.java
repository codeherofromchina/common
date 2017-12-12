package com.erui.order.service;

import com.erui.order.entity.Area;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface AreaService {
    /**
     * 根据id查询地区信息
     * @param id
     * @return
     */
    Area findById(Integer id);
}
