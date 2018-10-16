package com.erui.boss.web.report;

import com.erui.boss.web.util.HttpUtils;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.SalesDataStatisticsService;
import com.erui.report.util.ParamsUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    @ResponseBody
    @RequestMapping(value = "agencySupplierStatistics", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
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
        } else if ("area".equals(typeDimension)) {
            data = supplierchainService.agencyAreaStatisticsData(params);
        }

        Result<Object> result = new Result<>();
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 导出代理商统计excel
     *
     * @return
     * @typeDimension 分析类型维度 ['事业部','国家','销售地区' ]
     * {"startTime":"2018-01-01","endTime":"2019-01-01","sort":"1","typeDimension":"country"}
     */
    @RequestMapping(value = "exportAgencySupplierStatistics")
    public Object exportAgencySupplierStatistics(HttpServletResponse response, String typeDimension, String startTime, String endTime, Integer sort) {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("sort", sort);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        StringBuilder fileName = new StringBuilder("代理商-");
        HSSFWorkbook wb = null;
        if ("org".equals(typeDimension)) { // 事业部类型维度获取数据
            fileName.append("事业部统计");
            wb = supplierchainService.exportAgencyOrgStatisticsData(params);
        } else if ("country".equals(typeDimension)) {
            fileName.append("国家统计");
            wb = supplierchainService.exportAgencySupplierCountryStatisticsData(params);
        } else if ("area".equals(typeDimension)) {
            fileName.append("地区统计");
            wb = supplierchainService.exportAgencyAreaStatisticsData(params);
        }
        if (wb == null ) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        try {
            fileName.append("-").append(System.currentTimeMillis()).append(".xls");
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 询报价统计 -- 流失会员、活跃会员
     * {"type":"1","startTime":"2018-01-01","endTime":"2018-01-10","sort":"1"}
     * type 1:活跃会员 其他：流失会员
     * sort 1:正序/升序   其他：倒序/降序
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "inquiryMemberStatistics", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> inquiryMemberStatistics(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
        if ("1".equals(type)) {
            /// 活跃会员信息
            data = supplierchainService.activeMemberStatistics(params);
        } else {
            /// 流失会员信息
            data = supplierchainService.lossMemberStatistics(params);
        }
        Result<Object> result = new Result<>();
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 导出询报价统计 -- 流失会员、活跃会员
     * {"type":"1","startTime":"2018-01-01","endTime":"2018-01-10","sort":"1"}
     * type 1:活跃会员 其他：流失会员
     * sort 1:正序/升序   其他：倒序/降序
     *
     * @return
     */
    @RequestMapping(value = "exportInquiryMemberStatistics")
    public Object exportInquiryMemberStatistics(HttpServletResponse response, String type, String startTime, String endTime, Integer sort) {
        Map<String, Object> params = new HashMap<>();
        params.put("sort", sort);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        StringBuilder fileName = new StringBuilder("询报价-");
        HSSFWorkbook wb = null;
        if ("1".equals(type)) {
            /// 活跃会员信息
            fileName.append("活跃会员");
            wb = supplierchainService.exportActiveMemberStatistics(params);
        } else {
            /// 流失会员信息
            fileName.append("流失会员");
            wb = supplierchainService.exportLossMemberStatistics(params);
        }
        if (wb == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        try {
            fileName.append("-").append(System.currentTimeMillis()).append(".xls");
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 询报价统计 - 询价失败列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "inquiryFailList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> inquiryFailList(@RequestBody Map<String, Object> params) {
        Map<String, Object> params02 = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params02 == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = params02;
        }
        String pageNumStr = String.valueOf(params.get("pageNum"));
        String pageSizeStr = String.valueOf(params.get("pageSize"));
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
     * 导出询报价统计 - 询价失败列表
     *
     * @return
     */
    @RequestMapping(value = "exportInquiryFailList")
    public Object exportInquiryFailList(HttpServletResponse response, String startTime, String endTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        Map<String, Object> params02 = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params02 == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = params02;
        }

        HSSFWorkbook wb = supplierchainService.exportInquiryFailList(params);
        String fileName = "询报价统计-询价失败-" + System.currentTimeMillis() + ".xls";
        try {
            HttpUtils.setExcelResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 事业部报价用时
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orgQuoteTotalCostTime", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orgQuoteTotalCostTime(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        data = supplierchainService.orgQuoteTotalCostTime(params);
        Result<Object> result = new Result<>();
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 导出事业部报价用时
     *
     * @return
     */
    @RequestMapping(value = "exportOrgQuoteTotalCostTime")
    public Result<Object> exportOrgQuoteTotalCostTime(HttpServletResponse response, String startTime, String endTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        HSSFWorkbook wb = supplierchainService.exportOrgQuoteTotalCostTime(params);
        if(wb == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String fileName = "询报价统计-报价用时-" + System.currentTimeMillis() + ".xls";
        try {
            HttpUtils.setExcelResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 会员询单额
     * {"startTime":"","endTime":"","type":""}
     * type  1：报价金额   2：询单数量   3：报价数量
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "memberInquiryAmount", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> memberInquiryAmount(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
        Map<String, List<Object>> data = null;
        Result<Object> result = new Result<>();
        if ("1".equals(type)) {
            // 报价金额按地区统计
            data = supplierchainService.quoteAmountGroupArea(params);
        } else if ("2".equals(type)) {
            // 询单数量按地区统计
            data = supplierchainService.inquiryNumbersGroupArea(params);
        } else if ("3".equals(type)) {
            // 报价数量按地区统计
            data = supplierchainService.quoteNumbersGroupArea(params);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }

        return result;
    }


    /**
     * 导出会员询单额
     * {"startTime":"","endTime":"","type":""}
     * type  1：报价金额   2：询单数量   3：报价数量
     *
     * @return
     */
    @RequestMapping(value = "exportMemberInquiryAmount")
    public Object exportMemberInquiryAmount(HttpServletResponse response,String startTime,String endTime,String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("endTime",endTime);
        params.put("startTime",startTime);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Map<String, List<Object>> data = null;
        Result<Object> result = new Result<>();
        StringBuilder fileName = new StringBuilder("会员询单额-");
        HSSFWorkbook wb = null;
        if ("1".equals(type)) {
            // 报价金额按地区统计
            fileName.append("报价金额");
            wb = supplierchainService.exportQuoteAmountGroupArea(params);
        } else if ("2".equals(type)) {
            // 询单数量按地区统计
            fileName.append("询单数量");
            wb = supplierchainService.exportInquiryNumbersGroupArea(params);
        } else if ("3".equals(type)) {
            // 报价数量按地区统计
            fileName.append("报价数量");
            wb = supplierchainService.exportQuoteNumbersGroupArea(params);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        try {
            fileName.append("-").append(System.currentTimeMillis()).append(".xls");
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 订单数据统计 - 整体/订单量与金额
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orderInfoWhole", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderStatisticsWholeInfo(@RequestBody Map<String, Object> params) {
        Map<String, Object> temParam = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (temParam == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = temParam;
        }
        String type = String.valueOf(params.get("type"));
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
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 导出订单数据统计 - 整体/订单量与金额
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "exportOrderInfoWhole")
    public Object exportOrderInfoWhole(HttpServletResponse response, String type, String startTime, String endTime, Integer sort) {
        Map<String,Object> params = new HashMap();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("sort",sort);
        Map<String, Object> temParam = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (temParam == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = temParam;
        }
        StringBuilder fileName = new StringBuilder("订单数据统计-");
        HSSFWorkbook wb = null;
        Result<Object> result = new Result<>();
        if ("1".equals(type)) {
            // 按事业部分析
            fileName.append("事业部");
            wb = supplierchainService.exportOrderStatisticsWholeInfoGroupByOrg(params);
        } else if ("2".equals(type)) {
            // 按地区分析
            fileName.append("地区");
            wb = supplierchainService.exportOrderStatisticsWholeInfoGroupByArea(params);
        } else if ("3".equals(type)) {
            // 按国家分析
            fileName.append("国家");
            wb = supplierchainService.exportOrderStatisticsWholeInfoGroupByCountry(params);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        try {
            fileName.append("-").append(System.currentTimeMillis()).append(".xls");
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 订单数据统计 - 利润
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orderInfoProfitPercent", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderInfoProfitPercent(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
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
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 导出订单数据统计 - 利润
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "exportOrderInfoProfitPercent")
    public Object exportOrderInfoProfitPercent(HttpServletResponse response, String type, String startTime, String endTime, Integer sort) {
        Map<String,Object> params = new HashMap();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("sort",sort);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        HSSFWorkbook wb = null;
        StringBuilder fileName = new StringBuilder("订单数据统计-");
        Result<Object> result = new Result<>();
        if ("1".equals(type)) {
            // 事业部利润率
            fileName.append("事业部利润率");
            wb = supplierchainService.exportOrderStatisticsProfitPercentGroupByOrg(params);
        } else if ("2".equals(type)) {
            // 地区利润率
            fileName.append("地区利润率");
            wb = supplierchainService.exportOrderStatisticsProfitPercentGroupByArea(params);
        } else if ("3".equals(type)) {
            // 国家利润率
            fileName.append("国家利润率");
            wb = supplierchainService.exportOrderStatisticsProfitPercentGroupByCountry(params);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        try {
            fileName.append("-").append(System.currentTimeMillis()).append(".xls");
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 订单数据统计 - 成单率
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orderInfoMonoRate", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderInfoMonoRate(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
        Map<String, List<Object>> data = null;
        Result<Object> result = new Result<>();
        if (type == null) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else if ("1".equals(type)) {
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
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 导出订单数据统计 - 成单率
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "exportOrderInfoMonoRate")
    public Result<Object> exportOrderInfoMonoRate(HttpServletResponse response, String type, String startTime, String endTime, Integer sort) {
        Map<String,Object> params = new HashMap();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("sort",sort);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        StringBuilder fileName = new StringBuilder("订单数据统计-");
        HSSFWorkbook wb = null;
        Result<Object> result = new Result<>();
        if ("1".equals(type)) {
            // 事业部成单率
            fileName.append("事业部成单率");
            wb = supplierchainService.exportOrderStatisticsMonoRateGroupByOrg(params);
        } else if ("2".equals(type)) {
            // 地区成单率
            fileName.append("地区成单率");
            wb = supplierchainService.exportOrderStatisticsMonoRateGroupByArea(params);
        } else if ("3".equals(type)) {
            // 国家成单率
            fileName.append("国家成单率");
            wb = supplierchainService.exportOrderStatisticsMonoRateGroupByCountry(params);
        } else {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        try {
            fileName.append("-").append(System.currentTimeMillis()).append(".xls");
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 订单数据统计 - 购买力
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orderInfoPurchasingPower", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderInfoPurchasingPower(@RequestBody Map<String, Object> params) {
        Map<String, Object> params02 = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params02 == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = params02;
        }
        String pageNumStr = String.valueOf(params.get("pageNum"));
        String pageSizeStr = String.valueOf(params.get("pageSize"));
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

        PageInfo<Map<String, Object>> pageInfo = supplierchainService.orderInfoPurchasingPower(params);

        return new Result<>(pageInfo);
    }


    /**
     * 导出订单数据统计 - 购买力
     *
     * @param startTime
     * @return
     */
    @RequestMapping(value = "exportOrderInfoPurchasingPower")
    public Object exportOrderInfoPurchasingPower(HttpServletResponse response, String startTime, String endTime, Integer sort) {
        Map<String,Object> params = new HashMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("sort",sort);
        Map<String, Object> params02 = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params02 == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = params02;
        }

        String fileName = "订单数据统计-购买力-" + java.lang.System.currentTimeMillis() + ".xls";
        HSSFWorkbook wb = supplierchainService.exportOrderInfoPurchasingPower(params);
        try {
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 订单数据统计 - 复购周期
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orderInfoBuyCycle", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderInfoBuyCycle(@RequestBody Map<String, Object> params) {
        Map<String, Object> params02 = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params02 == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = params02;
        }
        String pageNumStr = String.valueOf(params.get("pageNum"));
        String pageSizeStr = String.valueOf(params.get("pageSize"));
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

        PageInfo<Map<String, Object>> pageInfo = supplierchainService.orderInfoBuyCycle(params);

        return new Result<>(pageInfo);
    }


    /**
     * 导出订单数据统计 - 复购周期
     *
     * @param startTime
     * @return
     */
    @RequestMapping(value = "exportOrderInfoBuyCycle")
    public Result<Object> exportOrderInfoBuyCycle(HttpServletResponse response, String startTime, String endTime, Integer sort) {
        Map<String,Object> params = new HashMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("sort",sort);
        Map<String, Object> params02 = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params02 == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = params02;
        }

        String fileName = "订单数据统计-复购周期-" + java.lang.System.currentTimeMillis() + ".xls";
        HSSFWorkbook wb = supplierchainService.exportOrderInfoBuyCycle(params);
        try {
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 订单数据统计 - 新老会员贡献度
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orderInfoMembersContribution", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderInfoMembersContribution(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Map<String, List<Object>> data = null;
        data = supplierchainService.orderInfoMembersContribution(params);
        Result<Object> result = new Result<>();
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 导出订单数据统计 - 新老会员贡献度
     *
     * @param startTime
     * @return
     */
    @RequestMapping(value = "exportOrderInfoMembersContribution")
    public Object exportOrderInfoMembersContribution(HttpServletResponse response, String startTime, String endTime, Integer sort) {
        Map<String,Object> params = new HashMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("sort",sort);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String fileName = "订单数据统计-新老会员贡献度-" + java.lang.System.currentTimeMillis() + ".xls";
        HSSFWorkbook wb = supplierchainService.exportOrderInfoMembersContribution(params);
        try {
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 订单数据统计 - 完成率
     *
     * @param params {"startTime":"2018-01-01","endTime":"2019-01-01","type":"2","sort":1}
     *               type  1：事业部完成率   2：地区完成率   3：国家完成率
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orderInfoDoneRate", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderInfoDoneRate(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Result<Object> result = new Result<>();
        Map<String, List<Object>> data = null;
        String type = String.valueOf(params.get("type"));
        if ("1".equals(type)) {
            data = supplierchainService.orderInfoDoneRateGroupbyOrg(params);
        } else if ("2".equals(type)) {
            data = supplierchainService.orderInfoDoneRateGroupbyArea(params);
        } else if ("3".equals(type)) {
            data = supplierchainService.orderInfoDoneRateGroupbyCountry(params);
        }
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }

    /**
     * 订单数据统计 - 完成率02
     *
     * @param params {"startTime":"2018-01-01","endTime":"2019-01-01","type":"2","sort":1}
     *               type  1：事业部完成率   2：地区完成率   3：国家完成率
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orderInfoDoneRate02", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderInfoDoneRate02(@RequestBody Map<String, Object> params) {
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Result<Object> result = new Result<>();
        Map<String, List<Object>> data = null;
        String type = String.valueOf(params.get("type"));
        if ("1".equals(type)) {
            data = supplierchainService.dayOrderInfoDoneRateGroupbyOrg(params);
        } else if ("2".equals(type)) {
            data = supplierchainService.dayOrderInfoDoneRateGroupbyArea(params);
        } else if ("3".equals(type)) {
            data = supplierchainService.dayOrderInfoDoneRateGroupbyCountry(params);
        }
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }

    /**
     * 导出订单数据统计 - 完成率02
     *
     * @param type {"startTime":"2018-01-01","endTime":"2019-01-01","type":"2","sort":1}
     *               type  1：事业部完成率   2：地区完成率   3：国家完成率
     * @return
     */
    @RequestMapping(value = "exportOrderInfoDoneRate02")
    public Object exportOrderInfoDoneRate02(HttpServletResponse response,String type,String startTime, String endTime, Integer sort) {
        Map<String,Object> params = new HashMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("sort",sort);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        HSSFWorkbook wb = null;
        StringBuilder fileName = new StringBuilder("订单数据统计-");
        if ("1".equals(type)) {
            fileName.append("事业部完成率");
            wb = supplierchainService.exportDayOrderInfoDoneRateGroupbyOrg(params);
        } else if ("2".equals(type)) {
            fileName.append("地区完成率");
            wb = supplierchainService.exportDayOrderInfoDoneRateGroupbyArea(params);
        } else if ("3".equals(type)) {
            fileName.append("国家完成率");
            wb = supplierchainService.exportDayOrderInfoDoneRateGroupbyCountry(params);
        }
        try {
            fileName.append("-").append(System.currentTimeMillis()).append(".xls");
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
