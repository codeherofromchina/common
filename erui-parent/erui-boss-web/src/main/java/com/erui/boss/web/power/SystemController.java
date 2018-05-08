package com.erui.boss.web.power;

import com.erui.boss.web.util.Result;
import com.erui.power.model.System;
import com.erui.power.service.SystemService;
import com.erui.power.vo.SystemVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by wangxiaodan on 2018/5/8.
 */
@RestController
@RequestMapping("power/system")
public class SystemController {
    @Autowired
    private SystemService systemService;

    /**
     * 分页查询系统列表
     *
     * @return
     */
    @RequestMapping("list")
    public Result<PageInfo> list(@RequestBody SystemVo params) {
        // 根据参数查询系统列表信息
        PageInfo<System> page = systemService.findByPage(params);
        // 返回数据信息
        return new Result<>(page);
    }

}
