package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


/**
 * Created by GS on 2017/12/15 0015.
 */
@RestController
@RequestMapping("order/orderManage")
public class OrderController {
    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    /**
     * 获取单列表
     *
     * @return
     */
    @RequestMapping(value = "orderManage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderManage(@RequestBody OrderListCondition condition) {
        Page<Order> orderPage = orderService.findByPage(condition);
        return new Result<>(orderPage);
    }
    /**
     * 删除订单
     *@param ids
     * @return
     */
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderDelete(@RequestBody int[] ids ) {
        orderService.deleteOrder(ids);
        return  new Result<>();
    }

    /**
     * 新增订单
     *
     * @param addOrderVo
     * @return
     */
    @RequestMapping(value = "addOrder", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addOrder(@RequestBody AddOrderVo addOrderVo) {
        // TODO参数检查略过
        try {
            boolean flag = false;
            if (addOrderVo.getId() != null) {
                flag = orderService.updateOrder(addOrderVo);
            } else {
                flag = orderService.addOrder(addOrderVo);
            }
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("订单操作失败：{}", addOrderVo, ex);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 获取订单详情
     *
     * @return
     */
    @RequestMapping(value = "queryOrderDesc", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public Result<Order> queryOrderDesc(@RequestParam(name = "id")Integer id) {
        Order order = orderService.findById(id);
        return new Result<>(order);
    }
}
