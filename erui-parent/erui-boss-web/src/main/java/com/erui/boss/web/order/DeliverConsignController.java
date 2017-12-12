package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Area;
import com.erui.order.entity.DeliverConsign;
import com.erui.order.service.AreaService;
import com.erui.order.service.DeliverConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/deliverConsign")
public class DeliverConsignController {


    @Autowired
    private DeliverConsignService deliverConsignService;

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
}
