package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.report.model.SalesmanNums;
import com.erui.report.service.SalesmanNumsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 * 销售人员数据控制器
 */
@Controller
@RequestMapping("/report/salesmanNums")
public class SalesmanNumsController {


    @Autowired
    private SalesmanNumsService salesmanNumsService;

    /**
     * 增加销售人员数据
     *
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public Result<Object> add(@RequestBody SalesmanNums SalesmanNums) {
        int flag = salesmanNumsService.add(SalesmanNums);
        Result<Object> result = new Result<>();
        if (1 == flag) {
            result.setStatus(ResultStatusEnum.FAIL);
        } else if (2 == flag) {
            result.setStatus(ResultStatusEnum.DATA_REPEAT_ERROR);
        }
        return result;
    }

    /**
     * 删除销售人员数据
     *
     * @return
     */
    @RequestMapping("del")
    @ResponseBody
    public Result<Object> del(@RequestBody List<Integer> ids) {
        Result<Object> result = new Result<>();
        if (ids == null || ids.size() == 0) {
            result.setStatus(ResultStatusEnum.MISS_PARAM_ERROR);
            return result;
        }
        int flag = salesmanNumsService.del(ids);
        if (1 == flag) {
            result.setStatus(ResultStatusEnum.FAIL);
        }
        return result;
    }

    /**
     * 更新销售人员数据
     *
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public Result<Object> update(@RequestBody SalesmanNums SalesmanNums) {
        Result<Object> result = new Result<>();
        if(SalesmanNums.getId() == null){
            result.setStatus(ResultStatusEnum.MISS_PARAM_ERROR);
            return result;
        }
        int flag = salesmanNumsService.update(SalesmanNums);
        if (1 == flag) {
            result.setStatus(ResultStatusEnum.FAIL);
        } else if (2 == flag) {
            result.setStatus(ResultStatusEnum.DATA_REPEAT_ERROR);
        } else if ( 3 == flag) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        return result;
    }

    /**
     * 查询销售人员数据
     *
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Result<Object> list(@RequestBody Map<String, Object> params) {
        Result<Object> result = new Result<>();
        PageInfo<SalesmanNums> pageInfo = salesmanNumsService.list(params);
        result.setData(pageInfo);
        return result;
    }
}
