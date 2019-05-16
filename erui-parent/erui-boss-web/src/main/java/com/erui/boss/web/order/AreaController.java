package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Area;
import com.erui.order.service.AreaService;
import com.erui.order.service.PurchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/area")
public class AreaController {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AreaService areaService;
    @Autowired
    private PurchService purchService;

    /**
     * 根据ID获取地区信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "get")
    public Result<Object> get(@RequestBody Map<String, Integer> map) {
        Area area = areaService.findById(map.get("id"));
        return new Result<>(area);

    }

    @Value("#{webProp[name_test]}")
    private String str;

    public void setStr(String str) {
        this.str = str;
    }

    /**
     * 根据ID获取地区信息
     *
     * @return
     */
    @RequestMapping(value = "a1")
    @Transactional
    public Result<Object> a1() {
        Map<String, Object> map1 = new HashMap();
        map1.put("abc", str);
        map1.put("abc2", "shuaiguo222222222");
        return new Result<>(map1);





    }

}
