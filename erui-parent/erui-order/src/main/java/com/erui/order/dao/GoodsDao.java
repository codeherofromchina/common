package com.erui.order.dao;

import com.erui.order.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface GoodsDao extends JpaRepository<Goods, Serializable> {

    List<Goods> findByProjectIdIn(List<Integer> projectIds);

    List<Goods> findByOrderId(Integer orderId);
}
