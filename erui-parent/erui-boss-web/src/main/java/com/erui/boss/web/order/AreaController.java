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

import java.util.Map;

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
     * @param map
     * @return
     */
    @RequestMapping(value = "get")
    public Result<Object> get(@RequestBody Map<String,Integer> map) {
        Area area = areaService.findById(map.get("id"));
        return new Result<>(area);

    }

}
