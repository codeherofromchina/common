package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Area;
import com.erui.order.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 根据ID获取地区信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "get")
    public Result<Object> get(@RequestParam(name = "id") Integer id) {
        Area area = areaService.findById(id);
        return new Result<>(area);

    }

}
