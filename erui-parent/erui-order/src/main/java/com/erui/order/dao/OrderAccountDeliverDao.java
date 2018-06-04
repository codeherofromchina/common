package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.OrderAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderAccountDao extends JpaRepository<OrderAccount, Serializable> {

    List<OrderAccount> findByOrderId(Integer id);

    List<OrderAccount> findByOrderIdAndDelYn(Integer id, int i);
}
