package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.Goods;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface GoodsService {
    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    Goods findById(Integer id);
}
