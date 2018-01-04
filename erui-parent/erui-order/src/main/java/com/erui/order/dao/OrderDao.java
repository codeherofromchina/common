package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderDao extends JpaRepository<Order, Serializable>, JpaSpecificationExecutor<Order>  {
    List<Order> findByIdIn(Integer[] ids);
}
