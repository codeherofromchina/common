package com.erui.order.service;

import com.erui.order.entity.DeliverDetail;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverDetailService {
    /**
     * 根据id查询出库详情信息
     * @param id
     * @return
     */
    DeliverDetail findById(Integer id);


    /**
     * 查询物流-出库单详情
     * @param id
     * @return
     */
    DeliverDetail findDetailById(Integer id);

}
