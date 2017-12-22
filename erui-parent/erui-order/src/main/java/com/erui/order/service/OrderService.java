package com.erui.order.service;


import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import org.springframework.data.domain.Page;

/**
 * 订单业务类
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderService {
    /**
     * 根据id查询订单信息
     * @param id
     * @return
     */
    Order findById(Integer id);
    /**
     * 根据条件分页查询订单列表
     * @param condition
     * @return
     */
    Page<Order> findByPage(OrderListCondition condition);
    /**
     * 删除订单
     * @param ids
     * @return
     */
    void deleteOrder(int ids []);
    /**
     * 修改订单
     * @param addOrderVo
     * @return
     */
    boolean updateOrder(AddOrderVo addOrderVo);
    /**
     * 添加订单
     * @param addOrderVo
     * @return
     */
    boolean addOrder(AddOrderVo addOrderVo);

    Order detail(Integer orderId);
}
