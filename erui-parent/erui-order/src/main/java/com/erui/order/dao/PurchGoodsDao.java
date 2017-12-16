package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.PurchGoods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface PurchGoodsDao extends JpaRepository<PurchGoods, Serializable> {
    List<PurchGoods> findByPurchId(Integer purchId);

    PurchGoods findByPurchIdAndGoodsId(Integer id, Integer goodsId);
}
