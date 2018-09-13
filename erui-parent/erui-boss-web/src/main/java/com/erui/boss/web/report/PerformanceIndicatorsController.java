package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.report.model.PerformanceIndicators;
import com.erui.report.service.PerformanceIndicatorsService;
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
 * 业绩指标控制器
 */
@Controller
@RequestMapping("/report/performanceIndicators")
public class PerformanceIndicatorsController {
    @Autowired
    private PerformanceIndicatorsService performanceIndicatorsService;

    /**
     * 增加业绩指标
     *
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public Result<Object> add(@RequestBody PerformanceIndicators performanceIndicators) {
        int flag = performanceIndicatorsService.add(performanceIndicators);
        Result<Object> result = new Result<>();
        if (1 == flag) {
            result.setStatus(ResultStatusEnum.FAIL);
        } else if (2 == flag) {
            result.setStatus(ResultStatusEnum.DATA_REPEAT_ERROR);
        }
        return result;
    }

    /**
     * 删除业绩指标
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
        int flag = performanceIndicatorsService.del(ids);
        if (1 == flag) {
            result.setStatus(ResultStatusEnum.FAIL);
        }
        return result;
    }

    /**
     * 更新业绩指标
     *
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public Result<Object> update(@RequestBody PerformanceIndicators performanceIndicators) {
        Result<Object> result = new Result<>();
        if(performanceIndicators.getId() == null){
            result.setStatus(ResultStatusEnum.MISS_PARAM_ERROR);
            return result;
        }
        int flag = performanceIndicatorsService.update(performanceIndicators);
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
     * 查询业绩指标
     *
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Result<Object> list(@RequestBody Map<String, Object> params) {
        Result<Object> result = new Result<>();
        PageInfo<PerformanceIndicators> pageInfo = performanceIndicatorsService.list(params);
        result.setData(pageInfo);
        return result;
    }
}
