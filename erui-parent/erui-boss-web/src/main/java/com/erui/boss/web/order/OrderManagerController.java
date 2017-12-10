package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import com.erui.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.sun.javafx.tools.resource.DeployResource.Type.data;

/**
 * Created by wangxiaodan on 2017/12/8.
 */
@RestController
@RequestMapping(value = "/order/orderManager")
public class OrderManagerController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     *
     * @return
     */
    @RequestMapping(value = "/orderList")
    public Result<Object> orderList(Long id) {
        OrderLog order = orderService.findById(id);

        return new Result<>(order);
    }

}
