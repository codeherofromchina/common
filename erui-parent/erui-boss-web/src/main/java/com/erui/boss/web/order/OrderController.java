package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by GS on 2017/12/15 0015.
 */
@RestController
@RequestMapping("order/orderManage")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 获取采购单列表
     *
     * @return
     */
    @RequestMapping(value = "orderManage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderManage(@RequestBody OrderListCondition condition) {
        Page<Order> orderPage = orderService.findByPage(condition);
        return new Result<>(orderPage);
    }
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderDelete(@RequestBody int[] ids ) {
        orderService.deleteOrder(ids);
        return  new Result<>();
    }
}
