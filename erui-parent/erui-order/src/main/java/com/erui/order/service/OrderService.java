package com.erui.order.service;


import com.erui.order.entity.ComplexOrder;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.requestVo.OutListCondition;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 根据条件查询门户订单列表
     * @param condition
     * @return
     */
    Page<ComplexOrder> findByOutList(OutListCondition condition);
    /**
     * 删除订单
     * @param ids
     * @return
     */
    void deleteOrder(Integer ids []);
    /**
     * 修改订单
     * @param addOrderVo
     * @return
     */
    boolean updateOrder(AddOrderVo addOrderVo) throws Exception;
    /**
     * 添加订单
     * @param addOrderVo
     * @return
     */
    boolean addOrder(AddOrderVo addOrderVo) throws Exception;
    /**
     * 订单详情
     * @param orderId
     * @return
     */
    Order detail(Integer orderId);
    /**
     * 订单日志
     * @param orderId
     * @return
     */
    List<OrderLog> orderLog(Integer orderId);


    void updateOrderDeliverConsignC(Set<Integer> orderId);
    /**
     * 订单确认
     * @param order
     * @return
     */
    boolean orderFinish(Order order);
     /**
      * @Author:SHIGS
      * @Description对接门户订单详情
      * @Date:14:47 2018/3/19
      * @modified By
      */
    Map<String,Object> findByIdOut(Integer id);
}
