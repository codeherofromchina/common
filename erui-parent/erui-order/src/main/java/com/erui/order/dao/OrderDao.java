package com.erui.order.dao;

import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/8.
 */
@Repository("orderDao")
public interface OrderDao extends JpaRepository<OrderLog, Serializable> {

    OrderLog findById(Long id);

}
