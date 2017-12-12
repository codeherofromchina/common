package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Purch;
import com.erui.order.requestVo.PurchListCondition;
import com.erui.order.service.PurchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangxiaodan on 2017/12/12.
 */
@RestController
@RequestMapping("order/purch")
public class PurchController {

    @Autowired
    private PurchService purchService;

    /**
     * 获取采购单列表
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody PurchListCondition condition) {
        Page<Purch> purchPage = purchService.findByPage(condition);
        return new Result<>(purchPage);
    }

}
