package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


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
        //页数不能小于1
        if (condition.getPage()<1){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        Page<Order> orderPage = orderService.findByPage(condition);
        if (orderPage.hasContent()) {
            orderPage.getContent().forEach(vo -> {
                vo.setAttachmentSet(null);
                vo.setOrderPayments(null);
                vo.setGoodsList(null);
            });
        }
        return new Result<>(orderPage);
    }
    /**
     * 删除订单
     *@param ids
     * @return
     */
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderDelete(@RequestBody Map<String, Integer[]> ids) {
      /*  Integer id = map.get("ids");
        Integer [] ids = new Integer[1];
        ids[0] = id;*/
        orderService.deleteOrder(ids.get("ids"));
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
        Result<Object> result = new Result<>();
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
            if (ex instanceof DataIntegrityViolationException){
                result.setCode(ResultStatusEnum.DUPLICATE_ERROR.getCode());
                result.setMsg("销售合同号已存在");
                return result;
            }
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 获取订单详情
     *
     * @return
     */
    @RequestMapping(value = "queryOrderDesc", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Order> queryOrderDesc(@RequestBody Map<String,Integer> map) {
        Order order = orderService.findById(map.get("id"));
        return new Result<>(order);
    }
}
