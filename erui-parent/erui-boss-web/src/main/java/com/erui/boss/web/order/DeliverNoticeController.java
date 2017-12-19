package com.erui.boss.web.order;


import com.erui.boss.web.util.Result;
import com.erui.order.entity.DeliverConsign;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.DeliverNoticeService;
import com.erui.order.service.GoodsService;
import com.erui.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value="/order/logisticsManage")
public class DeliverNoticeController {


    @Autowired
    private DeliverNoticeService deliverNoticeService;  //看货单

    @Autowired
    private DeliverConsignService deliverConsignService;      //出口发货通知单

    @Autowired
    private OrderService orderService;  //订单

    @Autowired
    private GoodsService goodsService;  //商品信息表

    /**
     * 根据出口发货通知单 查询信息
     *
     * @param id    出口发货通知单id
     * @return
     */
    @RequestMapping(value="queryGatheringRecord", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> querExitInformMessage(@RequestParam(name = "id") Integer id ){


        return new Result<>(null);
    }





}
