package com.erui.out.web.order;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.encrypt.MD5;
import com.erui.order.entity.ComplexOrder;
import com.erui.order.requestVo.OutListCondition;
import com.erui.order.requestVo.ResponseOutOrder;
import com.erui.out.web.util.Result;
import com.erui.out.web.util.ResultStatusEnum;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by GS on 2017/12/15 0015.
 */

@RestController
@RequestMapping("order/orderManageOut")
public class OrderOutController {
    private final static Logger logger = LoggerFactory.getLogger(OrderOutController.class);
    @Autowired
    private OrderService orderService;

    private static final String api_key_order = "3a13749d4b3af3b2bb601552278a0051";


    /**
     * 获取单列表
     *
     * @return
     */
    @RequestMapping(value = "orderManage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object orderManage(@RequestBody OutListCondition condition) {
        Result<Object> result = new Result<>();
        //页数不能小于1
        if (condition.getPage() < 1) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        List<ResponseOutOrder> outList = new ArrayList<>();
        Page<ComplexOrder> orderPage = orderService.findByOutList(condition);
        HashMap<Object, Object> map = null;
        if (orderPage.hasContent()) {
            try {
                orderPage.getContent().forEach(vo -> {
                    ResponseOutOrder responseOutOrder = new ResponseOutOrder();
                    responseOutOrder.setId(vo.getId());
                    responseOutOrder.setOrder_no(vo.getContractNo());
                    responseOutOrder.setStatus(vo.getStatus());
                    responseOutOrder.setAmount(BigDecimal.valueOf(Double.parseDouble(vo.getTotalPrice())));
                    responseOutOrder.setDelivery_at(vo.getDeliveryDate());
                    responseOutOrder.setPay_status(vo.getPayStatus());
                    responseOutOrder.setPo_no(vo.getPoNo());
                    responseOutOrder.setTrade_terms_bn(vo.getTradeTerms());
                    responseOutOrder.setCreated_at(vo.getCreateTime());
                    responseOutOrder.setType(vo.getType());
                    responseOutOrder.setCurrency_bn(vo.getCurrencyBn());
                    responseOutOrder.setTo_country(vo.getToCountry());
                    responseOutOrder.setTo_port(vo.getToPort());
                    responseOutOrder.setShow_status_text(ComplexOrder.fromStatusCode(vo.getStatus()));
                    responseOutOrder.setPay_status_text(ComplexOrder.fromPayCode(vo.getPayStatus()));
                    outList.add(responseOutOrder);
                });
            }catch (Exception e){
                e.printStackTrace();
            }
            map = new HashMap<>();
            map.put("count",orderPage.getTotalElements());
            map.put("data",outList);
            map.put("message",result.getMsg());
            map.put("code",result.getCode());
        }else {
            result.setCode(ResultStatusEnum.DATA_NULL.getCode());
            result.setMsg(ResultStatusEnum.DATA_NULL.getMsg());
            return result;

        }
        return map;
    }
    /**
     * 删除订单
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderDelete(@RequestBody Map<String, Integer[]> ids) {
        orderService.deleteOrder(ids.get("ids"));
        return new Result<>();
    }

    /**
     * 获取订单详情
     *
     * @return
     */
    @RequestMapping(value = "queryOrderDesc", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> queryOrderDesc(@RequestBody JSONObject json) {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        Map<String,Object> resultMap = orderService.findByIdOut(json.getInteger("id"));
        if (resultMap != null) {
           result.setData(resultMap);
           result.setCode(ResultStatusEnum.SUCCESS.getCode());
           result.setMsg(ResultStatusEnum.SUCCESS.getMsg());
           return result;
        }
        return result;
    }

    /**
     * 获取订单跟踪
     *
     *
     *
     * @return
     */
    @RequestMapping(value = "queryOrderLog", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> queryOrderLog(@RequestBody Map<String, Integer> map) {
        Integer orderId = map.get("orderId");
        Result<Object> result = new Result<>();
        if (orderId == null || orderId <= 0) {
            return result.setStatus(ResultStatusEnum.FAIL).setMsg("订单号错误");
        }
        List<OrderLog> logList = orderService.orderLog(orderId);
        if (logList.size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(logList);
    }

    /**
     * 确认订单
     *
     * @param order
     * @return
     */
    @RequestMapping(value = "orderFinish", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderFinish(@RequestBody Order order) {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        boolean flag;
        flag = orderService.orderFinish(order);
        if (flag) {
            result.setCode(ResultStatusEnum.SUCCESS.getCode());
            result.setMsg(ResultStatusEnum.SUCCESS.getMsg());
            return result;
        }
        return result;
    }

}
