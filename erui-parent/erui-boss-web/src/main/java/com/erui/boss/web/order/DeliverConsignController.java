package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.DeliverConsign;
import com.erui.order.entity.Order;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/deliverConsign")
public class DeliverConsignController {
    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private DeliverConsignService deliverConsignService;

    @Autowired
    private OrderService orderService;

    /**
     * 根据ID获取出口通知单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "get")
    public Result<Object> get(@RequestParam(name = "id") Integer id) {
        DeliverConsign deliverConsign = deliverConsignService.findById(id);
        return new Result<>(deliverConsign);
    }


    /**
     * 根据订单ID获取出口通知单所需信息
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "orderInfoForAdd", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderInfoForAdd(@RequestParam(name = "orderId") Integer orderId) {
        Order order = orderService.detail(orderId);
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", order.getId());
        data.put("deptId", order.getExecCoId());
        data.put("deptName", order.getExecCoName());
        data.put("createUserId", order.getAgentId());
        data.put("coId", order.getSigningCo());
        //    data.put("coName",order.getId());
        data.put("goodsList", order.getGoodsList());
        return new Result<>(data);
    }

    /**
     * 新增办理出口发货单
     *
     * @param deliverConsign
     *
     * @return
     */
    @RequestMapping(value = "addDeliverConsign", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addOrder(@RequestBody DeliverConsign deliverConsign) {
        // TODO参数检查略过
        try {
            boolean flag = false;
            if (deliverConsign.getId() != null) {
                flag = deliverConsignService.updateDeliverConsign(deliverConsign);
            } else {
                flag = deliverConsignService.addDeliverConsign(deliverConsign);
            }
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("出口发货通知单操作失败：{}", deliverConsign, ex);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }
    /**
     * 获取出口发货单详情
     *
     * @return
     */
    @RequestMapping(value = "queryDeliverConsignDesc", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public Result<DeliverConsign> queryOrderDesc(@RequestParam(name = "id")Integer id) {
        DeliverConsign deliverConsign = deliverConsignService.findById(id);
        return new Result<>(deliverConsign);
    }
    /**
     * 获取出口发货单列表
     *
     * @return
     */
    @RequestMapping(value = "queryExportList", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public Result<List<DeliverConsign> > queryExportList(@RequestParam(name = "orderId")Integer orderId) {
        List<DeliverConsign> deliverList = deliverConsignService.findByOrderId(orderId);
        return new Result<>(deliverList);
    }
}
