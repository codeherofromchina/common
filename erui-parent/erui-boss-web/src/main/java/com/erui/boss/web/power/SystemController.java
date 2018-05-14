package com.erui.boss.web.power;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.power.model.System;
import com.erui.power.service.SystemService;
import com.erui.power.vo.SystemVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public Result<PageInfo> list(@RequestBody @Valid SystemVo params) {
        // 根据参数查询系统列表信息
        PageInfo<System> page = systemService.findByPage(params);
        // 返回数据信息
        return new Result<>(page);
    }

    /**
     * 删除资源
     *
     * @param params
     * id
     * @return
     */
    @RequestMapping(value = "del", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> del(@RequestBody Map<String, String> params) {
        String idStr = params.get("id");
        int id = 0;
        if (StringUtils.isNumeric(idStr)) {
            id = Integer.parseInt(idStr);
        }
        if (id > 0){
            if (systemService.delete(id)){
                return new Result<>();
            }
        }

        return  new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 新增API资源
     * @param params
     *      {"id":"","name":"","bn":"","desc":"","enable":"是否可用",created_time:"创建时间"}
     * @return
     */
    @RequestMapping(value = "add", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody System params) {
        String bn = params.getBn();

        if (StringUtils.isNotBlank(bn)){
            if (systemService.add(params)){
                return new Result<>();
            }
        }

        return  new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 更新API资源
     * @param params
     *      {"id":"","name":"","bn":"","desc":"","enable":"是否可用",created_time:"创建时间"}
     * @return
     */
    @RequestMapping(value = "update", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> update(@RequestBody System params) {
        String bn = params.getBn();

        if (StringUtils.isNotBlank(bn)){
            if (systemService.update(params)){
                return new Result<>();
            }
        }

        return  new Result<>(ResultStatusEnum.FAIL);
    }
}
