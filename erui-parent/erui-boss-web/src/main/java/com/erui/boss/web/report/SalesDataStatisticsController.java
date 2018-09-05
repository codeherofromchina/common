package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.SalesDataStatisticsService;
import com.erui.report.util.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 销售数据统计控制器
 */
@Controller
@RequestMapping(value = "/report/salesDataStatistics")
public class SalesDataStatisticsController {
    @Autowired
    private SalesDataStatisticsService supplierchainService;

    /**
     * 代理商统计
     * {"startTime":"2018-01-01","endTime":"2019-01-01","sort":"1","typeDimension":"country"}
     * @return
     */
    @RequestMapping("agencySupplierStatistics")
    @ResponseBody
    public Result<Object> agencySupplierStatistics(@RequestBody Map<String,Object> params){
        Map<String,List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String typeDimension = (String) params.get("typeDimension"); // 分析类型维度 ['事业部','国家','销售地区' ]
        if ("org".equals(typeDimension)) { // 事业部类型维度获取数据
            data = supplierchainService.agencyOrgStatisticsData(params);
        } else if ("country".equals(typeDimension)) {
            data = supplierchainService.agencySupplierCountryStatisticsData(params);
        } else if("saleCountry".equals(typeDimension)){
            // TODO 这里待完善信息
            //data = supplierchainService.agencyOrgStatisticsData(params);
        }

        Result<Object> result = new Result<>();
        if (data == null) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }

    /**
     * 询报价统计 -- 流失会员、活跃会员
     * {} TODO 待完善
     * @return
     */
    @RequestMapping("quoteMemberStatistics")
    @ResponseBody
    public Result<Object> quoteMemberStatistics(@RequestBody Map<String,Object> params){
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }


        return new Result<>();
    }

    /**
     * 询单失败列表
     * @return
     */
    @ResponseBody
    @RequestMapping("inquiryFailList")
    public Result<Object> inquiryFailList(@RequestBody Map<String,Object> params) {



        return new Result<>();
    }



}
