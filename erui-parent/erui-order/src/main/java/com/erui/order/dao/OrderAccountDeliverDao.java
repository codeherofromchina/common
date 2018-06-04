package com.erui.order.dao;

import com.erui.order.entity.OrderAccount;
import com.erui.order.entity.OrderAccountDeliver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderAccountDeliverDao extends JpaRepository<OrderAccountDeliver, Serializable> {

    List<OrderAccountDeliver> findByOrderIdAndDelYn(Integer id, int i);
}
