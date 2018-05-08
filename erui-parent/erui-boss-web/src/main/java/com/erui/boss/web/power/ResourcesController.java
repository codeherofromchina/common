package com.erui.boss.web.power;

import com.erui.boss.web.util.Result;
import com.erui.power.service.EmployeeService;
import com.erui.power.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by wangxiaodan on 2018/5/7.
 * URL资源控制器
 */
@RestController
@RequestMapping("power/resources")
public class ResourcesController {

    @Autowired
    private ResourcesService resourcesService;

    /**
     * API资源列表
     * 条件分页查询api资源列表接口
     * @param params
     *      {"name":"资源名称","url":"资源路径","noneAuth":"权限","enable":"是否可用"}
     * @return
     */
    @RequestMapping(value = "list", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> list(@RequestBody Map<String, String> params) {

        return null;
    }

    /**
     * 新增API资源
     * @param params
     *      {"name":"资源名称","url":"资源路径","tip":"权限","noneAuth":"权限","enable":"是否可用",funcPermIds:["1","2","7"]}
     * @return
     */
    @RequestMapping(value = "add", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody Map<String, String> params) {

        return null;
    }

    /**
     * API资源详情
     * @param params
     *      {"id":"资源ID"}
     * @return 返回api基本内容和界面资源的选择信息
     */
    @RequestMapping(value = "detail", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> detail(@RequestBody Map<String, String> params) {

        return null;
    }


    /**
     * API资源更新
     * @param params
     *      TODO
     * @return
     */
    @RequestMapping(value = "update", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> update(@RequestBody Map<String, String> params) {

        return null;
    }

    /**
     * 设置API资源是否可用
     * @param params
     *      {"id":"资源ID","enable":"是否可用true/false"}
     * @return
     */
    @RequestMapping(value = "enable", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> enable(@RequestBody Map<String, String> params) {

        return null;
    }
}
