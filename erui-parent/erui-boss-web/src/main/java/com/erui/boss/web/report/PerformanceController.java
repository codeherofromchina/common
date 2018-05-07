package com.erui.boss.web.report;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.report.service.PerformanceService;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *  销售业绩统计
 * Created by lirb on 2018/5/3
 */
@Controller
@RequestMapping("/report/salesPerformance")
public class PerformanceController {


    /**
     * @Author: lirb
     * @Description: 获取 日期月份列表
     * @Date:10:55 2018/5/3
     * @modified By
     */
    @ResponseBody
    @RequestMapping("/dateList")
    public Object dateList() {
        return new Result<>(new ArrayList<String>());
    }

    /**
     * @Author: lirb
     * @Description: 获取所有大区和大区中的所有国家列表
     * @Date:10:55 2018/5/3
     * @modified By
     */
    @RequestMapping("/areaList")
    @ResponseBody
    public Object areaList(String areaName) {
        Result<Object> result = new Result<>();

        List<InquiryAreaVO> arayList = null;//inquiryService.selectAllAreaAndCountryList();
        if (StringUtils.isNotBlank(areaName)) {
            List<InquiryAreaVO> ll = arayList.parallelStream().filter(vo -> vo.getAreaName().equals(areaName))
                    .collect(Collectors.toList());
            if (ll.size() > 0) {
                result.setData(ll.get(0).getCountries());
            } else {
                return result.setStatus(ResultStatusEnum.AREA_NOT_EXIST);
            }
        } else {
            List<String> areaList = arayList.parallelStream().map(InquiryAreaVO::getAreaName)
                    .collect(Collectors.toList());
            result.setData(areaList);
        }
        return result;
    }


    /**
     * @Author: lirb
     * @Description: 新增会员统计
     * @Date:10:55 2018/5/3
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "/incrBuyer", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object incrBuyerCount(@RequestBody Map<String, Object> params) {
        Result<Object> result = new Result<>();

        return result;
    }


}