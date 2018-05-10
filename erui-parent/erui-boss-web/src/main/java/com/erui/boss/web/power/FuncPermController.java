package com.erui.boss.web.power;

import com.erui.boss.web.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 界面资源管理控制器
 * Created by wangxiaodan on 2018/5/7.
 */
@RestController
@RequestMapping("power/funcperm")
public class FuncPermController {

    /**
     * 获取所有界面资源
     *
     * @return
     */
    @RequestMapping(value = "allFuncPerm", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> allFuncPerm() {


        return new Result<>();
    }

    /**
     * 添加界面资源
     * @param params
     *      TODO
     * @return
     */
    @RequestMapping(value = "add", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> add(@RequestBody Map<String, String> params) {

        return null;
    }



    /**
     * 查看界面资源详情
     * 包含该资源下的API资源列表
     * @param params
     *      {"id":"界面资源ID"}
     * @return
     */
    @RequestMapping(value = "detail", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> detail(@RequestBody Map<String, String> params) {

        return null;
    }


    /**
     * 删除界面资源
     * 只可删除最低级的菜单或按钮
     * @param params
     *      {"id":"界面资源ID"}
     * @return
     */
    @RequestMapping(value = "del", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> del(@RequestBody Map<String, String> params) {

        return null;
    }



    /**
     * 编辑界面资源详情
     * @param params
     *      TODO
     * @return
     */
    @RequestMapping(value = "edit", consumes = "application/json", method = RequestMethod.POST)
    public Result<Object> edit(@RequestBody Map<String, String> params) {

        return null;
    }



}
