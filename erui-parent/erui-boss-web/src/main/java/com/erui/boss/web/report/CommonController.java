package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.PerformanceIndicators;
import com.erui.report.service.CommonService;
import com.erui.report.service.PerformanceIndicatorsService;
import com.erui.report.util.ParamsUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 * 公共信息控制器
 */
@Controller
@RequestMapping("/report/common")
public class CommonController {
    @Autowired
    private CommonService commonService;

    /**
     * 地区列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("areaList")
    public Result<Object> areaList() {
        List<Map<String, Object>> areaList = commonService.areaList();
        Result<Object> result = new Result<>();
        if (areaList == null || areaList.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(areaList);
        }
        return result;
    }

    /**
     * 国家列表
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("countryList")
    public Result<Object> countryList(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> countryList = commonService.countryList(params);
        Result<Object> result = new Result<>();
        if (countryList == null || countryList.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(countryList);
        }
        return result;
    }


    /**
     * 事业部列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("orgList")
    public Result<Object> orgList() {
        List<Map<String, Object>> orgList = commonService.orgList();
        Result<Object> result = new Result<>();
        if (orgList == null || orgList.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(orgList);
        }
        return result;
    }
}
