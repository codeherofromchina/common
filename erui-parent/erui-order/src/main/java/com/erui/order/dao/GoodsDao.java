package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface GoodsDao extends JpaRepository<Goods, Serializable> {
}
