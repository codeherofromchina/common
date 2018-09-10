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
     *
     * @return
     */
    @RequestMapping("agencySupplierStatistics")
    @ResponseBody
    public Result<Object> agencySupplierStatistics(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String typeDimension = (String) params.get("typeDimension"); // 分析类型维度 ['事业部','国家','销售地区' ]
        if ("org".equals(typeDimension)) { // 事业部类型维度获取数据
            data = supplierchainService.agencyOrgStatisticsData(params);
        } else if ("country".equals(typeDimension)) {
            data = supplierchainService.agencySupplierCountryStatisticsData(params);
        } else if ("saleCountry".equals(typeDimension)) {
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
     *
     * @return
     */
    @RequestMapping("inquiryMemberStatistics")
    @ResponseBody
    public Result<Object> inquiryMemberStatistics(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
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
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("inquiryFailList")
    public Result<Object> inquiryFailList(@RequestBody Map<String, Object> params) {
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
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);

        PageInfo<Map<String, Object>> pageInfo = supplierchainService.inquiryFailListByPage(params);

        return new Result<>(pageInfo);
    }

    /**
     * 事业部报价用时
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("orgQuoteTotalCostTime")
    public Result<Object> orgQuoteTotalCostTime(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        data = supplierchainService.orgQuoteTotalCostTime(params);
        Result<Object> result = new Result<>();
        if (data == null) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 会员询单额
     * {"startTime":"","endTime":"","type":""}
     * type  1：报价金额   2：询单数量   3：报价数量
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("memberInquiryAmount")
    public Result<Object> memberInquiryAmount(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = (String) params.get("type");
        Map<String, List<Object>> data = null;
        Result<Object> result = new Result<>();
        if ("1".equals(type)) {
            // 报价金额按事业部统计
            data = supplierchainService.quoteAmountGroupOrg(params);
        } else if ("2".equals(type)) {
            // 询单数量按事业部统计
            data = supplierchainService.inquiryNumbersGroupOrg(params);
        } else if ("3".equals(type)) {
            // 报价数量按事业部统计
            data = supplierchainService.quoteNumbersGroupOrg(params);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        result.setData(data);
        return result;
    }

    /**
     * 订单数据统计 - 整体
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("orderInfoWhole")
    public Result<Object> orderStatisticsWholeInfo(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = (String) params.get("type");
        Map<String, List<Object>> data = null;
        Result<Object> result = new Result<>();
        if ("1".equals(type)) {
            // 按事业部分析
            data = supplierchainService.orderStatisticsWholeInfoGroupByOrg(params);
        } else if ("2".equals(type)) {
            // 按地区分析
            data = supplierchainService.orderStatisticsWholeInfoGroupByArea(params);
        } else if ("3".equals(type)) {
            // 按国家分析
            data = supplierchainService.orderStatisticsWholeInfoGroupByCountry(params);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        result.setData(data);
        return result;
    }


    /**
     * 订单数据统计 - 利润
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("orderInfoProfitPercent")
    public Result<Object> orderInfoProfitPercent(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = (String) params.get("type");
        Map<String, List<Object>> data = null;
        Result<Object> result = new Result<>();
        if ("1".equals(type)) {
            // 事业部利润率
            data = supplierchainService.orderStatisticsProfitPercentGroupByOrg(params);
        } else if ("2".equals(type)) {
            // 地区利润率
            data = supplierchainService.orderStatisticsProfitPercentGroupByArea(params);
        } else if ("3".equals(type)) {
            // 国家利润率
            data = supplierchainService.orderStatisticsProfitPercentGroupByCountry(params);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        result.setData(data);
        return result;
    }



    /**
     * 订单数据统计 - 成单率
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("orderInfoMonoRate")
    public Result<Object> orderInfoMonoRate(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = (String) params.get("type");
        Map<String, List<Object>> data = null;
        Result<Object> result = new Result<>();
        if ("1".equals(type)) {
            // 事业部成单率
            data = supplierchainService.orderStatisticsMonoRateGroupByOrg(params);
        } else if ("2".equals(type)) {
            // 地区成单率
            data = supplierchainService.orderStatisticsMonoRateGroupByArea(params);
        } else if ("3".equals(type)) {
            // 国家成单率
            data = supplierchainService.orderStatisticsMonoRateGroupByCountry(params);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        result.setData(data);
        return result;
    }
}
