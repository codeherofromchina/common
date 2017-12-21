package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Area;
import com.erui.order.entity.DeliverNotice;
import com.erui.order.service.AreaService;
import com.erui.order.service.DeliverNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 看货通知单控制器
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/deliverNotice")
public class DeliverNoticeController {
    @Autowired
    private DeliverNoticeService deliverNoticeService;


    /**
     * 看货通知单列表
     * @param condition
     * @return
     */
    @RequestMapping(value = "/list")
    public Result<Object> list(DeliverNotice condition) {

        Page<DeliverNotice> page = deliverNoticeService.listByPage(condition);

        return new Result<>(page);
    }

}
