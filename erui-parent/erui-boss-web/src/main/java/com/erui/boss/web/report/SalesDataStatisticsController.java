package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.SalesDataStatisticsService;
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
     * {"type":"1","startTime":"2018-01-01","endTime":"2018-01-10","sort":"1"}
     * type 1:活跃会员 其他：流失会员
     * sort 1:正序/升序   其他：倒序/降序
     * @return
     */
    @RequestMapping("inquiryMemberStatistics")
    @ResponseBody
    public Result<Object> inquiryMemberStatistics(@RequestBody Map<String,Object> params){
        Map<String,List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Object type = params.get("type");
        if (type != null && "1".equals(type.toString())) {
            /// 活跃会员信息
            data = supplierchainService.activeMemberStatistics(params);
        } else {
            /// 流失会员信息
            data = supplierchainService.lossMemberStatistics(params);
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
     * 询单失败列表
     * @return
     */
    @ResponseBody
    @RequestMapping("inquiryFailList")
    public Result<Object> inquiryFailList(@RequestBody Map<String,Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String pageNumStr = (String) params.get("pageNum");
        String pageSizeStr = (String) params.get("pageSize");
        Integer pageNum = null;
        Integer pageSize = null;
        if (StringUtils.isNumeric(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
            if (pageNum < 1) {
                pageNum = new Integer(1);
            }
        } else {
            pageNum = new Integer(1);
        }
        if (StringUtils.isNumeric(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
            if (pageSize < 1) {
                pageSize = new Integer(20);
            }
        } else {
            pageSize = new Integer(20);
        }
        params.put("pageNum",pageNum);
        params.put("pageSize",pageSize);

        PageInfo<Map<String, Object>> pageInfo = supplierchainService.inquiryFailListByPage(params);

        return new Result<>(pageInfo);
    }



}
