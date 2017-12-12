package com.erui.order.service;

import com.erui.order.entity.Order;
import com.erui.order.entity.Project;

/**
 * 订单业务类
 * Created by wangxiaodan on 2017/12/11.
 */
public interface ProjectService {
    /**
     * 根据id查询项目信息
     * @param id
     * @return
     */
    Project findById(Integer id);
}
