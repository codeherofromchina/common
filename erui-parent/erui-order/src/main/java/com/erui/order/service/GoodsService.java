package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.Goods;

import java.util.List;

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

    /**
     * 通过项目id查找商品列表
     * @param projectIds
     * @return
     */
    List<Goods> findByProjectIds(List<Integer> projectIds);
}
